<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTVehicleVsDriver.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script type="text/javascript" src="script/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<link href="styles/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/date.js"></script>
<script type="text/javascript"  src="script/calendar.js"></script>
<script type="text/javascript"  src="script/calendar-en.js"></script>
<script type="text/javascript"  src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/mt.js"></script>
<title>Vehicle Vs Driver Mapping</title>
</head>
<body onload="javascript:clearVehicleDriverDetails();">

	<form:form method="post" commandName="mtBean" id="mtBean">
		
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
								<div class="headTitle">Allocate Driver To Vehicle</div>
								<div>
									<%-- Content Page starts --%>
							
								  <div class="line">
								  		<div class="quarter leftmar" style="margin-left: 8px;">Vehicle No<span class='failure'>*</span></div>
									   <div class="quarter bold"><form:select path="vehicleId" id="vehicleId" cssClass="formSelect" >
																    <form:option value="0">Select</form:option>
																	<form:options items="${VehicleList}" itemValue="vehicleId" itemLabel="vehicleNo"/>
																</form:select>													
									  </div>
	                              </div>
	                                <div class="line">
								  		<div class="quarter leftmar" style="margin-left: 8px;">Driver Name</div>
									   <div class="quarter bold"><form:select path="driverId" id="driverId" cssClass="formSelect"  >
																   <form:option value="0">Select</form:option>
																	<form:options items="${DriverEmployeeList}" itemValue="driverId" itemLabel="driverName"/>
																</form:select>													
									  </div>
	                              </div>
	                              	<div class="line">
										<div class="quarter leftmar" style="width: 24%;"><b>Allotment Date & Time</b><span class='failure'>*</span> </div>
										<div class="quarter">
											<input name="fromDate" id="fromDate"  readonly="readonly"  style="height:16px;width:80px;font-size: 11px;font-weight: bold;" value="${mtBean.currentDate}"/>
											<img  src="./images/calendar.gif" id="date_start_trigger1" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
											</script>
											<input name="fromTime" id="fromTime" style="width:45px" readonly="readonly" class="fromTime" onmouseover="javascript:timePicker('fromTime')" value="08:40"/>
										</div>
										
									</div>
									<div class="line">
										<div class="quarter leftmar" style="margin-left: 8px;">Remarks<span></span></div>
										<div class="quarter"><form:textarea path="remarks" id="remarks" cols="20" rows="4" onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"></form:textarea>
											<input type="text" name="counter" id="counter" value="500" class="counter" disabled="disabled"/>
										</div>
									</div>
									<div class="line">
													<div  style="margin-left: 25%;" class="expbutton"><a onclick="javascript:saveVehicleDriverDetails();"> <span>Save</span></a></div>
													<div class="expbutton"><a onclick="javascript:clearVehicleDriverDetails();"> <span>Clear</span></a></div>
									</div>	
								<div id="result" class="line"><jsp:include page="MTVehicleVsDriverList.jsp" /></div>
								
								<%-- Content Page end --%>
						
								<div>&nbsp;</div>
							</div>
						</div></div>
						<div class="whitebox_b_non">
							<div class="whitebox_bl">
								<div class="whitebox_br"></div>
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
<!-- End:MTVehicleVsDriver.jsp -->