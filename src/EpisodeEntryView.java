import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * Entry view for an episode
 * @author Daniel Schon
 *
 */
public class EpisodeEntryView extends MediaEntryView
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Label for title */
	JLabel titleLabel,
	/** label for date */
		   dateLabel,
		   /** label for release  */
		   seriesLabel;
	/** textarea for title*/
	JTextArea titleArea,
	/** textarea for date */
			 dateArea;
	/** combobox for releaseform */
	JComboBox<Series> seriesPicker;
	
	/** a list of serieses to choose from*/
	private Series[] serieses = new Series[]{};
	/** a reference to the media model */
	private MediaModel model;
			 
	/** initializes the components */
	@Override
	public void initComponents()
	{
		super.initComponents();
		
		this.setTitle("Enter Episode Data");

		titleLabel = new JLabel("Title:", JLabel.CENTER);
		dateLabel = new JLabel("Date:", JLabel.CENTER);
		seriesLabel = new JLabel("Series:", JLabel.CENTER);
		
		titleArea = new JTextArea();
		titleArea.setBorder(BorderFactory.createLineBorder(Color.black));
		dateArea = new JTextArea();
		dateArea.setBorder(BorderFactory.createLineBorder(Color.black));
		seriesPicker = new JComboBox<Series>(serieses);
		setSerieses();
		seriesPicker.setBorder(BorderFactory.createLineBorder(Color.black));
		doneButton = new JButton("Done");
		
		panel.add(titleLabel);
		panel.add(titleArea);
		panel.add(dateLabel);
		panel.add(dateArea);
		panel.add(seriesLabel);
		panel.add(seriesPicker);
		panel.add(new JLabel(""));
		panel.add(doneButton);
		

		this.add(panel);
		this.setVisible(true);
	}
	
	/** 
	 * constructor for episodeentryview 
	 */
	public EpisodeEntryView(MediaModel model)
	{
		super();
		this.model = model;
		initComponents();
	}
	
	/**
	 * Create the dialog with a movie object given
	 * @param movie
	 */
	public EpisodeEntryView(MediaModel model, TVEpisode episode)
	{
		super(episode);
		this.model = model;
		this.titleArea.setText(episode.getEpisodeName());
		this.dateArea.setText(episode.getEpisodeYear());
		initComponents();
	}
	
	/** 
	 * creates an instance of movie according to the entered data 
	 */
	@Override
	public TVEpisode instantiate()
	{
		return new TVEpisode(getSeries().getSeriesName(),
						     getEpisodeYear(),  
						     getEpisodeTitle(), 
						     getSeries().getSeriesStartYear());
	}
	
	/**
	 * Set the serieseseses
	 * @param serieseses
	 */
	public void setSerieses()
	{
		if (model == null)
		{
			serieses = new Series[] {new Series("No associated series")};
		}
		else
		{
			serieses = (Series[]) Driver.concat(new Series[] {new Series("No associated series", "????", "????")}, model.seriesDataBase.getSeriesList().toArray(new Series[model.seriesDataBase.getSeriesList().size()]));
			seriesPicker.removeAllItems();
			for (Series s: serieses)
			{
				seriesPicker.addItem(s);
			}
		}
	}
	
	/**
	 * called when an action is performed
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals(EventMessages.DATA_CHANGED))
			setSerieses();
	}
	
	public String getEpisodeTitle()
	{
		return titleArea.getText();
	}
	
	public String getEpisodeYear()
	{
		return dateArea.getText();
	}
	
	public Series getSeries()
	{
		return (Series) seriesPicker.getSelectedItem();
	}

}
