<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : DoPartSearchList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div class="line">	
	<div id="Pagination">
		<display:table name="${sessionScope.doPartList}" excludedParams="*"
			export="false" class="list" requestURI="" id="doPartList" pagesize="10"
			sort="list">		
			<display:column title="Gazetted Type" style="width:10%">&nbsp;<c:if test="${doPartList.gazType=='G'}">Gazetted</c:if><c:if test="${doPartList.gazType=='NG'}">Non Gazetted</c:if>
			</display:column>	
			<%--<display:column title="DOPart Type" style="width:10%">&nbsp;${doPartList.doPartType}</display:column>--%>
			<display:column title="DOPart No" style="width:15%">&nbsp;${doPartList.doPartNumber}</display:column>
			<display:column title="DOPart Date" style="width:15%">&nbsp;<fmt:formatDate value="${doPartList.doPartDate}" pattern="dd-MMM-yyyy" /></display:column>
			<display:column title="Last DOPart No" style="width:12%">&nbsp;${doPartList.preDoPartNo}</display:column>
			<display:column title="Last DOPart Date" style="width:12%">&nbsp;<fmt:formatDate value="${doPartList.preDoPartDate}" pattern="dd-MMM-yyyy" /></display:column>
			<c:if test="${doPartList.status ne (null)}">
			<c:choose>
			<c:when test="${doPartList.status =='60'}">
			<display:column title="Status" style="width:15%">Freezed</display:column>
			</c:when>
			<c:otherwise>
			<display:column title="Status" style="width:15%">UnderWorking</display:column>
			</c:otherwise>
			</c:choose>
			</c:if>
			<display:column title="Admin Released Date" style="width:12%">&nbsp;<fmt:formatDate value="${doPartList.releasedDate}" pattern="dd-MMM-yyyy" /></display:column>
			<display:column title="Finance Accepted Date" style="width:12%">&nbsp;<fmt:formatDate value="${doPartList.acceptedDate}" pattern="dd-MMM-yyyy" /></display:column>
			<display:column title="PDF" style="width:5%" ><a href="javascript:leaveApplication('${doPartList.id}','doPartReport','pdf');">PDF</a></display:column>
			<display:column title="DOC" style="width:5%" ><a href="javascript:leaveApplication('${doPartList.id}','doPartReport','doc');">DOC</a></display:column>
		</display:table>
	</div>	
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End : DoPartSearchList.jsp -->
