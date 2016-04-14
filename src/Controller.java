import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Controller {
	
	private MediaModel model; 
	private SelectionView selectionView;
	
	/** Creates new Controller */
	Controller() {
		model = new MediaModel();
		setModel(model);
		
		selectionView = new SelectionView();
		setView(selectionView);
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

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			
			
		}
	}
	public class EditEditListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			
		}
	}
	public class EditDeleteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			
		}
	}
	public class EditClearListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			
		}
	}
	public class EditClearAllListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			
		}
	}
	public class DisplayPieChartListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			
		}
	}
	public class DisplayHistogramListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			
		}
	}
}
