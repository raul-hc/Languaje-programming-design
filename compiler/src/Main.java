import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import GeneracionCodigo.GeneradorCodigo;
import GeneracionCodigo.VisitorOffset;
import errors.ManejadorErrores;
import lexico.Lexico;
import semantico.VisitorIdentificacion;
import semantico.VisitorSemantico;
import sintactico.Parser;

/**
 * Prueba del analizador l�xico.<br/>
 * Dise�o de Lenguajes de Programaci�n.<br/>
 * Escuela de Ingenier�a Inform�tica.<br/>
 * Universidad de Oviedo <br/>
 * 
 * @author Francisco Ortin
 */
 
public class Main {
	
	 // * Gestion de errores
	static ManejadorErrores manejadorErrores = ManejadorErrores.getInstance();
	static boolean errores = false;
	
	@SuppressWarnings("unused")
	public static void main(String args[]) throws IOException {
	    if (args.length<1) {
	        System.err.println("Necesito el archivo de entrada.");
	        return;
	    }
	        
		FileReader fr=null;
		try {
			fr=new FileReader(args[0]);
			System.out.println("Fichero analizado: " + args[0] + "\n");
		} catch(IOException io) {
			System.err.println(io.getMessage());
			System.err.println("El archivo "+args[0]+" no se ha podido abrir.");
			return;
		}
				
// 1. Fases de Análisis Léxico y Sintáctico
		// * Creamos léxico y sintáctico
		Lexico lexico = new Lexico(fr);
		Parser parser = new Parser(lexico);
		// * "Parseamos"
		parser.run();
		
//		IntrospectorModel modelo=new IntrospectorModel("Programa",parser.ast);
//		new IntrospectorTree("Introspector", modelo);
		
// 2. Fase de Análisis Semántico
	System.out.println("  #Fase Identificación:");
		VisitorIdentificacion identificacion = new VisitorIdentificacion();
			parser.ast.accept(identificacion, null);
		mostrarErrores();
		

		
	System.out.println("  #Fase Inferencia:");
		VisitorSemantico inferencia = new VisitorSemantico(); 	//VisitorSemantico_LValue inferencia_lvalues = new VisitorSemantico_LValue();
			parser.ast.accept(inferencia, null);				//parser.ast.accept(inferencia_lvalues, null);
		mostrarErrores();
		
// 3. Fase de Generación de Código
	System.out.println("  #Fase Generación de código:");
	
		if (!errores) { //* Si no hubo errores semanticos generamos el fichero con el codigo a bajo nivel
			
			String ficheroSalida = "salidaGC.txt";
	
			/** Gestión de memoria -> Calculo de los desplazamientos/offsets de las variables */
			VisitorOffset offset = new VisitorOffset(); 	
				parser.ast.accept(offset, null);				
			
			/** Seleccion de las instrucciones */
			GeneradorCodigo gc = new GeneradorCodigo(parser.ast, ficheroSalida);		
			
		}
	}
	
	public static void mostrarErrores(){
		if (manejadorErrores.huboErrores()){ 
			manejadorErrores.mostrarErrores(System.out);
			PrintStream stdout = System.out;
			System.setOut(stdout);   
			errores = true;
		} else {
			System.out.println("\tNo se detectaron errores en esta fase\n");
		}
	}
}