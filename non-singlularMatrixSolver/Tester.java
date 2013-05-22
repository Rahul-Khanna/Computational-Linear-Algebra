import java.io.File;
import java.io.FileNotFoundException;
/**
 * The Tester class for Solver
 * @author Rahul Khanna
 */

public class Tester {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			File in= new File(args[0]);
			File out= new File(args[1]);
			Solver m = new Solver(in, out);
			m.solve();
			m.write();
		}

		catch (FileNotFoundException e) // tells the user that he hasn't put in the correct file names
		{
			System.out.println("Please try again with correct input and output file names");
		}

		catch(ArrayIndexOutOfBoundsException e) //tells the user that he hasn't given enough information or the format is not correct
		{
			System.out.println("You must enter the information like this: <Input File> <Output File> when running the program");
			System.out.println("If you have done that then the formating of the data in the Input file is not correct please look at the readMe file for more information.");
		}
	}

}
