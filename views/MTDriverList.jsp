<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : MTDriverList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
	<c:if test="${message=='exist'}"> <span class="failure">Sorry, You Can not Delete This Driver, It is Mapped to Vehicle </span></c:if>
<div id="dataTable">
   	<display:table name="${sessionScope.driverList}" excludedParams="*"
		export="false" class="list" requestURI="" id="driver" pagesize="10" sort="list">
		<display:column  title='Driver Name' >${driver.driverName}</display:column>
		<display:column  title='Driver Type' >${driver.driverTypeDetails.vehicleType}</display:column>
		<display:column  title='Driver Id' >${driver.driverIdSfid}</display:column>
		
		<display:column  title='MobileNo' style="text-align:right">${driver.driverMobileNo}</display:column>
		<%--<display:column  title='Travel Agency Name' >${driver.driverTravelAgencyNameDetail}</display:column>--%>
		<display:column style="width:10%;text-align:center" title="Edit">
			<%--<img src="./images/edit.gif" title="Edit" onclick="editDriverDetails('${driver.driverId}','${driver.driverType}','${driver.driverIdSfid}','${driver.driverName}','${driver.driverMobileNo}','${driver.driverTravelAgencyName}')"/>--%>
		<img src="./images/edit.gif" title="Edit" onclick="editDriverDetails('${driver.driverId}')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteDriverDetails('${driver.driverId}')" />
		</display:column>
	</display:table>
</div>
<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	 driverListJson	= <%=(net.sf.json.JSONArray)session.getAttribute("driverListJson")%>;
	
</script>


<%-- End :  MTDriverList.jsp --%>
