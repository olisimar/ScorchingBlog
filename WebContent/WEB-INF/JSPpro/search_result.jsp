<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
		import="JSPpro.*"
		import="java.util.*"
%>
<%
	BeanMenu info = null;
	BeanError error = null;
	if( request.getAttribute( Literals.TYPE_USER ) != null ) {
		info = (BeanMenu)request.getAttribute( Literals.TYPE_USER );
	}
	if( request.getAttribute( Literals.ERROR_MESSAGE ) != null ) {
		error = (BeanError)request.getAttribute( Literals.ERROR_MESSAGE );
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="general.css" type="text/css" media="screen" />
		<title> Scorching Blogs - Comment on a Blog </title>
	</head>
	<body>
		<div id="wrapper">
			<div id="header">
				<jsp:include page="header.jsp" />
			</div>
			
			<div id="main">
				<% if( request.getAttribute( Literals.TYPE_USER ) != null ) {	%>
				<div id="search">
					<p>
						Found one user with that Alias/Email. Follow the link to the users
						blogs. If this is not the user you please search again.
					</p>
					<a href="User?user=<%=info.getId() %>"> <%=info.getName() %> </a>
				</div>
				<% } if( request.getAttribute( Literals.ERROR_MESSAGE ) != null ) {	%>
				<div id="error">
			  	<div id="error_message">
			  		<h3> <%=error.getType() %> </h3>
			  		<p>
			  			<%=error.getMessage() %>
			  		</p>
			  	</div>
			  </div>
				<% } %>
			</div> <!-- End of Main -->
			
			<div id="footer">
				<% pageContext.include( "footer.jsp" ); %>
			</div> <!-- End of Footer -->
		</div>
	</body>
</html>