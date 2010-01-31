package JSPpro;
import java.util.*;
/**
 * Created: 090916
 * @author tux
 *
 *	This is designed to transport items to the info part of the webpage.
 *	If idUser isn't included it will be set to -1 which means it's not
 *	a real account.
 */
public class BeanInfo {
	private int id;
	private int idUser;
	private String type;
	private String screenName;
	private String title;
	private String text;
	private Date time;

	/* * * Constructors * * */
	public BeanInfo() {
		id = 0;
		idUser = -1;
		screenName = null;
		type = null;
		title = "Failed to initiate Correctly";
		text = null;
		time = null;
	}
	public BeanInfo( int id, String screenName, String type, String title, String text, Date time ) {
		this.id = id;
		this.idUser = -1;
		this.screenName = screenName;
		this.type = type;
		this.title = title;
		this.text = text;
		this.time = time;
	}
	public BeanInfo( int id, int idUser, String screenName, String type, String title, String text, Date time ) {
		this.id = id;
		this.idUser = idUser;
		this.screenName = screenName;
		this.type = type;
		this.title = title;
		this.text = text;
		this.time = time;
	}
	
	/* * * Set methods * * */
	public void setId( int id ) {
		this.id = id;
	}
	public void setUserId( int id ) {
		this.idUser = id;
	}
	public void setScreenName( String screenName ) {
		this.screenName = screenName;
	}
	public void setType( String type ) {
		this.type = type;
	}
	public void setTitle( String title ) {
		this.title = title;
	}
	public void setText( String text ) {
		this.text = text;
	}
	public void setTime( Date time ) {
		this.time = time;
	}
	
	/* * * Get methods * * */
	public int getId() {
		return id;
	}
	public int getUserId() {
		return idUser;
	}
	public String getScreenName() {
		return screenName;
	}
	public String getType() {
		return type;
	}
	public String getTitle() {
		return title;
	}
	public String getText() {
		return text;
	}
	public Date getTime() {
		return time;
	}
} // End of Class: BeanInfo
