package visitor;

import ast.Programa;
import ast.expresion.AccesoArray;
import ast.expresion.AccesoCampo;
import ast.expresion.Aritmetica;
import ast.expresion.Cast;
import ast.expresion.Comparacion;
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

public interface Visitor {
	
	public Object visit(Programa node, Object param);
	
// Tipos
	public Object visit(TipoEntero node, 		Object param);
	public Object visit(TipoDouble node, 		Object param);
	public Object visit(TipoCaracter node, 		Object param);
	
	public Object visit(TipoVoid tipoVoid, 		Object param);
	public Object visit(TipoError tipoError, 	Object param);
	
	public Object visit(TipoArray node, 		Object param);
	public Object visit(TipoStruct node, 		Object param);
	
	public Object visit(TipoFuncion node, Object param);
	
	
// Sentencias
	public Object visit(Asignacion node, 	Object param);
	public Object visit(Escritura node, 	Object param);
	public Object visit(Lectura node, 		Object param);
	public Object visit(Return node, 		Object param);
	public Object visit(IfElse node, 		Object param);
	public Object visit(While node, 		Object param);
	public Object visit(Invocacion node, 	Object param); // invocacion como expresion y como sentencia van juntos en Invocacion

	// Sentencias: Definiciones
		public Object visit(DefinicionVariable node, 	Object param);
		public Object visit(DefinicionCampo node, 	Object param);
		public Object visit(DefinicionFuncion node, Object param);

	
// Expresiones		
	
	public Object visit(Aritmetica node, 		Object param);
	public Object visit(Comparacion node, 		Object param);
	public Object visit(ExpresionLogica node, 	Object param);
	
	public Object visit(Variable node, 			Object param);
	public Object visit(LiteralEntero node, 	Object param);
	public Object visit(LiteralReal node, 		Object param);
	public Object visit(LiteralChar node, 		Object param);
	
	public Object visit(MenosUnario node, 		Object param);
	public Object visit(NegacionLogica node, 	Object param);

	public Object visit(Cast node, 				Object param);

	public Object visit(AccesoArray node, 		Object param);
	public Object visit(AccesoCampo node, 		Object param);
	
	// invocacion como expresion y como sentencia van juntos en Invocacion
	
	/*	No necesito este visitor
	ExpresionBinaria = Aritmetica + ExpresionLogica + Comparacion
	public Object visit(ExpresionBinaria node, 	Object param);
	*/

}
