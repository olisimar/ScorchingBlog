package JSPpro;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseObj {
	private final String server;
	private final String user;
	private final String password;
	
	
	/**
	 * Public constructor of a database connection, SQL and all that around.
	 */
	public DataBaseObj() {
		server = "jdbc:mysql://localhost/JSPpro";
		user = "jsp";
		password = "tui98kil";
	}
	
	/**
	 * A custom database object constructor so you can connect to any MySQL 
	 * database you currently use. 
	 * @param server
	 * @param user
	 * @param password
	 */
	public DataBaseObj( String server, String user, String password ) {
		this.server = server;
		this.user = user;
		this.password = password;
	}
	
	/**
	 * This returns a connection object for the use of the one operationg on the
	 * database of their choice.
	 * @return conn
	 */
	public Connection getDBConnection() {
		Connection conn = null;
		 try{
			 conn = DriverManager.getConnection(	server, user,	password	);
			}
		 catch( Exception e ) {
			 e.printStackTrace();
		 }
		return conn;
	}
	
//==========================================================================//
	
	public ResultSet getQueryResult( Connection conn, String selStmt, int[] options ) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement( selStmt );

			if( options.length != 0 ) {
				for( int i=0; i < options.length; i++ ) {
					ps.setInt( i+1, options[ i ] );
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getQueryResult( conn, ps );
		
	}
	
	
	/**
	 * This takes 2 arguments and passes it along to the real query function.
	 * This is a convenience method.
	 * @param conn
	 * @param selStmt
	 * @return ResultSet
	 */
	public ResultSet getQueryResult( Connection conn, String selStmt ) {
		String options[] = {};
		return getQueryResult( conn, selStmt, options );
	}
	/**
	 * This functions properly prepares the PreparedStatement and sends it along
	 * to the real query method to return the ResultSet from there. 
	 * This is a convenience method.
	 * @param conn
	 * @param selStmt
	 * @param options
	 * @return ResultSet
	 */
	public ResultSet getQueryResult( Connection conn, String selStmt, String[] options ) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement( selStmt );

			if( options.length != 0 ) {
				for( int i=0; i < options.length; i++ ) {
					ps.setString( i+1, options[ i ] );
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getQueryResult( conn, ps );
	}


	/**
	 * Takes 2 arguments, transforms it into the SQL query intended and returns
	 * the ResultSet generated.
	 * 
	 * @param conn
	 * @param ps
	 * @return ResultSet
	 */
	public ResultSet getQueryResult( Connection conn, PreparedStatement ps ) {
		// Set when constructed.
		ResultSet rs = null;

		try {
					
			rs = ps.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
	
//==========================================================================//
	/**
	 * This is a helper method taking a Connection object, a String that contains
	 * the sql statement as well as a String[] array with optional data to be
	 * entered into the database.
	 * 
	 * @param conn
	 * @param sqlStmnt
	 * @param options
	 * @return int
	 */
	public int setDataBaseEntry( Connection conn, String sqlStmnt, String[] options ) {

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement( sqlStmnt );
			for( int roll = 0; roll < options.length; roll++ ) {
				ps.setString( roll+1, options[ roll ] );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return setDataBaseEntry( conn, ps );
	}
		
	/**
	 * This is a helper method taking a Connection object, a String that contains
	 * the sql statement as well as a String[] array with optional data to be
	 * entered into the database.
	 * This for insertion of blogs or comments, not updates of blogs.
	 * @param conn
	 * @param sqlStmnt
	 * @param idUser
	 * @param options
	 * @return int
	 */
	public int setDataBaseEntry( Connection conn, String sqlStmnt, int id, String[] options ) {

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement( sqlStmnt );
			ps.setInt( 1, id );
			for( int roll = 0; roll < options.length; roll++ ) {
				ps.setString( roll+2, options[ roll ] ); //+2 due to the setInt before.
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return setDataBaseEntry( conn, ps );
	}
			
/**
 * This takes two options, a connection object as well as a PreparedStatement
 * to execute to the database. This method is be used for INSERT, UPDATE or 
 * DELETE commands. It returns an int as a result to judge the success thereof.
 * If you send along a DELETE directive be sure to include a LIMIT as this
 * method takes no caution in this regard, it trusts you to get it right.
 * @param conn
 * @param ps
 * @return rs
 */
	public int setDataBaseEntry( Connection conn, PreparedStatement ps ) {
		int rs = -100; // Catastrophic failure, my own value.
		
		try {
			
			 rs = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
//==========================================================================//
	public Boolean increaseAddBlog( Connection conn, int[] options ) {
		try {
			PreparedStatement ps = conn.prepareStatement( "UPDATE `JSP-blog` SET `count`=? WHERE `id_blog`=?");
			ps.setInt( 1, options[ 0 ] );
			ps.setInt( 2, options[ 1 ] );
			
			ps.executeUpdate();
			
			return true;
		} catch( Exception e ) {
			
		}
		return false;
	}
	/**
	 * Takes a Connection object to a database and an id of a blog. It pulls that
	 * and all of its comments, turn them into a List of BeanInfo object that
	 * @param conn
	 * @param idBlog
	 * @return info
	 */
	public List<BeanInfo> createInfo( Connection conn, int idBlog ) {
		List<BeanInfo> info = new ArrayList<BeanInfo>();
		
		try {			
			int[] options = { idBlog };
			String selStmt = "SELECT `JSP-blog`.*,`JSP-user`.`screen_name` FROM `JSP-blog` INNER JOIN `JSP-user` ON `JSP-blog`.`id_user`=`JSP-user`.`id_user` WHERE `id_blog`=?";
			ResultSet rs = getQueryResult( conn, selStmt, options );
			Boolean go = rs.first();
			if( go ) {
				String title = rs.getString( "title" );
				String text = rs.getString( "text" );
				Date time = rs.getDate( "time" );
				int[] idUser = { rs.getInt( "id_user" ) };
				String screenName = rs.getString( "screen_name" );
				
				// Updates the count of the blog, this is async so it might miss the odd
				// read but it should make it more even and slightly slower to update.
				int[] count = { rs.getInt( "count" )+1, idBlog };
				increaseAddBlog( conn, count );

				info.add( new BeanInfo( idBlog, idUser[0], screenName, Literals.TYPE_BLOG, title, text, time ) );

				selStmt = "SELECT * FROM `JSP-comment` WHERE `id_blog`=? ORDER BY `time` ASC";
				rs = getQueryResult( conn, selStmt, options );

				if( rs != null ) {
					while( rs.next() ) {
						int id = rs.getInt( "id_comment" );
						screenName = rs.getString( "screen_name" );
						title = rs.getString( "title" );
						text = rs.getString( "text" );
						time = rs.getDate( "time" );
						info.add( new BeanInfo( id, screenName, Literals.TYPE_COMMENT, title, text, time ) );
					}
				}
			}
			else {
				info.add( new BeanInfo( 0, "Failure inside TRY:createInfo", Literals.TYPE_BLOG, "Failure", "Failure", new Date() ) );
			}
		} catch( Exception e ) {
			info.add( new BeanInfo( 0, "Failure in CATCH:createInfo", Literals.TYPE_BLOG, "Failure", "Failure", new Date() ) );
			e.printStackTrace();
		}
		
		return info;
		
	} // End of Method: createInfo

//==========================================================================//
	/**
	 * Closes an open DataBase Connection.
	 * @param conn
	 * @return Boolean
	 */
	public Boolean closeConnection( Connection conn ) {
		try {
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false; // Just to make sure, at least the close() failed.
	}
}
