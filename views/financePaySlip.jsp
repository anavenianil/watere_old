<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:financePaySlip.jsp  -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/payBill.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<title>Finance Pay Slip Details</title>
</head>
<body>
	<form:form method="post" commandName="schedulereports" id="schedulereports">
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
								<div class="headTitle">Finance Pay Slip Details</div>
								<div class="line">
									<%-- Content Page starts --%>
									<div class="line">
                        				<div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
											  <div class="quarter"><form:input path="sfid" id="sfid" maxlength="1000"/>				
										     </div>
								    </div>
									<div class="line">
										<div class="quarter leftmar">Pay Slip Month<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="value2" id="value2" cssClass="required" onkeypress="javscript:setReadOnly(event, 'value2')"/>
										<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"value2",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
											</script>
										</div>
									</div>
								<div class="line">
									<div style="margin-left:50%">
										<div class="expbutton"><a href="javascript:getFinancePaySlip();"><span>Submit</span></a></div>
										<div class="expbutton"><a href="javascript:clearFinancePaySlipDetails();"><span>Clear</span></a></div>
									</div>
									</div>
									<div class="line" id="result">
									   
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
		</form:form>
		</body>
</html>
<!-- End:financePaySlip.jsp -->
