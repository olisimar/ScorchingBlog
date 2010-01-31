package JSPpro;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/****************************************************************************
 * Authour: Werner Created: 090916 Servlet implementation class Start. This is
 * for basic browsing of the site. It does only present a top 20 and last 20
 * blog entries. It also puts out the latest blog for display on the main
 * window. This could possibly be changed at a later date for a proper
 * start-page.
 */
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataBaseObj dbService; // For connections to the database

	/*************************************************************************
	 * Setup of the database object.
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		String dbServer = config.getInitParameter(Literals.INIT_PARAMETER_DBSERVER);
		String dbUser = config.getInitParameter(Literals.INIT_PARAMETER_USERNAME);
		String dbPassword = config.getInitParameter(Literals.INIT_PARAMETER_PASSWORD);

		dbService = new DataBaseObj( dbServer, dbUser, dbPassword );
		
		if( dbService == null ) {
			dbService = new DataBaseObj(); // Making sure we have base object.
		}
	}

	/************************************************************************
	 * @see HttpServlet#HttpServlet()
	 */
	public Start() {
		super();
		// TODO Auto-generated constructor stub
  	try {
  		Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
  	} catch (InstantiationException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	} catch (IllegalAccessException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	} catch (ClassNotFoundException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
	}

	/*************************************************************************
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idBlog = 1; // This is a standard setting to avoid errors down the line.
		List<BeanBlog> lastBlogList = new ArrayList<BeanBlog>();
		List<BeanBlog> topBlogList = new ArrayList<BeanBlog>();
		
		List<BeanMenu> lastBlogMenu = new ArrayList<BeanMenu>();
		List<BeanMenu> topBlogMenu = new ArrayList<BeanMenu>();
		List<BeanInfo> infoBlog = new ArrayList<BeanInfo>();
		
		Connection conn = dbService.getDBConnection();
		
		String sqlStr1 = "SELECT * FROM `JSP-blog` ORDER BY `time` DESC LIMIT 0,?";
		String sqlStr2 = "SELECT * FROM `JSP-blog` ORDER BY `count` DESC LIMIT 0,?";
		int[] option1 = { 20 };
		int[] option2 = { 20 };
		lastBlogList = getBlogEntry( conn, sqlStr1, option1 ); // amount of blogs, selected id_blog if applicable.
		topBlogList = getBlogEntry( conn, sqlStr2, option2 ); // amount of blogs, selected id_blog if applicable.

		// If a blog was selected this is where we get hold of the id.
		if( request.getParameter( "blog" ) != null ) {
			idBlog = Integer.parseInt( request.getParameter( "blog" ) );
			infoBlog = dbService.createInfo( conn, idBlog );
		} 
		else if( lastBlogList.isEmpty() ) {
			infoBlog.add( new BeanInfo() );
		} 
		else {
			BeanBlog bb = lastBlogList.get( 0 );
			infoBlog = dbService.createInfo( conn, bb.getIdBlog() );
		}
		
		request.setAttribute( Literals.INFO_LIST, infoBlog );
		dbService.closeConnection( conn );
		
		// Two different menues, built along the same lines.
		for( BeanBlog blog : lastBlogList ) {
			if( idBlog == blog.getIdBlog() ) {
				lastBlogMenu.add( new BeanMenu( blog.getIdBlog(), blog.getTitle(), Literals.TYPE_BLOG, Literals.CSS_SEL_BLOG ) );
			} else {
				lastBlogMenu.add( new BeanMenu( blog.getIdBlog(), blog.getTitle(), Literals.TYPE_BLOG, Literals.CSS_UNSEL_BLOG ) );
			}
		}
		request.setAttribute( Literals.LAST_LIST, lastBlogMenu );
		
		for( BeanBlog blog : topBlogList ) {
			if( idBlog == blog.getIdBlog() ) {
				topBlogMenu.add( new BeanMenu( blog.getIdBlog(), blog.getTitle(), Literals.TYPE_BLOG, Literals.CSS_SEL_BLOG ) );
			} else {
				topBlogMenu.add( new BeanMenu( blog.getIdBlog(), blog.getTitle(), Literals.TYPE_BLOG, Literals.CSS_UNSEL_BLOG ) );
			}
		}
		request.setAttribute( Literals.MENU_LIST, topBlogMenu );
		
		// Sending out the info
		request.getRequestDispatcher( "WEB-INF/JSPpro/welcome.jsp" ).forward( request, response );
		
	} // End of Method: doGet

	
	/*************************************************************************
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		if( request.getParameter( "search_field" ) != null ) {
			String query = request.getParameter( "search_field" );
			System.out.println ( query );
			Connection conn = dbService.getDBConnection();
			
			String selStmt = "SELECT `id_user`,`email`,`screen_name` FROM `JSP-user` WHERE (`email`=? OR `screen_name`=?)";
			String[] options = { query, query };
			ResultSet rs = dbService.getQueryResult( conn, selStmt, options );
			try {
				if( rs.next() ) {
					rs.first();
					request.setAttribute( Literals.TYPE_USER, new BeanMenu( rs.getInt( "id_user" ), rs.getString( "screen_name" ), Literals.TYPE_USER, Literals.CSS_SEL_AUTH ) );
					request.getRequestDispatcher( "WEB-INF/JSPpro/search_result.jsp" ).forward( request, response );
				} // ENd of IF( rs.next )
				else {
					request.setAttribute( Literals.ERROR_MESSAGE, new BeanError( "Nothing found.", "Sadly no user with that alias or email was found in the database. This search can't help you anymore unless you misspelled in some way." ) );
					request.getRequestDispatcher( "WEB-INF/JSPpro/search_result.jsp" ).forward( request, response );
				} // End of ELSE
			} // End of TRY 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		else {
			// redirect to /Start as there was no Search.
		}
	} // End of doPost()

	
	
	
	/*************************************************************************
	 * This will return the last entered blogs into the database formatted to be
	 * put in a menu. There is
	 */
	private List<BeanBlog> getBlogEntry( Connection conn, String sqlStr, int[] option ) {
		List<BeanBlog> blogList = new ArrayList<BeanBlog>();
		
		try {

			ResultSet rs = dbService.getQueryResult( conn, sqlStr, option );

			if( rs != null ) {
				while( rs.next() ) {
					int idBlog = rs.getInt( "id_blog" );
					int idUser = rs.getInt( "id_user" );
					String title = rs.getString( "title" );
					String text = rs.getString( "text" );
					String ip = rs.getString( "ip" );
					Date time = rs.getDate( "time" );
					int count = rs.getInt( "count" );
					
					blogList.add( new BeanBlog( idBlog, idUser, title, text, ip, time, count) );
				}
			}
			else {
				blogList.add( new BeanBlog() );
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return blogList;
	} // End of Method: getBlogEntry
	
} // End of Class: Start
