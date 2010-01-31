<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="JSPpro.*"
	import="java.util.*"
%>
<%
	List<BeanMenu> menu = (List<BeanMenu>)request.getAttribute( Literals.MENU_LIST );
	List<BeanInfo> info = (List<BeanInfo>)request.getAttribute( Literals.INFO_LIST );
	BeanInfo blog = info.get( 0 );
	String authour = blog.getScreenName();

	BeanUser user = (BeanUser) session.getAttribute( Literals.SESSION_USER );
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<link rel="stylesheet" href="general.css" type="text/css" media="screen" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title> Scorching Blogs - <%=authour %>'s blogs </title>
	</head>
	<body>
		<div id="wrapper">
			<div id="header">
				<jsp:include page="header.jsp" />
			</div> <!-- End of Header -->
			
			<div id="main">
				<div id="menu">
					<ul>
						<li	class="top_of_blog">
						<% if( user != null) { %>
							<a href="Blog"> Create New Blog </a>
						<% } else { %>
							<a href="Login"> Login To Blog </a>
						<% } %>
						</li>
						<% for( BeanMenu bm : menu ) { %>
						<li class="<%=bm.getCssSelect() %>">
							<a href="User?user=<%=bm.getUserId() %>&blog=<%=bm.getId() %>"> <%=bm.getName() %> </a>
						</li>
						<% } %>
						<li class="menu_bottom">
						</li>
					</ul>
						
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