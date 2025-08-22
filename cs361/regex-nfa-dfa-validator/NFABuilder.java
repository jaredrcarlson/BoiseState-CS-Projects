import java.util.ArrayList;

/**
 * Builds an NFA State Machine from Regular Expression "Path Components"
 * Steps through each PC object and builds groups of NFA States based on
 * the the object class type
 * 
 * @author jcarlson <jaredcarlson@u.boisestate.edu>
 */
public class NFABuilder extends Debugable {
	private static int nextStateID = 0;
	private static ArrayList<FAState> states = new ArrayList<FAState>();
	private static boolean machineAcceptsEmptyString;
	
	public static NFAStateMachine build(ArrayList<REPathComponent> pathComponents) {
		
		debugIndentReset();
		debugStepIn();
		writeBld("[BuildNFA] Building from Path Components...");
		FAState startState = newState(nextStateID);
		writeBld("[BuildNFA] Marking Start State");
		startState.makeStart();
		addState(startState);
		FAState beginState = startState;
		FAState endState = null;
		REPathComponent currComp;
		for(int i=0; i < pathComponents.size(); i++) {
			currComp = pathComponents.get(i);
			writeBld("[BuildNFA] Building Path Component --> " + currComp.toShortString());
			newSB();
			appSB(currComp.getExpr().toShortString());
			beginState.setNextPath(getSB());
			endState = buildPC(beginState,currComp,i);
			beginState = endState;
			if(i == (pathComponents.size() - 1)) {
				writeBld("[BuildNFA] Marking Final State");
				endState.makeFinal();
			}
			writeBld("[BuildNFA] Finished Path Component Build");
			addState(endState);
		}
		
		writeBld("[BuildNFA] Finished Build");
		debugBackOut();
		machineAcceptsEmptyString = machineAcceptsEmptyString(pathComponents);
		return new NFAStateMachine(startState,endState,states,machineAcceptsEmptyString);	
	}
	
	// Build Path Component
	private static FAState buildPC(FAState beginState, REPathComponent component, int componentID) {
		debugStepIn();
		REExpr expr = component.getExpr();
		int endStateID = beginState.getID() + expr.numStatesRequired() + 1;
		FAState endState = newState(endStateID);
		writeBld("[buildPC] Building Path Component (" + componentID + ") --> " + component.toShortString());
		writeBld("[buildPC] beginState = [" + ((beginState == null) ? "NULL" : beginState.getID()) + "] endState = [" + ((endState == null) ? "NULL" : endState.getID()) + "] nextStateID = [" + nextStateID + "]");
		buildExpr(beginState,endState,expr);
		writeBld("[buildPC] Finished Build");
		debugBackOut();
		return endState;
	}
	
	// Build Expression
	private static void buildExpr(FAState beginState, FAState endState, REExpr expr) {
		debugStepIn();		
		switch(expr.gCode) {
		case EXPR_SINGLE:
			writeBld("[buildExpr] Building EXPR_SINGLE --> " + expr.toShortString());
			writeBld("[buildExpr] beginState = [" + ((beginState == null) ? "NULL" : beginState.getID()) + "] endState = [" + ((endState == null) ? "NULL" : endState.getID()) + "] nextStateID = [" + nextStateID + "]");
			buildTerm(beginState,endState,expr.getTerm());
			break;
		case EXPR_COMPOUND:
			writeBld("[buildExpr] Building EXPR_COMPOUND --> " + expr.toShortString());
			writeBld("[buildExpr] beginState = [" + ((beginState == null) ? "NULL" : beginState.getID()) + "] endState = [" + ((endState == null) ? "NULL" : endState.getID()) + "] nextStateID = [" + nextStateID + "]");
			FAState branchBeginState, branchEndState;
			REExpr currExpr = expr;
			int branchID = 0;
			String branchName;
			while(currExpr != null) {
				if(!currExpr.getTerm().isEmptyString()) {
					branchID++;
					branchName = "Branch " + branchID;
					debugStepIn();
					branchBeginState = newState(nextStateID);
					writeBld("[" + branchName + "] --> " + currExpr.getTerm().toShortString());
					writeBld("[" + branchName + "] Begins in State: [" + branchBeginState.getID() + "]");
					buildTransition("[buildExpr]",beginState,SYMB_E,branchBeginState);
					addState(branchBeginState);
					int branchEndStateID = branchBeginState.getID() + currExpr.getTerm().numStatesRequired() + 1;
					branchEndState = newState(branchEndStateID);
					buildTerm(branchBeginState,branchEndState,currExpr.getTerm());
					buildTransition("[buildExpr]",branchEndState,SYMB_E,endState);
					addState(branchEndState);
					currExpr = currExpr.getExpr();
					writeBld("[" + branchName + "] Ends in State: [" + branchEndState.getID() + "]");
					writeBld("[" + branchName + "] Finished Build");
				}
				else {
					writeBld("[buildExpr] Expr accepts the Empty String");
					writeBld("[buildExpr] Creating Transition on Empty");
					buildTransition("[buildExpr]",beginState,SYMB_E,endState);
					currExpr = currExpr.getExpr();
				}
				debugBackOut();
			}
			break;
		default:
		}
		writeBld("[buildExpr] Finished Build");
		debugBackOut();
	}
	
