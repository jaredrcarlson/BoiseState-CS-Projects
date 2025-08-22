import java.util.ArrayList;

/**
 * Represents a Non-Deterministic Finite Automata State Machine
 * 
 * @author jcarlson <jaredcarlson@u.boisestate.edu>
 * 
 */
public class NFAStateMachine extends Debugable {
	private FAState startState, finalState, nextToProcess;
	private ArrayList<FAState> states;
	RESymb currSymb;
	ArrayList<FAState> nextStates;
	private boolean acceptsEmptyString;
	
	public NFAStateMachine(FAState startState, FAState finalState, ArrayList<FAState> states, boolean acceptsEmptyString) {
		setStartState(startState);
		setFinal(finalState);
		this.states = states;
		setAcceptsEmptyString(acceptsEmptyString);
		if(this.acceptsEmptyString) {getStartState().makeFinal();}		
	}
	
	public NFAStateMachine(FAState startState, boolean acceptsEmptyString) {
		this.startState = startState;
		states = new ArrayList<FAState>();
		nextStates = new ArrayList<FAState>();
		setAcceptsEmptyString(acceptsEmptyString);
	}
	
	public FAState getStartState() {
		return startState;
	}
	
	public void setStartState(FAState startState) {
		this.startState = startState;
	}
	
	public FAState addState(FAState state) {
		for(FAState existingState : states) {
			if(existingState.getName().equals(state.getName())) {
				return existingState;
			}
		}
		return state;
	}
	
	public boolean contains(String name) {
		for(FAState existingState : states) {
			if(existingState.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public FAState get(String name) {
		for(FAState existingState : states) {
			if(existingState.getName().equals(name)) {
				return existingState;
			}
		}
		return null;
	}
	
	public boolean hasMoreToProcess() {
		for(FAState state : states) {
			if(!state.processed) {
				nextToProcess = state;
				return true;
			}
		}
		return false;
	}
	
	public FAState nextToProcess() {
		return nextToProcess;
	}
	
	public ArrayList<FAState> getStates() {
		return states;
	}
	
	public void setFinal(FAState finalState) {
		this.finalState = finalState;
	}
	
	public FAState getFinalState() {
		return finalState;
	}
	
	public boolean acceptsEmptyString() {
		return acceptsEmptyString;
	}
	
	public void setAcceptsEmptyString(boolean acceptsEmptyString) {
		this.acceptsEmptyString = acceptsEmptyString;
	}
	
	public void buildClosures(FAState state) {
		
	}
	
	private String writeStates(FAState state) {
		StringBuilder strBld = new StringBuilder();
		for(int i=0; i < states.size(); i++) {
			strBld.append("\n" + states.get(i).toString());
		}
		return strBld.toString();
	}
	
	public String toString() {
		StringBuilder strBld = new StringBuilder();
		strBld.append(writeStates(startState));
		return strBld.toString();
	}
}
