package semantico;

import java.util.ArrayList;
import java.util.List;

import ast.expresion.AccesoArray;
import ast.expresion.AccesoCampo;
import ast.expresion.Aritmetica;
import ast.expresion.Cast;
import ast.expresion.Comparacion;
import ast.expresion.Expresion;
import ast.expresion.ExpresionLogica;
import ast.expresion.LiteralChar;
import ast.expresion.LiteralEntero;
import ast.expresion.LiteralReal;
import ast.expresion.MenosUnario;
import ast.expresion.NegacionLogica;
import ast.expresion.Variable;
import ast.sentencia.Asignacion;
import ast.sentencia.Escritura;
import ast.sentencia.IfElse;
import ast.sentencia.Invocacion;
import ast.sentencia.Lectura;
import ast.sentencia.Return;
import ast.sentencia.Sentencia;
import ast.sentencia.While;
import ast.sentencia.definicion.DefinicionFuncion;
import ast.sentencia.definicion.DefinicionVariable;
import ast.tipo.Tipo;
import ast.tipo.TipoCaracter;
import ast.tipo.TipoDouble;
import ast.tipo.TipoEntero;
import ast.tipo.TipoError;
import ast.tipo.TipoFuncion;
import ast.tipo.TipoStruct;
import errors.ManejadorErrores;
import visitor.DefaultVisitor;

/**
 * Vamos a ampliar VisitorSemantico.java para recorrer el arbol comprobando que todo sea correcto:
 *   Sistema de tipos:
 * 		1.- Inferencia 	 de tipos: 	Rellenar con toda la informacion de tipos.
 * 		2.- Comprobación de tipos:	Detectar todos los errores no detectados antes.
 *  
 * Sistema de tipos de un compilador -> un monton de metodos que realizan comprobaciones
 * Detectar todos los errores no detectados antes:
 * 		-> A partir de esta fase ya damos por sentado que nuestro programa es válido. 
 */
public class VisitorSemantico extends DefaultVisitor {
	
// ##########################################################

	ManejadorErrores manejador;
	
	public VisitorSemantico() {
		this.manejador = ManejadorErrores.getInstance();
	}

	public Object visit(Lectura node, Object param) {

		if (node.getExpresiones() != null)
			for (Expresion child : node.getExpresiones()){
				child.accept(this, param);
				
				if (!child.getLValue()){
					TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node, "La expresion debe ser modificable (LVALUE)");
					ManejadorErrores.getInstance().addError(tipoError);
					child.setTipo(tipoError);
				}
			}
		
