public class NodeMulop extends Node {

    private String mulop;

    public NodeMulop(int pos, String mulop) {
    	this.pos=pos;
    	this.mulop=mulop;
    	if(debug) {
    		System.out.println("Created mulop[" + this.pos + "," + this.mulop + "]\n");
    	}
    }

    public double op(double o1, double o2) throws EvalException {
	if (mulop.equals("*"))
	    return o1*o2;
	if (mulop.equals("/"))
	    return o1/o2;
	throw new EvalException(pos,"bogus mulop: "+mulop);
    }

}
