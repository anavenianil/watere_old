<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:coinCutDeduction.jsp -->

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
<div class="line"><br/></div>
				<div class="line">
					<div class="quarter leftmar">Deduction Type<span class="mandatory">*</span></div>
					<div class="quarter">
					<spring:bind path="payBillMaster">
					<form:select path="payBillMaster.deductionID" id="deductionID"  cssClass="formSelect"  >
							<form:option value="select">Select</form:option>
							<form:options items="${sessionScope.coinCutMasterDetails}" itemValue="id" itemLabel="name"/>
						</form:select>
						</spring:bind>
						</div>
				</div>
				<div class="line">
					<div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
					<div class="quarter">
					<spring:bind path="payBillMaster">
					<form:input path="payBillMaster.empID" id="empID"  cssClass="formSelect" readonly="true" />
					</spring:bind>
							</div>
				</div>
				<div class="line">
					<div class="quarter leftmar">Amount<span class="mandatory">*</span></div>
					<div class="quarter">
					<spring:bind path="payBillMaster">
					<form:input path="payBillMaster.amount" id="amount"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'amount');"/>
					</spring:bind>
							</div>
				</div>
				<div class="line">
					<div class="quarter leftmar">No of Installments<span class="mandatory">*</span></div>
					<div class="quarter">
					<spring:bind path="payBillMaster">
					<form:input path="payBillMaster.inst" id="inst"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'inst');"/>
					</spring:bind>
							</div>
				</div>
				<div class="line">
					<div class="quarter leftmar">Present Installment<span class="mandatory">*</span></div>
					<div class="quarter">
					<spring:bind path="payBillMaster">
					<form:input path="payBillMaster.presentInst" id="presentInst"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'presentInst');"/>
					</spring:bind>
							</div>
				</div>
				<div class="line">
					<div class="quarter leftmar">Effective Date<span class="mandatory">*</span></div>
					<div class="quarter">
					<spring:bind path="payBillMaster">
					<form:input path="payBillMaster.effDate" id="effDate"  cssClass="formSelect" onkeypress="javascript:setReadOnly(event, 'effDate')" />
					</spring:bind>
					<img  src="./images/calendar.gif" id="fromDateImg" />	
					<script type="text/javascript">
						Calendar.setup({inputField :"effDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"fromDateImg",singleClick : true,step : 1});
					</script>
							</div>
				</div>
				<div class="line">
					<div style="margin-left:50%">
						<div class="expbutton"><a href="javascript:payCCDetails();"><span>Submit</span></a></div>
						<div class="expbutton"><a href="javascript:clearPaybillCCMaster();"><span>Clear</span></a></div>
					</div>
				</div>
				<div class="line" id="result1"><jsp:include page="empCCList.jsp"></jsp:include></div>					
									
							
<!-- End:coinCutDeduction.jsp -->