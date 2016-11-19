%{
// * Declaraciones de código Java
// * Se sitúan al comienzo del archivo generado
// * El package lo acade yacc si utilizamos la opción -Jpackage

import lexico.Lexico;

import java.util.List;
import java.util.ArrayList;

import ast.NodoAST;
import ast.Programa;
import ast.expresion.*;
import ast.sentencia.*;
import ast.sentencia.definicion.*;
import ast.tipo.*;
import errors.ManejadorErrores;
import java.util.Collection;

%}

// * Declaraciones Yacc

%token CTE_ENTERA
%token CTE_REAL
%token CTE_CARACTER
%token STRUCT
%token RETURN
%token WHILE
%token IF
%token ELSE
%token Y
%token O
%token MAYORIGUAL
%token MENORIGUAL
%token DISTINTO
%token IGUALDAD
%token CAST
%token MAIN
%token AS

/*Cuanto mas abajo esten mas prioridad tienen*/
%right '='
//%left '>>' '<<'
%left '+' '-'
%left '*' '/' '%'
%left '>' MAYORIGUAL '<' MENORIGUAL DISTINTO IGUALDAD
%left Y O '!'
%right MENOSUNARIO			/* hay que poner %prec en expresion */
%left '[' ']' '.'
%left CAST
%nonassoc '(' ')'



%%
// * Gramática y acciones Yacc
// Recursividad Izquierda // https://class.coursera.org/compilers/lecture


programa: listaDefiniciones main  { 
DefinicionFuncion main = (DefinicionFuncion)$2;
List<Definicion> listaDefiniciones = ((List<Definicion>)$1);
listaDefiniciones.add(main);
								ast = new Programa(lexico.getLinea(), lexico.getColumna(), listaDefiniciones ); }
; 

main: 'DEF' MAIN '(' ')' '{' listaDefsVars listaSentssOpt '}'	
{ 
TipoFuncion t = new TipoFuncion(new ArrayList<DefinicionVariable>(), new TipoVoid());
$$ = new DefinicionFuncion(lexico.getLinea(), lexico.getColumna(), "MAIN", (Tipo)t,  ((List<DefinicionVariable>)$6), ((List<Sentencia>)$7)) ; }	 
;

listaDefiniciones: listaDefiniciones definicion  			{ $$ = $1;
															((ArrayList<Definicion> )$$).addAll((ArrayList<Definicion>)$2); }
			 	  |  										{ $$ = new ArrayList<Definicion>(); }
;

definicion:   defVariable				{$$ = $1;} /* Definicion de una variable global */
			| defFuncion 				{$$ = $1;} /* Definicion de una funcion */
;


//###########################################################################################

defVariable: listaIdents AS tipo ';' {  ArrayList<DefinicionVariable> xx = new ArrayList<DefinicionVariable>();
										for (String s: (ArrayList<String>)$1){
											xx.add(new DefinicionVariable(lexico.getLinea(), lexico.getColumna(), s, (Tipo)$3)); 
										}
										$$ = xx;
									 }
;

listaIdents: listaIdents ',' 'IDENT'   { $$ = $1; 
										 if (!((ArrayList<String>)$$).contains((String)$3))
											 ((ArrayList<String>)$$).add((String)$3); 
										 else
											 ManejadorErrores.getInstance().addError(new TipoError(lexico.getLinea(), lexico.getColumna(), null, "Identificador repetido: " + (String)$3)); }
			| 'IDENT' 					{ List<String> xx = new ArrayList<String>(); 
										  xx.add((String)$1); 
										  $$ = xx; }
;

//###########################################################################################

defFuncion: 'DEF' 'IDENT' '(' listaParametrosOpcional ')' tipoOpc  '{' listaDefsVars listaSentssOpt '}' 
					{ 	
						Tipo t = new TipoFuncion( (ArrayList<DefinicionVariable>)$4, (Tipo)$6);
						
						Definicion def = new DefinicionFuncion(lexico.getLinea(), lexico.getColumna(), (String) $2, (Tipo)t, (List<DefinicionVariable>)$8, (List<Sentencia>)$9); 
						List<Definicion> xx = new ArrayList<Definicion>();
						
						xx.add(def);
						$$ = xx;
					 }
