import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

/**
 * Essentially a JFileChooser wrapped in a view
 *
 */
public class FileSelectionView extends View
{
	private File selectedFile;
	
	public FileSelectionView()
	{
		super();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initComponents()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		File selectedFile = chooser.getSelectedFile();
	}

	public File getSelectedFile()
	{
		return selectedFile;
	}
}
