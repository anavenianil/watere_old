<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.dto.DepartmentsDTO"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>	
<%@ taglib uri="/tags/spring" prefix="spring" %>
<c:if test="${message=='employeeexists' || Result=='employeeexists'}"> <span class="success"><spring:message code="employeeexists"/></span></c:if>
<c:if test="${message=='empNotExists' || Result=='empNotExists'}"> <span class="failure"><spring:message code="empNotExists"/></span></c:if>

<div class="line">
	<script type="text/javascript">
		deptJSON = <%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<DepartmentsDTO>)session.getAttribute("department")) %>;
		getDepartments('${message}');
	</script>
</div>