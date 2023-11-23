import java.util.TreeMap;

/**
 * Represents a Deterministic Finite Automata State Machine
 * 
 * @author jcarlson <jaredcarlson@u.boisestate.edu>
 * 
 */
public class DFAStateMachine extends Debugable {
	private boolean acceptsEmptyString;
	private TreeMap<Integer,FAState> states;
	private FAState startState;
	
	public DFAStateMachine(TreeMap<Integer,FAState> transTable, boolean acceptsEmptyString) {
		states = transTable;
		startState = states.firstEntry().getValue();
		this.acceptsEmptyString = acceptsEmptyString;
		removeAllNextOnE();
	}
	
	public FAState getStartState() {
		return startState;
	}
	
	public boolean acceptsEmptyString() {
		return acceptsEmptyString;
	}
	
	private void removeAllNextOnE() {
		for(FAState state : states.values()) {
			state.removeAllNextOnE();
		}
	}
	
	public String toString() {
		StringBuilder strBld = new StringBuilder();
		for(FAState state : states.values()) {
			strBld.append("\n" + state.toString() + "\n");
		}
		return strBld.toString();
	}
}
