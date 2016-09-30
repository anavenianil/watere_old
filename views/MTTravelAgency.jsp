<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTTravelAgency.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>


<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/mt.js"></script>
<title>Travel Agency Master</title>
</head>
<body onload="javascript:clearTravelAgencyDetails();">
	<form:form method="post" commandName="mtBean" id="mtBean">
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
							<div class="lightblueBg1 mtBMW">
								<div class="headTitle">Travel Agency Master</div>
								<div>
									<%-- Content Page starts --%>
								 <div class="line">
										<div class="quarter leftmar" style="margin-left: 8px;">Travel Agency Name <span class='failure'>*</span></div>
										<div class="quarter"><form:input path="travelAgencyName" id="travelAgencyName" maxlength="100" onkeypress="return checkChar(event);"></form:input></div>
								  </div>
								  <div class="line">
								     	<div class="quarter leftmar" style="margin-left: 8px;">Contact Person Name<span class='failure'>*</span></div>
										<div class="quarter"><form:input path="contactPerson" id="contactPerson" maxlength="100" onkeypress="return checkChar(event);"></form:input></div>	
	                              </div>
	                              <div class="line">
										<div class="quarter leftmar" style="margin-left: 8px;">Mobile Number<span	class='failure'>*</span></div>
										<div class="quarter"><form:input path="travelMobileNo" id="travelMobileNo" maxlength="100" onkeypress="return checkInt(event)"></form:input></div>
								</div>
								 <div class="line">
										<div class="quarter leftmar" style="margin-left: 8px;">Address<span	class='failure'>*</span></div>
										<div class="quarter"><form:textarea path="address" id="address" cols="20" rows="4" onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"/>
										<input type="text" name="counter" id="counter" value="500" class="counter" disabled="disabled"/>
										</div>
								</div>			
								<div class="line" >
												<div style="margin-left: 25%;" class="expbutton"><a onclick="javascript:saveTravelAgencyDetails();"> <span>Save</span></a></div>
												<div class="expbutton"><a onclick="javascript:clearTravelAgencyDetails();"> <span>Clear</span></a></div>
								</div>	
								<div id="result" class="line"><jsp:include page="MTTravelAgencyList.jsp" /></div>
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
		<form:hidden path="pk" id="pk"/>
		</form:form>
	</body>
</html>
<!-- End:MTTravelAgency.jsp -->