<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : ITArrearsStatusDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<div>
    <jsp:include page="ArrearsResult.jsp"></jsp:include>	
</div>
<div id="dataTable">

   	<display:table name="${sessionScope.arrearsStatusList}" excludedParams="*"
		export="false" class="list" requestURI="" id="arr" pagesize="10" sort="list">
		<display:column title="Run From" style="width:10%;text-align:left">${arr.fromDate}</display:column>
		<display:column title="Run To" style="width:10%;text-align:left">${arr.toDate}</display:column>
		<display:column title="Old DA" style="width:10%;text-align:left">${arr.oldDa}</display:column>
		<display:column title="New DA" style="width:10%;text-align:left">${arr.newDa}</display:column>
		<display:column title="Category Name" style="width:10%;text-align:left">
		<spring:bind path="incomeTaxMaster">
		<form:select path="categoryId" id="categoryId"  cssClass="formSelect">
			 <form:option value="select">Select</form:option>
			 <form:options items="${sessionScope.empCategoryMasterDetails}" itemValue="id" itemLabel="name" />
		</form:select></spring:bind>
	    </display:column>
	    <c:if test="${arr.daType=='1'}">
	  <display:column title="Type" style="width:10%;text-align:left">DA1</display:column>
	  </c:if>
	  <c:if test="${arr.daType=='2'}">
	    <display:column title="Type" style="width:10%;text-align:left">DA2</display:column>
	    </c:if>
	    <display:column title="Run Status" style="width:10%;text-align:left">Completed</display:column>
	    <display:column title="Report" style="width:10%;text-align:left"><a id="report" href="javascript:printDAArrearsDetails('${arr.fromDate}','${arr.toDate}','${arr.daType}');">Report</a></display:column>
	
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
</script>


<!-- End : ITArrearsStatusDetails.jsp -->
