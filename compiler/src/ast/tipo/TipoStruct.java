package ast.tipo;

import java.util.List;

import ast.sentencia.definicion.DefinicionCampo;
import visitor.Visitor;

public class TipoStruct extends AbstractTipo {
	
	private List<DefinicionCampo> campos;

	public TipoStruct(List<DefinicionCampo> campos){		
		this.campos = campos;
	}
	
	public List<DefinicionCampo> getCampos() {
		return campos;
	}
	public void setCampos(List<DefinicionCampo> campos) {
		this.campos = campos;
	}

	public Tipo punto(String nombre){
		for (DefinicionCampo dc : campos){
			if (dc.getNombre().toString().equals(nombre.toString())){
				return dc.getTipo(); // devuelvo el tipo del campo al que se esta accediendo
			}
		}
		
		return null;
	}	
	
	public int calculaOffset() {
		
		int offsetTotal = 0;
		
		for (DefinicionCampo campo : getCampos()){
			offsetTotal += campo.getTipo().getNumeroBytes();
		}
		
		return offsetTotal;
	}
	
	public int campo(String nombreCampo){
		
		if (nombreCampo!= null){
		
		for (DefinicionCampo dc : campos){
			if (dc.getNombre().toString().equals(nombreCampo.toString())){
				return dc.getOffset(); // devuelvo el offset que tiene el campo dentro de la estructura (calculado en VisitorOffset)
			}
		}
		
		}
		
		return 0;
	}
	
	@Override
	public String toString() {
		return "TipoStruct";
	}

	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}
	
}