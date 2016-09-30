<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTReleaseAllVehicles.jsp  -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>




								 
	                             <%--  <div class="line" id="result1">--%>
		                              <div class="line">
										<display:table name="${sessionScope.AllocatedAllVehicles}" excludedParams="*"
											export="false" class="list" requestURI="" id="vehicle" pagesize="500" sort="list">
										
										
										    <display:column  title='RequestId'>
										    <c:if test="${vehicle.requestId !='0'}">
										    ${vehicle.requestId}
										    </c:if>
										    <c:if test="${vehicle.requestId eq '0'}">
										      ---
										    </c:if>
										    </display:column>
											<display:column  title='Alloted To'>${vehicle.sfid}</display:column>
											<display:column  title='Vehicle No' >${vehicle.vehicleDriverMapDetails.vehicleDetails.vehicleNo}(${vehicle.vehicleDriverMapDetails.vehicleDetails.vehiclePoolTypeDetails.vehiclePoolType})</display:column>
											<display:column  title='Driver Name' >${vehicle.vehicleDriverMapDetails.diverDetails.driverName}</display:column>

												<display:column  title='From Date & Time'><fmt:formatDate value="${vehicle.fromDate}" pattern="dd-MMM-yyyy HH:mm" /></display:column>

												<display:column  title='To Date & Time'><fmt:formatDate value="${vehicle.toDate}" pattern="dd-MMM-yyyy HH:mm" /></display:column>
												<display:column  title='Released Date & Time'><input path="todayDate" id='todayDate${vehicle.id}' maxlength="100" readonly="true" style="height:16px;width:80px;font-size: 11px;font-weight: bold;" value="<fmt:formatDate value='${vehicle.toDate}' pattern='dd-MMM-yyyy' />" onchange="javascript:onchangeReleaseDate('${vehicle.id}_${vehicle.vehicleDriverMapId}');"/>
												<img  src="./images/calendar.gif"   id='${vehicle.id}' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"todayDate${vehicle.id}",ifFormat : "%d-%b-%Y",showsTime : false,button :"${vehicle.id}",singleClick : true,step : 1});
														</script>
														<%-- <input id="fromTime${vehicle.id}" style="width:45px" readonly="readonly" class="fromTime${vehicle.id}" onmouseover="javascript:timePicker('fromTime${vehicle.id}');" value="<fmt:formatDate value='${vehicle.toDate}' pattern='HH:mm' />"/>--%>
														<input id="fromTime${vehicle.id}" style="width:45px" class="fromTime${vehicle.id}" value="<fmt:formatDate value='${vehicle.toDate}' pattern='HH:mm' />" onkeypress="javascript:onchangeReleaseDate('${vehicle.id}_${vehicle.vehicleDriverMapId}');"/>
												</display:column>
												<display:column  title='Release' ><input type="checkbox" name="" id="${vehicle.id}_${vehicle.vehicleDriverMapId}" onclick="javascript:releaseDatesCheck(id);checkNextAllocationForRelease(id);"/></display:column>
									
											
	
										</display:table>		
									</div>
									
										<c:if test="${fn:length(sessionScope.AllocatedAllVehicles) ne 0}">
											<div class="line" style="margin-left: 80%;"><br/>
											<div class="expbutton"><a onclick="javascript:releaseAllVehicles()"> <span>Submit</span></a></div>
											</div>
										</c:if>
									
							
								
								
								
<!-- End:MTReleaseAllVehicles.jsp  -->