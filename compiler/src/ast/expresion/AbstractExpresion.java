package ast.expresion;

import ast.AbstractNodeAST;
import ast.tipo.Tipo;

public abstract class AbstractExpresion extends AbstractNodeAST implements Expresion {

	public AbstractExpresion(int linea, int columna){
		super(linea, columna);
	}
	
	/* LValues */
	boolean LValue;
		public boolean getLValue(){ return LValue; }	
		public void setLValue(boolean b){ this.LValue = b; }
	
	/* Tipos */
	Tipo tipo; 			// Todas las expresiones van a tener un tipo 
		public Tipo getTipo(){ return tipo; }	
		public void setTipo(Tipo t){ this.tipo = t; }
	
}
