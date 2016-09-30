<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : PayBillCGEISMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>

<div><jsp:include page="Result.jsp"></jsp:include></div>
<div id="dataTable">
   	<display:table name="${sessionScope.cgeisMasterDetails}" excludedParams="*"
		export="false" class="list" requestURI="" id="cghs" pagesize="10" sort="list">
		<display:column title="Group Name" style="width:20%;text-align:left">&nbsp;${cghs.groupMasterDTO.name}</display:column>
		<display:column title="Before Member" style="width:25%;text-align:right">&nbsp;${cghs.beforeMember}</display:column>
		<display:column title="After Member" style="width:25%;text-align:right">&nbsp;${cghs.afterMember}</display:column>
		<display:column style="width:15%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editCgiesMasterDetails('${cghs.pk}','${cghs.beforeMember}','${cghs.afterMember}','${cghs.groupId}','${cghs.grpInsuranceDate}')"/>
		</display:column>
		<display:column style="width:15%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deletePayBillRecord('${cghs.pk}','cgeis','cgeisMasterList')" />
		</display:column>
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	clearCgeisMasterDetails();
</script>


<!-- End : PayBillCGEISMasterList.jsp -->
