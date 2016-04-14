import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Project #3
 * CS 2334, Section ******
 * *** 02-05-2016 ***
 * <P>
 * This is the MovieDataBase class of Project3. 
 * </P>
 * @version 1.0
 */
public class MovieDataBase implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5949244929456781510L;

	private ArrayList<Movie> movieList = new ArrayList<>();
	
	/*
	 * This method will read in the file containing the information of interest and then begin parsing that 
	 * information into Movie objects. It will achieve this by reading the data one line at a time.
	 * It will then commence reading the line backwards in order to simplify the process.
	 * 
	 * @param
	 * fileName - the name of the file to be read.
	 * 
	 * @return 
	 * An ArrayList<Movie> objects
	 */
	public ArrayList<Movie> movieDataReader(String fileName)throws IOException
	{
		return null;
	}
	
	/*
	 * This method will search for the requested user input based on their interaction with the main program.
	 * 
	 * @param
	 * userInput - The user's input.
	 * 
	 * @return
	 * An ArrayList<String>
	 */
	public ArrayList<String> movieDataSearcher(String [] questions)
	{
		return null;
	}
	
	public void writeObject(ObjectOutputStream aOutputStream) throws IOException 
	 {
		 //perform the default serialization for all non-transient, non-static fields
		 aOutputStream.defaultWriteObject();
	 }
	
	public void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
	{
		aInputStream.defaultReadObject();
	}
	
	/**
	 * Load a file and add it to the database
	 * @param fileName
	 */
	public void loadFile(String fileName)
	{
		//TODO: load a file using object IO
	}
}
