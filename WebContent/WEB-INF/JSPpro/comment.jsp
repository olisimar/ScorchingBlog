<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
		import="JSPpro.*"
		import="java.util.*"
%>
<%
	BeanInfo info = (BeanInfo)request.getAttribute( "BeanBlog" );
	BeanUser user = new BeanUser();
	if( session.getAttribute( Literals.SESSION_USER ) != null ) {
		user = (BeanUser)session.getAttribute( Literals.SESSION_USER );
		session.setAttribute( Literals.SESSION_USER, user );
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
			
				<form id="comment" action="Blog" method="post">
					<fieldset>
						<legend> Please enter your comment </legend>
							<label> Title: </label> <input type="text" name="comment_title" />
					<% if( session.getAttribute( Literals.SESSION_USER) != null ) { %>
							<label> Name/Alias: </label> <input type="text" name="comment_alias" value="<%=user.getScreenName() %>" readonly="readonly" />
							<label> Email: </label> <input type="text" name="comment_email" value="<%=user.getEmail() %>" readonly="readonly" />
					<% } else { %>
							<label> Name/Alias: </label> <input type="text" name="comment_alias" value="" />
							<label> Email: </label> <input type="text" name="comment_email" value="" />
					<% } %>
						<label> Comment: </label> <br /> 
						<textarea name="comment_text"></textarea>
						<input type="hidden" name="idBlog" value="<%=info.getId() %>" />
						<input class="rightedge" type="submit" name="submit_input" value="Comment" />
						</fieldset>
				</form>
				
				<div id="cb_info">
					<% pageContext.include( "info.jsp" ); %>
				</div> <!-- End of Info -->
			</div> <!-- End of Main -->
			<div id="footer">
				<% pageContext.include( "footer.jsp" ); %>
			</div> <!-- End of Footer -->
		</div>
	</body>
</html>