<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:PayBillDAMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div>
	<jsp:include page="Result.jsp"></jsp:include>
</div>
<div id="Pagination">
   	<display:table name="${sessionScope.dearNessAllowanceMasterDetails}" excludedParams="*" export="false" class="list" requestURI="" id="cghs" pagesize="10" sort="list">
		<display:column title="Year" style="width:30%;text-align:center;"> ${cghs.displayDate}</display:column>
		<display:column title="Rate of DA" style="width:30%;text-align:right"> ${cghs.daValue}</display:column>
		<display:column style="width:15%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editDAMasterDetails('${cghs.pk}','${cghs.displayDate}','${cghs.daValue}')"/>
		</display:column>
		<display:column style="width:15%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deletePayBillRecord('${cghs.pk}','dearNessAllowance','DAMasterList')"/>
		</display:column>
	</display:table>
</div>
<script>
	$jq( function()
	{ $jq("#Pagination").displayTagAjax('paging');
	})
</script>
<!-- end:PayBillDAMasterList.jsp -->