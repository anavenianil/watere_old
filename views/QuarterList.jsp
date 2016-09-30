<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : QuarterList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div class="line">
	<jsp:include page="Result.jsp" />
</div>
<div class="line">
	<div id="Pagination">
			<display:table name="${sessionScope.quartersList}" excludedParams="*"
				export="false" class="list" requestURI="" id="quarterData" pagesize="5" sort="list">
			<display:column title="Quarter Type" style="width:10%;">${quarterData.quarter}</display:column>
			<display:column title="Quarter Sub Type" style="width:10%;">${quarterData.quartersType}</display:column>
			<display:column title="Quarter Number" style="width:20%;">&nbsp;${quarterData.quarterNo}</display:column>
			<display:column title="Occupied Date" style="width:15%;">&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${quarterData.occupiedDate}"/></display:column>
			
			<display:column style="width:5%;text-align:center" title="Edit">
				<img src="./images/edit.gif" title="Edit"
					onclick="editQuarter('${quarterData.id}','${quarterData.quarterSubTypeID}','${quarterData.quarterNo}','<fmt:formatDate pattern="dd-MMM-yyyy" value="${quarterData.occupiedDate}"/>')" />
			</display:column>
			<display:column style="width:5%;text-align:center" title="Delete">
				<img src="./images/delete.gif" title="Delete"
					onclick="deleteQuarter('${quarterData.id}')" />
			</display:column>
		</display:table>			
	</div>		
</div>	

<script>
	$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
</script>
<!-- End : QuarterList.jsp -->