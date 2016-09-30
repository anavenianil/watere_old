<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:EmpProfessionalDetails.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/aa.js"></script>
<script type="text/javascript" src="./script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="./script/script.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>

<title>Employee Professional Details</title>
</head>

<body>
	<form:form method="post" commandName="employee">
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
								<div style="padding: 10px 0 0 0;"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								
								<%-- Content Page starts --%>
								<div class="headTitle">Create Employee</div>
								<fieldset><legend><strong><font color='green'>Professional Details</font></strong></legend>
									<div>
											<div class="line">
												<div class="quarter">Appointment Type<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:select path="appointmentTypeId" id="appointmentTypeId" cssClass="formSelect">
														<form:option value="Select">Select</form:option>
	               	      								<form:options items="${sessionScope.appointmentTypeList}" itemValue="id" itemLabel="name"/>
	               	   								</form:select>
												</div>
												<div class="quarter">Reporting to</div>
												<div class="quarter" id="selectedDivision">
													<form:select path="divisionId" id="divisionId" cssClass="formSelect" disabled="true">
														<form:option value="Select">Select</form:option>
	               	      								<c:forEach items="${sessionScope.divisionList}" var="division">
															<form:option value="${division[0]}">${division[1]}</form:option>
														</c:forEach>
	               	   								</form:select>
												</div>
											</div>
											<div class="line">
											<div class="quarter">Employment Type<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:select path="employmentTypeId" id="employmentTypeId" cssClass="formSelect">
														<form:option value="Select">Select</form:option>
	               	      								<form:options items="${sessionScope.employementTypeList}" itemValue="id" itemLabel="name"/>
	               	   								</form:select>
												</div>
												<div class="quarter">Department<span class="mandatory">*</span></div>
												<div class="quarter" >
													<form:select path="directorateId" id="directorateId" cssClass="formSelect" onchange="javascript:selectedDivisionList();">
														<form:option value="Select">Select</form:option>
	               	      								<%--<form:options items="${directorateList}" itemValue="id" itemLabel="name"/>  --%>
	               	      								<c:forEach items="${sessionScope.directorateList}" var="directorate">
															<form:option value="${directorate[0]}">${directorate[1]}</form:option>
														</c:forEach>
	               	   								</form:select>
												</div>
											</div>
											<div class="line">
												<div class="quarter">Designation<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:select path="designationId" id="designationId" cssClass="formSelect">
														<form:option value="Select">Select</form:option>
	               	      								<form:options items="${sessionScope.designationList}" itemValue="id" itemLabel="name"/>
	               	   								</form:select>
												</div>
												<div class="quarter">Reservation Type</div>
												<div class="quarter"><!-- Reservation Type -->
														<form:select path="reservationId" id="reservationId" cssClass="formSelect" onchange="javascritp:selectReservation();">
															<form:option value="Select">Select</form:option>
															<form:options items="${sessionScope.reservationList}" itemValue="id" itemLabel="name"/>
														</form:select>
												</div>
											</div>
											<div class="line">
												<div class="quarter">PH Type</div>
												<div class="quarter"><!-- Handicap Type -->
														<form:select path="handicapId" id="handicapId" cssClass="formSelect" disabled="true">
															<form:option value="Select">Select</form:option>
															<form:options items="${sessionScope.handicapList}" itemValue="id" itemLabel="name"/>
														</form:select>
												</div>
												<div class="quarter">Whether Service Person</div>
													<div class="quarter"><!-- Join Type -->
														<form:select path="joinType" id="joinType" cssClass="formSelect" disabled="true">
															<form:option value="Select">Select</form:option>
															<form:options items="${sessionScope.joinTypeList}" itemValue="id" itemLabel="name"/>
															
														</form:select>
													</div>
												</div>
												<div class="line">
													
													<div class="quarter">Duration of Service</div>
													<div class="quarter"><form:input path="workedYears" id="workedYears" maxlength="50" disabled="true" cssClass="formSelect"/></div>
													<div class="quarter">Family Planning Allowances</div>
													<div class="quarter"><form:radiobutton path="famPlanning" label="Yes" value="Yes" id="yesfamPlanning"/>
																		<form:radiobutton path="famPlanning" label="No" value="No" id="nofamPlanning" />
													</div>
												</div>
												<div class="line">
													<div class="quarter">House Build Allowance</div>
													<div class="quarter"><form:radiobutton path="houseAllowence" label="Yes" value="Yes" id="yeshouseAllowence"/>
																		<form:radiobutton path="houseAllowence" label="No" value="No" id="nohouseAllowence"/></div>
													<div class="quarter">Group Insurance Allowance</div>
													<div class="quarter"><form:radiobutton path="groupAllowence" label="Yes" value="Yes" id="yesgroupAllowence" onchange="javascript:uptoDateAllowence();"/>
																		<form:radiobutton path="groupAllowence" label="No" value="No" id="nogroupAllowence" onchange="javascript:uptoDateAllowence();"/></div>
												</div>
												
												<div class="line">
													<div class="quarter">Group Insurance Expire Date</div>
													<div class="quarter"><form:input path="uptoDate" id="uptoDate" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('uptoDate');"/>
														<img  src="./images/calendar.gif"   id="date_start_trigger6" />	
														<script type="text/javascript">
															Calendar.setup({inputField :"uptoDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger6",singleClick : true,step : 1});
														</script>
													</div>
													<div class="quarter">Date of Join in Govt<span class="mandatory">*</span></div>
													<div class="quarter">
														<form:input path="dojGovt" id="dojGovt" cssClass="dateClass" readonly="true" onchange="javascript:displayInDRDO();" onclick="javascript:clearDateText('dojGovt');"/>
														<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
														<script type="text/javascript">
															Calendar.setup({inputField :"dojGovt",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
														</script>
													</div>
												</div>
											<div class="line">
												<div class="quarter">Date of Join in DRDO<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="dojDrdo" id="dojDrdo" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('dojDrdo');"/>
													
													<img  src="./images/calendar.gif"   id="date_start_trigger2" />	
													<script type="text/javascript">
														Calendar.setup({inputField :"dojDrdo",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
													</script>
												</div>
												<div class="quarter">Date of Join in ASL<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="dojAsl" id="dojAsl" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('dojAsl');"/>
													<img  src="./images/calendar.gif"   id="date_start_trigger3" />	
													<script type="text/javascript">
														Calendar.setup({inputField :"dojAsl",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
													</script>
												</div>
												
											</div>
											<div class="line">
												<div class="quarter">Present Rank Seniority Date<span class="mandatory">*</span></div>
												<div class="quarter"><form:input path="seniorityDate" id="seniorityDate" cssClass="dateClass" readonly="true" onchange="javascript:displayInPromotion();" onclick="javascript:clearDateText('seniorityDate');"/>
													<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
													<script type="text/javascript">
														Calendar.setup({inputField :"seniorityDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
													</script>
												</div>
												<div class="quarter">Date Of Promotion To The Present Rank1<span class="mandatory">*</span></div>
												<div class="quarter"><form:input path="lastPromotion" id="lastPromotion" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('lastPromotion');"/>
												<img  src="./images/calendar.gif"   id="date_start_trigger5" />	
													<script type="text/javascript">
														Calendar.setup({inputField :"lastPromotion",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger5",singleClick : true,step : 1});
													</script>
												</div>
											</div>
											<div class="line">
												<div id="checkHead"></div>
											</div>
										</div>	
								</fieldset>
								<div class="line">
									<div style="padding-left: 45%;padding-top: 1%;"><a href="javascript:backToGeneral();" class="appbutton" style="text-decoration: none;">Back</a></div>
									<div><a href="javascript:nextOther();" class="appbutton" style="text-decoration: none;">Next</a></div>
									<div><a href="javascript:resetProfessional();"  class="appbutton" style="text-decoration: none;">Clear</a></div>
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
		
		<form:hidden path="type"/>
		<form:hidden path="param"/>
		
		<form:hidden path="appName"/>
		<form:hidden path="empName"/>
		<form:hidden path="divisionName"/>
		<form:hidden path="directorateName"/>
		<form:hidden path="designationName"/>
		<form:hidden path="reservationName"/>
		<form:hidden path="handicapName"/>
		<form:hidden path="joinName"/>
		
		
		
		<script>
		uptoDateAllowence();
		selectReservation();
		if(!document.getElementById('yesgroupAllowence').checked==true)
		document.getElementById('nogroupAllowence').checked = true;
		if(!document.getElementById('yesfamPlanning').checked==true)
		document.getElementById('nofamPlanning').checked = true;
		if(!document.getElementById('yeshouseAllowence').checked==true)
		document.getElementById('nohouseAllowence').checked = true;
		var check='${employee.dob}';
		</script>
		</form:form>
	</body>
</html>
<!-- end:EmpProfessionalDetails.jsp -->
