<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTRequestApplication.jsp  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script type="text/javascript" src="script/jquery-ui-timepicker-addon.js"></script>
<link href="styles/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/date.js"></script>

<script type="text/javascript"  src="script/calendar.js"></script>
<script type="text/javascript"  src="script/calendar-en.js"></script>
<script type="text/javascript"  src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/mt.js"></script>

<title>Vehicle Request Application</title>
</head>
<body  style="width: 98%;">
	<form:form method="post" commandName="mtBean" id="mtBean">
		<div>
			<div class="Innermaindiv" style="width: 100%;">
				<div class="header" style="width: 100%;"></div>
				<div><jsp:include page="MenuLinks.jsp" /></div>					
				<div class="mainDisplay"  style="width: 100%;">
					<div class="whitebox1"  style="width: 98%;">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								<div class="whitebox_tr"></div>
							</div>
						</div>
						<div class="whitebox_m1" style="width: 98.5%;">
							<div class="lightblueBg1 mtBMW" style="width: 100%;">
								<div class="headTitle">Vehicle Requisition Form</div>
								<div>
									<%-- Content Page starts --%>
										<c:if test="${message=='invalid'}"><span class="failure"><spring:message code="Invalid"/></span></c:if>
								  <div class="line" id="result"></div>
								  <c:if test="${mtBean.param eq 'vehicleRequestForm'}">
								  <div class="line">
							     			<div class="quarter bold">Initiating Officer: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
											<div class="quarter">${mtBean.employeeDetails.nameInServiceBook}&nbsp;&&nbsp; ${mtBean.employeeDetails.designationDetails.name}</div>
	                              			<div class="quarter bold">SFNo. & Phone No.: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
											<div class="quarter">${mtBean.employeeDetails.userSfid}&nbsp;&&nbsp; ${mtBean.employeeDetails.personalNumber}</div>
	                              </div>
	                              </c:if>
	                              <c:if test="${mtBean.param eq 'OfflineVehicleRequestForm' || mtBean.param eq 'getEmpDetails'}">
	                               <div class="line">
	                               <div class="quarter bold">Initiating Officer(Sfid)<span class="mandatory">*</span></div>
												<div class="quarter">
												   <form:select path="requestedFor" id="requestedFor" cssClass="formSelect" onchange="javascript:getEmpDetails(event);">
													<form:option value="0">Select</form:option>
													
													<c:forEach items="${AllEmployeeList}" var="empList">
													
													<form:option value="${empList.userSfid}">${empList.nameInServiceBook} -- ${empList.designationDetails.name}(${empList.userSfid})</form:option>
												
													
													</c:forEach>
												  </form:select>		
												 
												<%--<form:input path="requestedFor" id="requestedFor" maxlength="100" onkeypress="javascript:getEmpDetails(event);"/>--%>
												</div>
												<div class="quarter" id="empDetails">
												<c:if test="${mtBean.employeeDetails ne null}">
												${mtBean.employeeDetails.nameInServiceBook},
												${mtBean.employeeDetails.designationName},
												${mtBean.employeeDetails.officeName}
												</c:if>
												</div>			
	                               </div>
	                               
	                              </c:if>
	                              <div class="line">
													<div class="quarter bold">Vehicle Required <span class="mandatory">*</span></div>
													<div class="quarter bold"> 
															<input type="radio" name="vehicleReturn" id="vehicleReturn1" value="0" checked="checked"  onclick="javascript:checkReturnVehicleRequired('NO');">One Way</input>
															<input type="radio" name="vehicleReturn" id="vehicleReturn2" value="1"  onclick="javascript:checkReturnVehicleRequired('YES');">Both Ways</input>
													</div>
								</div>	
								<div class="line">
												<div class="quarter bold">Purpose of Visit<span class="mandatory">*</span></div>
												<div class="quarter">
												<%--<form:textarea path="purposeOfVisiting" id="purposeOfVisiting" maxlength="100"/>
												 <input type="text" disabled="" id="counter" value="500" name="counter" class="counter"/>--%>
												<form:textarea path="purposeOfVisiting" id="purposeOfVisiting" cols="20" rows="3" onkeypress="textCounterMT(event,$jq('#purposeOfVisiting'),$jq('#counter'),99);"></form:textarea>
												 <input type="text" disabled="" id="counter" value="100" name="counter" class="counter"/>
												<%--<form:select path="purposeOfVisiting" id="purposeOfVisiting" cssClass="formSelect">
													<form:option value="">Select</form:option>
													<form:option value="">Select</form:option>
													</form:select>--%>
												</div>		
								</div>
								
								<div class="line">
								</div>
								
								<div  class="line" id="onwardJourneyDetailsID">
									<fieldset><legend><strong><font color="green">Onward Journey Details </font></strong></legend>
										<div class="line">
											<table class="fortable" width="100%" id="journeyDetailsID">
											<tr>
												<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
											</tr>	
												<tr>
													<td rowspan="3" class="tabletd" align="center">No of People<span class="mandatory">*</span></font></td>
													<td rowspan="3" class="tabletd" align="center" style="width: 12%;">Vehicle Shall Report To  (Name With Designation)<span class="mandatory">*</span></td>
													<td colspan="5" class="tabletd" align="center" >Departure/Arrival</td>
													<td rowspan="2" colspan="2" class="tabletd" align="center">Pickup Place</td>
													<td rowspan="2" colspan="2" class="tabletd" align="center">Dropping Place</td>
													<td rowspan="3" class="tabletd" align="center">Accom Req<span class="mandatory">*</span></td>
													<td rowspan="3" class="tabletd" align="center">Accom Place</td>
													<td rowspan="3" class="tabletd" align="center">Article Carry<span class="mandatory">*</span></td>
													<td rowspan="3" class="tabletd" align="center">Remarks (Mobile No/ Flight No)/Lab</td>
													<td rowspan="3" class="tabletd" align="center">Add</td>
													<td rowspan="3" class="tabletd" align="center">Del</td>
													<td rowspan="3" class="tabletd" align="center">Repeat For Next</td>
												</tr>
												<tr>
													<td colspan="2" class="tabletd" align="center">Reporting</td>
													<td colspan="1" class="tabletd" align="center">Duration</td>
													<td colspan="2" class="tabletd" align="center">Estimated</td>
												</tr>
												<tr>
													<td class="tabletd" align="center">Date<span class="mandatory">*</span></td>
													<td class="tabletd" align="center">Time<span class="mandatory">*</span></td>
													<td class="tabletd" align="center">HH:MM</td>
													<td class="tabletd" align="center">Date<span class="mandatory">*</span></td>
													<td class="tabletd" align="center">Time<span class="mandatory">*</span></td>
													<td class="tabletd" align="center">Pickup<span class="mandatory">*</span></td>
													<td class="tabletd" align="center">Other Source</td>
													<td class="tabletd" align="center">Drop<span class="mandatory">*</span></td>
													<td class="tabletd" align="center">Other Destin</td>
												</tr>
												<tr>
													<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
												</tr>	
													<tr id="row_0">
														<td>
															<input type="text"  id="passengNo0" style="width:25px;" onkeypress="return checkInt(event)" onkeyup="copyToReturnJourney(0);"/>
														</td>
														<td>
														<input type="text"  id="passengName0" style="width:auto;" onkeyup="copyToReturnJourney(0);setArticals('onward',0);"/>
														<%--<c:if test="${mtBean.param eq 'vehicleRequestForm'}">
															<input type="text"  id="passengName0" style="width:70px;" onkeyup="copyToReturnJourney(0);setArticals('onward',0);" value="${mtBean.employeeDetails.nameInServiceBook},${mtBean.employeeDetails.designationDetails.name}"/>
														</c:if>
														 <c:if test="${mtBean.param ne 'vehicleRequestForm'}">
														 <input type="text"  id="passengName0" style="width:70px;" onkeyup="copyToReturnJourney(0);setArticals('onward',0);" value="${mtBean.employeeDetails.nameInServiceBook},
												${mtBean.employeeDetails.designationName}"/>
														 </c:if>--%>
														</td>
														<td>
															<input type="text" readonly="readonly" id="startDate0" style="height:16px;width:64px;font-size: 9px;font-weight: bold;"  onmouseover ="javascript:Calendar.setup({inputField :'startDate0',ifFormat : '%d-%b-%Y',showsTime : false,button :'startDate0',step : 1});" onchange="checkReqDates('onward');copyToReturnJourney(0);mtAddHoursMin(0,'onward');durationOfDates(0,'onward');" />
														</td>
														<td>
															<input type="text" readonly="readonly" id="startTime0" name=""  class ="startTime" style="width:37px" onmouseover="javascript:timePicker('startTime')" onchange="copyToReturnJourney(0);mtAddHoursMin(0,'onward');durationOfDates(0,'onward');" />
														</td>
														<td>
															<input type="text" id="durationH0" style="width:18px" onkeyup="mtAddHoursMin(0,'onward');" maxlength="3"/>
															<input type="text" id="durationM0" style="width:18px" onkeyup="mtAddHoursMin(0,'onward');" maxlength="2"/>
														</td>
														<td>
															<input type="text" readonly="readonly" id="estDate0" style="height:16px;width:64px;font-size: 9px;font-weight: bold;"  onmouseover ="javascript:Calendar.setup({inputField :'estDate0',ifFormat : '%d-%b-%Y',showsTime : false,button :'estDate0',step : 1});" onchange="checkReqDates('onward');copyToReturnJourney(0);durationOfDates(0,'onward');" />
														</td>
														<td>
															<input type="text" readonly="readonly" id="estTime0" name="" class ="estTime" style="width:37px" onmouseover="javascript:timePicker('estTime')" onchange="copyToReturnJourney(0);durationOfDates(0,'onward');" />
														</td>
														<td>
															<select id="pickupPoint0" style="width:80px;" onchange="copyToReturnJourney(0);">
																	<option value="0">Select</option>
																<c:forEach var="pic" items="${addressList}">
																	<option value="${pic.addressId}">${pic.addressName}</option>
																</c:forEach>
															</select>
														</td>
														<td>
															<input type="text"  id="otherSourceName0" style="width:50px;" onkeyup="copyToReturnJourney(0);"/>
														</td>
														<td>
															<select id="droppingPoint0" style="width:80px;" onchange="copyToReturnJourney(0);">
																	<option value="0">Select</option>
																<c:forEach var="drop" items="${addressList}">
																	<option value="${drop.addressId}">${drop.addressName}</option>
																</c:forEach>
															</select>
														</td>
														<td>
															<input type="text"  id="otherDestiName0" style="width:50px;" onkeyup="copyToReturnJourney(0);"/>
														</td>
														<td>
															<input type="checkbox"  id="accomReq10" value="1"  onclick="javascript:checkAccomdationRequired('YES','0');copyToReturnJourney(0);">Y</input>
															<input type="checkbox"  id="accomReq20" value="0"  onclick="javascript:checkAccomdationRequired('NO','0');copyToReturnJourney(0);" checked="checked">N</input>
														</td>
														<td>
															<input type="text" id="accomPlace0" style="width:50px;" readonly="readonly"/>
														</td>
														<td>
															<input type="checkbox" id="articleCarry10" value="1" onclick="javascript:checkArticleRequired('YES','0');copyToReturnJourney(0);">Y</input> 
															<input type="checkbox" id="articleCarry20" value="0" onclick="javascript:checkArticleRequired('NO','0');copyToReturnJourney(0);" checked="checked">N</input>
														</td>
														<td>
															<input type="text" id="remarks0" style="width:70px;" onkeyup="copyToReturnJourney(0);"/>
														</td>
														<td ><input type="button" id="add0" class="smallbtn" value="+" onclick="javascript:funcreatenewJourneyRow(0)"/></td>
														<td ><input type="button" id="del0" class="smallbtn" value="-" style="display: none" /></td>
										             	<td style="width: 100px;">
										             	    <input type="text" id="repeateNoOfDays" style="width:30px;" onkeyup="copyToReturnJourney(0);"/>Days
										             	    <input type="button" value="Repeat" onclick="javascript:checkRepeation(0);"/>
														</td>
													</tr>
												</table>		
										</div>
									</fieldset>
								</div>
								
								
								<div  class="line" id="onwardArticleDetailsID" style="display:none;">
									<fieldset><legend><strong><font color="green">Onward Article Details </font></strong></legend>
										<div class="line">
											<table class="fortable" width="100%">
												<tr id="passeng" style="display:none;"></tr>
												<%--<tr id="passeng04325" style="display:none;">
													<td>
														<div class="line" id="passeng0Name" style="color:red;">Passenger Row0</div>		
															<table id="passeng0Tab" width="100%" border="2px" cellpadding="0px" cellspacing="0px" align="center">
																<tr>
																		<td class="tabletd" align="center" style="width:15%;">Type</td>
																		<td class="tabletd" align="center" style="width:15%;">Length (CM)</td>
																		<td class="tabletd" align="center" style="width:15%;">Breadth (CM)</td>
																		<td class="tabletd" align="center" style="width:15%;">Height (CM)</td>
																		<td class="tabletd" align="center" style="width:15%;">Weight (KG)</td>
																		<td class="tabletd" align="center" style="width:15%;">Quantity</td>
																		<td class="tabletd" align="center" style="width:5%;">Add</td>
																		<td class="tabletd" align="center" style="width:5%;">Del</td>
																</tr>
																<tr id="passeng0Row0">
																		<td><select  id="passeng0RowArticleType0" style="width:98%;">
																				<option value="Non-Explosive">Non-Explosive</option>
																				<option value="Explosive">Explosive</option>
																			</select>
																		</td>
																		<td><input type="text"  style="width:98%;" id="passeng0RowLength0" onkeypress="javascript:return checkFloat(event,'passeng0RowLength0');" /></td>
																		<td><input type="text"  style="width:98%;" id="passeng0RowBreadth0" onkeypress="javascript:return checkFloat(event,'passeng0RowBreadth0');" /></td>
																		<td><input type="text"  style="width:98%;" id="passeng0RowHeight0" onkeypress="javascript:return checkFloat(event,'passeng0RowHeight0');" /></td>
																		<td><input type="text"  style="width:98%;" id="passeng0RowWeight0" onkeypress="javascript:return checkFloat(event,'passeng0RowWeight0');" /></td>
																		<td><input type="text"  style="width:98%;" id="passeng0RowQuantity0" onkeypress="javascript:return checkInt(event);" /></td>
																		<td><input type="button" style="width:98%;" id="passeng0RowAdd0" value="+" onclick="javascript:insertNewRow('passeng0','passeng0Tab','passeng0Row',0);" /></td>
																		<td><input type="button" style="width:98%;display:none;" id="passeng0RowDel0" value="-" onclick="javascript:deleteRow('passeng0','passeng0Tab','passeng0Row',0);" /></td>
																</tr>
															</table>				
													</td>
												</tr>--%>
											</table>
										</div>
									</fieldset>
								</div>
								
								<div  class="line" id="returnJourneyDetailsID" style="display:none;">
									<fieldset><legend><strong><font color="green">Return Journey Details </font></strong></legend>
										<div class="line">
											<table class="fortable" width="100%" id="RjourneyDetailsID">
											<tr>
												<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
											</tr>	
												<tr>
													<td rowspan="3" class="tabletd" align="center">No of People<span class="mandatory">*</span></font></td>
													<td rowspan="3" class="tabletd" align="center" style="width: 12%;">Vehicle Shall Report To  (Name With Designation<span class="mandatory">*</span></td>
													<td colspan="5" class="tabletd" align="center" >Departure/Arrival</td>
													<td rowspan="2" colspan="2" class="tabletd" align="center">Pickup Place</td>
													<td rowspan="2" colspan="2" class="tabletd" align="center">Dropping Place</td>
													<td rowspan="3" class="tabletd" align="center">Accom Req<span class="mandatory">*</span></td>
													<td rowspan="3" class="tabletd" align="center">Accom Place</td>
													<td rowspan="3" class="tabletd" align="center">Article Carry<span class="mandatory">*</span></td>
													<td rowspan="3" class="tabletd" align="center">Remarks (Mobile No/ Flight No)/Lab</td>
													<td rowspan="3" class="tabletd" align="center">Add</td>
													<td rowspan="3" class="tabletd" align="center">Del</td>
												</tr>
												<tr>
													<td colspan="2" class="tabletd" align="center">Reporting</td>
													<td colspan="1" class="tabletd" align="center">Duration</td>
													<td colspan="2" class="tabletd" align="center">Estimated</td>
												</tr>
												<tr>
													<td class="tabletd" align="center">Date<span class="mandatory">*</span></td>
													<td class="tabletd" align="center">Time<span class="mandatory">*</span></td>
													<td class="tabletd" align="center">HH:MM</td>
													<td class="tabletd" align="center">Date<span class="mandatory">*</span></td>
													<td class="tabletd" align="center">Time<span class="mandatory">*</span></td>
													<td class="tabletd" align="center">Pickup<span class="mandatory">*</span></td>
													<td class="tabletd" align="center">Other Source</td>
													<td class="tabletd" align="center">Drop<span class="mandatory">*</span></td>
													<td class="tabletd" align="center">Other Destin</td>
												</tr>
												<tr>
													<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
												</tr>	
													<tr id="Rrow_0">
														<td>
															<input type="text"  id="RpassengNo0" style="width:25px;" onkeypress="return checkInt(event)" />
														</td>
														<td>
															<%-- <input type="text"  id="RpassengName0" style="width:70px;" onkeyup="setArticals('return',0);"/>--%>
														<input type="text"  id="RpassengName0" style=width:auto; onkeyup="setArticals('return',0);"/>
															
														</td>
														<td>
															<input type="text" readonly="readonly" id="RstartDate0" style="height:16px;width:64px;font-size: 9px;font-weight: bold;"  id="RstartDate0" onmouseover ="javascript:Calendar.setup({inputField :'RstartDate0',ifFormat : '%d-%b-%Y',showsTime : false,button :'RstartDate0',step : 1});" onchange="checkReqDates('return');mtAddHoursMin(0,'return');durationOfDates(0,'return');"/>
														</td>
														<td>
															<input type="text" readonly="readonly" id="RstartTime0" name=""  class ="RstartTime" style="width:37px" onmouseover="javascript:timePicker('RstartTime')" onchange="javascript:mtAddHoursMin(0,'return');durationOfDates(0,'return');"/>
														</td>
														<td>
															<input type="text" id="RdurationH0" style="width:18px" onkeyup="mtAddHoursMin(0,'return');" maxlength="3"/>
															<input type="text" id="RdurationM0" style="width:18px" onkeyup="mtAddHoursMin(0,'return');" maxlength="2"/>
														</td>
														<td>
															<input type="text" readonly="readonly" id="RestDate0" style="height:16px;width:64px;font-size: 9px;font-weight: bold;"  id="RestDate0" onmouseover ="javascript:Calendar.setup({inputField :'RestDate0',ifFormat : '%d-%b-%Y',showsTime : false,button :'RestDate0',step : 1});" onchange="checkReqDates('return');durationOfDates(0,'return');"/>
														</td>
														<td>
															<input type="text" readonly="readonly" id="RestTime0" name="" class ="RestTime" style="width:37px" onmouseover="javascript:timePicker('RestTime')" onchange="javascript:durationOfDates(0,'return');"/>
														</td>
														<td>
															<select id="RpickupPoint0" style="width:80px;">
																	<option value="0">Select</option>
																<c:forEach var="pic" items="${addressList}">
																	<option value="${pic.addressId}">${pic.addressName}</option>
																</c:forEach>
															</select>
														</td>
														<td>
															<input type="text"  id="RotherSourceName0" style="width:50px;"/>
														</td>
														<td>
															<select id="RdroppingPoint0" style="width:80px;">
																	<option value="0">Select</option>
																<c:forEach var="drop" items="${addressList}">
																	<option value="${drop.addressId}">${drop.addressName}</option>
																</c:forEach>
															</select>
														</td>
														<td>
															<input type="text"  id="RotherDestiName0" style="width:50px;"/>
														</td>
														<td>
															<input type="checkbox"  id="RaccomReq10" value="1"  onclick="javascript:ReturncheckAccomdationRequired('YES','0');">Y</input>
															<input type="checkbox"  id="RaccomReq20" value="0"  onclick="javascript:ReturncheckAccomdationRequired('NO','0');" checked="checked">N</input>
														</td>
														<td>
															<input type="text" id="RaccomPlace0" style="width:50px;" readonly="readonly"/>
														</td>
														<td>
															<input type="checkbox" id="RarticleCarry10" value="1" onclick="javascript:ReturncheckArticleRequired('YES','0');">Y</input> 
															<input type="checkbox" id="RarticleCarry20" value="0" onclick="javascript:ReturncheckArticleRequired('NO','0');" checked="checked">N</input>
														</td>
														<td>
															<input type="text" id="Rremarks0" style="width:70px;"/>
														</td>
														<td ><input type="button" id="Radd0" class="smallbtn" value="+" onclick="javascript:ReturnfuncreatenewJourneyRow(0)"/></td>
														<td ><input type="button" id="Rdel0" class="smallbtn" value="-" style="display: none" /></td>
											
													</tr>
												</table>		
										</div>
									</fieldset>
								</div>
								
								<div  class="line" id="returnArticleDetailsID" style="display:none;">
									<fieldset><legend><strong><font color="green">Return Article Details </font></strong></legend>
										<div class="line">
											<table class="fortable" width="100%">
												<tr id="Rpasseng" style="display:none;"></tr>
												<%--<tr id="passeng04325" style="display:none;">
													<td>
														<div class="line" id="passeng0Name" style="color:red;">Passenger Row0</div>		
															<table id="passeng0Tab" width="100%" border="2px" cellpadding="0px" cellspacing="0px" align="center">
																<tr>
																		<td class="tabletd" align="center" style="width:15%;">Type</td>
																		<td class="tabletd" align="center" style="width:15%;">Length (CM)</td>
																		<td class="tabletd" align="center" style="width:15%;">Breadth (CM)</td>
																		<td class="tabletd" align="center" style="width:15%;">Height (CM)</td>
																		<td class="tabletd" align="center" style="width:15%;">Weight (KG)</td>
																		<td class="tabletd" align="center" style="width:15%;">Quantity</td>
																		<td class="tabletd" align="center" style="width:5%;">Add</td>
																		<td class="tabletd" align="center" style="width:5%;">Del</td>
																</tr>
																<tr id="passeng0Row0">
																		<td><select  id="passeng0RowArticleType0" style="width:98%;">
																				<option value="Non-Explosive">Non-Explosive</option>
																				<option value="Explosive">Explosive</option>
																			</select>
																		</td>
																		<td><input type="text"  style="width:98%;" id="passeng0RowLength0" onkeypress="javascript:return checkFloat(event,'passeng0RowLength0');" /></td>
																		<td><input type="text"  style="width:98%;" id="passeng0RowBreadth0" onkeypress="javascript:return checkFloat(event,'passeng0RowBreadth0');" /></td>
																		<td><input type="text"  style="width:98%;" id="passeng0RowHeight0" onkeypress="javascript:return checkFloat(event,'passeng0RowHeight0');" /></td>
																		<td><input type="text"  style="width:98%;" id="passeng0RowWeight0" onkeypress="javascript:return checkFloat(event,'passeng0RowWeight0');" /></td>
																		<td><input type="text"  style="width:98%;" id="passeng0RowQuantity0" onkeypress="javascript:return checkInt(event);" /></td>
																		<td><input type="button" style="width:98%;" id="passeng0RowAdd0" value="+" onclick="javascript:insertNewRow('passeng0','passeng0Tab','passeng0Row',0);" /></td>
																		<td><input type="button" style="width:98%;display:none;" id="passeng0RowDel0" value="-" onclick="javascript:deleteRow('passeng0','passeng0Tab','passeng0Row',0);" /></td>
																</tr>
															</table>				
													</td>
												</tr>--%>
											</table>
										</div>
									</fieldset>
								</div>
								<%--
	                              <div class="line">
											<div class="quarter bold">Requested For<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="requestedFor" id="requestedFor" maxlength="100" /></div>
											<div class="quarter bold">Mobile Number<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="mobileNo" id="mobileNo" maxlength="100" onkeypress="return checkInt(event)" /></div>
								</div>	
								
								<div class="line">
											<div class="quarter bold">No of People<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="noOfPeople" id="noOfPeople" maxlength="100" onkeypress="return checkInt(event)" /></div>
											<div class="quarter bold">Purpose of Visit<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="purposeOfVisiting" id="purposeOfVisiting" maxlength="100" /></div>		
								</div>
								<div class="line">
											<div class="quarter bold">Traveling Date & Time<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="travellingDate" id="travellingDate" maxlength="100" readonly="true"/><img  src="./images/calendar.gif"   id="date_start_trigger1" />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"travellingDate",ifFormat : "%d-%b-%Y %H-%M ",showsTime : true,button :"date_start_trigger1",singleClick : true,step : 1});
														</script> 
											</div>
											<div class="quarter bold">Estimated Date & Time<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="estimatedDateTime" id="estimatedDateTime" maxlength="100" readonly="true"/><img  src="./images/calendar.gif"   id="date_start_trigger4" />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"estimatedDateTime",ifFormat : "%d-%b-%Y %H-%M ",showsTime : true,button :"date_start_trigger4",singleClick : true,step : 1});
														</script> 
											</div>
											
								</div>
								<div class="line">
											<div class="quarter bold">Pickup point<span class="mandatory">*</span></div>
											<div class="quarter">
													<form:select path="pickupPoint" id="pickupPoint" >
																<form:option value="0">Select</form:option>
																<form:options items="${addressList}" itemValue="addressId" itemLabel="addressName"/>
													</form:select>
											</div>
											<div class="quarter bold">Other Source<span class="mandatory"></span></div>
											<div class="quarter"><form:input path="otherSourceName" id="otherSourceName" maxlength="100" /></div>
		 										
								</div>
								<div class="line">
												<div class="quarter bold">Dropping point<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:select path="droppingPoint" id="droppingPoint">
														<form:option value="0">Select</form:option>
														<form:options items="${addressList}" itemValue="addressId" itemLabel="addressName"/>
													</form:select>
												</div>
												<div class="quarter bold">Other Destination<span class="mandatory"></span></div>
												<div class="quarter"><form:input path="otherDestiName" id="otherDestiName" maxlength="100" /></div>
								</div>	
								<div class="line">
													<div class="quarter bold">If any article to be Carry<span class="mandatory">*</span></div>
													<div class="quarter bold">
															<input type="radio" name="articleCarry" id="articleCarry1" value="1" onclick="javascript:getArticle('YES');">Yes</input> 
															<input type="radio" name="articleCarry" id="articleCarry2" value="0"  onclick="javascript:getArticle('NO');">No</input>
													</div>
								</div>	
								<div id="articlediv" style="display:none"></div>
								<div class="line">
													<div class="quarter bold">Accommodation Required<span class="mandatory">*</span></div>
													<div class="quarter bold" >
															<input type="radio" name="accommodation" id="accommodation1" value="1"  onclick="javascript:getAccomdation('YES');">Yes</input>
															<input type="radio" name="accommodation" id="accommodation2" value="0"  onclick="javascript:getAccomdation('NO');">No</input>
													</div>
								</div>	
								<div id="accomdationdiv" style="display:none"></div>
	
								<div id="vehicleReturndiv" style="display:none">
									
												
												<div class="line">
													<div class="quarter bold">Same Vehicle Reqiured <span class="mandatory">*</span></div>
													<div class="quarter bold">
														<input type="radio" name="vehicleReturnSame" id="vehicleReturnSame1" value="1" >Yes</input>
														<input type="radio" name="vehicleReturnSame" id="vehicleReturnSame2" value="0" >No </input>
													</div>
											   </div>
												<div class="line">	
													<div class="quarter bold" >No. of People<span class="mandatory">*</span></div>
													<div class="quarter"><input name="returnPeople" id="returnPeople" onkeypress="return checkInt(event)"/></div>
												</div>
												<div class="line">
													<div class="quarter bold" >Travelling Date & Time<span class="mandatory">*</span></div>
													<div class="quarter"><input name="returnDate" id="returnDate"  readonly="true" /><img  src="./images/calendar.gif" id="date_start_trigger3" />
														<script>Calendar.setup( {inputField : "returnDate",ifFormat : "%d-%b-%Y %H-%M ",showsTime : true,button : "date_start_trigger3",singleClick : true,step : 1});</script>
												    </div>
													<div class="quarter bold" >Estimated Date & Time<span class="mandatory">*</span></div>
													<div class="quarter"><input name="returnEstimatedDateTime" id="returnEstimatedDateTime"  readonly="true" /><img  src="./images/calendar.gif" id="date_start_trigger2" />
														<script>Calendar.setup( {inputField : "returnEstimatedDateTime",ifFormat : "%d-%b-%Y %H-%M ",showsTime : true,button : "date_start_trigger2",singleClick : true,step : 1});</script>
												    </div>
											  </div>
											 <div class="line">
													<div class="quarter bold">Pickup point<span class="mandatory">*</span></div>
													<div class="quarter">
															<form:select path="returnPickUpPoint" id="returnPickUpPoint" >
																		<form:option value="0">Select</form:option>
																		<form:options items="${addressList}" itemValue="addressId" itemLabel="addressName"/>
															</form:select>
													</div>
													<div class="quarter bold">Other Source<span class="mandatory"></span></div>
													<div class="quarter"><form:input path="returnOtherSourceName" id="returnOtherSourceName" maxlength="100" /></div>
				 										
											</div>
											<div class="line">
														<div class="quarter bold">Dropping point<span class="mandatory">*</span></div>
														<div class="quarter">
															<form:select path="returnDroppingPoint" id="returnDroppingPoint">
																<form:option value="0">Select</form:option>
																<form:options items="${addressList}" itemValue="addressId" itemLabel="addressName"/>
															</form:select>
														</div>
														<div class="quarter bold">Other Destination<span class="mandatory"></span></div>
														<div class="quarter"><form:input path="returnOtherDestiName" id="returnOtherDestiName" maxlength="100" /></div>
											</div>	
											<div class="line">
												<div class="quarter bold">If any article to be Carry<span class="mandatory">*</span></div>
												<div class="quarter bold">
													<input type="radio" name="returnArticleCarry" id="returnArticleCarry1" value="1" onclick="javascript:getReturnArticle('YES');" />Yes 
													<input type="radio" name="returnArticleCarry" id="returnArticleCarry2" value="0" onclick="javascript:getReturnArticle('NO');" />No 
												</div>
										</div>
										<div id="returnarticlediv" style="display:none"></div>
								
								</div>
								<div class="line" style="margin-left: 25%;">
												<div class="expbutton"><a onclick="javascript:saveRequestFormDetails('Online');"> <span>Send Request</span></a></div>
												<div class="expbutton"><a onclick="javascript:clearRequestFormDetails();"> <span>Clear</span></a></div>
								</div>	
								<div id="result" class="line"></div>
								--%>
								<div class="line"></div>
								<div class="line"></div>
								<div class="line">
												<div class="expbutton" style="margin-left:75%;"><a onclick="javascript:saveVehicleRequestDetails();"> <span>Send Request</span></a></div>
												<div class="expbutton"><a onclick="javascript:clearVehicleRequestDetails();"> <span>Clear</span></a></div>
								</div>	
								
								<%-- Content Page end --%>
								</div>
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
			<div><%-- Begin : Footer.jsp --%>
<div class="whiteboxmain" style="width: 100%;">
	<div class="footermain"  style="width: 100%;">
		<div class="footerLeft"  style="width: 1%;"></div>
		<div class="footerBg"  style="width: 98%;">Copyright 1999-2009 © ASL All rights reserved.<span style="float:right;">
			Call us @ 040-2418-8059 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Mail us at: <b>ess@asl.net</b></span></div>
		<div class="footerright"  style="width: 1%;"></div>
	</div>
</div>
<%-- End : Footer.jsp --%></div>			
		</div>
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type"/>
		<form:hidden path="pk" id="pk"/>
		<form:hidden path="onwardMainJSON" id="onwardMainJSON"/>
		<form:hidden path="returnMainJSON" id="returnMainJSON"/>
		<form:hidden path="requestId" />
		<form:hidden path="requestID" />
		
		</form:form>
	</body>
	<script>
	var addressJSON=<%= (net.sf.json.JSONArray)session.getAttribute("addressListJSON") %>;
	</script>
</html>
<!-- End:MTRequestApplication.jsp  -->