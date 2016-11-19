package ast.expresion;

import visitor.Visitor;

public class LiteralChar extends AbstractExpresion {

	private Character caracter;

	public LiteralChar(int linea, int columna, Character caracter) {
		super(linea, columna);
		this.caracter = caracter;
	}

	public int getValor() {
		int valor = caracter;
		return valor;
	}
	
	@Override
	public String toString() {
		return String.valueOf(caracter);
	}

	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}
	
}
