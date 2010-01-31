<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="JSPpro.*"
	import="java.util.*"
%>
<%
	List<BeanMenu> menuList = (List<BeanMenu>)request.getAttribute( Literals.MENU_LIST );
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<link rel="stylesheet" href="general.css" type="text/css" media="screen" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title> Scorching Blogs - Welcome </title>
	</head>
	<body>
		<div id="wrapper">
			<div id="header">
				<jsp:include page="header.jsp" />
			</div> <!-- End of Header -->
			<div id="main">
				
				<div id="menu">
					<% pageContext.include( "face1_menu.jsp" ); %>
				</div> <!-- End of Menu -->
				
				<div id="info">
					<% pageContext.include( "info.jsp" ); %>
				</div> <!-- End of Info -->
			</div>  <!-- End of Main -->
			<div id="footer">
				<% pageContext.include( "footer.jsp" ); %>
			</div> <!-- End of Header -->
		</div>
	</body>
</html>