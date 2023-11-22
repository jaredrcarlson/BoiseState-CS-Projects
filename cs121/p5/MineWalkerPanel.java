import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



/**
 * The Class MineWalkerPanel is a game that simulates walking through a mine field.
 * The game lets a user start from the lower-right corner on a grid and attempt to
 * walk to the upper-left corner while avoiding hidden mines. Points are awarded for
 * a successful walk. 
 */
public class MineWalkerPanel extends JPanel implements ActionListener, ChangeListener {
	// <Constants>
	/** The default grid dimension. */
	private final int DEFAULT_GRID_DIMENSION = 10;
	
	/** The max grid dimension. */
	private final int MAX_GRID_DIMENSION = 16;
	
	/** The min grid dimension. */
	private final int MIN_GRID_DIMENSION = 4;
	
	/** The default difficulty. */
	private final int DEFAULT_DIFFICULTY = 25;
	
	/** The minimum difficulty. */
	private final int MIN_DIFFICULTY = 5;
	
	/** The maximum difficulty. */
	private final int MAX_DIFFICULTY = 75;
	
	/** The initial score. */
	private final int INITIAL_SCORE = 0;
	
	/** The initial number of lives remaining. */
	private final int INITIAL_LIVES = 5;
	
	/** The number of milliseconds between player marker animation states. */
	private final int BLINK_DELAY = 600;
	
	/** The percentage of free guy tiles. */
	private final double PERCENTAGE_OF_FREE_GUY_TILES = 0.03;
	
	/** The percentage of bonus points tiles. */
	private final double PERCENTAGE_OF_BONUS_POINTS_TILES = 0.10;
	
	/** The bonus points value. */
	private final int BONUS_POINTS_VALUE = 100;
	
	/** The initial game tile color. */
	private final Color INITIAL_TILE_COLOR = new Color(190,190,190);
	
	/** The light color. */
	private final Color LIGHT_COLOR = new Color(250,250,250);
	
	/** The dark color. */
	private final Color DARK_COLOR = new Color(10,10,10);
	
	/** The "0 Mines Nearby" tile color. */
	private final Color MINES_0_COLOR = new Color(80,230,75);
	
	/** The "1 Mine Nearby" tile color. */
	private final Color MINES_1_COLOR = new Color(240,250,10);
	
	/** The "2 Mines Nearby" tile color. */
	private final Color MINES_2_COLOR = new Color(250,190,10);
	
	/** The "3 Mines Nearby" tile color. */
	private final Color MINES_3_COLOR = new Color(240,5,5);
	
	/** The "Exploded Mine" tile color. */
	private final Color MINES_X_COLOR = new Color(10,10,10);
	
	/** The font used for game status components. */
	private final Font GAME_STATUS_FONT = new Font("Serif", Font.BOLD, 30);
	
	/** The game background music. */
	private final AudioClip GAME_MUSIC = Applet.newAudioClip(MineWalkerPanel.class.getResource("sounds/GameMusic.wav"));
	
	/** The victory sound clip. */
	private final AudioClip VICTORY = Applet.newAudioClip(MineWalkerPanel.class.getResource("sounds/Victory.wav"));
	
	/** The game over sound clip. */
	private final AudioClip GAME_OVER = Applet.newAudioClip(MineWalkerPanel.class.getResource("sounds/GameOver.wav"));
	
	/** The free guy sound effect. */
	private final AudioClip FREE_GUY = Applet.newAudioClip(MineWalkerPanel.class.getResource("sounds/FreeGuy.wav"));
	
	/** The bonus points sound effect. */
	private final AudioClip BONUS_POINTS = Applet.newAudioClip(MineWalkerPanel.class.getResource("sounds/BonusPoints.wav"));
	
	/** The mine explosion sound effect. */
	private final AudioClip MINE = Applet.newAudioClip(MineWalkerPanel.class.getResource("sounds/Mine.wav"));
	
	/** The player move sound effect. */
	private final AudioClip MOVE = Applet.newAudioClip(MineWalkerPanel.class.getResource("sounds/Move.wav"));
	
	/** The error sound effect. */
	private final AudioClip ERROR = Applet.newAudioClip(MineWalkerPanel.class.getResource("sounds/Error.wav"));
	
	/** The control buttons click sound effect. */
	private final AudioClip CLICK = Applet.newAudioClip(MineWalkerPanel.class.getResource("sounds/Click.wav"));
	
	/** The victory image. */
	private final JLabel SANDWICH = new JLabel(new ImageIcon("images/Sandwich.png"));
	
	/** The loss image. */
	private final JLabel EFFORT = new JLabel(new ImageIcon("images/Effort.png"));
	
	// <Game Status> instance variables (NORTH Panel)
	/** The north panel. */
	private JPanel northPanel;
	
	/** The game status panel. */
	private JPanel gameStatusPanel;
	
