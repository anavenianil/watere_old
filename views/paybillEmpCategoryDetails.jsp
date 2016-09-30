<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : PayBillEmpCategoryDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>

<div>
<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="dataTable">
   	<display:table name="${sessionScope.empCategoryMasterDetails}" excludedParams="*"
		export="false" class="list" requestURI="" id="emp" pagesize="10" sort="list">
		<display:column title="Order No" style="width:15%;text-align:right;">&nbsp;${emp.payOrderNo}</display:column>
		<display:column title="Category Name" style="width:40%;text-align:left">&nbsp;${emp.name}</display:column>
		<display:column title="PayBill Type" style="width:20%;text-align:left">&nbsp;${emp.payBillType}</display:column>
		<display:column style="width:15%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="payEditCategoryDetails('${emp.id}','${emp.name}','${emp.payOrderNo}','${emp.payBillType}')"/>
		</display:column>
		<display:column style="width:15%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="payDeleteCategoryDetails('${emp.id}','${emp.name}','${emp.payOrderNo}')" />
		</display:column>
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	jsonPayOrderNo = <%= (net.sf.json.JSONArray)session.getAttribute("jsonPayOrderNo")%>;
</script>


<!-- End : PayBillEmpCategoryDetails.jsp -->
