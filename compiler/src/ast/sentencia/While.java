package ast.sentencia;

import java.util.List;

import ast.AbstractNodeAST;
import ast.expresion.Expresion;
import visitor.Visitor;

public class While extends AbstractNodeAST implements Sentencia {

	private List<Sentencia> sentencias;
	public Expresion condicion;

	public While(int line, int column, Expresion condicion, List<Sentencia> sentencias) {
		super(line, column);
		
		this.condicion = condicion;
		this.sentencias = sentencias;
	}

	public List<Sentencia> getSentencias() {
		return sentencias;
	}
	
	public Expresion getCondicion(){
		return condicion;
	}
	
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}
}
