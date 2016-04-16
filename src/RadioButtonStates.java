
public class RadioButtonStates
{
	private boolean mediaButton,    
					moviesButton,   
					seriesButton,   
					episodesButton, 
					makersButton,   
					actorsButton,   
					directorsButton,
					producersButton;

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

	public boolean isMediaSelected()
	{
		return mediaButton;
	}

	public boolean isMoviesSelected()
	{
		return moviesButton;
	}

	public boolean isSeriesSelected()
	{
		return seriesButton;
	}

	public boolean isEpisodesSelected()
	{
		return episodesButton;
	}

	public boolean isMakersSelected()
	{
		return makersButton;
	}

	public boolean isActorsSelected()
	{
		return actorsButton;
	}

	public boolean isDirectorsSelected()
	{
		return directorsButton;
	}

	public boolean isProducersSelected()
	{
		return producersButton;
	}
	
	
}
