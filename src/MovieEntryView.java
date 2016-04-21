import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class MovieEntryView extends MediaEntryView
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
		   releaseFormLabel;
	/** textarea for title*/
	JTextArea titleArea,
	/** textarea for date */
			 dateArea;
	/** combobox for releaseform */
	JComboBox<String> releaseFormPicker;
			 
	@Override
	public void initComponents()
	{
		super.initComponents();
		
		this.setTitle("Enter Movie Data");

		titleLabel = new JLabel("Title:", JLabel.CENTER);
		dateLabel = new JLabel("Date:", JLabel.CENTER);
		releaseFormLabel = new JLabel("Release form:", JLabel.CENTER);
		
		titleArea = new JTextArea();
		titleArea.setBorder(BorderFactory.createLineBorder(Color.black));
		dateArea = new JTextArea();
		dateArea.setBorder(BorderFactory.createLineBorder(Color.black));
		releaseFormPicker = new JComboBox<>(new String[] {"Theater", "Made for TV", "Straight to video"});
		releaseFormPicker.setBorder(BorderFactory.createLineBorder(Color.black));
		doneButton = new JButton("Done");
		
		panel.add(titleLabel);
		panel.add(titleArea);
		panel.add(dateLabel);
		panel.add(dateArea);
		panel.add(releaseFormLabel);
		panel.add(releaseFormPicker);
		panel.add(new JLabel(""));
		panel.add(doneButton);
		

		this.add(panel);
		this.setVisible(true);
	}
	
	public MovieEntryView()
	{
		super();
		initComponents();
	}
	
	/**
	 * Create the dialog with a movie object given
	 * @param movie
	 */
	public MovieEntryView(Movie movie)
	{
		super(movie);
		initComponents();
		this.titleArea.setText(movie.getTitle());
		this.dateArea.setText(movie.getYear());
		if (movie.getReleaseForm().equals(""))
			this.releaseFormPicker.setSelectedIndex(0);
		else if (movie.getReleaseForm().contains("(TV)"))
			this.releaseFormPicker.setSelectedIndex(1);
		else if (movie.getReleaseForm().contains("(V)"))
			this.releaseFormPicker.setSelectedIndex(0);
	}
	
	@Override
	public Movie instantiate()
	{
		String venueSymbol =  releaseFormPicker.getSelectedItem().equals("Theater") ? "" :
							  releaseFormPicker.getSelectedItem().equals("Made for TV") ? "(TV)" :
							  "(V)";
		return new Movie(titleArea.getText(), 
						 dateArea.getText(), 
						 dateArea.getText(),
						 venueSymbol
						);
		}
	
	public String getMovieTitle()
	{
		return titleArea.getText();
	}
	
	public String getMovieYear()
	{
		return dateArea.getText();
	}

	public String getReleaseForm()
	{
		return releaseFormPicker.getSelectedItem().equals("Theater") ? "" :      
	           releaseFormPicker.getSelectedItem().equals("Made for TV") ? "(TV)" :
               "(V)";    
	}
}

