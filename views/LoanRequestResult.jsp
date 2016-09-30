<!-- Begin:LoanRequestResult.jsp -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div class="line">
<c:if test="${Result=='failure'}">  <span class="failure">${remarks}</span></c:if>
<c:if test="${Result=='success'}"> <span class="success"><spring:message code="success"/></span> </c:if>
<script>
requestIDLOAN = '<%= session.getAttribute("LoanRequestIdwork") != null ? session.getAttribute("LoanRequestIdwork") : "" %>';
</script>

<script>
requestIDHBALOAN = '<%= session.getAttribute("LoanHbaRequestIdwork") != null ? session.getAttribute("LoanHbaRequestIdwork") : "" %>';
</script>
<script>
requestIDFpa = '<%= session.getAttribute("fpaRequestID") != null ? session.getAttribute("fpaRequestID") : "" %>';
</script>


</div>
<!-- End:LoanRequestResult.jsp -->