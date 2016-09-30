<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start :  MTVehicleList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
	<c:if test="${message=='exist'}"> <span class="failure">Sorry, You Can not Delete This Vehicle, It is Mapped to Driver </span></c:if>
	<c:if test="${message=='invalid'}"><span class="failure"><spring:message code="Invalid"/></span></c:if>
	
<div id="dataTable">
   	<display:table name="${sessionScope.VehicleList}" excludedParams="*"
		export="false" class="list" requestURI="" id="vehicle" pagesize="10" sort="list">
		<display:column  title='Vehicle Model' >${vehicle.modelMasterDetails.modelName}</display:column>
		<display:column  title='Vehicle Type' >${vehicle.vehicleTypeDetails.vehicleType}</display:column>
		<display:column  title='Pool Type' >${vehicle.vehiclePoolTypeDetails.vehiclePoolType}</display:column>
		<display:column  title='Dedicated Sfid' >${vehicle.dedicatedPersonSfid}</display:column>
		<display:column  title='Vehicle Category' >${vehicle.modelMasterDetails.categoryDetails.categoryName}</display:column>
		
		<display:column  title='Vehicle Name' >${vehicle.vehicleName}</display:column>
		<display:column  title='Vehicle No' >${vehicle.vehicleNo}</display:column>
		<display:column  title='Travel Agency Name' >${vehicle.vehicleTravelAgencyNameDetail}</display:column>
		<display:column  title='Driver Name' >${vehicle.driverNameString}</display:column>
		<display:column  title='Driver Mobile No' >${vehicle.driverMobileNo}</display:column>
		<display:column  title='No.of Persons /Load Capacity' >${vehicle.noOfPeople}</display:column>
		<display:column  title='Fuel Type' >${vehicle.fuelTypeDetails.fuelName}</display:column>
		<display:column  title='Tank Capacity (Ltrs)' >${vehicle.fuelCapacity}</display:column>
		<display:column  title='KPL' >${vehicle.mileage}</display:column>
		<display:column  title='Initial Reading' >${vehicle.initialReading}</display:column>
		<display:column  title='Sensitive Type' >${vehicle.vehicleSensitiveTypeDetails.vehicleSensitiveType}</display:column>
		<%--<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editVehicleDetails('${vehicle.vehicleId}','${vehicle.vehicleType}','${vehicle.vehicleCategoryId}',
			'${vehicle.vehicleModelId}','${vehicle.vehicleName}','${vehicle.vehicleNo}','${vehicle.vehicleTravelAgencyName}','${vehicle.driverName}','${vehicle.driverMobileNo}',
			'${vehicle.noOfPeople}','${vehicle.fuelCapacity}','${vehicle.mileage}','${vehicle.initialReading}','${vehicle.vehiclePoolType}','${vehicle.vehicleSensitiveType}','${vehicle.dedicatedPersonSfid}','${vehicle.fuelTypeDetails.fuelId}')"/>
		</display:column>--%>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editVehicleDetails('${vehicle.vehicleId}','${vehicle.vehicleType}','${vehicle.vehicleCategoryId}',
			'${vehicle.vehicleModelId}','${vehicle.vehicleName}','${vehicle.vehicleNo}','${vehicle.vehicleTravelAgencyName}','${vehicle.driverName}','${vehicle.driverMobileNo}',
			'${vehicle.noOfPeople}','${vehicle.fuelCapacity}','${vehicle.mileage}','${vehicle.initialReading}','${vehicle.vehiclePoolType}','${vehicle.vehicleSensitiveType}','${vehicle.fuelTypeDetails.fuelId}')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Del">
			<img src="./images/delete.gif" title="Delete" onclick="deleteVehicleDetails('${vehicle.vehicleId}')" />
		</display:column>
	</display:table>
</div>
<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	var vehicleListJson=<%= (net.sf.json.JSONArray)session.getAttribute("vehicleListJson")%>;
	hiredDriversJson	= <%=(net.sf.json.JSONArray)session.getAttribute("hiredDriversJson")%>;
</script>


<%-- End :   MTVehicleList.jsp --%>
