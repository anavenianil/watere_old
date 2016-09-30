<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : payEmpLoanDetailsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div>
<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="dataTable">
   	<display:table name="${sessionScope.empLoanList}" excludedParams="*"
		export="false" class="list" requestURI="" id="emp" pagesize="10" sort="list">
	<display:column title="Loan Name" style="width:12%;text-align:left">&nbsp;${emp.loanMasterDetails.loanName}</display:column>
	<display:column title="Total Amt" style="width:11%;text-align:right">&nbsp;${emp.totalAmt}</display:column>
	<display:column title="EMI" style="width:10%;text-align:right">&nbsp;${emp.emi}</display:column>
	<display:column title="Inst Rate" style="width:11%;text-align:right">&nbsp;${emp.interestRate}</display:column>
	<display:column title="Present Inst" style="width:11%;text-align:right">&nbsp;${emp.presentInst}</display:column>
	<display:column title="Total Inst" style="width:11%;text-align:right">&nbsp;${emp.totalInst}</display:column>
	<display:column title="OutstandAmt" style="width:11%;text-align:right">&nbsp;${emp.outStandAmt}</display:column>
	<display:column title="Deduction" style="width:10%;text-align:right">&nbsp;${emp.loanDeduType}</display:column>
	<display:column title="Recovery Date" style="width:15%;text-align:center">&nbsp;<fmt:formatDate value="${emp.recoveryStartDate}" pattern="dd-MMM-yyyy"/> </display:column>
	
		<display:column style="width:15%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editPayLoanDetails('${emp.id}','${emp.loanMasterDetails.id}','${emp.totalAmt}','${emp.emi}','${emp.presentInst}','${emp.totalInst}','${emp.loanDeduType}','${emp.outStandAmt}','${emp.referenceId}','${emp.loanRefId}','${emp.runId}','<fmt:formatDate value="${emp.recoveryStartDate}" pattern="dd-MMM-yyyy"/>','${emp.interestRate}')"/>
		</display:column>
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
</script>


<!-- End : payEmpLoanDetailsList.jsp -->
