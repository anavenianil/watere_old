<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : DesignationList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='deletefail'}"> <span class="failure"><spring:message code="recordexists"/></span></c:if>
	</div>
	<div>
		<div id="Pagination">
			<display:table name="${sessionScope.displayDesig}" excludedParams="*"
				export="false" class="list" requestURI="" id="desigList" pagesize="10"
				sort="list">
				<display:column title="Order No" style="width:5%">&nbsp;${desigList.orderNo}</display:column>
				<display:column title="Designation" style="width:20%" sortable="true">&nbsp;${desigList.designationId}</display:column>
				<display:column title="Code" style="width:5%">&nbsp;${desigList.code}</display:column>
				<display:column title="Desig In Short" style="width:5%">&nbsp;${desigList.description}</display:column>
				<display:column title="Sub Category" style="width:15%">&nbsp;${desigList.subCategoryName}</display:column>
				<display:column title="Group" style="width:5%">&nbsp;${desigList.groupName}</display:column>
				<display:column title="Type" style="width:15%">&nbsp;${desigList.type}</display:column>
				<display:column title="Service Type" style="width:5%"><div ><c:if test="${desigList.serviceType eq 'Y'}">Yes</c:if>
				<c:if test="${desigList.serviceType eq 'N'}">No</c:if></div></display:column>
				<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editDesignation('${desigList.desigID}','${desigList.categoryID}','${desigList.subCategoryID}','${desigList.groupID}','${desigList.type}','${desigList.designationId}','${desigList.code}','${desigList.serviceType}','${desigList.description}','${desigList.orderNo}','${desigList.desigAlias}')" />
				</display:column>
				<display:column style="width:5%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteDesignation('${desigList.desigID}')" />
				</display:column>
			</display:table>
		</div>
		<script>
			//displayPaging("desigTable","desigList");
			$jq( function(){
					$jq("#Pagination").displayTagAjax('paging');
				})
			designationID="";
			var jsonSubCategoryList= <%= (net.sf.json.JSONArray)session.getAttribute("subCategoryJSON") %>;
		</script>
	</div>
</div>
<!-- End : DesignationList.jsp -->