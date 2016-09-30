<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : payBandMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div>
<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="dataTable">
   	<display:table name="${sessionScope.payBandListDetails}" excludedParams="*"
		export="false" class="list" requestURI="" id="pay" pagesize="10" sort="list">
		<display:column title="Pay Band Name" style="width:25%;text-align:left">${pay.name}</display:column>
		<display:column title="Pay Range From" style="width:20%;text-align:right">${pay.rangeFrom}</display:column>
		<display:column title="Pay Range To" style="width:20%;text-align:right">${pay.rangeTo}</display:column>
		<display:column title="Effective From" style="width:20%;text-align:center;"><fmt:formatDate pattern="dd-MMM-yyyy" value="${pay.effDate}"/></display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="payBandEditDetails('${pay.id}','${pay.name}','${pay.rangeFrom}','${pay.rangeTo}','<fmt:formatDate pattern="dd-MMM-yyyy" value="${pay.effDate}"/>')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="payBandDeleteDetails('${pay.id}')" />
		</display:column>
	</display:table>
</div>
<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
</script>


<!-- End : payBandMasterList.jsp -->
