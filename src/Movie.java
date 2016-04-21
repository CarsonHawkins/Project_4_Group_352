import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**The Class Movie will receive the selected movie information and separate the information
 * into four different strings - movieTitle, movieData, movieRelease and releaseForm. Movie
 * has three constructors depending on the amount of information is know that we help narrow
 * search for the movie the user is trying to find.
 * @author Eric_Morales
 * March 4th, 2016
 */
public class Movie extends Media 
{
	/** movieTitle will hold the name of the title that the user is searching for. */
	private String movieTitle;

	/** movieDate tells the user when the movie was release */
	private String movieDate;

	/** movieRelease tells the user when the movie was release */
	@SuppressWarnings("unused")
	private String movieRelease;

	/** releaseForm tells the user in what form the movie was release i.e. TV / DVD */
	private String releaseForm = "";
	
	private ArrayList<String> movieTitles = new ArrayList<String>();

	/**Movie() with no parameters is a constructor that will assign null to the 
	 * assign null to movieTitle because to warn the user that no String was added.
	 */
	public Movie() {

		//Creating a movie constructor with no parameters will default into null.
		this.movieTitle = "";
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
		this.movieDate = "";
		this.movieRelease = ""; 
		this.releaseForm = "";
	}

	public Movie(String movieTitle, String movieDate) {

		//The Movie constructor will assign movieTitle to the Class Movie movieTitle
		this.movieTitle = movieTitle;
		this.movieDate = movieDate;
		this.movieRelease = ""; 
		this.releaseForm = "";
	}

	public Movie(String movieTitle, String movieDate, String movieRelease) {

		//The Movie constructor will assign movieTitle to the Class Movie movieTitle
		this.movieTitle = movieTitle;
		this.movieDate = movieDate;
		this.movieRelease = movieRelease; 
		this.releaseForm = "";
	}

	public Movie(String movieTitle, String movieDate, String movieRelease, String releaseForm) {

		//The Movie constructor will assign movieTitle to the Class Movie movieTitle
		this.movieTitle = movieTitle;
		this.movieDate = movieDate;
		this.movieRelease = movieRelease; 
		this.releaseForm = releaseForm;
	}

