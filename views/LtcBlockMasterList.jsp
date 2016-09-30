<!-- Begin : LtcBlockMasterLIst.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div class="line">
	<div class="line">
		<jsp:include page="Result.jsp"></jsp:include>
	</div>
	<div id="Pagination" class="line">
		<display:table name="${sessionScope.blockYearsList}" excludedParams="*"
			export="false" class="list" requestURI="" id="dataList" pagesize="2"
			sort="list">
				<display:column title="Block Type" style="width:35%">${dataList.blockTypeDetails.name}</display:column>
				<display:column title="Starting Date" style="width:15%"><fmt:formatDate pattern="dd-MMM-yyyy" value="${dataList.formatStartingDate}" /></display:column>
				<display:column title="Ending Date" style="width:15%"><fmt:formatDate pattern="dd-MMM-yyyy" value="${dataList.formatEndingDate}" /></display:column>
				<display:column title="Extended Date" style="width:15%"><fmt:formatDate pattern="dd-MMM-yyyy" value="${dataList.formatExtendingDate}" /></display:column>
				<display:column style="width:10%;text-align:center" title="Edit">
					<img src="./images/edit.gif"
						onclick="editBlockYear('${dataList.id}','${dataList.blockTypeDetails.id}','<fmt:formatDate pattern="dd-MMM-yyyy" value="${dataList.formatStartingDate}" />','<fmt:formatDate pattern="dd-MMM-yyyy" value="${dataList.formatEndingDate}" />','<fmt:formatDate pattern="dd-MMM-yyyy" value="${dataList.formatExtendingDate}" />')" />
				</display:column>
				<display:column style="width:10%;text-align:center" title="Delete">
					<img src="./images/delete.gif"
						onclick="deleteBlockYear('${dataList.id}')" />
				</display:column>
		</display:table>
	</div>
	<script>
		$jq( function(){
			   $jq("#Pagination").displayTagAjax('logical');
			});	
	</script>
</div>
<!-- End : LtcBlockMasterLIst.jsp -->