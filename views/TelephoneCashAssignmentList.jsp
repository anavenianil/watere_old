<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : TelephoneCashAssignmentList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt" %>
<div class="line"> <jsp:include page="Result.jsp"/>
</div>

<div id="dataTable">
	<display:table name="${telephone.teleCashDetails}" excludedParams="*"
		export="false" class="list" requestURI="" id="teleCash" pagesize="10"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column title="SFID" style="width:2%;vertical-align:middle">${teleCash.sfid}</display:column>
		<display:column title="EFF FROM" style="width:2%;vertical-align:middle"><fmt:formatDate value="${teleCash.fromDate}" pattern="dd-MMM-yyyy"/></display:column>
		<display:column title="EFF TO" style="width:2%;vertical-align:middle"><fmt:formatDate value="${teleCash.toDate}" pattern="dd-MMM-yyyy"/></display:column>
		<display:column style="width:5%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editTeleCashAssignmentMaster('${teleCash.id}','${teleCash.sfid}','<fmt:formatDate value="${teleCash.fromDate}" pattern="dd-MMM-yyyy"/>','<fmt:formatDate value="${teleCash.toDate}" pattern="dd-MMM-yyyy"/>')"/>
		</display:column>
		<display:column style="width:5%;text-align:center" title="Delete">
		<img src="./images/delete.gif" title="Delete" onclick="deleteTeleCashAssignmentMaster('${teleCash.id}')" />
		</display:column>
		</display:table>	   
</div>
	 <script>
			$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	  </script>
<!-- End : TelephoneCashAssignmentList.jsp -->




