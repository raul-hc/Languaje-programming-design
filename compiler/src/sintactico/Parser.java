//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package sintactico;



//#line 2 "../../src/sintactico/sintactico.y"
/* * Declaraciones de código Java*/
/* * Se sitúan al comienzo del archivo generado*/
/* * El package lo acade yacc si utilizamos la opción -Jpackage*/

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

//#line 36 "Parser.java"



public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Object
String   yytext;//user variable to return contextual strings
Object yyval; //used to return semantic vals from action routines
Object yylval;//the 'lval' (result) I got from yylex()
Object valstk[] = new Object[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Object();
  yylval=new Object();
  valptr=-1;
}
final void val_push(Object val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Object[] newstack = new Object[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Object val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Object val_peek(int relative)
{
  return valstk[valptr-relative];
}
final Object dup_yyval(Object val)
{
  return val;
}
//#### end semantic value section ####
public final static short CTE_ENTERA=257;
public final static short CTE_REAL=258;
public final static short CTE_CARACTER=259;
public final static short STRUCT=260;
public final static short RETURN=261;
public final static short WHILE=262;
public final static short IF=263;
public final static short ELSE=264;
public final static short Y=265;
public final static short O=266;
public final static short MAYORIGUAL=267;
public final static short MENORIGUAL=268;
public final static short DISTINTO=269;
public final static short IGUALDAD=270;
public final static short CAST=271;
public final static short MAIN=272;
public final static short AS=273;
public final static short MENOSUNARIO=274;
public final static short DEF=275;
public final static short IDENT=276;
public final static short INT=277;
public final static short DOUBLE=278;
public final static short CHAR=279;
public final static short INPUT=280;
public final static short PRINT=281;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    2,    1,    1,    5,    5,    6,    8,    8,    7,
   10,   10,   12,   12,   11,   11,    3,    3,    4,    4,
   13,   13,    9,    9,    9,    9,    9,   15,   15,   16,
   14,   14,   14,   14,   14,   14,   14,   24,   24,   26,
   26,   17,   18,   19,   20,   21,   22,   23,   23,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,
};
final static short yylen[] = {                            2,
    2,    8,    2,    0,    1,    1,    4,    3,    1,   10,
    1,    0,    3,    5,    2,    0,    2,    0,    1,    0,
    2,    1,    1,    1,    1,    4,    4,    2,    1,    4,
    1,    1,    1,    1,    1,    1,    1,    1,    3,    1,
    0,    4,    3,    3,    5,    3,    5,    5,    9,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    1,    1,    1,    1,    2,    2,    3,    3,
    3,    4,    3,    4,
};
final static short yydefred[] = {                         4,
    0,    0,    0,    9,    1,    3,    5,    6,    0,    0,
    0,    0,    0,    0,    0,    0,   23,   24,   25,    0,
    8,    0,    0,    0,    0,    0,    0,    7,   18,    0,
    0,    0,    0,    0,   29,    0,    0,    0,    0,    0,
    0,    0,   27,   28,   26,   64,   65,   66,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   17,
    0,   22,   31,   32,   33,   34,   35,   36,   37,    0,
    0,   18,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    2,    0,   21,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   30,    0,
   46,    0,    0,   69,   70,    0,    0,   43,    0,   44,
    0,    0,    0,    0,    0,    0,   71,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   73,    0,    0,    0,
    0,    0,    0,   42,   72,   10,   74,   47,    0,   45,
    0,    0,    0,   49,
};
final static short yydgoto[] = {                          1,
    2,    5,   37,   59,    6,   60,    8,    9,   20,   24,
   40,   25,   61,   62,   34,   35,   63,   64,   65,   66,
   67,   68,   69,  116,   70,  117,
};
final static short yysindex[] = {                         0,
    0, -210, -250,    0,    0,    0,    0,    0,  -42,  -36,
  -31, -157, -259,  -16, -249,  -68,    0,    0,    0,  -40,
    0,  -48, -197,   37,   35, -196, -176,    0,    0, -157,
 -188, -185,  -38, -124,    0,   -5,   16,    2, -157,  -22,
 -171, -157,    0,    0,    0,    0,    0,    0,   44,   44,
   44,   44,   44,   44,   44,   65,   44,   44,  -18,    0,
   24,    0,    0,    0,    0,    0,    0,    0,    0,   99,
    2,    0, -157,  -28,   75,  269,  311,  339,  -32,  -32,
  363,  375,   44,  -26,  464,   -1,    0,   65,    0,   44,
   44,   44,   44,   44,   44, -157,   44,   44,   44,   44,
   44,   44,   44,   44,   44, -179,   16,    2,    0,   44,
    0,   24,   24,    0,    0,   73,   82,    0,   44,    0,
  -32,  -32,   28,   28,   28,   28,    0,  396,  471,  471,
  281,  281,  281,   28,   28,  403,    0,    7,   84,  -33,
  -25,   76,  464,    0,    0,    0,    0,    0, -130,    0,
   14,   24,  -17,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   97,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  102,    0,    0,    0,    0,    0,
   17,    0,    0,    0,    0,    0,   23,   26,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   67,    0,    0,    0,    0,
   25,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   31,    0,    0,    0,  127,    0,    0,    0,  524,  706,
    0,    0,  108,    0,  -20,    0,    0,  430,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   23,   27,    0,  108,
    0,    0,    0,    0,    0,  110,    0,    0,    0,    0,
  715,  741,  783,  809,  818,  854,    0,    0,    1,  529,
   -7,  555, 1002,  907,  975,    0,    0,    0,    0,    0,
    0,  437,  -12,    0,    0,    0,    0,    0,    8,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   83,   49,    0,  155,    0,  -21,   57,    0,
    0,    0, -102,  -58,    0,  128,    0,    0,    0,    0,
    0,    0,    0,   15, 1052,   53,
};
final static int YYTABLESIZE=1245;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         53,
   43,   13,   89,   14,   33,   13,   54,   53,   15,  140,
  141,   52,   33,  106,   54,   53,   21,  119,   28,   52,
   38,   10,   54,   38,   22,   11,   23,   52,   39,   52,
  109,   39,  118,   52,   52,   52,   52,   52,   38,   52,
   48,   50,  119,   50,   50,   50,   39,   48,   53,  153,
   27,   52,   48,   52,   26,   54,   53,  120,  105,   50,
   52,   50,   27,   54,    3,    4,   13,   14,   52,   13,
   14,   84,   86,  106,   29,   30,   53,   31,   32,    4,
   36,   89,   89,   54,   39,   52,   38,   45,   52,   55,
   41,  148,   27,   50,   89,   71,  137,   55,   74,  149,
   72,   73,   16,   63,   83,   55,   87,  154,   63,   63,
    9,   63,   63,   63,  110,   52,  119,   52,  105,   17,
   18,   19,  142,   50,  147,   50,   63,   63,   63,  108,
   48,  146,   48,  151,  150,  102,  152,   12,   55,   16,
  100,   98,   11,   99,  106,  101,   55,   20,   41,   19,
   40,    4,  127,   15,  107,  138,    7,   63,  104,   97,
  103,   44,  139,   63,    0,    0,   55,   63,   63,   63,
   63,   63,   63,   63,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   63,   63,   63,   63,  105,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   63,    0,   63,
    0,    0,    0,   46,   47,   48,    0,   49,   50,   51,
   12,   46,   47,   48,   42,   49,   50,   51,   96,   46,
   47,   48,   88,   49,   50,   51,   57,   58,    0,   63,
   88,   63,    0,    0,   57,   58,    0,    0,   88,    0,
    0,    0,   57,   58,   48,   48,   48,    0,   48,   48,
   48,    0,   46,   47,   48,    0,   49,   50,   51,    0,
   46,   47,   48,   48,   49,   50,   51,   48,   48,    0,
    0,   56,   90,   91,    0,   57,   58,    0,   96,   88,
   46,   47,   48,   57,   58,  102,    0,    0,    0,    0,
  100,   98,    0,   99,  106,  101,    0,    0,    0,   75,
    0,    0,    0,    0,    0,    0,  106,  111,  104,    0,
  103,   63,   63,   63,   63,   63,   63,   63,    0,    9,
  104,    0,  103,    0,    0,    0,    0,  102,    0,    0,
    0,    0,  100,   98,    0,   99,  106,  101,    0,  105,
    0,    0,    0,   90,   91,   92,   93,   94,   95,   96,
  104,  105,  103,    0,    0,  102,    0,    0,    0,    0,
  100,   98,    0,   99,  106,  101,    0,    0,    0,    0,
    0,   63,   63,   63,   63,   63,   63,   63,  104,  102,
  103,  105,    0,  114,  100,   98,    0,   99,  106,  101,
    0,  102,    0,    0,    0,    0,  100,   98,    0,   99,
  106,  101,  104,    0,  103,    0,    0,    0,    0,  105,
    0,    0,  102,  112,  104,    0,  103,  100,   98,  102,
   99,  106,  101,    0,  100,   98,    0,   99,  106,  101,
    0,    0,    0,  105,  144,  104,    0,  103,    0,    0,
    0,  113,  104,    0,  103,  105,   63,    0,    0,    0,
    0,   63,   63,   74,   63,   63,   63,    0,   74,   74,
    0,   74,   74,   74,    0,    0,  105,    0,    0,   63,
   63,   63,    0,  105,    0,  145,   74,   74,   74,  115,
  102,    0,    0,    0,    0,  100,   98,  102,   99,  106,
  101,    0,  100,    0,    0,    0,  106,  101,    0,    0,
   63,    0,    0,  104,    0,  103,    0,   74,    0,    0,
  104,    0,  103,   90,   91,   92,   93,   94,   95,   96,
    0,    0,    0,    0,    0,   90,   91,   92,   93,   94,
   95,   96,    0,    0,  105,    0,    0,    0,    0,    0,
   67,  105,    0,    0,   67,   67,   67,   67,   67,   51,
   67,   51,   51,   51,    0,   90,   91,   92,   93,   94,
   95,   96,   67,   67,   67,   67,    0,   51,    0,   51,
    0,   53,    0,    0,    0,   53,   53,   53,   53,   53,
    0,   53,    0,   90,   91,   92,   93,   94,   95,   96,
    0,    0,    0,   53,    0,   53,   67,    0,    0,    0,
    0,   51,    0,    0,    0,    0,    0,   90,   91,   92,
   93,   94,   95,   96,    0,    0,    0,    0,    0,   90,
   91,   92,   93,   94,   95,   96,   67,   53,   67,    0,
    0,   51,    0,   51,    0,    0,    0,    0,    0,    0,
   90,   91,   92,   93,   94,   95,   96,   90,   91,   92,
   93,   94,   95,   96,    0,    0,    0,   53,    0,   53,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   63,   63,   63,   63,   63,   63,
   63,   74,   74,   74,   74,   74,   74,   74,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   90,   91,
   92,   93,   94,   95,   96,   90,   91,   92,   93,   94,
   95,   96,   68,    0,    0,    0,   68,   68,   68,   68,
   68,   61,   68,    0,    0,   61,   61,   61,   61,   61,
    0,   61,    0,    0,   68,   68,   68,   68,    0,    0,
    0,    0,    0,   61,   61,   61,   61,   62,    0,    0,
    0,   62,   62,   62,   62,   62,    0,   62,   67,   67,
   67,   67,   67,   67,    0,    0,    0,    0,   68,   62,
   62,   62,   62,    0,    0,    0,    0,   61,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   55,
    0,    0,    0,   55,   55,   55,   55,   55,   68,   55,
   68,    0,    0,   62,    0,    0,    0,   61,    0,   61,
    0,   55,   55,   55,   55,   56,    0,    0,    0,   56,
   56,   56,   56,   56,   59,   56,    0,    0,   59,   59,
   59,   59,   59,   62,   59,   62,    0,   56,   56,   56,
   56,    0,    0,    0,    0,   55,   59,   59,   59,   59,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   60,    0,    0,    0,   60,   60,   60,   60,   60,    0,
   60,   56,    0,    0,    0,   55,    0,   55,    0,    0,
   59,    0,   60,   60,   60,   60,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   56,    0,   56,    0,    0,    0,    0,    0,    0,
   59,    0,   59,   58,    0,    0,   60,   58,   58,   58,
   58,   58,    0,   58,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   58,   58,   58,   58,    0,
   68,   68,   68,   68,   68,   68,   60,    0,   60,   61,
   61,   61,   61,   61,   61,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   58,
    0,    0,    0,    0,    0,   62,   62,   62,   62,   62,
   62,   57,    0,    0,    0,   57,   57,   57,   57,   57,
    0,   57,    0,    0,    0,    0,    0,    0,    0,   58,
    0,   58,    0,   57,   57,   57,   57,    0,   54,    0,
    0,    0,   54,   54,   54,   54,   54,    0,   54,   55,
   55,   55,   55,    0,    0,    0,    0,    0,    0,    0,
   54,    0,   54,    0,    0,    0,    0,   57,    0,    0,
    0,    0,    0,    0,    0,   56,   56,   56,   56,    0,
    0,    0,    0,    0,   59,   59,   59,   59,    0,    0,
    0,    0,    0,    0,   54,    0,    0,   57,    0,   57,
   76,   77,   78,   79,   80,   81,   82,    0,   85,   85,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   60,   60,   60,   60,   54,    0,   54,    0,    0,    0,
    0,    0,    0,    0,   85,    0,    0,    0,    0,    0,
    0,  121,  122,  123,  124,  125,  126,    0,  128,  129,
  130,  131,  132,  133,  134,  135,  136,    0,    0,    0,
    0,   85,    0,    0,    0,    0,    0,    0,    0,    0,
  143,    0,    0,   58,   58,   58,   58,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   57,   57,   57,   57,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
  125,   44,   61,   40,   26,   44,   40,   33,   40,  112,
  113,   45,   34,   46,   40,   33,  276,   44,   59,   45,
   41,  272,   40,   44,   41,  276,  276,   45,   41,   37,
   59,   44,   59,   41,   42,   43,   44,   45,   59,   47,
   33,   41,   44,   43,   44,   45,   59,   40,   33,  152,
   91,   59,   45,   61,  123,   40,   33,   59,   91,   59,
   45,   61,   91,   40,  275,  276,   41,   41,   45,   44,
   44,   57,   58,   46,  123,  273,   33,   41,   44,  276,
  257,  140,  141,   40,  273,   93,   30,   93,   45,  123,
  276,  125,   91,   93,  153,   39,  276,  123,   42,  125,
  123,  273,  260,   37,   40,  123,  125,  125,   42,   43,
   44,   45,   46,   47,   40,  123,   44,  125,   91,  277,
  278,  279,   41,  123,   41,  125,   60,   61,   62,   73,
  123,  125,  125,  264,   59,   37,  123,   41,  123,  123,
   42,   43,   41,   45,   46,   47,  123,  125,   41,  125,
   41,  276,   96,  123,   72,  107,    2,   91,   60,   61,
   62,   34,  110,   37,   -1,   -1,  123,   41,   42,   43,
   44,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   59,   60,   61,   62,   91,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,   -1,   93,
   -1,   -1,   -1,  257,  258,  259,   -1,  261,  262,  263,
  273,  257,  258,  259,  273,  261,  262,  263,  271,  257,
  258,  259,  276,  261,  262,  263,  280,  281,   -1,  123,
  276,  125,   -1,   -1,  280,  281,   -1,   -1,  276,   -1,
   -1,   -1,  280,  281,  257,  258,  259,   -1,  261,  262,
  263,   -1,  257,  258,  259,   -1,  261,  262,  263,   -1,
  257,  258,  259,  276,  261,  262,  263,  280,  281,   -1,
   -1,  276,  265,  266,   -1,  280,  281,   -1,  271,  276,
  257,  258,  259,  280,  281,   37,   -1,   -1,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,  276,
   -1,   -1,   -1,   -1,   -1,   -1,   46,   59,   60,   -1,
   62,  265,  266,  267,  268,  269,  270,  271,   -1,  273,
   60,   -1,   62,   -1,   -1,   -1,   -1,   37,   -1,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,   91,
   -1,   -1,   -1,  265,  266,  267,  268,  269,  270,  271,
   60,   91,   62,   -1,   -1,   37,   -1,   -1,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
   -1,  265,  266,  267,  268,  269,  270,  271,   60,   37,
   62,   91,   -1,   41,   42,   43,   -1,   45,   46,   47,
   -1,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   60,   -1,   62,   -1,   -1,   -1,   -1,   91,
   -1,   -1,   37,  123,   60,   -1,   62,   42,   43,   37,
   45,   46,   47,   -1,   42,   43,   -1,   45,   46,   47,
   -1,   -1,   -1,   91,   59,   60,   -1,   62,   -1,   -1,
   -1,  123,   60,   -1,   62,   91,   37,   -1,   -1,   -1,
   -1,   42,   43,   37,   45,   46,   47,   -1,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   91,   -1,   -1,   60,
   61,   62,   -1,   91,   -1,   93,   60,   61,   62,  125,
   37,   -1,   -1,   -1,   -1,   42,   43,   37,   45,   46,
   47,   -1,   42,   -1,   -1,   -1,   46,   47,   -1,   -1,
   91,   -1,   -1,   60,   -1,   62,   -1,   91,   -1,   -1,
   60,   -1,   62,  265,  266,  267,  268,  269,  270,  271,
   -1,   -1,   -1,   -1,   -1,  265,  266,  267,  268,  269,
  270,  271,   -1,   -1,   91,   -1,   -1,   -1,   -1,   -1,
   37,   91,   -1,   -1,   41,   42,   43,   44,   45,   41,
   47,   43,   44,   45,   -1,  265,  266,  267,  268,  269,
  270,  271,   59,   60,   61,   62,   -1,   59,   -1,   61,
   -1,   37,   -1,   -1,   -1,   41,   42,   43,   44,   45,
   -1,   47,   -1,  265,  266,  267,  268,  269,  270,  271,
   -1,   -1,   -1,   59,   -1,   61,   93,   -1,   -1,   -1,
   -1,   93,   -1,   -1,   -1,   -1,   -1,  265,  266,  267,
  268,  269,  270,  271,   -1,   -1,   -1,   -1,   -1,  265,
  266,  267,  268,  269,  270,  271,  123,   93,  125,   -1,
   -1,  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,
  265,  266,  267,  268,  269,  270,  271,  265,  266,  267,
  268,  269,  270,  271,   -1,   -1,   -1,  123,   -1,  125,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  265,  266,  267,  268,  269,  270,
  271,  265,  266,  267,  268,  269,  270,  271,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  265,  266,
  267,  268,  269,  270,  271,  265,  266,  267,  268,  269,
  270,  271,   37,   -1,   -1,   -1,   41,   42,   43,   44,
   45,   37,   47,   -1,   -1,   41,   42,   43,   44,   45,
   -1,   47,   -1,   -1,   59,   60,   61,   62,   -1,   -1,
   -1,   -1,   -1,   59,   60,   61,   62,   37,   -1,   -1,
   -1,   41,   42,   43,   44,   45,   -1,   47,  265,  266,
  267,  268,  269,  270,   -1,   -1,   -1,   -1,   93,   59,
   60,   61,   62,   -1,   -1,   -1,   -1,   93,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   37,
   -1,   -1,   -1,   41,   42,   43,   44,   45,  123,   47,
  125,   -1,   -1,   93,   -1,   -1,   -1,  123,   -1,  125,
   -1,   59,   60,   61,   62,   37,   -1,   -1,   -1,   41,
   42,   43,   44,   45,   37,   47,   -1,   -1,   41,   42,
   43,   44,   45,  123,   47,  125,   -1,   59,   60,   61,
   62,   -1,   -1,   -1,   -1,   93,   59,   60,   61,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   37,   -1,   -1,   -1,   41,   42,   43,   44,   45,   -1,
   47,   93,   -1,   -1,   -1,  123,   -1,  125,   -1,   -1,
   93,   -1,   59,   60,   61,   62,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,
  123,   -1,  125,   37,   -1,   -1,   93,   41,   42,   43,
   44,   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   59,   60,   61,   62,   -1,
  265,  266,  267,  268,  269,  270,  123,   -1,  125,  265,
  266,  267,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   93,
   -1,   -1,   -1,   -1,   -1,  265,  266,  267,  268,  269,
  270,   37,   -1,   -1,   -1,   41,   42,   43,   44,   45,
   -1,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,
   -1,  125,   -1,   59,   60,   61,   62,   -1,   37,   -1,
   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,  267,
  268,  269,  270,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   59,   -1,   61,   -1,   -1,   -1,   -1,   93,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  267,  268,  269,  270,   -1,
   -1,   -1,   -1,   -1,  267,  268,  269,  270,   -1,   -1,
   -1,   -1,   -1,   -1,   93,   -1,   -1,  123,   -1,  125,
   49,   50,   51,   52,   53,   54,   55,   -1,   57,   58,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  267,  268,  269,  270,  123,   -1,  125,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   83,   -1,   -1,   -1,   -1,   -1,
   -1,   90,   91,   92,   93,   94,   95,   -1,   97,   98,
   99,  100,  101,  102,  103,  104,  105,   -1,   -1,   -1,
   -1,  110,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  119,   -1,   -1,  267,  268,  269,  270,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  267,  268,  269,  270,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=281;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"CTE_ENTERA","CTE_REAL",
"CTE_CARACTER","STRUCT","RETURN","WHILE","IF","ELSE","Y","O","MAYORIGUAL",
"MENORIGUAL","DISTINTO","IGUALDAD","CAST","MAIN","AS","MENOSUNARIO","\"DEF\"",
"\"IDENT\"","\"INT\"","\"DOUBLE\"","\"CHAR\"","\"INPUT\"","\"PRINT\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : listaDefiniciones main",
"main : \"DEF\" MAIN '(' ')' '{' listaDefsVars listaSentssOpt '}'",
"listaDefiniciones : listaDefiniciones definicion",
"listaDefiniciones :",
"definicion : defVariable",
"definicion : defFuncion",
"defVariable : listaIdents AS tipo ';'",
"listaIdents : listaIdents ',' \"IDENT\"",
"listaIdents : \"IDENT\"",
"defFuncion : \"DEF\" \"IDENT\" '(' listaParametrosOpcional ')' tipoOpc '{' listaDefsVars listaSentssOpt '}'",
"listaParametrosOpcional : listaParametros",
"listaParametrosOpcional :",
"listaParametros : \"IDENT\" AS tipo",
"listaParametros : listaParametros ',' \"IDENT\" AS tipo",
"tipoOpc : AS tipo",
"tipoOpc :",
"listaDefsVars : listaDefsVars defVariable",
"listaDefsVars :",
"listaSentssOpt : listaSents",
"listaSentssOpt :",
"listaSents : listaSents sent",
"listaSents : sent",
"tipo : \"INT\"",
"tipo : \"DOUBLE\"",
"tipo : \"CHAR\"",
"tipo : tipo '[' CTE_ENTERA ']'",
"tipo : STRUCT '{' listaCampos '}'",
"listaCampos : listaCampos campo",
"listaCampos : campo",
"campo : listaIdents AS tipo ';'",
"sent : asignacion",
"sent : lectura",
"sent : escritura",
"sent : invProcedimiento",
"sent : return",
"sent : while",
"sent : ifelse",
"listaExpresiones : expresion",
"listaExpresiones : listaExpresiones ',' expresion",
"listaExpresionesOpcional : listaExpresiones",
"listaExpresionesOpcional :",
"asignacion : expresion '=' expresion ';'",
"lectura : \"INPUT\" listaExpresiones ';'",
"escritura : \"PRINT\" listaExpresiones ';'",
"invProcedimiento : \"IDENT\" '(' listaExpresionesOpcional ')' ';'",
"return : RETURN expresion ';'",
"while : WHILE expresion '{' listaSents '}'",
"ifelse : IF expresion '{' listaSents '}'",
"ifelse : IF expresion '{' listaSents '}' ELSE '{' listaSents '}'",
"expresion : expresion '+' expresion",
"expresion : expresion '-' expresion",
"expresion : expresion '*' expresion",
"expresion : expresion '/' expresion",
"expresion : expresion '%' expresion",
"expresion : expresion MAYORIGUAL expresion",
"expresion : expresion MENORIGUAL expresion",
"expresion : expresion '<' expresion",
"expresion : expresion '>' expresion",
"expresion : expresion DISTINTO expresion",
"expresion : expresion IGUALDAD expresion",
"expresion : expresion Y expresion",
"expresion : expresion O expresion",
"expresion : \"IDENT\"",
"expresion : CTE_ENTERA",
"expresion : CTE_REAL",
"expresion : CTE_CARACTER",
"expresion : '-' expresion",
"expresion : '!' expresion",
"expresion : '(' expresion ')'",
"expresion : '{' expresion '}'",
"expresion : expresion CAST tipo",
"expresion : expresion '[' expresion ']'",
"expresion : expresion '.' \"IDENT\"",
"expresion : \"IDENT\" '(' listaExpresionesOpcional ')'",
};

