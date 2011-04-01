import java.util.ArrayList;
import java.util.Random;


/**
 * @author Keffis
 *
 */
public class RandomHoundPlayer implements HoundPlayer {

	private GameBoard gameBoard;
	
	RandomHoundPlayer(GameBoard gb)
	{
		gameBoard = gb;
	}
	
	@Override
	public void play() {
		Random rand = new Random();
		int h = rand.nextInt(3);
		ArrayList<Integer> pos = gameBoard.possibleMovesHound(h);

		while(canMove() && pos.size() == 0)
		{
			h = rand.nextInt(3);
			pos = gameBoard.possibleMovesHound(h);
		}
		
		if(canMove())
		{
		int m = pos.get(rand.nextInt(pos.size()));
		gameBoard.moveHounds(h, m);
		}
	}
	
	private boolean canMove()
	{
		
		
		if(gameBoard.possibleMovesHound(0).size() == 0 && gameBoard.possibleMovesHound(1).size() == 0 &&gameBoard.possibleMovesHound(2).size() == 0)
			return false;
		else
			return true;
	}


}
