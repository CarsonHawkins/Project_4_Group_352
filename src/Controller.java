import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

public class Controller {
	
	private MediaModel model; 
	private SelectionView selectionView;
	
	
	/** Creates new Controller */
	Controller() 
	{
		
	}
	
	/** Sets of the model
	 * 
	 * @param model			The model that will be set. 
	 */
	public void setModel(MediaModel model) { 
		this.model = model;
	}
	
	/**Sets up the view
	 * 
	 * @param masterView	The view that will be for the entire view. 
	 */
	public void setView(SelectionView masterView) {
		// Set the master view.
		this.selectionView = masterView;

		// Register listeners 
		selectionView.addSaveListener(new FileSaveListener());
		selectionView.addLoadListener(new FileLoadListener());
		selectionView.addExportListener(new FileExportListener());
		selectionView.addImportListener(new FileImportListener());
		selectionView.addAddListener(new EditAddListener());
		selectionView.addEditListener(new EditEditListener());
		selectionView.addDeleteListener(new EditDeleteListener());
		selectionView.addClearListener(new EditClearListener());
		selectionView.addClearAllListener(new EditClearAllListener());
		selectionView.addPieChartListener(new DisplayPieChartListener());
		selectionView.addHistogramListener(new DisplayHistogramListener());
		selectionView.addRadiobuttonChangedListener(new RadiobuttonChangedListener());
	}
	
