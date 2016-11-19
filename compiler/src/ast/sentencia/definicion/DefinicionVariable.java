package ast.sentencia.definicion;

import visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

import ast.tipo.Tipo;
import ast.tipo.TipoArray;

public class DefinicionVariable extends AbstractDefinicion {

	public String nombre;
	public Tipo tipo;
	
	public int offset;

	public DefinicionVariable(int linea, int columna, String nombre, Tipo tipo) {
		super(linea, columna);

		this.nombre = nombre;
		this.tipo = tipo;

		invertirArray_2Dimensiones();
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	/* De momento unicamente valido para arrays de 2 dimensiones */
	public void invertirArray_2Dimensiones() {
		// Array[10][4] --> [tipo=TipoArray[tipo=int,tamaño=10], tamaño=4] //
		// INCORRECTO
		// Array[10][4] --> [tipo=TipoArray[tipo=int,tamaño=4], tamaño=10] //
		// CORRECTO

		List<Tipo> tipos = new ArrayList<Tipo>(); // TiposArray
		tipos.add(this.tipo);
		Tipo aux;

		if (tipo instanceof TipoArray) { // Nos llega un tipo array, que es un
											// tipo compuesto

			aux = tipo;

			while (aux.getTipoRetorno() instanceof TipoArray) { // Mientras no sea un
															// tipo basico
				tipos.add(aux.getTipoRetorno());
				aux = aux.getTipoRetorno();
			} // Al llegar aqui ya he llegado al final

			if (tipos.size() == 2) {
				// tipos = [TipoArray[tipo=tipoArray=[###],tamaño=4] ;
				// TipoArray[tipo=int,tamaño=10]]
				int tam1 = ((TipoArray) tipos.get(0)).getTamano(); // 4
				int tam2 = ((TipoArray) tipos.get(1)).getTamano(); // 10
				((TipoArray) tipos.get(0)).setTamano(tam2); // 10
				((TipoArray) tipos.get(1)).setTamano(tam1); // 4
			}

		}
	}

	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public String toString() {
		return "DefinicionVariable [nombre=" + nombre + ", tipo=" + tipo + ", ambito=" + ambito + "]  " + "(offset: " + offset +")";
	}

	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
