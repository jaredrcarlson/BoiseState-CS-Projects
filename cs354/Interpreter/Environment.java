import java.util.HashMap;

/**
 * The Class Environment - Makes use of a VariableStorage object
 * in order to store and retrieve environment variables for future processing.
 * 
 */
public class Environment {
	
	/** The object used for variable storage */
	private static VariableStorage storage;
	
	/**
	 * Instantiates a new environment.
	 */
	public Environment() {
		storage = new VariableStorage();
	}
	
    /**
     * Stores or Updates a variable in storage
     * and returns the variable's value
     *
     * @param pos the variable's position within the program string
     * @param var the variable's name (identifier)
     * @param val the varaible's value
     * @return the variable's value
     */
    public void put(int pos, String var, double val) {
    	storage.put(var,val);
    }
    
    /**
     * Retrieves the value of a variable in storage or throws an
     * EvalException if the variable is not found in storage.
     *
     * @param pos the variable's position within the program string
     * @param var the variable's name (identifier)
     * @return the variable's value
     * @throws EvalException if the variable cannot be evaluated
     */
    public double get(int pos, String var) throws EvalException {
    	if(storage.contains(var)) {
    		return storage.get(var);
    	}
    	else {
    		storage.put(var,0);
    		return 0;
    	}
    }
    
    // Private class for variable --> value storage
    /**
     * The Class VariableStorage
     * Makes use of a key(String) --> value(Double) map to store/update
     * variable identifiers and their associated values.
     * 
     * @author jcarlson
     * 
     */
    private class VariableStorage {
    	
    	/** The key --> value map */
    	private HashMap<String, Double> keyValueMap;
    	
    	/**
    	 * Instantiates a new variable storage.
    	 */
    	public VariableStorage() {
    		keyValueMap = new HashMap<String, Double>();
    	}
    	
    	/**
    	 * Stores/Updates a variable and value
    	 *
    	 * @param var the variable
    	 * @param val the value
    	 */
    	public double put(String key, double val) {
    		keyValueMap.put(key,val);
    		return 1;
    	}
    	
    	/**
    	 * Fetches a variable's value
    	 *
    	 * @param var the variable
    	 * @return the value
    	 */
    	public double get(String var) {
    		return keyValueMap.get(var);
    	}
    	
    	/**
    	 * Checks to see if a variable is exists in this storage
    	 *
    	 * @param var the variable
    	 * @return true, if the variable is found in this storage
    	 */
    	public boolean contains(String var) {
    		return keyValueMap.containsKey(var);
    	}
    	
    	
    }
}
