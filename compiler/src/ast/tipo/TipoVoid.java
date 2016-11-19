package ast.tipo;

import visitor.Visitor;

public class TipoVoid extends AbstractTipo {
	
	@Override
	public String toString () {
		return "void";
	}
	
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}
	
}