//#line 278 "../../src/sintactico/sintactico.y"

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
//#line 627 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


@SuppressWarnings("unchecked")
//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 61 "../../src/sintactico/sintactico.y"
{ 
DefinicionFuncion main = (DefinicionFuncion)val_peek(0);
List<Definicion> listaDefiniciones = ((List<Definicion>)val_peek(1));
listaDefiniciones.add(main);
								ast = new Programa(lexico.getLinea(), lexico.getColumna(), listaDefiniciones ); }
break;
case 2:
//#line 69 "../../src/sintactico/sintactico.y"
{ 
TipoFuncion t = new TipoFuncion(new ArrayList<DefinicionVariable>(), new TipoVoid());
yyval = new DefinicionFuncion(lexico.getLinea(), lexico.getColumna(), "MAIN", (Tipo)t,  ((List<DefinicionVariable>)val_peek(2)), ((List<Sentencia>)val_peek(1))) ; }
break;
case 3:
//#line 74 "../../src/sintactico/sintactico.y"
{ yyval = val_peek(1);
															((ArrayList<Definicion> )yyval).addAll((ArrayList<Definicion>)val_peek(0)); }
break;
case 4:
//#line 76 "../../src/sintactico/sintactico.y"
{ yyval = new ArrayList<Definicion>(); }
break;
case 5:
//#line 79 "../../src/sintactico/sintactico.y"
{yyval = val_peek(0);}
break;
case 6:
//#line 80 "../../src/sintactico/sintactico.y"
{yyval = val_peek(0);}
break;
case 7:
//#line 86 "../../src/sintactico/sintactico.y"
{  ArrayList<DefinicionVariable> xx = new ArrayList<DefinicionVariable>();
										for (String s: (ArrayList<String>)val_peek(3)){
											xx.add(new DefinicionVariable(lexico.getLinea(), lexico.getColumna(), s, (Tipo)val_peek(1))); 
										}
										yyval = xx;
									 }
