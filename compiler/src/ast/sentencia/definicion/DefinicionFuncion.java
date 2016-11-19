package ast.sentencia.definicion;

import java.util.List;

import ast.sentencia.Sentencia;
import ast.tipo.Tipo;
import visitor.Visitor;

public class DefinicionFuncion extends AbstractDefinicion {
	
	public String nombre;
	public Tipo tipoFuncion;	
	public List<DefinicionVariable> variables;
	public List<Sentencia> sentencias;
	
//	public int offsetVariablesLocales;


	public DefinicionFuncion(int linea, int columna, String nombre, Tipo tipo, List<DefinicionVariable> variables, List<Sentencia> sentencias) {
		super(linea, columna);

		this.nombre = nombre;
		this.tipoFuncion = tipo;
		this.variables = variables;
		this.sentencias = sentencias;
	}

	public String getNombre() {
		return nombre;
	}

	public List<DefinicionVariable> getVariables() {
		return variables;
	}

	public List<Sentencia> getSentencias() {
		return sentencias;
	}

	/** El offset de la ultima variable local nos dice exactamente lo que ocuparon las variables */
	public int getOffsetVariablesLocales() {
		if (variables.size()>0)
			return ( -1 * (variables.get(variables.size()-1)).getOffset() ); // Tenemos que cambiar el signo
		
		return 0;
	}

//	public void setOffsetVariablesLocales(int offsetVariablesLocales) {
//		this.offsetVariablesLocales = offsetVariablesLocales;
//	}

	@Override
	public String toString() {
		return "DefinicionFuncion [nombre=" + nombre + ", ambito=" + ambito
				+ ", var. locales: " + variables.size() + ", num. sents: "
				+ sentencias.size() + ", offsetVariablesLocales: " + getOffsetVariablesLocales() +"]";
	}

	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	public Tipo getTipo() {
		return tipoFuncion;
	}

}
