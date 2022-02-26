package game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Instructions extends JDialog implements WindowListener {
	public Instructions() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout(5, 5));
		JTextArea taInstructions = new JTextArea(
				" * The Mine Sweeper Game.\r\n * Left-click to reveal a cell.\r\n * Right-click to plant/remove a flag for marking a suspected mine.\r\n * You win if all the cells not containing mines are revealed.\r\n * You lose if you reveal a cell containing a mine.");

		JButton btnCloseInstruc = new JButton("Close Instructions");
		GameController.closeMouseListener(btnCloseInstruc, this);
		contentPane.add(taInstructions, "Center");
		contentPane.add(btnCloseInstruc, "South");
		contentPane.setPreferredSize(new Dimension(400, 300));
		pack();
		setTitle("Instructions for Minesweeper game");
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
