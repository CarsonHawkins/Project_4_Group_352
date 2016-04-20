import java.awt.event.ActionListener;

public abstract class DataEntryView extends View
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public abstract void addDoneListener(ActionListener listener);
	
	public abstract ListItem instantiate() throws InstantiationException, IllegalAccessException; 
	
}
