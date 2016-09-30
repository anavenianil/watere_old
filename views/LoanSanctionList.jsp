<!-- Start : LoanSanctionList.jsp -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div id="Pagination">
	<display:table name="${loanRequestDetailList}" excludedParams="*"
		export="false" class="list" requestURI="" id="dataList" pagesize="1"
		sort="list">
		<display:column title="Name of the Applicant" style="width:15%;">${dataList.NAME}</display:column>
		<display:column title="Sfid"  style="width:15%;">${dataList.SFID}</display:column>
		<display:column title="Loan Type" style="width:15%;">${dataList.LOANTYPE}</display:column>
		<display:column title="Amount Requested" style="width:15%;text-align:right">${dataList.REQUESTAMOUNT}</display:column>
		<display:column title="Sanctioned" style="width:15%;">${dataList.SANCTIONFLAG}</display:column>
	<c:if test="${dataList.SANCTIONFLAG eq 'yes'}">
		<display:column title="Sanctioned Amount"  style="width:15%;"><input type="text" name="sanctionedAmount"  id="${dataList.ID}" DISABLED/></display:column>	
	</c:if>
	<c:if test="${dataList.SANCTIONFLAG eq 'no'}">
		<display:column title="Sanctioned Amount"  style="width:15%;"><input type="text" name="sanctionedAmount"  id="${dataList.ID}" /></display:column>	
	</c:if>
	</display:table>
</div>

<script>
 var loanRequestDetailList=<%=(net.sf.json.JSONArray) request.getAttribute("loanRequestDetailList")%>;
  	$jq( function(){
	     $jq("#Pagination").displayTagAjax('paging');
	         })
</script>



<!-- End : LoanSanctionList.jsp -->