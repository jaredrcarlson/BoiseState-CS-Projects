
public class NodeRelop extends Node {
	
	private String relop;
	private Double d1, d2;

    public NodeRelop(int pos, String relop) {
    	this.pos=pos;
    	this.relop=relop;
    	if(debug) {
    		System.out.println("Created relop[" + this.pos + "," + this.relop + "]\n");
    	}
    }

    public double op(double o1, double o2) throws EvalException {
    	d1 = o1;
    	d2 = o2;
    	boolean resultIsTrue = false;
    	if(relop.equals("<")) {
    		if(Double.compare(d1,d2) < 0) {
    			resultIsTrue = true;
    		}
    	}
	    else if(relop.equals("<=")) {
	    	if(Double.compare(d1,d2) <= 0) {
    			resultIsTrue = true;
    		}
	    }
	    else if(relop.equals(">")) {
	    	if(Double.compare(d1,d2) > 0) {
    			resultIsTrue = true;
    		}
	    }
	    else if(relop.equals(">=")) {
	    	if(Double.compare(d1,d2) >= 0) {
    			resultIsTrue = true;
    		}
	    }
	    else if(relop.equals("<>")) {
	    	if(Double.compare(d1,d2) != 0) {
    			resultIsTrue = true;
    		}
	    }
	    else if(relop.equals("==")) {
	    	if(Double.compare(d1,d2) == 0) {
    			resultIsTrue = true;
    		}
	    }
	    else {
	    	throw new EvalException(pos,"bogus relop: "+relop);
	    }
		if(resultIsTrue) {
			return 1;
		}
		else {
			return -1;
		}
    }
}
