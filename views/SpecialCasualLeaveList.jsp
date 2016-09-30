<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : SpecialCasualLeaveList.jsp -->
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
		<aa:zone name="sclTable">
			<display:table name="${sessionScope.SPLeaveList}" excludedParams="*"
				export="false" class="list" requestURI="" id="scl" pagesize="10"
				sort="list">
				<display:column title="Nature of leave" style="width:30%">${scl.leaveTypeDetails.name}</display:column>
				<display:column title="Gender" style="width:10%">
					<c:if test="${scl.gender=='B'}"><spring:message code="both"/></c:if>
					<c:if test="${scl.gender=='M'}"><spring:message code="male"/></c:if>
					<c:if test="${scl.gender=='F'}"><spring:message code="female"/></c:if>
				</display:column>				
				<display:column title="No of Days" style="width:10%;text-align:right">${scl.noOfDays}</display:column>
				<display:column title="Remarks" style="width:40%">${scl.remarks}</display:column>
				<display:column title="Edit" style="width:5%;text-align:center">
					<img src="./images/edit.gif" title="Edit"
						onclick="javascript:editSpecialCasualLeave('${scl.leaveTypeDetails.id}','${scl.gender}','${scl.noOfDays}','${scl.remarks}')" />
				</display:column>
				<display:column title="Delete" style="width:5%;text-align:center">
					<img src="./images/delete.gif" title="Delete"
						onclick="javascript:deleteSpecialCasualLeave('${scl.id}')" />
				</display:column>
			</display:table>
		</aa:zone>
		<script>
			displayPaging("sclTable","scl");
		</script>
	</div>
</div>
<!-- End : SpecialCasualLeaveList.jsp -->