package ast.expresion;

public abstract class ExpresionBinaria extends AbstractExpresion {

	public Expresion exprIzqda;
	public Expresion exprDcha;
	public String operador;
	
	public ExpresionBinaria(int linea, int columna, Expresion exprIzqda, String operador, Expresion exprDcha) {
		super(linea, columna);
		
		this.exprIzqda = exprIzqda;
		this.operador = operador;
		this.exprDcha = exprDcha;
	}
	
	public Expresion getExprIzqda() {
		return exprIzqda;
	}

	public Expresion getExprDcha() {
		return exprDcha;
	}
	
	public String getOperador() {
		return operador;
	}

}
