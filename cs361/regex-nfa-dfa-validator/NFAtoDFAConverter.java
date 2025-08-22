import java.util.TreeMap;

/** 
 * Converts an NFA State Machine to DFA State Machine
 * 
 * @author jcarlson <jaredcarlson@u.boisestate.edu>
 *
 */
public class NFAtoDFAConverter extends Debugable {
	private static TreeMap<String,FAState> transTable = new TreeMap<String,FAState>();
	private static TreeMap<Integer,FAState> transTableSorted = new TreeMap<Integer,FAState>();
	private static Integer nextStateID = 0;
	private static FAState trap = new FAState("DNE");
	private static NFAStateMachine nfaSM;
	private static FAState nfaStartState, dfaStartState, nextToProcess;
	private static boolean newNextOnA, newNextOnB;
	
	
	public static DFAStateMachine convert(NFAStateMachine nfaStateMachine) {
		// Build Transition Table
		nfaSM = nfaStateMachine;
		nfaStartState = nfaSM.getStartState();
		dfaStartState = new FAState(nextStateID++,nfaStartState.getClosure());
		dfaStartState.makeStart();
		transTable.put(dfaStartState.getName(),dfaStartState);
		writeMnr("Added to Transition Table - Start State: " + dfaStartState.toShortString());
		
		while(haveMoreToProcess()) {process(nextToProcess);}
		
		writeMnr("\nAll New States have been processed.");
		
		for(FAState state : transTable.values()) {
			transTableSorted.put(state.getID(),state);
		}
		
		return new DFAStateMachine(transTableSorted,nfaSM.acceptsEmptyString());
	}
	
	private static String buildName(TreeMap<Integer,FAState> cStates) {
		StringBuilder strBld = new StringBuilder("");
		String prefix = "";
		for(FAState eState : cStates.values()) {
			strBld.append(prefix + eState.getID());
			prefix = ",";
		}
		return strBld.toString();
	}
	
	private static boolean haveMoreToProcess() {
		for(FAState newState : transTable.values()) {
			if(!newState.processed) {
				nextToProcess = newState;
				return true;
			}
		}
		return false;
	}
	
	private static void process(FAState currState) {
		TreeMap<Integer,FAState> nsOnA_Closure, nsOnB_Closure;
		FAState nextOnA, nextOnB;
		String nameOfNextOnA, nameOfNextOnB;
		String currName = currState.getName();
		newNextOnA = newNextOnB = false;
		
		if(containsFinalState(currState)) {currState.makeFinal();}
		buildNextStates(currState);
		
		writeMnr("\nProcessing: " + currState.toShortString());
		
		nameOfNextOnA = buildName(currState.nextStatesOnA);
		writeMnr("Name of Next State on 'a': {" + nameOfNextOnA + "}");
		
		nameOfNextOnB = buildName(currState.nextStatesOnB);
		writeMnr("Name of Next State on 'b': {" + nameOfNextOnB + "}");
		
		if(currState.nextStatesOnA.size() == 0) {
			nextOnA = trap;
			writeMnr(currState.toBasicString() + " --> 'a' --> {TRAP}");
		}
		else {
			if(nameOfNextOnA.equals(currName)) {
				nextOnA = currState;
				writeMnr(currState.toBasicString() + " --> 'a' --> " + currState.toShortString() + " (Loop)");
			}
			else if(transTable.containsKey(nameOfNextOnA)) {
				nextOnA = transTable.get(nameOfNextOnA);
				writeMnr(currState.toBasicString() + " --> 'a' --> " + nextOnA.toShortString() + " (Existing State)");
			}
			else {
				newNextOnA = true;
				nsOnA_Closure = new TreeMap<Integer,FAState>();
				for(FAState nsOnA : currState.nextStatesOnA.values()) {
					nsOnA_Closure.putAll(nfaSM.getStates().get(nsOnA.getID()).getClosure());
				}
				nextOnA = new FAState(nextStateID++,nsOnA_Closure);
				writeMnr(currState.toBasicString() + " --> 'a' --> " + nextOnA.toShortString() + " (New State)");
			}
		}
		currState.setNextOnA(nextOnA);
		
		if(currState.nextStatesOnB.size() == 0) {
			nextOnB = trap;
			writeMnr(currState.toBasicString() + " --> 'b' --> {TRAP}");
		}
		else {
			if(nameOfNextOnB.equals(currName)) {
				nextOnB = currState;
				writeMnr(currState.toBasicString() + " --> 'b' --> " + currState.toShortString() + " (Loop)");
			}
			else if(transTable.containsKey(nameOfNextOnB)) {
				nextOnB = transTable.get(nameOfNextOnB);
				writeMnr(currState.toBasicString() + " --> 'b' --> " + nextOnB.toShortString() + " (Existing State)");
			}
			else {
				newNextOnB = true;
				nsOnB_Closure = new TreeMap<Integer,FAState>();
				for(FAState nsOnB : currState.nextStatesOnB.values()) {
					nsOnB_Closure.putAll(nfaSM.getStates().get(nsOnB.getID()).getClosure());
				}
				nextOnB = new FAState(nextStateID++,nsOnB_Closure);
				writeMnr(currState.toBasicString() + " --> 'b' --> " + nextOnB.toShortString() + " (New State)");
			}
		}
		currState.setNextOnB(nextOnB);
		
		currState.processed = true;
		if(newNextOnA) {
			transTable.put(nextOnA.getName(),nextOnA);
			writeMnr("Added to Transition Table - New State: " + nextOnA.toShortString());
		}
		if(newNextOnB) {
			transTable.put(nextOnB.getName(),nextOnB);
			writeMnr("Added to Transition Table - New State: " + nextOnB.toShortString());
		}
		
		StringBuilder strBld = new StringBuilder("\n------<| Transition Table |>------\n");
		for(FAState state : transTable.values()) {
			strBld.append(state.toShortString() + ((state.processed)? " (Processed)" : " (Not Processed)") + "\n");
		}
		writeMnr(strBld.toString());
	}
	
	private static boolean containsFinalState(FAState newState) {
		for(FAState cState : newState.closure.values()) {
			if(cState.isFinalState()) {
				return true;
			}
		}
		return false;
	}
	
	private static void buildNextStates(FAState origin) {
		Integer cStateID, nsOnAID, nsOnBID;
		for(FAState cState : origin.closure.values()) {
			cStateID = cState.getID();
			FAState cState_ns = nfaSM.getStates().get(cStateID);
			if(cState_ns.hasNextOnA()) {
				nsOnAID = cState_ns.getNextOnA().getID();
				origin.nextStatesOnA.putAll(nfaSM.getStates().get(nsOnAID).getClosure());
			}
			if(cState_ns.hasNextOnB()) {
				nsOnBID = cState_ns.getNextOnB().getID();
				origin.nextStatesOnB.putAll(nfaSM.getStates().get(nsOnBID).getClosure());
			}
		}
	}
}
