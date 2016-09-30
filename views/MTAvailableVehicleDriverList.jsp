<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : MTAvailableVehicleDriverList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div class="line" id="dataTable">
<fieldset><legend><strong><font color="green">Available Vehicle-Driver Details </font></strong></legend>
	<display:table name="${sessionScope.VDList}" excludedParams="*"
			export="false" class="list" requestURI="" id="vd" pagesize="50" sort="list">
			<c:if test="${mtBean.remarks eq 'editAlloc'}">	
		<display:column  title='Select' ><input type="checkbox" id="${vd.id}" onchange="javascript:editAllotOrCombineButtons('${mtBean.type}','allocate');"></input></display:column>											
		</c:if>
		<c:if test="${mtBean.remarks ne 'editAlloc'}">
		<display:column  title='Select' ><input type="checkbox" id="${vd.id}" onchange="javascript:allotOrCombineButtons('${mtBean.type}','allocate');"></input></display:column>
		</c:if>
		<display:column  title='Vehicle No'>${vd.vehicleDetails.vehicleNo}(${vd.vehicleDetails.vehiclePoolTypeDetails.vehiclePoolType} to ${vd.vehicleDetails.dedicatedPersonSfid})</display:column>
		<display:column  title='Vehicle Type' >${vd.vehicleDetails.vehicleTypeDetails.vehicleType}</display:column>
		<display:column  title='Driver Name' >${vd.diverDetails.driverName}</display:column>
		<display:column  title='Driver Mobile No'>${vd.diverDetails.driverMobileNo}</display:column>
		<display:column  title='Vehicle Capacity'>${vd.vehicleDetails.noOfPeople}</display:column>
		<%--<display:column  title='Allocated No.Of Persons'></display:column>	--%>									
	</display:table>	
	
	
</fieldset>
<script>
 BusyVDListJSON	= <%=(net.sf.json.JSONArray)session.getAttribute("BusyVDListJSON")%>;
 //$jq( function(){ $jq("#dataTable").displayTagAjax('paging')})

</script>
												  								
</div>

							
<%-- End :  MTAvailableVehicleDriverList.jsp --%>
