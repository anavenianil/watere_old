<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:Welcome.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/jstl-fn" prefix="fn" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<title>Welcome</title>


</head>

<body>
	<form:form method="post" commandName="workflowmap">
		<div>
			<div class="Innermaindiv">
				<div class="header"></div>
				<div><jsp:include page="MenuLinks.jsp" /></div>					
				
	
	<div class="mainDisplay">
					<div class="whitebox1">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								<div class="whitebox_tr"></div>
							</div>
						</div>
						<div class="whitebox_m1">
							<div class="lightblueBg1">
								<div style="padding: 15px 0 0 0;"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" />&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								
													
								<div id="result"></div>
								<c:if test="${workflowmap.requestfailure eq 'requestfailure'}"> <span class="failure"><spring:message code="requestfailure"/></span></c:if>
								<c:if test="${workflowmap.requestsuccess eq 'requestsuccess'}"> <span class="success"><spring:message code="requestsuccess"/></span></c:if>
								<c:if test="${workflowmap.requestnotexist  eq 'requestnotexist'}"> <span class="failure"><spring:message code="requestnotexist"/></span></c:if>
								  
								  <div class="line">
								  	<div style="padding-left:34%">
								     <table>
								     <tr>
								     <td><div class="quarter bold rightmar" style="margin-left:15%">RequestID&nbsp;&nbsp;</div></td>
								        
                                      <td><div class="quarter" style="margin-right:20%"><input path="requestIdBased" id="requestIdBased" 
									        onkeypress="return checkKey(event,this.id,'requestId','roleId');"/></div></td>
									  </tr>
									  </table>
									 </div>
	                              </div>
	                               
								<div class="line">
								<%int i=0; %>
									<table width="100%" cellpadding="2" cellspacing="2" border="0"  >
										<tr>
											<td class="layer" valign="top">
												<div class="line">
													<%--Pending Layer starts --%>
													
														<div class="line" id="pendingDiv" > 
															<table width="100%" cellpadding="2" cellspacing="0" align="center" border="1" class="dashboard" bordercolor="#10519a" id="pendingTable" >
																<tr class="dashboardHeaderPR">
																	<th colspan="3" align="center">Pending Requests</th>
																</tr>
																<tr class="dashboardSubHeaderPR">
																	<th width="38%">Requester</th>
																	<th width="27%">Request</th>
																	<th width="35%">Request id/ Date</th>
																</tr>
																<tr >
																	<td colspan="3" style="height:300px" valign="top">
																		<table width="100%" cellpadding="2" cellspacing="0" align="center" class="dashboard" border="1" id="pending">
																			<% i=0; %>
																			<c:forEach items="${workflowmap.pendingRequests}" var="pending">
																			<%i++ ;%>
																				<tr id="pendingTr<%=i %>" onclick="javascript:getRequestDetails('${pending.historyID}','${pending.requestID}','pending','')" style="height:25px;cursor: pointer;">
																					<td onmouseover="javascript: ysrcp('pendingDiv','pendingTr<%=i %>');" onmouseout="javascript: ysrcp2('pendingDiv','pendingTr<%=i %>');" width="38%">${pending.requester}</td>
																					<td onmouseover="javascript: ysrcp('pendingDiv','pendingTr<%=i %>');" onmouseout="javascript: ysrcp2('pendingDiv','pendingTr<%=i %>');" width="27%">${pending.requestType}</td>
																					<td onmouseover="javascript: ysrcp('pendingDiv','pendingTr<%=i %>');" onmouseout="javascript: ysrcp2('pendingDiv','pendingTr<%=i %>');" width="35%" style="text-align: center;">${pending.requestID}<br/>${pending.assignedDate}</td>
																				</tr>
																			</c:forEach>
																		</table>
																	</td>
																</tr>																
																<c:if test="${fn:length(workflowmap.pendingRequests)==workflowmap.requestSize}">
																	<tr>
																		<td colspan="3" style="text-align:right"><a href="javascript:moreRequests('pending')">More...</a></td>
																	</tr>																
																</c:if>											
															</table>
															<script>gridStyle("pendingTable")</script>
														</div>
													<%--Pending Layer end --%>
														
													
												</div>
											</td>
											<td class="layer" valign="top">
												<div class="line"  >
													<%--My requests Layer starts --%>
					
													<div class="line" id="myrequestsDiv" >
														<jsp:include page="MyRequests.jsp"></jsp:include>														
													</div>
													<%--My requests Layer end --%>
													
													
												</div>
											</td>
											<td class="layer" valign="top">
												<div class="line">
													<%--Completed Layer starts --%>
														<div class="line" id="completedDiv" >
															<table width="100%" cellpadding="2" cellspacing="0" align="center" border="1" class="dashboard" bordercolor="#10519a" id="completedTable">
																<tr class="dashboardHeaderCR">
																	<th colspan="3" align="center">Completed Requests</th>
																</tr>
																<tr class="dashboardSubHeaderCR">
																	<th width="38%">Requester</th>
																	<th width="27%">Request</th>
																	<th width="35%">Request id/ Date</th>
																</tr>
																<tr>
																	<td colspan="3" colspan="3" style="height:300px" valign="top">
																		<table width="100%" cellpadding="2" cellspacing="0" align="center" class="dashboard" border="1" id="completed">
																			<% i=0; %>
																			<c:forEach items="${workflowmap.completedRequests}" var="completed">
																				<%i++ ;%>
																				<tr id="completedTr<%=i %>" onclick="javascript:getRequestDetails('${completed.historyID}','${completed.requestID}','completed','')" style="height:25px;cursor: pointer;">
																					<td width="38%" onmouseover="javascript: ysrcp('completedDiv','completedTr<%=i %>');" onmouseout="javascript: ysrcp2('completedDiv','completedTr<%=i %>');">${completed.requester}</td>
																					<td width="27%" onmouseover="javascript: ysrcp('completedDiv','completedTr<%=i %>');" onmouseout="javascript: ysrcp2('completedDiv','completedTr<%=i %>');">${completed.requestType}</td>
																					<td width="35%" style="text-align: center;" onmouseover="javascript: ysrcp('completedDiv','completedTr<%=i %>');" onmouseout="javascript: ysrcp2('completedDiv','completedTr<%=i %>');">${completed.requestID}<br/>${completed.actionedDate}</td>
																					
																				</tr>
																			</c:forEach>
																		</table>
																	</td>
																</tr>
																<c:if test="${fn:length(workflowmap.completedRequests)==workflowmap.requestSize}">
																	<tr>
																		<td colspan="3" style="text-align:right"><a href="javascript:moreRequests('completed')">More...</a></td>
																	</tr>																
																</c:if>																	
															</table>
															
															<script>gridStyle("completedTable")</script>
														</div>
													<%--Completed Layer end --%>
																						
												</div>
											</td>
										</tr>	
										<tr>
										<td class="layer" valign="top">
										<%--Delegated Layer starts --%>
														<div class="line" id="delegatedDiv" >
															<table width="100%" cellpadding="2" cellspacing="0" align="center" border="1" class="dashboard" bordercolor="#10519a" id="delegatedTable">
																<tr class="dashboardHeaderDR">
																	<th colspan="3" align="center">Delegated Requests</th>
																</tr>
																<tr class="dashboardSubHeaderDR">
																	<th width="38%">Requester</th>
																	<th width="27%">Request</th>
																	<th width="35%">Request id/ Date</th>
																</tr>
																<tr>
																	<td colspan="3" colspan="3" style="height:300px" valign="top">
																		<table width="100%" cellpadding="2" cellspacing="0" align="center" class="dashboard" border="1" id="delegated">
																			<% i=0; %>
																			<c:forEach items="${workflowmap.delegatedRequests}" var="delegated">
																				<%i++ ;%>
																				<tr id="delegatedTr<%=i %>" onclick="javascript:getRequestDetails('${delegated.historyID}','${delegated.requestID}','delegated','')" style="height:25px;cursor: pointer;">
																					<td width="38%" onmouseover="javascript: ysrcp('delegatedDiv','delegatedTr<%=i %>');" onmouseout="javascript: ysrcp2('delegatedDiv','delegatedTr<%=i %>');">${delegated.requester}</td>
																					<td width="27%" onmouseover="javascript: ysrcp('delegatedDiv','delegatedTr<%=i %>');" onmouseout="javascript: ysrcp2('delegatedDiv','delegatedTr<%=i %>');">${delegated.requestType}</td>
																					<td width="35%" style="text-align: center;" onmouseover="javascript: ysrcp('completedDiv','completedTr<%=i %>');" onmouseout="javascript: ysrcp2('completedDiv','completedTr<%=i %>');">${delegated.requestID}<br/>${delegated.assignedDate}</td>
																					
																				</tr>
																			</c:forEach>
																		</table>
																	</td>
																</tr>
																<c:if test="${fn:length(workflowmap.delegatedRequests)==workflowmap.requestSize}">
																	<tr>
																		<td colspan="3" style="text-align:right"><a href="javascript:moreRequests('delegated')">More...</a></td>
																	</tr>																
																</c:if>	
															</table>
															
															<script>gridStyle("delegatedTable")</script>
														</div>
													
													<%--Delegated Layer end --%>
													</td>
										<td class="layer" valign="top">

													<%--Alerts Layer starts --%>
													<div class="line" id="myAlertsDiv" >
														<jsp:include page="MyAlerts.jsp"></jsp:include>
													</div>
													<%--Alerts Layer end --%>
													</td>
										<td class="layer" valign="top">
										<%--Escalated Layer starts --%>
														<div class="line" id="escalatedDiv" >
															<table width="100%" cellpadding="2" cellspacing="0" align="center" border="1" class="dashboard" bordercolor="#10519a">
																<tr class="dashboardHeaderER">
																	<th colspan="3" align="center">Escalated Requests</th>
																</tr>
																<tr class="dashboardSubHeaderER">
																	<th width="38%">Requester</th>
																	<th width="27%">Request</th>
																	<th width="35%">Request id/ Date</th>
																</tr>
																<tr>
																	<td colspan="3" style="height:300px" valign="top">
																	<%i=0; %>
																		<table width="100%" cellpadding="2" cellspacing="0" align="center" class="dashboard" border="1" id="escalated">
																			<c:forEach items="${workflowmap.escalatedRequests}" var="escalated">
																				<%i++; %>
																				<tr id="escalatedTr<%=i %>"  onclick="javascript:getRequestDetails('${escalated.historyID}','${escalated.requestID}','escalated','')" style="height:25px;cursor: pointer;">
																					<td width="38%" onmouseover="javascript: ysrcp('escalatedDiv','escalatedTr<%=i %>');" onmouseout="javascript: ysrcp2('escalatedDiv','escalatedTr<%=i %>');">${escalated.requester}</td>
																					<td width="27%" onmouseover="javascript: ysrcp('escalatedDiv','escalatedTr<%=i %>');" onmouseout="javascript: ysrcp2('escalatedDiv','escalatedTr<%=i %>');">${escalated.requestType}</td>
																					<td width="35%" style="text-align: center;" onmouseover="javascript: ysrcp('escalatedDiv','escalatedTr<%=i %>');" onmouseout="javascript: ysrcp2('escalatedDiv','escalatedTr<%=i %>');">${escalated.requestID}<br/>${escalated.assignedDate}</td>
																					
																				</tr>
																			</c:forEach>
																		</table>
																	</td>
																</tr>
																<c:if test="${fn:length(workflowmap.escalatedRequests)==workflowmap.requestSize}">
																	<tr>
																		<td colspan="3" style="text-align:right"><a href="javascript:moreRequests('escalated')">More...</a></td>
																	</tr>																
																</c:if>	
															</table>															
															<script>gridStyle("escalatedTable")</script>
														</div>										
													<%--Escalated Layer end --%>
										</td>
										
										</tr>									
									</table>
								</div>					
								<%-- Content Page end --%>
								<div>&nbsp;</div>
							</div>
						</div>
						<div class="whitebox_b_non">
							<div class="whitebox_bl">
								<div class="whitebox_br"></div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="message"/>
		<form:hidden path="historyID"/>
		<form:hidden path="requestId"/>
		<form:hidden path="statusMsg"/>
		<form:hidden path="back"/>
		<form:hidden path="roleId" id="roleId"/>
		</form:form>
	</body>
</html>

<!-- End:Welcome.jsp -->