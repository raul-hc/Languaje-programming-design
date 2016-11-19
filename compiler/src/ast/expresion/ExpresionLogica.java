package ast.expresion;

import visitor.Visitor;

public class ExpresionLogica extends ExpresionBinaria {

	public ExpresionLogica(int linea, int columna, Expresion exprIzqda, String operador, Expresion exprDcha) {
		super(linea, columna, exprIzqda, operador, exprDcha);
	}
	
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}
	
	
}
