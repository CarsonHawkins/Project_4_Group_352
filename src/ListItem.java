import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Represents an item that could be displayed in the SelectView (Media or Maker)
 * @author dlsch
 *
 */
public abstract class ListItem
{
	public abstract String getDisplayText();
	
	public void writeObject(ObjectOutputStream aOutputStream) throws IOException 
	 {
		 //perform the default serialization for all non-transient, non-static fields
		 aOutputStream.defaultWriteObject();
	 }
	
	public void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
	{
		aInputStream.defaultReadObject();
	}
}
