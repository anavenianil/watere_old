<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : PayAllowanceConfigurationList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>

<div>
<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="dataTable">
   	<display:table name="${sessionScope.payAllowanceConfigurationList}" excludedParams="*"
		export="false" class="list" requestURI="" id="emp" pagesize="10" sort="list">
		<display:column title="Allowance Name" style="width:20%;text-align:right">&nbsp;${emp.value}</display:column>
		<display:column title="Applys To" style="width:20%;text-align:right">&nbsp;${emp.key}</display:column>
		<display:column style="width:15%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="payEditAllowanceDetails('${emp.name}')"/>
		</display:column>
		<display:column style="width:15%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="payDeleteAllowanceDetails('${emp.name}')" />
		</display:column>
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
</script>


<!-- End : PayAllowanceConfigurationList.jsp -->