break;
case 8:
//#line 94 "../../src/sintactico/sintactico.y"
{ yyval = val_peek(2); 
										 if (!((ArrayList<String>)yyval).contains((String)val_peek(0)))
											 ((ArrayList<String>)yyval).add((String)val_peek(0)); 
										 else
											 ManejadorErrores.getInstance().addError(new TipoError(lexico.getLinea(), lexico.getColumna(), null, "Identificador repetido: " + (String)val_peek(0))); }
break;
case 9:
//#line 99 "../../src/sintactico/sintactico.y"
{ List<String> xx = new ArrayList<String>(); 
										  xx.add((String)val_peek(0)); 
										  yyval = xx; }
break;
case 10:
//#line 107 "../../src/sintactico/sintactico.y"
{ 	
						Tipo t = new TipoFuncion( (ArrayList<DefinicionVariable>)val_peek(6), (Tipo)val_peek(4));
						
						Definicion def = new DefinicionFuncion(lexico.getLinea(), lexico.getColumna(), (String) val_peek(8), (Tipo)t, (List<DefinicionVariable>)val_peek(2), (List<Sentencia>)val_peek(1)); 
						List<Definicion> xx = new ArrayList<Definicion>();
						
						xx.add(def);
						yyval = xx;
					 }
break;
case 11:
//#line 118 "../../src/sintactico/sintactico.y"
{ yyval = val_peek(0); }
break;
case 12:
//#line 119 "../../src/sintactico/sintactico.y"
{ yyval = new ArrayList<DefinicionVariable>(); }
break;
case 13:
//#line 122 "../../src/sintactico/sintactico.y"
{ yyval = new ArrayList<DefinicionVariable>();
									 						((ArrayList<Definicion>)yyval).add(new DefinicionVariable(lexico.getLinea(), lexico.getColumna(), (String)val_peek(2), (Tipo)val_peek(0))); }
