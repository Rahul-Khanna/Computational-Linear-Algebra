/**
 * The solver class that firsts inverts a matrix and then finds the smallest eigenvalue of the original Matrix
 * @author R_K
 *
 */

public class Solver {

	private Matrix a=new Matrix(10,10);
	private Matrix id=new Matrix(10,10);
	private Matrix random= new Matrix(10,1);
	private double eigenValue;

	public Solver() // fills up the identity matrix and the needed matrix, also creates the random vector
	{
		for(int i=0; i<a.getNumberOfColumns(); i++)
		{
			for(int j=0; j<a.getNumberOfRows(); j++)
			{
				if(i==j)
				{
					a.setElement(j, i, 2);
					id.setElement(j,i,1);
				}

				else if(Math.abs(i-j)==1)
					a.setElement(j, i, -1);
				else
				{
					a.setElement(j, i, 0);
					id.setElement(j,i,0);
				}
			}
		}

		for(int i=0; i<random.getNumberOfRows(); i++)
		{
			random.setElement(i, 0, Math.random());
		}
	}

	public void forwardElimination() // forward eliminates in order to find the inverse
	{
		int counter=0;
		for(int i=0; i<a.getNumberOfColumns()-1; i++)
		{
			int j=((counter)+1);

			double mult= -(a.getElement(j,i)/a.getElement(i, i));
			for(int k=0; k<a.getNumberOfColumns(); k++)
			{
				double value= a.getElement(j, k) + mult*(a.getElement(counter, k));
				a.setElement(j, k, value);
				value=id.getElement(j, k) +  mult*(id.getElement(counter, k));
				id.setElement(j, k, value);
			}
			counter++;
		}
	}

	
	public void backElimination() // back eliminates in order to find the inverse
	{
		int counter=a.getNumberOfColumns()-1;
		for(int i=(a.getNumberOfColumns()-1); i>0; i--)
		{
			int j=((counter)-1);
			double mult= - (a.getElement(j, i))/(a.getElement(i, i));
			for(int k=a.getNumberOfColumns()-1; k>=0; k--)
			{
				double value= a.getElement(j, k) + mult*(a.getElement(counter, k));
				a.setElement(j, k, value);
				value=id.getElement(j, k) +  mult*(id.getElement(counter, k));
				id.setElement(j, k, value);
			}
			counter--;
		}
	}
	
	public void normalize() // divides everything by the diagonal values of the original matrix
	{
		for(int i=0; i<a.getNumberOfRows();i++)
		{
			double divider= a.getElement(i, i);
			a.setElement(i, i, 1);
			for(int j=0; j<a.getNumberOfColumns(); j++)
			{
				double value= (id.getElement(i,j)/divider);
				id.setElement(i, j, value);
			}
		}
	}
	
	
	public void inverse() // just creates the inverse
	{
		forwardElimination();
		backElimination();
		normalize();
		Matrix temp= new Matrix(10,10);
		temp=a;
		a=id;
		id=temp;
	}
	
	public void powerMethod()// finds the largest eigenvalue of the inverse matrix
	{
		Matrix tempRandom= new Matrix(10,1);
		for(int i=0; i<50; i++)
		{
			
			for(int j=0; j<a.getNumberOfRows(); j++) 
			{
				double value=0;
				
				for (int k=0; k<a.getNumberOfColumns(); k++)
				{
					value= value+((a.getElement(j, k))*(random.getElement(k, 0)));	
				}
				tempRandom.setElement(j, 0, value);
			
			}
		    for(int j=0; j<random.getNumberOfRows(); j++)
			{
				random.setElement(j, 0, (tempRandom.getElement(j, 0)));
				tempRandom.setElement(j, 0, 0);
			}
			
		}
		double norm=0;
	    for (int i=0; i<random.getNumberOfRows(); i++)
	    {
	         norm = norm+ random.getElement(i, 0)*random.getElement(i,0); 
	    }
	    norm = Math.sqrt(norm);
	    
	    for(int j=0; j<random.getNumberOfRows(); j++)
		{
			random.setElement(j, 0, (random.getElement(j, 0)/norm));
		}
	    
		double value=0;
		for(int i=0; i<a.getNumberOfColumns(); i++)
		{
			value= value+a.getElement(0, i)*random.getElement(i, 0);
		}
		
		eigenValue= value/(random.getElement(0, 0));;
		eigenValue= 1/(eigenValue);
	}
	
	public void output() // the output
	{
		System.out.println("This is the original Matrix: ");
		System.out.println(a);
		inverse();
		System.out.println("This is the inverse of the Matrix: ");
		System.out.println(a);
		powerMethod();
		System.out.println("This is the largest eigenvalue of the inverse Matrix: " + (1/eigenValue)+ "\n");
		System.out.println("Therefore this is the smallest eigenvalue of the original Matrix: " + eigenValue);	
	}

}
