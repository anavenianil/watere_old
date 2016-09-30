<!-- Begin : TadaTdDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/tada.js"></script>

<div>
    <c:if test="${workflowmap.tadaApprovalRequestDTO.stayDuration!=0}">
	<div class="line">
	<div style="display: none"><!--added by bkr 25/04/2016 only hidden  -->
		<div class="quarter bold">Basic Pay & Grade Pay${workflowmap.tadaApprovalRequestDTO.projectName}</div>
		<div class="quarter">:&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.tadaApprovalRequestDTO.basicPay}/- & <font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.tadaApprovalRequestDTO.gradePay}/-</div>
	</div>
		<div class="quarter bold">Address Of TD Work Place</div>
		<div class="quarter" id="tdWorkPlace">:&nbsp;${workflowmap.tadaApprovalRequestDTO.tdWorkPlace}</div>
		
		
		<!--added by bkr 26/04/2016   -->
		 <c:if test="${ not empty  workflowmap.tadaApprovalRequestDTO.purpose}">
		<div class="quarter bold">Purpose</div>
		<div class="quarter" id="purpose">:&nbsp;${workflowmap.tadaApprovalRequestDTO.purpose}</div>
		</c:if>
		
	</div>
	<div class="line">
		<div class="quarter bold">Stay Duration</div>
		<div class="quarter" id="stayDuration">: ${workflowmap.tadaApprovalRequestDTO.stayDuration} Days</div>
	<div style="display: none"><!--added by bkr 25/04/2016 only hidden  -->
		<div class="quarter bold">TD move is Authorized From</div>
		<div class="quarter" id="authorizedMove"><c:if test="${workflowmap.tadaApprovalRequestDTO.authorizedMove==1}">: Build-Up</c:if><c:if test="${workflowmap.tadaApprovalRequestDTO.authorizedMove==2}">: Project <c:if test="${workflowmap.tadaApprovalRequestDTO.projectName!=null && workflowmap.tadaApprovalRequestDTO.projectName!=''}">- ${workflowmap.tadaApprovalRequestDTO.projectName}</c:if></c:if></div>
	</div>
	</div>
	<div class="line">
	<div style="display: none"><!--added by bkr 26/04/2016 only hidden  -->
		<div class="quarter bold">DA Requirement</div>
		<div class="quarter" id="daType">: <c:if test="${workflowmap.tadaApprovalRequestDTO.daType=='hotelrate'}">Hotel Rates</c:if>
		                                   <c:if test="${workflowmap.tadaApprovalRequestDTO.daType=='normalrate'}">Normal Rates</c:if>
		                                   <c:if test="${workflowmap.tadaApprovalRequestDTO.daType=='na'}">Not Required</c:if>
		</div>
			</div>
		<div style="display: none"><!--added by bkr 25/04/2016 only hidden  -->
		<div class="quarter bold">Ticket Cancellation Charges</div>
		<div class="quarter" id="authorizedMove">: <font size="4.5em"><span class="WebRupee" >R</span></font> ${workflowmap.tadaApprovalRequestDTO.ticketCancelCharges}</div>
	</div>
	</div>
	<c:if test="${workflowmap.tadaAmendmentDetails!='' && workflowmap.tadaAmendmentDetails!=null && workflowmap.tadaAmendmentDetails!='[]'}">
	<div class="line">
		<div class="quarter bold">Reason For Amendment</div>
		<div class="quarter" id="daType">: ${workflowmap.tadaApprovalRequestDTO.remarks}</div>
	</div>
	</c:if>
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
		<table BORDER="1" cellpadding="2" cellspacing="0" id="reqJourneyDetailsId" style="width:100%" class="sub_2">
			<tr>
				<td  class="tabletd" align="center" >Departure Date</td>
				<td  class="tabletd" align="center" >Arrival Date</td>
				<td  class="tabletd" align="center" >From Place</td>
				<td  class="tabletd" align="center" >To Place</td>
				<td  class="tabletd" align="center" >No. Of Days</td>
				<td  class="tabletd" align="center" >Mode of Conveyance</td>
				<td  class="tabletd" align="center" >Class Of Entitlement</td>
				<!--commented by bkr 26/04/2016 not required   -->
				<!-- <td  class="tabletd" align="center" >Tatkal Quota</td> -->
			</tr>
			
			<c:forEach var="tadaTdReqJourneyList" items="${workflowmap.tadaTdReqJourneyList}">
				<tr>
					<td><fmt:formatDate pattern="dd-MMM-yyyy" value="${tadaTdReqJourneyList.departureDate}"/></td>
					<td><fmt:formatDate pattern="dd-MMM-yyyy" value="${tadaTdReqJourneyList.arrivalDate}"/></td>
					<td>${tadaTdReqJourneyList.fromPlace}</td>
					<td>${tadaTdReqJourneyList.toPlace}</td>
					<td>${tadaTdReqJourneyList.noOfDays}</td>
					<td>${tadaTdReqJourneyList.conveyanceMode}</td>
					<td>${tadaTdReqJourneyList.classOfEntitlement}</td>
					<!--commented by bkr 26/04/2016 not required   -->
					<%-- <td><c:if test="${tadaTdReqJourneyList.tatkalQuota=='na'}">N/A</c:if><c:if test="${tadaTdReqJourneyList.tatkalQuota=='yes'}">Required</c:if></td> --%>
				</tr>
			</c:forEach>
			
		</table> 
	</div>
	<c:if test="${workflowmap.tadaApprovalRequestDTO.authorizedMove==2}">
	  <c:if test="${(workflowmap.sfid==workflowmap.projectDirSfid || workflowmap.sfid=='SF0002') && workflowmap.statusMsg=='' && workflowmap.tadaApprovalRequestDTO.status==2}">
	    <c:if test="${workflowmap.tadaPrjDirList!=null && workflowmap.tadaPrjDirList!='' && workflowmap.tadaPrjDirList!='[]'}">
	    <div class="line">
	       <div class="quarter bold">Choose Project</div>
		   <div class="quarter" id="projectTypeDiv">: 
		     <c:choose>
		     <c:when test="${workflowmap.tadaApprovalRequestDTO.projectName==null || workflowmap.tadaApprovalRequestDTO.projectName==''}">
		     <form:select path="projectType" id="projectType" cssStyle="width:142px" >
				<form:option value="0">Select</form:option>
				<form:options items="${workflowmap.tadaPrjDirList}" itemValue="projectName"  itemLabel="projectName"/>
			 </form:select>
			 </c:when>
			 <c:otherwise>
			 <select name="projectType" id="projectType" style="width:142px">
			 <option value="0">Select</option>
			 <c:forEach var="prjList" items="${workflowmap.tadaPrjDirList}">
			 <c:choose>
			 <c:when test="${prjList.projectName==workflowmap.tadaApprovalRequestDTO.projectName}">
			   <option value="${workflowmap.tadaApprovalRequestDTO.projectName}" selected="selected">${workflowmap.tadaApprovalRequestDTO.projectName}</option>
			 </c:when>
			 <c:otherwise>
			   <option value="${prjList.projectName}">${prjList.projectName}</option>
			 </c:otherwise>
			 </c:choose>
			 </c:forEach>
			 </select>
			 </c:otherwise>
			 </c:choose>	 
		 </div>
		 <c:forEach var="prjList" items="${workflowmap.tadaPrjDirList}">
		 <c:if test="${prjList.projectName==workflowmap.tadaApprovalRequestDTO.projectName}">
			   <a id="refId" href="javascript:showTadaTdPrjDirector();" style="display: none">change</a>
			   <script>showTadaTdDir();</script>
		 </c:if>
		 </c:forEach>
	    </div>
	    </c:if> 
	  </c:if>
	
	</c:if>
	</c:if>
	<c:if test="${workflowmap.tadaApprovalRequestDTO.stayDuration==0}">
	<div class="line">
		<div class="quarter bold">Basic Pay & Grade Pay</div>
		<div class="quarter">:&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.tadaApprovalRequestDTO.basicPay}/- & <font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.tadaApprovalRequestDTO.gradePay}/-</div>
		<div class="quarter bold">Reason for Cancellation</div>
		<div class="quarter" id="tdWorkPlace">:&nbsp;${workflowmap.tadaApprovalRequestDTO.remarks}</div>
	</div>
	</c:if>
	
	<div class="line">
		<div class="quarter bold">TD Application Form</div>
		<c:choose>
		<c:when test="${workflowmap.tadaAmendmentDetails!=null && workflowmap.tadaAmendmentDetails!='[]' && workflowmap.tadaAmendmentDetails!=''}">
		<div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.tadaAmendmentDetails.requestId}','tadaTdApproval')">Print</a></div>
		</c:when>
		<c:otherwise><div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdApproval')">Print</a></div></c:otherwise>
		</c:choose>
		<c:if test="${workflowmap.tadaAmendmentDetails!=null && workflowmap.tadaAmendmentDetails!='[]' && workflowmap.tadaAmendmentDetails!=''}">
		<c:if test="${workflowmap.tadaApprovalRequestDTO.stayDuration!=0}">
		<div class="quarter bold">TD Amendment Form</div>
		<div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdAmendmentApproval')">Print</a></div>
		</c:if>
		<c:if test="${workflowmap.tadaApprovalRequestDTO.stayDuration==0}">
		<div class="quarter bold">TD Cancellation Form</div>
		<div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdCancelApproval')">Print</a></div>
		</c:if>
		</c:if>
	</div>
	<div class="line">
	<c:if test="${workflowmap.tadaTdAdvanceRequestDTO!=null && workflowmap.tadaTdAdvanceRequestDTO!='[]'}">
		<div class="quarter bold">Advance RequestId:</div>
		<div class="quarter">:&nbsp;<a href="javascript:getRequestDetails('${workflowmap.tadaTdAdvanceRequestDTO.historyID}','${workflowmap.tadaTdAdvanceRequestDTO.requestId}','myRequests','completed')">${workflowmap.tadaTdAdvanceRequestDTO.requestId}</a></div>
	</c:if>
	<c:if test="${(workflowmap.tadaTdAdvanceRequestDTO==null || workflowmap.tadaTdAdvanceRequestDTO=='[]') && (workflowmap.tdReimReqId==null || workflowmap.tdReimReqId=='')}">
		<div class="quarter bold">Advance RequestId:</div>
		<div class="half">:&nbsp;<font color="red">Advance not taken through online process.</font></div>
	</c:if>
	</div>
	<div class="line">
	<c:if test="${workflowmap.tdSettlementReqId!=null && workflowmap.tdSettlementReqId!=''}">
		<div class="quarter bold">Settlement RequestId:</div>
		<div class="quarter">:&nbsp;<a href="javascript:getRequestDetails('${workflowmap.tdSettlementHistoryID}','${workflowmap.tdSettlementReqId}','myRequests','completed')">${workflowmap.tdSettlementReqId}</a></div>
	</c:if>
	</div>
	<div class="line">
	<c:if test="${workflowmap.tdReimReqId!=null && workflowmap.tdReimReqId!=''}">
		<div class="quarter bold">Reimbursement RequestId:</div>
		<div class="quarter">:&nbsp;<a href="javascript:getRequestDetails('${workflowmap.tdReimHistoryID}','${workflowmap.tdReimReqId}','myRequests','completed')">${workflowmap.tdReimReqId}</a></div>
	</c:if>
	</div>
	<div class="line" id="individualDetails">
										<div style="colore:red"><font color='red'>Note for Individual</font></div>
										<div><font color='purple'>
										1)Go to My Admin---->TADA---->TADA TD Claims/Amendment link for <b>:</b>  TD Advance,Settlement,Reimbursement,Amendment,Cancel.
										</font></div> 
								</div>
	
			
			<form:hidden path="message"/>
			<form:hidden path="historyID"/>
		    <form:hidden path="statusMsg"/>
	
		    
	
<script>
reqStatus = '${workflowmap.tadaApprovalRequestDTO.status}';
advanceFlag = '${workflowmap.tadaApprovalRequestDTO.advanceFlag}';
ammendementId = '${workflowmap.tadaApprovalRequestDTO.ammendementId}';
</script>
	
</div>

<!-- End : TadaTdDetails.jsp -->