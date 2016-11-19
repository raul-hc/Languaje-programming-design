package GeneracionCodigo;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import ast.NodoAST;
import ast.expresion.Expresion;
import ast.tipo.Tipo;
import ast.tipo.TipoCaracter;
import ast.tipo.TipoDouble;
import ast.tipo.TipoEntero;

public class GeneradorCodigo {

	static PrintWriter out;
	static int contadorEtiquetas;
	
	static VisitorGC_Direccion visitorGCDireccion;
	static VisitorGC_Valor visitorGCValor;
	static VisitorGC_Ejecutar visitorGC_Ejecutar;
	
	public GeneradorCodigo(NodoAST ast, String ficheroSalida) throws FileNotFoundException, UnsupportedEncodingException {
		
		out = new PrintWriter(ficheroSalida);
		out.println("\n#source	\"entrada.dlp\"\n");
		
		contadorEtiquetas = 0;
		
		visitorGCDireccion = new VisitorGC_Direccion();
		visitorGCValor = new VisitorGC_Valor();
		visitorGC_Ejecutar = new VisitorGC_Ejecutar(visitorGCDireccion, visitorGCValor);
		
		ast.accept(visitorGC_Ejecutar, null);
		
		out.close();
		System.out.println(" ~Fichero salidaGC generado correctamente.");
	}
	
//* Operadores Manipulación de la pila
	public static void push(Tipo tipo, int value){
		imprime("\t\t  push" + tipo.getSufijo() + "\t" + value);
	}
	public static void push(Tipo tipo, double value){
		imprime("\t\t  push" + tipo.getSufijo() + "\t" + value);//String.format(java.util.Locale.S,"%.1f", value) );
	}
	public static void pushi(int offset) {
		imprime("\t\t  pushi \t" + offset);
	}
	public static void pusha(int direccion){
		imprime("\t\t  pusha \t" + direccion);
	}
	public static void pusha(String puntero){
		imprime("\t\t  push \t" + puntero);
	}
	public static void load(Tipo tipo){
		imprime("\t\t  load" + tipo.getSufijo());
	}
	public static void store(Tipo tipo){
		imprime("\t\t  store" + tipo.getSufijo());
	}
	public static void pop(Tipo tipo){
		imprime("\t\t  pop" + tipo.getSufijo());
	}
	
//* Operadores Aritmeticos
	public static void add(Tipo tipo){
		imprime("\t\t  add" + tipo.getSufijo());
	}
	public static void addi() {
		imprime("\t\t  addi ");
	}
	public static void sub(Tipo tipo){
		imprime("\t\t  sub" + tipo.getSufijo());
	}
	public static void mul(Tipo tipo){
		imprime("\t\t  mul" + tipo.getSufijo());
	}
	public static void muli(){
		imprime("\t\t  muli ");
	}
	public static void div(Tipo tipo){
		imprime("\t\t  div" + tipo.getSufijo());
	}
	public static void modulo(Tipo tipo){
		imprime("\t\t  mod" + tipo.getSufijo());
	} 
	
//* Operadores Logicos
	public static void and(){
		imprime("\t\t  and");
	}
	public static void or(){
		imprime("\t\t  or");
	}
	public static void not(){
		imprime("\t\t  not");
	}	
	
//* Operadores Comparación
	public static void greaterThan(Tipo tipo){
		imprime("\t\t  gt" + tipo.getSufijo());
	}
	public static void lessThan(Tipo tipo){
		imprime("\t\t  lt" + tipo.getSufijo());
	}
	public static void greaterEqual(Tipo tipo){
		imprime("\t\t  ge" + tipo.getSufijo());
	}
	public static void lessEqual(Tipo tipo){
		imprime("\t\t  le" + tipo.getSufijo());
	}
	public static void equal(Tipo tipo){
		imprime("\t\t  eq" + tipo.getSufijo());
	}
	public static void notEqual(Tipo tipo){
		imprime("\t\t  ne" + tipo.getSufijo());
	}
	
//* Operadores E/S
	public static void out(Tipo tipo){
		imprime("\t\t  out" + tipo.getSufijo());
	}
	public static void in(Tipo tipo){
		imprime("\t\t  in" + tipo.getSufijo());
	}
	
//* Operadores de salto
	public static void jmp(String etiqueta){
		imprime("\t\t  jmp " + etiqueta);
	}
	public static void jumpIfZero(String etiqueta){
		imprime("\t\t  jz " + etiqueta);
	}
	public static void jumpIfNOTZero(String etiqueta){
		imprime("\t\t  jnz " + etiqueta);
	}
	
//* Operadores Funciones
	public static void call(String identificador){
		imprime("\t\t  call \t" + identificador);
	}
	public static void enter(int tamanoReservaVars){
		imprime("\t\t  enter \t" + tamanoReservaVars);
	}
	public static void ret(int tamValorRetorno, int tamLocalVars, int tamParametros){
		imprime("\t\t  ret " + tamValorRetorno + ", " + tamLocalVars + ", " + tamParametros); //ret	0, 0, 0
	}
	public static void id(String nombre) {
		imprime("\n\t  " + nombre + ":");
	}
	
//* Otros operadores
	public static void halt(){
		imprime("\t\t  halt");
	}
	 
	
	/** Convierte el tipo de la exprDCHA al tipo de la exprIZDA	 */
	public static void convertir(Expresion exprIzqda, Expresion exprDcha) {
		// Necesitamos este metodo porque puede que las expresiones sean de tipos distintos

		if (exprIzqda.getTipo() instanceof TipoCaracter && exprDcha.getTipo() instanceof TipoEntero){
			imprime("\t\t  b2i");//imprime("\t\t  I2B");
		} else if (exprIzqda.getTipo() instanceof TipoEntero && exprDcha.getTipo() instanceof TipoCaracter){
			imprime("\t\t  i2b");//imprime("\t\t  B2I");
		} else if (exprIzqda.getTipo() instanceof TipoEntero && exprDcha.getTipo() instanceof TipoDouble){
			imprime("\t\t  i2f");// F2I
		} else if (exprIzqda.getTipo() instanceof TipoDouble && exprDcha.getTipo() instanceof TipoEntero){
			imprime("\t\t  f2i");// I2F
		} else if (exprIzqda.getTipo() instanceof TipoDouble && exprDcha.getTipo() instanceof TipoCaracter){
			imprime("\t\t  f2i");
			imprime("\t\t  i2b");
		} else if (exprIzqda.getTipo() instanceof TipoCaracter && exprDcha.getTipo() instanceof TipoDouble){
			imprime("\t\t  b2i");
			imprime("\t\t  i2f");
		}
	}
	
