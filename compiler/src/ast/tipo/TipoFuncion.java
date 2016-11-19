package ast.tipo;

import java.util.List;

import ast.sentencia.definicion.DefinicionVariable;
import visitor.Visitor;

public class TipoFuncion extends AbstractTipo {

	public Tipo tipoRetorno;
	public List<DefinicionVariable> parametros;
	
	public TipoFuncion(List<DefinicionVariable> parametros, Tipo tipoRetorno) {
		this.tipoRetorno = tipoRetorno;
		this.parametros = parametros;
		
	}

	public List<DefinicionVariable> getParametros() {
		return parametros;
	}
	
	public Tipo getTipoRetorno() {
		return tipoRetorno;
	}

	public void setTipo(Tipo tipo) {
		this.tipoRetorno = tipo;
	}
	
	public Tipo parentesis(List<Tipo> tipos){
		
		// 1.- Comprobar que la longitud de la lista de parametros que me pasan es igual a la longitud  de la lista de parametros definida en el tipo
		// 2.- Ir comprobando tipo a tipo si los tipos (llamando a asignación) de los parametros coinciden
		if (tipos.size() == parametros.size()){
			for (int i=0 ; i < tipos.size() ; i++){
				if ( (parametros.get(i).getTipo()).asignacion(tipos.get(i)) == null){
					return null;
				}
			}
			return tipoRetorno;
		}
		
		return null;
	}
	
	public char getSufijo() {
		return tipoRetorno.getSufijo();
	}
	
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}