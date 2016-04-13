import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class MediaModel {
	public ArrayList<ActionListener> list;
	public TVDataBase seriesDataBase = new TVDataBase();
	public MovieDataBase movieDataBase = new MovieDataBase();
	public MediaMakerDataBase mediaMakerDataBase = new MediaMakerDataBase();
	
	
	public Movie movie = new Movie();
	public Series series =  new Series();
	public Actor actor = new Actor();
	public Director director = new Director();
	public Producer producer = new Producer();
	
	
	
	public void addActionListenener(ActionListener listener){
		list.add(listener);
	}
	
	public void processEvent(){
		
		for (ActionListener action : list){
			//TODO:	action.actionPerformed(new ActionEvent( 0,"Data Change."));
			
		}
	}
	
	public void importFileMovie(String fileName){
		movieDataBase.importMovieDB(fileName);
		processEvent();
	}
	
	public void loadFileMovie(String fileName){
		movie.loadFile(fileName);
		processEvent();
	}
	
	public void importFileSeries(String fileName) throws IOException{
		seriesDataBase.importTvDataBase(fileName);
		processEvent();
	}
	
	public void loadFileSeries(String fileName){
		series.loadFile(fileName);
		processEvent();
	}
	
	public void importFileActor(String fileName) throws IOException{
		mediaMakerDataBase.importActorDataBase(fileName);
		processEvent();
	}
	
	public void loadFileActor(String fileName){
		actor.loadFile(fileName);
		processEvent();
	}
	
	public void importFileDirecotr(String fileName) throws IOException{
		mediaMakerDataBase.importDirectorDataBase(fileName);
		processEvent();
	}
	
	public void loadFileDirector(String fileName){
		director.loadFile(fileName);
		processEvent();
	}
	
	public void importFileProducer(String fileName) throws IOException{
		mediaMakerDataBase.importProducerDataBase(fileName);
		processEvent();
	}
	
	public void loadFileProducer(String fileName){
		producer.loadFile(fileName);
		processEvent();
	}
	
}
