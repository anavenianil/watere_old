
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:ReimbursementDetailsForm.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/ajaxUpload.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/cghs.js"></script>
</head>

<body>
	<form:form method="post" commandName="cghs" id="cghs">
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
								<div>
									<%-- Content Page starts --%>
									<div id="result"></div>
										<c:if test="${cghs.type eq 'cghssettlement'}">
											<div class="line">
												<div id="patientName">
													<div class="quarter leftmar">Patient Name</div>
													<div class="quarter">${cghs.cghsAdvanceDetails.cghsRequestDetails.familyMemberDetails.name}</div>
												</div>
												<div id="hospitalName">
													<div class="quarter leftmar">Name of the Hospital</div>
													<div class="quarter">${cghs.cghsAdvanceDetails.cghsRequestDetails.referralHospitalDetails.hospitalName}</div>
												</div>
											</div>
											<c:if test="${cghs.cghsAdvanceDetails.cghsRequestDetails.wardTypeDetails.name ne '0'}">
											<div class="line">
												<div id="wardType">
													<div class="quarter leftmar">Entitle Ward Type </div>
													<div class="quarter">${cghs.cghsAdvanceDetails.cghsRequestDetails.wardTypeDetails.name}</div>
												</div>
												<div>
													<div class="quarter leftmar">Admission Date</div>
													<div class="quarter"><fmt:formatDate value="${cghs.cghsAdvanceDetails.admissionDate}" pattern="dd-MMM-yyyy" /> </div>
												</div>
											</div>
											</c:if>
										</c:if>
										<c:if test="${cghs.type eq 'cghsreimbursement'}">
											<div class="line" id="patientName">
												<div class="quarter leftmar">Patient Name</div>
												<div class="quarter" id="patient"><input type="hidden" value="${cghs.cghsRequestDetails.familyMemberDetails.id}" />${cghs.cghsRequestDetails.familyMemberDetails.name}</div>
											</div>
											<div class="line" id="hospitalName">
												<div class="quarter leftmar">Name of the Hospital</div>
												<div class="quarter" id="hospital">${cghs.cghsRequestDetails.referralHospitalDetails.hospitalName}</div>
											</div>
											<c:if test="${cghs.cghsRequestDetails.requestTypeDetails.name eq 'Admission'}">
											<div class="line" id="wardType">
												<div class="quarter leftmar">Entitle Ward Type </div>
												<div class="quarter" >${cghs.cghsRequestDetails.wardTypeDetails.name}</div>
											</div>
											</c:if>
											
										</c:if>
										<div class="line" id="familyMember" style="display:none">
											<div class="quarter leftmar">Family Member<span class="mandatory">*</span></div>
											<div class="quarter"><form:select path="familyMembetId" id="familyMembetId" onchange="javascript:displayFamilyMemberDetailsreimb('familyMembetId');" >
																<form:option value="">Select</form:option>
																<form:options items="${cghs.cghsRequestBean.familyMemberList}" itemLabel="name" itemValue="id"/>
															</form:select>
											</div>
										</div>
										  <div class="line" id="hospitalAddress" style="display: none">
											<div class="quarter leftmar">Hospital Address<span class="mandatory">*</span></div>
											<div class="quarter"><form:textarea path="hospitalDetails" id="hospitalDetails" rows="5" cols="20" onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"/>
											<input type="text" class="counter" name="counter" value="500" id="counter" disabled="disabled"/>
											</div>
										</div>							
										<div  class="line">
										   	<c:if test="${cghs.cghsRequestDetails.requestTypeDetails.name ne 'Consultation' && cghs.cghsRequestDetails.requestTypeDetails.name ne 'Investigation'}">
										     <c:if test="${empty cghs.cghsAdvanceDetails.admissionDate}">
										     	<div>
												<div class="quarter leftmar">Date of Admission<span class="mandatory">*</span></div>
												<div class="quarter"><form:input path="admissionDate" id="admissionDate" cssClass="dateClass" readonly="true"/>
												<img  src="./images/calendar.gif"   id="date_start_trigger3" />	
														<script type="text/javascript">
															Calendar.setup({inputField :"admissionDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
														</script>
												</div>
											</div>
										     </c:if>
											<div class="quarter leftmar">Date of Discharge<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="dischargeDate" id="dischargeDate" cssClass="dateClass" readonly="true"/>
											<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
													<script type="text/javascript">
														Calendar.setup({inputField :"dischargeDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
													</script>
											</div>
										    </c:if>
										    <c:if test="${not empty cghs.cghsAdvanceDetails.cdaAmount}">
												<div class="quarter leftmar">CDA Issued Amount</div>
												<div class="quarter" id="issuedAmount">${cghs.cghsAdvanceDetails.cdaAmount}</div>
											</c:if>	
										</div>
										<div class="line">
												<div class="quarter leftmar">Lab Charges</div>
												<div class="quarter"><form:input path="labCharges" id="labCharges" onchange="javascript:CalculateAmount('${cghs.type}');" onkeypress="return checkFloat(event,'labCharges');"/></div>
												<div class="quarter leftmar">Medicines Charges</div>
												<div class="quarter"><form:input path="medicinesCharges" id="medicinesCharges" onchange="javascript:CalculateAmount('${cghs.type}');" onkeypress="return checkFloat(event,'medicinesCharges');"/></div>
										</div>
										<c:if test="${cghs.cghsRequestDetails.requestTypeDetails.name eq 'Consultation' || cghs.cghsRequestDetails.requestTypeDetails.name eq 'Investigation'}">
											<div class="line">
												<div class="quarter leftmar">Consultation Fees</div>
												<div class="quarter"><form:input path="consultationFees" id="consultationFees" onchange="javascript:CalculateAmount('${cghs.type}');" onkeypress="return checkFloat(event,'consultationFees');"/></div>
												<div class="quarter leftmar">Disposable Surgi-sundries</div>
												<div class="quarter"><form:input path="disposableCharges" id="disposableCharges" onchange="javascript:CalculateAmount('${cghs.type}');" onkeypress="return checkFloat(event,'disposableCharges');"/></div>
											</div>
											<div class="line">
												<div class="quarter leftmar">Special devices Charges(if any)</div>
												<div class="quarter"><form:input path="specialDevices" id="specialDevices" onchange="javascript:CalculateAmount('${cghs.type}');" onkeypress="return checkFloat(event,'specialDevices');"/></div>
											</div>
										</c:if>
										<c:if test="${cghs.cghsRequestDetails.requestTypeDetails.name eq 'Admission' || cghs.type eq 'noncghsReimbursement' || cghs.type eq 'cghssettlement'}">
											<div class="line">
												<div class="quarter leftmar">Room Rent Charges(ICU/ICCU/Ward)</div>
												<div class="quarter"><form:input path="roomRent" id="roomRent" onchange="javascript:CalculateAmount('${cghs.type}');" onkeypress="return checkFloat(event,'roomRent');"/></div>
												<div class="quarter leftmar">O.T Charges</div>
												<div class="quarter"><form:input path="otCharges" id="otCharges" onchange="javascript:CalculateAmount('${cghs.type}');" onkeypress="return checkFloat(event,'otCharges');"/></div>
											</div>
											<div class="line">
												<div class="quarter leftmar">O.T.Consumables Charges</div>
												<div class="quarter"><form:input path="otConsumables" id="otConsumables" onchange="javascript:CalculateAmount('${cghs.type}');" onkeypress="return checkFloat(event,'otConsumables');"/></div>
												<div class="quarter leftmar">Anaesthesia Charges</div>
												<div class="quarter"><form:input path="anaesthesiaCharges" id="anaesthesiaCharges" onchange="javascript:CalculateAmount('${cghs.type}');" onkeypress="return checkFloat(event,'anaesthesiaCharges');"/></div>
											</div>
											<div class="line">
												<div class="quarter leftmar">Implants like pacemaker joint replacement,Coronary stent,etc</div>
												<div class="quarter"><form:input path="implantsCharges" id="implantsCharges" onchange="javascript:CalculateAmount('${cghs.type}');" onkeypress="return checkFloat(event,'implantsCharges');"/></div>
												<div class="quarter leftmar">Artificial devices Charges</div>
												<div class="quarter"><form:input path="artificialCharges" id="artificialCharges" onchange="javascript:CalculateAmount('${cghs.type}');" onkeypress="return checkFloat(event,'artificialCharges');"/></div>
											</div>
											<div class="line">
												<div class="quarter leftmar">Procedure</div>
												<div class="quarter"><form:input path="procedure" id="procedure" onchange="javascript:CalculateAmount('${cghs.type}');" onkeypress="return checkFloat(event,'procedure');"/></div>
												<div class="quarter leftmar">Special Nurse/Ayah Charges(if any)</div>
												<div class="quarter"><form:input path="specialNurse" id="specialNurse" onchange="javascript:CalculateAmount('${cghs.type}');" onkeypress="return checkFloat(event,'specialNurse');"/></div>
											</div>
										</c:if>
										<div class="line">
											<div class="quarter leftmar">Miscellaneous Charges</div>
											<div class="quarter"><form:input path="miscellaneousCharges" id="miscellaneousCharges" onchange="javascript:CalculateAmount('${cghs.type}');" onkeypress="return checkFloat(event,'miscellaneousCharges');"/></div>
											<c:if test="${not empty cghs.cghsAdvanceDetails.cdaAmount}">
											<div id="SettelmentAmount">
												<div class="quarter leftmar">Settelment Amount</div>
												<div class="quarter" id="settelmentMoney"></div>
											</div>
											</c:if>
										</div>
										<div class="line">
											<div class="quarter leftmar">Total No.of vouchers</div>
											<div class="half"><form:input path="noofVouchers" id="noofVouchers" onkeypress="return checkFloat(event,'noofVouchers');"/></div>
										</div>
										<c:if test="${cghs.type ne 'cghssettlement'}">
											<div class="line">
												<div class="quarter leftmar">Total Amount</div>
												<div class="quarter" id="total"></div>
											</div>
										</c:if>
										<div id="files">
										<div class="line">
											<div class="quarter leftmar">Medical bills (Cash Receipt duly signed by hospital authorities) with break up, if any</div>
											<div class="half"><form:input path="medicalBillsFile" type="file" id="files1"/></div>
										</div>
										<c:if test="${cghs.cghsRequestDetails.requestTypeDetails.name eq 'Admission' || cghs.cghsAdvanceDetails.cghsRequestDetails.requestTypeDetails.name eq 'Admission' || cghs.type eq 'noncghsReimbursement'}">
											<div class="line" id="dischargeSummary">
												<div class="quarter leftmar">Discharge Summary(In case of Admission)</div>
												<div class="quarter"><form:input path="dischargeSummeryFile" type="file" id="files2"/></div>
											</div>
										</c:if>
										<div class="line" id="settlement" style="display: none">
											<div class="quarter leftmar">Refund cheque from hospital, if advance is more than expenditure</div>
											<div class="quarter"><form:input path="refundChequeFile" type="file" id="files3"/></div>
										</div>
										<div class="line">
											<div class="quarter leftmar">Copy of CGHS card</div>
											<div class="quarter"><form:input path="cghsCardFile" type="file" id="files4"/></div>
										</div>
										</div>
										<div>
										   	<div style="margin-left:25%">
											     <a href="javascript:manageReimbursementDetails('${cghs.requestId}','${cghs.type}');"><div class="appbutton"><span>Submit</span></div></a>
											     <a href="javascript:clearReimbursementDetails()"><div class="appbutton"><span>Clear</span></div></a>
											</div>
										</div>
									<%-- Content Page end --%>
								</div>
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
		<form:hidden path="settleAmount"/>
		<form:hidden path="requestID"/>
		<form:hidden path="requestId"/>
		<form:hidden path="cghscardref"/>
		
		
		</form:form>
		 <script>
requestIDCGHs = '<%= session.getAttribute("LoanRequestIdwork") != null ? session.getAttribute("LoanRequestIdwork") : "" %>';
</script>
		<script>
			setFormValues('${cghs.type}');
			clearReimbursementDetails();
		</script>
		<script>
			familyMember = <%= (net.sf.json.JSONArray)session.getAttribute("jsonfamilyMemberList") %>;
			</script>
	</body>
</html>
<!-- End:ReimbursementDetailsForm.jsp -->