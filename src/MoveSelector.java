import java.util.*;


public  class MoveSelector {
	Move finalmove;
	
	
	MoveSelector(int[][] exampleboard,int[][] exampleboard2,int direction, int[]strategyinfo)
	{
		Move[] moves1 = findPossibleMove(exampleboard, 8, 12);
		
		int omg=0;
		int whenbomb=strategyinfo[0];
		int iter1=0;
		int powerofmove = 0;
		int iterator=0;
		int powerofmove2 = 0;
		int iterator2=0;
		int[] finalpoints = {0,0,0,0};
		for(Move move1:moves1)
		{
			//String numb = String.valueOf(iter1);
			//move1.showMove(numb);
			
			VirtualBoard tmpboard1 = new VirtualBoard(exampleboard,exampleboard2,direction, move1, 0, whenbomb);
			//move1.showMove(String.valueOf(iter1));
			//System.out.println(tmpboard1.getScore());
			Move[] moves2 = MoveSelector.findPossibleMove(tmpboard1.getJavelBoard(), tmpboard1.getA(), tmpboard1.getB());
			//int iter2=0;
			for(Move move2:moves2)
			{
				VirtualBoard tmpboard2 = new VirtualBoard(tmpboard1.getJavelBoard() , tmpboard1.getSquareBorad(), direction , move2 , 0 , whenbomb - 1);
				Move[] moves3 = MoveSelector.findPossibleMove(tmpboard2.getJavelBoard(), tmpboard2.getA(), tmpboard2.getB());
				//int iter3=0;
				for(Move move3:moves3)
				{
					VirtualBoard tmpboard3 = new VirtualBoard(tmpboard2.getJavelBoard() , tmpboard2.getSquareBorad(), direction , move3 , 0, whenbomb - 2);
//					String numb1 = String.valueOf(iter1);
//					move1.showMove(numb1);
//					String numb2 = String.valueOf(iter2);
//					move2.showMove(numb2);
//					String numb3 = String.valueOf(iter3);
//					move3.showMove(numb3);
//					
					//iter3++;
//					String points1 = String.valueOf(tmpboard1.getScore());
//					String points2 = String.valueOf(tmpboard2.getScore());
//					String points3 = String.valueOf(tmpboard3.getScore());
					int tmppower =  tmpboard1.getScore()*strategyinfo[1] + tmpboard2.getScore()*strategyinfo[2] + tmpboard3.getScore()*strategyinfo[3];
					if(tmppower >= powerofmove)
					{
						powerofmove = tmppower;
						iterator = iter1;
						finalpoints[0] = tmpboard1.getScore();
						finalpoints[1] = tmpboard2.getScore();
						finalpoints[2] = tmpboard3.getScore();
					}
					int tmppower2 = tmpboard1.getProbScore();
					if(tmppower2 >= powerofmove2)
					{
						powerofmove2 = tmppower2;
						iterator2 = iter1;
						finalpoints[3] = tmppower2;
					}
//					String totalpoints = numb1 + "  " + numb2 + "  " + numb3 + " ---- " +points1 + " " + points2 + " " + points3 + " ";
//					System.out.println(totalpoints);
//					System.out.println("----------------");
					
					omg++;
					
				}
				//iter2++;
			}
			iter1++;
		}
		System.out.println("---");
		System.out.println(omg);
		System.out.println("---");
		if(powerofmove > 0)
		{
			this.finalmove = moves1[iterator];
		}
		else this.finalmove = moves1[iterator2];
		String str = String.valueOf(finalpoints[0]) + "  " + String.valueOf(finalpoints[1]) + "  " + String.valueOf(finalpoints[2]) + " probmove: "   + String.valueOf(finalpoints[3]) + "  " ;
		System.out.println(str);
	}
	
	
	
