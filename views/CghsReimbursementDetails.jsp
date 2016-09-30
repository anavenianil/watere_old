<!-- Begin : CghsReimbursementDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div>
	<div class="line">
		<c:if test="${workflowmap.cghsReimbursement.cghsRequestDetails.requestTypeDetails.name eq 'Admission'}">
		<div id="wardType">
			<div class="quarter leftmar">Entitle Ward Type</div>
			<div class="quarter">&nbsp;${workflowmap.cghsReimbursement.cghsRequestDetails.wardTypeDetails.name}</div>
		</div>
		</c:if>
		<c:if test="${not empty workflowmap.cghsReimbursement.advanceDetails}">
		<div>
			<div class="quarter leftmar">Issued Advance</div>
			<div class="quarter">&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.cghsReimbursement.advanceDetails}</div>
		</div>
		</c:if>
		<div id="disease">
		    <c:if test="${not empty workflowmap.cghsReimbursement.disease}">
			<div class="quarter leftmar">Disease</div>
			<div class="quarter">&nbsp;${workflowmap.cghsReimbursement.disease}</div>
			</c:if>
		</div>
	</div>
	<div class="line" id="nonCghsValues">
		<div class="quarter leftmar">Family Member$</div>
		<div class="quarter">&nbsp;${workflowmap.cghsReimbursement.familyMemberDetails.name}</div>
		<div class="quarter leftmar">Hospital Details</div>
		<div class="quarter">&nbsp;${workflowmap.cghsReimbursement.hospitalDetails}</div>
	</div>
	<div class="line">
	     <c:if test="${not empty workflowmap.cghsReimbursement.admissionDate}">
		<div class="quarter leftmar">Date of Admission</div>
		<div class="quarter">&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.cghsReimbursement.admissionDate}"/></div>
		<div class="quarter leftmar">Date of Discharge</div>
		<div class="quarter">&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.cghsReimbursement.dischargeDate}"/></div>
		</c:if>
	</div>
	<div class="line">
		<div class="quarter leftmar">Amount</div>
		<div class="quarter">&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.cghsReimbursement.amountClaimed}/-</div>
		<c:if test="${(workflowmap.checkStage eq 'last' && workflowmap.message eq 'pending') || workflowmap.cghsReimbursement.admissibleTotalAmount ne '0' }">
			<div class="quarter leftmar">Finance Issued Amount</div>
			<div class="quarter" id="financeIssue"><font size="4.5em"><span class="WebRupee" >R</span></font><span id="amount">
				<c:if test="${workflowmap.cghsReimbursement.admissibleTotalAmount ne '0'}">
					${workflowmap.cghsReimbursement.admissibleTotalAmount}
				</c:if>
				<c:if test="${workflowmap.cghsReimbursement.admissibleTotalAmount eq '0'}">
					${workflowmap.cghsReimbursement.amountClaimed}
				</c:if></span>/-
			</div>
		</c:if>
	</div>
	
	<c:if test="${workflowmap.cghsReimbursement.admissibleTotalAmount ne '0' && empty workflowmap.cghsReimbursement.cdaAmount}">
		<div class="line"><div class="quarter leftmar">CDA IssuedAmount</div>
		<div class="quarter">&nbsp;<font color='red'>CDA Amount is not entered</font></div>
		</div>
	</c:if>
	<c:if test="${not empty workflowmap.cghsReimbursement.cdaAmount}">
	<div class="line">
	<div class="quarter leftmar">CDA IssuedAmount</div>
	<div class="quarter">&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font>${workflowmap.cghsReimbursement.cdaAmount}/-</div>
	</div>
	</c:if>
	<div class="line" id="settlement" style="display: none">
		<div class="quarter leftmar">Refund cheque from hospital, if advance is more than expenditure</div>
		<div class="quarter">&nbsp;<a href="javascript:downloadFile('${workflowmap.cghsReimbursement.refundChequeFile}')">Print</a></div>
	</div>

	<c:if test="${workflowmap.checkStage eq 'last' && workflowmap.message eq 'pending'}">
		<div class="line">
			<c:if test="${not empty workflowmap.cghsReimbursement.labCharges && workflowmap.cghsReimbursement.labCharges ne '0'}">
				<div class="quarter leftmar">Admissible Lab Charges</div>
				<div class="quarter"><input name="admissibleLabCharges" id="admissibleLabCharges" value="${workflowmap.cghsReimbursement.labCharges}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleLabCharges','${workflowmap.cghsReimbursement.labCharges}')"/></div>
			</c:if>
			<c:if test="${not empty workflowmap.cghsReimbursement.medicinesCharges && workflowmap.cghsReimbursement.medicinesCharges ne '0'}">
				<div class="quarter leftmar">Admissible Medicines Charges</div>
				<div class="quarter"><input name="admissibleMedicinesCharges" id="admissibleMedicinesCharges" value="${workflowmap.cghsReimbursement.medicinesCharges}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleMedicinesCharges','${workflowmap.cghsReimbursement.medicinesCharges}')"/></div>
			</c:if>
		</div>
		<div class="line">
			<c:if test="${not empty workflowmap.cghsReimbursement.consultationFees && workflowmap.cghsReimbursement.consultationFees ne '0'}">
				<div class="quarter leftmar">Admissible Consultation Fees</div>
				<div class="quarter"><input name="admissibleConsultationFees" id="admissibleConsultationFees" value="${workflowmap.cghsReimbursement.consultationFees}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleConsultationFees','${workflowmap.cghsReimbursement.consultationFees}')"/></div>
			</c:if>
			<c:if test="${not empty workflowmap.cghsReimbursement.disposableCharges && workflowmap.cghsReimbursement.disposableCharges ne '0'}">
				<div class="quarter leftmar">Admissible Disposable Charges</div>
				<div class="quarter"><input name="admissibleDisposableCharges" id="admissibleDisposableCharges" value="${workflowmap.cghsReimbursement.disposableCharges}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleDisposableCharges','${workflowmap.cghsReimbursement.disposableCharges}')"/></div>
			</c:if>
		</div>
		
		<div class="line">
			<c:if test="${not empty workflowmap.cghsReimbursement.specialDevices && workflowmap.cghsReimbursement.specialDevices ne '0'}">
				<div class="quarter leftmar">Admissible Special Devices</div>
				<div class="quarter"><input name="admissibleSpecialDevices" id="admissibleSpecialDevices" value="${workflowmap.cghsReimbursement.specialDevices}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleSpecialDevices','${workflowmap.cghsReimbursement.specialDevices}')"/></div>
			</c:if>
			<c:if test="${not empty workflowmap.cghsReimbursement.roomRent && workflowmap.cghsReimbursement.roomRent ne '0'}">
				<div class="quarter leftmar">Room Rent Charges</div>
				<div class="quarter"><input name="admissibleRoomRent" id="admissibleRoomRent" value="${workflowmap.cghsReimbursement.roomRent}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleRoomRent','${workflowmap.cghsReimbursement.roomRent}')"/></div>
			</c:if>
		</div>
		<div class="line">
			<c:if test="${not empty workflowmap.cghsReimbursement.otCharges && workflowmap.cghsReimbursement.otCharges ne '0'}">
				<div class="quarter leftmar">Admissible O.T. Charges</div>
				<div class="quarter"><input name="admissibleOtCharges" id="admissibleOtCharges" value="${workflowmap.cghsReimbursement.otCharges}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleOtCharges','${workflowmap.cghsReimbursement.otCharges}')"/></div>
			</c:if>
			<c:if test="${not empty workflowmap.cghsReimbursement.otConsumables && workflowmap.cghsReimbursement.otConsumables ne '0'}">
				<div class="quarter leftmar">Admissible O.T.Consumables</div>
				<div class="quarter"><input name="admissibleOtConsumables" id="admissibleOtConsumables" value="${workflowmap.cghsReimbursement.otConsumables}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleOtConsumables','${workflowmap.cghsReimbursement.otConsumables}')"/></div>
			</c:if>
		</div>
		<div class="line">
			<c:if test="${not empty workflowmap.cghsReimbursement.anaesthesiaCharges && workflowmap.cghsReimbursement.anaesthesiaCharges ne '0'}">
				<div class="quarter leftmar">Admissible Anaesthesia Charges</div>
				<div class="quarter"><input name="admissibleAnaesthesiaCharges" id="admissibleAnaesthesiaCharges" value="${workflowmap.cghsReimbursement.anaesthesiaCharges}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleAnaesthesiaCharges','${workflowmap.cghsReimbursement.anaesthesiaCharges}')"/></div>
			</c:if>
			<c:if test="${not empty workflowmap.cghsReimbursement.implantsCharges && workflowmap.cghsReimbursement.implantsCharges ne '0'}">
				<div class="quarter leftmar">Admissible Implants Charges</div>
				<div class="quarter"><input name="admissibleImplantsCharges" id="admissibleImplantsCharges" value="${workflowmap.cghsReimbursement.implantsCharges}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleImplantsCharges','${workflowmap.cghsReimbursement.implantsCharges}')"/></div>
			</c:if>
		</div>
		<div class="line">
			<c:if test="${not empty workflowmap.cghsReimbursement.artificialCharges && workflowmap.cghsReimbursement.artificialCharges ne '0'}">
				<div class="quarter leftmar">Admissible Artificial devices</div>
				<div class="quarter"><input name="admissibleArtificialCharges" id="admissibleArtificialCharges" value="${workflowmap.cghsReimbursement.artificialCharges}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleArtificialCharges','${workflowmap.cghsReimbursement.artificialCharges}')"/></div>
			</c:if>
			<c:if test="${not empty workflowmap.cghsReimbursement.procedure && workflowmap.cghsReimbursement.procedure ne '0'}">
				<div class="quarter leftmar">Admissible Procedure Charges</div>
				<div class="quarter"><input name="admissibleProcedure" id="admissibleProcedure" value="${workflowmap.cghsReimbursement.procedure}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleProcedure','${workflowmap.cghsReimbursement.procedure}')"/></div>
			</c:if>
		</div>
		<div class="line">
			<c:if test="${not empty workflowmap.cghsReimbursement.specialNurse && workflowmap.cghsReimbursement.specialNurse ne '0'}">
				<div class="quarter leftmar">Admissible Special Nurse</div>
				<div class="quarter"><input name="admissibleSpecialNurse" id="admissibleSpecialNurse" value="${workflowmap.cghsReimbursement.specialNurse}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleSpecialNurse','${workflowmap.cghsReimbursement.specialNurse}')"/></div>
			</c:if>
			<c:if test="${not empty workflowmap.cghsReimbursement.miscellaneousCharges && workflowmap.cghsReimbursement.miscellaneousCharges ne '0'}">
				<div class="quarter leftmar">Admissible Miscellaneous Charges</div>
				<div class="quarter"><input name="admissibleMiscellaneousCharges" id="admissibleMiscellaneousCharges" value="${workflowmap.cghsReimbursement.miscellaneousCharges}" onkeypress="return checkInt(event);" onchange="javascript:checkAdmissibleValue('admissibleMiscellaneousCharges','${workflowmap.cghsReimbursement.miscellaneousCharges}')"/></div>
			</c:if>
		</div>
	</c:if>

	<div class="line">
	<fieldset><legend><strong><font color='red'>Note for Individual</font></strong></legend>
	<div id="result1"></div>
	<br/><c:if test="${workflowmap.cghsReimbursement.status !=8 }">
	<marquee direction="left" behavior="alternate"><div class="line" style="color:red" align="center">
	Immediately submit your bills to Finance,Unless bills verified with Finance Section this claim will not be processed further.
	</div></marquee></c:if>
		<div class="line">1). Individual should submit signed copy of the following forms
		<c:if test="${workflowmap.cghsReimbursement.admissibleTotalAmount ne '0'}">(<a href="javascript:getInitialReport('${workflowmap.cghsReimbursement.requestID}','${workflowmap.requestType}')">Print & Sign after Finance Approval</a>)<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
		<c:if test="${workflowmap.cghsReimbursement.admissibleTotalAmount eq '0'}"><font color="red">(Don't forget to print & Sign report after finance approval from here)</font><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:if>	
				a) Check List&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				b) Medical 2004 Form&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				c) Essentiality Certificate
		</div>
		<div class="line">2). Submit the following copies to finance<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		      <div id="NoFiles" class="line">
		      <div style="width: 450px;  margin-left: 20px;">
				<div  class="fvireq"> 
				  <c:if test="${not empty workflowmap.cghsReimbursement.cghscardref || not empty workflowmap.cghsReimbursement.cghsRequestDetails.cghscardref}">
				     <c:if test="${ not empty workflowmap.cghsReimbursement.cghsRequestDetails.cghscardref}">
				         a) Copy of CGHS card<a href="javascript:showDatabaseFile('${workflowmap.cghsReimbursement.cghsRequestDetails.cghscardref}')">View</a>
				      </c:if>
					      <c:if test="${ not empty workflowmap.cghsReimbursement.cghscardref}">
					           a) Copy of CGHS card<a href="javascript:showDatabaseFile('${workflowmap.cghsReimbursement.cghscardref}')">View</a>
	                      </c:if>			
				  </c:if>
				
				 <c:if test="${ empty workflowmap.cghsReimbursement.cghscardref || empty workflowmap.cghsReimbursement.cghsRequestDetails.cghscardref}">
				       <!--   <c:if test="${ empty workflowmap.cghsReimbursement.cghsCardFile}">
				          a) Copy of CGHS card<a href="javascript:showDatabaseFile('${workflowmap.cghsReimbursement.cghsCardFile}')">View</a>
				        </c:if>--> 
				       <c:if test="${ empty workflowmap.cghsReimbursement.cghsRequestDetails.cghscardref}">
				          a) Copy of CGHS card<a href="javascript:showDatabaseFile('${workflowmap.cghsReimbursement.cghsRequestDetails.cghscardref}')">View</a>
				        </c:if>
				 </c:if>
				</div>
				<div class="svireq"><c:if test="${ (workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='COMPLETED')&&( empty workflowmap.cghsReimbursement.cghscardref )}">
				        <c:if test="${ empty workflowmap.cghsReimbursement.cghsRequestDetails.cghscardref}">
				           <input type="file" id="files1" name="cghsCardFile"/></c:if>
				</c:if></div>
				<c:choose>
				<c:when test="${workflowmap.requestType !='NON CGHS REIMBURSEMENT'}">
				<div class="fvireq">
				b) Prescription copy&nbsp;<a href="javascript:showDatabaseFile('${workflowmap.cghsReimbursement.cghsRequestDetails.prescriptionFile}')">View</a>
				</div>
				<div  class="svireq">
				<c:if test="${ (workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='COMPLETED')}">
				<input type="file" id="files2" name="prescriptionFile"/></c:if>
				</div>
				</c:when>
				<c:otherwise>
				 <!--    <c:if test="${not empty workflowmap.cghsReimbursement.refundChequeFile}">
				<div class="fvireq">
				  b) Refund cheque&nbsp;<a href="javascript:showDatabaseFile('${workflowmap.cghsReimbursement.refundChequeFile}')">View</a></div>
				<div class="svireq"><input type="file" id="files2" name="refundChequeFile"/></div>
				</c:if>  -->
				<div class="fvireq">
				b) Original medical bills&nbsp;<a href="javascript:showDatabaseFile('${workflowmap.cghsReimbursement.medicalBillsFile}')">View</a></div>
				<div  class="svireq">
				<c:if test="${ (workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='COMPLETED')}">
				<input type="file" id="files2" name="medicalBillsFile"/></c:if></div>
				
				</c:otherwise>
				</c:choose>
				<c:if test="${workflowmap.requestType !='NON CGHS REIMBURSEMENT'}">
				<div class="fvireq">
				c) Original medical bills&nbsp;<a href="javascript:showDatabaseFile('${workflowmap.cghsReimbursement.medicalBillsFile}')">View</a></div>
				<div  class="svireq">
				<c:if test="${ (workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='COMPLETED')}">
				<input type="file" id="files3" name="medicalBillsFile"/></c:if></div>
				</c:if>
				
				<c:if test="${workflowmap.cghsReimbursement.cghsRequestDetails.requestTypeDetails.name eq 'Admission'}">
				<div class="fvireq">
				d) Discharge Summary&nbsp;<a href="javascript:showDatabaseFile('${workflowmap.cghsReimbursement.dischargeSummeryFile}')">View</a></div>
				<div  class="svireq">
				<c:if test="${ (workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='COMPLETED')}">
				<input type="file" id="files4" name="dischargeSummeryFile"/></c:if>
				</div>
				</c:if>
				
				
				<c:if test="${not empty workflowmap.cghsReimbursement.refundChequeFile}">
				<div class="fvireq">
				e) Refund cheque&nbsp;<a href="javascript:showDatabaseFile('${workflowmap.cghsReimbursement.refundChequeFile}')">View</a></div>
				<div class="svireq">
				<c:if test="${ (workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='COMPLETED')}">
				<input type="file" id="files5" name="refundChequeFile"/></c:if></div>
				</c:if>
				
				</div>
				</div>
		</div>
		<div class="quarter">
				 <div style="margin-left:45%">
				 <c:choose>
				  <c:when test="${workflowmap.requestType=='CGHS REIMBURSEMENT'}">
					<c:if test="${ (workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='COMPLETED')}">
					<a href="javascript:manageUploadedFiles('${workflowmap.cghsReimbursement.cghsCardFile}','${workflowmap.cghsReimbursement.cghsRequestDetails.prescriptionFile}','${workflowmap.cghsReimbursement.medicalBillsFile}','${workflowmap.cghsReimbursement.refundChequeFile}');"><div class="appbutton" id="button" style="display:show">Upload</div></a>
		     	    </c:if>
		     	  </c:when>
		     	  <c:otherwise>
		     	  <c:if test="${ (workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='COMPLETED')}">
		     	    <a href="javascript:manageUploadedFiles('${workflowmap.cghsReimbursement.cghsCardFile}','${workflowmap.cghsReimbursement.medicalBillsFile}','','${workflowmap.cghsReimbursement.refundChequeFile}');"><div class="appbutton" id="button" style="display:show">Upload1</div></a>
		     	 </c:if>
		     	  </c:otherwise>
		     	  </c:choose>
		     	</div>
			</div>
			
			
			
	</fieldset>
	</div>
	<input type="hidden" name="admissibleTotalAmount" id="admissibleTotalAmount"/>
	<input type="hidden" name="cghsCardFileName" id="cghsCardFileName"/>
	<input type="hidden" name="prescriptionFileName" id="prescriptionFileName"/>
	<input type="hidden" name="medicalBillsFileName" id="medicalBillsFileName"/>
	<input type="hidden" name="refundChequeFileName" id="refundChequeFileName"/>

	<script type="text/javascript">cghsAdvance = '${workflowmap.cghsReimbursement.advanceDetails}' 
									setCghsValues('${workflowmap.requestType}');
									$jq('#admissibleTotalAmount').val($jq('#amount').text().trim());
									</script>
</div> 
<!-- End : CghsReimbursementDetails.jsp -->
