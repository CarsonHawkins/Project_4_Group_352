/**
 * Project #3
 * CS 2334, Section ***  ***
 * *** 02-05-2016 ***
 * <P>
 * This is the Movie class of Project3. 
 * </P>
 * @version 1.0
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JOptionPane;

/**The Class Movie will receive the selected movie information and separate the information
 * into four different strings - movieTitle, movieData, movieRelease and releaseForm. Movie
 * has three constructors depending on the amount of information is know that we help narrow
 * search for the movie the user is trying to find.
 * @author Eric_Morales
 * March 4th, 2016
 */
public class Movie extends Media implements Comparable<Movie>, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1128354735910710984L;

	/** movieTitle will hold the name of the title that the user is searching for. */
	private String movieTitle;
	
	/** movieDate tells the user when the movie was release */
	private String movieDate;
	
	
	/** releaseForm tells the user in what form the movie was release i.e. TV / DVD */
	private String releaseForm = null;

	private String movieRelease;
	
	/**Movie() with no parameters is a constructor that will assign null to the 
	 * assign null to movieTitle because to warn the user that no String was added.
	 */
	public Movie() {
		
		//Creating a movie constructor with no parameters will default into null.
		this.movieTitle = null;
	}
	
	
	/**Movie(String movieTitle) will assign the parameter movieTitle onto the Movie class 
	 * movieTitle. It will allow the methods to be able to use the appropriate movieTitle 
	 * and not have to constantly have movieTitle as a parameter.
	 * @param movieTitle	movieTitle will be assign from the MbdDriver class and assign it 
	 * 						to the Movie movieTitle.
	 */
	public Movie(String movieTitle) {
		
		//The Movie constructor will assign movieTitle to the Class Movie movieTitle
		this.movieTitle = movieTitle;
	}
	
	public Movie(String movieTitle,String movieDate,String releaseForm,String movieRelease) {
		
		//The Movie constructor will assign movieTitle to the Class Movie movieTitle
		this.movieTitle = movieTitle;
		this.movieDate = movieDate;
		this.releaseForm = releaseForm;
		this.movieRelease = movieRelease;
	}
	
	public Movie(String title, String year) {
		// TODO Auto-generated constructor stub
		this.movieTitle = title;
		this.movieDate = year;
	}

	/**arrangeData() will just print out the information of the movie.
	 * @return		Has a String return type to rearranges to fit the output format. 
	 */
	public String arrangeData() {
				//The method will print out all of the data retrieved from the reading the line.
		if (this.movieDate.contains("(\\?\\?\\?\\?)")) {
			this.movieDate = "(????)";
		}
		return "MOVIE " + releaseForm + ": " + this.movieTitle + " " + this.movieDate;
	}
	
	/**The method will retrieve the movie line from the File and separate the information 
	 * into variable. It will spit the movie line in a specific format to try and print out the 
	 * information appropriately.
	 * @param movieLine	The parameter will be accepted to split apart and assign each information to
	 * 					each of the variables. 
	 * return			Has a void return type. 
	 */
	public void parseLine(Movie movieLine) {
		
		//The method will split each of the lines to be able to initialize it to the appropriate variable
		String nextLine = movieLine.toString();
		
		//Determining whether or not there is more than one title with the same name. 
		if(nextLine.contains("/I")) {
			this.movieDate = "(" + (nextLine.substring(nextLine.length() - 4, nextLine.length()));
			if(nextLine.contains("/IIII")) {
				this.movieDate = "(" + (nextLine.substring(nextLine.length() - 4, nextLine.length())) + "/IIII)";
			}
			
			else if(nextLine.contains("/III")) {
				this.movieDate = "(" + (nextLine.substring(nextLine.length() - 4, nextLine.length())) + "/III)";
			}
			
			else if(nextLine.contains("/II")) {
				this.movieDate = "(" + (nextLine.substring(nextLine.length() - 4, nextLine.length())) + "/II)";
			}
			
			else if(nextLine.contains("/I")) {
				this.movieDate = "(" + (nextLine.substring(nextLine.length() - 4, nextLine.length())) + "/I)";
			}
		}
		
		else{
			this.movieDate = "(" + (nextLine.substring(nextLine.length() - 4, nextLine.length())) + ")";
		}
		
		//To not disrupt the split method. 
		if (movieDate.contains("????")) {
			movieDate = "(\\?\\?\\?\\?)";
		}
		
		//Removes the final year to be able to use the data to parse it. 
		nextLine = nextLine.substring(0, nextLine.length() - 5);
		
		//Inserts the parsed data into the ArrayList. [1] contains title [2] contains releaseForm. 
		ArrayList<String> parsed = new ArrayList(Arrays.asList(nextLine.split(movieDate)));
		
		//Inserts the title, but uses substring to avoid the unnecessary white spaces in the end. 
		String tempTitle =parsed.get(0).substring(0, parsed.get(0).length() - 2);
		String containsSpace = tempTitle.substring(tempTitle.length() - 1, tempTitle.length());
		if(containsSpace.equalsIgnoreCase(" ")) {
			this.movieTitle = parsed.get(0).substring(0, parsed.get(0).length() - 3);
		}
		else { 
			this.movieTitle = parsed.get(0).substring(0, parsed.get(0).length() - 2);
		}
		//If the parsed ArrayList contains more than one element that mean will have a release form.  
		if (parsed.size() > 1) {
			
			//Allocate the information into the variable nextLine. 
			nextLine = parsed.get(1);
				
			//Determine whether it has a version and a release form in the movie line. 
			if (nextLine.contains("(")) {
				
				//Splits the line by the parentheses "(DVD)" or "(TV)" and stores it into the ArrayList versionParse. 
				ArrayList<String> versionParse = new ArrayList(Arrays.asList(nextLine.split("\\(")));
					
				//Store each individual element into a variable to reduce confusion.
				String releaseTemp = versionParse.get(1).toString();
					
				//Adds the version and release form into the Movies classes.
				if(releaseTemp.contains("   "))
					this.releaseForm = "(" + releaseTemp.substring(0, 4);
				else
					this.releaseForm = "(" + releaseTemp;
			}
				
			//The program is redirected here if it does not have a version, but does have a release form. (i.e. DVD, TV) 
			else {
					
				//Store each individual element into a variable to reduce confusion.
				String versionTemp = parsed.get(1);
				//Assigns the version onto the Classes version and assigns "N/A" when it is not present. 
				this.releaseForm = "";
			}
			
		}
		
		//Determines that the only information give in the movie line is the year, so it assigns it to "N/A"
		else {
		
			this.releaseForm = "";
		}
		
		return; 
	}
	
	@Override
	/** The method will convert a variable Movie to String.
	 * 	@return		Has a String return type.
	 */
	public String toString() {
		
		//Convert the Movie variable into a String
		return movieTitle + " " + movieDate;
	}
	
	@Override
	/**The method compare to compares the title of the users title to the title of the assigned index
	 * of the movie collection
	 * 
	 * returns 			An int whether or not the title matches. 
	 */
	public int compareTo(Movie o) {
		
		return (this.movieTitle.toLowerCase().compareTo(o.movieTitle.toLowerCase()));
	}
	
	/**The getTitle method will read in a movie line to be able to return only the title of the movie that, so it 
	 * can be stored into an ArrayList. The function of this is to be able to search for a specific title.
	 * 
	 * @param movieLine		It will parse the movieLine and separate all the information. 
	 * @return				It will return the movie title of the movie line presented in the parameters. 
	 */
	public static String getTitle(Movie movieLine) {
		
		String nextLine = movieLine.toString(); 
		String tempLine;
		//The method will split each of the lines to be able to initialize it to the appropriate variable
		String movieDate = "(" + (nextLine.substring(nextLine.length() - 4, nextLine.length())) + ")";
				
		//To not disrupt the split method. 
		if (movieDate.equals("(????)")) {
			movieDate = "(\\?\\?\\?\\?)";
		}
		//Removes the final year to be able to use the data to parse it. 
		nextLine = nextLine.substring(0, nextLine.length() - 5);
		//Inserts the parsed data into the ArrayList. [1] contains title [2] contains releaseForm. 
		ArrayList<String> parsed = new ArrayList(Arrays.asList(nextLine.split(movieDate)));  
		String tempTitle = parsed.get(0);
		
		//Inserts the title, but uses substring to avoid the unnecessary white spaces in the end. 
		String movieTitle;
		
		String containsSpace = tempTitle.substring(tempTitle.length() - 1, tempTitle.length());
		if(containsSpace.equalsIgnoreCase(" ")) {
			return movieTitle = parsed.get(0).substring(0, parsed.get(0).length() - 3).toLowerCase();
		}
		else { 
			
			return movieTitle = parsed.get(0).substring(0, parsed.get(0).length() - 2).toLowerCase();
		}
	}
	
	public static String getYear(Movie movieLine) {
		
		String nextLine = movieLine.toString(); 
		//The method will split each of the lines to be able to initialize it to the appropriate variable
		return nextLine.substring(nextLine.length() - 4, nextLine.length());
		
	}
	
	public String getYear()
	{
		return this.movieRelease;
	}
	
	/**The containsTitle method will run through a loop of all of the lines containing the movie information in the file
	 * and determine whether a keyword is present in the title. If it is present in the movie line, then it will parse the
	 * line and print out the information of the movie. If found it will increase the variable count. 
	 * 
	 * @param movieTitle			The movieTitle variable will contain all of the information of the movie. 
	 * @param movieCollection		The movieCollection ArrayList contains all of the lines for each movie given by the file. 
	 * @param indexTitle			The indexTitle ArrayList contains all of the indexs of the movies found. 
	 * @return						The method will return a counter that increase every time a movie is found that matches the criteria. 
	 */
	public static ArrayList<Integer> containsTitleSearch (Movie movieTitle, ArrayList<Movie> movieCollection, ArrayList<Integer> indexTitle) {
		
		//It will search through the list of movies to try and find the movie user entered. 
		for(int i = 0; i < movieCollection.size(); ++i) {
			
			boolean containsTitle = false;
			//Parses the line to be able to compare the titles
			String nextLine = movieCollection.get(i).toString().toLowerCase();
			//Compares the titles results and inserts the results into indexOf. 
			containsTitle = nextLine.contains(movieTitle.toString().toLowerCase());
			if (containsTitle) {
				
				indexTitle.add(i);
			}			
					
		}
				
		//Will return -1 because it was not found in the ArrayList
		return indexTitle;
	}
	
	/**The specificTitleSearch method will search for a movieTitle using the binarySearch function. If the search finds a movie 
	 * that matches the title that is entered, the it will print out the data of the movie. After displaying the information it will remove 
	 * the title of the movie and run the search again until it no longer finds any title that matches what the input is written. Each time 
	 * a title matches it will increase the count. 
	 * @param movieTitle			The movieTitle variable will contain the input of the user and the title being searched for. 
	 * @param movieTitleSearch		The movieTitleSearch ArrayList contains all of the titles in the file. 
	 * @param movieCollection		The movieCollection ArrayList contains all of the information of the movies from the file. 
	 * @param fileName				The name of the file to receive the data.
	 * @param titleIndex			The indexTitle ArrayList will contain all of the index's of the movies that are found.
	 * @return						It will return an ArrayList that contains all of the index's that from the movie file.
	 * @throws IOException 			To be able to read the file. 
	 */
	public static ArrayList<Integer> specificTitleSearch(Movie movieTitle, ArrayList<String> movieTitleSearch, ArrayList<Movie> movieCollection, String fileName, ArrayList<Integer> titleIndex) throws IOException {
		
		//Searches for the movie title using binarySearch. It will return the index of where it is found in the ArrayList. 
		int containsTitle = Collections.binarySearch(movieTitleSearch, (movieTitle.toString()).toLowerCase());
		int count = 0;
		
		//If containsTitle is equal to or greater than, then it means it will be found. 
		while (containsTitle >= 0) {
			titleIndex.add(containsTitle + count);
			
			//Removes the movieTitle that was already displayed to search again in case a movie has the same title. 
			movieTitleSearch.remove(containsTitle);
			
			//Searches for the movie title using binarySearch. It will return the index of where it is found in the ArrayList. 
			containsTitle = Collections.binarySearch(movieTitleSearch, (movieTitle.toString()).toLowerCase());
			++count;
		}
		
		//Returns count that determines whether or not a movie was found or not. 
		return titleIndex;
	}
	
	/** The yearSearch method will search for a movie by year. Depending on the input of the year or years will decide the method it will search. The user can
	 * can input the information in a single year, or multiple years separated by a space, or a star year and end year separated by a dash to imply that it 
	 * will like all the movies that were made in the the years that was entered. Once a movie is found that matches the criteria it will increase the count. 
	 * 
	 * @param movieTitle			The movieTitle variable contains the year(s) that the user would like the movies created. 
	 * @param movieCollection		The movieCollection ArrayList contains the information to all of the movie that is in the file. 
	 * @param indexTitle			The indexTitle ArrayList will contain all of the index's of the movies that are found. 
	 * @return						It will return an ArrayList that contains all of the index's that from the movie file. 
	 */
	public static ArrayList<Integer> yearSearch(Movie movieTitle, ArrayList<Movie> movieCollection, ArrayList<Integer> indexTitle) {
		
		//The year is converted into a String because it is as a Movie object. 
		String movieYear = movieTitle.toString();
		
		//Determines if the format is written in "xxxx, xxxx"
		if(movieYear.contains(",")) {
			
			//Spits all of the year inputs and stores them into an ArrayList. 
			ArrayList<String> parsed = new ArrayList(Arrays.asList(movieYear.split(", ")));
			
			//The loop is made to be able to reiterate the amount of year the user input. If user inputed 3 years, it loops three times. 
			for (int index = 0; index < parsed.size(); ++index) {
				
				//Store the independent year into the variable year. 
				String year = parsed.get(index);
				
				//Uses the variable and compares all of the elements in the arrayList to see if there is a match. 
				for(int i = 0; i < movieCollection.size(); ++i) {
			
					boolean containsTitle = false;
					//Stores the movie line into the variable nextLine. 
					String nextLine = movieCollection.get(i).toString();
					//Checks whether or not the line contains the year.  
					containsTitle = nextLine.contains(year);
					//If the line does indeed contain the year it will then run through the if statement. 
					if (containsTitle) {
					
						//Converts the movie line into a movie object to be able to parse it. 
						indexTitle.add(i);
						
					}			
						
				}
			}
		}
		
		//Determines if the format is written in "xxxx-xxxx" starting with the start year and the end year. 
		else if (movieYear.contains("-")) {
			
			//Spits the two years and stores the input into an ArrayList. 
			ArrayList<String> parsed = new ArrayList(Arrays.asList(movieYear.split("-")));

			//Converts the two years from String into int. 
			int firstYear = Integer.parseInt(parsed.get(0));
			int secondYear = Integer.parseInt(parsed.get(1));
			//Examines the gap between the two numbers. 
			int timeGap = secondYear - firstYear;
			
			//The loop is made to be able to reiterate the times equal to the time gap. 
			for (int i = 0; i <= timeGap; ++i) {
			
				//Uses the variable and compares all of the elements in the arrayList to see if there is a match. 
				for(int index = 0; index < movieCollection.size(); ++index) {
					
					boolean containsTitle = false;
					//Stores the movieLine onto the variable nextLine. 
					String nextLine = movieCollection.get(index).toString();
					//Compares the titles results and inserts the results into indexOf. 
					containsTitle = nextLine.contains(Integer.toString(firstYear));
					//If the line does indeed contain the year it will then run through the if statement. 
					if (containsTitle) {
					
						//Stores the movie line into the variable currentTitle. 
						indexTitle.add(index);
					}
					
				}
				//Increase the year to be able to follow through the time gap. 
				++firstYear;
			}
		}
		else {
			
			//It will search through the list of movies to try and find the movie user entered. 
			for(int i = 0; i < movieCollection.size(); ++i) {
			
				boolean containsTitle = false;
				//Stores the movie line into the variable nextLine. 
				String nextLine = movieCollection.get(i).toString();
				//Compares the titles results and inserts the results into indexOf. 
				containsTitle = nextLine.contains(movieTitle.toString());
				
				//If the line does indeed contain the year it will then run through the if statement. 
				if (containsTitle) {
				
					indexTitle.add(i);
				}			
			}
		}
				
		//Will return -1 because it was not found in the ArrayList
		return indexTitle;
	}
	
	/**This methods clears the movie data in the ArrayList movieCollection and reinserts the movie data
	 * back on to the ArrayList. While going through parsing, it modify's each line and disrupts the 
	 * search method.
	 * @param movieCollection	The collection of movies where the data is store. 
	 * @param fileName			The name of the file to receive the data. 
	 * @throws IOException		To read in the file. 
	 */
	public static void resetArray(String fileName, ArrayList<Movie> movieCollection) throws IOException {
		
		//Clears the ArrayList to be able to input the list again. 
		movieCollection.clear();
		
		///Initializes the variable nextLine and call in the File to be able to read the Lines from it 
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		String nextLine = br.readLine();
		
		//readLines is calling the method that will individually read each line in the file.
		//It will be a loop that will repeatedly change the nextLine to the following line in the File.
		while(nextLine != null) {
			
			//Will used the method readLines and read in the movie information into the movieCollection.
			readLines(nextLine, movieCollection);
			nextLine = br.readLine();
		}
		//Sorts the movieCollection ArrayList. 
		Collections.sort(movieCollection);
		br.close();
	}
	
	/**readLines is method will use the parameter nextLine to insert it into  individually read each line
	 * in the ArrayList, so it could be use anywhere in the program. 
	 * @param nextLine			Contains the line that will be inserted into the ArrayList
	 * @param movieCollection	The ArrayList that will contain the information of the movie.
	 * return 					Void return type, will only read in the lines.
	 */
	public static void readLines(String nextLine, ArrayList<Movie> movieCollection) {
		
		//Inserts the String into the ArrayList.
		movieCollection.add(new Movie(nextLine));
		return;
	}
	
	/**The method readInTitles goes through each line of the movie information and sections out only the title, so it could be stored in an 
	 * ArrayList. It is used to properly search for a movieTitle using the binarySearch method. 
	 * 
	 * @param fileName				The variable fileName contains the name of the file that contains the movie information.
	 * @param movieTitleSearch		The variable movieTitle search is an arrayList that all the titles will be added too. 
	 * @param movieCollection		The variable movieCollection is an ArrayList that has all of the information for the movies. 
	 * @param sortType				The variable sortType determines how the output will be formatted. 
	 * @return						This method returns an ArrayList of String that contains all of the movie titles. 
	 * @throws IOException			To be able to read in the file
	 */
	public static ArrayList<String> readInTitles(String fileName, ArrayList<String> movieTitleSearch, ArrayList<Movie> movieCollection, String sortType) throws IOException {
		
		//Clears the ArrayList in case it has a modified version of the list after the parse. 
		movieTitleSearch.clear();
		
		//Will read in each line independently to parse and only return the title to be able to place it into the ArrayList. 
		for(int i = 0; i < movieCollection.size(); ++i) {
			

			Movie.resetArray(fileName, movieCollection);
			//Adds the ArrayList into the variable tempTitle to then add it into the ArrayList of movieTitle Search. 
			String tempTitle = Movie.getTitle(movieCollection.get(i));
			
			if (sortType.equalsIgnoreCase("T")) {
				if(i == 2) {
					movieTitleSearch.add(tempTitle.substring(0, tempTitle.length() - 1));
				}
			
				else
					movieTitleSearch.add(tempTitle);
			}
			
			else {
				
				//Adds the ArrayList into the variable tempTitle to then add it into the ArrayList of movieTitle Search. 
				String tempTitle2 = Movie.getTitle(movieCollection.get(i));
				
				if (sortType.equalsIgnoreCase("Y")) {
					if(i == 93) {
						movieTitleSearch.add(tempTitle.substring(0, tempTitle.length() - 1));
					}
				
					else
						movieTitleSearch.add(tempTitle);
				}
			}
		}
		
		//It will reset the array in case it was modified when being parsed. 
		Movie.resetArray(fileName, movieCollection);
		
		return movieTitleSearch;
	}
	
	/**This method sorts the movieCollection by year so the instead of the natural order of by title. 
	 * 
	 * @param movieYearCollection		This variable will hold all of the movies that are sorted by year. 
	 * @param fileName					This variable contains the name of the file created. 
	 * @throws IOException				To be able to read the file. 
	 */
	public static void sortYear(ArrayList<Movie> movieYearCollection, String fileName) throws IOException {
		
		//Clears the ArrayList to be able to input the list again. 
				movieYearCollection.clear();
				
				///Initializes the variable nextLine and call in the File to be able to read the Lines from it 
				FileReader fr = new FileReader(fileName);
				BufferedReader br = new BufferedReader(fr);
				String nextLine = br.readLine();
				
				//readLines is calling the method that will individually read each line in the file.
				//It will be a loop that will repeatedly change the nextLine to the following line in the File.
				while(nextLine != null) {
					
					//Will used the method readLines and read in the movie information into the movieCollection.
					String yearDate = (nextLine.substring(nextLine.length() - 4, nextLine.length()));
					nextLine = yearDate + " " + nextLine;
					readLines(nextLine, movieYearCollection);
					nextLine = br.readLine();
					
				}
				Collections.sort(movieYearCollection);
				
				for(int i = 0; i < movieYearCollection.size(); ++i) {
					String tempHolder = movieYearCollection.get(i).toString();
					movieYearCollection.set(i, new Movie(tempHolder.substring(5, tempHolder.length())));
				}
				br.close();
				
	}

	/**
	 * @return the movieTitle
	 */
	

	public String getMovieTitle() {
		return movieTitle;
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
}
