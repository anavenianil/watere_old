<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : MTMileageMasterList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>


	<c:if test="${message eq 'success'}"><div class="myStyle success""><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
	<c:if test="${message eq 'exist'}"><div class="myStyle failure">Already Details Exist For The Given Vehicle With The Given Date</div></c:if>
	<c:if test="${message eq 'getPreDayClosingRDE'}">
		<script>
			$jq('#startingMilometerRde').val('');
			$jq('#startingMilometerRde').val('${mtBean.vehicleMileageDTO.endingMilometerRde}');
			$jq('#natureOfDuty').val('${mtBean.vehicleMileageDTO.natureOfDuty}');
			$jq('#fromPlace').val('${mtBean.vehicleMileageDTO.fromPlace}');
			$jq('#toPlace').val('${mtBean.vehicleMileageDTO.toPlace}');
			if(${mtBean.vehicleMileageDTO.fuelConsumed eq '1'}){
			$jq("#fuel1").attr('checked', true);
			getPOLDiv('YES');
			 pFuelMRD = ${mtBean.vehicleMileageDTO.fuelMRD};
			 pFuel = ${mtBean.vehicleMileageDTO.fuel};
			$jq('#fuel').val('${mtBean.vehicleMileageDTO.fuel}');
			$jq('#fuelMRD').val('${mtBean.vehicleMileageDTO.fuelMRD}');
			}else{
			$jq("#fuel2").attr('checked', true);
			}
			if(${mtBean.vehicleMileageDTO.engineOilConsumed eq '1'}){
			$jq("#engineOil1").attr('checked', true);
			getEngOilDiv('YES');
			pEngineOilMRD = ${mtBean.vehicleMileageDTO.engineOilMRD};
			pEngineOil = ${mtBean.vehicleMileageDTO.engineOil};
			$jq('#engineOil').val('${mtBean.vehicleMileageDTO.engineOil}');
			$jq('#engineOilMRD').val('${mtBean.vehicleMileageDTO.engineOilMRD}');
			}else{
			$jq("#engineOil2").attr('checked', true);
			}
			if(${mtBean.vehicleMileageDTO.coolentConsumed eq '1'}){
			$jq("#coolent1").attr('checked', true);
			getCoolentDiv('YES');
			pCoolentMRD = ${mtBean.vehicleMileageDTO.coolentMRD};
			pCoolent = ${mtBean.vehicleMileageDTO.coolent};
			$jq('#coolent').val('${mtBean.vehicleMileageDTO.coolent}');
			$jq('#coolentMRD').val('${mtBean.vehicleMileageDTO.coolentMRD}');
			}else{
			$jq("#coolent2").attr('checked', true);
			}
		</script>
	</c:if>
<div id="dataTable">
   	<display:table name="${sessionScope.MileageList}" excludedParams="*"
		export="false" class="list" requestURI="" id="mileage" pagesize="10" sort="list">
		<display:column  title='Date' ><fmt:formatDate value="${mileage.todayDate}" pattern="dd-MMM-yyyy"/></display:column>
		<display:column  title='Vehicle No' >${mileage.vehicleDetails.vehicleNo}</display:column>
		<display:column  title='Nature Of Duty' >${mileage.natureOfDuty}</display:column>
		<display:column  title='From Place' >${mileage.fromPlace}</display:column>
		<display:column  title='To Place' >${mileage.toPlace}</display:column>
		<display:column  title='Starting Kilometer Rde' >${mileage.startingMilometerRde}</display:column>
		<display:column  title='Closing Kilometer Rde' >${mileage.endingMilometerRde}</display:column>
		<display:column  title='Total Kilometers' >${mileage.totKilometers}</display:column>
		<display:column  title='P.O.L Quantity(Ltrs)/Meter Reading' >${mileage.fuel}/${mileage.fuelMRD}</display:column>
		<display:column  title='Eng. Oil Quantity(Ltrs)/Meter Reading' >${mileage.engineOil}/${mileage.engineOilMRD}</display:column>
		<display:column  title='Coolent(Ltrs)/Meter Reading' >${mileage.coolent}/${mileage.coolentMRD}</display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editMileageDetails('${mileage.id}','<fmt:formatDate value="${mileage.todayDate}" pattern="dd-MMM-yyyy"/>','${mileage.vehicleDetails.vehicleId}','${mileage.natureOfDuty}','${mileage.fromPlace}','${mileage.toPlace}','${mileage.startingMilometerRde}','${mileage.endingMilometerRde}',
			'${mileage.totKilometers}','${mileage.fuel}','${mileage.engineOil}','${mileage.coolent}','${mileage.fuelMRD}','${mileage.engineOilMRD}','${mileage.coolentMRD}','${mileage.fuelConsumed}','${mileage.engineOilConsumed}','${mileage.coolentConsumed}')"/>
		</display:column>
		<display:column style="width:10%;text-align:center" title="Delete">
			<img src="./images/delete.gif" title="Delete" onclick="deleteMileageDetails('${mileage.id}')" />
		</display:column>
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
	
</script>


<%-- End :  MTMileageMasterList.jsp --%>
