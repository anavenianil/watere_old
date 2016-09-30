<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : PayEmpLoanDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fieldset>
                 <div class="line">
					<div class="quarter">SFID: <strong>${payBillMaster.empID}</strong></div>
					<div class="quarter">NAME: <strong>${payBillMaster.nameInServiceBook}</strong></div>
					<div class="quarter">DIVISION: <strong>${payBillMaster.divisionName}</strong></div>
					<div class="quarter">DESIGNATION: <strong>${payBillMaster.designationName}</strong></div>
				 </div>
</fieldset>
<fieldset><legend><strong><font color='green'>Employee Loan Details</font></strong></legend>
	<div class="line">
				<div class="quarter bold">SFID<span class="mandatory">*</span></div>
				<div class="quarter">
				<spring:bind path="payBillMaster">
					<form:input path="payBillMaster.empID" id="empId" size="10" readonly="true"/>
				</spring:bind>
				</div>
			</div>
			<div class="line">
			<div class="quarter bold">Loan Name<span class="mandatory">*</span></div>
					<div class="quarter">
					<spring:bind path="payBillMaster">
						<form:select path="payBillMaster.loanType" id="loanType" cssClass="formSelect">
								<form:option value="select">Select</form:option>
								<form:options items="${payBillMaster.loanMasterList}" itemValue="id" itemLabel="loanName"/>
							</form:select>
						</spring:bind>
			</div>
			<div class="quarter">
					<spring:bind path="payBillMaster">
						<form:select path="payBillMaster.loanDeduType" id="loanDeduType" cssClass="formSelect">
								<form:option value="select">Select</form:option>
								<form:option value="P">Principal</form:option>
								<form:option value="I">Interest</form:option>
								
							</form:select>
						</spring:bind>
						
					</div>
			</div>
			<div class="line"><div class="quarter bold">EMI<span class="mandatory">*</span></div>
			<div class="quarter">
			<spring:bind path="payBillMaster">
						<form:input path="payBillMaster.emi" id="emi" size="10" onkeypress="return checkTwoDigitFloat(event,'emi');"/>
						</spring:bind></div>
						<div class="quarter bold">Total Amt<span class="mandatory">*</span></div>
					<div class="quarter">
					<spring:bind path="payBillMaster">
						<form:input path="payBillMaster.totalAmt" id="totalAmt" size="10"  onkeypress="return checkTwoDigitFloat(event,'totalAmt');"/>
						</spring:bind>
					</div></div>
			<div class="line">
					<div class="quarter bold">Present Inst No<span class="mandatory">*</span></div>
					<div class="quarter">
					<spring:bind path="payBillMaster">
						<form:input path="payBillMaster.presentInst" id="presentInst"  size="10" onkeypress="return checkTwoDigitFloat(event,'presentInst');"/>
						</spring:bind>
					</div>
					<div class="quarter bold">Total No of Inst<span class="mandatory">*</span></div>
					<div class="quarter">
					<spring:bind path="payBillMaster">
						<form:input path="payBillMaster.totalInst" id="totalInst"  size="10" onkeypress="return checkTwoDigitFloat(event,'totalInst');"/>
						</spring:bind>
					</div>
					</div>
			<div class="line">
			<div class="quarter bold">Outstanding Amount<span class="mandatory">*</span></div>
					<div class="quarter">
					<spring:bind path="payBillMaster">
						<form:input path="payBillMaster.outStandAmt" id="outStandAmt" size="10"  onkeypress="return checkTwoDigitFloat(event,'outStandAmt');"/>
						</spring:bind>
					</div>
					<div class="quarter bold">Recovery Date<span class="mandatory">*</span></div>
					<div class="quarter">
					<spring:bind path="payBillMaster">
					<form:input path="payBillMaster.recoveryStartDate" id="recoveryStartDate"  cssClass="formSelect" onkeypress="javascript:setReadOnly(event, 'recoveryStartDate')"/>
					</spring:bind>
					<img  src="./images/calendar.gif" id="fromDateImg" />	
					<script type="text/javascript">
						Calendar.setup({inputField :"recoveryStartDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"fromDateImg",singleClick : true,step : 1});
					</script>
							</div>
				</div>
				<div class="line">
					<div class="quarter bold">Inst Rate<span class="mandatory">*</span></div>
					<div class="quarter">
					<spring:bind path="payBillMaster">
						<form:input path="payBillMaster.interestRate" id="interestRate"  size="10" onkeypress="return checkTwoDigitFloat(event,'interestRate');"/>
						</spring:bind>
					</div>
					
					</div>
					<div class="line">
						<div style="margin-left:40%">
								<div class="expbutton"><a href="javascript:saveEmpLoanDeatils();"><span>Save</span></a></div>
								<div class="expbutton"><a href="javascript:clearEmpLoanDeatils();"><span>Clear</span></a></div>
						</div>
					<div class="quarter">
					</div>
				</div>
				<div class="line" id="result1"><jsp:include page="payEmpLoanDetailsList.jsp"></jsp:include></div>
				
	</fieldset>			
<!-- End : PayEmpLoanDetails.jsp -->