;

	listaParametrosOpcional:  listaParametros 	{ $$ = $1; }
				 			|   				{ $$ = new ArrayList<DefinicionVariable>(); }  
	;	

	listaParametros: 'IDENT' AS tipo 						{ $$ = new ArrayList<DefinicionVariable>();
									 						((ArrayList<Definicion>)$$).add(new DefinicionVariable(lexico.getLinea(), lexico.getColumna(), (String)$1, (Tipo)$3)); }
					| listaParametros ',' 'IDENT' AS tipo  { $$ = $1;
															DefinicionVariable vd = new DefinicionVariable(lexico.getLinea(), lexico.getColumna(), (String)$3, (Tipo)$5);						
															
															if (!((ArrayList<DefinicionVariable>)$$).contains(vd))
																((ArrayList<Definicion>) $$).add(vd);
															else
																ManejadorErrores.getInstance().addError(new TipoError(lexico.getLinea(), lexico.getColumna(), vd, "Parametros duplicados.")); }
	;		
	//-------------------------------------------------------------------------------------------

	tipoOpc: AS tipo  	{ $$ = (Tipo)$2; }
			| 			{ $$ = new TipoVoid(); }
			;

	//-------------------------------------------------------------------------------------------

	listaDefsVars:  listaDefsVars defVariable 	{ $$ = $1;  
												  for (DefinicionVariable vd : (ArrayList<DefinicionVariable>)$2){
													if ( !((ArrayList<DefinicionVariable>)$$).contains(vd) ){
														((ArrayList<DefinicionVariable>)$$).add(vd); 
													} else { 
														ManejadorErrores.getInstance().addError(new TipoError(lexico.getLinea(), lexico.getColumna(), vd, "Una variable esta repetida")); }
													}
												}
					| 							{ $$ = new ArrayList<DefinicionVariable> (); }
	;

	//-------------------------------------------------------------------------------------------

	listaSentssOpt: listaSents 		{ $$ = $1; }
					| 				{ $$ = new ArrayList<Sentencia>(); }  
	;

	listaSents: listaSents sent 	{ $$ = $1;
									((ArrayList<Sentencia>)$$).add((Sentencia)$2); }
				| sent 				{ $$ = new ArrayList<Sentencia>();
							 		((ArrayList<Sentencia>)$$).add((Sentencia)$1); }
	;

//###########################################################################################

tipo: 'INT' 						{ $$ = new TipoEntero(); }
	 |'DOUBLE'						{ $$ = new TipoDouble(); }				
	 |'CHAR'						{ $$ = new TipoCaracter(); }			
	 | tipo '[' CTE_ENTERA ']'		{ $$ = new TipoArray((Tipo)$1, Integer.parseInt( String.valueOf($3)) ); }					
	 | STRUCT '{' listaCampos '}' 	{ $$ = new TipoStruct((ArrayList<DefinicionCampo>)$3); }	
;

listaCampos: listaCampos campo 	{ 	$$ = $1;		// Aqui tenemos que detectar si los campos estan repetidos	
								
									List<String> anteriores = new ArrayList<String>();
									
									for (DefinicionCampo dc : (ArrayList<DefinicionCampo>)$$ ){
										anteriores.add(dc.toString());
									}
									
									for (DefinicionCampo defcampo : (ArrayList<DefinicionCampo>)$2 ){
										if ( anteriores.contains(defcampo.toString()) ){
											ManejadorErrores.getInstance().addError(new TipoError(lexico.getLinea(), lexico.getColumna(), defcampo, "Campo repetido"));
										} else {
											((ArrayList<DefinicionCampo>)$$).add(defcampo);	
										}
									}
								}
			| campo 			{ ArrayList<DefinicionCampo> xx = new ArrayList<DefinicionCampo>();
								  xx.addAll((Collection<? extends DefinicionCampo>)$1);
								  $$ = xx; }
;

campo: listaIdents AS tipo ';' { ArrayList<DefinicionCampo> xx = new ArrayList<DefinicionCampo>();
									for (String s : (ArrayList<String>)$1){
										xx.add(new DefinicionCampo(lexico.getLinea(), lexico.getColumna(), s, (Tipo)$3));
									}
								 $$ = xx;
};

