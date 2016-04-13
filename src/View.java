import java.awt.event.ActionListener;

import javax.swing.JFrame;

public abstract class View extends JFrame implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public View()
	{
		initComponents();
	}

	protected abstract void initComponents();
}
