import java.util.ArrayList;
import java.util.LinkedHashMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Project #3
 * CS 2334, Section ***  ***
 * *** 02-05-2016 ***
 * <P>
 * This is the Actor class of Project3. 
 * </P>
 * @version 1.0
 */
public class Actor extends MediaMaker
{
	
	public Actor()
	{
		super();
	}
	
	public Actor(String mdbMediaFirstName,String mdbMediaLastName,String mdbMediaDisambiguationNumber,
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
		return "Actor: " + toString();
	}
}
