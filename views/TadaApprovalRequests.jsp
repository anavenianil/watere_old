<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:TadaApprovalRequest.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/date.js"></script>
<script type="text/javascript" src="script/tada.js"></script>

<title>Tada Approval Request</title>

</head>
<body onload="javascript:enableGpfAcNo();">
	<form:form method="post" commandName="tada" id="conveyanceModeList">
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
								<div class="headTitle">Requistion Cum Movement Order For Temporary Duty Move</div>
								<%-- Content Page starts --%>
								<div class="line">
								    <div class="line" id="result">
																		
									</div>
									<div class="line">
										<div class="" id="gpfAcNoId" align="right" style="display:none">
										
										</div>
										
									</div>
								    <div class="line">
										<div class="quarter leftmar">Name & Designation<br/>SFNo. & Phone No.</div>
											<div class="quarter">
											    ${tada.empDetailsList.nameInServiceBook}&nbsp;&nbsp;& ${tada.empDetailsList.designationDetails.name}<br/>${tada.empDetailsList.userSfid}&nbsp;&nbsp;& ${tada.empDetailsList.personalNumber}
											</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Basic Pay  &  Grade Pay</div>
											<div class="quarter">
											    ${tada.payDetailsList.basicPay}&nbsp;&nbsp;& ${tada.payDetailsList.gradePay}
											</div>
									</div>
									
									<div class="line">
										<div class="quarter leftmar">Directorate in which working<span class="mandatory">*</span></div>
											<div class="quarter" id="workingPlace">
											    ${tada.deptDTO.deptName}
											</div>
									</div>
								
								    <div class="line">
										<div class="quarter leftmar">Date Of Departure<span class="mandatory">*</span></div>
											<div class="quarter">
											    <form:input path="departureDate" id="departureDate" readonly="true" onchange="javascript:checkDepartureDate();"/> 
																<img  src="./images/calendar.gif"   id="date_start_trigger3" />	
																<script type="text/javascript">
																	Calendar.setup({inputField :"departureDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
																</script>
											</div>
									</div>
									<div id="travelType" style="display:none">
							
									</div>
									<div id="entitleClass" style="display:none">
							
									</div>
									<div class="line">
										<div class="quarter leftmar">Duration Of Stay at Out Station<span class="mandatory">*</span></div>
											<div class="quarter">
											    <form:input path="stayDuration" id="stayDuration" maxlength="30"/>
											</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Exact Place Of Work at temporary duty station with postal address,name and phone no. of the person to be contacted<span class="mandatory">*</span></div>
											<div class="quarter">
											    <form:textarea path="tdWorkPlace" id="tdWorkPlace" rows="5" cols="30"  onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"/>
		                                        <input type="text" class="counter" name="counter" value="500" id="counter" disabled=""/>
											</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Purpose<span class="mandatory">*</span></div>
											<div class="quarter">
											    <form:input path="purpose" id="purpose" maxlength="30"/>
											</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">TD move is authorised for<span class="mandatory">*</span></div>
											<div class="quarter">
											    <form:radiobutton path="authorizedMove" id="authorizedMove" value="1"/><label>Build-Up</label>
											    <form:radiobutton path="authorizedMove" id="authorizedMove" value="2"/><label>Project</label>
											</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Wheather TA/DA is required with Hotel/Normal rates<span class="mandatory">*</span></div>
											<div class="quarter">
											    <form:radiobutton path="tadaRequirement" id="tadaRequirement" value="hotelrate"/><label>Hotel Rates</label>
											    <form:radiobutton path="tadaRequirement" id="tadaRequirement" value="normalrate"/><label>Normal Rates</label>
											</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">The individual is required to move at a short notice.Hence booking of ticket under TATKAL quota is sanctioned<span class="mandatory">*</span></div>
											<div class="half">
											    <form:radiobutton path="tatkalQuota" id="tatkalQuota" value="onward"/><label>Onward</label>
											    <form:radiobutton path="tatkalQuota" id="tatkalQuota" value="return"/><label>Return</label>
											    <form:radiobutton path="tatkalQuota" id="tatkalQuota" value="both"/><label>Both</label>
											    <form:radiobutton path="tatkalQuota" id="tatkalQuota" value="na"/><label>N/A</label>
											</div>
									</div>
	
									<div class="line" style="margin-left: 35%;">
											<div class="expbutton"><a onclick="javascript:submitTdAmendmentApproval('${tada.requestId}')"> <span>Submit</span></a></div>
											<div class="expbutton"><a onclick="javascript:clearTdApproval()"> <span>Clear</span></a></div>
									</div>	
									
																									
								</div>					
								<%-- Content Page end --%>
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
		<form:hidden path="param"/>
		<form:hidden path="requestType"/>
		<form:hidden path="requestId"/>
		<form:hidden path="type"/>
		</form:form>	
		<script type="text/javascript">
		  travelTypeMapDetailsJSON= <%= (net.sf.json.JSONArray) session.getAttribute("travelTypeMapDetailsJSON") %>;
		  entitleClassListJSON = <%= (net.sf.json.JSONArray) session.getAttribute("entitleClassListJSON") %>;
		  taEntitleClassListJSON = <%= (net.sf.json.JSONArray) session.getAttribute("taEntitleClassListJSON") %>;
		  travelTypeListJSON = <%= (net.sf.json.JSONArray) session.getAttribute("travelTypeListJSON") %>;
		  empDetailsJSON = <%= (net.sf.json.JSONObject) session.getAttribute("empDetailsJSON") %>;
		  clearTdApproval();
		</script>
		
		<c:if test="${not empty requestScope.TadaRequestBean}">
		 <script>
			tadaRequestBean = <%= (net.sf.json.JSONObject)request.getAttribute("TadaRequestBean") %>;
			
			
		 </script>
	   </c:if>	
			
	</body>
</html>
<!-- End:TadaApprovalRequest.jsp -->
