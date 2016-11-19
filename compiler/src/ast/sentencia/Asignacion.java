package ast.sentencia;

import ast.AbstractNodeAST;
import ast.expresion.Expresion;
import visitor.Visitor;

public class Asignacion extends AbstractNodeAST implements Sentencia {

	public Expresion exprIzqda;
	public Expresion exprDcha;
	
	public Asignacion(int linea, int columna, Expresion exprIzqda, Expresion exprDcha) {
		super(linea, columna);
		
		this.exprIzqda = exprIzqda;
		this.exprDcha = exprDcha;
	}

	public Expresion getExprIzqda() {
		return exprIzqda;
	}

	public Expresion getExprDcha() {
		return exprDcha;
	}
	
	@Override
	public String toString() {
		return exprIzqda + " = " + exprDcha;
	}
	
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}
}
