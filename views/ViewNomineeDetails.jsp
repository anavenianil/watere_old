<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin : ViewNomineeDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script language="javascript" src="script/prototype.js"></script>
<script language="javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>


</head>
<body>
		<div id="nomineeResult"></div>
		<fieldset><legend><strong><font color='green'>Nominee Details</font></strong></legend>
			<div class="line">
				<div class="quarter">SFID</div>
				<div class="quarter">${sfid}</div>
			</div>
			<div class="line">
				<div class="quarter">Nominee Type<span class="mandatory">*</span></div>
				<div class="quarter">
					<spring:bind path="nominee">
						<form:select path="nominee.nomineeTypeId"
							id="nomineeTypeId" cssClass="formSelect" onmouseover="setSelectWidth('#nomineeTypeId')">
							<form:option value="select">Select</form:option>
							<form:options items="${nomineeTypeList}" itemValue="id"
								itemLabel="name"></form:options>
						</form:select>
					</spring:bind>
				</div>
				<div class="quarter">Family Member<span class="mandatory">*</span></div>
				<div class="quarter">
					<spring:bind path="nominee">
						<form:select path="nominee.familyID"
							 id="familyID" onchange="displayAddress();" cssClass="formSelect" onmouseover="setSelectWidth('#familyID')">
							<form:option value="select">Select</form:option>
							<form:option value="other">Other</form:option>
							<form:options items="${familyMembersList}" itemValue="id"
								itemLabel="name"></form:options>
						</form:select>
					</spring:bind>
				</div>
			</div>
			<div id="other" style="display: none">
			<div class="line">
			<div class="quarter">Nominee<span class="mandatory">*</span></div>
			<div class="quarter">
				<spring:bind path="nominee">
					<form:input path="nominee.nomineeName"
						id="nomineeName" maxlength="100" />
				</spring:bind>
			</div>
			<div class="quarter">Address Line1</div>
			<div class="quarter">
				<spring:bind path="nominee">
					<form:input path="nominee.address1" id="address1"
						maxlength="100" />
				</spring:bind>
			</div>
			</div>
			<div class="line">
			<div class="quarter">Address Line2</div>
			<div class="quarter">
				<spring:bind path="nominee">
					<form:input path="nominee.address2" id="address2"
						maxlength="100" />
				</spring:bind>
			</div>
			<div class="quarter">Address Line3</div>
			<div class="quarter">
				<spring:bind path="nominee">
					<form:input path="nominee.address3" id="address3"
						maxlength="100" />
				</spring:bind>
			</div>
			
			</div>
			<div class="line">
			<div class="quarter">City</div>
			<div class="quarter">
				<spring:bind path="nominee">
					<form:input path="nominee.city" id="city"
						maxlength="100" />
				</spring:bind>
			</div>
			<div class="quarter">State</div>
			<div class="quarter">
				<spring:bind path="nominee">
					<form:select path="nominee.stateId"
						 id="stateId"
						onchange="javascript:getDistrictList('stateId','districtId')" cssClass="formSelect" onmouseover="setSelectWidth('#stateId')">
						<form:option value="select">Select</form:option>
						<form:options items="${nominee.stateList}" itemValue="id" itemLabel="name"></form:options>
					</form:select>
				</spring:bind>
			</div>
			
			</div>
			<div class="line">
			<div class="quarter">District</div>
			<div class="quarter">
				<spring:bind path="nominee">
					<form:select path="nominee.districtId"
						 id="districtId" cssClass="formSelect" onmouseover="setSelectWidth('#districtId')">
						<form:option value="select">Select</form:option>
					</form:select>
				</spring:bind>
			</div>
			<div class="quarter">Pin code</div>
			<div class="quarter">
				<spring:bind path="nominee">
					<form:input path="nominee.pincode" id="pincode"
						maxlength="6" />
				</spring:bind>
			</div>
			</div>
			
			</div>
			<div class="line">
			<div class="quarter">Percentage<span class="mandatory">*</span></div>
			<div class="quarter">
				<spring:bind path="nominee">
					<form:input path="nominee.percentage" id="percentage"
						maxlength="6"
						onkeypress="javascript:return checkFloat(event,'percentage');" />
				</spring:bind>
			</div>
			<div class="quarter">Date of Nominate</div>
			<div class="quarter">
				<spring:bind path="nominee">
					<form:input path="nominee.dateOfNominate"
					id="dateOfNominate" cssClass="dateClass" readonly="true" /> <img
					src="./images/calendar.gif" id="date_start_trigger4" />
					 <script type="text/javascript">
							Calendar.setup({inputField :"dateOfNominate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
					 </script>
				</spring:bind>
			</div>
			</div>
			<div class="line">
			<div class="quarter">Remarks</div>
			<div class="quarter">
				<spring:bind path="nominee">
					<form:textarea path="nominee.remarks" id="remarks" onkeypress="textCounter(this,document.forms[0].counter3,500);" onkeyup="textCounter(this,document.forms[0].counter3,500);" />
					<div class="line"><input type="text" class="counter" name="counter" value="500" id="counter3"/></div>
				</spring:bind>
			</div>
			</div>
			<div class="line">
				<table cellpadding="0" cellspacing="0" align="center" width="100%" id="incontengensy">
				
				</table>
			</div>
			<div class="line">
			<div style="margin-left: 69%"><a
				href="javascript:nomineeInconsistency();">
			<div class="appbutton">Incont</div>
			</a> <a href="javascript:submitNominee('employee');">
			<div class="appbutton">Submit</div>
			</a> <a href="javascript:clearNominee();">
			<div class="appbutton">Clear</div>
			</a></div>
			</div>
		</fieldset>
		
	
		<script>
			nomineeJSONList = <%= (net.sf.json.JSONArray)session.getAttribute("nomineeListJSON") %>;
			percentageList = <%= (net.sf.json.JSON)session.getAttribute("percentageList") %>;
			familyMemberInNominee = <%= (net.sf.json.JSON)session.getAttribute("familyMemberInNominee") %>;
			districtJSONList = <%= (net.sf.json.JSONArray)session.getAttribute("districtJSONList") %>;
			editNominee('${editNomineeId}');
			
		</script>
	</body>
</html>
<!-- End : ViewNomineeDetails.jsp -->