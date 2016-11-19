package ast.expresion;

import visitor.Visitor;

public class NegacionLogica extends AbstractOperadorUnario {

	public NegacionLogica(int linea, int columna, Expresion expresion) {
		super(linea, columna, "!", expresion);
	}
	
	public Expresion getExpresion() {
		return expresion;
	}
	
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
