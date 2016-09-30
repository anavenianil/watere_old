<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:payAllwDetails.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/payBill.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<title>Configuration Details</title>
</head>

<body>
	<form:form method="post" commandName="payScale">
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
								<div class="headTitle">Pay Configuration Details</div>
								<div>
									<%-- Content Page starts --%>
								<div class="line">
								<div class="quarter bold">Configuration Type<span class="failure">*</span></div>
								<div class="quarter">:&nbsp;<form:select path="cofigurationId"
									id="cofigurationId" cssClass="formSelect">
									<form:option value="0">Select</form:option>
									<form:options items="${payScale.allwTypeList}" itemLabel="allwType" itemValue="id"/>
								</form:select></div>
								</div>
								<div class="line">
                        				<div class="quarter bold">Amount<span class="mandatory">*</span></div>
											  <div class="quarter">:&nbsp;<form:input path="amount" id="amount" maxlength="10" onkeypress="return checkInt(event);"/>				
										     </div>
								</div>
								<div class="line">
									<div class="quarter leftmar">Eff From<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="effDate" id="effDate" cssClass="required" readonly="true"/>
										<img  src="./images/calendar.gif" id="date_start_trigger2" />	
										<script type="text/javascript">
											Calendar.setup({inputField :"effDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
										</script>
										</div></div>
								<div class="line">
											<div style="margin-left:50%;"><div class="appbutton" onclick="javascript:managePayBillAllwConfDetails();">Submit</div></div>
											<div style="margin-left:50%;"><div class="appbutton" onclick="javascript:clearpayBillAllwConfDetails();">Clear</div></div>
								            <div style="margin-left:50%;"><div class="appbutton" onclick="javascript:printPayBillAllwConfDetails();">Report</div></div>
								</div>
								<div class="line" id="result"><jsp:include page="payAllwDetailsList.jsp"></jsp:include></div>
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
		<form:hidden path="pk"/>
		</form:form>
	</body>
</html>
<!-- End:payAllwDetails.jsp -->