
public class NodeStmtCondWhile extends NodeStmt {
	
	private NodeBoolExpr boolExpr;
	private NodeStmt stmt;
	
	public NodeStmtCondWhile(int pos, NodeBoolExpr boolExpr, NodeStmt stmt) {
		this.pos = pos;
		this.boolExpr = boolExpr;
		this.stmt = stmt;
		if(debug) {
    		System.out.println("Created condWhile[" + this.pos + "]\n");
    	}
	}

	public double eval(Environment env) throws EvalException {
		while(boolExpr.eval(env) > 0) {
			try {
				stmt.eval(env);	
			} catch (EvalException e) {
    			System.out.println("Evaluation Error!");
    			e.printStackTrace();
			}
		}
		return 1;
		
	}	
}
