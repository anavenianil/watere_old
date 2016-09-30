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
	<display:table name="${sessionScope.erpAppliedLeavesList}" style="width:100%;" excludedParams="*" export="false" class="list" requestURI="" id="leaveList" pagesize="10" sort="list">
			<display:column title="Leave Type" style="width:23%">
				&emsp;&emsp;&emsp;&emsp;<c:if test="${leaveList.leaveType=='AL'}">Annual Leave</c:if>
				<c:if test="${leaveList.leaveType=='ML'}">Maternity Leave</c:if>
				<c:if test="${leaveList.leaveType=='PL'}">Peternity Leave</c:if>
				<c:if test="${leaveList.leaveType=='SL'}">Sick Leave</c:if>
				<c:if test="${leaveList.leaveType=='CL'}">Compassionate Leave</c:if>
			</display:column>
			<display:column title="From Date"  style="width:23%" >
				&emsp;&emsp;&emsp;&emsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${leaveList.fromDate}" />
			</display:column>
			<display:column title="To Date"  style="width:23%">
				&emsp;&emsp;&emsp;&emsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${leaveList.toDate}" />
			</display:column>
			<display:column title="No.Of Days"  style="width:20%">&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;${leaveList.noOfDays}</display:column>
		
		
		
			<display:column title="Applied Date"   style="width:25%">
			<fmt:formatDate pattern="dd-MMM-yyyy" value="${leaveList.applyDate}" />
			</display:column>
			<display:column title=""  style="width:23%">
				&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
			</display:column>
	</display:table>
</div>

