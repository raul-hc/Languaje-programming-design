package ast.expresion;

import visitor.Visitor;

public class AccesoCampo extends AbstractExpresion {

	private Expresion objetoEstructura;
	private String nombreCampo;

	public AccesoCampo(int linea, int columna, Expresion objetoCampo, String nombreCampo) {
		super(linea, columna);

		this.objetoEstructura = objetoCampo;
		this.nombreCampo = nombreCampo;
	}

	public Expresion getObjetoEstructura() {
		return objetoEstructura;
	}

	public String getNombreCampo() {
		return nombreCampo;
	}
	
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
