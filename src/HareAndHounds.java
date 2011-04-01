import java.util.*;




public class HareAndHounds {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{

		//init start
		GameBoard gameBoard = new GameBoard();
		Scanner sc = new Scanner(System.in);
		Stats stats = new Stats();
		int numberOfGames = 0;
		boolean showGameBoard = false;
		boolean quit = false;


		int houndPlayer = 0;
		HoundPlayer houndPlayers[] = new HoundPlayer[2];
		houndPlayers[0] = new HumanHoundPlayer(gameBoard);
		houndPlayers[1] = new RandomHoundPlayer(gameBoard);



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
			while(choice < 0 || choice > 2)
			{
				System.out.println("Vem ska spela hundarna?");
				System.out.println("1. Human");
				System.out.println("2. Random");

				choice = sc.nextInt();

				if(choice > 2 && choice < 0)
					System.out.println("Den hundspelaren finns inte.");
				else
					houndPlayer = choice - 1;
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
			while(choice < 0 && choice > 2)
			{
				System.out.print("Vill du se spelplanen?");
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



			for(int i = 0; i < numberOfGames; i++)
			{
				gameBoard.reset();
				if(showGameBoard)
					System.out.println(gameBoard.toString());

				while(gameBoard.hasWon() == 0)
				{

					if(gameBoard.getHoundsTurn())
					{
						if(showGameBoard)
							System.out.println("HUNDARNAS TUR");

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
					stats.hareEscapeWins++;
				if(gameBoard.hasWon() == 2)
					stats.hareStallingWins++;
				if(gameBoard.hasWon() == 3)
					stats.houndWins++;

			}
			
			System.out.println(stats);

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
