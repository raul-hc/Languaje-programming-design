
package sintactico;

import lexico.Lexico;

/**
 * Clase Analizador Sint�ctico (Parser).<br/>
 * Dise�o de Lenguajes de Programaci�n.<br/>
 * Escuela de Ingenier�a Inform�tica.<br/>
 * Universidad de Oviedo.<br/>
 * @author Francisco Ortin
 */

public class Parser {

    // * Tokens
    public final static int CTE_ENTERA = 257;
    public final static int CTE_REAL = 258;    
    public final static int CTE_CARACTER = 259;  
    
    public final static int INT = 260;
    public final static int DOUBLE = 261;
    public final static int CHAR = 262;  
    
    public final static int IGUALDAD = 263;    
    public final static int DISTINTO = 264;    
    public final static int MAYORIGUAL = 265;    
    public final static int MENORIGUAL = 266;  
    
    public final static int ID = 267;
    public final static int MAIN = 268;
    public final static int READ = 269;
    public final static int WRITE = 270;
    
    public final static int INPUT = 271;
    public final static int PRINT = 272;
    public final static int WHILE = 273;
    public final static int IF = 274;
    public final static int ELSE = 275;
    public final static int STRUCT = 276;
    public final static int RETURN = 277;
    public final static int DEF = 278;
    public final static int AS = 279;
    public final static int CAST = 280;
    
    public final static int O = 281;
    public final static int Y = 282;


    // * Lexema del token devuelto (valor sem�ntico)
    Object yylval;

    // * El yylval no es un atributo p�blico
    public Object getYylval() {
    	return yylval;
    }
    public void setYylval(Object yylval) {
        this.yylval = yylval;
    }

    /**
    * Referencia al analizador l�xico
    */
    @SuppressWarnings("unused")
	private Lexico lexico;
    
    // * Constructor del Sint�ctico
    public Parser(Lexico lexico) {
        // * El sint�tico conoce al l�xico
        this.lexico = lexico;
        // * El l�xico conoce al sint�ctico (para el yylval)
        lexico.setParser(this);
    }
    
}