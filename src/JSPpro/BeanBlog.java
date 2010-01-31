package JSPpro;
import java.util.*;

public class BeanBlog {
	private int idBlog;
	private int idUser;
	private String title;
	private String text;
	private String ip;
	private Date time;
	private int count;

	/* * * Constructors * * */
	public BeanBlog() {
		idBlog = 0;
		idUser = 0;
		title = null;
		text = null;
		ip = null;
		time = null;
		count = 0;
	}
	
	public BeanBlog( int idBlog, int idUser, String title, String text, String ip, Date time, int count ) {
		this.idBlog = idBlog;
		this.idUser = idUser;
		this.title = title;
		this.text = text;
		this.ip = ip;
		this.time = time;
		this.count = count;
	}
	
	/* * * Set methods * * */
	public void setIdBlog( int id ) {
		this.idBlog = id;
	}
	public void setIdUser( int id ) {
		this.idUser = id;
	}
	public void setTitle( String title ) {
		this.title = title;
	}
	public void setText( String text ) {
		this.text = text;
	}
	public void setIp( String ip ) {
		this.ip = ip;
	}
	public void setTime( Date time ) {
		this.time = time;
	}
	public void setCount( int count ) {
		this.count = count;
	}
	
	/* * * Get methods * * */
	public int getIdBlog() {
		return idBlog;
	}
	public int getIdUser() {
		return idUser;
	}
	public String getTitle() {
		return title;
	}
	public String getText() {
		return text;
	}
	public String getIp() {
		return ip;
	}
	public Date getTime() {
		return time;
	}
	public int getCount() {
		return count;
	}
} // End of Class: BeanBlog
