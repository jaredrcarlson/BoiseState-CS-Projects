public class NodeFactNum extends NodeFact {

    private String num;
    
    public NodeFactNum(String num) {
    	this.num=num;
    	if(debug) {
    		System.out.println("Created num[" + this.num + "]\n");
    	}
    }

    public double eval(Environment env) throws EvalException {
		return Double.parseDouble(num);
    }

}
