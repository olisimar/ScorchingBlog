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
		<title> Scorching Blogs - Register </title>
	</head>
	<body>
		<div id="wrapper">
			<div id="header">
				<jsp:include page="header.jsp" />
			</div>
			<div id="main">
				<ol style="circles">
					<li>
						The information you enter here are all subject to swedish
						computer laws.
					</li>
					<li>
						Your personal information won't be used aside from what is needed
						in the running of this site. This mean your email won't be sold or
						shared to other entities.	
					<li>
					<li>
						Your current IP will be logged on every comment and blog you submit. 
					</li>	
					<li>
						If you are promoting illegal or dangerous behavior this will be
						submitted to the concerned authorities.
					</li>
				</ol>
				<div id="register_box">
					<form action="Login" method="post">
						<fieldset>
							<legend> Please fill all of the fields below </legend>
							<label for="login"> Email: </label> <input type="text" name="reg_email" />
							<label for="login"> Alias: </label> <input type="text" name="reg_screen" />
							<label for="password"> Password: </label> <input type="password" name="reg_password1" />
							<label for="password"> Password: </label> <input type="password" name="reg_password2" />
							<label> &nbsp; </label> <input type="submit" name="submit_user" value="Register" />
						</fieldset>
					</form>	
				</div>
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
			</div> <!-- End of Main -->
			<div id="footer">
				<% pageContext.include( "footer.jsp" ); %>
			</div> <!-- End of Footer -->
		</div>
	</body>
</html>