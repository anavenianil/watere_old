<!-- Begin : ApplicationRolesList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<script type="text/javascript" src="script/aa.js"></script>

<div>
	<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='deleted'}"> <span class="success"><spring:message code="delete"/></span></c:if>
	</div>
	<div>
		<aa:zone name="approlesTable">
			<display:table name="${sessionScope.applicationRolesList}" excludedParams="*"
				export="false" class="list" requestURI="" id="approlesList" pagesize="10"
				sort="list">
				<display:column title="EmployeeID" style="width:10%">${approlesList.sfid}</display:column>
				<display:column title="Name" style="width:20%">${approlesList.empName}</display:column>
				<display:column title="Application Role" style="width:15%">${approlesList.applicationRoleName}</display:column>
				<display:column title="Designation" style="width:20%">${approlesList.designation}</display:column>
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editAppRole('${approlesList.sfid}','${approlesList.applicationRoleID}')" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteAppRole('${approlesList.sfid}','${approlesList.id}')" />
				</display:column>
			</display:table>
		</aa:zone>
		<script>
			displayPaging("approlesTable","approlesList");
			applicationRolesMapJSON = <%= (net.sf.json.JSONArray)session.getAttribute("applicationRolesMapJSON") %>;
			
		</script>		
	</div>
</div>
<!-- End : ApplicationRolesList.jsp -->