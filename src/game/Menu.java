package game;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Menu extends JDialog implements WindowListener {
	public static final String WELCOME_MSG = "WELCOME PLAYER";
	public static final Font FONT_MENU = new Font("Sans_Serif", 1, 60);
	public static final int MENU_WIDTH = 600;
	public static final int MENU_HEIGHT = 600;
	public static final Font FONT_WELCOME = new Font("Sans_Serif", 1, 30);

	public Menu() {
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.BLACK);
		JButton startButton = new JButton("Start Game!");
		JButton instructionButton = new JButton("Show Instructions");
		contentPane.setLayout(new GridLayout(4, 1, 5, 5));
		JLabel labelMenu = new JLabel("MINESWEEPER", 0);
		JLabel labelWelcome = new JLabel("WELCOME " + GameController.username, 0);
		labelMenu.setFont(FONT_MENU);
		labelMenu.setForeground(Color.red);
		labelWelcome.setFont(FONT_WELCOME);
		labelWelcome.setForeground(Color.red);
		startButton.setFont(FONT_WELCOME);
		instructionButton.setFont(FONT_WELCOME);
		contentPane.add(labelMenu);
		contentPane.add(labelWelcome);
		contentPane.add(startButton);
		contentPane.add(instructionButton);
		GameController.startMouseListener(startButton, this);
		GameController.instructionMouseListener(instructionButton);
		contentPane.setPreferredSize(new Dimension(MinesweeperGame.CANVAS_WIDTH, MinesweeperGame.CANVAS_HEIGHT));
		pack();
		setTitle("Mineweeper Menu");
		setVisible(true);
		addWindowListener(this);
	}

	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}
}
