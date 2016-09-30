<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : UserSpecificList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<script type="text/javascript" src="script/aa.js"></script>


<div>
	<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='deleted'}"> <span class="success"><spring:message code="delete"/></span></c:if>
	</div>
	<div>
		<aa:zone name="userspecificTable">
			<display:table name="${workflowmap.allList}" excludedParams="*"
				export="false" class="list" requestURI="" id="work" pagesize="10"
				sort="list">
				<display:column title="Role Name" style="width:15%">${work.instanceName}</display:column>
				<display:column title="Request Type" style="width:15%">${work.requestType}</display:column>
				<display:column title="Delegated Employee" style="width:20%">${work.empName}</display:column>
				<display:column title="Assigned Type" style="width:10%">
					<c:if test="${work.delegateType=='10'}">Manual</c:if>
					<c:if test="${work.delegateType=='11'}">Automatic</c:if>
					<c:if test="${work.delegateType=='32'}">On Leave</c:if>
				</display:column>
				<display:column title="Gazetted Type" style="width:15%">&nbsp;${work.subordinateId}</display:column>
				<display:column title="Designation" style="width:15%">&nbsp;${work.designation}</display:column>
				<display:column title="Edit" style="width:5%;text-align:center">
					<img src="./images/edit.gif" title="Edit"
						onclick="javascript:editUserSpecific('${work.rowId}','${work.instanceId}','${work.requestId}','${work.subordinateId}','${work.delegatedSfid}','${work.delegateType}','${work.designationID}')" />
				</display:column>
				<display:column title="Delete" style="width:5%;text-align:center">
					<img src="./images/delete.gif" title="Delete"
						onclick="javascript:deleteUserSpecific('${work.rowId}')" />
				</display:column>
			</display:table>
		</aa:zone>
		<script>
			displayPaging("userspecificTable","work");
			resetUserConfiguration();
			var subordinateList= <%= (net.sf.json.JSONArray)request.getAttribute("divisionList") %>;
			setSubordinateList(subordinateList);
			document.getElementById('instanceId').value=instanceId;
		</script>
	</div>
</div>
<!-- End : UserSpecificList.jsp -->