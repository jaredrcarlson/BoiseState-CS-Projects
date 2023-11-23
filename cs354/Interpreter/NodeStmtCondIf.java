
public class NodeStmtCondIf extends NodeStmt {
	
	private NodeBoolExpr boolExpr;
	private NodeStmt stmtCondT;
	private NodeStmt stmtCondF;
	
	public NodeStmtCondIf(int pos, NodeBoolExpr boolExpr, NodeStmt stmtCondT, NodeStmt stmtCondF) {
		this.pos = pos;
		this.boolExpr = boolExpr;
		this.stmtCondT = stmtCondT;
		this.stmtCondF = stmtCondF;
		if(debug) {
    		System.out.println("Created condIf[" + this.pos + "]\n");
    	}
	}
	
	public double eval(Environment env) throws EvalException {
		//TODO: Add Throw
		if(boolExpr.eval(env) > 0) {
			return stmtCondT.eval(env);
		}
		else {
			if(stmtCondF != null) {
				return stmtCondF.eval(env);
			}
		}
		return -1;	
	}
}
