import java.util.TreeMap;

/**
 * Represents an NFA State
 *  
 * @author jcarlson <jaredcarlson@u.boisestate.edu>
 *
 */
public class FAState extends Debugable implements Comparable<FAState> {
	protected Integer id;
	protected String name, nextPath;
	protected boolean startState, finalState, isTrap;
	protected boolean processed;
	protected FAState nextStateOnA, nextStateOnB;
	protected TreeMap<Integer,FAState> nextStatesOnA, nextStatesOnB, closure, inOnE, inOnA, inOnB;
	
	public FAState(Integer id) { this(id,"" + id); }
	public FAState(Integer id, String name) {
		setID(id); setName(name);
		closure = new TreeMap<Integer,FAState>();
		closure.put(id,this);
		inOnE = new TreeMap<Integer,FAState>();
		inOnA = new TreeMap<Integer,FAState>();
		inOnB = new TreeMap<Integer,FAState>();
		isTrap = false;
		processed = false;
		nextStatesOnA = new TreeMap<Integer,FAState>();
		nextStatesOnB = new TreeMap<Integer,FAState>();
	}
	
	public FAState(Integer id, TreeMap<Integer,FAState> cStates) {
		setID(id);
		closure = cStates;
		name = buildName();
		finalState = containsFinal(closure);
		isTrap = false;
		processed = false;
		nextStatesOnA = new TreeMap<Integer,FAState>();
		nextStatesOnB = new TreeMap<Integer,FAState>();
	}
	
	public FAState(Integer id, FAState state) {
		setID(id);
		closure = state.closure;
		name = buildName();
		nextPath = state.nextPath;
		startState = state.startState;
		finalState = state.finalState;
		isTrap = false;
		processed = false;
		nextStateOnA = state.nextStateOnA;
		nextStateOnB = state.nextStateOnB;
		nextStatesOnA = state.nextStatesOnA;
		if(nextStatesOnA == null) {nextStatesOnA = new TreeMap<Integer,FAState>();}
		nextStatesOnB = state.nextStatesOnB;
		if(nextStatesOnB == null) {nextStatesOnB = new TreeMap<Integer,FAState>();}
		inOnE = state.inOnE;
		inOnA = state.inOnA;
		inOnB = state.inOnB;
	}
	
	private String buildName() {
		StringBuilder strBld = new StringBuilder();
		String prefix = "";
		for(FAState cState : closure.values()) {
			strBld.append(prefix + cState.getID());
			prefix = ",";
		}
		return strBld.toString();
	}
	
	public FAState(String trapName) {id = 666; name = trapName; startState = false; finalState = false; isTrap = true; processed = true;}
	
	public void makeStart() {startState = true;}
	public void makeFinal() {finalState = true;}
	public void setID(int id) {this.id = id;}
	public void setName(String name) {this.name = name;}
	
	public boolean isStartState() {return startState;}
	public boolean isFinalState() {return finalState;}
	public Integer getID() {return id;}
	public String getName() {return name;}
		
	public boolean hasNextOn(RESymb symb) { switch(symb.gCode) {
		case SYMB_A: return hasNextOnA();
		case SYMB_B: return hasNextOnB();
		case SYMB_E: return hasNextOnE(); default: return false; }
	}
	public boolean hasNextOnA() {return nextStateOnA != null;}
	public boolean hasNextOnB() {return nextStateOnB != null;}
	public boolean hasNextOnE() {return closure.size() > 0;}
	
	public TreeMap<Integer,FAState> getNextOn(RESymb symb) {
		TreeMap<Integer,FAState> nextOnSymb = new TreeMap<Integer,FAState>();
		switch(symb.gCode) {
		case SYMB_A: nextOnSymb.put(nextStateOnA.getID(),nextStateOnA); break;
		case SYMB_B: nextOnSymb.put(nextStateOnB.getID(),nextStateOnB); break;
		case SYMB_E: nextOnSymb = getClosure();	break; default: break; }
		return nextOnSymb;
	}
	public FAState getNextStateOn(RESymb symb) {
		switch(symb.gCode) {
		case SYMB_A: return nextStateOnA;
		case SYMB_B: return nextStateOnB;
		default: return null;}
	}
	public FAState getNextOnA() {return nextStateOnA;}
	public FAState getNextOnB() {return nextStateOnB;}
	public TreeMap<Integer,FAState> getClosure() {return closure;}
	
