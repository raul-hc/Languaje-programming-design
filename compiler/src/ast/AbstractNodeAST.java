package ast;

public abstract class AbstractNodeAST implements NodoAST {

	public int linea;
	public int columna;
	
	public AbstractNodeAST(int linea, int columna) {
		this.linea = linea;
		this.columna = columna;
	}

	public int getLinea() {
		return linea;
	}

	public int getColumna() {
		return columna;
	}
	
}
