<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Begin : PayBillCGHSMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div class="line">
	<jsp:include page="Result.jsp"></jsp:include>
</div>

<div id="Pagination">
   	<display:table name="${sessionScope.cghsMasterDetails}" excludedParams="*" export="false" class="list" requestURI="" id="cghs" pagesize="10" sort="list">
		<display:column title="Grade Pay" style="width:25%;text-align:right;">&nbsp;<fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${cghs.gradePay}"/></display:column>
		<display:column title="Monthly Contribution" style="width:25%;text-align:right;"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${cghs.allowanceAmount}"/></display:column>
		<display:column title="Effective From" style="width:25%;text-align:center;"><fmt:formatDate pattern="dd-MMM-yyyy" value="${cghs.effDate}"/></display:column>
		<display:column style="width:10%;text-align:center;" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editCghsMasterDetails('${cghs.pk}','${cghs.gradePay}','${cghs.allowanceAmount}','<fmt:formatDate pattern="dd-MMM-yyyy" value="${cghs.effDate}"/>')" />
		</display:column>
		<display:column style="width:10%;text-align:center;" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deletePayBillRecord('${cghs.pk}','cghs','cghsMasterList')" />
		</display:column>
	</display:table>
</div>
<script>
 	$jq( function(){$jq("#Pagination").displayTagAjax('paging');})
 	clearPaybillCghsMaster();
</script>
<!-- End : PayBillCGHSMasterList.jsp -->