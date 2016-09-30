<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LeaveDaysEntry.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css"
	rel="stylesheet" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript"
	src="script/jquery.displaytag-ajax-1.2.js"></script>

<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<title>LeaveDays</title>
</head>

<body>
	<form:form method="post" commandName="LeaveWaterRequest" id="LeaveWaterRequest">
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
							
								<div class="headTitle" id="title">Enter No.Of Leaves</div>
								<div>
								<div class="line" id="result"></div>
							<%-- 	<div class="line">
											<div class="quarter bold">Select Annual Year<span class="mandatory">*</span></div>
											<div class="quarter bold">
										<select name="ltcYear" path="ltcYear" id="ltcYear"  onchange="javascript:checkApplingLtcYear();checkYearValidOrnot()">
													<c:forEach var="item" items="${LeaveWaterRequest.setleaveYearsList}">
														<option value="${item.id}">${item.year}</option>
													</c:forEach>
											</select>
												
											</div>
									</div> --%>
									<div class="line">
										<table class="list" cellpadding="5">
										
										<tr>
										
										<td colspan="4">
										<b>Select Annual Year</b>
										<select name="ltcYear" path="leaveYear" id="leaveYear" >
													<c:forEach var="item" items="${LeaveWaterRequest.setleaveYearsList}">
														<option value="${item.id}">${item.year}</option>
													</c:forEach>
											</select>
										</td>
										
										</tr>
										
											<tr>
												<th>Leave Type</th>
												<th>No.Of Days For Permanent Employees</th>
												<th>No.of Days For Contract Employees</th>
												<th>Applicable Gender</th>
											</tr>
											<tr>
												<td>Annual Leave</td>
												<td><form:input path="annualLeavePermanent" id="annualLeavePermanent"  maxlength="30" /></td>
												<td><form:input path="annualLeaveContract" id="annualLeaveContract"  maxlength="30" /></td>
												<td>
													<form:radiobutton path="annualLeaveGender" label="Male" value="M" id="male1" />
													<form:radiobutton path="annualLeaveGender" label="Female" value="F" id="female1" />
													<form:radiobutton path="annualLeaveGender"  label="All"  value="A" id="all1"  />
												</td>
											</tr>
											<tr>
												<td>Sick Leave</td>
												<td><form:input path="sickLeavePermanent" id="sickLeavePermanent"  maxlength="30" /></td>
												<td><form:input path="sickLeaveContract" id="sickLeaveContract"  maxlength="30" /></td>
												<td>
													<form:radiobutton path="sickLeaveGender" label="Male" value="M" id="male2"/>
													<form:radiobutton path="sickLeaveGender" label="Female" value="F" id="female2"/>
													<form:radiobutton path="sickLeaveGender" label="All" value="A" id="all2"/>
												</td>
											</tr>
											<tr>
												<td>Maternity Leave</td>
												<td><form:input path="maternityLeavePermanent" id="maternityLeavePermanent"  maxlength="30" /></td>
												<td><form:input path="maternityLeaveContract" id="maternityLeaveContract"  maxlength="30" /></td>
												<td>
													<%-- <form:radiobutton path="maternityLeaveGender" label="Male" value="M" id="male3"/> --%>
													<form:radiobutton path="maternityLeaveGender" label="Female" value="F" id="female3"/>
												<%-- 	<form:radiobutton path="maternityLeaveGender" label="All" value="a" id="all3"/> --%>
												</td>
											</tr>
											<tr>
												<td>Paternity Leave</td>
												<td><form:input path="paternityLeavePermanent" id="paternityLeavePermanent"  maxlength="30" /></td>
												<td><form:input path="paternityLeaveContract" id="paternityLeaveContract"  maxlength="30" /></td>
												<td>
													<form:radiobutton path="paternityLeaveGender" label="Male" value="M" id="male4"/>
												<%--	<form:radiobutton path="paternityLeaveGender" label="Female" value="F" id="female4"/>
												 	<form:radiobutton path="paternityLeaveGender" label="All" value="A" id="all4"/> --%>
												</td>
											</tr>

									<!-- 		<tr>
											
											<td colspan="4" style="text-align: center;">

												<div class="expbutton">
													<a onclick="javascript:submitLTCWaterRequest()"> <span>Submit</span></a>
												</div>
												<div class="expbutton">
													<a onclick="javascript:clearLTCWaterRequest()"> <span>Clear</span></a>
												</div>
							
											</td>
										</tr> -->
										</table>
										<table width="100%" cellpadding="10">
											<tr>
											<td width="25%">&nbsp;</td>
											<td width="25%">&nbsp;</td>
											<td width="25%">

												<div class="expbutton">
													<a onclick="javascript:submitLeaveDays()"> <span>Submit</span></a>
												</div>
												<div class="expbutton">
													<a onclick="javascript:clearLeaveDays()"> <span>Clear</span></a>
												</div>
			
											</td>
											<td width="25%">&nbsp;</td>
										</tr> 
										
										</table>
									</div>
									<div>&nbsp;</div>
								</div>
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
		<form:hidden path="type" />
		<form:hidden path="param" />
		<form:hidden path="requestID" />
		<form:hidden path="requestId" />
	</form:form>
	
	<script>
	document.getElementById('all1').checked = true;
	document.getElementById('all2').checked = true;
	document.getElementById('female3').checked = true;
	document.getElementById('male4').checked = true;
	</script>
</body>
</html>