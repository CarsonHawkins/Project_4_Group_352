import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Controller {
	
	private MediaModel model; 
	private SelectionView selectionView;
	
	/** Creates new Controller */
	Controller() 
	{
		
	}
	
	public void setModel(MediaModel model) { 
		this.model = model;
	}
	
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
	
	public class FileSaveListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String fileSelected = new FileSelector().showSaveDialog();
			model.save(fileSelected);
		}	
	}
	public class FileLoadListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] filesSelected = new FileSelector().showOpenDialog();
			
			for (String fileName : filesSelected)
			{
				try
				{
				String fileType = new FileTypeSelector().showDialog(fileName);
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
				catch (IOException ex)
				{
					ex.printStackTrace();
				}
			}
		}	
	}
	public class FileExportListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String filename = new FileSelector().showSaveDialog();
			try
			{
				model.export(filename);
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}	
	}
	public class FileImportListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] filesSelected = new FileSelector().showOpenDialog();
			
			for (String fileName : filesSelected)
			{
				try
				{
					String fileType = new FileTypeSelector().showDialog(fileName);
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

	public class EditAddListener implements ActionListener {

		MakerEntryView entryView = null;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			RadioButtonStates states = selectionView.getButtonStates();
			if (states.isMakersSelected())
				entryView = new MakerEntryView(MediaMaker.class);
			if (states.isActorsSelected())
				entryView = new MakerEntryView(Actor.class);
			if (states.isDirectorsSelected())
				entryView = new MakerEntryView(Director.class);
			if (states.isProducersSelected())
				entryView = new MakerEntryView(Producer.class);
			
			entryView.addDoneListener(new EntryDoneListener());	
			
		}
		
		class EntryDoneListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					MediaMaker maker = entryView.instantiate();
					if (maker instanceof Actor)
						model.addActor(maker.getMdbMediaLastName(), maker.getMdbMediaFirstName(), maker.getMdbMediaDisambiguationNumber(), maker.getMovieCredits(), maker.getSeriesCredits());
					if (maker instanceof Director)
						model.addDirector(maker.getMdbMediaLastName(), maker.getMdbMediaFirstName(), maker.getMdbMediaDisambiguationNumber(), maker.getMovieCredits(), maker.getSeriesCredits());
					if (maker instanceof Producer) 
						model.addProducer(maker.getMdbMediaLastName(), maker.getMdbMediaFirstName(), maker.getMdbMediaDisambiguationNumber(), maker.getMovieCredits(), maker.getSeriesCredits());
					
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} finally {
					entryView.dispose();
				}
			}
			
		}
	}
	
	public class EditEditListener implements ActionListener {

		MakerEntryView entryView = null;
		MediaMaker makerToEdit = null;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			
			makerToEdit = (MediaMaker) selectionView.getSelectedItem();				
			entryView = new MakerEntryView(makerToEdit);
		
			entryView.addDoneListener(new EntryDoneListener());
			
			
		}
		
		class EntryDoneListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				//Remove object from hash-map
				model.mediaMakerDataBase.getMediaMakerMap().remove(makerToEdit.toString());
				
				//Change the object
				makerToEdit.setMdbMediaFirstName(entryView.getFirstName());
				makerToEdit.setMdbMediaLastName(entryView.getLastName());
				makerToEdit.setMdbMediaDisambiguationNumber(entryView.getDisambiguationNumber());	
				
				// Add it back under new key
				model.mediaMakerDataBase.getMediaMakerMap().put(makerToEdit.toString(), makerToEdit);
				entryView.dispose();
			}
			
		}
	}
	public class EditDeleteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			//TODO
			model.delete(selectionView.getSelectedItem());
			
		}
	}
	public class EditClearListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			//TODO
			model.clear(selectionView.getSelectedItem());
			
		}
	}
	public class EditClearAllListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			//TODO
			model.clearAll();
			
		}
	}
	public class DisplayPieChartListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			selectionView.showPieChart((MediaMaker)selectionView.getSelectedItem());
			
		}
	}
	public class DisplayHistogramListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			selectionView.showHistogram((MediaMaker)selectionView.getSelectedItem());			
		}
	}
	
	public class RadiobuttonChangedListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			RadioButtonStates states = selectionView.getButtonStates();
			model.setButtonStates(states);
			model.createDisplayItemList();
			model.processEvent(EventMessages.DATA_CHANGED);	
			
			if (states.isMediaSelected())
				selectionView.setDataLabel("Media");
			else if (states.isMoviesSelected())
				selectionView.setDataLabel("Movie");
			else if (states.isEpisodesSelected())
				selectionView.setDataLabel("Episode");
			else if (states.isSeriesSelected())
				selectionView.setDataLabel("Series");
			else if (states.isMakersSelected())
				selectionView.setDataLabel("Makers");
			else if (states.isActorsSelected())
				selectionView.setDataLabel("Actor");
			else if (states.isDirectorsSelected())
				selectionView.setDataLabel("Director");
			else if (states.isProducersSelected())
				selectionView.setDataLabel("Producer");
		}
	}
}
