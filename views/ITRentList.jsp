<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : ITSectionList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
    <jsp:include page="ArrearsResult.jsp"></jsp:include>	
</div>

<div id="dataTable">
   	<display:table name="${sessionScope.RentList}" excludedParams="*"
		export="false" class="list" requestURI="" id="sec" pagesize="12" sort="list">
		<display:column title="Month" style="width:20%;text-align:left">&nbsp;${sec.rentMonth}</display:column>
		<display:column title="Rent" style="width:20%;text-align:left">&nbsp;${sec.rent}</display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editITRentDetails('${sec.id}','${sec.rentMonth}','${sec.rent}','${sec.remarks}')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteITRentDetails('${sec.id}')" />
		</display:column>
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
</script>


<!-- End : ITSectionList.jsp -->
