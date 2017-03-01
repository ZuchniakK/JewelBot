import java.awt.Color;
	public final class ColorComparator {
	public static final int[][][] javelcolors = 
		{
		
			{
				{252,57,215},//fiolet(r,b,g)
				{225,13,182},
				{187,10,153},
			},
			{
				{57,178,0},//green
				{165,246,58},
				{89,228,0},
			},
			{
				{253,251,214},//yellow
				{220,193,0},
				{209,180,0},
			},
			{
				{144,21,1},//red
				{137,21,1},
				{249,33,243},
			},
			{
				{254,170,31},//orange
				{242,127,0},
				{255,184,54},
			},
			{
				{0,34,78},//bomb
				{150,159,114},
				{23,80,130},
			}
		};
	public static final int[][] plus_color_value=
		{
			{
				237,217,96
			},
			{
				234,209,84
			},
			{
				237,217,96
			},
		};
	public static final int backgroundcolor= 47; //dark

	
		
	public static void showSample()
	{
		
		 System.out.println(javelcolors[3][1][2]);
	}
	public static int compareJavels(Color[] screensample)
	{
		//System.out.println("wywolano");
		int[][] tmp_components = new int[3][3]; //[3 samples][3 components]
		int cindex=0;
		
		for(Color tmp_colors:screensample)
		{
			tmp_components[cindex][0]=tmp_colors.getRed();
			tmp_components[cindex][1]=tmp_colors.getGreen();
			tmp_components[cindex][2]=tmp_colors.getBlue();
			cindex++;
		}
		
		
		
		int[] square_error= {0,0,0,0,0,0};
		int sqeindex=0;
		for(int[][] definecolor:javelcolors)
		{
			for(int samp=0;samp<3;samp++)
			{
				for(int col=0;col<3;col++)
				{
					square_error[sqeindex]+=Math.pow(tmp_components[samp][col]-definecolor[samp][col], 2);
				}
			}
			sqeindex++;
		}
		
//		for(int i:square_error)
//		{
//			System.out.print(i);
//			System.out.print(" ");
//		}
//		System.out.println(" ");
		return getMinIndex(square_error);
	}
	public static int compareBackground(Color screenbackground)

	{	
		int[] tmp_components = new int[3];
		tmp_components[0]=screenbackground.getRed();
		tmp_components[1]=screenbackground.getGreen();
		tmp_components[2]=screenbackground.getBlue();
		
		int square_error= 0;
		
		
		for(int col=0;col<3;col++)
		{
			square_error+=Math.pow(tmp_components[col]-backgroundcolor, 2);
		}
	
		
//		System.out.print("grayerror : ");
//		System.out.print(square_error);
//		System.out.print(" ");
//		
//		System.out.println(" ");
		if (square_error>750)return 0;
		else return 1;
	}
	
	public static boolean isReady(Color[] plusssample)
	{
		
			int[][] tmp_components = new int[3][3]; //[3 samples][3 components]
			int cindex=0;
			
			for(Color tmp_colors:plusssample)
			{
				tmp_components[cindex][0]=tmp_colors.getRed();
				tmp_components[cindex][1]=tmp_colors.getGreen();
				tmp_components[cindex][2]=tmp_colors.getBlue();
				cindex++;
			}
			
			
			int sqeindex=0;
			for(int[] definecolor:plus_color_value)
			{
					int square_error = 0;
					for(int samp=0;samp<3;samp++)
					{
						square_error+=Math.pow(tmp_components[sqeindex][samp]-definecolor[samp], 2);
					}
					
					//System.out.print(square_error);
					//System.out.print("wykonano  ");
					if(square_error <= 500)return false;
				
				sqeindex++;
			}
		
		return true;
	}
	public static int setDirection(Color[] compassample)
	{
		//0-down,1-left,2-up,3-right
		if(compassample[0].getGreen()+compassample[0].getBlue()+
		compassample[2].getGreen()+compassample[2].getBlue() >=
		compassample[1].getGreen()+compassample[1].getBlue()+
		compassample[3].getGreen()+compassample[3].getBlue())//If true direction must be on left-right line
		{
			if(compassample[1].getRed() >= compassample[3].getRed())return 1; //If true direction must be on left direction
			else return 3;//right direction
		}
		else
		{
			if(compassample[0].getRed() >= compassample[2].getRed())return 0; //If true direction must be on left direction
			else return 2;//right direction
		}
		
	}
	public static int setDirectionV2(Color[] compassample)
	{
		//0-down,1-left,2-up,3-right
		
		double[] reddomination = new double[4];
		String[] directions = {"Down ","Left ","Up    ","Right"};
		for(int i=0;i<4;i++)	
		{
			reddomination[i] = (double) compassample[i].getRed()/(compassample[i].getGreen() + compassample[i].getBlue());
			String compasinfo ="||" + directions[i] + " R/(G+B)= " +String.valueOf(reddomination[i]) + "  || ";
			System.out.print(compasinfo);
		}
			
		return getMaxIndex(reddomination);
		
	}
	private static int getMinIndex(int[] tab)

	{
		int mindex=0;
		int index_of_min=-1;
		int min=256*256*9+1;
		for(int m:tab)
		{
			if(m<=min)
			{
				min=m;
				index_of_min=mindex+1;
			}
			mindex++;
		}
		//System.out.println(index_of_min);
		return index_of_min;
	}
	private static int getMaxIndex(double[] tab)
	{
		int maxindex=0;
		int index_of_max=-1;
		double max = 0;
		for(double m:tab)
		{
			if(m>=max)
			{
				max=m;
				index_of_max = maxindex;
			}
			maxindex++;
		}
		System.out.println(index_of_max);
		return index_of_max;
	}
}
