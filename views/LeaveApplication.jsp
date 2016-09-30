<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LeaveApplication.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/ajaxUpload.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<script type="text/javascript" src="script/script.js"></script>

<title>Leave Application Form</title>
</head>

<body>
	<form:form method="post" commandName="leaveRequest" enctype="multipart/form-data" name="leave">
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
								<div class="headTitle">Leave Application Form</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line" id="result">${resultMsg}</div>
									<c:if test="${message == 'offline'}">
										<div class="line">
											<div class="quarter leftmar">SFID<span class="failure">&nbsp;*</span></div>
											<div class="quarter">
											<form:input path="offlineSFID" id="offlineSFID" maxlength="10" onchange="javascript:getOfflineSfidLeaves();" onkeypress="javascript:return isAlphaNumaricExp(event);"/>
												<%--<form:input path="offlineSFID" id="offlineSFID" onblur="javascript:offlineLeave()"/> onchange="javascript:getOfflineSfidLeaves();"--%>											
											</div>
										</div>									
									</c:if>
									<div class="line">
										<div class="quarter leftmar">Nature of leave<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:select path="leaveType" id="leaveType" cssClass="formSelect" onchange="javascript:selectedLeaveType('');" onmouseover="setSelectWidth('#leaveType')">
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.availableLeavesList}" itemValue="leaveTypeDetails.id" itemLabel="leaveTypeDetails.leaveType"/>
											</form:select>
										</div>
									</div>
									<div class="line" id="subType" style="display:none">
										<div class="quarter leftmar">Leave Sub-Type<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:select path="leaveSubType" id="leaveSubType" cssClass="formSelect" onchange="javascript:selectedSubLeaveType();">
												<form:option value="select">Select</form:option>
											</form:select>
										</div>
									</div>
									<div class="line" id="studyImpact" style="display:none">
										<div class="quarter leftmar">Select Degree<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:select path="studyDegree" id="studyDegree" cssClass="formSelect">
												<form:option value="select">Select</form:option>
												<form:option value="D">Ph.D</form:option>
												<form:option value="P">PG</form:option>
											</form:select>
										</div>
									</div>
									<div class="line" id="dateImpact" style="display:none">
										<div class="quarter leftmar">Delivery/Adoption Date<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:input path="deliveryDate" id="deliveryDate" cssClass="dateClass" readonly="true"/>
												
											<script type="text/javascript">
											new tcal({'formname':'leave','controlname':'deliveryDate'});
												
											</script>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">From Date<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:input path="fromDate" id="fromDate" cssClass="dateClass" readonly="true" onchange="javescript:checkHolidayDate('fromDate','prefix');"/>
												<script type="text/javascript">
													new tcal({'formname':'leave','controlname':'fromDate'});
												</script>
									
										</div>
										<div class="half">
											<div style="width: 65px; float: left;font-weight: bold;">Prefixed :</div>
											<div id="prefix" class="bold"></div>
										</div>					
									</div>
									<div class="line">
										<div class="quarter leftmar">To Date<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:input path="toDate" id="toDate" cssClass="dateClass" readonly="true" onchange="javescript:checkHolidayDate('toDate','suffix');"/>
												<script type="text/javascript">
													new tcal({'formname':'leave','controlname':'toDate'});	
												</script>
										</div>
										<div class="half">
											<div style="width: 65px; float: left;font-weight: bold;">Suffixed :</div>
											<div id="suffix" class="bold"></div>
										</div>
									</div>	
									<div class="line" id="halfDayCheck" style="display:none;">
										<div class="quarter" style="padding-left: 24.4%;"><form:checkbox path="fromHalfDayFlag" id="fromHalfDayFlag" value="0.5" label="Halfday on From Date" onclick="setHalfDay('fromHalfDayFlag')"/></div>
										<div class="quarter"><form:checkbox path="toHalfDayFlag" id="toHalfDayFlag" value="0.5" label="Halfday on To Date" onclick="setHalfDay('toHalfDayFlag')"/></div>
									</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">No of days</div>
										<div class="quarter">
											<div id="days" style="width: 30px;float:left">0</div>
											<div>days</div>
										</div>
									</div>
									<div class="line" id="familyImpact" style="display:none">
										<div class="quarter leftmar">Child Name<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:select path="childName" id="childName" cssClass="formSelect">
												<form:option value="select">Select</form:option>												
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Reason<span class="failure">&nbsp;*</span></div>
										<div class="half">
											<form:textarea path="reason" cols="25" rows="4" id="reason" style="resize: none;" onkeypress="textCounter(this, document.forms[0].counter, 100);" onkeyup="textCounter(this, document.forms[0].counter, 100);"/>
											<input type="text" class="counter" name="counter" value="100" id="counter" disabled="disabled" />
										</div>										
									</div>
									<div class="line">
										<div class="quarter leftmar">Address<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:select path="selectAddress" id="selectAddress" cssClass="formSelect" onchange="javascript:getLeaveAddress();">
												<form:option value="select">Select</form:option>
												<form:option value="2">Present</form:option>
												<!--<form:option value="1">Permanent</form:option>  -->
												<form:option value="3">Hometown</form:option>
												<form:option value="4">Other</form:option>
											</form:select>
										</div>
										<div style="width: 38%; float: left;display:none;" id="addressDiv">
											<form:textarea path="address" cols="30" rows="4"/>
										</div>
										<div style="width: 38%; float: left;" id="addressTextDiv">											
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Contact Number<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:input path="contactNumber" id="contactNumber" maxlength="12" onkeypress="return checkInt(event);"/>
										</div>
									</div>
									<div id="leaveFiles">
									<div class="line" id="medicalFlagDiv" style="display:none">
										<div class="line">
											<div class="quarter leftmar">Medical Certificate Attachment<span class="failure" style="display:none" id="mmc">&nbsp;*</span></div>
											<div class="quarter">
												<form:input path="mcFile" type="file" id="mcFile"/>											
											</div>
										</div>																	
									</div>
									
									<div class="line" id="fitnessFlagDiv" style="display:none">
										<div class="line">
											<div class="quarter leftmar">Fitness Certificate Attachment<span class="failure" style="display:none" id="mfc">&nbsp;*</span></div>
											<div class="quarter">
												<form:input path="fitnessFile" type="file" id="fitnessFile"/>											
											</div>
										</div>
										
									</div>
									<div class="line" id="otherCertDiv" style="display:none">
										<div class="line">
											<div class="quarter leftmar" id="otherCertificate"><span class="failure" style="display:none" id="moc">&nbsp;*</span></div>
											<div class="quarter">
												<form:input path="otherCertFile" type="file" id="otherCertFile"/>											
											</div>
										</div>
										
									</div>
									</div>
									<div class="line" id="exceptions" style="display:none">
										<fieldset><legend><strong style="failure">Exceptions</strong></legend>
											<div id="priorApprExceptions" class="line" style="display:none"></div>
											<div id="minDaysExceptions" class="line" style="display:none"></div>
											<div id="maxDaysExceptions" class="line" style="display:none"></div>
											<div id="mcExceptions" class="line" style="display:none"></div>	
											<div id="fitnessExceptions" class="line" style="display:none"></div>	
											<div id="otherAvailExceptions" class="line" style="display:none"></div>
											<div id="otherCertExceptions" class="line" style="display:none"></div>	
										</fieldset>
									</div>
									
									<div class="line" id="applyLeaveBtn">
										<div><a href="javascript:resetLeave('');" class="appbutton" style="text-decoration: none;float:right">Clear</a></div>
										<div><a href="javascript:submitLeave('');"  class="appbutton" style="text-decoration: none;float:right">Submit</a></div>
									</div>
									
										<div class="line"><hr /></div>
										<div class="line" id="leaves">
											<jsp:include page="LeaveSearchList.jsp"></jsp:include>
										</div>
								
								</div>
								<%-- Content Page end --%>								
								<div>&nbsp;</div>
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
		<form:hidden path="type"/>
		<form:hidden path="param"/>	
		<form:hidden path="currentDate" id="currentDate"/>	
		<form:hidden path="requestID"/>	
		<form:hidden path="requestId"/>
		<form:hidden path="roleId"/>
		</form:form>
		<script>
			availableLeavesDetailsJson = <%= (net.sf.json.JSONArray)session.getAttribute("availableLeavesDetailsJson") %>;
			familyMembersListJson = <%= (net.sf.json.JSONArray)session.getAttribute("familyMembersListJson") %>;
			familyImpactListJson = <%= (net.sf.json.JSONArray)session.getAttribute("familyImpactListJson") %>;
			exceptionalDetailsListJson = <%= (net.sf.json.JSONArray)session.getAttribute("exceptionalDetailsListJson") %>;
			leaveCreditsListJson = <%= (net.sf.json.JSONArray)session.getAttribute("leaveCreditsListJson") %>;
			specialLeavesListJson = <%= (net.sf.json.JSONArray)session.getAttribute("specialLeavesListJson") %>;
			resetLeave('sfid');
		</script>
		
	</body>
</html>
<!-- End:LeaveApplication.jsp -->
