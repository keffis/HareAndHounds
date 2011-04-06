import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Random;


public class QLearningHoundPlayer2 implements QLearningHoundPlayer {
	private GameBoard gameBoard;
	private LinkedList<StateActionContainer> log;
	
	double q[][][][][][][];
	double epsilon;
	double gamma;
	double alpha;

	int chp;
	int[] chsp;
	int currentStallingCount;
	
	int houndMoving;
	int moveTo;
	
	int logCount;
	


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

		houndMoving = 0;
		moveTo = 0;
		
		currentStallingCount = 0;
	}

	public void play() {
		Random rand = new Random();	
		//updateQ();
		
		
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
		
		StateActionContainer sac = new StateActionContainer(Arrays.copyOf(chsp, 3), chp, currentStallingCount, houndMoving, moveTo);
		log.addFirst(sac);
		
		gameBoard.moveHounds(houndMoving, moveTo);
		
		
		if(gameBoard.hasWon() == 2 || gameBoard.hasWon() == 3)
			updateQ();
		
	}


	public void updateQ()
	{
		double reward = reward();
		StateActionContainer a = log.pop();
		double prev = q[a.houndsPositions[0]][a.houndsPositions[1]][a.houndsPositions[2]][a.harePosition][a.stallingCount][a.houndMoving][a.moveTo];
		double prevMaxQ = 0.0;
		
		q[a.houndsPositions[0]][a.houndsPositions[1]][a.houndsPositions[2]][a.harePosition][a.stallingCount][a.houndMoving][a.moveTo] =
			prev + alpha *(reward + (gamma * prevMaxQ) - prev);
		
		prevMaxQ = q[a.houndsPositions[0]][a.houndsPositions[1]][a.houndsPositions[2]][a.harePosition][a.stallingCount][a.houndMoving][a.moveTo];
		
		for(StateActionContainer sac : log)
		{	
			prev = q[sac.houndsPositions[0]][sac.houndsPositions[1]][sac.houndsPositions[2]][sac.harePosition][sac.stallingCount][sac.houndMoving][sac.moveTo];
			
			q[sac.houndsPositions[0]][sac.houndsPositions[1]][sac.houndsPositions[2]][sac.harePosition][sac.stallingCount][sac.houndMoving][sac.moveTo] =
				prev + alpha *(0 + (gamma * prevMaxQ) - prev);
			
			prevMaxQ = q[sac.houndsPositions[0]][sac.houndsPositions[1]][sac.houndsPositions[2]][sac.harePosition][sac.stallingCount][sac.houndMoving][sac.moveTo];
			

		}
		
		log.clear();
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
		if(gameBoard.possibleMovesHound(0).size() == 0 && gameBoard.possibleMovesHound(1).size() == 0 && gameBoard.possibleMovesHound(2).size() == 0)
			return false;
		else
			return true;
	}

	private double reward()
	{
		if(gameBoard.hasWon() == 0)
			return -1.0;
		else if(gameBoard.hasWon() == 1)
			return -50.0;
		else if(gameBoard.hasWon() == 2)
			return -50.0;
		else if(gameBoard.hasWon() == 3)
			return 1000.0;
		else
			return 0.0;

	}

	public void printCurrentQ()
	{
		for(int i = 0; i < 3; i++)
		{
			System.out.println("Q values for hound " + i);

			for(int j = 0; j < 11; j++)
			{
				System.out.print(q[chsp[0]][chsp[1]][chsp[2]][chp][currentStallingCount][i][j] + ", ");
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
