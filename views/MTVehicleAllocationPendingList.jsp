<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTVehicleAllocationPendingList.jsp  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

                                  <div class="line" id="result">
                                  <c:if test="${message=='allocateSuccess'}"><span class="success"><spring:message code="allocateSuccess"/></span></c:if>
                                  <c:if test="${message=='allocateFail'}"><span class="success"><spring:message code="allocateFail"/></span></c:if>
<script>
		var ApprovedReqJSON = <%=(net.sf.json.JSONArray)session.getAttribute("ApprovedReqJSON")%>;
		
	</script>
								  <%---<jsp:include page="MTVehicleAllocationResult.jsp" />--%>
								  </div>
								  
								 <div class="line">
									  <div id="dataTable1">
									  <%int i=1;%>
										   	<display:table name="${sessionScope.ApprovedReqList}" excludedParams="*"
												export="false" class="list" requestURI="" id="req" pagesize="1000" sort="list" >
											
												<display:column  title='SNO' ><%=i%></display:column>
												<display:column  title='REQUEST ID' ><a href="javascript:getRequestIdDetails('${req.requestID}')"><font color="blue">${req.requestID}</font></a></display:column>
												<display:column  title='STATUS' >${req.statusDetails.name}</display:column>
												<display:column  title='SFID' >${req.sfid}</display:column>
												<display:column  title='REQUESTED DATE' ><fmt:formatDate pattern="dd-MMM-yyyy" value="${req.requestedDate}"/></display:column>
												<display:column  title='EMPLOYEE NAME' >${req.requesterName}</display:column>
												<display:column  title='PURPOSE OF VISIT' >${req.purposeOfVisit}</display:column>
												<display:column  title='ONWARD JOURNEY' >
										
												<%int j=0;%>
												<c:forEach items="${req.mtJourneyDetails}" var="journey">
												<c:if test="${journey.journeyTypeFlag eq 'ONWARD'}">
											
												
												<% j++;%>
												</c:if>
												</c:forEach>
												<%if(j!=0){ %>
										
												<a href="javascript:onwardGrid('${req.requestID}');"><span id="hideOnwardGrid${req.requestID}" style="display:none;">hide</span><span id="showOnwardGrid${req.requestID}" >Allocate vehicle</span></a>
												<%}%>
												
							
												
												<%--<%int j=0;
												<c:forEach items="${req.mtJourneyDetails}" var="journey">
												<c:if test="${journey.journeyTypeFlag eq 'ONWARD' && journey.status ne '9'}">
												<% j++;%>
												</c:if>
												</c:forEach>
												<%if(j!=0){ %>
												<a href="javascript:onwardGrid('${req.requestID}');"><span id="hideOnwardGrid${req.requestID}" style="display:none;">hide</span><span id="showOnwardGrid${req.requestID}" >show</span></a>
												<%}%>--%>
												</display:column>
												
												<display:column  title='RETURN JOURNEY' >
												<c:if test="${req.vehicleRequiredFlag eq 1}">
												<%int k=0;%>
												<c:forEach items="${req.mtJourneyDetails}" var="journey">
												<c:if test="${journey.journeyTypeFlag eq 'RETURN'}">
												<% k++;%>
												</c:if>
												</c:forEach>
												<%if(k!=0){ %>
												
									
												<a href="javascript:returnGrid('${req.requestID}');"><span id="hideReturnGrid${req.requestID}" style="display:none;">hide</span><span id="showReturnGrid${req.requestID}" >Allocate vehicle</span></a>
											
												<%}%>
												
												
												
												
												</c:if>
												<%--<%int k=0;
												<c:forEach items="${req.mtJourneyDetails}" var="journey">
												<c:if test="${journey.journeyTypeFlag eq 'RETURN' && journey.status ne '9'}">
												<% k++;%>
												</c:if>
												</c:forEach>
												<%if(k!=0){ %>
												
												<c:if test="${req.vehicleRequiredFlag eq 1}">
												<a href="javascript:returnGrid('${req.requestID}');"><span id="hideReturnGrid${req.requestID}" style="display:none;">hide</span><span id="showReturnGrid${req.requestID}" >show</span></a>
												</c:if>
												<%}%>--%>
												</display:column>
													
												  <%i++;%>
											
											</display:table>
										</div>
								  </div>
								 
								<div id="OnwardGrid" style="display:none;">
									<div id="OnwardPassEngVehicleAllotGrid"></div>
									<div id="OnwardPassEngArticleGrid" style="display:none;"></div>
								</div> 
								
								 <div class="line" id="onwardSearch" style="display:none;">
								 <div class="quarter leftmar" style="width:35%">
								 FromDate&Time <input id='searchFromDate' maxlength="100" readonly="true" style="height:16px;width:80px;font-size: 11px;font-weight: bold;"/>
												<img  src="./images/calendar.gif"   id='searchFromDate_trigger' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"searchFromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"searchFromDate_trigger",singleClick : true,step : 1});
														</script>
														<input id="searchFromTime" style="width:45px"/>
								</div>
								<div class="quarter leftmar" style="width:35%">
														ToDate&Time <input id='searchToDate' maxlength="100" readonly="true" style="height:16px;width:80px;font-size: 11px;font-weight: bold;"/>
												<img  src="./images/calendar.gif"   id='searchToDate_trigger' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"searchToDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"searchToDate_trigger",singleClick : true,step : 1});
														</script>
														<input id="searchToTime" style="width:45px"/>
                                  </div>
                                 <div class="expbutton" id="onwardSearchDivId"></div>
                                 
                              
                                  </div>
								 
							
								<div id="OnwardVehicleAllotmentDetailsGrid" style="display:none;"></div>
								<div id="OnwardCombineVehicleDetailsGrid" style="display:none;"></div>
								<div class="line" id="onwardSubmitDiv" style="display:none;margin-left: 60%;">
								     <%--  <div class="expbutton" ><a onclick="saveCombineAlloc('onward')"><span>Combine Allocation</span></a></div>
									 <div class="expbutton"><a onclick="saveVehicleAllocationDetails('onward')"> <span>Allocate For Onward</span></a></div>--%>
								</div>
								
								<div id="ReturnGrid" style="display:none;">
									<div id="ReturnPassEngVehicleAllotGrid"></div>
									<div id="ReturnPassEngArticleGrid" style="display:none;"></div>
								</div> 
								
								 <div class="line" id="returnSearch" style="display:none;">
								 <div class="quarter leftmar" style="width:35%">
								 FromDate&Time <input id='RsearchFromDate' maxlength="100" readonly="true" style="height:16px;width:80px;font-size: 11px;font-weight: bold;"/>
												<img  src="./images/calendar.gif"   id='RsearchFromDate_trigger' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"RsearchFromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"RsearchFromDate_trigger",singleClick : true,step : 1});
														</script>
														<input id="RsearchFromTime" style="width:45px"/>
								</div>
								<div class="quarter leftmar" style="width:35%">
														ToDate&Time <input id='RsearchToDate' maxlength="100" readonly="true" style="height:16px;width:80px;font-size: 11px;font-weight: bold;"/>
												<img  src="./images/calendar.gif"   id='RsearchToDate_trigger' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"RsearchToDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"RsearchToDate_trigger",singleClick : true,step : 1});
														</script>
														<input id="RsearchToTime" style="width:45px"/>
                                  </div>
                                 <div class="expbutton" id="returnSearchDivId"></div>
                                 
                              
                                  </div>
								
									<div id="ReturnVehicleAllotmentDetailsGrid" style="display:none;"></div>
								<div id="ReturnCombineVehicleDetailsGrid" style="display:none;"></div>
								<div class="line" id="returnSubmitDiv" style="display:none;margin-left: 60%;">
								      <%-- <div class="expbutton" ><a onclick="saveCombineAlloc('return')"> <span>Combine Allocation</span></a></div>
									 <div class="expbutton"><a onclick="saveVehicleAllocationDetails('return')"> <span>Allocate For Return</span></a></div>--%>
								</div>
								 
