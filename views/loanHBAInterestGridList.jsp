<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : LoanHBAInterestGrid.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>

<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div>
<div id="Pagination">
		<display:table name="${sessionScope.loanHBAInterestGridList}" excludedParams="*" export="false" class="list" requestURI="" id="loan" pagesize="10" sort="list">
		    <display:column title="Lower Amount Limit" style="width:20%"> ${loan.lowerAmountLimit}</display:column>
		    <display:column title="Upper Amount Limit" style="width:20%"> ${loan.upperAmountLimit}</display:column>
		    <display:column title="Interest Rate" style="width:20%"> ${loan.interestRate}</display:column>
		    <display:column title="Applicable Year" style="width:20%"><fmt:formatDate pattern="dd-MMM-yyyy" value="${loan.applicableYear}"/> </display:column>
		    <display:column style="width:10%;text-align:center" title="Edit">
					<img src="./images/edit.gif" title="Edit"
						onclick="editLoanHBAInterestDetails('${loan.id}','${loan.lowerAmountLimit}','${loan.upperAmountLimit}','${loan.interestRate}','${loan.applicableYear}')" />
			</display:column>
			<display:column style="width:10%;text-align:center" title="Delete">
				<img src="./images/delete.gif" title="Delete"
					onclick="deleteLoanHBAInterestDetails('${loan.id}')" />
			</display:column>
		</display:table>
	</div>	
	<script>
		$jq( function(){$jq("#Pagination").displayTagAjax('paging');});
	</script>
	<script>
		
			jsonLoanHBAInterestGridList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonLoanHBAInterestGridList") %>;
			
		</script>
</div>
<!-- End :LoanHBAInterestGrid.jsp-->