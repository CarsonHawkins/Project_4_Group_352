import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Project #3
 * CS 2334, Section ***  ***
 * *** 02-05-2016 ***
 * <P>
 * This is the Producer class of Project3. 
 * </P>
 * @version 1.0
 */
public class Producer extends MediaMaker implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4630576433692213364L;
	/*
	 * Default constructor for the Producer class.
	 */
	public Producer()
	{
		super();
	}
	
	public Producer(String mdbMediaFirstName,
			String mdbMediaLastName,
			String mdbMediaDisambiguationNumber,
			ArrayList<Credit> movieCredits,
			ArrayList<Credit> seriesCredits)
	{
		super(mdbMediaFirstName,mdbMediaLastName,mdbMediaDisambiguationNumber,movieCredits,seriesCredits);
	}
	/*
	 * (non-Javadoc)
	 * @see MediaMaker#toString()
	 */
	public String toString()
	{
		return super.toString();
	}
	
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
	public String getDisplayText()
	{
		return "Producer: " + toString();
	}
}