	public void addNextOn(RESymb symb, FAState nextState) { switch(symb.gCode) {
		case SYMB_A: setNextOnA(nextState); break;
		case SYMB_B: setNextOnB(nextState); break;
		case SYMB_E: addNextOnE(nextState); break; default: }
	}
	public void setNextOnA(FAState nextOnA) {nextStateOnA = nextOnA;}
	public void setNextOnB(FAState nextOnB) {nextStateOnB = nextOnB;}
	public void addNextOnE(FAState nextOnE) {closure.putIfAbsent(nextOnE.getID(),nextOnE);
        									  nextOnE.addInOnE(this);
    }
	
	public TreeMap<Integer,FAState> getNextStatesOnA() {return nextStatesOnA;}
	public TreeMap<Integer,FAState> getNextStatesOnB() {return nextStatesOnB;}
	
	public void addInOnE(FAState inState) {
		inOnE.putIfAbsent(inState.getID(),inState);
		inState.updateNextOnE(closure);
	}
	
	public void updateNextOnE(TreeMap<Integer,FAState> newNextOnE) {
		for(FAState stateOnE : newNextOnE.values()) {
			closure.putIfAbsent(stateOnE.getID(),stateOnE);
		}
		for(FAState inStateOnE : inOnE.values()) {
			inStateOnE.updateNextOnE(closure);
		}
	}
	
	public void removeAllNextOnE() {
		closure.clear();
	}
	
	private boolean containsFinal(TreeMap<Integer,FAState> cStates) {
		boolean containsFinalState = false;
		for(FAState cState : cStates.values()) {
			if(cState.isFinalState()){
				containsFinalState = true; break;
			}
		}
		return containsFinalState;
	}
	
	public void setNextPath(String path) {nextPath = path;}
	public String getNextPath() {return nextPath;}
	
	@Override
	public int compareTo(FAState other) {
		if(this.id < other.id) {return -1;}
		else if(this.id > other.id) {return 1;}
		else {return 0;}
	}
	public boolean sameID(FAState other) {return this.compareTo(other) == 0;}
	public boolean sameName(FAState other) {return name.equals(other.getName());}
	
	public String toBasicString() {return "[" + id + "]";}
	public String toShortString() {
		StringBuilder strBld = new StringBuilder(toBasicString());
		if(name != null) {strBld.append(" {" + name + "}");}
		if(startState) {strBld.append(" (Start)");}
		if(finalState) {strBld.append(" (Final)");}
		return strBld.toString();
	}
	public String toString() {
		StringBuilder strBld = new StringBuilder(toShortString());
		if(nextPath != null) {strBld.append(" Path Component: " + nextPath);}
		strBld.append("\n" + toBasicString() + " --> 'a' --> " + ((nextStateOnA != null)? nextStateOnA.toShortString() : "[-]"));
		strBld.append("\n" + toBasicString() + " --> 'b' --> " + ((nextStateOnB != null)? nextStateOnB.toShortString() : "[-]"));
		for(FAState cState : closure.values()) {
			if(cState.getID() != this.id) {
				strBld.append("\n" + toBasicString() + " --> 'e' --> " + cState.toBasicString());
			}
		}
		if(closure.size() > 0) {
			strBld.append("\nClosure: {");
			String prefix = "";
			for(FAState cState : closure.values()) {
				strBld.append(prefix + cState.getID());
				prefix = ",";
			}
			strBld.append("}\n");
		}
		return strBld.toString();
	}
	public boolean isTrap() {
		return isTrap;
	}							
}
