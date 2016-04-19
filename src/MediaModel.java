import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MediaModel {
	public ArrayList<ActionListener> listenerList = new ArrayList<>();
	public TVDataBase seriesDataBase = new TVDataBase();
	public MovieDataBase movieDataBase = new MovieDataBase();
	public MediaMakerDataBase mediaMakerDataBase = new MediaMakerDataBase();
	public ArrayList<ListItem> displayList = new ArrayList<>();
	
	private FileWriter fw;
	private BufferedWriter bw;
	
	/** the state of the view's buttons, as the model remembers them */
	private RadioButtonStates states;
	
	public void addMovie(String title, String date, String releaseForm, String release){
		Movie movie = new Movie(title, date, releaseForm, release);
		movieDataBase.getMovieList().addMovie(movie);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	public void addSeries(String name, String startYear, String endYear){
		Series series = new Series(name, startYear, endYear);
		seriesDataBase.getSeriesList().add(series);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	public void addEpisode(String name, String startYear, String episodeInfo, String episodeYear, Series series){
		
		for(TVEpisode episode : series.getEpisodeList()){
			if (episode.getEpisodeInfo().equals(episodeInfo)){
				JFrame frame = new JFrame("Error");
				JOptionPane.showMessageDialog(frame, "That season number and episode number already exist for this series.");
				return;
			}
			
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
					processEvent(EventMessages.DATA_CHANGED);
				}
		
			}
		}		
	}
	
	public void addActor(String lastName, String firstName, String num, ArrayList<Credit> movieCredits, ArrayList<Credit> seriesCredits){
		Actor actor = new Actor(lastName, firstName, num, movieCredits, seriesCredits);
		mediaMakerDataBase.getMediaMakerMap().put(actor.toString(), actor);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	public void addDirector(String lastName, String firstName, String num, ArrayList<Credit> movieCredits, ArrayList<Credit> seriesCredits){
		Director director = new Director(lastName, firstName, num, movieCredits, seriesCredits);
		mediaMakerDataBase.getMediaMakerMap().put(director.toString(), director);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	public void addProducer(String lastName, String firstName, String num, ArrayList<Credit> movieCredits, ArrayList<Credit> seriesCredits){
		Producer producer = new Producer(lastName, firstName, num, movieCredits, seriesCredits);
		mediaMakerDataBase.getMediaMakerMap().put(producer.toString(),producer);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	/**
	 * Creates the list of objects to be displayed in the SelectionView
	 * @param states
	 */
	public void createDisplayItemList()
	{
		this.displayList.clear();
		
		if(states.isMediaSelected()){
			displayList.addAll(movieDataBase.getMovieList());
			displayList.addAll(seriesDataBase.getSeriesList());
			displayList.addAll(seriesDataBase.getEpisodeList());
		}
		
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
	}
	
	public void export(String fileName) throws IOException
	{
		fw = new FileWriter(fileName);
		bw = new BufferedWriter(fw);
		
		for (ListItem i : displayList)
		{
			bw.write(i.toString() + "\n");
		}
		processEvent(EventMessages.FILE_EXPORTED);
	}
	
	public  void save(String fileName){
		//TODO: finish this method for writing using I/O
		processEvent(EventMessages.FILE_SAVED);
	}
	
	public void addActionListenener(ActionListener listener){
		if (listenerList == null) {
			listenerList = new ArrayList<ActionListener>();
		}		
		listenerList.add(listener);
	}
	
	public void removeActionListener(ActionListener listener) {
		if (listenerList != null && listenerList.contains(listener)) {
			listenerList.remove(listener);
		}
	}
	
	public void processEvent(String message){
		
		/* Update the model's display list */
		if (message.equals(EventMessages.DATA_CHANGED))
			createDisplayItemList();
		
		if(listenerList.isEmpty()){
			return;
		} 
		else {
			for (ActionListener action : listenerList) {
				action.actionPerformed(new ActionEvent(this, 0, message));
			}
		}
	}
	
	public void setButtonStates(RadioButtonStates states)
	{
		this.states = states;
	}
	
	public void importFileMovie(String fileName) throws IOException{
		movieDataBase.importMovieDataBase(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	public void loadFileMovie(String fileName) throws IOException{
		movieDataBase.loadFile(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	public void importFileSeries(String fileName) throws IOException{
		seriesDataBase.importTvDataBase(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	public void loadFileSeries(String fileName) throws IOException{
		seriesDataBase.loadFile(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	public void importFileActor(String fileName) throws IOException{
		mediaMakerDataBase.importActorDataBase(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	public void loadFileActor(String fileName) throws IOException{
		mediaMakerDataBase.loadFile(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	 
	public void importFileDirector(String fileName) throws IOException{
		mediaMakerDataBase.importDirectorDataBase(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	public void loadFileDirector(String fileName) throws IOException{
		mediaMakerDataBase.loadFile(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	public void importFileProducer(String fileName) throws IOException{
		mediaMakerDataBase.importProducerDataBase(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	public void loadFileProducer(String fileName) throws IOException{
		mediaMakerDataBase.loadFile(fileName);
		processEvent(EventMessages.DATA_CHANGED);
	}
	
	public void delete(ListItem item){
	
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
	
	public void clear(){
		for (ListItem item : displayList){
			displayList.clear();
			processEvent(EventMessages.DATA_CHANGED);
		}
	}
	
	public void clearAll(){
		movieDataBase.getMovieList().clear();
		mediaMakerDataBase.getMediaMakerMap().clear();
		seriesDataBase.getEpisodeList().clear();
		seriesDataBase.getSeriesList().clear();
		processEvent(EventMessages.DATA_CHANGED);
	}
	
}
