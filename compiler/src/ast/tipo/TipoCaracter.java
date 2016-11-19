package ast.tipo;

import visitor.Visitor;

public class TipoCaracter extends AbstractTipo {

	public boolean esLogico(){ // porque promociona a entero
		return true;
	}
	
	public boolean esBasico(){
		return true;
	}
	
	public Tipo aritmetica(Tipo tipoOtro){
		
		if (tipoOtro instanceof TipoCaracter){
			return (new TipoEntero().getTipoRetorno()); // Caracter + Caracter = ENTERO
		}
		if (tipoOtro instanceof TipoEntero){
			return (new TipoEntero().getTipoRetorno()); // Caracter + Entero = ENTERO
		}
		if (tipoOtro instanceof TipoDouble){
			return tipoOtro; // Caracter + Double = DOUBLE
		} 
		
		return null; // Para el resto de tipos retorno null
	}
	public Tipo aritmetica(){
		return  new TipoEntero().getTipoRetorno();
	}
	
	public Tipo asignacion(Tipo tipoOtro){
		if (tipoOtro instanceof TipoCaracter)// CARACTER = CARACTER -> CARACTER
			return this;
		
		return null;
	}
	
	public Tipo comparacion(Tipo tipoOtro){
		if (tipoOtro instanceof TipoEntero){
			return tipoOtro;
		}
		if (tipoOtro instanceof TipoCaracter){
			return (new TipoEntero().getTipoRetorno());
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
		return (new TipoEntero().getTipoRetorno());
	}
	
	public Tipo cast(Tipo tipoOtro){
		if (tipoOtro.esBasico()){
			return tipoOtro;
		}
		return null;
	}
	
	public int getNumeroBytes() {
		return 1;
	}
	
	public int calculaOffset() {
		return getNumeroBytes();
	}
	
	public Tipo mayor(Tipo tipoOtro) {
		if (tipoOtro instanceof TipoCaracter){
			return this;
		}
		if (tipoOtro instanceof TipoEntero){
			return tipoOtro;
		}
		if (tipoOtro instanceof TipoDouble){
			return tipoOtro;
		}
		
		return null;
	}
	
	public char getSufijo() {
		return 'b';
	}	
	
	@Override
	public String toString () {
		return "char";
	}
	
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
}