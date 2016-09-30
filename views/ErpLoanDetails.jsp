<!-- Begin : ErpLoanDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>

<div class="line">
	<div class="line" id="resultLoan"></div>
	<div class="line">
		<div class="quarter bold">Reason For Loan Application</div>
		<div class="quarter">${workflowmap.erpLoanRequestDTO.requestedLoanType}</div>
	</div>
	<div class="line">
		<div class="quarter bold">Amount Requested</div>
		<div class="quarter">${workflowmap.erpLoanRequestDTO.amountRequested}</div>
	</div>
	<div class="line">
		<div class="quarter bold">Requested Date</div>
		<fmt:formatDate pattern="dd-MMM-yyyy"
			value="${workflowmap.erpLoanRequestDTO.requestedDate}" />
	</div>
	<c:if
		test="${workflowmap.erpLoanRequestDTO.loanStatus==0}">
		<fieldset style="border: 1px solid #00ff00 !important;">
			<legend>
				<strong><font color='green'>For Office Use</font></strong>
			</legend>
			<div class="line">
				<div class="quarter bold">Position of Existing Loan(TZS)</div>
				<form:input path="previousLoanAmount" id="previousLoanAmount" />
			</div>
			<div class="line">
				<div class="quarter bold">New Loan eligible Amount(TZS)</div>
				<form:input path="eligibleAmount" id="eligibleAmount" />
			</div>
			<div class="line">
				<div class="quarter bold">Monthly Deduction(TZS)</div>
				<form:input path="monthlyDeduction" id="monthlyDeduction" />
			</div>
		</fieldset>
	</c:if>

	<c:if test="${workflowmap.requesterSfid ne workflowmap.sfid && workflowmap.erpLoanRequestDTO.loanStatus == 1 ||workflowmap.erpLoanRequestDTO.loanStatus == 2}">
		<fieldset style="border: 1px solid #00ff00 !important;">
			<legend>
				<strong><font color='green'>For Office Use</font></strong>
			</legend>
			<div class="line">
				<div class="quarter bold">Position of Existing Loan(TZS)</div>
				<div class="quarter">${workflowmap.erpLoanRequestDTO.previousLoanAmount}</div>
			</div>
			<div class="line">
				<div class="quarter bold">New Loan eligible Amount(TZS)</div>
				<div class="quarter">${workflowmap.erpLoanRequestDTO.eligibleAmount}</div>
			</div>
			<div class="line">
				<div class="quarter bold">Monthly Deduction(TZS)</div>
				<div class="quarter">${workflowmap.erpLoanRequestDTO.monthlyDeduction}</div>
			</div>
		</fieldset>
	</c:if>
	
	
	<c:if test="${workflowmap.erpLoanRequestDTO.loanStatus==1}">
		<fieldset style="border: 1px solid #00ff00 !important;">
			<legend>
				<strong><font color='green'>Finance Manager</font></strong>
			</legend>
			<div class="line">
				<div class="quarter bold">Approval/Non Approval of TZS</div>
				<form:input path="approvedAmount" id="approvedAmount" />
			</div>
		</fieldset>
	</c:if>
	<c:if test="${workflowmap.erpLoanRequestDTO.loanStatus==2}">
		<fieldset style="border: 1px solid #00ff00 !important;">
			<legend>
				<strong><font color='green'>Finance Manager</font></strong>
			</legend>
			<div class="line">
				<div class="quarter bold">Approval/Non Approval of TZS</div>
				<div class="quarter">${workflowmap.erpLoanRequestDTO.approvedAmount}</div>
			</div>
		</fieldset>
	</c:if>
</div>
<!-- End : ErpLoanDetails.jsp -->