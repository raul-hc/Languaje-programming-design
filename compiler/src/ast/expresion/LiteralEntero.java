package ast.expresion;

import visitor.Visitor;

public class LiteralEntero extends AbstractExpresion {

	public int valor;

	public LiteralEntero(int linea, int columna, int valor) {
		super(linea, columna);
		this.valor = valor;
	}
	
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	public int getValor() {
		return valor;
	}

}
