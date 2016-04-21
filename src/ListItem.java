import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Represents an item that could be displayed in the SelectView (Media or Maker)
 * @author dlsch
 *
 */
public abstract class ListItem implements Comparable<ListItem>
{
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
