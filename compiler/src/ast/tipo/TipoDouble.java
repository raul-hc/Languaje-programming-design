package ast.tipo;

import visitor.Visitor;

public class TipoDouble extends AbstractTipo {
	
	
	public boolean esBasico(){
		return true;
	}
	
	public Tipo aritmetica(Tipo tipoOtro){
		if (tipoOtro.esBasico()) {
			return this; 
		} 
		return null; // Para el resto de tipos retorno null
	}
	public Tipo aritmetica(){
		return this;
	}
	
	public Tipo asignacion(Tipo tipoOtro){
		if (tipoOtro.esBasico()) {
			return this; 
		} 
		return null; // Para el resto de tipos retorno null
	}
	
	public Tipo comparacion(Tipo tipoOtro){
		if (tipoOtro.esBasico()){
			return (new TipoEntero().getTipoRetorno());
		}
		return null; // Para el resto de tipos retorno null
	}
	
	public Tipo logica(Tipo tipoOtro){
		return null; // TODO // Para el resto de tipos retorno null
	}
	
	public Tipo cast(Tipo tipoOtro){
		if (tipoOtro.esBasico()){
			return tipoOtro;
		}
		return null;
	}
	
	public int getNumeroBytes() {
		return 4;
	}
	
	public int calculaOffset() {
		return getNumeroBytes();
	}
	
	public Tipo mayor(Tipo tipoOtro) {
		if (tipoOtro.esBasico()){
			return this;
		}
		
		return null;
	}
	
	public char getSufijo() {
		return 'f';
	}
	
	@Override
	public String toString () {
		return "double";
	}
	
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
}