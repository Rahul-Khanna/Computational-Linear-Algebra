import java.io.*;


public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			File in= new File(args[0]);
			Solver solver= new Solver(in);
			solver.solve();
		}
		
		catch (FileNotFoundException e) // tells the user that he hasn't put in the correct file names
		{
			System.out.println("Please try again with correct input file names");
		}

		catch(ArrayIndexOutOfBoundsException e) //tells the user that he hasn't given enough information
		{
			System.out.println("You must enter the information like this: <Input File> when running the program");
		}

	}

}
