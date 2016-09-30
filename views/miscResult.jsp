<!-- Begin: Result.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>


<c:if test="${message=='success' }">
	<script>
	
		var gazType = '${message}';
		var adminmisctype='<c:out value='${sessionScope.adminMiscType}'/>';	
		var idJson='<c:out value='${sessionScope.idGenerator}'/>'; 	
		getAdminMiscReport1(adminmisctype,idJson,gazType);
	</script>
</c:if>

<!-- End: Result.jsp -->