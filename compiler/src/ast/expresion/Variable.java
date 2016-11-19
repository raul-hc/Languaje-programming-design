package ast.expresion;

import ast.sentencia.definicion.Definicion;
import visitor.Visitor;

public class Variable extends AbstractExpresion {
	
	public Definicion definicion;// Referencia a su definicion

	public String nombre;

	
	public Variable(int linea, int columna, String nombre) {
		super(linea, columna);
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public Definicion getDefinicion() {
		return definicion;
	}
	public void setDefinicion(Definicion definicion) {
		this.definicion = definicion;
	}

	@Override
	public String toString() {
		return nombre;
	}

	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
