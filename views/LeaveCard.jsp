<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LeaveCard.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<title>Leave Card</title>
</head>

<body>
	<form:form method="post" commandName="leaveRequest">
		<div>
			<div class="Innermaindiv">
				<div class="header"></div>
				<c:choose>
					<c:when test="${leaveRequest.menuFlag != 'disable'}">
						<div><jsp:include page="MenuLinks.jsp" /></div>	
					</c:when>	
					<c:otherwise>
						<div style="height: 45px;">&nbsp;</div>	
					</c:otherwise>
				</c:choose>
				<div class="mainDisplay">
					<div class="whitebox1">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								<div class="whitebox_tr"></div>
							</div>
						</div>
						<div class="whitebox_m1">
							<div class="lightblueBg1" style="background-color: #E5FDE5;">
								<div class="line" style="text-align:center">
									<div class="line" style="font-weight: bold; font-size: 22px;">ADVANCED SYSTEMS LABORATORY</div>
									<div class="line">Post Kanchanbagh, Hyderabad - 500 058</div><!-- -->
									<div class="line" style="font-size: 15px;font-weight:bold;">
										<c:if test="${leaveRequest.gazType == 'GAZETTED'}">OFFICERS LEAVE CARD FOR THE YEAR ${leaveRequest.currentYear}</c:if>
										<c:if test="${leaveRequest.gazType == 'NON GAZETTED'}">N.G. STAFF LEAVE CARD FOR THE YEAR ${leaveRequest.currentYear}</c:if>
									</div>
									<!--  <div class="line" style="font-size: 15px;font-weight:bold;">(LEAVE REGULARISED UPTO JAN 1	)</div> -->
								</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div style="text-align:center;width:100%;margin-bottom:10px;float:left">
										<div style="width:4%;float:left;text-align:left;font-weight:bold">Name:&nbsp;</div>
										<div style="width:17%;float:left;text-align:left">${leaveRequest.empName}</div>
										<div style="width:3%;float:left;text-align:left;font-weight:bold">SFID:&nbsp;</div>
										<div style="width:7%;float:left;text-align:left">${leaveRequest.sfID}</div>
										<div style="width:7%;float:left;text-align:left;font-weight:bold">Designation:&nbsp;</div>
										<div style="width:20%;float:left;text-align:left">${leaveRequest.designation}</div>
										<div style="width:5%;float:left;text-align:left;font-weight:bold">Division:&nbsp;</div>
										<div style="width:12%;float:left;text-align:left">${leaveRequest.department}</div>
										<div style="width:18%;float:left;text-align:left;font-weight:bold">Date of Retirement / Relieving :</div>
										<div style="width:7%;float:left;text-align:left">${leaveRequest.lastWorkingDay}</div>
									</div>
									
									<c:choose>
										<c:when test="${not empty leaveRequest.earnedLeaveCard || not empty leaveRequest.casualLeaveCard || fn:length(leaveRequest.onetimeEntryBalance) ne 0}">				
										<% int val = 0; %>
											<c:forEach var="roles" items="${sessionScope.rolelist}">
												<c:if test="${roles eq 'ROLE_SUPERADMIN' || roles eq 'ROLE_ADMIN' || roles eq 'ROLE_PERSI' || roles eq 'ROLE_PERSII' || roles eq 'ROLE_LEAVE_TASK_HOLDER1'}">
													<% val++; %>
												</c:if>
											</c:forEach>
										<div class="line">
											<table width="100%" cellpadding="2" cellspacing="0" border="1" id="cltable">
												<tr>
													<th colspan="11" style="background-color: #DCDCDC;">Casual Leave</th>											
												</tr>																					
												<tr>
													<th style="width:5%">Request ID</th>
													<th style="width:10%">Leave Type</th>
													<th style="width:14%">Applied Date/Transaction Date</th>
													<th style="width:5%">Total Days </th>
													<th style="width:10%">From Date</th>
													<th style="width:10%">To Date</th>
													<th style="width:6%">Leaves(+/-)</th>
													<th style="width:11%">Balance</th>
													<th style="width:9%">Status</th>
													<th style="width:15%">Request Type</th>
													<% if (val > 0) { %>
														<th style="width:5%">Do Part ID</th>
													<% } %>
												</tr>
												<tr>
													<td colspan="6"><div class="curyear"></div></td>	
													<c:choose>
														<c:when test="${fn:length(leaveRequest.yearAvailableLeaves) ne 0}">
															<c:forEach var="clbal" items="${leaveRequest.yearAvailableLeaves}">
																<c:if test="${clbal.leaveTypeID eq '3'}">
																	<td style="text-align:left" colspan="5">${clbal.balance}</td>
																</c:if>
															</c:forEach>
														</c:when>
														<c:when test="${fn:length(leaveRequest.onetimeEntryBalance) ne 0 || fn:length(leaveRequest.yearAvailableLeaves) eq 0}">
															<c:forEach var="clbal" items="${leaveRequest.onetimeEntryBalance}">
																<c:if test="${clbal.leaveTypeDetails.id eq '3'}">
																	<td style="text-align:left" colspan="3">${clbal.noOfLeaves}</td><td style="text-align:left" colspan="2"><font color="red"><b>First Time Entry</b></font><span id="firstEntryDate" style="display: none;">${clbal.strTxnDate}</span></td>
																</c:if>
															</c:forEach>
														</c:when>
														<c:otherwise><td style="text-align:left" colspan="5"></td></c:otherwise>
													</c:choose>
												</tr>
												<c:if test="${not empty leaveRequest.casualLeaveCard}">
													<c:forEach var="casual" items="${leaveRequest.casualLeaveCard}" varStatus="rowCounter">
														<tr>
															<td style="text-align:right">${casual.requestId}</td>
															<td>${casual.leaveType}</td>
															<td>${casual.appliedDate}</td>
															<td style="text-align:right">${casual.totalDays}</td>
															<td style="text-align:right">${casual.fromDate}</td>
															<td style="text-align:right">${casual.toDate}</td>
															<c:if test="${casual.days eq '-.5'}">
																<td style="text-align:right">-0.5</td>
															</c:if>
															<c:if test="${casual.days ne '-.5'}">
																<td style="text-align:right">${casual.days}</td>
															</c:if>
															<td style="text-align:right">${casual.balance}</td>
															<td>${casual.status}</td>
															<td>${casual.requestType}</td>	
															<% if (val > 0) { %>
																<td style="text-align:right">${casual.doPartNo}</td>
															<% } %>
														</tr>											
													</c:forEach>		
												</c:if>								
											</table>
										</div>

										<div class="line">
											<table width="100%" cellpadding="2" cellspacing="0" border="1" id="eltable">
												<tr>
													<th colspan="11" style="background-color: #DCDCDC;">Earned/Extraordinary Leave</th>											
												</tr>										
												<tr>
													<th style="width:5%">Request ID</th>
													<th style="width:10%">Leave Type</th>
													<th style="width:14%">Applied Date/Transaction Date</th>
													<th style="width:5%">Total Days </th>
													<th style="width:10%">From Date</th>
													<th style="width:10%">To Date</th>
													<th style="width:6%">Leaves(+/-)</th>
													<th style="width:11%">Balance</th>
													<th style="width:9%">Status</th>
													<th style="width:15%">Request Type</th>
													<% if (val > 0) { %>
														<th style="width:5%">Do Part ID</th>
													<% } %>	
												</tr>
												<tr>
													<td colspan="6"><div class="curyear"></div></td>												
													<c:choose>
														<c:when test="${fn:length(leaveRequest.yearAvailableLeaves) ne 0}">
															<c:forEach var="elbal" items="${leaveRequest.yearAvailableLeaves}">
																<c:if test="${elbal.leaveTypeID eq '1'}">
																	<td style="text-align:left" colspan="5">${elbal.balance}</td>
																</c:if>
															</c:forEach>
														</c:when>
														<c:when test="${fn:length(leaveRequest.onetimeEntryBalance) ne 0 || fn:length(leaveRequest.yearAvailableLeaves) eq 0}">
															<c:forEach var="elbal" items="${leaveRequest.onetimeEntryBalance}">
																<c:if test="${elbal.leaveTypeDetails.id  eq '1'}">
																	<td style="text-align:left" colspan="3">${elbal.noOfLeaves}</td><td style="text-align:left" colspan="2"><font color="red"><b>First Time Entry</b></font></td>
																</c:if>
															</c:forEach>
														</c:when>
														<c:otherwise><td style="text-align:left" colspan="5"></td></c:otherwise>
													</c:choose>	
												</tr>
												<c:if test="${not empty leaveRequest.earnedLeaveCard}">
													<c:forEach var="earned" items="${leaveRequest.earnedLeaveCard}"  varStatus="rowCounter">
														<tr>
															<td style="text-align:right">${earned.requestId}</td>
															<td>${earned.leaveType}</td>
															<td>${earned.appliedDate}</td>
															<td style="text-align:right">${earned.totalDays}</td>
															<td style="text-align:right">${earned.fromDate}</td>
															<td style="text-align:right">${earned.toDate}</td>
	                                                        <td style="text-align:right">${earned.days}</td>
	                                                     	<td style="text-align:right"><c:if test="${not empty earned.balance}">${earned.balance}</c:if></td>
															<td>${earned.status}</td>
															<td>${earned.requestType}</td>	
															<% if (val > 0) { %>
																<td style="text-align:right">${earned.doPartNo}</td>
															<% } %>
														</tr>										
													</c:forEach>	
												</c:if>							
											</table>
										</div>

										<div class="line">
											<table width="100%" cellpadding="2" cellspacing="0" border="1" id="hpltable">
												<tr>
													<th colspan="11" style="background-color: #DCDCDC;">Half Pay/Commuted Leave/Leave Not Due</th>											
												</tr>																					
												<tr>
													<th style="width:5%">Request ID</th>
													<th style="width:10%">Leave Type</th>
													<th style="width:14%">Applied Date/Transaction Date</th>
													<th style="width:5%">Total Days </th>
													<th style="width:10%">From Date</th>
													<th style="width:10%">To Date</th>
													<th style="width:6%">Leaves(+/-)</th>
													<th style="width:11%">Balance</th>
													<th style="width:9%">Status</th>
													<th style="width:15%">Request Type</th>
													<% if (val > 0) { %>
														<th style="width:5%">Do Part ID</th>
													<% } %>
												</tr>
												<tr>
													<td colspan="6"><div class="curyear"></div></td>				
													<c:choose>
														<c:when test="${fn:length(leaveRequest.yearAvailableLeaves) ne 0}">
															<c:forEach var="hplbal" items="${leaveRequest.yearAvailableLeaves}">
																<c:if test="${hplbal.leaveTypeID eq '2'}">
																	<td style="text-align:left" colspan="5">${hplbal.balance}</td>
																</c:if>
															</c:forEach>
														</c:when>
														<c:when test="${fn:length(leaveRequest.onetimeEntryBalance) ne 0 || fn:length(leaveRequest.yearAvailableLeaves) eq 0}">
															<c:forEach var="hplbal" items="${leaveRequest.onetimeEntryBalance}">
																<c:if test="${hplbal.leaveTypeDetails.id  eq '2'}">
																	<td style="text-align:left" colspan="3">${hplbal.noOfLeaves}</td><td style="text-align:left" colspan="2"><font color="red"><b>First Time Entry</b></font></td>
																</c:if>
															</c:forEach>														
														</c:when>
														<c:otherwise><td style="text-align:left" colspan="5"></td></c:otherwise>
													</c:choose>		
												</tr>
												<c:if test="${not empty leaveRequest.halfPayLeaveCard}">
													<c:forEach var="halfPay" items="${leaveRequest.halfPayLeaveCard}"  varStatus="rowCounter">
														<tr>
															<td style="text-align:right">${halfPay.requestId}</td>
															<td>${halfPay.leaveType}</td>
															<td>${halfPay.appliedDate}</td>
															<td style="text-align:right">${halfPay.totalDays}</td>
															<td style="text-align:right">${halfPay.fromDate}</td>
															<td style="text-align:right">${halfPay.toDate}</td>
	                                                        <td style="text-align:right">${halfPay.days}</td>
															<td style="text-align:right">${halfPay.balance}</td>
															<td>${halfPay.status}</td>
															<td>${halfPay.requestType}</td>	
															<% if (val > 0) { %>
																<td style="text-align:right">${halfPay.doPartNo}</td>
														    <% } %>
														</tr>
													</c:forEach>	
												</c:if>
											</table>
										</div>
								
									<c:if test="${not empty leaveRequest.childCareLeaveCard}">
										<div class="line">
											<table width="100%" cellpadding="2" cellspacing="0" border="1" id="ccltable">
												<tr>
													<th colspan="11" style="background-color: #DCDCDC;">Child Care Leave</th>											
												</tr>	
												<tr>
													<th style="width:7%">Request ID</th>
													<th style="width:10%">Leave Type</th>
													<th style="width:14%">Applied Date/Transaction Date</th>
													<th style="width:5%">Total Days </th>
													<th style="width:10%">From Date</th>
													<th style="width:10%">To Date</th>
													<th style="width:6%">Leaves(+/-)</th>
													<th style="width:11%">Balance</th>
													<th style="width:9%">Status</th>
													<th style="width:15%">Request Type</th>
													<% if (val > 0) { %>
														<th style="width:5%">Do Part ID</th>
												    <% } %>
												</tr>
												<tr>
													<td colspan="6"><div class="curyear"></div></td>	
													<c:choose>
														<c:when test="${fn:length(leaveRequest.yearAvailableLeaves) ne 0}">
															<c:forEach var="cclbal" items="${leaveRequest.yearAvailableLeaves}">
																<c:if test="${cclbal.leaveTypeID eq '9'}">
																	<td style="text-align:left" colspan="6">${cclbal.balance}</td>
																</c:if>
															</c:forEach>
														</c:when>
														<c:when test="${fn:length(leaveRequest.onetimeEntryBalance) ne 0 || fn:length(leaveRequest.yearAvailableLeaves) eq 0}">
															<c:forEach var="cclbal" items="${leaveRequest.onetimeEntryBalance}">
																<c:if test="${clbal.leaveTypeDetails.id  eq '9'}">
																	<td style="text-align:left" colspan="2">${cclbal.noOfLeaves} </td><td style="text-align:left" colspan="2"><font color="red"><b>First Time Entry</b></font></td>
																</c:if>
															</c:forEach>
														</c:when>
														<c:otherwise><td style="text-align:left" colspan="4"></td></c:otherwise>
													</c:choose>						
												</tr>
												<c:forEach var="childCare" items="${leaveRequest.childCareLeaveCard}"  varStatus="rowCounter">
													<tr>
														<td style="text-align:right">${childCare.requestId}</td>
														<td>${childCare.leaveType}</td>
														<td>${childCare.appliedDate}</td>
														<td style="text-align:right">${childCare.totalDays}</td>
														<td style="text-align:right">${childCare.fromDate}</td>
														<td style="text-align:right">${childCare.toDate}</td>
														<td style="text-align:right">${childCare.days}</td>
														<td style="text-align:right">${childCare.balance}</td>
														<td>${childCare.status}</td>
														<td>${childCare.requestType}</td>	
														<% if (val > 0) { %>
															<td style="text-align:right">${childCare.doPartNo}</td>
												    	<% } %>										
													</tr>
												</c:forEach>										
											</table>
										</div>
									</c:if>
									<!-- 
									<c:if test="${not empty leaveRequest.leaveNotDueLeaveCard}">
										<div class="line">
											<table width="100%" cellpadding="2" cellspacing="0" border="1" id="lndtable">
												<tr>
													<th colspan="6">Leave Not Due</th>											
												</tr>																					
												<tr>
													<th style="width:15%">Days</th>
													<th style="width:15%">From Date</th>
													<th style="width:15%">To Date</th>
													<th style="width:15%">Balance</th>
													<th style="width:20%">Status</th>
													<th style="width:20%">Request Type</th>
												</tr>
												<tr>
													<td id="lndsource"></td>
													<td colspan="4"><div class="curyear"></div></td>												
													<td></td>
												</tr>
												<c:forEach var="leaveNotDue" items="${leaveRequest.leaveNotDueLeaveCard}"  varStatus="rowCounter">
													<tr>
														<td style="text-align:right">${leaveNotDue.days}</td>
														<td style="text-align:right">${leaveNotDue.fromDate}</td>
														<td style="text-align:right">${leaveNotDue.toDate}</td>
														<td style="text-align:right">${leaveNotDue.balance}</td>
														<td>${leaveNotDue.status}</td>
														<td>${leaveNotDue.requestType}</td>												
													</tr>											
												</c:forEach>										
											</table>
										</div>
									</c:if> -->
									<c:if test="${not empty leaveRequest.otherLeaveCard}">									
										<div class="line">
											<table width="100%" cellpadding="2" cellspacing="0" border="1">
												<tr>
													<th colspan="9" style="background-color: #DCDCDC;">Other Leave</th>											
												</tr>										
												<tr>
													<th style="width:7%">Request ID</th>
													<th style="width:28%">Nature of Leave</th>												
													<th style="width:10%">From Date</th>
													<th style="width:10%">To Date</th>
													<th style="width:10%">Debit Leaves</th>
													<th style="width:7%">Total Days </th>
													<th style="width:9%">Status</th>
													<th style="width:14%">Request Type</th>
													<% if (val > 0) { %>
														<th style="width:5%">Do Part ID</th>
												    <% } %>
												</tr>
												<c:forEach var="other" items="${leaveRequest.otherLeaveCard}"  varStatus="rowCounter">
													<tr>
														<td style="text-align:right">${other.requestId}</td>													
														<td>${other.leaveType}</td>
														<td style="text-align:right">${other.fromDate}</td>
														<td style="text-align:right">${other.toDate}</td>
														<td style="text-align:right">${other.days}</td>
														<td style="text-align:right">${other.totalDays}</td>
														<td>${other.status}</td>
														<td>${other.requestType}</td>	
														<% if (val > 0) { %>
															<td style="text-align:right">${other.doPartNo}</td>
												   		 <% } %>
													</tr>
												</c:forEach>
											</table>
										</div>
									</c:if>
									<c:if test="${not empty leaveRequest.specialCasualLeaveCard}">									
										<div class="line">
											<table width="100%" cellpadding="2" cellspacing="0" border="1">
												<tr>
													<th colspan="7" style="background-color: #DCDCDC;">Special Casual Leave</th>											
												</tr>										
												<tr>
													<th style="width:7%">Request ID</th>
													<th style="width:30%">Nature of Leave</th>												
													<th style="width:10%">From Date</th>
													<th style="width:10%">To Date</th>
													<th style="width:11%">Debit Leaves</th>
													<th style="width:11%">Total Days </th>
													<th style="width:23%">Status</th>
													<% if (val > 0) { %>
														<th style="width:5%">Do Part ID</th>
												   	<% } %>
												</tr>
												<c:forEach var="scl" items="${leaveRequest.specialCasualLeaveCard}"  varStatus="rowCounter">
													<tr>	
														<td style="text-align:right">${scl.requestId}</td>												
														<td>${scl.leaveType}</td>
														<td style="text-align:right">${scl.fromDate}</td>
														<td style="text-align:right">${scl.toDate}</td>
														<td style="text-align:right">${scl.days}</td>
														<td style="text-align:right">${scl.totalDays}</td>
														<td>${scl.status}</td>
														<% if (val > 0) { %>
															<td style="text-align:right">${scl.doPartNo}</td>
												   		<% } %>
													</tr>
												</c:forEach>										
											</table>
										</div>
									</c:if>
									
								</c:when>
								<c:otherwise>
									<div style="margin-top:40px;text-align:center; width:100%;"> <font color="brown" size="5"> Leave Card is empty as Joining/Retirement Date is : <font color="red"><u>${leaveRequest.retirementDate}</u></font></font></div>	
								 </c:otherwise>
							</c:choose>	
						</div>
								<%-- Content Page end --%>								
								<div><b>Note:&nbsp;</b>Leaves are displayed in the order of applied date.</div>
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
		<form:hidden path="currentYear" id="currentYear"/>
		</form:form>
		<script>
			setSource();
		</script>
	</body>
</html>
<!-- End:LeaveCard.jsp -->
