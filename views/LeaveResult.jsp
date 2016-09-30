<!-- Begin: LeaveResult.jsp -->

<%@page import="com.callippus.web.beans.dto.YearTypeDTO"%>
<%@page import="java.util.List"%>
<%@page import="net.sf.json.JSONSerializer"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>

<div>

<c:if test="${message == 'employeeexists' || Result == 'employeeexists'}"> <span class="success"><spring:message code="employeeexists"/></span></c:if>
<c:if test="${message == 'empNotExists' || Result == 'empNotExists'}"> <span class="failure"><spring:message code="empNotExists"/></span></c:if>
<c:if test="${message == 'retired'}"><span class="failure">Employee retired on : ${requestScope.retiredDate}</span></c:if>
<c:if test="${message == 'success'}"><span class="success"></span></c:if>
<c:choose>
	<c:when test="${message == 'holidays'}">${resultMsg}</c:when>
	<c:when test="${message == 'address'}">
		<script>
			addressDetails = <%= (net.sf.json.JSONArray)session.getAttribute("AddressList") %>;
		</script>
	</c:when>
	<c:when test="${message == 'noofdays'}">
		<div id="noDays">${resultMsg}</div>
		<span id="currentNextToken">${requestScope.currentNextToken}</span>
		<span id="futureLeavesToken">${requestScope.futureAvailableLeaves}</span>
		<script>
			jsonCurrentNextToken = '<%= (request.getAttribute("currentNextToken") != null) ? request.getAttribute("currentNextToken").toString() : "" %>';
			jsonFutureLeavesToken = '<%= (request.getAttribute("futureAvailableLeaves") != null) ? request.getAttribute("futureAvailableLeaves").toString() : "" %>';
		</script>
	</c:when>
	<c:when test="${message == 'dataEntry'}">
		<script>
			jsonLeaveSpellsList = <%= (net.sf.json.JSONObject)session.getAttribute("JsonLeaveSpellsList") %>;
		</script>
	</c:when>
	<c:when test="${message == 'request'}">
		<c:choose>
			<c:when test="${resultMsg == 'failure'}"><div class="failure">${remarks}</div></c:when>
			<c:when test="${resultMsg != 'failure'}">
				<c:if test="${remarks == 'success'}"><div class="success">Successfully Sent Leave Request...</div></c:if>
				<c:if test="${remarks != 'success'}">
					<div class="line">
						<div class="line"><div class="failure" id="remarks">${remarks}</div></div>
						<div class="line" id="proceedBtn">
							<div><a href="javascript:cancelRemarks();" class="appbutton" style="text-decoration: none;float:right">Cancel</a></div>
							<div><a href="javascript:proceedLeave();" class="appbutton" style="text-decoration: none;float:right">Proceed</a></div>
						</div>
					</div>
					<script>
						$jq('#applyLeaveBtn').hide();
						jsonPreviousDays = '<%= (request.getAttribute("previousDays") != null) ? (Integer) request.getAttribute("previousDays") : 0 %>';
						jsonNextDays = '<%= (request.getAttribute("nextDays") != null) ? (Integer) request.getAttribute("nextDays") : 0 %>';
						
						if (jsonPreviousDays != 0) {
							$jq('#fromDate').val(formatDateIntoString(addDays($jq('#fromDate').val(), -parseInt(jsonPreviousDays, 10)), 'dd-MMM-yyyy'));
						}
						if (jsonNextDays != 0) {
							$jq('#toDate').val(formatDateIntoString(addDays($jq('#toDate').val(), parseInt(jsonNextDays, 10)), 'dd-MMM-yyyy'));
						}
						$jq('#days').text(parseInt($jq('#days').text(), 10) + parseInt(jsonPreviousDays, 10) + parseInt(jsonNextDays, 10));
						$jq('#fromDate').css('background-color', '#ffffcc');
						$jq('#toDate').css('background-color', '#ffffcc');
						
					</script>
				</c:if>
			</c:when>
		</c:choose>
	</c:when>
	<c:when test="${message == 'cancel'}">
		<c:choose>
			<c:when test="${resultMsg == 'failure'}"><div class="failure">${remarks}</div></c:when>
			<c:when test="${resultMsg == 'success'}"><div class="success">Successfully Sent Request...</div></c:when>
		</c:choose>
	</c:when>
	<c:when test="${message == 'convert'}">
		<c:choose>
			<c:when test="${resultMsg == 'failure'}">
				<div class="line">
					<div class="failure">${remarks}</div>
				</div>
				<div class="line" id="proceedBtn" style="display:none">
					<div><a href="javascript:proceedConvert('${sessionScope.prevHolidays}', '${sessionScope.nextHolidays}');" class="appbutton" style="text-decoration: none;float:right">Proceed</a></div>
					<div><a href="javascript:cancelConvert();" class="appbutton" style="text-decoration: none;float:right">Cancel</a></div>
				</div>
				<script> convertRemarks(); </script>
			</c:when>
			<c:when test="${resultMsg == 'success'}"><div class="success">Successfully Sent Request...</div></c:when>
		</c:choose>
	</c:when>
</c:choose>

<script>

	requestID = '<%= session.getAttribute("RequestIdWork") != null ? session.getAttribute("RequestIdWork") : "" %>';
	requestId = '<%= session.getAttribute("RequestIdLeaveCancel") != null ? session.getAttribute("RequestIdLeaveCancel") : "" %>';
	requestIDcon = '<%= session.getAttribute("RequestIdLeaveConvert") != null ? session.getAttribute("RequestIdLeaveConvert") : "" %>';
	jsonRetirementIntimation = '<%= request.getAttribute("intimation") != null ? (String) request.getAttribute("intimation") : "" %>';
	<% if (request.getAttribute("applicableYears") != null) { %>
		years = <%= (JSONArray) JSONSerializer.toJSON((List<YearTypeDTO>) request.getAttribute("applicableYears")) %>;
	<% } %>
	<%-- <% session.removeAttribute("applicableYears"); %> --%>
	
</script>
</div>

<!-- End: LeaveResult.jsp -->