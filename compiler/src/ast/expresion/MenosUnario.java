package ast.expresion;

import visitor.Visitor;

public class MenosUnario extends AbstractOperadorUnario {
	
	public MenosUnario(int linea, int columna, Expresion expresion) {
		super(linea, columna, "-", expresion);
	}
	
	public Expresion getExpresion() {
		return expresion;
	}

	@Override
	public String toString() {
		return "-(" + expresion + ")";
	}
	
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
