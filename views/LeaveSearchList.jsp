<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : LeaveSearchList.jsp -->
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
	<display:table name="${sessionScope.appliedLeavesList}" excludedParams="*" export="false" class="list" requestURI="" id="leaveList" pagesize="10" sort="list">
		<display:column title="Request ID" style="width: 7%;text-align: right;">${leaveList.requestID}&nbsp;</display:column>
		<display:column title="Nature of leave" style="width:25%">${leaveList.leaveType}</display:column>
		<display:column title="From Date" style="width:10%">${leaveList.fromDate}</display:column>
		<display:column title="To Date" style="width:10%">${leaveList.toDate}</display:column>
		<display:column title="No of leaves" style="width:10%;text-align:right"><c:out value="${leaveList.noOfDays}"/></display:column>
		<display:column title="Cancellation" style="width:10%;text-align: center;">			
			<%--<c:if test="${leaveList.leaveCancellation!='N'}">
				<a href="javascript:getLeaveDetails('cancel','${leaveList.requestID}','${leaveList.requestStage}','${leaveList.historyID}')">Cancel</a>
			</c:if>--%>
			<c:if test="${leaveList.status == '29' || leaveList.status == '8'}">
				<a href="javascript:getLeaveDetails('cancel', '${leaveList.requestID}', '${leaveList.requestStage}', '${leaveList.historyID}')">Cancel</a>
			</c:if>
		</display:column>
		<display:column title="Conversion" style="width:10%;text-align: center;">				
			<c:if test="${leaveList.leaveConversion != 'N'}">
				<c:if test="${leaveList.status == '29' || leaveList.status == '8'}">
					<a href="javascript:getLeaveDetails('convert', '${leaveList.requestID}', '', '${leaveList.historyID}')">Convert</a>
				</c:if>
			</c:if>
		</display:column>
		<display:column title="Amendment" style="width: 10%;text-align: center;">
			<c:if test="${leaveList.status == '29' || leaveList.status == '8'}">		
				<c:if test="${leaveList.otherRemarks ne null}">
					<c:set var="remarksText" value="${leaveList.otherRemarks}" />
					<c:choose>
						<c:when test="${fn:contains(remarksText, 'amendment')}"><font color="grey">Not possible!</font></c:when>
						<c:otherwise><a href="javascript:leaveAmendment('${leaveList.requestID}', '${leaveList.leaveCode}', 'LEAVE')">Amendment</a></c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${leaveList.otherRemarks eq null}">
					<a href="javascript:leaveAmendment('${leaveList.requestID}', '${leaveList.leaveCode}', 'LEAVE')">Amendment</a>
				</c:if>
			</c:if>
		</display:column>			
		<display:column title="Report" style="width:8%;text-align: center;">
			<c:if test="${leaveList.status=='29' || leaveList.status=='8'}">
				<a href="javascript:leaveApplication('${leaveList.requestID}', '${leaveList.leaveCode}', 'LEAVE')">PDF</a>
			</c:if>
		</display:column>				
	</display:table>
	<script>
			$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
	<div class="line" style="color:red">
		Note: 1. Leave Cancellation / Conversion can not be possible after completion of <u style="color: #ff00ff">30 days</u> period of the relevant spell of leave.<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2. Amendment is possible for only <u style="color: #ff00ff">once</u>.
	</div>
</div>


<!-- End : LeaveSearchList.jsp -->
