// ************  C�digo a incluir ********************

package lexico;
import sintactico.Parser;

%%
// ************  Opciones ********************
// % debug // * Opci�n para depurar
%byaccj
%class Lexico
%public
%unicode
%line
%column

%{
// ************  Atributos y m�todos ********************
// * El analizador sint�ctico
private Parser parser;
public void setParser(Parser parser) {
	this.parser=parser;
}

// * Para acceder al n�mero de l�nea (yyline es package)
public int getLinea() { 
	// * Flex empieza en cero
	return yyline+1;
}

// * Para acceder al n�mero de columna (yycolumn es package)
public int getColumna() { 
	// * Flex empieza en cero
	return yycolumn+1;
}

%}

// ************  Patrones (macros) ********************

Entero 					= int
ConstanteEntera 		= [0-9]*

Real 					= double
ConstanteReal			= [-+]?[0-9]+\.?[0-9]*([eE][-+]?[0-9]+)? // [-+]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?

Caracter 				= char
ConstanteCaracter		= \'.\'|\'\\.\'|\'\\[0-9]*\'   // \'[a-zA-ZñÑ]\'|\'\\[0-9]*\'|\'\\n\'

ConstanteIdentificador	= [a-zA-ZñÑ][a-zA-Z0-9_ñÑ]*

ComentarioUnaLinea 		= "#".*   
ComentarioVariasLineas	= "'''"~"'''"			
 

%%
// ************  Acciones ********************

[+\-\*/%<>;,.:(){}=\]\[!\']  					{ parser.setYylval((yytext().charAt(0))); return yytext().charAt(0); }

">=" 											{parser.setYylval(yytext()); return Parser.MAYORIGUAL; }
"<="											{parser.setYylval(yytext()); return Parser.MENORIGUAL; }
"=="											{parser.setYylval(yytext()); return Parser.IGUALDAD; }
"!="											{parser.setYylval(yytext()); return Parser.DISTINTO; }

"&&"											{parser.setYylval(yytext()); return Parser.Y;}
"||"											{parser.setYylval(yytext()); return Parser.O;}


input											{ parser.setYylval("INPUT"); 	return Parser.INPUT;  }
else 											{ parser.setYylval("ELSE");		return Parser.ELSE;  }
if												{ parser.setYylval("IF");    	return Parser.IF; }
print											{ parser.setYylval("PRINT");	return Parser.PRINT;  }
while											{ parser.setYylval("WHILE");	return Parser.WHILE;  }
struct											{ parser.setYylval("STRUCT");	return Parser.STRUCT;  }
return											{ parser.setYylval("RETURN");	return Parser.RETURN;  }
def												{ parser.setYylval("DEF");		return Parser.DEF;  }
as												{ parser.setYylval("AS");		return Parser.AS;  }
cast											{ parser.setYylval("CAST");		return Parser.CAST;  }
main											{ parser.setYylval("MAIN"); 	return Parser.MAIN; }

// * Constante Entera
{ConstanteEntera}								{ parser.setYylval(new Integer(yytext()));	
												  return Parser.CTE_ENTERA;  }
{Entero}										{ parser.setYylval("INT");
         			  							  return Parser.INT;  }


{ConstanteReal}									{ parser.setYylval(new Float(yytext()));
         			  							  return Parser.CTE_REAL;  }
{Real}											{ parser.setYylval("DOUBLE");
         			  							  return Parser.DOUBLE;  }

{ConstanteCaracter}								{ parser.setYylval((yytext().charAt(1)));
         			  							  return Parser.CTE_CARACTER;  }
{Caracter}										{ parser.setYylval("CHAR");
         			  							  return Parser.CHAR;  }

{ConstanteIdentificador}						{ parser.setYylval(new String(yytext()));
         			  							  return Parser.IDENT;  }				  
     			  							  
{ComentarioUnaLinea}							{ }
{ComentarioVariasLineas}						{ }
[ \n\t\r\f]     								{ }

// "\t"		{ yycolumn += 3; } // Para que coincida con la info del editor de Eclipse (opcional). En eclipse: \t = 4 espacios. En Jflex: \t = 1 car�cter.

.			{ System.err.println("Léxico  -  Cadena \"" + yytext() +"\" no reconocida. \t Linea: " + getLinea() + " Columna: " + (getColumna()+3) ) ; }

