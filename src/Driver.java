/**
 * The main driver class. Kicks it all off
 * @author dlsch
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
}
