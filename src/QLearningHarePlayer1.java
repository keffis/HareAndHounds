import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class QLearningHarePlayer1 implements HarePlayer{
	GameBoard gameBoard;

	double q[][][][][][];
	double epsilon;
	double gamma;
	double alpha;

	int chp;
	int[] chsp;

	int php;
	int[] phsp;

	int moveTo;

	int previousPosition;

	int currentStallingCount;
	int previousStallingCount;

	QLearningHarePlayer1(GameBoard gb)
	{
		gameBoard = gb;
		//[hund][hund][hund][hare][stallingcount][hund som ska g�][m�jliga drag] skynet
		q = new double[11][11][11][11][11][11];
		epsilon = 0.5;
		gamma = 0.7;
		alpha = 0.8;


		chp = gameBoard.getHarePos();
		chsp = Arrays.copyOf(gameBoard.getHoundsPos(), 3);
		currentStallingCount = gameBoard.getStallingCount();
		previousStallingCount = gameBoard.getStallingCount();

		php = chp;
		phsp = Arrays.copyOf(chsp, 3);

		moveTo = 0;

		currentStallingCount = 0;
	}

	public void play() {
		Random rand = new Random();	
		updateQ();


		//chose action randomly for current state,else choose the one with max Q value
		if(rand.nextDouble() < epsilon)
		{

			ArrayList<Integer> pos = gameBoard.possibleMovesHare();
			moveTo = pos.get(rand.nextInt(pos.size()));
		}
		else
		{
			double maxQ = Double.NEGATIVE_INFINITY;
			moveTo = 0;
			ArrayList<Integer> lol = new ArrayList<Integer>();


			ArrayList<Integer> arrlist = gameBoard.possibleMovesHare();
			for(Integer m : arrlist)
			{
				if(q[chsp[0]][chsp[1]][chsp[2]][chp][currentStallingCount][m] > maxQ)
				{
					maxQ = q[chsp[0]][chsp[1]][chsp[2]][chp][currentStallingCount][m];
					moveTo = m;
				}
			}


			arrlist = gameBoard.possibleMovesHare();
			for(Integer m : arrlist)
			{
				if(q[chsp[0]][chsp[1]][chsp[2]][chp][currentStallingCount][m] == maxQ)
				{
					lol.add(m);
				}
			}

			if(lol.size() <= 0)
				System.out.println("HARALD E B�G!");
			
			moveTo = lol.get(rand.nextInt(lol.size()));
		}

		phsp = Arrays.copyOf(chsp, 3);
		php = chp;
		previousStallingCount = currentStallingCount;

		gameBoard.moveHare(moveTo);

	}


	public void updateQ()
	{
		chsp = gameBoard.getHoundsPos();
		chp = gameBoard.getHarePos();
		currentStallingCount = gameBoard.getStallingCount();

		double previousq = q[phsp[0]][phsp[1]][phsp[2]][php][previousStallingCount][moveTo];
		double currentmaxq = currentMaxQ();

		q[phsp[0]][phsp[1]][phsp[2]][php][previousStallingCount][moveTo] = previousq + alpha *(reward() + (gamma * currentmaxq) - previousq);
	}

	private double currentMaxQ()
	{
		double max = Double.NEGATIVE_INFINITY;


		ArrayList<Integer> arrlist = gameBoard.possibleMovesHare();
		for(Integer m : arrlist)
		{
			if(q[chsp[0]][chsp[1]][chsp[2]][chp][currentStallingCount][m] >= max)
			{
				max = q[chsp[0]][chsp[1]][chsp[2]][chp][currentStallingCount][m];
			}
		}
		if(max == Double.NEGATIVE_INFINITY)
		{
			return 0.0;
		}
		else
			return max;
	}


	private boolean canMove()
	{
		if(gameBoard.possibleMovesHound(0).size() == 0 && gameBoard.possibleMovesHound(1).size() == 0 &&gameBoard.possibleMovesHound(2).size() == 0)
			return false;
		else
			return true;
	}

	private double reward()
	{
		if(gameBoard.hasWon() == 0)
			return 0.0;
		else if(gameBoard.hasWon() == 1)
			return 1.0;
		else if(gameBoard.hasWon() == 2)
			return 1.0;
		else if(gameBoard.hasWon() == 3)
			return -1.0;
		else
			return 0.0;


		/*
		 * 

		  if(previousGameBoard.hasWon() == 1)
			return -100.0;
		else if(previousGameBoard.hasWon() == 2)
			return -100.0;
		else if(previousGameBoard.hasWon() == 3)
			return 100.0;
		else
			return 0.0;
		 */


		/*
		 * else if(previousGameBoard.getHoundsPos()[0] == 10 || nextGameBoard.getHoundsPos()[1] == 10 || nextGameBoard.getHoundsPos()[2] == 10)
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

	public boolean qTableCheck()
	{
		//int changes = 0;
		for(int i = 0; i < 11; i++)
		{
			for(int j = 0; j < 11; j++)
			{
				for(int k = 0; k < 11; k++)
				{
					for(int l = 0; l < 11; l++)
					{
						for(int m = 0; m < 11; m++)
						{
							for(int n = 0; n < 11; n++)
							{
								//if(q[i][j][k][l][m][n] != 0.0)
								{
									System.out.println("P� plats [" + i + ", " + j + ", " + k + ", " + l + ", " + m + ", " + n + "] st�r det: " + q[i][j][k][l][m][n]);
									//changes++;
								}

							}
						}
					}
				}
			}
		}
		//System.out.println(changes);
		return false;
	}

	public void setEpsilon(double eps)
	{
		epsilon = eps;
	}

	public void setAlpha(double alp)
	{
		alpha = alp;
	}

	public void setGamma(double gam)
	{
		gamma = gam;
	}

}
