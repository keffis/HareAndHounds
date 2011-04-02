import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class QLearningHoundPlayer2 implements HoundPlayer {
	GameBoard gameBoard;
	GameBoard rewardCalc;

	double q[][][][][][];
	double epsilon;
	double gamma;
	double alpha;

	int currentHarePos;
	int[] currentHoundsPos;

	int nextHarePos;
	int[] nextHoundsPos;
	
	int houndMoving;
	int position;
	
	int nextHoundMoving;
	int nextPosition;

	QLearningHoundPlayer2(GameBoard gb)
	{
		gameBoard = gb;
		rewardCalc = new GameBoard();
		//[hund][hund][hund][hare][hund som ska gå][möjliga drag] skynet
		q = new double[11][11][11][11][3][11];
		epsilon = 0.5;
		gamma = 0.95;
		alpha = 0.95;
		
		
		currentHarePos = gameBoard.getHarePos();
		currentHoundsPos = Arrays.copyOf(gameBoard.getHoundsPos(), 3);

		nextHarePos = currentHarePos;
		nextHoundsPos = Arrays.copyOf(currentHoundsPos, 3);

		houndMoving = 0;
		position = 0;
		
		nextHoundMoving = 0;
		nextPosition = 0;
	}

	public void play() {
		Random rand = new Random();
		
		
	}

	/*
	private double qFunction()
	{
		if()
	}
	 */

	private boolean canMove()
	{
		if(gameBoard.possibleMovesHound(0).size() == 0 && gameBoard.possibleMovesHound(1).size() == 0 &&gameBoard.possibleMovesHound(2).size() == 0)
			return false;
		else
			return true;
	}

	private double reward()
	{
		if(rewardCalc.hasWon() == 1)
			return -100.0;
		else if(rewardCalc.hasWon() == 2)
			return -100.0;
		else if(rewardCalc.hasWon() == 3)
			return 100.0;
		else
			return 0.0;
		
		
		/*
		 * else if(rewardCalc.getHoundsPos()[0] == 10 || rewardCalc.getHoundsPos()[1] == 10 || rewardCalc.getHoundsPos()[2] == 10)
			return -50.0;
		 */
	}
	
	public void printCurrentQ()
	{
		for(int i = 0; i < 3; i++)
		{
			System.out.println("Q values for hound " + i);
			
			for(int j = 0; j < 11; j++)
			{
				System.out.print(q[currentHoundsPos[0]][currentHoundsPos[1]][currentHoundsPos[2]][currentHarePos][i][j] + ", ");
			}
			System.out.println(" ");
		}
	}
	
	void setEpsilon(double eps)
	{
		epsilon = eps;
	}

}
