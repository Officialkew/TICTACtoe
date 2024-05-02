package OS.Code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Random;

public class TicTacToe
    extends JFrame
{
	public static void main(String [] args)
	{
		new TicTacToe();
	}

	private JButton btnA1, btnA2, btnA3, btnB1, btnB2, btnB3, btnC1, btnC2, btnC3;

	private JButton btnSave, btnResume;

	private TicTacToeBoard board;

	public TicTacToe()
	{
		// Set up the grid
		this.repaint();
		this.setSize(300,300);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setTitle("Tic-Tac-Toe");
		JPanel panel1 = new JPanel();
	    panel1.setSize(300,300);
	    panel1.setLayout(new GridLayout(3,3));
		this.setUndecorated(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) (dim.width/ (1.2 + new Random().nextFloat() * (2.5 - 1.2))-this.getSize().width/2), (int) (dim.height/2-this.getSize().height/(1.2 + new Random().nextFloat() * (2.5 - 1.2))));
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
	    btnA1 = createButton("A1");
	    btnA2 = createButton("A2");
	    btnA3 = createButton("A3");
	    btnB1 = createButton("B1");
	    btnB2 = createButton("B2");
	    btnB3 = createButton("B3");
	    btnC1 = createButton("C1");
	    btnC2 = createButton("C2");
	    btnC3 = createButton("C3");
		panel1.add(btnA1);
		panel1.add(btnA2);
		panel1.add(btnA3);
		panel1.add(btnB1);
		panel1.add(btnB2);
		panel1.add(btnB3);
		panel1.add(btnC1);
		panel1.add(btnC2);
		panel1.add(btnC3);
	    this.add(panel1,BorderLayout.CENTER);

		JPanel panel2 = new JPanel();

		this.add(panel2,BorderLayout.SOUTH);

	    this.setVisible(true);

		// Start the game
		board = new TicTacToeBoard();

	}

	private JButton createButton(String square)
	{
		JButton btn = new JButton();
		btn.setPreferredSize(new Dimension(50, 50));
		Font f = new Font("Dialog", Font.PLAIN, 72);
		btn.setFont(f);
		btn.addActionListener(e -> btnClick(e, square));
		return btn;
	}

	private void btnClick(ActionEvent e, String square)
	{
		if (board.getSquare(square) != 0)
			return;

		JButton btn = (JButton)e.getSource();
		btn.setText("X");


		board.playAt(square, 1);

		if (board.isGameOver() == 3)
		{
			JOptionPane.showMessageDialog(null,
				"It's a draw!", "Game Over",
				JOptionPane.INFORMATION_MESSAGE);
			resetGame();
			return;
		}

		if (board.isGameOver() == 1)
		{
			JOptionPane.showMessageDialog(null,
				"You beat me!", "Game Over",
				JOptionPane.INFORMATION_MESSAGE);
			resetGame();
			return;
		}

		String computerMove = board.getNextMove();
		board.playAt(computerMove,2);

		switch (computerMove)
		{
			case "A1":
				btnA1.setText("O");
				break;
			case "A2":
				btnA2.setText("O");
				break;
			case "A3":
				btnA3.setText("O");
				break;
			case "B1":
				btnB1.setText("O");
				break;
			case "B2":
				btnB2.setText("O");
				break;
			case "B3":
				btnB3.setText("O");
				break;
			case "C1":
				btnC1.setText("O");
				break;
			case "C2":
				btnC2.setText("O");
				break;
			case "C3":
				btnC3.setText("O");
				break;
		}

		if (board.isGameOver() == 2)
		{
			JOptionPane.showMessageDialog(null,
				"I beat you!", "Game Over",
				JOptionPane.INFORMATION_MESSAGE);
			resetGame();
			return;
		}
	}

	private void resetGame()
	{
		board.reset();
		btnA1.setText("");
		btnA2.setText("");
		btnA3.setText("");
		btnB1.setText("");
		btnB2.setText("");
		btnB3.setText("");
		btnC1.setText("");
		btnC2.setText("");
		btnC3.setText("");
	}

	private void btnSaveClick() throws IOException {
		File file = new File(".","SaveGame.ttt");
		 file.createNewFile();
			SaveGame(file);
		}


	private void btnResumeClick()
	{
		File file = new File(".","SaveGame.ttt");
			ResumeGame(file);
		}


	private void SaveGame(File file)
	{
		PrintWriter out;
		out = openWriter(file);
		WriteOutputLine(out, "A1");
		WriteOutputLine(out, "A2");
		WriteOutputLine(out, "A3");
		WriteOutputLine(out, "B1");
		WriteOutputLine(out, "B2");
		WriteOutputLine(out, "B3");
		WriteOutputLine(out, "C1");
		WriteOutputLine(out, "C2");
		WriteOutputLine(out, "C3");
		out.close();
	}

	private PrintWriter openWriter(File file)
	{
		try
		{
			PrintWriter out =
				new PrintWriter(
					new BufferedWriter(
						new FileWriter(file)), true);
			return out;
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(this,
				"Could not open output file.", "Tic Tac Toe",
				JOptionPane.INFORMATION_MESSAGE);
		}
		return null;
	}

	private void WriteOutputLine(PrintWriter out, String square)
	{
		int value = board.getSquare(square);
		if (value != 0)
			out.println(square + "=" + value);
	}

	private void ResumeGame(File file)
	{
		board.reset();
		BufferedReader in = getReader(file);
		try
		{
			String line = in.readLine();
			while (line != null)
			{
				String square = line.substring(0,2);
				String value = line.substring(3,4);
				if (value.equals("1"))
					board.playAt(square, 1);
				else if (value.equals("2"))
					board.playAt(square, 2);
				line = in.readLine();
			}
			in.close();
		}
		catch (IOException ex)
		{
			JOptionPane.showMessageDialog(this,
				"Error reading file..", "Tic Tac Toe",
				JOptionPane.INFORMATION_MESSAGE);
		}

		MarkButton(btnA1, "A1");
		MarkButton(btnA2, "A2");
		MarkButton(btnA3, "A3");
		MarkButton(btnB1, "B1");
		MarkButton(btnB2, "B2");
		MarkButton(btnB3, "B3");
		MarkButton(btnC1, "C1");
		MarkButton(btnC2, "C2");
		MarkButton(btnC3, "C3");
	}

	private void MarkButton(JButton btn, String square)
	{
		int value = board.getSquare(square);
		if (value == 1)
			btn.setText("X");
		else if (value == 2)
			btn.setText("O");
		else
			btn.setText("");
	}

	private BufferedReader getReader(File file)
	{
		BufferedReader in = null;
		try
		{
			in = new BufferedReader(new FileReader(file));
		}
		catch (FileNotFoundException ex)
		{
			JOptionPane.showMessageDialog(this,
				"Error opening file.", "Tic Tac Toe",
				JOptionPane.INFORMATION_MESSAGE);
		}
		return in;
	}

	private class FileFilter
		extends javax.swing.filechooser.FileFilter
	{
		public boolean accept(File f)
		{
			if (f.isDirectory())
				return true;
			String name = f.getName();
			if (name.matches(".*\\.ttt"))
				return true;
			else
				return false;
		}

		public String getDescription()
		{
			return "Tic-Tac-Toe files (*.ttt)";
		}
	}
}
