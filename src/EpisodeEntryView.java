import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

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
	private Series[] serieses = {new Series()};
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
		seriesPicker.setBorder(BorderFactory.createLineBorder(Color.black));
		
		panel.add(titleLabel);
		panel.add(titleArea);
		panel.add(dateLabel);
		panel.add(dateArea);
		panel.add(seriesLabel);
		panel.add(seriesPicker);
		

		this.add(panel);
		this.setVisible(true);
	}
	
	/** constructor for episodeentryview */
	public EpisodeEntryView(MediaModel model)
	{
		super();
		this.model = model;
	}
	
	/**
	 * Create the dialog with a movie object given
	 * @param movie
	 */
	public EpisodeEntryView(MediaModel model, TVEpisode episode)
	{
		super(episode);
		this.model = model;
		this.titleArea.setText(episode.getEpisodeInfo());
		this.dateArea.setText(episode.getEpisodeYear());
	}
	
	/** creates an instance of movie according to the entered data */
	@Override
	public Movie instantiate()
	{
		return null;
	}
	
	/**
	 * Set the serieseseses
	 * @param serieseses
	 */
	public void setSerieses()
	{
		seriesPicker = new JComboBox<Series>((Series[]) model.seriesDataBase.getSeriesList().toArray());
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals(EventMessages.DATA_CHANGED))
			setSerieses();
	}

}
