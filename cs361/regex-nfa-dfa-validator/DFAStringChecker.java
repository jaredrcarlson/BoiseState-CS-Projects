
/**
 * Checks a series of strings and reports if they are Accepted or Rejected
 * by the DFA State Machine provided to the Constructor
 * 
 * @author jcarlson <jaredcarlson@u.boisestate.edu>
 *  
 */
public class DFAStringChecker extends Debugable {
	private DFAStateMachine stateMachine;
	private FAState startState;
	private FAState currState;
	private FAState nextState;
	private RESymb currSymb;
	private String currString;
	private int pos;
	private int strLen;
	
	public DFAStringChecker(DFAStateMachine stateMachine) {
		setStateMachine(stateMachine);
		startState = this.stateMachine.getStartState();
		pos = 0;
	}

	public boolean accepts(String string) {
		return validate(string);
	}
	
	private boolean validate(String string) {
		currState = startState;
		nextState = null;
		currString = string;
		boolean reachedEndOfString = true;
		pos = 0;
		writeStrChk("\n<| " + currString + " |>");
		if(string.equals("e")) { return stateMachine.acceptsEmptyString(); }
		strLen = string.length();			
		while(pos < strLen) {
			currSymb = new RESymb(string.charAt(pos));
			if(currState.hasNextOn(currSymb)) {
				nextState = currState.getNextStateOn(currSymb);
				writeStrChk(currState.toBasicString() + " : " + currString + indent(pos + strLen - ((currState.isTrap()) ? 1 : 0)) + currState.toBasicString() + " --> " + currSymb.getCharacter() + " --> " + nextState.toBasicString());
				currString = currString.substring(1);
				currState = nextState;
			}
			else {
				if(pos < strLen) {reachedEndOfString = false;} break;
			}
			pos++;
		}
		writeStrChk(currState.toBasicString() + " : " + currString + indent(pos + strLen - ((currState.isTrap()) ? 1 : 0)) + currState.toBasicString() + ((currState.isFinalState()) ? " Final State" : " {" + currState.getName() + "}"));
		return (reachedEndOfString && (currState.isFinalState()) ? true : false);
	}
	
	private void setStateMachine(DFAStateMachine stateMachine) {
		this.stateMachine = stateMachine;
	}
	
	private String indent(int numSpaces) {
		newSB();
		for(int i=0; i < numSpaces; i++) {
			appSB(" ");
		}
		return getSB();
	}
	
	
}
