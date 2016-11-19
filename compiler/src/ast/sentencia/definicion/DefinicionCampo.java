package ast.sentencia.definicion;

import ast.tipo.Tipo;
import visitor.Visitor;


public class DefinicionCampo extends AbstractDefinicion {

	private String nombre;
	private Tipo tipo;
	
	public int offset;

	public DefinicionCampo(int linea, int columna, String nombre, Tipo tipo) {
		super(linea, columna);
		
		this.nombre = nombre;
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}
	
	public Tipo getTipo(){
		return tipo;
	}
	
	
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	@Override
	public String toString() {
		return "Definicion CAMPO [nombre=" + nombre + ", tipo=" + tipo + ", ambito=" + ambito + "]  " + "(offset: " + offset +")";
	}

	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}
}
