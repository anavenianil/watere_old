<!-- Begin : PayFixationListDOPartList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div id="resultMsg">
	<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="Pagination">

	<display:table name="${sessionScope.payFixationDOPartDetails}" excludedParams="*"
		export="false" class="list" requestURI="" id="DOPartList" pagesize="1000"
		sort="list" >		
		<display:column title="DO Part NO" style="width:33%" >${DOPartList.doPartNo}</display:column>				
		<display:column title="DO Part Date" style="width:33%" ><fmt:formatDate pattern="dd-MMM-yyyy" value="${DOPartList.formatDoPartDate}"/></display:column>
		<display:column title="Report" style="width:34%"><a href="javascript:viewPayFixationDOPart('${DOPartList.id}')">PDF</a></display:column>				
	</display:table>
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End : PayFixationDOPartList.jsp -->