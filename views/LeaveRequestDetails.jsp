<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LeaveRequestDetails.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/jstl-fn" prefix="fn" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/> <!-- Old calendar -->
<!-- <link href="styles/calendar.css" type="text/css" rel="stylesheet"/> --> <!-- New calendar -->

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/calendar.js"></script> <!-- Old calendar -->
<script type="text/javascript" src="script/calendar-en.js"></script> <!-- Old calendar -->
<script type="text/javascript" src="script/calendar-setup_3.js"></script> <!-- Old calendar -->
<!-- <script type="text/javascript" src="script/calendar-hd.js"></script> --> <!-- New calendar -->
<!-- <script type="text/javascript" src="script/calendar_us.js"></script> --> <!-- New calendar -->
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<script type="text/javascript" src="script/workflow.js"></script>
<title>Leave Request Details</title>
</head>

<body>
	<form:form method="post" commandName="leaveRequest"> <!-- name="leave" -->
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
								<c:if test="${leaveRequest.type == 'cancel'}">
									<div class="headTitle">Leave Cancellation</div>
								</c:if>
								<c:if test="${leaveRequest.type == 'convert'}">
									<div class="headTitle">Leave Conversion</div>
									<script>
										appliedLeaveFromDate = '<%= request.getAttribute("appliedLeaveFromDate").toString() %>';
										appliedLeaveToDate = '<%= request.getAttribute("appliedLeaveToDate").toString() %>';
										appliedLeave = '<%= request.getAttribute("appliedLeave").toString() %>';
										appliedLeaveNoDays = '<%= request.getAttribute("appliedLeaveNoDays").toString() %>';
									</script>
								</c:if>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="middle"/>&nbsp;Loading...</div>

								<%-- Content Page starts --%>
								<div class="line">
									<div class="line" id="result"></div>
									<fieldset><legend><strong><font color='green'>Leave Request Details</font></strong></legend>
										<div class="line">
											<div class="line">
												<div class="quarter bold">Nature of leave</div>
												<div class="quarter" id="appliedLeave">:
													<c:if test="${leaveRequest.leaveTypeDetails.id != 11}">
														${leaveRequest.leaveTypeDetails.leaveType}
													</c:if>
													<c:if test="${leaveRequest.leaveTypeDetails.id == 11}">
														${leaveRequest.leaveSubTypeDetails.leaveSubType} (SCL)
													</c:if>
												</div>
												<div class="quarter bold">Leave Applied Date</div>
												<div class="quarter">:&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${leaveRequest.formattedRequestedDate}" /></div>
											</div>
											<div class="line">
												<div class="quarter bold">Leave Applied By</div>
												<div class="quarter">:&nbsp;<c:out  value="${leaveRequest.requestedBy}"/>			
												</div>
												<div class="quarter bold">Section/Div./Dte. with Ph. No.</div>
												<div class="quarter">:&nbsp;<c:out  value="${leaveRequest.departmentDetails.deptName}, ${leaveRequest.internalNo}"/>	</div>
											</div>
											<div class="line">
												<div class="quarter bold">From Date</div>
												<div class="quarter" id="fromDate">:
													<c:if test="${leaveRequest.otherRemarks ne null}">
														<c:set var="remarksText" value="${leaveRequest.otherRemarks}" />
														<c:choose>
															<c:when test="${fn:contains(remarksText, 'proceed')}">${leaveRequest.fromDate}</c:when>
															<c:otherwise><fmt:formatDate pattern="dd-MMM-yyyy" value="${leaveRequest.formattedFromDate}" /></c:otherwise>
														</c:choose>
													</c:if>
													<c:if test="${leaveRequest.otherRemarks eq null}"><fmt:formatDate pattern="dd-MMM-yyyy" value="${leaveRequest.formattedFromDate}" /></c:if>
													<%-- <fmt:formatDate pattern="dd-MMM-yyyy" value="${leaveRequest.formattedFromDate}" /> --%>
													<c:if test="${leaveRequest.fromHalfDayFlag == 'Y'}">
														&nbsp;(Half Day)
													</c:if>												
												</div>
												<div class="quarter bold">Prefixed</div>
												<div class="quarter">:&nbsp;<c:out  value="${leaveRequest.prefix}"></c:out></div>
											</div>
											
											
											
											<div class="line">
												<div class="quarter bold">To Date</div>
												<div class="quarter" id="toDate">:
													<c:if test="${leaveRequest.otherRemarks ne null}">
														<c:set var="remarksText" value="${leaveRequest.otherRemarks}" />
														<c:choose>
															<c:when test="${fn:contains(remarksText, 'proceed')}">${leaveRequest.toDate}</c:when>
															<c:otherwise><fmt:formatDate pattern="dd-MMM-yyyy" value="${leaveRequest.formattedToDate}" /></c:otherwise>
														</c:choose>
													</c:if>
													<c:if test="${leaveRequest.otherRemarks eq null}"><fmt:formatDate pattern="dd-MMM-yyyy" value="${leaveRequest.formattedToDate}" /></c:if>
													<%-- <fmt:formatDate pattern="dd-MMM-yyyy" value="${leaveRequest.formattedToDate}" /> --%>
													<c:if test="${leaveRequest.toHalfDayFlag=='Y'}">
														&nbsp;(Half Day)
													</c:if>
												</div>
												<div class="quarter bold">Suffixed</div>
												<div class="quarter">:&nbsp;<c:out  value="${leaveRequest.suffix}"></c:out></div>
											</div>
											
											
											
											
											
											
											<div class="line">
												<div class="quarter bold">Number of leaves</div>
												<div class="quarter" id="noOfDays">:&nbsp;<c:out  value="${leaveRequest.noOfDays}"></c:out> ${leaveRequest.debitCode}</div> <%--leaveRequest.debitLeaves+leaveRequest.prevHolidays+leaveRequest.nextHolidays --%>
											</div>	
											<div class="line">
												<div class="quarter bold">Reason</div>
												<div class="threefourth">:&nbsp;<c:out  value="${leaveRequest.reason}"></c:out></div>
											</div>											
											<c:if test="${leaveRequest.leaveTypeDetails.id=='10'}">
												<div class="line">
													<div class="quarter bold">Degree</div>
													<div>:&nbsp;<c:out  value="${leaveRequest.additionalData}"></c:out></div>
												</div>
											</c:if>
											<c:if test="${leaveRequest.leaveTypeDetails.id=='8'}">
												<div class="line">
													<div class="quarter bold">Delivery Date</div>
													<div>:&nbsp;<c:out  value="${leaveRequest.additionalData}"></c:out></div>
												</div>
											</c:if>
											<c:if test="${leaveRequest.leaveTypeDetails.id=='9'}">
												<div class="line">
													<div class="quarter bold">Child Name</div>
													<div>:&nbsp;<c:out  value="${leaveRequest.additionalData}"></c:out></div>
												</div>
											</c:if>
											<div class="line">
												<div class="quarter bold">Address</div>
												<div class="threefourth">
													:&nbsp;<c:out  value="${leaveRequest.address}"></c:out>			
												</div>
											</div>
											<div class="line">
												<div class="quarter bold">Contact Number</div>
												<div class="quarter">:&nbsp;<c:out  value="${leaveRequest.contactNumber}"></c:out></div>
												<c:if test="${leaveRequest.medicalCertName!=null}">
													<div class="quarter bold">Medical Ceritificate</div>
													<div class="quarter">:&nbsp;
														<a href="javascript:showFile('${leaveRequest.medicalCertName}')">View</a>			
													</div>
												</c:if>	
											</div>
											<div class="line">
												<c:if test="${leaveRequest.fitnessCertName!=null}">
													<div class="quarter bold">Fitness Certificate</div>
													<div class="quarter">:&nbsp;		
														<a href="javascript:showFile('${leaveRequest.fitnessCertName}')">View</a>					
													</div>
												</c:if>	
												<c:if test="${leaveRequest.otherCertName!=null}">
													<div class="quarter bold">Other Certificate</div>
													<div class="quarter">:&nbsp;		
														<a href="javascript:showFile('${leaveRequest.otherCertName}')">View</a>					
													</div>
												
												</c:if>	
											</div>
											<c:if test="${leaveRequest.remarks!=null}">
												<div class="line">
													<div class="quarter bold">Remarks</div>
													<div class="threefourth failure">:&nbsp;<c:out  value="${leaveRequest.remarks}"></c:out></div>	
												</div>
											</c:if>
											<div class="line">
												<div class="quarter bold">Leave Application Form</div>
												<div class="quarter">:&nbsp;<a href="javascript:leaveApplication('${leaveRequest.requestID}','','leave')">PDF</a></div>
												<div class="quarter bold">Leave Card</div>
												<div class="quarter">:&nbsp;<a href="javascript:showLeaveCard('${leaveRequest.sfID}')">View</a></div>
											</div>						
											
										</div>
										<c:if test="${not empty leaveRequest.requestExceptionList}">
											<div class="line">
												<fieldset><legend><strong><font class="failure">Exceptions</font></strong></legend>
													<c:forEach var="empList" items="${leaveRequest.requestExceptionList}">
														<div class="line"><img src="./images/arrow.jpg"></img> ${empList.exceptionDetails.description}</div>
													</c:forEach> 
												</fieldset>	
											</div>	
										</c:if>
									</fieldset>
									<div class="line">&nbsp;</div>
									<c:if test="${leaveRequest.type == 'cancel'}">
										<div class="line">
											<div class="quarter bold">Reason for Cancellation</div>
											<div class="half">
												<form:textarea path="reason" cols="35" rows="3" id="reason" onkeypress="textCounter(this, document.forms[0].counter, 100);" onkeyup="textCounter(this, document.forms[0].counter, 100);" style="resize: none;" />
												<input type="text" class="counter" name="counter" value="100" id="counter" disabled="disabled"/>
											</div>
										</div>
										<c:choose>
											<c:when test="${leaveRequest.ltcRequestType =='ltcAdvanceAmendment' || leaveRequest.ltcRequestType == 'ltcApprovalAmendment'}">
												<div class="line" style="width : 50%">
													<div><a href="javascript:cancelLtcLeave('${leaveRequest.requestID}','${leaveRequest.ltcRequestType}');"  class="appbutton" style="text-decoration: none;float:right">Submit</a></div>
												</div>
											</c:when>
											<c:otherwise>
												<div class="line" style="width: 50%">
													<div><a href="javascript:cancelLeave('${leaveRequest.requestID}');" class="appbutton" style="text-decoration: none;float:right">Submit</a></div>
												</div>	
											</c:otherwise>
										</c:choose>
									</c:if>	 
									<c:if test="${leaveRequest.type == 'convert'}">										
										<div class="line">
											<table width="100%" cellpadding="3" cellspacing="0" align="center" border="1" id="convertTable">
												<tr style="background-color: #ffd7ae;">
													<th width="20%">Nature of leave</th>
													<th width="10%">Available Leaves</th>
													<th width="10%">From Date</th>
													<th width="10%">To Date</th>
													<th width="8%">No of Days</th>
													<th width="21%">Prefixed</th>
													<th width="21%">Suffixed</th>
												</tr>
												<c:forEach var="leaves" items="${leaveRequest.conversionMappingDetails}" varStatus="rowCounter">
													<tr>
														<td id="${rowCounter.count}#${leaves.id}">${leaves.leaveType}</td>
														<td style="text-align:right" id="avil${rowCounter.count}">${leaves.presentLeaves}</td>
														<td>
															<input type="text" readonly="readonly" id="fromDate${rowCounter.count}" style="width:70%" onchange="javescript:getPrefix('fromDate${rowCounter.count}','${leaves.id}','${leaves.mapDays}','${leaves.leaveDetails.holidayIntFlag}');" onmousedown="javascript:textClear('fromDate${rowCounter.count}');rollBackMappedLeaves('${rowCounter.count}', '${leaves.id}');$jq('#days${rowCounter.count}').text('');$jq('#prefix${rowCounter.count}').text('');"></input> <!-- checkHolidayDate('fromDate${rowCounter.count}','prefix'); --> <!-- Old calendar -->
															<img  src="./images/calendar.gif" id="fromDatePik${rowCounter.count}" /> <!-- Old calendar -->	
															<script type="text/javascript">
																Calendar.setup({inputField :"fromDate${rowCounter.count}",ifFormat : "%d-%b-%Y",showsTime : false,button :"fromDatePik${rowCounter.count}",singleClick : true,step : 1});
															</script> <!-- Old calendar -->
															<%-- <input type="text" readonly="readonly" id="fromDate${rowCounter.count}" style="width:70%" onchange="checkHolidayDate('fromDate${rowCounter.count}','prefix');" onmousedown="javascript:textClear('fromDate${rowCounter.count}');$jq('#days${rowCounter.count}').text('');$jq('#prefix${rowCounter.count}').text('');"></input> --%> <!-- New calendar -->
															<!-- <script type="text/javascript">
																new tcal({'formname':'leave', 'controlname':'fromDate${rowCounter.count}'});
															</script> --> <!-- New calendar -->
														</td>
														<td>
															<input type="text" readonly="readonly" id="toDate${rowCounter.count}" style="width:70%" onmousedown="javascript:textClear('toDate${rowCounter.count}');rollBackMappedLeaves('${rowCounter.count}', '${leaves.id}');$jq('#days${rowCounter.count}').text('');$jq('#suffix${rowCounter.count}').text('');" onchange="javescript:getSuffix('toDate${rowCounter.count}','${leaves.id}','${leaves.mapDays}','${leaves.leaveDetails.holidayIntFlag}');"></input> <!-- checkHolidayDate('toDate${rowCounter.count}','suffix'); --> <!-- Old calendar -->
															<img  src="./images/calendar.gif" id="toDatePik${rowCounter.count}" /> <!-- Old calendar -->
															<script type="text/javascript">
																Calendar.setup({inputField :"toDate${rowCounter.count}",ifFormat : "%d-%b-%Y",showsTime : false,button :"toDatePik${rowCounter.count}",singleClick : true,step : 1});
															</script> <!-- Old calendar -->
															<%-- <input type="text" readonly="readonly" id="toDate${rowCounter.count}" style="width:70%" onmousedown="javascript:textClear('toDate${rowCounter.count}');$jq('#days${rowCounter.count}').text('');$jq('#suffix${rowCounter.count}').text('');" onchange="checkHolidayDate('toDate${rowCounter.count}','suffix');"></input> --%> <!-- New calendar -->
															<!-- <script type="text/javascript">
																new tcal({'formname':'leave', 'controlname':'toDate${rowCounter.count}'});	
															</script> --> <!-- New calendar -->
														</td>
														<td style="text-align:right" id="days${rowCounter.count}"></td>
														<td id="prefix${rowCounter.count}"></td>
														<td id="suffix${rowCounter.count}"></td>
													</tr>
												</c:forEach>
											</table>
										</div>
										
										<div class="line">
											<div class="quarter bold">Reason for Conversion</div>
											<div class="half">
												<form:textarea path="reason" cols="35" rows="3" id="reason" onkeypress="textCounter(this, document.forms[0].counter, 100);" onkeyup="textCounter(this, document.forms[0].counter, 100);" style="resize: none;" />
												<input type="text" class="counter" name="counter" value="100" id="counter" disabled="disabled" />
											</div>
										</div>
										<div class="line" id="applyLeaveBtn" style="width: 50%">
											<div>
												<div><a href="javascript:convertLeave('${leaveRequest.requestID}');"  class="appbutton" style="text-decoration: none;float:right">Submit</a></div>
											</div>
										</div>	
									</c:if>			
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
		<form:hidden path="requestID"/>
		<form:hidden path="requestId"/>
		<form:hidden path="ltcRequestType"/>
		<form:hidden path="convertLeaves"/>
		</form:form>	
		<script>$jq('#reason').val('');</script>	
		
	</body>
</html>
<!-- End:LeaveRequestDetails.jsp -->