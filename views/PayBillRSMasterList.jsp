<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<div>
	<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="dataTable">
   	<display:table name="${sessionScope.residentialSecurityMasterDetails}" excludedParams="*" export="false" class="list" requestURI="" id="cghs" pagesize="10" sort="list">
		<display:column title="Quarters Type" style="width:35%;text-align:left;"> ${cghs.quartersTypeMasterDTO.name}</display:column>
		<display:column title="Monthly Contribution" style="width:25%;text-align:right"> ${cghs.amount}</display:column>
		<display:column title="Effective From" style="width:20%;text-align:center;"><fmt:formatDate pattern="dd-MMM-yyyy" value="${cghs.effDate}"/> </display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editRSMasterDetails('${cghs.pk}','${cghs.amount}','${cghs.quarterTypeId}','<fmt:formatDate pattern="dd-MMM-yyyy" value="${cghs.effDate}"/>')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deletePayBillRecord('${cghs.pk}','ResidentialSecurity','RSMasterList')"/>
		</display:column>
	</display:table>
</div>

<script>
	 $jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	clearRSMasterDetails();
</script>