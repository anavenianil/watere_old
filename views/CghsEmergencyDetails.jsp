<!-- Begin : CghsEmergencyDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div>

  	<div class="line">
		<div class="quarter leftmar">SFID</div>
		<div class="quarter">&nbsp;${workflowmap.cghsEmergency.employeeDetails.userSfid}</div>
		<div class="quarter leftmar">CGHS Card Holder Name</div>
		<div class="quarter">&nbsp;${workflowmap.cghsEmergency.employeeDetails.nameInServiceBook}</div>
	</div>
	<div class="line">
		<div class="quarter leftmar">CGHS Card Number</div>
		<div class="quarter">&nbsp;${workflowmap.cghsEmergency.employeeDetails.cgshNumber}</div>
		<div class="quarter leftmar">Total Amount Claimed(Rs)</div>
		<div class="quarter">&nbsp;Rs.${workflowmap.cghsEmergency.amountClaimed}/-</div>
	</div>
	<div class="line" id="nonCghsValues">
		<div class="quarter leftmar">Family Member</div>
		<div class="quarter">&nbsp;${workflowmap.cghsEmergency.familyDetails.name}-${workflowmap.cghsEmergency.familyDetails.relationDetails.name}</div>
		<div id="hospitalName">
			<div class="quarter leftmar">Hospital Name</div>
			<div class="quarter">&nbsp;${workflowmap.cghsEmergency.hospitalDetails.hospitalName}</div>
		</div>		
		<div id="hospitalAddress">
			<div class="quarter leftmar">Hospital Address</div>
			<div class="quarter">&nbsp;${workflowmap.cghsEmergency.hospitalAddress}</div>
		</div>
	</div>
		
	<div class="line">
		<div class="quarter leftmar">Date of Admission</div>
		<div class="quarter">&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.cghsEmergency.admissionDate}"/></div>
		<div class="quarter leftmar">Date of Discharge</div>
		<div class="quarter">&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.cghsEmergency.dischargeDate}"/></div>
	</div>
	<div class="line">
	<c:if test="${(workflowmap.checkStage eq 'last' && workflowmap.message eq 'pending') || workflowmap.cghsEmergency.admissibleTotalAmount ne '0' }">
			<div class="quarter leftmar">Finance Issued Amount</div>
			<div class="quarter" id="financeIssue">
				Rs.<span id="amountClaimed">
				<c:if test="${workflowmap.cghsEmergency.admissibleTotalAmount ne '0'}">
					${workflowmap.cghsEmergency.admissibleTotalAmount}
				</c:if>
				<c:if test="${workflowmap.cghsEmergency.admissibleTotalAmount eq '0'}">
					${workflowmap.cghsEmergency.amountClaimed}
				</c:if>
				</span>/-
			</div>
	</c:if>
	</div>
		<c:if test="${workflowmap.cghsEmergency.admissibleTotalAmount ne '0' && empty workflowmap.cghsEmergency.cdaAmount}">
		<div class="line"><div class="quarter leftmar">CDA IssuedAmount</div>
		<div class="quarter">&nbsp;<font color='red'>CDA Amount not entered</font></div>
		</div>
		</c:if>
	<c:if test="${workflowmap.checkStage eq 'last' && workflowmap.message eq 'pending'}">
		<div class="line">
			<c:if test="${not empty workflowmap.cghsEmergency.labCharges && workflowmap.cghsEmergency.labCharges ne '0'}">
				<div class="quarter leftmar">Admissible Lab Charges</div>
				<div class="quarter"><input name="admissibleLabCharges" id="admissibleLabCharges" value="${workflowmap.cghsEmergency.labCharges}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleLabCharges','${workflowmap.cghsEmergency.labCharges}')"/></div>
			</c:if>
			<c:if test="${not empty workflowmap.cghsEmergency.medicinesCharges && workflowmap.cghsEmergency.medicinesCharges ne '0'}">
				<div class="quarter leftmar">Admissible Medicines Charges</div>
				<div class="quarter"><input name="admissibleMedicinesCharges" id="admissibleMedicinesCharges" value="${workflowmap.cghsEmergency.medicinesCharges}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleMedicinesCharges','${workflowmap.cghsEmergency.medicinesCharges}')"/></div>
			</c:if>
		</div>
		<div class="line">
			<c:if test="${not empty workflowmap.cghsEmergency.roomRent && workflowmap.cghsEmergency.roomRent ne '0'}">
				<div class="quarter leftmar">Room Rent Charges</div>
				<div class="quarter"><input name="admissibleRoomRent" id="admissibleRoomRent" value="${workflowmap.cghsEmergency.roomRent}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleRoomRent','${workflowmap.cghsEmergency.roomRent}')"/></div>
			</c:if>
			<c:if test="${not empty workflowmap.cghsEmergency.otCharges && workflowmap.cghsEmergency.otCharges ne '0'}">
				<div class="quarter leftmar">Admissible O.T. Charges</div>
				<div class="quarter"><input name="admissibleOtCharges" id="admissibleOtCharges" value="${workflowmap.cghsEmergency.otCharges}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleOtCharges','${workflowmap.cghsEmergency.otCharges}')"/></div>
			</c:if>
		</div>
		<div class="line">
			<c:if test="${not empty workflowmap.cghsEmergency.otConsumables && workflowmap.cghsEmergency.otConsumables ne '0'}">
				<div class="quarter leftmar">Admissible O.T.Consumables</div>
				<div class="quarter"><input name="admissibleOtConsumables" id="admissibleOtConsumables" value="${workflowmap.cghsEmergency.otConsumables}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleOtConsumables','${workflowmap.cghsEmergency.otConsumables}')"/></div>
			</c:if>
			<c:if test="${not empty workflowmap.cghsEmergency.anaesthesiaCharges && workflowmap.cghsEmergency.anaesthesiaCharges ne '0'}">
				<div class="quarter leftmar">Admissible Anaesthesia Charges</div>
				<div class="quarter"><input name="admissibleAnaesthesiaCharges" id="admissibleAnaesthesiaCharges" value="${workflowmap.cghsEmergency.anaesthesiaCharges}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleAnaesthesiaCharges','${workflowmap.cghsEmergency.anaesthesiaCharges}')"/></div>
			</c:if>
		</div>
		<div class="line">
			<c:if test="${not empty workflowmap.cghsEmergency.implantsCharges && workflowmap.cghsEmergency.implantsCharges ne '0'}">
				<div class="quarter leftmar">Admissible Implants Charges</div>
				<div class="quarter"><input name="admissibleImplantsCharges" id="admissibleImplantsCharges" value="${workflowmap.cghsEmergency.implantsCharges}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleImplantsCharges','${workflowmap.cghsEmergency.implantsCharges}')"/></div>
			</c:if>
			<c:if test="${not empty workflowmap.cghsEmergency.artificialCharges && workflowmap.cghsEmergency.artificialCharges ne '0'}">
				<div class="quarter leftmar">Admissible Artificial devices</div>
				<div class="quarter"><input name="admissibleArtificialCharges" id="admissibleArtificialCharges" value="${workflowmap.cghsEmergency.artificialCharges}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleArtificialCharges','${workflowmap.cghsEmergency.artificialCharges}')"/></div>
			</c:if>
		</div>
		<div class="line">
			<c:if test="${not empty workflowmap.cghsEmergency.procedure && workflowmap.cghsEmergency.procedure ne '0'}">
				<div class="quarter leftmar">Admissible Procedure Charges</div>
				<div class="quarter"><input name="admissibleProcedure" id="admissibleProcedure" value="${workflowmap.cghsEmergency.procedure}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleProcedure','${workflowmap.cghsEmergency.procedure}')"/></div>
			</c:if>
			<c:if test="${not empty workflowmap.cghsEmergency.specialNurse && workflowmap.cghsEmergency.specialNurse ne '0'}">
				<div class="quarter leftmar">Admissible Special Nurse</div>
				<div class="quarter"><input name="admissibleSpecialNurse" id="admissibleSpecialNurse" value="${workflowmap.cghsEmergency.specialNurse}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleSpecialNurse','${workflowmap.cghsEmergency.specialNurse}')"/></div>
			</c:if>
		</div>
		<div class="line">
			<c:if test="${not empty workflowmap.cghsEmergency.miscellaneousCharges && workflowmap.cghsEmergency.miscellaneousCharges ne '0'}">
				<div class="quarter leftmar">Admissible Miscellaneous Charges</div>
				<div class="quarter"><input name="admissibleMiscellaneousCharges" id="admissibleMiscellaneousCharges" value="${workflowmap.cghsEmergency.miscellaneousCharges}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleMiscellaneousCharges','${workflowmap.cghsEmergency.miscellaneousCharges}')"/></div>
			</c:if>
		</div>
	</c:if>
	
	
	<div class="line">
	<fieldset><legend><strong><font color='red'>Note for Individual</font></strong></legend>
	<div id="result1"></div>
	<c:if test="${workflowmap.cghsEmergency.status !=8 }">
	<marquee direction="left" behavior="alternate"><div class="line" style="color:red" align="center">
	Immediately submit your bills to Finance,Unless bills verified with Finance Section this claim will not be processed further.
	</div></marquee></c:if>
		<div class="line">1). Individual should submit signed copy of the following forms<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<c:if test="${workflowmap.cghsEmergency.admissibleTotalAmount ne '0'}">(<a href="javascript:getInitialReport('${workflowmap.cghsEmergency.requestID}','${workflowmap.requestType}')">Print & Sign after Finance Approval</a>)<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
		<c:if test="${workflowmap.cghsEmergency.admissibleTotalAmount eq '0'}"><font color="red">(Don't forget to print & Sign report after finance approval from here)</font><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:if>	
				a) Check List&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				b) Medical 2004 Form&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				c) Essentiality Certificate&nbsp;(<a href="javascript:getInitialReport('${workflowmap.cghsEmergency.requestID}','emerReim')">Print & Sign</a>)<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				d) Application for Reimbursement&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				e) Statement of Case&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				f) Recommendation by Technology Director&nbsp;(<a href="javascript:getInitialReport('${workflowmap.cghsEmergency.requestID}','emerForm')">Print & Sign</a>)<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
		</div>
		<div class="line">2). Submit the following copies to finance<br>
				<div id="NoFiles" class="line">
				<div style="width: 450px;  margin-left: 20px;">
				<div  class="fvireq">
				<c:if test="${ empty workflowmap.cghsEmergency.cghscardref}" >
				a) Copy of CGHS card<a href="javascript:showDatabaseFile('${workflowmap.cghsEmergency.cghsCardFile}')">View</a>
				</c:if>
				
				<c:if test="${ not empty workflowmap.cghsEmergency.cghscardref}">
				a) Copy of CGHS card<a href="javascript:showDatabaseFile('${workflowmap.cghsEmergency.cghscardref}')">View</a></c:if>
				</div>
				<div class="svireq">
				<c:if test="${ (workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='FORWARDED')&& (empty workflowmap.cghsEmergency.cghscardref)}">
				<input type="file" id="files1" name="cghsCardFile"/></c:if></div>
				<div  class="fvireq">
				b) Original medical bills<a href="javascript:showDatabaseFile('${workflowmap.cghsEmergency.medicalBillsFile}')">View</a></div>
				<div class="svireq">
				<c:if test="${ (workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='FORWARDED')}">
				<input type="file" id="files2" name="medicalBillsFile"/></c:if></div>
				<div  class="fvireq">
				c) Discharge Summary&nbsp;<a href="javascript:showDatabaseFile('${workflowmap.cghsEmergency.dischargeSummeryFile}')">View</a>
				</div>
				<div class="svireq">
				<c:if test="${ (workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='FORWARDED')}">
				<input type="file" id="files3" name="dischargeSummeryFile"/></c:if><br></div>
			   </div>
			   </div>
		</div>
		<div class="quarter">
				 <div style="margin-left:45%">
				 <c:if test="${ (workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='FORWARDED')}">
					<a href="javascript:manageUploadedFiles('${workflowmap.cghsEmergency.cghsCardFile}','${workflowmap.cghsEmergency.medicalBillsFile}','${workflowmap.cghsEmergency.dischargeSummeryFile}');"><div class="appbutton" id="button" style="display:show">Upload</div></a>
		     	 </c:if>
		     	 </div>
		</div>
	</fieldset>
	</div>
	<input type="hidden" name="admissibleTotalAmount" id="admissibleTotalAmount"/>
	<input type="hidden" name="cghsCardFileName" id="cghsCardFileName"/>
	<input type="hidden" name="medicalBillsFileName" id="medicalBillsFileName"/>
	<input type="hidden" name="dischargeSummeryFileName" id="dischargeSummeryFileName"/>
	
	<script type="text/javascript">setCghsEmergency('${workflowmap.cghsEmergency.requestType}');
	$jq('#admissibleTotalAmount').val($jq('#amountClaimed').text().trim());
	</script>
</div> 
<!-- End : CghsEmergencyDetails.jsp -->