	private static Move[] findPossibleMove(int[][] javelboard, int a, int b)
	{
		//int [][] javelboard2=new int[a][b];
		//javelboard2 = arrayCloner(javelboard,a,b);
		ArrayList<Move> possibleMove = new ArrayList<Move>();
		for(int i=0;i<a;i++)
		{
			for(int j=0;j<b/2;j++)
			{
				int m = i%2==0 ? 2*j : 2*j+1;
				//System.out.print(javelboard[i][m]);
//				System.out.print(i);
//				System.out.print(" , ");
//				System.out.print(m);
//				System.out.print("  ");
//				
//				System.out.println("  ");
//				
//				System.out.print("  ");
				if(javelboard[i][m] != 0)
				{
					
					if(i>0 && javelboard[i-1][m] != 0)//up neighbour is possible
					{
	//					int [][] tmpjb=new int[a][b]; //not necessery
	//					tmpjb= arrayCloner(javelboard,a,b);
	//					tmpjb[i][k]=javelboard[i-1][m];
	//					tmpjb[i-1][k]=javelboard[i][m];
						int[] vertical1 = new int[5]; 
						int[] vertical2 = new int[5];
						for (int k=0;k<5;k++)
						{
							if(m-2+k>=0 && m-2+k<b)
							{
								vertical1[k] = javelboard[i-1][m-2+k];
								vertical2[k] = javelboard[i][m-2+k];
							}
							else
							{
								vertical1[k] = -1;
								vertical2[k] = -1;
							}
						vertical1[2]=javelboard[i][m];
						vertical2[2]=javelboard[i-1][m];
						}
						int[] horizontal1 = new int[6];
						for (int k=0;k<6;k++)
						{
							if(i-3+k>=0 && i-3+k<a)
							{
								horizontal1[k] = javelboard[i-3+k][m];
								
							}
							else
							{
								horizontal1[k] = -1;
							}
						}
						horizontal1[2]=javelboard[i][m];
						horizontal1[3]=javelboard[i-1][m];
						int [][] arraytest = {horizontal1,vertical1,vertical2};
						//showArray(arraytest);
						
						start_test:
						for(int[] e:arraytest)
						{
							int sekwencelong=0;
							int previousvalue =0;
							for(int er:e)
							{
								if(er==previousvalue && er!=0)sekwencelong++;
								else sekwencelong=0;
								previousvalue=er;
								if(sekwencelong>=2)
								{
									possibleMove.add(new Move(i,m,i-1,m));
	//								String info=String.valueOf(i)+" , "+String.valueOf(m)+" -> "+ String.valueOf(i-1)+" , "+String.valueOf(m);
	//								System.out.println(info);
									break start_test;
								}
							}
						}
					}
					
					if(i<a-1  && javelboard[i+1][m] != 0)//down neighbour is possible
					{
						int[] vertical1 = new int[5]; 
						int[] vertical2 = new int[5];
						for (int k=0;k<5;k++)
						{
							if(m-2+k>=0 && m-2+k<b)
							{
								vertical1[k] = javelboard[i][m-2+k];
								vertical2[k] = javelboard[i+1][m-2+k];
							}
							else
							{
								vertical1[k] = -1;
								vertical2[k] = -1;
							}
						vertical1[2]=javelboard[i+1][m];
						vertical2[2]=javelboard[i][m];
						}
						int[] horizontal1 = new int[6];
						for (int k=0;k<6;k++)
						{
							if(i-2+k>=0 && i-2+k<a)
							{
								horizontal1[k] = javelboard[i-2+k][m];
								
							}
							else
							{
								horizontal1[k] = -1;
							}
						}
						horizontal1[2]=javelboard[i+1][m];
						horizontal1[3]=javelboard[i][m];
						int [][] arraytest = {horizontal1,vertical1,vertical2};
						//showArray(arraytest);
						
						start_test:
							for(int[] e:arraytest)
							{
								int sekwencelong=0;
								int previousvalue =0;
								for(int er:e)
								{
									if(er==previousvalue && er!=0)sekwencelong++;
									else sekwencelong=0;
									previousvalue=er;
									if(sekwencelong>=2)
									{
										possibleMove.add(new Move(i,m,i+1,m));
										break start_test;
									}
								}
							}
					}
					
					if(m>0  && javelboard[i][m-1] != 0)//left neighbour is possible
					{
						int[] horizontal1 = new int[5]; 
						int[] horizontal2 = new int[5];
						for (int k=0;k<5;k++)
						{
							if(i-2+k>=0 && i-2+k<a)
							{
								horizontal1[k] = javelboard[i-2+k][m-1];
								horizontal2[k] = javelboard[i-2+k][m];
							}
							else
							{
								horizontal1[k] = -1;
								horizontal2[k] = -1;
							}
						horizontal1[2]=javelboard[i][m];
						horizontal2[2]=javelboard[i][m-1];
						}
						int[] vertical1 = new int[6];
						for (int k=0;k<6;k++)
						{
							if(m-3+k>=0 && m-3+k<b)
							{
								vertical1[k] = javelboard[i][m-3+k];
								
							}
							else
							{
								vertical1[k] = -1;
							}
						}
						vertical1[2]=javelboard[i][m];
						vertical1[3]=javelboard[i][m-1];
						int [][] arraytest = {horizontal1,horizontal2,vertical1};
						//showArray(arraytest);
						
						start_test:
							for(int[] e:arraytest)
							{
								int sekwencelong=0;
								int previousvalue =0;
								for(int er:e)
								{
									if(er==previousvalue && er!=0)sekwencelong++;
									else sekwencelong=0;
									previousvalue=er;
									if(sekwencelong>=2)
									{
										possibleMove.add(new Move(i,m,i,m-1));
										break start_test;
									}
								}
							}
					}
					
					if(m<b-1 && javelboard[i][m+1] != 0)//right neighbour is possible
					{
						int[] horizontal1 = new int[5]; 
						int[] horizontal2 = new int[5];
						for (int k=0;k<5;k++)
						{
							if(i-2+k>=0 && i-2+k<a)
							{
								horizontal1[k] = javelboard[i-2+k][m];
								horizontal2[k] = javelboard[i-2+k][m+1];
							}
							else
							{
								horizontal1[k] = -1;
								horizontal2[k] = -1;
							}
						horizontal1[2]=javelboard[i][m+1];
						horizontal2[2]=javelboard[i][m];
						}
						int[] vertical1 = new int[6];
						for (int k=0;k<6;k++)
						{
							if(m-2+k>=0 && m-2+k<b)
							{
								vertical1[k] = javelboard[i][m-2+k];
								
							}
							else
							{
								vertical1[k] = -1;
							}
						}
						vertical1[2]=javelboard[i][m+1];
						vertical1[3]=javelboard[i][m];
						int [][] arraytest = {horizontal1,horizontal2,vertical1};
						//showArray(arraytest);
						
						start_test:
							for(int[] e:arraytest)
							{
								int sekwencelong=0;
								int previousvalue =0;
								for(int er:e)
								{
									if(er==previousvalue && er!=0)sekwencelong++;
									else sekwencelong=0;
									previousvalue=er;
									if(sekwencelong>=2)
									{
										possibleMove.add(new Move(i,m,i,m+1));
										break start_test;
									}
								}
							}
					}
				}
				
				
			}
			
		}
		
		
		
		return possibleMove.toArray(new Move[possibleMove.size()]);
		
	}
//	public static VirtualBoard changeBoard(Move move)
//	{
//		return null;
//	}
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
	
	private static void showArray(int [][] tab)
	{
		for(int[] e : tab)
		{
			for(int r : e)
			{
				System.out.print(r);
				System.out.print(" ");
			}
			System.out.println(" ");
		}
	}

	public Move getFinalMove()
	{
		return this.finalmove;
	}
}







