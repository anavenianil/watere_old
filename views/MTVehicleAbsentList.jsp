<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : MTVehicleAbsentList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	<c:if test="${message eq 'exist'}"><div class="myStyle failure"">Vehicle With Given From Date and To Date Already Exist</div></c:if>

<div id="dataTable">
   	<display:table name="${sessionScope.AbsentVehicleList}" excludedParams="*"
		export="false" class="list" requestURI="" id="vehicle" pagesize="10" sort="list">
		<display:column  title='Vehicle No' >${vehicle.vehicleDetails.vehicleNo}</display:column>
		<display:column  title='From Date' ><fmt:formatDate value="${vehicle.fromDate}" pattern="dd-MMM-yyyy"/></display:column>
		<display:column  title='From Time' >${vehicle.fromTime}</display:column>
		<display:column  title='To Date' ><fmt:formatDate value="${vehicle.toDate}" pattern="dd-MMM-yyyy"/></display:column>
		<display:column  title='To Time' >${vehicle.toTime}</display:column>
		<display:column  title='Remarks' >${vehicle.remarks}</display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<%--<img src="./images/edit.gif" title="Edit" onclick="editVehicleAbsentDetails('${vehicle.id}','${vehicle.vehicleId}','<fmt:formatDate value="${vehicle.fromDate}" pattern="dd-MMM-yyyy"/>','${vehicle.fromTime}','<fmt:formatDate value="${vehicle.toDate}" pattern="dd-MMM-yyyy"/>','${vehicle.toTime}','${vehicle.remarks}')"/>--%>
		<img src="./images/edit.gif" title="Edit" onclick="editVehicleAbsentDetails('${vehicle.id}','<fmt:formatDate value="${vehicle.fromDate}" pattern="dd-MMM-yyyy"/>','<fmt:formatDate value="${vehicle.toDate}" pattern="dd-MMM-yyyy"/>')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteVehicleAbsentDetails('${vehicle.id}')" />
		</display:column>
	</display:table>
</div>

<script>

	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	AbsentVehicleListJson	= <%=(net.sf.json.JSONArray)session.getAttribute("AbsentVehicleListJson")%>;
	
</script>


<%-- End :  MTVehicleAbsentList.jsp --%>
