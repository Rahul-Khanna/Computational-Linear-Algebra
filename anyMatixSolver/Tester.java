import java.io.File;
import java.io.FileNotFoundException;


public class Tester {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		try{
			File in= new File(args[0]);
			Solver m = new Solver(in);
			m.solve();
		}

		catch (FileNotFoundException e) // tells the user that he hasn't put in the correct file names
		{
			System.out.println("Please try again with correct input and output file names");
		}

		catch(ArrayIndexOutOfBoundsException e) //tells the user that he hasn't given enough information or the format is not correct
		{
			System.out.println("You must enter the information like this: <Input File> when running the program");
			System.out.println("If you have done that then the formating of the data in the Input file is not correct please look at the readMe file for more information.");
		}
	}
}

