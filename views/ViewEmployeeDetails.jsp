<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ViewEmployeeDetails.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring-form" prefix="form" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/aa.js"></script>
<script type="text/javascript" src="./script/RegExpValidate.js"></script>
<script type="text/javascript" src="./script/script.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>

<c:if test="${message eq 'editEmployee'}">
	<title>Edit Employee Details</title>													
</c:if>
<c:if test="${message ne 'editEmployee'}">
	<title>View Employee Details</title>													
</c:if>


</head>

<body>
	<form:form method="post">
	
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
								<c:if test="${command.message eq 'Invalid'}"><span class="mandatory"><spring:message code="Invalid"/></span></c:if>
									
								<c:if test="${message eq 'editEmployee'}">
								<div class="headTitle">Update Employee Details</div>
								<div class="line">
										<div class="quarter bold" id="labelType" style="margin-left:11px;">EmployeeID</div>
										<div class="quarter"><form:input path="userSfid" id="userSfid" maxlength="15" onkeypress="funOnKeyPress(event,'editEmployee')"/></div>
										<div class="appbutton"><a href="javascript:getEmployeeDetails();" style="text-decoration: none";>Search</a></div>
								</div>
								
								</c:if>
								<c:if test="${message ne 'editEmployee'}">
								<div class="headTitle">View Employee Profile</div>
								</c:if>
								<%-- Content Page starts --%>
								<c:if test="${editMessage=='mandatory'}"> <span class="mandatory"><spring:message code="mandatory"/></span></c:if>
								<c:if test="${editMessage=='success'}"> <span class="success">&nbsp;&nbsp;<spring:message code="success"/></span></c:if>
								<fieldset><legend><strong><font color='green'>General Details</font></strong></legend>
									
										<div class="line">
											<div class="quarter" > EmployeeID</div>
											<div class="quarter" id="sfid" style="text-align:left">
												<form:input path="sfid" id="sfid" readonly="true" disabled="true"/>
											</div>
											<div class="quarter">Name In Service Book<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="nameInServiceBook" id="nameInServiceBook" readonly="true" maxlength="200"/></div>
										</div>
										<div class="line">
											<div class="quarter">Title<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="title" id="title" cssClass="formSelect"  disabled="true">
													<form:option value="Select">Select</form:option>
               	      								<form:options items="${sessionScope.titleList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											</div>
											<div class="quarter">First Name<span class="mandatory">(Base Name)*</span></div>
											<div class="quarter"><form:input path="firstName" id="firstName" maxlength="50" disabled="true" onkeypress="javascript:return checkSpecialChar(event)" onkeyup="javascript:fullName()"/></div>
										</div>
										
										
										<div class="line">
											<div class="quarter">Middle Name</div>
											<div class="quarter"><form:input path="middleName" id="middleName" maxlength="50" disabled="true" onkeypress="javascript:return checkSpecialChar(event)" onkeyup="javascript:fullName()"/></div>
											<div class="quarter">Last Name<span class="mandatory">(Sur Name)*</span></div>
											<div class="quarter"><form:input path="lastName" id="lastName" maxlength="50" disabled="true" onkeypress="javascript:return checkSpecialChar(event)" onkeyup="javascript:fullName()"/></div>
										</div>
										<div class="line">
											<div class="quarter">Father/Spouse</div>
											<div class="quarter"><form:select path="relationId" id="relationId"  onchange="javascript:FatherOrSpouse();">
													<form:option value="0">Select</form:option>
               	      								<form:option value="25">Father</form:option>
               	      								<form:option value="26">Spouse</form:option>
               	   								</form:select><form:select path="relationTitle" id="fatherSpouseTitle"  >
													<form:option value="0">Select</form:option>
               	      								<form:options items="${sessionScope.titleList}" itemValue="id" itemLabel="name"/>
               	   								</form:select></div>
               	   							<div class="quarter" id="name">Name</div>
											<div class="quarter"><form:input path="relationName" id="relationName" maxlength="50" cssClass="formSelect" disabled="true"/></div>
										</div>
										
										<div class="line">
											<div class="quarter">Date of Birth<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="dob" id="dob" cssClass="dateClass" readonly="true" disabled="true" onclick="javascript:clearDateText('dob');"/>&nbsp;
												<img  src="./images/calendar.gif"   id="date_start_trigger7" />	
												<script type="text/javascript">
												Calendar.setup({inputField :"dob",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger7",singleClick : true,step : 1});
												</script>
											</div>
											<div class="quarter">Gender<span class="mandatory">*</span></div>
											<div class="quarter"><form:radiobutton path="gender" label="Male" value="M" id="male" disabled="true"/>
																<form:radiobutton path="gender" label="Female" value="F" id="female" disabled="true"/></div>
										</div>
										
										
										<div class="line">
											<div class="quarter">Marital Status<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="maritalId" id="maritalId"  cssClass="formSelect" disabled="true">
													<form:option value="Select">Select</form:option>
               	      								<form:options items="${sessionScope.maritalList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											</div>
										<!-- 	<div class="quarter">Religion<span class="mandatory">*</span></div> -->
											<div class="quarter">State<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="religion" id="religion"  cssClass="formSelect"  disabled="true">
													<form:option value="Select">Select</form:option>
               	      								<form:options items="${sessionScope.ReligionList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											
											</div>
										
										</div>
										<div class="line">
												<div class="quarter" hidden>Community<span class="mandatory">*</span></div>
											<div class="quarter" hidden>
												<form:select path="communityId" id="communityId"  cssClass="formSelect" disabled="true">
													<form:option value="Select">Select</form:option>
               	      								<form:options items="${sessionScope.communityList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											</div>
											<div class="quarter">Nationality<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="nationality" id="nationalityId" cssClass="formSelect"  disabled="true">
													<form:option value="Select">Select</form:option>
               	      								<form:options items="${sessionScope.NationalityList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											</div>
											
											
										</div>
											<div class="line">
											<div class="quarter">Blood Group<span class="mandatory"></span></div>
											<div class="quarter"><form:select path="blood" id="blood" cssClass="formSelect" >
																	<form:option value="Select">Select</form:option>
               	      												<form:options items="${sessionScope.bloodList}" itemValue="id" itemLabel="name"/>
               	   												</form:select></div>
											<div class="quarter">National Id</div>
											<div class="quarter"><form:input path="residenceNo" id="residenceNo" maxlength="15"/></div>
											
										</div>
										
										<div class="line">
											<div class="quarter">Mobile Number<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="personalNumber" id="personalNumber" maxlength="15" cssClass="formSelect" onkeypress="return checkInt(event);"/></div>
											<div class="quarter">Internal Number<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="internalNo" id="internalNo" maxlength="10" disabled="true" onkeypress="return checkInt(event);"/></div>
										</div>
										<div class="line">
												<div class="quarter">Mother Tongue<span class="mandatory">*</span></div>
												<div class="quarter"><form:input path="motherTongue" id="motherTongue" maxlength="15" cssClass="formSelect" onkeypress="return checkChar(event);"/></div>
												<div class="quarter" hidden>Height<span class="mandatory"></span></div>
												<div class="quarter" hidden><form:input path="height" id="height" maxlength="4" cssClass="formSelect" onkeypress="return checkFloat(event,'height');" onblur="javascript:checkDecimals('height');"/>
													<form:select path="heightType" id="heightType"  >
														<form:option value="Select">Select</form:option>
               	      									<form:option value="1">Cms</form:option>
               	      									<form:option value="2">Inches</form:option>
               	   								</form:select>
               	   								</div>
												
										</div>
										<div class="line">
												<div class="quarter">ID Marks<span class="mandatory">*</span></div>
												<div ><form:textarea path="idMarks" id="idMarks" disabled="true" rows="5" cols="30" onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"/>
												<input type="text" class="counter1" name="counter" value="500" id="counter" disabled="disabled"/>
												</div>
										</div>
								</fieldset>
								
								<fieldset><legend><strong><font color='green'>Professional Details</font></strong></legend>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle"/>&nbsp;Loading...</div>
									<div>
										
											<div class="line">
												<div class="quarter">Appointment Type<span class="mandatory"></span></div>
												<div class="quarter">
													<form:select path="appointmentTypeId" id="appointmentTypeId" cssClass="formSelect"  disabled="true">
														<form:option value="Select">Select</form:option>
	               	      								<form:options items="${sessionScope.appointmentTypeList}" itemValue="id" itemLabel="name"/>
	               	   								</form:select>
												</div>
												 <div class="quarter" hidden >Reporting to</div>
												<div class="quarter" id="selectedDivision" hidden>
													<form:select path="divisionId" id="divisionId" cssClass="formSelect" onmouseover="setSelectWidth('#divisionId')">
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
													<form:select path="employmentTypeId" id="employmentTypeId" cssClass="formSelect"  disabled="true">
														<form:option value="Select">Select</form:option>
	               	      								<form:options items="${sessionScope.employementTypeList}" itemValue="id" itemLabel="name"/>
	               	   								</form:select>
												</div>
												<div class="quarter" hidden>Department</div>
												<div class="quarter" hidden>
													<form:select path="directorateId" id="directorateId" cssClass="formSelect" onmouseover="setSelectWidth('#directorateId')" disabled="disabled" onchange="javascript:selectedDivisionList();">
														<form:option value="Select">Select</form:option>
	               	      								<%--<form:options items="${directorateList}" itemValue="id" itemLabel="name"/>  --%>
	               	      								<c:forEach items="${sessionScope.directorateList}" var="directorate">
															<form:option value="${directorate[0]}">${directorate[1]}</form:option>
														</c:forEach>
	               	   								</form:select>
												</div>
											</div>
											<div class="line" hidden>
												<div class="quarter">Designation<span class="mandatory">*</span></div>
												<c:choose>
												<c:when test="${command.roleId ne null && command.roleId=='1'}">
												<div class="quarter">
													<form:select path="designationId" id="designationId" cssClass="formSelect"  onmouseover="setSelectWidth('#designationId')"  >
														<form:option value="Select">Select</form:option>
	               	      								<form:options items="${sessionScope.designationList}" itemValue="id" itemLabel="name" />
	               	   								</form:select>
												</div>
												</c:when>
												
													<c:otherwise >
													<div class="quarter">
													<form:select path="designationId" id="designationId" cssClass="formSelect" disabled="true">
														<form:option value="Select">Select</form:option>
	               	      								<form:options items="${sessionScope.designationList}" itemValue="id" itemLabel="name" />
	               	   								</form:select>
												</div>
													</c:otherwise>
												</c:choose>
												
												<div class="quarter">Reservation Type</div>
												<div class="quarter">
														<form:select path="reservationId" id="reservationId" cssClass="formSelect"  onchange="javascritp:selectReservation();" disabled="true">
															<form:option value="Select">Select</form:option>
															<form:options items="${sessionScope.reservationList}" itemValue="id" itemLabel="name"/>
														</form:select>
												</div>
												
											</div>
											<div class="line" hidden>
												<div class="quarter">PH Type<span class="mandatory">*</span></div>
												<div class="quarter">
														<form:select path="handicapId" id="handicapId" cssClass="formSelect" onmouseover="setSelectWidth('#handicapId')" disabled="true">
															<form:option value="Select">Select</form:option>
															<form:options items="${sessionScope.handicapList}" itemValue="id" itemLabel="name"/>
														</form:select>
												</div>
												<div class="quarter">Whether Service Person</div>
													<div class="quarter">
														<form:select path="joinType" id="joinType"  cssClass="formSelect" onmouseover="setSelectWidth('#joinType')" disabled="true">
															<form:option value="Select">Select</form:option>
															<form:options items="${sessionScope.joinTypeList}" itemValue="id" itemLabel="name"/>
														</form:select>
													</div>
												</div>
											
												<div class="line" hidden>
													
													<div class="quarter" >Duration of Service</div>
													<div class="quarter"><form:input path="workedYears" id="workedYears" maxlength="50" disabled="true" onkeypress="javascript:return checkInt(event);"/></div>
													<div class="quarter" >Family Planning Allowances</div>
													<div class="quarter" ><form:radiobutton path="famPlanning" label="Yes" value="Yes" id="yesfamPlanning" disabled="true"/>
																		<form:radiobutton path="famPlanning" label="No" value="No" id="nofamPlanning" disabled="true"/>
													</div>
												</div>
												<div class="line" hidden>
													<div class="quarter">House Build Allowance</div>
													<div class="quarter"><form:radiobutton path="houseAllowence" label="Yes" value="Yes" id="yeshouseAllowence" disabled="true"/>
																		<form:radiobutton path="houseAllowence" label="No" value="No" id="nohouseAllowence" disabled="true"/></div>
													<div class="quarter">Group Insurance Allowance</div>
													<div class="quarter"><form:radiobutton path="groupAllowence" label="Yes" value="Yes" id="yesgroupAllowence" disabled="true" onchange="javascript:uptoDateAllowence();"/>
																		<form:radiobutton path="groupAllowence" label="No" value="No" id="nogroupAllowence" disabled="true" onchange="javascript:uptoDateAllowence();"/></div>
												</div>
												<div class="line" hidden>
													<div class="quarter">Group Insurance Expire Date</div>
													<div class="quarter"><form:input path="uptoDate" id="uptoDate" cssClass="dateClass" readonly="true" disabled="true" onclick="javascript:clearDateText('uptoDate');"/>&nbsp;
														<img  src="./images/calendar.gif"   id="date_start_trigger6" />	
														<script type="text/javascript">
															Calendar.setup({inputField :"uptoDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger6",singleClick : true,step : 1});
														</script>
													</div>
													<div class="quarter">Date of Join in Govt<span class="mandatory">*</span></div>
													<div class="quarter">
														<form:input path="dojGovt" id="dojGovt" cssClass="dateClass" readonly="true" onchange="javascript:displayInDRDO();" disabled="true" onclick="javascript:clearDateText('dojGovt');"/>&nbsp;
														<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
														<script type="text/javascript">
															Calendar.setup({inputField :"dojGovt",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
														</script>
													</div>
												</div>
												<div class="line">
													<div class="quarter" hidden>Date of Join in DRDO<span class="mandatory">*</span></div>
													<div class="quarter" hidden>
														<form:input path="dojDrdo" id="dojDrdo" cssClass="dateClass" readonly="true" disabled="true" onclick="javascript:clearDateText('dojDrdo');"/>&nbsp;
														
														<img  src="./images/calendar.gif"   id="date_start_trigger2" />	
														<script type="text/javascript">
															Calendar.setup({inputField :"dojDrdo",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
														</script>
													</div>
													<div class="quarter">Date of Join <span class="mandatory">*</span></div>
													<div class="quarter">
														<form:input path="dojAsl" id="dojAsl" cssClass="dateClass" readonly="true" disabled="true" onclick="javascript:clearDateText('dojAsl');"/>&nbsp;
														<img  src="./images/calendar.gif"   id="date_start_trigger3" />	
														<script type="text/javascript">
															Calendar.setup({inputField :"dojAsl",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
														</script>
													</div>
												
											</div>
											
											
											
											<div class="line" hidden>
												<div class="quarter">Present Rank Seniority Date<span class="mandatory">*</span></div>
												<div class="quarter">
													<c:choose>
													<c:when test="${command.roleId ne null && command.roleId=='1'}">
													<form:input path="seniorityDate" id="seniorityDate" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('seniorityDate');"/>&nbsp;
													<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
													<script type="text/javascript">
														Calendar.setup({inputField :"seniorityDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
													</script>
													</c:when>
													<c:otherwise>
													<form:input path="seniorityDate" id="seniorityDate" cssClass="dateClass" readonly="true" disabled="true" onclick="javascript:clearDateText('seniorityDate');"/>&nbsp;
													<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
													</c:otherwise>
													</c:choose>
													
												</div>
												<div class="quarter">Date of Promotion To The Present Rank<span class="mandatory">*</span></div>
												<div class="quarter">
												<c:choose>
												<c:when test="${command.roleId ne null && command.roleId=='1'}">
												<form:input path="lastPromotion" id="lastPromotion" cssClass="dateClass" readonly="true"   onclick="javascript:clearDateText('lastPromotion');"/>&nbsp;
												<img  src="./images/calendar.gif"   id="date_start_trigger5" />	
												<script type="text/javascript">
														Calendar.setup({inputField :"lastPromotion",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger5",singleClick : true,step : 1});
													</script>
												</c:when>
												<c:otherwise>
												<form:input path="lastPromotion" id="lastPromotion" cssClass="dateClass" readonly="true" disabled="true"  onclick="javascript:clearDateText('lastPromotion');"/>&nbsp;
												<img  src="./images/calendar.gif"   id="date_start_trigger5" />	
												</c:otherwise>
												
												</c:choose>
													
												</div>
											</div>
										</div>
								</fieldset>
								<fieldset hidden><legend><strong><font color='green'>Employee Other Details</font></strong></legend>
									<div>
										<div class="line">
												<div class="quarter">Dispensary No<span class="mandatory">*</span></div>
												<div class="quarter"><form:select path="dispersaryNumber" id="dispersaryNumber" cssClass="formSelect"  disabled="true">
															<form:option value="Select">Select</form:option>
               	      										<form:options items="${sessionScope.dispensaryList}" itemValue="id" itemLabel="dispensaryNumber"/>
               	   										</form:select></div>
												<div class="quarter">CGHS Card Number</div>
												<div class="quarter"><form:input path="cgshNumber" id="cgshNumber" maxlength="15" disabled="true"/></div>
										</div>
										<div class="line">
												<div class="quarter">PPA Card</div>
												<div class="quarter"><form:input path="ppaNo" id="ppaNo" maxlength="20" disabled="true"/></div>
												<div class="quarter">PRAN</div>
												<div class="quarter"><form:input path="pranNo" id="areaOfInterest" maxlength="25" disabled="true"/></div>
												
										</div>
										<div class="line">
												<div class="quarter">GPF A/C No<span class="mandatory">*</span></div>
												<div class="quarter"><form:input path="gpfAcNo" id="gpfAcNo" maxlength="25" disabled="true"/></div>
										</div>
									</div>
								</fieldset>
								
								<c:if test="${message eq 'editEmployee'}">
								<div class="line">
									<div><a href="javascript:saveEditEmployee();" class="quarterbutton"><div class="appbutton" style="float:right;">Update</div></a></div>
								</div>
									<script>
										enableEditEmployee();
										selectReservation();
										FatherOrSpouse();
										var checkDate='${command.checkDate}';
										if(!document.getElementById('yesgroupAllowence').checked==true)
											document.getElementById('nogroupAllowence').checked = true;
										if(!document.getElementById('yesfamPlanning').checked==true)
											document.getElementById('nofamPlanning').checked = true;
										if(!document.getElementById('yeshouseAllowence').checked==true)
											document.getElementById('nohouseAllowence').checked = true;
										document.getElementById('heightType').value=1;	
										document.getElementById('divisionId').disabled=true
										document.getElementById('directorateId').disabled=true
									</script>
								</c:if>
								<div class="line">
										<div id="checkHead"></div>
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
			<div><jsp:include page="Footer.jsp" /></div>
			</div>			
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="sfid"/>
		<form:hidden path="storeHight" id="storeHight"/>
		</form:form>
	</body>
</html>
<!-- End:ViewEmployeeDetails.jsp -->