	/**arrangeData() will just print out the information of the movie.
	 * @return		Has a String return type to rearranges to fit the output format. 
	 */
	public String toString() {

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
	public Movie parseLine(String movieLine) {

		//The method will split each of the lines to be able to initialize it to the appropriate variable
		String nextLine = movieLine.toString();

		//Determining whether or not there is more than one title with the same name. 
		if(nextLine.contains("/I")) {
			movieDate = "(" + (nextLine.substring(nextLine.length() - 4, nextLine.length()));
			if(nextLine.contains("/IV")) {
				movieDate = nextLine.substring(nextLine.length() - 4, nextLine.length()) + "/IV";
			}

			else if(nextLine.contains("/III")) {
				movieDate = nextLine.substring(nextLine.length() - 4, nextLine.length()) + "/III";
			}

			else if(nextLine.contains("/II")) {
				movieDate = nextLine.substring(nextLine.length() - 4, nextLine.length()) + "/II";
			}

			else if(nextLine.contains("/I")) {
				movieDate = nextLine.substring(nextLine.length() - 4, nextLine.length()) + "/I";
			}
		}

		else{
			movieDate = nextLine.substring(nextLine.length() - 4, nextLine.length());

		}

		//To not disrupt the split method. 
		if (movieDate.contains("????")) {
			movieDate = "\\?\\?\\?\\?";
		}

		//Removes the final year to be able to use the data to parse it. 
		nextLine = nextLine.substring(0, nextLine.length() - 5);

		//Inserts the parsed data into the ArrayList. [1] contains title [2] contains releaseForm. 
		ArrayList<String> parsed = new ArrayList<String>(Arrays.asList(nextLine.split(movieDate)));

		//Inserts the title, but uses substring to avoid the unnecessary white spaces in the end. 
		String tempTitle =parsed.get(0).substring(0, parsed.get(0).length() - 2);
		String containsSpace = tempTitle.substring(tempTitle.length() - 1, tempTitle.length());
		if(containsSpace.equalsIgnoreCase(" ")) {
			movieTitle = parsed.get(0).substring(0, parsed.get(0).length() - 3);
		}
		else { 
			movieTitle = parsed.get(0).substring(0, parsed.get(0).length() - 2);
		}
		//If the parsed ArrayList contains more than one element that mean will have a release form.  
		if (parsed.size() > 1) {

			//Allocate the information into the variable nextLine. 
			nextLine = parsed.get(1);

			//Determine whether it has a version and a release form in the movie line. 
			if (nextLine.contains("(")) {

				//Splits the line by the parentheses "(DVD)" or "(TV)" and stores it into the ArrayList versionParse. 
				ArrayList<String> versionParse = new ArrayList<String>(Arrays.asList(nextLine.split("\\(")));

				//Store each individual element into a variable to reduce confusion.
				String releaseTemp = versionParse.get(1).toString();

				//Adds the version and release form into the Movies classes.
				if(releaseTemp.contains("   "))
					releaseForm = "(" + releaseTemp.substring(0, 4);
				else
					releaseForm = "(" + releaseTemp;
			}

			//The program is redirected here if it does not have a version, but does have a release form. (i.e. DVD, TV) 
			else {

				//Assigns the version onto the Classes version and assigns "N/A" when it is not present. 
				releaseForm = "";
			}

		}

		//Determines that the only information give in the movie line is the year, so it assigns it to "N/A"
		else {

			releaseForm = "";
		}

		return new Movie(movieTitle, "(" + movieDate + ")", movieDate, releaseForm); 
	}

	public static final Comparator<Movie> MovieYearComparator = new Comparator<Movie>()
	{
		public int compare(Movie a,Movie b)
		{

			return a.getYear().compareTo(b.getYear());

		}
	};

	/**The getTitle method will read in a movie line to be able to return only the title of the movie that, so it 
	 * can be stored into an ArrayList. The function of this is to be able to search for a specific title.
	 * 
	 * @param movieLine		It will parse the movieLine and separate all the information. 
	 * @return				It will return the movie title of the movie line presented in the parameters. 
	 */
	public String getTitle() {

		return movieTitle;
	}
	
	/**The getYear method will read in a movieLine to be able to return only the year of the movie, so it 
	 * can be stored into an ArrayList. The function of this is to be able to search for a specific title.
	 * 
	 * @param movieLine		It will parse the movieLine and separate all the information. 
	 * @return				It will return the movie year of the movieLine presented in the parameters.
	 */
	public String getYear() {

		return movieDate;

	}
	
	


	public void setMovieTitle(String movieTitle)
	{
		this.movieTitle = movieTitle;
	}

	public String getReleaseForm()
	{
		return releaseForm;
	}

	public void setReleaseForm(String releaseForm)
	{
		this.releaseForm = releaseForm;
	}

	public void setYear(String movieDate) {
		this.movieDate = "(" + movieDate + ")";
		this.movieRelease = movieDate;
	}

	/**The containsTitle method will run through a loop of all of the lines containing the movie information in the file
	 * and determine whether a keyword is present in the title. If it is present in the movie line, then it will parse the
	 * line and print out the information of the movie. If found it will increase the variable count. 
	 * 
	 * @param movieCollection		The movieCollection ArrayList contains all of the lines for each movie given by the file. 
	 * @return						The method will return a counter that increase every time a movie is found that matches the criteria. 
	 */
	public ArrayList<String> containsTitleSearch (ArrayList<Movie> movieCollection) {

		Set<Integer> index = new HashSet<Integer>();

		//It will search through the list of movies to try and find the movie user entered. 
		for(int i = 0; i < movieCollection.size(); ++i) {

			boolean containsTitle = false;
			//Parses the line to be able to compare the titles
			String nextLine = movieCollection.get(i).toString().toLowerCase();
			//Compares the titles results and inserts the results into indexOf. 
			containsTitle = nextLine.contains(movieTitle.toLowerCase());
			if (containsTitle) {

				index.add(i);
			}			

		}

		ArrayList<Integer> titleIndex = new ArrayList<Integer>(index);
		ArrayList<String> moviesFound = new ArrayList<String>();
		for(int i = 0; i < titleIndex.size(); ++i) {

			moviesFound.add(movieCollection.get(titleIndex.get(i)).toString());
		}
		return moviesFound;
	}

	/**The specificTitleSearch method will search for a movieTitle using the binarySearch function. If the search finds a movie 
	 * that matches the title that is entered, the it will print out the data of the movie. After displaying the information it will remove 
	 * the title of the movie and run the search again until it no longer finds any title that matches what the input is written. Each time 
	 * a title matches it will increase the count. 
	 * @param titleCollection		The movieCollection ArrayList contains all of the information of the movies from the file. 
	 * @return						It will return an ArrayList that contains all of the index's that from the movie file.
	 * @throws IOException 			To be able to read the file. 
	 */
	public ArrayList<String> specificSearch(ArrayList<String> titleCollection, ArrayList<Movie> movieCollection) throws IOException {

		Set<Integer> index = new HashSet<Integer>();

		//Searches for the movie title using binarySearch. It will return the index of where it is found in the ArrayList. 
		int containsTitle = Collections.binarySearch(titleCollection, movieTitle.toLowerCase());
		int count = 0;

		//If containsTitle is equal to or greater than, then it means it will be found. 
		while (containsTitle >= 0) {
			index.add(containsTitle + count);

			//Removes the movieTitle that was already displayed to search again in case a movie has the same title. 
			titleCollection.remove(containsTitle);

			//Searches for the movie title using binarySearch. It will return the index of where it is found in the ArrayList. 
			containsTitle = Collections.binarySearch(titleCollection, movieTitle.toLowerCase());
			++count;
		}

		//Returns count that determines whether or not a movie was found or not. 
		ArrayList<Integer> titleIndex = new ArrayList<Integer>(index);
		ArrayList<String> moviesFound = new ArrayList<String>();
		for(int i = 0; i < titleIndex.size(); ++i) {

			moviesFound.add(movieCollection.get(titleIndex.get(i)).toString());
		}
		return moviesFound;
	}

	/** The yearSearch method will search for a movie by year. Depending on the input of the year or years will decide the method it will search. The user can
	 * can input the information in a single year, or multiple years separated by a space, or a star year and end year separated by a dash to imply that it 
	 * will like all the movies that were made in the the years that was entered. Once a movie is found that matches the criteria it will increase the count. 
	 * 
	 * @param movieCollection		The movieCollection ArrayList contains the information to all of the movie that is in the file. 
	 * @return						It will return an ArrayList that contains all of the index's that from the movie file. 
	 */
	public ArrayList<String> yearSearch(ArrayList<Movie> movieCollection) {

		Set<Integer> index = new HashSet<Integer>();

		//The year is converted into a String because it is as a Movie object. 
		String movieYear = movieTitle;

		//Determines if the format is written in "xxxx, xxxx"
		if(movieYear.contains(",")) {

			//Spits all of the year inputs and stores them into an ArrayList. 
			ArrayList<String> parsed = new ArrayList<String>(Arrays.asList(movieYear.split(", ")));

			//The loop is made to be able to reiterate the amount of year the user input. If user inputed 3 years, it loops three times. 
			for (int i = 0; i < parsed.size(); ++i) {

				//Store the independent year into the variable year. 
				String year = parsed.get(i);

				//Uses the variable and compares all of the elements in the arrayList to see if there is a match. 
				for(int a = 0; i < movieCollection.size(); ++a) {

					boolean containsTitle = false;
					//Stores the movie line into the variable nextLine. 
					String nextLine = movieCollection.get(a).toString();
					//Checks whether or not the line contains the year.  
					containsTitle = nextLine.contains(year);
					//If the line does indeed contain the year it will then run through the if statement. 
					if (containsTitle) {

						//Converts the movie line into a movie object to be able to parse it. 
						index.add(a);
					}			
				}
			}
		}

		//Determines if the format is written in "xxxx-xxxx" starting with the start year and the end year. 
		else if (movieYear.contains("-")) {

			//Spits the two years and stores the input into an ArrayList. 
			ArrayList<String> parsed = new ArrayList<String>(Arrays.asList(movieYear.split("-")));

			//Converts the two years from String into int. 
			int firstYear = Integer.parseInt(parsed.get(0));
			int secondYear = Integer.parseInt(parsed.get(1));
			//Examines the gap between the two numbers. 
			int timeGap = secondYear - firstYear;

			//The loop is made to be able to reiterate the times equal to the time gap. 
			for (int i = 0; i <= timeGap; ++i) {

				//Uses the variable and compares all of the elements in the arrayList to see if there is a match. 
				for(int a = 0; a < movieCollection.size(); ++a) {

					boolean containsTitle = false;
					//Stores the movieLine onto the variable nextLine. 
					String nextLine = movieCollection.get(a).toString();
					//Compares the titles results and inserts the results into indexOf. 
					containsTitle = nextLine.contains(Integer.toString(firstYear));
					//If the line does indeed contain the year it will then run through the if statement. 
					if (containsTitle) {

						//Stores the movie line into the variable currentTitle. 
						index.add(a);
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

					index.add(i);
				}			
			}
		}

		ArrayList<Integer> titleIndex = new ArrayList<Integer>(index);
		ArrayList<String> moviesFound = new ArrayList<String>();
		for(int i = 0; i < titleIndex.size(); ++i) {

			moviesFound.add(movieCollection.get(titleIndex.get(i)).toString());
		}
		return moviesFound;
	}

	/**The method readInTitles goes through each line of the movie information and sections out only the title, so it could be stored in an 
	 * ArrayList. It is used to properly search for a movieTitle using the binarySearch method. 
	 * 
	 * @param sortType				The variable sortType determines how the output will be formatted. 
	 * @return						This method returns an ArrayList of String that contains all of the movie titles. 
	 * @throws IOException			To be able to read in the file
	 */
	public ArrayList<String> readTitles(ArrayList<Movie> movieCollection) throws IOException {

		movieTitles = new ArrayList<String>();
		
		for(int i = 0; i < movieCollection.size(); ++i) {

			movieTitles.add(movieCollection.get(i).getTitle());
		}
		return movieTitles;
	}

	@Override
	public String getDisplayText()
	{
		return "Movie: " + movieTitle + " " + movieDate + " " + releaseForm;
	}

	@Override
	public String getFileText()
	{
		return this.movieTitle + " (" + movieDate.replaceAll("[()]", "") + ") " + releaseForm + "\t" + movieDate;
	}
}