//###########################################################################################

sent:    	asignacion 			{ $$ = $1; }
			| lectura			{ $$ = $1; }
			| escritura 		{ $$ = $1; }
			| invProcedimiento	{ $$ = $1; }	/* Un procedimiento es una funcion que tiene tipoVoid (no devuelve nada) */
			| return			{ $$ = $1; }
			| while				{ $$ = $1; }
			| ifelse			{ $$ = $1; }
			;

//###########################################################################################

listaExpresiones: expresion  						{ $$ = new ArrayList<Expresion>();
													((ArrayList<Expresion>)$$).add((Expresion)$1); }
				| listaExpresiones ',' expresion  	{ $$ = $1;
													((ArrayList<Expresion>)$$).add((Expresion)$3); }
;				

listaExpresionesOpcional: listaExpresiones 			{ $$ = $1; }
						| 							{ $$ = new ArrayList<Expresion>(); }
;

asignacion: expresion '=' expresion ';' 							{ $$ = new Asignacion(lexico.getLinea(), lexico.getColumna(), (Expresion)$1, (Expresion)$3); }
;
lectura: 	'INPUT' listaExpresiones ';' 							{ $$ = new Lectura(lexico.getLinea(), lexico.getColumna(), (List<Expresion>)$2); }
;
escritura: 	'PRINT' listaExpresiones ';' 							{ $$ = new Escritura(lexico.getLinea(), lexico.getColumna(), (List<Expresion>)$2); }
;
invProcedimiento: 'IDENT' '(' listaExpresionesOpcional ')' ';'  	{ $$ = new Invocacion(lexico.getLinea(), lexico.getColumna(), $1.toString(), (List<Expresion>)$3); }
; 
return: RETURN expresion ';' 										{ $$ = new Return(lexico.getLinea(), lexico.getColumna(), (Expresion)$2); }
;
while: WHILE expresion '{' listaSents '}' 							{ $$ = new While(lexico.getLinea(), lexico.getColumna(), (Expresion)$2, (List<Sentencia>)$4); }
;	
ifelse: IF expresion '{' listaSents '}'  							{ $$ = new IfElse(lexico.getLinea(), lexico.getColumna(), (Expresion)$2, (List<Sentencia>)$4, new ArrayList<Sentencia>() ); }
		| IF expresion '{' listaSents '}' ELSE '{' listaSents '}'	{ $$ = new IfElse(lexico.getLinea(), lexico.getColumna(), (Expresion)$2, (List<Sentencia>)$4, (List<Sentencia>)$8); }
