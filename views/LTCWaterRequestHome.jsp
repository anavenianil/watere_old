<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:LTCWaterRequestHome.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css"
	rel="stylesheet" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript"
	src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/ltc.js"></script>
<!-- <script type="text/javascript" src="script/tada.js"></script> -->
<title>LTC ADVANCE HOME</title>
</head>
<body>
	<form:form method="post" commandName="ltcwater" name="ltcWaterApproval"
		id="ltcWaterApproval">
		<div>
			<div class="Innermaindiv">
				<div class="header"></div>
				<div><jsp:include page="MenuLinks.jsp" /></div>
				<div class="mainDisplay">
					<div class="whitebox_t">
						<div class="whitebox_tl">
							<div class="whitebox_tr"></div>
						</div>
					</div>
					<div class="whitebox_m1">
						<div class="lightblueBg1">
							<div class="headTitle">APPLICATION FOR ANNUAL LEAVE</div>
							<div class="line" id="result"></div>
							<div>
								<div>
									<b>Name & Designation:</b>&nbsp;${ltcwater.empDetailsList.nameInServiceBook}&nbsp;&nbsp;&
									${ltcwater.empDetailsList.designationDetails.name}<br /> </br> <b>Employee
										ID. & Phone No. </b>:&nbsp;${ltcwater.empDetailsList.userSfid}&nbsp;&nbsp;&
									&nbsp;${ltcwater.empDetailsList.personalNumber}</br> </br>
								</div>
								<div>
									<div>
										<b>Directorate in which working</b> &nbsp;
										:&nbsp;${ltcwater.deptDTO.deptName}
									</div>
								</div>
								</br>
								<fieldset style="border-color: green">
									<legend>
										<strong><font color='green'><b>ANNUAL
													LEAVE REQUEST INFORMATION</b></font></strong>
									</legend>
									<table cellspacing="2" cellpadding="5">
										<tr>
											<td colspan="4">
												<div id="result" class="line">
													<%-- <jsp:include page="LtcFamilyDataList.jsp" /> --%>
													<jsp:include page="LtcWaterFamilyDataList.jsp" />
												</div>
											</td>
										</tr>
										<tr>
											<td style="display: none">Type Of Ltc :</td>
											<td style="display: none"><form:input path="typeOfLtc"
													id="typeOfLtc" maxlength="50" value="HomeTown"
													readonly="true" onkeypress="return checkChar(event);" /></td>
											<td>Annual Year : <span class="mandatory">*</span>
											</td>
											<td><select name="ltcYear" path="ltcYear" id="ltcYear"
												onchange="javascript:checkApplingLtcYear();checkYearValidOrnot()">
													<c:forEach var="item" items="${ltcwater.ltcYearList}">
														<option value="${item.id}">${item.year}</option>
													</c:forEach>
											</select></td>
										</tr>
										<%-- ${ltcwater.homeTownAddress} --%>
										<tr>
											<td>HomeTown Address :</td>
											<td>
												<div id="hometownAddr"
													style="border: 1px solid #ddd; margin-right: 20px; padding: 4px">
													&nbsp;<b>${ltcwater.homeTownAddress.address1}&nbsp;${ltcwater.homeTownAddress.address2}&nbsp;<br />${ltcwater.homeTownAddress.address3}&nbsp;
														${ltcwater.homeTownAddress.city}&nbsp;<br />${ltcwater.homeTownAddress.districtDetails.name}&nbsp;${ltcwater.homeTownAddress.stateDetails.name}-${ltcwater.homeTownAddress.pincode}</b>
												</div>
												</div>

											</td>
											<td>
												<%-- <form:textarea path="hometownAddr" id="hometownAddr" rows="3"
												cols="50" /> --%>
											</td>
											<td></td>
										</tr>
										<tr>
											<td>Start Date Of Holiday : <span class="mandatory">*</span></td>
											<td><form:input path="startHoliday" id="startHoliday"
													cssClass="dateClass" readonly="true"
													onchange="javascript:noOfdays()"
													onclick="javascript:clearDateText('startHoliday');" />
												&nbsp; <img src="./images/calendar.gif"
												id="date_start_trigger5" /> <script type="text/javascript">
													Calendar
															.setup({
																inputField : "startHoliday",
																ifFormat : "%d-%b-%Y",
																showsTime : false,
																button : "date_start_trigger5",
																singleClick : true,
																step : 1
															});
												</script></td>
											<td>Return Date Of Holiday : <span class="mandatory">*</span></td>
											<td><form:input path="returnHoliday" id="returnHoliday"
													cssClass="dateClass" readonly="true"
													onchange="javascript:noOfdays()"
													onclick="javascript:clearDateText('returnHoliday');" />
												&nbsp; <img src="./images/calendar.gif"
												id="date_start_trigger4" /> <script type="text/javascript">
													Calendar
															.setup({
																inputField : "returnHoliday",
																ifFormat : "%d-%b-%Y",
																showsTime : false,
																button : "date_start_trigger4",
																singleClick : true,
																step : 1
															});
												</script></td>
										</tr>
										<tr>
											<td>Number Of Days :</td>
											<td><form:input path="nod" id="nod" maxlength="50"
													readonly="true" onkeypress="return checkChar(event);" /></td>
											<%-- <td>Date Of Join :</td>
											<td><form:input path="doj" id="doj" maxlength="50" value="${ltcwater.empDetailsList.dojAsl}"  readonly="true" onkeypress="return checkChar(event);"/></td>
											 --%>
											<td>Leave Type :</td>
											<td><form:input path="leaveType" id="leaveType"
													maxlength="50" value="Annual Leave" readonly="true"
													onkeypress="return checkChar(event);" /></td>
										</tr>
										<tr>
											<td>Number Of Adults Tickets :</td>
											<td><form:input path="noOfAdultsTickets"
													id="noOfAdultsTickets" maxlength="10"
													onkeyup="javascript:totalTicketsvalue()"
													onkeypress="javascript:return checkInt(event);" /></td>
											<td>Number Of Children Tickets :</td>
											<td><form:input path="noOfChildrenTickets"
													id="noOfChildrenTickets" maxlength="10"
													onkeyup="javascript:totalTicketsvalue()"
													onkeypress="javascript:return checkInt(event);" /></td>
										</tr>
										<tr>
											<td>Total No.of Tickets</td>
											<td><form:input path="totalTickets" id="totalTickets"
													readonly="true" maxlength="10"
													onkeypress="javascript:return checkInt(event);" /></td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td colspan="2" align="center">
												<div class="expbutton">
													<a onclick="javascript:submitLTCWaterRequest()"> <span>Submit</span></a>
												</div>
												<div class="expbutton">
													<a onclick="javascript:clearLTCWaterRequest()"> <span>Clear</span></a>
												</div>
											</td>
										</tr>
									</table>
								</fieldset>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div><jsp:include page="Footer.jsp" /></div>
		</div>
		<form:hidden path="param" />
		<form:hidden path="type" />
		<form:hidden path="requestID" />
		<form:hidden path="requestId" />
	</form:form>
</body>
</html>