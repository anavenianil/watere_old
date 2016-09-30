<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ErpLoanRequest.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page
	import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.loan.beans.dto.GPFRulesDTO,
	com.callippus.web.loan.beans.dto.LoanLeavesMappingDTO,com.callippus.web.loan.beans.dto.LoanFestivalMasterDTO,
	com.callippus.web.loan.beans.dto.LoanAmountDetailsDTO,com.callippus.web.loan.beans.dto.LoanDesigMappingDTO,
	com.callippus.web.loan.beans.dto.LoanAmountGridDTO"%>
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
<script type="text/javascript" src="script/erpLoan.js"></script>
<script type="text/javascript" src="script/date.js"></script>
<link href="styles/calendar.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="script/calendar-hd.js"></script>
<!-- <script type="text/javascript" src="script/calendar_us.js"></script> -->

<title>Loan Request Form</title>
</head>

<body onload="javascript:clearLoan();">
	<form:form method="post" commandName="erpLoanRequests"
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
									<fieldset>
										<legend>APPLICANTS INFORMATION</legend>
										<div class="line">
											<div class="half leftmar">SFID</div>
											<div class="half">${erpLoanRequests.sfID}</div>
										</div>
										<div class="line">
											<div class="half leftmar">Name</div>
											<div class="half">${erpLoanRequests.employeeDetails.nameInServiceBook}</div>
										</div>
										<div class="line">
											<div class="half leftmar">Basic Salary</div>
											<div class="half">${erpLoanRequests.paymentDetails.basicPay}</div>
										</div>
										<%-- <div class="line">
											<div class="half leftmar">Gross Salary</div>
											<div class="half">${erpLoanRequests.paymentDetails.gradePay}</div>
										</div> --%>
									</fieldset>
									<fieldset>
										<legend>LOAN INFORMATION</legend>
										<div class="line">
											<div class="half leftmar">
												Loan Type<span class="mandatory">*</span>
											</div>
											<div class="quarter">
												<form:select path="requestedLoanType" id="requestedLoanType"
													cssClass="formSelect"
													onmouseover="setSelectWidth('#loanType')" required="true">
													<form:option value="0">Select</form:option>
													<form:option value="Compassionate grounds">Compassionate grounds</form:option>
													<form:option value="Annual Leave">Annual Leave</form:option>
													<form:option value="Payment of School fee">Payment of School fee</form:option>
													<form:option value="">Others(Specify)</form:option>
												</form:select>
											</div>
										</div>
										<div class="line">
											<div class="half leftmar">
												Maximum Loan Amount<span class="mandatory">*</span>
											</div>
											<div class="half">
												<form:radiobutton path="maxLaonAmtType" id="maxLaonAmtType"
													value="Fixed Amount" label="Fixed Amount" />
												<form:radiobutton path="maxLaonAmtType" id="maxLaonAmtType"
													value="Basic Salary" label="Basic Salary" />
											</div>
										</div>
										<div class="line">
											<div class="half leftmar">
												Amount Requested<span class="mandatory">*</span>
											</div>
											<div class="quarter">
												<form:input path="amountRequested" id="amountRequested"
													onkeypress="return isNumberExp(event);"
													onChange="checkLoanAmount('${erpLoanRequests.paymentDetails.basicPay}', '${amountRequested}')" />
											</div>
										</div>
										<div class="line">
											<div class="half leftmar">Reason</div>
											<div class="half">
												<textarea id="reasonForLoan" cols="30" rows="4"
													onkeyup="textCounter(this,document.forms[0].counter,500);"
													onkeypress="textCounter(this,document.forms[0].counter,500);"
													name="reasonForLoan"></textarea>
												<input id="counter" class="counter" type="text"
													disabled="disabled" value="500" name="counter" />
											</div>
										</div>
									</fieldset>
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
		
	<%if (session.getAttribute("gpfTypes")!=null ) {%>
		jsonArrayObject =
	<%=(net.sf.json.JSONArray)JSONSerializer.toJSON((List<GPFRulesDTO>)session.getAttribute("gpfTypes"))%>
		;
	<%}if (session.getAttribute("festivalsList")!=null ) {%>
		jsonFestivalObject =
	<%=(net.sf.json.JSONArray)JSONSerializer.toJSON((List<LoanFestivalMasterDTO>)session.getAttribute("festivalsList"))%>
		;
	<%}if (session.getAttribute("loanLeavesMappingJSON")!=null ) {%>
		jsonAvailabeLeavesList =
	<%=(net.sf.json.JSONArray)JSONSerializer.toJSON((List<LoanLeavesMappingDTO>)session.getAttribute("loanLeavesMappingJSON"))%>
		;
	<%}if (session.getAttribute("LoanTypeDetailsList")!=null ) {%>
		jsonLoanTypeDetailsList =
	<%=(net.sf.json.JSONArray)JSONSerializer.toJSON((List<LoanLeavesMappingDTO>)session.getAttribute("LoanTypeDetailsList"))%>
		;
	<%}if (session.getAttribute("loanAmountList")!=null ) {%>
		jsonLoanAmountDetails =
	<%=(net.sf.json.JSONArray) JSONSerializer.toJSON((List<LoanAmountDetailsDTO>) session.getAttribute("loanAmountList"))%>
		;
	<%}if (session.getAttribute("loanDesigMappings")!=null ) {%>
		jsonLoanDesigMappings =
	<%=(net.sf.json.JSONArray) JSONSerializer.toJSON((List<LoanDesigMappingDTO>) session.getAttribute("loanDesigMappings"))%>
		;
	<%}if (session.getAttribute("loanAmountGrid")!=null ) {%>
		jsonLoanAmountGrid =
	<%=(net.sf.json.JSONArray) JSONSerializer.toJSON((List<LoanAmountGridDTO>) session.getAttribute("loanAmountGrid"))%>
		;
	<%}%>
		if ('${loanRequest.employeeDetails.designationDetails.type}' == "GAZETTED") {
			type = "GAZ"
		} else if ('${loanRequest.employeeDetails.designationDetails.type}' == 'NON GAZETTED') {
			type = "NG"
		};
		basicPay = $
		{
			//loanRequest.paymentDetails.basicPay
		};
	</script>

</body>
</html>
<!-- End:LoanRequest.jsp -->