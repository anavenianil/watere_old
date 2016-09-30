<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TreatmentRequestDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/ajaxUpload.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/cghs.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/date.js"></script>

<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>
<title>CGHS Request Form</title>
</head>

<body onload="javascript:setValues('${cghs.type}')" >

	<form:form method="post" commandName="cghs" id="cghs" name="cghrequest">
	
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
								<div class="headTitle">CGHS Request Form</div>
								<div>
									<%-- Content Page starts --%>
								<div id="result"></div>
									<div class="line" id="offlineSFIDDiv" style="display: none;">
										<div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
										<div class="half"><form:input path="offlineSFID" id="offlineSFID" onchange="javascript:getFamilyMembers();"/></div>
									</div>
								    <div class="line">
										<div class="quarter leftmar">Family Member Name<span class="mandatory">*</span></div>
										<div class="half" id="familyMembersList">
											<form:select path="familyMemberId" id="familyMemberId" onchange="javascript:displayFamilyMemberDetails();"  cssClass="formSelect required"  onmouseover="setSelectWidth('#familyMemberId')">
												<form:option value="">Select</form:option>
												<form:options items="${cghs.familyMemberList}" itemValue="id" itemLabel="name" id="cghscard" />
											</form:select>
										</div>									</div>
										<div class="line">


											<div class="quarter leftmar">As per Cghs Prescription Date<span class="mandatory">*</span></div>
											<div class="half">
												<form:input path="prescriptionDate" id="prescriptionDate" cssClass="dateClass" readonly="true" onchange="javascript:getRefHospitalList('${cghs.todaysDate}');" />&nbsp
												
												<script type="text/javascript">
											new tcal({'formname':'cghrequest','controlname':'prescriptionDate'});
											</script>
											</div>
									</div>
										
										
									
									<!-- <div class="line">
											<div class="quarter leftmar">Name Of the Disease<span class="mandatory">*</span></div>
											<div class="half">
											<form:input path="disease" id="disease"  value="As per Cghs Prescription Dt. "  onkeypress="javascript:increaseTextWidth('disease')"/></div>
									</div> -->
									
									<div class="line">
											<div class="quarter leftmar">Consultation / Investigation / Admission<span class="mandatory">*</span></div>
											<div class="half">
												<form:select path="cghsRequestTypeId" id="cghsRequestTypeId" cssClass="formSelect required" onchange="javascript:enableOption();">
													<form:option value="">Select</form:option>
													<form:options items="${cghs.requestTypeList}" itemValue="id" itemLabel="name"/>
												</form:select>
											</div>
									</div>
									<div class=line id="showsettlement" style="display:none">
									<div class="quarter leftmar">Select Settlement Type<span class="mandatory">*</span></div>
									<div class="half" style="margin-left:0%">
								         <div style="float:left;"><form:radiobutton value ="advance" path="settlement" id="settlement" onclick="javascript:changeButton();"/>Advance</div>
									     <div style="float:left;"><form:radiobutton value ="reimbursement" path="settlement" id="settlement" onclick="javascript:changeButton();"/>Reimbursement</div>
									     <div style="float:left"><form:radiobutton value ="permission" path="settlement" id="settlement" onclick="javascript:changeButton();"/> Permission</div>
																			
									</div>
									</div>
									
									
									<!-- <div class="line">
											<div class="quarter leftmar">Referred by Doctor<span class="mandatory">*</span></div>
											<div class="half"><form:input path="referredDoctor" id="referredDoctor" onkeypress="return checkChar(event);"/></div>
									</div> -->
									<div class="line">
											<div class="quarter leftmar">Hospital Name<span class="mandatory">*</span></div>
											<div class="quarter" id="refHospitalList">
												<form:select path="referralHospitalId" id="referralHospitalId" cssClass="formSelect required"  onmouseover="setSelectWidth('#referralHospitalId')">
													<form:option value="">Select</form:option>
												</form:select>
										   </div>
									</div>
									<!-- <div class="line">
											<div class="quarter leftmar">Specialist Doctor</div>
											<div class="half">
												<form:input path="referenceSpecialist" id="referenceSpecialist" onkeypress="return checkChar(event);"/>	
											</div>
									</div> -->
									
									
									
									<div class="line">

											<div class="quarter leftmar">Prescription Copy</div>
											<div class="half">
												<form:input path="prescriptionFile" type="file" id="files" />	
											</div>
									</div>
									<div class="line">
										<div style="margin-left:25%">
											<a href="javascript:manageTreatmentRequest();"><div class="appbutton" id="button" style="display:show">Submit</div></a>
											<a href="javascript:manageInvistigationRequest();"><div class="appbutton" id="button1" style="display:none">Next</div></a>
											<a href="javascript:clearTreatmentRequest();"><div class="appbutton">Clear</div></a>
										
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="requestId" />
		<form:hidden path="requestID"/>
		<form:hidden path="cghscardref"/>
		<form:hidden path="roleId"/>
		
		</form:form>
		<script>
			familyMember = <%= (net.sf.json.JSONArray)session.getAttribute("jsonfamilyMemberList") %>;
			
			if($jq('input:radio[name=settlement]:checked').val() == "advance"){
			  window.confirm('please Wait you will be redirected to advance Page');
			  }
			else if($jq('input:radio[name=settlement]:checked').val() == "reimbursement"){
			  window.confirm('please Wait you will be redirected to reimbursement Page');
			  }
	</script>
	

	</body>
</html>
<!-- End:TreatmentRequestDetails.jsp -->