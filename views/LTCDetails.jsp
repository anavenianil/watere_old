<!-- Begin : LTCDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html>
<head>
</head>
<body>
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
		<div class="quarter bold">Nearest Railway Station</div>
		<div class="quarter">:&nbsp;${workflowmap.ltcApprovalRequestDTO.nearestRlyStation}</div>
	</div>
	<div class="line">
		<div class="quarter bold">Date of Departure</div>
		<div class="quarter" id="departureDate">: <fmt:formatDate value="${workflowmap.ltcApprovalRequestDTO.departureDate}" pattern="dd-MMM-yyyy"/></div>
		<div class="quarter bold">Expected Date of Return</div>
		<div class="quarter" id="departureDate">: <fmt:formatDate value="${workflowmap.ltcApprovalRequestDTO.returnDate}" pattern="dd-MMM-yyyy"/></div>
	</div>
	<div class="line">
		<c:if test="${not empty workflowmap.ltcApprovalRequestDTO.leaveRequestId}">
			<div class="quarter bold">Leave Details</div>
			<div class="quarter">:&nbsp;${workflowmap.ltcApprovalRequestDTO.leaveRequestId}</div>
		</c:if>
		<c:if test="${not empty workflowmap.ltcApprovalRequestDTO.encashmentDays}">
			<div class="quarter bold">EL encashment days</div>
			<div class="quarter">:&nbsp;${workflowmap.ltcApprovalRequestDTO.encashmentDays}</div>
		</c:if>
	</div>
	<div class="line">
			<c:if test="${not empty workflowmap.ltcApprovalRequestDTO.leaveId}">
			<div class="quarter bold">Leave Request ID</div>
			<div class="quarter">:&nbsp;${workflowmap.ltcApprovalRequestDTO.leaveId}</div>
		</c:if>
	</div>
	<div class="line">
	<!--commented by bkr 12/04/2016  -->
		<%-- <c:if test="${workflowmap.ltcApprovalRequestDTO.doPartNo ne null  && workflowmap.ltcApprovalRequestDTO.doPartNo ne '' && workflowmap.ltcApprovalRequestDTO.doPartNo !=0}">
				<div class="quarter bold">DO Part No & Date</div>
				<div class="quarter">:&nbsp;${workflowmap.ltcApprovalRequestDTO.doPartNo}</div>
		</c:if> --%>
		<div class="quarter bold">LTC Application Form</div>
		<div class="quarter">:&nbsp;<a href="javascript:getInitialReport('${workflowmap.requestId}','ltcApproval')">Print</a></div>
	</div>
	<c:if test="${not empty workflowmap.ltcAdvanceRequestDTO.encashmentDays  && not empty workflowmap.ltcApprovalRequestDTO.doPartNo}">
		<div class="line">
			<div class="quarter bold">Finance Issued Encashment Amount</div>
			<div class="quarter">
				<c:if test="${not empty workflowmap.ltcAdvanceRequestDTO.financeEncashmentAmt}">
					:&nbsp;Rs.${workflowmap.ltcAdvanceRequestDTO.financeEncashmentAmt}/-
				</c:if>
				<c:if test="${empty workflowmap.ltcAdvanceRequestDTO.financeEncashmentAmt}">
					<font color='red'>:&nbsp;Finance Encashment Amount is not entered</font>
				</c:if>
			</div>
		<div class="quarter bold">CDA Issued Encashment Amount</div>
			<div class="quarter">
				<c:if test="${not empty workflowmap.ltcAdvanceRequestDTO.cdaEncashmentAmt}">
					:&nbsp;Rs.${workflowmap.ltcAdvanceRequestDTO.cdaEncashmentAmt}/-
				</c:if>
				<c:if test="${empty workflowmap.ltcAdvanceRequestDTO.cdaEncashmentAmt}">
					<font color='red'>:&nbsp;CDA Encashment Amount is not entered</font>
				</c:if>
			</div>
	</div>
	</c:if>
	<c:if test="${workflowmap.requestType eq 'LTC APPROVAL AMENDMENT'}">
	<div class="quarter bold">LTC Approval Amendment Form</div>
	<div class="quarter">:&nbsp;<a href="javascript:getInitialDocReport('${workflowmap.requestId}','ltcApprovalAmendment')">Print</a></div>
	</c:if>
	
	<div class="line">
		<c:if test="${not empty workflowmap.ltcApprovalRequestDTO.escortDetails}">
				<div class="quarter bold">Escort Details</div>
				<div class="quarter">:&nbsp;${workflowmap.ltcApprovalRequestDTO.escortDetails}</div>
		</c:if>
	</div>
	<div class="headTitle">Details of Members for LTC</div>
	<div class="line">
		<table border="1" width="100%" align="center" cellpadding="2" cellspacing="0" class="sub_2">
			<tr>
				<th>Name</th>
				<th>Date of Birth</th>
				<th>Age</th>
				<th>Relation Ship with Govt. Servant</th>
			</tr>
			<c:forEach items="${workflowmap.ltcApprovalRequestDTO.ltcMemberDetails}" var="membersList">
				<tr>
					<td width="25%">${membersList.familyMemberName}</td>
					<td width="10%">${membersList.dob}</td>
					<td width="10%">${membersList.age}</td>
					<td width="15%">${membersList.relation}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="line" id="individualNote" style="display: none"><br/>
		<div style="colore:red"><font color='red'>Note for Individual</font></div>
		<div><font color='purple'>1)This is only for LTC Approval Request. <br/>2)For LTC Advance Please raise Advance Request.</font></div> 
	</div>
	
	<c:if test="${workflowmap.doPartShowFlag eq 'Y' && (workflowmap.ltcApprovalRequestDTO.doPartNo eq null || workflowmap.ltcApprovalRequestDTO.doPartNo==0)}">
	<fieldset><legend><strong><font color='green' id="emergency">Admin DoPart Details</font></strong></legend>
		<div class="line">
			<div class="quarter bold">DO Part Date<span class='mandatory'>*</span></div>
			<div class="quarter">
				<!-- <input type="text" name="doPartDate" id="doPartDate" readonly="readonly" onchange="javascript:getDoPartNumber('${workflowmap.ltcApprovalRequestDTO.gazType}');"></input>
					<img  src="./images/calendar.gif"   id="date_start_trigger3" />	
					<script type="text/javascript">
						Calendar.setup({inputField :"doPartDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
					</script> -->
					
					<form:select path="doPartDate" id="doPartDate" cssStyle="width:145px;" onchange="javascript:getDoPartNumber('${workflowmap.ltcApprovalRequestDTO.gazType}');">
														<form:option value="Select" label="Select"></form:option>
														<form:options items="${doPartDatewiseList}"></form:options>
													</form:select>
					
					
			</div>
			<div class="quarter bold">DO Part No<span class='mandatory'>*</span></div>
			<div class="quarter" id="doPartNumber"><input type="text" name="doPartNo" id="doPartNo"></input></div>
			
		</div>
		</fieldset>
	</c:if>
	
	
	
	<div class="line">
<fieldset>
		<div class="quarter">
			 <div>
         <c:if test="${ not empty workflowmap.ltcApprovalRequestDTO.advrequestid && workflowmap.ltcApprovalRequestDTO.advrequestid !=0}">
            <div class="quarter leftmar">Advance RequestId:
             <div class="quarter"> <a href="javascript:getRequestDetails('${workflowmap.ltcApprovalRequestDTO.advreqhistoryid}','${workflowmap.ltcApprovalRequestDTO.advrequestid}','myRequests','completed','')">${workflowmap.ltcApprovalRequestDTO.advrequestid}</a></div>
		    </div>
	        </c:if> 
		  </div>
		  </div>
		  
		  
		  <div class="quarter">
			 <div>
         <c:if test="${ not empty workflowmap.ltcApprovalRequestDTO.amprequestid && workflowmap.ltcApprovalRequestDTO.amprequestid !=0}">
            <div class="quarter leftmar">Approval RequestId: 
				
             <div class="quarter"> <a href="javascript:getRequestDetails('${workflowmap.ltcApprovalRequestDTO.amphistoryid}','${workflowmap.ltcApprovalRequestDTO.amprequestid}','myRequests','completed','')">${workflowmap.ltcApprovalRequestDTO.amprequestid}</a></div>
		    </div>
	        </c:if> 
		  </div>
		  </div>
		  <div class="quarter">
			 <div>
         <c:if test="${ not empty workflowmap.ltcApprovalRequestDTO.reimrequestid && workflowmap.ltcApprovalRequestDTO.reimrequestid !=0}">
            <div class="quarter leftmar">Settlement RequestId:
             <div class="quarter"> <a href="javascript:getRequestDetails('${workflowmap.ltcApprovalRequestDTO.reimhistoryid}','${workflowmap.ltcApprovalRequestDTO.reimrequestid}','myRequests','completed','')">${workflowmap.ltcApprovalRequestDTO.reimrequestid}</a></div>
		    </div>
	        </c:if> 
		  </div>
		  </div>
		  
		  
		   <div class="quarter">
			 <div>
         <c:if test="${ not empty workflowmap.ltcApprovalRequestDTO.reimrequestidApp && workflowmap.ltcApprovalRequestDTO.reimrequestidApp !=0}">
            <div class="quarter leftmar">Reimbursement RequestId:
             <div class="quarter"> <a href="javascript:getRequestDetails('${workflowmap.ltcApprovalRequestDTO.reimhistoryidApp}','${workflowmap.ltcApprovalRequestDTO.reimrequestidApp}','myRequests','completed','')">${workflowmap.ltcApprovalRequestDTO.reimrequestidApp}</a></div>
		    </div>
	        </c:if> 
		  </div>
		  </div>
		  
		  
		  
		  
		</fieldset>
</div>
	</div>
	
	<input type="hidden"  id="statusMsg"/>	
	  <input type="hidden"  id="back"/>	
	  <input type="hidden"  id="message"/>	
	  <input type="hidden" id="historyID"/>
<script type="text/javascript">
	// gazType is global variable
	gazType = '${workflowmap.ltcApprovalRequestDTO.gazType}';
	leaveStatus = '${workflowmap.ltcApprovalRequestDTO.leaveStatus}';
	leaveID='${workflowmap.ltcApprovalRequestDTO.leaveId}';
</script>
	

</body>
</html>

<!-- End : LTCDetails.jsp -->