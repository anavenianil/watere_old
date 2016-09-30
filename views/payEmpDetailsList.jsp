<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : PayEmpDetailsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<div class="line" id="result1"></div>
<div class="line"><hr/></div>

<fieldset>
	<div class="line">
		<div class="quarter">SFID: <strong>${payBillMaster.sfidSearchKey}</strong></div>
		<div class="quarter">Name: <strong>${payBillMaster.nameInServiceBook}</strong></div>
		<div class="quarter ">Desig: <strong>${payBillMaster.designationName}</strong></div>
		<div class="quarter">Divsion: <strong>${payBillMaster.divisionName}</strong></div>
	</div>
	<div class="line">
		<div class="quarter">Dob: <strong>${payBillMaster.dob}</strong></div>
		<div class="quarter">Doj: <strong>${payBillMaster.dojGovt}</strong></div>
		<div class="quarter">Dor: <strong>${payBillMaster.retirementDate}</strong></div>
		<div class="quarter">Seniority date: <strong>${payBillMaster.seniorityDate}</strong></div>
	</div>
</fieldset>
<div class="line"></div>
     <div class="line">
     		<div class="quarter leftmar">Employee Status<span class="failure">*</span></div>
			<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:select path="payBillMaster.status" id="empStatus"  cssClass="formSelect"  >
												<form:option value="1">Enable</form:option>
												<form:option value="0">Disable</form:option>
											</form:select>
											</spring:bind>
			</div>							
								
		  <div class="quarter leftmar" style="display:none;">SFID<span class="failure">*</span></div>
		  <div class="quarter" style="display:none;">
				<spring:bind path="payBillMaster">
				<form:input path="payBillMaster.empID" id="empID"  cssClass="formSelect" readonly="true"/>
				</spring:bind>
		  </div>
		  <div class="quarter leftmar">Pay Stop<span class="failure">*</span></div>
		  <div class="quarter">
			<spring:bind path="payBillMaster">
			<form:select path="payBillMaster.payStopFlag" id="payStopFlag"  cssClass="formSelect" onchange="javascript:showCCSDiv()" >
				<form:option value="Yes">Yes</form:option>
				<form:option value="No">No</form:option>
			</form:select>
			</spring:bind>
		  </div>
	 </div>
     <div class="line">
		  <div class="quarter leftmar">Category<span class="failure">*</span></div>
		  <div class="quarter">
				<spring:bind path="payBillMaster">
				<form:select path="payBillMaster.categoryID" id="categoryID"  cssClass="formSelect" >
					<form:option value="select">Select</form:option>
					<form:options items="${sessionScope.empCategoryMasterDetails}" itemValue="id" itemLabel="name"/>
				</form:select>
				</spring:bind>
		 </div>
	     <div class="quarter leftmar">Basic Pay<span class="failure">*</span></div>
		 <div class="quarter">
			<spring:bind path="payBillMaster">
			<form:input path="payBillMaster.basicPay" id="basicPay"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'basicPay');"/>
			</spring:bind>
		 </div>
	</div>
									<div class="line">
										<div class="quarter leftmar">Designation<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:select path="payBillMaster.payDesignationId" id="payDesignationId"  cssClass="formSelect" onchange="javascript:changePayscale()">
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.paybilldesglist}" itemValue="id" itemLabel="designation"/>
											</form:select>
											</spring:bind>
										</div>
										
										
										<div class="quarter leftmar">Scale<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:select path="payBillMaster.payscaleId" id="payscaleId"  cssClass="formSelect" disabled="true">
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.payBillScaleList}" itemValue="id" itemLabel="name"/>
											</form:select>
											</spring:bind>
										</div>
										
									</div>
										<div class="line">
										<div class="quarter leftmar">Grade Pay<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:select path="payBillMaster.gradePay" id="gradePay"  cssClass="formSelect" disabled="true">
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.payBillScaleList}" itemValue="id" itemLabel="gradePay"/>
											</form:select>
											</spring:bind>
										</div>
										
									</div>
									
									<div class="line">
										<div class="quarter leftmar">Variable Incr Pts<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:input path="payBillMaster.varIncrPts" id="varIncrPts"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'varIncrPts');"/>
											</spring:bind>
										</div>
										<div class="quarter leftmar">Two Add Increment<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:input path="payBillMaster.twoAddIncr" id="twoAddIncr"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'twoAddIncr');"/>
											</spring:bind>
										</div>
									</div>	
									<div class="line">
										<div class="quarter leftmar">Income Tax<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:input path="payBillMaster.incomeTaxAmt" id="incomeTaxAmt"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'incomeTaxAmt');"/>
											</spring:bind>
										</div>
										<div class="quarter leftmar">Pan Card No<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:input path="payBillMaster.panCardNo" id="panCardNo"  maxlength="10" cssClass="formSelect" onkeypress="return checkSpecialChar(event);" />
											</spring:bind>
										</div>
									</div>
										
										
									<div class="line">
										
										<div class="quarter leftmar">TPT<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:select path="payBillMaster.tptFlag" id="tptFlag"  cssClass="formSelect"  >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select>
											</spring:bind>
										</div>
										<div class="quarter leftmar">PH<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:select path="payBillMaster.handicap" id="handicap"  cssClass="formSelect"  >
												<form:option value="0">No</form:option>
												<form:options items="${sessionScope.phtypeList}" itemValue="id" itemLabel="name"/>
											</form:select>
											</spring:bind>
										</div>
									</div>
		
				<fieldset><legend><strong><font color='green'>CGHS Eligible Info</font></strong></legend>
				<div class="line">
										<div class="quarter leftmar">CGHS Eligible Flag<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:select path="payBillMaster.cghsFlag" id="cghsFlag"  cssClass="formSelect" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select>
											</spring:bind>
										</div>
									</div>	
				
				</fieldset>
				
				<fieldset><legend><strong><font color='green'>CCS Info</font></strong></legend>					
			
                             		<div class="line">
										<div class="quarter leftmar">CCS Member<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:select path="payBillMaster.ccsMemFlag" id="ccsMemFlag"  cssClass="formSelect" onchange="javascript:showCCSDiv()" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select>
											</spring:bind>
										</div>
									</div>	
									<div id="ccsDiv" class="line" style="display:none">
									<div class="line">
										<div class="quarter leftmar">CCS No<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:input path="payBillMaster.ccsNo" id="ccsNo"  cssClass="formSelect" onkeypress="return checkTwoDigitFloat(event,'ccsNo');" />
											</spring:bind>
										</div>
										<div class="quarter leftmar">CCS Sub<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:input path="payBillMaster.ccsSubAmt" id="ccsSubAmt"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'ccsSubAmt');"/>
											</spring:bind>
										</div>
									</div>
									</div></fieldset>
										
										
										
							<fieldset><legend><strong><font color='green'>
                              <c:if test="${payBillMaster.member eq 'No'}">CPS</c:if>
					           <c:if test="${payBillMaster.member eq 'Yes'}">GPF</c:if>
					           Info
                               </font></strong></legend>			
										
									<div class="line">
										<div class="quarter leftmar"><c:if test="${payBillMaster.member eq 'No'}">CPS</c:if>
					           <c:if test="${payBillMaster.member eq 'Yes'}">GPF</c:if><span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:select path="payBillMaster.gpfFlag" id="gpfFlag"  cssClass="formSelect" onchange="javascript:showGPFDiv()" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select>
											</spring:bind>
										</div>
									</div>	
									
									<div id="gpfDiv" class="line" style="display:none">
									<div class="line">
										<div class="quarter leftmar"><c:if test="${payBillMaster.member eq 'No'}">CPS</c:if>
					                      <c:if test="${payBillMaster.member eq 'Yes'}">GPF</c:if> No<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:input path="payBillMaster.gpfAccountNo" id="gpfAccountNo"  cssClass="formSelect"  />
											</spring:bind>
										</div>
										<div class="quarter leftmar"><c:if test="${payBillMaster.member eq 'No'}">CPS</c:if>
					                     <c:if test="${payBillMaster.member eq 'Yes'}">GPF</c:if> Sub<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:input path="payBillMaster.gpfSubAmt" id="gpfSubAmt"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'gpfSubAmt');"/>
											</spring:bind>
										</div>
									</div>	
									</div></fieldset>
									<fieldset><legend><strong><font color='green'>CGEIGS Info</font></strong></legend>
									<div class="line">
										<div class="quarter leftmar">CGEIGS<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:select path="payBillMaster.cgeisGroupID" id="cgeisGroupID"  cssClass="formSelect"  >
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.payBillGroupMasterList}" itemValue="pk" itemLabel="name"/>
											</form:select>
											</spring:bind>
										</div>
										<fieldset style="width:150px; height:35px;"><legend><strong><font color='green'>subscription</font></strong></legend>
											<spring:bind path="payBillMaster">
												<div class="line">
										<form:radiobutton path="payBillMaster.cgeisMemFlag" id="cegis1" value="Yes" label="Full Sub"/> 
										<form:radiobutton path="payBillMaster.cgeisMemFlag" id="cegis2" value="No" label="1/3 Sub"/>
												</div> 
											</spring:bind>
										</fieldset>
										<div class="quarter bold">
											
										</div>
									</div></fieldset>
									<fieldset><legend><strong><font color='green'>FPA Info</font></strong></legend>
									<div class="line">
										<div class="quarter leftmar">FPA<span class="failure">*</span></div>
										<div class="quarter">
										<spring:bind path="payBillMaster">
											<form:select path="payBillMaster.fpaFlag" id="fpaFlag"  cssClass="formSelect" onchange="javascript:showFPADiv()" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select>
											</spring:bind>
										</div>
										<div id="fpaDiv" style="display:none">
										<div class="quarter leftmar">FPA Grade Pay<span class="failure">*</span></div>
										<div class="quarter">
										<spring:bind path="payBillMaster">
											<form:input path="payBillMaster.fpaGradePay" id="fpaGradePay"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'fpaGradePay');"/>
											</spring:bind>
										</div>
										</div>
									</div></fieldset>
									<fieldset><legend><strong><font color='green'>Bank A/C Info</font></strong></legend>
									<div class="line">
										<div class="quarter leftmar">Bank A/c<span class="failure">*</span></div>
										<div class="quarter">
										<spring:bind path="payBillMaster">
											<form:select path="payBillMaster.bankFlag" id="bankFlag"  cssClass="formSelect" onchange="javascript:showBankDiv()" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select>
											</spring:bind>
										</div>
										
									</div>
									<div id="bankDiv" class="line" style="display:none">
									<div class="line">
										
										<div class="quarter leftmar">Bank Name<span class="failure">*</span></div>
										<div class="quarter">
										<spring:bind path="payBillMaster">
											<form:input path="payBillMaster.branchCode" id="branchCode"  cssClass="formSelect" size="50" onkeypress="return isAlphabetExp(event);"/>
										</spring:bind>
										</div>
										<div class="quarter leftmar">Branch Name<span class="failure">*</span></div>
										<div class="quarter">
										<spring:bind path="payBillMaster">
											<form:input path="payBillMaster.bankBranch" id="bankBranch"  cssClass="formSelect" onkeypress ="return isAlphabetExp(event);"/>
										</spring:bind>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Bank Account No<span class="failure">*</span></div>
										<div class="quarter">
										<spring:bind path="payBillMaster">
											<form:input path="payBillMaster.bankAccNo" id="bankAccNo" maxlength="11" cssClass="formSelect" onkeypress="return checkTwoDigitFloat(event,'bankAccNo');"/>
											</spring:bind>
										</div>
									</div>		
								</div>
								</fieldset>
								<fieldset><legend><strong><font color='green'>Wash Allowance Info</font></strong></legend>
				                   <div class="line">
										<div class="quarter leftmar">Wash Allowance Flag<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster">
											<form:select path="payBillMaster.washAllwFlag" id="washAllwFlag"  cssClass="formSelect" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select>
											</spring:bind>
										</div>
									</div>	
				
								</fieldset>
								<c:if test="${payBillMaster.quartersFlag eq 'yes'}">
								<fieldset><legend><strong><font color='green'>Quarter Info</font></strong></legend>
								<div id="quarterDiv" class="line">
									<div class="line"> 
										<div class="quarter leftmar">Quarter Type<span class="failure">*</span></div>
										<div class="quarter">
										<spring:bind path="payBillMaster">
											<form:select path="payBillMaster.quarterTypeId" id="quarterTypeId"  cssClass="formSelect" disabled="true">
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.payBillQuarterList}" itemValue="id" itemLabel="name"/>
											</form:select>
											</spring:bind>
											
										</div>
										<div class="quarter leftmar">Quarter No<span class="failure">*</span></div>
										<div class="quarter">
										<spring:bind path="payBillMaster">
											<form:input path="payBillMaster.quarterNo" id="quarterNo"  cssClass="formSelect"  disabled="true" />
											</spring:bind>
										</div>
									</div>
									</div>	</fieldset></c:if>
									
									<div class="line">
										<div style="margin-left:50%;margin-top:5px;">
											<div class="expbutton"><a href="javascript:updatePayEmpDeatils();"><span>Update</span></a></div>
										</div>
									</div>
									<div class="line">
									<spring:bind path="payBillMaster"><form:hidden path="payBillMaster.basicId"  id="basicId" /></spring:bind>
		                            <spring:bind path="payBillMaster"><form:hidden path="payBillMaster.gradeId"  id="gradeId" /></spring:bind>		
									</div>				
<!-- End : PayEmpDetailsList.jsp -->
