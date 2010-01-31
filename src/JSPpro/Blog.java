package JSPpro;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class Blog
 */
public class Blog extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DataBaseObj dbService; // For connections to the database
	
	public void init(ServletConfig config) throws ServletException {
  	// TODO Auto-generated method stub
  	super.init(config);
  	String dbServer = config.getInitParameter( Literals.INIT_PARAMETER_DBSERVER );
  	String dbUser = config.getInitParameter( Literals.INIT_PARAMETER_USERNAME );
  	String dbPassword = config.getInitParameter( Literals.INIT_PARAMETER_PASSWORD );
  	
  	dbService = new DataBaseObj( dbServer, dbUser, dbPassword );
  } 

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Blog() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * This will handle my GET variables coming in, or none if that's the case.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		BeanUser user = null;
		
		if( session.getAttribute( Literals.SESSION_USER) != null ) {
			user = (BeanUser)session.getAttribute( Literals.SESSION_USER );
			session.setAttribute( Literals.SESSION_USER, user );
		}
		
		if( request.getParameter( "comment" ) != null  ) { // We want to comment.
			int idBlog = Integer.parseInt( request.getParameter( "comment" ) );
			
			List<BeanInfo> info = new ArrayList<BeanInfo>();
			Connection conn = dbService.getDBConnection();
			info = dbService.createInfo(conn, idBlog );
			BeanInfo blog = (BeanInfo)info.get( 0 );
			request.setAttribute( "BeanBlog", blog );
			request.setAttribute( Literals.INFO_LIST, info );
			
			dbService.closeConnection( conn );
			request.getRequestDispatcher( "WEB-INF/JSPpro/comment.jsp" ).forward( request, response );
		}
		else if( request.getParameter( "alter" ) != null ) {
			int idBlog = Integer.parseInt( request.getParameter( "alter" ) );
			List<BeanInfo> info = new ArrayList<BeanInfo>();
			Connection conn = dbService.getDBConnection();
			info = dbService.createInfo(conn, idBlog );
			BeanInfo blog = info.get( 0 ); // First item which is the actual blog.
			
			dbService.closeConnection( conn );
			request.setAttribute( Literals.INFO_LIST, blog );
			request.getRequestDispatcher( "WEB-INF/JSPpro/blog.jsp" ).forward( request, response );
		}
		else {
			if( user != null ) { // Automatic blogging.
				//public BeanInfo( int id, int idUser, String screenName, String type, String title, String text, Date time ) {
				request.setAttribute( Literals.INFO_LIST,  new BeanInfo( 0, user.getId(), user.getScreenName(), Literals.TYPE_BLOG, "", "", new Date() ) );
				request.getRequestDispatcher( "WEB-INF/JSPpro/blog.jsp" ).forward( request, response );
			}
			else { // Nothing to comment or blog as we aren't logged in.
				response.sendRedirect( "Login" );
			}
		}
	} // End of Method: doGet
	
	

	/**
	 * This should be unused as it should only deal with presentation from the
	 * webpages that comes in from GET
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String wanted = request.getParameter( "submit_input" );
		
		if( wanted.equals( "Comment" ) ) {
			int idBlog = Integer.parseInt( request.getParameter( "idBlog" ) );
			String title = request.getParameter( "comment_title" );
			String alias = request.getParameter( "comment_alias" );
			String email = request.getParameter( "comment_email" );
			String text = request.getParameter( "comment_text" );
			String ip = request.getRemoteAddr();
			String[] options = { email, alias, title, text, ip };
			String sqlStmnt = "INSERT INTO `JSP-comment` (`id_comment`,`id_blog`,`email`,`screen_name`,`title`,`text`,`ip`,`time`) VALUES (NULL,?,?,?,?,?,?,NULL)";
			
			Connection conn = dbService.getDBConnection();
			int result = dbService.setDataBaseEntry(conn, sqlStmnt, idBlog, options);
			dbService.closeConnection( conn );
			
			response.sendRedirect( "User" );
		} // End of IF( Comment )
		else if( wanted.equals( "Blog" ) ) {
			int idUser = Integer.parseInt( request.getParameter( "idUser" ) );
			int idBlog = Integer.parseInt( request.getParameter( "idBlog" ) );
			String title = request.getParameter( "blog_title" );
			String alias = request.getParameter( "blog_alias" );
			String text = request.getParameter( "blog_text" );
			String ip = request.getRemoteAddr();

			String[] options = { title, text, ip };
			String sqlStmnt = "INSERT INTO `JSP-blog` (`id_blog`,`id_user`,`title`,`text`,`ip`,`time`,`count`) VALUES ( NULL, ?, ?, ?, ?, NULL, 0 )";
			Connection conn = dbService.getDBConnection();
			int result = dbService.setDataBaseEntry( conn, sqlStmnt, idUser, options );
			
			dbService.closeConnection( conn );
			response.sendRedirect( "User" );
		} // End of IF( Blog )
		else if( wanted.equals( "Update" ) ) {
			int idBlog = Integer.parseInt( request.getParameter( "idBlog" ) );
			String title = request.getParameter( "blog_title" );
			String text = request.getParameter( "blog_text" );

			String[] options = { title, text };
			String sqlStmnt = "UPDATE `JSP-blog` SET `title`=?,`text`=? WHERE `id_blog`="+idBlog;
			Connection conn = dbService.getDBConnection();
			int result = dbService.setDataBaseEntry( conn, sqlStmnt, options );
			
			dbService.closeConnection( conn );
			response.sendRedirect( "User" );
		} // End of IF( Alter )
		else {
			response.sendRedirect( "User" );
		} // End of ELSE( Comment / Blog / Alter )
	} // End of Method: doPost
} // End of class: Blog
