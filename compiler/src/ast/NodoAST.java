package ast;

import visitor.Visitor;

public interface NodoAST {
	
	public Object accept(Visitor v, Object param);
	
}
