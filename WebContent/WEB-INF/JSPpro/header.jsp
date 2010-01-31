<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*"
    import="JSPpro.*"
%>
<%
	Boolean loggedIn = false;
	BeanUser user = null;
	if( session.getAttribute( Literals.SESSION_USER ) != null ) {
		user = (BeanUser)session.getAttribute( Literals.SESSION_USER );
		loggedIn = true;
	}
%>
<div id="header_banner">
</div>
<div id="header_menu">
	<ul>
		<li>
			<a href="Start"> First page </a>
		</li>
		<% if( loggedIn ) { %>
		<li>
			<a href="Login?action=logout"> Logout </a>
		</li>
		<li>
			<a href="User?user=<%=user.getId() %>"> My Pages </a>
		</li>
		<% } else { %>
		<li>
			<a href="Login"> Login </a>
		</li>
		<li>
			<a href="Login?action=register"> Register New Account </a>
		</li>
		<% } %>
	</ul>
			<form action="Start" method="post" title="Search for email or screen name of an authour">
				<input type="text" name="search_field" />
				<input type="submit" name="search" value="Search" />
			</form>
</div>