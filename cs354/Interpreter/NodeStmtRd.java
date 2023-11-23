import java.util.Scanner;

public class NodeStmtRd extends NodeStmt {
	
	private String id;
	private java.util.Scanner sysIn;
	
	public NodeStmtRd(int pos, String id, Scanner sysIn) {
		this.pos = pos;
		this.id = id;
		this.sysIn = sysIn;
		if(debug) {
    		System.out.println("Created rd[" + this.pos + "," + this.id + "]\n");
    	}
	}
	
	public double eval(Environment env) throws EvalException {
		//System.out.print("Enter a Floating-Point value: ");
		double val = sysIn.nextDouble();
		env.put(pos,id,val);
		return 1;
	}
}
