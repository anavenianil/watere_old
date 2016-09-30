<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : RequestWorkflowList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/aa.js"></script>


<div>
	<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='deleted'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
	</div>
	<div>
		<aa:zone name="workflowTable">
			<display:table name="${sessionScope.allList}" excludedParams="*"
				export="false" class="list" requestURI="" id="work" pagesize="5"
				sort="list">
				<display:column title="Instance Name" style="width:30%">${work.instanceName}</display:column>
				<display:column title="Request Name" style="width:30%">${work.requestName}</display:column>
				<display:column title="WorkFlow Name" style="width:15%">${work.workflowName}</display:column>
				
				<display:column title="EDIT" style="width:5%">
					<img src="./images/edit.gif" title="Edit"
						onclick="javascript:editWorkflowRequest('${work.rowId}','${work.instanceId}','${work.requestType}','${work.workflow}')" />
				</display:column>
				<display:column title="DELETE" style="width:5%">
					<img src="./images/delete.gif" title="Delete"
						onclick="javascript:deleteWorkflowRequest('${work.rowId}')" />
				</display:column>
			</display:table>
		</aa:zone>
		<script>
			displayPaging("workflowTable","work");
			clearworkflow();
			
		</script>
	</div>
</div>







<!-- End : RequestWorkflowList.jsp -->