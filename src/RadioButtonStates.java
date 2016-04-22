
/**
 * Contains data about the on/off states of all the selectionview's radiobuttons
 * @author Daniel Schon
 *
 */
public class RadioButtonStates
{
	/** state of the mediaButton */
	private boolean mediaButton,    
					/** state of the moviesButton */
					moviesButton,   
					/** state of the seriesButton */
					seriesButton,   
					/** state of the episodesButton */
					episodesButton, 
					/** state of the makersButton */
					makersButton,   
					/** state of the actorsButton */
					actorsButton,   
					/** state of the directorsButton */
					directorsButton,
					/** state of the producersButton */
					producersButton;

	/**
	 * Constructor for this class
	 * @param mediaButton
	 * @param moviesButton
	 * @param seriesButton
	 * @param episodesButton
	 * @param makersButton
	 * @param actorsButton
	 * @param directorsButton
	 * @param producersButton
	 */
	public RadioButtonStates(boolean mediaButton, boolean moviesButton, boolean seriesButton, boolean episodesButton,
			boolean makersButton, boolean actorsButton, boolean directorsButton, boolean producersButton)
	{
		this.mediaButton = mediaButton;
		this.moviesButton = moviesButton;
		this.seriesButton = seriesButton;
		this.episodesButton = episodesButton;
		this.makersButton = makersButton;
		this.actorsButton = actorsButton;
		this.directorsButton = directorsButton;
		this.producersButton = producersButton;
	}

	/**
	 * returns media state
	 * @return
	 */
	public boolean isMediaSelected()
	{
		return mediaButton;
	}

	/**
	 * returns movie state
	 * @return
	 */
	public boolean isMoviesSelected()
	{
		return moviesButton;
	}

	/**
	 * returns Series state
	 * @return
	 */
	public boolean isSeriesSelected()
	{
		return seriesButton;
	}
	/**
	 * returns Episodes state
	 * @return
	 */
	public boolean isEpisodesSelected()
	{
		return episodesButton;
	}

	/**
	 * returns Makers state
	 * @return
	 */
	public boolean isMakersSelected()
	{
		return makersButton;
	}

	/**
	 * returns Actors state
	 * @return
	 */
	public boolean isActorsSelected()
	{
		return actorsButton;
	}

	/**
	 * returns Directors state
	 * @return
	 */
	public boolean isDirectorsSelected()
	{
		return directorsButton;
	}

	/**
	 * returns Producers state
	 * @return
	 */
	public boolean isProducersSelected()
	{
		return producersButton;
	}
	
	
}
