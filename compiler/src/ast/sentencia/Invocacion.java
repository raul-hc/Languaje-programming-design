package ast.sentencia;

import java.util.List;

import ast.expresion.AbstractExpresion;
import ast.expresion.Expresion;
import ast.expresion.Variable;
import visitor.Visitor;

public class Invocacion extends AbstractExpresion implements Sentencia, Expresion {
	
	public Variable funcion; //public String nombre;
	public List<Expresion> parametros;
	
	public Invocacion(int linea, int columna, String nombre, List<Expresion> parametros) {
		super(linea, columna);
		
		this.parametros = parametros;
		
		funcion = new Variable(linea, columna, nombre); //this.nombre = nombre;
	}

	public Variable getFuncion() { return funcion; } //public String getNombre() { return nombre; }
	
	public List<Expresion> getParametros() {
		return parametros;
	}
	
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
}
