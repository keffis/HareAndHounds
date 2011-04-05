
public class Stats {
	String name;
	public int houndWins;
	public int hareEscapeWins;
	public int hareStallingWins;
	public long time;
	public double meanNumberOfMoves;
	public int numberOfGames;
	public int numberOfMoves;
	
	Stats(String n)
	{
		name = n;
		houndWins = 0;
		hareEscapeWins = 0;
		hareStallingWins = 0;
		time = 0;
		meanNumberOfMoves = 0;
		numberOfGames = 0;
		numberOfMoves = 0;
	}
	
	public void reset()
	{
		houndWins = 0;
		hareEscapeWins = 0;
		hareStallingWins = 0;
		time = 0;
		meanNumberOfMoves = 0;
		numberOfGames = 0;
		numberOfMoves = 0;
	}
	
	
	public String toString()
	{
		int hareTotalWins = hareEscapeWins + hareStallingWins;
		double total = houndWins + hareEscapeWins + hareStallingWins;
		double hwp = (houndWins/total) * 100.0;
		double hewp = (hareEscapeWins/total) * 100.0;
		double hswp = (hareStallingWins/total) * 100.0;
		double htwp = hewp + hswp;
		double ms = time / 1000000000.0;
			
		StringBuilder sb = new StringBuilder();
		
		sb.append("\nStatistik (" + name + ")\n");
		sb.append("Tid: " + ms + " sekunder.\n");
		sb.append("Antal drag i snitt: " + meanNumberOfMoves + " drag.\n");
		sb.append("Vinster haren:\t" + hareTotalWins + "\t" + htwp + "%\n");
		sb.append("\tEscape: " + hareEscapeWins + "\t" + hewp + "%\n");
		sb.append("\tStalling: " + hareStallingWins + "\t" + hswp + "%\n");
		sb.append("Vinster hundarna: "+ houndWins + "\t" + hwp + "%\n\n");
		return sb.toString();
	}
}


