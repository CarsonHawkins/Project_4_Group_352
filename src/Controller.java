import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Controller.SelectPlaceButtonListener;
import employeePackage.SalariedEmployee;

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

		// Register listener 
		selectionView.addSaveListener(new FileSaveListener());
	}
	
	public class FileSaveListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String filename = new FilePickerView().getFileName();
			model.saveFile(filename);
		}	
	}
	public class FileLoadListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String filename = new FilePickerView().getFileName();
			model.LoadFile(filename);
		}	
	}
	public class FileExportListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String filename = new FilePickerView().getFileName();
			model.ExportFile(filename);
		}	
	}
	public class FileImportListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String filename = new FilePickerView().getFileName();
			model.ImportFile(filename);
		}	
	}
	public class LoadFileActorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String filename = new FilePickerView().getFileName();
			model.LoadFileActor(filename);
		}
	}
	public class LoadFileMovieListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String filename = new FilePickerView().getFileName();
			model.LoadFileMovie(filename);
		}
	}
	public class LoadFileSeriesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String filename = new FilePickerView().getFileName();
			model.LoadFileSeries(filename);
		}
	}
	public class LoadFileDirectorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String filename = new FilePickerView().getFileName();
			model.LoadFileDirector(filename);
		}
	}
	public class LoadFileProducerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String filename = new FilePickerView().getFileName();
			model.LoadFileProducer(filename);
		}
	}	
	public class addEditAddItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
			
		}
	}
	public class addEditEditItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
		}
	}
	public class addEditDeleteItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
		}
	}
	public class addEditClearItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
		}
	}
	public class addEditClearAllItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
		}
	}
	public class addDisplayPieChartItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
		}
	}
	public class addDisplayHistogramItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model == null)
				return; // No model associated yet. Do nothing
		}
	}
}
