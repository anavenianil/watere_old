<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : nocPassportResult.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>


<script>

$jq('#sfidName').html('${passport.empDetails.nameInServiceBook}');
$jq('#sfidDesig').html('${passport.empDetails.designationDetails.name}');
$jq('#sfidAddress').html('${passport.empAddress}');

familyMembersJson = <%= session.getAttribute("familyMembersJson")!=null ? (net.sf.json.JSONArray)session.getAttribute("familyMembersJson") : null %>;
passPortTypeJson = <%= session.getAttribute("passPortTypeJson")!=null ? (net.sf.json.JSONArray)session.getAttribute("passPortTypeJson") : null %>;
sfidPassPortDetailsJson = <%= session.getAttribute("nocPassportResult")!=null ? (net.sf.json.JSONArray)session.getAttribute("nocPassportResult") : null %>;


<c:if test="${message == 'empNotExists'}">
clearPassPortApplication();
</c:if>

</script>
<div>
<c:if test="${message=='empNotExists' || Result=='empNotExists'}"> <span class="failure"><spring:message code="empNotExists"/></span></c:if>
</div>




<!-- End : nocPassportResult.jsp -->





