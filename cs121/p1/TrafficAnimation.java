/* 
 * TrafficAnimation.java 
 * CS 121 Project 1: Traffic Animation
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Animates a ghost-driven golf cart driving through the fairway.
 * @author Jared Carlson
 */
@SuppressWarnings("serial")
public class TrafficAnimation extends JPanel {
	//Note: This is where you declare constants and variables that
	//	need to keep their values between calls	to paintComponent().
	//	Any other variables should be declared locally, in the
	//	method where they are used.

	//constant to regulate the frequency of Timer events
	// Note: 100ms is 10 frames per second - you should not need
	// a faster refresh rate than this
	private final int DELAY = 200; //milliseconds
	//anchor coordinate for drawing / animating
	private int x = 0;
	
	//animated wheel coordinates
	private int leftWheelX = 3;
	private int rightWheelX = 14;
	private int bothWheelY = 87;
	private boolean x_Increment = true;
	private boolean y_Decrement = true;
	
	//pixels added to x each time paintComponent() is called
	private int stepSize = 10;
	
	/* This method draws on the applet's Graphics context.
	 * This is where the majority of your work will be.
	 *
	 * (non-Javadoc)
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paintComponent(Graphics canvas) 
	{
		//clears the previous image
		//super.paintComponent(canvas);
		
		//account for changes to window size
		int width = getWidth(); // panel width
		int height = getHeight(); // panel height
		
		//Fill the canvas with the background color
		canvas.setColor(getBackground());
		canvas.fillRect(0, 0, width, height);
    	
		//System.out.println("x: " + x);
		
		//Calculate the new position
		if (x > width) {
			x = -1 * width*30/100;
		}
		else {
		    x = (x + stepSize);
		}
		
		//Set animated wheel coordinates
		if(leftWheelX < 7 && x_Increment) {
			leftWheelX++;
		}
		else if(leftWheelX == 7) {
			leftWheelX--;
			x_Increment = false;
		}
		else if(leftWheelX == 3) {
			leftWheelX++;
			x_Increment = true;
		}
		else {
			leftWheelX--;
		}
		
		rightWheelX = leftWheelX + 11;
		
		if(bothWheelY > 85 && y_Decrement) {
			bothWheelY--;
		}
		else if(bothWheelY == 85) {
			bothWheelY++;
			y_Decrement = false;
		}
		else if(bothWheelY == 89) {
			bothWheelY--;
			y_Decrement = true;
		}
		else {
			bothWheelY++;
		}
		
    	// ---- DRAW SCENE ---- 
		//Ruff Grass
		canvas.setColor(new Color(59,117,14));
		canvas.fillRect(0, height*65/100, width, height); 
		
		//Fairway Grass
		canvas.setColor(new Color(110,232,116));
		canvas.fillRect(0, height*78/100, width, height*17/100);
		
		//Sun
		canvas.setColor(new Color(245,237,17));
		canvas.fillOval(width*80/100, height*10/100, width*15/100, height*18/100);
		
		//Trees
		canvas.setColor(new Color(94,82,47));
		canvas.fillRect(width*10/100, height*50/100, width*4/100, height*20/100); //left trunk
		canvas.fillRect(width*30/100, height*50/100, width*4/100, height*20/100); //right trunk
		
		canvas.setColor(new Color(59,117,14));
		int triangle1_xCoords[] = {width*5/100,width*12/100,width*19/100};
		int triangle1_yCoords[] = {height*50/100,height*34/100,height*50/100};
		canvas.fillPolygon(triangle1_xCoords, triangle1_yCoords, 3); //left leaves 0
		int triangle3_xCoords[] = {width*5/100,width*12/100,width*19/100};
		int triangle3_yCoords[] = {height*42/100,height*26/100,height*42/100};
		canvas.fillPolygon(triangle3_xCoords, triangle3_yCoords, 3); //left leaves 1
		int triangle5_xCoords[] = {width*5/100,width*12/100,width*19/100};
		int triangle5_yCoords[] = {height*34/100,height*18/100,height*34/100};
		canvas.fillPolygon(triangle5_xCoords, triangle5_yCoords, 3); //left leaves 2
		
		int triangle2_xCoords[] = {width*25/100,width*32/100,width*39/100};
		int triangle2_yCoords[] = {height*50/100,height*34/100,height*50/100};
		canvas.fillPolygon(triangle2_xCoords, triangle2_yCoords, 3); //right leaves 0
		int triangle4_xCoords[] = {width*25/100,width*32/100,width*39/100};
		int triangle4_yCoords[] = {height*42/100,height*26/100,height*42/100};
		canvas.fillPolygon(triangle4_xCoords, triangle4_yCoords, 3); //right leaves 1
		int triangle6_xCoords[] = {width*25/100,width*32/100,width*39/100};
		int triangle6_yCoords[] = {height*34/100,height*18/100,height*34/100};
		canvas.fillPolygon(triangle6_xCoords, triangle6_yCoords, 3); //right leaves 2
		
		//Avatar (Scare Crow) -- to keep bird droppings off the golf course
		canvas.setColor(Color.black);
		canvas.fillRect(width*50/100, height*70/100, width*1/100, height*6/100); //left leg
		canvas.fillRect(width*52/100, height*70/100, width*1/100, height*6/100); //right leg
		canvas.fillRect(width*50/100, height*66/100, width*3/100, height*4/100); //torso
		canvas.setColor(Color.red);
		canvas.fillRect(width*50/100, height*60/100, width*3/100, height*6/100); //chest
		canvas.fillRect(width*47/100, height*60/100, width*9/100, height*1/100); //arms
		canvas.setColor(Color.orange);
		canvas.fillOval(width*49/100, height*55/100, width*5/100, height*5/100); //head
		
		//Sand Trap
		canvas.setColor(new Color(250,216,112));
		canvas.fillOval(width*80/100, height*67/100, width*60/100, height*10/100);
		
		//Animated Golf Cart
		canvas.setColor(Color.black);
		canvas.fillRoundRect(x+width*5/100, height*76/100, width*2/100, height*9/100, width*2/100, height*2/100); //backrest
		canvas.fillRoundRect(x+width*5/100, height*80/100, width*5/100, height*5/100, width*2/100, height*4/100); //seat
		
		canvas.setColor(new Color(255,255,254)); //off-white
		canvas.fillRect(x, height*85/100, width*22/100, height*2/100); //base
		canvas.fillRect(x+width*4/100, height*82/100, width*6/100, height*5/100); //base rear
		canvas.fillRect(x+width*4/100, height*78/100, width*1/100, height*5/100); //base back
		canvas.fillRect(x+width*4/100, height*70/100, width*10/100, height*1/100); //roof
		canvas.fillRect(x+width*17/100, height*80/100, width*1/100, height*5/100); //front block 
		int frontEnd_xCoords[] = {x+width*18/100,x+width*18/100,x+width*22/100};
		int frontEnd_yCoords[] = {height*80/100,height*85/100,height*87/100};
		canvas.fillPolygon(frontEnd_xCoords, frontEnd_yCoords, 3); //front end
		
		canvas.setColor(Color.red);
		canvas.fillRect(x+width*1/100, height*75/100, width*2/100, height*10/100); //golf bag
		canvas.setColor(new Color(200,200,200));
		canvas.fillRect(x+width*1/100, height*73/100, width*1/100, height*2/100); //golf club shaft
		canvas.fillOval(x-6, height*72/100, width*3/100, height*1/100); //golf club head
		
		canvas.setColor(Color.black);
		canvas.fillOval(x+width*2/100, height*84/100, width*7/100, height*7/100); //left wheel
		canvas.fillOval(x+width*13/100, height*84/100, width*7/100, height*7/100); //right wheel
		canvas.drawLine(x+width*14/100, height*70/100, x+width*17/100, height*80/100); //front bars
		canvas.drawLine(x+width*14/100, height*71/100, x+width*17/100, height*80/100); //front bars
		canvas.drawLine(x+width*4/100, height*71/100, x+width*5/100, height*78/100); //rear bars
		canvas.drawLine(x+width*4/100, height*71/100, x+width*4/100, height*78/100); //rear bars
		canvas.drawLine(x+width*5/100, height*71/100, x+width*4/100, height*78/100); //rear bars
		canvas.drawLine(x+width*5/100, height*71/100, x+width*5/100, height*78/100); //rear bars
		canvas.fillRect(x, height*85/100, width*1/100, height*2/100); //rear bumper
		canvas.fillRect(x+width*21/100, height*85/100, width*1/100, height*2/100); //front bumper
		
		canvas.setColor(new Color(255,255,254)); // off-white
		canvas.fillOval(x+width*5/100, height*87/100, width*1/100, height*1/100); //left wheel center
		canvas.fillOval(x+width*16/100, height*87/100, width*1/100, height*1/100); //right wheel center
		
		//Wheel Animation
		canvas.setColor(new Color(100,200,200)); // light-blue
		canvas.fillOval(x+width*leftWheelX/100, height*bothWheelY/100, width*1/100, height*1/100); //left wheel rotating dot
		canvas.fillOval(x+width*rightWheelX/100, height*bothWheelY/100, width*1/100, height*1/100); //right wheel rotating dot
		
		//Text (Yard Markers)
		String marker50 = "50 yds";
		String marker100 = "100 yds";
		canvas.setFont(new Font("Serif", Font.BOLD, 14));
		canvas.setColor(new Color(255,255,254));
		canvas.drawString(marker100, width*20/100, height*98/100);
		canvas.drawString(marker50, width*80/100, height*98/100);
		
	}

	/**
	 * Constructor for the display panel initializes
	 * necessary variables. Only called once, when the
	 * program first begins.
	 * This method also sets up a Timer that will call
	 * paint() with frequency specified by the DELAY
	 * constant.
	 */
	public TrafficAnimation() 
	{
		setBackground(new Color(156,204,251));
		//Do not initialize larger than 800x600
		int initWidth = 800;
		int initHeight = 600;
		setPreferredSize(new Dimension(initWidth, initHeight));
		this.setDoubleBuffered(true);
		
		//Start the animation - DO NOT REMOVE
		startAnimation();
	}

	/////////////////////////////////////////////
	// DO NOT MODIFY main() or startAnimation()
	/////////////////////////////////////////////
	
	/**
	 * Starting point for the TrafficAnimation program
	 * @param args unused
	 */
	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("Traffic Animation");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new TrafficAnimation());
		frame.pack();
		frame.setVisible(true);
	}

   /**
    * Create an animation thread that runs periodically
	* DO NOT MODIFY this method!
	*/
    private void startAnimation()
    {
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                repaint();
            }
        };
        new Timer(DELAY, taskPerformer).start();
    }
}