	/** Convierte el tipo de la exprDCHA al tipo de la exprIZDA	 */
	public static void convertir(Expresion exprIzqda, Tipo tipoBase) {
		// Necesitamos este metodo porque puede que las expresiones sean de tipos distintos
		
		if (exprIzqda.getTipo() instanceof TipoCaracter && tipoBase instanceof TipoEntero){
			imprime("\t\t  b2i");//imprime("\t\t  I2B");
		} else if (exprIzqda.getTipo() instanceof TipoEntero && tipoBase instanceof TipoCaracter){
			imprime("\t\t  i2b");//imprime("\t\t  B2I");
		} else if (exprIzqda.getTipo() instanceof TipoEntero && tipoBase instanceof TipoDouble){
			imprime("\t\t  i2f");// F2I
		} else if (exprIzqda.getTipo() instanceof TipoDouble && tipoBase instanceof TipoEntero){
			imprime("\t\t  f2i");// I2F
		} else if (exprIzqda.getTipo() instanceof TipoDouble && tipoBase instanceof TipoCaracter){
			imprime("\t\t  f2i");
			imprime("\t\t  i2b");
		} else if (exprIzqda.getTipo() instanceof TipoCaracter && tipoBase instanceof TipoDouble){
			imprime("\t\t  b2i");
			imprime("\t\t  i2f");
		}	
	}
	
	
/**	***************************************************************************/
	private static void imprime(String instruccion) {
		out.println(instruccion);
		out.flush();
	}

	public static void comentario(String comment) {
		imprime("\t'" + comment);
	}
	
	public static int dameEtiqueta() {
		int valorARetornar = contadorEtiquetas;
		contadorEtiquetas++;
		return valorARetornar;
	}
	
	

}
