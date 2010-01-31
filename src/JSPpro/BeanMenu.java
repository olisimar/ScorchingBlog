package JSPpro;

public class BeanMenu {
	int idUsed;
	int idUser;
	String name;
	String type;
	String cssSelect;
	
	/* * * Constructors * * */
	public BeanMenu() {
		idUsed = 0;
		idUser = 0;
		name = null;
		type = null;
		cssSelect = null;
	}
	
	public BeanMenu( int idUsed, String name, String type, String cssSelect ) {
		this( idUsed, 0, name, type, cssSelect );
	}
	
	public BeanMenu( int idUsed, int idUser, String name, String type, String cssSelect ) {
		this.idUsed = idUsed;
		this.idUser = idUser;
		if( name.length() > 30 ) {
			this.name = name.substring( 0, 27 ) + "...";
		}
		else {
			this.name = name;
		}
		this.type = type;
		this.cssSelect = cssSelect;
	}
	
	/* * * Set methods * * */
	public void setId( int idUsed ) {
		this.idUsed = idUsed;
	}
	public void setUserId( int idUser ) {
		this.idUser = idUser;
	}
	public void setName( String name ) {
		if( name.length() > 30 ) {
			this.name = name.substring( 0, 27 ) + "...";
		}
		else {
			this.name = name;
		}
	}
	public void setType( String type ) {
		this.type = type;
	}
	public void setCssSelect( String cssSelect ) {
		this.cssSelect = cssSelect;
	}
	
	/* * * Get methods * * */
	public int getId() {
		return idUsed;
	}
	public int getUserId() {
		return idUser;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public String getCssSelect() {
		return cssSelect;
	}
} // End of Class: BeanMenu
