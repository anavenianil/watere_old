<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:DashBoardRequestList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div id="Pagination">
	<display:table name="${sessionScope.dashBoradList}" excludedParams="*"
		export="false" class="list" requestURI="" id="allRequest" pagesize="10"
		sort="list">
		<c:if test="${workflowmap.type eq 'myrequest'}">
			<display:column title="Request ID" style="width:20%;">&nbsp;<a href="javascript:getRequestDetails('${allRequest.historyID}','${allRequest.requestID}','${type}','','home')">${allRequest.requestID}</a></display:column>
			<display:column title="Request" style="width:20%;"><%-- <a href="javascript:getRequestDetails('${allRequest.historyID}','${allRequest.requestID}','myRequests','${allRequest.status}','home')">${allRequest.requestType}</a> --%>${allRequest.requestType}</display:column>
			<display:column title="Status" style="width:20%;">${allRequest.status}</display:column>
		</c:if>
		<c:if test="${workflowmap.type eq 'hold'}">
		    <display:column title="Request ID" style="width:20%;">&nbsp;<a href="javascript:getRequestDetails('${allRequest.historyID}','${allRequest.requestID}','${type}','','home')">${allRequest.requestID}</a></display:column>
			<display:column title="RequestType" style="width:20%;">${allRequest.requestType}</display:column>
			<display:column title="Remarks" style="width:20%;">${allRequest.remarks}</display:column>
			<display:column title="Holding From" style="width:20%;">${allRequest.actionedDate}</display:column>
		</c:if>
		<c:if test="${workflowmap.type eq 'TotalHold'}">
		    <display:column title="Request ID" style="width:20%;">&nbsp;<a href="javascript:getRequestDetails('${allRequest.historyID}','${allRequest.requestID}','${type}','','home')">${allRequest.requestID}</a></display:column>
			<%--<display:column title="Requester" style="width:20%;">&nbsp;${allRequest.requester}</display:column>
			<display:column title="Request" style="width:20%;">${allRequest.requestType}</display:column>
			<display:column title="Date" style="width:20%;">&nbsp;${allRequest.assignedDate}</display:column>--%>
			<display:column title="Remarks" style="width:20%;">${allRequest.remarks}</display:column>
		</c:if>
		<c:if test="${workflowmap.type ne 'myrequest' && workflowmap.type ne 'hold' && workflowmap.type ne 'TotalHold'}">
		    <display:column title="Request ID" style="width:15%;">&nbsp;<a href="javascript:getRequestDetails('${allRequest.historyID}','${allRequest.requestID}','${type}','','home')">${allRequest.requestID}</a></display:column>
			<display:column title="Requester" style="width:35%;">&nbsp;${allRequest.requester}</display:column>
			<display:column title="Request" style="width:25%;">${allRequest.requestType}</display:column>
			<display:column title="Date" style="width:15%;">&nbsp;${allRequest.assignedDate}</display:column>
		</c:if>
	</display:table>
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- end:DashBoardRequestList.jsp -->
