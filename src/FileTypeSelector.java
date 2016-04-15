import java.awt.Component;

import javax.swing.JOptionPane;

public class FileTypeSelector
{
	public String showDialog(String fileName)
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
