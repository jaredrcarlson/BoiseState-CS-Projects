
public class NodeBlock extends Node {
	
	private NodeStmt stmt;
	private NodeBlock block;
	
	public NodeBlock(int pos, NodeStmt stmt, NodeBlock block) {
		this.pos = pos;
		this.stmt = stmt;
		this.block = block;
	}
	
	public double eval(Environment env) throws EvalException {
		stmt.eval(env);
		if(block != null){
			block.eval(env);
		}
		return 1;
	}
}
