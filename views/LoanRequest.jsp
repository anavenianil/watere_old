<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LoanRequest.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page
	import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.loan.beans.dto.GPFRulesDTO,com.callippus.web.loan.beans.dto.LoanLeavesMappingDTO,com.callippus.web.loan.beans.dto.LoanFestivalMasterDTO,com.callippus.web.loan.beans.dto.LoanAmountDetailsDTO,com.callippus.web.loan.beans.dto.LoanDesigMappingDTO,com.callippus.web.loan.beans.dto.LoanAmountGridDTO"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="script/jquery.js"></script>

<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/loan.js"></script>
<script type="text/javascript" src="script/date.js"></script>
<link href="styles/calendar.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>

<title>Loan/Advance Request</title>
</head>

<body onload="javascript:clearLoan();">
	<form:form method="post" commandName="loanRequest"
		name="LoanApplication">
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
								<div class="headTitle">Loan/Advance Request</div>
								<div class="line">
									<%-- Content Page starts --%>
									<div id="result">

										<c:if test="${message=='empNotExists'}">
											<font color=red>sfid not exists please enter valid
												sfid</font>
										</c:if>
									</div>
									<c:if test="${message=='offline' || message=='empNotExists'}">
										<div class="line">
											<div class="half leftmar">
												SFID<span class="failure">&nbsp;*</span>
											</div>

											<div class="half">
												<form:input path="offlineSFID" id="offlineSFID"
													onblur="javascript:offlineLoan()" />
											</div>
										</div>
									</c:if>
									<div class="line" id="result"></div>
									<c:if test="${message!='offline'}">
										<div class="line">
											<div class="half leftmar">SFID</div>
											<div class="half">${loanRequest.sfID}</div>
										</div>
									</c:if>
									<div class="line">
										<div class="half leftmar">Name</div>
										<div class="half">${loanRequest.employeeDetails.nameInServiceBook}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Designation</div>
										<div class="half">${loanRequest.employeeDetails.designationDetails.name}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Employment Type</div>
										<div class="half">${loanRequest.employeeDetails.employmentDetails.name}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Date of joining in DRDO</div>
										<div class="half">
											<fmt:formatDate pattern="dd-MMM-yyyy"
												value="${loanRequest.employeeDetails.dojDrdoInFormat}" />
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">Date of superannuation or
											retirement</div>
										<div class="half">${loanRequest.employeeDetails.retiredDate}</div>
									</div>
									<div class="line">
										<div class="half leftmar">
											Loan/Advance Type<span class="mandatory">*</span>
										</div>
										<div class="quarter">
											<form:select path="loanType" id="loanType"
												cssClass="formSelect"
												onmouseover="setSelectWidth('#loanType')"
												onchange="javascript:getLoanType();setRuleValues();">
												<form:option value="0">Select</form:option>
												<form:options items="${loanRequest.loanTypeMasterList}"
													itemValue="id" itemLabel="loanName" />
											</form:select>
										</div>
									</div>
									<div class="line" style="display: none" id="loanSubTypeDiv">
										<div class="half leftmar">
											<span id="loanSubName"></span><span class="mandatory">*</span>
										</div>
										<div class="quarter">
											<form:select path="loanSubType" id="loanSubType"
												cssClass="formSelect"
												onmouseover="setSelectWidth('#loanSubType')"
												onchange="javascript:getRules()">
												<form:option value="0">Select</form:option>
											</form:select>
										</div>
									</div>
									<%-- Festival Starts --%>
									<div class="line" style="display: none" id="festival">
										<c:if
											test="${loanRequest.employeeDetails.employmentDetails.name=='Temporary'}">
											<div class="line">
												<div class="half leftmar">
													If temporary, whether surety from a permanent official is
													furnished<span class="mandatory">*</span>
												</div>
												<div class="half">
													<form:input path="temporaryDesc" id="temporaryDesc" />
												</div>
											</div>
										</c:if>
										<div class="line">
											<div class="half leftmar">Basic Pay</div>
											<div class="half">${loanRequest.paymentDetails.basicPay}</div>
										</div>
										<div class="line">
											<div class="half leftmar">Grade Pay</div>
											<div class="half">${loanRequest.paymentDetails.gradePay}</div>
										</div>
										<div class="line">
											<div class="half leftmar">
												Whether a festival advance has been drawn earlier during the
												current financial year<span class="mandatory">*</span>
											</div>
											<div class="half">
												<form:radiobutton path="prevLoanFlag" id="prevLoanFlagF"
													value="Y" label="Yes"
													onclick="javascript:checkPreviousLoanDiv('F');" />
												<form:radiobutton path="prevLoanFlag" id="prevLoanFlagF"
													value="N" label="No"
													onclick="javascript:checkPreviousLoanDiv('F');" />
											</div>
										</div>
										<div class="line" id="previousLoanDivF" style="display: none">
											<div class="line">
												<div class="half leftmar">
													Festival Name<span class="mandatory">*</span>
												</div>
												<div class="half">
													<form:input path="prevLoanName" id="prevLoanNameF" />
												</div>
											</div>
											<div class="line">
												<div class="half leftmar">
													Whether a festival advance granted on the previous occasion
													has been recovered fully<span class="mandatory">*</span>
												</div>
												<div class="half">
													<form:radiobutton path="prevLoanRecFlag"
														id="prevLoanRecFlagF" value="Y" label="Yes" />
													<form:radiobutton path="prevLoanRecFlag"
														id="prevLoanRecFlagF" value="N" label="No" />
												</div>
											</div>
										</div>
										<div class="line">
											<div class="half leftmar">Whether under
												Suspension/EXOL/HPL/LPR and the period of leave</div>
											<div class="half">
												<form:radiobutton path="currentPositionFlag"
													id="currentPositionFlagF" value="Y" label="Yes"
													onchange="javascript:showLeaveDiv('F');" />
												<form:radiobutton path="currentPositionFlag"
													id="currentPositionFlagF" value="N" label="No"
													onchange="javascript:showLeaveDiv('F');" />
											</div>
										</div>
										<div class="line" id="leaveDivF" style="display: none">
											<div class="line">
												<div class="half leftmar">The date of commencement of
													leave</div>
												<div class="quarter">
													<form:input path="leaveFromDate" id="leaveFromDateF"
														cssClass="dateClass" readonly="true" />
													<script type="text/javascript">
														new tcal(
																{
																	'formname' : 'LoanApplication',
																	'controlname' : 'leaveFromDateF'
																});
													</script>
												</div>
											</div>
											<div class="line">
												<div class="half leftmar">The date of expiry of leave</div>
												<div class="quarter">
													<form:input path="leaveToDate" id="leaveToDateF"
														cssClass="dateClass" readonly="true" />
													<script type="text/javascript">
														new tcal(
																{
																	'formname' : 'LoanApplication',
																	'controlname' : 'leaveToDateF'
																});
													</script>
												</div>
											</div>
										</div>
									</div>
									<%-- Festival Ends --%>

									<%-- GPF Starts --%>
									<div class="line" style="display: none" id="gpf">
										<div class="line">
											<div class="half leftmar">GPF Account Number</div>
											<div class="quarter">${loanRequest.employeeDetails.gpfAcNo}</div>
										</div>
										<div class="line">
											<div class="half leftmar">Previous Financial Year
												Closing Balance</div>
											<div class="quarter">${loanRequest.paymentDetails.gpfClosingBalance}</div>
										</div>
										<div class="line">
											<div class="half leftmar">Rule</div>
											<div class="quarter" id="gpfRule"></div>
										</div>
										<div class="line">
											<div class="half leftmar">Amount Required</div>
											<div class="quarter">
												<form:input path="reqAmount" id="reqAmountG"
													onkeypress="return isNumberExp(event);" />
											</div>
										</div>
										<div class="line">
											<div class="half leftmar">The circumstance justifying
												the application for the advance</div>
											<div class="quarter">
												<form:input path="circumstance" id="circumstance" />
											</div>
										</div>
										<div class="line">
											<div class="half leftmar">
												Whether any withdrawal/advance was taken for the same
												purpose earlier<span class="mandatory">*</span>
											</div>
											<div class="half">
												<form:radiobutton path="prevLoanFlag" id="prevLoanFlagG"
													value="Y" label="Yes"
													onclick="javascript:checkPreviousLoanDiv('G');" />
												<form:radiobutton path="prevLoanFlag" id="prevLoanFlagG"
													value="N" label="No"
													onclick="javascript:checkPreviousLoanDiv('G');" />
											</div>
										</div>
										<div class="line" id="previousLoanDivG" style="display: none">
											<div class="line">
												<div class="half leftmar">Previous Loan Details</div>
												<div class="quarter">
													<form:input path="prevLoanDetails" id="prevLoanDetails"
														maxlength="200" />
												</div>
											</div>
											<div class="line">
												<div class="half leftmar">
													Whether gpf advance granted on the previous occasion has
													been recovered fully<span class="mandatory">*</span>
												</div>
												<div class="half">
													<form:radiobutton path="prevLoanRecFlag"
														id="prevLoanRecFlagG" value="Y" label="Yes" />
													<form:radiobutton path="prevLoanRecFlag"
														id="prevLoanRecFlagG" value="N" label="No" />
												</div>
											</div>
										</div>
									</div>
									<%-- GPF Ends --%>
									<%-- Motor Car/Cycle/Scooter/Moped/Personal Computer Starts --%>
									<div class="line" style="display: none" id="motor">
										<div class="line">
											<div class="half leftmar">
												Anticipated price of Motor Car/Cycle/Scooter/Moped/Personal
												Computer<span class="mandatory">*</span>
											</div>
											<div class="quarter">
												<form:input path="anticipatedAmount" id="anticipatedAmount"
													onkeypress="return isNumberExp(event);" />
											</div>
										</div>
										<div class="line">
											<div class="half leftmar">
												Amount of Advance<span class="mandatory">*</span>
											</div>
											<div class="half">
												<form:input path="reqAmount" id="reqAmountM"
													onkeypress="return isNumberExp(event);" />
											</div>
										</div>
										<div class="line">
											<div class="half leftmar">
												Number of principle installments in which the advance is
												desired to be repaid<span class="mandatory">*</span>
											</div>
											<div class="half">
												<form:input path="requestedInstalments"
													id="requestedInstalments"
													onkeypress="return isNumberExp(event);"
													onblur="javascript:totalInstMessageBasedOnPrin();" />
											</div>
										</div>
										<div class="line">
											<div class="half leftmar">
												Number of interest installments in which the advance is
												desired to be repaid<span class="mandatory">*</span>
											</div>
											<div class="half">
												<form:input path="requestedInterestInstalments"
													id="requestedInterestInstalments"
													onkeypress="return isNumberExp(event);"
													onblur="javascript:totalInstMessageBasedOnInst();" />
											</div>
										</div>
										<div class="line">
											<div class="half leftmar">
												Whether the advance for similar purpose was obtained
												previously<span class="mandatory">*</span>
											</div>
											<div class="half">
												<form:radiobutton path="prevLoanFlag" id="prevLoanFlagM"
													value="Y" label="Yes"
													onclick="javascript:checkPreviousLoanDiv('M');" />
												<form:radiobutton path="prevLoanFlag" id="prevLoanFlagM"
													value="N" label="No"
													onclick="javascript:checkPreviousLoanDiv('M');" />
											</div>
										</div>
										<div class="line" id="previousLoanDivM" style="display: none">
											<div class="line">
												<div class="half leftmar">Date of drawal of the
													advance</div>
												<div class="quarter">
													<form:input path="prevAdvanceDate" id="prevAdvanceDate"
														cssClass="dateClass" readonly="true" />

													<script type="text/javascript">
														new tcal(
																{
																	'formname' : 'LoanApplication',
																	'controlname' : 'prevAdvanceDate'
																});
													</script>
												</div>
											</div>
											<div class="line">
												<div class="half leftmar">
													Whether this loan granted on the previous occasion has been
													recovered fully<span class="mandatory">*</span>
												</div>
												<div class="half">
													<form:radiobutton path="prevLoanRecFlag"
														id="prevLoanRecFlagM" value="Y" label="Yes" />
													<form:radiobutton path="prevLoanRecFlag"
														id="prevLoanRecFlagM" value="N" label="No" />
												</div>
											</div>

										</div>
										<div class="line">
											<div class="half leftmar">
												Whether the intension is to purchase a new or an old Motor
												Car/Cycle/Scooter/Moped/Personal Computer<span
													class="mandatory">*</span>
											</div>
											<div class="half">
												<form:radiobutton path="intensionFlag" id="intensionFlag"
													value="Y" label="New" />
												<form:radiobutton path="intensionFlag" id="intensionFlag"
													value="N" label="Old" />
											</div>
										</div>
										<div class="line">
											<div class="half leftmar">
												If the intension is to purchase Motor
												Car/Cycle/Scooter/Moped/Personal Computer from a person
												having official dealings with the government servant,
												whether previous sanction of the component authority has
												been obtained as required under Rule 18(3) of the Central
												Civil Services (Conduct) Rules, 1964<span class="mandatory">*</span>
											</div>
											<div class="half">
												<form:radiobutton path="intensionRuleFlag"
													id="intensionRuleFlag" value="Y" label="Yes" />
												<form:radiobutton path="intensionRuleFlag"
													id="intensionRuleFlag" value="N" label="No" />
											</div>
										</div>
										<div class="line">
											<div class="half leftmar">
												Whether the officer is on leave or is about to proceed on
												leave<span class="mandatory">*</span>
											</div>
											<div class="half">
												<form:radiobutton path="currentPositionFlag"
													id="currentPositionFlagM" value="Y" label="Yes"
													onchange="javascript:showLeaveDiv('M');" />
												<form:radiobutton path="currentPositionFlag"
													id="currentPositionFlagM" value="N" label="No"
													onchange="javascript:showLeaveDiv('M');" />
											</div>
										</div>
										<div class="line" id="leaveDivM" style="display: none">
											<div class="line">
												<div class="half leftmar">The date of commencement of
													leave</div>
												<div class="quarter">
													<form:input path="leaveFromDate" id="leaveFromDateM"
														cssClass="dateClass" readonly="true" />
													<script type="text/javascript">
														new tcal(
																{
																	'formname' : 'LoanApplication',
																	'controlname' : 'leaveFromDateM'
																});
													</script>
												</div>
											</div>
											<div class="line">
												<div class="half leftmar">The date of expiry of leave</div>
												<div class="quarter">
													<form:input path="leaveToDate" id="leaveToDateM"
														cssClass="dateClass" readonly="true" />
													<script type="text/javascript">
														new tcal(
																{
																	'formname' : 'LoanApplication',
																	'controlname' : 'leaveToDateM'
																});
													</script>
												</div>
											</div>
										</div>
										<div class="line">
											<div class="half leftmar">
												Are any negotiations or preliminary enquiries being made so
												that delivery may be taken of the Motor
												Car/Cycle/Scooter/Moped/Personal Computer within month from
												the date of drawal of the advance<span class="mandatory">*</span>
											</div>
											<div class="half">
												<form:radiobutton path="negotiationFlag"
													id="negotiationFlag" value="Y" label="Yes" />
												<form:radiobutton path="negotiationFlag"
													id="negotiationFlag" value="N" label="No" />
											</div>
										</div>
										<div class="line">
											<div class="half leftmar">
												Certified that the information given about is complete and
												true<span class="mandatory">*</span>
											</div>
											<div class="half">
												<form:radiobutton path="declarationFlag"
													id="declarationFlag" value="Y" label="Yes" />
												<form:radiobutton path="declarationFlag"
													id="declarationFlag" value="N" label="No" />
											</div>
										</div>
										<div class="line">
											<div class="half leftmar">
												Certified that I have not taken delivery of the Motor
												Car/Cycle/Scooter/Moped/Personal Computer on account of
												which I apply for the advance, that I shall complete
												negotiations for the purchase of, pay finally and take
												possession of the same before the expiry of one month from
												the date of drawal of the advance<span class="mandatory">*</span>
											</div>
											<div class="half">
												<form:radiobutton path="commitmentFlag" id="commitmentFlag"
													value="Y" label="Yes" />
												<form:radiobutton path="commitmentFlag" id="commitmentFlag"
													value="N" label="No" />
											</div>
										</div>

									</div>
									<%-- Motor Car/Cycle/Scooter/Moped/Personal Computer Ends --%>
									<div class="line">
										<div class="half leftmar">Reason</div>
										<div class="half">
											<form:textarea path="reason" id="reason" cols="30" rows="4"
												onkeypress="textCounter(this,document.forms[0].counter,500);"
												onkeyup="textCounter(this,document.forms[0].counter,500);" />
											<input type="text" class="counter" name="counter" value="500"
												id="counter" disabled="disabled" />
										</div>
									</div>
									<div class="line" id="buttons" style="display: none">
										<div class="expbutton" style="margin-left: 50%">
											<a href="javascript:loanSubmit()"><span>Submit</span></a>
										</div>
										<div class="expbutton">
											<a href="javascript:clearLoan();"><span>Clear</span></a>
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
		<form:hidden path="param" />
		<form:hidden path="type" />
		<form:hidden path="requestID" />
		<form:hidden path="requestId" />
	</form:form>
	<script>
		<% if (session.getAttribute("gpfTypes")!=null ) { %>
			jsonArrayObject = <%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<GPFRulesDTO>)session.getAttribute("gpfTypes")) %>;
		<%}if (session.getAttribute("festivalsList")!=null ) { %>
			jsonFestivalObject = <%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<LoanFestivalMasterDTO>)session.getAttribute("festivalsList")) %>;
		<%}if (session.getAttribute("loanLeavesMappingJSON")!=null ) {%>
		jsonAvailabeLeavesList= <%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<LoanLeavesMappingDTO>)session.getAttribute("loanLeavesMappingJSON"))%>;
		<%}if (session.getAttribute("LoanTypeDetailsList")!=null ) {%>
		jsonLoanTypeDetailsList= <%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<LoanLeavesMappingDTO>)session.getAttribute("LoanTypeDetailsList"))%>;
		<%}if (session.getAttribute("loanAmountList")!=null ) {%>
			jsonLoanAmountDetails =<%=(net.sf.json.JSONArray) JSONSerializer.toJSON((List<LoanAmountDetailsDTO>) session.getAttribute("loanAmountList"))%>;
		<%}if (session.getAttribute("loanDesigMappings")!=null ) {%>
			jsonLoanDesigMappings = <%=(net.sf.json.JSONArray) JSONSerializer.toJSON((List<LoanDesigMappingDTO>) session.getAttribute("loanDesigMappings"))%>;
		<%}if (session.getAttribute("loanAmountGrid")!=null ) {%>
			jsonLoanAmountGrid =<%=(net.sf.json.JSONArray) JSONSerializer.toJSON((List<LoanAmountGridDTO>) session.getAttribute("loanAmountGrid"))%>;
		<%}%>
			if('${loanRequest.employeeDetails.designationDetails.type}'=="GAZETTED"){type="GAZ"}
			else if('${loanRequest.employeeDetails.designationDetails.type}'=='NON GAZETTED'){type="NG"};
			basicPay=${loanRequest.paymentDetails.basicPay};
		</script>

</body>
</html>
<!-- End:LoanRequest.jsp -->