	/** The score. */
	private int score;
	
	/** The score label. */
	private JLabel scoreLabel;
	
	/** The lives remaining. */
	private int livesRemaining;
	
	/** The lives remaining label. */
	private JLabel livesRemainingLabel;
	
	// <Game Control & Settings> instance variables (EAST Panel)
	/** The show/hide safe path button. */
	private JButton showHideSafePath;
	
	/** The show/hide mines button. */
	private JButton showHideMineLocations;
	
	/** The game grid dimension. */
	private int gameGridDimension;
	
	/** The game grid dimension user-definable text field. */
	private JTextField gameGridDimensionTextField;
	
	/** The game difficulty. */
	private int gameDifficulty;
	
	/** The game difficulty selector. */
	private JSlider gameDifficultySelector; 
	
	/** The new/quit game button. */
	private JButton newQuitGameButton;
	
	/** The play/mute music button. */
	private JButton playMuteMusic;
		
	// <Game Grid> instance variables (CENTER Panel)
	/** The game grid panel. */
	private JPanel gameGridPanel;
	
	/** The game tile buttons. */
	private JButton[][] gameTileButtons;
	
	/** The game tile buttons coordinates. */
	private Point[][] gameTileButtonsCoordinates;
	
	// <Game Play> instance variables
	/** The random walk. */
	private RandomWalk randWalk;
	
	/** The safe path Points. */
	private Point[] safePath;
	
	/** The mine locations. */
	private Point[] mineLocations;
	
	/** The free guy locations. */
	private Point[] freeGuyLocations;
	
	/** The bonus points locations. */
	private Point[] bonusPointsLocations;
	
	/** The tiles visited. */
	private ArrayList<JButton> tilesVisited;
	
	/** The path taken by the player. */
	private ArrayList<Point> playerPath;
	
	/** The player's path coordinates */
	private Point[] playerPathCoordinates;
	
	/** True if game is in progress. */
	private boolean gameInProgress;
	
	/** True if player's icon is in "blink" state. */
	private boolean currentTileBlinking;
	
	/** The maximum x coord. */
	private int maxXCoord;
	
	/** The maximum y coord. */
	private int maxYCoord;
	
	/** The starting tile. */
	private Point startTile;
	
	/** The current tile. */
	private Point currentTile;
	
	/** The ending tile. */
	private Point endTile;
	
	/** The adjacent tile north of current tile. */
	private Point northTile;
	
	/** The adjacent tile east of current tile. */
	private Point eastTile;
	
	/** The adjacent tile south of current tile. */
	private Point southTile;
	
