<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin: payBillEmpDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="./styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>  
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/payBill.js"></script>
</head>
<title>Pay Bill</title>
<body onload="javascript:clearPayEmpOneTimeDeatils();">
<form:form method="post" commandName="payBillMaster" id="PayBillMasterBean">
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
								<div class="headTitle">Emp Core Info</div>
								
								<div class="line" id="result">
									<%-- Content Page starts --%>
								<div class="line">
								     <div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
                                          <div class="quarter">
							                   <form:select path="sfidSearchKey" id="sfidSearchKey" cssClass="formSelect" onchange="javascript:getEmpCoreDetails();" >
							                         <form:option value="select" label="Select"></form:option>
							                         <form:options items="${payBillMaster.empList}" itemLabel="name" itemValue="name"/>
							                   </form:select>
						                  </div>
						       </div>
							   <div class="line">
										<div class="quarter leftmar">Basic Pay<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="basicPay" id="basicPay"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'basicPay');"/>
										</div>
										<div class="quarter leftmar">Grade Pay<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="gPay" id="gPay"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'gPay');"/>
										</div>
								</div>
								<div class="line">
										<div class="quarter leftmar">Income Tax<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="incomeTaxAmt" id="incomeTaxAmt"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'incomeTaxAmt');"/>
										</div>
										<div class="quarter leftmar">Pan Card No<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="panCardNo" id="panCardNo"  cssClass="formSelect" onkeypress="javascript:return checkSpecialChar(event);" />
										</div>
								</div>
								<div class="line">
										<div class="quarter leftmar">Variable Incr Pts<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="varIncrPts" id="varIncrPts"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'varIncrPts');"/>
										</div>
										<div class="quarter leftmar">Two Add Increment<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="twoAddIncr" id="twoAddIncr"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'twoAddIncr');"/>
										</div>
								</div>
								<div class="line">
										<div class="quarter leftmar">CATEGORY<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="categoryID" id="categoryID"  cssClass="formSelect" >
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.empCategoryMasterDetails}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
										<div class="quarter leftmar">TPT<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="tptFlag" id="tptFlag"  cssClass="formSelect"  >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select>
										</div>
								</div>
								<div class="line">
										<div class="quarter leftmar">CCS Member<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="ccsMemFlag" id="ccsMemFlag"  cssClass="formSelect" onchange="javascript:showCCSDiv()" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select>
										</div>
										<div class="quarter leftmar">CGHS Eligible Flag<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="cghsFlag" id="cghsFlag"  cssClass="formSelect" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select>
										</div>
								</div>	
								<div id="ccsDiv" class="line" style="display:none">
									<div class="line">
										<div class="quarter leftmar">CCS No<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="ccsNo" id="ccsNo"  cssClass="formSelect"  />
										</div>
										<div class="quarter leftmar">CCS Sub<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="ccsSubAmt" id="ccsSubAmt"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'ccsSubAmt');"/>
										</div>
									</div>
								</div>
								<div class="line">
										<div class="quarter leftmar">GPF / CPS <span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="gpfFlag" id="gpfFlag"  cssClass="formSelect" onchange="javascript:showGPFDiv()" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select>
										</div>
								</div>	
								<div id="gpfDiv" class="line" style="display:none">
									<div class="line">
										<div class="quarter leftmar">GPF / CPS No<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="gpfAccountNo" id="gpfAccountNo"  cssClass="formSelect"  />
										</div>
										<div class="quarter leftmar">GPF / CPS Sub<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="gpfSubAmt" id="gpfSubAmt"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'gpfSubAmt');"/>
										</div>
									</div>	
								</div>
								<div class="line">
									<fieldset style="line-height:25px;padding-bottom: 5px;padding-left: 4px;">
										<div class="quarter leftmar" style="margin-left:0px;width: 24%">CGEIS<span class="failure">*</span></div>
										<div class="quarter" style="margin-left: 5px;">
											<form:select path="cgeisGroupID" id="cgeisGroupID"  cssClass="formSelect"  >
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.payBillGroupMasterList}" itemValue="pk" itemLabel="name"/>
											</form:select>
										</div>
										<div class="quarter">
											<form:radiobutton path="cgeisMemFlag" id="cegis1" value="Yes"/><strong>Full Sub</strong>&nbsp;&nbsp;&nbsp;
											<form:radiobutton path="cgeisMemFlag" id="cegis2" value="No"/><strong>1/3 Sub</strong>
										</div>
									</fieldset>
								</div>
								<div class="line">
										<div class="quarter leftmar">FPA<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="fpaFlag" id="fpaFlag"  cssClass="formSelect" onchange="javascript:showFPADiv()" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select>
										</div>
										<div id="fpaDiv" style="display:none">
										<div class="quarter leftmar">FPA Grade Pay<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="fpaGradePay" id="fpaGradePay"  cssClass="formSelect"  onkeypress="return checkTwoDigitFloat(event,'fpaGradePay');"/>
										</div>
										</div>
								</div>
								<div class="line">
										<div class="quarter leftmar">Bank A/c<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="bankFlag" id="bankFlag"  cssClass="formSelect" onchange="javascript:showBankDiv()" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select>
										</div>
								</div>
								<div id="bankDiv" class="line" style="display:none">
									<div class="line">
										<div class="quarter leftmar">Bank Account No<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="bankAccNo" id="bankAccNo"  cssClass="formSelect"  />
										</div>
										<div class="quarter leftmar">Branch Name<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="bankBranch" id="bankBranch"  cssClass="formSelect"  />
										</div>
								   </div>
								   <div class="line">
										<div class="quarter leftmar">Branch Code<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="branchCode" id="branchCode"  cssClass="formSelect"  />
										</div>
										
									</div>		
								</div>
								<div id="washAllw" class="line">
								<div class="quarter leftmar">Wash Allowance Flag<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="washAllwFlag" id="washAllwFlag"  cssClass="formSelect" >
												<form:option value="Yes">Yes</form:option>
												<form:option value="No">No</form:option>
											</form:select>
								</div></div>
								<div class="line">
										<div style="margin-left:45%">
											<div class="expbutton"><a href="javascript:submitPayEmpOneTimeDeatils();"><span>Submit</span></a></div>
											<div class="expbutton"><a href="javascript:clearPayEmpOneTimeDeatils();"><span>Clear</span></a></div>
										</div>
								</div>
								<form:hidden path="param" id="param"/>
								<form:hidden path="type"/>	
								<form:hidden path="basicId"/>
								<form:hidden path="gradeId"/>
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
			
		</form:form>	
		
	</body>
</html>
<!-- End: payBillEmpDetails.jsp -->	