	// Build Term or Term '*'
	private static void buildTerm(FAState beginState, FAState endState, RETerm term) {
		debugStepIn();
		writeBld("[buildTerm] Building Term --> " + term.toShortString());
		writeBld("[buildTerm] beginState = [" + ((beginState == null) ? "NULL" : beginState.getID()) + "] numStatesRequired [" + term.numStatesRequired() + "] endState = [" + ((endState == null) ? "NULL" : endState.getID()) + "] nextStateID = [" + nextStateID + "]");
		FAState termBeginState, termEndState;
		if(term.astk) {
			writeBld("[buildTerm] Has form: <Term> '*' (Zero or More)");
			writeBld("[buildTerm] Creating intermediate Beginning State for Term");
			termBeginState = newState(nextStateID);
			buildTransition("[buildTerm]",beginState,SYMB_E,termBeginState);
			addState(termBeginState);
			int termEndStateID = endState.getID() - 1;
			writeBld("[buildTerm] Creating intermediate Ending State for Term");
			termEndState = newState(termEndStateID);
		}
		else {
			termBeginState = beginState;
			termEndState = endState;
		}
		switch(term.gCode) {
			case TERM_SYMBOL:
				writeBld("[buildTerm] Building TERM_SYMBOL --> " + term.toShortString());
				buildSymb(termBeginState,termEndState,term.getSymb());
				break;
			case TERM_GROUP:
				writeBld("[buildTerm] Building TERM_GROUP --> " + term.toShortString());
				buildGroup(termBeginState,termEndState,term.getGroup());
				break;
			default:
				break;
		}
		if(term.astk) {
			buildTransition("[buildTerm]",termEndState,SYMB_E,endState);
			addState(termEndState);
			writeBld("[buildTerm] Creating Transition for Case: Zero");
			buildTransition("[buildTerm]",beginState,SYMB_E,endState);
			writeBld("[buildTerm] Creating Transition for Case: More");
			buildTransition("[buildTerm]",termEndState,SYMB_E,termBeginState);
			
		}
		/*else if(term.acceptsEmptyString()) {
			writeBld("[buildTerm] Term accepts the Empty String");
			writeBld("[buildTerm] Creating Transition on Empty");
			buildTransition("[buildTerm]",beginState,SYMB_E,endState);
		}*/
		writeBld("[buildTerm] Finished Build");
		debugBackOut();
	}
	
	// <Symb>
	private static void buildSymb(FAState beginState, FAState endState, RESymb symb) {
		debugStepIn();
		writeBld("[buildSymb] Building Symbol --> " + symb.toShortString());
		writeBld("[buildSymb] beginState = [" + ((beginState == null) ? "NULL" : beginState.getID()) + "] numStatesRequired [" + symb.numStatesRequired() + "] endState = [" + ((endState == null) ? "NULL" : endState.getID()) + "] nextStateID = [" + nextStateID + "]");
		buildTransition("[buildSymb]",beginState,symb,endState);
		writeBld("[buildSymb] Finished Build");
		debugBackOut();
	}
	
