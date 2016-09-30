<!-- Start : LoanRequestDeatailsList.jsp -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div id="Pagination">
<display:table
	name="${sessionScope.AppliedLoansList}" excludedParams="*"
	export="false" class="list" requestURI="" id="dataList" pagesize="10"
	sort="list">
	<display:column  style="width:5%;text-align:center" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(\'selectall\',\'row\');"/>'>
					<input type="checkbox" class="row" name="row" id="${dataList.requestID}" onclick="checkBoxCheck('row');"/>
	</display:column>
	<display:column title="SFID" style="width:20%;">${dataList.sfID}</display:column>
	<display:column title="Name" style="width:25%">${dataList.name}</display:column>
	<display:column title="Loan Type" style="width:25%">${dataList.loanName}</display:column>
	<display:column title="Requested Amount" style="width:25%">${dataList.reqAmount}</display:column>
</display:table></div>
<script>	
	$jq( function(){$jq("#Pagination").displayTagAjax('paging');});
</script>
<!-- End : LoanRequestDeatailsList.jsp -->
