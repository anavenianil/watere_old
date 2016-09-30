	  <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<script>
   catWiseDesigJson= <%=session.getAttribute("catWiseDesigJson")%>;
</script>
	<c:if test="${message=='empNotExists' || Result=='empNotExists'}"> <span class="failure"><spring:message code="empNotExists"/></span></c:if>
	<c:if test="${message=='employeeexists' || Result=='employeeexists'}"> 
 	<div class="leftmar">Name : ${sessionScope.employeeDetails.nameInServiceBook} 
 	&nbsp; &nbsp; &nbsp; &nbsp; Designation : ${sessionScope.employeeDetails.designationDetails.name}</div>
	</c:if>

