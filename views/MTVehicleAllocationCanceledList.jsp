<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTVehicleAllocationCanceledList.jsp  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/displaytag" prefix="display" %>

                          <c:if test="${message=='deallocateSuccess'}"><span class="success"><spring:message code="deallocateSuccess"/></span></c:if>
                          <c:if test="${message=='deallocateFail'}"><span class="success"><spring:message code="deallocateFail"/></span></c:if>
                           <c:if test="${message=='deallocError'}"><span class="failure"><spring:message code="deallocError"/></span></c:if>
                          
	                              <%-- <div class="line" id="result2">--%>
		                             <div class="line">
		                             
										<table class="fortable" id="cancelReq">
											<tr>
												<td class="tabletd">Request Id</td>
												<td class="tabletd">Sfid</td>
												<td class="tabletd">Passenger Name</td>
												<td class="tabletd">Departure Date</td>
												<td class="tabletd">Estimated Date</td>
												<td class="tabletd">Cancel</td>
											</tr>
											<c:if test="${fn:length(sessionScope.CanceledJourneyList) ne 0}">
												<c:forEach items="${sessionScope.CanceledJourneyList}" var="cancelReq">
														<c:forEach items="${cancelReq.mtJourneyDetails}" var="cancelJourney">
														<c:if test="${cancelJourney.status eq 9}">
															<tr>
																<td style="">${cancelReq.requestID}</td>
																<td style="">${cancelReq.sfid}</td>
																<td>${cancelJourney.nameWithDesignation}</td>
																<td><fmt:formatDate pattern="dd-MMM-yyy HH:mm" value="${cancelJourney.departureDate}"/></td>
																<td><fmt:formatDate pattern="dd-MMM-yyy HH:mm" value="${cancelJourney.estimatedDate}"/></td>
																<%-- <td><input type="checkbox" id="${cancelJourney.id}"></td>--%>
																<td><input type="checkbox" id="${cancelJourney.mtVehicleAllocationtDetailsDTO[0].id}"></td>
															</tr>
															</c:if>
													   </c:forEach>
												</c:forEach>
												</c:if>
												<c:if test="${fn:length(sessionScope.CanceledJourneyList) eq 0}">
												<tr>adsdas</tr>
												
												</c:if>
										</table>
											<%--<display:table name="${sessionScope.CanceledJourneyList}" excludedParams="*"
											export="false" class="list" requestURI="" id="cancelReq" pagesize="500" sort="list">
											
											<display:column  title='Request Id'>${cancelReq.requestID}</display:column>
											<c:forEach items="${cancelReq.mtJourneyDetails}" var="cancelJourney">
											<display:column  title='Name'>${cancelJourney.nameWithDesignation}</display:column>
											<display:column  title='Departure Date'>${cancelJourney.departureDate}</display:column>
											<display:column  title='Estimated Date'>${cancelJourney.estimatedDate}</display:column>
											
											</c:forEach>
											
											<%-- <input type="checkbox" id="${cancelReq.mtJourneyDetails[0].id}"></input>--%>
											
											<%--<display:column  title='Name'>${cancelJourney.nameWithDesignation}</display:column>
											<display:column  title='Departure Date'>${cancelJourney.departureDate}</display:column>
											<display:column  title='Estimated Date'>${cancelJourney.estimatedDate}</display:column>
											
										</display:table>--%>	
										</div>
										<c:if test="${fn:length(sessionScope.CanceledJourneyList) ne 0}">
											<div class="line" style="margin-left: 80%;"><br/>
											<div class="expbutton"><a onclick="javascript:deallocateVehicleForJourney();"> <span>Deallocate</span></a></div>
											</div>
										</c:if>	
								
								
								
								
<!-- End:MTVehicleAllocationCanceledList.jsp  -->

