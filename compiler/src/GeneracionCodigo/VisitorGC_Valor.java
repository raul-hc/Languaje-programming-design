package GeneracionCodigo;

import java.util.HashMap;
import java.util.Map;

import ast.expresion.AccesoArray;
import ast.expresion.AccesoCampo;
import ast.expresion.Aritmetica;
import ast.expresion.Cast;
import ast.expresion.Comparacion;
import ast.expresion.Expresion;
import ast.expresion.ExpresionLogica;
import ast.expresion.LiteralChar;
import ast.expresion.LiteralEntero;
import ast.expresion.LiteralReal;
import ast.expresion.NegacionLogica;
import ast.expresion.Variable;
import ast.sentencia.Invocacion;
import ast.sentencia.Return;
import ast.sentencia.definicion.DefinicionFuncion;
import ast.sentencia.definicion.DefinicionVariable;
import ast.tipo.Tipo;
import ast.tipo.TipoFuncion;
import visitor.DefaultVisitor;

/**
 * Visitor VisitorGC_Valor: 
 * Genera el codigo necesario para apilar el valor de una EXPRESION 
 * en el tope de la pila
 * 
 * No todas las expresiones tienen direccion, pero si tienen valor todas
 */
public class VisitorGC_Valor extends DefaultVisitor {
	private Map<String, Integer> asciiCodes = new HashMap<String, Integer>();

	VisitorGC_Direccion visitorGC_Direccion = new VisitorGC_Direccion();
	
	public VisitorGC_Valor(){
		initAscii();
	}
	
	public Object visit(LiteralEntero node, Object param) {
		GeneradorCodigo.push(node.getTipo(), node.getValor());
		return null;
	}

	public Object visit(LiteralReal node, Object param) {
		GeneradorCodigo.push(node.getTipo(), node.getValor());
		return null;
	}

	public Object visit(LiteralChar node, Object param) {
		// Si es salto de linea o tabulador
		if (node.getValor() == 92) {
			GeneradorCodigo.push(node.getTipo(), 10);
		} else {
			GeneradorCodigo.push(node.getTipo(), node.getValor());
		}
		
		return null;
	}
	
	public Object visit(Variable node, Object param) {
		node.accept(this.visitorGC_Direccion, param);
		GeneradorCodigo.load(node.getTipo());
		return null;
	}
	
	public Object visit(Aritmetica node, Object param) {

		if (node.getExprIzqda() != null)
			node.getExprIzqda().accept(this, param);
		
	// Convertir el tipo de los operandos al tipo resultante	
		GeneradorCodigo.convertir(node.getExprIzqda(), node.getTipo());

		if (node.getExprDcha() != null)
			node.getExprDcha().accept(this, param);
		
	// Convertir el tipo de los operandos al tipo resultante	
		GeneradorCodigo.convertir(node.getExprDcha(), node.getTipo());

			if (node.getOperador().equals("+"))
				GeneradorCodigo.add(node.getTipo());
			else if (node.getOperador().equals("-"))
				GeneradorCodigo.sub(node.getTipo());
			else if (node.getOperador().equals("*"))
				GeneradorCodigo.mul(node.getTipo());
			else if (node.getOperador().equals("/"))
				GeneradorCodigo.div(node.getTipo());
			else if (node.getOperador().equals("%"))
				GeneradorCodigo.modulo(node.getTipo());
			
			
			
		
		return null;
	}
	
	public Object visit(Comparacion node, Object param) {
		
		if (node.getExprIzqda() != null)
			node.getExprIzqda().accept(this, param);
		
		if (node.getExprDcha() != null)
			node.getExprDcha().accept(this, param);

		// La comparacion no es a nivel de entero, es a nivel del mayor
		Tipo tipoResultante = node.getExprIzqda().getTipo().mayor(node.getExprIzqda().getTipo());
		GeneradorCodigo.convertir(node.getExprIzqda(), tipoResultante);
		GeneradorCodigo.convertir(node.getExprDcha(), tipoResultante);
		
		if (node.getOperador().equals(">"))
			GeneradorCodigo.greaterThan(tipoResultante);
		else if (node.getOperador().equals("<"))
			GeneradorCodigo.lessThan(tipoResultante);
		else if (node.getOperador().equals(">="))
			GeneradorCodigo.greaterEqual(tipoResultante);
		else if (node.getOperador().equals("<="))
			GeneradorCodigo.lessEqual(tipoResultante);
		else if (node.getOperador().equals("=="))
			GeneradorCodigo.equal(tipoResultante);
		else if (node.getOperador().equals("!="))
			GeneradorCodigo.notEqual(tipoResultante);

		return null;
	}

