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
		System.out.println("Möjliga drag hund 0: " + gameBoard.possibleMovesHound(0));
		System.out.println("Möjliga drag hund 1: " + gameBoard.possibleMovesHound(1));
		System.out.println("Möjliga drag hund 2: " + gameBoard.possibleMovesHound(2));
		
		System.out.print("Välj hund och drag: ");
		int hound = sc.nextInt();
		int move = sc.nextInt();
		
		if(hound > 2)
		{
			System.out.print("Den hunden finns inte, gör om: ");
			hound = sc.nextInt();
			move = sc.nextInt();
			
		}
			
		if(!gameBoard.moveHounds(hound, move))
			System.out.println("FEL GÖR OM!");
	}

}
