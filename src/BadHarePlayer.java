import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

/**
 * 
 */

/**
 * @author Keffis
 *
 */
public class BadHarePlayer implements HarePlayer {

	private GameBoard gameBoard;
	
	BadHarePlayer(GameBoard gb)
	{
		gameBoard = gb;
	}
	
	public void play() {
		ArrayList<Integer> pos = gameBoard.possibleMovesHare();
		Collections.sort(pos);
		int m = pos.get(0);
		
		gameBoard.moveHare(m);
	}

}
