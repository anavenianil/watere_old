<!-- Begin : RequestHistoryDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<div>
	<div class="headTitle_sub2" >Status Details</div>
	<div class="line">
		<table width="100%" cellpadding="2" cellspacing="0" border="1" class="sub_2">
			<tr>
				<th width="15%">From</th>
				<th width="30%">To</th>	
				<th width="12%">Action taken on</th>
				<th width="23%">Remarks</th>
				<th width="10%">Status</th>
				<th width="10%">IP Address</th>
			</tr>
			<c:forEach items="${statusHistory}" var="history">
				<tr>
				
					<td>&nbsp;${history.assignedFrom}</td>
					<td>&nbsp;${history.assignedTo} (${history.roleName}) (${history.parentID}) </td>
					<td>&nbsp;${history.actionedDate}</td>
					<td>&nbsp;${history.remarks}</td>
					<td>&nbsp;${history.status}</td>
					<td>&nbsp;${history.ipAddress}</td>											
				</tr>
			</c:forEach>
		</table>							
	</div>
</div>
<div>
<c:if test="${not empty leaveCancelDetails}">
	<script>
		cancelLeaveRequest('${leaveCancelDetails.key}', '${leaveCancelDetails.value}');
	</script>
</c:if>
</div>
<script>
	displayResult('${message}', '${remarks}');
	setDetails('${message}');
</script>
<!-- End : RequestHistoryDetails.jsp -->