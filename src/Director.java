import java.util.ArrayList;

/**
 * Project #3
 * CS 2334, Section ***  ***
 * *** 02-05-2016 ***
 * <P>
 * This is the Director class of Project3. 
 * </P>
 * @version 1.0
 */
public class Director extends MediaMaker
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7431467323651870487L;

	/*
	 * The default constructor of Director.
	 */
	public Director()
	{
		super();
	}
	
	public Director(String mdbMediaFirstName,String mdbMediaLastName,String mdbMediaDisambiguationNumber,
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
	
	@Override
	public String getDisplayText()
	{
		return "Director: " + toString();
	}
}
