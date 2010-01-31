package JSPpro;
/**
 * Used to report error messages to the user across the site.
 * Do not confuse this with actual errors within the site.
 * @author tux
 *
 */
public class BeanError {
	private String type;
	private String message;
	
	public BeanError() {
		this.type = "Unknown Error";
		this.message = "An unknown event or other has failed and thusly you have.";
	}
	public BeanError( String type, String message ) {
		this.type = type;
		this.message = message;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
