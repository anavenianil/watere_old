<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : ITSavingsMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<%@page import="net.sf.json.JSONArray"%>
<div>
    <jsp:include page="Result.jsp"></jsp:include>	
</div>
<div id="dataTable">
   	<display:table name="${sessionScope.twoAddIncrList}" excludedParams="*" export="false" class="list" requestURI="" id="tai" pagesize="10" sort="list">
		<display:column title="Designation Name" style="width:20%;text-align:left">&nbsp;${tai.designationDetails.name}</display:column>
		<display:column title="Amount" style="width:20%;text-align:right;">&nbsp;${tai.amount}</display:column>
		<display:column title="Effective From" style="width:20%;text-align:center;">&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${tai.effDate}"/></display:column>
		<display:column style="width:10%;text-align:center;" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editTAIDetails('${tai.id}','${tai.designationDetails.id}','${tai.designationDetails.name}','${tai.amount}','<fmt:formatDate pattern="dd-MMM-yyyy" value="${tai.effDate}"/>')"/>
		</display:column>
		<display:column style="width:10%;text-align:center;" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteTAIDetails('${tai.id}');" />
		</display:column>
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	jsonPayDesigList = <%= (JSONArray)session.getAttribute("jsonPayDesigList")%>;
	jsonPayDesigIds = <%= (JSONArray)session.getAttribute("jsonPayDesigIds")%>;
</script>


<!-- End : ITSavingsMasterList.jsp -->
