import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Project #3
 * CS 2334, Section ***  ***
 * *** 02-05-2016 ***
 * <P>
 * This is the MediaMaker class of Project3. 
 * </P>
 * @version 1.0
 */
public class MediaMaker  extends ListItem implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2949480466314429965L;
	/*
	 * The default constructor which will be used to instantiate the variables.
	 */
	private String mdbMediaFirstName;
	private String mdbMediaLastName;
	private String mdbMediaDisambiguationNumber;
	
	private ArrayList<Credit> movieCredits;
	private ArrayList<Credit> seriesCredits;
	
	public MediaMaker()
	{
		mdbMediaFirstName = "";
		mdbMediaLastName = "";
		mdbMediaDisambiguationNumber = "";
		this.setMovieCredits(new ArrayList<Credit>());
		this.setSeriesCredits(new ArrayList<Credit>());
	}
	
	/*
	 * The Overloaded constructor which will take in values read directly from the file and initialize them into
	 * appropriate values.
	 * 
	 * @param
	 * 
	 */
	public MediaMaker(String mdbMediaFirstName,
			String mdbMediaLastName,
			String mdbMediaDisambiguationNumber,
			ArrayList<Credit> movieCredits,
			ArrayList<Credit> seriesCredits)
	{
		this.mdbMediaFirstName = mdbMediaFirstName;
		this.mdbMediaLastName = mdbMediaLastName;
		this.mdbMediaDisambiguationNumber = mdbMediaDisambiguationNumber;
		this.setMovieCredits(movieCredits);
		this.setSeriesCredits(seriesCredits);
	}
	
	
	
	/**
	 * @return the mdbMediaFirstName
	 */
	public String getMdbMediaFirstName() {
		return mdbMediaFirstName;
	}

	/**
	 * @return the mdbMediaLastName
	 */
	public String getMdbMediaLastName() {
		return mdbMediaLastName;
	}

	/**
	 * @return the mdbMediaDisambiguationNumber
	 */
	public String getMdbMediaDisambiguationNumber() {
		return mdbMediaDisambiguationNumber;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return (mdbMediaLastName + ", " + mdbMediaFirstName + " " + mdbMediaDisambiguationNumber).trim();
	}

	public ArrayList<Credit> getSeriesCredits() {
		return seriesCredits;
	}

	public void setSeriesCredits(ArrayList<Credit> seriesCredits) {
		this.seriesCredits = seriesCredits;
	}

	public ArrayList<Credit> getMovieCredits() {
		return movieCredits;
	}

	public void setMovieCredits(ArrayList<Credit> movieCredits) {
		this.movieCredits = movieCredits;
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
}	
