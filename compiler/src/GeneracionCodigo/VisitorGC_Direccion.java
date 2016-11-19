package GeneracionCodigo;

import ast.expresion.AccesoArray;
import ast.expresion.AccesoCampo;
import ast.expresion.Variable;
import ast.sentencia.definicion.DefinicionVariable;
import ast.tipo.TipoEntero;
import visitor.DefaultVisitor;

/**
 * Visitor VisitorGC_Direccion 
 * Genera el codigo necesario para apilar la Direccion de una EXPRESIÓN 
 * en el tope de la pila
 * 
 * No todas las expresiones tienen direccion, pero si tienen valor todas
 */
public class VisitorGC_Direccion extends DefaultVisitor {
	
	public Object visit(Variable node, Object param) {
		
		if (node.getDefinicion() instanceof DefinicionVariable){
			
			DefinicionVariable defVariable = (DefinicionVariable) node.getDefinicion();
			
			if (defVariable.getAmbito() == 0 ){ // Variable Global
				GeneradorCodigo.pusha(defVariable.getOffset());
			} else {							// Variable LOCAL o Parametro (Como lo guardamos con signo nos da igual que sea una cosa o la otra)
				GeneradorCodigo.pusha("bp");
				GeneradorCodigo.pushi(defVariable.getOffset());
				GeneradorCodigo.addi();
			}
			
		}
		return null;
	}
	
	public Object visit(AccesoArray node, Object param) {
		
		node.getNombreArray().accept(this, param);
		node.getIndice().accept(GeneradorCodigo.visitorGCValor, param);//node.getIndice().accept(this.visitorGC_Valor, param);
		
		GeneradorCodigo.convertir(node.getIndice(), new TipoEntero());
		
		GeneradorCodigo.pushi(node.getIndice().getTipo().getNumeroBytes());//		GeneradorCodigo.pushi(node.getNombreArray().getTipo().getNumeroBytes());

		GeneradorCodigo.muli();
		GeneradorCodigo.addi();
		
		return null;
	}
	
	public Object visit(AccesoCampo node, Object param) {

		node.getObjetoEstructura().accept(this, param);
		
		GeneradorCodigo.pushi(node.getObjetoEstructura().getTipo().campo(node.getNombreCampo()));
		GeneradorCodigo.addi();
						
//		GeneradorCodigo.pushi(node.getObjetoEstructura().getTipo().campo((String)param));
//		GeneradorCodigo.addi();
		
		return null;
	}

}