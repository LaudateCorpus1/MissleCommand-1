public class Building
{
	int hits;
	boolean active;
	int xRect;
	int yRect;
	int wRect;
	int hRect;

	public Building(int rect_x, int rect_y, int width, int height)
	{
		xRect=rect_x;
		yRect=rect_y;
		wRect=width;
		hRect=height;
		hits=0;
		active=true;

	}

	public int getX()
	{
		return xRect;
	}

	public int getY()
	{
		return yRect;
	}

	public int getWidth()
	{
		return wRect;
	}

	public int getHeight()
	{
		return hRect;
	}

	public int getHits()
	{
		return hits;
	}
	public int incHit()
	{
		if(hits<2)
		{
			hits++;
			if (hits==2)
			 active=false;
		}



		return hits;

	}

	public boolean changeState()
	{
		active=false;

		return active;
	}

	public void reset()
	{
		active=true;
		hits=0;
	}

	public boolean isActive()
	{
		return active;
	}

	public String toString() {
		String state;
		if (isActive())
		 return "alive "+hits;
		else
		 return "dead  "+hits;
	}
}