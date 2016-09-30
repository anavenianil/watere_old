<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TadaTdSettlement.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script type="text/javascript" src="script/jquery-ui-timepicker-addon.js"></script>
<link href="styles/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="./script/RegExpValidate.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/tada.js"></script>

<title>TadaTdSettlement</title>
</head>
<body onload="timePicker('endTime'); timePicker('startTime');">
	<form:form method="post" commandName="tada" name="tada" id="tada">
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
							<div class="line" id="result">
								<jsp:include page="RequestResult.jsp"></jsp:include>
							</div>
							<c:if test="${tada.type=='tadaTdSettlement'}">
								<div class="headTitle" id="headTitle">TD Settlement Request</div>
							</c:if>
							<c:if test="${tada.type=='tadaTdReimbursement'}">
								<div class="headTitle" id="headTitle">TD Reimbursement Request</div>
							</c:if>
								<%-- Content Page starts --%>
								<div class="line">
									<c:if test="${tada.tadaApprovalRequestDTO.stayDuration!=0}">
									<div class="line">
									<!--basic and grade pay details hidden by bkr 26/04/2016  -->
									   <div class="quarter bold" style="display: none">Basic Pay & Grade Pay</div>
									   <div style="display: none" class="quarter" >:&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font>${tada.tadaApprovalRequestDTO.basicPay}/- & <font size="4.5em"><span class="WebRupee" >R</span></font>${tada.tadaApprovalRequestDTO.gradePay}/-</div>
									   <div class="quarter bold">Address Of TD Work Place</div>
									   <div class="quarter" id="tdWorkPlace">:&nbsp;${tada.tadaApprovalRequestDTO.tdWorkPlace}</div>
									</div>
									<div class="line">
									   <div class="quarter bold">Stay Duration</div>
									   <div class="quarter" id="stayDuration">: ${tada.tadaApprovalRequestDTO.stayDuration} Days</div>
									   <!--this div hidden by bkr 26/04/2014  -->
									   <div style="display: none">
									    <div class="quarter bold">Project For Which Move is authorized</div>
									   <div class="quarter" id="authorizedMove"><c:if test="${tada.tadaApprovalRequestDTO.authorizedMove==1}">: Build-Up</c:if><c:if test="${tada.tadaApprovalRequestDTO.authorizedMove==2}">: Project - ${tada.tadaApprovalRequestDTO.projectName}</c:if></div>
									</div>
									
									</div>
									</c:if>
									<c:if test="${tada.tadaApprovalRequestDTO.stayDuration==0}">
									<div class="line">
									   <div class="quarter bold">Basic Pay & Grade Pay</div>
									   <div class="quarter">:&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font>${tada.tadaApprovalRequestDTO.basicPay}/- & <font size="4.5em"><span class="WebRupee" >R</span></font>${tada.tadaApprovalRequestDTO.gradePay}/-</div>
									</div>
									</c:if>
								</div>		
								<div class="line">
								 <div class="quarter bold">Advance Drawn</div>
								 <div class="quarter" id="advAmount">:<font size="4.5em"><span class="WebRupee" >R</span></font>${tada.tadaApprovalRequestDTO.cdaAmount}</div>
								 <div style="display: none"><!--this div hidden by bkr 26/04/2014  -->
								 <div class="quarter bold">Ticket Cancellation Amount</div>
								 <div class="quarter" id="TicketCancel">:<font size="4.5em"><span class="WebRupee" >R</span></font>${tada.tadaApprovalRequestDTO.ticketCancelCharges} </div>
								</div>
								</div>
								<marquee direction="left" behavior="alternate"><div class="line" style="color:red" align="center">
								   Enter Accommodation and Food details bill wise as per submitted proofs.Don't consolidate bill amounts day wise. 
								</div></marquee>
								<div  class="line bold">
								Journey details [<fmt:formatDate pattern="dd-MMM-yyyy" value="${tada.departureDate}"/> to <fmt:formatDate pattern="dd-MMM-yyyy" value="${tada.arrivalDate}"/>]
								</div>
								<div class="line">
								<table style="width:100%" border="1" class="list" id="journeyDetailsId">
									
									<tr>
										<th style="width:34%;text-align:center" colspan="3">Departure</th>
										<th style="width:34%;text-align:center" colspan="3">Arrival</th>
										<th style="width:5%;text-align:center" rowspan="2">Distance in KM</th>
										<th rowspan="2" style="width:5%;text-align:center">Mode of travel</th>
										<th rowspan="2" style="width:7%;text-align:center">Ticket Fare <font size="4.5em"><span class="WebRupee" >R</span></font></th>
										<th rowspan="2" style="width:5%;text-align:center"><font color="pink">Ticket Cancel Amount <font size="4.5em"><span class="WebRupee" >R</span></font></font></th>
										<th rowspan="2" style="width:5%;text-align:center">Ticket No.</th>
										<th rowspan="2" style="width:5%;text-align:center">Add</th>
										<th rowspan="2" style="width:5%;text-align:center">Del</th>
									</tr>
									<tr>
										<th style="width:4%;text-align:center">Date</th>
										<th style="width:4%;text-align:center">Time</th>
										<th style="width:15%;text-align:center">Station</th>
										<th style="width:4%;text-align:center">Date</th>
										<th style="width:4%;text-align:center">Time</th>
										<th style="width:15%;text-align:center">Station</th>
									</tr>
									<c:if test="${tada.tdReqJourneyList!=null && tada.tdReqJourneyList!='[]' && tada.tdReqJourneyList!=''}">
									<%int i=0; %>
									<c:forEach var="tdReqJourneyList" items="${tada.tdReqJourneyList}">	
										<tr id="journeyRow<%=i %>">
											<td >
												<input type="text" readonly="readonly" id="journeyDate<%=i %>" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdReqJourneyList.departureDate}"/>" style="height:16px;width:65px;font-size: 9px;font-weight: bold;"  id="journeyDate<%=i %>" onfocus ="javascript:Calendar.setup({inputField :'journeyDate<%=i %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkJourneyDate('journeyDate<%=i %>');" />
											</td>
											<td ><input type="text" name="startTime" readonly=readonly class="startTime" id="startTime<%=i %>" style="width:37px" onfocus="javascript:timePicker('startTime');"/></td>
											<td >
											<select name="cityName" id="startStation<%=i %>"  style="width:50px;" onchange="javascript:enableFromCityClass(<%=i %>);">
												<option value="Select">Select</option>
												<c:forEach var="cityTypeList" items="${sessionScope.cityTypeList}">
												<option value="${cityTypeList.cityName}">${cityTypeList.cityName}</option>
												</c:forEach>
												<option value="Other">Other</option>
											</select>
											<input type="text" id="otherStartStation<%=i %>" style="display:none" size="7px" />
											</td>
											<td >
												<input type="text" id="journeyEndDate<%=i %>" readonly="readonly" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdReqJourneyList.arrivalDate}"/>" style="height:16px;width:65px;font-size: 9px;font-weight: bold;"  id="journeyEndDate<%=i %>" onfocus ="javascript:Calendar.setup({inputField :'journeyEndDate<%=i %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'journeyEndDate<%=i %>',step : 1});" onchange="javascript:checkArrivalDate('journeyEndDate<%=i %>');"/>
											</td>
											<td ><input type="text" id="endTime<%=i %>" readonly=readonly class = "endTime" name = "endTime" style="width:37px" onfocus="javascript:timePicker('endTime')" /></td>
											<td >
											<select name="cityName" id="endStation<%=i %>"  style="width:50px;" onchange="javascript:enableToCityClass(<%=i %>);">
												<option value="Select">Select</option>
												<c:forEach var="cityTypeList" items="${sessionScope.cityTypeList}">
												<option value="${cityTypeList.cityName}">${cityTypeList.cityName}</option>
												</c:forEach>
												<option value="Other">Other</option>
											</select>
									        <input type="text" id="otherEndStation<%=i %>" style="display:none" size="7px" maxlength="20" />
											</td>
											<td ><input type="text" id="distanceJourney<%=i %>" style="width:60px" onkeypress="javascript:return checkFloat(event,'distanceJourney<%=i %>');"/></td>
											<td >
											<select name="travelType" id="modeOfTravel<%=i %>" style="width:70px;" onchange="javascript:enableToCityClass(<%=i %>);">
												<option value="Select">Select</option>
												<c:forEach var="travelTypeList" items="${sessionScope.travelTypeList}">
												<option value="${travelTypeList.travelType}">${travelTypeList.travelType}</option>
												</c:forEach>
											</select>
											</td>
											<td ><input type="text" id="ticketFare<%=i %>" value="${tdReqJourneyList.ticketFare}" style="width:50px" onkeypress="javascript:return checkFloat(event,'ticketFare<%=i %>');" onchange="javascript:totalAdjustment(<%=i %>);"/></td>
											<td ><input type="text" id="totalJourneyAmount<%=i %>" value="0" style="width:50px" onchange="javascript:totalAdjustment(<%=i %>);checkCancellationTotal();" onkeypress="return checkFloat(event,'totalJourneyAmount<%=i %>');"/></td>
											<td ><input type="text" id="ticketNo<%=i %>" style="width:50px" /></td>
											<td ><input type="button" id="add0" value="+" onclick="javascript:checkJourneyRow(<%=i %>);"/></td>
											<td ><input type="button" id="del0" value="-" onclick="javascript:deleteJourneyDetailsRow(this,'journeyDetailsId');" /></td>
											
										</tr>
									<% i++; %>
									</c:forEach>
									</c:if>
									</table>		
								</div>
								
								<c:if test="${tada.tadaApprovalRequestDTO.stayDuration!=0 && tada.tadaApprovalRequestDTO.status!=6}">
								<div class="line" id="DAReqDiv">
										<div class="quarter bold">DA Requirement</div>
										<c:if test="${tada.tdDaNewDetailsList!=null || tada.tdRMAKmList!=null}">
										    <div class="quarter" id="tadaRequirementId" style="display:none">
										    
											</div>
										</c:if>
										<c:if test="${tada.tdDaNewDetailsList==null || tada.tdRMAKmList==null}">
										    <div class="quarter">
											    <form:radiobutton path="tadaRequirement" id="tadaRequirement" value="oldDA" onclick="javascript:checkJourneyTable();totalAdjustment(0);"/><label>Old DA</label>
											    <form:radiobutton path="tadaRequirement" id="tadaRequirement" value="newDA" onclick="javascript:checkCancellationTotal();"/><label>New DA</label>
											</div>
											  <!-- <div class="quarter bold">DA Percentage</div> --><div><input type="hidden" id ="tadaDaPercentage" value="${tada.tadaDaPercentage}"/></div>
										</c:if>
								</div>
								</c:if>
								
								<div id="daId" class="line" style="display:none">
										<div class="quarter bold">Whether DA is required with hotel or normal rates<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:radiobutton path="daOldRequirement" id="daOldRequirement" value="hotelRate" onclick="javascript:enableDaDetails();totalAdjustment(0);"/><label>Hotel Rate</label>
											<form:radiobutton path="daOldRequirement" id="daOldRequirement" value="normalRate" onclick="javascript:enableDaDetails();totalAdjustment(0);"/><label>Normal Rate</label>
										</div>
								</div>
								
								
								
								<div id="daNewDetailsID" class="line" style="display:none">
								<div class="quarter bold">DA for Journey/Halt</div>
								<table class="fortable" width="100%" id="daNewDetailsId">
								<tr>
									<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
									<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
								</tr>	
									<tr>
										<td rowspan="3" class="tabletd" align="center" >Date</td>
										<td rowspan="3" class="tabletd" align="center" >Stay Duration in Hrs.</td>
										<td rowspan="3" class="tabletd" align="center" >Amount for Food in <font size="4.5em"><span class="WebRupee" >R</span></font></td>
										<td rowspan="3" class="tabletd" align="center" >Amount for Accommodation in <font size="4.5em"><span class="WebRupee" >R</span></font></td>
										<td rowspan="3" class="tabletd" align="center" >Total Amount in <font size="4.5em"><span class="WebRupee" >R</span></font></td>
										<td rowspan="3" class="tabletd" >Add</td>
										<td rowspan="3" class="tabletd" >Del</td>
									</tr>
									<tr>
										
									</tr>
									<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
									<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
									</tr>
									<c:if test="${tada.tdDaNewDetailsList!=null}">
									<c:forEach var="tdDaNewDetailsList" items="${tada.tdDaNewDetailsList}">
				                    <tr id="daNewRow0">
					                  <td ><input type="text" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaNewDetailsList.daNewDate}"/>" readonly="readonly" id="date0" style="height:16px;width:100px;font-size: 9px;font-weight: bold;"  id="date0" onfocus ="javascript:Calendar.setup({inputField :'date0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkDates('date0');"/></td>
					                  <td ><input type="text" value="${tdDaNewDetailsList.stayDuration}" id="stayDuration0" style="width:100px" onkeypress="javascript:return checkFloat(event,'stayDuration0');"/></td>
									  <td ><input type="text" value="${tdDaNewDetailsList.foodAmount}" id="food0" style="width:200px" onkeypress="javascript:return checkFloat(event,'accommodation0');" onchange="javascript:getDaNewAmount(0); totalAdjustment(0);checkDaNewAmount(0);" /></td>
									  <td ><input type="text" value="${tdDaNewDetailsList.accommodationAmount}" id="accommodation0" style="width:180px" onkeypress="javascript:return checkInt(event);" onchange="javascript:getDaNewAmount(0); totalAdjustment(0);checkDaNewAmount(0)" /></td>
									  <td ><input type="text" value="${tdDaNewDetailsList.totalAmount}" readonly="readonly" id="amount0" style="width:180px" /></td>
									  <td ><input type="button" id="newDaAdd0" class="smallbtn" value="+" onclick="javascript:funcreatenewNewDaRow('daNewDetailsId')"/></td>
									  <td ><input type="button" id="newDaDel0" class="smallbtn" value="-" style="display: none" /></td>
									</tr>
									</c:forEach>
								    </c:if>
								    <c:if test="${tada.tdDaNewDetailsList==null || tada.tdDaNewDetailsList=='[]'}">
									    <tr id="daNewRow0">
											<td >
												<input type="text" readonly="readonly" id="date0" style="height:16px;width:100px;font-size: 9px;font-weight: bold;"  id="date0" onfocus ="javascript:Calendar.setup({inputField :'date0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkDates('date0');"/>
											</td>
											<td ><input type="text" id="stayDuration0" style="width:100px" onkeypress="javascript:return checkFloat(event,'stayDuration0');"/></td>
											<td ><input type="text" id="food0" style="width:200px" onkeypress="javascript:return checkFloat(event,'food0');" onchange="javascript:getDaNewAmount(0); totalAdjustment(0);checkDaNewAmount(0);" /></td>
											<td ><input type="text" id="accommodation0" style="width:180px" onkeypress="javascript:return checkFloat(event,'accommodation0');" onchange="javascript:getDaNewAmount(0); totalAdjustment(0);checkDaNewAmount(0)" /></td>
											<td ><input type="text" readonly="readonly" id="amount0" style="width:180px" /></td>
											<td ><input type="button" id="newDaAdd0" class="smallbtn" value="+" onclick="javascript:funcreatenewNewDaRow('daNewDetailsId')"/></td>
											<td ><input type="button" id="newDaDel0" class="smallbtn" value="-" style="display: none" /></td>
											
										</tr>
									</c:if>
									</table>		
								</div>
								
								
								<div id="daNewAccDetailsID" class="line" style="display:none">
								<div class="half"><b>Accommodation Details <font color="red">[Enter Bill Wise]</font></b></div>
								<table style="width:100%" border="1" class="list" id="daNewAccDetailsId">
									
									<tr>
										<th style="width:20%;text-align:center">From Date</th>
										<th></th>
										<th style="width:20%;text-align:center">To Date</th>
										<th></th>
										<th style="width:30%;text-align:center">Amount for Accommodation in <font size="4.5em"><span class="WebRupee" >R</span></font></th>
										<th style="width:20%;text-align:center">Claimed Amount in <font size="4.5em"><span class="WebRupee" >R</span></font></th>
										<th style="width:5%;text-align:center">Add</th>
										<th style="width:5%;text-align:center">Del</th>
									</tr>
									
									<c:if test="${tada.tdDaNewDetailsList!=null}">
									<c:forEach var="tdDaNewDetailsList" items="${tada.tdDaNewDetailsList}">
				                    <tr id="daNewAccRow0">
					                  <td ><input type="text" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaNewDetailsList.daNewDate}"/>" readonly="readonly" id="date0" style="height:16px;width:100px;font-size: 9px;font-weight: bold;"  id="date0" onfocus ="javascript:Calendar.setup({inputField :'date0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkDates('date0');" /></td>
					                  <td ><input type="text" value="${tdDaNewDetailsList.stayDuration}" id="stayDuration0" style="width:100px" onkeypress="javascript:return checkFloat(event,'stayDuration0');"/></td>
									  <td ><input type="text" value="${tdDaNewDetailsList.foodAmount}" id="food0" style="width:200px" onkeypress="javascript:return checkFloat(event,'accommodation0');" onchange="javascript:getDaNewAmount(0); totalAdjustment(0);checkDaNewAmount(0);" /></td>
									  <td ><input type="text" value="${tdDaNewDetailsList.accommodationAmount}" id="accommodation0" style="width:180px" onkeypress="javascript:return checkInt(event);" onchange="javascript:getDaNewAmount(0); totalAdjustment(0);checkDaNewAmount(0)" /></td>
									  <td ><input type="text" value="${tdDaNewDetailsList.totalAmount}" readonly="readonly" id="amount0" style="width:180px" /></td>
									  <td ><input type="button" id="newDaAdd0" class="smallbtn" value="+" onclick="javascript:funcreatenewNewDaRow('daNewDetailsId')"/></td>
									  <td ><input type="button" id="newDaDel0" class="smallbtn" value="-" style="display: none" /></td>
									</tr>
									</c:forEach>
								    </c:if>
								    <c:if test="${tada.tdDaNewDetailsList==null || tada.tdDaNewDetailsList=='[]'}">
									    <tr id="daNewAccRow0">
											<td >
												<input type="text" readonly="readonly" id="fromDateAcc0" style="height:16px;width:170px;font-size: 9px;font-weight: bold;"  id="fromDateAcc0" onfocus ="javascript:Calendar.setup({inputField :'fromDateAcc0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkJourneyDate('fromDateAcc0');clearAccAmt(0);"/>
											</td>
											<td ></td>
											<td >
												<input type="text" readonly="readonly" id="toDateAcc0" style="height:16px;width:170px;font-size: 9px;font-weight: bold;"  id="toDateAcc0" onfocus ="javascript:Calendar.setup({inputField :'toDateAcc0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:clearAccAmt(0);checkFromToAccDate(0);checkNewDate(this);" />
											</td>
											<td ></td>
											<td ><input type="text" id="accAmount0" style="width:260px" onkeypress="javascript:return checkFloat(event,'accAmount0');" onchange="javascript:enableAccClaimAmount();totalAdjustment(0);" /></td>
											<td ><input type="text" readonly="readonly" id="totalAccAmount0" style="width:180px" /></td>
											<td ><input type="button" id="newDaAccAdd0" value="+" onclick="javascript:checkDaNewAccRow(0);"/></td>
											<td ><input type="button" id="newDaAccDel0" value="-" style="display: none" /></td>
											
										</tr>
									</c:if>
									</table>		
								</div>
								
								<div id="daNewFoodDetailsID" class="line" style="display:none">
								<div class="half"><b>Food Details <font color="red">[Enter Bill Wise]</font></b></div>
								<table style="width:100%" border="1" class="list" id="daNewFoodDetailsId">
									
									<tr>
										<th style="width:20%;text-align:center">From Date</th>
										<th style="width:20%;text-align:center" >To Date</th>
										<th style="width:30%;text-align:center" >Amount for Food in <font size="4.5em"><span class="WebRupee" >R</span></font></th>
										<th style="width:20%;text-align:center" >Claimed Amount in <font size="4.5em"><span class="WebRupee" >R</span></font></th>
										<th style="width:5%;text-align:center" >Add</th>
										<th style="width:5%;text-align:center" >Del</th>
									</tr>
									
									<c:if test="${tada.tdDaNewDetailsList!=null}">
									<c:forEach var="tdDaNewDetailsList" items="${tada.tdDaNewDetailsList}">
				                    <tr id="daNewFoodRow0">
					                  <td ><input type="text" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaNewDetailsList.daNewDate}"/>" readonly="readonly" id="date0" style="height:16px;width:100px;font-size: 9px;font-weight: bold;"  id="date0" onfocus ="javascript:Calendar.setup({inputField :'date0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkDates('date0');"/></td>
					                  <td ><input type="text" value="${tdDaNewDetailsList.stayDuration}" id="stayDuration0" style="width:100px" onkeypress="javascript:return checkFloat(event,'stayDuration0');"/></td>
									  <td ><input type="text" value="${tdDaNewDetailsList.foodAmount}" id="food0" style="width:200px" onkeypress="javascript:return checkFloat(event,'accommodation0');" onchange="javascript:getDaNewAmount(0); totalAdjustment(0);checkDaNewAmount(0);" /></td>
									  <td ><input type="text" value="${tdDaNewDetailsList.accommodationAmount}" id="accommodation0" style="width:180px" onkeypress="javascript:return checkInt(event);" onchange="javascript:getDaNewAmount(0); totalAdjustment(0);checkDaNewAmount(0)" /></td>
									  <td ><input type="text" value="${tdDaNewDetailsList.totalAmount}" readonly="readonly" id="amount0" style="width:180px" /></td>
									  <td ><input type="button" id="newDaAdd0"  value="+" onclick="javascript:funcreatenewNewDaRow('daNewDetailsId')"/></td>
									  <td ><input type="button" id="newDaDel0"  value="-" style="display: none" /></td>
									</tr>
									</c:forEach>
								    </c:if>
								    <c:if test="${tada.tdDaNewDetailsList==null || tada.tdDaNewDetailsList=='[]'}">
									    <tr id="daNewFoodRow0">
											<td >
												<input type="text" readonly="readonly" id="fromDateFood0" style="height:16px;width:180px;font-size: 9px;font-weight: bold;"  id="fromDateFood0" onfocus ="javascript:Calendar.setup({inputField :'fromDateFood0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkJourneyDate('fromDateFood0');clearFoodAmt(0);"/>
											</td>
											<td >
												<input type="text" readonly="readonly" id="toDateFood0" style="height:16px;width:180px;font-size: 9px;font-weight: bold;"  id="toDateFood0" onfocus ="javascript:Calendar.setup({inputField :'toDateFood0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkFromToFoodDate(0);clearFoodAmt(0);checkNewDate(this);"/>
											</td>
											<td ><input type="text" id="foodAmount0" style="width:270px" onkeypress="javascript:return checkFloat(event,'foodAmount0');" onchange="javascript:enableFoodAmtAftCal(0);totalAdjustment(0);" /></td>
											<td ><input type="text" readonly="readonly" id="totalFoodAmount0" style="width:180px" /></td>
											<td ><input type="button" id="newDaFoodAdd0"  value="+" onclick="javascript:checkDaNewFoodRow(0);"/></td>
											<td ><input type="button" id="newDaFoodDel0"  value="-" style="display: none" /></td>
										</tr>
									</c:if>
									</table>		
								</div>
								
								<div id="result9">
								<c:if test="${tada.tadaTdJdaList!=null && tada.tadaTdJdaList!='[]' && tada.tadaTdJdaList!=''}">
								    <jsp:include page="TadaTdSettlementOldDa.jsp"></jsp:include>
								</c:if>
								</div>
								
								
								<div id="daNewRMAKmID" class="line" style="display:none">
								<div class="quarter bold">Daily RMA at TD Details<input type="checkbox" id="rmaKmId" onclick="javascript:showKmRMAGrid();"/></div>
								<table class="list" style="width:100%;display: none" border="1" id="daNewRMAKmId">
									
									<tr>
										<th style="width:15%;text-align:center">Date</th>
										<th style="width:15%;text-align:center">From Place</th>
										<th style="width:15%;text-align:center">To Place</th>
										<th style="width:15%;text-align:center" id="travelBy">Travel By</th>
										<th style="width:10%;text-align:center" id="distance">Distance (in k.m)</th>
										<th style="width:10%;text-align:center" id="amountPerKm">Amount (per k.m)</th>
										<th style="width:10%;text-align:center" id="totalAmount">Total Amount Claimed in <font size="4.5em"><span class="WebRupee" >R</span></font></th>
										<th style="width:5%;text-align:center">Add</th>
										<th style="width:5%;text-align:center">Del</th>
									</tr>
									
									<c:if test="${tada.tdDaNewDetailsList!=null}">
									<c:forEach var="tdRMAKmList" items="${tada.tdRMAKmList}">
				                    <tr id="daNewRMAKmRow0">
					                        <td ><input type="text" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdRMAKmList.dateRMAKm}"/>" readonly="readonly" id="dateRMAKm0" style="height:16px;width:70px;font-size: 9px;font-weight: bold;"  id="dateRMAKm0" onfocus ="javascript:Calendar.setup({inputField :'dateRMAKm0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkJourneyDate('dateRMAKm0');checkDates('dateRMAKm0');"/></td>
											<td ><input type="text" value="${tdRMAKmList.fromPlace}" id="fromPlace0" style="width:110px" onkeypress="javascript:return isAlphaNumaricExp(event);"/></td>
											<td ><input type="text" value="${tdRMAKmList.toPlace}" id="toPlace0" style="width:110px" onkeypress="javascript:return isAlphaNumaricExp(event);"/></td>
											<td id="travelByA">
											<form:select path="travelBy" id="travelBy0"  multiple="" cssStyle="width:100px;" onkeypress="javascript:return isAlphaNumaricExp(event);" onchange="javascript:checkRMATravelType(0);">
												<form:option value="${tdRMAKmList.travelBy}" label="${tdRMAKmList.travelBy}"></form:option>
												<form:option value="Select" label="Select"></form:option>
												<form:option value="AC Taxi" label="AC Taxi"></form:option>
												<form:option value="Non AC Taxi" label="Non AC Taxi"></form:option>
												<form:option value="Ordinary Taxi" label="Ordinary Taxi"></form:option>
												<form:option value="OwnTransport" label="Own Transport"></form:option>
												<form:option value="Other" label="Other"></form:option>
											</form:select>
											</td>
											<td id="distanceA"><input type="text" value="${tdRMAKmList.distance}" id="distance0" style="width:100px" onkeypress="javascript:return checkFloat(event,'distance0');" onchange="javascript:getDaNewRMAKmAmount(0); totalAdjustment(0);" /></td>
											<td id="amountPerKmA"><input type="text" value="${tdRMAKmList.amountPerKm}" id="amountPerKm0" style="width:100px" onkeypress="javascript:return checkFloat(event,'amountPerKm0');" onchange="javascript:getDaNewRMAKmAmount(0); totalAdjustment(0)" /></td>
											<td><input type="text" readonly="readonly" value="${tdRMAKmList.totalRMAKmAmount}" id="totalRMAKmAmount0" style="width:180px"/></td>
											<td ><input type="button" id="newDaRMAAdd0"  value="+" onclick="javascript:funcreateNewRMAKmRow('daNewRMAKmId')"/></td>
											<td ><input type="button" id="newDaRMADel0"  value="-" style="display: none" /></td>
									</tr>
									</c:forEach>
								    </c:if>
									<c:if test="${tada.tdDaNewDetailsList==null || tada.tdDaNewDetailsList=='[]'}">	
										<tr id="daNewRMAKmRow0">
											<td >
												<input type="text" readonly="readonly" id="dateRMAKm0" style="height:16px;width:70px;font-size: 9px;font-weight: bold;"  id="dateRMAKm0" onfocus ="javascript:Calendar.setup({inputField :'dateRMAKm0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkJourneyDate('dateRMAKm0');"/>
											</td>
											
											<td ><input type="text" id="fromPlace0" style="width:110px" onkeypress="javascript:return isAlphaNumaricExp(event);"/></td>
											<td ><input type="text" id="toPlace0" style="width:110px" onkeypress="javascript:return isAlphaNumaricExp(event)"/></td>
											<td id="travelByA">
											<form:select path="travelBy" id="travelBy0"  multiple="" cssStyle="width:100px;" onkeypress="javascript:return checkInt(event);" >
												<form:option value="Select" label="Select"></form:option>
												<form:option value="AC Taxi" label="AC Taxi"></form:option>
												<form:option value="Non AC Taxi" label="Non AC Taxi"></form:option>
												<form:option value="Ordinary Taxi" label="Ordinary Taxi"></form:option>
												<form:option value="Other" label="Other"></form:option>
											</form:select>
											</td>
											<td id="distanceA"><input type="text" id="distance0" style="width:100px" onkeypress="javascript:return checkFloat(event,'distance0');" onchange="javascript:getDaNewRMAKmAmount(0); totalAdjustment(0);" /></td>
											<td id="amountPerKmA"><input type="text" id="amountPerKm0" style="width:100px" onkeypress="javascript:return checkFloat(event,'amountPerKm0');" onchange="javascript:getDaNewRMAKmAmount(0); totalAdjustment(0)" /></td>
											<td><input type="text" readonly="readonly" id="totalRMAKmAmount0" style="width:180px"/></td>
											<td ><input type="button" id="newDaRMAAdd0"  value="+" onclick="javascript:checkRMAKmRow();"/></td>
											<td ><input type="button" id="newDaRMADel0"  value="-" style="display: none" /></td>
											
										</tr>
									</c:if>
									</table>		
								</div>
								
								
								<div id="daNewRMADayID" class="line" style="display:none">
								<div class="quarter bold">Daily RMA at TD Details<input type="checkbox" id="rmaDayId" onclick="javascript:showDayRMAGrid();"/></div>
								<table style="width:100%;display: none" border="1" class="list"  id="daNewRMADayId">
								
								    <tr>
										<th style="width:15%;text-align:center">Date</th>
										<th style="width:15%;text-align:center">From Place</th>
										<th style="width:15%;text-align:center">To Place</th>
										<th style="width:15%;text-align:center" id="amountPerDay">Amount (per day)</th>
										<th style="width:15%;text-align:center" id="amountPerDay">Calculated RMA Amount</th>
										<th style="width:15%;text-align:center" id="totalAmount">Claimed Amount in <font size="4.5em"><span class="WebRupee" >R</span></font></th>
										<th style="width:5%;text-align:center">Add</th>
										<th style="width:5%;text-align:center">Del</th>
									</tr>
									
									<c:if test="${tada.tdDaNewDetailsList!=null}">
									<c:forEach var="tdRMADayList" items="${tada.tdRMADayList}">
				                    <tr id="daNewRMADayRow0">
					                        <td ><input type="text" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdRMADayList.dateRMADay}"/>" readonly="readonly" id="dateRMADay0" style="height:16px;width:70px;font-size: 9px;font-weight: bold;"  id="dateRMADay0" onfocus ="javascript:Calendar.setup({inputField :'dateRMADay0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkDates('dateRMADay0');"/></td>
											<td ><input type="text" value="${tdRMADayList.fromPlace}" id="fromPlace0" style="width:110px" onkeypress="javascript:return isAlphaNumaricExp(event);"/></td>
											<td ><input type="text" value="${tdRMADayList.toPlace}" id="toPlace0" style="width:110px" onkeypress="javascript:return isAlphaNumaricExp(event)"/></td>
											<td id="amountPerDayA"><input type="text" value="${tdRMADayList.amountPerDay}" id="amountPerDay0" style="width:100px" onkeypress="javascript:return checkFloat(event,'amountPerDay0');" onchange="javascript:getDaNewRMAKmAmount(0); totalAdjustment(0)" /></td>
											<td><input type="text" readonly="readonly" value="${tdRMADayList.totalRMADayAmount}" id="totalAmount0" style="width:180px"/></td>
											<td ><input type="button" id="newDaRMAAdd0" class="smallbtn" value="+" onclick="javascript:funcreateNewRMADayRow('daNewRMADayId')"/></td>
											<td ><input type="button" id="newDaRMADel0" class="smallbtn" value="-" style="display: none" /></td>
									</tr>
									</c:forEach>
								    </c:if>
								
									<c:if test="${tada.tdDaNewDetailsList==null || tada.tdDaNewDetailsList=='[]'}">
										<tr id="daNewRMADayRow0">
											<td >
												<input type="text" readonly="readonly" id="dateRMADay0" style="height:16px;width:70px;font-size: 9px;font-weight: bold;"  id="dateRMADay0" onfocus ="javascript:Calendar.setup({inputField :'dateRMADay0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkJourneyDate('dateRMADay0');"/>
											</td>
											
											<td ><input type="text" id="fromPlaceRMADay0" style="width:110px"/></td>
											<td ><input type="text" id="toPlaceRMADay0" style="width:110px"/></td>
											<td id="amountPerDayA"><input type="text" id="amountPerDay0" style="width:100px" onkeypress="javascript:return checkFloat(event,'amountPerDay0');" onchange="javascript:enableRMADayAmtAftCal(0);totalAdjustment(0)"/></td>
											<td><input type="text" readonly="readonly" id="amountPerDayAftCal0" style="width:170px"/></td>
											<td><input type="text" readonly="readonly" id="totalAmount0" style="width:130px"/></td>
											<td ><input type="button" id="newDaRMAAdd0"  value="+" onclick="javascript:checkRMADayRow(0);"/></td>
											<td ><input type="button" id="newDaRMADel0"  value="-" style="display: none" /></td>
										</tr>
									</c:if>
									</table>		
								</div>
								
								<div id="daNewRMADailyID" class="line" style="display:none">
								<div class="quarter bold">RMA at TD Details<input type="checkbox" id="rmaDailyId" onclick="javascript:showDailyRMAGrid();"/></div>
								<table class="list" style="width:100%;display: none" border="1" id="daNewRMADailyId">
									
									<tr>
										<th style="width:15%;text-align:center">Date</th>
										<th style="width:15%;text-align:center">From Place</th>
										<th style="width:15%;text-align:center">To Place</th>
										<th style="width:15%;text-align:center" id="travelBy">Travel By</th>
										<th style="width:10%;text-align:center" id="distance">Distance (in k.m)</th>
										<th style="width:10%;text-align:center" id="amountPerKm">Amount (per k.m)</th>
										<th style="width:10%;text-align:center" id="totalAmount">Total Amount Claimed in <font size="4.5em"><span class="WebRupee" >R</span></font></th>
										<th style="width:5%;text-align:center">Add</th>
										<th style="width:5%;text-align:center">Del</th>
									</tr>
									<c:if test="${tada.tdDaNewDetailsList==null || tada.tdDaNewDetailsList=='[]'}">	
										<tr id="daNewRMADailyRow0">
											<td >
												<input type="text" readonly="readonly" id="dateRMADaily0" style="height:16px;width:70px;font-size: 9px;font-weight: bold;"  id="dateRMADaily0" onfocus ="javascript:Calendar.setup({inputField :'dateRMADaily0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkNewDate(this);"/>
											</td>
											
											<td ><input type="text" id="fromPlaceDaily0" style="width:110px" onkeypress="javascript:return isAlphaNumaricExp(event);"/></td>
											<td ><input type="text" id="toPlaceDaily0" style="width:110px" onkeypress="javascript:return isAlphaNumaricExp(event)"/></td>
											<td id="travelByA">
											<form:select path="travelBy" id="travelByDaily0"  multiple="" cssStyle="width:100px;" onkeypress="javascript:return checkInt(event);" >
												<form:option value="Select" label="Select"></form:option>
												<form:option value="AC Taxi" label="AC Taxi"></form:option>
												<form:option value="Non AC Taxi" label="Non AC Taxi"></form:option>
												<form:option value="Ordinary Taxi" label="Ordinary Taxi"></form:option>
												<form:option value="Other" label="Other"></form:option>
											</form:select>
											</td>
											<td id="distanceADaily"><input type="text" id="distanceDaily0" style="width:100px" onkeypress="javascript:return checkFloat(event,'distanceDaily0');" onchange="javascript:getDaNewRMADailyAmount(0); totalAdjustment(0);" /></td>
											<td id="amountPerKmADaily"><input type="text" id="amountPerKmDaily0" style="width:100px" onkeypress="javascript:return checkFloat(event,'amountPerKmDaily0');" onchange="javascript:getDaNewRMADailyAmount(0); totalAdjustment(0)" /></td>
											<td><input type="text" readonly="readonly" id="totalRMAKmAmountDaily0" style="width:180px"/></td>
											<td ><input type="button" id="newDaRMADailyAdd0"  value="+" onclick="javascript:checkRMADailyRow();"/></td>
											<td ><input type="button" id="newDaRMADailyDel0"  value="-" style="display: none" /></td>
											
										</tr>
									</c:if>
									</table>		
								</div>
								
								
								
								<div id="daNewRMALocalID" class="line" style="display:none">
								<div class="quarter bold">Local RMA Details<input type="checkbox" id="rmaId" onclick="javascript:showLocalRMAGrid();"/></div>
								<table style="width:100%;display: none" border="1" class="list" id="daNewRMALocalId">
								
								    <tr>
										<th style="width:10%;text-align:center">Date</th>
										<th style="width:10%;text-align:center">From Place</th>
										<th style="width:10%;text-align:center" id="residencePlaceFromLabel">Residence Place</th>
										<th style="width:10%;text-align:center" id="otherPlaceFromLabel">Other From Place</th>
										<th style="width:10%;text-align:center">To Place</th>
										<th style="width:10%;text-align:center" id="residencePlaceToLabel">Residence Place</th>
										<th style="width:10%;text-align:center" id="otherPlaceToLabel">Other To Place</th>
										<th style="width:10%;text-align:center">Mode Of Conveyance</th>
										<th style="width:10%;text-align:center">Distance in k.m</th>
										<th style="width:10%;text-align:center">Amount (per k.m)</th>
										<th style="width:10%;text-align:center">Amount Claimed in <font size="4.5em"><span class="WebRupee" >R</span></font></th>
										<th style="width:5%;text-align:center">Add</th>
										<th style="width:5%;text-align:center">Del</th>
									</tr>
									
									<c:if test="${tada.tdDaNewDetailsList!=null}">
									<c:forEach var="tdRMADayList" items="${tada.tdRMADayList}">
				                    <tr id="daNewRMALocalRow0">
					                        <td ><input type="text" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdRMADayList.dateRMADay}"/>" readonly="readonly" id="dateRMADay0" style="height:16px;width:70px;font-size: 9px;font-weight: bold;"  id="dateRMADay0" onfocus ="javascript:Calendar.setup({inputField :'dateRMADay0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkNewDate(this);;"/></td>
											<td ><input type="text" value="${tdRMADayList.fromPlace}" id="fromPlace0" style="width:110px" onkeypress="javascript:return isAlphaNumaricExp(event);"/></td>
											<td ><input type="text" value="${tdRMADayList.toPlace}" id="toPlace0" style="width:110px" onkeypress="javascript:return isAlphaNumaricExp(event)"/></td>
											<td id="amountPerDayA"><input type="text" value="${tdRMADayList.amountPerDay}" id="amountPerDay0" style="width:100px" onkeypress="javascript:return checkFloat(event,'amountPerDay0');" onchange="javascript:getDaNewRMAKmAmount(0); totalAdjustment(0)" /></td>
											<td><input type="text" readonly="readonly" value="${tdRMADayList.totalRMADayAmount}" id="totalAmount0" style="width:180px"/></td>
											<td ><input type="button" id="newDaRMAAdd0" class="smallbtn" value="+" onclick="javascript:funcreateNewRMADayRow('daNewRMADayId')"/></td>
											<td ><input type="button" id="newDaRMADel0" class="smallbtn" value="-" style="display: none" /></td>
									</tr>
									</c:forEach>
								    </c:if>
								
									<c:if test="${tada.tdDaNewDetailsList==null || tada.tdDaNewDetailsList=='[]'}">
										<tr id="daNewRMALocalRow0">
											<td >
												<input type="text" readonly="readonly" id="dateRMALocal0" style="height:16px;width:60px;font-size: 9px;font-weight: bold;"  id="dateRMALocal0" onfocus ="javascript:Calendar.setup({inputField :'dateRMALocal0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkNewDate(this);;"/>
											</td>
											
											<td >
											<form:select path="fromPlaceLocal" id="fromPlaceLocal0"  multiple="" cssStyle="width:85px;" onchange="javascript:checkFromToLocalPlace(0);checkLocalPlace(0);">
												<form:option value="Select" label="Select"></form:option>
												<form:option value="ASL/DRDL" label="ASL/DRDL"></form:option>
												<form:option value="RCI/CPDC" label="RCI/CPDC"></form:option>
												<form:option value="Begumpet Airport" label="Begumpet Airport"></form:option>
												<form:option value="Secunderabad Railway Station" label="Secunderabad Railway Station"></form:option>
												<form:option value="Hyderabad Railway Station" label="Hyderabad Railway Station"></form:option>
												<form:option value="MGBS" label="MGBS"></form:option>
												<form:option value="JBS" label="JBS"></form:option>
												<form:option value="Kachiguda" label="Kachiguda"></form:option>
												<form:option value="Shamshabad Airport" label="Shamshabad Airport"></form:option>
												<form:option value="Residence" label="Residence"></form:option>
												<form:option value="Other" label="Other"></form:option>
											</form:select>
											</td>
											<td id="residencePlaceFromTd">
											   <input type="text" id="residencePlaceFrom0" style="width:70px;display:none" onkeypress="javascript:return isAlphaNumaricExp(event);"/>
											</td>
											<td id="otherPlaceFromTd">
											   <input type="text" id="otherPlaceFrom0" style="width:60px;display:none" onkeypress="javascript:return isAlphaNumaricExp(event)"/>
											</td>
											<td >
											<form:select path="toPlaceLocal" id="toPlaceLocal0"  multiple="" cssStyle="width:85px;" onchange="javascript:checkLocalPlace(0);enableLocalDistance(0);">
												<form:option value="Select" label="Select"></form:option>
											</form:select>
											</td>
											<td id="residencePlaceToTd">
											    <input type="text" id="residencePlaceTo0" style="width:70px;display:none" onkeypress="javascript:return isAlphaNumaricExp(event);"/>
											</td>
											<td id="otherPlaceToTd">
											    <input type="text" id="otherPlaceTo0" style="width:60px;display:none" onkeypress="javascript:return isAlphaNumaricExp(event)"/>
											</td>
											<td>
											<form:select path="conveyanceMode" id="localConveyanceMode0"  multiple="" cssStyle="width:75px;" onchange="javascript:checkLocalCmode(0);totalAdjustment(0);">
												<form:option value="Select" label="Select"></form:option>
												<form:option value="Govt Tpt." label="Govt Tpt."></form:option>
												<form:option value="Shared Taxi" label="Shared Taxi"></form:option>
												<form:option value="Non-Shared Taxi" label="Non-Shared Taxi"></form:option>
												<form:option value="Shared Auto " label="Shared Auto"></form:option>
												<form:option value="Non-Shared Auto" label="Non-Shared Auto"></form:option>
												<form:option value="Bus" label="Bus"></form:option>
												<form:option value="OwnTransport" label="Own Transport"></form:option>
											</form:select>
											</td>
											<td id="distanceLocalTd0"><input type="text" id="distanceLocal0" style="width:50px" onkeypress="javascript:return checkFloat(event,'distanceLocal0');" onchange="javascript:getRMALocalAmount(0);totalAdjustment(0)"/></td>
											<td id="amountPerKmLocalTd0"><input type="text" id="amountPerKmLocal0" style="width:50px" onkeypress="javascript:return checkFloat(event,'amountPerKmLocal0');" onchange="javascript:getRMALocalAmount(0);totalAdjustment(0)"/></td>
											<td id="totalAmountLocalTd"><input type="text" readonly="readonly" id="totalAmountLocal0" style="width:60px"/></td>
											<td ><input type="button" id="newDaRMALocalAdd0"  value="+" onclick="javascript:checkRMALocalRow();"/></td>
											<td ><input type="button" id="newDaRMALocalDel0"  value="-" style="display: none" /></td>
										</tr>
									</c:if>
									</table>		
								</div>
								<div class="line">
										<div class="quarter bold">User Remarks</div>
										<div>										
											<form:textarea path="userRemarks" cols="75" rows="4" id="userRemarks"  onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"/>
											<input type="text" class="counter" name="counter" value="500" id="counter" disabled="disabled"/>										
										</div>
									</div>
								
								<div class="line">
									<div class="quarter">Total Amount<font size="4.5em"><span class="WebRupee" >R</span></font>:</div>
									<div class="quarter bold" id="totAmt">&nbsp;</div>
									
								</div>
								<c:if test="${tada.type=='tadaTdSettlement'}">
								<div class="line" id="settlementDiv">
										<div class="quarter">Advance Drawn</div>
										<div class="quarter" id="advAmount"><font size="4.5em"><span class="WebRupee" >R</span></font>${tada.tadaApprovalRequestDTO.cdaAmount}</div>
										<div class="quarter">Settlement Amount</div>
										<div class="quarter" id="settlementAmount">&nbsp;</div>
								</div>	
								</c:if>
													
								<div class="line">
									<div class="appbutton" style="margin-left:30%;"><a href="javascript:saveTadaTdSettlement('${tada.requestId}','${tada.type}','${tada.amendmentType}');" class="quarterbutton">Submit</a></div>
									<div class="appbutton"><a href="javascript:clearTadaTdSettlement();" class="quarterbutton">Clear</a></div>
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
			<form:hidden path="amendmentType"/>
			<form:hidden path="requestId"/>
			<form:hidden path="id"/>
			<form:hidden path="jsonValue"/>
			
		</form:form>
		<script>
			daNewDetailsList = <%= (net.sf.json.JSONArray)session.getAttribute("daNewDetailsList") %>;
			daOldDetailsList = <%= (net.sf.json.JSONArray)session.getAttribute("daOldDetailsList") %>;
			TadaRequestBean = <%= (net.sf.json.JSONObject)session.getAttribute("TadaRequestBean") %>;
			cityTypeList = <%= (net.sf.json.JSONArray)session.getAttribute("cityTypeListJSON") %>;
			travelTypeList = <%= (net.sf.json.JSONArray)session.getAttribute("travelTypeListJSON") %>;
			tadaRequestBean = <%= (net.sf.json.JSONObject)session.getAttribute("TadaRequestBean") %>;
			localRMAList = <%= (net.sf.json.JSONArray)session.getAttribute("localRMAList") %>;
			
			selectDAType();
			totalAdjustment(0);
			setSettValues();
			
		</script>
	</body>
</html>
<!-- End:TadaTdSettlement.jsp -->