<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : MTModelMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
	<c:if test="${message eq 'failure'}"><div class="myStyle failure">Sorry, You Can not Delete This Record Due to Vehicles Exist For This Model</div></c:if>

<div id="dataTable">
   	<display:table name="${sessionScope.modelList}" excludedParams="*"
		export="false" class="list" requestURI="" id="model" pagesize="10" sort="list">
		<display:column  title='Model Name' >${model.modelName}</display:column>
		<display:column  title='Category Name' >${model.categoryDetails.categoryName}</display:column>
		<display:column  title='Description' >${model.modelDesc}</display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<%--<img src="./images/edit.gif" title="Edit" onclick="editModelDetails('${model.modelId}','${model.vehicleCategoryId}','${model.modelName}','${model.modelDesc}')"/>--%>
		<img src="./images/edit.gif" title="Edit" onclick="editModelDetails('${model.modelId}')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteModelDetails('${model.modelId}')" />
		</display:column>
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	 modelListJson	= <%=(net.sf.json.JSONArray)session.getAttribute("modelListJson")%>;
</script>


<%-- End :  MTModelMasterList.jsp --%>
