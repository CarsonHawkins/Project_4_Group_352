import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Project #2
 * CS 2334, Section ***  ***
 * *** 02-019-2016 ***
 * @author Ramon Valenzuela
 * <P>
 * This is the TvSeries class of Project1. 
 * </P>
 * @version 1.0
 */
public class Series extends Media implements Comparable<Series>, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -864529516154819206L;
	private String seriesName;
	private String seriesStartYear;
	private String seriesEndYear;
	
	private ArrayList<TVEpisode> episodeList = new ArrayList<>();
	

	/*
	 * The default constructor of the TvSeries class.
	 */
	public Series()
	{
		seriesName        = "";
		seriesStartYear   = "";
		seriesEndYear   = "";
		
	}
	
	/* movieTitle. It will allow the methods to be able to use the appropriate movieTitle  
	 * and not have to constantly have movieTitle as a parameter. 
	 * @param movieTitle	movieTitle will be assign from the MbdDriver class and assign it  
	 * to the Movie movieTitle. 
 	 */ 
	public Series(String seriesName) 
	{ 
 		 
 		//The Movie constructor will assign movieTitle to the Class Movie movieTitle 
 		this.seriesName = seriesName; 
 		seriesStartYear   = "";
 		seriesEndYear   = "";
		
 	} 

	/*
	 * The overloaded TvSeries constructor which will create Movie objects.
	 */
	public Series(String mdbSeriesName,String mdbSeriesStartYear,String mdbSeriesEndYear)
	{
		seriesName          = mdbSeriesName;
		seriesStartYear     = mdbSeriesStartYear;
		seriesEndYear     = mdbSeriesEndYear;
		
	}
	
	/*
	 * The overloaded compareTo method which had to be implemented due to the abstract nature of Compare.
	 */
	public int compareTo(Series a)
	{
		return (this.seriesName.toLowerCase().compareTo(a.seriesName.toLowerCase()));
	}
	
	/*
	 * The overloaded Comparator constructor which will compare to years within two
	 * Movie objects.
	 */
	public static final Comparator<Series> TvSeriesYearComparator = new Comparator<Series>()
		{
			public int compare(Series a,Series b)
			{
				return a.getSeriesStartYear().compareTo(b.getSeriesStartYear());
			}
		};

	/**
	 * @return the seriesName
	 */
	public String getSeriesName() 
	{
		return seriesName;
	}

	/**
	 * @return the seriesStartYear
	 */
	public String getSeriesStartYear() 
	{
		return seriesStartYear;
	}

	/**
	 * @return the seriesEpisodeYear
	 */
	public String getSeriesEndYear() 
	{
		return seriesEndYear;
	}

	/**
	 * @return the tvseriesyearcomparator
	 */
	public static Comparator<Series> getTvEpisodeyearcomparator() 
	{
		return TvSeriesYearComparator;
	}
	
	public ArrayList<TVEpisode> getEpisodeList()
	{
		return episodeList;
	}

	public void setEpisodeList(ArrayList<TVEpisode> episodeList)
	{
		this.episodeList = episodeList;
	}
	/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
	public String toString()
	{
		return seriesName + " (" + seriesStartYear + "-" + seriesEndYear + ")";
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
		return seriesName + " (" + seriesStartYear + "-" + seriesEndYear + ")";
	}

}

