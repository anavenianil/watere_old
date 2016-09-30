<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTDedicatedAllVehiclesList.jsp  -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" src="script/mt.js"></script>
<script></script>

	<c:if test="${message == 'freeSuccess'}"><span class="success"><spring:message code="freeSuccess"/></span></c:if>
    <c:if test="${message == 'freeFail'}"><span class="failure"><spring:message code="freeFail"/></span></c:if>
	<spring:bind path="mtBean">
	 							     <div class="line">
										<display:table name="${sessionScope.DedicatedVehiclesList}" excludedParams="*"
											export="false" class="list" requestURI="" id="dedicatedVehicle" pagesize="500" sort="list">
										<%int i=0; %>
										
											<display:column  title='Alloted To'>${dedicatedVehicle.vehicleDriverMapDetails.vehicleDetails.dedicatedPersonSfid}</display:column>
											<display:column  title='Vehicle No' >${dedicatedVehicle.vehicleDriverMapDetails.vehicleDetails.vehicleNo}(${dedicatedVehicle.vehicleDriverMapDetails.vehicleDetails.vehiclePoolTypeDetails.vehiclePoolType})</display:column>
											<display:column  title='Driver Name' >${dedicatedVehicle.vehicleDriverMapDetails.diverDetails.driverName}</display:column>

												<display:column  title='Free FromDate & Time'>
												<c:if test="${dedicatedVehicle.flag eq 'yes'}">
												<input path="fromDate" id='fromDate${dedicatedVehicle.id}' maxlength="100" readonly="true" style="height:16px;width:80px;font-size: 11px;font-weight: bold;" value="${mtBean.currentDate}" onchange="javascript:onchangeFreeDedicatedDate('${dedicatedVehicle.id}_${dedicatedVehicle.vehicleDriverMapId}');"/>
												</c:if>
												<c:if test="${dedicatedVehicle.flag ne 'yes'}">
												<input path="fromDate" id='fromDate${dedicatedVehicle.id}' maxlength="100" readonly="true" style="height:16px;width:80px;font-size: 11px;font-weight: bold;" value="<fmt:formatDate value='${dedicatedVehicle.fromDate}' pattern='dd-MMM-yyyy' />" onchange="javascript:onchangeFreeDedicatedDate('${dedicatedVehicle.id}_${dedicatedVehicle.vehicleDriverMapId}');"/>
												</c:if>
												<img  src="./images/calendar.gif"   id='fromB${dedicatedVehicle.id}' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"fromDate${dedicatedVehicle.id}",ifFormat : "%d-%b-%Y",showsTime : false,button :"fromB${dedicatedVehicle.id}",singleClick : true,step : 1});
														</script>
														<c:if test="${dedicatedVehicle.flag eq 'yes'}">
														<input id="fromTime${dedicatedVehicle.id}" style="width:45px" readonly="readonly" class="fromTime${dedicatedVehicle.id}" onmouseover="javascript:timePicker('fromTime${dedicatedVehicle.id}');" value="08:00" onchange="javascript:onchangeFreeDedicatedDate('${dedicatedVehicle.id}_${dedicatedVehicle.vehicleDriverMapId}');"/>
												</c:if>
												<c:if test="${dedicatedVehicle.flag ne 'yes'}">
														<input id="fromTime${dedicatedVehicle.id}" style="width:45px" readonly="readonly" class="fromTime${dedicatedVehicle.id}" onmouseover="javascript:timePicker('fromTime${dedicatedVehicle.id}');" value="<fmt:formatDate value='${dedicatedVehicle.fromDate}' pattern='HH:mm' />" onchange="javascript:onchangeFreeDedicatedDate('${dedicatedVehicle.id}_${dedicatedVehicle.vehicleDriverMapId}');"/>
												</c:if>
												</display:column>
												
												<display:column  title='Free ToDate & Time'>
												<input path="toDate" id='toDate${dedicatedVehicle.id}' maxlength="100" readonly="true" style="height:16px;width:80px;font-size: 11px;font-weight: bold;" onchange="javascript:onchangeFreeDedicatedDate('${dedicatedVehicle.id}_${dedicatedVehicle.vehicleDriverMapId}');"/><img  src="./images/calendar.gif"   id='toB${dedicatedVehicle.id}' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"toDate${dedicatedVehicle.id}",ifFormat : "%d-%b-%Y",showsTime : false,button :"toB${dedicatedVehicle.id}",singleClick : true,step : 1});
														</script>
														<input id="toTime${dedicatedVehicle.id}" style="width:45px" readonly="readonly" class="toTime${dedicatedVehicle.id}" onmouseover="javascript:timePicker('toTime${dedicatedVehicle.id}');" onchange="javascript:onchangeFreeDedicatedDate('${dedicatedVehicle.id}_${dedicatedVehicle.vehicleDriverMapId}');"/>
												</display:column>
												
												<display:column  title='Free' ><input type="checkbox" name="" id="${dedicatedVehicle.id}_${dedicatedVehicle.vehicleDriverMapId}" onclick="freeDedicatedDateCheck(id)"/></display:column>
									
											 
										</display:table>		
									</div>
									</spring:bind>
										<c:if test="${fn:length(sessionScope.DedicatedVehiclesList) ne 0}">
											<div class="line" style="margin-left: 80%;"><br/>
											<div class="expbutton"><a onclick="javascript:freeSelectedDedicatedVehicle()"> <span>Submit</span></a></div>
											</div>
										</c:if>
<!--end:MTDedicatedAllVehiclesList.jsp  -->