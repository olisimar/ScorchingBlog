package JSPpro;

public class BeanUser {
	private int id;
	private String email;
	private String screenName;
	private Boolean showComments;
	private String role;
	private String usedIp;
	
	/* * * Constructors * * */
	public BeanUser() {
		id = 0;
		email = "none@none";
		screenName = "NoName";
		showComments = true;
		role = "None";
		usedIp = "0.0.0.0";
	}
	
	public BeanUser( int id,String email, String screenName, Boolean showComments, String role, String usedIp  ) {
		this.id = id;
		this.email = email;
		this.screenName = screenName;
		this.showComments = showComments;
		this.role = role;
		this.usedIp = usedIp;
	}
	
	// Get functions for the bean.
	public int getId() {
		return this.id;
	}
	public String getEmail() {
		return this.email;
	}
	public String getScreenName() {
		return this.screenName;
	}
	public Boolean getShowComments() {
		return this.showComments;
	}
	public String getRole() {
		return this.role;
	}
	public String getUsedIp() {
		return this.usedIp;
	}
	
	// Set functions for the bean.
	public void setId( int id ) {
		this.id = id;
	}
	public void setEmail( String email ) {
		this.email = email;
	}
	public void setScreenName( String screenName ) {
		this.screenName = screenName;
	}
	public void setShowComments( Boolean showComments ) {
		this.showComments = showComments;
	}
	public void setRole( String role ) {
		this.role = role;
	}
	public void setUsedIp( String usedIp ) {
		this.usedIp = usedIp;
	}

} // End of Class: BeanUser
