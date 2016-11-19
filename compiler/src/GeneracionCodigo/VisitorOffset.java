package GeneracionCodigo;

import java.util.Collections;
import java.util.List;

import ast.sentencia.definicion.DefinicionCampo;
import ast.sentencia.definicion.DefinicionVariable;
import ast.tipo.TipoArray;
import ast.tipo.TipoFuncion;
import ast.tipo.TipoStruct;
import visitor.DefaultVisitor;

/**
 * Visitor Offset 
 * 	 #Calcular desplazamientos:
 * 		1.- Calcular el desplazamiento de las variables globales
 * 		2.- Calcular el desplazamiento de las variables locales
 * 		3.- Calcular el desplazamiento de los parametros
 * 		4.- Calcular el desplazamiento de los campos
 *   #
 * 
 *	 DefinicionVariable 
 *		 Offset v.Locales (cuando el ambito es local) : BP - sumatorio(tamaño anteriores) - tamaño de la propia variable
 *		 Offset v.global: v.getTipo.getSize() + acumuladoVarGlobales
 *	 TipoStruct 
 *		 Offset campos: sumatorio(tamaño campos anteriores)
 *	 TipoFuncion 
 *		 Offset parametros: BP + 4 + sumatorio(tamaño parametros a su derecha)
 */
public class VisitorOffset extends DefaultVisitor {

	private int offsetLocales = 0;
	private int offsetGlobales = 0; // cada vez que defino una variable global incremento
	private int offsetParametros = 4;
		
//	TipoFuncion(List<DefinicionVariable> parametros, Tipo tipoRetorno
	public Object visit(TipoFuncion node, Object param) {
				
		offsetLocales = 0;
		offsetParametros = 4;
		
		if (node.getParametros() != null){
			List<DefinicionVariable> parametros = node.getParametros();
			Collections.reverse(parametros);
		    
			// NO LLAMAR AL ACCEPT DE LOS PARAMETROS
			for (DefinicionVariable child : parametros){ //Offset parametros: BP + 4 + sumatorio(tamaño parametros a su derecha)
				child.setOffset(offsetParametros);
				
				System.out.println("\t\t  -Parametro: " + child.toString() + " \t: " + offsetParametros);
				
				offsetParametros = offsetParametros + child.getTipo().getNumeroBytes();
			}
		}
		
		return null;
	}
	
	public Object visit(TipoStruct node, Object param) {
		
		//Offset de cada campo: sumatorio(tamaño campos anteriores)
		
		int offsetCampo = 0;
		for (DefinicionCampo campo : node.getCampos()){
			campo.setOffset(offsetCampo);
			offsetCampo += campo.getTipo().getNumeroBytes();
			
			System.out.println("\t\t\t-" + campo.toString() + " : " + campo.getOffset());
		}
		
		return null;
	}
	
	public Object visit(DefinicionVariable node, Object param) {
		
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
				
		if (node.getAmbito()>0){ /** Variable LOCAL */
			
			
			if (node.getTipo() instanceof TipoArray || node.getTipo() instanceof TipoStruct){//if (!node.getTipo().esBasico()){
				
				// Offset Struct local: sumatorio(tamaño campos anteriores)
				offsetLocales = offsetLocales - node.getTipo().calculaOffset();
				node.setOffset(offsetLocales);
				
			} else {
				
				// Offset v.Locales (cuando el ambito es local) : BP - sumatorio(tamaño anteriores) - tamaño de la propia variable
				offsetLocales = offsetLocales - node.getTipo().getNumeroBytes();
				node.setOffset(offsetLocales);
				
			}
									
			System.out.println("\t\t  -Local: " + node.toString() + " \t: " + offsetLocales);


		} else { 				/** Variable GLOBAL */
			// Offset v.global: v.getTipo.getSize() + acumuladoVarGlobales
			node.setOffset(offsetGlobales);
			
			System.out.println("\t\tGlobal: " + node.toString() + " \t: " + offsetGlobales);
			
			offsetGlobales += node.getTipo().calculaOffset(); //offsetGlobales += node.getTipo().getNumeroBytes();
		}
				
		return null;
	}
	
}