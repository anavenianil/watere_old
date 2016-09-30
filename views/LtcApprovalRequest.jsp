<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LtcApprovalRequest.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>

<link href="styles/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/ltc.js"></script>
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />

<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>



<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>

<script type="text/javascript">
	$jq(document).ready(function(){
		lables('<c:out value='${ltcApprovalRequest.type}'/>');
	});
	
</script>
<title>Ltc Approval Request</title>
</head>

<body onload="timePicker('encashmentDays')">
	<form:form method="post" commandName="ltcApprovalRequest" name="ltcApprovalRequest" id="ltcApprovalRequest">
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
							<div class="lightblueBg1 BGWORLD">
								<div class="headTitle" id="headTitle"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</img></div>
								<%-- Content Page starts --%>
								<div>
									<div id="result">									
									</div>
									<div id ="ltcerrorb"></div>
									<div id ="ltcerror">
									<div class="line">
										<%-- <div class="headTitle" >Details of Family Members For LTC</div>--%>
										
										<div style="float:left;"  ><b>Details of Family Members For LTC</b></div>     
										<div id="result" class="line">
											<jsp:include page="LtcFamilyDataList.jsp" />
										</div>
									</div>
									<div class="line">
											<div class="line" id="ltcCancelDetails" style="display: none;">
												<div class="quarter">Amendment Type<span class='failure'>*</span></div>
												<div class="half" id="ltcCancelDiv">
												<input id="Amendment" name="AmendmentLtc" type="radio" checked="checked" onclick="cancelRadioCheck()">Amendment LTC</input> &nbsp;
												<input id="Cancel" name="AmendmentLtc" type="radio" onclick="cancelRadioCheck()">Cancel LTC</input> <br/>
												</div>
											</div>
											<div class="line" id="ltcDetails">
												<div class="quarter">Type of LTC<span class='failure'>*</span></div>
												<div class="quarter" id="ltcTypeLabelDiv">
													<%-- <form:select path="ltcTypeId" id="ltcTypeId" cssStyle="width:145px;" onchange="javascript:enableTypeOfLtc()"> --%>
													<form:select path="ltcTypeId" id="ltcTypeId" cssStyle="width:145px;" onchange="javascript:enableTypeOfLtc()">
														<form:option value="Select" label="Select"></form:option>
														<form:options items="${ltcApprovalRequest.ltcTypeDetails}" itemLabel="name" itemValue="id"></form:options>
													</form:select>
												</div>
												<!-- <div class="quarter">Block Year<span class='failure'>*</span></div> -->
												<div class="quarter">Business Year<span class='failure'>*</span></div>
												<div class="quarter" id="blockYearLabelDiv">
													<form:select path="ltcBlockYearId" id="ltcBlockYearId" cssStyle="width:145px;" onchange="javascript:checkApplingBlockYear();">
														<form:option value="Select" label="Select"></form:option>
														<form:options items="${ltcApprovalRequest.ltcBlockYearList}" itemLabel="value" itemValue="key"></form:options>
													</form:select>
												</div>
												
											</div>
											
											<div id="typeOfLtc" style="display:none">
												<div class="line">
													<div class="quarter">Place of Visit<span class='failure'>*</span></div>
													<div class="quarter">
														<form:input path="placeOfVisit" id="placeOfVisit" maxlength="50" onkeypress="return checkChar(event);"/>
													</div>
													<div class="quarter">Nearest Railway Station/Airport<span class='failure'>*</span></div>
													<div class="quarter">
														<form:input path="nearestRlyStation" id="nearestRlyStation" maxlength="50" onkeypress="return checkChar(event);"/>
													</div>
												</div>
												
											</div>
											<div id="typeOfLtcLables" style="display:none">
												<div class="line">
													<div class="quarter">Place of Visit</div>
												<!-- 	<div class="quarter" id="placeOfVisitDiv"> -->
														<div id="visitPlace">&nbsp;
														<form:input type ="text"  path="placeOfVisit" id="placeOfVisit"  value ="${ltcApprovalRequest.homeTownAddress.city}"/> 
															</div>
													<!-- </div> -->
													<div class="quarter">Nearest Railway Station/Airport</div>
													<div class="quarter" id="nearestRailStationDiv">
														<div id="nrlyStation">&nbsp;
															<c:choose>
																<c:when test='${ltcApprovalRequest.homeTownAddress.nearestRyStation ne null and
																ltcApprovalRequest.homeTownAddress.nearestRyStation ne ""}'>
																	${ltcApprovalRequest.homeTownAddress.nearestRyStation}
																</c:when>
																<c:otherwise><c:out value='${ltcApprovalRequest.homeTownAddress.nearestAirport}'></c:out></c:otherwise>
															</c:choose>
														</div>
													</div>
												</div>
												</div>
												<div class="line">
												<!-- commented by bkr 11/04/2016  -->
													<%-- <div id="homeTownAddress">
														<div class="quarter">Home Town Address</div>
														<div class="quarter" >
															<div id="homeAddress" style="border: 1px solid #ddd; margin-right: 20px ; padding: 4px">&nbsp;<b>${ltcApprovalRequest.homeTownAddress.address1}&nbsp;${ltcApprovalRequest.homeTownAddress.address2}&nbsp;<br/>${ltcApprovalRequest.homeTownAddress.address3}&nbsp;
															${ltcApprovalRequest.homeTownAddress.city}&nbsp;<br/>${ltcApprovalRequest.homeTownAddress.districtDetails.name}&nbsp;${ltcApprovalRequest.homeTownAddress.stateDetails.name}-${ltcApprovalRequest.homeTownAddress.pincode}</b>
															</div>
														</div>
													</div> --%>
													
													
													<!--added by bkr 11/04/2016 just remove id value remaing same as above commented code  -->
													<div >
														<div class="quarter">Home Town Address</div>
														<div class="quarter" >
															<div id="homeAddress" style="border: 1px solid #ddd; margin-right: 20px ; padding: 4px">&nbsp;<b>${ltcApprovalRequest.homeTownAddress.address1}&nbsp;${ltcApprovalRequest.homeTownAddress.address2}&nbsp;<br/>${ltcApprovalRequest.homeTownAddress.address3}&nbsp;
															${ltcApprovalRequest.homeTownAddress.city}&nbsp;<br/>${ltcApprovalRequest.homeTownAddress.districtDetails.name}&nbsp;${ltcApprovalRequest.homeTownAddress.stateDetails.name}-${ltcApprovalRequest.homeTownAddress.pincode}</b>
															</div>
														</div>
													</div>
													
													
													<div id="dateOfDepartureDiv">
													<!--commented by bkr 12/04/2016 add below  -->
													<!-- <div class="quarter">Date of Departure<span class='failure'>*</span></div> -->
													<div class="quarter">Start Date for Holiday<span class='failure'>*</span></div>
													<div class="quarter" id="departureDateDiv">
															<form:input path="departureDate" id="departureDate" readonly="true" onchange="javascript:checkApplingYear('${ltcApprovalRequest.typeValue}');return validdate('departureDate')" /> 
																
																<script type="text/javascript">
											                    	new tcal({'formname':'ltcApprovalRequest','controlname':'departureDate'});
											                   </script>
													</div>
													</div>
												</div>
												 
											<div class="line">
												<div id="dateOfReturnDiv">
												<!--commented by bkr 12/04/2016 add below  -->
													<!-- <div class="quarter">Date of Arrival<span class='failure'>*</span></div> -->
													<div class="quarter">Return Date<span class='failure'>*</span></div>
													<div class="quarter" id="returnDateDiv">
														<form:input path="returnDate" id="returnDate" readonly="true"   onchange="javascript:checkApplingYear('${ltcApprovalRequest.typeValue}');return validdate('returnDate')"/> 
															<script type="text/javascript">
											                    new tcal({'formname':'ltcApprovalRequest','controlname':'returnDate'});
											                   </script>
													</div>
												</div>
												
												<!--added by bkr 11/04/2016 only <div style="display:none"> -->
												<div style="display:none">
												
												<div id="approvedLeave" style="display:none">
													<div class="quarter">Attach Leave<span class='failure'>*</span></div>
													
													<div class="quarter" id="approvedLeaveList">
													<!-- commented by bkr 06/04/2016 -->
														<%-- <form:select path="leaveRequestId" id="leaveRequestId" cssStyle="width:145px;" onclick="fieldChecking();" onchange="checkLeaveStatus();">
															<form:option value="" label="Select"></form:option>
															<form:options items="${ltcApprovalRequest.ltcLeaveTypeList}" itemValue="key" itemLabel="name"></form:options>
														</form:select> --%>
														<!--added by bkr 06/04/2016  -->
														 <form:select path="leaveRequestId" id="leaveRequestId" cssStyle="width:145px;">
															<form:option value="999" label="Select"></form:option>
															<form:options items="${ltcApprovalRequest.ltcLeaveTypeList}" itemValue="key" itemLabel="name"></form:options>
														</form:select>
													</div>
												</div>
												</div>
												
												
												
											</div>
											<fieldset id="changeLeaveFieldset" style="display:none"><legend><strong><font color='red'>Leave Cancel/Change</font></strong></legend>
											<div class="line" id="changeLeaveDiv" style="display:none">
											<div class="quarter">What do u want to do with Leave<span class='failure'>*</span></div>
											<div class="quarter"><form:radiobutton path="changeLeaveFlag" id="change" value="change" label="Change" onclick="ltcLeaveAmendment();"/><br/>
											<form:radiobutton path="changeLeaveFlag" id="cancel" value="cancel" label="Cancel" onclick="ltcLeaveAmendment();"/><br/>
											<form:radiobutton path="changeLeaveFlag" id="none" value="none" label="No change" checked="checked" onchange="ltcLeaveAmendment();"/></div>
											</div>
											<div class="line" id="cancelLeaveDiv" style="display:none">
											<div class="quarter">Do you want to cancel the leave</div>
											<div class="quarter"><form:radiobutton path="cancelLeaveFlag" id="cancelLeaveFlagY" value="Yes" label="Yes"/>
											<form:radiobutton path="cancelLeaveFlag" id="cancelLeaveFlagN" value="No" checked="checked" label="No"/></div>
											<div class="quarterbutton expbutton"><a href="javascript:ltcLeaveCancleAmendment('${ltcApprovalRequest.typeValue}','${ltcApprovalRequest.leaveRequestHistoryBean.historyID}','${ltcApprovalRequest.leaveRequestHistoryBean.requestID}','${ltcApprovalRequest.leaveRequestHistoryBean.stageID}','${ltcApprovalRequest.typeValue}');"><span>Cancel Leave</span></a></div>
											</div>
											</fieldset>
											<div class="line" id="leaveType" style="display:none">
												<div class="quarter" style="font-size:18px;bold;">Leave Encashment Type</div>
												<div class="quarter">
													<form:select path="encashTypeId" id="encashTypeId" cssStyle="width:145px;"><%--onchange="javascript:advanceEncashOfEL();" --%>
					
														<form:options items="${ltcApprovalRequest.leaveEncasList}" itemValue="id" itemLabel="leaveTypeDetails.leaveType"></form:options>
													</form:select>
												</div>
												<div id="numOfDays" >
												<!--commented by bkr 11/04/2016  -->
													<!-- <div class="quarter" style="font-size:18px;bold;">No of Days (Max 10)</div> -->
													<!--added by bkr 11/04/2016  -->
													<div class="quarter" style="font-size:18px;bold;">No of Vacation Days (Max 28) </div>
													<div class="quarter">
														<form:input path="encashmentDays" id="encashmentDays" maxlength="3" onkeypress="javascript:return checkInt(event);" onclick="javascript:timePicker('encashmentDays')"/>
													</div>
												</div>
											</div>
											
											<!--added by bkr only div   -->
										<div >
										<div class="line">
											<div id="numberOfTicketsDiv">
												<div class="quarter">No.of full tickets(One way)<span class='failure'>*</span></div>
												<div class="quarter"><form:input path="noOfTickets" id="noOfTickets" readonly="true" onkeypress="javascript:return checkInt(event);" onkeyup="javascript:calculateAdvance();"  /></div>
											</div>
											<div id="amountPerPersonDiv" >
												<div class="quarter">Amount per each full ticket(One way)<span class='failure'>*</span></div>
												<div class="quarter"><font size="4.5em"><span class="WebRupee" >R</span></font><form:input path="amountPerPerson" id="amountPerPerson" onkeypress="javascript:return checkIntDecimal(event);" onkeyup="javascript:calculateAdvance();" onblur="javascript:twoDecimalCalculationAmount();" /></div>
											</div>
										</div>
										</div>
										<!--added by bkr only div   -->
										<div >
										<div class="line">
											<div id="noOfInfantTicketsDiv" >
												<div class="quarter">No.of infant/child tickets(One way)</div>
												<div class="quarter"><form:input path="noOfInfantTickets" id="noOfInfantTickets" readonly="true" onkeypress="javascript:return checkInt(event);" onkeyup="javascript:calculateAdvance();"/></div>
											</div>
											<div id="amountPerEachInfantDiv" >
												<div class="quarter">Amount per each infant/child ticket(One way)</div>
												<div class="quarter"><font size="4.5em"><span class="WebRupee" >R</span></font><form:input path="amountPerEachInfant" id="amountPerEachInfant" onkeypress="javascript:return checkIntDecimal(event);" onkeyup="javascript:calculateAdvance();"  onblur="javascript:twoDecimalCalculationAmount();"/></div>
											</div>
										</div>
										</div>
										
										<div class="line">
										<!--added by bkr only div   -->
										<div style="display: none">
											<div id="desiredAdvanceAmountDiv" style="display: none">
												<div class="quarter" id="desiredAdvanceLable">Desired advance amount<span class='mandatory'>*</span></div>
												<div class="quarter" id="amountClaimedLable"><font size="4.5em"><span class="WebRupee" >R</span></font><form:input path="amountClaimed" id="amountClaimed" readonly="true" maxlength="10" onkeypress="javascript:return checkInt(event);"/></div>
											</div>
										</div>	
											
											<div class="quarter" >Date Of Joining in GOVT</div><div class="quarter" ><b>${ltcApprovalRequest.empBean.dojDrdo}</b></div>   
											
										</div>
										<!--Added by bkr 11/04/2016  -->
										<!-- <div  class="line">
											<div class="quarter" >Number Of Vacation Days </div>
											<div><input type="text" size="2" name="noOfvacationdays" id="noOfvacationdays" /> </div>
										
										</div>
										 -->
										
									</div>
									<div class="line" id="ltcAmendmentSubmit">
										<div style="margin-left:75%;" id="ltcAmendmentSubmit"><a class="quarterbutton appbutton" href="javascript:submitLtcApprRequest('${ltcApprovalRequest.type}','${ltcApprovalRequest.id}','${ltcApprovalRequest.typeValue}','${ltcApprovalRequest.leaveRequestBean.requestID}','${ltcApprovalRequest.cancelLeaveRequestBean.requestID}')">Submit</a></div>
										<div class="appbutton"><a class="quarterbutton appbutton" href="javascript:clearLtcApprRequest('${ltcApprovalRequest.type}');">Clear</a></div>
									</div>
									
									<%-- <div>
									<div style="colore:red"><span><font color='red'>Note for </font></span></div>
									</div>
									<div class="line">
										<div class="headTitle">Details of Family Members For LTC</div>
										<div id="result" class="line">
											<jsp:include page="LtcFamilyDataList.jsp" />
										</div>
									</div>--%>
									</div>
									<div class="line" id="individualDetails" style="display: none">
										<div style="colore:red"><font color='red'>Note for Individual</font></div>
										<div><font color='purple'>1)This is only for LTC Approval Request. <br/>2)For LTC Approval Cum Advance <a href="javascript:ltcApprovalRequest('ltcAdvance');">Click here</a>.</font></div> 
									</div>
									
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
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type"/>
		<form:hidden path="id" id="id"/>
		<form:hidden path="requestID" id="requestID"/>
		<form:hidden path="requestId"/>
		<form:hidden path="ltcRequestType" id="ltcRequestType"/>
		<form:hidden path="jsonValue" id="jsonValue"/>
	</form:form>
	<c:if test="${not empty requestScope.LtcApplicationBean}">
		<script>
			ltcApplicationBean = <%= (net.sf.json.JSONObject)request.getAttribute("LtcApplicationBean") %>;
			ltcLeaveTypeList = <%= (net.sf.json.JSONArray)session.getAttribute("ltcLeaveTypeList") %> 
			setAmendmentValues();
		</script>
	</c:if>
	<c:if test="${empty ltcApprovalRequest.typeValue}">
		<script>
			clearLtcApprRequest('${ltcApprovalRequest.type}');
			ltcAdvanceDto = <%= (net.sf.json.JSONObject)request.getAttribute("ltcAdvanceDto") %>;
		</script>
	</c:if>
	<script>
		addId='${ltcApprovalRequest.homeTownAddress.id}';
		place='${ltcApprovalRequest.homeTownAddress.city}';
		rlyStation='${ltcApprovalRequest.homeTownAddress.nearestRyStation}';
		checkDate = '${ltcApprovalRequest.empBean.checkDate}';
		district='${ltcApprovalRequest.homeTownAddress.district}';
		ltcAdvanceDays='${ltcApprovalRequest.empBean.ltcAdvanceDays}';
		checkLeaveStatus();
		function cancelRadioCheck(){
		if($jq('#Cancel').is(':checked')){
		clearLtcApprRequest('ltcApproval');
		cancelLtcRequest('${ltcApprovalRequest.cancelReqRequestType}','${ltcApprovalRequest.cancelReqRequestId}','${ltcApprovalRequest.cancelReqDoPartId}','${ltcApprovalRequest.cancelReqHistoryId}','${ltcApprovalRequest.cancelReqIssuedAmount}','${ltcApprovalRequest.cancelReqCdaAmount}');
		}
		}
	</script>	
	<c:if test="${(ltcApprovalRequest.typeValue=='ltcAdvanceAmendment' || ltcApprovalRequest.typeValue=='ltcApprovalAmendment') }">
	<script>
	$jq('#ltcCancelDetails').show();
	</script>
	<c:if test="${not empty ltcApprovalRequest.cancelLeaveRequestBean}">
	<script>
	alert("Leave application attached to this LTC is in cancellation state please change the leave");
	amendmentLeaves=$jq('#approvedLeaveList').html();
	amendmentLeaveSelected=$jq('#leaveRequestId').val();
	$jq('#changeLeaveFieldset').show();
	$jq('#changeLeaveDiv').show();
	$jq('#cancel').hide();
	</script>	
	</c:if>	
	</c:if>
	<script>
paymentDetails = '<%= session.getAttribute("PaymentDetailsNotFound") != null ? session.getAttribute("PaymentDetailsNotFound") : "" %>';
</script>
	</body>
</html>
<!-- End:LtcApprovalRequest.jsp -->
