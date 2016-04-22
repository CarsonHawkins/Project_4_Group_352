import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * allows the user to enter data about a mediamaker
 * @author Daniel Schon
 *
 */
public class MakerEntryView extends DataEntryView
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panel;
	JLabel firstNameLabel,
		   lastNameLabel,
		   disambiguationLabel;
	JTextArea firstNameArea,
			  lastNameArea,
			  disambiguationArea;
	JButton doneButton;
	
	Class<?> entryType;
		   
	/**
	 * constructor for makerentryview
	 * @param makerType
	 */
	public MakerEntryView(Class<?> makerType)
	{
		super();
		entryType = makerType;
		this.setTitle("Enter " + makerType.getName() + " data");
		initComponents();
	}
	

	/**
	 * alternate constructor with params for existing values
	 * @param makerType
	 * @param first
	 * @param last
	 * @param disamb
	 */
	public MakerEntryView(Class<?> makerType, String first, String last, String disamb)
	{
		super();
		entryType = makerType;
		initComponents();
		this.setTitle("Enter " + makerType.getName() + " data");
		this.firstNameArea.setText(first);
		this.lastNameArea.setText(last);
		this.disambiguationArea.setText(disamb);
	}
	
	/**
	 * alternate constructor taking a mediamaker as a parameter 
	 * @param maker
	 */
	public MakerEntryView(MediaMaker maker)
	{
		super();
		entryType = maker.getClass();
		initComponents();
		this.setTitle("Enter " + maker.getClass().getName() + " data");
		this.firstNameArea.setText(maker.getMdbMediaFirstName());
		this.lastNameArea.setText(maker.getMdbMediaLastName());
		this.disambiguationArea.setText(maker.getMdbMediaDisambiguationNumber());
	}

	/**
	 * called when an action is performed
	 */
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		
	}

	@Override
	protected void initComponents()
	{
		this.setSize(300, 150);

		panel = new JPanel();
		GridLayout layout = new GridLayout(0,2);
		layout.setVgap(10);
		panel.setLayout(layout);
		firstNameLabel = new JLabel("First name:", JLabel.CENTER);
		lastNameLabel = new JLabel("Last name:", JLabel.CENTER);
		disambiguationLabel = new JLabel("Disambiguation Number:", JLabel.CENTER);
		firstNameArea = new JTextArea();
		firstNameArea.setBorder(BorderFactory.createLineBorder(Color.black));
		lastNameArea = new JTextArea();
		lastNameArea.setBorder(BorderFactory.createLineBorder(Color.black));
		disambiguationArea = new JTextArea();
		disambiguationArea.setBorder(BorderFactory.createLineBorder(Color.black));
		doneButton = new JButton("Done");
		panel.add(firstNameLabel);
		panel.add(firstNameArea);
		panel.add(lastNameLabel);
		panel.add(lastNameArea);
		panel.add(disambiguationLabel);
		panel.add(disambiguationArea);
		panel.add(new JLabel(""));	//Just a placeholder 
		panel.add(doneButton);

		this.add(panel);
		this.setVisible(true);
		
	}
	
	public MediaMaker instantiate() throws InstantiationException, IllegalAccessException
	{
		MediaMaker maker = (MediaMaker) entryType.newInstance();
		maker.setMdbMediaFirstName(firstNameArea.getText());
		maker.setMdbMediaLastName(lastNameArea.getText());
		maker.setMdbMediaDisambiguationNumber(disambiguationArea.getText());
		return maker;
		
	}

	public String getFirstName()
	{
		return firstNameArea.getText();
	}

	public String getLastName()
	{
		return lastNameArea.getText();
	}

	public String getDisambiguationNumber()
	{
		return disambiguationArea.getText();
	}

	public void addDoneListener(ActionListener listener)
	{
		doneButton.addActionListener(listener);
	}
}
