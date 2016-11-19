package ast.tipo;

import ast.NodoAST;
import visitor.Visitor;

public class TipoError extends AbstractTipo {

	public String mensaje;
	public NodoAST nodo;
	
	int linea, columna;
	
	public TipoError(NodoAST nodo, String mensaje) {
		this.linea = -1;
		this.columna = -1;
		
		this.nodo = nodo;
		this.mensaje = mensaje;
	}

	public TipoError(int linea, int columna, NodoAST nodo, String mensaje) {
		this.linea = linea;
		this.columna = columna;
		
		this.nodo = nodo;
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		if (mensaje != null){
			if (nodo==null)
				return ("[" + linea + ": " + columna + "] ->  " + mensaje);
			else 
				return ("[" + linea + ": " + columna + "] ->  " + mensaje + " (" + nodo.toString() + ")");
		}
		return "";
	}

	public NodoAST getNodo() {
		return nodo;
	}
	
	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}
	
}