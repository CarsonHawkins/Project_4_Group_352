
/**
 * Regular Expressions used for text parsing
 * @author Daniel Schon
 *
 */
public class Regexes 
{
	/** Matches on a single date of 4 numbers (or ?): xxxx */
	public static final String SINGLE_DATE = "(?<![-\\d])([\\d?]{4})(?![-\\d])";
	/** Matches on a single date of 4 numbers inside parentheses (or ?): (xxxx) */
	public static final String SINGLE_DATE_PARENTHESES = "(?<![-\\d])\\(([\\d?]{4})[/IVXL]*\\)(?![-\\d])";
	/** Matches on a range of dates separated by a hyphen: xxxx-xxxx */
	public static final String DATE_RANGE = "(?<![\\d?])(([\\d?]{4})-([\\d?]{4}))(?![-\\d?])";
	/** Matches on a an movie title: __________ */
	public static final String MOVIE_TITLE = "^(.*)(?= \\([\\d?]{4}(?:\\)|\\/))"; 
	/** Matches on a duplicate numeral */
	public static final String MOVIE_DUPLICATE = "(?<=[\\d?]{4}\\/)([IVXL]+)(?=\\))";
	/** Matches on venue */
	public static final String MOVIE_VENUE = "\\((TV|V)\\)";
	/** Matches on a an TV Series title: "__________" */
	public static final String TV_SERIES_TITLE = "^(?:\\\")(.*)(?:\\\")";
	/** Matches on a an episode title: {__________} */
	public static final String EP_TITLE = "(?<!\\{)(?:\\{)([^}{]*)(?:\\})";
	/** Matches on a an episode number: (#x.x) */
	public static final String EP_NUMBER = "(?<=\\(#)([\\d\\.]{3,5})(?=\\))";
	/** Matches on the date an episode was aired */
	public static final String EP_YEAR = "\\{\\((\\d{4})";
	/** Matches on a disambiguation number */
	public static final String BILLING = "<(\\d+)>";
	/** Matches on the actor role*/
	public static final String ACTOR_ROLE = "\\[(.+)\\]";
	/** Matches the actor last name */
	public static final String LAST_NAME = "^([^,]+)";
	/** Matches the actor first name */
	public static final String FIRST_NAME = ", ([^\\t\\(]+)";
	/** Matches the actor disambiguation number */
	public static final String DISAMBIGUATION_NUMBER = "\\(([IVXL]+)\\)";
	/** Matches on whether a movie is an archive footage */
	public static final String ARCHIVE_FOOTAGE = "\\((archive footage)\\)";
	/** Matches on whether an actor is uncredited */ 
	public static final String UNCREDITED = "\\((uncredited)\\)";


}
