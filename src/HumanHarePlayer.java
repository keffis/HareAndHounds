import java.io.*;
import java.util.*;


public class HumanHarePlayer implements HarePlayer {
	private GameBoard gameBoard;
	private Scanner sc;
	
	
	HumanHarePlayer(GameBoard gb)
	{
		sc = new Scanner(System.in);
		gameBoard = gb;
	}
	
	@Override
	public void play() {
		System.out.println("M�jliga drag hare: " + gameBoard.possibleMovesHare());
		
		System.out.print("Var ska haren g�: ");
		int move = sc.nextInt();
		if(!gameBoard.moveHare(move))
				System.out.println("FEL G�R OM!");
		
	}

}
