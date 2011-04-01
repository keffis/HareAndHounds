import java.util.ArrayList;
import java.util.Random;

/**
 * 
 */

/**
 * @author Keffis
 *
 */
public class RandomHarePlayer implements HarePlayer {

	private GameBoard gameBoard;
	
	RandomHarePlayer(GameBoard gb)
	{
		gameBoard = gb;
	}
	
	@Override
	public void play() {
		Random rand = new Random();
		ArrayList<Integer> pos = gameBoard.possibleMovesHare();
		int m = pos.get(rand.nextInt(pos.size()));
		
		gameBoard.moveHare(m);
	}

}
