<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LtcApplicationForm.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/ltc.js"></script>
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>

<title>Application For Grant Of LTC </title>
</head>

<body>
	<form:form method="post" commandName="ltcApplication" name="ltcApplication" id="ltcApplication">
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
								<div class="headTitle" id="headTitle">Application For Grant Of LTC</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</img></div>
								<%-- Content Page starts --%>
								<div>
									<div id="result"></div>
									<div class="line">
											<div class="line">
												<div class="quarter">SFID</div>
												<div class="quarter">
													<div id="sfID">${sessionScope.EmployeeDetails.sfid}</div>	
												</div>
												<div class="quarter">Name</div>
												<div class="quarter">
													<div id="empName">${sessionScope.EmployeeDetails.name}</div>	
												</div>
											</div>
											<div class="line">
												<div class="quarter">Designation</div>
												<div class="quarter">
													<div id="designation">${sessionScope.EmployeeDetails.designationName}</div>	
												</div>
												<div class="quarter">DIR/PD/AD/TD/PROJ/DIV</div>
												<div class="quarter">
													<div id="division">
														<c:choose>
															<c:when test='${sessionScope.EmployeeDetails.divisionName ne null and
															sessionScope.EmployeeDetails.divisionName ne ""}'>
																${sessionScope.EmployeeDetails.directorateName}
															</c:when>
															<c:otherwise><c:out value='${sessionScope.EmployeeDetails.directorateName}'></c:out></c:otherwise>
														</c:choose>
													</div>	
												</div>
											</div>
											<div class="line">
												<div class="quarter">Internal Phone No</div>
												<div class="quarter">
													<div id="internalPhone">${sessionScope.EmployeeDetails.internalNo}</div>	
												</div>
											</div>
											<div class="line">
												<div class="quarter">Type Of LTC<span class='failure'>*</span></div>
												<div class="quarter">
													<input type="radio" id="ltcType1" name="ltcType" onclick="javascript:enableTypeOfLtc();"/>All India
												</div>
												<div class="quarter">
													<input type="radio" id="ltcType2" name="ltcType" onclick="javascript:enableTypeOfLtc();"/>Home Town
												</div>
											</div>
											<div class="line">
												<div class="quarter">Block Year<span class='failure'>*</span></div>
												<div class="quarter">
													<form:select path="blockId" id="blockId" cssStyle="width:145px;">
														<form:option value="Select" label="Select"></form:option>
														<form:options items="${sessionScope.blockYearsList}" itemLabel="blockYear" itemValue="id"></form:options>
													</form:select>
												</div>
											</div>
											<div id="typeOfLtc" style="display:none">
												<div class="line">
													<div class="quarter">Place Of Visit<span class='failure'>*</span></div>
													<div class="quarter">
														<form:input path="placeOfVisit" id="placeOfVisit"/>
													</div>
													<div class="quarter">Nearest Railway Station/Airport</div>
													<div class="quarter">
														<form:input path="nearestRlyStation" id="nearestRlyStation"/>
													</div>
												</div>
												
											</div>
											<div id="typeOfLtcLables" style="display:none">
												<div class="line">
													<div class="quarter">Place Of Visit</div>
													<div class="quarter">
														<div id="visitPlace">&nbsp;${sessionScope.homeTownAddress.address2}</div>
													</div>
													<div class="quarter">Nearest Railway Station/Airport</div>
													<div class="quarter">
														<div id="nrlyStation">&nbsp;
															<c:choose>
																<c:when test='${sessionScope.homeTownAddress.nearestRyStation ne null and
																sessionScope.homeTownAddress.nearestRyStation ne ""}'>
																	${sessionScope.homeTownAddress.nearestRyStation}
																</c:when>
																<c:otherwise><c:out value='${sessionScope.homeTownAddress.nearestAirport}'></c:out></c:otherwise>
															</c:choose>
														</div>
													</div>
												</div>
												<div class="line">
													<div class="quarter">Home Town Address</div>
													<div class="quarter">
														<div id="homeAddress">&nbsp;${sessionScope.homeTownAddress.address1}&nbsp;${sessionScope.homeTownAddress.address2}&nbsp;${sessionScope.homeTownAddress.address3}&nbsp;
														${sessionScope.homeTownAddress.city}&nbsp;${sessionScope.homeTownAddress.district}&nbsp;${sessionScope.homeTownAddress.state}&nbsp;${sessionScope.homeTownAddress.pincode}
														</div>
													</div>
												</div>
											</div>
											<div class="line">
												<div class="quarter">Date Of Departure</div>
													<div class="quarter">
															<form:input path="departureDate" id="departureDate" readonly="true"/> 
																<img  src="./images/calendar.gif"   id="date_start_trigger3" />	
																<script type="text/javascript">
																	Calendar.setup({inputField :"departureDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
																</script>
													</div>
												<div class="quarter">Applied Leave Type<span class='failure'>*</span></div>
												<div class="quarter">
													<form:select path="appliedLeaveType" id="appliedLeaveType" cssStyle="width:145px;">
														<form:option value="Select" label="Select"></form:option>
														<form:options items="${sessionScope.ltcLeaveTypeList}" itemValue="id" itemLabel="name"></form:options>
													</form:select>
												</div>
												
											</div>
											<div class="line">
												<div class="quarter">Leave Duration From<span class='failure'>*</span></div>
												<div class="quarter">
														<form:input path="leaveDurationFrom" id="leaveDurationFrom" readonly="true"/> 
															<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
															<script type="text/javascript">
																Calendar.setup({inputField :"leaveDurationFrom",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
															</script>
												</div>
												<div class="quarter">Leave Duration To<span class='failure'>*</span></div>
												<div class="quarter">
													<form:input path="leaveDurationTo" id="leaveDurationTo" readonly="true"/> 
															<img  src="./images/calendar.gif"   id="date_start_trigger2" />	
															<script type="text/javascript">
																Calendar.setup({inputField :"leaveDurationTo",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
															</script>
												</div>
											</div>
											<div class="line">
												<div class="quarter">Whether Encashment Of EL Required</div>
												<div class="quarter">
													<input type="radio" id="encashmentOfEL1" name="encashmentOfEL" onclick="javascript:encashOfEL();"/>Yes
												</div>
												<div class="quarter">
													<input type="radio" id="encashmentOfEL2" name="encashmentOfEL" onclick="javascript:encashOfEL();"/>No
												</div>
											</div>
											<div class="line">
												<div id="numOfDays" style="display:none">
													<div class="quarter">No Of Days</div>
													<div class="quarter">
														<form:input path="noOfDays" id="noOfDays"/>
													</div>
												</div>
											</div>
											<div class="line">
												<div class="quarter">Whether LTC Advance Drawn Earlier For The Same Block Year? If So Please Indicate</div>
												<div class="quarter">
													<input type="radio" id="ltcAdvance1" name="ltcAdvance" onclick="javascript:ltcAdvanceDrawn();"/>Yes
												</div>
												<div class="quarter">
													<input type="radio" id="ltcAdvance2" name="ltcAdvance" onclick="javascript:ltcAdvanceDrawn();"/>No
												</div>
											</div>
											<div id="ltcAdvDrawn" style="display:none">
												<div class="line">
													<div class="quarter">Date Of Advance Drawn</div>
													<div class="quarter">
														<form:input path="advanceDrawnDate" id="advanceDrawnDate" readonly="true"/> 
																<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
																<script type="text/javascript">
																	Calendar.setup({inputField :"advanceDrawnDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
																</script>
													</div>
													<div class="quarter">Amount Drawn</div>
													<div class="quarter">
														<form:input path="advanceAmount" id="advanceAmount"/>
													</div>
												</div>
												<div class="line">
													<div class="quarter">Place Of Visit</div>
													<div class="quarter">
														<form:input path="lastPlaceOfVisit" id="lastPlaceOfVisit"/>
													</div>
													<div class="quarter">Reasons For Not Performing The Journey</div>
													<div class="quarter">
														<form:input path="reasonForNotPerforming" id="reasonForNotPerforming"/>
													</div>
												</div>
											</div>
											<div class="line">
												<div class="quarter">Spouse Employeed Or Not</div>
												<div class="quarter">
													<input type="radio" id="spouseEmpFlag1" name="spouseEmployFlag"/>Yes
												</div>
												<div class="quarter">
													<input type="radio" id="spouseEmpFlag2" name="spouseEmployFlag"/>No
												</div>
											</div>
									</div>
									<div class="line">
										<div class="headTitle">Details Of Family Members Including Self For LTC</div>
										<div id="result" class="line">
											<jsp:include page="LtcFamilyDataList.jsp" />
										</div>
									</div>
									<div class="line">
										<div style="margin-left:25%;"><a class="quarterbutton appbutton" href="javascript:ltcRequest();">Submit</a></div>
										<div class="appbutton"><a class="quarterbutton appbutton" href="javascript:clearLtcRequest();">Clear</a></div>
									</div>
								</div>
								<%-- Content Page end --%>
								<div>&nbsp;</div>
							</div>
						</div>
						<div class="whitebox_b_ Non">
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
	</form:form>
		
		
	</body>
</html>
<!-- End:LtcApplicationForm.jsp -->