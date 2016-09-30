<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:TadaApprovalRequest.jsp -->
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
<script type="text/javascript" src="script/tada.js"></script>

<title>Tada Approval Request</title>

</head>
<body
	onload="javascript:multiSelectBox();enableConveyanceModes(0);enableGpfAcNo();">
	<form:form method="post" commandName="tada" id="conveyanceModeList"
		name="${sessionScope.conveyanceModeList}">
		<div id="tada">
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
								<div class="headTitle">Requistion Cum Movement Order For
									Temporary Duty Move</div>
								<%-- Content Page starts --%>
								<c:choose>
									<c:when test="${tada.statusMsg ne 'failure' }">

										<div class="line">
											<div class="line" id="result"></div>
											<div class="line"></div>
											<div class="line">
												<div class="quarter leftmar">
													Name & Designation<br />SFNo. & Phone No.
												</div>
												<div class="half">
													:&nbsp;${tada.empDetailsList.nameInServiceBook}&nbsp;&nbsp;&
													${tada.empDetailsList.designationDetails.name}<br />:&nbsp;${tada.empDetailsList.userSfid}&nbsp;&nbsp;
													${tada.empDetailsList.personalNumber}
												</div>
											</div>
											<!--added by bkr 21/04/2016 only one div hidden purpose  -->
											<div style="display: none;">
												<div class="line">
													<div class="quarter leftmar">Basic Pay & Grade Pay</div>
													<div class="quarter">
														:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font>${tada.payDetailsList.basicPay}/-&nbsp;&nbsp;&
														<font size="4.5em"><span class="WebRupee">R</span></font>${tada.payDetailsList.gradePay}/-
													</div>
												</div>
											</div>

											<div class="line">
												<div class="quarter leftmar">Directorate in which
													working</div>
												<div class="half">:&nbsp;${tada.deptDTO.deptName}</div>
											</div>
											<div class="line" id="amendmentTypeDiv" style="display: none">
												<div class="quarter leftmar">
													Amendment Type<span class="mandatory">*</span>
												</div>
												<div class="quarter">
													:&nbsp;
													<form:radiobutton path="amendmentType" id="amendmentType"
														value="amendment"
														onclick="javascript:showRequestDetails();" />
													<label>Amendment</label>
													<form:radiobutton path="amendmentType" id="amendmentType"
														value="cancel" onclick="javascript:showRequestDetails();" />
													<label>Cancellation</label>
												</div>
											</div>
											<div id="requestDetailsDiv">
												<div class="line">
													<div class="quarter leftmar">Total Stay Duration at
														Out Station in Days&nbsp;</div>
													<div class="quarter">
														<form:hidden path="stayDuration" id="stayDuration"
															onkeypress="javascript:return checkInt(event);" />
														<div id="stayDurationId"
															style="font-size: larger; font-family: cursive;">:&nbsp;${tada.stayDuration}</div>
														<!--change to hidden true  -->
													</div>
													<div class="quarter leftmar">
														TD Place<span class="mandatory">*</span>
													</div>
													<div class="quarter">
														:&nbsp;
														<form:input path="workingPlace" id="workingPlace"
															maxlength="30"
															onkeypress="javascript:return checkChar(event);" />
													</div>
												</div>
												<div class="line">
													<div class="quarter leftmar" style="text-align: justify">
														Exact Place Of Work at TD station with postal address,name
														and phone no. of the person to be contacted<span
															class="mandatory">*</span>
													</div>
													<div class="quarter">
														:&nbsp;
														<form:textarea path="tdWorkPlace" id="tdWorkPlace"
															rows="4" cols="15"
															onkeypress="javascript:textCounter(this,document.forms[0].counter1,150);"
															onkeyup="textCounter(this,document.forms[0].counter1,150);" />
														<input type="text" class="counter" name="counter"
															value="150" id="counter1" />
													</div>
													<div class="quarter leftmar">
														Purpose<span class="mandatory">*</span>
													</div>
													<div class="quarter">
														:&nbsp;
														<form:textarea path="purpose" id="purpose" rows="4"
															cols="15"
															onkeypress="textCounter(this,document.forms[0].counter2,150);"
															onkeyup="textCounter(this,document.forms[0].counter2,150);" />
														<input type="text" class="counter" name="counter"
															value="150" id="counter2" />
													</div>
												</div>



												<!--added by bkr 21/04/2016 only one div hidden purpose  -->
												<div style="display: none;">
													<div class="line">
														<div class="quarter leftmar">
															TD move is authorized for<span class="mandatory">*</span>
														</div>
														<div class="quarter">
															:
															<c:if
																test="${tada.empDetailsList.workingLocation==null || tada.empDetailsList.workingLocation=='1'}">
																<form:radiobutton path="authorizedMove"
																	id="authorizedMove" value="1" checked="checked"
																	onclick="javascript:showProjDirNames();" />
																<label>Build-Up</label>
															</c:if>
															<c:if
																test="${tada.empDetailsList.workingLocation!=null && tada.empDetailsList.workingLocation!='1'}">
																<input type="radio" name="authorizedMove"
																	id="authorizedMove" value="1" disabled="disabled"
																	onclick="javascript:showProjDirNames();" />
																<label>Build-Up</label>
															</c:if>
															<form:radiobutton path="authorizedMove"
																id="authorizedMove" value="2"
																onclick="javascript:showProjDirNames();" />
															<label>Project</label>
														</div>
														<div class="quarter leftmar">
															Whether TA/DA is required with Hotel/Normal rates<span
																class="mandatory">*</span>
														</div>
														<div class="quarter">
															:&nbsp;
															<form:radiobutton path="tadaRequirement"
																id="tadaRequirement" value="hotelrate" />
															<label>Hotel Rates</label>
															<form:radiobutton path="tadaRequirement"
																id="tadaRequirement" value="normalrate" />
															<label>Normal Rates</label>
															<!--<form:radiobutton path="tadaRequirement" id="tadaRequirement" value="na"/><label>Not Required</label> -->
														</div>
													</div>

												</div>



												<div class="line" id="projDirNameDiv" style="display: none">
													<c:if
														test="${tada.empDetailsList.userSfid ne 'SF0002' && tada.empDetailsList.userSfid ne 'SF0008' && tada.empDetailsList.userSfid ne 'SF0251'}">
														<div class="quarter leftmar">
															Project Director<span class="mandatory">*</span>
														</div>
														<div class="quarter">
															:&nbsp;
															<form:select path="projDirName" id="projDirName"
																multiple="" cssStyle="width:145px;">
																<form:option value="Select" label="Select"></form:option>
																<form:options
																	items="${sessionScope.tadaProjDirNamesList}"
																	itemLabel="projectDirName" itemValue="sfID"></form:options>
															</form:select>
														</div>
													</c:if>
												</div>
												<div class="line" id="entitleExemptionDiv"
													style="display: none">
													<div class="quarter leftmar">
														Entitlement Exemption<span class="mandatory">*</span>
													</div>
													<div class="quarter">
														:&nbsp;
														<form:radiobutton path="entitlementExemption"
															id="entitlementExemption1" value="1"
															onclick="javascript:setCModes(0,true);" />
														<label>Yes</label>
														<form:radiobutton path="entitlementExemption"
															id="entitlementExemption2" value="2"
															onclick="javascript:setCModes(0,true);" />
														<label>No</label>
													</div>
												</div>
											</div>
											<div class="line" id="journeyDetailsDiv"
												style="display: none">
												<fieldset>
													<legend>
														<strong><font color='green'>Journey
																Details</font></strong>
													</legend>
													<div class="line">
														<table style="width: 100%" border="1" class="list"
															id="requestJourneyDetailsId">

															<tr>
																<th style="width: 10%; text-align: center">Departure
																	Date<span class="mandatory">*</span>
																</th>
																<th style="width: 10%; text-align: center">Arrival
																	Date</th>
																<th style="width: 20%; text-align: center">From
																	Place<span class="mandatory">*</span>
																</th>
																<th style="width: 20%; text-align: center">To Place<span
																	class="mandatory">*</span></th>
																<th style="width: 6%; text-align: center">No. Of
																	Days of Stay<span class="mandatory">*</span>
																</th>
																<th style="width: 7%; text-align: center">Mode of
																	conveyance<span class="mandatory">*</span>
																</th>
																<th style="width: 10%; text-align: center">Class of
																	Entitlement<span class="mandatory">*</span>
																</th>
																<th style="width: 7%; text-align: center;display: none">Tatkal
																	Quota<span class="mandatory">*</span>
																</th>
																<th style="width: 5%; text-align: center">Add</th>
																<th style="width: 5%; text-align: center">Del</th>
															</tr>

															<tr id="requestJourneyRow0">
																<td><input type="text" readonly="readonly"
																	id="journeyDate0"
																	style="height: 16px; width: 70px; font-size: 9px; font-weight: bold;"
																	id="journeyDate0"
																	onfocus="javascript:Calendar.setup({inputField :'journeyDate0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});"
																	onchange="javascript:enableLeave();changeArrivalDate(0);checkDate(this);" />
																</td>
																<td><input type="text" readonly="readonly"
																	id="journeyArrDate0"
																	style="height: 16px; width: 70px; font-size: 9px; font-weight: bold;"
																	id="journeyArrDate0"
																	onfocus="javascript:Calendar.setup({inputField :'journeyArrDate0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});"
																	onchange="javascript:checkReqArrivalDate();enableLeave();setReqDeptDate(1);" />
																</td>
																<td><form:select path="fromPlace" id="fromPlace0"
																		multiple="" cssStyle="width:90px;"
																		onchange="javascript:setToPlace(0);showOtherFromPlace(0);setSelectBoxWidth('fromPlace0');setNextFromPlace(0);">
																		<form:option value="Select" label="Select"></form:option>
																		<form:options items="${sessionScope.cityTypeList}"
																			itemLabel="cityName" itemValue="cityName"></form:options>
																		<form:option value="Other" label="Other"></form:option>
																	</form:select> <input type=text id=otherFromPlace0 size=11px
																	style="display: none" /></td>
																<td><form:select path="toPlace" id="toPlace0"
																		multiple="" cssStyle="width:90px;"
																		onchange="javascript:showOtherToPlace(0);setSelectBoxWidth('toPlace0');setNextFromPlace(0);">
																		<form:option value="Select" label="Select"></form:option>
																	</form:select> <input type=text id=otherToPlace0 size=11px
																	style="display: none"
																	onchange="javascript:setNextFromPlace(0);" /></td>
																<td><input type="text" id="noOfDays0" value=0
																	style="width: 40px" maxlength="4"
																	onkeypress="javascript:return checkInt(event);"
																	onchange="javascript:setReqDeptDate(1);"
																	onblur="javascript:setTotalStay();" /></td>
																<td><form:select path="toPlace" id="modeOfTravel0"
																		multiple="" cssStyle="width:80px;"
																		onchange="javascript:enableEntitleClasses(0);setTatkalQuota();">
																		<form:option value="Select" label="Select"></form:option>
																	</form:select></td>
																<td><form:select path="toPlace"
																		id="classOfEntitlement0" multiple=""
																		cssStyle="width:100px;">
																		<form:option value="Select" label="Select"></form:option>
																	</form:select></td>
																<td style="display: none"><form:select path="toPlace" id="tatkalQuota0"
																		multiple="" cssStyle="width:80px;">
																		<form:option value="na" label="N/A"></form:option>
																		<form:option value="yes" label="Required"></form:option>
																	</form:select></td>
																<td><input type="button" id="add0" value="+"
																	onclick="javascript:checkRequestJourneyRow(0);" /></td>
																<td><input type="button" id="del0" value="-"
																	style="display: none" /></td>

															</tr>

														</table>
													</div>
													<span class="mandatory">Note:-Make sure that you are
														entering complete round trip journey details including
														every stay and halt.</span>
												</fieldset>
												<div class="line">
													<div class="quarter leftmar" style="text-align: justify">
														Whether leave is required or not<span class="mandatory">*</span>
													</div>
													<div class="quarter">
														:&nbsp;
														<form:radiobutton path="leaveRequirement"
															id="leaveRequirement1" value="1"
															onclick="javascript:enableLeave();" />
														<label>Required</label>
														<form:radiobutton path="leaveRequirement"
															id="leaveRequirement2" value="2"
															onclick="javascript:enableLeave();" />
														<label>Not Required</label>
													</div>
												</div>
												<div class="line" id="leaveDivID">
													<div class="line" id="leaveDiv" style="display: none">
														<div class="line">
															<div class="leftmar">
																<div style="float: left; width: 45%;">Non-Selected
																	Leaves</div>
																<div class="half">Selected Leaves</div>
															</div>
														</div>

														<div class="line">
															<div class="leftmar">
																<div style="float: left; width: 35%">
																	<form:select path="leaveRequirement" id="SelectLeft"
																		size="5" multiple="true" cssStyle="width:300px">

																	</form:select>
																</div>
																<div style="float: left; width: 10%; margin-top: 15px;">
																	<div style="margin-bottom: 5px;" align="center">
																		<input style="margin-bottom: 5px" id="MoveRight"
																			type="button" value=" Add "
																			onclick="javascript:addSelect();" /> <input
																			id="MoveLeft" type="button" value=" Remove "
																			onblur="javascript:removeSelect();" />
																	</div>
																</div>
																<div style="float: left; width: 30%">
																	<form:select path="leaveRequirement" id="SelectRight"
																		size="5" multiple="true" cssStyle="width:300px">
																	</form:select>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
											<div class="line" id="ticketCancellationDiv"
												style="display: none">
												<div class="quarter leftmar">Sum Of Ticket(s)
													Cancellation Charges(if any)</div>
												<div class="quarter">
													:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font>
													<input name="ticketCancelCharges" id="ticketCancelCharges"
														onkeypress="return checkFloat(event,'ticketCancelCharges');"
														onkeyup="javascript:showTicketCancelReason();" />
												</div>
												<div class="quarter leftmar" id="ticketCancelReasonDiv"
													style="display: none">
													Reason for ticket(s) cancellation<span class="mandatory">*</span>
												</div>
												<div class="quarter" id="ticketCancelReasonDiv1"
													style="display: none">
													:&nbsp;
													<form:textarea path="ticketCancelReason"
														id="ticketCancelReason" rows="4" cols="15"
														onkeypress="textCounter(this,document.forms[0].counter3,500);"
														onkeyup="textCounter(this,document.forms[0].counter3,500);" />
													<input type="text" class="counter" name="counter"
														value="500" id="counter3" disabled="" />
												</div>
											</div>
											<div class="line" id="remarksDiv" style="display: none">
												<div class="quarter leftmar" id="reasonText"></div>
												<div class="quarter">
													:&nbsp;
													<form:textarea path="remarks" id="remarks" rows="4"
														cols="15"
														onkeypress="textCounter(this,document.forms[0].counter4,500);"
														onkeyup="textCounter(this,document.forms[0].counter4,500);" />
													<input type="text" class="counter" name="counter"
														value="500" id="counter4" disabled="" />
												</div>

											</div>
											<div class="line"></div>




											<div class="line" style="margin-left: 35%;">
												<div class="expbutton">
													<a onclick="javascript:submitTdApproval()"> <span>Submit</span></a>
												</div>
												<div class="expbutton">
													<a onclick="javascript:clearTdApproval()"> <span>Clear</span></a>
												</div>
											</div>
											<div class="line" id="individualDetails">
												<div style="colore: red">
													<font color='red'>Note for Individual</font>
												</div>
												<div>
													<font color='purple'> 1)Go to My
														Admin---->TADA---->TADA TD Claims/Amendment link for <b>:</b>
														TD Advance,Settlement,Reimbursement,Amendment,Cancel.<br />
														2)Note that you can not add additional journey details
														during advance requisition.So clearly mention complete
														journey details.
													</font>
												</div>
											</div>

										</div>
									</c:when>
									<c:otherwise>
										<div id="detailsNotAvailble" style="size: 15%;" align="left">
											<font size="3%" color="red" style="text-align: left;"><br />
												<br />IMPORTANT NOTE:<br /> <b><u>Please Contact
														your Admin Task Holder.</u></b><br />AS your Basic Pay And Grade
												Pay Details are not Entered.<br /> Unless Basic And Grade
												Pay Details are entered <b><u>Tada Request cannot be
														raised </u></b>. </font>
										</div>
									</c:otherwise>
								</c:choose>
								<!--This  new condition for div  -->
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
		<form:hidden path="param" />
		<form:hidden path="type" />
		<form:hidden path="requestID" />
		<form:hidden path="requestId" />
	</form:form>
	<script type="text/javascript">
		3
		travelTypeMapDetailsJSON =
	<%=(net.sf.json.JSONArray) session
					.getAttribute("travelTypeMapDetailsJSON")%>
		;
		entitleClassListJSON =
	<%=(net.sf.json.JSONArray) session
					.getAttribute("entitleClassListJSON")%>
		;
		taEntitleClassListJSON =
	<%=(net.sf.json.JSONArray) session
					.getAttribute("taEntitleClassListJSON")%>
		;
		travelTypeListJSON =
	<%=(net.sf.json.JSONArray) session
					.getAttribute("travelTypeListJSON")%>
		;
		empDetailsJSON =
	<%=(net.sf.json.JSONObject) session
					.getAttribute("empDetailsJSON")%>
		;
		cityList =
	<%=(net.sf.json.JSONArray) session.getAttribute("cityList")%>
		;
		entitleExemptionList =
	<%=(net.sf.json.JSONArray) session
					.getAttribute("entitleExemptionList")%>
		;
		clearTdApproval();
		setDeptPlace();
	</script>

	<c:if test="${not empty requestScope.TadaRequestBean}">
		<script>
			tadaRequestBean =
		<%=(net.sf.json.JSONObject) request
						.getAttribute("TadaRequestBean")%>
			;
			setAmendmentValues();
			setTdReqJourneyList();
			setLeaveCheck();
		</script>
	</c:if>

</body>
</html>
<!-- End:TadaApprovalRequest.jsp -->