break;
case 14:
//#line 124 "../../src/sintactico/sintactico.y"
{ yyval = val_peek(4);
															DefinicionVariable vd = new DefinicionVariable(lexico.getLinea(), lexico.getColumna(), (String)val_peek(2), (Tipo)val_peek(0));						
															
															if (!((ArrayList<DefinicionVariable>)yyval).contains(vd))
																((ArrayList<Definicion>) yyval).add(vd);
															else
																ManejadorErrores.getInstance().addError(new TipoError(lexico.getLinea(), lexico.getColumna(), vd, "Parametros duplicados.")); }
break;
case 15:
//#line 134 "../../src/sintactico/sintactico.y"
{ yyval = (Tipo)val_peek(0); }
break;
case 16:
//#line 135 "../../src/sintactico/sintactico.y"
{ yyval = new TipoVoid(); }
break;
case 17:
//#line 140 "../../src/sintactico/sintactico.y"
{ yyval = val_peek(1);  
												  for (DefinicionVariable vd : (ArrayList<DefinicionVariable>)val_peek(0)){
													if ( !((ArrayList<DefinicionVariable>)yyval).contains(vd) ){
														((ArrayList<DefinicionVariable>)yyval).add(vd); 
													} else { 
														ManejadorErrores.getInstance().addError(new TipoError(lexico.getLinea(), lexico.getColumna(), vd, "Una variable esta repetida")); }
													}
												}
