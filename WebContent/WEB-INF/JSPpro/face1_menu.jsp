<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*"
    import="JSPpro.*"
%>
<%
	List<BeanMenu> lastBlogs = (List<BeanMenu>)request.getAttribute( Literals.LAST_LIST );
	List<BeanMenu> topBlogs = (List<BeanMenu>)request.getAttribute( Literals.MENU_LIST );
%>
<div id="last_menu">
	<ul>
		<li class="menu_top">
			Last Blogs Submitted
		</li>
		<% for(BeanMenu bm : lastBlogs ) { %>
		<li class="<%=bm.getCssSelect() %>">
			<a href="Start?<%=bm.getType() %>=<%=bm.getId() %>" > <%=bm.getName() %></a>
		</li>
		<% } %>
		<li class="menu_bottom">
		</li>
	</ul>
</div>
<div id="top_menu">
	<ul>
		<li class="menu_top">
			Most Read Blogs
		</li>
		<% for(BeanMenu tm : topBlogs ) { %>
		<li class="<%=tm.getCssSelect() %>">
			<a href="Start?<%=tm.getType() %>=<%=tm.getId() %>" > <%=tm.getName() %></a>
		</li>
		<% } %>
		<li class="menu_bottom">
		</li>
	</ul>
</div>