<!-- Begin:LoanResult.jsp -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.dto.DesignationDTO"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div class="line">
<c:if test="${message=='loanTypeMaster'}">
<script>
 	jsonSelectedArrayObject =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<DesignationDTO>)session.getAttribute("selectedDesignationsList"))%>;
 	setSelectedDesignations();
</script>

</c:if>

<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
<c:if test="${message=='empNotExists'}"> <span class="failure"><spring:message code="empNotExists"/></span></c:if>
<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
<c:if test="${message=='EXISTS'}"> <span class="failure"><spring:message code="detailsExist"/></span></c:if>
<c:if test="${message=='employeeexists'}"> <span class="success"><spring:message code="employeeexists"/></span></c:if>
<c:if test="${message=='delete'}"> <span class="success"><spring:message code="delete"/></span></c:if>


<script>
requestIDLOAN = '<%= session.getAttribute("LoanRequestIdwork") != null ? session.getAttribute("LoanRequestIdwork") : "" %>';
</script>
</div>
<!-- End:LoanResult.jsp -->