<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : NormalLeaveList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<div class="line">
	<div class="line">
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='deleted'}"> <span class="success"><spring:message code="delete"/></span></c:if>
	</div>
	<div class="line">
		<aa:zone name="leaveTable">
			<display:table name="${sessionScope.NormalLeavesList}" excludedParams="*"
				export="false" class="list" requestURI="" id="leave" pagesize="20"
				sort="list">
				<display:column title="Nature of leave" style="width:20%">${leave.leaveTypeDetails.name}</display:column>
				<display:column title="Duration" style="width:5%;text-align:right">${leave.duration}</display:column>
				<display:column title="Duration Type" style="width:10%">${leave.leaveDurationDetails.name}</display:column>
				<display:column title="No of Days" style="width:5%;text-align:right">${leave.noOfLeaves}</display:column>
				<display:column title="Max Leaves" style="width:10%;text-align:right">${leave.maxLeaves}</display:column>
				<display:column title="Max Cont Leaves" style="width:10%;text-align:right">${leave.maxContLeaves}</display:column>
				<display:column title="New Emp Avail Leaves" style="width:10%;text-align:right">${leave.newEmpLeaves}</display:column>
				<display:column title="Medical Certificate" style="width:10%;">
					<c:if test="${leave.medicalFlag=='Y'}"><spring:message code="yes"/></c:if>
					<c:if test="${leave.medicalFlag=='N'}"><spring:message code="no"/></c:if>
				</display:column>
				<display:column title="Gender" style="width:10%;">
					<c:if test="${leave.gender=='B'}"><spring:message code="both"/></c:if>
					<c:if test="${leave.gender=='M'}"><spring:message code="male"/></c:if>
					<c:if test="${leave.gender=='F'}"><spring:message code="female"/></c:if>
				</display:column>
				<display:column title="Edit" style="width:5%">
					<img src="./images/edit.gif" title="Edit"
						onclick="javascript:editLeaveManagement('${leave.leaveTypeDetails.id}')"/>
				</display:column>
				<display:column title="Delete" style="width:5%">
					<img src="./images/delete.gif" title="Delete"
						onclick="javascript:deleteLeaveManagement('${leave.id}')" />
				</display:column>
			</display:table>
		</aa:zone>
		<script>
			displayPaging("leaveTable","leave");
			normalLeavesJSONList = <%= (net.sf.json.JSONArray)session.getAttribute("NormalLeavesJSONList") %>;
		</script>
	</div>
</div>
<!-- End : NormalLeaveList.jsp -->