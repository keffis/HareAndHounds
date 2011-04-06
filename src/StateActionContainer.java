
public class StateActionContainer {
	public int[] houndsPositions;
	public int harePosition;
	public int stallingCount;
	public int houndMoving;
	public int moveTo;
	
	StateActionContainer(int[] hps, int hp, int sc, int hm, int mt)
	{
		houndsPositions = hps;
		harePosition = hp;
		stallingCount = sc;
		houndMoving = hm;
		moveTo = mt;
	}
}
