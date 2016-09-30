<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:SendingReport.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>


<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/calendar-hd.js"></script>
<script language="javascript" src="./script/RegExpValidate.js"></script>	
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/loan.js"></script>
<script type="text/javascript" src="script/ltc.js"></script>

<title>HQ Sending Details</title>
</head>

<body onload="javascript:clearSendingReport()">
	<form:form method="post" commandName="loan" >
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
								<div class="headTitle">HQ Sending Details</div>
								<%-- Content Page starts --%>
								<div class="line">
								<div class="line">
							  			<div class="quarter leftmar">Financial Year<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:select path="financialYear" id="financialYear"  cssClass="formSelect required" onmouseover="setSelectWidth('#financialYear')">
                                               <form:option value="0">Select</form:option>
                                               <form:option value="">All</form:option>
                                               <form:options items="${loan.financialYearList}" itemValue="id" itemLabel="financialYear" />
                                            </form:select>
							  			</div>
							  		</div>
							  		<div class="line">
							  			<div class="quarter leftmar">Loan Type</div>
							  			<div class="quarter">
							  				<form:select path="loanType" id="loanType"  cssClass="formSelect required" onmouseover="setSelectWidth('#loanType')" >
                                               <form:option value="">Select</form:option>
                                               <form:options items="${loan.loanTypeMasterList}" itemValue="id" itemLabel="loanName" />
                                            </form:select>
							  			</div>
							  		</div>
									<div class="line" id="reportNoDiv" style="display:none">
							  			<div class="quarter leftmar">Report Number<span class="mandatory">*</span></div>
							  				<div class="quarter">
							  					<form:input path="reportNumber" id="reportNumber"/>
							  				</div>
							  		</div>
							  		<div class="line" id="reportDateDiv" style="display:none">
							  			<div class="quarter leftmar">Report Date<span class="mandatory">*</span></div>
								  			<div class="quarter">
									  			<form:input path="reportDate" id="reportDate" readonly="true"/>
									  			<img  src="./images/calendar.gif" id="fromDateImg" />	
												<script type="text/javascript">
													Calendar.setup({inputField :"reportDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"fromDateImg",singleClick : true,step : 1});
												</script>
									  		</div>
							  		</div>
							  		<div class="line">
										<div style="margin-left:30%;">
											<a href="javascript:searchSendingReport();" class="appbutton">Search</a>
											<a href="javascript:submitSendingReport();" class="appbutton" id="submitBut" style="display:none">Submit</a>
											<a href="javascript:clearSendingReport();" class="appbutton" id="clearBut" style="display:none">Clear</a>
										</div>										
									</div>
							  		<div id="loanRequestDeatails" class="line" style="display:none">
										<jsp:include page="LoanRequestDetailsList.jsp" />
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
		</form:form>
	</body>
</html>
<!-- End:SendingReport.jsp -->