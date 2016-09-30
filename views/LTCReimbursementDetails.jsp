<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LTCReimbursementDetails.jsp -->
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
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script type="text/javascript" src="script/jquery-ui-timepicker-addon.js"></script>
<link href="styles/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="./script/RegExpValidate.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/ltc.js"></script>
<script type="text/javascript">
	$jq(document).ready(function(){
		lables('<c:out value='${ltcApprovalRequest.type}'/>');
	});
</script>

</head>
<body onload="timePicker('endTime'); timePicker('startTime');">
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
							<div class="lightblueBg1">
								<div class="headTitle" id="headTitle"></div>
								<%-- Content Page starts --%>
								<div id="result">
								</div>
								<div class="line">
									
									<div class="line">
										<div class="quarter">LTC type</div>
										<div class="quarter">:&nbsp;${ltcApprovalRequest.ltcTypeId}</div>
										<div class="quarter">DO part no & Date</div>
										<div class="quarter">:&nbsp;${ltcApprovalRequest.doPartNo }</div>
									</div>
									<div class="line">
										<div class="quarter">Place of visit/home town</div>
										<div class="quarter">:&nbsp;${ltcApprovalRequest.placeOfVisit}</div>
										<div class="quarter">Block year</div>
										<div class="quarter">:&nbsp;${ltcApprovalRequest.ltcBlockYear}</div>
									</div>
									<div class="line">
										<div class="quarter"></div>
										<div class="quarter">
											<div id="empName"></div>	
										</div>
									</div>
									<div class="line">
										<div class="quarter">Basic pay</div>
										<div class="quarter">:&nbsp;Rs.<fmt:formatNumber value="${ltcApprovalRequest.empBean.basicPay}"/>/-</div>
										<div class="quarter">Applied date</div>
										<div class="quarter">:&nbsp;${ltcApprovalRequest.creationDate}</div>
									</div>
									<div class="line">
										<div class="quarter">Departure Date</div>
										<div class="quarter">:&nbsp;<fmt:formatDate value="${ltcApprovalRequest.departureDate}" pattern="dd-MMM-yyyy"/></div>
										<div class="quarter">Return Date</div>
										<div class="quarter">:&nbsp;<fmt:formatDate value="${ltcApprovalRequest.returnDate}" pattern="dd-MMM-yyyy"/></div>
									</div>
									</div>		
								<div  class="line bold">Family details for whom LTC claimed</div>
								<div class="line" id="Pagination">
									<display:table name="${ltcApprovalRequest.ltcApproveDetailsList}" excludedParams="*"
											export="false" class="list" requestURI="" id="ltclist" pagesize="10"
										sort="list">
											<display:column  style="width:3%;text-align:center" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
												<input type="checkbox" class="row" name="row" id="${ltclist.id}" onclick="checkBoxCheck(this.name);"/>
											</display:column>
											<display:column title="Name">&nbsp;${ltclist.familyMemberName}</display:column>				 		
											<display:column title="Date of Birth">&nbsp;${ltclist.dob}</display:column>
											<display:column title="Age">&nbsp;${ltclist.age}</display:column>
											<display:column title="Relationship with Govt. servant">&nbsp;${ltclist.relation}</display:column>
								</display:table>
								</div>
								
								<div  class="line bold">
								Journey details &nbsp;[<fmt:formatDate value="${ltcApprovalRequest.departureDate}" pattern="dd-MMM-yyyy"/> to <fmt:formatDate value="${ltcApprovalRequest.returnDate}" pattern="dd-MMM-yyyy"/>]
								</div>
								<div class="line">
								<table class="fortable" width="100%" id="journeyDetailsID">
								<tr>
									<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
									<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
								</tr>	
									<tr>
										<td colspan="3" class="tabletd" align="center" >Departure</td>
										<td colspan="3" class="tabletd" align="center" >Arrival</td>
										<td rowspan="2" class="tabletd" >Distance in KM</td>
										<td rowspan="2" class="tabletd" >Mode of travel & type of accommo</td>
										<td rowspan="2" class="tabletd" >Fare per person Rs.</td>
										<td rowspan="2" class="tabletd" >Persons</td>
										<td rowspan="2" class="tabletd" >Total amount claimed Rs.</td>
										<td rowspan="2" class="tabletd" >Ticket No.</td>
										<td rowspan="2" class="tabletd" >Journey Type</td>
										<td rowspan="2" class="tabletd" >Add</td>
										<td rowspan="2" class="tabletd" >Del</td>
									</tr>
									<tr>
										<td class="tabletd" >Date</td>
										<td class="tabletd" >Time</td>
										<td class="tabletd" >Station</td>
										<td class="tabletd" >Date</td>
										<td class="tabletd" >Time</td>
										<td class="tabletd" >Station</td>
									</tr>
									<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
									<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
									</tr>	
										<tr id="row0">
											<td >
												<input type="text" readonly="readonly" id="startDate0" style="height:16px;width:60px;font-size: 9px;font-weight: bold;"  id="startDate0" onfocus ="javascript:Calendar.setup({inputField :'startDate0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkDates('startDate0');"/>
											</td>
											<td ><input type="text" name="startTime" readonly=readonly class="startTime" id="startTime0" style="width:37px" onfocus="javascript:timePicker('startTime');"/></td>
											<td ><input type="text" id="startStation0" style="width:55px"/></td>
											<td >
												<input type="text" id="endDate0" readonly="readonly" style="height:16px;width:60px;font-size: 9px;font-weight: bold;"  id="endDate0" onfocus ="javascript:Calendar.setup({inputField :'endDate0',ifFormat : '%d-%b-%Y',showsTime : false,button :'endDate0',step : 1});" onchange="javascript:checkDates('endDate0');"/>
											</td>
											<td ><input type="text" id="endTime0" readonly=readonly class = "endTime" name = "endTime" style="width:37px" onfocus="javascript:timePicker('endTime')"/></td>
											<td ><input type="text" id="endStation0" style="width:55px"/></td>
											<td ><input type="text" id="distance0" style="width:55px" onkeypress="javascript:return checkInt(event);"/></td>
											<td ><input type="text" id="modeOfTravel0" style="width:100px"/></td>
											<td ><input type="text" id="farePerPerson0" style="width:49px" onchange="javascript:getTotal()" onkeypress="return checkInt(event);"/></td>
											<td ><input type="text" id="noOfPersons0" style="width:56px" onchange="javascript:getTotal()" onkeypress="return checkInt(event);"/></td>
											<td ><input type="text" id="totalAmount0" style="width:52px" onchange="javascript:totalAdjustment()" onkeypress="return checkInt(event);"/></td>
											<td ><input type="text" id="ticketNo0" style="width:70px" /></td>
											<td ><select name="" style="width:70px" id="JourneyType0">
													<option value="">Select</option>
													<option value="O">Onward</option>
													<option value="R">Return</option>
												</select>
											</td>
											<td ><input type="button" id="add0" class="smallbtn" value="+" onclick="javascript:funcreatenewJourneyRow('journeyDetailsID')"/></td>
											<td ><input type="button" id="del0" class="smallbtn" value="-" style="display: none" /></td>
											
										</tr>
									</table>		
								</div>
								
								<div class="line">
									<div class="quarter">Total Amount:</div>
									<div class="quarter bold" id="totAmt">&nbsp;</div>
								</div>
								<c:if test="${ltcApprovalRequest.type eq 'settlement'}">
									<div class="line" id="settlementDiv">
										<div class="quarter">Advance Drawn</div>
										<div class="quarter" id="issuedAmount">${ltcApprovalRequest.issuedAmount}</div>
										<div class="quarter">Settlement Amount</div>
										<div class="quarter" id="settlementAmount">&nbsp;</div>
									</div>	
								</c:if>
								<div class="line" id="ltcReimburseButtons">
									<div class="appbutton" style="margin-left:30%;"><a href="javascript:saveLTCReimbursement();" class="quarterbutton">Save</a></div>
									<div class="appbutton"><a href="javascript:clearLTCReimbursement();" class="quarterbutton">Clear</a></div>
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
			<form:hidden path="id"/>
			<form:hidden path="requestId"/>
			<form:hidden path="jsonValue"/>
		</form:form>
		<script>
			var leaveRequestId='${ltcApprovalRequest.leaveRequestId }';
			departureDate = '<fmt:formatDate value="${ltcApprovalRequest.departureDate}" pattern="dd-MMM-yyyy"/>';
			returnDate = '<fmt:formatDate value="${ltcApprovalRequest.returnDate}" pattern="dd-MMM-yyyy"/>';
		</script>
	</body>
</html>
<!-- End:LTCReimbursementDetails.jsp -->