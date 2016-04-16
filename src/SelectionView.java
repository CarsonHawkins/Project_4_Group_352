import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SelectionView extends View
{
	/////// Only exists for testing purposes
	//public static void main(String[] args){new SelectionView();};
	
	/** The panel that contains the other panels */
	JPanel containerPanel,
	/** The panel that contains the controls */
		   controlsPanel,
	/** The panel that contains the data */
		   dataPanel;
	
	/** The menu bar at the top */
	JMenuBar menuBar;
	
	/** Menu that contains file options */
	JMenu fileMenu,
	/** Menu that contains edit options */
		  editMenu,
	/** Menu that contains display options */
		  displayMenu;
	/** item that saves */
	JMenuItem fileSaveItem,
	/** item that loads */
			  fileLoadItem,
	/** item that exports */
			  fileExportItem,
	/** item that imports */
			  fileImportItem,
			  editAddItem,
			  editEditItem,
			  editDeleteItem,
			  editClearItem,
			  editClearAllItem,
			  displayPieChartItem,
			  displayHistogramItem;
	
	Box buttonBox;
	JRadioButton mediaButton,
				 moviesButton,
				 seriesButton,
				 episodesButton,
				 makersButton,
				 actorsButton,
				 directorsButton,
				 producersButton;
	
	MediaModel model;
	
	public SelectionView()
	{
		super();
	}
	
	@Override
	protected void initComponents()
	{
		this.setTitle("MDb");
		this.setSize(600, 400);
		

		menuBar = new JMenuBar();
		 fileMenu = new JMenu("File");
		  fileSaveItem = new JMenuItem("Save");
		  fileSaveItem.setEnabled(false);
		  fileLoadItem = new JMenuItem("Load");
		  fileExportItem = new JMenuItem("Export");
		  fileExportItem.setEnabled(false);
		  fileImportItem = new JMenuItem("Import");
		  fileMenu.add(fileSaveItem);
		  fileMenu.add(fileLoadItem);
		  fileMenu.add(fileExportItem);
		  fileMenu.add(fileImportItem);
		 editMenu = new JMenu("Edit");
		  editAddItem = new JMenuItem("Add");
		  editEditItem = new JMenuItem("Edit");
		  editEditItem.setEnabled(false);
		  editDeleteItem = new JMenuItem("Delete");
		  editDeleteItem.setEnabled(false);
		  editClearItem = new JMenuItem("Clear");
		  editClearItem.setEnabled(false);
		  editClearAllItem = new JMenuItem("Clear All");
		  editClearAllItem.setEnabled(false);
		  editMenu.add(editAddItem);
		  editMenu.add(editEditItem);
		  editMenu.add(editDeleteItem);
		  editMenu.add(editClearItem);
		  editMenu.add(editClearAllItem);
		 displayMenu = new JMenu("Display");
		  displayPieChartItem = new JMenuItem("Pie chart");
		  displayHistogramItem = new JMenuItem("Histogram");
		menuBar.add(fileMenu);
		  displayMenu.add(displayPieChartItem);
		  displayMenu.add(displayHistogramItem);
		menuBar.add(editMenu);
		menuBar.add(displayMenu);
		
		containerPanel = new JPanel();
		containerPanel.setSize(600,400);
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.X_AXIS));

		controlsPanel = new JPanel();
		controlsPanel.setPreferredSize(new Dimension(200,400));
		controlsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		buttonBox = Box.createVerticalBox();
		mediaButton = new JRadioButton("All Media");
		moviesButton = new JRadioButton("Movies");
		seriesButton = new JRadioButton("Series");
		episodesButton = new JRadioButton("Episodes");
		makersButton = new JRadioButton("All Makers");
		actorsButton = new JRadioButton("Actors");
		directorsButton = new JRadioButton("Directors");
		producersButton = new JRadioButton("Producers");
		buttonBox.add(mediaButton);
		buttonBox.add(moviesButton);
		buttonBox.add(seriesButton);
		buttonBox.add(episodesButton);
		buttonBox.add(makersButton);
		buttonBox.add(actorsButton);
		buttonBox.add(directorsButton);
		buttonBox.add(producersButton);
		
		controlsPanel.add(buttonBox);

		dataPanel = new JPanel();
		dataPanel.setPreferredSize(new Dimension(400,400));
		dataPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		containerPanel.add(controlsPanel);
		containerPanel.add(dataPanel);

		this.setJMenuBar(menuBar);
		this.add(containerPanel);
		this.setVisible(true);

	}
	
	public void setModel(MediaModel m)
	{
		this.model = m;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		/* Enables save/export options if model has objects, else disables them */
		boolean enableButtons = !model.listItems.isEmpty();
		fileSaveItem.setEnabled(enableButtons);
		fileExportItem.setEnabled(enableButtons);
		editEditItem.setEnabled(enableButtons);
		editDeleteItem.setEnabled(enableButtons);
		editClearItem.setEnabled(enableButtons);
		editClearAllItem.setEnabled(enableButtons);
		
		// Enables the add button only if an appropriate thing is selected
		RadioButtonStates s = getButtonStates();
		editAddItem.setEnabled(s.isMoviesSelected() ||
							   s.isSeriesSelected() ||
							   s.isMakersSelected() ||
							   s.isActorsSelected() ||
							   s.isDirectorsSelected() ||
							   s.isProducersSelected());
		
	}

	private void addListenerToComponent(AbstractButton b, ActionListener l)
	{
		if (b != null)
		b.addActionListener(l);
	}
	/**
	 * Add a save listener
	 * @param listener
	 */
	public void addSaveListener(ActionListener listener)
	{
		addListenerToComponent(fileSaveItem, listener);
	}
	
	/**
	 * Add a load listener
	 * @param listener
	 */
	public void addLoadListener(ActionListener listener)
	{
		addListenerToComponent(fileLoadItem, listener);
	}
	
	/**
	 * Add a export listener
	 * @param listener
	 */
	public void addExportListener(ActionListener listener)
	{
		addListenerToComponent(fileExportItem, listener);
	}
	
	/**
	 * Add a import listener
	 * @param listener
	 */
	public void addImportListener(ActionListener listener)
	{
		addListenerToComponent(fileImportItem, listener);
	}
	
	/**
	 * Add a add listener
	 * @param listener
	 */
	public void addAddListener(ActionListener listener)
	{
		addListenerToComponent(editAddItem, listener);
	}
	
	/**
	 * Add a edit listener
	 * @param listener
	 */
	public void addEditListener(ActionListener listener)
	{
		addListenerToComponent(editEditItem, listener);
	}
	
	/**
	 * Add a delete listener
	 * @param listener
	 */
	public void addDeleteListener(ActionListener listener)
	{
		addListenerToComponent(editDeleteItem, listener);
	}
	
	/**
	 * Add a clear listener
	 * @param listener
	 */
	public void addClearListener(ActionListener listener)
	{
		addListenerToComponent(editClearItem, listener);
	}
	
	/**
	 * Add a clear all listener
	 * @param listener
	 */
	public void addClearAllListener(ActionListener listener)
	{
		addListenerToComponent(editClearAllItem, listener);
	}
	
	/**
	 * Add a pieChart listener
	 * @param listener
	 */
	public void addPieChartListener(ActionListener listener)
	{
		addListenerToComponent(displayPieChartItem, listener);
	}
	
	/**
	 * Add a pieChart listener
	 * @param listener
	 */
	public void addHistogramListener(ActionListener listener)
	{
		addListenerToComponent(displayHistogramItem, listener);
	}
	
	public void addRadiobuttonChangedListener(ItemListener listener)
	{
		mediaButton.addItemListener(listener);
		moviesButton.addItemListener(listener);
		seriesButton.addItemListener(listener);
		episodesButton.addItemListener(listener);
		makersButton.addItemListener(listener);
		actorsButton.addItemListener(listener);
		directorsButton.addItemListener(listener);
		producersButton.addItemListener(listener);
	}
	
	public RadioButtonStates getButtonStates()
	{
		return new RadioButtonStates(
				mediaButton.isSelected(),
				moviesButton.isSelected(),
				seriesButton.isSelected(),
				episodesButton.isSelected(),
				makersButton.isSelected(),
				actorsButton.isSelected(),
				directorsButton.isSelected(),
				producersButton.isSelected());
	}
}
