import java.io.*;
import java.util.*;

public class Solver {

	private Matrix coefficient;
	private Matrix augment;
	private Matrix pivots;
	private Matrix solution;
	private Matrix b;
	private File input;
	private Scanner in;
	private String result;

	public Solver(File in) throws FileNotFoundException //Instantiates everything
	{
		input=in;
		this.in= new Scanner(input);
		coefficient= new Matrix(this.in.nextInt(), this.in.nextInt());
		pivots= new Matrix(2,coefficient.getNumberOfColumns());
		b= new Matrix(coefficient.getNumberOfRows(),1);
		augment= new Matrix(coefficient.getNumberOfRows(), coefficient.getNumberOfColumns()+1);

	}

	public void scanInput() throws FileNotFoundException //fills everything up
	{
		coefficient.fillMatrix(input,3);
		b.fillMatrix(input,3+coefficient.getNumberOfRows()); 

		for(int i=0; i< (coefficient.getNumberOfRows()); i++)// filling the augmented matrix
		{
			for(int j=0; j< (coefficient.getNumberOfColumns()); j++)
			{
				augment.setElement(i, j, coefficient.getElement(i, j));		
			}
			augment.setElement(i,(augment.getNumberOfColumns()-1), b.getElement(i, 0));
		}
	}

	public void forwardElimination() //forward elimination
	{
		int numberOfIterations= Math.min(augment.getNumberOfColumns()-1,augment.getNumberOfRows()); //min of (m,n)
		int initial= numberOfIterations;// starting value
		for(int i=0; i<numberOfIterations; i++) //column
		{
			if(augment.getElement((i-(numberOfIterations-initial)), i)!=0)// checks to see if the pivot is in the column
				{
					pivots.setElement(0, i, 1);//says there is a pivot in that column i
					pivots.setElement(1, i, i-(numberOfIterations-initial)); //gives the row position of the pivot in column i
					for(int j=(i-(numberOfIterations-initial)+1); j<augment.getNumberOfRows(); j++) // rows below
					{
						double mult= -((augment.getElement(j, i))/(augment.getElement(i-(numberOfIterations-initial), i)));
						for(int k=i;k<augment.getNumberOfColumns();k++) // going across columns to eliminate
						{
							double newValue= augment.getElement(j, k) + mult*augment.getElement(i-(numberOfIterations-initial), k);
							augment.setElement(j, k, newValue);
						}
					}
				}

			else // if the pivot is not in the column
			{
				if(augment.checkForZeroColumn(i-(numberOfIterations-initial), i))//checks for a zero column, if zero goes to the next columns and tries to find pivot
				{
					boolean noMore=false;
					int j=i-(numberOfIterations-initial);
					while(augment.getElement(j,i)==0)//going through the columns
					{
						pivots.setElement(0, i, 0);
						pivots.setElement(1,i,-1);
						i++;
						if(i==(augment.getNumberOfColumns()-1))
						{
							noMore=true;
							break;
						}
					}
					if(!noMore) // if a column is found
					{
						pivots.setElement(0,i,1);
						pivots.setElement(1, i, j);
						for(int k=(j+1); k<augment.getNumberOfRows(); k++) //row
						{
							double mult= -((augment.getElement(k, i))/(augment.getElement(j, i)));
							for(int l=i;l<augment.getNumberOfColumns();l++) // going across columns to eliminate
							{
								double newValue= augment.getElement(k, l) + mult*augment.getElement(j, l);
								augment.setElement(k, l, newValue);
							}
						}
						if(augment.getNumberOfColumns()-1>augment.getNumberOfRows()) // if there are more columns than rows, then we can keep finding more pivots
							numberOfIterations++;
					}
					
					
				}

				else // if it is not a zero column, then we do row swaps
				{
					int j=i-(numberOfIterations-initial);
					boolean noRow=false;
					while(augment.getElement(j, i)==0) // which row in the column is non-zero
					{
						j++;
						if(j==(augment.getNumberOfRows()))
						{
							noRow= true;
							break;
						}
					}

					if(!noRow) // if there is a row to swap with
					{
						for(int k=0;k<augment.getNumberOfColumns();k++)
						{
							double holder1= augment.getElement(j, k);
							augment.setElement(j, k, (augment.getElement(i-(numberOfIterations-initial), k)));
							augment.setElement(i-(numberOfIterations-initial), k, holder1);
						}
						i--; // re-does the process since now there is a pivot
					}
				}
			}
		}
	}

