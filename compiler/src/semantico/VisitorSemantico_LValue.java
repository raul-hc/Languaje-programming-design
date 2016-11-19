package semantico;

import ast.expresion.AccesoArray;
import ast.expresion.AccesoCampo;
import ast.expresion.Aritmetica;
import ast.expresion.Expresion;
import ast.expresion.Variable;
import ast.sentencia.Asignacion;
import ast.sentencia.Lectura;
import ast.tipo.TipoError;
import errors.ManejadorErrores;
import visitor.DefaultVisitor;

/**
 * Visitor semantico 1 - Lab. num. 6 DLP --> LValues
 * 
 * 		Solamente hay 3 cosas que son LValues: 
 * 			- Accesos a campos
 * 			- Accesos a arrays 
 * 			- Variables
 * 
 * 	Solo hay que implementar 12 metodos visit.
 *  
 * 		Hay que hacer 2 comprobaciones:
 * 			- Asignacion: tenemos que comprobar que lo que hay a la izda sea un Lvalue
 *  		- Lectura: miramos si todas las expresiones que me pasan en la lista de expresiones son LValues. 
 */
public class VisitorSemantico_LValue extends DefaultVisitor {

//Expresiones
	
	public Object visit(AccesoArray node, Object param){
		node.setLValue(true);
		
		return null;
	}
	
	public Object visit(AccesoCampo node, Object param){
		node.setLValue(true);

		return null;
	}
	
	public Object visit(Variable node, Object param){
		node.setLValue(true);
		
		return null;
	}
	
	/* No sería necesario. */
	public Object visit(Aritmetica node, Object param){
		// Recorrido preorden -> primero visito a los hijos
		node.getExprIzqda().accept(this, param);
		node.getExprDcha().accept(this, param);
		//  -> luego hago mis calculos
		node.setLValue(false);
				
		return null;
	}
	
	/* 	setLValue(false);
	 * 	public Object visit(Comparacion node, 		Object param);
		public Object visit(ExpresionLogica node, 	Object param);
		public Object visit(MenosUnario node, 		Object param);
		public Object visit(NegacionLogica node, 	Object param);
		public Object visit(Cast node, 				Object param);
		public Object visit(Invocacion node, 		Object param); */

// Sentencias		
	public Object visit(Asignacion node, Object param){
		// Recorrido preorden -> primero visito a los hijos ->
		node.getExprIzqda().accept(this, param);
		node.getExprDcha().accept(this, param);
		
		// -> luego hago mis calculos -> node.setLValue(true); -> a la asignacion no hay que calcularle LValue
		if (!node.getExprIzqda().getLValue()){
			TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(),node.getExprIzqda(), "Se esperaba un LValue");
			ManejadorErrores.getInstance().addError(tipoError);
			node.getExprIzqda().setTipo(tipoError);
		}
		return null;
	}
	
	public Object visit(Lectura node, Object param){
		for (Expresion e : node.getExpresiones()){
			e.accept(this, param);	
			
			if (!e.getLValue()){
				TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), e, "Se esperaba un LValue");
				ManejadorErrores.getInstance().addError(tipoError);
				e.setTipo(tipoError);
			}
		}
					
		return null;
	}
	
}