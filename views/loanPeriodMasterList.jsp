<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : LoanPerodMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.dto.DesignationDTO,com.callippus.web.beans.dto.KeyValueDTO"%>
<%@page import="com.callippus.web.loan.beans.dto.LoanPeriodMasterDTO"%>
<%@page import="com.callippus.web.loan.beans.dto.HolidayMasterDTO"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />

<div>
<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="dataTable">
   	<display:table name="${sessionScope.LoanTypeMasterList}" excludedParams="*"
		export="false" class="list" requestURI="" id="loan" pagesize="10" sort="list">
		<display:column title="Loan Type" style="width:15%;text-align:right">&nbsp;${loan.loanName}</display:column>
		<display:column title="Sub Type" style="width:15%;text-align:right">&nbsp;${holidayMasterDTO.name}</display:column>
		<display:column style="width:15%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editLoanTypeMasterDetails('${loan.id}','${loan.loanName}','${holidayMasterDTO.name}')"/>
		</display:column>
		<display:column style="width:15%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteLoanTypeMasterDetails('${loan.id}','loanPeriod','LoanPeriodMasterList')" />
		</display:column>
	</display:table>
</div>

	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
		HolidayMasterList =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<HolidayMasterDTO>)session.getAttribute("HolidayMasterList"))%>;
		LoanPeriodMasterList =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<LoanPeriodMasterDTO>)session.getAttribute("LoanPeriodMasterList"))%>;
	</script>

<!-- End : LoanPeriodMasterList.jsp -->
