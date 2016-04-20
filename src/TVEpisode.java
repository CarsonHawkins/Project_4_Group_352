import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;


/**
	 * Project #2
	 * CS 2334, Section ***  ***
	 * *** 02-019-2016 ***
	 * @author Ramon Valenzuela
	 * <P>
	 * This is the TvEpisode class of Project1. 
	 * </P>
	 * @version 1.0
	 */
	public class TVEpisode extends Media implements Comparable<TVEpisode>, Serializable
	{
		
	private static final long serialVersionUID = 7410524650572578561L;
		private String episodeName;
		private String episodeStartYear;
		private String episodeInfo;
		private String episodeYear;
		private Series series;
		
		/*
		 * The default constructor of the TvSeries class.
		 */
		public TVEpisode()
		{
			episodeName        = "";
			episodeStartYear   = "";
			episodeInfo        = "";
			episodeYear        = "";
		}
		
		public TVEpisode(String mdbEpisodeName)
		{
			episodeName        = mdbEpisodeName;
			episodeStartYear   = "";
			episodeInfo        = "";
			episodeYear        = "";
		}
		
		/*
		 * The overloaded TvSeries constructor which will create Movie objects.
		 */
		public TVEpisode(String mdbEpisodeName,String mdbEpisodeStartYear,String mdbEpisodeInfo,String mdbEpisodeYear)
		{
			episodeName        = mdbEpisodeName;
			episodeStartYear   = mdbEpisodeStartYear;
			episodeInfo        = mdbEpisodeInfo;
			episodeYear        = mdbEpisodeYear;
		}

		/*
		 * The overloaded compareTo method which had to be implemented due to the abstract nature of Compare.
		 */
		public int compareTo(TVEpisode b)
		{
			return (this.episodeName.toLowerCase().compareTo(b.episodeName.toLowerCase()));
		}
		
		/*
		 * The overloaded Comparator constructor which will compare to years within two
		 * Movie objects.
		 */
		public static final Comparator<TVEpisode> TvSeriesYearComparator = new Comparator<TVEpisode>()
			{
				public int compare(TVEpisode a,TVEpisode b)
				{
					
					return a.getEpisodeStartYear().compareTo(b.getEpisodeStartYear());
					
				}
			};

		/**
		 * @return the seriesName
		 */
		public String getEpisodeName() 
		{
			return episodeName;
		}

		/**
		 * @return the seriesStartYear
		 */
		public String getEpisodeStartYear() 
		{
			return episodeStartYear;
		}
		
		
		/**
		 * @return the seriesEpisodeYear
		 */
		public String getEpisodeYear() 
		{
			return episodeYear;
		}
		
		/**
		 * @return the episodeInfo
		 */
		public String getEpisodeInfo() {
			return episodeInfo;
		}
		
		
		
		public Series getSeries()
		{
			return series;
		}

		public void setSeries(Series series)
		{
			this.series = series;
		}

		/**
		 * @return the tvseriesyearcomparator
		 */
		public static Comparator<TVEpisode> getTVEpisodeyearcomparator() 
		{
			return TvSeriesYearComparator;
		}

		/*
			 * (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
		public String toString()
		{
			return episodeName + " " + episodeStartYear + " " + episodeInfo + " " + episodeYear;
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
		
		public String getDisplayText()
		{
			return "Episode: " + episodeInfo + " (" + episodeYear + "): " + episodeName;
		}
}
