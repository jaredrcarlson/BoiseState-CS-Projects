
/**
 * The Class NodeAssn - represents an assignment statement
 * Contains an identifier and an expression that will be
 * the value assigned to the identifier upon evaluation.
 */
public class NodeStmtAssn extends NodeStmt {

    /** The identifier string */
    private String id;
    
    /** The expression to evaluate and assign to id */
    private NodeExpr expr;

    /**
     * Instantiates a new Assignment Node
     *
     * @param id the identifier string
     * @param expr the expression to evaluate and assign to id
     */
    public NodeStmtAssn(int pos, String id, NodeExpr expr) {
    	this.pos = pos;
    	this.id=id;
    	this.expr=expr;
    	if(debug) {
    		System.out.println("Created assn[" + this.id + "]\n");
    	}
    }

    /* (non-Javadoc)
     * @see Node#eval(Environment)
     */
    public double eval(Environment env) throws EvalException {
    	env.put(pos,id,expr.eval(env));
    	return 1;
    }

}
