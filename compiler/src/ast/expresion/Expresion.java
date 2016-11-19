package ast.expresion;

import ast.NodoAST;
import ast.tipo.Tipo;

public interface Expresion extends NodoAST {

	public boolean getLValue();	
	public void setLValue(boolean b);
	
	public Tipo getTipo();
	public void setTipo(Tipo t);
}
