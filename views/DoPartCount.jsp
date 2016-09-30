<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :LoanImmediateReliefList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>

<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div>
<div id="Pagination">
	<display:table name="${sessionScope.doPartList}" excludedParams="*"
		export="false" class="list" requestURI="" id="doList" pagesize="10"
		sort="list">
		<c:if test="${not empty doList.year}">
		<display:column title="Year" style="width:30%">&nbsp;${doList.year}</display:column>
		</c:if>
		<c:if test="${not empty doList.month}">
		<display:column title="Month" style="width:30%">&nbsp;${doList.month}</display:column>
		</c:if>
		<c:if test="${not empty doList.doPartDate}">
		<display:column title="Date" style="width:50%">&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${doList.doPartDate}"/></display:column>
		<display:column title="PDF Report" style="width:25%">&nbsp; <a href="javascript:leaveApplication('${doList.id}','doPartReport','pdf');"> PDF </a></display:column>
		<display:column title="DOC Report" style="width:25%">&nbsp; <a href="javascript:leaveApplication('${doList.id}','doPartReport','doc');">DOC </a></display:column>
		</c:if>
		<c:if test="${empty doList.doPartDate}">
		<display:column title="Count" style="width:30%"><a href="javascript:getOrderedDoList('${doList.year}','${doList.typeId}','${doList.month}')">${doList.count}</a></display:column>
	    </c:if>
	</display:table>	
	</div>
	</div>
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
	
<!-- End :LoanImmediateReliefList.jsp -->