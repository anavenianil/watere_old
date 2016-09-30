<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin: payBillEmpDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>


               <div class="line" id="payresult1">
                     <div class="line">
										<div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
										<div class="quarter"><spring:bind path="payBillMaster">
										<form:select path="payBillMaster.sfidSearchKey"  id="sfidSearchKey"  cssClass="formSelect" onchange="javascript:getEmpCoreDetails();">
										<form:option value="select" label="Select"></form:option>
										<form:options items="${sessionScope.empList}" itemLabel="name" itemValue="name"/>
										</form:select>
												</spring:bind> </div>
												<div class="quarter" >
										</div>
										<div class="half" id="payResult">${remarks}</div>
					</div>
				</div>
                                 	
								<div class="line">
										<div class="quarter leftmar">Basic Pay<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"> <form:input path="payBillMaster.basicPay" id="basicPay"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'basicPay');"/></spring:bind>
										</div>
										<div class="quarter leftmar">Grade Pay<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:input path="payBillMaster.gPay" id="gPay"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'gPay');"/></spring:bind> 
										</div>
									</div>
										
									<div class="line">
										<div class="quarter leftmar">Income Tax<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:input path="payBillMaster.incomeTaxAmt" id="incomeTaxAmt"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'incomeTaxAmt');"/></spring:bind> 
										</div>
										<div class="quarter leftmar">Pan Card No<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:input path="payBillMaster.panCardNo" id="panCardNo"  cssClass="formSelect"  /></spring:bind> 
										</div>
									</div>
										
										<div class="line">
										<div class="quarter leftmar">Variable Incr Pts<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:input path="payBillMaster.varIncrPts" id="varIncrPts"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'varIncrPts');"/></spring:bind> 
										</div>
										<div class="quarter leftmar">Two Add Increment<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:input path="payBillMaster.twoAddIncr" id="twoAddIncr"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'twoAddIncr');"/></spring:bind> 
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">CATEGORY<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:select path="payBillMaster.categoryID" id="categoryID"  cssClass="formSelect" >
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.empCategoryMasterDetails}" itemValue="id" itemLabel="name"/>
											</form:select></spring:bind> 
										</div>
										<div class="quarter leftmar">TPT<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:select path="payBillMaster.tptFlag" id="tptFlag"  cssClass="formSelect"  >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select></spring:bind> 
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">CCS Member<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:select path="payBillMaster.ccsMemFlag" id="ccsMemFlag"  cssClass="formSelect" onchange="javascript:showCCSDiv()" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select></spring:bind> 
										</div>
										<div class="quarter leftmar">CGHS Eligible Flag<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:select path="payBillMaster.cghsFlag" id="cghsFlag"  cssClass="formSelect" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select></spring:bind> 
										</div>
									</div>	
									<div id="ccsDiv" class="line" style="display:none">
									<div class="line">
										<div class="quarter leftmar">CCS No<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:input path="payBillMaster.ccsNo" id="ccsNo"  cssClass="formSelect"  /></spring:bind> 
										</div>
										<div class="quarter leftmar">CCS Sub<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:input path="payBillMaster.ccsSubAmt" id="ccsSubAmt"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'ccsSubAmt');"/></spring:bind> 
										</div>
									</div>
									</div>
										
									<div class="line">
										<div class="quarter leftmar">GPF / CPS <span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:select path="payBillMaster.gpfFlag" id="gpfFlag"  cssClass="formSelect" onchange="javascript:showGPFDiv()" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select></spring:bind> 
										</div>
									</div>	
									
									<div id="gpfDiv" class="line" style="display:none">
									<div class="line">
										<div class="quarter leftmar">GPF / CPS No<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:input path="payBillMaster.gpfAccountNo" id="gpfAccountNo"  cssClass="formSelect"  /></spring:bind> 
										</div>
										<div class="quarter leftmar">GPF / CPS Sub<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:input path="payBillMaster.gpfSubAmt" id="gpfSubAmt"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'gpfSubAmt');"/></spring:bind> 
										</div>
									</div>	
									</div>
								<fieldset style="height: 15px;">
									<div class="line">
										<div class="quarter leftmar" style="margin-left:-4px;width: 25%">CGEIS<span class="failure">*</span></div>
										<div class="quarter" style="margin-left: -4px;">
											<spring:bind path="payBillMaster"> <form:select path="payBillMaster.cgeisGroupID" id="cgeisGroupID"  cssClass="formSelect"  >
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.payBillGroupMasterList}" itemValue="pk" itemLabel="name"/>
											</form:select></spring:bind>
										</div>
										<div class="quarter leftmar">
											<spring:bind path="payBillMaster"><form:radiobutton path="payBillMaster.cgeisMemFlag" id="cegis1" value="Yes"/></spring:bind>Full Sub
											<spring:bind path="payBillMaster"> <form:radiobutton path="payBillMaster.cgeisMemFlag" id="cegis2" value="No"/></spring:bind> 1/3 Sub
										</div>
									</div>
								</fieldset>
									<div class="line">
										<div class="quarter leftmar">FPA<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"> <form:select path="payBillMaster.fpaFlag" id="fpaFlag"  cssClass="formSelect" onchange="javascript:showFPADiv()" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select></spring:bind>
										</div>
										<div id="fpaDiv" style="display:none">
										<div class="quarter leftmar">FPA Grade Pay<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:input path="payBillMaster.fpaGradePay" id="fpaGradePay"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'fpaGradePay');"/></spring:bind> 
										</div>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Bank A/c<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:select path="payBillMaster.bankFlag" id="bankFlag"  cssClass="formSelect" onchange="javascript:showBankDiv()" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select></spring:bind> 
										</div>
										
									</div>
									<div id="bankDiv" class="line" style="display:none">
									<div class="line">
										<div class="quarter leftmar">Bank Account No<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:input path="payBillMaster.bankAccNo" id="bankAccNo"  cssClass="formSelect"  /></spring:bind> 
										</div>
										<div class="quarter leftmar">Branch Name<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"> <form:input path="payBillMaster.bankBranch" id="bankBranch"  cssClass="formSelect"  /></spring:bind>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Branch Code<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:input path="payBillMaster.branchCode" id="branchCode"  cssClass="formSelect"  /></spring:bind> 
										</div>
										
									</div>		
								</div>
								<div  class="line">
								      <div class="quarter leftmar">Wash Allowance Flag<span class="failure">*</span></div>
										<div class="quarter">
											<spring:bind path="payBillMaster"><form:select path="payBillMaster.washAllwFlag" id="washAllwFlag"  cssClass="formSelect" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select></spring:bind>
										</div>
									</div>
								<div class="line">
										<div style="margin-left:50%">
											<div class="expbutton"><a href="javascript:submitPayEmpOneTimeDeatils();"><span>Submit</span></a></div>
											<div class="expbutton"><a href="javascript:clearPayEmpOneTimeDeatils();"><span>Clear</span></a></div>
										</div>
									</div>
									
								<%-- Content Page end --%>
								<div>&nbsp;</div>
							
					
					
		<spring:bind path="payBillMaster"><form:hidden path="payBillMaster.param" id="param" /></spring:bind>
		<spring:bind path="payBillMaster"><form:hidden path="payBillMaster.type"  id="type" /></spring:bind>
		<spring:bind path="payBillMaster"><form:hidden path="payBillMaster.basicId"  id="basicId" /></spring:bind>
		<spring:bind path="payBillMaster"><form:hidden path="payBillMaster.gradeId"  id="gradeId" /></spring:bind>		

<!-- End: payBillEmpDetails.jsp -->	