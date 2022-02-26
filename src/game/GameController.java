package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("unused")
public class GameController {
	public static String username;

	public static void main(String[] args) {
		username = JOptionPane.showInputDialog(null, "Please Enter A Username", "Enter a username",
				JOptionPane.PLAIN_MESSAGE);
		if (username == "" || username == null) {
			username = "Player";
		}
		new Menu();
	}

	public static void startMouseListener(JButton startButton, final Menu mainMenu) {
		startButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mainMenu.dispose();
				String difficultyOptions[] = { "Easy", "Intermediate", "Hard" };
				int difficultySelected = JOptionPane.showOptionDialog(mainMenu, "Select Difficulty",
						"Select Game Difficulty", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						difficultyOptions, difficultyOptions[1]);
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
				new MinesweeperGame();
			}
		});
	}

	public static void instructionMouseListener(JButton instructionButton) {
		instructionButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new Instructions();
			}
		});
	}

	public static void instructionActionListener(JMenuItem optionInstructions) {
		optionInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Instructions();
			}
		});
	}

	public static void restartMouseListener(JMenuItem restartMenuItem, final MinesweeperGame game) {
		restartMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String difficultyOptions[] = { "Easy", "Intermediate", "Hard" };
				int difficultySelected = JOptionPane.showOptionDialog(null, "Select Difficulty",
						"Select Game Difficulty", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						difficultyOptions, difficultyOptions[1]);
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
				game.dispose();

				new MinesweeperGame();
			}
		});
	}

	public static void returnMouseListener(JMenuItem returnMenuItem, final MinesweeperGame game) {
		returnMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.dispose();
				new Menu();
			}
		});
	}

	public static void closeMouseListener(JButton btnCloseInstruc, final Instructions instruc) {
		btnCloseInstruc.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				instruc.dispose();
			}
		});
	}
}
