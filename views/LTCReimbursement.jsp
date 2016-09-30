<!-- Begin : LTCReimbursement.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div>
	<div class="line">
		<div class="quarter bold">Type of LTC Applied</div>
		<div class="quarter">:&nbsp;${workflowmap.ltcApprovalRequestDTO.ltcTypeId}</div>
		<div class="quarter bold">Block Year</div>
		<div class="quarter">:&nbsp;${workflowmap.ltcApprovalRequestDTO.ltcBlockYearId}</div>
	</div>
	<div class="line">
		<div class="quarter bold">Place of Visit</div>
		<div class="quarter">:&nbsp;${workflowmap.ltcApprovalRequestDTO.placeOfVisit}</div>
		<c:if test="${not empty workflowmap.ltcApprovalRequestDTO.leaveRequestId}">
			<div class="quarter bold">Approved Leave</div>
			<div class="quarter">:&nbsp;${workflowmap.ltcApprovalRequestDTO.leaveRequestId}</div>
		</c:if>
	</div>
	
	<div class="line"><c:choose>
			<div class="quarter bold">DO Part II No & Date</div>
			<c:when test="${not empty workflowmap.ltcApprovalRequestDTO.orgroleIdLink}">
			<div class="quarter">:&nbsp;${workflowmap.ltcApprovalRequestDTO.doPartNo}</div></c:when>
			<c:otherwise><font color='red'><div class="quarter" >:&nbsp;Published </div></font></c:otherwise>
			</c:choose></div>
	<div class="line">
		<c:if test="${workflowmap.requestType eq 'LTC SETTLEMENT'}">
			<div class="quarter bold">Issued Advance Amount</div>
			<div class="quarter">:&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.ltcApprovalRequestDTO.issuedAmount}/-</div>
		</c:if>
	</div>
	<div class="line">
		<div class="quarter bold" id="amountClaimedLabel">Employee Claimed Amount</div>
		<div class="quarter" id="amountClaimed"><font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.ltcApprovalRequestDTO.totalAmount}/-</div>
		<c:if test="${not empty workflowmap.ltcApprovalRequestDTO.settlementAmount}">
			<div class="quarter bold" id="financeAmountLabel">Finance Issued Amount</div>
			<div class="quarter" id="financeAmount"><font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.ltcApprovalRequestDTO.settlementAmount}/-</div>
		</c:if>
	</div>
	<c:if test="${workflowmap.ltcApprovalRequestDTO.settlementAmount lt '0'}">
		<div class="line">
					<div class="line">
						<marquee direction="left" behavior="alternate">
						<div class="line" style="color:red" align="center">
						  <b>Please take the print and immediately pay MRO to avoid additional Penal Interest</b>
						</div></marquee>
					</div>
			<div id="MroFormDiv">
				<div class="quarter bold">MRO Form</div>
				<div class="quarter">:&nbsp;<a href="javascript:getInitialReport('${workflowmap.requestId}','MroForm')">Print</a></div>
			</div>
		</div>
	</c:if>
		<div class="line">
		<fieldset><legend><strong><font color='green'>Reports</font></strong></legend>
			<c:if test="${workflowmap.ltcApprovalRequestDTO.settlementAmount eq null}">
	<div class="line">
		<marquee direction="left" behavior="alternate"><div class="line" style="color:red" align="center">
	<b>Immediately submit your bills to Finance,Unless bills verified with Finance Section this claim will not be processed further.</b>
	</div></marquee>
	</div>
	</c:if>
				<div class="quarter bold">LTC Reimbursement Request Form</div>
			<c:if test="${workflowmap.ltcApprovalRequestDTO.settlementAmount ne null}"><div class="quarter">:&nbsp;<a href="javascript:getInitialReport('${workflowmap.requestId}','${workflowmap.ltcApprovalRequestDTO.requestType}')">Print</a></div></c:if>
			<c:if test="${workflowmap.ltcApprovalRequestDTO.settlementAmount eq null}"><div class="quarter">:&nbsp;<font color="red">Don't forget to print & Sign report after finance approval from here</font></div></c:if>
	
				 <div class="quarter bold">LTC Tour Particulars</div>
				 <c:if test="${workflowmap.ltcApprovalRequestDTO.settlementAmount ne null}">
			<!-- <div class="quarter">:&nbsp;<a href="javascript:getInitialReport('${workflowmap.requestId}','TourSettlement')">Print</a></div> -->
		<c:if test="${workflowmap.ltcApprovalRequestDTO.requestType eq 'ltcReim'}">
			<div class="quarter">:&nbsp;<a href="javascript:getInitialReport('${workflowmap.requestId}','TourSettlement')">Print</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getInitialDocReport('${workflowmap.requestId}','TourSettlement')">Report in Doc</a></div>
			
			
			
			
		</c:if>
		<c:if test="${workflowmap.ltcApprovalRequestDTO.requestType eq 'ltcSett'}">
			<div class="quarter">:&nbsp;<a href="javascript:getInitialReport('${workflowmap.requestId}','SettTourSettlement')">Print</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getInitialDocReport('${workflowmap.requestId}','SettTourSettlement')">Report in Doc</a></div>
		</c:if>
		
	
	</c:if>
	<c:if test="${workflowmap.ltcApprovalRequestDTO.settlementAmount eq null}"><div class="quarter">:&nbsp;<font color="red">Don't forget to print & Sign report after finance approval from here</font></div></c:if>
	</fieldset>
	<fieldset>
	 <div>
         <c:if test="${ not empty workflowmap.ltcApprovalRequestDTO.requestidApprival && workflowmap.ltcApprovalRequestDTO.requestidApprival!=0}">
            <div class="quarter leftmar">Approval RequestId RequestId:
           <DIV>  <div class="quarter"> <a href="javascript:getRequestDetails('${workflowmap.ltcApprovalRequestDTO.historyrequestidApprival}','${workflowmap.ltcApprovalRequestDTO.requestidApprival}','myRequests','completed','')">${workflowmap.ltcApprovalRequestDTO.requestidApprival}</a></div></DIV>
		    </div>
	        </c:if> 
		  </div></fieldset>
	</div>
	<div class="headTitle">Details of Members for LTC</div>
	<div class="line">
		<table border="1" width="100%" align="center" cellpadding="2" cellspacing="0" class="sub_2">
			<tr>
				<th>Name</th>
				<th>Date of Birth</th>
				<th>Age as on ${workflowmap.ltcApprovalRequestDTO.departureDate}</th>
				<th>Relation Ship with Govt. Servant</th>
			</tr>
			<c:forEach items="${workflowmap.ltcApprovalRequestDTO.ltcMemberDetails}" var="membersList">
				<tr>
					<td width="30%">${membersList.familyMemberName}</td>
					<td width="20%">${membersList.dob}</td>
					<td width="10%">${membersList.age}</td>
					<td width="40%">${membersList.relation}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="headTitle">Journey Details</div>
	<div class="line">
		<table BORDER="1" cellpadding="2" cellspacing="0" id="journeyDetails" class="sub_2">
			<tr>
				<!--  <td colspan="3" align="center" class="bold">Departure</td>
				<td colspan="3" align="center" class="bold">Arrival</td>
				<td rowspan="2" class="bold">Distance in KM</td>
				<td rowspan="2" class="bold">Mode of travel and type of accommo</td>
				<td rowspan="2" class="bold">Fare per person Rs.</td>
				<td rowspan="2" class="bold">Persons</td>
				<td rowspan="2" class="bold">Amount Rs.</td>
				<td rowspan="2" class="bold">Amount after Res. Rs.</td>
				<td rowspan="2" class="bold">Ticket Nos.</td>-->
				 <th colspan="3" align="center" class="bold">Departure</th>
				<th colspan="3" align="center" class="bold">Arrival</th>
				<th rowspan="2" class="bold">Distance in KM</th>
				<th rowspan="2" class="bold">Mode of travel and type of accommo</th>
				<th rowspan="2" class="bold">Fare per person <font size="4.5em"><span class="WebRupee" >R</span></font></th>
				<th rowspan="2" class="bold">Persons</th>
				<th rowspan="2" class="bold">Amount <font size="4.5em"><span class="WebRupee" >R</span></font></th>
				<th rowspan="2" class="bold">Amount after Res. <font size="4.5em"><span class="WebRupee" >R</span></font></th>
				<th rowspan="2" class="bold">Ticket Nos.</th>
			</tr>
			<tr>
				<th class="bold">Date</th>
				<th class="bold">Time</th>
				<th class="bold">Station</th>
				<th class="bold">Date</th>
				<th class="bold">Time</th>
				<th class="bold">Station</th>
			</tr>
			<c:forEach items="${workflowmap.ltcJourneyDetails}" var="ltcJourney">
				<tr>
					<td>${ltcJourney.departureDateOne}</td>
					<td>${ltcJourney.departureTime}</td>
					<td>${ltcJourney.departureStation}</td>
					<td>${ltcJourney.arrivalDateOne}</td>
					<td>${ltcJourney.arrivalTime}</td>
					<td>${ltcJourney.arrivalStation}</td>
					<td>${ltcJourney.distance}</td>
					<td>${ltcJourney.modeOfTravel}</td>
					<td>${ltcJourney.farePerPerson}</td>
					<td>${ltcJourney.noOfPersons}</td>
					<td>${ltcJourney.totalClaimed}</td>
					<td>
					
					<c:if test="${not empty ltcJourney.amtAfterRestriction}">
						<input type="text" name="amountAfterRestriction" id="${ltcJourney.id}" size="6" onkeyup="javascript:calculateAmountAfterRestriction();" onkeypress="return isNumberExp(event);" value='${ltcJourney.amtAfterRestriction}' />
					</c:if>
					<c:if test="${empty ltcJourney.amtAfterRestriction && workflowmap.checkStage eq 'last' && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
						<input type="text" name="amountAfterRestriction" id="${ltcJourney.id}" size="6" onkeyup="javascript:calculateAmountAfterRestriction();" style="background-color: pink;text-align: center;" onkeypress="return isNumberExp(event);"/>
					</c:if>
					<c:if test="${empty ltcJourney.amtAfterRestriction && workflowmap.checkStage eq 'previous' && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
						<input type="text" name="amountAfterRestriction" id="${ltcJourney.id}" size="6" onkeyup="javascript:calculateAmountAfterRestriction();" style="background-color: pink;text-align: center;" onkeypress="return isNumberExp(event);"/>
					</c:if>
					</td>
					<td>${ltcJourney.titcketNos}</td>
				</tr>
			</c:forEach>
			
		</TABLE> 
	</div>
	
	<c:if test="${workflowmap.checkStage eq 'last'|| workflowmap.checkStage eq 'previous' && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
		<div class="line">
			<div class="quarter bold">Finance Issuing Amount after Resrticion</div>
			<div class="quarter" id="restrictionAmount">&nbsp;</div>
			<div id="excessAmountDiv" style="display: none">
				<div class="quarter bold">Excess Amount After Restriction</div>
				<div class="quarter"><font size="4.5em"><span class="WebRupee" >R</span></font><input type="text" name="excessAmount" id="excessAmount" readonly="readonly"/></div>
			</div>
		</div>
		<c:if test="${not empty workflowmap.ltcApprovalRequestDTO.excessAmountFine}">
		<div class="line">
		<div class="quarter bold">Penal Interest</div>
		<div class="quarter">${workflowmap.ltcApprovalRequestDTO.excessAmountFine}</div>
		</div>
		</c:if>
		<div class="line" id="excessAmountFineDiv" style="display: none">
		<div class="quarter bold">Penal Interest</div>
		<div class="quarter"><input type="text"  readonly="readonly"  name="excessAmountFine" id="excessAmountFine"/></div>
		<div class="quarter">No of days&nbsp;&nbsp;<input type="text" size="5" name ="noOfDays" id="noOfDays" onkeyup="calculatePenalFine('${workflowmap.ltcApprovalRequestDTO.issuedAmount}')"   style="background-color: pink;text-align: center;"/></div>
		<div class="quarter">Interest Rate&nbsp;&nbsp;<input type="text" size="5" name="interestRate" readonly="readonly"   id="${workflowmap.ltcApprovalRequestDTO.ltcPenalMasterDTO.id}" value="${workflowmap.ltcApprovalRequestDTO.ltcPenalMasterDTO.typeValue}"/></div>
		</div>
		<c:if test="${workflowmap.ltcApprovalRequestDTO.settlementAmount lt '0'}">
		<div class="line" id="MRODetailsDiv">
			<div class="quarter bold">MRO Paid No<span class="mandatory">*</span></div>
			<div class="quarter"><input type="text" name="MROPaidNo" id="MROPaidNo" onkeypress="javascript:return checkInt(event);"  style="background-color: pink;text-align: center;"/></div>
			<div class="quarter bold">MRO Paid Date<span class="mandatory">*</span></div>
			<div class="quarter">
				<input type="text" name="MROPaidDate" id="MROPaidDate" readonly="readonly"  style="background-color: pink;text-align: center;"></input>
					<img  src="./images/calendar.gif"   id="MROPaidDateImg" />	
					<script type="text/javascript">
						Calendar.setup({inputField :"MROPaidDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"MROPaidDateImg",singleClick : true,step : 1});
					</script>
			</div>
		</div>
		</c:if>
	</c:if>
	<form:hidden path="amount" id="amount"/>
	<form:hidden path="settleAmount" id="settleAmount"/>
	<form:hidden path="penalInterestId" id="penalInterestId"/>
	<form:hidden path="statusMsg" id ="statusMsg"/>	
	
	
	
	<input type = "hidden" id="historyID"/> 
	<script type="text/javascript">ltcAdvanceMoney = '${workflowmap.ltcApprovalRequestDTO.issuedAmount}'
		setLtcReimbursement('${workflowmap.ltcApprovalRequestDTO.totalAmount}','${workflowmap.ltcApprovalRequestDTO.settlementAmount}','${workflowmap.ltcApprovalRequestDTO.excessAmount}');
		$jq('#penalInterestId').val('${workflowmap.ltcApprovalRequestDTO.ltcPenalMasterDTO.id}');
	</script>
</div>
<!-- End : LTCReimbursement.jsp -->