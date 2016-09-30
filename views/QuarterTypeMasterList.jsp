<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :QuarterTypeMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div>
	<div id="Pagination">
	
		<display:table name="${sessionScope.quarterList}" excludedParams="*" export="false" class="list" requestURI="" id="quarter" pagesize="5" sort="list">
			<display:column title="Quarters  Type" style="width:20%">${quarter.quarterTypeDetails.name}</display:column>
	    	<display:column title="Quarters sub Type" style="width:20%"> ${quarter.quarterSubType}</display:column>
			<display:column style="width:10%;text-align:center" title="Edit">
				<img src="./images/edit.gif" title="Edit"
					onclick="editQuarterTypeDetails('${quarter.id}','${quarter.quarterSubType}','${quarter.quarterTypeDetails.id}')" />
			</display:column>
			<display:column style="width:10%;text-align:center" title="Delete">
				<img src="./images/delete.gif" title="Delete"
					onclick="deleteQuarterTypeDetails('${quarter.id}')" />
			</display:column>
		</display:table>
	</div>	
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End :QuarterTypeMasterList.jsp-->