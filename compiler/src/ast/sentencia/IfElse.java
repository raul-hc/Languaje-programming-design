package ast.sentencia;

import java.util.List;

import ast.AbstractNodeAST;
import ast.expresion.Expresion;
import visitor.Visitor;

public class IfElse extends AbstractNodeAST implements Sentencia {

	public List<Sentencia> cuerpoIF;
	public List<Sentencia> cuerpoELSE;
	public Expresion condicion;

	public IfElse(int linea, int columna, Expresion condicion, List<Sentencia> cuerpoIF, List<Sentencia> cuerpoELSE) {
		super(linea, columna);
		
		this.condicion = condicion;
		this.cuerpoIF = cuerpoIF;
		this.cuerpoELSE = cuerpoELSE;
	}

	public Expresion getCondicion(){
		return condicion;
	}
	
	public List<Sentencia> getCuerpoIF() {
		return cuerpoIF;
	}
	
	public List<Sentencia> getCuerpoELSE() {
		return cuerpoELSE;
	}

	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}
}
