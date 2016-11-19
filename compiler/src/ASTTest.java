/**
 * Peque�o programa de prueba.<br/>
 * Dise�o de Lenguajes de Programaci�n<br/>
 * Escuela de Ingenier�a Inform�tica<br/>
 * Universidad de Oviedo<br/>
 * 
 * @author Francisco Ortin
 */
public class ASTTest {

	/**
	 * Prueba de creaci�n de un AST.
	 * El programa de entrada es: 
	 * input x,y;
	 * z = (-x + 5) / y * (2 � x);
	 * print z;
	 */
/*
	private static Programa crearArbol() {
		Sentencia sentencia1, sentencia2, sentencia3;
		// * Primera L�nea
		List<Expresion> expresiones = new ArrayList<Expresion>();
		expresiones.add(new Variable(1, 7, "x"));
		expresiones.add(new Variable(1, 9, "y"));
		
		sentencia1 = new Lectura(1, 1, expresiones);
		
		// * Segunda L�nea
		sentencia2 = new Asignacion(2, 3, 
						new Variable(2, 1, "z"),						
						new Aritmetica(2, 18,
								new Aritmetica(2, 18,
										new Aritmetica(2, 18,
												new MenosUnario(),
												"+",
												new LiteralEntero(2, 24, 5)
										),
										"/",
										new Variable(2, 29, "y")
								),
								"*",
								new Aritmetica(2, 20,
										new LiteralEntero(2, 21, 2),
										"-",
										new Variable(2, 25, "x")
								)
						)
					);

		// * Tercera L�nea
		expresiones = new ArrayList<Expresion>();
		expresiones.add(new Variable(3, 7, "z"));
		
		sentencia3 = new Escritura(3, 1, expresiones);
		
		// * Construimos y devolvemos el �rbol
		List<Sentencia> sentencias = new ArrayList<Sentencia>();
			sentencias.add(sentencia1);
			sentencias.add(sentencia2);
			sentencias.add(sentencia3);
		return new Programa(1, 1, sentencias);
	}

	public static void main(String[] args) {
		IntrospectorModel modelo = new IntrospectorModel("Programa", crearArbol());
		new IntrospectorTree("Introspector", modelo);
	}
*/
}
