package visitor;

import java.util.List;

import ast.NodoAST;
import ast.Programa;
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
import ast.sentencia.While;
import ast.sentencia.definicion.DefinicionCampo;
import ast.sentencia.definicion.DefinicionFuncion;
import ast.sentencia.definicion.DefinicionVariable;
import ast.tipo.TipoArray;
import ast.tipo.TipoCaracter;
import ast.tipo.TipoDouble;
import ast.tipo.TipoEntero;
import ast.tipo.TipoError;
import ast.tipo.TipoFuncion;
import ast.tipo.TipoStruct;
import ast.tipo.TipoVoid;

/**	DefaultVisitor: implementación por defecto para cada nodo 
	Lo unico que hace es visitar los nodos hijos. */
public class DefaultVisitor implements Visitor {

		/** ## Método auxiliar ## */
		protected void visitChildren(List<? extends NodoAST> children, Object param) {
			if (children != null){
				for (NodoAST child : children){
					child.accept(this, param);
				}
			}
		}
	
	public Object visit(Programa node, Object param) {
		visitChildren(node.getDefiniciones(), param);
		return null;
	}
	
/** Tipos*/
	// TipoEntero {}
	public Object visit(TipoEntero node, Object param) {
		return null;
	}

	// TipoDouble {}
	public Object visit(TipoDouble node, Object param) {
		return null;
	}

	// TipoCaracter {}
	public Object visit(TipoCaracter node, Object param) {
		return null;
	}

	// TipoVoid {}
	public Object visit(TipoVoid tipoVoid, Object param) {
		return null;
	}

	// TipoError {NodoAST nodo, String mensaje}
	public Object visit(TipoError tipoError, Object param) {
		return null;
	}
	
// TipoArray {Tipo tipo, Integer tamano}
	public Object visit(TipoArray node, Object param) {	
		if (node.getTipoRetorno() != null)
			node.getTipoRetorno().accept(this, param);
		return null;
	}

// TipoStruct {List<DefinicionCampo> campos}
	public Object visit(TipoStruct node, Object param) {
		visitChildren(node.getCampos(), param);
		return null;
	}
	
// TipoFuncion { Tipo tipoRetorno; List<DefinicionVariable> parametros;}
	public Object visit(TipoFuncion node, Object param) {
		visitChildren(node.getParametros(), param);
		if (node.getTipoRetorno() != null)  
			node.getTipoRetorno().accept(this, param);
		return null;
	}

/** Sentencias */
	// Asignacion {Expresion exprIzqda, Expresion exprDcha}
	public Object visit(Asignacion node, Object param) {
		if (node.getExprIzqda() != null)
			node.getExprIzqda().accept(this, param);
		if (node.getExprDcha() != null)
			node.getExprDcha().accept(this, param);
		return null;
	}

	// Escritura {List<Expresion> expresiones}
	public Object visit(Escritura node, Object param) {
		if (node.getExpresiones() != null){
			for (Expresion expr : node.getExpresiones()){
				expr.accept(this, param);
			}
		}
		return null;
	}

	// Lectura {List<Expresion> expresiones}
	public Object visit(Lectura node, Object param) {
		if (node.getExpresiones() != null){
			for (Expresion expr : node.getExpresiones()){
				expr.accept(this, param);
			}
		}
		return null;
	}
	
	// Return {Expresion valorRetorno}
	public Object visit(Return node, Object param) {
		if (node.getValorRetorno() != null)
			node.getValorRetorno().accept(this, param);
		return null;
	}

	// IfElse {Expresion condicion; List<Sentencia> cuerpoIF; List<Sentencia> cuerpoELSE}
	public Object visit(IfElse node, Object param) {
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getCuerpoIF(), param);
		visitChildren(node.getCuerpoELSE(), param);
		return null;
	}

	// While {Expresion condicion, List<Sentencia> sentencias}
	public Object visit(While node, Object param) {
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getSentencias(), param);
		return null;
	}
	
	// Invocacion {Variable nombre; List<Expresion> parametros;}
	public Object visit(Invocacion node, Object param) {
		
		visit(node.getFuncion(), param); //Ahora cuando visito una invocacion tengo que visitar su variable, ya que antes lo tenia como  Invocacion {String nombre; List<Expresion> parametros;}
		
		visitChildren(node.getParametros(), param);
		return null;
	}
	
/** Sentencias: DEFINICIONES */
	// DefinicionVariable {String nombre; Tipo tipo;}
	public Object visit(DefinicionVariable node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	// DefinicionCampo {String nombre; Tipo tipo;}
	public Object visit(DefinicionCampo node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	/* DefinicionFuncion {String nombre; List<DefinicionVariable> variables; 
						  List<Sentencia> sentencias;} */
	public Object visit(DefinicionFuncion node, Object param) {
		visitChildren(node.getVariables(), param);
		visitChildren(node.getSentencias(), param);
		node.getTipo().accept(this, param);
		return null;
	}
	
	
// Aritmetica {Expresion exprIzqda, String operador, Expresion exprDcha}
	public Object visit(Aritmetica node, Object param) {		
		if (node.getExprIzqda() != null)
			node.getExprIzqda().accept(this, param);
		if (node.getExprDcha() != null)
			node.getExprDcha().accept(this, param);
		return null;
	}

// Comparacion {Expresion exprIzqda, String operador, Expresion exprDcha}
	public Object visit(Comparacion node, Object param) {
		if (node.getExprIzqda() != null)
			node.getExprIzqda().accept(this, param);
		if (node.getExprDcha() != null)
			node.getExprDcha().accept(this, param);
		return null;
	}

// ExpresionLogica {Expresion exprIzqda, String operador, Expresion exprDcha}
	public Object visit(ExpresionLogica node, Object param) {
		if (node.getExprIzqda() != null)
			node.getExprIzqda().accept(this, param);
		if (node.getExprDcha() != null)
			node.getExprDcha().accept(this, param);
		return null;
	}

// LiteralReal {int valor}
	public Object visit(LiteralEntero node, Object param) {
		return null;
	}

	// LiteralReal {double valor}
	public Object visit(LiteralReal node, Object param) {
		return null;
	}

	// Char {String caracter}
	public Object visit(LiteralChar node, Object param) {
		return null;
	}

	// Variable {String nombre}
	public Object visit(Variable node, Object param) {
		return null;
	}

// MenosUnario {Expresion expresion}
	public Object visit(MenosUnario node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	// NegacionLogica {Expresion expresion}
	public Object visit(NegacionLogica node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

// Cast {Tipo tipo, Expresion parametroCast}
	public Object visit(Cast node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		if (node.getParametroCast() != null)
			node.getParametroCast().accept(this, param);
		return null;
	}

/** EXPRESIONES */
	// AccesoArray {Expresion nombreArray, Expresion indice}
	public Object visit(AccesoArray node, Object param) {
		if (node.getNombreArray() != null)
			node.getNombreArray().accept(this, param);
		if (node.getIndice() != null)
			node.getIndice().accept(this, param);
		return null;
	}
	
	// AccesoCampo {Expresion objeto, Variable campo}
	public Object visit(AccesoCampo node, Object param) {
		if (node.getObjetoEstructura() != null)
			node.getObjetoEstructura().accept(this, param);
		return null;
	}

	

}
