<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin: RequestDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css"
	rel="stylesheet" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/workflow.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/cghs.js"></script>
<script type="text/javascript" src="script/mmgscript.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script language="javascript" src="script/tutionFee.js"></script>
<script type="text/javascript" src="script/ajaxUpload.js"></script>
<title>View Request</title>
</head>

<body>
	<form:form method="post" commandName="workflowmap" id="workflowmap">
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
								<div class="headTitle_sub2">
									<c:if test="${workflowmap.requestType == 'ADDRESS'}">
										${workflowmap.rowNumber}										
									</c:if>
									<c:choose>
										<c:when
											test="${(workflowmap.requestType=='TADA TD BUILDUP' || workflowmap.requestType=='TADA TD PROJECT') && (workflowmap.tadaAmendmentDetails!='' && workflowmap.tadaAmendmentDetails!=null && workflowmap.tadaAmendmentDetails!='[]')}">
											<font color="green">${workflowmap.requestType} </font>
											<c:if
												test="${workflowmap.tadaApprovalRequestDTO.stayDuration==0}">
												<font color="orange">Cancel </font>
											</c:if>
											<c:if
												test="${workflowmap.tadaApprovalRequestDTO.stayDuration!=0}">
												<font color="orange">Amendment </font>
											</c:if>
											<font color="red"> Request Details of</font>
											<font color="voilet">${workflowmap.requester}</font>
										</c:when>
										<c:otherwise>
											<font color="green">${workflowmap.requestType}</font>
											<c:if test="${workflowmap.referenceID != null}">
												<font color="green"> Amendment </font>
											</c:if>
											<c:if test="${workflowmap.referenceID eq null}">
												<font color="red"> Request </font>
											</c:if>
											<font color="red"> Details of </font>
											<font color="voilet">${workflowmap.requester}</font>
										</c:otherwise>
									</c:choose>
								</div>




								<div id="notification" class="notification"
									style="display: none;">
									<img src="./images/ajax-loader.gif" width="32" height="16"
										align="middle" />&nbsp;Loading...
								</div>

								<%-- Content Page starts --%>
								<div id="result"></div>
								<div class="line">
									<div class="line">
										<div class="quarter leftmar">Request ID</div>
										<div class="quarter" id="requestID">${workflowmap.requestId}</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Requester SFID</div>
										<div class="quarter">${workflowmap.requesterSfid}</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Requester Designation</div>
										<div class="quarter">${workflowmap.designation}</div>
									</div>

									<!--this content added again   -->
									<%-- <div class="line">
										<div class="quarter leftmar">Requested Date</div>
										<div class="quarter">${workflowmap.requestDate}</div>
									</div> --%>

									<c:if test="${workflowmap.requestType=='FAMILY'}">
										<div class="line">
											<div class="quarter leftmar">Record Type</div>
											<div class="quarter">${workflowmap.recordType}</div>
										</div>
										<div class="line">
											<div class="quarter leftmar">Relationship</div>
											<div class="quarter">${workflowmap.relation}</div>
										</div>
										<c:if test="${workflowmap.recordType=='OLD'}">
											<div class="line">
												<div class="quarter leftmar">Family Member Name</div>
												<div class="quarter">${workflowmap.familyMemberName}</div>
											</div>
										</c:if>
									</c:if>
									<c:if test="${workflowmap.requestType=='NOMINEE'}">
										<div class="line">
											<div class="quarter leftmar">Record Type</div>
											<div class="quarter">${workflowmap.recordType}</div>
										</div>
										<div class="line">
											<div class="quarter leftmar">Nominee Type</div>
											<div class="quarter">${workflowmap.nomineeType}</div>
										</div>
									</c:if>
									<c:if test="${workflowmap.requestType ne 'LTC WATER'}">
										<div class="line">
											<div class="quarter leftmar">Requester IP Address</div>
											<div class="quarter">${workflowmap.ipAddress}</div>
										</div>
									</c:if>


									<!--this div & content added by bkr 05/05/2016   -->
									<div>
										<c:choose>
											<c:when test="${workflowmap.requestType=='TADA WATER'}">


												<fieldset style="border: 1px solid #00ff00 !important;">
													<legend>
														<strong><font color='green'>TADA WATER</font></strong>
													</legend>
													<div>
														<jsp:include page="TadaWaterDetails.jsp"></jsp:include>
													</div>
												</fieldset>

											</c:when>

										</c:choose>

									</div>
									
									
										<!--added by bkr 21/06/2016 for ERPLEAVECancel  -->
									<div>
										<c:choose>
											<c:when test="${workflowmap.requestType=='ERP LEAVE CANCEL'}">


												<fieldset style="border: 1px solid #00ff00 !important;">
													<legend>
														<strong><font color='green'>Leave Deatils</font></strong>
													</legend>
													<div>
														<jsp:include page="ERPLeaveDetails.jsp"></jsp:include>
													</div>
												</fieldset>

											</c:when>

										</c:choose>

									</div>
									
									
									<!--added by bkr 21/06/2016 for ERPLEAVE  -->
									<div>
										<c:choose>
											<c:when test="${workflowmap.requestType=='ERP LEAVE'}">


												<fieldset style="border: 1px solid #00ff00 !important;">
													<legend>
														<strong><font color='green'>Leave Deatils</font></strong>
													</legend>
													<div>
														<jsp:include page="ERPLeaveDetails.jsp"></jsp:include>
													</div>
												</fieldset>

											</c:when>

										</c:choose>

									</div>
									<!-- added by mohib for loan -->
									<div>
										<c:choose>
											<c:when test="${workflowmap.requestType=='LOAN'}">
												<fieldset style="border: 1px solid #00ff00 !important;">
													<legend>
														<strong><font color='green'>Loan Details</font></strong>
													</legend>
													<div>
														<jsp:include page="ErpLoanDetails.jsp"></jsp:include>
													</div>
												</fieldset>
											</c:when>
										</c:choose>
									</div>
									<!-- end for loan -->

									<!--this div added by bkr 24/05/2016  -->


									<div>
										<c:choose>
											<c:when test="${workflowmap.requestType=='LTC WATER'}">
												<div class="line">
													<div class="quarter leftmar">Requester IP Address</div>
													<div class="quarter">${workflowmap.ltcWaterRequestDTO.ipAddress}</div>
												</div>
												<fieldset style="border: 1px solid #00ff00 !important;">
													<legend>
														<strong><font color='green'>Annual Leave
																Details</font></strong>
													</legend>
													<div>
														<jsp:include page="LTCWaterDetails.jsp"></jsp:include>
													</div>
												</fieldset>

											</c:when>

										</c:choose>

									</div>

									<c:choose>
										<c:when test="${workflowmap.requestType=='CGHS ADVANCE'}">
											<fieldset>
												<legend>
													<strong><font color='green'>CGHS Advance
															Request Details</font></strong>
												</legend>
												<div>
													<jsp:include page="CghsAdvanceDetails.jsp"></jsp:include>
													<c:if
														test="${workflowmap.checkStage eq 'last' && workflowmap.statusMsg eq ''}">
														<div class="line">
															<div class="quarter leftmar">Issued
																Amount(${workflowmap.cghsAdvanceDTO.percentage}% of Est.
																Amt.)</div>
															<!--  
															<div class="quarter">${cghs.cghsAdvanceDetails.cghsRequestDetails.familyMemberDetails.name}</div>
															-->
															<div class="quarter">
																<input name="issuedAmount" id="issuedAmount"
																	value="${workflowmap.cghsAdvanceDTO.configurationAmount}" />
															</div>
														</div>
													</c:if>
												</div>
											</fieldset>
										</c:when>
										<c:when
											test="${workflowmap.requestType=='CGHS REIMBURSEMENT'}">
											<fieldset>
												<legend>
													<strong><font color='green'>CGHS
															Reimbursement Request Details</font></strong>
												</legend>
												<div>
													<jsp:include page="CghsReimbursementDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when
											test="${workflowmap.requestType=='NON CGHS REIMBURSEMENT'}">
											<fieldset title="">
												<legend>
													<strong><font color='green'>Non CGHS
															Reimbursement Request Details</font></strong>
												</legend>
												<div>
													<jsp:include page="CghsReimbursementDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when test="${workflowmap.requestType=='CGHS SETTLEMENT'}">
											<fieldset>
												<legend>
													<strong><font color='green'>CGHS Settlement
															Request Details</font></strong>
												</legend>
												<div>
													<jsp:include page="CghsReimbursementDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when
											test="${workflowmap.requestType=='CGHS EMERGENCY' || workflowmap.requestType=='NON CGHS EMERGENCY'}">
											<fieldset>
												<legend>
													<strong><font color='green' id="emergency">CGHS
															Emergency Request Details</font></strong>
												</legend>
												<div>
													<jsp:include page="CghsEmergencyDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when test="${workflowmap.requestType=='CGHS'}">
											<fieldset>
												<legend>
													<strong><font color='green'>CGHS Request
															Details</font></strong>
												</legend>
												<div>
													<jsp:include page="CghsDetails.jsp"></jsp:include>
													<c:if
														test="${workflowmap.checkStage eq 'last' && workflowmap.message eq 'pending'}">
														<div class="line">
															<div class="quarter leftmar">ASL Reference No</div>
															<div class="quarter">
																<form:input path="referenceNumber" id="referenceNumber"
																	value="ASL/" />
															</div>

															<div class="quarter">
																Approved Date<span class="mandatory">*</span>
															</div>
															<div class="quarter">
																<form:input path="approvedDate" id="approvedDate"
																	cssClass="dateClass" readonly="true" />
																<img src="./images/calendar.gif" id="approvedDateImg" />
																<script type="text/javascript">
																	Calendar
																			.setup({
																				inputField : "approvedDate",
																				ifFormat : "%d-%b-%Y",
																				showsTime : false,
																				button : "approvedDateImg",
																				singleClick : true,
																				step : 1
																			});
																</script>
															</div>
														</div>
													</c:if>
												</div>
											</fieldset>
										</c:when>
										<%-- <c:when
											test="${workflowmap.requestType=='LOAN' || workflowmap.requestType=='HOUSE BUILDING LOAN'|| workflowmap.requestType=='LOAN CONVEYANCE'}">
											<fieldset>
												<legend>
													<strong><font color='green'>Loan Details</font></strong>
												</legend>
												<div id="loanDetails">
													<jsp:include page="loanDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when> --%>
										<c:when test="${workflowmap.requestType=='TUITION FEE'}">
											<fieldset>
												<legend>
													<strong><font color='green'>Tution Fee
															Details</font></strong>
												</legend>
												<div id="tutionFeeDetails" style="float: none;">
													<jsp:include page="tutionFeeClaimRequestDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when test="${workflowmap.requestType=='TELEPHONE BILL'}">
											<fieldset>
												<legend>
													<strong><font color='green'>Telephone Bill
															Details</font></strong>
												</legend>
												<div id="telephoneBillDetails">
													<jsp:include page="TelephoneBillClaimRequestDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when test="${workflowmap.requestType=='MT VEHICLE'}">

											<fieldset>
												<legend>
													<strong><font color='green'>Vehicle Request
															Details</font></strong>
												</legend>
												<div id="vehicleRequestDetails">
													<jsp:include page="MTVehicleRequestDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when test="${workflowmap.requestType=='FPA' }">
											<fieldset>
												<legend>
													<strong><font color='green'>FPA Request
															Details</font></strong>
												</legend>
												<div id="fpaDetails">
													<jsp:include page="fpaDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when
											test="${workflowmap.requestType=='LTC REFUND' || workflowmap.requestType=='LTC CANCEL' || workflowmap.requestType=='LTC APPR CUM ADV CANCEL'}">
											<fieldset>
												<legend>
													<strong><font color='green'>${workflowmap.requestType}
															Details</font></strong>
												</legend>
												<div>
													<jsp:include page="ltcrefundDetails.jsp"></jsp:include>
												</div>

											</fieldset>
										</c:when>
										<c:when
											test="${workflowmap.requestType=='LTC ADVANCE' || workflowmap.requestType=='LTC APPROVAL CUM ADVANCE' || workflowmap.requestType=='LTC ADVANCE AMENDMENT'}">
											<fieldset>
												<legend>
													<strong><font color='green'>Ltc Advance</font></strong>
												</legend>
												<div>
													<jsp:include page="ltcAdvanceDetails.jsp"></jsp:include>
												</div>

											</fieldset>
										</c:when>
										<c:when
											test="${workflowmap.requestType=='LTC APPROVAL' ||workflowmap.requestType=='LTC APPROVAL AMENDMENT'}">
											<fieldset>
												<legend>
													<strong><font color='green'>LTC Request
															Details</font></strong>
												</legend>
												<div>
													<jsp:include page="LTCDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when
											test="${workflowmap.requestType eq 'LTC REIMBURSEMENT'}">
											<fieldset>
												<legend>
													<strong><font color='green'>LTC
															Reimbursement Details</font></strong>
												</legend>
												<div>
													<jsp:include page="LTCReimbursement.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when test="${workflowmap.requestType eq 'LTC SETTLEMENT'}">
											<fieldset>
												<legend>
													<strong><font color='green'>LTC Settlement
															Details</font></strong>
												</legend>
												<div>
													<jsp:include page="LTCReimbursement.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when test="${workflowmap.requestType == 'LEAVE'}">
											<fieldset>
												<legend>
													<strong> <font color='green'>Leave Details
															<%-- <c:if test="${workflowmap.referenceID != null}">Leave Amendment Details</c:if>
													<c:if test="${workflowmap.referenceID eq null}">Leave Request Details</c:if> --%>
													</font></strong>
												</legend>
												<div>
													<jsp:include page="LeaveDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>

										<c:when test="${workflowmap.requestType == 'LEAVE CANCEL'}">
											<fieldset>
												<legend>
													<strong><font color='green'>Leave Details</font></strong>
												</legend>
												<div class="line">
													<jsp:include page="LeaveDetails.jsp"></jsp:include>
												</div>
												<div class="line">
													<div class="quarter bold">Leave Cancellation Form</div>
													<div class="quarter">
														:&nbsp;<a
															href="javascript:leaveApplication('${workflowmap.requestId}','${workflowmap.leaveRequestDetails.leaveTypeDetails.code}','${workflowmap.requestType}')">PDF</a>
													</div>
												</div>
												<div class="line">
													<div class="quarter bold">Leave Cancellation Date</div>
													<div class="quarter">
														:&nbsp;
														<fmt:formatDate pattern="dd-MMM-yyyy"
															value="${workflowmap.formattedAppliedDate}" />
													</div>
												</div>
												<div class="line">
													<div class="quarter bold">Reason for Cancellation</div>
													<div class="threefourth">
														:&nbsp;
														<c:out value="${workflowmap.reason}"></c:out>
													</div>
												</div>
											</fieldset>
										</c:when>
										<c:when
											test="${workflowmap.requestType == 'LEAVE CONVERSION'}">
											<fieldset>
												<legend>
													<strong><font color='green'>Leave Details</font></strong>
												</legend>
												<div class="line">
													<jsp:include page="LeaveDetails.jsp"></jsp:include>
												</div>
												<fieldset>
													<legend>
														<strong><font color='green'>Leave
																Conversion Details</font></strong>
													</legend>
													<div class="line">
														<jsp:include page="LeaveConversionDetails.jsp"></jsp:include>
													</div>
													<div class="line">
														<div class="quarter bold">Leave Conversion Date</div>
														<div class="quarter">
															:&nbsp;
															<fmt:formatDate pattern="dd-MMM-yyyy"
																value="${workflowmap.formattedAppliedDate}" />
														</div>
													</div>
													<div class="line">
														<div class="quarter bold">Reason for Conversion</div>
														<div class="threefourth">
															:&nbsp;
															<c:out value="${workflowmap.reason}"></c:out>
														</div>
													</div>
												</fieldset>
											</fieldset>
										</c:when>
										<c:when
											test="${workflowmap.requestType eq 'NEW QUARTER' || workflowmap.requestType eq 'CHANGE QUARTER'}">
											<fieldset>
												<legend>
													<strong><font color='green'>Quarter Details</font></strong>
												</legend>
												<div>
													<jsp:include page="QuarterRequestDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>

										<c:when
											test="${workflowmap.requestType eq 'Property Detials'}">
											<fieldset>
												<legend>
													<strong><font color='green'>Quarter Details</font></strong>
												</legend>
												<div>
													<jsp:include page="propertyDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>

										<c:when
											test="${workflowmap.requestType eq 'HIGHER QUALIFICATION'}">
											<fieldset>
												<legend>
													<strong><font color='green'>Higher
															Qualification Details</font></strong>
												</legend>
												<div>
													<jsp:include page="HQRequestDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when test="${workflowmap.requestType=='DEMAND'}">
											<div class="headTitle">Demand Details</div>
											<div>
												<jsp:include page="DemandRequestDetails.jsp"></jsp:include>
											</div>
										</c:when>
										<c:when test="${workflowmap.requestType=='Self Lab Transfer'}">
											<fieldset>
												<legend>
													<strong><font color='green'>Self Lab
															Transfer</font></strong>
												</legend>
												<div>
													<jsp:include page="TransferRequestDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when
											test="${workflowmap.requestType=='Employee Lab Transfer'}">
											<fieldset>
												<legend>
													<strong><font color='green'>Employee
															Transfer Details</font></strong>
												</legend>
												<div>
													<jsp:include page="TransferRequestDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when test="${workflowmap.requestType=='DEMANDCANCEL'}">
											<div class="headTitle">Demand Cancel Details</div>
											<div>
												<jsp:include page="DemandRequestDetails.jsp"></jsp:include>
											</div>
										</c:when>
										<c:when test="${workflowmap.requestType=='INVOICECANCEL'}">
											<div class="headTitle">IR Details</div>
											<div>
												<jsp:include page="InvoiceRequestDetails.jsp"></jsp:include>
											</div>
										</c:when>
										<c:when test="${workflowmap.requestType eq 'VOUCHER'}">
											<div class="headTitle">Voucher Details</div>
											<div>
												<jsp:include page="VoucherRequestDetails.jsp"></jsp:include>
											</div>
										</c:when>
										<c:when
											test="${workflowmap.requestType eq 'CERTIFICATESANCTION'}">
											<div class="headTitle">Voucher Details</div>
											<div>
												<jsp:include page="VoucherRequestDetails.jsp"></jsp:include>
											</div>
										</c:when>
										<c:when test="${workflowmap.requestType=='INVOICE'}">
											<div class="headTitle">IR Details</div>
											<div>
												<jsp:include page="InvoiceRequestDetails.jsp"></jsp:include>
											</div>
										</c:when>
										<c:when test="${workflowmap.requestType=='PASSPORT'}">
											<fieldset>
												<legend>
													<strong><font color='green'>Passport
															Application Details</font></strong>
												</legend>
												<div>
													<jsp:include page="PassportRequestDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when test="${workflowmap.requestType=='TADA TD BUILDUP'}">
											<fieldset>
												<legend>
													<strong><font color='green'>TADA TD <c:if
																test="${(workflowmap.tadaApprovalRequestDTO.ammendementId!='' && workflowmap.tadaApprovalRequestDTO.ammendementId!=null) && 
											(workflowmap.tadaApprovalRequestDTO.stayDuration!=0)}">Amendment </c:if>
															<c:if
																test="${(workflowmap.tadaApprovalRequestDTO.ammendementId!='' && workflowmap.tadaApprovalRequestDTO.ammendementId!=null) && 
											(workflowmap.tadaApprovalRequestDTO.stayDuration==0)}">Cancel </c:if>Request
															Details
													</font></strong>
												</legend>
												<div>
													<jsp:include page="TadaTdDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when test="${workflowmap.requestType=='TADA TD PROJECT'}">
											<fieldset>
												<legend>
													<strong><font color='green'>TADA TD Request
															Details</font></strong>
												</legend>
												<div>
													<jsp:include page="TadaTdDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>



										<c:when test="${workflowmap.requestType=='TADA TD ADVANCE'}">
											<fieldset>
												<legend>
													<strong><font color='green'>TADA TD Request
															Details</font></strong>
												</legend>
												<div>
													<jsp:include page="TadaTdAdvanceDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when
											test="${workflowmap.requestType=='TADA TD SETTLEMENT'}">
											<fieldset>
												<legend>
													<strong><font color='green'>TADA TD
															Settlement Request Details</font></strong>
												</legend>
												<div>
													<jsp:include page="TadaTdSettlementDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when
											test="${workflowmap.requestType=='TADA TD REIMBURSEMENT'}">
											<fieldset>
												<legend>
													<strong><font color='green'>TADA TD
															Reimbursement Request Details</font></strong>
												</legend>
												<div>
													<jsp:include page="TadaTdSettlementDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when
											test="${workflowmap.requestType=='TRAINING NOMINATION'}">
											<fieldset>
												<legend>
													<strong><font color='green'>Training
															Nomination Request Details</font></strong>
												</legend>
												<div>
													<jsp:include page="TrainingNominationDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:when
											test="${workflowmap.requestType=='CANCEL TRAINING NOMINATION'}">
											<fieldset>
												<legend>
													<strong><font color='green'>Cancel Training
															Nomination Request Details</font></strong>
												</legend>
												<div>
													<jsp:include page="CancelTrainingNominationDetails.jsp"></jsp:include>
												</div>
											</fieldset>
										</c:when>
										<c:otherwise>


											<!--this condition added by bkr 05/05/2016  -->
											<c:if
												test="${workflowmap.requestType ne 'TADA WATER' && workflowmap.requestType ne 'LTC WATER'  && workflowmap.requestType ne 'ERP LEAVE' &&  workflowmap.requestType ne 'ERP LEAVE CANCEL' &&  workflowmap.requestType ne 'LOAN'  }">

												<div class="line bold">Requested Fields</div>
												<div class="line">
													<table width="100%" cellpadding="2" cellspacing="0"
														border="1" class="sub_2">
														<tr>
															<th width="33%">Field Name</th>
															<th width="33%">From</th>
															<th width="33%">To</th>
														</tr>
														<c:forEach items="${workflowmap.modifiedValues}"
															var="modifiedValues">
															<tr>
																<td>${modifiedValues.fieldName}</td>
																<td>${modifiedValues.from}</td>
																<td>${modifiedValues.to}</td>
															</tr>
														</c:forEach>
													</table>
													<c:if test="${workflowmap.requestType == 'ADDRESS'}">
														<div class="bold quarter">Address Declaration Form</div>
														<div class="bold quarter">
															<a
																href="javascript:viewaddressReports('${workflowmap.requestId}','${workflowmap.requesterSfid}','${workflowmap.rowNumber}');">PDF</a>
														</div>
													</c:if>
												</div>

												<c:if test="${workflowmap.requestType=='NOMINEE'}">
													<div class="line bold">Incontengensy Details</div>
													<div class="line">
														<%
															int i = 0;
														%>
														<c:forEach items="${workflowmap.contengensyList}"
															var="contList">
															<%
																i++;
															%>
															<fieldset>
																<legend>
																	<strong><font color='green'>Incontengensy
																			<%=i%></font></strong>
																</legend>
																<table width="100%" align="center" cellpadding="2"
																	cellspacing="0" class="sub_2">

																	<c:if test="${contList.familyID!='other'}">
																		<tr>
																			<td class="bold">Family Member</td>
																			<td colspan=3></td>
																		</tr>
																	</c:if>
																	<tr>
																		<td width="25%" class="bold">Nominee</td>
																		<td width="25%">${contList.nomineeName}</td>
																		<td width="25%" class="bold">Percentage</td>
																		<td width="25%">${contList.percentage}</td>
																	</tr>

																	<tr>
																		<td class="bold">Remarks</td>
																		<td>${contList.remarks}</td>
																		<td colspan=2></td>
																	</tr>
																	<c:if test="${contList.familyID=='other'}">
																		<tr>
																			<td class="bold">Address Line1</td>
																			<td>${contList.address1}</td>
																			<td class="bold">Address Line2</td>
																			<td>${contList.address2}</td>
																		</tr>
																		<tr>
																			<td class="bold">Address Line3</td>
																			<td>${contList.address3}</td>
																			<td class="bold">City</td>
																			<td>${contList.city}</td>
																		</tr>
																		<tr>
																			<td class="bold">State</td>
																			<td>${contList.stateId}</td>
																			<td class="bold">District</td>
																			<td>${contList.districtId}</td>
																		</tr>
																		<tr>
																			<td class="bold">Pincode</td>
																			<td>${contList.pincode}</td>
																			<td></td>
																			<td></td>
																		</tr>
																	</c:if>
																</table>
															</fieldset>
														</c:forEach>
													</div>
												</c:if>
											</c:if>
										</c:otherwise>

									</c:choose>

									<c:if test="${workflowmap.message!='requestDelegation'}">

										<div class="line" style="display: none" id="requestBackBtn">
											<div class="expbutton">
												<a href="javascript:pisHome()"><span>Home</span></a>
											</div>
											<div class="expbutton">
												<a href="javascript:moreRequests('pending')"><span>Back</span></a>
											</div>
										</div>
									</c:if>
									<c:if test="${workflowmap.message =='requestDelegation'}">
										<div class="line">
											<div class="expbutton">
												<a href="javascript:requestDelegation()"><span>Back</span></a>
											</div>
										</div>
									</c:if>
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


				<div class="mainDisplay">
					<div class="whitebox1">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								<div class="whitebox_tr"></div>
							</div>
						</div>
						<div class="whitebox_m1">
							<div class="lightblueBg1">
								<table width="100%">
									<tr>
										<td>


											<div id="historyDetails">
												<jsp:include page="RequestHistoryDetails.jsp"></jsp:include>
											</div>
											<div id="requestButtons">
												<c:if test="${sessionScope.Result=='home'}">
													<c:choose>
														<c:when test="${message=='pending'}">
															<div class="line">
																<div class="quarter">Remarks</div>
																<div class="quarter">
																	<form:textarea path="remarks" id="remarks"></form:textarea>
																</div>
															</div>
															<c:if test="${not empty workflowmap.delegateList}">
																<div class="line">
																	<div class="quarter bold leftmar">Manual Delegate
																		List</div>
																	<div class="quarter">
																		<form:select path="deletedSfid" id="deletedSfid">
																			<form:option value="select">Select</form:option>
																			<form:options items="${workflowmap.delegateList}"
																				itemValue="key" itemLabel="name" />
																		</form:select>
																	</div>
																</div>
															</c:if>
															<div class="line">
																<c:if
																	test="${(workflowmap.loanPaymentDetails.loanTypeID == '4') || (workflowmap.loanPaymentDetails.loanTypeID == '5')
    	 													|| (workflowmap.loanPaymentDetails.loanTypeID == '6') || (workflowmap.loanPaymentDetails.loanTypeID == '7') || (workflowmap.loanPaymentDetails.loanTypeID == '8') ||(workflowmap.loanHBAPaymentDetails.loanTypeID == '9')}">
																	<c:if
																		test="${workflowmap.lastStagePendingCheck=='lpending'}">
																		<div class="expbutton">
																			<a
																				href="javascript:sendHQRequest('${workflowmap.historyID}','${workflowmap.requestId}','${workflowmap.requestTypeID}')"><span>Send
																					HQ</span></a>
																		</div>
																	</c:if>
																</c:if>
																<c:choose>
																	<c:when
																		test="${(workflowmap.lastStagePendingCheck=='lpending') && ((workflowmap.loanPaymentDetails.loanTypeID == '4') || (workflowmap.loanPaymentDetails.loanTypeID == '5')
    	 													|| (workflowmap.loanPaymentDetails.loanTypeID == '6') || (workflowmap.loanPaymentDetails.loanTypeID == '7') || (workflowmap.loanPaymentDetails.loanTypeID == '8') ||(workflowmap.loanHBAPaymentDetails.loanTypeID == '9')) }">
																	</c:when>
																	<c:otherwise>
																		<div id="tadaApproved">
																			<div class="expbuttongreen">
																				<a
																					href="javascript:approveRequest('${workflowmap.historyID}','${workflowmap.requestType}','${workflowmap.requestId}','${workflowmap.approvedDept}','${workflowmap.workflowStageID}','${workflowmap.requestTypeID}');">
																					<span id="approveDiv">${workflowmap.workflowStage}</span>
																				</a>
																			</div>
																		</div>
																	</c:otherwise>
																</c:choose>
																<c:if test="${not empty workflowmap.delegateList}">
																	<div class="expbuttondeleg" id="delegateBtnId">
																		<a
																			href="javascript:delegateRequest('${workflowmap.historyID}','${workflowmap.requestId}');"><span>Delegate</span></a>
																	</div>
																</c:if>
																<c:if
																	test="${(workflowmap.lastStagePendingCheck=='lpending') && ((workflowmap.requestTypeID=='33') ||( workflowmap.requestTypeID=='34'))}">
																	<div class="expbutton">
																		<a
																			href="javascript:sendToEMU('${workflowmap.historyID}','sendToEMU','${workflowmap.requestId}','${workflowmap.requestTypeID}');"><span>Send
																				To EMU</span></a>
																	</div>
																</c:if>
																<!--only added  if condition by bkr 29/06/2016 for the purpose of remove decline button in cancel request workflow   -->
																<c:if test="${workflowmap.requestType ne 'ERP LEAVE CANCEL'}">
																<div id="tadaDecline">
																	<div class="expbuttonred">
																		<a
																			href="javascript:declineRequest('${workflowmap.historyID}','${workflowmap.requestType}','${workflowmap.requestId}','${workflowmap.approvedDept}')"><span>Decline</span></a>
																	</div>
																</div>
																</c:if>
																<!-- <div><a href="javascript:escalated()"><div class="appbutton">Escalated</div></a></div>-->
															
															<!--below code commented by bkr for remove hold button in workflow by bkr 29/06/2016  -->
															<%-- 	<div id="tadaHold">
																	<div class="expbuttonorg">
																		<a
																			href="javascript:waitRequest('${workflowmap.historyID}','${workflowmap.requestId}','${workflowmap.requestTypeID}')"><span>Hold</span></a>
																	</div>
																</div> --%>
																<div class="expbutton">
																	<a href="javascript:pisHome()"><span>Go Home</span></a>
																</div>
																<c:if test="${workflowmap.back eq 'home'}">
																	<div class="expbutton">
																		<a href="javascript:moreRequests('pending')"><span>Back</span></a>
																	</div>
																</c:if>
															</div>
														</c:when>
														<c:when test="${message=='delegated'}">
															<div class="line">
																<div class="quarter">Remarks</div>
																<div class="quarter">
																	<form:textarea path="remarks" id="remarks"></form:textarea>
																</div>
															</div>
															<c:if test="${not empty workflowmap.delegateList}">
																<%-- <div class="line">
															<div class="quarter bold leftmar">Manual Delegate List</div>
															<div class="quarter">
																<form:select path="deletedSfid" id="deletedSfid">
																	<form:option value="select">Select</form:option>
																	<form:options items="${workflowmap.delegateList}" itemValue="key" itemLabel="name"/>
																</form:select>
															</div>
														</div> --%>
																<!-- Manual Delegate button will be enabled -->
															</c:if>
															<div class="line">
																<c:if
																	test="${(workflowmap.loanPaymentDetails.loanTypeID == '4') || (workflowmap.loanPaymentDetails.loanTypeID == '5')
    	 													|| (workflowmap.loanPaymentDetails.loanTypeID == '6') || (workflowmap.loanPaymentDetails.loanTypeID == '7') || (workflowmap.loanPaymentDetails.loanTypeID == '8') ||(workflowmap.loanHBAPaymentDetails.loanTypeID == '9')}">
																	<c:if
																		test="${workflowmap.lastStagePendingCheck=='lpending'}">
																		<div class="expbutton">
																			<a
																				href="javascript:sendHQRequest('${workflowmap.historyID}','${workflowmap.requestId}','${workflowmap.requestTypeID}')"><span>Send
																					HQ</span></a>
																		</div>
																	</c:if>
																</c:if>
																<c:choose>
																	<c:when
																		test="${(workflowmap.lastStagePendingCheck=='lpending') && ((workflowmap.loanPaymentDetails.loanTypeID == '4') || (workflowmap.loanPaymentDetails.loanTypeID == '5')
    	 													|| (workflowmap.loanPaymentDetails.loanTypeID == '6') || (workflowmap.loanPaymentDetails.loanTypeID == '7') || (workflowmap.loanPaymentDetails.loanTypeID == '8') ||(workflowmap.loanHBAPaymentDetails.loanTypeID == '9')) }">
																	</c:when>
																	<c:otherwise>
																		<div id="tadaApproved">
																			<div class="expbuttongreen">
																				<a
																					href="javascript:approveRequest('${workflowmap.historyID}','${workflowmap.requestType}','${workflowmap.requestId}','${workflowmap.demandRequestDetails.approvedDept}','${workflowmap.workflowStageID}','${workflowmap.requestTypeID}');">
																					<span>${workflowmap.workflowStage}</span>
																				</a>
																			</div>
																		</div>
																	</c:otherwise>
																</c:choose>
																<c:if test="${not empty workflowmap.delegateList}">
																	<%-- <div class="expbuttondeleg" id="delegateBtnId"><a href="javascript:delegateRequest('${workflowmap.historyID}','${workflowmap.requestId}');"><span>Delegate</span></a></div> --%>
																	<!-- delegation button will be enabled -->
																</c:if>
																<div id="tadaDecline">
																	<div class="expbuttonred">
																		<a
																			href="javascript:declineRequest('${workflowmap.historyID}','${workflowmap.requestType}','${workflowmap.requestId}','${workflowmap.demandRequestDetails.approvedDept}')"><span>Decline</span></a>
																	</div>
																</div>
																<!-- <div><a href="javascript:escalated()"><div class="appbutton">Escalated</div></a></div>-->
																<div id="tadaHold">
																	<div class="expbuttonorg">
																		<a
																			href="javascript:waitRequest('${workflowmap.historyID}','${workflowmap.requestId}','${workflowmap.requestTypeID}')"><span>Hold</span></a>
																	</div>
																</div>
																<div class="expbutton">
																	<a href="javascript:pisHome()"><span>Go Home</span></a>
																</div>
																<c:if test="${workflowmap.back eq 'home'}">
																	<div class="expbutton">
																		<a href="javascript:moreRequests('delegated')"><span>Back</span></a>
																	</div>
																</c:if>

															</div>
														</c:when>
														<c:when test="${message=='myRequests'}">
															<c:if test="${workflowmap.statusMsg=='PENDING'}">
																<div class="line">
																	<div class="expbuttonred">
																		<a
																			href="javascript:cancelRequest('${workflowmap.historyID}','${workflowmap.requestType}','${workflowmap.requestId}','${workflowmap.demandRequestDetails.approvedDept}')"><span>Cancel</span></a>
																	</div>
																	<div class="expbutton">
																		<a href="javascript:pisHome()"><span>Go
																				Home</span></a>
																	</div>
																	<c:if test="${workflowmap.back eq 'home'}">
																		<div class="expbutton">
																			<a href="javascript:moreRequests('myrequest')"><span>Back</span></a>
																		</div>
																	</c:if>
																</div>
															</c:if>
															<c:if test="${workflowmap.statusMsg!='PENDING'}">
																<div class="line">
																	<div class="expbutton">
																		<a href="javascript:pisHome()"><span>Go
																				Home</span></a>
																	</div>
																	<c:if
																		test="${(workflowmap.requestTypeID == 45) || (workflowmap.requestTypeID == 46)}">
																		<c:if
																			test="${(workflowmap.statusMsg !='COMPLETED') && (workflowmap.statusMsg !='CANCELLED') && (workflowmap.statusMsg !='SANCTIONED')}">
																			<%-- <div class="expbutton"><a href="javascript:cancelRequest('${workflowmap.historyID}','${workflowmap.requestType}','${workflowmap.requestId}','${workflowmap.demandRequestDetails.approvedDept}')"><span>Cancel</span></a></div> --%>
																		</c:if>

																	</c:if>
																	<c:if test="${workflowmap.back eq 'home'}">
																		<div class="expbutton">
																			<a href="javascript:moreRequests('myrequest')"><span>Back</span></a>
																		</div>
																	</c:if>
																</div>
															</c:if>
														</c:when>
														<c:otherwise>
															<div class="line">
																<div class="expbutton">
																	<a href="javascript:pisHome()"><span>Go Home</span></a>
																</div>
																<c:if test="${workflowmap.back eq 'home'}">
																	<div class="expbutton">
																		<a
																			href="javascript:moreRequests('${workflowmap.message}')"><span>Back</span></a>
																	</div>
																</c:if>
															</div>
														</c:otherwise>
													</c:choose>
												</c:if>
												<c:if test="${sessionScope.Result!='home'}">
													<div class="line">
														<div class="expbutton">
															<a href="javascript:pisHome()"><span>Go Home</span></a>
														</div>
														<div class="expbutton">
															<a
																href="javascript:moreRequests('${workflowmap.message}')"><span>Back</span></a>
														</div>
													</div>
												</c:if>
											</div>
										</td>
									</tr>
								</table>
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
		<form:hidden path="param" id="param" />
		<form:hidden path="type" id="type" />
		<form:hidden path="jsonValue" />
		<form:hidden path="back" />
		<form:hidden path="roleId" />
		<form:hidden path="requestId" />
		<form:hidden path="message" />
	</form:form>
	<script>
		individualDetails();
	</script>
</body>

</html>
<!-- End: RequestDetails.jsp -->
