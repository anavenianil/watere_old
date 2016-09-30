<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : PayBillEmpCategoryDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div>
<jsp:include page="Result.jsp"/>
</div>
<div id="dataTable">
   	<display:table name="${sessionScope.ccDetails}" excludedParams="*" export="false" class="list" requestURI="" id="emp" pagesize="10" sort="list">
		<display:column title="Deduction Type" style="width:20%;text-align:left;"> ${emp.deductionDetails.name}</display:column>
		<display:column title="Installment Amount" style="width:15%;text-align:right"> ${emp.amount}</display:column>
		<display:column title="Present Installment" style="width:15%;text-align:right"> ${emp.presentInst}</display:column>
		<display:column title="Total Installments" style="width:15%;text-align:right"> ${emp.noOfInst}</display:column>
		<display:column title="Effective Date" style="width:15%;text-align:center"><fmt:formatDate value="${emp.effDate}" pattern="dd-MMM-yyyy"/></display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="payEditCcDetails('${emp.id}','${emp.amount}','${emp.deductionDetails.id}','${emp.presentInst}','${emp.noOfInst}','<fmt:formatDate value="${emp.effDate}" pattern="dd-MMM-yyyy"/>')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="payDeleteCcDetails('${emp.id}')"/>
		</display:column>
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
</script>


<!-- End : PayBillEmpCategoryDetails.jsp -->
