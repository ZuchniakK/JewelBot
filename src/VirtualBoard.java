import java.util.*;
public class VirtualBoard {
	private int a;  // szerokosc tablicy
	private int b;  // wysokosc tablicy
	private int[][] javelboard;
	private int[][] squareboard;
	private int direction;
	private int score;
	private int probscore;

	public VirtualBoard(int[][] javelboard, int[][] squareboard ,int direction, Move move, int score, int whenbomb)
	{
		this.a = 8;
		this.b = 12;
		this.score = score;
		this.direction = direction;
		//this.javelboard  = new int[this.a][this.b];
		//this.squareboard = new int[this.a][this.b];
		this.javelboard = arrayCloner(javelboard, this.a, this.b);
		this.squareboard = arrayCloner(squareboard, this.a, this.b);
		//showBoard(this.javelboard);
		//showBoard(this.squareboard);
		swapPoints(move);
		int beforebombscore=0;
		if(whenbomb >0)
		{
			beforebombscore = scoreFromBomb(whenbomb);
		}
		
		int score_now=0;
		int[][] tmp_javelboard = new int[this.a][this.b];
		do
		{	
			tmp_javelboard = arrayCloner(this.javelboard, this.a, this.b);
			score_now += removeJavels();
			//showBoard(this.javelboard);
			//showBoard(this.squareboard);
			organizeJavels();
			//showBoard(this.javelboard);
			//showBoard(this.squareboard);
		}while(! isTheSame( tmp_javelboard , this.javelboard , this.a , this.b));
		this.score += score_now;
		if(this.score > 0) this.probscore=0;
		else this.probscore = probScoreCounter();
		if(whenbomb > 1)
		{
			this.score += (scoreFromBomb(whenbomb) - beforebombscore);
		}
//		System.out.println(" ");
//		System.out.println(this.score);
//		System.out.println(" ");
		//showBoard(this.javelboard);
		//showBoard(this.squareboard);
		//organizeJavels();
		//showBoard(this.javelboard);
		
		
		
	}
	
	
	private void swapPoints(Move move)
	{
		int tmp = this.javelboard[move.getX1()][move.getY1()];
		this.javelboard[move.getX1()][move.getY1()] = this.javelboard[move.getX2()][move.getY2()];
		this.javelboard[move.getX2()][move.getY2()] = tmp;		
	}
	private int scoreFromBomb(int whenbomb)
	{
		int[][] tmp_squareboard = arrayCloner(this.squareboard, this.a, this.b,1);
		int bombscore=0;
		for(int i=0;i<this.a;i++)
		{
			for(int j=0;j<this.b;j++)
			{
				if(this.javelboard[i][j] == 6)//bomb!
				{	
					int k=i+1;
					int l=j+1;
					for(int q=-1;q<2;q++)
					{
						for(int w=-1;w<2;w++)
						{
							bombscore+=tmp_squareboard[k+q][l+w];
							tmp_squareboard[k+q][l+w] = 0;
							if(whenbomb==1 && i+q>=0 && i+q<this.a && j+w>=0 && j+w<this.b  )this.squareboard[i+q][j+w] = 0;
						}
					}
					
					
				}
			}
		}
		return bombscore;
	}
	private int removeJavels()
	{
		ArrayList<Point> removePoint = new ArrayList<Point>();
		//possibleMove.add(new Move(i,m,i-1,m));
		//return removePoint.toArray(new Point[removePoint.size()]);
		
		for(int i =0; i<this.a ; i++)
		{
			int sekwencelong=0;
			int previousvalue =0;
			for(int j = 0; j<this.b ; j++)
			{
				if(this.javelboard[i][j]==previousvalue && this.javelboard[i][j]!=0)sekwencelong++;
				else sekwencelong=0;
				previousvalue=this.javelboard[i][j];
				if(sekwencelong == 2)
				{
					removePoint.add(new Point(i,j));
					removePoint.add(new Point(i,j-1));
					removePoint.add(new Point(i,j-2));
					
					
				}
				if(sekwencelong>=3)
				{
					removePoint.add(new Point(i,j));
				}
			}
			
			
		}
		
		for(int j =0; j<this.b ; j++)
		{
			int sekwencelong=0;
			int previousvalue =0;
			for(int i = 0; i<this.a ; i++)
			{
				if(this.javelboard[i][j]==previousvalue && this.javelboard[i][j]!=0)sekwencelong++;
				else sekwencelong=0;
				previousvalue=this.javelboard[i][j];
				if(sekwencelong == 2)
				{
					removePoint.add(new Point(i,j));
					removePoint.add(new Point(i-1,j));
					removePoint.add(new Point(i-2,j));
					
					
				}
				if(sekwencelong>=3)
				{
					removePoint.add(new Point(i,j));
				}
			}
			
			
		}
		int score_counter=0;
		for( Point p:removePoint.toArray(new Point[removePoint.size()]))
		{
			int x = p.getX();
			int y = p.getY();
			this.javelboard[x][y] = 0;
			score_counter+=this.squareboard[x][y];
			this.squareboard[x][y]=0;
		}
		return score_counter;
	}
	private void organizeJavels() //TODO dziala tylko przesowanie na prawo i lewo
	{
		
		
		
		if (this.direction == 2) // from up to down
		{
			for(int j=0; j<b ; j++)
			{
				int[] tmp_column =new int[a];
				do
				{
					tmp_column = separateColumn(this.javelboard,j,a);
					
					for(int i=0;i<a-1;i++)
					{
						if(this.javelboard[i][j] == 0)
						{
							this.javelboard[i][j]=this.javelboard[i+1][j];
							this.javelboard[i+1][j]=0;
						}
					}
				}while(!isTheSame(tmp_column, separateColumn(this.javelboard,j,a) , a));
				
			}
		}
		
		if (this.direction == 1) // from right to left
		{
			for(int i=0; i<a ; i++)
			{
				int[] tmp_row =new int[b];
				do
				{
					tmp_row = separateRow(this.javelboard,i,b);
					
					for(int j=0;j<b-1;j++)
					{
						if(this.javelboard[i][j] == 0)
						{
							this.javelboard[i][j]=this.javelboard[i][j+1];
							this.javelboard[i][j+1]=0;
						}
					}
				}while(!isTheSame(tmp_row, separateRow(this.javelboard,i,b) , b));
				
			}
		}
		
		if (this.direction == 3) // from left to right
		{
			for(int i=0; i<a ; i++)
			{
				int[] tmp_row =new int[b];
				do
				{
					tmp_row = separateRow(this.javelboard,i,b);
					
					for(int j=b-1;j>0;j--)
					{
						if(this.javelboard[i][j] == 0)
						{
							this.javelboard[i][j]=this.javelboard[i][j-1];
							this.javelboard[i][j-1]=0;
						}
					}
				}while(!isTheSame(tmp_row, separateRow(this.javelboard,i,b) , b));
				//while(r>=5);
			}
		}
		
		if (this.direction == 0) // from up to down
		{
			for(int j=0; j<b ; j++)
			{
				int[] tmp_column =new int[a];
				do
				{
					tmp_column = separateColumn(this.javelboard,j,a);
					
					for(int i=a-1;i>0;i--)
					{
						if(this.javelboard[i][j] == 0)
						{
							this.javelboard[i][j]=this.javelboard[i-1][j];
							this.javelboard[i-1][j]=0;
						}
					}
				}while(!isTheSame(tmp_column, separateColumn(this.javelboard,j,a) , a));
				
			}
		}
	}
	private int[] separateRow(int[][] tab,int row_number, int row_size)
	{
		int[] new_row = new int[row_size];
		for (int i=0;i<row_size;i++)
		{
			new_row[i] = tab[row_number][i];
		}
		return new_row;
	}
	private int[] separateColumn(int[][] tab, int column_number, int column_size)
	{
		int[] new_column = new int[column_size];
		for (int i=0;i<column_size;i++)
		{
			new_column[i] = tab[i][column_number];
		}
		return new_column;
	}
	private int probScoreCounter()
	{
		int tmpval=0;
		for(int i=0;i<a;i++)
		{
			for(int j=0;j<b;j++)
			{
				if (this.javelboard[i][j] == 0 )
				{
					if(this.squareboard[i][j] == 1)tmpval+=10;
					else
					{
						int tmpval2=0;
						if(j-1>=0 && i-1>=0 && this.squareboard[i-1][j-1] == 1)tmpval2++;
						if( i-1>=0 && this.squareboard[i-1][j] == 1)tmpval2++;
						if(j+1<b && i-1>=0 && this.squareboard[i-1][j+1] == 1)tmpval2++;
						if(j-1>=0  && this.squareboard[i][j-1] == 1)tmpval2++;
						if(j+1<b  && this.squareboard[i][j+1] == 1)tmpval2++;
						if(j-1>=0 && i+1<a && this.squareboard[i+1][j-1] == 1)tmpval2++;
						if(i+1<a && this.squareboard[i+1][j] == 1)tmpval2++;
						if(j+1<b && i+1<a && this.squareboard[i+1][j+1] == 1)tmpval2++;
						tmpval+=tmpval2;
					}
				}
			}
		}
		return tmpval;
	}
	private boolean isTheSame(int[][] tab1 , int[][] tab2, int a , int b)
	{
		for (int elements = 0; elements<a ; elements++)
		{
			for (int element = 0; element<b ; element++)
			{
				if(tab1[elements][element] != tab2[elements][element]) return false;
			}
		}
		return true;
	}
 	private boolean isTheSame(int[] tab1 , int[]tab2 , int size)
	{
		for(int element = 0;element<size;element++)
		{
			if(tab1[element] != tab2[element]) return false;
		}
		return true;
	}
 	
