public class Round
{
	int xCoor;
	int yCoor;
	int size;
	boolean growLarger=true;

	public Round(int xLoc, int yLoc, int s)
	{
		xCoor=xLoc;
		yCoor=yLoc;
		size=s;
	}
	public boolean grow()
	{
		return growLarger;
	}
	public void setGrow(boolean g)
	{
		growLarger=g;
	}
	public int getX()
	{
		return xCoor;
	}

	public int getY()
	{
		return yCoor;
	}

	public int getSize()
	{
		return size;
	}

	public void getBigger()
	{
		size+=2;
	}

	public void getSmaller()
	{
		size-=2;
	}


}