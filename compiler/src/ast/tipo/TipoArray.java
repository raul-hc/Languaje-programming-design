package ast.tipo;

import visitor.Visitor;

//public class TipoArray implements Tipo {
public class TipoArray extends AbstractTipo {

	public Integer tamano;
	public Tipo tipoArray;

	public TipoArray(Tipo tipo, Integer tamano) {
		this.tipoArray = tipo;
		this.tamano = tamano;
	}

	@Override
	public String toString() {
		return "TipoArray [tipo=" + tipoArray + ", tamano=" + tamano + "]";
	}

	public Tipo getTipoRetorno() {
		return tipoArray;
	}

	public void setTipo(Tipo tipo) {
		this.tipoArray = tipo;
	}

	public Integer getTamano() {
		return tamano;
	}

	public void setTamano(Integer tamano) {
		this.tamano = tamano;
	}
	
	
	public int calculaOffset() {

		int total = tamano*tipoArray.getNumeroBytes();
		
		if (tipoArray.esBasico()){ 	//array de 1 Dimension
			return total;
		} else { 					//array > 1 Dimension
			int tamArrayInterior = aux((TipoArray) this.tipoArray);
			return this.tamano * tamArrayInterior;  // Multiplico por -1 debido a que negativo * negativo = positivo
		}
		
	}
	
	public int aux(TipoArray tipo){
		return tipo.tamano * tipo.tipoArray.getNumeroBytes() ;
	}
	
	

	/**
	 * Si lo que me pasan como parametro es un TipoEntero o TipoChar -> return TipoEntero 
	 * 	ELSE -> return null;
	 */
	public Tipo corchete(Tipo tipoOtro) {
		if (tipoOtro instanceof TipoEntero || tipoOtro instanceof TipoCaracter){
			return new TipoEntero();
		}

		return null;
	}
	
	public char getSufijo() {
		return tipoArray.getSufijo();
	}

	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}