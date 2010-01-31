<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
		import="JSPpro.*"
		import="java.util.*"
%>
<%
	BeanInfo info = (BeanInfo)request.getAttribute( Literals.INFO_LIST );
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="general.css" type="text/css" media="screen" />
		<title> Scorching Blogs - Write your new blog </title>
	</head>
	<body>
		<div id="wrapper">
			<div id="header">
				<jsp:include page="header.jsp" />
			</div>
			<div id="main">
				
				<form id="comment" action="Blog" method="post">
					<fieldset>
					
						<legend> Please enter your blog </legend>
						
						<label> Title: </label> <input type="text" name="blog_title" value="<%=info.getTitle() %>" />
						<label> Name/Alias: </label> <input type="text" name="blog_alias" value="<%=info.getScreenName() %>" readonly="readonly" />
						<label> Blog: </label>
						<textarea name="blog_text"><%=info.getText() %></textarea>
						<input type="hidden" name="idBlog" value="<%=info.getId() %>"/>
						<input type="hidden" name="idUser" value="<%=info.getUserId() %>" />
						<% if( info.getId() > 0 ) { %>
						<input class="rightedge" type="submit" name="submit_input" value="Update" />
						<% } else { %>
						<input class="rightedge" type="submit" name="submit_input" value="Blog" />
						<% } %>
					</fieldset>
				</form>
				
			</div> <!-- End of Main -->
			<div id="footer">
				<% pageContext.include( "footer.jsp" ); %>
			</div> <!-- End of Footer -->
		</div>
	</body>
</html>