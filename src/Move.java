import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Random;

public class Move {
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	Move (int x1, int y1, int x2, int y2)
	{
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
	}
	
	public int getX1()
	{
		return x1;
	}
	public int getY1()
	{
		return y1;
	}
	public int getX2()
	{
		return x2;
	}
	public int getY2()
	{
		return y2;
	}
	public void showMove()
	{
		String moveinfo=String.valueOf(x1)+" , "+String.valueOf(y1)+" <-> "+ String.valueOf(x2)+" , "+String.valueOf(y2);
		System.out.println(moveinfo);
	}
	public void showMove(String label)
	{
		String moveinfo=label + " : " + String.valueOf(x1)+" , "+String.valueOf(y1)+" <-> "+ String.valueOf(x2)+" , "+String.valueOf(y2);
		System.out.println(moveinfo);
	}
	public void click()
	{
		try
		{

			Robot robot = new Robot();
			int x_start=319;
			int y_start=239;
			int x_step=45;
			int y_step=45;
			robot.delay(1100 + fluctuation(0,50,250,-250));
			robot.mouseMove(22 + x_start+x_step*this.y1 + fluctuation(0,3,15,-15),22 +  y_start+y_step*this.x1 + fluctuation(0,3,15,-15));
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.delay(250 + fluctuation(0,6,30,-30));
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            
            robot.delay(570 + fluctuation(0,30,150,-150));
            
			robot.mouseMove(22 + x_start+x_step*this.y2 + fluctuation(0,3,15,-15),22 +  y_start+y_step*this.x2 + fluctuation(0,3,15,-15));
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.delay(215 + fluctuation(0,6,30,-30));
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            
            robot.delay(350 + fluctuation(0,6,30,-30));
            robot.mouseMove(30 + fluctuation(0,2,10,-10), 30 + fluctuation(0,2,10,-10));
            robot.delay(1400 + fluctuation(0,50,250,-250));
			
		}
		catch (AWTException e) 
		{
			e.printStackTrace();
		}
	

	}
	public void wait(int ms)
	{
		try
		{
			Robot robot = new Robot();
			robot.delay(ms + fluctuation(0,5,25,-25));
			
		}
		catch (AWTException e) 
		{
			e.printStackTrace();
		}
		
	}
	private int fluctuation(double mean, double variance, double max, double min)
	{
		Random random = new Random();
		double tmp = mean + random.nextGaussian() * variance;
		if(tmp > max) tmp = max;
		if(tmp < min) tmp = min;
		return  (int)Math.round(tmp);
		
		
	}
}
