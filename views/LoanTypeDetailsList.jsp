<!-- Start : LoanTypeList.jsp -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.loan.beans.dto.LoanLeavesMappingDTO"%>
<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div id="Pagination">
	<display:table name="${sessionScope.LoanTypeDetailsList}" excludedParams="*"
		export="false" class="list" requestURI="" id="dataList" pagesize="10"
		sort="list">
		<display:column title="Loan Type" style="width:15%;">${dataList.loanTypeDetails.loanName}</display:column>
		<display:column title="Loan SubType" style="width:15%">
		${dataList.loanSubTypeName}
		</display:column>
		<display:column title="Minimum Principle Installments" style="width:5%;text-align:right">${dataList.minInstallments}</display:column>
		<display:column title="Maximum Principle Installments" style="width:5%;text-align:right">${dataList.maxInstallments}</display:column>
		<display:column title="Minimum Interest Installments" style="width:5%;text-align:right">${dataList.minInterestInstallments}</display:column>
		<display:column title="Maximum Interest Installments" style="width:5%;text-align:right">${dataList.maxInterestInstallments}</display:column>
		<display:column title="Minimum Service" style="width:10%;text-align:right"><fmt:formatNumber  pattern="##0.00" type="number" maxFractionDigits="0" value="${dataList.experience}"/></display:column>
		<display:column title="Maximum Recovery Period" style="width:10%;text-align:right">${dataList.maxRecoveryPeriod}</display:column>
		<display:column title="Interest Rate" style="width:10%;text-align:right">&nbsp;<fmt:formatNumber  pattern="##0.00" type="number" maxFractionDigits="2" value="${dataList.interestRate}"/></display:column>

		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editLoanDetails('${dataList.id}','${dataList.loanTypeDetails.id}','${dataList.loanSubTypeID}','${dataList.minInstallments}','${dataList.maxInstallments}','${dataList.minInterestInstallments}','${dataList.maxInterestInstallments}','${dataList.experience}','${dataList.periodTypeFlag}','${dataList.fromDate}','${dataList.toDate}','${dataList.recoveryFlag}','${dataList.recoveryStart}','${dataList.maxRecoveryPeriod}','${dataList.impactOnLeaveFlag}','${dataList.interestRate}')" />
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteLoanDetails('${dataList.id}')" />
		</display:column>
	</display:table>
</div>

<script>			
	jsonAvailabeLeavesList= <%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<LoanLeavesMappingDTO>)session.getAttribute("loanLeavesMappingJSON"))%>;
	$jq( function(){$jq("#Pagination").displayTagAjax('paging');})
</script>
<!-- End : LoanTypeList.jsp -->