import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Project #4
 * CS 2334, Section 013
 * 04-21-16
 * @author Carson Hawkins, Daniel Schon, and Eric Morales
 * <P>
 * This is the Model class of Project4. 
 * </P>
 * @version 1.0
 */
public class MediaModel {
	/** An ArrayList of ActionListeners that have been registered to this object  */
	public ArrayList<ActionListener> listenerList = new ArrayList<>();
	/** AnArrayList of Listitems that will hold all of the Items which should be displayed in the view */
	public ArrayList<ListItem> displayList = new ArrayList<>();
	/** A TVDataBase that will be used to hold the lists of Episodes and Series, as well as some methods for using those lists */
	public TVDataBase seriesDataBase = new TVDataBase();
	/** A MovieDataBase that will be used to hold the list of Movies, as well as some methods for using that list */
	public MovieDataBase movieDataBase = new MovieDataBase();
	/** A MediaMakerDataBase that will be used to hold the lists of actors, directors, and producers, as well as some methods for using those lists */
	public MediaMakerDataBase mediaMakerDataBase = new MediaMakerDataBase();
	/** The FileWriter that is going to be used to save/export to a file */
	private FileWriter fw;
	/** The BufferedWriter which will use fw and write to the file */
	private BufferedWriter bw;
	/** the state of the view's buttons, as the model remembers them */
	private RadioButtonStates states;
	
