<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTVehicleVsDriverDetails.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
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

<title>Change Driver</title>
</head>
<body >
	<form:form method="post" commandName="mtBean" id="mtBean">
		<div>
		<script>$jq('#fromDate').val('');</script>
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
								<div class="headTitle" id="title">Change Driver</div>
								<div>
									<%-- Content Page starts --%>
									 <div class="line">
										<div class="quarter leftmar" style="width: 50%;"><b>FromDate & Time</b>
										
												<input name="searchFromDate" id="searchFromDate"  readonly="readonly"  style="height:16px;width:80px;font-size: 11px;font-weight: bold;" value="${mtBean.currentDate}"/>
											           <img  src="./images/calendar.gif" id="searchFromDate_trigger" />	
											         <script type="text/javascript">
												       Calendar.setup({inputField :"searchFromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"searchFromDate_trigger",singleClick : true,step : 1});
											          </script>
											          <input name="searchFromTime" id="searchFromTime" style="width:45px" readonly="readonly" class="searchFromTime" onmouseover="javascript:timePicker('searchFromTime')" value="08:40"/>
										
										</div>
											
										    <div class="quarter leftmar" style="width: 45%;"><b>ToDate & Time</b>
										   
												<input name="searchToDate" id="searchToDate"  readonly="readonly"  style="height:16px;width:80px;font-size: 11px;font-weight: bold;" value="${mtBean.currentDate}"/>
											           <img  src="./images/calendar.gif" id="searchToDate_trigger" />	
											         <script type="text/javascript">
												       Calendar.setup({inputField :"searchToDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"searchToDate_trigger",singleClick : true,step : 1});
											          </script>
											          <input name="searchToTime" id="searchToTime" style="width:45px" readonly="readonly" class="searchToTime" onmouseover="javascript:timePicker('searchToTime')" value="05:10"/>
										   
										     </div>
										    
										   
									</div>
									<div class="line">
									 <div class="expbutton" style="margin-left: 30%;"><a onclick="javascript:getDateWiseVDMap();"> <span>Search</span></a></div>
									</div>
									
								 
									 <div id="result" class="line"><jsp:include page="MTVehicleVsDriverDetailsList.jsp" /></div>
								
	                      
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
<!-- End:MTVehicleVsDriverDetails.jsp -->