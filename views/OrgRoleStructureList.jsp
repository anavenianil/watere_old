<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : OrgRoleStructureList.jsp -->
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
		<c:if test="${Result=='delete'}"> <span class="delete"><spring:message code="delete"/></span></c:if>
	</div>
	<div class="line">
		<div id="Pagination">
			<display:table name="${sessionScope.rolesList}" excludedParams="*" export="false" class="list" requestURI="" id="rolesList" pagesize="10" sort="list">
				<display:column title="Role Names" style="width:25%">${rolesList.roleName}</display:column>
				<display:column title="Hierarchy Level" style="width:15%">${rolesList.hierarchyName}</display:column>
				<display:column title="Reports To" style="width:25%">${rolesList.parentName}</display:column>
				<display:column title="DIR/PD/AD/TD/PROJ/DIV/GP/SEC" style="width:25%">${rolesList.departmentName}</display:column>
				
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif" onclick="editRole('${rolesList.id}','${rolesList_rowNum}','${rolesList.hierarchyID}','${rolesList.parentID}','${rolesList.departmentID}','10','${rolesList.isHead}')"/>
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif" onclick="deleteStr('${rolesList.id}','role')"/>
				</display:column>
			</display:table>
			<script>
				$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
				jsonArrayObject =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<OrgInstanceDTO>)session.getAttribute("rolesListJson"))%>;
			</script>
		</div>
	</div>
</div>
<!-- End : OrgRoleStructureList.jsp -->