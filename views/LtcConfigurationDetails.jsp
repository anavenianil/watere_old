<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LtcConfigurationDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/ltc.js"></script>
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<title>Ltc Configuration Details</title>
</head>

<body>
	<form:form method="post" commandName="ltcMaster" name="ltcMaster" id="ltcMaster">
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
								<div class="headTitle" id="headTitle">LTC Configuration Details</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</img></div>
								<%-- Content Page starts --%>
								<div>
									<div id="result"></div>
									<div class="line">
											<div class="line">
												<div class="quarter bold" style="margin-left:8px;">  No Of All India Trips In 4 Year Block<span class="failure">*</span></div>
												<div class="quarter">
													<form:input path="allIndiaTripsInFourYearBlock" id="allIndiaTripsInFourYearBlock" maxlength="3"/>	
												</div>
											</div>
											<div class="line">
												<div class="quarter bold" style="margin-left:8px;">  No Of Home Town Trips In 2 Year Block<span class="failure">*</span></div>
												<div class="quarter">
													<form:input path="homeTownTripsInTwoYearBlock" id="homeTownTripsInTwoYearBlock" maxlength="3"/>	
												</div>
											</div>
											<div class="line">
												<div class="quarter bold" style="margin-left:8px;">Max No Of EL's Encashment In Service<span class="failure">*</span></div>
												<div class="quarter">
													<form:input path="maxELEncashInService" id="maxELEncashInService" maxlength="3"/> Days
												</div>
											</div>
											<div class="line">
												<div class="quarter bold" style="margin-left:8px;">Max EL's Encashment In a Trip<span class="failure">*</span></div>
												<div class="quarter">
													<form:input path="maxELEncashInTrip" id="maxELEncashInTrip" maxlength="3"/>	Days
												</div>
											</div>
											
										<fieldset><legend><strong><font color='green'>For New Employee</font></strong></legend>
											<div class="line">
												<div class="quarter bold" style="margin-left:1px;">  No Of Block Years<span class="failure">*</span></div>
												<div class="quarter">
													<form:input path="newEmpBlockYears" id="newEmpBlockYears" maxlength="3"/>	
												</div>
											</div>
											<div class="line">
												<div class="quarter bold" style="margin-left:1px;"> No Of All India Trips<span class="failure">*</span></div>
												<div class="quarter">
													<form:input path="newEmpAllIndiaTrips" id="newEmpAllIndiaTrips" maxlength="3"/>	
												</div>
											</div>
											<div class="line">
												<div class="quarter bold" style="margin-left:1px;"> No Of Home Town Trips<span class="failure">*</span></div>
												<div class="quarter">
													<form:input path="newEmpHomeTownTrips" id="newEmpHomeTownTrips" maxlength="3"/>	
												</div>
											</div>
										</fieldset>
									</div>
									<div class="line">
											<div style="margin-left:25%;"><a class="quarterbutton appbutton" href="javascript:manageLtcConfig();">Submit</a></div>
											<div class="appbutton"><a class="quarterbutton appbutton" href="javascript:clearLtcConfig();">Clear</a></div>
									</div>
								</div>
								<%-- Content Page end --%>
								<div>&nbsp;</div>
							</div>
						</div>
						<div class="whitebox_b_ Non">
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
<!-- End:LtcConfigurationDetails.jsp -->