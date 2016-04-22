import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Represents an item that could be displayed in the SelectView (Media or Maker)
 * @author Daniel Schon
 *
 */
public abstract class ListItem implements Comparable<ListItem>, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Get the string that represents the object as seen in the gui
	 * @return
	 */
	public abstract String getDisplayText();
	
	/**
	 * Gets the string that represents the object as seen in a text file
	 * @return
	 */
 	public abstract String getFileText();
	
	public void writeObject(ObjectOutputStream aOutputStream) throws IOException 
	 {
		 //perform the default serialization for all non-transient, non-static fields
		 aOutputStream.defaultWriteObject();
	 }
	
	public void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
	{
		aInputStream.defaultReadObject();
	}
	
	@Override
	public int compareTo(ListItem arg0)
	{
		return this.toString().compareTo(arg0.toString());
	}
}