	// <Group>
	private static void buildGroup(FAState beginState, FAState endState, REGroup group) {
		debugStepIn();
		writeBld("[buildGroup] beginState = [" + ((beginState == null) ? "NULL" : beginState.getID()) + "] numStatesRequired [" + group.numStatesRequired() + "] endState = [" + ((endState == null) ? "NULL" : endState.getID()) + "] nextStateID = [" + nextStateID + "]");
		switch(group.gCode) {
			case GROUP_MULT_SYMBOL:
				writeBld("[buildGroup] Building GROUP_MULT_SYMBOL --> " + group.toShortString());
				buildSymbMult(beginState,endState,group.getSymbMult());
				break;
			case GROUP_SINGLE_EXPR:
				writeBld("[buildGroup] Building GROUP_SINGLE_EXPR --> " + group.toShortString());
				buildExpr(beginState,endState,group.getExpr());
				break;
			case GROUP_MULT_EXPR:
				writeBld("[buildGroup] Building GROUP_MULT_EXPR --> " + group.toShortString());
				buildExprMult(beginState,endState,group.getExprMult());
				break;
			default:	
		}
		writeBld("[buildGroup] Finished Build");
		debugBackOut();
	}
	
	// <SymbMult>
	private static void buildSymbMult(FAState beginState, FAState endState, RESymbMult symbMult) {
		debugStepIn();
		writeBld("[buildSymbMult] Building --> " + symbMult.toShortString());
		ArrayList<RESymb> symbols = symbMult.getSymbols();
		FAState intermediateState;
		for(int i=0; i < symbols.size(); i++) {
			intermediateState = newState(nextStateID);
			buildSymb(beginState,intermediateState,symbols.get(i));
			if(i < symbols.size() - 1) {
				addState(intermediateState);
			}
			beginState = intermediateState;
		}
		writeBld("[buildSymbMult] Finished Build");
		debugBackOut();
	}
	
	// <ExprMult>
	private static void buildExprMult(FAState beginState, FAState endState, REExprMult exprMult) {
		debugStepIn();
		writeBld("[buildExprMult] Building --> " + exprMult.toShortString());
		ArrayList<REExpr> expressions = exprMult.getExpressions();
		writeBld("[buildExprMult] Expression Chain Begins in State: [" + beginState.getID() + "]");
		REExpr currExpr;
		FAState exprBeginState = beginState;
		FAState exprEndState = null;
		boolean lastExpression = false;
		for(int i=0; i < expressions.size(); i++) {
			debugStepIn();
			lastExpression = (i == expressions.size() - 1);
			currExpr = expressions.get(i);
			if(lastExpression) {
				exprEndState = endState;	
			}
			else {
				int exprEndStateID = exprBeginState.getID() + currExpr.numStatesRequired() + 1;
				exprEndState = newState(exprEndStateID);
			}
			buildExpr(exprBeginState,exprEndState,currExpr);
			if(!lastExpression) {
				addState(exprEndState);
				exprBeginState = exprEndState;
			}
			debugBackOut();
		}
		writeBld("[buildExprMult] Expression Chain Ends in State: [" + endState.getID() + "]");
		writeBld("[buildExprMult] Finished Build");
		debugBackOut();
	}
	
	private static void buildTransition(String origin, FAState source, RESymb symb, FAState destination) {
		newSB(origin);
		appSB(" Building Transition: [" + source.getID() + "] --> '");
		appSB(symb.getCharacter() + "' --> [" + destination.getID() + "]");
		writeBld(getSB());
		source.addNextOn(symb,destination);
	}

	private static FAState newState(int id) {
		FAState state = new FAState(id);
		writeBld("Created: " + state.toShortString());
		return state;
	}
	
	private static void addState(FAState state) {
		states.add(state);
		nextStateID++;
		writeBld("Added: " + state.toShortString());
	}
	
	private static boolean machineAcceptsEmptyString(ArrayList<REPathComponent> pathComponents) {
		boolean acceptsEmptyString = true;
		newSB();
		for(int i=0; i < pathComponents.size(); i++) {
			REExpr expr = pathComponents.get(i).getExpr();
			if(expr.acceptsEmptyString()) {
				appSB("\nPath Component: " + expr.toShortString() + " is Optional");
			}
			else {
				appSB("\nPath Component: " + expr.toShortString() + " is NOT Optional");
				acceptsEmptyString = false;
			}
		}
		if(acceptsEmptyString) {
			appSB("\n\nMachine ACCEPTS the EMPTY STRING");
		}
		else {
			appSB("\n\nMachine REJECTS the EMPTY STRING");
		}
		writeMjr("EMPTY STRING",getSB());
		return acceptsEmptyString;
	}
}
