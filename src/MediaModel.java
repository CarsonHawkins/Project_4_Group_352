import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MediaModel {
	public ArrayList<ActionListener> list;
	public TVDataBase seriesDataBase = new TVDataBase();
	public MovieDataBase movieDataBase = new MovieDataBase();
	public MediaMakerDataBase mediaMakerDataBase = new MediaMakerDataBase();
	public ArrayList<ListItems> listItems;
	
	private FileWriter fw;
	private BufferedWriter bw;
	
	
	public Movie movie = new Movie();
	public Series series =  new Series();
	public Actor actor = new Actor();
	public Director director = new Director();
	public Producer producer = new Producer();
	
	public void addMovie(String title, String date, String releaseForm, String release){
		this.movie = new Movie(title, date, releaseForm, release);
		movieDataBase.getMovieList().addMovie(movie);
		processEvent(EventMessages.MOVIE_ADDED);
	}
	
	public void addSeries(String name, String startYear, String episodeYear){
		this.series = new Series(name, startYear, episodeYear);
		seriesDataBase.getSeriesList().add(series);
		processEvent(EventMessages.SERIES_ADDED);
	}
	
	public void addActor(String lastName, String firstName, String num, ArrayList<Credit> movieCredits, ArrayList<Credit> seriesCredits){
		this.actor = new Actor(lastName, firstName, num, movieCredits, seriesCredits);
		mediaMakerDataBase.getMediaMakerMap().put(actor.toString(), actor);
		processEvent(EventMessages.ACTOR_ADDED);
	}
	
	public void addDirector(String lastName, String firstName, String num, ArrayList<Credit> movieCredits, ArrayList<Credit> seriesCredits){
		this.director = new Director(lastName, firstName, num, movieCredits, seriesCredits);
		mediaMakerDataBase.getMediaMakerMap().put(director.toString(), director);
		processEvent(EventMessages.DIRECTOR_ADDED);
	}
	
	public void addProducer(String lastName, String firstName, String num, ArrayList<Credit> movieCredits, ArrayList<Credit> seriesCredits){
		this.producer = new Producer(lastName, firstName, num, movieCredits, seriesCredits);
		mediaMakerDataBase.getMediaMakerMap().put(producer.toString(),producer);
		processEvent(EventMessages.PRODUCER_ADDED);
	}
	
	
	public void export(String fileName)
	{
		fw = new FileWriter(fileName);
		bw = new BufferedWriter(fw);
		
		for (ListItem i : listItems)
		{
			bw.write(i.toString());
		}
		processEvent(EventMessages.FILE_EXPORTED);
	}
	
	public  void save(String fileName){
		//TODO: finish this method for writing using I/O
		processEvent(EventMessages.FILE_SAVED);
	}
	
	public void addActionListenener(ActionListener listener){
		if (list == null) {
			list = new ArrayList<ActionListener>();
		}		
		list.add(listener);
	}
	
	public void removeActionListener(ActionListener listener) {
		if (list != null && list.contains(listener)) {
			list.remove(listener);
		}
	}
	
	public void processEvent(String message){
		for (ActionListener action : list){
			action.actionPerformed(new ActionEvent( this, 0, message));
		}
	}
	
	public void importFileMovie(String fileName){
		movieDataBase.importMovieDataBase(fileName);
		processEvent(EventMessages.MOVIE_IMPORT_FILE);
	}
	
	public void loadFileMovie(String fileName){
		movie.loadFile(fileName);
		processEvent(EventMessages.MOVIE_LOAD_FILE);
	}
	
	public void importFileSeries(String fileName) throws IOException{
		seriesDataBase.importTvDataBase(fileName);
		processEvent(EventMessages.SERIES_IMPORT_FILE);
	}
	
	public void loadFileSeries(String fileName){
		series.loadFile(fileName);
		processEvent(EventMessages.SERIES_LOAD_FILE);
	}
	
	public void importFileActor(String fileName) throws IOException{
		mediaMakerDataBase.importActorDataBase(fileName);
		processEvent(EventMessages.ACTOR_IMPORT_FILE);
	}
	
	public void loadFileActor(String fileName){
		actor.loadFile(fileName);
		processEvent(EventMessages.ACTOR_LOAD_FILE);
	}
	
	public void importFileDirecotr(String fileName) throws IOException{
		mediaMakerDataBase.importDirectorDataBase(fileName);
		processEvent(EventMessages.DIRECTOR_IMPORT_FILE);
	}
	
	public void loadFileDirector(String fileName){
		director.loadFile(fileName);
		processEvent(EventMessages.DIRECTOR_LOAD_FILE);
	}
	
	public void importFileProducer(String fileName) throws IOException{
		mediaMakerDataBase.importProducerDataBase(fileName);
		processEvent(EventMessages.PRODUCER_IMPORT_FILE);
	}
	
	public void loadFileProducer(String fileName){
		producer.loadFile(fileName);
		processEvent(EventMessages.PRODUCER_LOAD_FILE);
	}
	
}
