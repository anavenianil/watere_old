<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:PreviewData.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Preview Employee Details</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="./script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="./script/script.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
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
								<div class="headTitle">Preview Employee Details</div>
								<%-- Content Page starts --%>
								<fieldset><legend><strong><font color='green'>General Details</font></strong></legend>
										<table style="width:100%;">
											
											<tr>
												<td style="width:25%;"><b>SFID</b></td>
												<td style="width:25%">:&nbsp;${employee.sfid}</td>
												<td style="width:25%;"><b>Name In Service Book</b></td>
												<td style="width:25%;">:&nbsp;${employee.nameInServiceBook}</td>
											</tr>
											<tr>
												<td style="width:25%;"><b>Title</b></td>
												<td style="width:25%;">:&nbsp;${employee.titleName}</td>
												<td style="width:25%;"><b>First Name</b></td>
												<td style="width:25%;">:&nbsp;${employee.firstName}</td>
											</tr>
											<tr>
												<td style="width:25%;"><b>Middle Name</b></td>
												<td style="width:25%;">:&nbsp;${employee.middleName}</td>
												<td style="width:25%;"><b>Last Name</b></td>
												<td style="width:25%;">:&nbsp;${employee.lastName}</td>
											</tr>
											<tr>
												<td style="width:25%;"><b>Father/Spouse Name:</b></td>
												<td style="width:25%;">:&nbsp;${employee.relationName}</td>
											</tr>
											<tr>
												<td style="width:25%;"><b> Date of Birth</b></td>
												<td style="width:25%;">:&nbsp;${employee.dob}</td>
												<td style="width:25%;"><b>Gender</b></td>
												<td style="width:25%;">:
												<c:if test="${employee.gender eq 'M'}">
													Male
												</c:if>
												<c:if test="${employee.gender eq 'F'}">
													Female
												</c:if></td>

											</tr>
											
											<tr>
												<td style="width:25%;"><b>Marital Status</b></td>
												<td style="width:25%;">:&nbsp;${employee.maritalName}</td>
												<td style="width:25%;"><b>Religion</b></td>
												<td style="width:25%;">:&nbsp;${employee.religionName}</td>
											</tr>
											<tr>
												<td style="width:25%;" hidden><b>Community</b></td>
												<td style="width:25%;" hidden>:&nbsp;${employee.communityName}</td>
												<td style="width:25%;"><b>Nationality</b></td>
												<td style="width:25%;">:&nbsp;${employee.nationalityName}</td>
												
											</tr>
											<tr>
												<td style="width:25%;"><b>Blood Group</b></td>
												<td style="width:25%;">:&nbsp;${employee.bloodName}</td>
												<td style="width:25%;" hidden><b>Residence Number</b></td>
												<td style="width:25%;" hidden>:&nbsp;${employee.residenceNo}</td>
												
											</tr>
											<tr>
												<td style="width:25%;"><b>Mobile Number</b></td>
												<td style="width:25%;">:&nbsp;${employee.personalNumber}</td>
												<td style="width:25%;"><b>Internal Number</b></td>
												<td style="width:25%;">:&nbsp;${employee.internalNo}</td>
												
											</tr>
											<tr>
												<td style="width:25%;"><b>Mother Tongue</b></td>
												<td style="width:25%;">:&nbsp;${employee.motherTongue}</td>
												<td style="width:25%;"><b>Height</b></td>
												<td style="width:25%;">:&nbsp;${employee.height} Cms</td>
											</tr>
											
											<tr>
												<td style="width:25%;"><b>ID Marks</b></td>
												<td colspan="3">:&nbsp;${employee.idMarks}</td>	
											</tr>
										</table>
									
								</fieldset>
								
								<fieldset><legend><strong><font color='green'>Professional Details</font></strong></legend>
									<table style="width:100%;">
										<tr>
											<td style="width:25%;"><b>Appointment Type</b></td>
											<td style="width:25%;">:&nbsp;${employee.appName}</td>
											<td style="width:25%;"><b>Reporting to</b></td>
											<td style="width:25%;">:&nbsp;${employee.divisionName}</td>
										</tr>
										<tr>
											<td style="width:25%;"><b>Employment Type</b></td>
											<td style="width:25%;">:&nbsp;${employee.empName}</td>
											<td style="width:25%;"><b>Department</b></td>
											<td style="width:25%;">:&nbsp;${employee.directorateName}</td>
										</tr>
										<tr>
											<td style="width:25%;"><b>Designation</b></td>
											<td style="width:25%;">:&nbsp;${employee.designationName}</td>
											<td style="width:25%;" hidden><b>Reservation Type</b></td>
											<td style="width:25%;" hidden>:&nbsp;${employee.reservationName}</td>
										</tr>
										<tr>
											<td style="width:25%;" hidden><b>PH Type</b></td>
											<td style="width:25%;" hidden>:
											<c:if test="${empty employee.handicapName}">
													NA
											</c:if>
											<c:if test="${not empty employee.handicapName}">
													${employee.handicapName}
											</c:if>
											</td>
											<td style="width:25%;" hidden><b>Whether Service Person</b></td>
											<td style="width:25%;" hidden>:
											<c:if test="${not empty employee.joinName}">
													${employee.joinName}
											</c:if>
											<c:if test="${empty employee.joinName}">
													NA
											</c:if></td>
										</tr>
										<tr>
											<td style="width:25%;" hidden><b>Duration Of Service</b></td>
											<td style="width:25%;" hidden>:
												<c:if test="${not empty employee.workedYears}">
													${employee.workedYears}
											</c:if>
											<c:if test="${empty employee.workedYears}">
													NA
											</c:if></td>
											<td style="width:25%;display: none"><b>Family Planning Allowances</b></td>
											<td style="width:25%;display: none">:&nbsp;${employee.famPlanning}</td>
										</tr>
										<tr>
											<td style="width:25%;display: none"><b>House Build Allowance</b></td>
											<td style="width:25%;display: none">:&nbsp;${employee.houseAllowence}</td>
											<td style="width:25%;display: none"><b>Group Insurance Allowance</b></td>
											<td style="width:25%;display: none">:&nbsp;${employee.groupAllowence}</td>
										</tr>
										<tr>
											<td style="width:25%;display: none"><b>Group Insurance Expire Date</b></td>
											<td style="width:25%;display: none">:
											<c:if test="${not empty employee.uptoDate}">
													${employee.uptoDate}
											</c:if>
											<c:if test="${empty employee.uptoDate}">
													NA
											</c:if></td>
											<td style="width:25%;display: none"><b>Date of Join in Govt</b></td>
											<td style="width:25%;display: none">:&nbsp;${employee.dojGovt}</td>
										</tr>
										
										<tr>
											<td style="width:25%;display: none"><b>Date Of Join in DRDO</b></td>
											<td style="width:25%;display: none">:&nbsp;${employee.dojDrdo}</td>
											<td style="width:25%;"><b>Date of Join </b></td>
											<td style="width:25%;">:&nbsp;${employee.dojAsl}</td>
										</tr>
										<tr>
											<td style="width:25%;display: none"><b>Present Rank Seniority Date</b></td>
											<td style="width:25%;display: none">:
											<c:if test="${ not empty employee.seniorityDate}">
													${employee.seniorityDate}
											</c:if>
											<c:if test="${empty employee.seniorityDate}">
													NA
											</c:if></td>
											<td style="width:25%;display: none"><b>Date Of Promotion </b></td>
											<td style="width:25%;display: none">:&nbsp;${employee.lastPromotion}</td>
										</tr>
										
									</table>
											
								</fieldset>
								<fieldset style="display: none"><legend><strong><font color='green'>Employee Other Details</font></strong></legend>
									<table style="width:100%;">
									<!-- //commented by bkr 02/04/2016 -->
										<%-- <tr>
											<td style="width:25%;"><b>Dispensary No</b></td>
											<td style="width:25%;"><b>:</b>
										    <c:if test="${employee.dispensaryName eq 0}">
											</c:if>
											<c:if test="${employee.dispensaryName ne 0}">
											${employee.dispensaryName}
											</c:if>
											<td style="width:25%;"><b>CGHS Card Number</b></td>
											<td style="width:25%;">:&nbsp;${employee.cgshNumber}</td>
										</tr> --%>
										<tr>
											<td style="width:25%;"><b>PPA Number</b></td>
											<td style="width:25%;">:&nbsp;${employee.ppaNo}</td>
											<td style="width:25%;"><b>PRAN Number</b></td>
											<td style="width:25%;">:&nbsp;${employee.pranNo}</td>
											
										</tr>
										<tr>
											<td style="width:25%;"><b>GPF A/C No</b></td>
											<td style="width:25%;">:&nbsp;${employee.gpfAcNo}</td>
										</tr>
									</table>
									</fieldset>
									<c:if test="${employee.param eq 'preview'}">
										<div class="line">
											<div style="padding-left: 45%;padding-top: 1%;"><a href="javascript:saveEmployee()"  class="appbutton" style="text-decoration: none;">Submit</a></div>
											<div><a href="javascript:backToOther()" class="appbutton" style="text-decoration: none;">Back</a></div>
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
			</div>
			<div><jsp:include page="Footer.jsp" /></div>	

		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="sfid"/>
		</form:form>
	</body>
</html>
<!-- End:PreviewData.jsp -->