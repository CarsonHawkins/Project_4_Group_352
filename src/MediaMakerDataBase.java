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
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project #4
 * CS 2334, Section 013
 * 04-21-16
 * @author Carson Hawkins, Daniel Schon, and Eric Morales
 * <P>
 * This is the MediaMakerDataBase class of Project4. 
 * </P>
 * @version 1.0
 */
public class MediaMakerDataBase implements Serializable
{
	/** Field that java created for the serialization */
	private static final long serialVersionUID = -5776795248719360180L;
	/** A linkedHashMap that will have a String representation of the MediaMaker as the key, and the mediaMaker itself as the value */
	private LinkedHashMap<String, MediaMaker> mediaMakerMap = new LinkedHashMap<String, MediaMaker>();;

	/*
	 * This method will read in the file containing the information of interest and then begin parsing that 
	 * information into Movie objects. It will achieve this by reading the data one line at a time.
	 * It will then commence reading the line backwards in order to simplify the process.
	 * 
	 * @param
	 * fileName - the name of the file to be read.
	 * 
	 * @return 
	 * An ArrayList<Actor> objects
	 */
	public LinkedHashMap<String, MediaMaker> importActorDataBase(String fileName)throws IOException
	{
		String nextLine; 
		FileReader fileReader = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fileReader);
		
		nextLine = br.readLine();
		
