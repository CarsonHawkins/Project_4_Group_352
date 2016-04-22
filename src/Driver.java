import java.util.Arrays;

/**
 * The main driver class. Kicks it all off
 * @author Daniel Schon
 *
 */
public class Driver
{
	/**
	 * The main entry point of the program
	 * @param args
	 */
	public static void main(String[] args)
	{
		// Create the MVC
		MediaModel model = new MediaModel();
		SelectionView selectionView = new SelectionView();
		Controller controller = new Controller();
		
		// Introduce them to each other
		controller.setView(selectionView);
		model.addActionListenener(selectionView);
		model.setButtonStates(selectionView.getButtonStates());
		controller.setModel(model);
		selectionView.setModel(model);
	}
	
	/**
	 * Random utility method I don't know where else to pu
	 * @param first
	 * @param second
	 * @return
	 */
	public static <T> T[] concat(T[] first, T[] second) 
	{
	  T[] result = Arrays.copyOf(first, first.length + second.length);
	  System.arraycopy(second, 0, result, first.length, second.length);
	  return result;
	}
}
