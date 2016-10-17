<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:OuterEmployeeDetails.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>

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
<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="./script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="./script/script.js"></script>
<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>

<title> Create Employee Details</title>

</head>

<body onload="javascript:resetOtherEmployee()">
	<form:form method="post" commandName="outside" id="outside" name="outside">
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
							<!-- 	<div class="headTitle">Create Outside Employee</div> -->
								<div class="headTitle">Create New Employee</div>
								<div id="result"></div>
								<fieldset><legend><strong><font color='green'>Employee Details</font></strong></legend>
								
									<div>
										<div class="line">
											<div class="quarter">Employee ID :  <!-- <span class="mandatory">*</span> --></div>	 	
											<%-- <div class="quarter">${sessionScope.userSfid}</div> --%>
											<%-- <div class="quarter" id="userSfid" style="text-align:left">${sessionScope.userSfid}</div> --%>
											<div class="quarter">
											<form:select path="userSfid" id="userSfid" cssClass="formSelect"  >
													<form:option value="${sessionScope.userSfid}" disabled="disabled">${sessionScope.userSfid}</form:option>
               	   								</form:select>
											
											</div>
											
											<%-- <div class="quarter"><form:input path="userSfid" id="userSfid" maxlength="15" cssClass="formSelect" onkeypress="return checkSpecialChar(event);"/></div> --%>											
											<!-- <div class="quarter">Name In Service Book<span class="mandatory">*</span></div> -->
									<%-- 		<div class="quarter">Full Name<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="nameInServiceBook" id="nameInServiceBook" maxlength="200" cssClass="formSelect" onkeypress="return checkChar(event);" readonly="true"/></div>
										 --%></div>
										<div class="line">
									
											<div class="quarter">Title<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="title" id="title" cssClass="formSelect"  >
													<form:option value="Select">Select</form:option>
               	      								<form:options items="${sessionScope.titleList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											</div>
											<div class="quarter">First Name<span class="mandatory">(Base Name)*</span></div>
											<div class="quarter"><form:input path="firstName" id="firstName" maxlength="50" size="100" cssClass="formSelect" onkeypress="return checkChar(event);"  onkeyup="javascript:fullName()"/></div>
										</div>
										<div class="line">
											<div class="quarter">Middle Name</div>
											<div class="quarter"><form:input path="middleName" id="middleName" maxlength="50" cssClass="formSelect" onkeypress="return checkChar(event);"    onkeyup="javascript:fullName()" /></div>
											<div class="quarter">Last Name<span class="mandatory">(Sur Name)*</span></div>
											<div class="quarter"><form:input path="lastName" id="lastName" maxlength="50" cssClass="formSelect" onkeypress="return checkChar(event);"    onkeyup="javascript:fullName()" /></div>
										</div>
										<div class="line">
													<div class="quarter">Full Name<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="nameInServiceBook" id="nameInServiceBook" maxlength="200" cssClass="formSelect" onkeypress="return checkChar(event);" readonly="true"/></div>
										
										<div class="quarter">Blood Group</div>
											<div class="quarter">
												<form:select path="bloodGroupId" id="bloodGroupId" cssClass="formSelect" >
													<form:option value="Select">Select</form:option>
               	      								<form:options items="${sessionScope.bloodList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
               	   							</div>
               	   							<!-- hidden by bkr 16/05/2016 -->
											<div class="quarter" style="display: none">PH Type</div>
											<div class="quarter" style="display: none"><!-- Handicap Type -->
													<form:select path="handicap" id="handicap" cssClass="formSelect">
														<form:option value="Select">Select</form:option>
														<form:options items="${sessionScope.handicapList}" itemValue="id" itemLabel="name"/>
													</form:select>
											</div>
										</div>	
										
										<!-- commented by bkr 29/03/2016 -->
										<div class="line"  style="display: none;">
											<div class="quarter">Dispensary No</div>
											 <div class="quarter">
												<form:select path="dispensaryNumberId" id="dispensaryNumberId" cssClass="formSelect" onmouseover="setSelectWidth('#dispersaryNumber')" >
													<%-- <form:option value="Select">Select</form:option> --%>
													<form:option value="429">I</form:option><!-- bkr change -->
	               	      							<form:options items="${sessionScope.dispensaryList}" itemValue="id" itemLabel="dispensaryNumber"/>
	               	   							</form:select></div> 
											<div class="quarter">PRA Number<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="pranNo" id="pranNo" maxlength="25" cssClass="formSelect"/></div>
										</div>
										
										
										<div class="line" >
										<div class="quarter"  style="display: none;">GPF Number<span class="mandatory"></span></div>
											<div class="quarter"  style="display: none;"><form:input path="gpfAcNo" id="gpfAcNo" maxlength="50" cssClass="formSelect" onkeypress="return isAlphaNumaricExp(event);"/></div>
											<div class="quarter">Gender<span class="mandatory">*</span></div>
											<div class="quarter"><form:radiobutton path="gender" label="Male" value="M" id="male"/>
																 <form:radiobutton path="gender" label="Female" value="F" id="female"/></div>
										
										<div class="quarter">Designation<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="designationId" id="designationId" cssClass="formSelect" onmouseover="setSelectWidth('#designationId')">
												<form:option value="Select">Select</form:option>
	               	      						<form:options items="${sessionScope.designationList}" itemValue="id" itemLabel="name"/>
	               	   							</form:select>
											</div>	
											</div>										
									    <div class="line">
									    
									    
									      <!-- commented by bkr 29/03/2016 -->
									   			<div class="quarter" style="display: none;">Working Organisation<span class="mandatory">*</span></div>	
											      <div class="quarter" style="display: none;">
												       <form:select path="workingLocation" id="workingLocation" cssClass="formSelect" onchange="javascript:hideASL();">
												       <%-- <form:option value="Select">Select</form:option> --%>
												       <form:option value="1">ASL</form:option>
               	      							       <form:options items="${sessionScope.organizationsList}" itemValue="id" itemLabel="name"/>
               	   								       </form:select>
											       </div> 
											       
											       
											       
											  <%--      
												<div class="quarter">Reporting to</div>
												<div class="quarter" id="selectedDivision">
												<form:select path="divisionId" id="divisionId" cssClass="formSelect" disabled="true">
												<form:select path="divisionId" id="divisionId" cssClass="formSelect" >
													<form:option value="Select">Select</form:option>
	               	      					    	<c:forEach items="${sessionScope.divisionList}" var="division">
													<form:option value="${division[0]}">${division[1]}</form:option>
													</c:forEach>
	               	   							</form:select>
											</div> --%>
										</div>
										<div class="line">
										<%-- <div class="quarter">Designation<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="designationId" id="designationId" cssClass="formSelect" onmouseover="setSelectWidth('#designationId')">
												<form:option value="Select">Select</form:option>
	               	      						<form:options items="${sessionScope.designationList}" itemValue="id" itemLabel="name"/>
	               	   							</form:select>
											</div> --%>
										<div class="quarter">Department<span class="mandatory">*</span></div>
											<div class="quarter" >
												<form:select path="directorateId" id="directorateId" cssClass="formSelect" onchange="javascript:selectedDivisionList();" onmouseover="setSelectWidth('#directorateId')" >
												<form:option value="Select">Select</form:option>
	               	      							<c:forEach items="${sessionScope.directorateList}" var="directorate">
													<form:option value="${directorate[0]}">${directorate[1]}</form:option>
													</c:forEach>
	               	   							</form:select>
											</div>
											<!--commented by bkr 26/05/2016 add again  -->
											<div class="quarter" style="display: none">Reporting to</div>
												<div class="quarter" id="selectedDivision"  style="display: none">
												<%-- <form:select path="divisionId" id="divisionId" cssClass="formSelect" disabled="true"> --%>
												<form:select path="divisionId" id="divisionId" cssClass="formSelect" >
													<form:option value="Select">Select</form:option>
	               	      					    	<c:forEach items="${sessionScope.divisionList}" var="division">
													<form:option value="${division[0]}">${division[1]}</form:option>
													</c:forEach>
	               	   							</form:select>
											</div>
											
											
										</div>
									   <div class="line">	
											<div class="quarter">Personal Number<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="personalNumber" id="personalNumber" maxlength="15" cssClass="formSelect" onkeypress="return checkInt(event);"/></div>										
											<div class="quarter">Internal Number<span class="mandatory"></span></div>
											<div class="quarter"><form:input path="internalNo" id="internalNo" maxlength="10" cssClass="formSelect" onkeypress="return checkInt(event);"/></div>
											
										</div>
										<div class="line">									
											<!-- <div class="quarter">Residence Number</div> -->
											<div class="quarter"> National Id</div>
											<div class="quarter"><form:input path="residenceNo" id="residenceNo" maxlength="15" cssClass="formSelect" /></div>
											<div class="quarter">Nationality<span class="mandatory">*</span></div>
											      <div class="quarter">
												       <form:select path="nationalityId" id="nationalityId" cssClass="formSelect" >
												       <form:option value="Select">Select</form:option>
               	      							       <form:options items="${sessionScope.NationalityList}" itemValue="id" itemLabel="name"/>
               	   								       </form:select>
											       </div>
									    </div>
									      	<div class="line">	
									      	  <!-- commented by bkr 29/03/2016 -->	
											 <div class="quarter" style="display: none;">Date of Join in DRDO<span class="mandatory">*</span></div>
											<div class="quarter" style="display: none;">
												<form:input path="dojDrdo" id="dojDrdo" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('dojDrdo');"/>
													
												<script type="text/javascript">
														new tcal({'formname':'outside','controlname':'dojDrdo'});
												</script>
											</div> 
											
											
											
											<div class="quarter">Date of Birth<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="dob" id="dob" cssClass="dateClass" readonly="true"  onclick="javascript:clearDateText('dob');"/>
													
												<script type="text/javascript">
												new tcal({'formname':'outside','controlname':'dob'});
												</script>
											</div>
											 </div>
											 <div class="line">	
											 
											 
											 	
											   <!-- commented by bkr 29/03/2016 -->	
											   
											   						
											 <div class="quarter" style="display: none;">Date of Join in GOVT<span class="mandatory">*</span></div>
											<div class="quarter" style="display: none;">
												<form:input path="dojGovt" id="dojGovt" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('dojGovt');"/>
													
												<script type="text/javascript">
														new tcal({'formname':'outside','controlname':'dojGovt'});
												</script>
											</div> 
											 <div class="quarter" style="display: none;">Last Promotion Date<span class="mandatory">*</span></div>
											<div class="quarter" style="display: none;">
												<form:input path="lastPromotion" id="lastPromotion" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('lastPromotion');"/>
													
												<script type="text/javascript">
												new tcal({'formname':'outside','controlname':'lastPromotion'});
												</script>
											</div> 
											
											
											 </div>
									    <div class="line">	<!-- id="aslId"	 -->							
											<!-- <div class="quarter">Date of Join in ASL<span class="mandatory">*</span></div> -->
											<div class="quarter">Date of Join<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="dojAsl" id="dojAsl" cssClass="dateClass"  readonly="true"  onclick="javascript:clearDateText('dojAsl');"/>
												
												<script type="text/javascript">
														new tcal({'formname':'outside','controlname':'dojAsl'});
												</script>
											</div>
											
											
												  <!-- commented by bkr 29/03/2016 -->		
												  
												  				
											 <div class="quarter"  style="display: none;">Present Rank Seniority Date<span class="mandatory">*</span></div>
										<div class="quarter"  style="display: none;">
												<form:input path="seniorityDate" id="seniorityDate" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('seniorityDate');"/>
													
												<script type="text/javascript">
														new tcal({'formname':'outside','controlname':'seniorityDate'});
												</script>
											</div> 
											
											
											
										 </div>
									  	<div class="line">
									  	<div class="quarter">Employment Type<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:select path="employmentId" id="employmentId" cssClass="formSelect" >
														<form:option value="Select">Select</form:option>
	               	      								<form:options items="${sessionScope.employementTypeList}" itemValue="id" itemLabel="name"/>
	               	   								</form:select>
												</div>
										<div class="quarter" style="display: none">Community<span class="mandatory">*</span></div>
											<div class="quarter"   style="display: none">
												<form:select path="community" id="community" cssClass="formSelect" >
													<form:option value="Select">Select</form:option>
               	      								<form:options items="${sessionScope.communityList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											</div>
									</div>	
									
									<div class="line">
								<!-- 	<div class="quarter">Religion<span class="mandatory">*</span></div> -->
									<div class="quarter">State<span class="mandatory">*</span></div>
											<div class="quarter">
											<form:select path="religion" id="religion" cssClass="formSelect" >
													<form:option value="Select">Select</form:option>
               	      								<form:options items="${sessionScope.ReligionList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											</div>
									
									
									</div>
										
									<%-- <div class="line">
									<div class="quarter">Religion<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="religion" id="religion" cssClass="formSelect" >
													<form:option value="Select">Select</form:option>
               	      								<form:options items="${employee.ReligionList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											
											</div>
											 <div class="quarter">
											<select name="religion" path="religion" id="religion">
																		<c:forEach var="item"
																			items="${employee.ReligionList}">
																			<option value="${employee.ReligionList.religion}">${employee.ReligionList.religion}</option>
																		</c:forEach>
																</select>
																</div>
																
									</div> --%>
									<div class="line">
									<div class="quarter">Mother Tongue<span class="mandatory"></span></div>
												<div class="quarter"><form:input path="motherTongue" id="motherTongue" maxlength="15" cssClass="formSelect" onkeypress="return checkChar(event); "/></div>
									<div class="quarter">Marital Status<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="marital" id="marital" cssClass="formSelect" >
													<form:option value="Select">Select</form:option>
               	      								<form:options items="${sessionScope.maritalList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											</div>
									</div>	
									<div class="line">
									<div class="quarter">Appointment Type<span class="mandatory"></span></div>
												<div class="quarter">
													<form:select path="appointmentId" id="appointmentId" cssClass="formSelect">
														<form:option value="Select">Select</form:option>
	               	      								<form:options items="${sessionScope.appointmentTypeList}" itemValue="id" itemLabel="name"/>
	               	   								</form:select>
												</div>
									</div>	
									<div class="line">
												<div class="quarter">ID Marks</div>
												<div><form:textarea path="idMarks" id="idMarks" rows="4" cols="30" onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500); "/>
													<input type="text" class="counter1" name="counter" value="500" id="counter" disabled=""/>
												</div>
										</div>		
									<div class="line"><div id="checkHead"></div></div>
								    </div>
								</fieldset>
								<div id="submit" class="float">
									<div style="padding-left: 55%;padding-top: 1%;"><a href="javascript:otherEmployeeDetails();" class="appbutton" style="text-decoration: none">Submit</a></div>
									<div><a href="javascript:resetOtherEmployee();" class="appbutton" style="text-decoration: none">Clear</a></div>
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
		<form:hidden path="userSfid"/>
		<form:hidden path="type"/>
			<script type="text/javascript">
				var checkDate='${outside.checkDate}';
			</script>
		</form:form>
	</body>
	
</html>
<!-- End:OuterEmployeeDetails.jsp -->