	public void backwardsElimination() throws FileNotFoundException
	{
		int i;
		for(i=(augment.getNumberOfColumns()-2); i>=0; i--)// columns
		{
			if(pivots.getElement(0, i)==1) // if it is a pivot
			{
				int j= (int) ((pivots.getElement(1, i))-1); //row position
				for(int k=j;k>=0;k--) // rows
				{
					double mult= -((augment.getElement(k, i))/(augment.getElement(j+1, i)));
					
					for(int l=(augment.getNumberOfColumns()-1);l>=0;l--) // going across columns to eliminate
					{
						double newValue= augment.getElement(k, l) +  mult*augment.getElement(j+1, l) ;
						if(Math.abs(newValue)<=1.1546319456101628E-15)
							newValue=0;
						augment.setElement(k, l, newValue);
					}
				}
			}
		}

		for(i=0;i<augment.getNumberOfColumns()-1;i++) // divides through by the pivot value
		{
			if(pivots.getElement(0, i)==1)
			{	
				double dividor= augment.getElement((int) pivots.getElement(1, i), i);

				for(int j=i; j<augment.getNumberOfColumns(); j++)
				{
					double newValue= (augment.getElement((int) pivots.getElement(1, i), j))/dividor;
					augment.setElement((int) pivots.getElement(1, i), j, newValue );
				}
			}
		}
	}

	public void solve() throws FileNotFoundException
	{
		scanInput();
		forwardElimination();
		backwardsElimination();
		boolean done=false;
		if(true) // if there is a zero row and not a zero b value
		{
			for(int i=0; i<augment.getNumberOfRows();i++)
			{
				if(augment.checkForZeroRow(0, i))
				{
					if(augment.getElement(i, augment.getNumberOfColumns()-1)!=0)
					{
						result= "Sorry there are no solutions";
						done=true;
						System.out.println(result);
						break;
					}	

				}

			}
			

		}

		if(!done)
		{
			if (augment.numberOfPivots(pivots)==coefficient.getNumberOfColumns()) // if there is a unique solution
			{
				solution= new Matrix(coefficient.getNumberOfColumns(),1);
				for(int i=0; i<augment.getNumberOfRows(); i++)
				{
					solution.setElement(i, 0, augment.getElement(i,coefficient.getNumberOfColumns()));
				}

				result="There is one unique solution, here it is: \n"+ solution;

				System.out.println(result);
			}

			else // multiple solutions
			{
				solution= new Matrix(coefficient.getNumberOfColumns(),1);
				Matrix nullSpace= new Matrix(coefficient.getNumberOfColumns(), (coefficient.getNumberOfColumns()-coefficient.numberOfPivots(pivots)));
				solution.instantiate();
				nullSpace.instantiate();
				int nullSpaceCounter=0;
				for(int i=0; i< pivots.getNumberOfColumns(); i++) // particular solution
				{

					if (pivots.getElement(0, i)==1)
						solution.setElement(i, 0, augment.getElement((int) pivots.getElement(1, i), augment.getNumberOfColumns()-1));

					else // creating the NullSpace
					{
						nullSpace.setElement(i, nullSpaceCounter, 1);
						for(int j=0; j<pivots.getNumberOfColumns();j++)
						{
							if(pivots.getElement(0,j)==1)
							{
								if(augment.getElement((int) pivots.getElement(1, j), i)!=0)
									nullSpace.setElement(j,nullSpaceCounter,(-1*(augment.getElement((int) pivots.getElement(1, j), i))));
							}
						}

						nullSpaceCounter++;
					}	
				}
				// I wanted to put it all into a string, so that it can be printed to various places.
				result="There are infinite solutions. These solutions can be desrcibed by a particular soultion, which is: \n" + solution + "Plus any linear combination of the following " + (coefficient.getNumberOfColumns()-coefficient.numberOfPivots(pivots)) + ", " + coefficient.getNumberOfColumns() +  " by 1 vectors:  \n";
				int i;
				for (i=0; i<(nullSpace.getNumberOfColumns()-1);i++)
				{
					result= result+nullSpace.getStringRowOrColumn(1, i) + "and \n";
				}
				result= result +nullSpace.getStringRowOrColumn(1, i);
				System.out.println(result);
			}

		}
	}
}
