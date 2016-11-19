package ast.expresion;

import visitor.Visitor;

public class Comparacion extends ExpresionBinaria {

	
	
	public Comparacion(int linea, int columna, Expresion exprIzqda, String operador, Expresion exprDcha) {
		super(linea, columna, exprIzqda, operador, exprDcha);
	}

	@Override
	public String toString() {
		return exprIzqda + " " + operador + " " + exprDcha;
	}
	
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}
	
}
