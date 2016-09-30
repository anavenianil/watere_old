<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTDriverMaster.jsp -->
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
<title>New Driver</title>
</head>
<body onload="javascript:clearDriverDetails();">
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
								<div class="headTitle">Driver Master</div>
								<div>
									<%-- Content Page starts --%>
								  <div class="line">
							     		<div class="quarter bold">Driver Type<span class="mandatory">*</span></div>
											<div class="quarter bold">
													<input type=radio name="driverType" tabindex="1" value="1" id="driverType1" />Govt
													<input type=radio name="driverType" tabindex="1" value="3" id="driverType2"  />Casual
													<input type=radio name="driverType" tabindex="1" value="2" id="driverType3"  />Hired											
											</div>
	                              </div>
	                              <div id ="driverTravelAgency" style="display:none"></div>
	                              <div class="line">
													<div class="quarter bold">Driver ID<span class="mandatory">*</span></div>
													<div class="half"><form:input path="driverIdSfid" id="driverIdSfid" maxlength="100" /></div>
								 </div>		
								<div class="line">
													<div class="quarter bold">Driver Name<span class="mandatory">*</span></div>
													<div class="half"><form:input path="driverName" id="driverName" maxlength="100" onkeypress="return checkChar(event);"/></div>
								</div>	
								<div class="line">
													<div class="quarter bold">Mobile Number<span class="mandatory">*</span></div>
													<div class="half"><form:input path="driverMobileNo" id="driverMobileNo" maxlength="12" onkeypress="return checkInt(event);" /></div>
								</div>
								<div class="line">
												<div  style="margin-left: 25%;" class="expbutton"><a onclick="javascript:saveDriverDetails();"> <span>Save</span></a></div>
												<div class="expbutton"><a onclick="javascript:clearDriverDetails();"> <span>Clear</span></a></div>
								</div>	
								<div id="result" class="line"><jsp:include page="MTDriverList.jsp" /></div>
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
	<script>
	var travelList=<%= (net.sf.json.JSONArray)session.getAttribute("TravelList") %>;
	</script>
</html>
<!-- End:MTDriverMaster.jsp -->