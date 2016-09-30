<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin: quarterOfflineEntry.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<script>
	$jq('#sfidName').html('${quarterRequest.employeeDetails.nameInServiceBook}');
	$jq('#sfidDesignation').html('${quarterRequest.employeeDetails.designationDetails.name}');
	$jq('#sfidDOA').html('<fmt:formatDate pattern="dd-MMM-yyyy" value="${quarterRequest.employeeDetails.dojDrdoInFormat}" />');
		showQuarterStatus('${quarterRequest.result}');	
	quarterEligibles = <%= session.getAttribute("quarterListJson") != null ? (net.sf.json.JSONArray)session.getAttribute("quarterListJson") : null %>;
	<c:if test="${message ne 'empNotExists'}"> 
		showQuarterEligibles();
	</c:if>
</script>
<div>
<c:if test="${message=='empNotExists' || Result=='empNotExists'}"> <span class="failure"><spring:message code="empNotExists"/></span>
	<script>
	//clearQuarterRequest();
	</script>
</c:if>
</div>

<!-- End:quarterOfflineEntry.jsp -->