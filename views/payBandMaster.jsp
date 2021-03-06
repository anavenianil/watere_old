<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:payBandMaster.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script type="text/javascript" src="script/payBill.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<title>Pay Bill </title>
</head>
<body onload="javascript:clearPayBandDetails();">
	<form:form method="post" commandName="payScale" id="PayScaleBean">
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
								<div class="headTitle">Pay Band Creation Master</div>
								<div class="line">
									<%-- Content Page starts --%>
									<div class="line">
										<div class="half" style="padding-left: 70%;width: 280px;"><strong>Pay Run Month:&nbsp;&nbsp;<font color="green">${payScale.payRunMonth}</font></strong></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Pay Band Name<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="name" id="name"  cssClass="formSelect"  /></div>
										<div class="quarter leftmar">Eff From<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="effDate" id="effDate" cssClass="required" onkeypress="javascript:setReadOnly(event, 'effDate')"/>
											<img  src="./images/calendar.gif" id="date_start_trigger2"/>
											<script type="text/javascript">
												Calendar.setup({inputField :"effDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Range From<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="rangeFrom" id="rangeFrom"  cssClass="formSelect"  onkeypress="return checkInt(event);" /></div>
										<div class="quarter leftmar">To<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="rangeTo" id="rangeTo"  cssClass="formSelect"  onkeypress="return checkInt(event);"/></div>
									</div>
									<div class="line">
										<div style="margin-left:40%">
											<div class="expbutton"><a href="javascript:createPayBand();"><span>Submit</span></a></div>
											<div class="expbutton"><a href="javascript:clearPayBandDetails();"><span>Clear</span></a></div>
											<div class="expbutton"><a href="javascript:printPayBandDetails();"><span>Report</span></a></div>
										</div>
									</div>
									<div class="line" id="result"><jsp:include page="payBandMasterList.jsp" /></div>
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
<!-- End:payBandMaster.jsp -->