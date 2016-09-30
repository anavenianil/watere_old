<!-- Start : LoanReportDetailsList.jsp -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<div id="Pagination"><display:table
	name="${sessionScope.searchCDAList}" excludedParams="*"
	export="false" class="list" requestURI="" id="dataList" pagesize="10"
	sort="list">
	<display:column  style="width:3%;text-align:center" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(\'selectall\',\'row\');"/>'>
					<input type="checkbox" class="row" name="row" id="${dataList.requestID}" onclick="checkBoxCheck('row');"/>
	</display:column>
	<display:column title="Req ID" style="width:5%;"><a href="javascript:getRequestDetails('${dataList.historyID}','${dataList.requestID}','myRequests','pending')"><font color="blue">${dataList.requestID}</font></a></display:column>
	<display:column title="SFID" style="width:5%;">${dataList.sfID}</display:column>
	<display:column title="Name" style="width:20%">${dataList.name}</display:column>
	<display:column title="Requested Amount" style="width:7%;">${dataList.reqAmount}</display:column>
	<display:column title="Sanctioned Amount" style="width:10%;"><input type="text" size=8 id="${dataList.id}" onkeyup="javascript:calculateInstallEmi(this)" onkeypress="return isNumberExp(event);"/></display:column>
	<display:column title="Recovery Start Date" style="width:12%;">
							<input type="text" size=8 id="recStartDate${dataList.id}" readonly="readonly" name="recStartDate"/>
								<img  src="./images/calendar.gif" id="recStartDateImg${dataList.id}" />	
									<script type="text/javascript">
										Calendar.setup({inputField :"recStartDate"+${dataList.id},ifFormat : "%d-%b-%Y",showsTime : false,button :"recStartDateImg"+${dataList.id},singleClick : true,step : 1});
									</script></display:column>
	<display:column title="Principle Install" style="width:4%;"><input type="text" value="${dataList.requestedInstalments}" size=5 onkeyup="javascript:calculateInstallEmi(this)" onkeypress="return isNumberExp(event);"/></display:column>
	<display:column title="Principle EMI" style="width:4%;"><input type="text" size=4 onkeypress="return isNumberExp(event);"/></display:column>
	<display:column title="Principle last EMI" style="width:4%;"><input type="text" size=4 onkeypress="return isNumberExp(event);"/></display:column>
	<display:column title="Interest Amt" style="width:4%;"><input type="text" size=4 onkeypress="return isNumberExp(event);"/></display:column>
	<display:column title="Interest Install" style="width:4%;"><input type="text" size=4 value="${dataList.requestedInterestInstalments}" id="${dataList.cdaId}" onkeypress="return isNumberExp(event);"/></display:column>
	<display:column title="Interest EMI" style="width:4%;"><input type="text" size=4 onkeypress="return isNumberExp(event);"/></display:column>
	<display:column title="Int last EMI" style="width:4%;"><input type="text" size=4 onkeypress="return isNumberExp(event);"/></display:column>
	<display:column title="Int Rate" style="width:3%;"><input readonly="readonly" type="text" size=3 value="${dataList.interestRate}" /></display:column>
	
</display:table></div>
<script>	
	$jq( function(){$jq("#Pagination").displayTagAjax('paging');})
</script>

<!-- End : LoanReportDeatailsList.jsp -->