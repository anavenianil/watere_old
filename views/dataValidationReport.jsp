<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:dataValidationReport.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<title>Data Validation Reports</title>
</head>
<body>
	<form:form method="post" commandName="schedulereports">
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
								<div class="headTitle">Data Validation Reports</div>
								<div>
									<%-- Content Page starts --%>
								<div class="line"><b><u>PIS:</u></b></div>
								<div class="line">
								<div class="quarter bold" style="width:35%; color: blue;">Address Is Not Entered</div>
								<div class="quarter" style="width:15% "><a href="javascript:printDataValidationReport('address');" style="color: red;">Not Entered</a></div>
								<div class="quarter" ><a href="javascript:printDataValidationReport('presentaddress');" style="color: purple;">Available in PDF</a></div>
								</div>
								<div class="line">
								<div class="quarter bold" style="width:35%; color: blue;">Mother Tongue Details</div>
								<div class="quarter" style="width:15%"><a href="javascript:printDataValidationReport('nullMotherTongue');" style="color: red;">Not Entered</a></div>
								<div class="quarter" ><a href="javascript:printDataValidationReport('empMotherTongue');" style="color: purple;">Available</a></div>
								</div>
								<div class="line">
								<div class="quarter half bold" style="color: blue;">Physically Handicapped</div>
								<div style="margin-left:50%"><a href="javascript:printDataValidationReport('phyHandicapped');" style="color: purple">Available</a></div>
								</div>
								<div class="line">
								<div class="quarter half bold" style="color: blue;">Exservice Man</div>
								<div style="margin-left:50%"><a href="javascript:printDataValidationReport('exserviceMan');" style="color: purple">Available</a></div>
								</div>
								<div class="line">
								<div class="quarter half bold" style="color: blue;">Nominal Role</div>
								<div style="margin-left:50%"><a href="javascript:printDataValidationReport('nominalRole');" style="color: purple">Available</a></div>
								</div>
								<div class="line">
								<div class="quarter  bold" style="color: blue;">Nearest Railway Is Not Entered</div>
								<div style="margin-left:35%"><a href="javascript:printDataValidationReport('nearestRailway');" style="color: red;">Not Entered</a></div>
								</div>
								<div class="line">
								<div class="quarter bold" style="color: blue;">Employee Qualification Details</div>
								<div class="quarter" style="margin-left:26%"><a href="javascript:printDataValidationReport('empQualification','pdf');" style="color: purple">Available in PDF</a></div>
								<div class="quarter" ><a href="javascript:printDataValidationReport('empQualification','excel');" style="color: purple">Available in EXCEL</a></div>
								</div>
								<div class="line">
								<div class="quarter half bold" style="color: blue;">Employee Internal Number Details</div>
								<div style="margin-left:50%"><a href="javascript:printDataValidationReport('empTeleNo');" style="color: purple">Available</a></div>
								</div>
								<div class="line">
								<div class="quarter half bold" style="color: blue;">Employee Permanent Address Details</div>
								<div style="margin-left:50%"><a href="javascript:printDataValidationReport('empPermanentAddr');" style="color: purple">Available</a></div>
								</div>
								<div class="line"><b><u>LEAVE:</u></b></div>
								<div class="line">
								<div class="quarter half bold" style="color: blue;">Leave Balance Verification Report</div>
								<div style="margin-left:50%"><a href="javascript:printDataValidationReport('leaveVerification');" style="color: purple">Available</a></div>
								</div>
								<div class="line">
								<div class="quarter half bold" style="color: blue;">Leave Data Not Entered</div>
								<div style="margin-left:50%"><a href="javascript:printDataValidationReport('leaveManualEntry');" style="color: purple">Available</a></div>
								</div>
								<div class="line">
								<div class="quarter half bold" style="color: blue;">Leave Balance To Be Corrected</div>
								<div style="margin-left:50%"><a href="javascript:printDataValidationReport('leaveBalanceValidation');" style="color: purple">Available</a></div>
								</div>
								<div class="line"><b><u>HINDI INCENTIVE:</u></b></div>
								<div class="line">
								<div class="quarter half bold" style="color: blue;">Hindi Incentive Details</div>
								<div style="margin-left:50%"><a href="javascript:printDataValidationReport('hindiIncentive');" style="color: purple">Available</a></div>
								</div>
								<div class="line"><b><u>LOAN:</u></b></div>
								<div class="line">
								<div class="quarter half bold" style="color: blue;">Loan Details</div>
								<div style="margin-left:50%"><a href="javascript:printDataValidationReport('loanDetails');" style="color: purple">Available</a></div>
								</div>
								<div class="line"><b><u>PAY:</u></b></div>
								<div class="line">
								<div class="quarter  bold" style="color: blue;">BasicPay Details</div>
								<div style="margin-left:35%"><a href="javascript:printDataValidationReport('missingBasicPay');" style="color: red;">Not Entered</a></div>
								</div>
								<div class="line">
								<div class="quarter  bold" style="color: blue;">GradePay Details</div>
								<div style="margin-left:35%"><a href="javascript:printDataValidationReport('missingGradePay');" style="color: red;">Not Entered</a></div>
								</div>
								<div class="line">
								<div class="quarter bold" style="width:35%; color: blue;">Pay Details</div>
								<div class="quarter" style="width:15%"><a href="javascript:printDataValidationReport('payEntry');" style="color: red;">Not Entered</a></div>
								<div class="quarter" ><a href="javascript:printDataValidationReport('missingPay');" style="color: purple;">Available</a></div>
								</div>
								<div class="line">
								<div class="quarter half bold" style="color: blue;">Quarter Details</div>
								<div style="margin-left:50%"><a href="javascript:printDataValidationReport('empQuarterDetails');" style="color: purple">Available</a></div>
								</div>
								<div class="line"><b><u>TASKHOLDER:</u></b></div>
								<div class="line">
								<div class="quarter half bold" style="color: blue;">Task Holder-Employee</div>
								<div style="margin-left:50%"><a href="javascript:printDataValidationReport('empTaskHolder');" style="color: purple">Available</a></div>
								</div>
								<div class="line">
								<div class="quarter half bold" style="color: blue;">Task Holder-Designations</div>
								<div style="margin-left:50%"><a href="javascript:printDataValidationReport('taskHolderDesig');" style="color: purple">Available</a></div>
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
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type"/>
		</form:form>
	</body>
</html>
<!-- End:dataValidationReport.jsp -->
