<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : financialYearDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
    <jsp:include page="Result.jsp"></jsp:include>	
</div>
<div id="dataTable">
   	<display:table name="${sessionScope.FinYearList}" excludedParams="*"
		export="false" class="list" requestURI="" id="fin" pagesize="10" sort="list">
		<display:column title="Financial Year" style="width:10%;text-align:left">&nbsp;${fin.fyFrom} - ${fin.fyTo}</display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editFinYearDetails('${fin.id}','${fin.fyFrom}','${fin.fyTo}')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteFinYearDetails('${fin.id}','${fin.fyFrom}','${fin.fyTo}')" />
		</display:column>
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
</script>


<!-- End : financialYearDetails.jsp -->
