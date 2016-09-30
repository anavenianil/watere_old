<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LtcAdvanceRequest.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/numberToword.js" ></script>
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

<title>LTC Advance Request</title>
</head>

<body>
	<form:form method="post" commandName="ltcAdvanceRequest" id="ltcAdvance">
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
								<div class="headTitle" id="headTitle">LTC Advance Request</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</img></div>
								<%-- Content Page starts --%>
								<fieldset><legend><strong><font color='green'>Ltc Advance</font></strong></legend>
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
												<div class="quarter">Division</div>
												<div class="quarter">
													<div id="division"><c:choose>
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
									<div class="quarter">Internal Phone number</div>
									<div class="quarter">
									    <div id="phoneNum">${sessionScope.EmployeeDetails.internalNo}</div>	
									</div>
									<div class="quarter">Grade Pay</div>
									<div class="quarter">
										<div id="gradePay">${sessionScope.EmployeeDetails.gradePay}</div>	
									</div>
								</div>
								<div class="line">
									<div class="quarter">Employee Type</div>
									<div class="quarter">
									    <div id="employmentType">${sessionScope.EmployeeDetails.employmentTypeId}</div>	
									</div>
									<div class="quarter">DOJ in DRDO</div>
									<div class="quarter">
										<div id="doj">${sessionScope.EmployeeDetails.dojDrdo}</div>	
									</div>
								</div>
								<div class="line">
									<div class="quarter">Date of retirement</div>
									<div class="quarter">
									    <div id="dor">${sessionScope.EmployeeDetails.retirementDate}</div>	
									</div>
									<div class="quarter">Type of LTC<span class='failure'>*</span></div>
									<div class="quarter">
										<c:forEach items="${ltcAdvanceRequest.ltcTypeDetails}" var="ltcTypes">
											<c:if test="${ltcTypes.name eq 'All India'}">
												<form:radiobutton id="ltcTypeId1" path="ltcTypeId" value='${ltcTypes.id}' onclick="javascript:enableTypeOfLtc();"/>India
											</c:if>
											<c:if test="${ltcTypes.name eq 'Home Town'}">
												<form:radiobutton id="ltcTypeId2" path="ltcTypeId"  value='${ltcTypes.id}' onclick="javascript:enableTypeOfLtc();"/>HomeTown
											</c:if>
										</c:forEach>
									</div>			
								</div>
								<div class="line">
									<div class="quarter">LTC Block Year<span class='failure'>*</span></div>
									<div class="quarter">
									    <div id="ltcBlockId">
									    <form:select path="ltcBlockYearId" id="ltcBlockYearId" cssStyle="width:145px;" onchange="javascript:checkApplingBlockYear();">
											<form:option value="Select" label="Select"></form:option>
											<form:options items="${ltcAdvanceRequest.ltcBlockYearList}" itemLabel="name" itemValue="key"></form:options>
										</form:select>
									    </div>	
									</div>
									<div class="quarter">Approved Leave<span class='failure'>*</span></div>
									<div class="quarter">
										<form:select path="leaveRequestId" id="leaveRequestId" cssStyle="width:145px;">
											<form:option value="Select" label="Select"></form:option>
											<form:options items="${ltcAdvanceRequest.ltcLeaveTypeList}" itemValue="key" itemLabel="name"></form:options>
										</form:select>
									</div>
								</div>
								<div class="line">
									<div id="typeOfLtc" style="display:none">
										<div class="line">
											<div class="quarter">Place of Visit<span class='failure'>*</span></div>
											<div class="quarter">
												<form:input path="placeOfVisit" id="placeOfVisit" maxlength="50"/>
											</div>
											<div class="quarter">Nearest Railway Station/Airport<span class='failure'>*</span></div>
											<div class="quarter">
												<form:input path="nearestRailwayStation" id="nearestRlyStation" maxlength="50"/>
											</div>
										</div>
									</div>
								</div>
								<div id="typeOfLtcLables" style="display:none">
								<div class="line">
									<div class="quarter">Place of Visit</div>
									<div class="quarter">
										<div id="visitPlace">&nbsp;${ltcAdvanceRequest.homeTownAddress.city}</div>
									</div>
									<div class="quarter">Nearest Railway Station/Airport</div>
									<div class="quarter">
										<div id="nrlyStation">&nbsp;
											<c:choose>
												<c:when test='${ltcAdvanceRequest.homeTownAddress.nearestRyStation ne null and
													ltcAdvanceRequest.homeTownAddress.nearestRyStation ne ""}'>
													${ltcAdvanceRequest.homeTownAddress.nearestRyStation}
												</c:when>
												<c:otherwise><c:out value='${ltcAdvanceRequest.homeTownAddress.nearestAirport}'></c:out></c:otherwise>
											</c:choose>
										</div>
									</div>
								</div>
								<div class="line">
									<div class="quarter">Home Town Address</div>
									<div class="quarter">
										<div id="homeAddress">&nbsp;${ltcAdvanceRequest.homeTownAddress.address1}&nbsp;${ltcAdvanceRequest.homeTownAddress.address2}&nbsp;${ltcAdvanceRequest.homeTownAddress.address3}&nbsp;
											${ltcAdvanceRequest.homeTownAddress.city}&nbsp;${ltcAdvanceRequest.homeTownAddress.district}&nbsp;${ltcAdvanceRequest.homeTownAddress.state}&nbsp;${ltcAdvanceRequest.homeTownAddress.pincode}
										</div>
									</div>
								</div>
								</div>
								<div class="line">
									<div class="quarter">Leave Encashment Type<span class='failure'>*</span></div>
									<div class="quarter">
										<form:select path="encashTypeId" id="encashTypeId" cssStyle="width:145px;" onchange="javascript:advanceEncashOfEL();">
											<form:option value="0" label="Select"></form:option>
											<form:options items="${ltcAdvanceRequest.leaveEncasList}" itemValue="id" itemLabel="leaveTypeDetails.leaveType"></form:options>
										</form:select>
									</div>
									<div id="numOfDays" style="display:none">
										<div class="quarter">Encashment days<span class='failure'>*</span></div>
										<div class="quarter">
											<div><form:input path="noOfDays" id="noOfDays" maxlength="50"/></div>	
										</div>
									</div>
								</div>	
								<div class="line">
									<div class="quarter">Date of Departure<span class='failure'>*</span></div>
									<div class="quarter">
										<form:input path="departureDate" id="departureDate" readonly="true"/> 
											<img  src="./images/calendar.gif"   id="date_start_trigger5" />	
												<script type="text/javascript">
													Calendar.setup({inputField :"departureDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger5",singleClick : true,step : 1});
												</script>
									</div>
									<div class="quarter">Date of return<span class='failure'>*</span></div>
									<div class="quarter">
										<form:input path="returnDate" id="returnDate" readonly="true"/> 
											<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
												<script type="text/javascript">
													Calendar.setup({inputField :"returnDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
												</script>
									</div>
								</div>
								<div class="line">		
									<div class="quarter">Desired advance amount</div>
									<div class="quarter">
										<div id="amountClaimed"><form:input path="amountClaimed" id="amountClaimed1" maxlength="50"/></div>	
									</div>
								</div>
								<div class="line">
									<div class="headTitle">Details of Family Members For LTC advance</div>
									<div id="LtcFamilyDetailsList" class="line">
										<jsp:include page="LtcFamilyDetailsList.jsp" />
									</div>
								</div>
							</fieldset>
							<div class="line" id="result"></div>
							<div class="line">
								<div style="margin-left:45%;"><a class="quarterbutton appbutton" href="javascript:submitLtcAdvanceRequest();">Save</a></div>
								<div class="appbutton"><a class="quarterbutton appbutton" href="javascript:clearLtcAdvanceForm();">Clear</a></div>
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
		<form:hidden path="jsonValue" id="jsonValue"/>
	</form:form>
	<script>
		addId='${ltcAdvanceRequest.homeTownAddress.id}';
		place='${ltcAdvanceRequest.homeTownAddress.city}';
		rlyStation='${ltcAdvanceRequest.homeTownAddress.nearestRyStation}';
	</script>	
		
	</body>
</html>
<!-- End:LtcAdvanceRequest.jsp -->