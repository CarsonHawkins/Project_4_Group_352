import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * The main view of the project
 * @author Daniel Schon
 *
 */
public class SelectionView extends View
{
	/////// Only exists for testing purposes
	//public static void main(String[] args){new SelectionView();};
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The panel that contains the other panels */
	JPanel containerPanel,
	/** The panel that contains the controls */
		   controlsPanel,
	/** The panel that contains the data */
		   dataPanel,
	/** The panel that holds the data label */
		   dataLabelPanel;
	
	/** scrollpane that contains items to be displayed */
	JScrollPane scrollPane;
	/** component that actually displays the info */
	JList<ListItem> itemList;
	
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
	/** item that adds */
			  editAddItem,
	/** item that edits */
			  editEditItem,
	/** item that deletes */
			  editDeleteItem,
	/** item that clears */
			  editClearItem,
	/** item that clears all */
			  editClearAllItem,
	/** item that displays a piechart */
			  displayPieChartItem,
	/** item that displays a histogram */
			  displayHistogramItem;
	
	/** label for the data */
	JLabel dataLabel;
	
	/** box for buttons */
	Box buttonBox;
	/** group for buttons */
	ButtonGroup group;
	/** radiobutton for media */
	JRadioButton mediaButton,
	/** radiobutton for movies */
				 moviesButton,
	 /** radiobutton for series */
				 seriesButton,
	 /** radiobutton for episodes */
				 episodesButton,
	 /** radiobutton for makers */
				 makersButton,
	 /** radiobutton for actors */
				 actorsButton,
	 /** radiobutton for directors */
				 directorsButton,
	 /** radiobutton for producers */
				 producersButton;
	
	/**
	 * A list of episodeentryviews used to update series lists
	 */
	MediaModel model;
	
	/**
	 * Constructor for selectionview
	 */
	public SelectionView()
	{
		super();
		initComponents();
	}
	
	/** Initializes the components */
	@Override
	protected void initComponents()
	{
		this.setTitle("MDb");
		this.setSize(600, 400);
		this.setMinimumSize(new Dimension(400,280));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

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
		  editAddItem.setEnabled(false);
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
		controlsPanel.setPreferredSize(new Dimension(100,400));
		controlsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		buttonBox = Box.createVerticalBox();
		mediaButton = new JRadioButton("Media");
		mediaButton.setSelected(true);
		moviesButton = new JRadioButton("Movies");
		seriesButton = new JRadioButton("Series");
		episodesButton = new JRadioButton("Episodes");
		makersButton = new JRadioButton("Makers");
		actorsButton = new JRadioButton("Actors");
		directorsButton = new JRadioButton("Directors");
		producersButton = new JRadioButton("Producers");
		group = new ButtonGroup();
		group.add(mediaButton);
		group.add(moviesButton);
		group.add(seriesButton);
		group.add(episodesButton);
		group.add(makersButton);
		group.add(actorsButton);
		group.add(directorsButton);
		group.add(producersButton);

		buttonBox.add(new JLabel("Selection"));
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
		dataPanel.setSize(new Dimension(500,400));
		dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
		
		dataLabelPanel = new JPanel();
		dataLabel = new JLabel("Media");
		dataLabelPanel.add(dataLabel);
		dataLabelPanel.setPreferredSize(new Dimension(500, 20));
		dataLabelPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		itemList = new JList<ListItem>(new ListItem[0]);
		itemList.setCellRenderer(new MyRenderer());
		itemList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane = new JScrollPane(itemList);
		scrollPane.setPreferredSize(new Dimension(500, 580));
		
		dataPanel.add(dataLabelPanel);
		dataPanel.add(scrollPane);
		
		containerPanel.add(controlsPanel);
		containerPanel.add(dataPanel);

		this.setJMenuBar(menuBar);
		this.add(containerPanel);
		this.setVisible(true);
	}
	
	/**
	 * sets model
	 * @param m
	 */
	public void setModel(MediaModel m)
	{
		this.model = m;
		itemList.setListData(m.displayList.toArray(new ListItem[m.displayList.size()]));
		this.actionPerformed(new ActionEvent(this, 0, EventMessages.DATA_CHANGED));
	}
	
