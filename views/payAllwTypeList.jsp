<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Begin : PayBillCGHSMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div class="line">
	<jsp:include page="Result.jsp"></jsp:include>
</div>

<div id="Pagination">
   	<display:table name="${sessionScope.allwTypeList}" excludedParams="*"
		export="false" class="list" requestURI="" id="allw" pagesize="10"
		sort="list">
		<display:column title="Configuration Name" style="width:45%;text-align:left">&nbsp;${allw.allwType}</display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editPayAllwType('${allw.id}','${allw.allwType}')" />
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deletePayAllwType('${allw.id}')" />
		</display:column>
	</display:table>
</div>
<script>
 	$jq( function(){$jq("#Pagination").displayTagAjax('paging');})
 </script>
<!-- End : PayBillCGHSMasterList.jsp -->