;

	
expresion:  expresion '+' expresion 			{ $$ = new Aritmetica(lexico.getLinea(), lexico.getColumna(), (Expresion) $1, "+", (Expresion)$3); }
		  | expresion '-' expresion 			{ $$ = new Aritmetica(lexico.getLinea(), lexico.getColumna(), (Expresion) $1, "-", (Expresion)$3); }
	      | expresion '*' expresion 			{ $$ = new Aritmetica(lexico.getLinea(), lexico.getColumna(), (Expresion) $1, $2.toString(), (Expresion)$3); } 
	      | expresion '/' expresion 			{ $$ = new Aritmetica(lexico.getLinea(), lexico.getColumna(), (Expresion) $1, $2.toString(), (Expresion)$3); }
	      | expresion '%' expresion				{ $$ = new Aritmetica(lexico.getLinea(), lexico.getColumna(), (Expresion) $1, $2.toString(), (Expresion)$3); } 
	      
	      | expresion MAYORIGUAL expresion		{ $$ = new Comparacion(lexico.getLinea(), lexico.getColumna(), (Expresion)$1, $2.toString(), (Expresion)$3); }
	      | expresion MENORIGUAL expresion		{ $$ = new Comparacion(lexico.getLinea(), lexico.getColumna(), (Expresion)$1, $2.toString(), (Expresion)$3); }
	      | expresion '<' expresion				{ $$ = new Comparacion(lexico.getLinea(), lexico.getColumna(), (Expresion)$1, $2.toString(), (Expresion)$3); }
	      | expresion '>' expresion				{ $$ = new Comparacion(lexico.getLinea(), lexico.getColumna(), (Expresion)$1, $2.toString(), (Expresion)$3); }
	      | expresion DISTINTO expresion		{ $$ = new Comparacion(lexico.getLinea(), lexico.getColumna(), (Expresion)$1, $2.toString(), (Expresion)$3); }
	      | expresion IGUALDAD expresion		{ $$ = new Comparacion(lexico.getLinea(), lexico.getColumna(), (Expresion)$1, $2.toString(), (Expresion)$3); }
	      
	      | expresion Y expresion				{ $$=  new ExpresionLogica(lexico.getLinea(), lexico.getColumna(), (Expresion)$1, (String)$2, (Expresion)$3); }
	      | expresion O expresion				{ $$=  new ExpresionLogica(lexico.getLinea(), lexico.getColumna(), (Expresion)$1, (String)$2, (Expresion)$3); }	     
	      
	      | 'IDENT'								{ $$ = new Variable(lexico.getLinea(), lexico.getColumna(), (String)$1); } 
	      | CTE_ENTERA							{ $$= new LiteralEntero(lexico.getLinea(), lexico.getColumna(), (Integer) $1); }
		  | CTE_REAL							{ $$= new LiteralReal(lexico.getLinea(), lexico.getColumna(), ((Float)$1) ); }
		  | CTE_CARACTER						{ $$= new LiteralChar(lexico.getLinea(), lexico.getColumna(), (Character)$1); }
		  
	      | '-' expresion %prec MENOSUNARIO 	{ $$= new MenosUnario(lexico.getLinea(), lexico.getColumna(), (Expresion)$2); }
		  | '!' expresion 						{ $$ = new NegacionLogica(lexico.getLinea(), lexico.getColumna(), (Expresion)$2); }
	      
		  | '(' expresion ')' 					{ $$= $2;}	
		  | '{' expresion '}' 					{ $$= $2;}	
		 
		  | expresion CAST tipo 				{ $$ = new Cast(lexico.getLinea(), lexico.getColumna(), (Tipo)$3, (Expresion)$1); }	

		  /* Acceso a un array */
	      | expresion '[' expresion ']'			{ $$ = new AccesoArray(lexico.getLinea(), lexico.getColumna(), (Expresion)$1, (Expresion)$3); } 
		  /* Acceso a un campo */
		  | expresion '.' 'IDENT'				{ $$ = new AccesoCampo(lexico.getLinea(), lexico.getColumna(), (Expresion)$1, (String)$3); } 
		  /* Invocacion a funcion como expresion */
		  | 'IDENT' '(' listaExpresionesOpcional ')'	{ $$ = new Invocacion(lexico.getLinea(), lexico.getColumna(), $1.toString(), (List<Expresion>)$3); }  	
		  ;
		  
%%

public NodoAST ast;

// * Cóodigo Java
// * Se crea una clase "Parser", lo que aqu�i ubiquemos ser�:
//	- Atributos, si son variables
//	- Métodos, si son funciones de la clase "Parser"

// * Estamos obligados a implementar:
//	int yylex()
//	void yyerror(String)

// * Referencia al analizador l�xico
private Lexico lexico;

// * Llamada al analizador léxico
private int yylex () {
    int token=0;
    try {
	token=lexico.yylex();
    } catch(Throwable e) {
	    System.err.println ("Error Léxico en linea " + lexico.getLinea()+
		" y columna "+lexico.getColumna()+":\n\t"+e);
    }
    return token;
}

// * Manejo de Errores Sintácticos
public void yyerror (String error) {
    System.err.println ("Error Sintactico en linea " + lexico.getLinea()+
		" y columna "+lexico.getColumna()+":\n\t"+error);
}

// * El yylval no es un atributo público
public Object getYylval() {
    	return yylval;
}
public void setYylval(Object yylval) {
        this.yylval = yylval;
}

// * Constructor del Sintáctico
public Parser(Lexico lexico) {
	this.lexico = lexico;
	lexico.setParser(this);
}