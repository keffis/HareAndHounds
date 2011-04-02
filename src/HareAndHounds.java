import java.util.*;




public class HareAndHounds {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{

		//init start
		GameBoard gameBoard = new GameBoard();
		Scanner sc = new Scanner(System.in);
		Stats totalStats = new Stats();
		Stats latestStats = new Stats();
		int numberOfGames = 0;
		boolean showGameBoard = false;
		boolean quit = false;
		long timer = 0;


		int houndPlayer = 0;
		HoundPlayer houndPlayers[] = new HoundPlayer[4];
		houndPlayers[0] = new HumanHoundPlayer(gameBoard);
		houndPlayers[1] = new RandomHoundPlayer(gameBoard);
		houndPlayers[2] = new QLearningHoundPlayer1(gameBoard);
		houndPlayers[3] = new QLearningHoundPlayer2(gameBoard);



		int harePlayer = 0;
		HarePlayer harePlayers[] = new HarePlayer[2];
		harePlayers[0] = new HumanHarePlayer(gameBoard);
		harePlayers[1] = new RandomHarePlayer(gameBoard);

		//init end



		System.out.println("VÄLKOMMEN TILL HARALDS OCH KEFFIS HARE AND HOUNDS!!");


		while(!quit)
		{
			int choice = -1;

			//choose hound
			while(choice < 0 || choice > 4)
			{
				System.out.println("Vem ska spela hundarna?");
				System.out.println("1. Human");
				System.out.println("2. Random");
				System.out.println("3. Q-Learning 1");
				System.out.println("4. Q-Learning 2");

				choice = sc.nextInt();

				if(choice > 4 && choice < 0)
					System.out.println("Den hundspelaren finns inte.");
				else
					houndPlayer = choice - 1;
			}
			
			double dchoice = -1.0;
			//if q-learning, input settings
			while(dchoice < 0 && houndPlayer == 2)
			{
				System.out.print("Q-Learning epsilon: ");
				dchoice = Double.parseDouble(sc.next());
				QLearningHoundPlayer1 qlhp = (QLearningHoundPlayer1) houndPlayers[2];
				qlhp.setEpsilon(dchoice);
			}
				


			//choose hare
			choice = -1;
			while(choice < 0 || choice > 2)
			{
				System.out.println("Vem ska spela haren?");
				System.out.println("1. Human");
				System.out.println("2. Random");

				choice = sc.nextInt();

				if(choice > 2 && choice < 0)
					System.out.println("Den harspelaren finns inte.");
				else
					harePlayer = choice - 1;
			}

			//choose number of games
			choice = -1;
			while(choice < 0)
			{
				System.out.print("Hur många spel vill du spela? ");

				choice = sc.nextInt();
				if(choice < 0)
					System.out.println("Endast positiva tal tack! HORA!");
				else
					numberOfGames = choice;
			}

			//choose if you wanna se the gameboard
			choice = -1;
			while(choice < 0 || choice > 2)
			{
				System.out.println("Vill du se spelplanen?");
				System.out.println("1. Ja");
				System.out.println("2. Nej");

				choice = sc.nextInt();
				if(choice < 0 || choice > 2)
				{
					System.out.println("Bara ja eller nej BÖGFITTA!");
					continue;
				}
				if(choice == 1)
					showGameBoard = true;
				else
					showGameBoard = false;
			}


			timer = System.nanoTime();
			
			for(int i = 0; i < numberOfGames; i++)
			{
				gameBoard.reset();
				
				if(showGameBoard)
				{
					int gn = i + 1;
					System.out.println("Spel " + gn + " av " + numberOfGames + ".");
					System.out.println(gameBoard.toString());
					Thread.sleep(2000);
				}

				
					
				while(gameBoard.hasWon() == 0)
				{

					if(gameBoard.getHoundsTurn())
					{
						if(showGameBoard)
						{
							System.out.println("HUNDARNAS TUR");
							if(houndPlayer == 2)
							{
								QLearningHoundPlayer1 qlhp = (QLearningHoundPlayer1) houndPlayers[2];
								qlhp.printCurrentQ();
							}
							
						}
						
						

						houndPlayers[houndPlayer].play();

						if(showGameBoard)
						{
							System.out.println("-------------------------------------------------");
							System.out.println(gameBoard.toString());
							Thread.sleep(1000);
						}

					}
					else
					{
						if(showGameBoard)
							System.out.println("HARENS TUR");

						harePlayers[harePlayer].play();

						if(showGameBoard)
						{
							System.out.println("-------------------------------------------------");
							System.out.println(gameBoard.toString());
							Thread.sleep(1000);
						}
					}
				}
				/*
			if(gameBoard.hasWon() == 3)
			{
				System.out.println(gameBoard.toString());
				Thread.sleep(5000);
			}
				 */
				//System.out.println(gameBoard.hasWon());

				if(gameBoard.hasWon() == 1)
				{
					if(showGameBoard)
						System.out.println("Haren vann, han rymde!");
					totalStats.hareEscapeWins++;
					latestStats.hareEscapeWins++;
				}
				if(gameBoard.hasWon() == 2)
				{
					if(showGameBoard)
						System.out.println("Haren vann, hundarna dröjde för länge!");
					totalStats.hareStallingWins++;
					latestStats.hareStallingWins++;
				}
				if(gameBoard.hasWon() == 3)
				{
					if(showGameBoard)
						System.out.println("Hundarna vann, de åt upp haren!");
					totalStats.houndWins++;
					latestStats.houndWins++;
				}
				
				if(showGameBoard)
					Thread.sleep(2000);

			}
			
			totalStats.time += System.nanoTime() - timer;
			latestStats.time = System.nanoTime() - timer;
			System.out.println(totalStats);
			System.out.println(latestStats);
			latestStats.reset();
			

			System.out.println("Sluta spela?");
			System.out.println("1. Ja");
			System.out.println("2. Nej");

			choice = sc.nextInt();

			if(choice == 1)
				quit = true;
			else if(choice == 2)
				quit = false;
			else
				System.out.println("DU E BÖG!");

		}
	}

}
