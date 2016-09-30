<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : MTTravelAgencyList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
	<c:if test="${message eq 'failure'}"><div class="myStyle failure">Sorry, You Can not Delete This Record Due to Vehicles Exist For This Travel Agency</div></c:if>

<div id="dataTable">
   	<display:table name="${sessionScope.TravelList}" excludedParams="*"
		export="false" class="list" requestURI="" id="travel" pagesize="10" sort="list">
		<display:column  title='Travel Agency Name' >${travel.travelAgencyName}</display:column>
		<display:column  title='Contact Person Name' >${travel.contactPerson}</display:column>
		<display:column  title='MobileNo' >${travel.travelMobileNo}</display:column>
		<display:column  title='Address' >${travel.address}</display:column>
		<display:column style="width:7%;text-align:center" title="Edit">
			<%-- <img src="./images/edit.gif" title="Edit" onclick="editTravelAgencyDetails('${travel.travelId}','${travel.travelAgencyName}','${travel.contactPerson}','${travel.travelMobileNo}','${travel.address}')"/>--%>
		<img src="./images/edit.gif" title="Edit" onclick="editTravelAgencyDetails('${travel.travelId}')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteTravelAgencyDetails('${travel.travelId}')" />
		</display:column>
	</display:table>
</div>
<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	TravelAgencyListJson=<%=(net.sf.json.JSONArray)session.getAttribute("TravelListJson") %>;
	
</script>


<%-- End :  MTTravelAgencyList.jsp --%>