		return null;
	}
	
	public Object visit(Escritura node, Object param) {

		if (node.getExpresiones() != null)
			for (Expresion child : node.getExpresiones()) {
				child.accept(this, param);
			}
		
		return null;
	}

	public Object visit(Return node, Object param) {

		if (node.getValorRetorno() != null)
			node.getValorRetorno().accept(this, param);
		
		// Comprobar que el tipo de la expresion del return coincide con el tipo de retorno de la función 
		if (param != null){ // Desempaqueto, hago cast a Tipo y compruebo que los tipos sean iguales
			
			Tipo tipoExprReturn = node.getValorRetorno().getTipo();
			Tipo tipoRetornoFuncion = (Tipo)param;
			
			if ( !(tipoRetornoFuncion.getClass().equals(tipoExprReturn.getClass())) ){ // Si los tipos no son los mismos o promocionables
				TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node, "El tipo de la expresion del return no coincide con el tipo de retorno de la función");
				ManejadorErrores.getInstance().addError(tipoError);
				node.getValorRetorno().setTipo(tipoError);
			}
			
		}
		
		return null;
	}

	public Object visit(IfElse node, Object param) {

		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);

	  //* Tengo que comprobar que el tipo de la condicion es un entero o un caracter (puede promocionar a entero, comportandose como un booleano)
		if (!node.getCondicion().getTipo().esLogico()){
			TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node.getCondicion(), "Se esperaba un booleano en la condición del IFELSE");
			ManejadorErrores.getInstance().addError(tipoError);
			node.getCondicion().setTipo(tipoError);
		}
		
		if (node.getCuerpoIF() != null){
			for (ast.sentencia.Sentencia child : node.getCuerpoIF()){
				child.accept(this, param);
			}
		}

		if (node.getCuerpoELSE() != null){
			for (Sentencia child : node.getCuerpoELSE()){
				child.accept(this, param);
			}
		}
		
		return null;
	}

	public Object visit(While node, Object param) {

		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		
		if (!node.getCondicion().getTipo().esLogico()){
			TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node.getCondicion(), "Se esperaba un booleano en la condición del WHILE");
			ManejadorErrores.getInstance().addError(tipoError);
			node.getCondicion().setTipo(tipoError);
		}
		
		if (node.getSentencias() != null){
			for (Sentencia child : node.getSentencias()){
				child.accept(this, param);
			}
		}
		
		return null;
	}

	public Object visit(Variable node, Object param) {		
//		System.out.println("node: " + node + " def: " + node.getDefinicion());
		
	  //* Tenemos que inferir el tipo de la variable:
//		if (node.getDefinicion() != null){
//			System.out.println(node.toString() + "  :  " + node.getDefinicion().toString());
			node.setTipo(node.getDefinicion().getTipo()); 
			node.setLValue(true);
//		}
		
		return null;
	}

	public Object visit(Aritmetica node, Object param){
		
		if (node.getExprIzqda() != null) // Calculamos el tipo de la expr izda
			node.getExprIzqda().accept(this, param);
		if (node.getExprDcha() != null) // Calculamos el tipo de la expr izda
			node.getExprDcha().accept(this, param);
		
		node.setLValue(false);
		
		// Calculamos el tipo de la expr resultante de la operacion aritmetica 
		Tipo tipoResultado = node.getExprIzqda().getTipo().aritmetica(node.getExprDcha().getTipo());
		
		// compruebo e infiero el tipo resultante de la expr resultante de la operacion aritmetica
		if (tipoResultado != null){ // aritmetica() nos devuelve el tipo resultado de la operacion
			node.setTipo(tipoResultado); 
		} else {
			TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node, "Los tipos no son compatibles para la operación aritmetica '" + node.getOperador() + "'");
			ManejadorErrores.getInstance().addError(tipoError);			
			node.setTipo(tipoError);
		}
		
		return null;
	}

	public Object visit(LiteralEntero node, Object param) {
		// Le calculamos el tipo. También lo podriamos hacer en el constructor
		node.setTipo(new TipoEntero()); 
		node.setLValue(false);
		return null;
	}

	public Object visit(LiteralReal node, Object param) {
		// Le calculamos el tipo. También lo podriamos hacer en el constructor
		node.setTipo(new TipoDouble());
		node.setLValue(false);
		return null;
	}

	public Object visit(LiteralChar node, Object param) {
		// Le calculamos el tipo. También lo podriamos hacer en el constructor
		node.setTipo(new TipoCaracter());
		node.setLValue(false);
		return null;
	}

	public Object visit(MenosUnario node, Object param) {
		
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		node.setLValue(false);
		
		node.setTipo(node.getExpresion().getTipo().aritmetica());
		
		return null;
	}

	public Object visit(Asignacion node, Object param) {
	
		if (node.getExprIzqda() != null)
			node.getExprIzqda().accept(this, param);
		if (node.getExprDcha() != null)
			node.getExprDcha().accept(this, param);
		
		if (!node.getExprIzqda().getLValue()){
			TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node.getExprIzqda(), "Se requiere LValue (valor modificable)");
			ManejadorErrores.getInstance().addError(tipoError);			
			node.getExprIzqda().setTipo(tipoError);
		}
		
		// Comprobar si el tipo de la derecha se puede guardar en el tipo de la izda.
		Tipo tipoResultado = node.getExprIzqda().getTipo().asignacion(node.getExprDcha().getTipo());
		
		if (tipoResultado != null){ 
			node.getExprIzqda().setTipo(tipoResultado);
		} else {
			TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node, "Los tipos no son compatibles para la operación de asignación");
			ManejadorErrores.getInstance().addError(tipoError);
			node.getExprIzqda().setTipo(tipoError);
		}
	
			return null;
		}

	public Object visit(Comparacion node, Object param) {
		
		if (node.getExprIzqda() != null)
			node.getExprIzqda().accept(this, param);
		if (node.getExprDcha() != null)
			node.getExprDcha().accept(this, param);
		
		node.setLValue(false);
		
		// comparacion(tipoOtro) nos devuelve el tipo resultado de la comparacion => TipoEntero
		Tipo tipoResultado = node.getExprIzqda().getTipo().comparacion(node.getExprDcha().getTipo());
		
		if (tipoResultado != null){ 
			node.setTipo(tipoResultado); // tipoResultado sera de TipoEntero
		} else {
			TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node, "Los tipos no son compatibles para la operación de comparacion solicitada: " + node.getOperador());
			ManejadorErrores.getInstance().addError(tipoError);
			node.setTipo(tipoError);
		}
		
		return null;
	}

	public Object visit(ExpresionLogica node, Object param) {
		
		if (node.getExprIzqda() != null)
			node.getExprIzqda().accept(this, param);
		if (node.getExprDcha() != null)
			node.getExprDcha().accept(this, param);
		
		node.setLValue(false);
		
		/*  logica(tipoOtro) nos devuelve el tipo resultado de la comparacion logica => 
			=> TipoEntero en los casos en los que se puede hacer dicha comparacion 			*/
		Tipo tipoResultado = node.getExprIzqda().getTipo().logica(node.getExprDcha().getTipo());
		
		if (tipoResultado != null){ 
			node.setTipo(tipoResultado);
		} else {
			TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node, "Los tipos no son compatibles para la operación de logica solicitada: " + node.getOperador());
			ManejadorErrores.getInstance().addError(tipoError);
			node.setTipo(tipoError);
		}
		
		return null;
	}
	
	// NegacionLogica {Expresion expresion}
	public Object visit(NegacionLogica node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		node.setLValue(false);
		
		
		Tipo tipoResultado = node.getExpresion().getTipo().logica();
		
		if (tipoResultado != null){ 
			node.setTipo(tipoResultado);
		} else {
			TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node, "No se puede llevar a cabo la operacion lógica solicitada");
			ManejadorErrores.getInstance().addError(tipoError);
			node.setTipo(tipoError);
		}
		
		
		node.setTipo(node.getExpresion().getTipo().logica());
		
		return null;
	}

	public Object visit(AccesoCampo node, Object param) {
			
		// Inferir su tipo. Si su tipo es de tipoStruct ya esta
		
		if (node.getObjetoEstructura() != null)
			node.getObjetoEstructura().accept(this, param);
		
		node.setLValue(true);
		
		/* Una vez que se que es de TipoStruct tenemos que comprobar si existe el campo al que se hace referencia */
		if (node.getObjetoEstructura().getTipo() instanceof TipoStruct){
			Tipo tipoResultado = node.getObjetoEstructura().getTipo().punto(node.getNombreCampo());
			
			if (tipoResultado != null){ 
				node.setTipo(tipoResultado);
			} else {
				TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node, "No se puede acceder al campo solicitado: " + node.getObjetoEstructura() + "." + node.getNombreCampo());
				ManejadorErrores.getInstance().addError(tipoError);
				node.setTipo(tipoError);
			}
		} else {
			TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node.getObjetoEstructura(), "La variable de la izquiera del . debe ser una estructura");
			ManejadorErrores.getInstance().addError(tipoError);
			node.setTipo(tipoError);
		}
		
		return null;
	}

	public Object visit(AccesoArray node, Object param) {
	
		if (node.getNombreArray() != null) // Será de tipo TipoArray
			node.getNombreArray().accept(this, param);
		
		if (node.getIndice() != null) // Será de tipo TipoEntero
			node.getIndice().accept(this, param);
		
		node.setLValue(true);
		
		Tipo tipoResultado = node.getNombreArray().getTipo().corchete(node.getIndice().getTipo());
		
		if (tipoResultado != null){ 
			node.setTipo(tipoResultado);
		} else {
			TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node, "No se puede acceder al array: " + node.getNombreArray() + "[" + node.getIndice() + "]");
			ManejadorErrores.getInstance().addError(tipoError);
			node.setTipo(tipoError);
		}
		
		node.setLValue(true);
		