		ArrayList<Credit> movieCredits = new ArrayList<Credit>();
		ArrayList<Credit> episodeCredits = new ArrayList<Credit>();
		Actor actor = null;
		while(nextLine != null)
		{	
			if (nextLine.length() >0 && nextLine.charAt(0) != '\t')
			{
				// If this is an actor
				String mdbMediaFirstName = "";
				String mdbMediaLastName = "";
				String mdbMediaDisambiguationNumber = "";
				
				
				Matcher disambiguation = Pattern.compile(Regexes.DISAMBIGUATION_NUMBER).matcher(nextLine);
				
				if (disambiguation.find())
				{
					mdbMediaDisambiguationNumber = disambiguation.group(1);
				}
				Matcher actorFirstName = Pattern.compile(Regexes.FIRST_NAME).matcher(nextLine);
				
				if (actorFirstName.find())
				{
					mdbMediaFirstName = actorFirstName.group(1).trim();
				}
				Matcher actorLastName = Pattern.compile(Regexes.LAST_NAME).matcher(nextLine);
				
				if (actorLastName.find())
				{
					mdbMediaLastName = actorLastName.group(1);
				}
				
				movieCredits = new ArrayList<Credit>(); 
				episodeCredits = new ArrayList<Credit>();
				
				String fullName = (mdbMediaLastName + ", " + mdbMediaFirstName + " " + mdbMediaDisambiguationNumber).trim();
				if (!mediaMakerMap.containsKey(fullName))
				{
					actor = new Actor(mdbMediaFirstName,mdbMediaLastName,mdbMediaDisambiguationNumber,movieCredits,episodeCredits);
					mediaMakerMap.put(actor.toString() ,actor);
				}
				else
				{
					movieCredits = mediaMakerMap.get(fullName).getMovieCredits();
					episodeCredits = mediaMakerMap.get(fullName).getSeriesCredits();
				}
				
				//Cut out the actor name because we're finished with it
				String[] split = nextLine.split("\t+");
				nextLine = "\t" + split[1];
			}
			
			if(nextLine.contains("\t"))
			{
				nextLine = nextLine.replaceAll("\t", "");
				
				String mdbActorRole = "";
				String mdbBilling = "";
				String mdbCreditMovie = "";
				String mdbReleaseYear = "";
				String mdbMediaType = "";
				String mdbArchiveFootage = "";
				String mdbUncredited = "";
				String mdbEpisodeYear = "";
				String mdbCreditSeries = "";
				String mdbCreditInfo = "";
				
				Matcher role = Pattern.compile(Regexes.ACTOR_ROLE).matcher(nextLine);
				
				if (role.find())
				{
					mdbActorRole = role.group(1);
				}
				
				Matcher billing = Pattern.compile(Regexes.BILLING).matcher(nextLine);
				if (billing.find())
				{
					mdbBilling = billing.group(1);
				}
				
				Matcher movieTitleMatcher = Pattern.compile(Regexes.MOVIE_TITLE).matcher(nextLine);
				
				if (movieTitleMatcher.find())
				{
					mdbCreditMovie = movieTitleMatcher.group(1);
				}
				
				Matcher movieReleaseYear = Pattern.compile(Regexes.SINGLE_DATE_PARENTHESES).matcher(nextLine);
				
				if (movieReleaseYear.find())
				{
					mdbReleaseYear = movieReleaseYear.group(1);
				}
				
				Matcher movieMediaType = Pattern.compile(Regexes.MOVIE_VENUE).matcher(nextLine);
				
				if (movieMediaType.find())
				{
					mdbMediaType = movieMediaType.group(1);
				}
				
				Matcher archiveFootageMatcher = Pattern.compile(Regexes.ARCHIVE_FOOTAGE).matcher(nextLine);
				
				if (archiveFootageMatcher.find())
				{
					mdbArchiveFootage = archiveFootageMatcher.group(1);
				}
				
				Matcher uncreditedMatcher = Pattern.compile(Regexes.UNCREDITED).matcher(nextLine);
				
				if (uncreditedMatcher.find())
				{
					mdbUncredited = uncreditedMatcher.group(1);
				}
				
				Matcher seriesTitleMatcher = Pattern.compile(Regexes.TV_SERIES_TITLE).matcher(nextLine);
				
				if (seriesTitleMatcher.find())
				{
					mdbCreditSeries = seriesTitleMatcher.group(0);
				}
				
				Matcher seriesInfoMatcher = Pattern.compile(Regexes.EP_TITLE).matcher(nextLine);
				
				if (seriesInfoMatcher.find())
				{
					mdbCreditInfo = seriesInfoMatcher.group(1);
				}
				
				Matcher episodeYearMatcher = Pattern.compile(Regexes.EP_YEAR).matcher(nextLine);
				if (episodeYearMatcher.find())
				{
					mdbEpisodeYear = episodeYearMatcher.group(1);
				}
				
				if (!mdbCreditSeries.equals(""))
				{
					TVEpisode episode = new TVEpisode(mdbCreditSeries,mdbReleaseYear,mdbCreditInfo, mdbEpisodeYear);
					ActingCredit episodeCredit = new ActingCredit(episode, Credit.MediaType.EPISODE, mdbArchiveFootage,mdbUncredited,mdbActorRole,mdbBilling,actor);
					episodeCredits.add(episodeCredit);
				}
				else if (!mdbCreditMovie.equals(""))
				{
					Movie movie = new Movie(mdbCreditMovie,mdbReleaseYear,mdbMediaType,mdbReleaseYear);
					ActingCredit movieCredit = new ActingCredit(movie, Credit.MediaType.MOVIE,mdbArchiveFootage,mdbUncredited,mdbActorRole,mdbBilling,actor);
					movieCredits.add(movieCredit);
				}
			}
			
			
			nextLine = br.readLine();
					
		}
		
