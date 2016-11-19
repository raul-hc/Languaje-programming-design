package ast.expresion;

public abstract class AbstractOperadorUnario extends AbstractExpresion {

	protected Expresion expresion;
	protected String operador;

	public AbstractOperadorUnario(int linea, int columna, String operador, Expresion expresion) {
		super(linea, columna);
		
		this.operador = operador;
		this.expresion = expresion;
	}
	
}
