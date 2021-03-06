import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Project #4
 * CS 2334, Section 013
 * 04 - 21 - 2016
 * @author Carson Hawkins, Daniel Schon, and Eric Morales
 * <P>
 * This is the MovieDataBase class of Project4. 
 * </P>
 * @version 1.0
 */
public class MovieDataBase extends Movie implements Serializable{
	/** The long variable made by java for the serialization */
	private static final long serialVersionUID = -5949244929456781510L;
	/** An ArrayList of movies, that will be used to hold the movies in the database */
	private ArrayList<Movie> movieList = new ArrayList<>();
	/** An ArrayList of Strings, that will be used to hold the titles of the movies */
	private ArrayList<String> movieTitles = new ArrayList<>();
	
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
	public ArrayList<Movie> importMovieDataBase(String fileName)throws IOException
	{
		///Initializes the variable nextLine and call in the File to be able to read the Lines from it 
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		String nextLine = br.readLine();

		//readLines is calling the method that will individually read each line in the file.
		//It will be a loop that will repeatedly change the nextLine to the following line in the File.
		while(nextLine != null) {

			//Will used the method readLines and read in the movie information into the movieCollection.
			movieList.add(parseLine(nextLine));
			nextLine = br.readLine();
		}
		//Sorts the movieCollection ArrayList. 
		Collections.sort(movieList);

		br.close();

		return movieList;
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
	public ArrayList<String> movieDataSearcher(String [] questions) throws IOException
	{
		movieTitles = new ArrayList<String>(readTitles(movieList));
		ArrayList<String> moviesFound = new ArrayList<String>();

		Movie movieTitle = new Movie(questions[4]);
		//If the user want to do a specific search it will direct the program here. 
		//This following section will primarily be based off of the user wanting to search by title. 
		if (questions[1].equalsIgnoreCase("T") || questions[1].equalsIgnoreCase("B")) {

			//If the user want to do a specific search it will direct the program here.
			if(questions[2].equalsIgnoreCase("E")) {

				//The users input will use the specificTitleSearch and every time it find the movie that is being searched it will increase
				//count. If no movie is when searching for the movie it will return 0; 					
				moviesFound = movieTitle.specificSearch(movieTitles, movieList);
			}

			//If the user wants to do a partial search it will redirect the program here. 
			else if(questions[2].equalsIgnoreCase("P")) {

				movieTitle = new Movie(questions[4]);

				moviesFound = movieTitle.containsTitleSearch(movieList);		
			}
		}

		if (questions[1].equalsIgnoreCase("Y") || questions[1].equalsIgnoreCase("B")) {

			Movie movieYear = new Movie(questions[5]);
			//The users input will use the yearSearch and every time it find the movie that is being searched it will increase
			moviesFound = movieYear.yearSearch(movieList);
		}

		return moviesFound;
	}
	/**
	 * method for writing out an object to an IO file
	 * @param aOutputStream : the outputStream which will be used to write the objects
	 */
	public void writeObject(ObjectOutputStream aOutputStream) throws IOException 
	 {
		 //perform the default serialization for all non-transient, non-static fields
		 aOutputStream.defaultWriteObject();
	 }
	
	/**
	 * Method for reading in an object from an IO file
	 * @param aInputStream :  the input stream used to read in the objects
	 */
	public void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
	{
		aInputStream.defaultReadObject();
	}
	
	/**
	 * @return the movieList
	 */
	public ArrayList<Movie> getMovieList() {
		return movieList;
	}
	
	/**
	 * Load a file and add it to the database
	 * @param fileName
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void loadFile(String fileName) throws IOException, ClassNotFoundException
	{
		FileInputStream fileInputStream = new FileInputStream(fileName);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		Object object = null;
		// while the next object isnt empty, read the object, cast it to a movie and add it tot he list of movies
		try
		{
			while (true)
			{
				object = objectInputStream.readObject();
				if (object instanceof Movie)
				{
					Movie movie = (Movie) object;
					movieList.add(movie);
				}
			}
		}
		catch(EOFException e)
		{
			//this supposed to happen I guess
		}
			
		objectInputStream.close();
		return;
	}
	
	/**
	 * Save a file using object IO to the specified file
	 * 
	 * @param fileName : the name of the file being saved to 
	 * @throws IOException
	 */
	public void saveFile(String fileName) throws IOException{
		FileOutputStream fileOutputStream = new FileOutputStream(fileName);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		// for every movie in movieList, write the object to the file
		for(Movie movie : movieList){
			objectOutputStream.writeObject(movie);
		}
			objectOutputStream.close();

	}
}
