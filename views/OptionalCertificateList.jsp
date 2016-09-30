<!-- Start : OptionalCertificateList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>

<div><jsp:include page="Result.jsp"></jsp:include></div>
<div id="Pagination">
	<display:table name="${sessionScope.OptionalCertificateList}" excludedParams="*"
		export="false" class="list" requestURI="" id="dataList" pagesize="10" sort="list">
		<display:column title="Sfid" style="width:10%" sortable="true" sortProperty="true">${dataList.sfID}</display:column>
		<display:column title="Scale of Pay" style="width:15%;"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${dataList.scaleOfPay}"/></display:column>
		<display:column title="Interview Date" style="width:15%"><fmt:formatDate pattern="dd-MMM-yyyy" value="${dataList.interviewDate}"/></display:column>
		<display:column title="Requested Date" style="width:15%"><fmt:formatDate pattern="dd-MMM-yyyy" value="${dataList.requestedDate}"/></display:column>
		<display:column title="Date of Increment" style="width:15%"><fmt:formatDate pattern="dd-MMM-yyyy" value="${dataList.incrementDate}"/></display:column>
		
		<c:choose>
		<c:when test="${dataList.payStatus==1}">
		<display:column title="Option Status" style="width:10%" sortable="true" sortProperty="true">Higher Grade</display:column>
		</c:when>
		<c:otherwise>
		<display:column title="Option Status" style="width:10%" sortable="true" sortProperty="true">Lower Grade</display:column>
		</c:otherwise>
		</c:choose>
		
			<display:column title="Report" style="width:20%"><a href="javascript:optionalCertificate('${dataList.id}')">Report</a></display:column>
		
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit"
				onclick="editOptionalCertificate('${dataList.id}','${dataList.payStatus}','<fmt:formatDate pattern="dd-MMM-yyyy" value="${dataList.incrementDate}"/>','${dataList.scaleOfPay}')" />
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete"
				onclick="deleteOptionalCertificate('${dataList.id}')" />
		</display:column>
	</display:table>
</div>
<script>	
	$jq( function(){$jq("#Pagination").displayTagAjax('paging');})
</script>
<!-- End : OptionalCertificateList.jsp -->
