<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
		import="JSPpro.*"
		import="java.util.*"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="general.css" type="text/css" media="screen" />
		<title> Scorching Blogs - Login </title>
	</head>
	<body>
		<div id="wrapper">
			<div id="header">
				<jsp:include page="header.jsp" />
			</div>
			<div id="main">
				<div id="login_box">
					<form action="Login" method="post">
						<fieldset>
							<legend> Enter your Alias or Email and Password </legend>
							<label for="login_user"> Email/Alias: </label> <input type="text" name="login_user" />
							<label for="login_password"> Password: </label> <input type="password" name="login_password" />
							<label for="submit_user"> &nbsp; </label> <input type="submit" name="submit_user" value="Login" />
						</fieldset>
					</form>	
					<% if( request.getAttribute( Literals.ERROR_MESSAGE ) != null ) {
							BeanError be = (BeanError)request.getAttribute( Literals.ERROR_MESSAGE );
			   	%>
			   	<div id="error_message">
			   		<h3> <%=be.getType() %></h3>
			   		<p>
			   			<%=be.getMessage() %>
			   		</p>
			   	</div>
			   	<%
						}
					%>
				</div>
			</div> <!-- End of Main -->
			<div id="footer">
				<% pageContext.include( "footer.jsp" ); %>
			</div> <!-- End of Footer -->
		</div>
	</body>
</html>