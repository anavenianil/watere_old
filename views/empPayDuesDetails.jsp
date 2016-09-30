<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : empPayDuesDetails.jsp -->
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
	<fieldset><legend><strong><font color='green'>Employee Recovery Details</font></strong></legend>
	
			<div class="line">
				<div class="quarter bold">SFID<span class="mandatory">*</span></div>
				<div class="quarter">
				<spring:bind path="payBillMaster">
					<form:input path="payBillMaster.empID" id="empId" size="10" readonly="true"/>
					</spring:bind>
					
				</div>
				<div class="quarter bold">Pending Type<span class="mandatory">*</span></div>
				<div class="quarter">
				<spring:bind path="payBillMaster">
						<form:select path="payBillMaster.deductionID" id="deductionID" cssClass="formSelect">
								<form:option value="select">Select</form:option>
								<form:option value="BenFund">BenFund</form:option>
								<form:option value="Canfin">Canfin</form:option>
								<form:option value="CarLoan">Car Loan</form:option>
								<form:option value="CEGIS">CEGIS</form:option>
								<form:option value="CGHS">CGHS</form:option>
								<form:option value="CourtAttachment">Court Attachment</form:option>
								<form:option value="CycleLoan">Cycle Loan</form:option>
								<form:option value="ElecBill">Elec Bill</form:option>
								<form:option value="EOL">EOL/HPL</form:option>
								<form:option value="FestAdv">Festival Advance</form:option>
								<form:option value="FurnBill">Furn Bill</form:option>
								<form:option value="Gic">Gic</form:option>
								<form:option value="GPF">GPF</form:option>
								<form:option value="GpfAdv">GPF Advance</form:option>
								<form:option value="GpfSA">GPF SA</form:option>
								<form:option value="HBA">HBA</form:option>
								<form:option value="Hdfc">Hdfc</form:option>
								<form:option value="Lic">Lic</form:option>
								<form:option value="LTC">LTC</form:option>
								<form:option value="MEDICAL">MEDICAL</form:option>
								<form:option value="Mess">Mess</form:option>
								<form:option value="PcLoan">PC Loan</form:option>
								<form:option value="Rent">Rent</form:option>
								<form:option value="RegFund">RegFund</form:option>
								<form:option value="ResSecu">ResSecu</form:option>
								<form:option value="ScooterLoan">Scooter Loan</form:option>
								<form:option value="TADA">TADA</form:option>
								<form:option value="WaterBill">Water Bill</form:option>
								<form:option value="WelFund">WelFund</form:option>
								<form:option value="WelRecovery">WelFund Recovery</form:option>
						</form:select>
				</spring:bind>
				</div>
			</div>
		<div class="line">
			<div class="quarter bold">Pending Amount</div>
			<div class="quarter"><spring:bind path="payBillMaster">
									<form:input path="payBillMaster.amount" id="amount" size="10" maxlength="9" onkeypress="return checkInt(event);"/>
								 </spring:bind>
			</div>
			<div class="quarter bold">
				<fieldset style="height:15px;width:140px;">
					<spring:bind path="payBillMaster">
						<form:radiobutton path="payBillMaster.loanDeduType" id="loanDeduType1"  value="credit"/>
					</spring:bind>Credit
					<spring:bind path="payBillMaster">
						<form:radiobutton path="payBillMaster.loanDeduType" id="loanDeduType2" value="debit"/>
					</spring:bind>Debit
				</fieldset>
			</div>
		</div>
			<div class="line">
				<div style="margin-left:35%">
					<div class="expbutton"><a href="javascript:submitPayDues();"><span>Submit</span></a></div>
				</div>
			</div>				
			<div class="line" id="result1"><jsp:include page="empPayDuesDetailsList.jsp"></jsp:include></div>
				
	</fieldset>			
<!-- End : empPayDuesDetails.jsp -->
