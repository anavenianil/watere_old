<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start :HindiIncentiveList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	<c:if test="${message eq 'duplicate'}"><div class="myStyle failure"><spring:message code="duplicate"/></div></c:if>
<div>
<div id="dataTable">
   	<display:table name="${sessionScope.hindiIncentiveDetailsList}" excludedParams="*"
		export="false" class="list" requestURI="" id="hindiIncentive" pagesize="10" sort="list">
		<%--<display:column  title='Sfid' >${hindiIncentive.sfid}</display:column>--%>
		<display:column  title='Total Installment Amount' style="text-align: right;">${hindiIncentive.totalAmount}</display:column>
		<%--<display:column  title='CashAward Amount' >${hindiIncentive.cashAwardAmount}</display:column>--%>
		<display:column  title='Total Installments' style="text-align: right;">${hindiIncentive.noOfInst}</display:column>
		<display:column  title='Present Installment' style="text-align: right;">${hindiIncentive.presentInst}</display:column>
		<display:column  title='Effective Date' style="text-align: center;"><fmt:formatDate value="${hindiIncentive.fromDate}" pattern="dd-MMM-yyyy"/></display:column>
		<%--<display:column  title='END DATE' ><fmt:formatDate value="${hindiIncentive.toDate}" pattern="dd-MMM-yyyy"/></display:column>--%>		
		
		
		<%--<display:column  title='REMINING AMOUNT' >${hindiIncentive.outStandingAmount}</display:column>--%>
		
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editIncentiveDetails('${hindiIncentive.id}','${hindiIncentive.sfid}','<fmt:formatDate value="${hindiIncentive.fromDate}" pattern="dd-MMM-yyyy"/>','${hindiIncentive.noOfInst}','${hindiIncentive.presentInst}','${hindiIncentive.totalAmount}','${hindiIncentive.creationDate}','${hindiIncentive.createdBy}','${hindiIncentive.cashAwardAmount}')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteIncentiveDetails('${hindiIncentive.id}','${hindiIncentive.sfid}')" />
		</display:column>
	</display:table>
</div>

<script>

	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
	
</script>
</div>

<%-- End : HindiIncentiveList.jsp --%>
