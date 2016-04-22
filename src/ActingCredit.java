

/**
 * Represents a credit for an actor
 * @author Ramon
 *
 */
public class ActingCredit extends Credit{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String role;
	private String billing;
	
	public ActingCredit(Media media, MediaType mediaType, String archiveFootage, String uncredited, String role, String billing, MediaMaker maker)
	{
		super(media, mediaType, MakerType.ACTOR, archiveFootage, uncredited, maker);
		this.role = role;
		this.billing = billing;
	}
	
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the billing
	 */
	public String getBilling() {
		return billing;
	}

	/**
	 * @param billing the billing to set
	 */
	public void setBilling(String billing) {
		this.billing = billing;
	}
	
	public String toString()
	{
		return super.toString() + " " + archiveFootage + " " + uncredited;
	}

}