break;
case 18:
//#line 148 "../../src/sintactico/sintactico.y"
{ yyval = new ArrayList<DefinicionVariable> (); }
break;
case 19:
//#line 153 "../../src/sintactico/sintactico.y"
{ yyval = val_peek(0); }
break;
case 20:
//#line 154 "../../src/sintactico/sintactico.y"
{ yyval = new ArrayList<Sentencia>(); }
break;
case 21:
//#line 157 "../../src/sintactico/sintactico.y"
{ yyval = val_peek(1);
									((ArrayList<Sentencia>)yyval).add((Sentencia)val_peek(0)); }
break;
case 22:
//#line 159 "../../src/sintactico/sintactico.y"
{ yyval = new ArrayList<Sentencia>();
							 		((ArrayList<Sentencia>)yyval).add((Sentencia)val_peek(0)); }
break;
case 23:
//#line 165 "../../src/sintactico/sintactico.y"
{ yyval = new TipoEntero(); }
break;
case 24:
//#line 166 "../../src/sintactico/sintactico.y"
{ yyval = new TipoDouble(); }
break;
case 25:
//#line 167 "../../src/sintactico/sintactico.y"
{ yyval = new TipoCaracter(); }
break;
case 26:
//#line 168 "../../src/sintactico/sintactico.y"
{ yyval = new TipoArray((Tipo)val_peek(3), Integer.parseInt( String.valueOf(val_peek(1))) ); }
break;
case 27:
//#line 169 "../../src/sintactico/sintactico.y"
{ yyval = new TipoStruct((ArrayList<DefinicionCampo>)val_peek(1)); }
break;
case 28:
//#line 172 "../../src/sintactico/sintactico.y"
{ 	yyval = val_peek(1);		/* Aqui tenemos que detectar si los campos estan repetidos	*/
								
									List<String> anteriores = new ArrayList<String>();
									
									for (DefinicionCampo dc : (ArrayList<DefinicionCampo>)yyval ){
										anteriores.add(dc.toString());
									}
									
									for (DefinicionCampo defcampo : (ArrayList<DefinicionCampo>)val_peek(0) ){
										if ( anteriores.contains(defcampo.toString()) ){
											ManejadorErrores.getInstance().addError(new TipoError(lexico.getLinea(), lexico.getColumna(), defcampo, "Campo repetido"));
										} else {
											((ArrayList<DefinicionCampo>)yyval).add(defcampo);	
										}
									}
								}
