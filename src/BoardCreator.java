import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;



class BoardCreator 
{	
	private int a;  // szerokosc tablicy
	private int b;  // wysokosc tablicy
	private int[][] javelboard;
	private int[][] squareboard;
	private int x_step;
	private int y_step;
	private int x_start;
	private int y_start;
	private int direction;


	public BoardCreator(int ms)
	{
		this.a = 12;
		this.b = 8;
		javelboard  = new int[this.a][this.b];
		squareboard = new int[this.a][this.b];
		System.out.println("Konstruktor domyslny wywolany");
		fillBoard(ms);
	}
	
	private void fillBoard(int ms)

	
	{
		try
		{
			
			Robot robot = new Robot();
			System.out.println("robot utworzony");
			robot.delay(ms);
			
			x_start=319;
			y_start=239;
			x_step=45;
			y_step=45;
			
			Point[] compasredpoint = new Point[4];
			compasredpoint[0]= new Point(838,668);//down direction
			compasredpoint[1] = new Point(818,647);//left
			compasredpoint[2] = new Point(839,626);//up
			compasredpoint[3] = new Point(860,647);//right direction (from left to right )
			
			Color[] compass = new Color[4];
			
			int compasindex=0;
			for(Point er: compasredpoint)
			{
				int x = er.getX();
				int y = er.getY();
				compass[compasindex] = robot.getPixelColor(x,y);
				System.out.println(compass[compasindex].toString());
				compasindex++;
			}
			//direction = ColorComparator.setDirection(compass);
			direction = ColorComparator.setDirectionV2(compass);
			System.out.println(direction);
			
			Point delta_background = new Point(38,39);	
			Point[] delta = new Point[3];
			delta[0]= new Point(23,10);
			delta[1]= new Point(13,20);
			delta[2]= new Point(23,29);
			


			long start = System.currentTimeMillis();
			for(int i=0;i<this.a;i++)
			{
				for(int j=0;j<this.b;j++)
				{
					Color[] colorsamples = new Color[3];
//					String pozition = "x="+ String.valueOf(i+1)  +"  y=" + String.valueOf(j+1);
//					System.out.println(pozition);
					int index = 0;
					for(Point er : delta)
					{	
						
						int x = er.getX();
						int y = er.getY();
						Color sample = robot.getPixelColor(x_start+i*x_step+x, y_start+j*y_step+y);
						colorsamples[index] = sample;
						
//						System.out.println(sample.toString());
						index++;
					}
					javelboard[i][j]=ColorComparator.compareJavels(colorsamples);
//					System.out.println("");
					int x = delta_background.getX();
					int y = delta_background.getY();
					Color sampleb = robot.getPixelColor(x_start+i*x_step+x, y_start+j*y_step+y);
//					System.out.println("kolor tla");
//					System.out.println(sampleb.toString());
					squareboard[i][j] = ColorComparator.compareBackground(sampleb);
				}
//				System.out.println("");
//				System.out.println("");
//				System.out.println("");
			}
//			System.out.println("");
//			System.out.println("koniec");
//			System.out.println("");
//			System.out.println("");
//			System.out.println("");
//			System.out.println("");
			long stop = System.currentTimeMillis();
			System.out.print(start-stop);
		}catch (AWTException e) 
		{
            e.printStackTrace();
        }

	}
	
	public  boolean isBoardReady(int pause)
	{
		boolean ready=true;
		try
		{
			Robot robot = new Robot();
			robot.delay(pause);
			Point[] score_pluses = new Point[3];
			score_pluses[0] = new Point(491,452);//points>1000
			score_pluses[1] = new Point(515,453);//1000>points>100
			score_pluses[2] = new Point(538,452);//100>points
			Color[] plussamples = new Color[3];
			boolean[] efect =new boolean[2];
			for(int test=0;test<2;test++)
			{
				
				if(test==1)robot.delay(pause);
				int index = 0;
				for(Point er : score_pluses)
				{	
					
					int x = er.getX();
					int y = er.getY();
					Color sample = robot.getPixelColor(x,y);
					plussamples[index] = sample;
					
					//System.out.println(sample.toString());
					index++;
				}	
				efect[test] =   ColorComparator.isReady(plussamples);
			}
			ready = efect[0] && efect[1] ? true : false;
			robot.delay(pause);
		}catch (AWTException e) 
		{
	        e.printStackTrace();
	    }
		return ready;
		
	}
	public int[][] getJavelBoard()
	{
		return transform(this.javelboard);
	}
	public int[][] getSquaerBoard()
	{
		return transform(this.squareboard);
	}
	public int getDirection()
	{
		return this.direction;
	}
	public void ShowJavelBoard()
	{
		System.out.println( " ");
		for(int y=0;y<b;y++)
		{
			for(int x=0;x<a;x++)
			{
				System.out.print(javelboard[x][y]);
				System.out.print( " ");
			}
			System.out.println( " ");
		}
	}
	
	public void ShowJavelBoard2()
	{
		System.out.println( " ");
		String[] color_tab={"fiolet","green ","yellow","red   ","orange","bomb  "};
		for(int y=0;y<b;y++)
		{
			for(int x=0;x<a;x++)
			{
				System.out.print(color_tab[javelboard[x][y]-1]);
				System.out.print( " ");
			}
			System.out.println( " ");
		}
	}
	
	public void ShowBackgroundBoard()
	{
		System.out.println( " ");
		for(int y=0;y<b;y++)
		{
			for(int x=0;x<a;x++)
			{
				System.out.print(squareboard[x][y]);
				System.out.print( " ");
			}
			System.out.println( " ");
		}
	}
	
	public void ShowBackgroundBoard2()
	{
		System.out.println( " ");
		String[] color_tab={"blue  ","black "};
		for(int y=0;y<b;y++)
		{
			for(int x=0;x<a;x++)
			{
				System.out.print(color_tab[squareboard[x][y]]);
				System.out.print( " ");
			}
			System.out.println( " ");
		}
	}
	private int[][] transform(int[][] tab)
	{
		int[][] reverse_tab = new int[this.b][this.a];
		for (int i=0; i<a;i++)
		{
			for (int j = 0; j<b ; j++)
			{
				reverse_tab[j][i] = tab[i][j];
			}
		}
		return reverse_tab;
	}
}		