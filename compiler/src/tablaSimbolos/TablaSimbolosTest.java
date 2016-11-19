package tablaSimbolos;

import java.util.Map;

import ast.sentencia.definicion.Definicion;
import ast.sentencia.definicion.DefinicionVariable;

public class TablaSimbolosTest {
		
	public void testInsertar() {
		TablaSimbolos ts = new TablaSimbolos();
		
		DefinicionVariable simbolo = new DefinicionVariable(0, 0, "a", null);
			assert(ts.insertar(simbolo));	
			assert(simbolo.getAmbito()==0);
			assert(!ts.insertar(simbolo));
		
		ts.set();
				
		DefinicionVariable simbolo2 = new DefinicionVariable(0, 0, "a", null);
			assert(ts.insertar(simbolo2));
			assert(simbolo2.getAmbito()==1);
			assert(!ts.insertar(simbolo2));
						
		ts.reset();
		
			assert(!ts.insertar(simbolo));
	
	}
	
	public void testBuscar() {
		TablaSimbolos ts = new TablaSimbolos();
				
		DefinicionVariable simbolo = new DefinicionVariable(0, 0, "a", null);
			assert(ts.insertar(simbolo));
			assert(ts.buscar("a")!=null);
			assert(ts.buscar("b")==null);

		ts.set();

		 DefinicionVariable simbolo2 = new DefinicionVariable(0, 0, "b", null);
			assert(ts.insertar(simbolo2));

			assert(ts.buscar("b")!=null);
			assert(ts.buscar("a")!=null);
			assert(ts.buscar("c")==null);
		
		ts.reset();

			assert(ts.buscar("a")!=null);
			assert(ts.buscar("b")==null);
	}

	public void testBuscarAmbitoActual() {
		TablaSimbolos ts = new TablaSimbolos();
		
		DefinicionVariable simbolo = new DefinicionVariable(0, 0, "a", null);
			assert(ts.insertar(simbolo));
			assert(ts.buscarAmbitoActual("a")!=null);
			assert(ts.buscarAmbitoActual("b")==null);
			
		ts.set();
		
		DefinicionVariable simbolo2 = new DefinicionVariable(0, 0, "b", null);
			assert(ts.insertar(simbolo2));
			assert(ts.buscarAmbitoActual("b")!=null);
			assert(ts.buscarAmbitoActual("a")==null);
			assert(ts.buscarAmbitoActual("c")==null);
		
		ts.reset();
			
			assert(ts.buscarAmbitoActual("a")!=null);
			assert(ts.buscarAmbitoActual("b")==null);		
	}
	
	public static void main(String[] args) {
		TablaSimbolosTest test = new TablaSimbolosTest();
		test.testInsertar();
		test.testBuscar();
		test.testBuscarAmbitoActual();
		
		System.out.println("\n\tSi sale esto es que la tabla de simbolos funciona correctamente.");
	}
	
	public void prettyPrint(TablaSimbolos ts){
		int nivel = 0;
		for (Map<String,Definicion> m : ts.tabla){
			System.out.println("nivel: " + nivel);
			System.out.println(new PrettyPrintingMap<String, Definicion>(m));
			System.out.println("-------------------------------------------------------------");
			nivel++;
		}
	}
	
}
