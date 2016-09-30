<!-- Begin : CghsDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/ajaxUpload.js"></script>

<script type="text/javascript" src="script/cghs.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<div>
	<div class="line">
		<div class="quarter leftmar">Family Member Name</div>
		<div class="quarter">:&nbsp;${workflowmap.cghsRequestDetails.familyMemberDetails.name}</div>
		<div class="quarter leftmar">Relationship</div>
		<div class="quarter">:&nbsp;${workflowmap.cghsRequestDetails.familyMemberDetails.relationDetails.name}</div>
	</div>
	<div class="line">
		<div class="quarter leftmar">As per CGHS Prescription Date</div>
		<div class="quarter">
			:&nbsp;
			<fmt:formatDate
				value="${workflowmap.cghsRequestDetails.prescriptionDate}"
				pattern="dd-MMM-yyyy" />
		</div>
		<div class="quarter leftmar">&nbsp;Consultation / Investigation
			/ Admission</div>
		<div class="quarter">:&nbsp;${workflowmap.cghsRequestDetails.requestTypeDetails.name}</div>
	</div>
	<div class="line">
		<div class="quarter leftmar">Hospital Name</div>
		<div class="quarter">:&nbsp;${workflowmap.cghsRequestDetails.referralHospitalDetails.hospitalName}</div>
		<c:if
			test="${workflowmap.cghsRequestDetails.requestTypeDetails.name eq 'Admission'}">
			<div class="quarter leftmar">Entitlement Ward</div>
			<div class="quarter">:&nbsp;${workflowmap.cghsRequestDetails.wardTypeDetails.name}</div>
		</c:if>
	</div>
	<div class="line">
		<c:if
			test="${not empty workflowmap.cghsRequestDetails.referenceNumber}">
			<div class="quarter leftmar">ASL Reference No</div>
			<div class="quarter">${workflowmap.cghsRequestDetails.referenceNumber}</div>
		</c:if>
		<c:if test="${not empty  workflowmap.cghsRequestDetails.approvedBy}">
			<div class="quarter leftmar">Approved By</div>
			<div class="quarter">${workflowmap.cghsRequestDetails.approvedBy}</div>
		</c:if>
	</div>
	<div class="line">
		<fieldset>
			<legend>
				<strong><font color='red'>Note for Individual</font></strong>
			</legend>
			1). Individual should submit 2 sets<font size="3" color="red">(1
				original + 1 duplicate)</font> of the following document to admin division.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			a) Request letter duly signed&nbsp;(<a
			 <%-- href="javascript:getInitialReport('${workflowmap.cghsRequestDetails.requestID}','initialReq')">Print  --%>
			 href="javascript:getInitialReport('43618','initialReq')">Print 
				& Sign</a>)<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; b) Please print and
			sign the permission letter(<a
				href="javascript:getInitialReport('${workflowmap.cghsRequestDetails.requestID}','${workflowmap.cghsRequestDetails.requestTypeDetails.name}')">Print
				& Sign</a>)<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<div class="line" id="NoFiles">
				<div style="width: 450px; margin-left: 20px;">
					<div class="fvireq">
						c) Prescription copy <a
							href="javascript:showDatabaseFile('${workflowmap.cghsRequestDetails.prescriptionFile}')">View</a>
					</div>
					<div class="svireq">
						<c:if
							test="${ (workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='FORWARDED')}">
							<input type="file" id="files1" name="prescriptionFile" />
							<br>
						</c:if>
					</div>

					<div class="fvireq">
						<c:if test="${ empty workflowmap.cghsRequestDetails.cghscardref}">
				
				d) Copy of CGHS Card <a
								href="javascript:showDatabaseFile('${workflowmap.cghsRequestDetails.cghsCardFile}')">View</a>
						</c:if>
						<c:if
							test="${ not empty workflowmap.cghsRequestDetails.cghscardref}">
				d) Copy of CGHS Card <a
								href="javascript:showDatabaseFile('${workflowmap.cghsRequestDetails.cghscardref}')">View</a>
						</c:if>

					</div>

					<div class="svireq">
						<c:if
							test="${(workflowmap.statusMsg  !='COMPLETED')&&(workflowmap.statusMsg  !='CANCELLED')&& (workflowmap.tdSettStrStatus !='FORWARDED')&&( empty workflowmap.cghsRequestDetails.cghscardref )}">
							<input type="file" id="files2" name="cghsCardFile" />
							<br>
						</c:if>

					</div>
				</div>
			</div>
			<div class="quarter">
				<div style="margin-left: 45%">
					<c:if
						test="${(workflowmap.statusMsg !='COMPLETED')  && (workflowmap.workflowStage !='FORWARDED')&& (workflowmap.tdSettStrStatus !='FORWARDED')}">
						<a
							href="javascript:manageUploadedFiles('${workflowmap.cghsRequestDetails.prescriptionFile}','${workflowmap.cghsRequestDetails.prescriptionFile}','','');"><div
								class="appbutton" id="button" style="display: show">Upload</div></a>
					</c:if>
				</div>
			</div>
		</fieldset>
	</div>



	<div class="line">

		<div class="quarter">
			<div>

				<c:if
					test="${ not empty workflowmap.cghsadvanceRequestId &&  workflowmap.cghsadvanceRequestId != 0}">

					<div class="quarter leftmar">
						Advance RequestId:
						<div class="quarter">
							<a
								href="javascript:getRequestDetails('${workflowmap.cghsadvHistoryID}','${workflowmap.cghsadvanceRequestId}','myRequests','completed','')">${workflowmap.cghsadvanceRequestId}</a>
						</div>
					</div>
				</c:if>
			</div>

		</div>


		<div class="quarter">

			<div></div>
			<div>
				<c:choose>

					<c:when
						test="${not empty workflowmap.cghsadvanceRequestId && workflowmap.cghsadvanceRequestId != 0}">

						<div class="quarter leftmar">
							Settlement RequestId:
							<div class="quarter">
								<a
									href="javascript:getRequestDetails('${workflowmap.cghssetlementHistoryID}','${workflowmap.cghssetlementRequestId}','myRequests','completed','')">
									${workflowmap.cghssetlementRequestId}</a>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<c:if test="${not empty workflowmap.cghsreimbursementId}">
							<div class="quarter leftmar">
								Reimbursement RequestId:
								<div class="quarter">
									<a
										href="javascript:getRequestDetails('${workflowmap.cghsreimbursementHistoryId}','${workflowmap.cghsreimbursementId}','myRequests','completed','')">
										${workflowmap.cghsreimbursementId}</a>
								</div>
							</div>
						</c:if>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="quarter">
			<c:if
				test="${empty workflowmap.cghssetlementRequestId && empty workflowmap.cghsadvanceRequestId &&  empty workflowmap.cghsreimbursementId}">
				<fieldset>
					<legend>
						<strong><font color='red'> ADVANCE / REIMBURSEMENT
								- ONLINE NOT TAKEN </font></strong>
					</legend>
				</fieldset>
			</c:if>
		</div>

	</div>




	<input type="hidden" name="prescriptionFileName"
		id="prescriptionFileName" /> <input type="hidden"
		name="cghsCardFileName" id="cghsCardFileName" /> <input type="hidden"
		name="cghsCardFileName" id="cghsCardFileName" /> <input type="hidden"
		id="requestID" /> <input type="hidden" id="historyID" /> <input
		type="hidden" id="cghsadvanceRequestId" /> <input type="hidden"
		id="statusMsg" /> <input type="hidden" id="back" /> <input
		type="hidden" id="message" />
</div>
<!-- End : CghsDetails.jsp -->