<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTVehicleAllocationCompltedList.jsp  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

                   <div id="result2"><jsp:include page="Result.jsp"></jsp:include></div>
                                <div class="line">
									  <div id="dataTable1">
									  <%int i=1;%>
										   	<display:table name="${sessionScope.AllocatedReqList}" excludedParams="*"
												export="false" class="list" requestURI="" id="allocReq" pagesize="1000" sort="list">
											
												
												<display:column  title='SNO' ><%=i%></display:column>
												<display:column  title='REQUEST ID' ><a href="javascript:getRequestIdDetails('${allocReq.requestID}')"><font color="blue">${allocReq.requestID}</font></a></display:column>
												<display:column  title='SFID' >${allocReq.sfid}</display:column>
												<display:column  title='EMPLOYEE NAME' >${allocReq.requesterName}</display:column>
												<display:column  title='PURPOSE OF VISIT' >${allocReq.purposeOfVisit}</display:column>
												<display:column  title='ONWARD JOURNEY' >
												<%-- <a href="javascript:onwardAllocation('${allocReq.requestID}','show');">
												<div id="onwardShow${allocReq.requestID}">show</div>
												</a>
												<a href="javascript:onwardAllocation('${allocReq.requestID}','hide');">
												<div id="onwardHide${allocReq.requestID}" style="display: none;">hide</div>
												</a>--%>
												
												<%int j=0;%>
												<c:forEach items="${allocReq.mtJourneyDetails}" var="journey">
												<c:if test="${journey.journeyTypeFlag eq 'ONWARD'}">
											
												
												<% j++;%>
												</c:if>
												</c:forEach>
												<%if(j!=0){ %>
											
												<a href="javascript:onwardAllocation('${allocReq.requestID}','show');">
												<div id="onwardShow${allocReq.requestID}">view allocation</div>
												</a>
												<a href="javascript:onwardAllocation('${allocReq.requestID}','hide');">
												<div id="onwardHide${allocReq.requestID}" style="display: none;">hide</div>
												</a>
												<%}%>
												
												
												</display:column>
												
												<display:column  title='RETURN JOURNEY' >
												<%--<c:if test="${allocReq.vehicleRequiredFlag eq 1}">
												<a href="javascript:returnAllocation('${allocReq.requestID}','show');">
												<div id="returnShow${allocReq.requestID}">show</div>
												</a>
												<a href="javascript:returnAllocation('${allocReq.requestID}','hide');">
												<div id="returnHide${allocReq.requestID}" style="display: none;">hide</div>
												</a>
												
												
												</c:if>--%>
												
												<c:if test="${allocReq.vehicleRequiredFlag eq 1}">
												<%int k=0;%>
												<c:forEach items="${allocReq.mtJourneyDetails}" var="journey">
												<c:if test="${journey.journeyTypeFlag eq 'RETURN'}">
												<% k++;%>
												</c:if>
												</c:forEach>
												<%if(k!=0){ %>
												
												
												<a href="javascript:returnAllocation('${allocReq.requestID}','show');">
												<div id="returnShow${allocReq.requestID}">view allocation</div>
												</a>
												<a href="javascript:returnAllocation('${allocReq.requestID}','hide');">
												<div id="returnHide${allocReq.requestID}" style="display: none;">hide</div>
												</a>
											
												<%}%>
												
												
												</c:if>
												
												
												
												
												
												</display:column>
												  <%i++;%>
												
											</display:table>
										</div>
								  </div>
								
								<div id="OnwardAllocation" class="line" style="display:none;">
								 		
								</div> 
								<div id="editOnwardSearch" class="line" style="display:none;">
								 	 <div class="quarter leftmar" style="width:35%">
								 FromDate&Time <input id='editSearchFromDate' maxlength="100" readonly="true" style="height:16px;width:80px;font-size: 11px;font-weight: bold;"/>
												<img  src="./images/calendar.gif"   id='editSearchFromDate_trigger' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"editSearchFromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"editSearchFromDate_trigger",singleClick : true,step : 1});
														</script>
														<input id="editSearchFromTime" style="width:45px"/>
								</div>
								<div class="quarter leftmar" style="width:35%">
														ToDate&Time <input id='editSearchToDate' maxlength="100" readonly="true" style="height:16px;width:80px;font-size: 11px;font-weight: bold;"/>
												<img  src="./images/calendar.gif"   id='editSearchToDate_trigger' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"editSearchToDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"editSearchToDate_trigger",singleClick : true,step : 1});
														</script>
														<input id="editSearchToTime" style="width:45px"/>
                                  </div>
                                 <div class="expbutton" id="editOnwardSearchDivId"></div>
										
								</div> 
								<div id="editOnwardAvailableVehiclesGrid" class="line" style="display:none;"></div> 
							    <div id="editOnwardCombineVehiclesGrid" style="display:none;"></div>
							    <div class="line" id="editOnwardSubmitDiv" style="display:none;margin-left: 60%;"></div>
							    
							    
							    
								<div id="ReturnAllocation" class="line">
								
								</div> 
								<div id="editReturnSearch" class="line" style="display:none;">
								 	 <div class="quarter leftmar" style="width:35%">
								 FromDate&Time <input id='editRSearchFromDate' maxlength="100" readonly="true" style="height:16px;width:80px;font-size: 11px;font-weight: bold;"/>
												<img  src="./images/calendar.gif"   id='editRSearchFromDate_trigger' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"editRSearchFromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"editRSearchFromDate_trigger",singleClick : true,step : 1});
														</script>
														<input id="editRSearchFromTime" style="width:45px"/>
								</div>
								<div class="quarter leftmar" style="width:35%">
														ToDate&Time <input id='editRSearchToDate' maxlength="100" readonly="true" style="height:16px;width:80px;font-size: 11px;font-weight: bold;"/>
												<img  src="./images/calendar.gif"   id='editRSearchToDate_trigger' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"editRSearchToDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"editRSearchToDate_trigger",singleClick : true,step : 1});
														</script>
														<input id="editRSearchToTime" style="width:45px"/>
                                  </div>
                                 <div class="expbutton" id="editReturnSearchDivId"></div>
										
								</div> 
								<div id="editReturnAvailableVehiclesGrid" class="line" style="display:none;"></div> 
							    <div id="editReturnCombineVehiclesGrid" style="display:none;"></div>
							    <div class="line" id="editReturnSubmitDiv" style="display:none;margin-left: 60%;"></div>
						<script>
		var AllocatedReqJSON = <%=(net.sf.json.JSONArray)session.getAttribute("AllocatedReqJSON")%>;
		var addressJSON = <%=(net.sf.json.JSONArray)session.getAttribute("addressListJSON")%>;
	</script>		
								


								  
								  