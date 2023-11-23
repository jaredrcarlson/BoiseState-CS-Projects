
public class NodeBoolExpr extends Node {
	
	private NodeExpr exprLeft;
	private NodeRelop relop;
	private NodeExpr exprRight;
	
	public NodeBoolExpr(int pos, NodeExpr exprLeft, NodeRelop relop, NodeExpr exprRight) {
		this.pos = pos;
		this.exprLeft = exprLeft;
		this.relop = relop;
		this.exprRight = exprRight;
	    }

	public double eval(Environment env) throws EvalException {
		return relop.op(exprLeft.eval(env),exprRight.eval(env));
	}
}
