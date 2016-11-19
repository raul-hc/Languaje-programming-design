package ast.expresion;

import visitor.Visitor;

public class AccesoArray extends AbstractExpresion {

	private Expresion nombreArray;
	private Expresion indice;
	
	public AccesoArray(int linea, int columna, Expresion nombreArray, Expresion indice) {
		super(linea, columna);

		this.nombreArray = nombreArray;
		this.indice = indice;
	}
	
	public Expresion getNombreArray() {
		return nombreArray;
	}
	
	public Expresion getIndice() {
		return indice;
	}
	
	@Override
	public String toString() {
		return nombreArray + " [" + indice + "]";
	}
	
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
