<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/jstl-fn" prefix="fn" %>
<style type="text/css">
	a:link {
	}
	a:visited {
	}
	a:hover {
		color: red;
	}
	a:active {
	}
</style>
<div id="Pagination">
	<display:table name="${sessionScope.erpAppliedLeavesList}" excludedParams="*" export="false" class="list" requestURI="" id="leaveList" pagesize="10" sort="list">
			<display:column title="Leave Type" style="width:10%">
				<c:if test="${leaveList.leaveType=='AL'}">Annual Leave</c:if>
				<c:if test="${leaveList.leaveType=='ML'}">Maternity Leave</c:if>
				<c:if test="${leaveList.leaveType=='PL'}">Peternity Leave</c:if>
				<c:if test="${leaveList.leaveType=='SL'}">Sick Leave</c:if>
				<c:if test="${leaveList.leaveType=='CL'}">Compassionate Leave</c:if>
				
				<c:if test="${leaveList.leaveType=='ComL'}">Compassionate Leave</c:if>
			</display:column>
			<display:column title="From Date" style="width:10%">
				<fmt:formatDate pattern="dd-MMM-yyyy" value="${leaveList.fromDate}" />
			</display:column>
			<display:column title="To Date" style="width:10%">
				<fmt:formatDate pattern="dd-MMM-yyyy" value="${leaveList.toDate}" />
			</display:column>
			<display:column title="No.Of Days" style="width:10%">${leaveList.noOfDays}</display:column>
			<display:column title="Applied Date" style="width:10%">
				<fmt:formatDate pattern="dd-MMM-yyyy" value="${leaveList.applyDate}" />
			</display:column>
			<display:column title="Leave Cancel" style="width:10%"><a href="javascript:ERPleaveCancelApplicationForm('${leaveList.noOfDays}','${leaveList.leaveType}','ErpLeaveCancel','${leaveList.requestId}')">Cancel</a></display:column>
			
				<display:column title="Leave Amendment" style="width:10%"><a href="javascript:ERPleaveAmendmentApplicationForm('${leaveList.noOfDays}','${leaveList.leaveType}','ErpLeaveAmendment','${leaveList.requestId}')">Amendment</a></display:column>
				
				<display:column title="Download PDF" style="width:10%"><a href="hello.htm?requestID=${leaveList.requestId}&param=LeaveRequestForm">Download PDF</a></display:column>
			
			
			<!--IF WE ARE USING BELOW LINK DIRECT cANCEL REQUEST WITHOUT WORKFLOW   -->
			<%-- <display:column title="Cancel Leave" style="width:10%"><a href="javascript:ErpLeaveCancel('${leaveList.noOfDays}','${leaveList.leaveType}','ErpLeaveCancel','${leaveList.requestId}')">Cancel1</a></display:column> --%>
	</display:table>
</div>

