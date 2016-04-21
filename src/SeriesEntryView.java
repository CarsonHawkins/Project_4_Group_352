import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class SeriesEntryView extends MediaEntryView
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel titleLabel,
		   startDateLabel,
		   endDateLabel;
	
	JTextArea titleArea,
		   	  startDateArea,
			  endDateArea;
			 
	@Override
	public void initComponents()
	{
		super.initComponents();
		
		this.setTitle("Enter Series Data");

		titleLabel = new JLabel("Title:", JLabel.CENTER);
		startDateLabel = new JLabel("Start Date:", JLabel.CENTER);
		endDateLabel = new JLabel("End Date:", JLabel.CENTER);
		
		titleArea = new JTextArea();
		titleArea.setBorder(BorderFactory.createLineBorder(Color.black));
		startDateArea = new JTextArea();
		startDateArea.setBorder(BorderFactory.createLineBorder(Color.black));
		endDateArea = new JTextArea();
		endDateArea.setBorder(BorderFactory.createLineBorder(Color.black));
		doneButton = new JButton("Done");
		
		panel.add(titleLabel);
		panel.add(titleArea);
		panel.add(startDateLabel);
		panel.add(startDateArea);
		panel.add(endDateLabel);
		panel.add(endDateArea);
		panel.add(new JLabel(""));
		panel.add(doneButton);
		

		this.add(panel);
		this.setVisible(true);
	}
	
	/**
	 * Create the dialog with a movie object given
	 * @param movie
	 */
	public SeriesEntryView(Series series)
	{
		super(series);
		initComponents();
		
		this.titleArea.setText(series.getSeriesName());
		this.startDateArea.setText(series.getSeriesStartYear());
		this.endDateArea.setText(series.getSeriesEndYear());
	}
	
	/**
	 * Constructor with no arguments
	 */
	public SeriesEntryView()
	{
		super();
		initComponents();
	}

	@Override
	public Series instantiate()
	{
		return new Series(titleArea.getText(), 
						 startDateArea.getText(), 
						 endDateArea.getText()
						);
		}
	
	public String getSeriesTitle()
	{
		return titleArea.getText();
	}

	public String getStartDate()
	{
		return startDateArea.getText();
	}

	public String getEndDate()
	{
		return endDateArea.getText();
	}
}
