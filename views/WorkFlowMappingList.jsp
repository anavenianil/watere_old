<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : WorkFlowMappingList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>


	<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='deleted'}"> <span class="success"><spring:message code="delete"/></span></c:if>
	</div>
<aa:zone name="workFlowListTable">
	<display:table name="sessionScope.reqWorkFlowList" excludedParams="*" export="false" class="list" requestURI=""	id="reqWorkFlowList" pagesize="10" sort="list">
		<display:column title='Workflow Type' style="width:15%">${reqWorkFlowList.workflowType}</display:column>
		<display:column title='Workflow Name' style="width:15%">${reqWorkFlowList.workflowName}</display:column>
		<display:column title='Role/Designation/Organization' style="width:35%">${reqWorkFlowList.roleInstanceName}${reqWorkFlowList.designation}${reqWorkFlowList.organization}</display:column>		
		<display:column title='Request Type' style="width:25%">${reqWorkFlowList.requestName}</display:column>
		
		<display:column title="Edit" style="text-align:center;width:5%"><img src="./images/edit.gif"  title="Edit" onclick="javascript:editReqWorkFlow('${reqWorkFlowList.rowId}','${reqWorkFlowList.requestType}','${reqWorkFlowList.workflow}','${reqWorkFlowList.roleInstanceID}','${reqWorkFlowList.designationID}','${reqWorkFlowList.organizationID}')" /></display:column>
	    <display:column title="Delete" style="text-align:center;width:5%"><img src="./images/delete.gif" title="Delete" onclick="javascript:deleteReqWorkFlow('${reqWorkFlowList.rowId}','${reqWorkFlowList.workflowType}')" /></display:column>
	</display:table>
</aa:zone>
<script>displayPaging("workFlowListTable","reqWorkFlowList");
		clearReqWorkFlowMapping();
</script> 
<!-- End : WorkFlowMappingList.jsp -->