//		if (!(node.getIndice().getTipo() instanceof TipoEntero)){
//			node.getNombreArray().setTipo(new TipoError(node.getNombreArray(), "El indice del array debe ser de tipo entero o caracter"));
//		}
//		if (!(node.getIndice().getTipo() instanceof TipoCaracter)){
//			node.getNombreArray().setTipo(new TipoError(node.getNombreArray(), "El indice del array debe ser de tipo entero o caracter"));
//		}
				
		return null;
	}

	public Object visit(Invocacion node, Object param) {
		
		/*  A la izqda de una invocacion tengo una variable. Tengo que visitarla porque esta variable 
			va a estar enlazada a un TipoFuncion que tiene un nombre y que tiene unos parametros  
		
			node.variable.accept(this, param);
			node.variable.getTipo.parentesis(List<Tipo> tiposParametros);
		*/
		
		// Variable
		if (node.getFuncion() != null)
			node.getFuncion().accept(this, param); // node.getTipo().accept(this, param);
		
		if (node.getParametros() != null)
			visitChildren(node.getParametros(), param);
	
		List<Tipo> tiposParametros = new ArrayList<Tipo>();
		for(Expresion e : node.getParametros()){
			tiposParametros.add(e.getTipo());
		}
		
		node.setLValue(false);
		
//		if (node.getTipo() != null){ // SI NO ES UN PROCEDIMIENTO
			
			Tipo tipoResultado = node.getFuncion().getTipo().parentesis(tiposParametros);
			
			if (tipoResultado != null){ 
				node.setTipo(tipoResultado);
			} else {
				TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node, "Tipo o orden de los parametros incorrecto: " + node.getFuncion().getNombre()); //node.getNombre()));
				ManejadorErrores.getInstance().addError(tipoError);
				node.setTipo(tipoError);
			}
