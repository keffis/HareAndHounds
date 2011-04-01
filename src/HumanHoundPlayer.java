import java.io.*;
import java.util.*;


public class HumanHoundPlayer implements HoundPlayer {
	private GameBoard gameBoard;
	private Scanner sc;
	
	
	HumanHoundPlayer(GameBoard gb)
	{
		sc = new Scanner(System.in);
		gameBoard = gb;
	}
	
	@Override
	public void play() {
		System.out.println("M�jliga drag hund 0: " + gameBoard.possibleMovesHound(0));
		System.out.println("M�jliga drag hund 1: " + gameBoard.possibleMovesHound(1));
		System.out.println("M�jliga drag hund 2: " + gameBoard.possibleMovesHound(2));
		
		System.out.print("V�lj hund och drag: ");
		int hound = sc.nextInt();
		int move = sc.nextInt();
		
		if(hound > 2)
		{
			System.out.print("Den hunden finns inte, g�r om: ");
			hound = sc.nextInt();
			move = sc.nextInt();
			
		}
			
		if(!gameBoard.moveHounds(hound, move))
			System.out.println("FEL G�R OM!");
	}

}
