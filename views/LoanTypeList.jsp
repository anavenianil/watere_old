<!-- Start : LoanTypeList.jsp -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div id="Pagination">
	<display:table name="${LoanDetailList}" excludedParams="*"
		export="false" class="list" requestURI="" id="dataList" pagesize="1"
		sort="list">
		<display:column title="Loan Type" style="width:15%;">${dataList.name}</display:column>
		<display:column title="Basic Pay"  style="width:25%;text-align:right"><fmt:formatNumber  pattern="##0.00" type="number" maxFractionDigits="2" value="${dataList.basicPay}"/></display:column>
		<display:column title="Maximum Loan Amount" style="width:25%;text-align:right">&nbsp;<fmt:formatNumber  pattern="##0.00" type="number" maxFractionDigits="2" value="${dataList.maxLoanAmount}"/></display:column>
		<display:column title="Maximum Installments" style="width:15%;text-align:right">${dataList.maxInstalment}</display:column>
		<display:column title="Interest Rate" style="width:15%;text-align:right">&nbsp;<fmt:formatNumber  pattern="##0.00" type="number" maxFractionDigits="2" value="${dataList.interestRate}"/></display:column>

		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editLoanDetails('${dataList.id}')" />
		</display:column>
		<display:column style="width:5%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteLoanDetails('${dataList.id}')" />
		</display:column>
	</display:table>
</div>

<script>
			
	var JSONLoanDetailsList=<%=(net.sf.json.JSONArray) request.getAttribute("JSONLoanDetailsList")%>;
							
				$jq( function(){
	                $jq("#Pagination").displayTagAjax('paging');
	                   })
			
			
</script>




<!-- End : LoanTypeList.jsp -->