<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : EmpRoleMappingList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.dto.OrgInstanceDTO"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div class="line">
	<div class="line">
		<c:if test="${Result=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${Result=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${remarks ne null}"> <span class="failure"><br/>${remarks}</span></c:if>		
		<c:if test="${Result=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
	</div>
	<div class="line">
		<div id="Pagination">
			<display:table name="${sessionScope.roleEmployeesList}" excludedParams="*" export="false" class="list" requestURI="" id="rolesList" pagesize="10" sort="list">
				<display:column title="SFID" style="width:10%">${rolesList.empSfid}</display:column>
				<display:column title="Employee Name" style="width:40%">${rolesList.empName}</display:column>
				<display:column title="Role Name" style="width:40%">${rolesList.roleName}</display:column>
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif" onclick="editEmpRole('${rolesList.id}','${rolesList.retirementDate}','${rolesList.empSfid}','${rolesList.empName}','${rolesList.roleID}','${rolesList.defRole}')"/>
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif" onclick="deleteEmpRole('${rolesList.id}','${rolesList.empSfid}','${rolesList.roleID}')"/>
				</display:column>
			</display:table>
			<script>
				$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
			</script>
		</div>
	</div>
</div>
<!-- End : EmpRoleMappingList.jsp -->