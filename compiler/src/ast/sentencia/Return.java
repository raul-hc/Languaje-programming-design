package ast.sentencia;

import ast.AbstractNodeAST;
import ast.expresion.Expresion;
import visitor.Visitor;

public class Return extends AbstractNodeAST implements Sentencia {

	private Expresion valorRetorno;

	public Return(int linea, int columna, Expresion valorRetorno) {
		super(linea, columna);
		this.valorRetorno = valorRetorno;
	}

	public String toString() {
		return "return " + valorRetorno;
	}
	
	public Expresion getValorRetorno() {
		return valorRetorno;
	}

	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}
}
