package ast.sentencia.definicion;

import ast.NodoAST;
import ast.tipo.Tipo;

public interface Definicion extends NodoAST {

	public Tipo getTipo();
	public String getNombre();
	
	public int getAmbito();
	public void setAmbito(int ambito);
	
}
