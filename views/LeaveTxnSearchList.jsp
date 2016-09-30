<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : LeaveTxnSearchList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>

<div id="Pagination">
	<display:table name="${sessionScope.leaveTxnList}" excludedParams="*" export="false" class="list" requestURI="" id="leave" pagesize="10" sort="list">
	    <display:column title="SFID" style="width:10%">${leave.searchSfid}</display:column>
		<display:column title="Emp Name" style="width:20%">${leave.empName}</display:column>
		<display:column title="Designation" style="width:20%">${leave.designation}</display:column>
		<display:column title="Leave Type" style="width:20%">${leave.leaveType}</display:column>
		<display:column title="Txn From Date" style="width:10%">${leave.txnFromDate}</display:column>
		<display:column title="Txn To Date" style="width:10%">${leave.txnToDate}</display:column>
		<display:column title="No.of Leaves" style="width:10%">${leave.noOfLeaves}</display:column>
		
		<display:column title="Status" style="width:10%">${leave.status}</display:column>
		<display:column title="DoPart Number" style="width:10%">${leave.doPartNo}</display:column>
		<display:column title="RequestId" style="width:10%">${leave.requestID}</display:column>
	</display:table>	
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End : LeaveTxnSearchList.jsp-->