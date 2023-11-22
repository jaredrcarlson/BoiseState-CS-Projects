import javax.swing.JFrame;

/**
 * The Class MineWalker creates a MineWalkerPanel object and a JFrame to hold and display the object.
 */
public class MineWalker {
	/**
	 * The main method creates a JFrame, adds a MineWalkerPanel to the JFrame, and displays it.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		JFrame mainFrame = new JFrame("Mine-Walker - Watch Your Step!");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().add(new MineWalkerPanel());
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}


