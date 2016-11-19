package ast;

import java.util.List;

import ast.sentencia.definicion.Definicion;
import visitor.Visitor;

public class Programa extends AbstractNodeAST {


	public List<Definicion> definiciones;

	public Programa (int linea, int columna, List<Definicion> definiciones) {
		
		super(linea, columna);
		this.definiciones = definiciones;
	}
	
	public List<Definicion> getDefiniciones() {
		return definiciones;
	}

	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}
	
}
