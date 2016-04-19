import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public abstract class MediaEntryView extends DataEntryView
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JPanel panel;

	JButton doneButton;
	
	GridLayout layout;
	
	Media media;
			   
	public MediaEntryView()
	{
		super();
	}
	
	public MediaEntryView(Media media)
	{
		super();
		this.media = media;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		
	}

	@Override
	protected void initComponents()
	{
		this.setSize(300, 150);

		panel = new JPanel();
		layout = new GridLayout(0,2);
		layout.setVgap(10);
		panel.setLayout(layout);
		
	}
	
	public abstract Media instantiate() throws InstantiationException, IllegalAccessException;


	public void addDoneListener(ActionListener listener)
	{
		doneButton.addActionListener(listener);
	}
}
