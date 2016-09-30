<!-- Begin : TadaTdSettlementDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/tada.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script type="text/javascript" src="script/jquery-ui-timepicker-addon.js"></script>
<link href="styles/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" />

<div>
    <c:forEach var="empClaimDetailsList" items="${workflowmap.empClaimDetailsList}">
	    <c:if test="${(empClaimDetailsList.workFlowStatus!=8 && empClaimDetailsList.workFlowStatus!=60 && empClaimDetailsList.workFlowStatus!=6)}">
          <marquee direction="left" behavior="alternate"><div class="line" style="color:red" align="center">
	       Immediately submit your bills to Finance,Unless bills verified with Finance Section this claim will not be processed further.
	      </div></marquee>
	   </c:if></c:forEach>
	<div class="line">
	<div style="display: none"> <!--this div hidden by bkr 26/04/2016  -->
	    <div class="quarter bold">Basic Pay & Grade Pay</div>
		<div class="quarter">:&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.tadaTdAdvanceRequestDTO.basicPay}/- & <font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.tadaTdAdvanceRequestDTO.gradePay}/-</div>
		</div>
		<div class="quarter bold">Address Of TD Work Place</div>
		<div class="quarter">:&nbsp;${workflowmap.tadaTdAdvanceRequestDTO.tdWorkPlace}</div>
			<div class="quarter bold">PURPOSE</div>
		<div class="quarter">:&nbsp;${workflowmap.tadaTdAdvanceRequestDTO.purpose}</div>
	</div>
	<div class="line">
	    <div class="quarter bold">Stay Duration</div>
		<div class="quarter" id="tdWorkPlace">:&nbsp;${workflowmap.tadaTdAdvanceRequestDTO.stayDuration} Days</div>
		<div style="display: none"> <!--this div hidden by bkr 26/04/2016  -->
		<div class="quarter bold">Project For Which Move is authorized</div>
		<div class="quarter" id="authorizedMove"><c:if test="${workflowmap.tadaTdAdvanceRequestDTO.authorizedMove==1}">: Build-Up</c:if><c:if test="${workflowmap.tadaTdAdvanceRequestDTO.authorizedMove==2}">: Project</c:if></div>
	</div>
	
	</div>
	<div class="line">
	    <div class="quarter bold">Advance Amount</div>
		<div class="quarter" id="tadaAdvanceAmount">: <font size="4.5em"><span class="WebRupee" >R</span></font><c:if test="${workflowmap.cdaDetailsDTO.cdaAmount==null}">0/-</c:if><c:if test="${workflowmap.cdaDetailsDTO.cdaAmount!=null}">${workflowmap.cdaDetailsDTO.cdaAmount}/-</c:if></div>
	    <div class="quarter bold">Total User Claimed Amount</div>
	    <div class="quarter" id="tadaAdvanceAmount">: <c:forEach var="empClaimDetailsList" items="${workflowmap.empClaimDetailsList}"><font size="4.5em"><span class="WebRupee" >R</span></font>${empClaimDetailsList.amountClaimed}/-</c:forEach></div>
	</div>
	<div class="line" style="display: none"><!--hidden by bkr 26/04/2016  -->
	   <div class="quarter bold">Ticket Cancellation Amount</div>
	   <div class="quarter" id="tadaCancelAmount">&nbsp;: <font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.tadaTdAdvanceRequestDTO.ticketCancelCharges}</div>
	</div>
	<div class="line">
	   <div class="quarter bold">User Remarks</div>
	   <div class="Half">&nbsp;: ${workflowmap.userRemarks}</div>
	</div>
	<div class="line">
	<c:if test="${workflowmap.requestType=='TADA TD REIMBURSEMENT'}">
	<div class="line">
	    <div class="quarter bold">TADA TD Application Form</div>
		<div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.tadaTdAdvanceRequestDTO.requestId}','tadaTdApproval')">View</a></div></div>
		<div class="quarter bold">TADA TD Reimbursement Form</div>
	    <c:forEach var="empClaimDetailsList" items="${workflowmap.empClaimDetailsList}">
	    <c:if test="${(empClaimDetailsList.workFlowStatus== 8 || empClaimDetailsList.workFlowStatus==60)}">
	    <c:if test="${workflowmap.tdDaNewAccDetailsList!='[]' && workflowmap.tdRMADayList!='[]'}">
	       <div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdReimRmaDay')">Print</a></div>
	    </c:if>
	    <c:if test="${workflowmap.tdDaNewAccDetailsList!='[]' && workflowmap.tdDaNewAccDetailsList!=null && workflowmap.tdRMAKmList!='[]' && workflowmap.tdRMAKmList!=null}">
	       <div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdReimRmaKm')">Print</a></div>
	    </c:if>
	    <c:if test="${workflowmap.tdDaNewAccDetailsList!='[]' && workflowmap.tdDaNewAccDetailsList!=null && (workflowmap.tdRMAKmList=='[]' || workflowmap.tdRMAKmList==null || workflowmap.tdRMAKmList=='') && (workflowmap.tdRMADayList=='[]' || workflowmap.tdRMADayList==null || workflowmap.tdRMADayList=='')}">
	       <div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdReimRmaKm')">Print</a></div>
	    </c:if>
	    <c:if test="${workflowmap.tdDaOldDetailsList!='[]' && workflowmap.tdDaOldDetailsList!=null}">
	       <div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdReimOldDa')">Print</a></div>
	    </c:if>
	    <c:if test="${(workflowmap.tdJourneyList !='[]' || workflowmap.tdJourneyList !=null || workflowmap.tdJourneyList !='') && (workflowmap.tdDaOldDetailsList =='[]' || workflowmap.tdDaOldDetailsList ==null || workflowmap.tdDaOldDetailsList =='') 
	    && (workflowmap.tdDaNewAccDetailsList =='[]' || workflowmap.tdDaNewAccDetailsList ==null || workflowmap.tdDaNewAccDetailsList =='')}">
	       <div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdReimRmaKm')">Print</a></div>
	    </c:if>
		</c:if>
		<c:if test="${(empClaimDetailsList.workFlowStatus!=8 && empClaimDetailsList.workFlowStatus!=60)}">
		   <c:choose>
		<c:when test="${workflowmap.tdDaNewAccDetailsList !='[]' && workflowmap.tdRMADayList !='[]'}">
	       <div class="quarter">:&nbsp;<b><span style="color:red">Don't forget to print & Sign report after finance approval from here</span></b><a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdReimRmaDay','required')"><b>(Draft Copy)</b></a></div>
	    </c:when>
	    <c:when test="${workflowmap.tdDaNewAccDetailsList !='[]' && workflowmap.tdDaNewAccDetailsList !=null && workflowmap.tdRMAKmList !='[]' && workflowmap.tdRMAKmList !=null}">
	       <div class="quarter">:&nbsp;<b><span style="color:red">Don't forget to print & Sign report after finance approval from here</span></b><a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdReimRmaKm','required')"><b>(Draft Copy)</b></a></div>
	    </c:when>
	    <c:when test="${workflowmap.tdDaNewAccDetailsList !='[]' && workflowmap.tdDaNewAccDetailsList !=null && (workflowmap.tdRMAKmList =='[]' || workflowmap.tdRMAKmList ==null || workflowmap.tdRMAKmList =='') && (workflowmap.tdRMADayList =='[]' || workflowmap.tdRMADayList ==null || workflowmap.tdRMADayList =='')}">
	       <div class="quarter">:&nbsp;<b><span style="color:red">Don't forget to print & Sign report after finance approval from here</span></b><a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdReimRmaKm','required')"><b>(Draft Copy)</b></a></div>
	    </c:when>
	    <c:when test="${workflowmap.tdDaOldDetailsList !='[]'}">
	       <div class="quarter">:&nbsp;<b><span style="color:red">Don't forget to print & Sign report after finance approval from here</span></b><a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdReimOldDa','required')"><b>(Draft Copy)</b></a></div>
	    </c:when>
	    <c:when test="${(workflowmap.tdJourneyList !='[]' || workflowmap.tdJourneyList !=null || workflowmap.tdJourneyList !='') && (workflowmap.tdDaOldDetailsList =='[]' || workflowmap.tdDaOldDetailsList ==null || workflowmap.tdDaOldDetailsList =='') 
	    && (workflowmap.tdDaNewAccDetailsList =='[]' || workflowmap.tdDaNewAccDetailsList ==null || workflowmap.tdDaNewAccDetailsList =='')}">
	       <div class="quarter">:&nbsp;<b><span style="color:red">Don't forget to print & Sign report after finance approval from here</span></b><a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdReimRmaKm','required')"><b>(Draft Copy)</b></a></div>
	    </c:when>
		</c:choose>
		</c:if>
		<div class="quarter bold">TD Tour Particulars Report</div>
		<c:if test="${(empClaimDetailsList.workFlowStatus==8 || empClaimDetailsList.workFlowStatus==60)}">
		    <div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tdReimTourParticulars')">Print</a></div>
		</c:if>
		<c:if test="${(empClaimDetailsList.workFlowStatus!=8 && empClaimDetailsList.workFlowStatus!=60)}">
		
		 <div class="quarter">:&nbsp;<b><span style="color:red">Don't forget to print & Sign report after finance approval from here</span>&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tdReimTourParticulars','required')">(Draft Copy)</a></b></div>
		
		</c:if>
		
		</c:forEach>
	</c:if>
	<c:if test="${workflowmap.requestType=='TADA TD SETTLEMENT'}">
	    <div class="quarter bold">TADA TD Application Form</div>
		<div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.tadaTdAdvanceRequestDTO.referenceRequestID}','tadaTdApproval')">View</a></div>
	    <div class="quarter bold">TADA TD Advance Form</div>
	    <div class="quarter">:&nbsp;
	    <c:if test="${workflowmap.tadaTdAdvanceRequestDTO.dvNumber=='asl'}">
	    <a href="javascript:getTdInitialReport('${workflowmap.tadaTdAdvanceRequestDTO.requestId}','tadaTdAdvanceFin')">View</a>
	    </c:if>
	    <c:if test="${workflowmap.tadaTdAdvanceRequestDTO.dvNumber!='asl'}">
	    <a href="javascript:getTdInitialReport('${workflowmap.tadaTdAdvanceRequestDTO.requestId}','tadaTdAdvanceCda')">View</a>
	    </c:if>
	    </div>
	    <div class="quarter bold">TADA TD Settlement Form</div>
	    <c:forEach var="empClaimDetailsList" items="${workflowmap.empClaimDetailsList}">
	    <c:if test="${(empClaimDetailsList.workFlowStatus==8 || empClaimDetailsList.workFlowStatus==60)}">
	    <c:if test="${workflowmap.tdDaNewAccDetailsList!='[]' && workflowmap.tdRMADayList!='[]'}">
	       <div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdSettlementRmaDay','')">Print</a></div>
	    </c:if>
	    <c:if test="${workflowmap.tdDaNewAccDetailsList!='[]' && workflowmap.tdDaNewAccDetailsList!=null && workflowmap.tdRMAKmList!='[]' && workflowmap.tdRMAKmList!=null}">
	       <div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdSettlementRmaKm','')">Print</a></div>
	    </c:if>
	    <c:if test="${workflowmap.tdDaNewAccDetailsList!='[]' && workflowmap.tdDaNewAccDetailsList!=null && (workflowmap.tdRMAKmList=='[]' || workflowmap.tdRMAKmList==null || workflowmap.tdRMAKmList=='') && (workflowmap.tdRMADayList=='[]' || workflowmap.tdRMADayList==null || workflowmap.tdRMADayList=='')}">
	       <div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdSettlementRmaKm','')">Print</a></div>
	    </c:if>
	    <c:if test="${workflowmap.tdDaOldDetailsList!='[]'}">
	       <div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdSettlementOldDa','')">Print</a></div>
	    </c:if>
	    <c:if test="${(workflowmap.tdJourneyList!='[]' || workflowmap.tdJourneyList!=null || workflowmap.tdJourneyList!='') && (workflowmap.tdDaOldDetailsList=='[]' || workflowmap.tdDaOldDetailsList==null || workflowmap.tdDaOldDetailsList=='') 
	    && (workflowmap.tdDaNewAccDetailsList=='[]' || workflowmap.tdDaNewAccDetailsList==null || workflowmap.tdDaNewAccDetailsList=='')}">
	       <div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdSettlementRmaKm','')">Print</a></div>
	    </c:if>
		</c:if>
		<c:if test="${(empClaimDetailsList.workFlowStatus!=8 && empClaimDetailsList.workFlowStatus!=60)}">
		 
		<c:choose>
		<c:when test="${workflowmap.tdDaNewAccDetailsList !='[]' && workflowmap.tdRMADayList !='[]'}">
	       <div class="quarter">:&nbsp;<b><span style="color:red">Don't forget to print & Sign report after finance approval from here</span></b><a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdSettlementRmaDay','required')"><b>(Draft Copy)</b></a></div>
	    </c:when>
	    <c:when test="${workflowmap.tdDaNewAccDetailsList !='[]' && workflowmap.tdDaNewAccDetailsList !=null && workflowmap.tdRMAKmList !='[]' && workflowmap.tdRMAKmList !=null}">
	       <div class="quarter">:&nbsp;<b><span style="color:red">Don't forget to print & Sign report after finance approval from here</span></b><a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdSettlementRmaKm','required')"><b>(Draft Copy)</b></a></div>
	    </c:when>
	    <c:when test="${workflowmap.tdDaNewAccDetailsList !='[]' && workflowmap.tdDaNewAccDetailsList !=null && (workflowmap.tdRMAKmList =='[]' || workflowmap.tdRMAKmList ==null || workflowmap.tdRMAKmList =='') && (workflowmap.tdRMADayList =='[]' || workflowmap.tdRMADayList ==null || workflowmap.tdRMADayList =='')}">
	       <div class="quarter">:&nbsp;<b><span style="color:red">Don't forget to print & Sign report after finance approval from here</span></b><a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdSettlementRmaKm','required')"><b>(Draft Copy)</b></a></div>
	    </c:when>
	    <c:when test="${workflowmap.tdDaOldDetailsList !='[]'}">
	       <div class="quarter">:&nbsp;<b><span style="color:red">Don't forget to print & Sign report after finance approval from here</span></b><a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdSettlementOldDa','required')"><b>(Draft Copy)</b></a></div>
	    </c:when>
	    <c:when test="${(workflowmap.tdJourneyList !='[]' || workflowmap.tdJourneyList !=null || workflowmap.tdJourneyList !='') && (workflowmap.tdDaOldDetailsList =='[]' || workflowmap.tdDaOldDetailsList ==null || workflowmap.tdDaOldDetailsList =='') 
	    && (workflowmap.tdDaNewAccDetailsList =='[]' || workflowmap.tdDaNewAccDetailsList ==null || workflowmap.tdDaNewAccDetailsList =='')}">
	       <div class="quarter">:&nbsp;<b><span style="color:red">Don't forget to print & Sign report after finance approval from here</span></b><a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdSettlementRmaKm','required')"><b>(Draft Copy)</b></a></div>
	    </c:when>
		</c:choose>
		
		</c:if>
		<div class="quarter bold">TD Tour Particulars Report:</div>
		<c:if test="${(empClaimDetailsList.workFlowStatus==8 || empClaimDetailsList.workFlowStatus==60)}">
		    <div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tdSettTourParticulars')">Print</a></div>
		</c:if>
		<c:if test="${(empClaimDetailsList.workFlowStatus!=8 && empClaimDetailsList.workFlowStatus!=60)}">
		
		 <div class="quarter">:&nbsp;<b><span style="color:red">Don't forget to print & Sign report after finance approval from here</span>&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tdSettTourParticulars','required')">(Draft Copy)</a></b></div>
		
		</c:if>
		</c:forEach>
	</c:if>
	</div>
	<c:if test="${workflowmap.mroDetailsList!='[]' && workflowmap.mroDetailsList!=null && workflowmap.mroDetailsList!=''}">
	<div class="quarter bold">MRO Report(s):</div>
	
	<%int i=1; %>
	<c:forEach var="mroDetailsList" items="${workflowmap.mroDetailsList}">
	    <div class="quarter">:&nbsp;Report-<%=i %>&nbsp;&nbsp;<a href="javascript:getMROReport('${workflowmap.requestId}','${mroDetailsList.id}','tadaTdMRODetails')">Print</a>&nbsp;&nbsp;&nbsp;</div>
	<% i++; %>
	</c:forEach>
	</c:if>
	<div class="line">
	    
	</div>
	<c:if test="${workflowmap.tdLeaveDetailsList!=null && workflowmap.tdLeaveDetailsList!='[]'}">
	<div class="headTitle">Attached Leave Details</div>
	<div class="line">
		<table BORDER="1" cellpadding="2" cellspacing="0" id="tdLeaveDetailsId" style="width:100%" class="sub_2">
			<tr>
				<td  class="tabletd" align="center" >Leave RequestId</td>
				<td  class="tabletd" align="center" >Leave Type</td>
				<td  class="tabletd" align="center" >From Date</td>
				<td  class="tabletd" align="center" >To Date</td>
				<td  class="tabletd" align="center" >No. Of Days</td>
			</tr>
			
			<c:forEach var="tdLeaveDetailsList" items="${workflowmap.tdLeaveDetailsList}">
				<tr>
				    <td>${tdLeaveDetailsList.requestID}</td>
				    <td>${tdLeaveDetailsList.leaveTypeDetails.leaveType}</td>
					<td><fmt:formatDate pattern="dd-MMM-yyyy" value="${tdLeaveDetailsList.formattedFromDate}"/></td>
					<td><fmt:formatDate pattern="dd-MMM-yyyy" value="${tdLeaveDetailsList.formattedToDate}"/></td>
					<td>${tdLeaveDetailsList.noOfDays}</td>
				</tr>
			</c:forEach>
			
		</table> 
	</div>
	</c:if>
	<div class="headTitle">Journey Details</div>
	<div class="line">
		<table BORDER="1" cellpadding="2" cellspacing="0" id="journeyDetailsId" style="width:100%" class="sub_2 tdsett">
			<tr>
				<td colspan="3" class="tabletd" align="center" >Departure</td>
				<td colspan="3" class="tabletd" align="center" >Arrival</td>
				<td rowspan="2" class="tabletd" align="center" >Distance in KM</td>
				<td rowspan="2" class="tabletd" align="center" >Mode of travel</td>
				<td rowspan="2" class="tabletd" align="center" >Ticket Fare <font size="4.5em"><span class="WebRupee" >R</span></font></td>
				<td rowspan="2" class="tabletd" align="center" ><font color="blue">Ticket Fare Aft Res <font size="4.5em"><span class="WebRupee" >R</span></font>(A)</font></td>
				<td rowspan="2" class="tabletd" align="center" >Ticket Cancel Amount<font size="4.5em"><span class="WebRupee" >R</span></font></td>
				<td rowspan="2" class="tabletd" align="center" >Ticket No.</td>
				<td rowspan="2" class="tabletd" align="center" ><font color="blue">Ticket Cancel Amount after Res. <font size="4.5em"><span class="WebRupee" >R</span></font>(B)</font></td>
				<c:if test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
				<td rowspan="2" class="tabletd" align="center" >Add</td>
				<td rowspan="2" class="tabletd" align="center" >Del</td>
				</c:if>
			</tr>
			<tr>
				<td class="tabletd" >Date</td>
				<td class="tabletd" >Time</td>
				<td class="tabletd" >Station</td>
				<td class="tabletd" >Date</td>
				<td class="tabletd" >Time</td>
				<td class="tabletd" >Station</td>
			</tr>
			<%int j=0; %>
			<c:forEach var="tdJourneyList" items="${workflowmap.tdJourneyList}">
				<tr id="journeyRow<%=j %>">
					<c:choose>
					<c:when test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
					   <td><input type="hidden" value="${tdJourneyList.id}" /><input type="text" maxlength="100" size="9px" style="width:75px" id="journeyDeptDate<%=j %>" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdJourneyList.journeyDate}"/>" onfocus ="javascript:Calendar.setup({inputField :'journeyDeptDate<%=j %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" /></td>
					   <td><input type="text" maxlength="100" size="2px" id="journeyDeptTime<%=j %>" class="journeyDeptTime" value="${tdJourneyList.startTime}" onfocus ="javascript:timePicker('journeyDeptTime')"/></td>
					   <td><input type="text" maxlength="100" size="8px" id="journeyDeptStn<%=j %>" value="${tdJourneyList.startStation}" onkeypress="javascript:return checkChar(event);" /></td>
					   <td><input type="text" maxlength="100" style="width:75px" size="9px" id="journeyArrDate<%=j %>" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdJourneyList.journeyEndDate}"/>" onfocus ="javascript:Calendar.setup({inputField :'journeyArrDate<%=j %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" /></td>
					   <td><input type="text" maxlength="100" size="2px" id="journeyArrTime<%=j %>" class="journeyArrTime" value="${tdJourneyList.endTime}" onfocus ="javascript:timePicker('journeyArrTime')"/></td>
					   <td><input type="text" maxlength="100" size="8px" id="journeyArrStn<%=j %>" value="${tdJourneyList.endStation}" onkeypress="javascript:return checkChar(event);" /></td>
					   <td><input type="text" maxlength="100" size="5px" id="journeyDistance<%=j %>" value="${tdJourneyList.distanceJourney}" onkeypress="javascript:return checkFloat(event,'journeyDistance<%=j %>');" autocomplete="off"/></td>
					   <td><input type="text" maxlength="100" size="4px" id="modeOfTravel<%=j %>" value="${tdJourneyList.modeOfTravel}" onkeypress="javascript:return checkChar(event);" /></td>
					   <td><input  type="text" maxlength="100" size="4px" id="ticketFare<%=j %>" style="text-align: right;" value="${tdJourneyList.ticketFare}" onkeypress="javascript:return checkFloat(event,'ticketFare<%=j %>');" autocomplete="off"/></td>
					   <td><input type="text" maxlength="100" size="4px" id="ticketFareAftRes<%=j %>" style="background-color: pink;text-align: right;" value="${tdJourneyList.ticketFareAftRes}" onkeypress="javascript:return checkFloat(event,'ticketFareAftRes<%=j %>');" onkeyup="javascript:individualTotAmount('journeyDetailsId');" autocomplete="off"/></td>
					   <td><input type="text" maxlength="100" size="4px" id="ticketCancelAmount<%=j %>" style="text-align: right;" value="${tdJourneyList.totalJourneyAmount}" onkeypress="javascript:return checkFloat(event,'ticketCancelAmount<%=j %>');" autocomplete="off" /></td>
					   <td><input type="text" maxlength="100"  size="4px" id="ticketNo<%=j %>" value="${tdJourneyList.ticketNo}" /></td>
					   <td><input type="text" maxlength="100" size="4px" id="ticketCancelAmountAftRes<%=j %>" style="background-color: pink;text-align: right;" value="${tdJourneyList.amountAftRestriction}" onkeypress="javascript:return checkFloat(event,'ticketCancelAmountAftRes<%=j %>');" onkeyup="javascript:individualTotAmount('journeyDetailsId');" autocomplete="off" /></td>
					   <td ><input type="button" id="add0" value="+" onclick="javascript:checkDAJourneyRow(<%=j %>);"/></td>
					   <td ><input type="button" id="del0" value="-" onclick="javascript:deleteDAJourneyRow(this,'journeyDetailsId');" /></td>
					</c:when>
					<c:otherwise>
					   <td><fmt:formatDate pattern="dd-MMM-yyyy" value="${tdJourneyList.journeyDate}"/></td>
					   <td>${tdJourneyList.startTime}</td>
					   <td>${tdJourneyList.startStation}</td>
					   <td><fmt:formatDate pattern="dd-MMM-yyyy" value="${tdJourneyList.journeyEndDate}"/></td>
					   <td>${tdJourneyList.endTime}</td>
					   <td>${tdJourneyList.endStation}</td>
					   <td>${tdJourneyList.distanceJourney}</td>
					   <td>${tdJourneyList.modeOfTravel}</td>
					   <td>${tdJourneyList.ticketFare}</td>
					   <td>${tdJourneyList.ticketFareAftRes}</td>
					   <td>${tdJourneyList.totalJourneyAmount}</td>
					   <td>${tdJourneyList.ticketNo}</td>
					   <td>${tdJourneyList.amountAftRestriction}</td>
					</c:otherwise>
					</c:choose>
				</tr>
				<% j++; %>
			</c:forEach>
			</table> 
			  <table border="1" style="width:100%">
			<tr>
				<td rowspan="5" class="tabletd" align="right" ><b>Journey Total(A+B)</b></td>
				<td rowspan="1" class="tabletd" align="right" >
				<div id="totJourneyAmount"></div>
				</td>
			</tr>
			</table>
			
	</div>
	<div class="line">
	<fieldset><legend><strong><font color='green'>Da Requirement Details</font></strong></legend>
	<c:choose>
	    <c:when test="${workflowmap.tdDaNewAccDetailsList!='[]' && workflowmap.tdDaNewAccDetailsList!=null}">
	      <div id="tadaRequirement"><b>DA Requirement: New DA</b></div>
	       <!--new  hidden varidable  -->
 DA Percentage :<input type="text" id="daValue" readonly="readonly" value="${workflowmap.daValue}" style="left: 150%"/>
 <div class="line"><font style="color:brown"> Calculations Details: In the event if DA raising above <u style=" color: "><b>50%</b></u>,all daily allowances on tourn,RMA and transportation of personal effects will be increased by <u><b>${workflowmap.tadaDaPercentage}%</b></u></font> <input type="hidden" id="tadaDaPercentage" name="tadaDaPercentage" value="${workflowmap.tadaDaPercentage}"/></div>
      </c:when>
	    <c:otherwise>
	      <div id="tadaRequirement"><b>DA Requirement: Old DA </b> </div>
	    </c:otherwise>
	
	</c:choose>
	</fieldset>
	</div>
	<div id="kdivold1">
	
	<c:if test="${workflowmap.tdDaNewAccDetailsList!='[]' && workflowmap.tdDaNewAccDetailsList!=null}">
	<div class="headTitle">Accommodation Details</div>
	<div class="line">
		<table BORDER="1" cellpadding="2" cellspacing="0" id="daNewAccDetailsId" style="width:100%" class="sub_2">
			<tr>
				<td rowspan="1" class="tabletd" align="center" >From Date</td>
				<td rowspan="1" class="tabletd" align="center" ></td>
				<td rowspan="1" class="tabletd" align="center" >To Date</td>
				<td rowspan="1" class="tabletd" align="center" ></td>
				<td rowspan="1" class="tabletd" align="center">Amount for Accommodation in <font size="4.5em"><span class="WebRupee" >R</span></font></td>
				<td rowspan="1" class="tabletd" align="center" ><font color="blue">Amount for Acc after Res. in <font size="4.5em"><span class="WebRupee" >R</span></font></font></td>
				<td rowspan="1" class="tabletd" align="center">Claimed Amount in <font size="4.5em"><span class="WebRupee" >R</span></font></td>
				<td rowspan="1" class="tabletd" align="center" ><font color="blue">Claimed Amount after Res. in <font size="4.5em"><span class="WebRupee" >R</span></font>(C) </font></td>
				<c:if test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
				 <td rowspan="1" class="tabletd" align="center" >Add</td>
				 <td rowspan="1" class="tabletd" align="center" >Del</td>
				</c:if>
			</tr>
			<%int k=0; %>
			<c:forEach var="tdDaNewAccDetailsList" items="${workflowmap.tdDaNewAccDetailsList}">
				<tr id="tdDaNewAccRow<%=k %>">
				    <c:choose>
				    <c:when test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
				       <td><input type="hidden" value="${tdDaNewAccDetailsList.id}" /><input type="text" maxlength="70" size="10px" id="accFromDate<%=k %>" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaNewAccDetailsList.fromDate}" />" onfocus ="javascript:Calendar.setup({inputField :'accFromDate<%=k %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkNewFoodDate(this);"/></td>
				       <td></td>
				       <td><input type="text" maxlength="70" size="10px" id="accToDate<%=k %>" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaNewAccDetailsList.toDate}" />" onfocus ="javascript:Calendar.setup({inputField :'accToDate<%=k %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkNewFoodDate(this);" /></td>
				       <td></td>
				       <td><input type="text" maxlength="70" size="20px" id="accAmount<%=k %>" style="text-align: right;" value="${tdDaNewAccDetailsList.accAmount}" onkeypress="javascript:return checkFloat(event,'accAmount<%=k %>');" autocomplete="off" /></td>
				       <td><input type="text" maxlength="70" size="20px" id="accAmountAftRes<%=k %>" style="background-color: pink;text-align: right;" value="${tdDaNewAccDetailsList.accAmountAftRes}" onkeypress="javascript:return checkFloat(event,'accAmountAftRes<%=k %>');" onchange="javascript:enableAccAmtAftCal(0);showAccAmtDayRepresentation('${workflowmap.requestId}','${workflowmap.requesterSfid}','${workflowmap.roleInstanceName}');individualTotAmount('all'); " autocomplete="off" /></td> <!-- This line has added for auto calculation ,individualTotAmount('daNewAccDetailsId'); -->
				       <td><input type="text" maxlength="70" size="20px" id="claimedAmount<%=k %>" style="text-align: right;" value="${tdDaNewAccDetailsList.claimedAmount}" onkeypress="javascript:return checkFloat(event,'claimedAmount<%=k %>');" autocomplete="off" /></td>
				       <td><input type="text" maxlength="70" size="25px" id="claimedAmountAftRes<%=k %>" style="background-color: pink;text-align: right;" value="${tdDaNewAccDetailsList.claimedAmountAftRes}" onkeypress="javascript:return checkFloat(event,'claimedAmountAftRes<%=k %>');" onchange="javascript:accAmtDayRepresentation('${workflowmap.requestId}','${workflowmap.requesterSfid}','${workflowmap.roleInstanceName}');individualTotAmount('all'); "  autocomplete="off"/></td> <!--individualTotAmount('daNewAccDetailsId'); here i  changed function in this function  -->
				       <td><input type="button" id="add0" value="+" onclick="javascript:checkFinDANewAccRow(<%=k %>);"/></td>
					   <td><input type="button" id="del0" value="-" onclick="javascript:deleteFinDANewAccRow(this,'daNewAccDetailsId');" /></td>
				    </c:when>
				    <c:otherwise>
				       <td><fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaNewAccDetailsList.fromDate}"/></td>
				       <td></td>
				       <td><fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaNewAccDetailsList.toDate}"/></td>
				       <td></td>
				       <td>${tdDaNewAccDetailsList.accAmount}</td>
				       <td>${tdDaNewAccDetailsList.accAmountAftRes}</td>
				       <td>${tdDaNewAccDetailsList.claimedAmount}</td>
				       <td>${tdDaNewAccDetailsList.claimedAmountAftRes}</td>
				    </c:otherwise>
				    </c:choose>
				</tr>
			<% k++; %>
			</c:forEach>
		</table> 
		
	</div>
	</c:if>
	</div>
	<c:choose>
	<c:when test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')&& workflowmap.tdDaNewAccDetailsList!='[]' && workflowmap.tdDaNewAccDetailsList!=null }">
	<a id="accDayRepID" href="javascript:accAmtDayRepresentation('${workflowmap.requestId}','${workflowmap.requesterSfid}','${workflowmap.roleInstanceName}');individualTotAmount('all');">Generate Day Representation For Accomodation</a>
	<div class="line">
	   <jsp:include page="TadaTdAcomidationDetailsDayRep.jsp"></jsp:include>
	</div>
	
	</c:when>
	<c:otherwise>
	<div class="line">
	   <jsp:include page="TadaTdAcomidationDetailsDayRep.jsp"></jsp:include>

	

	</div>
	
	</c:otherwise>
	</c:choose>
	
	
	
	
