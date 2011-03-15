import java.util.*;
public class HareAndHounds {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		GameBoard gameBoard = new GameBoard();
		System.out.println(gameBoard.toString());
		System.out.println("HARENS: " + gameBoard.possibleMovesHare());
		System.out.println("HUND 0: " + gameBoard.possibleMovesHound(0));
		System.out.println("HUND 1: " + gameBoard.possibleMovesHound(1));
		System.out.println("HUND 2: " + gameBoard.possibleMovesHound(2));
			
		while(gameBoard.hasWon() == 0)
		{
			gameBoard.moveHounds(0);
			System.out.println("-------------------------------------------------");
			System.out.println(gameBoard.toString());
			Thread.sleep(2000);
			gameBoard.moveHare(0);
			System.out.println("-------------------------------------------------");
			System.out.println(gameBoard.toString());
			Thread.sleep(2000);
		}
		
	}

}
