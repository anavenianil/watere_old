<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : MTVehicleVsDriverListt.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure">Sorry, You Can not Delete This Vehicle Due to Allocations Are There..</div></c:if>
	<c:if test="${message eq 'exist'}"><div class="myStyle failure"">The Given Vehicle or Driver Already Exists</div></c:if>
	

<div id="dataTable">
   	<display:table name="${sessionScope.VehicleDriverList}" excludedParams="*"
		export="false" class="list" requestURI="" id="vehicledriver" pagesize="500" sort="list">
		<display:column  title='Driver Name' >${vehicledriver.diverDetails.driverName}</display:column>
		<display:column  title='Vehicle No' >${vehicledriver.vehicleDetails.vehicleNo}(${vehicledriver.vehicleDetails.vehiclePoolTypeDetails.vehiclePoolType})</display:column>
		
		<display:column  title='DedicatedTo' >${vehicledriver.vehicleDetails.dedicatedPersonSfid}</display:column>
		
		<display:column  title='Allotment Date' ><fmt:formatDate value="${vehicledriver.allotmentFromDate}" pattern="dd-MMM-yyyy"/></display:column>
		<display:column  title='Allotment Time' >${vehicledriver.allotmentFromTime}</display:column>
		<display:column  title='Remarks' >${vehicledriver.remarks}</display:column>
		<%--<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editVehicleDriverDetails('${vehicledriver.id}','${vehicledriver.vehicleId}','${vehicledriver.driverId}','<fmt:formatDate value="${vehicledriver.allotmentFromDate}" pattern="dd-MMM-yyyy"/>','${vehicledriver.allotmentFromTime}','${vehicledriver.remarks}')"/>
		</display:column>--%>
		<%--<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteVehicleDriverDetails('${vehicledriver.id}')" />
		</display:column>--%>
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	DriverEmployeeListJson= <%= (net.sf.json.JSONArray)session.getAttribute("DriverEmployeeListJson") %>;
	VehicleListJson= <%= (net.sf.json.JSONArray)session.getAttribute("VehicleListJson") %>;
</script>


<%-- End :  MTVehicleVsDriverList.jsp --%>
