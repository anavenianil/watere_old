<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--Begin: IONInitiatedRoleList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/spring-form" prefix="form"%>

	<c:if test="${sessionScope.getIONRoleList eq 'getIONInitiatedRoleList'}">
	<script>
	jsonIONInitiatedRoleList= <%= (net.sf.json.JSONArray)session.getAttribute("jsonIONInitiatedRoleList") %>;
	setInitiatedRoleList(jsonIONInitiatedRoleList);
   </script>
   </c:if>
   <c:if test="${sessionScope.getIONRoleList eq 'getIONForwardRoleList'}">
	<script>
	jsonIONForwardRoleList= <%= (net.sf.json.JSONArray)session.getAttribute("jsonIONForwardRoleList") %>;
	setForwardRoleList(jsonIONForwardRoleList);
   </script>
   </c:if>
    <c:if test="${sessionScope.getIONRoleList eq 'getIONApprovedRoleList'}">
	<script>
	jsonIONApprovedRoleList= <%= (net.sf.json.JSONArray)session.getAttribute("jsonIONApprovedRoleList") %>;
	setApprovedRoleList(jsonIONApprovedRoleList);
   </script>
   </c:if>
   <c:if test="${sessionScope.getIONRoleList eq 'getEncloserFileList'}">
	<script>
	jsonfileEncloserList= <%= (net.sf.json.JSONArray)session.getAttribute("jsonfileEncloserList") %>;
	createEncloserList(jsonfileEncloserList);
   </script>
   </c:if>
	
	
<!--End: IONInitiatedRoleList.jsp -->