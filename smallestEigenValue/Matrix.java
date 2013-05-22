import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Matrix{

	private int m;
	private int n;
	private double matrix[][];


	public Matrix(int numberOfRows, int numberOfColumns)  // creates a matrix
	{
		m=numberOfRows;
		n=numberOfColumns;
		matrix= new double[m][n];
	}

	public int getNumberOfRows() 
	{
		return m;
	}

	public int getNumberOfColumns()
	{
		return n;
	}

	public double getElement(int m, int n)
	{
		return matrix[m][n];
	}

	public String getStringRowOrColumn(int choice, int position)
	{
		DecimalFormat df = new DecimalFormat("#.###");
		String rowOrColumn="";
		if(choice==1)//column
		{
			for(int j=0;j<(2*m);j++)
			{
				if(j%2==0)
					rowOrColumn= rowOrColumn+ df.format((matrix[j/2][position]));
				else
					rowOrColumn= rowOrColumn+ "\n";
			}
		}

		if(choice==0)//row
		{
			for(int j=0;j<(2*n);j++)
			{
				if(j%2==0)
					rowOrColumn= rowOrColumn+ df.format((matrix[position][j/2]));
				else
					rowOrColumn= rowOrColumn+ "\n";
			}
		}

		return rowOrColumn;
	}
	public void setElement(int m, int n, double element)
	{
		matrix[m][n]=element;
	}

	public boolean checkForZeroColumn(int rowPosition, int column)
	{
		boolean zero= true;
		for(int i=rowPosition; i<m; i++)
		{
			if(getElement(i, column)!=0)
			{
				zero=false;
				break;
			}

		}

		return zero;
	}

	public boolean checkForZeroRow(int columnPosition, int row)
	{
		boolean zero= true;
		for(int i=columnPosition; i<(n-1); i++)
		{
			if(getElement(row, i)!=0)
			{
				zero= false;
				break;
			}

		}

		return zero;
	}

	public int numberOfPivots(Matrix pivots)
	{
		int number=0;
		for(int i=0; i< pivots.getNumberOfColumns(); i++)
		{
			number= (int) (number+ pivots.getElement(0,i));
		}

		return number;
	}


	public void fillMatrix(File input, int startingLine) throws FileNotFoundException
	{
		Scanner in= new Scanner(input);

		for(int j=1; j<startingLine;j++)
		{
			in.nextLine();
		}

		for(int i=0; i< m; i++)// creates the coefficient matrix from the input file
		{
			for(int j=0; j<n; j++)
			{
				matrix[i][j]=in.nextDouble();
			}
		}
	}

	public void instantiate()
	{
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<m; j++)
			{
				setElement(j,i,0);
			}
		}
	}


	public String toString()
	{
		DecimalFormat df = new DecimalFormat("#.###");
		String matrixString= "";
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<(2*n);j++)
			{
				if(j%2==0)
					matrixString= matrixString+ df.format((matrix[i][j/2]));
				else
					matrixString= matrixString+ " ";
			}
			matrixString=matrixString +"\n";
		}
		return matrixString;
	}
	

}


