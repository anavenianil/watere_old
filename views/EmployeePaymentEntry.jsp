<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:EmployeePaymentEntry.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<title>Basic Pay History</title>
</head>
<body onload="javascript:clearEmpPayment();">
	<form:form method="post" commandName="empPayment">
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
								<div class="headTitle" id="title">Employee Basic Pay History</div>
								<div>
								<%-- Content Page starts --%>
									<div class="line"></div>
									<div class="line">
										<div class="line">
											<div class="quarter bold leftmar">Enter SFID<span class="mandatory">*</span></div>
											<div class="quarter">
												<input  id="SearchSfid" onkeypress="javascript:return checkSpecialChar(event)"/>
											</div>
											<div class="quarter">
												<div class="expbutton"><a href="javascript:getBPSFIDDetails();"><span>Search</span></a></div>
											</div>
										</div>
										<div id="empDetailsMainGrid" style="display:none;"></div>
										<div id="empBasicPayGrid" style="display:none;">
											<fieldset style="float:left;">
												<div class="line">
													<div class="quarter bold leftmar">SFID<span class="mandatory">*</span></div>
													<div class="quarter">
														<form:input path="sfid" id="sfid" readonly="true"  />
													</div>
													
													<div class="quarter leftmar">Designation<span class="mandatory">*</span></div>
													<div class="quarter">
														<form:select path="designationId" id="designationId" cssClass="formSelect" onchange="javascript:changeEmpPayscale();">
															<form:option value="0">Select</form:option>
															
														</form:select>
													</div>
												</div>
												
												<div class="line">
													<div class="quarter bold leftmar">Basic<span class="mandatory">*</span></div>
													<div class="quarter">
														<form:input path="basic" id="basic" maxlength="10" onkeypress="javascript:increaseTextWidth('basic');return checkFloat(event,'basic');"/>
													</div>
													<div class="quarter leftmar">Scale<span class="failure">*</span></div>
													<div class="quarter">
													<form:select path="" id="empPayScaleId"  cssClass="formSelect" disabled="true">
														<form:option value="0">Select</form:option>
														
													</form:select>
													</div>
												</div>	
												<div class="line">
													<div class="quarter bold leftmar">Increment Type<span class="mandatory">*</span></div>
													<div class="quarter">
														<form:select path="incrementType" id="incrementType" >
															<form:option value="">Select</form:option>
															<form:option value="N">Initial Entry</form:option>
															<form:option value="A">Annual Increment</form:option>
															<form:option value="I">Increment</form:option>
															<form:option value="P">Promotion</form:option>
															<form:option value="AA">AnnualIncrement Ammmend</form:option>
															
														</form:select>
													</div>
													
													<div class="quarter bold leftmar">Increment Value<span class="mandatory">*</span></div>
													<div class="quarter">
														<form:input path="incrementValue" id="incrementValue" onkeypress="return checkFloat(event,'incrementValue');"/>
													</div>
												</div>
												<div class="line">
													<div class="quarter leftmar">Effective Date<span class="mandatory">*</span></div>
																<div class="quarter">
																	<form:input path="effectiveDate" id="effectiveDate"  cssClass="dateClass" readonly="true"/>
																	<img src="./images/calendar.gif" id="effectiveDate_trigger" /> 
																	<script type="text/javascript">
										                        		Calendar.setup({inputField :"effectiveDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"effectiveDate_trigger",singleClick : true,step : 1});
									                             	</script>
																</div>
												</div>	
												<div class="line" id="empSumbitDiv" style="display: none;">
												   <div style="margin-left:24%;">
														<div class="expbutton"><a href="javascript:manageEmpPayment();"> <span>Submit</span></a></div>
													</div>
													<div class="expbutton"><a href="javascript:clearEmpPayment();"><span>Clear</span></a></div>
												</div>	
											</fieldset>
										</div>
										<div class="line" id="result">
		                               <jsp:include page="EmployeePaymentEntryList.jsp"></jsp:include>
	                                   </div>
	                                  </div> 
									
									</div>
									<%-- Content Page end --%>								
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
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>
		<form:hidden path="param"/>	
		<form:hidden path="type"/>
		
		<form:hidden path="status" id="status"/>
		<form:hidden path="referenceType" id="referenceType"/>	
		
		</form:form>	
	</body>
</html>					
	<!-- end:EmployeePaymentEntry.jsp -->		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
									