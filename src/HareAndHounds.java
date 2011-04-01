import java.util.*;




public class HareAndHounds {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		GameBoard gameBoard = new GameBoard();
		HarePlayer harePlayer;
		HoundPlayer houndPlayer;
		
		harePlayer = new RandomHarePlayer(gameBoard);
		houndPlayer = new RandomHoundPlayer(gameBoard);
		
		
		
		System.out.println(gameBoard.toString());
		System.out.println("HARENS: " + gameBoard.possibleMovesHare());
		System.out.println("HUND 0: " + gameBoard.possibleMovesHound(0));
		System.out.println("HUND 1: " + gameBoard.possibleMovesHound(1));
		System.out.println("HUND 2: " + gameBoard.possibleMovesHound(2));
			
		while(gameBoard.hasWon() == 0)
		{
			
			if(gameBoard.getHoundsTurn())
			{
				System.out.println("HUNDARNAS TUR");
				houndPlayer.play();
				System.out.println("-------------------------------------------------");
				System.out.println(gameBoard.toString());
				Thread.sleep(1000);
			}
			else
			{
				System.out.println("HARENS TUR");
				harePlayer.play();
				System.out.println("-------------------------------------------------");
				System.out.println(gameBoard.toString());
				Thread.sleep(1000);
			}
		}
		
		System.out.println(gameBoard.hasWon());
		
	}

}
