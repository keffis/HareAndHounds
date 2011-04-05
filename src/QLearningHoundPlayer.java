
public interface QLearningHoundPlayer extends HoundPlayer {
	public void updateQ();
	public void printCurrentQ();
	public boolean qTableCheck();
	public void setEpsilon(double eps);
	public void setAlpha(double alp);
	public void setGamma(double gam);
}
