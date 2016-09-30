<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LeaveConversionDetails.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div>
	<table width="100%" cellpadding="3" cellspacing="0" align="center" border="1" class="sub_2">
		<tr>
			<th width="8%">Request ID</th>
			<th width="20%">Nature of leave</th>
			<th width="8%">From Date</th>
			<th width="8%">To Date</th>
			<th width="8%">No of leaves</th>
			<th width="24%">Prefixed</th>
			<th width="24%">Suffixed</th>
		</tr>
		<c:forEach var="leaves" items="${workflowmap.conversionDetails}">
			<tr>
				<td style="text-align: center;">${leaves.newLeaveRequestID}</td>
				<td style="text-align: center;">${leaves.leaveTypeDetails.leaveType}</td>				
				<td><fmt:formatDate pattern="dd-MMM-yyyy" value="${leaves.formattedFromDate}" /></td>
				<td><fmt:formatDate pattern="dd-MMM-yyyy" value="${leaves.formattedToDate}" /></td>
				<td style="text-align:right">${leaves.noOfDays}</td>
				<td>${leaves.prefix}</td>
				<td>${leaves.suffix}</td>
			</tr>
		</c:forEach>
	</table>
</div>
<!-- End:LeaveConversionDetails.jsp -->	