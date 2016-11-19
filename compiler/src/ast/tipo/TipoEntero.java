package ast.tipo;

import visitor.Visitor;

public class TipoEntero extends AbstractTipo {
	
	public boolean esLogico(){
		return true;
	}
	
	public Tipo aritmetica(Tipo tipoOtro){
		
		if (tipoOtro instanceof TipoCaracter){
			return this; // Entero + Caracter = ENTERO
		}
		if (tipoOtro instanceof TipoEntero){
			return this; // Entero + Entero = ENTERO
		}
		if (tipoOtro instanceof TipoDouble){
			return tipoOtro; // Entero + Double = DOUBLE
		} 
		
		return null; // Para el resto de tipos retorno null
	}
	public Tipo aritmetica(){
		return this;
	}
	
	public Tipo asignacion(Tipo tipoOtro){
		if (tipoOtro instanceof TipoCaracter) // ENTERO = caracter -> ENTERO
			return this;
		if (tipoOtro instanceof TipoEntero){  // ENTERO = ENTERO -> ENTERO
			return this;
		}
		
		return null;
	}
	
	public Tipo comparacion(Tipo tipoOtro){
		if (tipoOtro instanceof TipoCaracter){
			return (new TipoEntero().getTipoRetorno());
		}
		if (tipoOtro instanceof TipoEntero){
			return tipoOtro;
		}
		
		return null;
	}
	
	public Tipo logica(Tipo tipoOtro){
		if (tipoOtro instanceof TipoEntero){
			return tipoOtro;
		}
		if (tipoOtro instanceof TipoCaracter){
			return (new TipoEntero().getTipoRetorno());
		}
		
		return null; // Para el resto de tipos retorno null
	}
	
	public Tipo logica(){
		return this;
	}
	
	public Tipo cast(Tipo tipoOtro){
		if (tipoOtro.esBasico()){
			return tipoOtro;
		}
		return null;
	}
	
	public boolean esBasico(){
		return true;
	}
	
	public int getNumeroBytes() {
		return 2;
	}
	
	public int calculaOffset() {
		return getNumeroBytes();
	}
	
	public Tipo mayor(Tipo tipoOtro) {
		if (tipoOtro instanceof TipoCaracter || tipoOtro instanceof TipoEntero){
			return this;
		}
		if (tipoOtro instanceof TipoDouble){
			return tipoOtro;
		}
		
		return null;
	}
	
	public char getSufijo() {
		return 'i';
	}
	
	@Override
	public String toString () {
		return "int";
	}
	
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	
}