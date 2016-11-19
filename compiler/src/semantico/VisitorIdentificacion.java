package semantico;

import ast.expresion.Variable;
import ast.sentencia.Sentencia;
import ast.sentencia.definicion.Definicion;
import ast.sentencia.definicion.DefinicionFuncion;
import ast.sentencia.definicion.DefinicionVariable;
import ast.tipo.TipoError;
import errors.ManejadorErrores;
import tablaSimbolos.TablaSimbolos;
import visitor.DefaultVisitor;

/* Se ejecuta antes que visitor Semantico 
 * 		- Comprobar que todas las variables que utilizamos estan declaradas */
public class VisitorIdentificacion extends DefaultVisitor {

	/* Tareas:
	 * 		- Enlazar una variable con su definición: 
	 * 				buscamos en todo nuestro programa variables. Cuando 
	 * 				encontramos una variable la enlazamos a su definición. 
	 * 				A traves de su definición puedo obtener su tipo.
	 */
	
	ManejadorErrores manejador;
	TablaSimbolos ts = new TablaSimbolos();
	
	public VisitorIdentificacion() {
		manejador = ManejadorErrores.getInstance();
	}

	public Object visit(Variable node, Object param) {
		
		Definicion def = ts.buscar(node.getNombre());
		
		if (def == null){ 
			manejador.addError(new TipoError(node.getLinea(), node.getColumna(), node, "Identificación -> Identificador no definido: " + node.getNombre()));
		} else {
			node.setDefinicion(def);	// Enlazar referencia con definicion
//			System.out.println(node.getLinea() + " " + node.getNombre() + "  :  " + node.getDefinicion().toString());
		}
		
		return null;
	}

	public Object visit(DefinicionVariable node, Object param) {
		
		// Buscamos si la variable esta definida en el contexto actual
		if(ts.buscarAmbitoActual(node.getNombre()) != null)
			manejador.addError(new TipoError(node.getLinea(), node.getColumna(), node, "Identificación -> Variable ya definida: " + node.getNombre()));
		else
			ts.insertar(node);

		if (node.getTipo() != null) // necesario por las funciones
			node.getTipo().accept(this, param);

		return null;
	}

	public Object visit(DefinicionFuncion node, Object param) {
				
		if (ts.buscar(node.getNombre()) != null)
			manejador.addError(new TipoError(node.getLinea(), node.getColumna(), node, "Identificación -> Función ya definida: " + node.getNombre()));
		else
			ts.insertar(node);
		
// IMPORTANTE: Comprobar que el tipo de retorno de la funcion y los parametros son tipos basicos
		
		ts.set();
			/* NOTA: varibales locales de una funcion y parametros de la funcion tienen el mismo ambito */
		
			// Recorrer el tipo de la funcion
			if (node.getTipo() != null)
				node.getTipo().accept(this, param);
				
			// Recorrer variables
			if (node.getVariables() != null){ // variables
				for (DefinicionVariable child : node.getVariables()){
					child.accept(this, param);
				}
			}

			// Recorrer Sentencias
			if (node.getSentencias() != null){
				for (Sentencia child : node.getSentencias()){
					child.accept(this, param);
				}
			}
			
		ts.reset();

		return null;
	}
}
