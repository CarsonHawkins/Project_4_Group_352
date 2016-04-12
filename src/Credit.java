import java.io.Serializable;

/**
 * Represents a credit
 * @author Ramon
 *
 */
public class Credit implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1856851487654869502L;

	public static enum MediaType
	{
		MOVIE,
		EPISODE
	}
	
	public static enum MakerType
	{
		ACTOR,
		PRODUCER,
		DIRECTOR
	}
	
	private Media media;
	private MediaType mediaType;
	private MakerType makerType;
	protected String archiveFootage;
	protected String uncredited;
	private MediaMaker maker;
	
	public Credit(Media media, MediaType mediaType, MakerType makerType, String archiveFootage, String uncredited, MediaMaker maker)
	{
		this.media = media;
		this.mediaType = mediaType;
		this.makerType = makerType;
		this.archiveFootage = archiveFootage;
		this.uncredited = uncredited;
		this.maker = maker;	}
	
	public String toString()
	{
		return media.toString() + " " + mediaType.name() + " " + makerType.name();
	}
	
	public String outputString()
	{
		if (media instanceof Movie)
			return makerType.name() + ": " + mediaType.name() + ": " + ((Movie)media).getMovieTitle();
		else
			return makerType.name() + ": " + mediaType.name() + ": " + ((TVEpisode)media).getEpisodeName();
	}

	public Media getMedia()
	{
		return media;
	}

	public void setMedia(Media media)
	{
		this.media = media;
	}

	public MediaType getMediaType()
	{
		return mediaType;
	}

	public void setMediaType(MediaType mediaType)
	{
		this.mediaType = mediaType;
	}

	public MakerType getMakerType()
	{
		return makerType;
	}

	public void setMakerType(MakerType makerType)
	{
		this.makerType = makerType;
	}

}
