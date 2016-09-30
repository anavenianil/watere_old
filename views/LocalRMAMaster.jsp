<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:LocalRMAMaster.jsp -->
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
<script type="text/javascript" src="script/tada.js"></script>

<title>City Type Master</title>

</head>
<body onload="javascript:clearlocalRMA();">
	<form:form method="post" commandName="tada" id="TadaManagementBean">
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
								<div class="headTitle">Local RMA Master</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter leftmar">From Place<span class="mandatory">*</span></div>
										<div class="quarter">
										    <form:select path="fromPlace" id="fromPlace" cssStyle="width:137px" onchange="javascript:otherFromPlaces();">
												<form:option value="Select">Select</form:option>
												<form:option value="ASL/DRDL">ASL/DRDL</form:option>
												<form:option value="RCI/CPDC">RCI/CPDC</form:option>
											</form:select>
										</div>
									</div>
									<div class="line" id="fromPlaceId" style="display:none">
										<div class="quarter leftmar">Other From Place<span class="mandatory">*</span></div>
										    <div class=quarter>
										        <form:input path="otherFromPlace" id="otherFromPlace" maxlength="30" onkeypress="javascript:return checkChar(event);"/>
										    </div>
									</div>
									
									<div class="line">
										<div class="quarter leftmar">To Place<span class="mandatory">*</span></div>
										<div class="quarter">
										    <form:select path="toPlace" id="toPlace" cssStyle="width:137px" onchange="javascript:otherToPlaces();">
												<form:option value="Select">Select</form:option>
												<form:option value="Begumpet Airport">Begumpet Airport</form:option>
												<form:option value="Secunderabad Railway Station">Secunderabad Railway Station</form:option>
												<form:option value="Hyderabad Railway Station">Hyderabad Railway Station</form:option>
												<form:option value="MGBS">MGBS</form:option>
												<form:option value="JBS">JBS</form:option>
												<form:option value="Kachiguda">Kachiguda</form:option>
												<form:option value="Shamshabad Airport">Shamshabad Airport</form:option>
												<form:option value="Other">Other</form:option>
											</form:select>
										</div>
									</div>
									<div class="line" id="toPlaceId" style="display:none">
										<div class="quarter leftmar">Other To Place<span class="mandatory">*</span></div>
										    <div class=quarter>
										        <form:input path="otherToPlace" id="otherToPlace" maxlength="30" onkeypress="javascript:return isAlphabetExp(event);" />
										    </div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Distance in k.m<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="localrmadistance" id="localrmadistance"  maxlength="30" onkeypress="javascript:return checkFloat(event,'localrmadistance');" onchange="javascript:checkPayValues('localrmadistance');"/>
										</div>
									</div>
									
						   			<div class="line" style="margin-left: 35%;">
											<div class="expbutton"><a onclick="javascript:submitlocalRMA();"> <span>Submit</span></a></div>
											<div class="expbutton"><a onclick="javascript:clearlocalRMA();"> <span>Clear</span></a></div>
									</div>	
									<div class="line"><hr /></div>
									<div class="line" id="result">
										<jsp:include page="LocalRMAMasterList.jsp"></jsp:include>								
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
		<form:hidden path="type"/>
		</form:form>	
		<script type="text/javascript">
		designationListJSON = <%= (net.sf.json.JSONArray)session.getAttribute("designationListJSON") %>;
		</script>	
			
	</body>
</html>
<!-- End:LocalRMAMaster.jsp -->