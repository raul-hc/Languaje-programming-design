package GeneracionCodigo;

import ast.NodoAST;
import ast.Programa;
import ast.expresion.Expresion;
import ast.sentencia.Asignacion;
import ast.sentencia.Escritura;
import ast.sentencia.IfElse;
import ast.sentencia.Invocacion;
import ast.sentencia.Lectura;
import ast.sentencia.Sentencia;
import ast.sentencia.While;
import ast.sentencia.definicion.Definicion;
import ast.sentencia.definicion.DefinicionFuncion;
import ast.sentencia.definicion.DefinicionVariable;
import ast.tipo.Tipo;
import ast.tipo.TipoEntero;
import ast.tipo.TipoFuncion;
import ast.tipo.TipoVoid;
import visitor.DefaultVisitor;

/**
 * Visitor VisitorGC_Ejecutar 
 * Genera el codigo necesario para ejecutar una SENTENCIA.
 */
public class VisitorGC_Ejecutar extends DefaultVisitor {

	public VisitorGC_Direccion visitorGCDireccion;
	public VisitorGC_Valor visitorGCValor;
	
	public VisitorGC_Ejecutar(VisitorGC_Direccion visitorGCDireccion2, VisitorGC_Valor visitorGCValor2) {
		this.visitorGCDireccion = visitorGCDireccion2;
		this.visitorGCValor = visitorGCValor2;
	}
	
	public Object visit(Programa programa, Object param) {
				
		GeneradorCodigo.call("main");
		GeneradorCodigo.halt();
		
		if (programa.getDefiniciones() != null){
			for (Definicion child : programa.getDefiniciones()){
				if (child instanceof DefinicionFuncion){
					child.accept(this, child);
				}
			}
		}
		
		return null;
	}
	
	public Object visit(Escritura node, Object param) {
						
		if (node.getExpresiones() != null){
			for (Expresion expr : node.getExpresiones()){
				expr.accept(this.visitorGCValor, param);
				GeneradorCodigo.out(expr.getTipo());
			}
		}
		return null;
	}
	
	public Object visit(Lectura node, Object param) {
		
		if (node.getExpresiones() != null){
			for (Expresion expr : node.getExpresiones()){
				expr.accept(this.visitorGCDireccion, param);
				GeneradorCodigo.in(expr.getTipo());
				GeneradorCodigo.store(expr.getTipo());
			}
		}
		return null;
	}
	
	public Object visit(Asignacion node, Object param) {
		
		GeneradorCodigo.convertir(node.getExprIzqda(), node.getExprDcha());
		
		if (node.getExprIzqda() != null){
			node.getExprIzqda().accept(this.visitorGCDireccion, param);
		}
		
		if (node.getExprDcha() != null){
			node.getExprDcha().accept(this.visitorGCValor, param);
		}
		
		GeneradorCodigo.store(node.getExprIzqda().getTipo());
		
		return null;
	}
	
	public Object visit(DefinicionFuncion node, Object param) {
		
		GeneradorCodigo.id(node.getNombre().toLowerCase());	
		
			GeneradorCodigo.comentario("*Parametros:");
			node.getTipo().accept(this, param);

			GeneradorCodigo.comentario("*Variables Locales:");
			for (DefinicionVariable defVariableLocal : node.getVariables()){
				GeneradorCodigo.comentario("\t" + defVariableLocal.toString());
			}
			
		GeneradorCodigo.enter( node.getOffsetVariablesLocales()); /** El offset de la ultima variable local nos dice exactamente lo que ocuparon las variables */
		
		if (node.getSentencias() != null){
			for (Sentencia child : node.getSentencias()){
				GeneradorCodigo.comentario("");
				GeneradorCodigo.comentario("# " + child.toString());
				child.accept(this, param);				 // visit(Sentencia)
			}
		}
	
		GeneradorCodigo.pusha("bp");
		GeneradorCodigo.pushi(node.getTipo().getTipoRetorno().getNumeroBytes());
		GeneradorCodigo.addi();
		GeneradorCodigo.load(node.getTipo().getTipoRetorno());
		
		Tipo tipoRetorno = (node.getTipo()).getTipoRetorno();
		if ( !(tipoRetorno instanceof TipoVoid) ){ /** Si se pusiera el RET en un procedimiento no pasa nada */	
			//* IMP: PARAMETROS Y TIPO DE RETORNO SON SIEMPRE DE TIPO SIMPLE -> me basta con llamar a getNumeroBytes()
			int tamValorRetorno = node.getTipo().getTipoRetorno().getNumeroBytes();
			int tamLocalVars = node.getOffsetVariablesLocales();
			int tamParametros = 0;

			TipoFuncion tipoFuncion = (TipoFuncion) node.getTipo();
			for (DefinicionVariable defVariable : tipoFuncion.getParametros()){ 
				tamParametros += defVariable.getTipo().getNumeroBytes();
			}
			
			GeneradorCodigo.ret(tamValorRetorno, tamLocalVars, tamParametros); 
		}
	
		return null;
	}
	
	public Object visit(TipoFuncion node, Object param) {
		for (DefinicionVariable parametro : node.getParametros()){
			GeneradorCodigo.comentario("\t" + parametro.toString());
		}
		
		return null;
	}
	
	public Object visit(While node, Object param) {
		
		String etiquetaInicio = "etiqueta" + GeneradorCodigo.dameEtiqueta();
		String etiquetaFinal = "etiqueta" + GeneradorCodigo.dameEtiqueta();
		GeneradorCodigo.id(etiquetaInicio);
		
		node.getCondicion().accept(visitorGCValor, param);
		GeneradorCodigo.convertir(node.getCondicion(), new TipoEntero());
		GeneradorCodigo.jumpIfZero(etiquetaFinal);
		
		for (Sentencia s : node.getSentencias() ){
			s.accept(this, param);
		}
		
		GeneradorCodigo.jmp(etiquetaInicio);
		GeneradorCodigo.id(etiquetaFinal);
		
		return null;
	}
	
	public Object visit(IfElse node, Object param) {
		
		String etiquetaELSE = "etiqueta" + GeneradorCodigo.dameEtiqueta();
		String etiquetaFinal = "etiqueta" + GeneradorCodigo.dameEtiqueta();
		
		node.getCondicion().accept(visitorGCValor, param);
		GeneradorCodigo.convertir(node.getCondicion(), new TipoEntero());
		GeneradorCodigo.jumpIfZero(etiquetaELSE);
		
		for (Sentencia s : node.getCuerpoIF()){
			s.accept(this, param);
		}
		
		GeneradorCodigo.jmp(etiquetaFinal);
		GeneradorCodigo.id(etiquetaELSE);
		
		for (Sentencia s : node.getCuerpoELSE()){
			s.accept(this, param);
		}
		
		GeneradorCodigo.id(etiquetaFinal);
		
		return null;
	}
	
	public Object visit(Invocacion node, Object param) {
		
		node.accept(visitorGCValor, param);
		
		if (!(node.getFuncion().getDefinicion().getTipo().getTipoRetorno() instanceof TipoVoid)){ // Procedimiento sin retorno
			
			node.getFuncion().accept(this, param);			
			GeneradorCodigo.pop(node.getFuncion().getDefinicion().getTipo().getTipoRetorno());
		}
		
		return null;
	}
	
}