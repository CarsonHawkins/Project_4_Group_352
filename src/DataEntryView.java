import java.awt.event.ActionListener;

/**
 * The abstract class for any view that is used to enter data
 * @author Daniel Schon
 *
 */
public abstract class DataEntryView extends View
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Adds a listener for when the data has been entered
	 * @param listener
	 */
	public abstract void addDoneListener(ActionListener listener);
	
	/**
	 * 
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public abstract ListItem instantiate() throws InstantiationException, IllegalAccessException; 
	
}
