package errors;

import java.io.PrintStream;
import java.util.ArrayList;

import ast.tipo.TipoError;

public class ManejadorErrores {

private static ManejadorErrores instance = null;
	
	ArrayList<TipoError> errores = new ArrayList<TipoError>();
	
	public ManejadorErrores() {	}
	
	public ManejadorErrores(TipoError error) {
		ManejadorErrores.getInstance().addError(error);
	}

	public static ManejadorErrores getInstance() {
	   if(instance == null) {
		   instance = new ManejadorErrores();
	   }
	   return instance;
	}
	
	public void addError(TipoError error) {
		errores.add(error);
	}
	
	public boolean huboErrores() {
		return (errores.size() == 0 ? false : true);
	}
	
	public void mostrarErrores(PrintStream out) {
		int i = 1;
		for (TipoError e : errores){
			out.println("   - Error " + i + " " +  e.getMensaje());
			i++;
		}
		out.close();
	}
	
}