import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class MediaEntryView extends DataEntryView
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * panel that holds the components
	 */
	JPanel panel;

	/**
	 * button to be clicked when done
	 */
	JButton doneButton;
	
	/**
	 * layout
	 */
	GridLayout layout;
	
	/**
	 * media object being created
	 */
	Media media;
		
	/**
	 * main constructor
	 */
	public MediaEntryView()
	{
		super();
	}
	
	/**
	 * constructor for mediaentryview 
	 * @param media
	 */
	public MediaEntryView(Media media)
	{
		super();
		this.media = media;
	}

	/**
	 * called when an action is performed
	 */
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		
	}

	/**
	 * inits the components
	 */
	@Override
	protected void initComponents()
	{
		this.setSize(300, 150);

		panel = new JPanel();
		layout = new GridLayout(0,2);
		layout.setVgap(10);
		panel.setLayout(layout);
		
	}
	
	/**
	 * method that creates an instance of the object
	 */
	public abstract Media instantiate() throws InstantiationException, IllegalAccessException;


	/**
	 * adds the listener to the done button
	 */
	public void addDoneListener(ActionListener listener)
	{
		doneButton.addActionListener(listener);
	}
}
