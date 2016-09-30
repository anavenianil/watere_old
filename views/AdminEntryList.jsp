<!-- Begin : AdminEntryList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<div>
	<jsp:include page="RequestResult.jsp"></jsp:include>
</div>
<div id="Pagination">
	<display:table name="${sessionScope.AdminEntryList}" excludedParams="*"
		export="false" class="list" requestURI="" id="adminList" pagesize="10"
		sort="list">
		<display:column title="Type" style="width:5%" sortable="true">${adminList.requestType}</display:column>
		<display:column title="SFID" style="width:10%" sortable="true">${adminList.sfID}</display:column>
		<display:column title="Family Members" style="width:20%">&nbsp;	${adminList.members}		</display:column>
		<display:column title="Place of Visit" style="width:15%;">&nbsp;	${adminList.placeOfVisit}		</display:column>		
		<display:column title="LTC Type" style="width:20%;">&nbsp;	${adminList.ltcType}		</display:column>
		<display:column title="LTC Block Year" style="width:15%;">&nbsp;	${adminList.block}		</display:column>
		<display:column title="Departure Date" style="width:15%;"><fmt:formatDate pattern="dd-MMM-yyyy" value="${adminList.departureDate}"/>		</display:column>		
	</display:table>
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End : AdminEntryList.jsp -->