//import java.util.Vector;


public class NodeProg extends Node {
	
	private NodeBlock block;
	//private Vector<NodeBlock> blocks = new Vector<NodeBlock>();
	
	public NodeProg(NodeBlock block) {
		this.block = block;
		//blocks.add(block);
	}
	
	public double eval(Environment env) throws EvalException {
		return block.eval(env);
		//for(NodeBlock nb : blocks) {
		//	nb.eval(env);
		//}
		//return 1;
	}

}
