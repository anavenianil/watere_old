<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : DepartmentsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.dto.DepartmentsDTO"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div class="line">
	<div class="line">
		<c:if test="${Result == 'success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${Result == 'failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${remarks ne null}"> <span class="failure"><br/>${remarks}</span></c:if>		
		<c:if test="${Result == 'duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${Result == 'delete'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
	</div>
	<div class="line">
		<div id="Pagination">
			<display:table name="${sessionScope.DepartmentsList}" excludedParams="*" export="false" class="list" requestURI="" id="deptList" pagesize="10" sort="list" >
				<display:column title="DepartmentName" style="width:25%">${deptList.deptName}</display:column>
				<display:column title="Full Form" style="width:25%">${deptList.description}&nbsp;</display:column>
				<display:column title="Hierarchy Level" style="width:25%">${deptList.hierarchyName}</display:column>
				<display:column title="Reports To" style="width:25%">${deptList.parentName}&nbsp;</display:column>
				<display:column title="Area of Deployment" style="width:15%">${deptList.departmentTypeName}</display:column>
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif" onclick="editDepartment('${deptList.id}','${deptList_rowNum}','${deptList.hierarchyID}','${deptList.parentID}','${deptList.departmentTypeId}','10','${deptList.description}','${deptList.fax}','${deptList.phoneNumber}','${deptList.email}');" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif" onclick="deleteStr('${deptList.id}','department')" />
				</display:column>
			</display:table>
			<script>
				$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
				jsonArrayObject =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<DepartmentsDTO>)session.getAttribute("DepartmentsListJson"))%>;
			</script>
		</div>
	</div>
</div>
<!-- End : DepartmentsList.jsp -->