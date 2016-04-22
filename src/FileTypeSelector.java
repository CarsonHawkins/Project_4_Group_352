import javax.swing.JOptionPane;

/**
 * Wrapper class that makers selecting a file type easier
 * @author Daniel Schon
 *
 */
public class FileTypeSelector
{
	/**
	 * Shows the dialog and returns the user's answer
	 * @param fileName
	 * @return
	 */
	public static String showDialog(String fileName)
	{
		String[] options = {"Movies", "TV Series", "Actors", "Producers", "Directors"};
	    int code = -1;
		while (code == -1)
		{
	    	code = JOptionPane.showOptionDialog(
	        null, 
	        "Which type of file is \"" + fileName + "\"?", 
	        "Option Dialog Box", 0, JOptionPane.QUESTION_MESSAGE, 
	        null, options, "Movies");
		}
		return options[code];
	}
}
