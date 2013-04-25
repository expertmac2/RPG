package me.expertmac2.rpg.command;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;

import me.expertmac2.rpg.Game;

public class ConsoleWindow extends JFrame {

	private JPanel contentPane;
	private JTextPane txtpnConsole;
	private JTextField textField;
	private final Game game;

	/**
	 * Create the frame.
	 */
	public ConsoleWindow(Game g) {

		game = g;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 523, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened( WindowEvent e ){
				textField.requestFocus();
			}
		});

		txtpnConsole = new JTextPane();
		txtpnConsole.setEditable(false);
		txtpnConsole.setText("RPG Console\n=============\n");

		JScrollPane scrollPane = new JScrollPane(txtpnConsole);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 11, 487, 242);
		contentPane.add(scrollPane);

		textField = new JTextField();
		textField.setBounds(10, 264, 487, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CommandHandler.handleCommand(game, game.gameContainer, textField.getText());
				textField.setText("");
			}
		});
	}

	public void addLineToConsole(String str) {
		txtpnConsole.setText(txtpnConsole.getText() + str + "\n");
		txtpnConsole.setCaretPosition(txtpnConsole.getDocument().getLength());
	}
}
