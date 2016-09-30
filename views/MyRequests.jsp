<!-- begin:MyRequests.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

  <table width="100%" cellpadding="2" cellspacing="0" align="center" border="1" class="dashboard" bordercolor="#10519a" id="myrequestsTable">
	<tr class="dashboardHeaderMR">
		<th colspan="3" align="center">My Requests</th>
	</tr>
	<tr class="dashboardSubHeaderMR">
	     <th width="30%">Request Id</th>
		<th width="40%">Request</th>
		<th width="30%">Status</th>
		
		<!--<th width="30%">Cancel</th>
	--></tr>
	<tr>
	<td colspan="3" colspan="3" class="restheight"  valign="top" style="height: 300px;">
		<table class="dashboard" width="100%" cellspacing="0" cellpadding="2" bordercolor="#10519a" border="1" align="center">
		<%int i=0; %>
			<c:forEach items="${myRequests}" var="myrequests">
			<%i++ ;%>
				<tr id="myrequestsTr<%=i %>" style="height:25px;cursor: pointer;" onclick="javascript:getRequestDetails('${myrequests.historyID}','${myrequests.requestID}','myRequests','${myrequests.status}')" >
				    <td width="30%" onmouseover="javascript: ysrcp('myrequestsDiv','myrequestsTr<%=i %>');" onmouseout="javascript: ysrcp2('myrequestsDiv','myrequestsTr<%=i %>');">${myrequests.requestID}</td>
					<td width="40%" onmouseover="javascript: ysrcp('myrequestsDiv','myrequestsTr<%=i %>');" onmouseout="javascript: ysrcp2('myrequestsDiv','myrequestsTr<%=i %>');">${myrequests.requestType}</td>
					<td width="30%" onmouseover="javascript: ysrcp('myrequestsDiv','myrequestsTr<%=i %>');" onmouseout="javascript: ysrcp2('myrequestsDiv','myrequestsTr<%=i %>');">${myrequests.status}</td>
					<!--<c:if test="${myrequests.status=='PENDING'}">
						<td width="25%"><input type="button" value="Cancel" onclick="javascript:cancelRequest('${myrequests.requestID}','${myrequests.requestType}','myrequestsDiv')" class="cancelbtn"></input></td>
					</c:if>
					<c:if test="${myrequests.status!='PENDING'}">
						<td width="25%"></td>
					</c:if>
				--></tr>
			</c:forEach>
		</table>
	</td>
	</tr>
	<c:if test="${fn:length(workflowmap.myRequests)==workflowmap.myRequestSize}">
		<tr>
			<td colspan="3" style="text-align:right"><a href="javascript:moreRequests('myrequest')">More...</a></td>
		</tr>																
	</c:if>
</table>
<script>
gridStyle("myrequestsTable");
</script>
<!-- End:MyRequests.jsp -->