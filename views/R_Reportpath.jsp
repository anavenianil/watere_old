
<%@ taglib uri="/tags/spring-form" prefix="form" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%
String pdfpath =(String)session.getAttribute("reportpath");
              System.out.println(pdfpath);              
              response.sendRedirect(pdfpath);
%>