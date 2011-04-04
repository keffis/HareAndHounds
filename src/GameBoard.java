import java.util.*;
/**
 * @author Keffis
 *
 */
public class GameBoard implements Cloneable {
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
		hounds[0] = 0;
		hounds[1] = 1;
		hounds[2] = 3;
		Arrays.sort(hounds);

		houndsTurn = true;
	}
	
	public boolean getHoundsTurn()
	{
		return houndsTurn;
	}
	
	public int getStallingCount()
	{
		return stallingCount;
	}
	
	public int getHarePos()
	{
		return hare;
	}
	
	public int[] getHoundsPos()
	{
		return hounds;
	}
	
	public void setHarePos(int h)
	{
		hare = h;
	}
	
	public void setHoundsPos(int[] h)
	{
		hounds = h;
	}

	public int hasWon()
	{
		if(hareHasWon() == 1)
			return 1; //Hare win by running away
		else if(hareHasWon() == 2)
			return 2; //Hare win by hounds stalling
		else if(houndsHasWon())
			return 3; //Hounds win
		else
			return 0;
	}
	
	private int hareHasWon()
	{
		if(hare == 0)
		{
			return 1;
		}
		if(hare <= 3)
		{
			if(hounds[0] > 3 && hounds[1] > 3 && hounds[2] > 3)
				return 1;
		}
		if(hare <= 6)
		{
			if(hounds[0] > 6 && hounds[1] > 6 && hounds[2] > 6)
				return 1;
		}
		if(stallingCount >= 10)
		{
			return 2;
		}
		return 0;
	}
	
	private boolean houndsHasWon()
	{
		if(possibleMovesHare().size() == 0)
			return true;
		else
			return false;
	}

	public boolean moveHare(int x)
	{
		ArrayList<Integer> pos = possibleMovesHare();
		
		if(!houndsTurn && pos.contains(x))
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
		ArrayList<Integer> pos = possibleMovesHound(h);
		
		if(houndsTurn && pos.contains(x))
		{
			if(houndsMoveIsForward(h, x))
				stallingCount = 0;
			else
				stallingCount++;
			
			//if(stallingCount > 10)
				//System.out.println("SC: " + stallingCount);
			
			hounds[h] = x;
			Arrays.sort(hounds);
			houndsTurn = !houndsTurn;
			return true;
		}
		else
			return false;
	}
	
	public boolean houndsMoveIsForward(int h, int x)
	{
		if(hounds[h] == 0 && x > 0)
			return true;
		else if(hounds[h] <= 3 && x > 3)
			return true;
		else if(hounds[h] <= 6 && x > 6)
			return true;
		else if(hounds[h] <= 9 && x > 9)
			return true;
		else
			return false;
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
	
	public void reset()
	{
		hare = 10;
		hounds[0] = 0;
		hounds[1] = 1;
		hounds[2] = 3;
		Arrays.sort(hounds);
		stallingCount = 0;
		houndsTurn = true;
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
				sa[i] = i + " ";
		}


		sb.append("      " + sa[1] + " -  " + sa[4] + " -  " + sa[7] + " \n");
		sb.append("   /  |  \\  |  /  |  \\ \n");
		sb.append(sa[0] + " -  " + sa[2] + " -  " + sa[5] + " -  " + sa[8] + " -  " + sa[10] + " \n");
		sb.append("   \\  |  /  |  \\  |  / \n");
		sb.append("      " + sa[3] + " -  " + sa[6] + " -  " + sa[9] + " \n");



		return sb.toString();
	}
}


