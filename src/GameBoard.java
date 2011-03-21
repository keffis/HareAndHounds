import java.util.*;
/**
 * @author Keffis
 *
 */
public class GameBoard {
	private ArrayList<Integer> gameGraph[];
	private boolean houndsTurn;
	private int stallingCount;
	private int hare;
	private int[] hounds;



	public GameBoard()
	{
		gameGraph = new ArrayList[11];
		//Sets up the gameboard graph.
		for(int i = 0; i < 11; i++)
		{
			gameGraph[i] = new ArrayList<Integer>();
		}
		//node 0
		gameGraph[0].add(1);
		gameGraph[0].add(2);
		gameGraph[0].add(3);
		//node 1
		gameGraph[1].add(0);
		gameGraph[1].add(2);
		gameGraph[1].add(4);
		gameGraph[1].add(5);
		//node 2
		gameGraph[2].add(0);
		gameGraph[2].add(1);
		gameGraph[2].add(3);
		gameGraph[2].add(5);
		//node 3
		gameGraph[3].add(0);
		gameGraph[3].add(2);
		gameGraph[3].add(5);
		gameGraph[3].add(6);
		//node 4
		gameGraph[4].add(1);
		gameGraph[4].add(5);
		gameGraph[4].add(7);
		//node 5
		gameGraph[5].add(1);
		gameGraph[5].add(2);
		gameGraph[5].add(3);
		gameGraph[5].add(4);
		gameGraph[5].add(6);
		gameGraph[5].add(7);
		gameGraph[5].add(8);
		gameGraph[5].add(9);
		//node 6
		gameGraph[6].add(3);
		gameGraph[6].add(5);
		gameGraph[6].add(9);
		//node 7
		gameGraph[7].add(4);
		gameGraph[7].add(5);
		gameGraph[7].add(8);
		gameGraph[7].add(10);
		//node 8
		gameGraph[8].add(5);
		gameGraph[8].add(7);
		gameGraph[8].add(9);
		gameGraph[8].add(10);
		//node 9
		gameGraph[9].add(5);
		gameGraph[9].add(6);
		gameGraph[9].add(8);
		gameGraph[9].add(10);
		//node 10
		gameGraph[10].add(7);
		gameGraph[10].add(8);
		gameGraph[10].add(9);

		//place play pieces
		hare = 10;
		hounds = new int[3];
		hounds[0] = 1;
		hounds[1] = 0;
		hounds[2] = 3;

		houndsTurn = true;
	}

	public int hasWon()
	{
		if(hareHasWon())
			return 1;
		else if(possibleMovesHare().size() == 0)
			return 2; //Hounds win
		else
			return 0;
	}

	private boolean hareHasWon()
	{
		if(stallingCount >= 10)
			return true;
		else
			return false;
	}

	public boolean moveHare(int x)
	{
		if(!houndsTurn)
		{
			hare = x;
			houndsTurn = !houndsTurn;
			return true;
		}
		else
			return false;
	}

	public boolean moveHounds(int h, int x)
	{
		if(houndsTurn)
		{
			if(!houndsMoveIsForward())
				stallingCount++;
			else
				stallingCount = 0;
			
			hounds[h] = x;
			houndsTurn = !houndsTurn;
			return true;
		}
		else
			return false;
	}
	
	private boolean houndsMoveIsForward()
	{
		return true;
	}

	public ArrayList<Integer> possibleMovesHare()
	{
		ArrayList<Integer> nei = gameGraph[hare];
		ArrayList<Integer> moves = new ArrayList<Integer>();

		for(Integer i : nei)
		{
			if(!(i == hounds[0] || i == hounds[1] || i == hounds[2]))
				moves.add(i);			
		}
		return moves;
	}

	public ArrayList<Integer> possibleMovesHound(int hound)
	{
		ArrayList<Integer> nei = gameGraph[hounds[hound]];
		ArrayList<Integer> moves = new ArrayList<Integer>();

		for(Integer i : nei)
		{
			if(hounds[hound] == 1 && i == 0)
				continue;
			else if(hounds[hound] == 10 && i == 9)
				continue;
			else if((hounds[hound] - i) > 1)
				continue;			
			else if(i == hare || i == hounds[0] || i == hounds[1] || i == hounds[2])
				continue;
			else
				moves.add(i);			
		}

		return moves;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		String sa[] = new String[11];

		for(int i = 0; i < gameGraph.length; i++)
		{
			if(i == hare)
				sa[i] = "X ";
			else if(i == hounds[0])
				sa[i] = "H0";
			else if(i == hounds[1])
				sa[i] = "H1";
			else if(i == hounds[2])
				sa[i] = "H2";
			else
				sa[i] = "# ";
		}


		sb.append("      " + sa[1] + " -  " + sa[4] + " -  " + sa[7] + " \n");
		sb.append("   /  |  \\  |  /  |  \\ \n");
		sb.append(sa[0] + " -  " + sa[2] + " -  " + sa[5] + " -  " + sa[8] + " -  " + sa[10] + " \n");
		sb.append("   \\  |  /  |  \\  |  / \n");
		sb.append("      " + sa[3] + " -  " + sa[6] + " -  " + sa[9] + " \n");



		return sb.toString();
	}
}


