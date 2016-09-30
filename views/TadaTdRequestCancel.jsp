<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:TadaTdRequestCancel.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
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

<title>Tada Approval Request</title>

</head>
<body>
	<form:form method="post" commandName="tada" id="conveyanceModeList" name="${sessionScope.conveyanceModeList}">
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
								<div class="headTitle">TADA TD Request Cancel Form</div>
								<%-- Content Page starts --%>
								<div class="line">
								    <div class="line" id="result">
																		
									</div>
									<div class="line">
									
									</div>
									<div class="line">
										<div class="quarter leftmar">Name & Designation<br/>SFNo. & Phone No.</div>
											<div class="half">
											    :&nbsp;${tada.empDetailsList.nameInServiceBook}&nbsp;&nbsp;& ${tada.empDetailsList.designationDetails.name}<br/>:&nbsp;${tada.empDetailsList.userSfid}&nbsp;&nbsp;& ${tada.empDetailsList.personalNumber}
											</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Basic Pay  &  Grade Pay</div>
											<div class="quarter">
											    :&nbsp;${tada.payDetailsList.basicPay}&nbsp;&nbsp;& ${tada.payDetailsList.gradePay}
											</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Directorate in which working</div>
											<div class="quarter" id="workingPlace">
											    :&nbsp;${tada.deptDTO.deptName}
											</div>
										<div class="quarter leftmar">Stay Duration at Out Station in Days<span class="mandatory">*</span></div>
											<div class="quarter">
											    :&nbsp;<form:input path="stayDuration" id="stayDuration" maxlength="30" onkeypress="javascript:return checkInt(event);"/>
											</div>
									</div>
								
								    <div class="line">
										<div class="quarter leftmar" style="text-align: justify">Exact Place Of Work at TD station with postal address,name and phone no. of the person to be contacted<span class="mandatory">*</span></div>
											<div class="quarter">
											    :&nbsp;<form:textarea path="tdWorkPlace" id="tdWorkPlace" rows="4" cols="15"  onkeypress="textCounter(this,document.forms[0].counter2,500);" onkeyup="textCounter(this,document.forms[0].counter2,500);"/>
		                                        <input type="text" class="counter" name="counter" value="500" id="counter2" disabled=""/>
											</div>
										<div class="quarter leftmar">Purpose<span class="mandatory">*</span></div>
											<div class="quarter">
											    :&nbsp;<form:textarea path="purpose" id="purpose" rows="4" cols="15"  onkeypress="textCounter(this,document.forms[0].counter1,500);" onkeyup="textCounter(this,document.forms[0].counter1,500);"/>
		                                        <input type="text" class="counter" name="counter" value="500" id="counter1" disabled=""/>
											</div>
									</div>
									
									<div class="line">
										<div class="quarter leftmar">TD move is authorized for<span class="mandatory">*</span></div>
											<div class="quarter">
											    :&nbsp;<form:radiobutton path="authorizedMove" id="authorizedMove" value="1" onclick="javascript:showProjDirNames();"/><label>Build-Up</label>
											    <form:radiobutton path="authorizedMove" id="authorizedMove" value="2" onclick="javascript:showProjDirNames();"/><label>Project</label>
											</div>
										<div class="quarter leftmar">Wheather TA/DA is required with Hotel/Normal rates<span class="mandatory">*</span></div>
											<div class="quarter">
											    :&nbsp;<form:radiobutton path="tadaRequirement" id="tadaRequirement" value="hotelrate"/><label>Hotel Rates</label>
											    <form:radiobutton path="tadaRequirement" id="tadaRequirement" value="normalrate"/><label>Normal Rates</label>
											</div>
									</div>
									<div class="line" id="projDirNameDiv" style="display:none">
										<div class="quarter leftmar">Project Director<span class="mandatory">*</span></div>
											<div class="quarter">:&nbsp;
											<form:select path="projDirName" id="projDirName"  multiple="" cssStyle="width:145px;">
												<form:option value="Select" label="Select"></form:option>
												<form:options  items="${sessionScope.tadaProjDirNamesList}"  itemLabel="projectDirName" itemValue="sfID"></form:options>
									        </form:select>
											</div>
									</div>
									<fieldset><legend><strong><font color='green'>Journey Details</font></strong></legend>
									<div class="line">
								<table style="width:100%" border="1" class="list" id="requestJourneyDetailsId">
									
									<tr>
										<th style="width:10%;text-align:center">Departure Date</th>
										<th style="width:25%;text-align:center">From Place</th>
										<th style="width:25%;text-align:center">To Place</th>
										<th style="width:6%;text-align:center">No. Of Days</th>
										<th style="width:7%;text-align:center">Mode of conveyance</th>
										<th style="width:10%;text-align:center">Class of Entitlement</th>
										<th style="width:7%;text-align:center">Tatkal Quota</th>
										<th style="width:5%;text-align:center">Add</th>
										<th style="width:5%;text-align:center">Del</th>
									</tr>
									
									   <tr id="requestJourneyRow0">
											<td >
												<input type="text" readonly="readonly" id="journeyDate0" style="height:16px;width:70px;font-size: 9px;font-weight: bold;"  id="journeyDate0" onfocus ="javascript:Calendar.setup({inputField :'journeyDate0',ifFormat : '%d-%b-%Y',showsTime : false,button :'date_start_trigger',step : 1});" onchange="javascript:checkJourneyDate('journeyDate0');"/>
											</td>
									        <td >
									        <form:select path="fromPlace" id="fromPlace0"  multiple="" cssStyle="width:90px;" onchange="javascript:setToPlace(0);showOtherPlace(0);">
												<form:option value="Select" label="Select"></form:option>
												<form:options  items="${sessionScope.cityTypeList}"  itemLabel="cityName" itemValue="cityName"></form:options>
												<form:option value="Other" label="Other"></form:option>
									        </form:select>
									        <input type=text id=otherFromPlace0 size=11px style=display:none />
									        </td>
											<td >
											<form:select path="toPlace" id="toPlace0"  multiple="" cssStyle="width:90px;" onchange="javascript:showOtherPlace(0);">
												<form:option value="Select" label="Select"></form:option>
											</form:select>
									        <input type=text id=otherToPlace0 size=11px style=display:none />
											</td>
											<td ><input type="text" id="noOfDays0"  value=0 style="width:40px"/></td>
											<td >
											<form:select path="toPlace" id="modeOfTravel0"  multiple="" cssStyle="width:80px;" onchange="javascript:enableEntitleClasses(0);" >
												<form:option value="Select" label="Select"></form:option>
											</form:select>
											</td>
											<td >
											<form:select path="toPlace" id="classOfEntitlement0"  multiple="" cssStyle="width:100px;" >
												<form:option value="Select" label="Select"></form:option>
											</form:select>
											</td>
											<td >
											<form:select path="toPlace" id="tatkalQuota0"  multiple="" cssStyle="width:80px;" >
												<form:option value="na" label="N/A"></form:option>
												<form:option value="yes" label="Required"></form:option>
											</form:select>
											</td>
											<td ><input type="button" id="add0" value="+" onclick="javascript:checkRequestJourneyRow(0);"/></td>
											<td ><input type="button" id="del0" value="-" style="display: none" /></td>
											
										</tr>
									
									</table>		
								</div>
								</fieldset>	
								<div class="line">
								</div>	
									
									
									
	
									<div class="line" style="margin-left: 35%;">
											<div class="expbutton"><a onclick="javascript:submitTdApproval()"> <span>Submit</span></a></div>
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
		<form:hidden path="type"/>
		</form:form>	
		<script type="text/javascript">
		</script>
			
	</body>
</html>
<!-- End:TadaTdRequestCancel.jsp -->