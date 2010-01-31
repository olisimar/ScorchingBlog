package JSPpro;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class Login.
 * This will handle logins from the site as well as registration and logout.
 */
public class Login extends HttpServlet {
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
	
	/*************************************************************************
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String todo = "nothing";
		
		if( request.getParameter( "action" ) != null ) {
			todo = request.getParameter( "action" );
			
			if( todo.equals( "register" ) ) {
				request.getRequestDispatcher( "WEB-INF/JSPpro/register.jsp" ).forward( request, response );
			}
			else if( todo.equals( "logout" ) ) {
				session.removeAttribute( Literals.SESSION_USER );
				response.sendRedirect( "Start" );
			}
			else {
				// Something went wrong, start page
				response.sendRedirect( "Start" );
			}
		} // End of IF( action )
		
		else {
			// Assumed you want to login
			request.getRequestDispatcher( "WEB-INF/JSPpro/login.jsp" ).forward( request, response );
		}
	} // End of Method: doGet

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String todo = "nothing";
		if( request.getParameter( "submit_user" ) != null ) {
			todo = request.getParameter( "submit_user" );
			
			if( todo.equals( "Login" ) ) { // Check login for user.
				String login = request.getParameter( "login_user" );
				String passwd = request.getParameter( "login_password" );
				
				String[] options = { login, login, passwd };
				String selStmt = "SELECT * FROM `JSP-user` WHERE (`email`=? OR `screen_name`=?) AND `password`=?";
				try {
					Connection conn = dbService.getDBConnection();
					ResultSet rs = dbService.getQueryResult( conn, selStmt, options );
					if( rs.next() ) {
						rs.first();
						int idUser = rs.getInt( "id_user" );
						String email = rs.getString( "email" );
						String screenName = rs.getString( "screen_name" );
						Boolean showComments = rs.getBoolean( "show_comments" );
						String role = rs.getString( "role" );

						BeanUser bu = new BeanUser( idUser, email, screenName,showComments, role, request.getRemoteAddr() );
						session.setAttribute( Literals.SESSION_USER, bu );
						dbService.closeConnection( conn );
						
						response.sendRedirect( "User" );
						
					}
					else { // Failed to login.
						BeanError be = new BeanError( "Security Error", "Wrong password, alias or email. In either case you have to retype it all." );
						request.setAttribute( Literals.ERROR_MESSAGE, be );
						request.getRequestDispatcher( "WEB-INF/JSPpro/login.jsp" ).forward( request, response );
					}
					
					dbService.closeConnection( conn );
				} 
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} // End of IF( Login )
			// Registering new user if possible.
			else if( todo.equals( "Register" ) ) { 
				Boolean register = true;
				String login = request.getParameter( "reg_email" );
				String screenName = request.getParameter( "reg_screen" );
				String passwd1 = request.getParameter( "reg_password1" );
				String passwd2 = request.getParameter( "reg_password2" );
				
				if( passwd1.equals( passwd2 ) ) {
					Connection conn = dbService.getDBConnection();
					try {
						ResultSet rs1 = dbService.getQueryResult(conn, "SELECT * FROM `JSP-user` WHERE `email`=?",new String[]{ login } );
						if( rs1.next() ) {
							request.setAttribute( Literals.ERROR_MESSAGE, new BeanError( "Already in use", "The email address you tried to use is already in use. Try one that you have your self or you might want to check if it's still yours.") );
							request.getRequestDispatcher( "WEB-INF/JSPpro/register.jsp" ).forward( request, response );
						}
						
						ResultSet rs2 = dbService.getQueryResult(conn, "SELECT * FROM `JSP-user` WHERE `screen_name`=?",new String[]{ screenName } );
						if( rs2.next() ) {
							request.setAttribute( Literals.ERROR_MESSAGE, new BeanError( "Already in use", "Your wanted Alias or Screen Name is already in use. Please pick another, try for your own name in one single word perhaps?") );
							request.getRequestDispatcher( "WEB-INF/JSPpro/register.jsp" ).forward( request, response );
						}
						
						if( register == true ) {
							// We had noone with that email, screenName and the passwords matched
							String sqlStmnt = "INSERT INTO `JSP-user` (`id_user`, `email`, `password`, `screen_name`, `show_comments`, `role`, `used_ip`) VALUES (NULL,?,?,?,'Y','blogger',?)";
							String[] options = { login, passwd1, screenName, request.getRemoteAddr() };
							int result = dbService.setDataBaseEntry(conn, sqlStmnt, options );
							try {
								ResultSet rs = dbService.getQueryResult(conn, "SELECT * FROM `JSP-user` WHERE `screen_name`=?", new String[] { screenName } );
								rs.first();
								int idUser = rs.getInt( "id_user" );
								session.setAttribute( Literals.SESSION_USER, new BeanUser( idUser, login, screenName, true, rs.getString( "role" ), request.getRemoteAddr() ) );
								response.sendRedirect( "User" );
							}
							catch( Exception e ) { // Opps it went south anyway...
								request.setAttribute( Literals.ERROR_MESSAGE, new BeanError( "Database Mishap", "Seemed like your email, alias and passwords all matched out but something went wrong anyway. Please try again.") );
								request.getRequestDispatcher( "WEB-INF/JSPpro/register.jsp" ).forward( request, response );
							}
						}
						else {
							System.out.println( "register went false" );
						}
					}
					catch( Exception e ) {
						System.out.println( "Catch:register -> active, bad." );
					}
				}
				else { // Passwords didn't match, redo.
					request.setAttribute( Literals.ERROR_MESSAGE, (new BeanError( "Bad Password", "Your password didn't match. Redo and be sure to type correctly and that capslock is off.") ) );
					request.getRequestDispatcher( "WEB-INF/JSPpro/register.jsp" ).forward( request, response );
					
				}
				
			}// End of IF( Register )
			else {
				// Something went wrong, start page
				response.sendRedirect( "Start" );
			}
		} // End of IF( submit_user )
		else {
			// Assumed you want to login
			request.getRequestDispatcher( "WEB-INF/JSPpro/login.jsp" ).forward( request, response );
		}
	} // End of Method: doPost

} // End of Class: Login
