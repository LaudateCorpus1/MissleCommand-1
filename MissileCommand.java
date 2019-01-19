import java.awt.geom.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.math.*;
import java.awt.image.*;
import javax.imageio.*;
import java.applet.*;
import java.util.ArrayList;
import java.awt.geom.Line2D;

public class MissileCommand  extends JPanel implements Runnable, MouseListener
{
	JFrame frame=new JFrame();


	ArrayList<Line> lineList= new ArrayList<Line>();
	ArrayList<Building> buildings = new ArrayList<Building>();







double r=0;
int score=0;
int level=1;
BufferedImage bgd;
BufferedImage img;
int chk_gm_over;
int ufo_count=0;
int split_count=0;
Line ufo_path;
int ufo_x, ufo_y, ufo_x2, ufo_y2;
int go_once=0;
boolean show_ufo;






	ArrayList<Round> circles=new ArrayList<Round>();
	ArrayList<Round> explosions = new ArrayList<Round>();
	public MissileCommand()
	{
		lineList.add(new Line( (int)(Math.random()*950)+25,0,(int)(Math.random()*950)+25,800,false));
		frame.add(this);
		frame.setSize(1000,800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMouseListener(this);
		Thread animator = new Thread(this);
		animator.start();

		 Building b1 = new Building(60,680,120,180);
		buildings.add(b1);
		buildings.add(new Building(270,680,120,180));
		buildings.add(new Building(630,680,120,180));
		buildings.add(new Building(840,680,120,180));

		bgd=null;
		img=null;
	//	x1= (int)(Math.random()*853)+126;
	///	y1= (int)(Math.random()*579)+1;
	///	x2= (int)(Math.random()*853)+126;
	//	y2= (int)(Math.random()*579)+1;

		try
		{
			System.out.println("I AM HERE");
			bgd = ImageIO.read(new File("spacepic.jpg"));
			img = ImageIO.read(new File("blueship2.png"));
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
	}

	public void run()
	{

		while (true)
		{

			try
			{

			//	int h=

					// check if the circles have hit any enemy lines
					for (int x=0;x<lineList.size();x++)   //hit detection
					{
						if (!lineList.get(x).getDirection())
						{
							int tip_x=lineList.get(x).getX();
							int tip_y=lineList.get(x).getY();


						for (int y=0;y<circles.size();y++)
						{
							int Xdiff= tip_x-circles.get(y).getX();
							int Ydiff = tip_y-circles.get(y).getY();
							int leftSide= (Xdiff*Xdiff) + (Ydiff*Ydiff);
							int rightSide= (circles.get(y).getSize()/2)*(circles.get(y).getSize()/2);

							//System.out.println("leftSide is "+leftSide);
							//System.out.println("RightSide is "+rightSide);

							if (leftSide<=rightSide)
							{
								System.out.println("REMOVE BETA");
								explosions.add(new Round(tip_x,tip_y,2));
								lineList.remove(x);
								if (score%25==0 && score!=0)
									level++;
								score+=5;
							}

						}

						}
					}

					//  check if UFO should appear
					ufo_count+=1;
				//	System.out.println("UFO COUNT " + ufo_count);
					if (ufo_count > 400 )
					{
						//System.out.println("I AM IN UFO");

						if (go_once==0) {
							ufo_x= (int)(Math.random()*579)+126;
							ufo_y= (int)(Math.random()*579)+1;

							ufo_x2= (int)(Math.random()*579)+126;
							ufo_y2= (int)(Math.random()*579)+1;

							ufo_path=new Line(ufo_x, ufo_y, ufo_x2, ufo_y2, false);
							go_once=1;

							System.out.println("I AM IN UFO GO ONCE");
						}

						show_ufo=true;
						if (ufo_count == 600) {
							lineList.add(new Line(ufo_x,ufo_y,(int)(Math.random()*950)+25,800,false));
						}

						if (ufo_count > 800) {

							ufo_path.update(level,true);
							//System.out.println ("Current " + ufo_path.getY() + "TARGET " + ufo_path.getEndY());
							int diff = Math.abs(ufo_path.getY()-ufo_path.getEndY());
							if (diff <=6 )
							{
								System.out.println("DROPPING SECOND UFO");
								lineList.add(new Line(ufo_path.getX(),ufo_path.getY(),(int)(Math.random()*950)+25,800,false));
								ufo_count=0;
								go_once=0;
								show_ufo=false;
							}
						}
					}


					for(int x=0;x<circles.size();x++)
					{
						if (circles.get(x).grow())
						{

							circles.get(x).getBigger();
							if(circles.get(x).getSize()==80)
								circles.get(x).setGrow(false);
						}
						else
						{
							circles.get(x).getSmaller();
							if(circles.get(x).getSize()==0)
							{
								circles.remove(x);
								x--;

							}

						}
					}

					for(int x=0;x<explosions.size();x++)
					{
						if (explosions.get(x).grow())
						{

							explosions.get(x).getBigger();
							if(explosions.get(x).getSize()==80)
								explosions.get(x).setGrow(false);
						}
						else
						{
								explosions.get(x).getSmaller();
							if(explosions.get(x).getSize()==0)
							{
								explosions.remove(x);
								x--;

							}

						}
					}


					int randomNum=(int)(Math.random()*100)+0;
					split_count++;
					if (split_count == 500)
					{
						int split_index=lineList.size()/2;
						//split line

						int split_center=lineList.get(split_index).getX();
						int split_y=lineList.get(split_index).getY();
						int split_endy=lineList.get(split_index).getEndY();
						int split_x1= (int)(Math.random()*(split_center-1))+0;
						int split_x2= (int)(Math.random()*999)+split_center;
						lineList.add(new Line( split_center,split_y,split_x1,split_endy,false));
						lineList.add(new Line( split_center,split_y,split_x2,split_endy,false));
						lineList.remove(split_index);
						split_count=0;
					}

					for(int x=0;x<lineList.size();x++)
					{
						lineList.get(x).update(level,false);

						if (lineList.get(x).getY()==500) //fix bug here
						{
							lineList.add(new Line( (int)(Math.random()*950)+25,0,(int)(Math.random()*950)+25,800,false));
						}


						// check if friendly line has reached the mouse click position, if so create circle
						if(!lineList.get(x).getDirection() && (lineList.get(x).getY()==800 || lineList.get(x).getEndY()<=lineList.get(x).getY()))
						{
							circles.add(new Round(lineList.get(x).getX(),lineList.get(x).getY(),2));
							lineList.remove(x);
							x--;
						}
						else if(lineList.get(x).getDirection() && lineList.get(x).getEndY()>=lineList.get(x).getY())
							{
								circles.add(new Round(lineList.get(x).getX(),lineList.get(x).getY(),2));
								lineList.remove(x);
								x--;
							}


					}

					// check if missiles have hit any buildings

					for (int x=0;x<lineList.size();x++)
					{
						for(int y=0;y<buildings.size();y++)
						{
							if (buildings.get(y).isActive())
							{
									Rectangle r2 = new Rectangle(buildings.get(y).getX(),buildings.get(y).getY(),buildings.get(y).getWidth(),buildings.get(y).getHeight());
								if (r2.contains(lineList.get(x).getX(),lineList.get(x).getY()))
								{
												explosions.add(new Round(lineList.get(x).getX(),lineList.get(x).getY(),2));
												explosions.add(new Round(lineList.get(x).getX(),lineList.get(x).getY()+5,2));
												explosions.add(new Round(lineList.get(x).getX(),lineList.get(x).getY()+10,2));
												buildings.get(y).incHit();
												lineList.remove(x);
								}
							}
						}
					}

					chk_gm_over=0;
					for(int y=0;y<buildings.size();y++)
					{
						if (buildings.get(y).isActive())
						{
							chk_gm_over=1;
						}
					}

				if (chk_gm_over!=0)
				repaint();

				Thread.sleep (10);
			}
			catch(InterruptedException e){}
		}
	}

	public void update(Graphics g)
	{
		paint(g);
	}

	public void paintComponent(Graphics g)
	{
		new Color(51,255,153);


		g.setColor(Color.RED);
		super.paintComponent(g);
		g.drawImage(bgd,0,0,1000,800,this);

		if (show_ufo)
			g.drawImage(img,ufo_path.getX(),ufo_path.getY(),30,30,null);

		g.setColor(Color.ORANGE);
		for(int x=0;x<circles.size();x++)
			g.fillOval(circles.get(x).getX()-circles.get(x).getSize()/2,circles.get(x).getY()-circles.get(x).getSize()/2,circles.get(x).getSize(),circles.get(x).getSize());
		g.setColor(Color.GREEN);
		for(int x=0;x<explosions.size();x++)
			g.fillOval(explosions.get(x).getX()-explosions.get(x).getSize()/2,explosions.get(x).getY()-explosions.get(x).getSize()/2,explosions.get(x).getSize(),explosions.get(x).getSize());

		g.fillRect(480,650,5,110);				//tower


		for(Line line:lineList)
		{
			if(line.getDirection())
			  g.setColor(Color.ORANGE);
			 else
			  g.setColor(Color.WHITE);

			g.drawLine(line.getOX(),line.getOY(),line.getX(),line.getY());
		}

		g.setColor(Color.GREEN);


		for (int x=0;x<buildings.size();x++)
		{
				//System.out.println(buildings.get(x));
				if (buildings.get(x).getHits()==0)
						g.setColor(Color.GREEN);


				if (buildings.get(x).getHits()==1)
					g.setColor(Color.YELLOW);

				if (buildings.get(x).getHits()==2)
					g.setColor(Color.RED);

				//System.out.println("IN FILL RECT LOOP");
				Rectangle r1= new Rectangle(buildings.get(x).getX(),buildings.get(x).getY(),buildings.get(x).getWidth(),buildings.get(x).getHeight());
				g.fillRect((int) r1.getX(),(int) r1.getY(),(int) r1.getWidth(),(int) r1.getHeight());

		}

		g.setColor(Color.BLUE);
		g.drawString("Score: "+score,50,200);

		g.drawString("Level: "+level,50,250);

		if (chk_gm_over == 0)
			g.drawString("GAME OVER",50,300);


		//System.out.println(lineList.size());
    }
	public void mouseClicked(MouseEvent e)
	{

	}
	public void mousePressed(MouseEvent e)
	{
		int x=e.getX();
		int y=e.getY();
		//add a new circle to a list
		if(e.getY()<650)
		lineList.add(new Line( 490,650,x,y,true));

		//size=2;

	}
	public void mouseReleased(MouseEvent e)
	{

	}

	public void mouseEntered(MouseEvent e)
	{

	}
	public void mouseExited(MouseEvent e)
	{

	}
	public static void main(String args[])
	{
		MissileCommand app=new MissileCommand();
	}
}