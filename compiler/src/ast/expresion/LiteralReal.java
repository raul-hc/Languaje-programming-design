package ast.expresion;

import visitor.Visitor;

public class LiteralReal extends AbstractExpresion {

	public double valor;

	public LiteralReal(int linea, int columna, double valor) {
		super(linea, columna);
		this.valor = valor;
	}

	public double getValor() {
		return valor;
	}
	
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}
	
}
