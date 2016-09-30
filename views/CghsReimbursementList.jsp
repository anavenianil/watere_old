<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : CghsReimbursementList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<script type="text/javascript" src="script/script.js"></script>
<div id="dataTable" class="line">
	<display:table name="${sessionScope.reimbursementMemberList}" excludedParams="*"
		export="false" class="list" requestURI="" id="reimbursement" pagesize="10"
		sort="page" defaultsort="1" defaultorder="descending" >
		<display:column title="Request ID" style="width:7%" sortable="true"><a href="javascript:getRequestDetails('${reimbursement.historyID}','${reimbursement.requestId}','myRequests','pending',' ');" >${reimbursement.requestId} </a></display:column>
		<display:column title="Patient Name" style="width:20%" sortable="true">&nbsp;${reimbursement.relation}</display:column>
		<display:column title="Type" style="width:11%" sortable="true">&nbsp;${reimbursement.cghsRequestTypeId}</display:column>
		<display:column style="width:12%" title="Reimbursement" sortable="true">
		<c:if test="${reimbursement.statusName eq 'ReimReqPending'}">
			<a href="javascript:reimbursementRequest('${reimbursement.requestId}','cghsreimbursement','${reimbursement.cdaAmount}');" title=""
				onclick="" >Reimbursement</a>
		</c:if>
		</display:column>
		<display:column style="width:20%" title="Advance/Settlement" sortable="true">
		<c:if test="${reimbursement.cghsRequestTypeId eq 'Admission'}">
			<c:if test="${reimbursement.statusName eq 'ReimReqPending'}">
				<a href="javascript:reimbursementRequest('${reimbursement.requestId}','cghsAdvance','${reimbursement.cdaAmount}');">Advance</a>
			</c:if> 
			</c:if>
			<c:if test="${reimbursement.cghsRequestTypeId eq 'Admission' || reimbursement.cghsRequestTypeId eq 'Investigation'}">
			<c:if test="${reimbursement.statusName eq 'SettReqPending'}"> 
				<a href="javascript:reimbursementRequest('${reimbursement.requestId}','cghssettlement','${reimbursement.cdaAmount}');" >Settlement</a>
			</c:if>
			</c:if>
	
		</display:column >
		<display:column title="Status" sortable="true" style="width:10%">&nbsp;${reimbursement.statusName}</display:column>
		<display:column title="Applied Date" sortable="true" style="width:10%">&nbsp;${reimbursement.todaysDate}</display:column>
	</display:table>
</div>

	<script>
		$jq( function(){
	 $jq("#dataTable").displayTagAjax('paging');
	})
	</script>


<!-- End : CghsReimbursementList.jsp -->





