
public class Stats {
	public int houndWins;
	public int hareEscapeWins;
	public int hareStallingWins;
	
	Stats()
	{
		houndWins = 0;
		hareEscapeWins = 0;
		hareStallingWins = 0;
	}
	
	
	public String toString()
	{
		int hareTotalWins = hareEscapeWins + hareStallingWins;
		double total = houndWins + hareEscapeWins + hareStallingWins;
		double hwp = (houndWins/total) * 100.0;
		double hewp = (hareEscapeWins/total) * 100.0;
		double hswp = (hareStallingWins/total) * 100.0;
		double htwp = hewp + hswp;
			
		StringBuilder sb = new StringBuilder();
		
		sb.append("\nStatistik senaste körning:\n");
		sb.append("Vinster haren:\t" + hareTotalWins + "\t" + htwp + "%\n");
		sb.append("\tEscape: " + hareEscapeWins + "\t" + hewp + "%\n");
		sb.append("\tStalling: " + hareStallingWins + "\t" + hswp + "%\n");
		sb.append("Vinster hundarna: "+ houndWins + "\t" + hwp + "%\n\n");
		return sb.toString();
	}
}


