<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : ITSavingsMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
    <jsp:include page="Result.jsp"></jsp:include>	
</div>
<div id="dataTable">
   	<display:table name="${sessionScope.SavingsList}" excludedParams="*" export="false" class="list" requestURI="" id="sav" pagesize="10" sort="list">
		<display:column title="Savings Name" style="width:20%;text-align:left">&nbsp;${sav.sName}</display:column>
		<display:column title="Savings Type" style="width:10%;text-align:left">&nbsp;${sav.sType}</display:column>
		<display:column title="Section Name" style="width:10%;text-align:left">&nbsp;${sav.sectionDetails.secName}</display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editSavingsDetails('${sav.id}','${sav.sName}','${sav.sType}','${sav.sectionDetails.id}')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteSavingsDetails('${sav.id}','${sav.sName}','${sav.sType}','${sav.sectionDetails.secName}')" />
		</display:column>
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
</script>


<!-- End : ITSavingsMasterList.jsp -->