<div id="kdivold">
	<c:if test="${workflowmap.tdDaNewFoodDetailsList!='[]' && workflowmap.tdDaNewFoodDetailsList!=null}">
	<div class="headTitle">Food Details</div>
	<div class="line">
		<table BORDER="1" cellpadding="2" cellspacing="0" id="daNewFoodDetailsId" style="width:100%" class="sub_2">
			<tr>
				<td rowspan="1" class="tabletd" align="center" >From Date</td>
				<td rowspan="1" class="tabletd" align="center" >To Date</td>
				<td rowspan="1" class="tabletd" align="center">Amount for Food in <font size="4.5em"><span class="WebRupee" >R</span></font></td>
				<td rowspan="1" class="tabletd" align="center" ><font color="blue">Amount for Food after Res. in <font size="4.5em"><span class="WebRupee" >R</span></font></font></td>
				<td rowspan="1" class="tabletd" align="center">Claimed Amount in <font size="4.5em"><span class="WebRupee" >R</span></font></td>
				<c:if test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
				 <td rowspan="1" class="tabletd" align="center" >Add</td>
				 <td rowspan="1" class="tabletd" align="center" >Del</td>
				</c:if>
			</tr>
			<%int l=0; %>
			<c:forEach var="tdDaNewFoodDetailsList" items="${workflowmap.tdDaNewFoodDetailsList}">
				<tr id="tdDaNewFoodRow<%=l %>">
				    <c:choose>
				    <c:when test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
				       <td><input type="hidden" value="${tdDaNewFoodDetailsList.id}" /><input type="text" maxlength="70" size="10px" id="foodFromDate<%=l %>" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaNewFoodDetailsList.fromDate}" />" onfocus ="javascript:Calendar.setup({inputField :'foodFromDate<%=l %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkNewFoodDate(this);"/></td>
				       <td><input type="text" maxlength="70" size="10px" id="foodToDate<%=l %>" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaNewFoodDetailsList.toDate}" />" onfocus ="javascript:Calendar.setup({inputField :'foodToDate<%=l %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkNewFoodDate(this);"/></td>
				       <td><input type="text" maxlength="70" size="30px" id="foodAmount<%=l %>" style="text-align: right;" value="${tdDaNewFoodDetailsList.foodAmount}" onkeypress="javascript:return checkFloat(event,'foodAmount<%=l %>');" autocomplete="off" /></td>
				       <td><input type="text" maxlength="70" size="35px" id="foodAmountAftRes<%=l %>" style="background-color: pink;text-align: right;" value="${tdDaNewFoodDetailsList.foodAmountAftRes}" onkeypress="javascript:return checkFloat(event,'foodAmountAftRes<%=l %>');" onchange="showFoodAmtDayRepresentation('${workflowmap.requestId}','${workflowmap.requesterSfid}','${workflowmap.roleInstanceName}');" autocomplete="off" /></td>
				       <td><input type="text" maxlength="70" size="25px" id="claimedFoodAmount<%=l %>" style="text-align: right;" value="${tdDaNewFoodDetailsList.claimedAmount}" onkeypress="javascript:return checkFloat(event,'claimedFoodAmount<%=l %>');" onkeyup="" autocomplete="off" /></td>
				       <td><input type="button" maxlength="70" id="add0" value="+" onclick="javascript:checkFinDANewFoodRow(<%=l %>);"/></td>
					   <td><input type="button" maxlength="70" id="del0" value="-" onclick="javascript:deleteFinDANewFoodRow(this,'daNewFoodDetailsId');" /></td>
				    </c:when>
				    <c:otherwise>
				       <td><fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaNewFoodDetailsList.fromDate}"/></td>
				       <td><fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaNewFoodDetailsList.toDate}"/></td>
				       <td>${tdDaNewFoodDetailsList.foodAmount}</td>
				       <td>${tdDaNewFoodDetailsList.foodAmountAftRes}</td>
				       <td>${tdDaNewFoodDetailsList.claimedAmount}</td>
				    </c:otherwise>
				    </c:choose>
				</tr>
			<% l++; %>
			</c:forEach>
		</table> 
	</div>
	</c:if>
	
	</div>
	<c:choose>
	<c:when test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending') && workflowmap.tdDaNewFoodDetailsList!='[]' && workflowmap.tdDaNewFoodDetailsList!=null}">
	<a id="foodDayRepID" href="javascript:foodAmtDayRepresentation('${workflowmap.requestId}','${workflowmap.requesterSfid}','${workflowmap.roleInstanceName}');showTotalFoodAmount();individualTotAmount('all');">Generate Day Representation For Food</a>
	<div class="line">
	   <jsp:include page="TadaTdFoodDetailsDayRep.jsp"></jsp:include>
	</div>
	
	</c:when>
	
	<c:otherwise>
	<div class="line">
	   <jsp:include page="TadaTdFoodDetailsDayRep.jsp"></jsp:include>
	</div>
	
	</c:otherwise>
	</c:choose>
	<c:if test="${workflowmap.tdDaOldDetailsList!='[]'}">
	<div class="headTitle">Stay DA and Journey DA Details</div>
	<div class="line">
		<table BORDER="1" cellpadding="2" cellspacing="0" id="daOldDetailsId" style="width:100%" class="sub_2">
			<tr>
				<td rowspan="1" class="tabletd" align="center" >Date</td>
				<td rowspan="1" class="tabletd" align="center" >JDA in days</td>
				<td rowspan="1" class="tabletd" align="center" >Amount Per day</td>
				<td rowspan="1" class="tabletd" align="center" >Claimed JDA Amount</td>
				<td rowspan="1" class="tabletd" align="center" >SDA in days</td>
				<td rowspan="1" class="tabletd" align="center" >Amount Per day</td>
				<td rowspan="1" class="tabletd" align="center">Claimed SDA Amount</td>
			</tr>
			<%int a=0; %>
			<c:forEach var="tdDaOldDetailsList" items="${workflowmap.tdDaOldDetailsList}">
			<c:choose>
			<c:when test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
			<tr>
					<td><input type="hidden" value="${tdDaOldDetailsList.id}" /><input type="text" maxlength="70" style="width: 80px;" id="oldDaDate<%=a %>" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaOldDetailsList.daOldDate}"/>" onfocus ="javascript:Calendar.setup({inputField :'oldDaDate<%=a %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" /></td>
					<td><input type="text" maxlength="70" style="width: 125px;text-align: right;" id="jdaDays<%=a %>" value="${tdDaOldDetailsList.jdaDays}" onkeypress="javascript:return checkFloat(event,'jdaDays<%=a %>');" onkeyup="javascript:setJdaSdaTotal();individualTotAmount('daOldDetailsId');" /></td>
					<td><input type="text" maxlength="70" style="width: 125px;text-align: right;" id="jdaAmount<%=a %>" value="${tdDaOldDetailsList.jdaAmount}" onkeypress="javascript:return checkFloat(event,'jdaAmount<%=a %>');" onkeyup="javascript:setJdaSdaTotal();individualTotAmount('daOldDetailsId');" /></td>
					<td><input type="text" maxlength="70" style="width: 125px;text-align: right;" id="jdaTotalJdaAmt<%=a %>" readonly="readonly" value="${tdDaOldDetailsList.totalJdaAmount}" /></td>
					<td><input type="text" maxlength="70" style="width: 125px;text-align: right;" id="sdaDays<%=a %>" value="${tdDaOldDetailsList.sdaDays}" onkeypress="javascript:return checkFloat(event,'sdaDays<%=a %>');" onkeyup="javascript:setJdaSdaTotal();individualTotAmount('daOldDetailsId');" /></td>
					<td><input type="text" maxlength="70" style="width: 125px;text-align: right;" id="sdaAmount<%=a %>" value="${tdDaOldDetailsList.sdaAmount}" onkeypress="javascript:return checkFloat(event,'sdaAmount<%=a %>');" onkeyup="javascript:setJdaSdaTotal();individualTotAmount('daOldDetailsId');" /></td>
					<td><input type="text" maxlength="70" style="width: 125px;text-align: right;" id="sdaTotalJdaAmt<%=a %>" readonly="readonly" value="${tdDaOldDetailsList.totalSdaAmount}" /></td>
			</tr>
			</c:when>
			<c:otherwise>
			<tr>
					<td><fmt:formatDate pattern="dd-MMM-yyyy" value="${tdDaOldDetailsList.daOldDate}"/></td>
					<td>${tdDaOldDetailsList.jdaDays}</td>
					<td>${tdDaOldDetailsList.jdaAmount}</td>
					<td>${tdDaOldDetailsList.totalJdaAmount}</td>
					<td>${tdDaOldDetailsList.sdaDays}</td>
					<td>${tdDaOldDetailsList.sdaAmount}</td>
					<td>${tdDaOldDetailsList.totalSdaAmount}</td>
			</tr>
			</c:otherwise>
			</c:choose>
			<% a++; %>
			</c:forEach>
		</table> 
		<table border="1" style="width:100%">
		<tr>
				<td rowspan="1" class="tabletd" align="right" ><b>Stay DA and Journey DA Total(C)</b></td>
				<td rowspan="1" class="tabletd" align="right" >
				<div id="totStayJourneyDAAmount"></div>
				</td>
			</tr>
			</table>
	</div>
	</c:if>
	
	<c:if test="${workflowmap.tdRMAKmList!='[]'}">
	
	<div class="headTitle">Daily RMA At TD Place Details</div>
	<div class="line">
		<table BORDER="1" cellpadding="2" cellspacing="0" id="RMAKmDetailsId" style="width:100%" class="sub_2">
			<tr>
			    <td class="tabletd" align="center" >Date</td>
				<td class="tabletd" align="center" >From Place</td>
				<td class="tabletd" align="center" >To Place</td>
				<td id="travelBy" class="tabletd" align="center" >Travel By</td>
				<td id="distance" class="tabletd" align="center" >Distance (in k.m)</td>
				<td id="distance" class="tabletd" align="center" ><font color="blue">Distance after Res.(in k.m)</font></td>
				<td id="amountPerKm" class="tabletd" align="center" >Amount (per k.m)</td>
				<td id="amountPerKm" class="tabletd" align="center" ><font color="blue">Amount after Res.(per k.m)</font></td>
				<td id="totalAmount" class="tabletd" align="center" >Claimed Amount in <font size="4.5em"><span class="WebRupee" >R</span></font></td>
				<td rowspan="1" class="tabletd" align="center"><font color="blue">Claimed Amount after Res. <font size="4.5em"><span class="WebRupee" >R</span></font>(E)</font></td>
				<c:if test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
				 <td rowspan="1" class="tabletd" align="center" >Add</td>
				 <td rowspan="1" class="tabletd" align="center" >Del</td>
				</c:if>
			</tr>
			<%int m=0; %>
			<c:forEach var="tdRMAKmList" items="${workflowmap.tdRMAKmList}">
				<tr id="tdRMAKmRow<%=m %>">
				    <c:choose>
				    <c:when test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
				      <td><input type="hidden" value="${tdRMAKmList.id}" /><input type="text" maxlength="100" id="rmaKmDate<%=m %>" size="9px" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdRMAKmList.dateRMAKm}" />" onfocus ="javascript:Calendar.setup({inputField :'rmaKmDate<%=m %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" /></td>
				      <td><input type="text" maxlength="150" id="rmaKmFromPlace<%=m %>" size="8px" value="${tdRMAKmList.fromPlace}" onkeypress="javascript:return isAlphaNumaricExp(event);" autocomplete="off" /></td>
				      <td><input type="text" maxlength="150"id="rmaKmToPlace<%=m %>" size="8px" value="${tdRMAKmList.toPlace}" onkeypress="javascript:return isAlphaNumaricExp(event);" autocomplete="off" /></td>
				      <td><input type="text" maxlength="100" id="rmaKmTravelBy<%=m %>" size="8px" value="${tdRMAKmList.travelBy}" onkeypress="javascript:return isAlphaNumaricExp(event);" autocomplete="off" /></td>
				      <td><input type="text" maxlength="100" id="rmaKmDiatance<%=m %>" style="text-align: right;" size="5px" value="${tdRMAKmList.distance}" onkeypress="javascript:return checkFloat(event,'rmaKmDiatance<%=m %>');" autocomplete="off" /></td>
				      <td><input type="text" maxlength="100" id="rmaKmDiatanceAftRes<%=m %>" style="text-align: right;background-color: pink" size="10px" value="${tdRMAKmList.distanceAftRes}" onkeypress="javascript:return checkFloat(event,'rmaKmDiatanceAftRes<%=m %>');" onkeyup="javascript:enableRMAKmClaimedAmount();individualTotAmount('RMAKmDetailsId');" autocomplete="off" /></td>
				      <td><input type="text" maxlength="100" id="rmaKmAmountPerKm<%=m %>" style="text-align: right;" size="5px" value="${tdRMAKmList.amountPerKm}" onkeypress="javascript:return checkFloat(event,'rmaKmAmountPerKm<%=m %>');" /></td>
				      <td><input type="text" maxlength="100" id="rmaKmAmountPerKmAftRes<%=m %>" style="text-align: right;background-color: pink" size="10px" value="${tdRMAKmList.amountPerKmAftRes}" onkeypress="javascript:return checkFloat(event,'rmaKmAmountPerKmAftRes<%=m %>');" onkeyup="javascript:enableRMAKmClaimedAmount();individualTotAmount('RMAKmDetailsId');" autocomplete="off" /></td>
				      <td><input type="text" maxlength="100" id="rmaKmClaimedAmount<%=m %>" style="text-align: right;" size="10px" value="${tdRMAKmList.totalRMAKmAmount}" onkeypress="javascript:return checkFloat(event,'rmaKmClaimedAmount<%=m %>');" /></td>
				      <td><input type="text" maxlength="100" id="rmaKmClaimedAmountAftRes<%=m %>" style="text-align: right;" size="10px" value="${tdRMAKmList.amountAftRestriction}" onkeypress="javascript:return checkFloat(event,'rmaKmClaimedAmountAftRes<%=m %>');" onkeyup="javascript:individualTotAmount('RMAKmDetailsId');" autocomplete="off" /></td>
				      <td><input type="button" id="add0" value="+" onclick="javascript:checkFinRMAKmRow(<%=m %>);"/></td>
					  <td><input type="button" id="del0" value="-" onclick="javascript:deleteFinRMAKmRow(this,'RMAKmDetailsId');" /></td>
				    </c:when>
				    <c:otherwise>
				      <td><fmt:formatDate pattern="dd-MMM-yyyy" value="${tdRMAKmList.dateRMAKm}"/></td>
				      <td>${tdRMAKmList.fromPlace}</td>
				      <td>${tdRMAKmList.toPlace}</td>
					  <td>${tdRMAKmList.travelBy}</td>
					  <td>${tdRMAKmList.distance}</td>
					  <td>${tdRMAKmList.distanceAftRes}</td>
					  <td>${tdRMAKmList.amountPerKm}</td>
					  <td>${tdRMAKmList.amountPerKmAftRes}</td>
					  <td>${tdRMAKmList.totalRMAKmAmount}</td>
					  <td>${tdRMAKmList.amountAftRestriction}</td>
				    </c:otherwise>
				    </c:choose>
				</tr>
			<% m++; %>
			</c:forEach>
			
		</table> 
		<table border="1" style="width:100%">
		<tr>
				<td rowspan="1" class="tabletd" align="right" ><b>Daily RMA At TD Place Total(E)</b></td>
				<td rowspan="1" class="tabletd" align="right" >
				<div id="totDailyRmaKMAmount"></div>
				</td>
			</tr>
			</table>
	</div>
	</c:if>
	<c:if test="${workflowmap.tdRMADayList!='[]' && workflowmap.tdRMADayList!=null}">
	<div class="headTitle">Daily RMA At TD Place Details</div>
	<div class="line">
		<table BORDER="1" cellpadding="2" cellspacing="0" id="RMADayDetailsId" style="width:100%" class="sub_2">
			<tr>
			    <td class="tabletd" align="center" >Date</td>
				<td class="tabletd" align="center" >From Place</td>
				<td class="tabletd" align="center" >To Place</td>
				<td id="amountPerDay" class="tabletd" align="center" >Amount (per day)</td>
				<td id="amountPerDay" class="tabletd" align="center" ><font color="blue">Amount after Res.(per day)</font></td>
				<td id="totalAmount" class="tabletd" align="center" >Claimed Amount in <font size="4.5em"><span class="WebRupee" >R</span></font></td>
				<td rowspan="1" class="tabletd" align="center"><font color="blue">Claimed Amount after Res. <font size="4.5em"><span class="WebRupee" >R</span></font>(E)</font></td>
				<c:if test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
				 <td rowspan="1" class="tabletd" align="center" >Add</td>
				 <td rowspan="1" class="tabletd" align="center" >Del</td>
				</c:if>
			</tr>
			<%int n=0;  %>
			<c:forEach var="tdRMADayList" items="${workflowmap.tdRMADayList}">
				<tr id="tdRMADayRow<%=n %>">
				   <c:choose>
				    <c:when test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
				      <td><input type="hidden" value="${tdRMADayList.id}" /><input type="text" maxlength="100" id="rmaDayDate<%=n %>" size="10px" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdRMADayList.dateRMADay}"/>" onfocus ="javascript:Calendar.setup({inputField :'rmaDayDate<%=n %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" /></td>
				      <td><input type="text" maxlength="100" id="rmaDayFromPlace<%=n %>" size="10px" value="${tdRMADayList.fromPlace}" onkeypress="javascript:return isAlphaNumaricExp(event);" autocomplete="off"/></td>
				      <td><input type="text" maxlength="100" id="rmaDayToPlace<%=n %>" size="10px" value="${tdRMADayList.toPlace}" onkeypress="javascript:return isAlphaNumaricExp(event);" autocomplete="off"/></td>
				      <td><input type="text" maxlength="100" id="rmaDayAmountPerDay<%=n %>" style="text-align: right;" size="15px" value="${tdRMADayList.amountPerDay}" onkeypress="javascript:return checkFloat(event,'rmaDayAmountPerDay<%=n %>');" autocomplete="off"/></td>
				      <td><input type="text" maxlength="100" id="rmaDayAmountPerDayAftRes<%=n %>" style="text-align: right;background-color: pink" size="18px" value="${tdRMADayList.amountPerDayAftRes}" onkeypress="javascript:return checkFloat(event,'rmaDayAmountPerDayAftRes<%=n %>');" onkeyup="javascript:enableRMADayClaimAmt();individualTotAmount('RMADayDetailsId');" autocomplete="off"/></td>
				      <td><input type="text" maxlength="100" id="rmaDayClaimedAmount<%=n %>" style="text-align: right;" size="18px" value="${tdRMADayList.totalRMADayAmount}" onkeypress="javascript:return checkFloat(event,'rmaDayClaimedAmount<%=n %>');" /></td>
				      <td><input type="text" maxlength="100" id="rmaDayClaimedAmountAftRes<%=n %>" style="text-align: right;" size="18px" value="${tdRMADayList.amountAftRestriction}" onkeypress="javascript:return checkFloat(event,'rmaDayClaimedAmountAftRes<%=n %>');" onkeyup="javascript:individualTotAmount('RMADayDetailsId');" autocomplete="off" /></td>
				      <td><input type="button" id="add0" value="+" onclick="javascript:checkFinRMADayRow(<%=n %>);"/></td>
					  <td><input type="button" id="del0" value="-" onclick="javascript:deleteFinRMADayRow(this,'RMADayDetailsId');" /></td>
				    </c:when>
				    <c:otherwise>
				      <td><fmt:formatDate pattern="dd-MMM-yyyy" value="${tdRMADayList.dateRMADay}"/></td>
				      <td>${tdRMADayList.fromPlace}</td>
					  <td>${tdRMADayList.toPlace}</td>
					  <td>${tdRMADayList.amountPerDay}</td>
					  <td>${tdRMADayList.amountPerDayAftRes}</td>
					  <td>${tdRMADayList.totalRMADayAmount}</td>
					  <td>${tdRMADayList.amountAftRestriction}</td>
				    </c:otherwise>
				    </c:choose>
				</tr>
			<% n++; %>
			</c:forEach>
			
		</table> 
		<table border="1" style="width:100%">
		<tr>
				<td rowspan="1" class="tabletd" align="right" ><b>Daily RMA At TD Place Total(E)</b></td>
				<td rowspan="1" class="tabletd" align="right" >
				<div id="totDailyRMAAmount"></div>
				</td>
			</tr>
			</table>
	</div>
	</c:if>
	
	
	<c:if test="${workflowmap.tdRMADailyList!='[]' && workflowmap.tdRMADailyList!=null}">
	<div class="headTitle">RMA At TD Place Details</div>
	<div class="line">
		<table BORDER="1" cellpadding="2" cellspacing="0" id="RMADailyDetailsId" style="width:100%" class="sub_2">
			<tr>
			    <td class="tabletd" align="center" >Date</td>
				<td class="tabletd" align="center" >From Place</td>
				<td class="tabletd" align="center" >To Place</td>
				<td id="travelBy" class="tabletd" align="center" >Travel By</td>
				<td id="distance" class="tabletd" align="center" >Distance (in k.m)</td>
				<td id="distance" class="tabletd" align="center" ><font color="blue">Distance after Res.(in k.m)</font></td>
				<td id="amountPerKm" class="tabletd" align="center" >Amount (per k.m)</td>
				<td id="amountPerKm" class="tabletd" align="center" ><font color="blue">Amount after Res.(per k.m)</font></td>
				<td id="totalAmount" class="tabletd" align="center" >Claimed Amount in <font size="4.5em"><span class="WebRupee" >R</span></font></td>
				<td rowspan="1" class="tabletd" align="center"><font color="blue">Claimed Amount after Res.<font size="4.5em"><span class="WebRupee" >R</span></font>(F)</font></td>
				<c:if test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
				 <td rowspan="1" class="tabletd" align="center" >Add</td>
				 <td rowspan="1" class="tabletd" align="center" >Del</td>
				</c:if>
			</tr>
			<%int o=0; %>
			<c:forEach var="tdRMADailyList" items="${workflowmap.tdRMADailyList}">
				<tr id="tdRMADailyRow<%=o %>">
				    <c:choose>
				    <c:when test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
				      <td><input type="hidden" value="${tdRMADailyList.id}" /><input type="text" maxlength="100" id="rmaDailyDate<%=o %>" size="9px" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdRMADailyList.dateRMAKm}" />" onfocus ="javascript:Calendar.setup({inputField :'rmaDailyDate<%=o %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" /></td>
				      <td><input type="text" maxlength="100" id="rmaDailyFromPlace<%=o %>" size="8px" value="${tdRMADailyList.fromPlace}" onkeypress="javascript:return isAlphaNumaricExp(event);" /></td>
				      <td><input type="text" maxlength="100" id="rmaDailyToPlace<%=o %>" size="8px" value="${tdRMADailyList.toPlace}" onkeypress="javascript:return isAlphaNumaricExp(event);" /></td>
				      <td><input type="text" maxlength="100" id="rmaDailyTravelBy<%=o %>" size="8px" value="${tdRMADailyList.travelBy}" onkeypress="javascript:return isAlphaNumaricExp(event);" /></td>
				      <td><input type="text" maxlength="100" id="rmaDailyDiatance<%=o %>" style="text-align: right;" size="5px" value="${tdRMADailyList.distance}" onkeypress="javascript:return checkFloat(event,'rmaDailyDiatance<%=o %>');" /></td>
				      <td><input type="text" maxlength="100" id="rmaDailyDiatanceAftRes<%=o %>" style="text-align: right;background-color: pink" size="10px" value="${tdRMADailyList.distanceAftRes}" onkeypress="javascript:return checkFloat(event,'rmaDailyDiatanceAftRes<%=o %>');" onkeyup="javascript:enableRMADailyClaimedAmount();individualTotAmount('RMADailyDetailsId');" /></td>
				      <td><input type="text" maxlength="100" id="rmaDailyAmountPerKm<%=o %>" style="text-align: right;" size="5px" value="${tdRMADailyList.amountPerKm}" onkeypress="javascript:return checkFloat(event,'rmaDailyAmountPerKm<%=o %>');" /></td>
				      <td><input type="text" maxlength="100" id="rmaDailyAmountPerKmAftRes<%=o %>" style="text-align: right;background-color: pink" size="10px" value="${tdRMADailyList.amountPerKmAftRes}" onkeypress="javascript:return checkFloat(event,'rmaDailyAmountPerKmAftRes<%=o %>');" onkeyup="javascript:enableRMADailyClaimedAmount();individualTotAmount('RMADailyDetailsId');" /></td>
				      <td><input type="text" maxlength="100" id="rmaDailyClaimedAmount<%=o %>" style="text-align: right;" size="10px" value="${tdRMADailyList.totalRMAKmAmount}" onkeypress="javascript:return checkFloat(event,'rmaDailyClaimedAmount<%=o %>');" /></td>
				      <td><input type="text" maxlength="100" id="rmaDailyClaimedAmountAftRes<%=o %>" style="text-align: right;" size="10px" value="${tdRMADailyList.amountAftRestriction}" onkeypress="javascript:return checkFloat(event,'rmaDailyClaimedAmountAftRes<%=o %>');" onkeyup="javascript:individualTotAmount('RMADailyDetailsId');" /></td>
				      <td><input type="button" id="add0" value="+" onclick="javascript:checkFinRMADailyRow(<%=o %>);"/></td>
					  <td><input type="button" id="del0" value="-" onclick="javascript:deleteFinRMADailyRow(this,'RMADailyDetailsId');" /></td>
				    </c:when>
				    <c:otherwise>
				      <td><fmt:formatDate pattern="dd-MMM-yyyy" value="${tdRMADailyList.dateRMAKm}"/></td>
				      <td>${tdRMADailyList.fromPlace}</td>
				      <td>${tdRMADailyList.toPlace}</td>
					  <td>${tdRMADailyList.travelBy}</td>
					  <td>${tdRMADailyList.distance}</td>
					  <td>${tdRMADailyList.distanceAftRes}</td>
					  <td>${tdRMADailyList.amountPerKm}</td>
					  <td>${tdRMADailyList.amountPerKmAftRes}</td>
					  <td>${tdRMADailyList.totalRMAKmAmount}</td>
					  <td>${tdRMADailyList.amountAftRestriction}</td>
				    </c:otherwise>
				    </c:choose>
				</tr>
			<% o++; %>
			</c:forEach>
			
		</table> 
		<table border="1" style="width:100%">
		<tr>
				<td rowspan="1" class="tabletd" align="right" ><b>RMA At TD Place Total(F)</b></td>
				<td rowspan="1" class="tabletd" align="right" >
				<div id="totRMAAtTdAmount"></div>
				</td>
			</tr>
			</table>
	</div>
	</c:if>
	
	<c:if test="${workflowmap.tdRMALocalList!='[]'}">
	<div class="headTitle">Local RMA Details</div>
	<div class="line">
		<table BORDER="1" cellpadding="2" cellspacing="0" id="RMALocalDetailsId" style="width:100%" class="sub_2 tdsett">
			<tr>
			    <td class="tabletd" align="center" >Date</td>
				<td class="tabletd" align="center" >From Place</td>
				<td class="tabletd" align="center" >To Place</td>
				<td id="travelBy" class="tabletd" align="center" >Mode of conveyance</td>
				<td id="distance" class="tabletd" align="center" >Distance (in k.m)</td>
				<td id="distance" class="tabletd" align="center" ><font color="blue">Distance after Res.(in k.m)</font></td>
				<td id="amountPerKm" class="tabletd" align="center" >Amount (per k.m)</td>
				<td id="amountPerKm" class="tabletd" align="center" ><font color="blue">Amount after Res.(per k.m)</font></td>
				<td id="totalAmount" class="tabletd" align="center" >Claimed Amount in <font size="4.5em"><span class="WebRupee" >R</span></font></td>
				<td rowspan="1" class="tabletd" align="center"><font color="blue">Claimed Amount after Res.<font size="4.5em"><span class="WebRupee" >R</span></font>(G)</font></td>
				<c:if test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
				 <td rowspan="1" class="tabletd" align="center" >Add</td>
				 <td rowspan="1" class="tabletd" align="center" >Del</td>
				</c:if>
			</tr>
			<%int p=0; %>
			<c:forEach var="tdRMALocalList" items="${workflowmap.tdRMALocalList}">
				<tr id="tdRMALocalRow<%=p %>">
				    <c:choose>
				    <c:when test="${(workflowmap.roleInstanceName eq 'TADA TASK HOLDER' || workflowmap.roleInstanceName eq 'TA DA /Medical Section Head') && (workflowmap.checkStage eq 'previous' || workflowmap.checkStage eq 'last') && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
				      <td><input type="hidden" value="${tdRMALocalList.id}" /><input type="text" maxlength="100" id="rmaLocalDate<%=p %>" size="9px" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${tdRMALocalList.localRMADate}"/>" onfocus ="javascript:Calendar.setup({inputField :'rmaLocalDate<%=p %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" /></td>
				      <td><input type="text" maxlength="100" id="rmaLocalFromPlace<%=p %>" size="8px" value="${tdRMALocalList.localFromPlace}" onkeypress="javascript:return isAlphaNumaricExp(event);" /></td>
				      <td><input type="text" maxlength="100" id="rmaLocalToPlace<%=p %>" size="8px" value="${tdRMALocalList.localToPlace}" onkeypress="javascript:return isAlphaNumaricExp(event);" /></td>
				      <td><input type="text" maxlength="100" id="rmaLocalTravelBy<%=p %>" size="8px" value="${tdRMALocalList.localCMode}" onkeypress="javascript:return isAlphaNumaricExp(event);" /></td>
				      <td><input type="text" maxlength="100" id="rmaLocalDiatance<%=p %>" style="text-align: right;" size="5px" value="${tdRMALocalList.localDistance}" onkeypress="javascript:return checkFloat(event,'rmaLocalDiatance<%=p %>');" /></td>
				      <td><input type="text" maxlength="100" id="rmaLocalDiatanceAftRes<%=p %>" style="text-align: right;background-color: pink" size="10px" value="${tdRMALocalList.localDistanceAftRes}" onkeypress="javascript:return checkFloat(event,'rmaLocalDiatanceAftRes<%=p %>');" onkeyup="javascript:enableRMALocalClaimedAmount();individualTotAmount('RMALocalDetailsId');" /></td>
				      <td><input type="text" maxlength="100" id="rmaLocalAmountPerKm<%=p %>" style="text-align: right;" size="5px" value="${tdRMALocalList.amountPerKmLocal}" onkeypress="javascript:return checkFloat(event,'rmaLocalAmountPerKm<%=p %>');" /></td>
				      <td><input type="text" maxlength="100" id="rmaLocalAmountPerKmAftRes<%=p %>" style="text-align: right;background-color: pink" size="10px" value="${tdRMALocalList.amountPerKmLocalAftRes}" onkeypress="javascript:return checkFloat(event,'rmaLocalAmountPerKmAftRes<%=p %>');" onkeyup="javascript:enableRMALocalClaimedAmount();individualTotAmount('RMALocalDetailsId');" /></td>
				      <td><input type="text" maxlength="100" id="rmaLocalClaimedAmount<%=p %>" style="text-align: right;" size="10px" value="${tdRMALocalList.claimedAmount}" onkeypress="javascript:return checkFloat(event,'rmaLocalClaimedAmount<%=p %>');" /></td>
				      <td><input type="text" maxlength="100" id="rmaLocalClaimedAmountAftRes<%=p %>" style="text-align: right;" size="10px" value="${tdRMALocalList.claimedAmountAftRes}" onkeypress="javascript:return checkFloat(event,'rmaLocalClaimedAmountAftRes<%=p %>');" onkeyup="javascript:individualTotAmount('RMALocalDetailsId');" /></td>
				      <td><input type="button" id="add0" value="+" onclick="javascript:checkFinRMALocalRow(<%=p %>);"/></td>
					  <td><input type="button" id="del0" value="-" onclick="javascript:deleteFinRMALocalRow(this,'RMALocalDetailsId');" /></td>
				    </c:when>
				    <c:otherwise>
				      <td><fmt:formatDate pattern="dd-MMM-yyyy" value="${tdRMALocalList.localRMADate}"/></td>
				      <td>${tdRMALocalList.localFromPlace}</td>
					  <td>${tdRMALocalList.localToPlace}</td>
					  <td>${tdRMALocalList.localCMode}</td>
					  <td>${tdRMALocalList.localDistance}</td>
					  <td>${tdRMALocalList.localDistanceAftRes}</td>
					  <td>${tdRMALocalList.amountPerKmLocal}</td>
					  <td>${tdRMALocalList.amountPerKmLocalAftRes}</td>
					  <td>${tdRMALocalList.claimedAmount}</td>
					  <td>${tdRMALocalList.claimedAmountAftRes}</td>
				    </c:otherwise>
				    </c:choose>
				</tr>
			<% p++; %>
			</c:forEach>
			
		</table> 
		<table border="1" style="width:100%">
		<tr>
				<td rowspan="1" class="tabletd" align="right" ><b>Local RMA Total(G)</b></td>
				<td rowspan="1" class="tabletd" align="right" >
				<div id="totLocalRMAAmount"></div>
				</td>
			</tr>
			</table>
	</div>
	</c:if>
	
	
	
	    <div class="line">
	        <div class="quarter bold">Finance Issued Amount:</div></br>
	        <div class="quarter bold">(Advance Taken Amount)</div>
	        <div class="quarter"><font size="4.5em"><span class="WebRupee" >R</span></font>
	        <c:choose>
	        <c:when test="${workflowmap.cdaDetailsDTO.cdaAmount==null}">0/-
	        </c:when>
	        <c:otherwise>
	             ${workflowmap.cdaDetailsDTO.cdaAmount}/-
	         </c:otherwise>
	         </c:choose>
	        </div>
			<div class="quarter bold">Finance Calculated Total Amount:(A+B+.....+G)</div>
			<div class="quarter">
			   <font size="4.5em"><span class="WebRupee" >R</span></font>
			   <input type="text" style="text-align: right" readonly="readonly" name="totalTadaTdCalAmount" id="totalTadaTdCalAmount" />
			   <div id="totalBeforeRound"> </div>
	        </div>
		</div>
		<div class="line">
			<div id="excessAmountDiv">
				<div class="quarter bold" style='width:34%'>Balance /Recovery due(Excess Amount)</div>
				<div class="quarter">
				<font size="4.5em"><span class="WebRupee" >R</span></font>
				   <input type="text" name="excessAmount" id="excessAmount" value="0" style="text-align: right" readonly="readonly" />
				</div>
			</div>
		</div>
			<c:if test="${workflowmap.roleInstanceName eq 'TA DA /Medical Section Head' or workflowmap.roleInstanceName eq 'TADA TASK HOLDER'}">
		<div id="penalInterestGrid" class="line" style="display:none">
		<fieldset><legend><strong><font color='green'>Penal Interest Details</font></strong></legend>
			<table style="width:100%" border="1" class="list" id="penalInterestTabId" class="sub_2">
				<tr>
					<th style="width:20%;text-align:center;color: blue">Penal Interest Imposement</th>
					<th style="width:8%;text-align:center;color: blue">No Of Days</th>
					<th style="width:6%;text-align:center;color: blue">Interest rate</th>
					<th style="width:8%;text-align:center;color: blue">Unutilized Balance</th>
					<th style="width:10%;text-align:center;color: blue">Penal Interest</th>
					<th style="width:11%;text-align:center;color: blue">Total Amount</th>
					<th style="width:7%;text-align:center;color: blue">MRO No.</th>
					<th style="width:10%;text-align:center;color: blue">MRO Date</th>
					<th style="width:5%;text-align:center;color: blue">Report</th>
					<th style="width:5%;text-align:center;color: blue">Add</th>
					<th style="width:5%;text-align:center;color: blue">Del</th>
				</tr>
				<c:if test="${workflowmap.mroDetailsList!=null && workflowmap.mroDetailsList!='[]' && workflowmap.mroDetailsList!=''}">
				<%int i=0; %>
				<c:forEach var="mroDetailsList" items="${workflowmap.mroDetailsList}">
				<tr id="penalInterestRow<%=i %>">
				<c:choose>
				<c:when test="${mroDetailsList.noOfDays==0}">
				<td><input type="hidden" value="${mroDetailsList.id}"/>NO</td>
				</c:when><c:otherwise><td><input type="hidden" value="${mroDetailsList.id}"/>YES</td></c:otherwise>
				</c:choose>
				  
				    <td>${mroDetailsList.noOfDays}<input type="hidden" id="penalID0" value="${workflowmap.ltcPenalInterestRate.id}" /></td>
				    <td>${workflowmap.ltcPenalInterestRate.typeValue}</td>
				      <c:choose><c:when test="${mroDetailsList.mroPaymentDetailsDTO.mroNumber==null || mroDetailsList.mroPaymentDetailsDTO.mroNumber=='[]' || mroDetailsList.mroPaymentDetailsDTO.mroNumber==''}">
				    <td><input type="text" readonly="readonly" id="unUtilizedBal<%=i %>" style="width:75px " value="${mroDetailsList.unUtilizedBalance}"/></td>
				    <td>${mroDetailsList.penalInterestAmount}</td>
				      <td><input type="text" disabled="disabled" id="totMroAmount<%=i %>" style="width:75px" value="${mroDetailsList.totalAmount}"/></td>
				    
				    </c:when>
				   <c:otherwise>
				   <td>${mroDetailsList.unUtilizedBalance}
				   </td>
				   <td>${mroDetailsList.penalInterestAmount}</td>
				      <td>${mroDetailsList.totalAmount}</td></c:otherwise>
				   </c:choose>
				     <%-- <td>${mroDetailsList.unUtilizedBalance}</td> --%>
				   <%--  <td>${mroDetailsList.penalInterestAmount}</td>
				      <td>${mroDetailsList.totalAmount}</td> --%>
				    <c:if test="${mroDetailsList.mroPaymentDetailsDTO==null || mroDetailsList.mroPaymentDetailsDTO=='[]' || mroDetailsList.mroPaymentDetailsDTO==''}">
				    <td><input type="text" id="MROPaidNo<%=i %>" style="width:100px" onkeypress="javascript:return checkFloat(event,'MROPaidNo<%=i %>');"/></td>
					<td><input type="text" id="MROPaidDate<%=i %>" style="height:16px;width:100px;font-size: 9px;font-weight: bold;"  id="MROPaidDate<%=i %>" readonly="readonly" onfocus ="javascript:Calendar.setup({inputField :'MROPaidDate<%=i %>',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" /></td>
					<td><a href="javascript:getMROReport('${workflowmap.requestId}','${mroDetailsList.id}','tadaTdMRODetails')">PDF</a></td>
					<td><input type="button" id="penalInterestAdd<%=i %>" value="+" onclick="javascript:createPenalInterestRow('penalInterestTabId','<%=i %>');"/></td>
					<td><input type="button" id="penalInterestDel<%=i %>" value="-" style="display: none" /></td>
				    </c:if>
				    <c:if test="${mroDetailsList.mroPaymentDetailsDTO!=null && mroDetailsList.mroPaymentDetailsDTO!='[]' && mroDetailsList.mroPaymentDetailsDTO!=''}">
				    <td>${mroDetailsList.mroPaymentDetailsDTO.mroNumber}</td>
					<td><fmt:formatDate pattern="dd-MMM-yyyy" value="${mroDetailsList.mroPaymentDetailsDTO.mroDate}"/></td>
					<td><a href="javascript:getMROReport('${workflowmap.requestId}','${mroDetailsList.id}','tadaTdMRODetails')">PDF</a></td>
					<td></td>
					<td></td>
				    </c:if>
				</tr>
				<% i++; %>
				</c:forEach>
				</c:if>
				<c:if test="${workflowmap.mroDetailsList==null || workflowmap.mroDetailsList=='[]' || workflowmap.mroDetailsList==''}">
				 <tr id="penalInterestRow0">
					<td >
						<input type="radio" name="penalInterestReq0" id="penalInterestReq10" value="yes" onclick="javascript:showPenalInterestDetails();" /><label>Yes</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="penalInterestReq0" id="penalInterestReq20" value="no" onclick="javascript:showPenalInterestDetails();calculateTadaPenalFines('${workflowmap.cdaDetailsDTO.cdaAmount}')" /><label>No</label>
					 
					</td>
					
					<td ><input type="text" name="noOfDays0" id="noOfDays0" style="width:60px;background-color:pink" onkeypress="javascript:return checkInt(event);" onkeyup="javascript:calculateTadaPenalFines('${workflowmap.cdaDetailsDTO.cdaAmount}');"/><input type="hidden" id="penalID0" value="${workflowmap.ltcPenalInterestRate.id}" /></td>
					<td>
					<input type="text" readonly="readonly" id="interestRate0" style="width:60px" value="${workflowmap.ltcPenalInterestRate.typeValue}" />
					</td>
					<td><input type="text" readonly="readonly" id="unUtilizedBal0" style="width:75px background-color:pink" /></td>	
					<td><input type="text" name="excessAmountFine0" id="excessAmountFine0"   disabled="disabled" style="width:75px" onkeypress="javascript:return checkFloat(event,'excessAmountFine0');"/></td>
					
					<td><input type="text" disabled="disabled" id="totMroAmount0" style="width:75px"/>
					<input type="hidden" name="penalInterestId" id="penalInterestId" value="${workflowmap.ltcPenalInterestRate.id}"/></td>
					
					<c:if test="${workflowmap.roleInstanceName eq 'TA DA /Medical Section Head'}">
					<td><input type="text" id="MROPaidNo" style="width:100px"  onkeypress="javascript:return checkFloat(event,'MROPaidNo'); "/></td>
					<td><input type="text" id="MROPaidDate" style="height:16px;width:100px;font-size: 9px;font-weight: bold;"  id="MROPaidDate" readonly="readonly" onfocus ="javascript:Calendar.setup({inputField :'MROPaidDate',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" /></td>
                   
				   </c:if>
				</tr>
				</c:if>
			</table>
		</fieldset>		
		</div>
		</c:if>
		
	
	<form:hidden path="daNewAmount" id="daNewAmount"/>
	<form:hidden path="rmaKmAmount" id="rmaKmAmount"/>
	<form:hidden path="journeyAmount" id="journeyAmount"/>
	<form:hidden path="rmaDayAmount" id="rmaDayAmount"/>
	<form:hidden path="daOldAmount" id="daOldAmount"/>
	<form:hidden path="settleAmount" id="settleAmount"/>
	<form:hidden path="tadaAdvanceAmount" id="tadaAdvanceAmount"/>
	<form:hidden path="foodDaNewAmount" id="foodDaNewAmount"/>
	<form:hidden path="accDaNewAmount" id="accDaNewAmount"/>
	<form:hidden path="distanceAftRes" id="distanceAftRes"/>
	<form:hidden path="amountPerKmAftRes" id="amountPerKmAftRes"/>
	<form:hidden path="stayDaNewAmount" id="stayDaNewAmount"/>
	<form:hidden path="amountPerDayAftRes" id="amountPerDayAftRes"/>
	<form:hidden path="stayDaOldAmount" id="stayDaOldAmount"/>
	<form:hidden path="amountPerDay" id="amountPerDay"/>
	
	<form:hidden path="strTicketFareAftRes" id="strTicketFareAftRes"/>
	<form:hidden path="strClaimedJourneyAmtAftRes" id="strClaimedJourneyAmtAftRes"/>
	<form:hidden path="strAccAmtAftRes" id="strAccAmtAftRes"/>
	<form:hidden path="strClaimedAccAmtAftRes" id="strClaimedAccAmtAftRes"/>
	<form:hidden path="strRMAKmDisAftRes" id="strRMAKmDisAftRes"/>
	<form:hidden path="strRMAKmAmtAftRes" id="strRMAKmAmtAftRes"/>
	<form:hidden path="strClaimedRMAKmAftRes" id="strClaimedRMAKmAftRes"/>
	<form:hidden path="strRMALocalDisAftRes" id="strRMALocalDisAftRes"/>
	<form:hidden path="strRMALocalAmtAftRes" id="strRMALocalAmtAftRes"/>
	<form:hidden path="strClaimedRMALocalAftRes" id="strClaimedRMALocalAftRes"/>
	<form:hidden path="strFoodAmtAftRes" id="strFoodAmtAftRes"/>
	<form:hidden path="strRMADayAmtPerDay" id="strRMADayAmtPerDay"/>
	<form:hidden path="strRMADayAmtAftRes" id="strRMADayAmtAftRes"/>
	<form:hidden path="strRMADailyDisAftRes" id="strRMADailyDisAftRes"/>
	<form:hidden path="strRMADailyAmtAftRes" id="strRMADailyAmtAftRes"/>
	<form:hidden path="strClaimedRMADailyAftRes" id="strClaimedRMADailyAftRes"/>
		
	<c:if test="${workflowmap.mroDetailsList!=null && workflowmap.mroDetailsList!='[]' && workflowmap.mroDetailsList!=''}">
	<script>
	  removePenalAndExcessAmtDiv();
	  showPenalInterestGrid();
	</script>
	</c:if>
