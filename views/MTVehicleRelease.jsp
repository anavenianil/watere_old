<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTVehicleRelease.jsp  -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
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
<script type="text/javascript"  src="script/calendar.js"></script>
<script type="text/javascript"  src="script/calendar-en.js"></script>
<script type="text/javascript"  src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/mt.js"></script>
<title>Vehicle Release</title>
</head>
<body onload="javascript:clearReleaseVehicleDetails();">
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
								<div class="headTitle">Vehicle Release Application</div>
								<div>
									<%-- Content Page starts --%>
								  
								  <div class="line">
							     			<div class="quarter bold">Name & Designation: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
											<div class="quarter">${mtBean.employeeDetails.nameInServiceBook}&nbsp;&&nbsp; ${mtBean.employeeDetails.designationDetails.name}</div>
	                              			<div class="quarter bold">SFNo. & Phone No.: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
											<div class="quarter">${mtBean.employeeDetails.userSfid}&nbsp;&&nbsp; ${mtBean.employeeDetails.personalNumber}</div>
	                              </div>
	                              <div class="line" id="result1">
		                              <div class="line">
										<display:table name="${sessionScope.AllocatedVehicles}" excludedParams="*"
											export="false" class="list" requestURI="" id="vehicle" pagesize="10" sort="list">
											<display:column  title='Vehicle No' >${vehicle.vehicleDriverMapDetails.vehicleDetails.vehicleNo}(${vehicle.vehicleDriverMapDetails.vehicleDetails.vehiclePoolTypeDetails.vehiclePoolType})</display:column>
											<display:column  title='Driver Name' >${vehicle.vehicleDriverMapDetails.diverDetails.driverName}</display:column>
										<c:if test="${vehicle.toDate ne null}">
												<display:column  title='From Date & Time'><fmt:formatDate value="${vehicle.fromDate}" pattern="dd-MMM-yyyy HH-mm" /></display:column>
												<display:column  title='To Date & Time'><fmt:formatDate value="${vehicle.toDate}" pattern="dd-MMM-yyyy HH-mm" /></display:column>
												<display:column  title='Released Date & Time'><form:input path="todayDate" id='todayDate${vehicle.id}' maxlength="100" readonly="true"/><img  src="./images/calendar.gif"   id='${vehicle.id}' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"todayDate${vehicle.id}",ifFormat : "%d-%b-%Y %H-%M ",showsTime : true,button :"${vehicle.id}",singleClick : true,step : 1});
														</script>
												</display:column>
												<display:column  title='Release' ><input type="checkbox" name="" id="${vehicle.id}#${vehicle.vehicleDriverMapId}" /></display:column>
											
										</c:if>
										<c:if test="${vehicle.toDate eq null}">
											
											<display:column  title='From Date & Time'><form:input path="fromDate" id="fromDate" maxlength="100" readonly="true"/><img  src="./images/calendar.gif"   id="fromDate1" />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y %H-%M ",showsTime : true,button :"fromDate1",singleClick : true,step : 1});
														</script>
											 </display:column>
											<display:column  title='To Date & Time'><form:input path="toDate" id="toDate" maxlength="100" readonly="true"/><img  src="./images/calendar.gif"   id="toDate1" />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y %H-%M ",showsTime : true,button :"toDate1",singleClick : true,step : 1});
														</script>
											</display:column>
											<display:column  title='Released Date & Time'><form:input path="todayDate" id='todayDate${vehicle.id}' maxlength="100" readonly="true"/><img  src="./images/calendar.gif"   id='${vehicle.id}' />	
														<script type="text/javascript">
											           		Calendar.setup({inputField :"todayDate${vehicle.id}",ifFormat : "%d-%b-%Y %H-%M ",showsTime : true,button :"${vehicle.id}",singleClick : true,step : 1});
														</script>
											</display:column>
												<display:column  title='Release' ><input type="checkbox" name="" id="${vehicle.id}@${vehicle.vehicleDriverMapId}" /></display:column>
										</c:if>
										</display:table>	
										<c:if test="${fn:length(sessionScope.AllocatedVehicles) ne 0}">
												<div class="line" style="margin-left: 80%;"><br/>
												<div class="expbutton"><a onclick="javascript:releaseSelectedVehicles()"> <span>Submit</span></a></div>
												</div>
										</c:if>	
									</div>
									
									
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
		<form:hidden path="pk" id="pk"/>
		</form:form>
	</body>
</html>
<!-- End:MTVehicleRelease.jsp  -->