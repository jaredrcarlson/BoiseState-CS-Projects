
public class NodeStmtBody extends NodeStmt {
	
	private NodeBlock block;
	
	public NodeStmtBody(int pos, NodeBlock block) {
		this.pos = pos;
		this.block = block;
		if(debug) {
    		System.out.println("Created stmtbody[" + this.pos + "]\n");
    	}
	}
	
	public double eval(Environment env) throws EvalException {
		if(block == null) {
			throw new EvalException(pos,"bogus block"+block);
		}
		else {
			return block.eval(env);
		}
	}

}
