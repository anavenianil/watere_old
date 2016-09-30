<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : MTVehicleVsDriverDetailsHistory.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div id="dataTable">
								 <%int i=0;int j=0; int k=0; int m=0; %>
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
														<c:if test="${vehicledriver.driverId eq 0}"><font color="red">--NO DRIVER--</font></c:if>
														<c:if test="${vehicledriver.driverId ne 0}">
															<c:forEach var="abDriver" items="${sessionScope.DriverAbsentList}">
																<c:if test="${abDriver.key eq vehicledriver.driverId}"><font color="red">${vehicledriver.diverDetails.driverName}</font>(${vehicledriver.diverDetails.driverMobileNo})(${abDriver.name} to ${abDriver.value})
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
																	${vehicledriver.diverDetails.driverName}(${vehicledriver.diverDetails.driverMobileNo})
																<%} }%>
																</c:if>
														</display:column>
														<display:column  title='From Date&Time'>
														<fmt:formatDate pattern="dd-MMM-yyyy HH:mm" value="${vehicledriver.allotmentFromDate}"/>
														
														</display:column>
														<display:column  title='To Date&Time'>
														<c:choose>
														<c:when test="${vehicledriver.allotmentToDate eq null}">
														Continuing
														</c:when>
														<c:otherwise>
														<fmt:formatDate pattern="dd-MMM-yyyy HH:mm" value="${vehicledriver.allotmentToDate}"/>
														</c:otherwise>
														</c:choose>	
														</display:column>
											</display:table>	
										<div class="line"></div>
																  	
								<div class="line">
									 <div class="expbutton" style="margin-left: 80%;"><a onclick="javascript:clearVehicleDriverMapDetails();mtVehicleVsDriverList();"> <span>Back</span></a></div>
									</div>
								
</div>