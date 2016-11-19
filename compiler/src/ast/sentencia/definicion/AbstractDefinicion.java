package ast.sentencia.definicion;

import ast.AbstractNodeAST;

public abstract class AbstractDefinicion extends AbstractNodeAST implements Definicion {

	public int ambito;
	
	public AbstractDefinicion(int linea, int columna) {
		super(linea, columna);
	}
	
	public int getAmbito(){ return ambito; }
	public void setAmbito(int ambito) { this.ambito = ambito; }
}
