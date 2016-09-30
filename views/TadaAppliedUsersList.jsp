<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :TadaAppliedUsersList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div>
	<div>
	    <%int i=1; %>
		<div id="Pagination">
			<display:table name="${sessionScope.tdAppliedUsersList}" excludedParams="*"
				export="false" class="list" requestURI="" id="tdAppliedUsersList" pagesize="10000000"
				sort="list">
				<display:column title="Request Id" style="width:10%" sortable="true" >&nbsp;<a href="javascript:getRequestDetails('${tdAppliedUsersList.historyID}','${tdAppliedUsersList.requestId}','myRequests','completed')">${tdAppliedUsersList.requestId}</a></display:column>
				<display:column title="Request Type" style="width:20%"><c:if test="${tdAppliedUsersList.authorizedMove=='1'}">TD Build-Up</c:if>
				                                                             <c:if test="${tdAppliedUsersList.authorizedMove=='2'}">TD Project</c:if>
				                                                             <c:if test="${tdAppliedUsersList.authorizedMove=='47'}">TD Advance</c:if>
				                                                             <c:if test="${tdAppliedUsersList.authorizedMove=='48'}">TD Settlement</c:if>
				                                                             <c:if test="${tdAppliedUsersList.authorizedMove=='49'}">TD Reimbursement</c:if>
				</display:column>
				<display:column title="SFID" style="width:15%">&nbsp;${tdAppliedUsersList.sfID}</display:column>
				<display:column title="Name" style="width:25%">&nbsp;${tdAppliedUsersList.empDetailsList.nameInServiceBook}</display:column>
				<display:column title="Applied Date" style="width:20%">&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdAppliedUsersList.applyDate}"/></display:column>
				<display:column title="Status" style="width:10%"><c:if test="${tdAppliedUsersList.status==2}">Pending</c:if>
				                                                             <c:if test="${tdAppliedUsersList.status==8}">Completed</c:if>
				                                                             <c:if test="${tdAppliedUsersList.status==6}">Declined</c:if>
				                                                             <c:if test="${tdAppliedUsersList.status==9}">Cancelled</c:if>
				                                                             <c:if test="${tdAppliedUsersList.status==103}">Waiting</c:if>
				</display:column>			
				<% i++; %>
			</display:table>
		</div>
		<div class="line">&nbsp;
		<div class="line" style="margin-left: 90%;">
											<div class="expbutton"><a onclick="javascript:getTdAppliedUsersReport();"> <span>Report</span></a></div>
									</div>
		</div>
		<script>
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
		</script>
		
		<script>
requestID = '<%= session.getAttribute("tadaAprovalRequestID") != null ? session.getAttribute("tadaAprovalRequestID") : "" %>';
</script>
<!-- jsonValue   tdAppliedUsersListJson -->
<script>
$jq("#Pagination").displayTagAjax('paging');
				
		var tdAppliedUsersListJson=<%=(net.sf.json.JSONArray)session.getAttribute("tdAppliedUsersListJson") %>;
		</script>

	</div>
</div>
<!-- End : TadaAppliedUsersList.jsp -->