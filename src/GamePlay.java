
public class GamePlay {
	
	
	public static int[][] exampleboard=
		{
			{
				3 ,4 ,4 ,3 ,5 ,2 ,2 ,5 ,2 ,5 ,4 ,1,
				//3  4  4  3  5  2  2  5  2  5  4  1  
				//3 ,4 ,4 ,3 ,5 ,2 ,2 ,5 ,2 ,5 ,4 ,1,
			},
			{
				//5 ,4 ,2 ,1 ,1 ,3 ,1 ,4 ,3 ,4 ,3 ,3,
				5 ,4 ,2 ,1 ,1 ,3 ,1 ,4 ,3 ,4 ,3 ,3,
				
			},
			{
				4, 1, 2, 1, 5, 4, 3, 5, 3, 5, 2, 4,
			},
			{
				2, 4, 5, 5, 2, 4, 5, 5, 2, 3, 1, 3,
			},
			{
				2, 2, 1, 5, 2, 1, 1, 4, 1, 5, 5, 2,	
				//0,0,0,0,0,0,0,0,0,0,0,0,
			},
			{
				5, 5, 1, 2, 1, 1, 2, 5, 2, 3, 5, 2,
			},
			{
				1, 3, 4, 2, 4, 3, 3, 2, 2, 4, 3, 3,
			},
			{
				4, 1, 4, 1, 3, 5, 5, 4, 1, 5, 5, 4,
			},
		
		};
	
	
	public static int[][] exampleboard2=
		{
			{
				//3 ,4 ,4 ,3 ,5 ,2 ,2 ,5 ,2 ,5 ,4 ,1,
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			},
			{
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			},
			{
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			},
			{
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			},
			{
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				//0,0,0,0,0,0,0,0,0,0,0,0,
			},
			{
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			},
			{
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			},
			{
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			},
		
		};
	

	public static void main(String[] args) // TODO to kiedys ma byc konstruktor a nie main
	{
		int isbomba=0;
		int whenbomb=0;
		for(int gry=0;gry<1000;gry++)
		{
			
			//MoveSelector moveselector = new MoveSelector(exampleboard  , exampleboard2 , 0);
			BoardCreator boardcreator = new BoardCreator(0);
			
			if(isbomba==0 && countElement(boardcreator.getJavelBoard(),6)>=1 )
			{
				whenbomb = 3;
				isbomba = countElement(boardcreator.getJavelBoard(),6);
			}
			else if(isbomba==1 && countElement(boardcreator.getJavelBoard(),6)>=2 )
			{
				whenbomb = 3;
				isbomba = countElement(boardcreator.getJavelBoard(),6);
			}
			else if(isbomba==2 && countElement(boardcreator.getJavelBoard(),6)>=3 )
			{
				whenbomb = 3;
				isbomba = countElement(boardcreator.getJavelBoard(),6);
			}
			else if(isbomba == countElement(boardcreator.getJavelBoard(),6))
			{
				whenbomb--;
			}
			else isbomba = countElement(boardcreator.getJavelBoard(),6);
			if(countElement(boardcreator.getJavelBoard(),6) == 0) whenbomb = 0;
			
			String bombinfo = "isbomba= " + String.valueOf(isbomba) + " whenbomb= " + String.valueOf(whenbomb);
			
			System.out.println(bombinfo);
			int[] strategyinfo = {whenbomb,7,2,1};
			boardcreator.ShowJavelBoard2();
			boardcreator.ShowBackgroundBoard();
			
			MoveSelector moveselector = new MoveSelector(boardcreator.getJavelBoard(), boardcreator.getSquaerBoard() , boardcreator.getDirection(),strategyinfo);
			Move selectmove = moveselector.getFinalMove();
			selectmove.showMove("Move: ");
			selectmove.click();
			
			//boardcreator.ShowJavelBoard();
			
			boolean ready;
			do
			{
				ready = boardcreator.isBoardReady(250);
				System.out.println(ready);
			}while(!ready);
			selectmove.wait(1000);
		}
	}
	private static int countElement(int[][] tab, int element)
	{
		int count = 0 ;
		for(int[] e : tab)
		{
			for(int er:e)
			{
				if(er == element) count++;
			}
		}
		return count;
	}
 	public static boolean test1()
	{
		for(int i=0;i<10;i++)
			
		{
			System.out.println(i);
			if(i>5)return false;
		}
		return true;
	}
	

}
