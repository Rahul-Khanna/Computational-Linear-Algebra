import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.math.*;


public class Solver {
	
	private Matrix coe;
	private Matrix time;
	private Matrix a;
	private int m;
	private int c;
	private int d;
	private int e;
	private Matrix b;
	private Matrix bUpdated;
	private File input;
	private Scanner in;
	
	public Solver(File in) throws FileNotFoundException
	{
		input= in;
		this.in= new Scanner(input);
		m= this.in.nextInt();
		c= this.in.nextInt();
		d= this.in.nextInt();
		e= this.in.nextInt();
		coe= new Matrix (m, 3);
		b= new Matrix(m,1);
		time= new Matrix(m,1);
		a= new Matrix(3,3);
		bUpdated= new Matrix(3,1);
	}
	
	public void createData()
	{
		for(int i=0; i<m; i++) // creating the perturbed data
		{
			time.setElement(i,0, i);
			double value= c+ (d*i) +(e*i*i);
			if(Math.random()>0.5)
				value= value + (Math.random());
			else
				value= value- (Math.random());
			b.setElement(i, 0, value);
		}
		
		for(int i=0; i<3; i++) // filling 
		{
			if(i==0)
			{
				for(int j=0; j<m; j++)
				{
					coe.setElement(j, i, 1);
				}
			}
			
			else if(i==1)
			{
				for(int j=0; j<m; j++)
				{
					coe.setElement(j, i, time.getElement(j, 0));
				}
			}
			
			else
			{
				for(int j=0; j<m; j++)
				{
					coe.setElement(j, i, ((time.getElement(j, 0))*(time.getElement(j, 0))));
				}
			}
		}
	}
	
	public void createLeftHandSide()
	{
		for(int i=0; i< 3; i++)
		{
			for(int j=0; j<3; j++)
			{
				double sum=0;
				for(int k=0; k<m; k++)
				{
					sum= sum + (coe.getElement(k, j)*(coe.getElement(k, i))); 
				}
				a.setElement(j, i, sum);
			}
		}
	}
	
	public void createRightHandSide()
	{
		for(int i=0; i<3; i++)
		{
			double sum=0;
			for(int j=0; j<m; j++)
			{
				sum = sum + (coe.getElement(j,i))*(b.getElement(j, 0));
			}
			bUpdated.setElement(i, 0, sum);
		}		
	}
	
	public void forwardElimination()
	{
		int counter=0;
		for(int i=0; i<3; i++)
		{
			if(a.getElement(i, i)!=0)
			{
				for(int j=((counter)+1); j<3; j++)
				{
					double mult= -(a.getElement(j,i)/a.getElement(i, i));
					for(int k=counter; k<3; k++)
					{
						double value= a.getElement(j, k) + mult*(a.getElement(counter, k));
						a.setElement(j, k, value);
					}
					double value = bUpdated.getElement(j, 0)+ mult*(bUpdated.getElement((counter),0));
					bUpdated.setElement(j,0, value);
				}
				counter++;
			}
			else
			{
				int j=i;
				boolean noRow=false;
				while(a.getElement(j, i)==0) // which row in the column is non-zero
				{
					j++;
					if(j==(a.getNumberOfRows()))
					{
						noRow= true;
						break;
					}
				}

				if(!noRow) // if there is a row to swap with
				{
					for(int k=0;k<a.getNumberOfColumns();k++)
					{
						double holder1= a.getElement(j, k);
						a.setElement(j, k, (a.getElement(i, k)));
						a.setElement(i, k, holder1);
					}
					i--; // re-does the process
				}
			}
		}
	}
	
	public void backElimination()
	{
		int counter=2;
		for(int i=2; i>0; i--)
		{
			for(int j=((counter)-1);j>=0;j--)
			{
				double mult= - (a.getElement(j, i))/(a.getElement(i, i));
				for(int k=counter; k>=0; k--)
				{
					double value= a.getElement(j, k) + mult*(a.getElement(counter, k));
					a.setElement(j, k, value);
				}
				double value = bUpdated.getElement(j, 0)+ mult*(bUpdated.getElement((counter),0));
				bUpdated.setElement(j,0, value);
			}
			counter--;
		}	
	}
	
	public void solve()
	{
		DecimalFormat df = new DecimalFormat("#.####");
		createData();
		createLeftHandSide();
		createRightHandSide();
		forwardElimination();
		backElimination();
		for(int i=0; i<3; i++)
		{
			bUpdated.setElement(i, 0, (bUpdated.getElement(i, 0))/(a.getElement(i, i)));
		}
		System.out.println("C: " + df.format(bUpdated.getElement(0, 0)));
		System.out.println("D: " + df.format(bUpdated.getElement(1, 0)));
		System.out.println("E: " + df.format(bUpdated.getElement(2, 0)));
	}
	
	

}
