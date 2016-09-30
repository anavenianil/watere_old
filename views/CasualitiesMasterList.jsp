<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :CasualitiesMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	
		<c:if test="${message=='failure' || Result=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success' || Result=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='empNotExists' || Result=='empNotExists'}"> <span class="failure"><spring:message code="empNotExists"/></span></c:if>
		<c:if test="${message=='duplicate' || Result=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='EXISTS' || Result=='EXISTS'}"> <span class="failure"><spring:message code="detailsExist"/></span></c:if>
		<c:if test="${message=='employeeexists' || Result=='employeeexists'}"> <span class="success"><spring:message code="employeeexists"/></span></c:if>
		<c:if test="${message=='delete' || Result=='delete'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
		<c:if test="${message=='update' || Result=='update'}"> <span class="success"><spring:message code="update"/></span></c:if>
	
	
	
	
	<c:if test="${leaveAdmin.param=='getGazettedWiseCasuality' || leaveAdmin.param=='paging' || leaveAdmin.param=='deleteCasualitiesDetails'}"> 
		<div id="Pagination">
				<display:table name="${sessionScope.CasualitiesMasterList}" excludedParams="*"
				export="false" class="list" requestURI="" id="casualityList" pagesize="1"
				sort="list">
				<display:column title="Type Name" style="width:13%">&nbsp;${casualityList.typeName}</display:column>
				<display:column title="Module Name" style="width:15%">&nbsp;${casualityList.moduleName}</display:column>
				<display:column title="Casuality Name" style="width:18%" sortable="true">&nbsp;${casualityList.name}</display:column>
			    <display:column title="Code" style="width:5%" sortable="true">&nbsp;${casualityList.code}</display:column>
			    <display:column title="Order" style="width:5%" sortable="true">&nbsp;${casualityList.orderBy}</display:column>
				<display:column title="Description " style="width:16%">&nbsp;${casualityList.description}</display:column>				
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editCasualityDetails('${casualityList.id}','${casualityList.moduleId}','${casualityList.name}','${casualityList.description}','${casualityList.orderBy}','${casualityList.typeId}','${casualityList.code}')" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteCasualityDetails('${casualityList.id}')" />
				</display:column>
				
			</display:table>
		
		</div>
	</c:if>	
	
	<c:if test="${leaveAdmin.param=='saveCasualityDetails' }"> 
		<div id="Pagination">
				<display:table name="${sessionScope.CasualitiesMasterList}" excludedParams="*"
				export="false" class="list" requestURI="" id="casualityList" pagesize="1"
				sort="list">
				<display:column title="Type Name" style="width:13%">&nbsp;${casualityList.typeName}</display:column>
				<display:column title="Module Name" style="width:15%">&nbsp;${casualityList.moduleName}</display:column>
				<display:column title="Casuality Name" style="width:18%" sortable="true">&nbsp;${casualityList.name}</display:column>
			    <display:column title="Code" style="width:5%" sortable="true">&nbsp;${casualityList.code}</display:column>
			    <display:column title="Order" style="width:5%" sortable="true">&nbsp;${casualityList.orderBy}</display:column>
				<display:column title="Description " style="width:16%">&nbsp;${casualityList.description}</display:column>				
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editCasualityDetails('${casualityList.id}','${casualityList.moduleId}','${casualityList.name}','${casualityList.description}','${casualityList.orderBy}','${casualityList.typeId}','${casualityList.code}')" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteCasualityDetails('${casualityList.id}')" />
				</display:column>
				
			</display:table>
		
		</div>
	</c:if>	
	
	
	
		
		<script>
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
		</script>
	
</div>
<!-- End : CasualitiesMasterList.jsp -->