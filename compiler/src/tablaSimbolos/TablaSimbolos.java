package tablaSimbolos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ast.sentencia.definicion.Definicion;

public class TablaSimbolos {
	
	public int ambito = 0;
	public List<Map<String,Definicion>> tabla = new ArrayList<Map<String,Definicion>>();
	
	public TablaSimbolos()  {
		Map<String,Definicion> m = new HashMap<String, Definicion>();
		tabla.add(m);
	}

	public void set() { // Crea un nuevo ambito en la lista de ambitos
		Map<String,Definicion> m = new HashMap<String, Definicion>();
		tabla.add(m);
		ambito++;
	}
	
	public void reset() { // elimina el ultimo ambito
		tabla.remove(ambito);
		ambito--;
	}
	
	public boolean insertar(Definicion simbolo) { // insertamos en el ultimo ambito
		Map<String, Definicion> m = tabla.get(ambito);
				
		if( buscarAmbitoActual(simbolo.getNombre()) == null) { // si el simbolo ya esta insertado
			m.put(simbolo.getNombre(), simbolo);
			simbolo.setAmbito(ambito);

			return true;
		}
		return false;
		
	}
	
	public Definicion buscar(String id) {
		
		for (Map<String, Definicion> m : tabla){
			if (m.get(id) != null) 
				return m.get(id);
		}
		
		return null;
	}

	public Definicion buscarAmbitoActual(String id) {
		Map<String, Definicion> m = tabla.get(ambito);
		return m.get(id);
	}
}
