<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : HindiCashAwardListPage.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	<c:if test="${message eq 'duplicate'}"><div class="myStyle failure"><spring:message code="duplicate"/></div></c:if>
<div>
<div id="dataTable">
   	<display:table name="${sessionScope.hindiCashAwardList}" excludedParams="*"
		export="false" class="list" requestURI="" id="hindiCashAward" pagesize="10" sort="list">
		<display:column  title='Exam Name' >${hindiCashAward.examDetails.examName}</display:column>
		<display:column  title='Lower Percentage' >${hindiCashAward.lowerPercentage}</display:column>
		<display:column  title='Upper Percentage' >${hindiCashAward.upperPercentage}</display:column>
		<display:column  title='CashAward Amount' >${hindiCashAward.cashAwardAmount}</display:column>		
		
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editCashAwardDetails('${hindiCashAward.id}','${hindiCashAward.examId}','${hindiCashAward.lowerPercentage}','${hindiCashAward.upperPercentage}','${hindiCashAward.cashAwardAmount}')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteCashAwardDetails('${hindiCashAward.id}')" />
		</display:column>
	</display:table>
</div>

<script>

	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
	
</script>
</div>

<%-- End : HindiCashAwardListPage.jsp --%>
