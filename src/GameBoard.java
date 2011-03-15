import java.util.*;
/**
 * @author Keffis
 *
 */
public class GameBoard {
	private ArrayList gameGraph[];
	private boolean houndsTurn;
	private int stallingCount;
	private int hare;
	private int hound1;
	private int hound2;
	private int hound3;
	
	
	public GameBoard()
	{
		gameGraph = new ArrayList[11];
		//Sets up the gameboard graph.
		for(int i = 0; i < 11; i++)
		{
			gameGraph[i] = new ArrayList();
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
		hound1 = 1;
		hound2 = 0;
		hound3 = 3;
		
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
			else if(i == hound1)
				sa[i] = "H1";
			else if(i == hound2)
				sa[i] = "H2";
			else if(i == hound3)
				sa[i] = "H3";
			else
				sa[i] = "__";
		}
		
		
		sb.append("     " + sa[1] + "   " + sa[4] + "   " + sa[7] + "     \n");
		sb.append("                       \n");
		sb.append(sa[0] + "   " + sa[2] + "   " + sa[5] + "   " + sa[8] + "   " + sa[10] + "\n");
		sb.append("                       \n");
		sb.append("     " + sa[3] + "   " + sa[6] + "   " + sa[9] + "     \n");
		
	
		
		return sb.toString();
	}
}


