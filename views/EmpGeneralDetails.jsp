<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:EmpGeneralDetails.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
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
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>

<title>Employee General Details</title>
</head>

<body>
	<form:form method="post" commandName="employee">
		<div class="header"></div>
		<div class="Innermaindiv">
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
								
								<%-- Content Page starts --%>
								<div class="headTitle">Create Employee</div>
								<fieldset><legend><strong><font color='green'>General Details</font></strong></legend>
									<div>
										<div class="line">
											<div class="quarter">SFID<span class="mandatory">*</span></div>
											<div class="quarter" id="sfid" style="text-align:left">${employee.userSfid}</div>
											<div class="quarter">Name In Service Book <span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="nameInServiceBook" id="nameInServiceBook" maxlength="200" cssClass="formSelect" onkeypress="return checkChar(event);"/></div>
										</div>
										<div class="line">
											<div class="quarter">Title<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="title" id="title" cssClass="formSelect"  >
													<form:option value="Select">Select</form:option>
               	      								<form:options items="${sessionScope.titleList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											</div>
											<div class="quarter">First Name<span class="mandatory">(Base Name)*</span></div>
											<div class="quarter"><form:input path="firstName" id="firstName" maxlength="50" size="100" cssClass="formSelect" onkeypress="return checkChar(event);"/></div>
										</div>
										<div class="line">
											<div class="quarter">Middle Name</div>
											<div class="quarter"><form:input path="middleName" id="middleName" maxlength="50" cssClass="formSelect" onkeypress="return checkChar(event);"/></div>
											<div class="quarter">Last Name<span class="mandatory">(Sur Name)*</span></div>
											<div class="quarter"><form:input path="lastName" id="lastName" maxlength="50" cssClass="formSelect" onkeypress="return checkChar(event);"/></div>
										</div>
										<div class="line">
											<div class="quarter">Father/Spouse</div>
											<div class="quarter"><form:select path="relationId" id="relationId" onchange="javascript:FatherOrSpouse();"  >
													<form:option value="0">Select</form:option>
               	      								<form:option value="25">Father</form:option>
               	      								<form:option value="26">Spouse</form:option>
               	   								</form:select><form:select path="relationTitle" id="fatherSpouseTitle"  >
													<form:option value="0">Select</form:option>
               	      								<form:options items="${sessionScope.titleList}" itemValue="id" itemLabel="name"/>
               	   								</form:select></div>
               	   							<div class="quarter" id="name">Name</div>
											<div class="quarter"><form:input path="relationName" id="relationName" maxlength="50" cssClass="formSelect" disabled="true" onkeypress="return checkChar(event);"/></div>
										</div>
										<div class="line">
											<div class="quarter">Marital Status<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="maritalId" id="maritalId" cssClass="formSelect" >
													<form:option value="Select">Select</form:option>
               	      								<form:options items="${sessionScope.maritalList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											</div>
											<div class="quarter">Religion<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="religion" id="religion" cssClass="formSelect" >
													<form:option value="Select">Select</form:option>
               	      								<form:options items="${sessionScope.ReligionList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											
											</div>
										
										</div>
										<div class="line">
											<div class="quarter">Community<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="communityId" id="communityId" cssClass="formSelect" >
													<form:option value="Select">Select</form:option>
               	      								<form:options items="${sessionScope.communityList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											</div>
											<div class="quarter">Nationality<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="nationality" id="nationalityId" cssClass="formSelect" >
													<form:option value="Select">Select</form:option>
               	      								<form:options items="${sessionScope.NationalityList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											</div>
											
											
										</div>
										<div class="line">
											<div class="quarter">Blood Group</div>
											<div class="quarter"><form:select path="blood" id="blood" cssClass="formSelect" >
																	<form:option value="Select">Select</form:option>
               	      												<form:options items="${sessionScope.bloodList}" itemValue="id" itemLabel="name"/>
               	   												</form:select></div>
											<div class="quarter">Residence Number</div>
											<div class="quarter"><form:input path="residenceNo" id="residenceNo" maxlength="15" cssClass="formSelect" onkeypress="return checkInt(event);"/></div>
											
										</div>
										<div class="line">
											<div class="quarter">Mobile Number<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="personalNumber" id="personalNumber" maxlength="15" cssClass="formSelect" onkeypress="return checkInt(event);"/></div>
											<div class="quarter">Internal Number<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="internalNo" id="internalNo" maxlength="10" cssClass="formSelect" onkeypress="return checkInt(event);"/></div>
											
										</div>
										<div class="line">
												<div class="quarter">Mother Tongue<span class="mandatory">*</span></div>
												<div class="quarter"><form:input path="motherTongue" id="motherTongue" maxlength="15" cssClass="formSelect" onkeypress="return checkChar(event); "/></div>
												<div class="quarter">Height<span class="mandatory">*</span></div>
												<div class="quarter"><form:input path="height" id="height" maxlength="4" cssClass="formSelect" onkeypress="return checkFloat(event,'height');" onblur="javascript:checkDecimals('height');"/>
													<form:select path="heightType" id="heightType"  >
														<form:option value="Select">Select</form:option>
               	      									<form:option value="1">Cms</form:option>
               	      									<form:option value="2">Inches</form:option>
               	   								</form:select>
               	   								</div>
									
										</div>
										<div class="line">
											<div class="quarter">Date of Birth<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="dob" id="dob" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('dob');"/>&nbsp;
												<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
												<script type="text/javascript">
												Calendar.setup({inputField :"dob",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
												</script>
											</div>
											<div class="quarter">Gender<span class="mandatory">*</span></div>
											<div class="quarter"><form:radiobutton path="gender" label="Male" value="M" id="male"/>
																<form:radiobutton path="gender" label="Female" value="F" id="female"/></div>
										</div>
										<div class="line">
												<div class="quarter">ID Marks</div>
												<div><form:textarea path="idMarks" id="idMarks" rows="4" cols="30" onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500); "/>
													<input type="text" class="counter1" name="counter" value="500" id="counter" disabled=""/>
												</div>
										</div>
									</div>
								</fieldset>
								
								<%-- 
								
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
	               	      								<form:options items="${directorateList}" itemValue="id" itemLabel="name"/> 
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
								</fieldset> --%>
								
								
								
								
								
								
								
								<c:if test="${edit != 'edit'}">
									<div class="line">
										<div style="padding-left: 45%;padding-top: 1%;"><a href="javascript:nextProfessional();" class="appbutton" style="text-decoration: none">Next</a></div>
									    <div><a href="javascript:resetGeneral();" class="appbutton" style="text-decoration: none">Clear</a></div>
									</div>
								</c:if>
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
			<div><jsp:include page="Footer.jsp" /></div>	
			</div>		
		<form:hidden path="param"/>
		<form:hidden path="userSfid"/>
		<form:hidden path="type"/>
		<form:hidden path="titleName" id="titleName"/>
		<form:hidden path="communityName" id="communityName"/>
		<form:hidden path="religionName" id="religionName"/>
		<form:hidden path="maritalName" id="maritalName"/>
		<form:hidden path="nationalityName" id="nationalityName"/>
		<form:hidden path="bloodName" id="bloodName"/>
		<form:hidden path="storeHight" id="storeHight"/>
		
		
	<script type="text/javascript">
	var checkDate='${employee.checkDate}';
	FatherOrSpouse();
	document.getElementById('storeHight').value="";
			</script>
		</form:form>
	</body>
	
</html>
<!-- End:EmpGeneralDetails.jsp -->
