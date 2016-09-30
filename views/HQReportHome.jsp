<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:HQReportHome.jsp -->
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
<script type="text/javascript" src="script/script.js"></script>

<title>HQ Received Details</title>
</head>

<body onload="javascript:clearHQReport()">
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
								<div class="headTitle">HQ Received Details</div>
								<%-- Content Page starts --%>
								<div class="line">
								<div class="line">
							  			<div class="quarter leftmar">Report Number<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:select path="reportNumber" id="reportNumber"  cssClass="formSelect required" onmouseover="setSelectWidth('#financialYear')" onchange="javascript:searchHQReport();">
                                               <form:option value="0">Select</form:option>
                                               <form:options items="${loan.reportNumberList}" itemValue="sendingReportNumber" itemLabel="sendingReportNumber" />
                                            </form:select>
							  			</div>
							  		</div>
							  		<div class="line">
							  			<div class="quarter leftmar">Loan Type</div>
							  			<div class="quarter">
							  				<form:select path="loanType" id="loanType"  cssClass="formSelect required" onmouseover="setSelectWidth('#loanType')" onchange="javascript:searchHQReport();" >
                                               <form:option value="">Select</form:option>
                                               <form:options items="${loan.loanTypeMasterList}" itemValue="id" itemLabel="loanName" />
                                            </form:select>
							  			</div>
							  		</div>
									<div class="line" id="cdaReportNoDiv" style="display:none">
							  			<div class="quarter leftmar">HQ Report Number<span class="mandatory">*</span></div>
							  				<div class="quarter">
							  					<form:input path="hqReportNumber" id="hqReportNumber"/>
							  				</div>
							  		</div>
							  		<div class="line" id="hqReportDateDiv" style="display:none">
							  			<div class="quarter leftmar">HQ Report Date<span class="mandatory">*</span></div>
								  			<div class="quarter">
									  			<form:input path="hqReportDate" id="hqReportDate" readonly="true"/>
									  			<img  src="./images/calendar.gif" id="fromDateImg" />	
												<script type="text/javascript">
													Calendar.setup({inputField :"hqReportDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"fromDateImg",singleClick : true,step : 1});
												</script>
									  		</div>
							  		</div>
							  		<div class="line">
										<div style="margin-left:30%;">
											<a href="javascript:submitHQReport();" class="appbutton" id="submitBut" style="display:none">Submit</a>
											<a href="javascript:reGenerateHQReport();" class="appbutton" id="reGenerateBut" style="display:none">Regenerate</a>
											<a href="javascript:clearHQReport();" class="appbutton" id="clearBut" style="display:none">Clear</a>
										</div>										
									</div>
									<div class="line" id="result"><jsp:include page="Result.jsp"></jsp:include></div>
									<div id="loanReportDetails" class="line" style="display:none">
										<jsp:include page="LoanReportDetailsList.jsp" />
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
		<form:hidden path="message"/>
		<form:hidden path="historyID"/>
		<form:hidden path="requestId"/>
		<form:hidden path="statusMsg"/>
		<form:hidden path="back"/>		
		</form:form>
	</body>
</html>
<!-- End:HQReportHome.jsp -->