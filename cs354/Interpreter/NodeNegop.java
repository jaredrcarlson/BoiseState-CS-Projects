
/**
 * The Class NodeNegop
 * Represents a Negation Operator
 * 
 * @author jcarlson
 */
public class NodeNegop extends Node {
	
	private String negop;

    /**
     * Instantiates a new Negation Operator Node
     *
     * @param pos the operator's position within the program string
     * @param negop the negation operator
     */
    public NodeNegop(int pos, String negop) {
	this.pos=pos;
	this.negop=negop;
    }

    /**
     * Performs the negation operation (unary minus) on a number
     *
     * @param num the number to negate
     * @return the value of the number after negation
     * @throws EvalException if the negation operator symbol is not detected
     */
    public double op(double num) throws EvalException {
	    if(negop.equals("-")) {
	    	return num * -1;
	    }
	    else {
	    	throw new EvalException(pos,"bogus negop: "+negop);
    
	    }
    }
}