	public Object visit(ExpresionLogica node, Object param) {
		
		if (node.getExprIzqda() != null)
			node.getExprIzqda().accept(this, param);
		
		if (node.getExprDcha() != null)
			node.getExprDcha().accept(this, param);

		if (node.getOperador().equals("&&"))
			GeneradorCodigo.and();
		else if (node.getOperador().equals("||"))
			GeneradorCodigo.or();

		return null;
	}
	
	public Object visit(NegacionLogica node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		GeneradorCodigo.not();
		
		return null;
	}
	
	public Object visit(Cast node, Object param) {
		
		if (node.getTipoDestinoCasteo() != null)
			node.getTipoDestinoCasteo().accept(this, param);
		
		if (node.getParametroCast() != null)
			node.getParametroCast().accept(this, param);
				
		GeneradorCodigo.convertir(node.getParametroCast(), node.getTipoDestinoCasteo());
		
		return null;
	}
	
	public Object visit(AccesoArray node, Object param) {
		if (node.getNombreArray() != null){
			node.getNombreArray().accept(this.visitorGC_Direccion, param);
		}
		
	if (node.getIndice() != null){
		node.getIndice().accept(this.visitorGC_Direccion, param);
	}
	
	GeneradorCodigo.load(node.getIndice().getTipo());
	GeneradorCodigo.pushi(node.getIndice().getTipo().getNumeroBytes());//node.getIndice().getTipo()
	GeneradorCodigo.muli();
	GeneradorCodigo.addi();
		
		GeneradorCodigo.load(node.getNombreArray().getTipo());
		
		return null;
	}
	
	public Object visit(AccesoCampo node, Object param) {
		if (node.getObjetoEstructura() != null)
			node.getObjetoEstructura().accept(this.visitorGC_Direccion, node.getNombreCampo());
		
	GeneradorCodigo.pushi(node.getObjetoEstructura().getTipo().campo(node.getNombreCampo()));
	GeneradorCodigo.addi();
		
		GeneradorCodigo.load(node.getObjetoEstructura().getTipo());
		
		return null;
	}
	
	public Object visit(Invocacion node, Object param) {
		
		//* Para invocar a una funcion nos hace falta apilar los parametros
		for (Expresion expr : node.getParametros()){
			expr.accept(this, param);
			GeneradorCodigo.convertir(expr, expr.getTipo());
		}
				
		GeneradorCodigo.call(node.getFuncion().getNombre());
			
		return null;
	}
	
	public Object visit(Return node, Object param) {

		node.getValorRetorno().accept(this, param);
		
		int tamRetorno = 0;
		int tamLocales = 0;
		int tamParametros = 0;
		
		if (param instanceof DefinicionFuncion){
			DefinicionFuncion df = (DefinicionFuncion) param;
			GeneradorCodigo.convertir(node.getValorRetorno(), df.getTipo().getTipoRetorno());
			
			tamRetorno = df.getTipo().getTipoRetorno().getNumeroBytes();
			tamLocales = df.getOffsetVariablesLocales();
		
			TipoFuncion tipoFuncion = (TipoFuncion) df.getTipo();
			for (DefinicionVariable defVariable : tipoFuncion.getParametros()){ 
				tamParametros += defVariable.getTipo().getNumeroBytes();
			}
		} 
		
		GeneradorCodigo.ret(tamRetorno, tamLocales, tamParametros);
		
		return null;
	}
	
	
	private void initAscii() {
		asciiCodes.put("'\n'", 10);
		asciiCodes.put("'\t'", 9);
	}

}