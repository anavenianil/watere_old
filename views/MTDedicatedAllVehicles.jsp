<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTDedicatedAllVehicles.jsp  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%><html xmlns="http://www.w3.org/1999/xhtml">
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

<title> Free Dedicated Vehicles</title>
</head>

<body>
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
							<div class="lightblueBg1">
								<div class="headTitle">Free Dedicated Vehicles</div>
								<div>

								  
	                              <div class="line" id="result1">
		                              <div class="line">
										<display:table name="${sessionScope.DedicatedVehiclesList}" excludedParams="*"
											export="false" class="list" requestURI="" id="dedicatedVehicle" pagesize="500" sort="list">
										<%int i=0; %>
										<%Date d=new Date();%>
										<%Calendar cal=Calendar.getInstance(); %>
											<display:column  title='Alloted To'>${dedicatedVehicle.vehicleDriverMapDetails.vehicleDetails.dedicatedPersonSfid}</display:column>
											<display:column  title='Vehicle No' >${dedicatedVehicle.vehicleDriverMapDetails.vehicleDetails.vehicleNo}(${dedicatedVehicle.vehicleDriverMapDetails.vehicleDetails.vehiclePoolTypeDetails.vehiclePoolType})</display:column>
											<display:column  title='Driver Name' >${dedicatedVehicle.vehicleDriverMapDetails.diverDetails.driverName}</display:column>

												<display:column  title='Free FromDate & Time'>
												<c:if test="${dedicatedVehicle.flag eq 'yes'}">
												<input path="fromDate" id='fromDate${dedicatedVehicle.id}' maxlength="100" readonly="true" style="height:16px;width:80px;font-size: 11px;font-weight: bold;" value="${mtBean.currentDate}" onchange="javascript:onchangeFreeDedicatedDate('${dedicatedVehicle.id}_${dedicatedVehicle.vehicleDriverMapId}');"/>
												</c:if>
												<c:if test="${dedicatedVehicle.flag ne 'yes'}">
												<input path="fromDate" id='fromDate${dedicatedVehicle.id}' maxlength="100" readonly="true" style="height:16px;width:80px;font-size: 11px;font-weight: bold;" value="<fmt:formatDate value='${dedicatedVehicle.fromDate}' pattern='dd-MMM-yyyy' />" onchange="javascript:onchangeFreeDedicatedDate('${dedicatedVehicle.id}_${dedicatedVehicle.vehicleDriverMapId}');"/>
												</c:if>
												<img  src="./images/calendar.gif"   id='fromB${dedicatedVehicle.id}' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"fromDate${dedicatedVehicle.id}",ifFormat : "%d-%b-%Y",showsTime : false,button :"fromB${dedicatedVehicle.id}",singleClick : true,step : 1});
														</script>
														<c:if test="${dedicatedVehicle.flag eq 'yes'}">
														<input id="fromTime${dedicatedVehicle.id}" style="width:45px" readonly="readonly" class="fromTime${dedicatedVehicle.id}" onmouseover="javascript:timePicker('fromTime${dedicatedVehicle.id}');" value="08:00" onchange="javascript:onchangeFreeDedicatedDate('${dedicatedVehicle.id}_${dedicatedVehicle.vehicleDriverMapId}');"/>
												</c:if>
												<c:if test="${dedicatedVehicle.flag ne 'yes'}">
														<input id="fromTime${dedicatedVehicle.id}" style="width:45px" readonly="readonly" class="fromTime${dedicatedVehicle.id}" onmouseover="javascript:timePicker('fromTime${dedicatedVehicle.id}');" value="<fmt:formatDate value='${dedicatedVehicle.fromDate}' pattern='HH:mm' />" onchange="javascript:onchangeFreeDedicatedDate('${dedicatedVehicle.id}_${dedicatedVehicle.vehicleDriverMapId}');"/>
												</c:if>
												</display:column>
												
												<display:column  title='Free ToDate & Time'>
												<input path="toDate" id='toDate${dedicatedVehicle.id}' maxlength="100" readonly="true" style="height:16px;width:80px;font-size: 11px;font-weight: bold;" onchange="javascript:onchangeFreeDedicatedDate('${dedicatedVehicle.id}_${dedicatedVehicle.vehicleDriverMapId}');"/><img  src="./images/calendar.gif"   id='toB${dedicatedVehicle.id}' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"toDate${dedicatedVehicle.id}",ifFormat : "%d-%b-%Y",showsTime : false,button :"toB${dedicatedVehicle.id}",singleClick : true,step : 1});
														</script>
														<input id="toTime${dedicatedVehicle.id}" style="width:45px" readonly="readonly" class="toTime${dedicatedVehicle.id}" onmouseover="javascript:timePicker('toTime${dedicatedVehicle.id}');" onchange="javascript:onchangeFreeDedicatedDate('${dedicatedVehicle.id}_${dedicatedVehicle.vehicleDriverMapId}');"/>
												</display:column>
												
												<display:column  title='Free' ><input type="checkbox" name="" id="${dedicatedVehicle.id}_${dedicatedVehicle.vehicleDriverMapId}" onclick="freeDedicatedDateCheck(id)"/></display:column>
									
											 
										</display:table>		
									</div>
									
										<c:if test="${fn:length(sessionScope.DedicatedVehiclesList) ne 0}">
											<div class="line" style="margin-left: 80%;"><br/>
											<div class="expbutton"><a onclick="javascript:freeSelectedDedicatedVehicle()"> <span>Submit</span></a></div>
											</div>
										</c:if>
									
								</div>
								
							
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
	
	DedicatedVehiclesListJson= <%= (net.sf.json.JSONArray)session.getAttribute("DedicatedVehiclesListJSON") %>;

</script>
</html>
								
								
<!-- end:MTDedicatedAllVehicles.jsp  -->