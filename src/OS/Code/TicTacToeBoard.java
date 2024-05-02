package OS.Code;

public class TicTacToeBoard
{
	private int board [];

	private int vectors [] [] =
	    {
		   {0, 1, 2},    // Row 1
	       {3, 4, 5},    // Row 2
	       {6, 7, 8},    // Row 3
	       {0, 3, 6},    // Column 1
	       {1, 4, 7},    // Column 2
	       {2, 5, 8},    // Column 3
	       {0, 4, 8},    // Diagonal 1
	       {2, 4, 6}     // Diagonal 2
	    };

	public TicTacToeBoard()
	{
		this.reset();
	}

	public void reset()
	{
		board = new int[] {2, 2, 2, 2, 2, 2, 2, 2, 2};
	}

	private int getSquare(int index)
	{
		if (index < 0 | index > 8)
		    throw new IllegalArgumentException("index must be 0-9");

		return board[index];
	}

	public int getSquare(String square)
	{
		int index = mapSquareToIndex(square);
		if (index == -1)
			throw new IllegalArgumentException("Invalid square");
		switch (getSquare(index))
		{
			case 3:
				return 1;
			case 5:
				return 2;
			default:
				return 0;
		}
	}

	private int mapSquareToIndex(String square)
	{
		switch (square)
		{
			case "A1":
				return 0;
			case "A2":
				return 1;
			case "A3":
				return 2;
			case "B1":
				return 3;
			case "B2":
				return 4;
			case "B3":
				return 5;
			case "C1":
				return 6;
			case "C2":
				return 7;
			case "C3":
				return 8;
			default:
				return -1;
		}
	}

	private String mapIndexToSquare(int index)
	{
		switch (index)
		{
			case 0:
				return "A1";
			case 1:
				return "A2";
			case 2:
				return "A3";
			case 3:
				return "B1";
			case 4:
				return "B2";
			case 5:
				return "B3";
			case 6:
				return "C1";
			case 7:
				return "C2";
			case 8:
				return "C3";
			default:
				return "";
		}
	}


	public void playAt(String square, int player)
	{
		int index = mapSquareToIndex(square);
		if (index == -1)
			throw new IllegalArgumentException("Invalid square");
		this.playAt(index, player);
	}

	private void playAt(int index, int player)
	{
		if (index < 0 | index > 8)
		    throw new IllegalArgumentException("Square must be 0-8");
		if (player <1 | player > 2)
			throw new IllegalArgumentException("Player must be 1 or 2");
		if (board[index] != 2)
			throw new IllegalArgumentException("Square is not empty.");
		if (player == 1)
			board[index] = 3;
		else
			board[index] = 5;
	}

	public int isGameOver()
	{
		// check for win
		for (int v = 0; v < 8; v++)
		{
			int p = getVectorProduct(v);
			if (p == 27)
			    return 1;      // Player 1 has won
			if (p == 125)
			    return 2;      // Player 2 has won
	    }

	    // check for draw

	    int blankCount = 0;
	    for (int i = 0; i < 9; i++)
	        if (board[i] == 2)
	            blankCount++;
	    if (blankCount == 0)
	        return 3;          // Game is a draw

	    return 0;              // Game is not over
	}

	public String canPlayerWin(int player)
	{
		if (player <1 | player > 2)
		    throw new IllegalArgumentException("player must be 1 or 2");

		boolean playerCanWin = false;

		for (int v = 0; v < 8; v++)
		{
			int p = getVectorProduct(v);
			if (   (player == 1 & p == 18)
			     | (player == 2 & p == 50) )
			{
				if (board[vectors[v][0]] == 2)
				    return mapIndexToSquare(vectors[v][0]);
				if (board[vectors[v][1]] == 2)
				    return mapIndexToSquare(vectors[v][1]);
				if (board[vectors[v][2]] == 2)
				    return mapIndexToSquare(vectors[v][2]);
		    }
		}
		return "";


	}
	private int getVectorProduct(int vector)
	{
		return board[vectors[vector][0]] *
		       board[vectors[vector][1]] *
		       board[vectors[vector][2]];
    }

	public String getNextMove()
	{
		String bestMove;

		// Win if possible
		bestMove = this.canPlayerWin(2);
		if (bestMove != "")
		    return bestMove;

		// Block if necessary
		bestMove = this.canPlayerWin(1);
		if (bestMove != "")
		    return bestMove;

		// Center if it is open
		if (board[4] == 2)
		    return "B2";

		// First open corner
		if (board[0] == 2)
		    return "A1";
		if (board[2] == 2)
		    return "A3";
		if (board[6] == 2)
		    return "C1";
		if (board[8] == 2)
		    return "C3";

		// First open edge
		if (board[1] == 2)
		    return "A2";
		if (board[3] == 2)
		    return "B1";
		if (board[5] == 2)
		    return "B3";
		if (board[7] == 2)
		    return "C2";

		return "";          // The board is full
	}

    public String toString()
	{
		return " " +
		       getMark(board[0]) + " | " +
		       getMark(board[1]) + " | " +
		       getMark(board[2]) +
		       "\n-----------\n" +
		       " " +
		       getMark(board[3]) + " | " +
		       getMark(board[4]) + " | " +
		       getMark(board[5]) +
		       "\n-----------\n" +
		       " " +
		       getMark(board[6]) + " | " +
		       getMark(board[7]) + " | " +
		       getMark(board[8]);
    }


	private String getMark(int status)
	{
		if (status == 3)
		    return "X";
		if (status == 5)
		    return "O";
		return " ";
	}

}
