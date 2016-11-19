package ast.tipo;

import java.util.List;

public abstract class AbstractTipo implements Tipo {

	/** Todos aquellos tipos que puedan ser utilizados como valores booleanos van a devolver TRUE */
	public boolean esLogico(){
		return false;
	}
	
	/** Lo implementan solamente los tipos basicos */
	public Tipo aritmetica(Tipo tipoOtro){
		return null;
	}
	/** Lo implementan solamente los tipos basicos */
	public Tipo aritmetica(){
		return null;
	}
	
	public boolean esBasico(){
		return false;
	}

	public Tipo asignacion(Tipo tipoOtro){
		return null;
	}
	
	public Tipo comparacion(Tipo tipoOtro){
		return null;
	}
	
	/*El resultado va a ser un entero siempre, pero hay que comprobar las combinaciones*/
	public Tipo logica(Tipo tipo){
		return null;
	}
	/** Lo implementar Char y Entero -> Devuelve TipoEntero en los dos casos */
	public Tipo logica(){
		return null;
	}
	
	/** Para los accesos a campos -> Solamente en TipoStruct */
	public Tipo punto(String nombre){
		return null;
	}
	
	/** Si lo que me pasan es un TipoEntero o TipoChar -> return TipoEntero ELSE -> return null; */
	public Tipo corchete(Tipo tipoOtro){
//		System.out.println("METODO CORCHETE DE ABSTRACT TIPO");
		if (tipoOtro instanceof TipoEntero || tipoOtro instanceof TipoCaracter){
			return new TipoEntero();
		}

		return null;
	}
	
	/** En TipoFuncion -> Devuelve el tipo de retorno de la función */
	public Tipo parentesis(List<Tipo> tipo){
		return null;
	}
	
	public Tipo cast(Tipo tipoOtro){
		return null;
	}
	
	public Tipo getTipoRetorno() {
		return this;
	}
	
	
	
	public int calculaOffset(){
		return 0;
	}
	
	// Para las comparaciones
	public Tipo mayor(Tipo tipo){
		return null;
	}
	
	public int campo(String nombreCampo){
		return 0;
	}
	
	
	public int getNumeroBytes(){
		return 0;
	}
	
	public char getSufijo(){
		return 'i';
	}
}
