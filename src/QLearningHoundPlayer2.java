import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Random;


public class QLearningHoundPlayer2 implements QLearningHoundPlayer {
	GameBoard gameBoard;
	LinkedList<StateActionContainer> log;
	
	double q[][][][][][][];
	double epsilon;
	double gamma;
	double alpha;

	int chp;
	int[] chsp;

	int php;
	int[] phsp;

	int houndMoving;
	int moveTo;

	int previousHoundMoving;
	int previousPosition;
	
	int currentStallingCount;
	int previousStallingCount;

	QLearningHoundPlayer2(GameBoard gb)
	{
		gameBoard = gb;
		log = new LinkedList<StateActionContainer>();
		
		//[hund][hund][hund][hare][stallingcount][hund som ska gå][möjliga drag] skynet
		q = new double[11][11][11][11][11][3][11];
		epsilon = 0.5;
		gamma = 0.7;
		alpha = 0.8;


		chp = gameBoard.getHarePos();
		chsp = Arrays.copyOf(gameBoard.getHoundsPos(), 3);
		currentStallingCount = gameBoard.getStallingCount();
		previousStallingCount = gameBoard.getStallingCount();
		
		php = chp;
		phsp = Arrays.copyOf(chsp, 3);

		houndMoving = 0;
		moveTo = 0;
		
		currentStallingCount = 0;
	}

	public void play() {
		Random rand = new Random();	
		updateQ();
		
		
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
			moveTo = pos.get(rand.nextInt(pos.size()));
		}
		else
		{
			double maxQ = Double.NEGATIVE_INFINITY;
			houndMoving = 0;
			moveTo = 0;
			ArrayList<MoveContainer> lol = new ArrayList<MoveContainer>();
			
			for(int i = 0; i < 3; i++)
			{
				ArrayList<Integer> arrlist = gameBoard.possibleMovesHound(i);
				for(Integer j : arrlist)
				{
					if(q[chsp[0]][chsp[1]][chsp[2]][chp][currentStallingCount][i][j] > maxQ)
					{
						maxQ = q[chsp[0]][chsp[1]][chsp[2]][chp][currentStallingCount][i][j];
						houndMoving = i;
						moveTo = j;
					}
				}
			}
			
			
			for(int i = 0; i < 3; i++)
			{
				ArrayList<Integer> arrlist = gameBoard.possibleMovesHound(i);
				for(Integer j : arrlist)
				{
					if(q[chsp[0]][chsp[1]][chsp[2]][chp][currentStallingCount][i][j] == maxQ)
					{
						lol.add(new MoveContainer(i, j));
					}
				}
			}
			MoveContainer mc = lol.get(rand.nextInt(lol.size()));
			houndMoving = mc.houndMoving;
			moveTo = mc.moveTo;
		}
		
		
		gameBoard.moveHounds(houndMoving, moveTo);
		
		
		if(gameBoard.hasWon() == 2 || gameBoard.hasWon() == 3)
			updateQ();
		
		phsp = Arrays.copyOf(chsp, 3);
		php = chp;
		previousStallingCount = currentStallingCount;
	}


	public void updateQ()
	{
		chsp = gameBoard.getHoundsPos();
		chp = gameBoard.getHarePos();
		currentStallingCount = gameBoard.getStallingCount();
		
		double previousq = q[phsp[0]][phsp[1]][phsp[2]][php][previousStallingCount][houndMoving][moveTo];
		double currentmaxq = currentMaxQ();

		q[phsp[0]][phsp[1]][phsp[2]][php][previousStallingCount][houndMoving][moveTo] = previousq + alpha *(reward() + (gamma * currentmaxq) - previousq);
	}

	private double currentMaxQ()
	{
		double max = Double.NEGATIVE_INFINITY;

		for(int i = 0; i < 3; i++)
		{
			ArrayList<Integer> arrlist = gameBoard.possibleMovesHound(i);
			for(Integer j : arrlist)
			{
				if(q[chsp[0]][chsp[1]][chsp[2]][chp][currentStallingCount][i][j] >= max)
				{
					max = q[chsp[0]][chsp[1]][chsp[2]][chp][currentStallingCount][i][j];
				}
			}
		}
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
			return -1.0;
		else if(gameBoard.hasWon() == 1)
			return -10.0;
		else if(gameBoard.hasWon() == 2)
			return -10.0;
		else if(gameBoard.hasWon() == 3)
			return 1000.0;
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
		int changes = 0;
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
							for(int n = 0; n < 3; n++)
							{
								for(int o = 0; o < 11; o++)
								{
									if(q[i][j][k][l][m][n][o] != 0.0)
									{
										//System.out.println("På plats [" + i + ", " + j + ", " + k + ", " + l + ", " + m + ", " + n + ", " + o + "] står det: " + q[i][j][k][l][m][n][o]);
										changes++;
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println(changes);
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
