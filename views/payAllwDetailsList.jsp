<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Begin : PayBillCGHSMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div class="line">
	<jsp:include page="Result.jsp"></jsp:include>
</div>

<div id="Pagination">
   	<display:table name="${sessionScope.allwDetailsList}" excludedParams="*"
		export="false" class="list" requestURI="" id="allw" pagesize="10"
		sort="list">
		<display:column title="Configuration Type" style="width:60%;text-align:left">&nbsp;${allw.confTypeDetails.allwType}</display:column>
		<display:column title="Amount" style="width:15%;text-align:right">${allw.amount}</display:column>
		<display:column title="Effective From" style="width:40%;text-align:center"><fmt:formatDate pattern="dd-MMM-yyyy" value="${allw.effDate}"/></display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editPayAllwDetails('${allw.id}','${allw.allwTypeId}','${allw.amount}','<fmt:formatDate pattern="dd-MMM-yyyy" value="${allw.effDate}"/>')" />
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deletePayAllwDetails('${allw.id}')" />
		</display:column>
	</display:table>
</div>
<script>
 	$jq( function(){$jq("#Pagination").displayTagAjax('paging');})
 	clearPaybillCghsMaster();
</script>
<!-- End : PayBillCGHSMasterList.jsp -->