break;
case 29:
//#line 188 "../../src/sintactico/sintactico.y"
{ ArrayList<DefinicionCampo> xx = new ArrayList<DefinicionCampo>();
								  xx.addAll((Collection<? extends DefinicionCampo>)val_peek(0));
								  yyval = xx; }
break;
case 30:
//#line 193 "../../src/sintactico/sintactico.y"
{ ArrayList<DefinicionCampo> xx = new ArrayList<DefinicionCampo>();
									for (String s : (ArrayList<String>)val_peek(3)){
										xx.add(new DefinicionCampo(lexico.getLinea(), lexico.getColumna(), s, (Tipo)val_peek(1)));
									}
								 yyval = xx;
}
break;
case 31:
//#line 202 "../../src/sintactico/sintactico.y"
{ yyval = val_peek(0); }
break;
case 32:
//#line 203 "../../src/sintactico/sintactico.y"
{ yyval = val_peek(0); }
break;
case 33:
//#line 204 "../../src/sintactico/sintactico.y"
{ yyval = val_peek(0); }
break;
case 34:
//#line 205 "../../src/sintactico/sintactico.y"
{ yyval = val_peek(0); }
break;
case 35:
//#line 206 "../../src/sintactico/sintactico.y"
{ yyval = val_peek(0); }
break;
case 36:
//#line 207 "../../src/sintactico/sintactico.y"
{ yyval = val_peek(0); }
break;
case 37:
//#line 208 "../../src/sintactico/sintactico.y"
{ yyval = val_peek(0); }
break;
case 38:
//#line 213 "../../src/sintactico/sintactico.y"
{ yyval = new ArrayList<Expresion>();
													((ArrayList<Expresion>)yyval).add((Expresion)val_peek(0)); }