//		} 
//		else {
//			node.setTipo(new TipoVoid());
//		}
			
		return null;
	}

	public Object visit(Cast node, Object param) {

		if (node.getTipoDestinoCasteo() != null)
			node.getTipoDestinoCasteo().accept(this, param);

		if (node.getParametroCast() != null)
			node.getParametroCast().accept(this, param);
		
		node.setLValue(false);
		
		Tipo tipoResultado = node.getParametroCast().getTipo().cast(node.getTipoDestinoCasteo());//Tipo tipoResultado = node.getTipo().cast(node.getTipoDestinoCasteo());
		
		if (tipoResultado != null){ 
			node.setTipo(tipoResultado);
		} else {
			TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node, "No se ha podido completar el casteo: " + node.getParametroCast() + " as " + node.getTipoDestinoCasteo()); 
			ManejadorErrores.getInstance().addError(tipoError);
			node.setTipo(tipoError);
		}
		
		if (!node.getParametroCast().getTipo().esBasico()){

			TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node, "La expresión del cast(parametro) debe ser de tipo simple"); 
			ManejadorErrores.getInstance().addError(tipoError);
			node.getParametroCast().setTipo(tipoError);		
		}
		
		if (!node.getTipoDestinoCasteo().esBasico()){
			TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node, "La expresión del cast(<...>) deben ser de tipo simple"); 
			ManejadorErrores.getInstance().addError(tipoError);
			node.setTipo(tipoError);
		}
		
		if ( node.getParametroCast().getTipo() == node.getTipoDestinoCasteo() ){
			TipoError tipoError = new TipoError(node.getLinea(), node.getColumna(), node, "El Cast debe realizarse entre distintos tipos"); 
			ManejadorErrores.getInstance().addError(tipoError);
			node.setTipo(tipoError);
		}
		
		return null;
	}
	
	public Object visit(DefinicionFuncion node, Object param) {

	  /* Tenemos que comprobar que el tipo de retorno de la funcion coincide con el tipo de la expresion del return: PARAMETRO HEREDADO */
		
		// Visitamos las variables definidas en la funcion (variables locales)
		if (node.getVariables() != null)
			for (DefinicionVariable child : node.getVariables())
				child.accept(this, param);
		
		// Visitamos las sentencias que hay en la función: LE PASAMOS EL TIPO DE RETORNO DE LA FUNCION COMO PARAMETRO.
		if (node.getSentencias() != null){
			
			Tipo tipoRetorno = (node.getTipo()).getTipoRetorno();
			
			// El tipo de retorno de la función tiene que ser basico
			for (Sentencia child : node.getSentencias()){
				child.accept(this, tipoRetorno); // Parametro heredado: se lo pasamos como parametro -> UTILIZADO EN  #visit(Return)#
			}
		}
		
		
		// El tipo de los parametros de una función debe ser simple.
		// El tipo de retorno de la función tiene que ser basico
		node.getTipo().accept(this, param); // -> visit(TipoFuncion)
		
		return null;
	}
	
	public Object visit(TipoFuncion node, Object param) {
		
		visitChildren(node.getParametros(), param);
		
		if (node.getTipoRetorno() != null)  
			node.getTipoRetorno().accept(this, param);
		
		// El tipo de los parametros de una función debe ser simple.
		for(DefinicionVariable dv : node.getParametros()){
			if (!dv.getTipo().esBasico()){
				dv.setTipo(new TipoError(node, "Los parámetros tienen que ser de tipo simple: " + dv));
			}
		}
		
		// El tipo de retorno de la función tiene que ser basico
		if ( !node.getTipoRetorno().esBasico() ){
			node.setTipo(new TipoError(node, "El tipo de retorno de la función debe ser básico: " + node.getTipoRetorno()));
		}
		
		return null;
	}
	
}