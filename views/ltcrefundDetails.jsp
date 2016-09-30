<!-- Begin : LeaveDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div class="line">

				
				<div class="line">
					<div class="quarter bold">&nbsp;LTC type</div>
					<div class="quarter">:&nbsp;${workflowmap.lTCRefundDetails.ltcTypeId}</div>
					<div class="quarter bold">&nbsp;Applied on</div>
					<div class="quarter">:&nbsp;${workflowmap.lTCRefundDetails.creationDate}</div>
				</div>
				<div class="line">
					<div class="quarter bold">&nbsp;Place of visit/home town</div>
					<div class="quarter">:&nbsp;${workflowmap.lTCRefundDetails.placeOfVisit }</div>
					<div class="quarter bold">&nbsp;Block year</div>
					<div class="quarter">:&nbsp;${workflowmap.lTCRefundDetails.ltcBlockYear }</div>
				</div>
				<div class="line">
					<c:if test="${not empty workflowmap.lTCRefundDetails.leaveDetails}">
						<div class="quarter bold">&nbsp;Leave type</div>
						<div class="quarter">:&nbsp;${workflowmap.lTCRefundDetails.leaveDetails}</div>
					</c:if>
					<c:if test="${not empty workflowmap.lTCRefundDetails.ltcAdvance}">
						<div class="quarter bold">&nbsp;Issued Advance</div>
						<div class="quarter">:Rs.&nbsp;${workflowmap.lTCRefundDetails.ltcAdvance}/-</div>
					</c:if>
					
				</div>
				<c:if test="${not empty workflowmap.lTCRefundDetails.doPartNo}">
				<div class="line">
				<div class="quarter bold">&nbsp;DO Part No</div>
				<div class="quarter">:&nbsp;${workflowmap.lTCRefundDetails.doPartNo}</div>
				<div class="quarter bold">&nbsp;DO Part Date</div>
				<div class="quarter">:&nbsp;${workflowmap.lTCRefundDetails.doPartDate}</div>
				</div>
				</c:if>
				<c:if test="${not empty workflowmap.lTCRefundDetails.encashmentDays}">
				<div class="line">
					<div class="quarter bold">&nbsp;Whether EL encashed</div>
					<div class="quarter">:&nbsp;Yes</div>
					<div class="quarter bold">&nbsp;No of days</div>
					<div class="quarter">:&nbsp;${workflowmap.lTCRefundDetails.encashmentDays}</div>
				</div>
				</c:if>
				<c:if test="${not empty workflowmap.lTCRefundDetails.ltcApproveDetailsList}">
	 <div class="headTitle">Details of Members for LTC</div>
	<div class="line">
		<table border="1" width="100%" align="center" cellpadding="2" cellspacing="0" class="sub_2">
			<tr>
				<th>Name</th>
				<th>Date of Birth</th>
				<th>Age</th>
				<th>Relation Ship with Govt. Servant</th>
			</tr>
			<c:forEach items="${workflowmap.lTCRefundDetails.ltcApproveDetailsList}" var="membersList">
				<tr>
					<td width="30%">${membersList.familyMemberName}</td>
					<td width="20%">${membersList.dob}</td>
					<td width="10%">${membersList.age}</td>
					<td width="20%">${membersList.relation}</td>
				</tr>
			</c:forEach>
		</table>
	</div>				
			</c:if>	
				<div class="line">
				<c:if test="${not empty workflowmap.lTCRefundDetails.encashmentDays && workflowmap.lTCRefundDetails.ltcAdvance}">
					<div class="quarter bold">MRO Form for EL encashment</div>
					<div class="quarter">:&nbsp;<a href="javascript:getInitialReport('${workflowmap.lTCRefundDetails.id}','${workflowmap.lTCRefundDetails.type}')">Print</a></div>
				</c:if>
				<c:if test="${not empty workflowmap.lTCRefundDetails.ltcAdvance}">
					<div class="quarter bold">MRO Form for advance</div>
					<div class="quarter">:&nbsp;<a href="javascript:getInitialReport('${workflowmap.lTCRefundDetails.requestIDs}','ltcRefund')">Print</a></div>
				</c:if>
				</div>
				<c:if test="${workflowmap.requestType eq 'LTC CANCEL'}">
				<div class="line">
					<div class="quarter bold">LTC Cancellation Form</div>
					<div class="quarter">:&nbsp;<a href="javascript:getInitialReport('${workflowmap.lTCRefundDetails.requestIDs}','ltcAppCancel')">Print</a></div>
				</div>
	          </c:if>
	          <c:if test="${workflowmap.requestType eq 'LTC APPR CUM ADV CANCEL' || workflowmap.requestType eq 'LTC REFUND'}">
				<div class="line">
					<div class="quarter bold">LTC Cancellation Form</div>
					<div class="quarter">:&nbsp;<a href="javascript:getInitialReport('${workflowmap.lTCRefundDetails.requestIDs}','ltcAppAdvCancel')">Print</a></div>
				</div>
	          </c:if>
	          
		<c:if test="${not empty workflowmap.lTCRefundDetails.ltcAdvance && workflowmap.checkStage eq 'last' && (workflowmap.message eq 'PENDING' || workflowmap.message eq 'pending')}">
			<div class="line">
				<div class="quarter bold">Excess Amount<span class="mandatory">*</span></div>
				<div class="quarter"><input type="text" name="excessAmount" id="excessAmount" onkeypress="javascript:return checkInt(event);"/></div>
				<div class="quarter bold">Penal Interest<span class="mandatory">*</span></div>
				<div class="quarter"><input type="text" name="excessAmountFine" id="excessAmountFine" onkeypress="javascript:return checkInt(event);"/></div>
			</div>
			<div class="line" id="MRODetailsDiv">
				<div class="quarter bold">MRO Paid No<span class="mandatory">*</span></div>
				<div class="quarter"><input type="text" name="MROPaidNo" id="MROPaidNo" onkeypress="javascript:return checkInt(event);"/></div>
				<div class="quarter bold">MRO Paid Date<span class="mandatory">*</span></div>
				<div class="quarter">
					<input type="text" name="MROPaidDate" id="MROPaidDate" readonly="readonly"></input>
						<img  src="./images/calendar.gif"   id="MROPaidDateImg" />	
						<script type="text/javascript">
							Calendar.setup({inputField :"MROPaidDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"MROPaidDateImg",singleClick : true,step : 1});
						</script>
				</div>
			</div>
		</c:if>
		
		<c:if test="${workflowmap.doPartShowFlag eq 'Y' && empty workflowmap.lTCRefundDetails.doPartNo}">
	<fieldset><legend><strong><font color='green' id="emergency">Admin DoPart Details</font></strong></legend>
		<div class="line">
			<div class="quarter bold">DO Part Date<span class="mandatory">*</span></div>
			<div class="quarter">
				<input type="text" name="doPartDate" id="doPartDate" readonly="readonly" onchange="javascript:getDoPartNumber('${workflowmap.lTCRefundDetails.gazType}');"></input>
					<img  src="./images/calendar.gif"   id="date_start_trigger3" />	
					<script type="text/javascript">
						Calendar.setup({inputField :"doPartDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
					</script>
			</div>
			<div class="quarter bold">DO Part No<span class="mandatory">*</span> </div>
			<div class="quarter" id="doPartNumber"><input type="text" name="doPartNo" id="doPartNo"></input></div>
		</div>
	</fieldset>
	</c:if>
	<div class="line" id="individualNote" ><br/>
    <div style="colore:red"><font color='red'><u></>Note for Individual :</u></font></div>
     <div><font color='purple'>1)Please submit letter to admin stating the reason for your cancellation to admin so that admin will process the Leave cancellation based<br/> on the Online Request and letter.</font></div> 
	 </div>
	
	
	
</div>
<!-- End : LeaveDetails.jsp -->