	@Override
	/**	 * 
	 * called when an action is performed
	 * 
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (model == null)
			return;
		
		/* Enables save/export options if model has objects, else disables them */
		boolean enableButtons = !model.displayList.isEmpty();
		fileSaveItem.setEnabled(enableButtons);
		fileExportItem.setEnabled(enableButtons);
		editEditItem.setEnabled(enableButtons);
		editDeleteItem.setEnabled(enableButtons);
		editClearItem.setEnabled(enableButtons);
		editClearAllItem.setEnabled(enableButtons);
		
		if (!enableButtons)
		{
			fileSaveItem.setToolTipText("No items to save");
			fileExportItem.setToolTipText("No items to export");
			editEditItem.setToolTipText("No items to edit");
			editDeleteItem.setToolTipText("No items to delete");
			editClearItem.setToolTipText("No items to clear");
			editClearAllItem.setToolTipText("No items to clear");
		}
		else
		{
			fileSaveItem.setToolTipText("");
			fileExportItem.setToolTipText("");
			editEditItem.setToolTipText("");
			editDeleteItem.setToolTipText("");
			editClearItem.setToolTipText("");
			editClearAllItem.setToolTipText("");
		
		}
		
		// Enables the add button only if an appropriate thing is selected
		RadioButtonStates s = getButtonStates();
		boolean enableAddButton = s.isMoviesSelected() ||
							   s.isSeriesSelected() ||
							   s.isEpisodesSelected() ||
							   s.isActorsSelected() ||
							   s.isDirectorsSelected() ||
							   s.isProducersSelected();

		editAddItem.setEnabled(enableAddButton);
		if (!enableAddButton)
			editAddItem.setToolTipText("Cannot add selected type");
		else
			editAddItem.setToolTipText("");
		
		
		boolean enableDisplays = false;
		if (model.displayList.size() > 0 && getSelectedItems().length > 0)
		{
			for (ListItem item : getSelectedItems())
			{
				if (item instanceof MediaMaker)
					enableDisplays = true;
			}
		}
		
		displayHistogramItem.setEnabled(enableDisplays);
		displayPieChartItem.setEnabled(enableDisplays);
		if (!enableDisplays)
		{
			displayHistogramItem.setToolTipText("Cannot display histogram for Media type");
			displayPieChartItem.setToolTipText("Cannot display pie chart for Media type");
		}
		else
		{
			displayHistogramItem.setToolTipText("");
			displayPieChartItem.setToolTipText("");
		}
		
		itemList.setListData(model.displayList.toArray(new ListItem[model.displayList.size()]));

	}
	
	/**returns the selected item */
	public ListItem[] getSelectedItems()
	{
		ArrayList<ListItem> items = new ArrayList<>();
		for (int index : this.itemList.getSelectedIndices())
		{
			items.add(model.displayList.get(index));
		}
		return (ListItem[]) items.toArray(new ListItem[0]);
	}

	/** adds a given listener to a given component */
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
	
	/**
	 * Adds a listener to every radiobutton
	 * @param listener
	 */
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
	
	/**
	 * get the states of all the buttons 
	 * @return
	 */
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
	
	/**
	 * display a pie chart
	 * @param maker
	 * @return
	 */
	public DisplayView showPieChart(MediaMaker maker)
	{
		return new DisplayView(Display.ChartType.PIE_CHART, maker);
	}
	
	/**
	 * display a histogram
	 * @param maker
	 * @return
	 */
	public DisplayView showHistogram(MediaMaker maker)
	{
		return new DisplayView(Display.ChartType.HISTOGRAM, maker);
	}
	
	/**
	 * set data label
	 * @param labelText
	 */
	public void setDataLabel(String labelText)
	{
		dataLabel.setText(labelText);
	}
	
	/**
	 * Custom renderer for the JList so it diplays getDisplayText instead of ToString,
	 *
	 */
	class MyRenderer extends DefaultListCellRenderer 
	{
		private static final long serialVersionUID = 1L;

		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		{
			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		    setText(((ListItem) value).getDisplayText()); // where getValue is some method you implement that gets the text you want to render for the component
		    return c;
		}
	}
}
