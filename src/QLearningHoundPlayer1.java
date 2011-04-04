import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class QLearningHoundPlayer1 implements HoundPlayer {
	GameBoard gameBoard;
	GameBoard nextGameBoard;

	double q[][][][][][];
	double epsilon;
	double gamma;
	double alpha;

	int chp;
	int[] chsp;

	int nhp;
	int[] nhsp;
	
	int houndMoving;
	int position;
	
	int nextHoundMoving;
	int nextPosition;
	
	double currentq;
	double nextmaxq;

	QLearningHoundPlayer1(GameBoard gb)
	{
		gameBoard = gb;
		nextGameBoard = new GameBoard();
		//[hund][hund][hund][hare][hund som ska gå][möjliga drag] skynet
		q = new double[11][11][11][11][3][11];
		epsilon = 0.5;
		gamma = 0.5;
		alpha = 0.5;
		
		
		chp = gameBoard.getHarePos();
		chsp = Arrays.copyOf(gameBoard.getHoundsPos(), 3);

		nhp = chp;
		nhsp = Arrays.copyOf(chsp, 3);

		houndMoving = 0;
		position = 0;
		
		nextHoundMoving = 0;
		nextPosition = 0;
		
		currentq = 0.0;
		nextmaxq = 0.0;
	}

	public void play() {
		Random rand = new Random();
		
		//while(canMove())
		{
			chp = gameBoard.getHarePos();
			nextGameBoard.setHarePos(chp);
			
			currentq = q[chsp[0]][chsp[1]][chsp[2]][chp][houndMoving][position];
			nextmaxq = q[nhsp[0]][nhsp[1]][nhsp[2]][chp][nextHoundMoving][nextPosition];
			
			currentq = currentq + alpha * (reward() + gamma*(nextmaxq) - currentq);
			
			q[chsp[0]][chsp[1]][chsp[2]][chp][houndMoving][position] = currentq;
			
			chp = gameBoard.getHarePos();
			chsp = Arrays.copyOf(gameBoard.getHoundsPos(), 3);

			nhp = chp;
			nhsp = Arrays.copyOf(chsp, 3);

			

			
			//chose action randomly for current state,else choose the one with max Q value
			if(rand.nextDouble() <= epsilon)
			{
				houndMoving = rand.nextInt(3);
				
				ArrayList<Integer> pos = gameBoard.possibleMovesHound(houndMoving);
				while(canMove() && pos.size() == 0)
				{
					houndMoving = rand.nextInt(3);
					
					pos = gameBoard.possibleMovesHound(houndMoving);
				}
				
				position = pos.get(rand.nextInt(pos.size()));
				nhsp[houndMoving] = position;
				Arrays.sort(nhsp);

				nextGameBoard.setHoundsPos(nhsp);


			}
			else
			{
				double max = q[chsp[0]][chsp[1]][chsp[2]][chp][0][0];
				houndMoving = 0;
				position = 0;

				for(int i = 0; i < 3; i++)
				{
					ArrayList<Integer> arrlist = gameBoard.possibleMovesHound(i);
					for(Integer j : arrlist)
					{
						if(q[chsp[0]][chsp[1]][chsp[2]][chp][i][j] >= max)
						{
							max = q[chsp[0]][chsp[1]][chsp[2]][chp][i][j];
							houndMoving = i;
							position = j;
						}
					}
				}
				
				nhsp[houndMoving] = position;
				Arrays.sort(nhsp);

				nextGameBoard.setHoundsPos(nhsp);
			}
			
			//max future value
			double maxnext = q[nhsp[0]][nhsp[1]][nhsp[2]][chp][0][0];

			for(int i = 0; i < 3; i++)
			{
				ArrayList<Integer> arrlist = nextGameBoard.possibleMovesHound(i);
				for(Integer j : arrlist)
				{
					if(q[nhsp[0]][nhsp[1]][nhsp[2]][chp][i][j] >= maxnext)
					{
						maxnext = q[nhsp[0]][nhsp[1]][nhsp[2]][chp][i][j];
						nextHoundMoving = i;
						nextPosition = j;
					}
				}
			}
			
			
			
			gameBoard.moveHounds(houndMoving, position);
			
			if(gameBoard.hasWon() == 3)
			{
				System.out.println("HEJSAN!");
				currentq = q[chsp[0]][chsp[1]][chsp[2]][chp][houndMoving][position];
				nextmaxq = q[nhsp[0]][nhsp[1]][nhsp[2]][chp][nextHoundMoving][nextPosition];
				currentq = currentq + alpha * (100.0 + gamma*(nextmaxq) - currentq);
				q[chsp[0]][chsp[1]][chsp[2]][chp][houndMoving][position] = currentq;
			}
		}




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
		if(gameBoard.hasWon() == 1)
		{
			System.out.println("HAREN VANN");
			return -100.0;
		}
		else if(gameBoard.hasWon() == 2)
		{
			System.out.println("HAREN VANN");
			return -100.0;
		}
		else if(gameBoard.hasWon() == 3)
		{
			System.out.println("HUNDANDA VANN!");
			return 100.0;
		}
		else
			return 0.0;
		
		
		/*
		 * 
		 
		  if(nextGameBoard.hasWon() == 1)
			return -100.0;
		else if(nextGameBoard.hasWon() == 2)
			return -100.0;
		else if(nextGameBoard.hasWon() == 3)
			return 100.0;
		else
			return 0.0;
		 */
		
		
		/*
		 * else if(nextGameBoard.getHoundsPos()[0] == 10 || nextGameBoard.getHoundsPos()[1] == 10 || nextGameBoard.getHoundsPos()[2] == 10)
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
				System.out.print(q[chsp[0]][chsp[1]][chsp[2]][chp][i][j] + ", ");
			}
			System.out.println(" ");
		}
	}
	
	void setEpsilon(double eps)
	{
		epsilon = eps;
	}

}
