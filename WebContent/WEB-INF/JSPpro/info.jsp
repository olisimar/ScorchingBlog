<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*"
    import="JSPpro.*"
%>
<%
	List<BeanInfo> info = (List<BeanInfo>)request.getAttribute( Literals.INFO_LIST );
	BeanInfo blog = info.get( 0 );
	info.remove( 0 ); // removes the blog and leaves the comments.
	
	BeanUser user = new BeanUser();
	if( session.getAttribute( Literals.SESSION_USER ) != null ) {
		user = (BeanUser)session.getAttribute( Literals.SESSION_USER );
	}
%>	

<div id="info_blog">
	<div class="blog_head">
		<h2> <%=blog.getTitle() %></h2>
		<span class="info_action">
			<a href="Blog?comment=<%=blog.getId() %>"> Comment</a>
			<% if( blog.getUserId() == user.getId() ) { %>
			<a href="Blog?alter=<%=blog.getId() %>"> Edit </a>
			<% } %>
		</span>
		<span> <%=blog.getTime() %></span>
	</div>
	
	<div class="text">
		<%=blog.getText() %>
	</div>
	
	<div class="authour">
		<span> Written by: <a href="User?user=<%=blog.getUserId()%>"><%=blog.getScreenName() %></a> </span>
	</div>
</div>

<% for( BeanInfo item : info ) { %>
<div class="comment">
	<div class="comment_head">
		<h2> <%=item.getTitle() %> </h2>
		<span class="authour"> Comment by: <%=item.getScreenName() %></span>
		<span class="time"> <%=item.getTime() %></span>
	</div>
	
	<div class="text">
		<%=item.getText() %>
	</div>
	
	<div class="bottom_cap">
	</div>
</div>
<% } %> 
