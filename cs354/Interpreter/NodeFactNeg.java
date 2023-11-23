
/**
 * The Class NodeNegFact
 * Represents a Factor Node that is Negated by a
 * unary minus (negation) operator
 * 
 * @author jcarlson
 */
public class NodeFactNeg extends NodeFact {
	
	private NodeNegop negop;
	private NodeFact fact;
	
	/**
	 * Instantiates a new Negated Factor
	 *
	 * @param negop the negation operator
	 * @param fact the factor
	 */
	public NodeFactNeg(NodeNegop negop, NodeFact fact){
		this.negop=negop;
		this.fact=fact;
	}
	
	/* (non-Javadoc)
	 * @see Node#eval(Environment)
	 */
	public double eval(Environment env) throws EvalException {
		return negop.op(fact.eval(env));
	}
}
