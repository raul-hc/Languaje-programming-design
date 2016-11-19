package ast.expresion;

import ast.tipo.Tipo;
import visitor.Visitor;

public class Cast extends AbstractExpresion {

	private Tipo tipoDestinoCast;
	private Expresion parametroCast;
	

	public Cast(int linea, int columna, Tipo tipo, Expresion parametroCast) {
		super(linea, columna);
		
		this.tipoDestinoCast = tipo;
		this.parametroCast = parametroCast;
	}
	
	public Tipo getTipoDestinoCasteo() {
		return this.tipoDestinoCast;
	}

	public Expresion getParametroCast() {
		return parametroCast;
	}

	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}
	
}
