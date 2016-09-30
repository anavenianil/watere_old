<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LoanSanctionDetails.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css"	rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script language="javascript" src="./script/RegExpValidate.js"></script>	

	
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/loan.js"></script>
<script type="text/javascript" src="./script/script.js"></script>

<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>

<title>Loan Amount Sanction</title>
</head>

<body>
	<form:form method="post" id="loanSanction">
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
								<div class="headTitle">Loan Amount Sanction</div>
								     <%-- Content Page starts --%>
								 <div class="line">     
								<div class="line"> 
								      <div class="quarter bold" style="margin-left:8px;"  >Financial Year <span class="mandatory">*</span></div>
									   <div class="quarter">
										<form:select path="financialYear" id="financialYear" cssClass="formSelect required" >
                                               <form:option value="" disabled="yes">Select</form:option>
                                              <form:options items="${sessionScope.sfYearList}" itemLabel="name" itemValue="name" /> 
                                            </form:select>
									</div>
								</div>	
								<div class="line"> 
									<div class="quarter bold" style="margin-left:8px;"  >Loan Type<span class="mandatory">*</span></div>
									   <div class="quarter">
										<form:select path="loanType" id="loanType" cssClass="formSelect required" >
                                               <form:option value="" disabled="yes">Select</form:option>
                                               <form:option value="0" disabled="yes">ALL</form:option>
                                               <form:options items="${sessionScope.sanctionLoanList}" itemValue="id" itemLabel="name" />
                                            </form:select>
									</div>
								</div>	
								<div class="line">
									<div style="margin-left:24%;">
										<div class="expbutton"><a href="javascript:manageSanctionDetails();"> <span>Submit</span></a></div>
									</div>
									<div class="expbutton"><a href="javascript:clearSanctionForm();"><span>Clear</span></a></div>
								</div>	
								<div class="line" ><hr/></div>
								<div class="line" id="LoanSanctionList"></div>
								<div class="line" id="sanction" style="display:none">
									<div class="quarter bold" style="margin-left:8px;" >Sanction Letter No<span class="mandatory">*</span></div>
									<div class="quarter"><form:input path="sanctionLetterNo"  id="sanctionLetterNo" /></div>
								    <div class="quarter bold" style="margin-left:8px;" >Sanction Date<span class="mandatory" onclick="javascript:clearDateText('dateofDeath');">*</span></div>
									<div class="quarter"><form:input path="sanctionDate" id="sanctionDate" cssClass="dateClass" readonly="true"
		                                onclick="javascript:clearDateText('sanctionDate');" /> <img
		                                  src="./images/calendar.gif" id="date_start_trigger" /> 
		                                  <script	type="text/javascript">
												Calendar.setup({inputField :"sanctionDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger",singleClick : true,step : 1});
												</script></div>
								    <div class="expbutton"><a href="javascript:submitSanctionDetails();"> <span>Sanction</span></a></div>
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
				
			</div>
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="jsonValue"/>
		</form:form>
	</body>
</html>
<!-- End:LoanSanctionDetails.jsp -->