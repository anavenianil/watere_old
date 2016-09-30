<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : MTCategoryList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
	<c:if test="${message eq 'failure'}"><div class="myStyle failure">Sorry, You Can not Delete This Record Due to Models Exist For This Category</div></c:if>

<div id="dataTable">
   	<display:table name="${sessionScope.categoryList}" excludedParams="*"
		export="false" class="list" requestURI="" id="category" pagesize="10" sort="list">
		<display:column  title='Category Name' >${category.categoryName}</display:column>
		<display:column  title='Carriage Type' >${category.carriageDetails.vehicleCarriageType}</display:column>
		<display:column  title='Description' >${category.catDesc}</display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<%--<img src="./images/edit.gif" title="Edit" onclick="editCategoryDetails('${category.categoryId}','${category.categoryName}','${category.carriageType}','${category.catDesc}')"/>--%>
		<img src="./images/edit.gif" title="Edit" onclick="editCategoryDetails('${category.categoryId}')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteCategoryDetails('${category.categoryId}')" />
		</display:column>
	</display:table>
</div>

<script>

	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
   categoryListJson	= <%=(net.sf.json.JSONArray)session.getAttribute("categoryListJson")%>;
	
</script>


<%-- End :  MTCategoryList.jsp --%>
