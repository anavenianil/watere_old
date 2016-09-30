<!-- Start : SanctionContingentList.jsp -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/workflow.js"></script>

<div id="Pagination">
	<display:table name="${sessionScope.SanctionContingentList}" excludedParams="*" export="false"	class="list" requestURI="" id="dataList" pagesize="20" sort="list">
			<display:column title="Request ID" style="width:5%;"><a href="javascript:getRequestDetails('${dataList.historyID}','${dataList.requestID}','myRequests','pending')"><font color="blue">${dataList.requestID}</font></a></display:column>
			<display:column title="SFID" style="width:5%;">${dataList.sfID}</display:column>
			<display:column title="Emp Name" style="width:10%;">${dataList.empName}</display:column>	
			<display:column title="Loan Name" style="width:10%;">${dataList.loanName}</display:column>	
			<c:if test="${dataList.loanTypeID ne 9}">
			<display:column title="Sanction Report" style="width:10%;"><a href="javascript:viewLoanForm('${dataList.loanTypeID}','${dataList.requestID}','certificate')">PDF</a></display:column>		
			</c:if>
			<c:if test="${dataList.loanTypeID eq 9}">
			<display:column title="Sanction Report" style="width:10%;"><a href="javascript:viewLoanForm('${dataList.loanTypeID}','${dataList.requestID}','sanction')">PDF</a></display:column>		
			</c:if>
			<display:column title="Contingent Bill" style="width:10%;"><a href="javascript:viewLoanForm('${dataList.loanTypeID}','${dataList.requestID}','contingent')">PDF</a></display:column>									
	</display:table>
</div>
<script>
	$jq( function(){$jq("#Pagination").displayTagAjax('paging');})
</script>
<!-- End : SanctionContingentList.jsp -->