<script type="text/javascript">
    tadaAdvanceAmount = '${workflowmap.cdaDetailsDTO.cdaAmount}'
</script>
<script>
			daNewDetailsList = <%= (net.sf.json.JSONArray)session.getAttribute("daNewDetailsList") %>;
			WorkBeanMap = <%= (net.sf.json.JSONObject)session.getAttribute("workBeanMap") %>;
			//totalJourneyAmount();
			//totalAccommodationAmount();
			//getRepresentedTotalAmt();
			//getTadaTdTotalAmount();
			//setSettlementValues();
			 //showTotalFoodAmount();
			individualTotAmount('all');
			
			
</script>	
</div>
<div id="light123" class="aui-popup box-shadow aui-dialog-open popup-width-large aui-dialog-content-ready" style="display:none; border: 6px solid #DDDDDD; border-radius: 16px; margin-left: -156px; margin-top: -153px; padding: 16px; position:fixed; width: 490px;">
<div><div  style="background-color: #66CCFF"><center><u><b style="font-size:200%">Confirmation</b></u></center></div><div>You have to pay anothere MRO<br/> If press "YES" It will take another MRO<br/> Otherwise page will be refresh and You must fill once again this page</div>
<div id="confirmId"><input type="button" value="YES" id="confirmYes"  size="25%" onclick="javascript:confirmStatus('YES');"/>&nbsp;<input type="button" value="NO" id="confirmNo" onclick="javascript:confirmStatus('NO');"/></div></div>
</div>
<div id="light456" class="aui-blanket" style="display:none;" ></div>

<!-- End : TadaTdSettlementDetails.jsp -->
