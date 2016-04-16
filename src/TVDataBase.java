import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project #2
 * CS 2334, Section ***  ***
 * *** 02-019-2016 ***
 * @author Ramon Valenzuela
 * <P>
 * This is the TvDataBase class of Project1. 
 * </P>
 * @version 1.0
 */
public class TVDataBase implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3385515282733881905L;
	private String mdbSeriesName;
	private String mdbSeriesStartYear;
	private String mdbSeriesEndYear;
	private ArrayList<TVEpisode> episodeList;
	private ArrayList<TVEpisode> episodeTitleList;
	
	private String mdbEpisodeSeriesName;
	private String mdbEpisodeStartYear;
	private String mdbEpisodeName;
	private String mdbEpisodeYear;
	private ArrayList<Series> seriesList;
	private ArrayList<Series> seriesTitleList;

	/*
	 * This method will take in a file containing the string arguments needed to create the 
	 * Series objects. First it will read the file line by line, parsing the strings based on 
	 * their values and finally store them into Series objects.
	 * 
	 * @param:
	 * fileName - The file containing the Series objects
	 * 
	 * @return:
	 * An ArrayList of type Series which will store all of the SeriesObjects.
	 */
	public ArrayList<Series> importTvDataBase(String fileName) throws IOException
	{
		String nextLine;
		
		FileReader fileReader = new FileReader(fileName);
		
		BufferedReader br = new BufferedReader(fileReader);
		
		String list[];
				
		episodeList = new ArrayList<TVEpisode>();
		setSeriesList(new ArrayList<Series>());
		episodeTitleList = new ArrayList<TVEpisode>();
		seriesTitleList = new ArrayList<Series>();
		
		nextLine = br.readLine();
		
		while(nextLine != null)
		{
			list = nextLine.split("[ |\t]+");
			
			if(list[list.length - 1].length() == 9)
			{
				Matcher rangeMatcher = Pattern.compile(Regexes.DATE_RANGE).matcher(nextLine);
				
				if (rangeMatcher.find())
				{
					mdbSeriesStartYear = rangeMatcher.group(1);
					mdbSeriesEndYear = rangeMatcher.group(2);
				}
				
				Matcher nameMatcher = Pattern.compile(Regexes.TV_SERIES_TITLE).matcher(nextLine);
						
				if (nameMatcher.find())
				{
					mdbSeriesName = nameMatcher.group(0);
				}
				
				
				Series seriesObject = new Series(mdbSeriesName,mdbSeriesStartYear,mdbSeriesEndYear);
				
				getSeriesList().add(seriesObject);				
			}
			else
			{
				// Find series name
				Matcher matcher = Pattern.compile(Regexes.TV_SERIES_TITLE).matcher(nextLine);
				if (matcher.find())
				{
					mdbEpisodeSeriesName = matcher.group(0);
				}
				
				// Find Start year and year
				matcher = Pattern.compile(Regexes.SINGLE_DATE).matcher(nextLine);
				if (matcher.find())
				{
					mdbEpisodeStartYear = matcher.group(0);
				}
				if (matcher.find())
				{
					mdbEpisodeYear = matcher.group(0);
				}
				
				// Find Episode name
				matcher = Pattern.compile(Regexes.EP_TITLE).matcher(nextLine);
				if (matcher.find())
				{
					mdbEpisodeName = matcher.group(0);
				}
				
				TVEpisode episodeObject = new TVEpisode(mdbEpisodeSeriesName,mdbEpisodeStartYear,mdbEpisodeName,mdbEpisodeYear);
				TVEpisode episodeTitle = new TVEpisode(mdbEpisodeSeriesName);
				
				episodeList.add(episodeObject);
				episodeTitleList.add(episodeTitle);
				
				Series parentSeries = getByTitle(mdbEpisodeSeriesName);
				if (parentSeries != null)
				{
					parentSeries.getEpisodeList().add(episodeObject);
				}
					
				
			}
			
			nextLine = br.readLine();
		}
			
		for(int index = 0; index < getSeriesList().size(); index++)
		{
			System.out.println(getSeriesList().get(index).toString());
		}
		for(int index = 0; index < episodeList.size(); index++)
		{
			System.out.println(episodeList.get(index).toString());
		}
			br.close();
			return getSeriesList();
	}
	
	
	/*
	 * This method will read in the ArrayList created by the tvDataBAse method. It will then search
	 * the list based on what the user inputs from the Driver class. The results will then be transfered to an
	 * ArrayList of type String and then be shown to the user.
	 * 
	 * @param:
	 * userInput - The input which the user has inputed based on what they want to search for
	 * 
	 * @return:
	 * An ArrayList of type String which will then be sent to be shown via GUI window.
	 */
	public ArrayList<String>tvDataSearcher(String[] questions)
	{
		
		System.out.println("TEST");
		ArrayList<String> positiveList = new ArrayList<String>();
		//System.out.println(seriesList.get(0));
		
		// For each loop will search the movie list and then store the matching values into a new ArrayList.
		if(questions[1] == "t" || questions[1] == "b")
		{
			if(questions[2] == "p")
			{
			for(Series seriesFound : getSeriesList())
			{
				System.out.println(seriesFound.getSeriesName());
				// This will be the comparison conditional being used. This also will take into account the 
				// case sensitive aspect of the user input.
				if(seriesFound.getSeriesName().contains(questions[4]) || seriesFound.getSeriesName().toLowerCase().contains(questions[4].toLowerCase()))
				{
					positiveList.add(seriesFound.toString());
				}	
				
			}
			}
			else
			{
				for(Series seriesFound : getSeriesList())
				{
					specificTitleSearch(seriesFound,positiveList,seriesTitleList);
				}
			}
			}
		
		else
		{
			for(Series seriesFound : getSeriesList())
			{
				yearSearch(seriesFound,getSeriesList(),seriesTitleList);
			}
		}

		return positiveList;
	}
	 
	
	public static int specificTitleSearch(Series seriesTitle, ArrayList<String> seriesTitleSearch, ArrayList<Series> seriesList) 
	{ 
		Collections.sort(seriesList);
		int containsTitle = Collections.binarySearch(seriesTitleSearch, (seriesTitle.toString()).toLowerCase());  
 		int count = 0; 
 		 
 		while (containsTitle >= 0) 
 		{  
 			//containsTitle = specificSearch(movieTitle, containsTitle, fileName, movieTitleSearch); 
 			Series currentTitle = seriesList.get(containsTitle); 
 			
			seriesTitleSearch.remove(containsTitle); 
 			containsTitle = Collections.binarySearch(seriesTitleSearch, (seriesTitle.toString()).toLowerCase()); 
 			++count; 
 		} 
 		return count; 
 	} 

	/** The yearSearch method will search for a movie by year. Depending on the input of the year or years will decide the method it will search. The user can  
	 * can input the information in a single year, or multiple years separated by a space, or a star year and end year separated by a dash to imply that it   
	 * will like all the movies that were made in the the years that was entered. Once a movie is found that matches the criteria it will increase the count.   
	 *   
	 * @param movieTitle			The movieTitle variable contains the year(s) that the user would like the movies created.   
	 * @param movieCollection		The movieCollection ArrayList contains the information to all of the movie that is in the file.   
	 * @return						It will return the variable count that determines if a move was found.   
	 */  
	public static ArrayList<Series> yearSearch(Series seriesTitle, ArrayList<Series> seriesList, ArrayList<Series> displaySeries) 
	{  
		  
		//The year is converted into a String because it is as a Movie object.   
		String seriesYear = seriesTitle.toString();  
		//Determines if the format is written in "xxxx, xxxx"  
		if(seriesYear.contains(",")) 
		{  
			  
			//Spits all of the year inputs and stores them into an ArrayList.   
			ArrayList<String> parsed = new ArrayList(Arrays.asList(seriesYear.split(", ")));  
		  
			//The loop is made to be able to reiterate the amount of year the user input. If user inputed 3 years, it loops three times.   
			for (int index = 0; index < parsed.size(); ++index) 
			{  
				  
				//Store the independent year into the variable year.   
				String year = parsed.get(index);  
				  
				//Uses the variable and compares all of the elements in the arrayList to see if there is a match.   
				for(int i = 0; i < seriesList.size(); ++i) 
				{  
			  
				boolean containsTitle = false;  
					//Stores the movie line into the variable nextLine.   
				String nextLine = seriesList.get(i).toString();  
					//Checks whether or not the line contains the year.    
				containsTitle = nextLine.contains(year);  
					//If the line does indeed contain the year it will then run through the if statement.   
					if (containsTitle) 
					{  
				  
						//Converts the movie line into a movie object to be able to parse it.   
						Series currentTitle = seriesList.get(i);  
						  
						//Parses the line and displays results in the permitted format.      
						displaySeries.add(new Series(currentTitle.toString()));  
						  
					}			  
						  
				}  
			}  
		}
		//Determines if the format is written in "xxxx-xxxx" starting with the start year and the end year.   
		else if (seriesYear.contains("-")) 
		{  
			//Spits the two years and stores the input into an ArrayList.   
			ArrayList<String> parsed = new ArrayList(Arrays.asList(seriesYear.split("-")));  
			//Converts the two years from String into int. 
			System.out.println(parsed.get(0));
			int firstYear = Integer.parseInt(parsed.get(0));  
			int secondYear = Integer.parseInt(parsed.get(1));  
			//Examines the gap between the two numbers.   
			int timeGap = secondYear - firstYear;  
			  
			//The loop is made to be able to reiterate the times equal to the time gap.   
			for (int i = 0; i <= timeGap; ++i) 
			{  

			//Uses the variable and compares all of the elements in the arrayList to see if there is a match.   
				for(int index = 0; index < seriesList.size(); ++index) 
				{  
					  
					boolean containsTitle = false;  
					//Stores the movieLine onto the variable nextLine.   
					String nextLine = seriesList.get(index).toString();  
					//Compares the titles results and inserts the results into indexOf.   
					containsTitle = nextLine.contains(Integer.toString(firstYear));  
					  
					//If the line does indeed contain the year it will then run through the if statement.   
					if (containsTitle) 
					{  
					  
						//Stores the movie line into the variable currentTitle.   
						Series currentTitle = seriesList.get(index);  
						  
						//Parses the line and displays results in the permitted format.    
						 
						displaySeries.add(new Series(currentTitle.toString()));  
					}  
				}  
				//Increase the year to be able to follow through the time gap.   
				++firstYear;  
			}  
			  
			  
		}  
		else 
		{  
			  
			System.out.println("AYYY");  
			//It will search through the list of movies to try and find the movie user entered.   
			for(int i = 0; i < seriesList.size(); ++i) 
			{  
			  
				boolean containsTitle = false;  
				//Stores the movie line into the variable nextLine.   
				String nextLine = seriesList.get(i).toString();  
				//Compares the titles results and inserts the results into indexOf.   
				containsTitle = nextLine.contains(seriesTitle.toString());  
				System.out.println(containsTitle);  
				System.out.println(nextLine);  
				  
				//If the line does indeed contain the year it will then run through the if statement.   
				if (containsTitle) 
				{  
				  
					//Stores the movie line into the variable currentTitle.   
					Series currentTitle = seriesList.get(i);  
					  
					//Parses the line and displays results in the permitted format.    
					 
					displaySeries.add(new Series(currentTitle.toString()));  
				}			  
			}  
		}  
				  
		//Will return -1 because it was not found in the ArrayList  
		return displaySeries;  
	}


	public ArrayList<Series> getSeriesList() {
		return seriesList;
	}
	
	public ArrayList<TVEpisode> getEpisodeList(){
		return episodeList;
	}


	public void setSeriesList(ArrayList<Series> seriesList) {
		this.seriesList = seriesList;
	}  
	
	/**
	 *  Finds a series by title
	 * @param title
	 * @return
	 */
	public Series getByTitle(String title)
	{
		if (seriesList.isEmpty())
			return null;
		for (Series s : this.seriesList)
		{
			if (s.getSeriesName() == null)
				continue;
			if (s.getSeriesName().equals(title))
				return s;
		}
		return null;
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
