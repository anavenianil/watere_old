<!-- Begin : PayScaleMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div>
	<div id="Pagination">
		<display:table name="${sessionScope.payScaleList}" excludedParams="*"
				export="false" class="list" requestURI="" id="payscale" pagesize="3"
				sort="list">
			<display:column titleKey="${sessionScope.masterType}.name" style="width:25%;text-align:right;" sortable="false">&nbsp;${payscale.name}</display:column>
			<display:column titleKey="${sessionScope.masterType}.key" style="width:25%;text-align:right;" sortable="false">&nbsp;${payscale.secondTypeValue}</display:column>
			<display:column title="Effective From" style="width:25%;text-align:center;">${payscale.effDateString}</display:column>
			<display:column style="width:12%;text-align:center" title="Edit">
				<img src="./images/edit.gif"
					onclick="editPayScale('${payscale.id}','${payscale.name}','${payscale.secondTypeValue}','${payscale.effDateString}')" />
			</display:column>
			<display:column style="width:12%;text-align:center" title="Delete">
				<img src="./images/delete.gif"
					onclick="deletePayScale('${payscale.id}','${sessionScope.masterType}')" />
			</display:column>
		</display:table>
	</div>		
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
		jsonPayScaleList = <%= (net.sf.json.JSONArray)session.getAttribute("jsonPayScaleList")%>;
	</script>
</div>
<!-- end : PayScaleMasterList.jsp -->
