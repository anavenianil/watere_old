<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:TadaTdAdvance.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/tada.js"></script>

<title>Tada Approval Request</title>

</head>
<!--onload function added by bkr 22/04/2016    -->
<body   onload="javascript:setFoodAmtPerDayZero();javascript:setTotalTdAdvance();"><form:form method="post" commandName="tada" id="tada" name="${sessionScope.tdRequestDetails}">
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
								<div class="headTitle">Requisition For TA/DA Advance</div>
								<%-- Content Page starts --%>
								<div class="line">
								    <div class="line" id="result">
									   <jsp:include page="RequestResult.jsp" />
									</div>
									<c:if test="${sessionScope.tdRequestDetails!=null}">
				
									<div class="line">
										<div class="quarter leftmar">SFNo.</div>
											<div class="quarter">
											    :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${tada.empDetailsList.userSfid}
											</div>
										<div class="quarter leftmar">Name & Designation</div>
										    <div class="quarter">
										        :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${tada.empDetailsList.nameInServiceBook}&nbsp;&nbsp;& ${tada.empDetailsList.designationDetails.name}
										    </div>
									</div>
									<div class="line">
									<!--added by bkr 21/04/2016 only one div for hidden purpose  -->
									<div style="display: none;">
											<div class="quarter leftmar">Basic Pay  &  Grade Pay</div>
											<div class="quarter">
											    :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font>${tada.payDetailsList.basicPay}/-&nbsp;&nbsp;& <font size="4.5em"><span class="WebRupee" >R</span></font>${tada.payDetailsList.gradePay}/-
											</div>
									</div>
										<c:forEach var="tdRequestDetails" items="${sessionScope.tdRequestDetails}">
										
										<div style="display: none"><!--added by bkr 25/04/2016 only div for hidden purpose  -->
										<div class="quarter leftmar">Authority To Move</div>
											<div class="quarter">
											    <c:if test="${tdRequestDetails.authorizedMove==1}">:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Build-up</c:if>
											    <c:if test="${tdRequestDetails.authorizedMove==2}">:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Project <c:if test="${tdRequestDetails.projectName!=null && tdRequestDetails.projectName!=''}">- ${tdRequestDetails.projectName}</c:if></c:if>
											</div>
											</div>
										</c:forEach>
									</div>
									<c:forEach var="tdRequestDetails" items="${sessionScope.tdRequestDetails}">
									<div class="line">
										    <div class="quarter leftmar">Temporary Duty Move to</div>
											<div class="quarter">
											    :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${tdRequestDetails.tdWorkPlace}
											</div>
											
											
											<!--added by bkr 26/04/2016 display pupose  -->
											<div class="quarter leftmar">Purpose</div>
											<div class="quarter">
											    :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${tdRequestDetails.purpose}
											</div>
											
											
											
											
											
											
										<div class="quarter leftmar">Stay duration</div>
											<div class="quarter">
											    :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${tdRequestDetails.stayDuration} Days
											</div>
									</div>
									<!--this div hidden by bkr 26/04/2016  -->
									<div class="line" style="display: none">
		                               <div class="quarter leftmar">DA Requirement</div>
		                               <div class="quarter">:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${tdRequestDetails.daType=='hotelrate'}">Hotel Rates</c:if>
		                                                                  <c:if test="${tdRequestDetails.daType=='normalrate'}">Normal Rates</c:if>
		                                                                  <c:if test="${tdRequestDetails.daType=='na'}">Not Required</c:if>
		                               </div>
	                                </div>
									</c:forEach>
									<c:forEach var="tdRequestDetails" items="${sessionScope.tdRequestDetails}">
									<c:if test="${tdRequestDetails.authorizedMove==2 && (tdRequestDetails.projectName==null || tdRequestDetails.projectName=='')}">
									<div class="line">
										    <div class="quarter leftmar">Choose Project</div>
											<div class="quarter">
											    :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:select path="projectName" id="projectName" cssStyle="width:145px">
											    <form:option value="Select" label="Select"></form:option>
											    <form:options items="${sessionScope.projList}" itemValue="projectName" itemLabel="projectName"></form:options>>
											    </form:select>
											</div>
									</div>
									</c:if>
									</c:forEach>
									<div class="line">
										<div class="quarter leftmar">Amount For Accommodation</div>
										<div class="full">
											:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No. Of Days:<form:input path="noOfDaysAcc" id="noOfDaysAcc" size="10px"  onkeypress="javascript:return checkFloat(event,'noOfDaysAcc');"  onkeyup="javascript:multiolyFunction();javascript:setTotalTdAdvance();"  readonly="true"/>&nbsp;*
											Amount per day:<form:input path="accAmtPerDay" id="accAmtPerDay" size="12px" onkeypress="javascript:return checkFloat(event,'accAmtPerDay');" onkeyup="javascript:multiolyFunction();javascript:setTotalTdAdvance();"/>&nbsp;=&nbsp;<form:input path="totalAccAmt" id="totalAccAmt" size="12px"  readonly="true"/>
										</div>
									</div>
									
									
									<!--this div hidden by bkr 22/04/2016   -->
									<div style="display: none;">
									<div class="line">
										<div class="quarter leftmar">Amount For Food</div>
										<div class="full">
											:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No. Of Days:<form:input path="noOfDaysFood" id="noOfDaysFood" size="10px" onkeyup="javascript:multiolyFunction();"  />&nbsp;*
											Amount per day:<form:input path="foodAmtPerDay" id="foodAmtPerDay" size="12px" onkeyup="javascript:multiolyFunction();"/>&nbsp;=&nbsp;<form:input path="totalFoodAmt" id="totalFoodAmt" size="12px" readonly="true"/>
										</div>
									</div>
									</div>
									<!--added by bkr 22/04/2016 two fields (new) taxi ,onTransit amount  -->
									<div>
										<div  class="quarter leftmar">Taxi Amount</div>
										<div >
											:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:input path="taxi" id="taxi" size="10px"   onkeyup="javascript:setTotalTdAdvance();" onkeypress="javascript:return checkFloat(event,'taxi');"  />
										</div>
									</div>
									<div>
										<div  class="quarter leftmar">OnTransit Amount</div>
										<div >
											:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:input path="onTransit" id="onTransit" size="10px"    onkeyup="javascript:setTotalTdAdvance();" onkeypress="javascript:return checkFloat(event,'onTransit');"  />
										</div>
									</div>
									
									
									<fieldset><legend><strong><font color='green'>Journey Details</font></strong></legend>
									<div class="line">
								<table style="width:100%" border="1" class="list" id="requestJourneyDetailsId">
									<tr>
										<th style="width:10%;text-align:center">Departure Date</th>
										<th style="width:25%;text-align:center">From Place</th>
										<th style="width:25%;text-align:center">To Place</th>
										<th style="width:6%;text-align:center">No. Of Days</th>
										<th style="width:7%;text-align:center">Mode of conveyance</th>
										<th style="width:10%;text-align:center">Class of Entitlement</th>
										<th style="width:7%;text-align:center">Tatkal Quota</th>
										<th style="width:10%;text-align:center">Ticket Fare<span class="mandatory">*</span></th>
									</tr>
									<c:forEach var="tdReqJourneyDetails" items="${sessionScope.tdReqJourneyDetails}">
									   <tr id="requestJourneyRow0">
											<td ><fmt:formatDate pattern="dd-MMM-yyyy" value="${tdReqJourneyDetails.departureDate}"/></td>
									        <td >${tdReqJourneyDetails.fromPlace}</td>
											<td >${tdReqJourneyDetails.toPlace}</td>
											<td >${tdReqJourneyDetails.noOfDays}</td>
											<td >${tdReqJourneyDetails.conveyanceMode}</td>
											<td >${tdReqJourneyDetails.classOfEntitlement}</td>
											<td ><c:if test="${tdReqJourneyDetails.tatkalQuota=='na'}">N/A</c:if><c:if test="${tdReqJourneyDetails.tatkalQuota=='yes'}">Required</c:if></td>
											<td >
											<c:if test="${tdReqJourneyDetails.ticketFare=='' || tdReqJourneyDetails.ticketFare==null}">
											   <input type="text" id="${tdReqJourneyDetails.id}" style="text-align: right" onkeypress="javascript:return checkFloat(event,'${tdReqJourneyDetails.id}');" onkeyup="javascript:setTotalTdAdvance();" />
											</c:if>
											<c:if test="${tdReqJourneyDetails.ticketFare!='' && tdReqJourneyDetails.ticketFare!=null}">
											   <input type="text" value="${tdReqJourneyDetails.ticketFare}" id="${tdReqJourneyDetails.id}" style="text-align: right" onkeypress="javascript:return checkFloat(event,'${tdReqJourneyDetails.id}');" onkeyup="javascript:setTotalTdAdvance();" />
											</c:if>
											</td>
										</tr>
									</c:forEach>
									</table>		
								</div>
								<div class="line">
										<div class="quarter leftmar">Total TA/DA Advance Amount</div>
										<c:forEach var="tdRequestDetails" items="${sessionScope.tdRequestDetails}">
										<c:if test="${tdRequestDetails.tadaAdvanceAmount!=null && tdRequestDetails.tadaAdvanceAmount!=0}">
											<div class="quarter" id="tadaAdvanceAmountId" style="display:none">
											    :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</div>
										</c:if>
										<c:if test="${tdRequestDetails.tadaAdvanceAmount==null || tdRequestDetails.tadaAdvanceAmount==0}">
											<div class="quarter">
											    :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font><form:input path="tadaAdvanceAmount" id="tadaAdvanceAmount" readonly="true" maxlength="30"/>
											</div>
										</c:if>
									    </c:forEach>
									</div>
								</fieldset>
									<div class="line"></div>
									<div class="line" style="margin-left: 35%;">
											<div class="expbutton">
											<a onclick="javascript:submitTdAdvance('${tada.requestId}')"> <span>Submit</span></a>
											</div>
											<div class="expbutton"><a onclick="javascript:clearTdAdvance()"> <span>Clear</span></a></div>
									</div>
									</c:if>
									<div class="line" id="individualDetails">
										<div style="colore:red"><font color='red'>Note for Individual</font></div>
										<div><font color='purple'>
										<!-- 1)Total TA/DA Advance Amount = Amount For Accommodation + Amount For Food + Ticket Fare(s). -->
										1)Total TA/DA Advance Amount = Amount For Accommodation + Amount For Taxi + Amount For ONTransit + Ticket Fare(s). <br/>
										</font></div> 
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="requestID"/>
		<form:hidden path="requestId"/>
		</form:form>	
		<script type="text/javascript">
		  travelTypeMapDetailsJSON= <%= (net.sf.json.JSONArray) session.getAttribute("travelTypeMapDetailsJSON") %>;
		  entitleClassListJSON = <%= (net.sf.json.JSONArray) session.getAttribute("entitleClassListJSON") %>;
		  taEntitleClassListJSON = <%= (net.sf.json.JSONArray) session.getAttribute("taEntitleClassListJSON") %>;
		  travelTypeListJSON = <%= (net.sf.json.JSONArray) session.getAttribute("travelTypeListJSON") %>;
		  tdRequestDetails = <%= (net.sf.json.JSONArray) session.getAttribute("tdRequestDetails") %>;
		  tadaDaNewDetails = <%= (net.sf.json.JSONObject) session.getAttribute("tadaDaNewDetails") %>;
		  tadaRequestBean = <%= (net.sf.json.JSONObject)request.getAttribute("TadaRequestBean") %>;
		  tdReqJourneyDetails = <%= (net.sf.json.JSONArray) session.getAttribute("tdReqJourneyDetailsJSON") %>;
		  clearTdAdvance();
		  calculateTdAdvance();
		  setAdvanceValue();
		</script>	
			
	</body>
</html>
<!-- End:TadaTdAdvance.jsp -->