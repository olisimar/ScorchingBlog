package JSPpro;

/**
 * @created 09-09-11
 * @author tux
 *
 *	This for the use only to have compile safe Strings as markers/alias.
 *	Add as needed but do not remove or edit already entered entities.
 */
public class Literals {
	// These are for web.xml
	public static final String INIT_PARAMETER_DBSERVER = "server";
	public static final String INIT_PARAMETER_USERNAME = "user";
	public static final String INIT_PARAMETER_PASSWORD = "password";

	// For usage of lists of users sent between functions and classes.
	public static final String USER_LIST = "userList";
	public static final String MENU_LIST = "menuList";
	public static final String LAST_LIST = "lastList";
	public static final String INFO_LIST = "infoList";
	
	public static final String TYPE_USER = "user";
	public static final String TYPE_BLOG = "blog";
	public static final String TYPE_COMMENT = "comment";
	public static final String ERROR_MESSAGE = "error_message";
	
	// For things in the session object
	public static final String SESSION_USER = "session_user"; 
	
	// CSS strings for classes when I generate from inside the servlets.
	public static final String CSS_SEL_AUTH = "select_authour";
	public static final String CSS_UNSEL_AUTH = "unselect_authour";
	public static final String CSS_SEL_BLOG = "select_blog";
	public static final String CSS_UNSEL_BLOG = "unselect_blog";
	
/*	String dbServer = config.getInitParameter(StringConst.INIT_PARAMETER_HOSTNAME);
	String dbUser = config.getInitParameter(StringConst.INIT_PARAMETER_USERNAME);
	String dbPassword = config.getInitParameter(StringConst.INIT_PARAMETER_PASSWORD);
*/
}
