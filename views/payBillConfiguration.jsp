<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:payBillConfiguration.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>


<title>Configuration Details</title>
</head>
<body>
	<form:form method="post" id="configuration">
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
								<div class="headTitle">Configuration Details</div>
								<div class="line">
									<%-- Content Page starts --%>
									<div class="line" id="result"></div>
									<fieldset><legend><strong><font color='green'>Allowance Cfg Details</font></strong></legend>
													<div class="line">

													<div class="quarter bold">Special Pay Allowance for Scientist G<span class="mandatory">*</span></div>
											
													<div class="quarter">:&nbsp;<form:input path="specialAllowance" id="specialAllowance" maxlength="10" onkeypress="return checkTwoDigitFloat(event,'specialAllowance');"/>				
													</div>
													
												</div>
											
												
												<div class="line">
											
													<div class="quarter bold"> Washing Allowance <span class="mandatory">*</span></div>
													<div class="quarter">:&nbsp;<form:input path="washAllowance" id="washAllowance" maxlength="7" onkeypress="return checkTwoDigitFloat(event,'washAllowance');"/>				
													</div>
											
													
												</div>
												<div class="line">
											
													<div class="quarter bold"> Xerox Allowance <span class="mandatory">*</span></div>
													<div class="quarter">:&nbsp;<form:input path="xeroxAllowance" id="xeroxAllowance" maxlength="7" onkeypress="return checkTwoDigitFloat(event,'xeroxAllowance');"/>				
													</div>
													
												</div>


												</fieldset>
																<fieldset><legend><strong><font color='green'>Debits Cfg Details</font></strong></legend>
													<div class="line">
													<div class="quarter bold"> Water bill<span class="mandatory">*</span></div>
													<div class="quarter">:&nbsp;<form:input path="water" id="water" maxlength="7" onkeypress="return checkTwoDigitFloat(event,'water');"/>				
													</div>
													<div class="quarter bold"> Furniture bill<span class="mandatory">*</span></div>
													<div class="quarter">:&nbsp;<form:input path="furniture" id="furniture" maxlength="7" onkeypress="return checkTwoDigitFloat(event,'furniture');"/>				
													</div>
													
												</div>
												

												</fieldset>
												
												<fieldset><legend><strong><font color='green'>Coin Cutting Cfg Details</font></strong></legend>
													<div class="line">

													<div class="quarter bold">Welfare Fund for Officers<span class="mandatory">*</span></div>
													<div class="quarter">:&nbsp;<form:input path="wfundOfficers" id="wfundOfficers" maxlength="7" onkeypress="return checkTwoDigitFloat(event,'wfundOfficers');"/>		
													</div>
														<div class="quarter bold">Welfare Fund for Staff<span class="mandatory">*</span></div>
											
													<div class="quarter">:&nbsp;<form:input path="wfundStaff" id="wfundStaff" maxlength="7" onkeypress="return checkTwoDigitFloat(event,'wfundStaff');"/>		
													</div>
													
													
												</div>
											
												
												<div class="line">
											
													<div class="quarter bold"> Officers Mess for Officers (in Qrts)<span class="mandatory">*</span></div>
													<div class="quarter">:&nbsp;<form:input path="messOfficers" id="messOfficers" maxlength="7" onkeypress="return checkTwoDigitFloat(event,'messOfficers');"/>		
													</div>
											
														<div class="quarter bold"> Officers Mess for Officers (outside)<span class="mandatory">*</span></div>
													<div class="quarter">:&nbsp;<form:input path="messOutside" id="messOutside" maxlength="7" onkeypress="return checkTwoDigitFloat(event,'messOutside');"/>		
													</div>
													
												</div>
												
											<div class="line">
											
													<div class="quarter bold"> Benevolent Fund for Officers <span class="mandatory">*</span></div>
											
													<div class="quarter">:&nbsp;<form:input path="bfundOfficers" id="bfundOfficers" maxlength="7" onkeypress="return checkTwoDigitFloat(event,'bfundOfficers');"/>		
													</div>
														<div class="quarter bold"> Benevolent Fund for Staff<span class="mandatory">*</span></div>
													<div class="quarter">:&nbsp;<form:input path="bfundStaff" id="bfundStaff" maxlength="7" onkeypress="return checkTwoDigitFloat(event,'bfundStaff');"/>		
													</div>
													
												</div>
												<div class="line">
											
													<div class="quarter bold">Regimental Fund for Officers <span class="mandatory">*</span></div>
													<div class="quarter">:&nbsp;<form:input path="regFundOfficers" id="regFundOfficers" maxlength="7" onkeypress="return checkTwoDigitFloat(event,'regFundOfficers');"/>		
													</div>
														<div class="quarter bold"> Regimental Fund for Staff<span class="mandatory">*</span></div>
													<div class="quarter">:&nbsp;<form:input path="regFundStaff" id="regFundStaff" maxlength="7" onkeypress="return checkTwoDigitFloat(event,'regFundStaff');"/>		
													</div>
											
													
												</div>
													
												</fieldset>
									<div class="line">
										<div style="margin-left:5%">
										<a href="javascript:saveConfiguration('pay');" class="appbutton">Submit</a>
										
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="configurationDetails" />
		</form:form>
	</body>
</html>
<!-- End:payBillConfiguration.jsp -->