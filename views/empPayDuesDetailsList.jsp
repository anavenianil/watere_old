<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : payEmpLoanDetailsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<div>
    
	<c:if test="${message=='empPayStop' || Result=='empPayStop'}"> <span class="failure"><spring:message code="empPayStop"/></span></c:if>
	<c:if test="${message=='failure' || Result=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success' || Result=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='empNotExists' || Result=='empNotExists'}"> <span class="failure"><spring:message code="empNotExists"/></span></c:if>
	<c:if test="${message=='duplicate' || Result=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
	<c:if test="${message=='EXISTS' || Result=='EXISTS'}"> <span class="failure"><spring:message code="detailsExist"/></span></c:if>
	<c:if test="${message=='employeeexists' || Result=='employeeexists'}"> <span class="success"><spring:message code="employeeexists"/></span></c:if>
	<c:if test="${message=='delete' || Result=='delete'}"> <span class="failure"><spring:message code="delete"/></span></c:if>
	<c:if test="${message=='update' || Result=='update'}"> <span class="success"><spring:message code="update"/></span></c:if>
	<c:if test="${message=='autoRunNotCompleted' || Result=='autoRunNotCompleted'}"> <span class="success"><spring:message code="autoRunNotCompleted"/></span></c:if>
	<c:if test="${message=='constraints' || Result=='constraints'}"><div class="failure">${remarks}</div></c:if>
	<c:if test="${message=='NA' || Result=='NA'}"><span class="failure"><spring:message code="notApplicable"/></span></c:if>
	<c:if test="${message=='Debit sum is greater than credit sum' || Result=='Debit sum is greater than credit sum'}"><span class="failure"><spring:message code="moreDebit"/></span></c:if>
	<c:if test="${message=='Employee NOT using quarters' || Result=='Employee NOT using quarters'}"><span class="failure"><spring:message code="notUsingQuarters"/></span></c:if>
	<c:if test="${message=='Mess charges are NOT applicable for staff' || Result=='Mess charges are NOT applicable for staff'}"><span class="failure"><spring:message code="notUsingMess"/></span></c:if>
	 
	<c:if test="${message=='NO' || Result=='NO'}"><span class="failure"><spring:message code="noLoan"/></span></c:if>
	<c:if test="${message=='alreadyDone' || Result=='alreadyDone'}"><span class="failure"><spring:message code="alreadyDone"/></span></c:if>
	

	
</div>
<div id="dataTable">
   	<display:table name="${sessionScope.payEmpDuesList}" excludedParams="*"
		export="false" class="list" requestURI="" id="emp" pagesize="10" sort="list">
		<display:column title="Recovery Type" style="width:20%;text-align:left;">&nbsp;${emp.deductionName }</display:column>
		<display:column title="Total Amount" style="width:20%;text-align:right">&nbsp;${emp.amount}</display:column>
	    <display:column title="Amount To Be Recovered" style="width:25%;text-align:right">&nbsp;${emp.recAmount}</display:column>
	    <display:column title="Date" style="width:20%;text-align:center;">&nbsp;${emp.creationDate}</display:column>
	    <display:column title="Type" style="width:20%;text-align:left;">&nbsp;${emp.deductionType}</display:column>
		</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
</script>


<!-- End : payEmpLoanDetailsList.jsp -->
