<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTVehicleReleaseList.jsp  -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" src="script/mt.js"></script>

	<c:if test="${message eq 'ReleaseVehicle'}"><div class="myStyle success">You Have Successfully Released the Vehicle</div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	
	 							    <div class="line">
										<display:table name="${sessionScope.AllocatedVehicles}" excludedParams="*"
											export="false" class="list" requestURI="" id="vehicle" pagesize="10" sort="list">
											<display:column  title='Vehicle No' >${vehicle.vehicleDriverMapDetails.vehicleDetails.vehicleNo}(${vehicle.vehicleDriverMapDetails.vehicleDetails.vehiclePoolTypeDetails.vehiclePoolType})</display:column>
											<display:column  title='Driver Name' >${vehicle.vehicleDriverMapDetails.diverDetails.driverName}</display:column>
										<c:if test="${vehicle.toDate ne null}">
												<display:column  title='From Date & Time'><fmt:formatDate value="${vehicle.fromDate}" pattern="dd-MMM-yyyy HH-mm" /></display:column>
												<display:column  title='To Date & Time'><fmt:formatDate value="${vehicle.toDate}" pattern="dd-MMM-yyyy HH-mm" /></display:column>
												<display:column  title='Released Date & Time'><form:input path="todayDate" id='todayDate${vehicle.id}' maxlength="100" readonly="true"/><img  src="./images/calendar.gif"   id='${vehicle.id}' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"todayDate${vehicle.id}",ifFormat : "%d-%b-%Y %H-%M ",showsTime : true,button :"${vehicle.id}",singleClick : true,step : 1});
														</script>
												</display:column>
												<display:column  title='Release' ><input type="checkbox" name="" id="${vehicle.id}#${vehicle.vehicleDriverMapId}" /></display:column>
											
										</c:if>
										<c:if test="${vehicle.toDate eq null}">
											
											<display:column  title='From Date & Time'><form:input path="fromDate" id="fromDate" maxlength="100" readonly="true"/><img  src="./images/calendar.gif"   id="fromDate1" />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y %H-%M ",showsTime : true,button :"fromDate1",singleClick : true,step : 1});
														</script>
											 </display:column>
											<display:column  title='To Date & Time'><form:input path="toDate" id="toDate" maxlength="100" readonly="true"/><img  src="./images/calendar.gif"   id="toDate1" />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y %H-%M ",showsTime : true,button :"toDate1",singleClick : true,step : 1});
														</script>
											</display:column>
											<display:column  title='Released Date & Time'><form:input path="todayDate" id='todayDate${vehicle.id}' maxlength="100" readonly="true"/><img  src="./images/calendar.gif"   id='${vehicle.id}' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"todayDate${vehicle.id}",ifFormat : "%d-%b-%Y %H-%M ",showsTime : true,button :"${vehicle.id}",singleClick : true,step : 1});
														</script>
											</display:column>
											
												<display:column  title='Release' ><input type="checkbox" name="" id="${vehicle.id}@${vehicle.vehicleDriverMapId}" /></display:column>
										</c:if>
										</display:table>	
										<c:if test="${fn:length(sessionScope.AllocatedVehicles) ne 0}">
												<div class="line" style="margin-left: 80%;"><br/>
												<div class="expbutton"><a onclick="javascript:releaseSelectedVehicles()"> <span>Submit</span></a></div>
												</div>
										</c:if>	
								</div>
<!-- End:MTVehicleReleaseList  -->