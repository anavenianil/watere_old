<!-- begin:MyAlerts.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<table width="100%" cellpadding="2" cellspacing="0" align="center" border="1" class="dashboard" bordercolor="#10519a" id="myAlertsTable" >
	<tr class="dashboardHeaderMRyell">
		<th colspan="3" align="center">My Alerts</th>
	</tr>
	<tr class="dashboardSubHeaderMRyell">
		<th width="60%">Alert</th>
		<th width="40%">Date</th>
	</tr>
	<tr height="10">
		<td colspan="3" colspan="3" class="restheight"  valign="top" style="height:300px;">
			<table width="100%" cellpadding="2" cellspacing="0" align="center"  class="dashboard" border="1"  >
			<%int i=0; %>
				<c:forEach items="${workflowmap.myAlerts}" var="myalerts" >
					<%i++ ;%>
					<tr id="myAlertsTr<%=i %>" style="height:25px; cursor: pointer;" onclick="javascript:getAlertDetails('${myalerts.id}')" >
						<td width="60%" onmouseover="javascript: ysrcp('myAlertsDiv','myAlertsTr<%=i %>');" onmouseout="javascript: ysrcp2('myAlertsDiv','myAlertsTr<%=i %>');">${myalerts.alertDetails.alert}</td>
						<td width="40%" onmouseover="javascript: ysrcp('myAlertsDiv','myAlertsTr<%=i %>');" onmouseout="javascript: ysrcp2('myAlertsDiv','myAlertsTr<%=i %>');"><fmt:formatDate pattern="dd-MMM-yyyy" value="${myalerts.assignedDate}"/></td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>	
	<c:if test="${fn:length(workflowmap.myAlerts) >= workflowmap.myalertSize}">
		<tr>
			<td colspan="3" style="text-align:right"><a href="javascript:moreAlerts('alerts')">More...</a></td>
		</tr>																
	</c:if>
</table>
<script>
gridStyle("myAlertsTable");
</script>
<!-- End:MyAlerts.jsp -->