	/** The adjacent tile west of current tile. */
	private Point westTile;
	
	
	/**
	 * Instantiates a new mine walker panel.
	 * Initializes core instance variables.
	 * Creates and populates the EAST panel that does not need updating.
	 */
	public MineWalkerPanel() {
		// Initialize core instance variables
		gameGridDimension = DEFAULT_GRID_DIMENSION;
		gameDifficulty = DEFAULT_DIFFICULTY;
		score = INITIAL_SCORE;
		livesRemaining = INITIAL_LIVES;
		gameInProgress = false;
		currentTileBlinking = false;
		
		this.setLayout(new BorderLayout());
				
		// Note: The EAST Panel is the only Panel created in the constructor as it will not need to be updated.
		
		// ------------------ <Create EAST Panel> ------------------ //
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.PAGE_AXIS));
		Border eastPanelBorder = BorderFactory.createCompoundBorder(new BevelBorder(0), new BevelBorder(1));
		eastPanel.setBorder(eastPanelBorder);
		
		// ------------------ <Create Game Controls Panel - EAST> ------------------ //
		JPanel gameControlsPanel = new JPanel();
		gameControlsPanel.setLayout(new BoxLayout(gameControlsPanel, BoxLayout.PAGE_AXIS));
		Border gameControlsPanelBorder = BorderFactory.createTitledBorder("Controls & Settings");
		gameControlsPanel.setBorder(gameControlsPanelBorder);
		
		// <Show/Hide Path & Mines Panel - GCP Upper>
		JPanel showHidePathMinesPanel = new JPanel();
		showHidePathMinesPanel.setLayout(new BoxLayout(showHidePathMinesPanel, BoxLayout.X_AXIS));
				
		// <Grid Size Panel - GCP Middle-Upper>
		JPanel gameGridSizePanel = new JPanel();
		gameGridSizePanel.setLayout(new BoxLayout(gameGridSizePanel, BoxLayout.PAGE_AXIS));
		Border gameGridSizePanelBorder = BorderFactory.createTitledBorder("Grid Size (Min = 4, Max = 16)");
		gameGridSizePanel.setBorder(gameGridSizePanelBorder);
		
		// <Difficulty Selector Panel - GCP Middle-Middle>
		JPanel gameDifficultySelectorPanel = new JPanel();
		gameDifficultySelectorPanel.setLayout(new BoxLayout(gameDifficultySelectorPanel, BoxLayout.PAGE_AXIS));
		Border gameSettingsPanelBorder = BorderFactory.createTitledBorder("Difficulty Level");
		gameDifficultySelectorPanel.setBorder(gameSettingsPanelBorder);
		
		// <New/Quit Game and Play/Mute Music Panel - GCP Middle-Lower>
		JPanel gameMusicPanel = new JPanel();
		gameMusicPanel.setLayout(new BoxLayout(gameMusicPanel, BoxLayout.X_AXIS));
		
		// <Color Key Panel - GCP Lower>
		JPanel colorKeyPanel = new JPanel();
		colorKeyPanel.setLayout(new BoxLayout(colorKeyPanel, BoxLayout.Y_AXIS));
		Border colorKeyPanelBorder = BorderFactory.createTitledBorder("Color Key");
		colorKeyPanel.setBorder(colorKeyPanelBorder);
		
		// ------------------ <Create Game Controls Components> ------------------ //
		
		// <Game Control Components>
		// <Show/Hide Path & Mines Components - GCP Upper>
		showHideSafePath = new JButton("Show Path");
		showHideSafePath.addActionListener(this);
		showHideMineLocations = new JButton("Show Mines");
		showHideMineLocations.addActionListener(this);
		
		// <Grid Size Components - GCP Middle-Upper>
		gameGridDimensionTextField = new JTextField("10");
		gameGridDimensionTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, gameGridDimensionTextField.getPreferredSize().height));
		
		// <Difficulty Selector Components - GCP Middle-Middle>
		gameDifficultySelector = new JSlider(0, MIN_DIFFICULTY, MAX_DIFFICULTY, DEFAULT_DIFFICULTY);
		gameDifficultySelector.setMinorTickSpacing(5);
		gameDifficultySelector.setMajorTickSpacing(10);
		gameDifficultySelector.setPaintLabels(true);
		gameDifficultySelector.setPaintTicks(true);
		gameDifficultySelector.setPaintTrack(true);
		gameDifficultySelector.setSnapToTicks(true);
		gameDifficultySelector.addChangeListener(this);
		
		// <New/Quit Game and Play/Mute Music Components - GCP Middle-Lower>
		newQuitGameButton = new JButton("Quit Game");
		newQuitGameButton.addActionListener(this);
		playMuteMusic = new JButton("Mute Music");
		playMuteMusic.addActionListener(this);
		
		// <Color Key Components - GCP Lower>
		JTextField greenField = new JTextField("0 Mines Nearby");
		greenField.setBackground(MINES_0_COLOR);
		greenField.setEditable(false);
		JTextField yellowField = new JTextField("1 Mine Nearby");
		yellowField.setBackground(MINES_1_COLOR);
		yellowField.setEditable(false);
		JTextField orangeField = new JTextField("2 Mines Nearby");
		orangeField.setBackground(MINES_2_COLOR);
		orangeField.setEditable(false);
		JTextField redField = new JTextField("3 Mines Nearby");
		redField.setForeground(LIGHT_COLOR);
		redField.setBackground(MINES_3_COLOR);
		redField.setEditable(false);
		JTextField blackField = new JTextField("Exploded Mine");
		blackField.setForeground(LIGHT_COLOR);
		blackField.setBackground(MINES_X_COLOR);
		blackField.setEditable(false);
		JTextField playerField = new JTextField("Blinking X = Your Position");
		playerField.setForeground(DARK_COLOR);
		playerField.setBackground(LIGHT_COLOR);
		playerField.setEditable(false);
		
		// ------------------ <Populate EAST Panel> ------------------- //
		
		// ------------------ <Populate Game Controls Panel> ------------------ //
		// <Show/Hide Path & Mines Panel - GCP Upper>
		showHidePathMinesPanel.add(showHideSafePath);
		showHidePathMinesPanel.add(showHideMineLocations);
		
		gameControlsPanel.add(Box.createVerticalStrut(3));
		gameControlsPanel.add(showHidePathMinesPanel);
		
		// <Grid Size Panel - GCP Middle-Upper>
		gameGridSizePanel.add(gameGridDimensionTextField);
		
		gameControlsPanel.add(Box.createVerticalStrut(15));
		gameControlsPanel.add(gameGridSizePanel);
		
		// <Difficulty Selector Panel - GCP Middle-Middle>
		gameDifficultySelectorPanel.add(gameDifficultySelector);
		
		gameControlsPanel.add(Box.createVerticalStrut(3));
		gameControlsPanel.add(gameDifficultySelectorPanel);
		
		// <New/Quit Game and Play/Mute Music Panel - GCP Middle-Lower>
		gameMusicPanel.add(newQuitGameButton);
		gameMusicPanel.add(playMuteMusic);
		
		gameControlsPanel.add(gameMusicPanel);
		
		eastPanel.add(gameControlsPanel);
		
		// <Color Key Panel - GCP Lower>
		colorKeyPanel.add(greenField);
		colorKeyPanel.add(yellowField);
		colorKeyPanel.add(orangeField);
		colorKeyPanel.add(redField);
		colorKeyPanel.add(blackField);
		colorKeyPanel.add(playerField);
				
		eastPanel.add(Box.createVerticalStrut(20));
		eastPanel.add(colorKeyPanel);
				
		// <Add EAST Panel to this MineWalkerPanel>
		this.add(eastPanel, BorderLayout.EAST);
		
		beginNewGame();
		
    }
	
	/**
	 * Begins a new game by setting all panels and components to starting values.
	 * Grid size and Game difficulty are validated and set to user-defined values.
	 */
	private void beginNewGame() {
		
		validateGridDimension();
		
		// Initialize Game-Specific Instance Variables
		score = INITIAL_SCORE;
		livesRemaining = INITIAL_LIVES;
		gameInProgress = true;
		maxXCoord = gameGridDimension - 1;
		maxYCoord = gameGridDimension - 1;
		startTile = new Point(maxXCoord, maxYCoord);
		endTile = new Point(0,0);
		currentTile = startTile;
		randWalk = new RandomWalk(gameGridDimension);
		randWalk.createWalk();
		ArrayList<Point> tmp = randWalk.getPath();
		safePath = new Point[tmp.size()];
		safePath = tmp.toArray(safePath);
		tilesVisited = new ArrayList<JButton>();
		playerPath = new ArrayList<Point>();
		playerPath.add(new Point(maxXCoord, maxYCoord));
		showHideSafePath.setText("Show Path");
		showHideMineLocations.setText("Show Mines");
		gameGridDimensionTextField.setEnabled(false);
		gameDifficultySelector.setEnabled(false);
		newQuitGameButton.setText("Quit Game");
		playMuteMusic.setText("Mute Music");
		
		createGameTiles();
		
		plantMines();
		
		plantFreeGuys();
		
		plantBonusPoints();
		
		updateThisPanel();
		
		GAME_MUSIC.play();
		
		startAnimation();
	}
	
	/**
	 * Validates the user-entered grid dimension (Must be within range)
	 */
	private void validateGridDimension() {
		int originalGameGridDimension = gameGridDimension; //store in case user enters invalid value
		try {
			gameGridDimension = Integer.parseInt(gameGridDimensionTextField.getText().trim());
			// Validate user-defined gameGridDimension 
			if (gameGridDimension < MIN_GRID_DIMENSION || gameGridDimension > MAX_GRID_DIMENSION) { // Invalid dimension
				//reset gameGridDimension and text field to previous values
				gameGridDimension = originalGameGridDimension;
				gameGridDimensionTextField.setText(Integer.toString(gameGridDimension));
			}
		} catch (NumberFormatException nfe) { // Invalid input in text field (user did not enter an integer)
			//reset gameGridDimension and text field to previous values
			gameGridDimension = originalGameGridDimension;
			gameGridDimensionTextField.setText(Integer.toString(gameGridDimension));
		}
		
	}
	
	/**
	 * Creates the game tiles.
	 */
	private void createGameTiles() {
		// Create and Populate game tile buttons
		gameTileButtons = new JButton[gameGridDimension][gameGridDimension];
		gameTileButtonsCoordinates = new Point[gameGridDimension][gameGridDimension];
		for(int row=0; row < gameGridDimension; row++) {
			for(int col=0; col < gameGridDimension; col++) {
				gameTileButtons[row][col] = new JButton("");
				gameTileButtons[row][col].setName(row + "," + col); // Name used to parse button coordinates
				gameTileButtons[row][col].setBackground(INITIAL_TILE_COLOR);
				gameTileButtons[row][col].addActionListener(this);
				gameTileButtonsCoordinates[row][col] = new Point(row, col);
			}
		}
	}
	
	/**
	 * Plants mines under available tiles.
	 */
	private void plantMines() {
		// Count non-safePath Tiles
		double percentMines = (double) gameDifficulty / 100;
		int numberOfTiles = gameGridDimension * gameGridDimension;
		int emptyTiles = numberOfTiles - safePath.length;
		int numberOfMines = (int) (percentMines * emptyTiles);
		mineLocations = new Point[numberOfMines]; // Stores the coordinates for each mine placed
		Random rand = new Random();
		Point randPoint;
		int minesIndex = 0;
		// Plant all mines under random available tiles
		while(numberOfMines > 0) {
			randPoint = new Point(rand.nextInt(gameGridDimension), rand.nextInt(gameGridDimension));
			if( !(Arrays.asList(safePath).contains(randPoint)) && !(Arrays.asList(mineLocations).contains(randPoint)) ) {
					mineLocations[minesIndex] = new Point(randPoint);
					numberOfMines--;
					minesIndex++;
			}
		}
	}
	
	/**
	 * Plants free lives under available tiles.
	 */
	private void plantFreeGuys() {
		// Count available tiles
		int numberOfTiles = gameGridDimension * gameGridDimension;
		int emptyTiles = numberOfTiles - safePath.length - mineLocations.length;
		int numberOfFreeGuys = (int) (PERCENTAGE_OF_FREE_GUY_TILES * emptyTiles);
		freeGuyLocations = new Point[numberOfFreeGuys]; // Stores the coordinates for each free-guy placed
		Random rand = new Random();
		Point randPoint;
		int freeGuyIndex = 0;
		// Plant all free-guys under random available tiles
		while(numberOfFreeGuys > 0) {
			randPoint = new Point(rand.nextInt(gameGridDimension), rand.nextInt(gameGridDimension));
			if( !(Arrays.asList(safePath).contains(randPoint)) && !(Arrays.asList(freeGuyLocations).contains(randPoint)) &&
				!(Arrays.asList(freeGuyLocations).contains(randPoint)) ) {
					freeGuyLocations[freeGuyIndex] = new Point(randPoint);
					numberOfFreeGuys--;
					freeGuyIndex++;
			}
		}
	}
	
	/**
	 * Plants bonus points under available tiles.
	 */
	private void plantBonusPoints() {
		// Count available tiles
		int numberOfTiles = gameGridDimension * gameGridDimension;
		int emptyTiles = numberOfTiles - safePath.length - mineLocations.length - freeGuyLocations.length;
		int numberOfBonusPointsTiles = (int) (PERCENTAGE_OF_BONUS_POINTS_TILES * emptyTiles);
		bonusPointsLocations = new Point[numberOfBonusPointsTiles]; // Stores the coordinates for each bonus placed
		Random rand = new Random();
		Point randPoint;
		int bonusPointsIndex = 0;
		// Plant all bonuses under random available tiles
		while(numberOfBonusPointsTiles > 0) {
			randPoint = new Point(rand.nextInt(gameGridDimension), rand.nextInt(gameGridDimension));
			if( !(Arrays.asList(safePath).contains(randPoint)) && !(Arrays.asList(mineLocations).contains(randPoint)) &&
				!(Arrays.asList(freeGuyLocations).contains(randPoint)) && !(Arrays.asList(bonusPointsLocations).contains(randPoint)) ) {
					bonusPointsLocations[bonusPointsIndex] = new Point(randPoint);
					numberOfBonusPointsTiles--;
					bonusPointsIndex++;
			}
		}
	}
	
	/**
	 * Updates this MineWalkerPanel.
	 */
	private void updateThisPanel() {
		updateNorthPanel();
		updateGameGridPanel();
		updateCurrentTile();
		this.add(northPanel, BorderLayout.NORTH);
		this.add(gameGridPanel, BorderLayout.CENTER);
		this.revalidate(); // Causes this MineWalkerPanel to refresh itself with new contents
	}
	
	/**
	 * Updates north panel.
	 */
	private void updateNorthPanel() {
		// Remove panel if not empty
		if(northPanel != null) {
			this.remove(northPanel);
		}
		updateGameStatusPanel();
		// Create new panel
		northPanel = new JPanel();
		northPanel.add(gameStatusPanel);
	}
	
	/**
	 * Updates game status panel.
	 */
	private void updateGameStatusPanel() {
		// Remove panel if not empty
		if(gameStatusPanel != null) {
			northPanel.remove(gameStatusPanel);
		}
		// Create new panel
		gameStatusPanel = new JPanel();
		FlowLayout gameStatusPanelLayout = new FlowLayout(FlowLayout.CENTER, 20, 2);
		gameStatusPanel.setLayout(gameStatusPanelLayout);
		TitledBorder gameStatusPanelBorder = BorderFactory.createTitledBorder("Game Status");
		gameStatusPanelBorder.setTitleJustification(TitledBorder.CENTER);
		gameStatusPanel.setBorder(gameStatusPanelBorder);
		
		// Create Components and populate panel
		scoreLabel = new JLabel("Points: " + score);
		scoreLabel.setFont(GAME_STATUS_FONT);
		livesRemainingLabel = new JLabel("Lives: " + livesRemaining);
		livesRemainingLabel.setFont(GAME_STATUS_FONT);
		gameStatusPanel.add(Box.createVerticalStrut(20));
		gameStatusPanel.add(scoreLabel);
		gameStatusPanel.add(Box.createVerticalStrut(5));
		gameStatusPanel.add(livesRemainingLabel);
	}
	
	/**
	 * Updates game grid panel.
	 */
	private void updateGameGridPanel() {
		// Remove panel if not empty
		if(gameGridPanel != null) { 
			this.remove(gameGridPanel);
		}
		// Create new panel
		gameGridPanel = new JPanel(new GridLayout(gameGridDimension, gameGridDimension));
		Border gameGridPanelBorder = BorderFactory.createCompoundBorder(new BevelBorder(0), new BevelBorder(1));
		gameGridPanel.setBorder(gameGridPanelBorder);
		Dimension preferredSize = new Dimension(gameGridDimension * 50, gameGridDimension * 50);
		gameGridPanel.setPreferredSize(preferredSize);
		
		// Populate panel with game tiles
		for(int row=0; row < gameGridDimension; row++) {
			for(int col=0; col < gameGridDimension; col++) {
				gameGridPanel.add(gameTileButtons[row][col]);
			}
		}
	}
	
	/**
	 * Updates the player's current tile.
	 * Adjacent tiles are checked for mines and the current tile color is set to match the nearby mines color key.
	 */
	private void updateCurrentTile() {
		// Loop through all game tiles to find player's current tile
		for(int row=0; row < gameGridDimension; row++) {
			for(int col=0; col < gameGridDimension; col++) {
				if(gameTileButtonsCoordinates[row][col].equals(currentTile)) { // Player's current tile found
					// Place player's marker and set player's current tile to proper nearby mines color
					gameTileButtons[row][col].setText("X");
					int numNearbyMines = countNearbyMines();
					if(numNearbyMines == 0) {
						gameTileButtons[row][col].setBackground(MINES_0_COLOR);
					}
					else if(numNearbyMines == 1) {
						gameTileButtons[row][col].setBackground(MINES_1_COLOR);
					}
					else if(numNearbyMines == 2) {
						gameTileButtons[row][col].setBackground(MINES_2_COLOR);
					}
					else if(numNearbyMines == 3) {
						gameTileButtons[row][col].setBackground(MINES_3_COLOR);
					}
					else {
						gameTileButtons[row][col].setBackground(MINES_X_COLOR);	
					}
				}
			}
		}
	}
	
	/**
	 * Ends game by disabling in-game only buttons and tiles.
	 * Checks for victory (counts total points) or failure and displays appropriate message.
	 */
	private void endGame() {
		// Disable all game tile buttons
		for(JButton[] ba : gameTileButtons) {
			for(JButton b : ba) {
				b.setEnabled(false);
			}
			GAME_MUSIC.stop();
			// Change game control texts and Enable game setting controls
			newQuitGameButton.setText("New Game");
			playMuteMusic.setText("Play Music");
			gameGridDimensionTextField.setEnabled(true);
			gameDifficultySelector.setEnabled(true);
		}
		if(victory()) { // Player has reached the goal
			// Convert remaining lives to points and add them to score
			score += (livesRemaining * 100);
			livesRemaining = 0;
			updateThisPanel();
			VICTORY.play();
			// Display Victory message
			JOptionPane.showMessageDialog(null, SANDWICH, "Congratulations!   Total Score: " + score + " Points!", JOptionPane.PLAIN_MESSAGE, null);
		}
		else if(failure()) { // Player has lost all lives
			GAME_OVER.play();
			// Display Effort Award message
			JOptionPane.showMessageDialog(null, EFFORT, "We're required to give everyone an Award.", JOptionPane.PLAIN_MESSAGE, null);
		}
		gameInProgress = false;
		showMines();
		showPlayerPath();
		updateThisPanel();
	}
	
	/**
	 * Shows the players path
	 */
	private void showPlayerPath() {
		playerPathCoordinates = new Point[playerPath.size()];
		playerPathCoordinates = playerPath.toArray(playerPathCoordinates);
		// Loop through all game tiles and make player path visible
		for(int row=0; row < gameGridDimension; row++) {
			for(int col=0; col < gameGridDimension; col++) {
				Point checkPoint = new Point(gameTileButtonsCoordinates[row][col]);
				if(Arrays.asList(playerPathCoordinates).contains(checkPoint)) {
					gameTileButtons[row][col].setText("x"); // make Player Path Point visible
				}
			}
		}
	}
	
	/**
	 * Reveals Mine Locations
	 */
	private void showMines() {
		// Loop through all game tiles and make all mines visible
		for(int row=0; row < gameGridDimension; row++) {
			for(int col=0; col < gameGridDimension; col++) {
				for(int i=0; i < mineLocations.length; i++) {
					if(gameTileButtonsCoordinates[row][col].equals(mineLocations[i])) { // Mine found
						gameTileButtons[row][col].setText("O"); // set mine visible
					}
				}
			}
		}
		showHideMineLocations.setText("Hide Mines");
	}
	
	/** Hides Mine Locations
	 * 
	 */
	private void hideMines() {
		// Loop through all game tiles and hide all mines
		for(int row=0; row < gameGridDimension; row++) {
			for(int col=0; col < gameGridDimension; col++) {
				for(int i=0; i < mineLocations.length; i++) {
					if(gameTileButtonsCoordinates[row][col].equals(mineLocations[i])) { // Mine found
						gameTileButtons[row][col].setText(""); // hide mine
					}
				}
			}
		}
		showHideMineLocations.setText("Show Mines");
	}
	
	/** 
	 * Reveals Safe Path
	 */
	private void showPath() {
		// Loop through all game tiles and make safe path visible
		for(int row=0; row < gameGridDimension; row++) {
			for(int col=0; col < gameGridDimension; col++) {
				for(int i=0; i < safePath.length; i++) {
					if(gameTileButtonsCoordinates[row][col].equals(safePath[i])) { // Safe Point found
						gameTileButtons[row][col].setText("*"); // make Safe Point visible
					}
				}
			}
		}
		showHideSafePath.setText("Hide Path");
	}
	
	/**
	 * Hides Safe Path
	 */
	private void hidePath() {
		// Loop through all game tiles and hide safe path
		for(int row=0; row < gameGridDimension; row++) {
			for(int col=0; col < gameGridDimension; col++) {
				for(int i=0; i < safePath.length; i++) {
					if(gameTileButtonsCoordinates[row][col].equals(safePath[i])) { // Safe Point found
						gameTileButtons[row][col].setText(""); // hide Safe Point
					}
				}
			}
		}
		showHideSafePath.setText("Show Path");
	}
	
	/**
	 * Counts nearby mines.
	 *
	 * @return the number of nearby mines
	 */
	private int countNearbyMines() {
		int numNearbyMines = 0; // Stores number of nearby mines for Player's current tile
		// Generate Points for all four possible adjacent tiles
		northTile = new Point(currentTile.x, currentTile.y - 1);
		eastTile = new Point(currentTile.x + 1, currentTile.y);
		southTile = new Point(currentTile.x, currentTile.y + 1);
		westTile = new Point(currentTile.x - 1, currentTile.y);
		// Increment nearby mines count if adjacent tile is in bounds and contains a mine
		if( (tileInBounds(northTile)) && (Arrays.asList(mineLocations).contains(northTile)) ) {
			numNearbyMines++;
		}
		if( (tileInBounds(eastTile)) && (Arrays.asList(mineLocations).contains(eastTile)) ) {
			numNearbyMines++;
		}
		if( (tileInBounds(southTile)) && (Arrays.asList(mineLocations).contains(southTile)) ) {
			numNearbyMines++;
		}
		if( (tileInBounds(westTile)) && (Arrays.asList(mineLocations).contains(westTile)) ) {
			numNearbyMines++;
		}
		return numNearbyMines;
	}
	
	/**
	 * Checks if passed in Point is in bounds.
	 *
	 * @param p the point to be checked for in-bounds status
	 * @return true, if p is in bounds
	 */
	private boolean tileInBounds(Point p) {
		boolean inBounds = false;
		// Check to make sure the Point's x and y coordinates are both within the valid range for the grid size
		if( (p.x >= 0) && (p.x <= maxXCoord) && (p.y >= 0) && (p.y <= maxYCoord) ) {
			inBounds = true;
		}
		return inBounds;
	}
	
	/**
	 * Checks if passed in Point is adjacent to current player position.
	 * Note: Points that are passed to method should already be "in-bounds" validated.
	 * @param p the point to be checked for if it is adjacent
	 * @return true, if p is adjacent to current player position
	 */
	private boolean tileIsAdjacent(Point p) {
		boolean adjacent = false;
		// Generate Points for all four or the current tile's adjacent tiles
		Point[] adjacentPoints = new Point[4]; // Stores the four adjacent Points
		adjacentPoints[0] = new Point(currentTile.x, currentTile.y - 1);
		adjacentPoints[1] = new Point(currentTile.x + 1, currentTile.y);
		adjacentPoints[2] = new Point(currentTile.x, currentTile.y + 1);
		adjacentPoints[3] = new Point(currentTile.x - 1, currentTile.y);
		// Check if Point passed in is one of the four adjacent Points
		if(Arrays.asList(adjacentPoints).contains(p)) {
			adjacent = true;
		}
		return adjacent;
	}
	
	/**
	 * Checks if player has reached the victory goal
	 *
	 * @return true, if player has reached the end tile
	 */
	private boolean victory() {
		boolean winner = false;
		if( (currentTile.equals(endTile)) && (gameInProgress) ) {
			winner = true;
		}
		return winner;
	}
	
	/**
	 * Checks if player has lost all lives
	 *
	 * @return true, if player's remaining lives count is zero
	 */
	private boolean failure() {
		boolean loser = false;
		if(livesRemaining == 0) {
			loser = true;
		}
		return loser;
	}
	
	/**
	 * Animates player's current tile.
	 */
	public void blinkCurrentTile() {
		// Make Player's marker disappear
		gameTileButtons[currentTile.x][currentTile.y].setText("");
	}
	
	/**
	 * Starts continuous timer used to animate player's current tile.
	 */
	private void startAnimation() {
	    ActionListener blinker = new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		// Alternate visibility of Player's marker to simulate "blinking"
	    		if(gameInProgress) {
	    			if(currentTileBlinking) {
		    			updateCurrentTile(); // This method makes Player's marker visible
		    			currentTileBlinking = false;
		    		}
		    		else {
		    			blinkCurrentTile(); // This method makes Player's marker disappear
		    			currentTileBlinking = true;
		    		}
	    		}
	    	}
	    };
	    new Timer(BLINK_DELAY, blinker).start(); // BLINK_DELAY can be changed to adjust "blinking" speed
    }
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * Listens for button action events and responds to each possible source button appropriately
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton sourceButton = (JButton) e.getSource();
		if(sourceButton.getText().equals("New Game")) { // New Game button has been pressed
			CLICK.play();
			beginNewGame();
		}
		else if(sourceButton.getText().equals("Quit Game")) { // Quit Game button has been pressed
			CLICK.play();
			endGame();
		}
		else if(sourceButton.getText().equals("Play Music")) { // Play Music button has been pressed
			CLICK.play();
			GAME_MUSIC.play();
			sourceButton.setText("Mute Music");
		}
		else if(sourceButton.getText().equals("Mute Music")) { // Mute Music button has been pressed
			CLICK.play();
			GAME_MUSIC.stop();
			sourceButton.setText("Play Music");
		}
		else if(sourceButton.getText().equals("Show Mines")) { // Show Mines button has been pressed
			CLICK.play();
			showMines();
		}
		else if(sourceButton.getText().equals("Hide Mines")) { // Hide Mines button has been pressed
			CLICK.play();
			hideMines();
		}
		else if(sourceButton.getText().equals("Show Path")) { // Show Path button has been pressed
			CLICK.play();
			showPath();
		}
		else if(sourceButton.getText().equals("Hide Path")) { // Hide Path button has been pressed
			CLICK.play();
			hidePath();
		}
		else { // A Game Tile button has been pressed
			String coordinatesString = sourceButton.getName(); // Stores the button's Name
			String delim = "[,]";
			String[] coordinates = coordinatesString.split(delim); // Parse button's Name for it's coordinates
			Point sourceTile = new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])); // button's Point established
			// Proceed if clicked game tile is adjacent to Player's current tile
			if(tileIsAdjacent(sourceTile)) {
				// Check if player has stepped on a mine
				if(Arrays.asList(mineLocations).contains(sourceTile)) {
					playerPath.add(sourceTile);
					sourceButton.setBackground(DARK_COLOR);
					sourceButton.setEnabled(false);
					livesRemaining--;
					score -= 30;
					updateThisPanel();
					if(!(failure()) ) { // If player still has lives remaining, just play mine explosion sound effect
						MINE.play();
					}
					else { // If all lives have been lost - Failure sound clip begins with a Mine explosion
						endGame();
					}
				}
				else { // The tile clicked has bonus points, free guy, or is safe
					if(!(tilesVisited).contains(sourceButton)) { // 10 points if new safe tile is visited
						score += 10;
					}
					if( (Arrays.asList(freeGuyLocations).contains(sourceTile)) && (!(tilesVisited).contains(sourceButton)) ) { // Found free-guy (1st visit)
						FREE_GUY.play();
						livesRemaining++;
						updateThisPanel();
					}
					else if( (Arrays.asList(bonusPointsLocations).contains(sourceTile)) && (!(tilesVisited).contains(sourceButton)) ) { // Found bonus (1st visit)
						BONUS_POINTS.play();
						score += BONUS_POINTS_VALUE;
						updateThisPanel();
					}
					else {
						MOVE.play();
					}
					tilesVisited.add(sourceButton);
					playerPath.add(new Point(sourceTile));
					gameTileButtons[currentTile.x][currentTile.y].setText(""); // Remove player's marker from last tile
					currentTile = sourceTile;
					updateThisPanel();
					if(victory()) { // Check if Player has reached goal
						endGame();
					}
				}
			}
			else { // clicked tile is not adjacent to current tile
				ERROR.play();
			}
		}	
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 * 
	 * Listens to change events triggered by the JSlider that controls game difficulty setting
	 */
	@Override
	public void stateChanged(ChangeEvent ce) {
		JSlider source = (JSlider) ce.getSource();
		// Change Difficulty level to user-specified value
		gameDifficulty = source.getValue();
	}
}
