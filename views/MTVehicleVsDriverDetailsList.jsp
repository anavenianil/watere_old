<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : MTVehicleVsDriverDetailsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

	<c:if test="${message eq 'success'}"><div class="myStylesuccess"><spring:message code="success"/></div></c:if>
	<c:if test="${message eq 'update'}"><div class="myStyle success"><spring:message code="update"/></div></c:if>
	<c:if test="${message eq 'delete'}"><div class="myStyle success"><spring:message code="delete"/></div></c:if> 
	<c:if test="${message eq 'failure'}"><div class="myStyle failure"><spring:message code="failure"/></div></c:if>
	<c:if test="${message eq 'duplicate'}"><div class="myStyle failure"><spring:message code="duplicate"/></div></c:if>
<%int i=0;int j=0; int k=0; int m=0; %>
							<div id="dataTable">
								 
										   		<display:table name="${sessionScope.VehicleDriverList}" excludedParams="*"
													export="false" class="list" requestURI="" id="vehicledriver" pagesize="100" sort="list">
													<%j=0;k=0; m=0; %>
														<display:column  title='Sl No' ><%=++i %></display:column>
														<display:column  title='Vehicle No'>
															<c:forEach var="abVehicle" items="${sessionScope.VehicleAbsentList}">
																<c:if test="${abVehicle.key eq vehicledriver.vehicleId}"><font color="red">${vehicledriver.vehicleDetails.vehicleNo}(${vehicledriver.vehicleDetails.vehiclePoolTypeDetails.vehiclePoolType})</font>(${abVehicle.name} to ${abVehicle.value})
																<%++j; %>
																</c:if>
															</c:forEach>
																<%if(j==0){%>
																	${vehicledriver.vehicleDetails.vehicleNo}(${vehicledriver.vehicleDetails.vehiclePoolTypeDetails.vehiclePoolType})
																<%} %>
														</display:column>
														
														<display:column  title='Driver Name'>
														   <c:if test="${vehicledriver.driverId ne 0}">
															<c:forEach var="abDriver" items="${sessionScope.DriverAbsentList}">
																<c:if test="${abDriver.key eq vehicledriver.driverId}"><font color="red">${vehicledriver.diverDetails.driverName}(${abDriver.name} to ${abDriver.value})</font>(<fmt:formatDate pattern="dd-MMM-yyyy HH:mm" value="${vehicledriver.allotmentFromDate}"/>)
																<%++k; %>
																</c:if>
															</c:forEach>
																<%if(k==0){%>
																<%--<c:forEach var="abVehicleDriver" items="${sessionScope.AbsentVehicleDriverList}">
																	<c:if test="${abVehicleDriver.key eq vehicledriver.vehicleId}">
																	<font color="green">${vehicledriver.diverDetails.driverName}(${vehicledriver.diverDetails.driverMobileNo})</font>
																	<%++m; 
																	</c:if>
																</c:forEach>--%>
																<%if(m==0){%>
																	${vehicledriver.diverDetails.driverName}(
																	<fmt:formatDate pattern="dd-MMM-yyyy HH:mm" value="${vehicledriver.allotmentFromDate}"/>)
									
																<%} }%>
																</c:if>
																 <c:if test="${vehicledriver.driverId eq 0}"><font color="red">--NO DRIVER--</font></c:if>
														</display:column>
														<display:column title="Allocated FromDate & Time">
														<%if(j==0){ %>
														<input id="fromDate<%=i %>"  readonly="readonly"  style="height:16px;width:80px;font-size: 11px;font-weight: bold;"  value="${mtBean.currentDate}" onchange="validateChangeDriver(<%=i %>);"/>
											           <img  src="./images/calendar.gif" id="date_start_trigger<%=i %>" />	
											         <script type="text/javascript">
												       Calendar.setup({inputField :"fromDate<%=i %>",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger<%=i %>",singleClick : true,step : 1});
											          </script>
											          <input id="fromTime<%=i %>" style="width:45px" readonly="readonly" class="fromTime<%=i %>" onmouseover="javascript:timePicker('fromTime<%=i %>');" value="08:40" onchange="validateChangeDriver(<%=i %>);"/>
											        <input type="button" value="GetDrivers" onclick="javascript:getAvailableDrivers(<%=i %>);" id="getDriversDiv<%=i %>"></input>
											          <div class="expbutton" id="changeDriverDiv<%=i %>" style="float: right;display:none;"><a class="expbutton" onclick="javascript:changeVehicleDriverMapDetails('${vehicledriver.id}','${vehicledriver.vehicleId}','${vehicledriver.driverId}','<%=i%>','<fmt:formatDate pattern='dd-MMM-yyyy HH:mm' value='${vehicledriver.allotmentFromDate}'/>');"><span>ChangeDriver</span></a></div>
														<%}%>
														</display:column>
															
													<display:column  title="Pool Drivers" style="width:10%">
															<%if(j==0){ %>
														<select name="driverId" id="driverId<%=i %>" onchange="changeButtons(<%=i %>);">
															  <option value="">Select</option>
							                                   <option value="0" style="color: red;">Remove Driver</option>
														</select>
															<%}%>
													</display:column>
													<%--<display:column style="width:5%;text-align:center" title="Change">
															<%if(j==0){ %>
														<div class="expbutton"><a onclick="javascript:changeVehicleDriverMapDetails('${vehicledriver.id}','${vehicledriver.vehicleId}','${vehicledriver.driverId}','<%=i%>','<fmt:formatDate pattern='dd-MMM-yyyy HH:mm' value='${vehicledriver.allotmentFromDate}'/>');"> <span>Driver</span></a></div>
															<%}%>
													</display:column>--%>
											</display:table>	
											<div class="line">
												<fieldset><legend><strong><font color="green">Not Mapped Vehicles</font></strong></legend>
												<display:table name="${sessionScope.allNotMappedVehicles}" excludedParams="*"
													export="false" class="list" requestURI="" id="notMapVehicles" pagesize="100" sort="list">
													<display:column title="Vehicle No">${notMapVehicles.vehicleNo}(${notMapVehicles.vehiclePoolTypeDetails.vehiclePoolType})-<font color="blue">Allocate Driver To This Vehicle</font></display:column>
													<display:column title="asd">${notMapVehicles.vehicleTypeDetails.vehicleType}</display:column>
												</display:table>
												</fieldset>		
											</div>	  	
								<div class="line">
								<b>Note*:</b><br/><font color="red">Red color</font> indicates Absent
								</div>	
							</div>
<c:if test="${sessionScope.AvailableDriverList ne null && sessionScope.AvailableDriverList!=''}">
<script>
	AvailableDriversJson= <%= (net.sf.json.JSONArray)session.getAttribute("AvailableDriverList") %>;
	DriverAbsentJson= <%= (net.sf.json.JSONArray)session.getAttribute("DriverAbsentListJson") %>;
</script>
</c:if>
<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
</script>

<%-- End :  MTVehicleVsDriverDetailsList.jsp --%>
