/**
 * Project #3
 * CS 2334, Section ******
 * *** 02-05-2016 ***
 * <P>
 * This is the driver class of Project3. 
 * </P>
 * @version 1.2
 */
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * Project #2 with Eclipse as the IDE
 * CS 2334, Section 013
 * Ramon Valenzuela, Tyler Maxwell
 * 2/19/16
 * <P>
 * This class creates a simple media database system allowing 
 * users to search through a list of movies and TV episodes/series
 */

public class Driver 
{
	public static MediaMakerDataBase dataBase = new MediaMakerDataBase();
	
	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		String saveFile;
		ArrayList<Movie> displayMovie = new ArrayList<Movie>(); //list containing info to display to user on movie
		ArrayList<Series> displaySeries = new ArrayList<Series>(); //list containing info to display to user on series/episode
		
		//Beginning dialogue
		JOptionPane.showMessageDialog(null, "Welcome to the MDb system. \n"
				+ "This system allows you to search our database of movies, \n TV series/episodes and the people that make them."
				+ "\nClick Ok to begin.");
						
		
		//This ArrayList will contain the information to the movies that will be read in from the File and sort by title.  
		ArrayList<Movie> movieTitleCollection = new ArrayList<Movie>();
		
		//This ArrayList will contain the information to the movies that will read in the file and sort by year. 
		ArrayList<Movie> movieYearCollection = new ArrayList<Movie>();
		
		//Construct the ArrayList<Movie> that will hold all of the information for the File.
		ArrayList<String> movieTitleSearch = new ArrayList<String>();
		
		//The index of the movieTitleCollection or movieYearCollection
		ArrayList<Integer> indexTitle = new ArrayList<Integer>();
		
		//Constructs a movie variable, but will not initialize the variable.
		Movie movieTitle;
		
		//Creates an array that will store user's choices
		String[] questionReplies = new String[21];
		
		
		
		//Request files to be read in
		promptFiles(questionReplies);
		
		//assign variable names to the given files 
		String movieFile = questionReplies[11];
		String seriesFile = questionReplies[12];
		String actorFile = questionReplies[13];
		String directorFile = questionReplies[14];
		String producerFile = questionReplies[15];
		String binaryFile = questionReplies[16];
		
		//Create the Movie array
		Movie.resetArray(movieFile, movieTitleCollection);
		Movie.sortYear(movieYearCollection, movieFile);
		
		//Create the TV database
		TVDataBase tvDataBase = new TVDataBase();
		displaySeries = tvDataBase.tvDataBase(seriesFile);
	
		MediaMakerDataBase mediaMakerDataBase = new MediaMakerDataBase();
		
		MovieDataBase movieDataBase = new MovieDataBase();
		
		/**
		* linkedHashMap of movie makers will be created
		* each file(producer,director, actor) will be added to the LHM separately.
		*/
		
		String userInput = "y";
		
		/**
		 * Array positions are as follows:
		 * 0: search by movie, series, or both
		 * 1: search by title, year, or both
		 * 2: search by exact or partial match title
		 * 3: include episode title in search and display
		 * 4: title
		 * 5: year
		 * 6: sort type
		 * 7: displaySearchType
		 * 8: exactPartial
		 * 9: String displaySortType = ""; //SORTED BY (TITLE/YEAR)
		 * 
		 * 10: Read text or binary data
		 * 11: movieFile
		 * 12: seriesFile
		 * 13: actorFile
		 * 14: directorFile
		 * 15: producerFile
		 * 16: binaryFile
		 * 
		 * 17: sort for Media or people
		 * 18: name of MovieMaker
		 * 19: text or graphical results (people only)
		 * 20: pie or histogram results
		 * 21: save to text or binary file
		 */
		
		//JOptionPane display stuff
		String equalsstuff = "="; //80 equals signs
		
		//Creates 80 "=" signs
		for (int i = 0; i < 80; ++i)
		{
			equalsstuff = equalsstuff + "=";
		}	
		
