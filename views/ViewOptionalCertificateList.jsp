<!-- Begin : ViewOptionalCertificateList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div class="line">
	<c:if test="${message=='empNotExists' || Result=='empNotExists'}"> <span class="failure"><spring:message code="empNotExists"/></span></c:if>
</div>
<div id="Pagination">

	<display:table name="${sessionScope.viewOptionalCertificateDetails}" excludedParams="*"
		export="false" class="list" requestURI="" id="OCList" pagesize="1000"
		sort="list" >		
		<display:column title="SFID" style="width:10%" >${OCList.sfID}</display:column>				
		<display:column title="Name" style="width:20%" >${OCList.empName}</display:column>
		<display:column title="Interview Date" style="width:15%" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${OCList.interviewDate}"/></display:column>				
		<display:column title="Promotion Date" style="width:15%" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${OCList.promotionDate}"/></display:column>				
						
		<display:column title="Scale of Pay" style="width:15%;text-align:right" >${OCList.scaleOfPay}</display:column>						
		<display:column title="Report" style="width:10%"><a href="javascript:optionalCertificate('${OCList.id}')">PDF</a></display:column>				
	</display:table>
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End : ViewOptionalCertificateList.js -->