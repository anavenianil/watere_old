

<%
	String path=session.getAttribute("path").toString();
	response.sendRedirect(path+"&redirect=true") ;
 %>