	/**Displays a dialog that will save the file where all of the information is stored. **/
	public class FileSaveListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			new FileSelector();
			//Receives and prompts the user for the files. 
			String fileSelected = FileSelector.showSaveDialog();
			try {
				//Gets the files
				model.save(fileSelected);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}	
	}
	
	/**Displays a dialog that will ask where to load the file all of the information it has stored. **/
	public class FileLoadListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			new FileSelector();
			//Gets the name of the file. 
			String[] filesSelected = FileSelector.showOpenDialog();
			
			for (String fileName : filesSelected)
			{
				try
				{
					new FileTypeSelector();
					
					//Gets the file type to sort correctly.
					String fileType = FileTypeSelector.showDialog(fileName);
					
					//Determines the type to load to the appropriate location. 
					if (fileType.equals("Movies"))
						model.loadFileMovie(fileName);
					if (fileType.equals("TV Series"))
						model.loadFileSeries(fileName);
					if (fileType.equals("Actors"))
						model.loadFileActor(fileName);
					if (fileType.equals("Directors"))
						model.loadFileDirector(fileName);
					if (fileType.equals("Producers"))
						model.loadFileProducer(fileName);
				}
				catch (IOException | ClassNotFoundException ex)
				{
					ex.printStackTrace();
				}
			}
		}	
	}
	
	/**Tells the model to exports the file where all of the information should be stored. **/
	public class FileExportListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			new FileSelector();
			//Ask the name of the file that it should be named. 
			String filename = FileSelector.showSaveDialog();
			try
			{
				//Tells the model to export under the filename given. 
				model.export(filename);
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}	
	}
	
	/**Tells the model to imports the file where all of the information is stored. **/
	public class FileImportListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			new FileSelector();
			//Gets the filename name that the files will be imported. 
			String[] filesSelected = FileSelector.showOpenDialog();
			
			//Runs through a loop to get all the files that were imported. 
			for (String fileName : filesSelected)
			{
				try
				{
					new FileTypeSelector();
					String fileType = FileTypeSelector.showDialog(fileName);
					//Determines the type to import to the appropriate location. 
					if (fileType.equals("Movies"))
						model.importFileMovie(fileName);
					if (fileType.equals("TV Series"))
						model.importFileSeries(fileName);
					if (fileType.equals("Actors"))
						model.importFileActor(fileName);
					if (fileType.equals("Directors"))
						model.importFileDirector(fileName);
					if (fileType.equals("Producers"))
						model.importFileProducer(fileName);
					
				}
				catch (IOException ex)
				{
					ex.printStackTrace();
				}
			}
		}	
	}

	/** The method is designed to listen to the view and correspond with the model when it is trying to Add a item. **/
	public class EditAddListener implements ActionListener {

		//Initializes the entry view
		DataEntryView entryView = null;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			//Reads in the states that were changed
			RadioButtonStates states = selectionView.getButtonStates();
			//Determines what type was selected to make the changes. 
			if (states.isActorsSelected())
				entryView = new MakerEntryView(Actor.class);
			if (states.isDirectorsSelected())
				entryView = new MakerEntryView(Director.class);
			if (states.isProducersSelected())
				entryView = new MakerEntryView(Producer.class);
			if(states.isMoviesSelected())
				entryView = new MovieEntryView();
			if(states.isSeriesSelected())
				entryView = new SeriesEntryView();
			if(states.isEpisodesSelected())
				entryView = new EpisodeEntryView(model);
			
			//Adds the recent changes to the model. 
			model.addActionListenener(entryView);
			entryView.addDoneListener(new EntryDoneListener());	
			
		}
		
		/**The method is to listen for the changes made in the view when they are trying to add a Actor, Producer, Director, Movie or Series **/
		class EntryDoneListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ListItem item = entryView.instantiate();
					
					//If it is a MediaMaker it will determine which it is to add the appropriate fields. 
					if (item instanceof MediaMaker)
					{
						//populates the desired variable to be able to store the information into the MediaMaker variable. 
						MakerEntryView castedEntryView = (MakerEntryView) entryView;
						MediaMaker maker = castedEntryView.instantiate();
						//Determines the type and populate the new information 
						if (item instanceof Actor)
							model.addActor(maker.getMdbMediaFirstName(), maker.getMdbMediaLastName(),  maker.getMdbMediaDisambiguationNumber(), maker.getMovieCredits(), maker.getSeriesCredits());
						if (item instanceof Director)
							model.addDirector(maker.getMdbMediaFirstName(), maker.getMdbMediaLastName(),  maker.getMdbMediaDisambiguationNumber(), maker.getMovieCredits(), maker.getSeriesCredits());
						if (maker instanceof Producer) 
							model.addProducer(maker.getMdbMediaFirstName(), maker.getMdbMediaLastName(),  maker.getMdbMediaDisambiguationNumber(), maker.getMovieCredits(), maker.getSeriesCredits());
					}
					
					//If it is a Media it will determine which it is to add the appropriate fields. 
					if (item instanceof Media)
					{
						//populates the desired variable to be able to store the information into the Media variable. 
						MediaEntryView castedEntryView = (MediaEntryView) entryView;
						Media media = castedEntryView.instantiate();
						//Determines the type and populates the new information.
						if (media instanceof Movie)
							model.addMovie(((Movie) media).getTitle(), "(" + ((Movie) media).getYear() + ")", ((Movie) media).getYear(), ((Movie) media).getReleaseForm());
						if(media instanceof Series) 
							model.addSeries(((Series) media).getSeriesName(), ((Series) media).getSeriesStartYear(), ((Series) media).getSeriesEndYear());
						if(media instanceof TVEpisode)
							model.addEpisode(((TVEpisode) media).getEpisodeName(), ((TVEpisode) media).getEpisodeStartYear(), ((TVEpisode) media).getEpisodeInfo(), ((TVEpisode) media).getEpisodeYear(), ((TVEpisode) media).getSeries());
					}
					
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} finally {
					//After all is completed it will dispose of everything. 
					entryView.dispose();
				}
			}
			
		}
	}
	
	/** Listen to the Edits made in the view sends the new acquired information to the model. **/
	public class EditEditListener implements ActionListener {

		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			
			//Gets the changes made. 
			for (ListItem itemToEdit : selectionView.getSelectedItems())
			{
				DataEntryView entryView = null;
			
				if (itemToEdit instanceof MediaMaker)
					entryView = new MakerEntryView((MediaMaker)itemToEdit);
				else
				{
					if (itemToEdit instanceof Movie)
						entryView = new MovieEntryView((Movie) itemToEdit);
					if (itemToEdit instanceof Series)
						entryView = new SeriesEntryView((Series) itemToEdit);
					if (itemToEdit instanceof TVEpisode)
						entryView = new EpisodeEntryView(model, (TVEpisode)itemToEdit);
				}
				//Adds the listener.
				entryView.addDoneListener(new EntryDoneListener(entryView, itemToEdit));
				model.addActionListenener(entryView);
			}
			
		}
		
		/** Determines the entries that were made in the view to transfer the information onto the model to update the information **/
		class EntryDoneListener implements ActionListener
		{

			ListItem itemToEdit;
			DataEntryView entryView;
			
			public EntryDoneListener(DataEntryView e, ListItem i)
			{
				itemToEdit = i;
				entryView = e;
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Remove object from hash-map
				model.mediaMakerDataBase.getMediaMakerMap().remove(itemToEdit.toString());
				
				if (itemToEdit instanceof MediaMaker)
				{
					MediaMaker maker = (MediaMaker) itemToEdit;
					MakerEntryView mev = (MakerEntryView) entryView;
					//Change the object
					maker.setMdbMediaFirstName(mev.getFirstName());
					maker.setMdbMediaLastName(mev.getLastName());
					maker.setMdbMediaDisambiguationNumber(mev.getDisambiguationNumber());	

				}
				else
				{
					if (itemToEdit instanceof Movie)
					{
						Movie movie = (Movie) itemToEdit;
						MovieEntryView mev = (MovieEntryView) entryView;
						
						movie.setMovieTitle(mev.getMovieTitle());
						movie.setYear(mev.getMovieYear());
						movie.setReleaseForm(mev.getReleaseForm());
					}
					if (itemToEdit instanceof Series)
					{
						Series series = (Series) itemToEdit;
						SeriesEntryView sev = (SeriesEntryView) entryView;
						
						series.setSeriesName(sev.getSeriesTitle());
						series.setSeriesStartYear(sev.getStartDate());
						series.setSeriesEndYear(sev.getEndDate());
					}
					if (itemToEdit instanceof TVEpisode)
					{
						TVEpisode ep = (TVEpisode) itemToEdit;
						EpisodeEntryView sev = (EpisodeEntryView) entryView;
						
						ep.setEpisodeInfo(sev.getEpisodeTitle());
						ep.setEpisodeYear(sev.getEpisodeYear());
						ep.setEpisodeName(sev.getSeries().getSeriesName());
						ep.setEpisodeStartYear(sev.getSeries().getSeriesStartYear());
						ep.setSeries(sev.getSeries());
					}
					
				}
				
				// Add it back under new key
				entryView.dispose();
			}
			
		}
	}
	
	/**Listens to the view to determine what particular objects the user would like to delete. **/ 
	public class EditDeleteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			//Deletes the section the user wants to remove. 
			for (ListItem item : selectionView.getSelectedItems())
			{
				model.delete(item);
			}
			
		}
	}
	
	/**Listens to the view to determine what objects the user would like to delete. **/ 
	public class EditClearListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			//Clears a particular object. 
			model.clear();
			
		}
	}
	
	/**Updates the model to clear all of the information in the database. **/ 
	public class EditClearAllListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			//Clears the database of all the information. 
			model.clearAll();
			
		}
	}
	
	/** Updates the model to display the pie chart of the media maker chosen. **/
	public class DisplayPieChartListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			//Displays the pie chart to the user. 
			for(ListItem item : selectionView.getSelectedItems())
				selectionView.showPieChart((MediaMaker) item);
			
		}
	}
	/** Updates the model to display the histogram of the media maker chosen. **/
	public class DisplayHistogramListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			//Displays the Histogram to the user. 
			for(ListItem item : selectionView.getSelectedItems())
				selectionView.showHistogram((MediaMaker) item);			
		}
	}
	
	/** Listens to determine what radio buttons were changed in the view to update the information in the model. **/
	public class RadiobuttonChangedListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			//Receives the information that was changed. 
			RadioButtonStates states = selectionView.getButtonStates();
			model.setButtonStates(states);
			model.createDisplayItemList();
			model.processEvent(EventMessages.DATA_CHANGED);	
			
			//Determines what was changed. 
			if (states.isMediaSelected())
				selectionView.setDataLabel("Medias");
			else if (states.isMoviesSelected())
				selectionView.setDataLabel("Movies");
			else if (states.isEpisodesSelected())
				selectionView.setDataLabel("Episodes");
			else if (states.isSeriesSelected())
				selectionView.setDataLabel("Series");
			else if (states.isMakersSelected())
				selectionView.setDataLabel("Makers");
			else if (states.isActorsSelected())
				selectionView.setDataLabel("Actors");
			else if (states.isDirectorsSelected())
				selectionView.setDataLabel("Directors");
			else if (states.isProducersSelected())
				selectionView.setDataLabel("Producers");
		}
	}
}
