<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTDriverAbsent.jsp -->
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
<title>Driver Absent Form</title>
</head>
<body onload="javascript:clearDriverAbsentDetails();">
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
								<div class="headTitle">Driver Absent Form</div>
								<div>
									<%-- Content Page starts --%>
								  <div class="line">
							     		<div class="quarter leftmar" >Driver Name<span class='failure'>*</span></div>
										<div class="quarter" >	
		                              					<form:select path="driverName" id="driverName">
														<form:option value="0">Select</form:option>
														<form:options items="${DriverEmployeeList}" itemValue="value" itemLabel="name"/>
														</form:select>								
		                                </div>
	                                
	                              </div>
	                            <div class="line"></div>
								<div class="line">
										<div class="quarter leftmar" style="width: 24%;"><b>From Date & Time</b><span class='failure'>*</span> </div>
										<div class="quarter">
											<input name="fromDate" id="fromDate"  readonly="readonly"  style="height:16px;width:80px;font-size: 11px;font-weight: bold;" />
											<img  src="./images/calendar.gif" id="date_start_trigger1" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
											</script>
											<input name="fromTime" id="fromTime" style="width:45px" readonly="readonly" class="fromTime" onmouseover="javascript:timePicker('fromTime')" />
										</div>
										
								</div>
								<div class="line">
										<div class="quarter leftmar" style="width: 24%;"><b>To Date & Time </b><span class='failure'>*</span> </div>
										<div class="quarter">
											<input name="toDate" id="toDate"  readonly="readonly" style="height:16px;width:80px;font-size: 11px;font-weight: bold;" />
											<img  src="./images/calendar.gif" id="date_start_trigger2" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
											</script>
											<input name="toTime" id="toTime" style="width:45px" readonly="readonly" class="toTime" onmouseover="javascript:timePicker('toTime')" />
										</div>
										
								</div>
								<div class="line">
										<div class="quarter leftmar" style="margin-left: 8px;">Remarks<span></span></div>
										<div class="quarter"><form:textarea path="remarks" id="remarks" cols="20" rows="4" onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"></form:textarea>
											<input type="text" name="counter" id="counter" value="500" class="counter" disabled="disabled"/>
										</div>
								</div>
								<div class="line">
												<div class="expbutton" style="margin-left: 25%;"><a onclick="javascript:saveDriverAbsentDetails();"> <span>Save</span></a></div>
												<div class="expbutton"><a onclick="javascript:clearDriverAbsentDetails();"> <span>Clear</span></a></div>
								</div>	
								<div id="result" class="line"><jsp:include page="MTDriverAbsentList.jsp"></jsp:include></div>
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
<!-- End:MTDriverAbsent.jsp -->