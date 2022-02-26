package game;

// Use AWT's Layout Manager
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
// Use AWT's Event handlers
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
// Use Swing's Containers and Components
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The Mine Sweeper Game. Left-click to reveal a cell. Right-click to
 * plant/remove a flag for marking a suspected mine. You win if all the cells
 * not containing mines are revealed. You lose if you reveal a cell containing a
 * mine.
 */
@SuppressWarnings("serial")
public class MinesweeperGame extends JFrame implements WindowListener {
	public static int ROWS = 10; // number of cells
	public static int COLS = 10;
	public static int COVERED = ROWS * COLS;
	public static int MINES_PLANTED = 2;
	// Name-constants for UI control (sizes, colors and fonts)
	public static final int CELL_SIZE = 60; // Cell width and height, in pixels
	public static int CANVAS_WIDTH = CELL_SIZE * COLS; // Game board width/height
	public static int CANVAS_HEIGHT = CELL_SIZE * ROWS;
	public static final Color BGCOLOR_NOT_REVEALED = Color.GREEN;
	public static final Color FGCOLOR_NOT_REVEALED = Color.RED; // flag
	public static final Color BGCOLOR_REVEALED = Color.DARK_GRAY;
	public static final Color FGCOLOR_REVEALED = Color.LIGHT_GRAY; // number of mines
	public static final Color MINE_REVEALED = Color.RED;
	public static Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);
	// Buttons for user interaction
	JButton btnCells[][] = new JButton[ROWS][COLS];
	// Number of mines in this game. Can vary to control the difficulty level.
	public static int numMines;
	int numFlags;
	int covered;
	int gameEndOption;
	JTextField tfNumMines = new JTextField(MINES_PLANTED + "");
	JTextField tfNumFlags = new JTextField(numFlags + "");
	// Location of mines. True if mine is present on this cell.
	boolean mines[][] = new boolean[ROWS][COLS];
	// User can right-click to plant/remove a flag to mark a suspicious cell
	boolean flags[][] = new boolean[ROWS][COLS];
	// Constructor to set up all the UI and game components
	boolean revealed[][] = new boolean[ROWS][COLS];

	public MinesweeperGame() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout(5, 5));
		cp.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		JPanel panelGame = new JPanel();
		panelGame.setLayout(new GridLayout(10, 10, 5, 5));
		JPanel panelStatus = new JPanel();
		panelStatus.setLayout(new FlowLayout());
		JLabel labelNumMines = new JLabel("Number of Mines");
		JLabel labelNumFlags = new JLabel("Number of Flags Placed");
		JLabel labelUsername = new JLabel("User Playing: ");
		JTextField tfUsername = new JTextField(GameController.username);
		panelStatus.add(labelUsername);
		panelStatus.add(tfUsername);
		panelStatus.add(labelNumMines);
		panelStatus.add(tfNumMines);
		panelStatus.add(labelNumFlags);
		panelStatus.add(tfNumFlags);
		tfNumMines.setEditable(false);
		tfNumFlags.setEditable(false);
		cp.add(panelGame, BorderLayout.CENTER);
		cp.add(panelStatus, BorderLayout.SOUTH);

		CellMouseListener listener = new CellMouseListener();
		JMenuBar menuBar = new JMenuBar();
		JMenu options = new JMenu("Options");
		JMenuItem optionRestart = new JMenuItem("Restart game");
		GameController.restartMouseListener(optionRestart, this);
		JMenuItem optionReturn = new JMenuItem("Return to menu");
		GameController.returnMouseListener(optionReturn, this);
		JMenuItem optionInstructions = new JMenuItem("Instructions");
		GameController.instructionActionListener(optionInstructions);
		options.add(optionRestart);
		options.add(optionReturn);
		options.add(optionInstructions);
		menuBar.add(options);
		setJMenuBar(menuBar);
		this.setResizable(false);
		panelGame.setLayout(new GridLayout(ROWS, COLS, 2, 2)); // in 10x10 GridLayout
		// Allocate a common instance of listener as the MouseEvent listener
		// for all the JButtons

		// Construct 10x10 JButtons and add to the content-pane
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				btnCells[row][col] = new JButton(); // Allocate each JButton of the array
				panelGame.add(btnCells[row][col]); // add to content-pane in GridLayout

				// Add MouseEvent listener to process the left/right mouse-click
				btnCells[row][col].addMouseListener(listener);
			}
		}
		// Set the size of the content-pane and pack all the components
		// under this container.
		panelGame.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // handle window-close button
		setTitle("Minesweeper");
		setVisible(true); // show it
		addWindowListener(this);
		// Initialize for a new game
		initGame();
	}

	public int getSurroundingMines(int thisRow, int thisCol) {
		int numMines = 0;
		for (int row = thisRow - 1; row <= thisRow + 1; row++) {
			for (int col = thisCol - 1; col <= thisCol + 1; col++) {
				if (row >= 0 && row < ROWS && col >= 0 && col < COLS && mines[row][col] == true) {
					numMines += 1;
				}
			}
		}
		return numMines;
	}

	public void revealSurrounding(int thisRow, int thisCol) {
		for (int row = thisRow - 1; row <= thisRow + 1; row++) {
			for (int col = thisCol - 1; col <= thisCol + 1; col++) {
				if (row >= 0 && row < ROWS && col >= 0 && col < COLS && !revealed[row][col]) {
					revealed[row][col] = true;
					btnCells[row][col].setEnabled(false);
					btnCells[row][col].setBackground(BGCOLOR_REVEALED);
					covered -= 1;
					int numMines = getSurroundingMines(row, col);
					if (numMines == 0) {
						btnCells[row][col].setText("");
						revealSurrounding(row, col);
					} else {
						btnCells[row][col].setText(numMines + "");
					}
				}
			}
		}
	}

	// Initialize and re-initialize a new game
	private void initGame() {
		// Reset cells, mines, and flags
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				// Set all cells to un-revealed
				btnCells[row][col].setEnabled(true); // enable button
				btnCells[row][col].setForeground(FGCOLOR_NOT_REVEALED);
				btnCells[row][col].setBackground(BGCOLOR_NOT_REVEALED);
				btnCells[row][col].setFont(FONT_NUMBERS);
				btnCells[row][col].setText(""); // display blank
				mines[row][col] = false; // clear all the mines
				flags[row][col] = false; // clear all the flags
				revealed[row][col] = false;
			}
		}
		// Set the number of mines and the mines' location
		// Hardcoded here! Should be randomly generated for each game.
		Random rand = new Random();
		numMines = MINES_PLANTED;
		covered = COVERED;
		for (int count = 0; count < numMines;) {
			int x = rand.nextInt(COLS);
			int y = rand.nextInt(ROWS);
			if (mines[x][y] == false) {
				mines[x][y] = true;
				count += 1;
			}
		}
	}

	private void gameEnd() {
		if (gameEndOption == JOptionPane.YES_OPTION) {
			this.dispose();
			new Menu();
		} else if (gameEndOption == JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		} else if (gameEndOption == JOptionPane.NO_OPTION) {
			String difficultyOptions[] = { "Easy", "Intermediate", "Hard" };
			int difficultySelected = JOptionPane.showOptionDialog(null, "Select Difficulty", "Select Game Difficulty",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, difficultyOptions,
					difficultyOptions[1]);
			if (difficultySelected == JOptionPane.YES_OPTION) {

			} else if (difficultySelected == JOptionPane.NO_OPTION) {
				MinesweeperGame.ROWS = 13;
				MinesweeperGame.COLS = 13;
				MinesweeperGame.MINES_PLANTED = 20;
				MinesweeperGame.CANVAS_WIDTH = 13 * MinesweeperGame.CELL_SIZE;
				MinesweeperGame.CANVAS_HEIGHT = 13 * MinesweeperGame.CELL_SIZE;
			} else if (difficultySelected == JOptionPane.CANCEL_OPTION) {
				MinesweeperGame.ROWS = 15;
				MinesweeperGame.COLS = 15;
				MinesweeperGame.MINES_PLANTED = 40;
				MinesweeperGame.CANVAS_WIDTH = 15 * MinesweeperGame.CELL_SIZE;
				MinesweeperGame.CANVAS_HEIGHT = 15 * MinesweeperGame.CELL_SIZE;
			}
			this.dispose();
			new MinesweeperGame();
		} else {
			System.exit(0);
		}
	}

	private class CellMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			// Determine the (row, col) of the JButton that triggered the event
			int rowSelected = -1;
			int colSelected = -1;
			// Get the source object that fired the Event
			JButton source = (JButton) e.getSource();
			// Scan all rows and columns, and match with the source object
			boolean found = false;
			for (int row = 0; row < ROWS && !found; ++row) {
				for (int col = 0; col < COLS && !found; ++col) {
					if (source == btnCells[row][col]) {
						rowSelected = row;
						colSelected = col;
						found = true; // break both inner/outer loops
					}
				}
			}

			// Left-click to reveal a cell; Right-click to plant/remove the flag.
			if (e.getButton() == MouseEvent.BUTTON1) { // Left-button clicked

				if (mines[rowSelected][colSelected] == true) {
					for (int row = 0; row < ROWS; row++) {
						for (int col = 0; col < COLS; col++) {

							btnCells[row][col].setEnabled(false);
							if (mines[row][col] == true) {
								btnCells[row][col].setBackground(MINE_REVEALED);
								Icon explosionIcon = new ImageIcon("Images/explosion.gif");
								((ImageIcon) explosionIcon).setImage(((ImageIcon) explosionIcon).getImage()
										.getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_DEFAULT));
								btnCells[row][col].setText("");
								btnCells[row][col].setIcon(explosionIcon);
							}
						}
					}
					String[] optionsLost = { "Return to menu", "Restart Game", "Exit Game" };
					Icon skullIcon = new ImageIcon("Images/skull.png");
					((ImageIcon) skullIcon).setImage(((ImageIcon) skullIcon).getImage().getScaledInstance(CELL_SIZE,
							CELL_SIZE, Image.SCALE_DEFAULT));
					gameEndOption = JOptionPane.showOptionDialog(null, "Game Over!", "GAME OVER",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, skullIcon, optionsLost,
							optionsLost[0]);
					gameEnd();
				} else if (getSurroundingMines(rowSelected, colSelected) == 0) {
					revealSurrounding(rowSelected, colSelected);
				} else {
					int numSurroundingMines = getSurroundingMines(rowSelected, colSelected);
					revealed[rowSelected][colSelected] = true;
					btnCells[rowSelected][colSelected].setEnabled(false);
					btnCells[rowSelected][colSelected].setBackground(BGCOLOR_REVEALED);
					covered -= 1;
					btnCells[rowSelected][colSelected].setIcon(null);
					btnCells[rowSelected][colSelected].setText(numSurroundingMines + "");
				}

			} else if (e.getButton() == MouseEvent.BUTTON3) { // right-button clicked
				if (flags[rowSelected][colSelected] == true) {
					flags[rowSelected][colSelected] = false;
					btnCells[rowSelected][colSelected].setIcon(null);
					numFlags--;
					tfNumFlags.setText(numFlags + "");
				} else {
					flags[rowSelected][colSelected] = true;
					Icon flagIcon = new ImageIcon("Images/flag.gif");
					((ImageIcon) flagIcon).setImage(((ImageIcon) flagIcon).getImage().getScaledInstance(CELL_SIZE,
							CELL_SIZE, Image.SCALE_DEFAULT));
					btnCells[rowSelected][colSelected].setIcon(flagIcon);
					numFlags++;
					tfNumFlags.setText(numFlags + "");
					// btnCells[rowSelected][colSelected].setText(">");
				}
			}

			if (covered == MINES_PLANTED) {
				String[] optionsWon = { "Return to menu", "Restart Game", "Exit Game" };
				Icon smileIcon = new ImageIcon("Images/smile.gif");
				((ImageIcon) smileIcon).setImage(((ImageIcon) smileIcon).getImage().getScaledInstance(CELL_SIZE,
						CELL_SIZE, Image.SCALE_DEFAULT));
				gameEndOption = JOptionPane.showOptionDialog(null, "SUCCESS! YOU WIN!", "YOU WON!",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, smileIcon, optionsWon,
						optionsWon[0]);
				gameEnd();
			}
		}
	}

	// WindowEvent handlers
	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0); // terminate the program
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

}
