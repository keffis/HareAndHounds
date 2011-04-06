import java.util.*;




public class HareAndHounds {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{

		//init start
		GameBoard gameBoard = new GameBoard();
		Scanner sc = new Scanner(System.in);
		Stats totalStats = new Stats("Total");
		Stats latestStats = new Stats("Senaste");
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
		HarePlayer harePlayers[] = new HarePlayer[4];
		harePlayers[0] = new HumanHarePlayer(gameBoard);
		harePlayers[1] = new RandomHarePlayer(gameBoard);
		harePlayers[2] = new BadHarePlayer(gameBoard);
		harePlayers[3] = new QLearningHarePlayer1(gameBoard);

		//init end



		System.out.println("VÄLKOMMEN TILL HARALDS OCH KEFFIS HARE AND HOUNDS!!");


		while(!quit)
		{
			int choice = -1;
			double echoice = -1.0;
			double achoice = -1.0;
			double gchoice = -1.0;

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

			echoice = -1.0;
			achoice = -1.0;
			gchoice = -1.0;
			//if q-learning, input settings
			while((echoice < 0 && achoice < 0 && gchoice < 0) && (houndPlayer == 2 || houndPlayer == 3))
			{
				System.out.print("Q-Learning epsilon: ");
				echoice = Double.parseDouble(sc.next());
				System.out.print("Q-Learning alpha: ");
				achoice = Double.parseDouble(sc.next());
				System.out.print("Q-Learning gamma: ");
				gchoice = Double.parseDouble(sc.next());
				QLearningHoundPlayer qlhp = (QLearningHoundPlayer) houndPlayers[houndPlayer];
				qlhp.setEpsilon(echoice);
				qlhp.setAlpha(achoice);
				qlhp.setGamma(gchoice);
			}



			//choose hare
			choice = -1;
			while(choice < 0 || choice > 4)
			{
				System.out.println("Vem ska spela haren?");
				System.out.println("1. Human");
				System.out.println("2. Random");
				System.out.println("3. Bad");
				System.out.println("4. Q-Learning 1");

				choice = sc.nextInt();

				if(choice > 4 && choice < 0)
					System.out.println("Den harspelaren finns inte.");
				else
					harePlayer = choice - 1;
			}

			echoice = -1.0;
			achoice = -1.0;
			gchoice = -1.0;
			//if q-learning, input settings
			while((echoice < 0 && achoice < 0 && gchoice < 0) && (harePlayer == 3))
			{
				System.out.print("Q-Learning epsilon: ");
				echoice = Double.parseDouble(sc.next());
				System.out.print("Q-Learning alpha: ");
				achoice = Double.parseDouble(sc.next());
				System.out.print("Q-Learning gamma: ");
				gchoice = Double.parseDouble(sc.next());
				QLearningHarePlayer1 qlhp = (QLearningHarePlayer1) harePlayers[3];
				qlhp.setEpsilon(echoice);
				qlhp.setAlpha(achoice);
				qlhp.setGamma(gchoice);
			}


			//choose number of games
			choice = -1;
			while(choice < 0)
			{
				System.out.print("Hur många spel vill du spela? ");

				choice = sc.nextInt();
				if(choice < 0)
					System.out.println("Endast positiva tal tack!");
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
					System.out.println("Bara ja eller nej!");
					continue;
				}
				if(choice == 1)
					showGameBoard = true;
				else
					showGameBoard = false;
			}


			timer = System.nanoTime();
			totalStats.numberOfGames += numberOfGames;
			latestStats.numberOfGames += numberOfGames;

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
							if(houndPlayer == 2 || houndPlayer == 3)
							{
								QLearningHoundPlayer qlhp = (QLearningHoundPlayer) houndPlayers[houndPlayer];
								qlhp.printCurrentQ();
							}

						}

						houndPlayers[houndPlayer].play();
						totalStats.numberOfMoves++;
						latestStats.numberOfMoves++;

						if(showGameBoard)
						{
							System.out.println("-------------------------------------------------");
							System.out.println(gameBoard.toString());
							Thread.sleep(1000);
						}
						continue;

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
						continue;
					}

				}

				if(gameBoard.hasWon() == 1)
				{
					if(showGameBoard)
						System.out.println("Haren vann, han rymde!");
					totalStats.hareEscapeWins++;
					latestStats.hareEscapeWins++;
					if(houndPlayer == 2 || houndPlayer == 3)
					{
						QLearningHoundPlayer qlhp = (QLearningHoundPlayer) houndPlayers[houndPlayer];
						qlhp.updateQ();
					}
					
					if(harePlayer == 3)
					{
						QLearningHarePlayer1 qlhp = (QLearningHarePlayer1) harePlayers[harePlayer];
						qlhp.updateQ();
					}
				}
				if(gameBoard.hasWon() == 2)
				{
					if(showGameBoard)
						System.out.println("Haren vann, hundarna dröjde för länge!");
					totalStats.hareStallingWins++;
					latestStats.hareStallingWins++;
					
					if(harePlayer == 3)
					{
						QLearningHarePlayer1 qlhp = (QLearningHarePlayer1) harePlayers[harePlayer];
						qlhp.updateQ();
					}
				}
				if(gameBoard.hasWon() == 3)
				{
					if(showGameBoard)
						System.out.println("Hundarna vann, de åt upp haren!");
					totalStats.houndWins++;
					latestStats.houndWins++;
					
					if(harePlayer == 3)
					{
						QLearningHarePlayer1 qlhp = (QLearningHarePlayer1) harePlayers[harePlayer];
						qlhp.updateQ();
					}
				}

				if(showGameBoard)
					Thread.sleep(2000);

			}

			totalStats.time += System.nanoTime() - timer;
			latestStats.time = System.nanoTime() - timer;
			totalStats.meanNumberOfMoves = (double)totalStats.numberOfMoves/(double)totalStats.numberOfGames;
			latestStats.meanNumberOfMoves = (double)latestStats.numberOfMoves/(double)latestStats.numberOfGames;


			System.out.println(totalStats);
			System.out.println(latestStats);
			latestStats.reset();

			if((houndPlayer == 2 || houndPlayer == 3))
			{
				QLearningHoundPlayer qlhp = (QLearningHoundPlayer) houndPlayers[houndPlayer];
				qlhp.qTableCheck();
			}
			
			if((harePlayer == 3))
			{
				QLearningHarePlayer1 qlhp = (QLearningHarePlayer1) harePlayers[harePlayer];
				//qlhp.qTableCheck();
			}


			System.out.println("Sluta spela?");
			System.out.println("1. Ja");
			System.out.println("2. Nej");

			choice = sc.nextInt();

			if(choice == 1)
				quit = true;
			else 
				quit = false;
			

		}
	}

}
