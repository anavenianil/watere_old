<!-- Start:error.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html>
	<body>
	<h2>	
		<c:if test="${exception.errorCode ne null}" >
		<c:if test="${exception.errorCode eq 'SessionExpExcep'}">	
			<%response.sendRedirect("secure/index.jsp"); %>
		</c:if>
		<c:if test="${exception.errorCode ne 'SessionExpExcep'}">			
			<spring:message code="${exception.errorCode}"/>
		</c:if>
		
	</c:if>
	</h2>
	</body>
	
</html>
<!-- End:error.jsp -->