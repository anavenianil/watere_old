<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : incomeTaxClaculateList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
    <jsp:include page="Result.jsp"></jsp:include>	
</div>
<div id="dataTable">
   	<display:table name="${sessionScope.incometaxStatusList}" excludedParams="*"
		export="false" class="list" requestURI="" id="status" pagesize="10" sort="list">
		<display:column title="Financial Year" style="width:20%;text-align:center">${status.finYear}</display:column>
		<display:column title="Planned" style="width:20%;text-align:center">
		<c:if test="${status.plannedStatus eq '1'}">
		 Completed
		</c:if>
		<c:if test="${status.plannedStatus eq '0'}">
		--
		</c:if>
		</display:column>
		<display:column title="Projection" style="width:20%;text-align:center">
		<c:if test="${status.projectedStatus eq '1'}">
		 Completed
		</c:if>
		<c:if test="${status.plannedStatus eq '0'}">
		--
		</c:if>
		</display:column>
		<display:column title="Actual" style="width:20%;text-align:center">
		<c:if test="${status.actualStatus eq '1'}">
		 Completed
		</c:if>
		<c:if test="${status.plannedStatus eq '0'}">
		--
		</c:if>
		</display:column>
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
</script>


<!-- End : incomeTaxClaculateList.jsp -->
