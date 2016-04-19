import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Arrays;

import javax.swing.JFileChooser;

/**
 * Essentially a JFileChooser wrapped in a view
 *
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