		//While loop for the MDb user interface for searching/displaying info
		while (userInput.equalsIgnoreCase("y"))
		{
			//Gather information on what, and how the user wants to search/sort the data
			promptUser(questionReplies);
			
			//Depending on question answers, these next conditionals will navigate to the correct search method and 
			//Sent the correct parameters to that search method
			//This is assuming the search methods will return an ArrayList<String> which is put into either
			
			
			//displayMovie or displaySeries
			//Handles the display of movie and series search results.
			if (questionReplies[0].equalsIgnoreCase("m") || questionReplies[0].equalsIgnoreCase("b"))
			{
				indexTitle.clear();
				displayMovie.clear();
				
				if(questionReplies[6].equalsIgnoreCase("Y")) {
					movieTitleSearch = Movie.readInTitles(movieFile, movieTitleSearch,movieYearCollection, questionReplies[6]);
				}
				
				movieTitle = new Movie(questionReplies[4]);
				//If the user want to do a specific search it will direct the program here. 
				//This following section will primarily be based off of the user wanting to search by title. 
				if (questionReplies[1].equalsIgnoreCase("T")) {
				
					//If the user want to do a specific search it will direct the program here.
					if(questionReplies[2].equalsIgnoreCase("E")) {
						
						Movie.resetArray(movieFile, movieTitleCollection);
						movieTitleSearch = Movie.readInTitles(movieFile, movieTitleSearch,movieTitleCollection, questionReplies[6]);
					
						//The users input will use the specificTitleSearch and every time it find the movie that is being searched it will increase
						//count. If no movie is when searching for the movie it will return 0; 
						indexTitle = Movie.specificTitleSearch(movieTitle, movieTitleSearch, movieTitleCollection, movieFile, indexTitle);
						
						if (questionReplies[6].equalsIgnoreCase("T")) {
							for (int i = 0; i < indexTitle.size(); ++i) {
								//Once the movie is found it will get the entire movie line and assign it to the variable currentTitle
								Movie currentTitle = movieTitleCollection.get(indexTitle.get(i));
								//Separates the information to be able to appropriately prints it. 
								currentTitle.parseLine(currentTitle);
								displayMovie.add(new Movie(currentTitle.arrangeData()));
							}
						}
						else if(questionReplies[6].equalsIgnoreCase("Y")) {
							for (int i = 0; i < indexTitle.size(); ++i) {
								//Once the movie is found it will get the entire movie line and assign it to the variable currentTitle
								Movie currentTitle = movieYearCollection.get(indexTitle.get(i));
								//Separates the information to be able to appropriately prints it. 
								currentTitle.parseLine(currentTitle);
								displayMovie.add(new Movie(currentTitle.arrangeData()));
							}
						}
						String results = "";
						for(Movie a: displayMovie) {
							results += a.toString() + "\n";
						}
					
						
					}
			
					//If the user wants to do a partial search it will redirect the program here. 
					else if(questionReplies[2].equalsIgnoreCase("P")) {
				
						movieTitle = new Movie(questionReplies[4]);
						
						if (questionReplies[6].equalsIgnoreCase("T")) {
														
							indexTitle = Movie.containsTitleSearch(movieTitle, movieTitleCollection, indexTitle);
							for (int i = 0; i < indexTitle.size(); ++i) {
								//Once the movie is found it will get the entire movie line and assign it to the variable currentTitle
								Movie currentTitle = movieTitleCollection.get(indexTitle.get(i));
								//Separates the information to be able to appropriately prints it. 
								currentTitle.parseLine(currentTitle);
								displayMovie.add(new Movie(currentTitle.arrangeData()));
							}
						}
						else if(questionReplies[6].equalsIgnoreCase("Y")) {
							
							Movie.sortYear(movieYearCollection, movieFile);
							
							indexTitle = Movie.containsTitleSearch(movieTitle, movieYearCollection, indexTitle);

							for (int i = 0; i < indexTitle.size(); ++i) {
								//Once the movie is found it will get the entire movie line and assign it to the variable currentTitle
								Movie currentTitle = movieYearCollection.get(indexTitle.get(i));
								//Separates the information to be able to appropriately prints it. 
								currentTitle.parseLine(currentTitle);
								displayMovie.add(new Movie(currentTitle.arrangeData()));
							}
						}
						
						String results = "";
						for(Movie a: displayMovie) {
							results += a.toString() + "\n";
						}
						//It will reset the array in case it was modified when being parsed. 
						Movie.resetArray(movieFile, movieTitleCollection);
						Movie.sortYear(movieYearCollection, movieFile);

					}
				}
				
				else if (questionReplies[1].equalsIgnoreCase("Y")) 
				{	
					Movie.resetArray(movieFile, movieTitleCollection);
					Movie.sortYear(movieYearCollection, movieFile);
					
					Movie movieYear = new Movie(questionReplies[5]);
					//The users input will use the yearSearch and every time it find the movie that is being searched it will increase
					if (questionReplies[6].equalsIgnoreCase("T")) 
					{
						indexTitle = Movie.yearSearch(movieYear, movieTitleCollection, indexTitle);
						Collections.sort(indexTitle);
					}
					else 
						indexTitle = Movie.yearSearch(movieYear, movieYearCollection, indexTitle);
					
					if (questionReplies[6].equalsIgnoreCase("T")) 
					{
						for (int i = 0; i < indexTitle.size(); ++i) 
						{
							//Once the movie is found it will get the entire movie line and assign it to the variable currentTitle
							Movie currentTitle = movieTitleCollection.get(indexTitle.get(i));
							//Separates the information to be able to appropriately prints it.
							currentTitle.parseLine(currentTitle);
							displayMovie.add(new Movie(currentTitle.arrangeData()));
						}
						
					}
					else if(questionReplies[6].equalsIgnoreCase("Y")) 
					{
						for (int i = 0; i < indexTitle.size(); ++i) 
						{
							//Once the movie is found it will get the entire movie line and assign it to the variable currentTitle
							Movie currentTitle = movieYearCollection.get(indexTitle.get(i));
							//Separates the information to be able to appropriately prints it. 
							currentTitle.parseLine(currentTitle);
							displayMovie.add(new Movie(currentTitle.arrangeData()));
						}
					}
					
					String results = "";
					for(Movie a: displayMovie) 
					{
						results += a.toString() + "\n";
					}
				}
					
			}
			
				
				

		//Creates a single string from the displayMovie with a newline after each entry
		String resultM = "";
		for (Movie b : displayMovie)
		{
			resultM += b.toString() + "\n";
		}
		//Creates a single string from the displaySeries with a newline after each entry
		String resultS = "";
		for (Series c : displaySeries)
		{
			resultS += c.toString() + "\n";
		}

		//The JTextArea that holds rext to be displayed withing the JOptionPane
		JTextArea textArea = new JTextArea("Insert your Text here");
		JScrollPane scrollPane = new JScrollPane(textArea);  
		textArea.setLineWrap(true);  
		textArea.setWrapStyleWord(true); 
		scrollPane.setPreferredSize( new Dimension( 640, 500 ) );
		
		//Display answers to user in specified manner found on Project2 PDF
		//If searching by movie
		if (questionReplies[0].equalsIgnoreCase("m"))
		{
			//If searching movies by title
			if (questionReplies[1].equalsIgnoreCase("t"))
			{
				textArea.setText(questionReplies[7] + "\n" + questionReplies[8] + questionReplies[4] +  "\n"
						+ "YEARS: ANY \n" + questionReplies[9] + "\n" + equalsstuff + "\n" + resultM);
			}
			//If searching movies by year
			else if (questionReplies[1].equalsIgnoreCase("y"))
			{
				textArea.setText(questionReplies[7] + "\n" + "TITLE: ANY \n" + "YEARS: "
							+ questionReplies[5] + "\n" + questionReplies[9] + "\n" + equalsstuff + "\n" + resultM);
			}
			//If searching movies by both year and title
			else if (questionReplies[1].equalsIgnoreCase("b"))
			{
				textArea.setText(questionReplies[7] + "\n" + questionReplies[8] + questionReplies[4] + "\n" 
						+ "YEARS: " + questionReplies[5] + "\n" + questionReplies[9] + "\n" + equalsstuff + "\n" + resultM);
			}
		}
		//If searching by series
		else if (questionReplies[0].equalsIgnoreCase("s"))
		{
			//If searching series by title
			if (questionReplies[1].equalsIgnoreCase("t"))
			{
				textArea.setText(questionReplies[7] + "\n" + questionReplies[8] + questionReplies[4] +  "\n"
						+ "YEARS: ANY \n" + questionReplies[9] + "\n" + equalsstuff + "\n" + resultS);
			}
			//If searching series by year
			else if (questionReplies[1].equalsIgnoreCase("y"))
			{
				textArea.setText(questionReplies[7] + "\n" + "TITLE: ANY \n" + "YEARS: "
							+ questionReplies[5] + "\n" + questionReplies[9] + "\n" + equalsstuff + "\n" + resultS);
			}
			//If searching series by both year and title
			else if (questionReplies[1].equalsIgnoreCase("b"))
			{
				textArea.setText(questionReplies[7] + "\n" + questionReplies[8] + questionReplies[4] + "\n" 
						+ "YEARS: " + questionReplies[5] + "\n" + questionReplies[9] + "\n" + equalsstuff + "\n" + resultS);
			}
		}
		//If searching for both
		else if (questionReplies[0].equalsIgnoreCase("b"))
		{
			//If searching both movies and series by title
			if (questionReplies[1].equalsIgnoreCase("t"))
			{
				textArea.setText(questionReplies[7] + "\n" + questionReplies[8] + questionReplies[4] +  "\n"
						+ "YEARS: ANY \n" + questionReplies[9] + "\n" + equalsstuff + "\n" + resultM + resultS);
			}
			//If searching both movies and series by year
			else if (questionReplies[1].equalsIgnoreCase("y"))
			{
				textArea.setText(questionReplies[7] + "\n" + "TITLE: ANY \n" + "YEARS: "
						+ questionReplies[5] + "\n" + questionReplies[8] + "\n" + equalsstuff + "\n" + resultM + resultS);
			}
			//If searching both movie and series by both title and year
			else if (questionReplies[1].equalsIgnoreCase("b"))
			{
				textArea.setText(questionReplies[7] + "\n" + questionReplies[8] + questionReplies[4] + "\n" 
						+ "YEARS: " + questionReplies[5] + "\n" + questionReplies[8] + "\n" + equalsstuff + "\n" + resultM + resultS);
			}
		}
		
		JOptionPane.showMessageDialog(null, scrollPane, "Results",  
                JOptionPane.OK_OPTION);
		
		//Optional save data to file
		userInput = JOptionPane.showInputDialog(null, "Save data to file (y/n)?");
		if (userInput == null)
		{
			System.exit(0);
		}
		if (userInput.equalsIgnoreCase("y"))
		{
			
			String fileSaveType = JOptionPane.showInputDialog(null,  "Save data as (t)ext or (b)inary");
			if (fileSaveType.equalsIgnoreCase("t"))
			{
				
			}
			else if (fileSaveType.equalsIgnoreCase("b"))
			{
				File fileName = new File("MDB.dat");
				FileOutputStream fileOutputStream = new FileOutputStream(fileName);
				ObjectOutputStream objectOutputStream  = new ObjectOutputStream(fileOutputStream);
				
				//objectOutputStream.writeObject(dataBase);
				objectOutputStream.writeObject(tvDataBase);
				objectOutputStream.writeObject(movieTitleCollection);
				objectOutputStream.close();
				
			}
			
			
			/**
			 * The following section saves results in text format.
			 */
			//Write data to specified file using the FileWriter and BufferedWriter objects
			//Tested and working.
			saveFile = JOptionPane.showInputDialog(null, "Enter the name of the file you wish to save data to:");	
			//Creates file of name saveFile
			FileWriter outfile = new FileWriter(saveFile);
			BufferedWriter bw = new BufferedWriter(outfile);
			
			//Check to see what ArrayList to get info from
			if (questionReplies[0].equals("m") || questionReplies[0].equals("b"))
			{
				for (int i = 0; i < displayMovie.size(); ++i)
				{
					//Writes each element in displayMovie to file saveFile
					bw.write(displayMovie.get(i).toString());
					bw.newLine();
				}
			}
			//Same as above. It is an if so the "b" will work (i.e. enter both conditionals if question1 = "b")
			if (questionReplies[0].equals("s") || questionReplies[0].equals("b"))
			{
				for (int i = 0; i < displaySeries.size(); ++i)
				{
					//Writes each element in displaySeries to file saveFile
					bw.write(displaySeries.get(i).toString());
					bw.newLine();
				}
			}
			
			
			//This command saves the file
			bw.close();
			JOptionPane.showMessageDialog(null, "Saved");
		}
		
		//Check if the user wants to make another search.
		searchAgain(questionReplies);
		}	
	} //END MAIN

	
	
	/**promptUser method will greet the user and explain the idea of the program and will ask the user to
	 * enter the name of the movie they would like to receive information for. 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void promptFiles(String[] questionReplies) throws IOException, ClassNotFoundException {
		
		//This next block of code will ask the user questions and put their answers into the above strings which will
		//be used to determine which search method will be implemented
		
		//#####################################
		questionReplies[10] = JOptionPane.showInputDialog(null, "Read (t)ext or (b)inary data?");
		
		if (questionReplies[10] == null)
		{
			System.exit(0);
		}
		else if (questionReplies[10].equalsIgnoreCase("t"))	
		{
			
			questionReplies[11] = "StarTrekMovies.txt";
			questionReplies[12] = "StarTrekTV.txt";	
			questionReplies[13] = "SomeActors.txt";
			questionReplies[14] = "SomeProducers.txt";
			questionReplies[15] = "SomeDirectors.txt";
			
			//TODO undo commenting and remove above section(was used for ease-of-testing)
			//questionReplies[11] = promptFilename("Enter the name of the file containing movie data:");
			//questionReplies[12] = promptFilename("Enter the name of the file containing series data:");
			//questionReplies[13] = promptFilename("Enter the name of the file containing actor data:");
			//questionReplies[14] = promptFilename("Enter the name of the file containing producer data:");
			//questionReplies[15] = promptFilename("Enter the name of the file containing director data:");
			
			dataBase.actorDataBase(questionReplies[13]);
			dataBase.producerDataBase(questionReplies[14]);
			dataBase.directorDataBase(questionReplies[15]);
		}
		else if(questionReplies[10].equalsIgnoreCase("b"))
			questionReplies[16] = promptFilename("Enter the name of the file containing the database information");
		
		//File fn = new File(questionReplies[16]);
		//FileInputStream fIS = new FileInputStream(fn);
		//ObjectInputStream oIS = new ObjectInputStream(fIS);
		//MediaMaker mm = (MediaMaker) oIS.readObject();
		//oIS.close();
	}
		
	/**
	 * 	Proceeds through a list of questions to determine how to search/sort information
	 * @param questionReplies	The array of responses
	 * @throws IOException 
	 */
	public static void promptUser(String[] questionReplies) throws IOException{
		questionReplies[17] = JOptionPane.showInputDialog(null, "Search for (m)edia or (p)eople?");
		
		
		//This block handles prompts relating to searching for media makers, rather than media.
		if (questionReplies[17] == null)
		{
			System.exit(0);
		}else if(questionReplies[17].equalsIgnoreCase("p"))
		{
			questionReplies[2] = JOptionPane.showInputDialog(null, "Search for (e)xact or (p)artial matches?");
			if (questionReplies[2] == null){
				System.exit(0);
			}
			questionReplies[18] = JOptionPane.showInputDialog(null, "Enter the name of an actor, director, or producer:");
			
			ArrayList<MediaMaker> results = new ArrayList<>();
			
			if (questionReplies[2].equalsIgnoreCase("e")){
				
				results.add(dataBase.specificTitleSearch(questionReplies[18]));
			}else if (questionReplies[2].equalsIgnoreCase("p")){
				
				results = dataBase.mediaMakerSearcher(questionReplies);
			}
			
			
			if (questionReplies[2].equalsIgnoreCase("e"))
				questionReplies[19] = JOptionPane.showInputDialog(null, "Display (t)ext or (g)raphical results?");
			else
				questionReplies[19] = "t";
			String text = "";
			if (questionReplies[19] == null){
				System.exit(0);
			}else if(questionReplies[19].equalsIgnoreCase("t"))
			{
				//The JTextArea that holds rext to be displayed withing the JOptionPane
				JTextArea textArea = new JTextArea("Insert your Text here");
				JScrollPane scrollPane = new JScrollPane(textArea);  
				textArea.setLineWrap(true);  
				textArea.setWrapStyleWord(true); 
				scrollPane.setPreferredSize( new Dimension( 640, 500 ) );
				
				
				text += "SEARCHED PEOPLE\n";
				text += (questionReplies[2].equalsIgnoreCase("p") ? "PARTIAL " : "EXACT ") + "NAME: " + questionReplies[18] + "\n";
				text += "================================================================================\n";
				
				for (MediaMaker maker : results)
				{
					text += maker.toString() + "\n";
					for (Credit c : maker.getMovieCredits())
					{
						text += c.outputString()+"\n";
					}
					for (Credit c : maker.getSeriesCredits())
					{
						text += c.outputString()+"\n";
					}
					text += "--------------------------------------------------------------------------------\n";
				}
				
				
				
				textArea.setText(text);
				JOptionPane.showMessageDialog(null, scrollPane, "Results",  
		                JOptionPane.OK_OPTION);
			}else if(questionReplies[19].equalsIgnoreCase("g"))
			{
				questionReplies[20] = JOptionPane.showInputDialog(null, "Display (p)ie chart or (h)istogram?");
				if (questionReplies[20].equalsIgnoreCase("p"))
				{
					new Display(Display.ChartType.PIE_CHART, results.get(0));
					JOptionPane.showMessageDialog(null, "Displaying graph...");
				}
				else if (questionReplies[20].equalsIgnoreCase("h"))
				{
					new Display(Display.ChartType.HISTOGRAM, results.get(0));
					JOptionPane.showMessageDialog(null, "Displaying graph...");
				}	
			}
			
			//Optional save data to file
			String userInput = JOptionPane.showInputDialog(null, "Save data to file (y/n)?");
			if (userInput == null)
			{
				System.exit(0);
			}
			if (userInput.equalsIgnoreCase("y"))
			{
				
				String fileSaveType = JOptionPane.showInputDialog(null,  "Save data as (t)ext or (b)inary");
				if (fileSaveType.equalsIgnoreCase("t"))
				{
					
				}
				else if (fileSaveType.equalsIgnoreCase("b"))
				{
					
				}
				
				
				/**
				 * The following section saves results in text format.
				 */
				//Write data to specified file using the FileWriter and BufferedWriter objects
				//Tested and working.
				 String saveFile = JOptionPane.showInputDialog(null, "Enter the name of the file you wish to save data to:");	
				//Creates file of name saveFile
				FileWriter outfile = new FileWriter(saveFile);
				BufferedWriter bw = new BufferedWriter(outfile);
				
				bw.write(text);

				//This command saves the file
				bw.close();
				JOptionPane.showMessageDialog(null, "Saved");
			}
		}			//**END MOVIE MAKER SEARCHING**
		

		//Next conditional checks to see if the user clicked "X", "Cancel", or didn't enter any data.
		//If so, the program gracefully exits. This is done after every JOptionPane dialog from here
		//on out
		
		//This smection governs responses relating to searching Media.
		if(questionReplies[17].equalsIgnoreCase("m"))
			questionReplies[0] = JOptionPane.showInputDialog(null, "Search by (m)ovies, (s)eries, or (b)oth?");	
		
		if (questionReplies[0] == null)
		{
			System.exit(0);
		}
		if (questionReplies[0].equalsIgnoreCase("m"))
			questionReplies[7] = "SEARCHED MOVIES";
		else if (questionReplies[0].equalsIgnoreCase("s"))
			questionReplies[7] = "SEARCHED TV SERIES AND TV EPISODE";
		else if (questionReplies[0].equalsIgnoreCase("b"))
			questionReplies[7] = "SEARCHED MOVIES, TV SERIES, AND TV EPISODE";
		
			
		
		questionReplies[1] = JOptionPane.showInputDialog(null, "Search by (t)itle, (y)ear, or (b)oth?");
		if (questionReplies[1] == null)
		{
			System.exit(0);
		}
		
		//Exact, Partial match
		if (questionReplies[1].equalsIgnoreCase("T")|| questionReplies[1].equalsIgnoreCase("B")){
			
			questionReplies[2] = JOptionPane.showInputDialog(null, "Search for (e)xact or (p)artial matches?");
			if (questionReplies[2] == null)
			{
				System.exit(0);
			}
			if (questionReplies[2].equalsIgnoreCase("e"))
				questionReplies[8] = "EXACT TITLE: ";
			else if (questionReplies[2].equalsIgnoreCase("p"))
				questionReplies[8] = "PARTIAL TITLE: ";
		}
		
		//Include episode titles in search and output
		if (questionReplies[1].equalsIgnoreCase("s") || questionReplies[0].equalsIgnoreCase("b"))
		{
			if (questionReplies[1].equalsIgnoreCase("t") || questionReplies[0].equalsIgnoreCase("b"))
				questionReplies[3] = JOptionPane.showInputDialog(null, "Include episode titles in search and output (y/n)?");
				if (questionReplies[3] == null)
				{
					System.exit(0);
				}
		}
		
		//Ask for title
		if (questionReplies[1].equalsIgnoreCase("t") || questionReplies[1].equalsIgnoreCase("b"))
		{
			questionReplies[4] = JOptionPane.showInputDialog(null, "Title?");
			if (questionReplies[4] == null)
			{
				System.exit(0);
			}
		}
		
		//Ask for year
		if (questionReplies[1].equalsIgnoreCase("y") || questionReplies[1].equalsIgnoreCase("b"))
		{
			questionReplies[5] = JOptionPane.showInputDialog(null, "Year (single, multiple seperated by commas, or range seperated by hyphon)?");
			if (questionReplies[5] == null)
			{
				System.exit(0);
			}
		}
		
		
		
		//Ask for sort type
		questionReplies[6] = JOptionPane.showInputDialog(null, "Sort by (t)itle or (y)ear");
		if (questionReplies[6] == null)
		{
			System.exit(0);
		}
		if (questionReplies[6].equalsIgnoreCase("t"))
			questionReplies[9] = "SORTED BY TITLE";
		else if (questionReplies[6].equalsIgnoreCase("y"))
			questionReplies[9] = "SORTED BY YEAR";
		
		
		
		return; 
		
	}
	
	/**
	 * Ask user for a file location, and verify that it exists. Close program if no files found more than twice
	 * @param message
	 * @return
	 * @author Tyler Maxwell, Daniel Schon
	 */
	private static String promptFilename(String message)
	{
		File file;
		String response;
		int count = 0;
		
		do
		{
			if (count > 1)
				System.exit(0);
			
			//Get user's response
			response = JOptionPane.showInputDialog(null, message);
			
			//Create a test file to check if it exists
			file = new File(response);
			
			if(response.equals(""))
				count++;
		} while (!file.exists());
		
		return response;
	}
	
	//resets the search criteria of the response array.
	private static String[] reset(String[] response)
	{
		for (int i = 0; i < 10; i++) {
			response[i] = null;
		}
		for (int i = 17; i < response.length; i++) {
			response[i] = null;
		}
		return response;

	}
	
	/**
	 * Asks if the user wants to begin a new search, setting the values of the response array
	 * to null, and beginning a new set of user prompts.
	 * 
	 * @author Tyler Maxwell
	 * @throws IOException 
	 */
	private static void searchAgain(String[] responses) throws IOException
	{
		String confirm = JOptionPane.showInputDialog(null, "Search again? (Y/N)");
		if (confirm == null || confirm.equalsIgnoreCase("n"))
		{
			System.exit(0);
		}else if (confirm.equalsIgnoreCase("y"))
		{
			reset(responses);
			promptUser(responses);
		}
	}
	
}


