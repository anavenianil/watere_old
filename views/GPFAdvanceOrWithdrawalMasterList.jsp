<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :GPFAdvanceOrWithdrawalMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>

<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div>
	<div id="Pagination">
		<display:table name="${sessionScope.gpfList}" excludedParams="*" export="false" class="list" requestURI="" id="loan" pagesize="1" sort="list">
		    <display:column title="Type" style="width:20%"> ${loan.gpfTypeDetails.name}</display:column>
			<display:column title="Purpose" style="width:30%">${loan.purposeName}</display:column>
			<display:column title="Rule No" style="width:20%"> ${loan.ruleNo}</display:column>
			<display:column style="width:15%;text-align:center" title="Edit">
					<img src="./images/edit.gif" title="Edit"
						onclick="editGPFAdvanceOrWithdrawlDetails('${loan.id}','${loan.gpfType}','${loan.purposeName}','${loan.ruleNo}')" />
			</display:column>
			<display:column style="width:15%;text-align:center" title="Delete">
				<img src="./images/delete.gif" title="Delete"
					onclick="deleteGPFAdvanceOrWithdrawlDetails('${loan.id}')" />
			</display:column>
		</display:table>
	</div>	
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End :GPFAdvanceOrWithdrawalMasterList.jsp-->