import java.io.File;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 * The Solver class which solves Ax=b where A is a n by n, k diagonal matrix
 * @author Rahul Khanna
 */

public class Solver {
	private double coefficientMatrix [][];
	private double bVector[];
	private double solutionVector[];
	private File input;
	private File output;
	private Scanner in; 
	private int k;
	private int n;
	private int kIndex;
	
	public Solver(File in, File out) throws FileNotFoundException
	{
		input=in;
		output= out;
		this.in= new Scanner(input);	
	}
	
	public void scanInput() //scans everything in
	{
		n=in.nextInt();
		in.nextLine();
		k=in.nextInt();
		coefficientMatrix= new double[n][n];
		bVector= new double[n];
		solutionVector= new double[n];
		in.nextLine();
		
		for(int i=0; i< n; i++)// creates the coefficient matrix from the input file
		{
			for(int j=0; j<n; j++)
			{
				coefficientMatrix[i][j]=in.nextDouble();
			}
			in.nextLine();
		}
		
		
		for(int i=0; i<n; i++)// creates the vector b
		{
			bVector[i]= in.nextDouble();
			in.nextLine();
		} 
		if(k%2==1) //makes use of the structure of the matrix, to create an upper bound
			kIndex=(k-1)/2;
		else
			kIndex=k;
	}
	
	public void elimination() // goes through the process of elimination
	{
		int counter=0; // to simulate i, without having to use it, useful in knowing where to start eliminating
		
		for(int i=0; i< n-1; i++) // goes through all the columns
		{
			boolean rowSwap=false;
			
			if(coefficientMatrix[i][i] !=0) // makes sure pivot is non-zero
			{
				for(int j=counter+1;(j< counter+1 +kIndex) && (j<n); j++) // goes through all the necessary rows below
				{
					double mult= -(coefficientMatrix[j][i]/coefficientMatrix[i][i]);
					bVector[j]= bVector[j]+ (mult*(bVector[counter]));
					
					for(int l=counter;(l< (counter+2+ kIndex)) && (l<n);l++) // goes through all the necessary columns
					{
						coefficientMatrix[j][l]= coefficientMatrix[j][l]+ (mult*(coefficientMatrix[i][l]));
						
						if(Math.abs(coefficientMatrix[j][l]) <= 1.7763568394002505E-15)// for some reason it wouldn't just put zero
							coefficientMatrix[j][l]=0.0;		
					}
				}
				counter++;
			}
			
			else // tries to swap rows
			{
				int row=i;
				boolean noRowSwap=false;
				while(!rowSwap) // checks if there is a row to swap with
				{
					row++;
					if(coefficientMatrix[row][i] !=0)
						rowSwap=true;
					
					if(row>=n-1)
					{
						noRowSwap=true;
						break;
					}			
				}
				if(rowSwap) // swaps rows if it possible
				{
					for(int p=i; p<n; p++)
					{
						double holder1=coefficientMatrix[i][p];
						coefficientMatrix[i][p]= coefficientMatrix[row][p];
						coefficientMatrix[row][p]= holder1;
					}
					double holder2=bVector[i];
					bVector[i]=bVector[row];
					bVector[row]= holder2;
					i--;
				}
				
				if(noRowSwap) // tells the user the matrix is singular and is not solvable, also exits the elimination process
				{
					System.out.println("This is a singular Matrix, it can't be solved.");
					break;
					
				}	
			}	
		}
	}
	
	public void solve() 
	{
		scanInput();
		elimination();
		
		solutionVector[n-1]=bVector[n-1]/coefficientMatrix[n-1][n-1];// calculates the first term in back substitution
		
		int counter=1;// same purpose as above
		for(int i=n-2; i>=0; i--)
		{
			double sum=0;
			for(int j=1; j<=counter; j++) 
			{
				sum= sum + (coefficientMatrix[i][(n-j)])*(solutionVector[(n-j)]); //plugs in the value of the previous found solutions
			}
			solutionVector[i]=((bVector[i])-sum)/(coefficientMatrix[i][i]); // solves for the current solution
			counter++;	
		}
	}
	
	
	public void write() throws FileNotFoundException // writes the solution vector into the output file
	{	
		PrintWriter writer= new PrintWriter(output);
		writer.println("Your Solution is: ");
		
		for(int i=0; i<n; i++)
		{
			writer.println(solutionVector[i]);
		}
		
		writer.close();
	}

}