break;
case 39:
//#line 215 "../../src/sintactico/sintactico.y"
{ yyval = val_peek(2);
													((ArrayList<Expresion>)yyval).add((Expresion)val_peek(0)); }
break;
case 40:
//#line 219 "../../src/sintactico/sintactico.y"
{ yyval = val_peek(0); }
break;
case 41:
//#line 220 "../../src/sintactico/sintactico.y"
{ yyval = new ArrayList<Expresion>(); }
break;
case 42:
//#line 223 "../../src/sintactico/sintactico.y"
{ yyval = new Asignacion(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(3), (Expresion)val_peek(1)); }
break;
case 43:
//#line 225 "../../src/sintactico/sintactico.y"
{ yyval = new Lectura(lexico.getLinea(), lexico.getColumna(), (List<Expresion>)val_peek(1)); }
break;
case 44:
//#line 227 "../../src/sintactico/sintactico.y"
{ yyval = new Escritura(lexico.getLinea(), lexico.getColumna(), (List<Expresion>)val_peek(1)); }
break;
case 45:
//#line 229 "../../src/sintactico/sintactico.y"
{ yyval = new Invocacion(lexico.getLinea(), lexico.getColumna(), val_peek(4).toString(), (List<Expresion>)val_peek(2)); }
break;
case 46:
//#line 231 "../../src/sintactico/sintactico.y"
{ yyval = new Return(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(1)); }
break;
case 47:
//#line 233 "../../src/sintactico/sintactico.y"
{ yyval = new While(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(3), (List<Sentencia>)val_peek(1)); }
break;
case 48:
//#line 235 "../../src/sintactico/sintactico.y"
{ yyval = new IfElse(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(3), (List<Sentencia>)val_peek(1), new ArrayList<Sentencia>() ); }
break;
case 49:
//#line 236 "../../src/sintactico/sintactico.y"
{ yyval = new IfElse(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(7), (List<Sentencia>)val_peek(5), (List<Sentencia>)val_peek(1)); }
break;
case 50:
//#line 240 "../../src/sintactico/sintactico.y"
{ yyval = new Aritmetica(lexico.getLinea(), lexico.getColumna(), (Expresion) val_peek(2), "+", (Expresion)val_peek(0)); }
break;
case 51:
//#line 241 "../../src/sintactico/sintactico.y"
{ yyval = new Aritmetica(lexico.getLinea(), lexico.getColumna(), (Expresion) val_peek(2), "-", (Expresion)val_peek(0)); }
break;
case 52:
//#line 242 "../../src/sintactico/sintactico.y"
{ yyval = new Aritmetica(lexico.getLinea(), lexico.getColumna(), (Expresion) val_peek(2), val_peek(1).toString(), (Expresion)val_peek(0)); }
break;
case 53:
//#line 243 "../../src/sintactico/sintactico.y"
{ yyval = new Aritmetica(lexico.getLinea(), lexico.getColumna(), (Expresion) val_peek(2), val_peek(1).toString(), (Expresion)val_peek(0)); }
break;
case 54:
//#line 244 "../../src/sintactico/sintactico.y"
{ yyval = new Aritmetica(lexico.getLinea(), lexico.getColumna(), (Expresion) val_peek(2), val_peek(1).toString(), (Expresion)val_peek(0)); }
break;
case 55:
//#line 246 "../../src/sintactico/sintactico.y"
{ yyval = new Comparacion(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(2), val_peek(1).toString(), (Expresion)val_peek(0)); }
break;
case 56:
//#line 247 "../../src/sintactico/sintactico.y"
{ yyval = new Comparacion(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(2), val_peek(1).toString(), (Expresion)val_peek(0)); }
break;
case 57:
//#line 248 "../../src/sintactico/sintactico.y"
{ yyval = new Comparacion(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(2), val_peek(1).toString(), (Expresion)val_peek(0)); }
break;
case 58:
//#line 249 "../../src/sintactico/sintactico.y"
{ yyval = new Comparacion(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(2), val_peek(1).toString(), (Expresion)val_peek(0)); }
break;
case 59:
//#line 250 "../../src/sintactico/sintactico.y"
{ yyval = new Comparacion(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(2), val_peek(1).toString(), (Expresion)val_peek(0)); }
break;
case 60:
//#line 251 "../../src/sintactico/sintactico.y"
{ yyval = new Comparacion(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(2), val_peek(1).toString(), (Expresion)val_peek(0)); }
break;
case 61:
//#line 253 "../../src/sintactico/sintactico.y"
{ yyval=  new ExpresionLogica(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(2), (String)val_peek(1), (Expresion)val_peek(0)); }
break;
case 62:
//#line 254 "../../src/sintactico/sintactico.y"
{ yyval=  new ExpresionLogica(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(2), (String)val_peek(1), (Expresion)val_peek(0)); }
break;
case 63:
//#line 256 "../../src/sintactico/sintactico.y"
{ yyval = new Variable(lexico.getLinea(), lexico.getColumna(), (String)val_peek(0)); }
break;
case 64:
//#line 257 "../../src/sintactico/sintactico.y"
{ yyval= new LiteralEntero(lexico.getLinea(), lexico.getColumna(), (Integer) val_peek(0)); }
break;
case 65:
//#line 258 "../../src/sintactico/sintactico.y"
{ yyval= new LiteralReal(lexico.getLinea(), lexico.getColumna(), ((Float)val_peek(0)) ); }
break;
case 66:
//#line 259 "../../src/sintactico/sintactico.y"
{ yyval= new LiteralChar(lexico.getLinea(), lexico.getColumna(), (Character)val_peek(0)); }
break;
case 67:
//#line 261 "../../src/sintactico/sintactico.y"
{ yyval= new MenosUnario(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(0)); }
break;
case 68:
//#line 262 "../../src/sintactico/sintactico.y"
{ yyval = new NegacionLogica(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(0)); }
break;
case 69:
//#line 264 "../../src/sintactico/sintactico.y"
{ yyval= val_peek(1);}
break;
case 70:
//#line 265 "../../src/sintactico/sintactico.y"
{ yyval= val_peek(1);}
break;
case 71:
//#line 267 "../../src/sintactico/sintactico.y"
{ yyval = new Cast(lexico.getLinea(), lexico.getColumna(), (Tipo)val_peek(0), (Expresion)val_peek(2)); }
break;
case 72:
//#line 270 "../../src/sintactico/sintactico.y"
{ yyval = new AccesoArray(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(3), (Expresion)val_peek(1)); }
break;
case 73:
//#line 272 "../../src/sintactico/sintactico.y"
{ yyval = new AccesoCampo(lexico.getLinea(), lexico.getColumna(), (Expresion)val_peek(2), (String)val_peek(0)); }
break;
case 74:
//#line 274 "../../src/sintactico/sintactico.y"
{ yyval = new Invocacion(lexico.getLinea(), lexico.getColumna(), val_peek(3).toString(), (List<Expresion>)val_peek(1)); }
break;
//#line 1138 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
