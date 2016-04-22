import java.util.Arrays;

import javax.swing.JFileChooser;

/**
 * Wrapper class that makes file selecting easier
 * @author Daniel Schon
 */
public class FileSelector
{
	/**
	 * Opens a save dialog and returns the result
	 * @return
	 */
	public static String showSaveDialog()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.showSaveDialog(null);
		return chooser.getSelectedFile().getAbsolutePath();
	}
	
	/**
	 * Opens an open dialog and returns the result(s)
	 * @return
	 */
	public static String[] showOpenDialog()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		chooser.showOpenDialog(null);
		return Arrays.stream(chooser.getSelectedFiles()).map(file -> file.getAbsolutePath()).toArray(String[]::new);
	}
}
