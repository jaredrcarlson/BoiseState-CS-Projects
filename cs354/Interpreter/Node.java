
/**
 * The Class Node
 * Represents a single piece of a Context-Free Grammar
 * Can be extended to support many different productions
 * within a grammar.
 * 
 */
public abstract class Node {
    
	protected static boolean debug = false; 
	
	/** The Node's position within a program string */
    protected int pos=0;
    
    public static void debug(boolean debugMode) {
    	debug = debugMode;
    }
    
    /**
     * This method must be overridden by a sub-class in order to
     * provide the proper evaluation routine for that sub-class.
     * 
     * This method evaluates a Node within the grammar.
     * This method uses an Environment object to store and
     * retrieve values for Nodes in order to support multiple
     * Node programs. 
     *
     * @param env the Environment object to store Node evaluations
     * @return the value of this Node
     * @throws EvalException if this super class method has been reached (Not overridden)
     */
    public double eval(Environment env) throws EvalException {
    	throw new EvalException(pos,"cannot eval() node!");
    }

}
