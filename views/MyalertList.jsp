<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MyalertList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div id="Pagination">
	<display:table name="${sessionScope.alertList}" excludedParams="*"
		export="false" class="list" requestURI="" id="allRequest" pagesize="10"
		sort="list">
		<display:column title="AlertType" style="width:20%;" ><a href="javascript:getAlertDetails('${allRequest.id}')">${allRequest.alertDetails.alert},</a></display:column>
		<display:column title="Date" style="width:20%;">&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${allRequest.assignedDate}"/></display:column>	
	</display:table>
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- end:MyalertList.jsp  -->
