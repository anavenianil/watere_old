<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : LeavesMappingList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<div>
	<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
	</div>
	<div>
		<aa:zone name="leaveTable">
			<display:table name="${sessionScope.leaveMappingList}" excludedParams="*"
				export="false" class="list" requestURI="" id="leave" pagesize="10"
				sort="list">
				<display:column title="Nature of leave1" style="width:25%">${leave.leaveTypeDetails.name}</display:column>
				<display:column title="Nature of leave2" style="width:25%">${leave.leaveTypeDetails2.name}</display:column>
				<display:column title="Description" style="width:40%">${leave.description}</display:column>
				
				<display:column title="Edit" style="width:5%">
					<img src="./images/edit.gif" title="Edit"
						onclick="javascript:editLeaveMapping('${leave.id}','${leave.leaveTypeDetails.id}','${leave.leaveTypeDetails2.id}','${leave.description}')"/>
				</display:column>
				<display:column title="Delete" style="width:5%">
					<img src="./images/delete.gif" title="Delete"
						onclick="javascript:deleteLeaveMapping('${leave.id}')" />
				</display:column>
			</display:table>
		</aa:zone>
		<script>
			displayPaging("leaveTable","leave");
		</script>
	</div>
</div>
<!-- End : LeavesMappingList.jsp -->