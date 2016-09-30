<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:TadaWaterReportHomePage.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css"
	rel="stylesheet" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript"
	src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/tada.js"></script>

<title>TadaAdvanceCumRequest1</title>

</head>
<body>
	<form:form method="post" commandName="tada" name="tadaWaterApproval" id="tadaWaterApproval">
		<div id="tada">
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
								<div class="headTitle">TA REPORT</div>
								<div>
									<div class="line" id="result"></div>
																					
									<div>
									<table align="center">
										<tr>
											<td>Date from : <span class="mandatory">*</span></td>
											<td><form:input path="fromDate" id="fromDate"
													cssClass="dateClass" readonly="true" onchange="javascript:noOfdays();javascript:multiplyFunction()"
													onclick="javascript:clearDateText('fromDate');" /> &nbsp;
												<img src="./images/calendar.gif" id="date_start_trigger3" />
												<script type="text/javascript">
													Calendar
															.setup({
																inputField : "fromDate",
																ifFormat : "%d-%b-%Y",
																showsTime : false,
																button : "date_start_trigger3",
																singleClick : true,
																step : 1
															});
												</script></td>
												<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td>Date to : <span class="mandatory">*</span></td>
											<td><form:input path="toDate" id="toDate"
													cssClass="dateClass" readonly="true"  
													 /> &nbsp; <img
												src="./images/calendar.gif" id="date_start_trigger4" /> <script
													type="text/javascript">
													Calendar
															.setup({
																inputField : "toDate",
																ifFormat : "%d-%b-%Y",
																showsTime : false,
																button : "date_start_trigger4",
																singleClick : true,
																step : 1
															});
												</script></td>
										</tr>
										<tr><td>&nbsp;</td></tr>
										<tr>
										</tr>
									</table>
									</div>
											<div class="line" style="margin-left: 35%;">
												<div class="expbutton">
													<a onclick="javascript:generateTaReport()"> <span>Submit</span></a>
												</div>
												<div class="expbutton">
													<a onclick="javascript:clearTadaAdvanceCumReq111()"> <span>Clear</span></a>
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
					</div>
				</div>
			</div>
		</div>
		<div><jsp:include page="Footer.jsp" /></div>
		</div>
		
		<form:hidden path="param" />
		<form:hidden path="type" />
		<form:hidden path="requestID" />
		<form:hidden path="requestId" />
	</form:form>
	
	
	<script type="text/javascript">
	$jq('#fromDate').val('');
	$jq('#toDate').val('');
	
	</script>

</body>
</html>
<!-- End:TadaAdvanceCumRequest.jsp -->
