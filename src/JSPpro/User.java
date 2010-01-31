package JSPpro;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class User
 */
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataBaseObj dbService;
       
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
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public User() {
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

	/**************************************************************************
	 * Take care of all eventualities bar comment and blog entries. This is also
	 * deals with displaying a certain authours blog submissions.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		BeanUser user = null;
		List <BeanMenu> menu = new ArrayList<BeanMenu>();
		List <BeanInfo> info = new ArrayList<BeanInfo>();
		
		if( request.getParameter( "user" ) != null ) {
			int[] idUser = { Integer.parseInt( request.getParameter( "user" ) ) };
			
			if( request.getParameter( "blog" ) != null ) {
				int[] idBlog = { Integer.parseInt( request.getParameter( "blog" ) ) };
				try {
					Connection conn = dbService.getDBConnection();
					ResultSet rs = dbService.getQueryResult(conn, "SELECT * FROM `JSP-blog` WHERE `id_user`=? ORDER BY `time` ASC", idUser );
					while( rs.next() ) {
						if(  rs.getInt( "id_blog" ) == idBlog[ 0 ] ) {
							menu.add( new BeanMenu( rs.getInt( "id_blog" ), idUser[0], rs.getString( "title" ), Literals.TYPE_BLOG, Literals.CSS_SEL_BLOG ) );
							info = dbService.createInfo( conn, idBlog[ 0 ] );
						} // end of if( id_blog )
						else {
							menu.add( new BeanMenu( rs.getInt( "id_blog" ), idUser[0], rs.getString( "title" ), Literals.TYPE_BLOG, Literals.CSS_SEL_BLOG ) );
						}
					}
					request.setAttribute( Literals.MENU_LIST, menu );
					request.setAttribute( Literals.INFO_LIST, info );
					
					dbService.closeConnection( conn );
					
					request.getRequestDispatcher( "WEB-INF/JSPpro/show_user.jsp" ).forward( request, response );
				} // end if( blog )
				catch( Exception e ) {
					response.sendRedirect( "Start" );
				}
				
			} // End of blog=?
			else { 
				try {
					Connection conn = dbService.getDBConnection();
					ResultSet rs = dbService.getQueryResult( conn, "SELECT * FROM `JSP-blog` WHERE `id_user`=?", idUser );
					rs.first();
					int[] idBlog = { rs.getInt( "id_blog" ) };
					
					menu.add( new BeanMenu( rs.getInt( "id_blog" ), idUser[ 0 ], rs.getString( "title" ), Literals.TYPE_BLOG, Literals.CSS_SEL_BLOG ) );
					while( rs.next() ) {
						menu.add( new BeanMenu( rs.getInt( "id_blog" ), idUser[0], rs.getString( "title" ), Literals.TYPE_BLOG, Literals.CSS_SEL_BLOG ) );
					}

					info = dbService.createInfo( conn, idBlog[ 0 ] );
					
					request.setAttribute( Literals.MENU_LIST, menu );
					request.setAttribute( Literals.INFO_LIST, info );

					dbService.closeConnection( conn );

					request.getRequestDispatcher( "WEB-INF/JSPpro/show_user.jsp" ).forward( request, response );
				}
				catch( Exception e ) { // bad thing happened.
					response.sendRedirect( "Start" );
				}
			} // End of else blog=?
		} // End of user=?
		
		else if( session.getAttribute( Literals.SESSION_USER ) != null ) {
			user = (BeanUser) session.getAttribute( Literals.SESSION_USER );
			int[] options = { user.getId() };
			Connection conn = dbService.getDBConnection();
			session.setAttribute( Literals.SESSION_USER, user );
			
			try {
				ResultSet rs = dbService.getQueryResult(conn, "SELECT * FROM `JSP-blog` WHERE `id_user`=? ORDER BY `time` ASC", options);
				if( rs.next() ) {
					int[] id_blog = { rs.getInt( "id_blog" ) };
					
					info = dbService.createInfo( conn, id_blog[ 0 ] );
				
					menu.add( new BeanMenu( rs.getInt( "id_blog" ),options[ 0 ], rs.getString( "title" ), Literals.TYPE_BLOG, Literals.CSS_SEL_BLOG ) );
					
					while( rs.next() ) {
						menu.add( new BeanMenu( rs.getInt( "id_blog" ), options[ 0 ], rs.getString( "title" ), Literals.TYPE_BLOG, Literals.CSS_SEL_BLOG ) );
					}

					request.setAttribute( Literals.MENU_LIST, menu );
					request.setAttribute( Literals.INFO_LIST, info );

					dbService.closeConnection( conn );

					request.getRequestDispatcher( "WEB-INF/JSPpro/show_user.jsp" ).forward( request, response );
				}
				else {
					// have to write this one.
					response.sendRedirect( "Blog" );
				}
			} 
			catch( Exception e ) { // bad thing happened.
				System.out.println( "Catch: RS went bad on me" );
				response.sendRedirect( "Start" );
			}
		}
		else { // Plain in which is nothing to us without a loggedIn user.
			response.sendRedirect( "Start" );
		}
	}

	/**************************************************************************
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