 	private static int[][] arrayCloner(int[][] tab,int a,int b)
	{	
		int[][] newtab = new int[a][b];
		for (int i=0;i<a;i++)
		{
			for (int j=0;j<b;j++)
			{
				newtab[i][j]=tab[i][j];// newtab[i][j]=tab[j][i] -> inverse matrix

			}
		}
		
		return newtab;
	}
	
	private static int[][] arrayCloner(int[][] tab,int a,int b, int framesize) //create new array with "0" value frame
	{	
		int[][] newtab = new int[a+2*framesize][b+2*framesize];
		
		for (int i=0;i<a+2*framesize;i++)
		{
			for (int j=0;j<b+2*framesize;j++)
			{
				newtab[i][j]=0;
			}
		}
		
		
		for (int i=0;i<a;i++)
		{
			for (int j=0;j<b;j++)
			{
				newtab[i+1][j+1]=tab[i][j];
			}
		}
		
		return newtab;
	}
	
 	
 	
	private void showBoard(int[][] tab)
	{
		System.out.println(" ");
		for (int i = 0;i<a;i++)
		{
			for (int j = 0; j<b ; j++)
			{
				System.out.print(tab[i][j]);
				System.out.print(" ");
			}
			System.out.println(" ");
		}
		System.out.println(" ");
	}
	public int[][] getJavelBoard()
	{
		return this.javelboard;
	}
	public int[][] getSquareBorad()
	{
		return this.squareboard;
	}
	public int getScore()
	{
		return this.score;
	}
	public int getProbScore()
	{
		return this.probscore;
	}
	public int getA()
	{
		return this.a;
	}
	public int getB()
	{
		return this.b;
	}
	public int getDirection()
	{
		return this.direction;
	}
}
