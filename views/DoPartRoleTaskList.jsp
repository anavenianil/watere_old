<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :DoPartRoleTaskList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	<div>
	<jsp:include page="Result.jsp"></jsp:include>
	</div>
	<div>
		<div id="Pagination">
				<display:table name="${sessionScope.DoPartRoleTaskList}" excludedParams="*"
				export="false" class="list" requestURI="" id="taskDesigList" pagesize="10"
				sort="list">
				<display:column title="Name" style="width:25%" sortable="true">&nbsp;${taskDesigList.typeName}--${taskDesigList.name}</display:column>
				<display:column title="Designations" style="width:65%">&nbsp;${taskDesigList.designations}</display:column>				
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editTaskHolderDesigDetails1('${taskDesigList.id}','${taskDesigList.name}','${taskDesigList.typeDesigIds}','${taskDesigList.roleId}','${taskDesigList.typeId}')" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteAssignedTypeDesig('${taskDesigList.id}')" />
				</display:column>
				
			</display:table>
		
		</div>
		<script>
		getAllDesignations = <%= (net.sf.json.JSONArray)session.getAttribute("getAllDesignations") %>;
		
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
		</script>
	</div>
</div>
<!-- End : DoPartRoleTaskList.jsp -->