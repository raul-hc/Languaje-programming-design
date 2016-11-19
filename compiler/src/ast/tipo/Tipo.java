package ast.tipo;

import java.util.List;

import ast.NodoAST;

public interface Tipo extends NodoAST {
	
	/** Todos aquellos tipos que puedan ser utilizados como valores booleanos van a devolver TRUE. El resto devolveran false */
	public boolean esLogico();
	
	public Tipo aritmetica(Tipo tipoOtro);
	public Tipo aritmetica();			/** Lo implementan solamente los tipos basicos -> MenosUnario */
	
	public boolean esBasico();

	public Tipo asignacion(Tipo tipo);
	
	/* COMPARACIONES ARITMETICAS */
	public Tipo comparacion(Tipo tipo);
	
	public Tipo logica(Tipo tipo);
	public Tipo logica();				/** Lo implementar Char y Entero -> Devuelve TipoEntero en los dos casos */
	
	public Tipo punto(String nombre); 	/** Para los accesos a campos -> Solamente en TipoStruct */
	
	public Tipo corchete(Tipo tipo);	/** Si lo que me pasan es un TipoEntero o TipoChar -> return TipoEntero ELSE -> return null; */
	
	public Tipo parentesis(List<Tipo> tipo); /** En TipoFuncion -> Devuelve el tipo de retorno de la función */
	
	public Tipo cast(Tipo tipoOtro); /** Solamente se puede hacer cast a tipos basicos */

	
	
	public int calculaOffset();
	public int getNumeroBytes();
	public char getSufijo();
	
	public Tipo mayor(Tipo tipo);	
	
	/* Metodo utilizado a la hora de invertir las dimensiones de un array durante su creacion */
	public Tipo getTipoRetorno();

	/* Metodo que retorna el offset de un campo dentro de una estructura */
	public int campo(String nombreCampo);
	
}