<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin :GpfClosingBalanceList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div class="line"><jsp:include page="Result.jsp"></jsp:include></div>
<div class="line">
	<div id="Pagination">
		<display:table name="${sessionScope.gpfBalanceList}" excludedParams="*" export="false" class="list" requestURI="" id="loan" pagesize="10" sort="list">
		    <display:column title="SFID" style="width:20%"> ${loan.sfID}</display:column>
			<display:column title="Amount" style="width:30%;text-align:right">${loan.balance}</display:column>
			<display:column title="FromDate" style="width:20%"><fmt:formatDate pattern="dd-MMM-yyyy" value="${loan.fromDate}"/></display:column>
			<display:column title="ToDate" style="width:20%"><fmt:formatDate pattern="dd-MMM-yyyy" value="${loan.toDate}"/> </display:column>
			<display:column style="width:5%;text-align:center" title="Edit">
					<img src="./images/edit.gif" title="<center>Edit</center>"
						onclick="editGpfClosingBalance('${loan.id}','${loan.sfID}','${loan.balance}','<fmt:formatDate pattern="dd-MMM-yyyy" value="${loan.fromDate}"/>','<fmt:formatDate pattern="dd-MMM-yyyy" value="${loan.toDate}"/>')" />
			</display:column>
			<display:column style="width:5%;text-align:center" title="<center>Delete</center>">
				<img src="./images/delete.gif" title="Delete"
					onclick="deleteGpfClosingBalance('${loan.id}')" />
			</display:column>
			
				
				<display:column title="GPFClosing Report" style="width:10%"><a href="javascript:reportGpfClosingBalance('${loan.sfID}')">PDF</a></display:column>
										
			
		</display:table>
	</div>	
	<script>	
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End :GpfClosingBalanceList.jsp-->