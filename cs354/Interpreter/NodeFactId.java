public class NodeFactId extends NodeFact {

    private String id;

    public NodeFactId(int pos, String id) {
    	this.pos=pos;
    	this.id=id;
    	if(debug) {
    		System.out.println("Created id[" + this.pos + "," + this.id + "]\n");
    	}
    }

    public double eval(Environment env) throws EvalException {
    	return env.get(pos,id);
    }

}
