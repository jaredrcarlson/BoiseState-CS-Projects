import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * The Class CircuitTracerGraphical displays the shortest paths for a circuit board.
 * Each shortest path can be selected in the right window pane to display the path.
 * 
 * @author jcarlson
 */
@SuppressWarnings("serial")
public class CircuitTracerGraphical extends JPanel implements ActionListener {
	// <Constants>
	/** The tile color. */
	private final Color TILE_COLOR = new Color(240,240,240);
	private final Font CB_FONT = new Font("Sans", Font.BOLD, 40);
	
	private JPanel eastPanel;
	private JPanel northPanel;
	private JPanel circuitBoardPanel;
	private CircuitBoard board;
	private ArrayList<JButton> solutions;
	private ArrayList<TraceState> bestPaths;
	private int rows;
	private int cols;
	private boolean adjacentStartEnd;
	private JLabel[][] tiles;
	
	/**
	 * Instantiates a new Circuit Tracer Graphical panel
	 * Initializes core instance variables
	 * Creates and populates the Circuit Board and EAST panel
	 * @param board original circuit board
	 * @param bestPaths list of best path solutions
	 * @param adjacentStartEnd true if start and end points are adjacent (special case handling)
	 * 
	 */
	public CircuitTracerGraphical(CircuitBoard board, ArrayList<TraceState> bestPaths, boolean adjacentStartEnd) {
		// Initialize core instance variables
		this.board = board;
		this.rows = board.numRows();
		this.cols = board.numCols();
		this.bestPaths = new ArrayList<TraceState>();
		this.bestPaths = bestPaths;
		this.adjacentStartEnd = adjacentStartEnd;
		
		this.setLayout(new BorderLayout());
				
		// ------------------ <Create File Menu> --------------------- //
		northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		
		JMenuBar menubar = new JMenuBar();
        
        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");
        file.setMnemonic(KeyEvent.VK_F);
        help.setMnemonic(KeyEvent.VK_H);

        JMenuItem exitOption = new JMenuItem("Exit");
        JMenuItem helpOption = new JMenuItem("About");
        exitOption.setMnemonic(KeyEvent.VK_E);
        helpOption.setMnemonic(KeyEvent.VK_A);
        
        exitOption.setToolTipText("Exit application");
        exitOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        
        helpOption.setToolTipText("App Info");
        helpOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	JOptionPane.showMessageDialog(circuitBoardPanel, "Circuit Tracing Search\n\nWritten by Jared Carlson (jcarlson@u.boisestate.edu)");
            }
        });
        
        file.add(exitOption);
        help.add(helpOption);
        menubar.add(file);
        menubar.add(help);
        
        northPanel.add(menubar);
        this.add(northPanel, BorderLayout.NORTH);
        
		// ------------------ <Create EAST Panel> --------------------- //
		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.PAGE_AXIS));
		Border eastPanelBorder = BorderFactory.createCompoundBorder(new BevelBorder(0), new BevelBorder(1));
		eastPanel.setBorder(eastPanelBorder);
		
		// ------------------ <Populate EAST Panel> ------------------- //
		// Create Original Circuit Board Button
		solutions = new ArrayList<JButton>();
		solutions.add(new JButton("Circuit Board"));
		solutions.get(0).addActionListener(this);
		eastPanel.add(solutions.get(0));
		
		// Create Solution Buttons
		for(int i = 1; i <= bestPaths.size(); i++) {
			solutions.add(new JButton("Solution " + (i))); // Button name from Solution #
			solutions.get(i).addActionListener(this);
			eastPanel.add(solutions.get(i));
		}
		this.add(eastPanel, BorderLayout.EAST);
		
		// Display Circuit Board
		displayBoardTiles(this.board);
    }
	
	/**
	 * Displays the circuit board tiles.
	 * @board circuit board to display
	 */
	private void displayBoardTiles(CircuitBoard board) {
		// Create and Populate tiles
		tiles = new JLabel[rows][cols];
		for(int row=0; row < rows; row++) {
			for(int col=0; col < cols; col++) {
				char tempChar = board.charAt(row,col);
				tiles[row][col] = new JLabel(Character.toString(tempChar),SwingConstants.CENTER);
				tiles[row][col].setBorder(new BevelBorder(BevelBorder.RAISED));
				tiles[row][col].setFont(CB_FONT);
				tiles[row][col].setBackground(TILE_COLOR);
			}
		}
		updateThisPanel();
	}
	
	/**
	 * Displays the solution board tiles.
	 * @board solution board to display
	 */
	private void displaySolutionBoardTiles(CircuitBoard board) {
		// Create and Populate tiles
		tiles = new JLabel[rows][cols];
	    
		for(int row=0; row < rows; row++) {
			for(int col=0; col < cols; col++) {
				char tempChar = board.charAt(row,col);
				tiles[row][col] = new JLabel(Character.toString(tempChar),SwingConstants.CENTER);
				tiles[row][col].setBorder(new BevelBorder(BevelBorder.RAISED));
				tiles[row][col].setFont(CB_FONT);
				tiles[row][col].setBackground(TILE_COLOR);
				if( (tempChar == 'T') || (adjacentStartEnd && (tempChar == '1' || tempChar == '2') ) ) {
					tiles[row][col].setForeground(Color.red);
				}
			}
		}
		updateThisPanel();
	}
	
	/**
	 * Updates this Panel.
	 */
	private void updateThisPanel() {
		updateCircuitBoardPanel();
		this.revalidate(); // Causes this Panel to refresh itself with new contents
	}
	
	/**
	 * Updates circuit board panel.
	 */
	private void updateCircuitBoardPanel() {
		// Remove panel if not empty
		if(circuitBoardPanel != null) { 
			this.remove(circuitBoardPanel);
		}
		// Create new panel
		circuitBoardPanel = new JPanel(new GridLayout(rows, cols));
		Border circuitBoardPanelBorder = BorderFactory.createCompoundBorder(new BevelBorder(0), new BevelBorder(1));
		circuitBoardPanel.setBorder(circuitBoardPanelBorder);
		Dimension preferredSize = new Dimension(rows * 100, cols * 80);
		circuitBoardPanel.setPreferredSize(preferredSize);
		
		// Populate panel with tiles
		for(int row=0; row < rows; row++) {
			for(int col=0; col < cols; col++) {
				circuitBoardPanel.add(tiles[row][col]);
			}
		}
		this.add(circuitBoardPanel, BorderLayout.CENTER);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * Listens for button action events and responds to each possible source button appropriately
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton sourceButton = (JButton) e.getSource(); // Get source button
		String buttonName = sourceButton.getText(); // Get name of selected Solution Button
		if(buttonName.equals("Circuit Board")) {
			displayBoardTiles(board);
		}
		else {
			// Parse Solution JButton name to use as bestPaths index
			Scanner indexScan = new Scanner(buttonName);
			indexScan.next();
			int i = Integer.parseInt(indexScan.next()) - 1; // Subtract 1 since Circuit Board Button is index 0 
			indexScan.close();
			
			// Update Circuit Board with selected Solution
			displaySolutionBoardTiles(bestPaths.get(i).getBoard());
		}
		updateThisPanel();
	}
}
