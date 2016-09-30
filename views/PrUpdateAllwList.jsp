<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : PrUpdateAllwList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
    <jsp:include page="Result.jsp"></jsp:include>	
</div>

<div id="dataTable">
   	<display:table name="${sessionScope.puaList}" excludedParams="*"
		export="false" class="list" requestURI="" id="pr" pagesize="12" sort="list">
		<display:column title="Grade Pay" style="width:20%;text-align:right;">&nbsp;${pr.designationDetails.gradePay}</display:column>
		<display:column title="Designation" style="width:40%;text-align:left">&nbsp;${pr.designationDetails.designationDetails.name}</display:column>
		<display:column title="Amount" style="width:20%;text-align:right;">&nbsp;${pr.amount}</display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editPUADetails('${pr.id}','${pr.designationDetails.gradePay}','${pr.designationId}','${pr.amount}')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deletePUADetails('${pr.id}')" />
		</display:column>
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
</script>


<!-- End : PrUpdateAllwList.jsp -->
