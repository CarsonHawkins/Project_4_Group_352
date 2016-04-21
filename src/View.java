import java.awt.event.ActionListener;

import javax.swing.JFrame;

/**
 * abstract class for any View, that is a window
 */
public abstract class View extends JFrame implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for View
	 */
	public View()
	{
	}

	/**
	 * Create and add all components within this view
	 */
	protected abstract void initComponents();
}
