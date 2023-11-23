
public class NodeStmtWr extends NodeStmt {
	
	private NodeExpr expr;
	
	public NodeStmtWr(int pos, NodeExpr expr) {
		this.pos = pos;
		this.expr = expr;
		if(debug) {
    		System.out.println("Created wr[" + this.pos + "]\n");
    	}
	}
	
	public double eval(Environment env) throws EvalException {
		System.out.println(expr.eval(env));
		return 1;
	}
}