		br.close();
		return mediaMakerMap;	
	}
	
	/*
	 * This method will read in the file containing the information of interest and then begin parsing that 
	 * information into Director objects. It will achieve this by reading the data one line at a time.
	 * It will then commence reading the line backwards in order to simplify the process.
	 * 
	 * @param
	 * fileName - the name of the file to be read.
	 * 
	 * @return 
	 * An ArrayList<Director> objects
	 */
	private LinkedHashMap<String, MediaMaker> importNonActorDataBase(String fileName, Credit.MakerType type)throws IOException
	{
		String nextLine;
		
		FileReader fileReader = new FileReader(fileName);
		
		BufferedReader br = new BufferedReader(fileReader);
		
		nextLine = br.readLine();
		
		ArrayList<Credit> movieCredits = new ArrayList<Credit>();
		ArrayList<Credit> episodeCredits = new ArrayList<Credit>();
		MediaMaker maker = null;
		while(nextLine != null)
		{	
			if (nextLine.length() >0 && nextLine.charAt(0) != '\t')
			{
				// If this is an actor
				String mdbMediaFirstName = "";
				String mdbMediaLastName = "";
				String mdbMediaDisambiguationNumber = "";
				
				
				Matcher disambiguation = Pattern.compile(Regexes.DISAMBIGUATION_NUMBER).matcher(nextLine);
				
				if (disambiguation.find())
				{
					mdbMediaDisambiguationNumber = disambiguation.group(1);
				}
				Matcher actorFirstName = Pattern.compile(Regexes.FIRST_NAME).matcher(nextLine);
				
				if (actorFirstName.find())
				{
					mdbMediaFirstName = actorFirstName.group(1).trim();
				}
				Matcher actorLastName = Pattern.compile(Regexes.LAST_NAME).matcher(nextLine);
				
				if (actorLastName.find())
				{
					mdbMediaLastName = actorLastName.group(1);
				}
				
				movieCredits = new ArrayList<Credit>(); 
				episodeCredits = new ArrayList<Credit>();
				
				if (type == Credit.MakerType.PRODUCER)
					maker = new Producer(mdbMediaFirstName,mdbMediaLastName,mdbMediaDisambiguationNumber,movieCredits,episodeCredits);
				else
					maker = new Director(mdbMediaFirstName,mdbMediaLastName,mdbMediaDisambiguationNumber,movieCredits,episodeCredits);
					
				
				if (!mediaMakerMap.containsKey(maker.toString()))
				{
					mediaMakerMap.put(maker.toString() ,maker);
				}
				else
				{
					movieCredits = mediaMakerMap.get(maker.toString()).getMovieCredits();
					episodeCredits = mediaMakerMap.get(maker.toString()).getSeriesCredits();
				}
				
				//Cut out the actor name because we're finished with it
				String[] split = nextLine.split("\t+");
				nextLine = "\t" + split[1];
			}
			
			if(nextLine.contains("\t"))
			{
				nextLine = nextLine.replaceAll("\t", "");
				
				String mdbCreditMovie = "";
				String mdbReleaseYear = "";
				String mdbMediaType = "";
				String mdbArchiveFootage = "";
				String mdbUncredited = "";
				String mdbEpisodeYear = "";
				
				String mdbCreditSeries = "";
				String mdbCreditInfo = "";
				
				Matcher movieTitleMatcher = Pattern.compile(Regexes.MOVIE_TITLE).matcher(nextLine);
				
				if (movieTitleMatcher.find())
				{
					mdbCreditMovie = movieTitleMatcher.group(1);
				}
				
				Matcher movieReleaseYear = Pattern.compile(Regexes.SINGLE_DATE_PARENTHESES).matcher(nextLine);
				
				if (movieReleaseYear.find())
				{
					mdbReleaseYear = movieReleaseYear.group(1);
				}
				
				Matcher movieMediaType = Pattern.compile(Regexes.MOVIE_VENUE).matcher(nextLine);
				
				if (movieMediaType.find())
				{
					mdbMediaType = movieMediaType.group(1);
				}
				
				Matcher archiveFootageMatcher = Pattern.compile(Regexes.ARCHIVE_FOOTAGE).matcher(nextLine);
				
				if (archiveFootageMatcher.find())
				{
					mdbArchiveFootage = archiveFootageMatcher.group(1);
				}
				
				Matcher uncreditedMatcher = Pattern.compile(Regexes.UNCREDITED).matcher(nextLine);
				
				if (uncreditedMatcher.find())
				{
					mdbUncredited = uncreditedMatcher.group(1);
				}
				
				Matcher seriesTitleMatcher = Pattern.compile(Regexes.TV_SERIES_TITLE).matcher(nextLine);
				
				if (seriesTitleMatcher.find())
				{
					mdbCreditSeries = seriesTitleMatcher.group(0);
				}
				
				Matcher seriesInfoMatcher = Pattern.compile(Regexes.EP_TITLE).matcher(nextLine);
				
				if (seriesInfoMatcher.find())
				{
					mdbCreditInfo = seriesInfoMatcher.group(1);
				}
				
				Matcher episodeYearMatcher = Pattern.compile(Regexes.EP_YEAR).matcher(nextLine);
				if (episodeYearMatcher.find())
				{
					mdbEpisodeYear = episodeYearMatcher.group(1);
				}
				
				if (!mdbCreditSeries.equals(""))
				{
					TVEpisode episode = new TVEpisode(mdbCreditSeries,mdbReleaseYear,mdbCreditInfo,mdbEpisodeYear);
					Credit episodeCredit = new Credit(episode, Credit.MediaType.EPISODE, type, mdbArchiveFootage, mdbUncredited, maker);
					episodeCredits.add(episodeCredit);
				}
				else if (!mdbCreditMovie.equals(""))
				{
					Movie movie = new Movie(mdbCreditMovie,mdbReleaseYear,mdbMediaType,mdbEpisodeYear);
					Credit movieCredit = new Credit(movie, Credit.MediaType.MOVIE, type, mdbArchiveFootage,mdbUncredited, maker);
					movieCredits.add(movieCredit);
				}
			}
			
			
			
			nextLine = br.readLine();
					
		}
		
		for(String key : mediaMakerMap.keySet())
		{
			System.out.println(mediaMakerMap.get(key).toString());
			
			for (Credit mc : mediaMakerMap.get(key).getMovieCredits())
			{
				System.out.println("\t" + mc.toString());
			}
			for (Credit sc : mediaMakerMap.get(key).getSeriesCredits())
			{
				System.out.println("\t" + sc.toString());
			}
		}
		
		br.close();
		return mediaMakerMap;	
	}
	
	/**
	 * This method will read in the file containing the information of interest and then begin parsing that 
	 * information into Producer objects. It will achieve this by reading the data one line at a time.
	 * It will then commence reading the line backwards in order to simplify the process.
	 * 
	 * @param
	 * fileName - the name of the file to be read.
	 * 
	 * @return 
	 * An ArrayList<Actor> objects
	 */
	public LinkedHashMap<String, MediaMaker> importProducerDataBase(String fileName)throws IOException
	{
		return importNonActorDataBase(fileName, Credit.MakerType.PRODUCER);
	}
	
	/**
	 * This method will read in the file containing the information of interest and then begin parsing that 
	 * information into Director objects. It will achieve this by reading the data one line at a time.
	 * It will then commence reading the line backwards in order to simplify the process.
	 * 
	 * @param
	 * fileName - the name of the file to be read.
	 * 
	 * @return 
	 * An ArrayList<Actor> objects
	 */
	public LinkedHashMap<String, MediaMaker> importDirectorDataBase(String fileName)throws IOException
	{
		return importNonActorDataBase(fileName, Credit.MakerType.DIRECTOR);
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
	public ArrayList<MediaMaker> mediaMakerSearcher(String questions[])
	{
		
		ArrayList<MediaMaker> positiveList = new ArrayList<MediaMaker>();
		
		
		// For each loop will search the movie list and then store the matching values into a new ArrayList.

		if(questions[2].equalsIgnoreCase("p"))
		{
			for(String makerName : mediaMakerMap.keySet())
			{
				if (makerName.toLowerCase().contains(questions[18].toLowerCase()))
				{
					MediaMaker maker = this.get(makerName);
					positiveList.add(maker);
				}
			}
		}
		else
		{
			for(String makerName : mediaMakerMap.keySet())
			{
				if (makerName.toLowerCase().equals(questions[18].toLowerCase()))
				{
					MediaMaker maker = this.get(makerName);
					positiveList.add(maker);
				}
			}
		}

		return positiveList;
	}
	 
	/**
	 * This Method is used to search through the Map of Makers and find the one that matches the parameter
	 * 
	 * @param title : the name of the Maker being looked for
	 * @return
	 */
	public MediaMaker specificTitleSearch(String title) 
	{ 
 			 //containsTitle = specificSearch(movieTitle, containsTitle, fileName, movieTitleSearch); 
 			 ArrayList<String> keySet = new ArrayList<>(mediaMakerMap.keySet());
 			 Collections.sort(keySet);
 			
 			 int index = Collections.binarySearch(keySet,title);
 			 String key = keySet.get(index);
 			 MediaMaker maker = this.get(key);
 		
 			 return maker;
 	} 
	
	/**
	 * @return the mediaMakerMap
	 */
	public LinkedHashMap<String, MediaMaker> getMediaMakerMap() {
		return mediaMakerMap;
	}

	/**
	 * @param mediaMakerMap the mediaMakerMap to set
	 */
	public void setMediaMakerMap(LinkedHashMap<String, MediaMaker> mediaMakerMap) {
		this.mediaMakerMap = mediaMakerMap;
	}
	
	/**
	 * returns the mediamaker object given a name
	 * @param name
	 * @return 
	 */
	public MediaMaker get(String name)
	{
		return mediaMakerMap.get(name);
	}

	/**
	 * Load a file and add it to the database
	 * @param fileName
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void loadFile(String fileName) throws ClassNotFoundException, IOException
	{
		FileInputStream fileInputStream = new FileInputStream(fileName);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		Object object;
		// use instance of to determine the object type to make and add it to its appropriate list
		try{
		while(true){
			object = objectInputStream.readObject();
			if(object instanceof Actor){
				Actor actor = (Actor) object;
				mediaMakerMap.put(actor.toString(), actor);
			}
			if (object instanceof Director){
				Director director = (Director) object;
				mediaMakerMap.put(director.toString(), director);
			}
			if (object instanceof Producer){
				Producer producer = (Producer) object;
				mediaMakerMap.put(producer.toString(), producer);
			}
		}
	}catch(EOFException e){
		
	}
		objectInputStream.close();
		return;
	}
	
	/**
	 * Method for saving a file using an IO file.
	 * 
	 * @param fileName : the name of the file that it will be saved to/as
	 * @throws IOException
	 */
	public void saveFile(String fileName) throws IOException{
		FileOutputStream fileOutputStream = new FileOutputStream(fileName);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		ArrayList<Actor> outputActors = new ArrayList<Actor>();
		ArrayList<Director> outputDirectors  = new ArrayList<Director>();
		ArrayList<Producer> outputProducers = new ArrayList<Producer>();
		
		//for ever mediaMaker in mediaMakerMap..
		for(MediaMaker maker : mediaMakerMap.values()){
			// if it is an actor add it to the outputActors
			if(maker instanceof Actor){
				outputActors.add((Actor) maker);
			}
			// if it is a director add it to the outputDirectors
			if(maker instanceof Director){
				outputDirectors.add((Director) maker);
			}
			// if it is a producer add it to the outputProducers
			if(maker instanceof Producer){
				outputProducers.add((Producer) maker);
			}
		}
		// write all of the objects in the outputActors to the file.
		for(Actor actor : outputActors){
			objectOutputStream.writeObject(actor);
		}
		// write all of the objects in the outputDirectors to the file.
		for(Director director : outputDirectors){
			objectOutputStream.writeObject(director);
		}
		// write all of the objects in the outputProducers to the file.
		for(Producer producer : outputProducers){
			objectOutputStream.writeObject(producer);
		}
		objectOutputStream.close();

	
	}
}