	/**
	 * The method for taking in data from the view and making a new Movie for its lists.
	 * 
	 * @param title : title of the movie
	 * @param date : the date it was released
	 * @param releaseForm : TV, DVD, or to Theater.
	 * @param release : the year it was released
	 */
	public void addMovie(String title, String date, String releaseForm, String release){
		Movie movie = new Movie(title, date, releaseForm, release);
		movieDataBase.getMovieList().add(movie);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	/**
	 * The method for taking in data from the view and making a new Series for its lists.
	 * 
	 * @param name : the name of the series
	 * @param startYear : the year it started
	 * @param endYear : the year it ended or if it is still going
	 */
	public void addSeries(String name, String startYear, String endYear){
		Series series = new Series(name, startYear, endYear);
		seriesDataBase.getSeriesList().add(series);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	/**
	 * The method for taking in data from the view and making a new Episode for its lists.
	 * 
	 * @param name : the name of the episode
	 * @param startYear : the start year of the series
	 * @param episodeInfo :  the season number and episode number
	 * @param episodeYear : the year that the episode came out
	 * @param series : the series that it is a part of
	 */
	public void addEpisode(String name, String startYear, String episodeInfo, String episodeYear, Series series){
		//set the add a new TVEpisode to the episode list
		this.seriesDataBase.getEpisodeList().add(new TVEpisode(name, startYear, episodeInfo, episodeYear));
		// if the series is not empty, and the series.getEpisodeList isnt null..
		if (series != null && series.getEpisodeList() != null)
		{
			for(TVEpisode episode : series.getEpisodeList()){
				//check to see if each episode has a unique episodeInfo
				if (episode.getEpisodeInfo().equals(episodeInfo)){
					JFrame frame = new JFrame("Error");
					JOptionPane.showMessageDialog(frame, "That season number and episode number already exist for this series.");
					return;
				}
				//check to see if the episode year is outside of the yearRange for the series
				else{
					if(Integer.parseInt(episodeYear) < Integer.parseInt(series.getSeriesStartYear()) 
							|| Integer.parseInt(episodeYear) > Integer.parseInt(series.getSeriesEndYear())){
						JFrame frame = new JFrame("Error");
						JOptionPane.showMessageDialog(frame, "That episode year is not in the range of the series.");
					}
					else{
						TVEpisode tvEpisode = new TVEpisode(name, startYear, episodeInfo, episodeYear);
						series.getEpisodeList().add(tvEpisode);
						seriesDataBase.getEpisodeList().add(tvEpisode);
					}
			
				}
			}	
		}
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	/**
	 * The method for making a new actor object and adding it to the lists it belongs to 
	 * 
	 * @param lastName : the last name of the actor
	 * @param firstName : the first name of the actor
	 * @param num : the numeral denoting if the actor has the same name as another
	 * @param movieCredits : the credits for all the movies the actor was in
	 * @param seriesCredits : the credits for all of the TVSeries the actor was in
	 */
	public void addActor(String lastName, String firstName, String num, ArrayList<Credit> movieCredits, ArrayList<Credit> seriesCredits){
		Actor actor = new Actor(lastName, firstName, num, movieCredits, seriesCredits);
		mediaMakerDataBase.getMediaMakerMap().put(actor.toString(), actor);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	/**
	 * The method for making a new director object and adding it to the lists it belongs to 
	 * 
	 * @param lastName : the last name of the director
	 * @param firstName : the first name of the director
	 * @param num : the numeral denoting if the director has the same name as another
	 * @param movieCredits : the credits for all the movies the director made
	 * @param seriesCredits : the credits for all of the TVSeries the director made
	 */
	public void addDirector(String lastName, String firstName, String num, ArrayList<Credit> movieCredits, ArrayList<Credit> seriesCredits){
		Director director = new Director(lastName, firstName, num, movieCredits, seriesCredits);
		mediaMakerDataBase.getMediaMakerMap().put(director.toString(), director);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	/**
	 * The method for making a new producer object and adding it to the lists it belongs to 
	 * 
	 * @param lastName : the last name of the producer
	 * @param firstName : the first name of the producer
	 * @param num : the numeral denoting if the producer has the same name as another
	 * @param movieCredits : the credits for all the movies the producer made
	 * @param seriesCredits : the credits for all of the TVSeries the produicer made
	 */
	public void addProducer(String lastName, String firstName, String num, ArrayList<Credit> movieCredits, ArrayList<Credit> seriesCredits){
		Producer producer = new Producer(lastName, firstName, num, movieCredits, seriesCredits);
		mediaMakerDataBase.getMediaMakerMap().put(producer.toString(),producer);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	/**
	 * Creates the list of objects to be displayed in the SelectionView.
	 */
	public void createDisplayItemList()
	{
		this.displayList.clear();
	
		// if media is selected add them all
		if(states.isMediaSelected()){
			displayList.addAll(movieDataBase.getMovieList());
			displayList.addAll(seriesDataBase.getSeriesList());
			displayList.addAll(seriesDataBase.getEpisodeList());
		}
		
		//otherwise check the selected button and add it to the list of that type
		else
		{
			if(states.isMoviesSelected())
			{
				displayList.addAll(movieDataBase.getMovieList());
			}
			if(states.isSeriesSelected()){
				displayList.addAll(seriesDataBase.getSeriesList());
			}
			if(states.isEpisodesSelected()){
				displayList.addAll(seriesDataBase.getEpisodeList());
			}
		}
		
		if(states.isMakersSelected()){
			displayList.addAll(mediaMakerDataBase.getMediaMakerMap().values());
		}
		
		else
		{
			if (states.isActorsSelected()){
				for (MediaMaker maker: mediaMakerDataBase.getMediaMakerMap().values())
				{
					if (maker instanceof Actor)
						displayList.add(maker);
				}
			}
			
			if(states.isDirectorsSelected()){
				for (MediaMaker maker: mediaMakerDataBase.getMediaMakerMap().values())
				{
					if (maker instanceof Director)
						displayList.add(maker);
				}	
			}
			
			if(states.isProducersSelected()){
				for (MediaMaker maker: mediaMakerDataBase.getMediaMakerMap().values())
				{
					if (maker instanceof Producer)
						displayList.add(maker);
				}
			}
		}
		
		//sort the displayList
		Collections.sort(displayList);
	}
	
	/**
	 * method for exporting the file using text.
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void export(String fileName) throws IOException
	{
		fw = new FileWriter(fileName);
		bw = new BufferedWriter(fw);
		
		String type = FileTypeSelector.showDialog(fileName);
		
		// if the type is Movies..
		if (type.equals("Movies"))
		{
			// write all of the movies to the file
			for (ListItem i : movieDataBase.getMovieList())
			{
				//FIXME: use the methods from Daniel to get the format of the items
				bw.write(i.getFileText());
			}
		}
		
		//if the type is TVSeries
		if (type.equals("TV Series")){
			// write all of the series to the file
			for (ListItem i : seriesDataBase.getSeriesList())
			{
				//FIXME: use the methods from Daniel to get the format of the items
				bw.write(i.getFileText());
			}
		}
		
		//if the type is TVEpisode
		if (type.equals("TV Episode")){
			// write all of the episode to the file
			for (ListItem i : seriesDataBase.getEpisodeList())
			{
				//FIXME: use the methods from Daniel to get the format of the items
				bw.write(i.getFileText());
			}
		}
		
		//if the type is actor...
		if (type.equals("Actor")){
			// write all of the actor to the file
			for (ListItem i : mediaMakerDataBase.getMediaMakerMap().values())
			{
				if(i instanceof Actor){
					//FIXME: use the methods from Daniel to get the format of the items
					bw.write(i.getFileText());
				}
			}
		}
		
		//if the type is director...
		if (type.equals("Director")){
			// write all of the actor to the file
			for (ListItem i : mediaMakerDataBase.getMediaMakerMap().values()){
				if(i instanceof Director){
					//FIXME: use the methods from Daniel to get the format of the items
					bw.write(i.getFileText());
				}
			}
		}

		//if the type is producer...
		if (type.equals("Producer")){
			// write all of the producer to the file
			for (ListItem i : mediaMakerDataBase.getMediaMakerMap().values())
			{
				if(i instanceof Producer){
					//FIXME: use the methods from Daniel to get the format of the items
					bw.write(i.getFileText());
				}
			}
		}
		
		bw.close();
		processEvent(EventMessages.FILE_EXPORTED);
	}
	
	/**
	 * Method for saving the file using I/O and Serializable.
	 * 
	 * @throws IOException
	 */
	public void save(String location) throws IOException{
		FileOutputStream out = new FileOutputStream(location);
		ObjectOutputStream oos = new ObjectOutputStream(out);
		// for each item in the displayList, write the object using IO
		for (ListItem item : displayList){
			oos.writeObject(item);
		}
		oos.close();
		processEvent(EventMessages.FILE_SAVED);
	}
	
	/**
	 * Method for adding actionListeners to the actionListenerList
	 * 
	 * @param listener : the listener that is going to be added
	 */
	public void addActionListenener(ActionListener listener){
		// if the listenerList is null, make a new one
		if (listenerList == null) {
			listenerList = new ArrayList<ActionListener>();
		}		
		//add the actionListener to the listenerList
		listenerList.add(listener);
	}
	
	/**
	 * Method for removing the action listener from the actionListenerList
	 * 
	 * @param listener : the listener that is going to be removed
	 */
	public void removeActionListener(ActionListener listener) {
		// if the listenerList isnt empty, and contains tha listener, remove it from the list.
		if (listenerList != null && listenerList.contains(listener)) {
			listenerList.remove(listener);
		}
	}
	
	/**
	 * This method will be called every time any method is performed in order to update the view.
	 * 
	 * @param message : the message that will be displayed
	 */
	public void processEvent(String message){
		
		/* Update the model's display list */
		if (message.equals(EventMessages.DATA_CHANGED))
			createDisplayItemList();
		
		if(listenerList.isEmpty()){
			return;
		}
		// for ever action listener in listenerList, use actionperformed on the actionEvent witht the message
		else {
			for (ActionListener action : listenerList) {
				action.actionPerformed(new ActionEvent(this, 0, message));
			}
		}
	}
	
	/**
	 * Method for setting the states of the radioButtons 
	 * 
	 * @param states : the states of the radio buttons
	 */
	public void setButtonStates(RadioButtonStates states)
	{
		this.states = states;
	}
	
	/**
	 * Method for importing a Movie file using Text
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void importFileMovie(String fileName) throws IOException{
		movieDataBase.importMovieDataBase(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	/**
	 * Method for loading a movie file using object I/O
	 * 
	 * @param fileName
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public void loadFileMovie(String fileName) throws IOException, ClassNotFoundException{
		movieDataBase.loadFile(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	/**
	 * Method for importing a series file using Text
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void importFileSeries(String fileName) throws IOException{
		seriesDataBase.importTvDataBase(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	/**
	 * Method for loading a series file using object I/O
	 * 
	 * @param fileName
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public void loadFileSeries(String fileName) throws IOException, ClassNotFoundException{
		seriesDataBase.loadFile(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	/**
	 * Method for importing an actor file using Text
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void importFileActor(String fileName) throws IOException{
		mediaMakerDataBase.importActorDataBase(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	/**
	 * Method for loading an actor file using object I/O
	 * 
	 * @param fileName
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public void loadFileActor(String fileName) throws IOException, ClassNotFoundException{
		mediaMakerDataBase.loadFile(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	/**
	 * Method for importing a director file using Text
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void importFileDirector(String fileName) throws IOException{
		mediaMakerDataBase.importDirectorDataBase(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	/**
	 * Method for loading a director file using object I/O
	 * 
	 * @param fileName
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public void loadFileDirector(String fileName) throws IOException, ClassNotFoundException{
		mediaMakerDataBase.loadFile(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	/**
	 * Method for importing a producer file using Text
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void importFileProducer(String fileName) throws IOException{
		mediaMakerDataBase.importProducerDataBase(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	/**
	 * Method for loading a producer file using object I/O
	 * 
	 * @param fileName
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public void loadFileProducer(String fileName) throws IOException, ClassNotFoundException{
		mediaMakerDataBase.loadFile(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	/**
	 * Method that is used when the user clicks the delete function of the edit menu.
	 * It will delete the selected object from the displayList and its database list
	 * 
	 * @param item : the item that is going to be deleted
	 */
	public void delete(ListItem item){
	
		// use instanceOf to determine the type item is and remove it from the related list
		if (item instanceof Movie){
			movieDataBase.getMovieList().remove(item);
			processEvent(EventMessages.DATA_CHANGED);
		}
		if (item instanceof MediaMaker){
			mediaMakerDataBase.getMediaMakerMap().remove(item.toString());
			processEvent(EventMessages.DATA_CHANGED);
		}
		if (item instanceof TVEpisode){
			seriesDataBase.getEpisodeList().remove(item);
			processEvent(EventMessages.DATA_CHANGED);
		}
		if (item instanceof Series){
			seriesDataBase.getSeriesList().remove(item);
			processEvent(EventMessages.DATA_CHANGED);
		}
	}
	
	/**
	 *  method for when the user selects the clear function of the edit menu.
	 *  This will clear the display list.
	 */
	public void clear(){
		// clear the dataBaseLists related to the object
		for(ListItem item : displayList){
			delete(item);
		}
		// clear the display list
		displayList.clear();
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	/**
	 * Method for when the user selects Clear All in the edit menu.  
	 * This will clear all of the lists, essentially making it all start from scratch 
	 */
	public void clearAll(){
		// clear all of the dataBase lists and the displayList
		movieDataBase.getMovieList().clear();
		mediaMakerDataBase.getMediaMakerMap().clear();
		seriesDataBase.getEpisodeList().clear();
		seriesDataBase.getSeriesList().clear();
		processEvent(EventMessages.DATA_CHANGED);
	}
	
}
