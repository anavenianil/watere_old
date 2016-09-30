<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:EmployeeDetails.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa" %>

								<%-- Content Page starts --%>
								<fieldset><legend><strong><font color='green'>Roles</font></strong></legend>
									   <table style="width:100%;">
									      <!--  <tr>
									         <td style="width:25%"><b>Primary Role</b></td>
									         <td style="width:25%"><b>:</b>${employee.defaultRole}</td>
									         
									      </tr>
									      <c:if test="${employee.parentName ne null}">
									      <tr>
									         <td style="width:25%"><b>Reporting To</b></td>
									         <td style="width:25%"><b>:</b>${employee.parentName}&nbsp;&nbsp;<b>(</b>${employee.parentRole}<b>)</b></td>
									        
									      </tr>
									      </c:if>
									      <c:if test="${employee.additionalRoles ne 'null' && employee.additionalRoles ne null }"> 
									      <tr>
									         <td style="width:25%"><b>Additional Roles</b></td>
									         <td style="width:25%"><b>:</b>${employee.additionalRoles}</td>
									         
									      </tr>
									      </c:if>-->
									      <c:forEach items="${employee.empRoleslist}" var="roles">
										      <tr>
											  	<c:if test="${roles.defaultRole eq 'YES'}">
											    	<td style="width:25%"><b>Primary Role</b></td>
											   	</c:if>
										      	<c:if test="${roles.defaultRole eq 'NO'}">
										     		<td style="width:25%"><b>Additional Role</b></td>
										      	</c:if>
										        <td style="width:25%"><b>: </b>${roles.additionalRoles}</td>
										        <td style="width:25%"><b>Reporting To</b></td>
										        <td style="width:25%"><b>: </b>${roles.parentName}&nbsp;&nbsp;<b>(</b>${roles.parentRole}<b>)</b></td>
										      </tr>
										      
										      <tr></tr>
									      </c:forEach>
									   </table>
								</fieldset>
								
								<fieldset><legend><strong><font color='green'>General Details</font></strong></legend>
										<table style="width:100%;">
											
											<tr>
												<td style="width:25%;"><b>SFID</b></td>
												<td style="width:25%"><b>:</b>&nbsp;${employee.sfid}</td>
												<td style="width:25%;"><b>Name In Service Book</b></td>
												<td style="width:25%;"><b>:</b>&nbsp;${employee.nameInServiceBook}</td>
											</tr>
											<tr>
												<td style="width:25%;"><b>Title</b></td>
												<td style="width:25%;"><b>:</b>&nbsp;${employee.titleName}</td>
												<td style="width:25%;"><b>First Name</b></td>
												<td style="width:25%;"><b>:</b>&nbsp;${employee.firstName}</td>
											</tr>
											<tr>
												<td style="width:25%;"><b>Middle Name</b></td>
												<td style="width:25%;"><b>:</b>&nbsp;${employee.middleName}</td>
												<td style="width:25%;"><b>Last Name</b></td>
												<td style="width:25%;"><b>:</b>&nbsp;${employee.lastName}</td>
											</tr>
											<tr>
												<td style="width:25%;"><b>Father/Spouse Name:</b></td>
												<td style="width:25%;"><b>:</b>&nbsp;${employee.relationName}</td>
											</tr>
											<tr>
												<td style="width:25%;"><b> Date of Birth</b></td>
												<td style="width:25%;"><b>:</b>&nbsp;${employee.dob}</td>
												<td style="width:25%;"><b>Gender</b></td>
												<td style="width:25%;"><b>:</b>
												<c:if test="${employee.gender eq 'M'}">
													Male
												</c:if>
												<c:if test="${employee.gender eq 'F'}">
													Female
												</c:if></td>

											</tr>
											
											<tr>
												<td style="width:25%;"><b>Marital Status</b></td>
												<td style="width:25%;"><b>:</b>&nbsp;${employee.maritalName}</td>
												<td style="width:25%;"><b>Religion</b></td>
												<td style="width:25%;"><b>:</b>&nbsp;${employee.religionName}</td>
											</tr>
											<tr>
												<td style="width:25%;"><b>Community</b></td>
												<td style="width:25%;"><b>:</b>&nbsp;${employee.communityName}</td>
												<td style="width:25%;"><b>Nationality</b></td>
												<td style="width:25%;"><b>:</b>&nbsp;${employee.nationalityName}</td>
												
											</tr>
											<tr>
												<td style="width:25%;"><b>Blood Group</b></td>
												<td style="width:25%;"><b>:</b>&nbsp;${employee.bloodName}</td>
												<td style="width:25%;"><b>Residence Number</b></td>
												<td style="width:25%;"><b>:</b>&nbsp;${employee.residenceNo}</td>
												
											</tr>
											<tr>
												<td style="width:25%;"><b>Mobile Number</b></td>
												<td style="width:25%;"><b>:</b>&nbsp;${employee.personalNumber}</td>
												<td style="width:25%;"><b>Internal Number</b></td>
												<td style="width:25%;"><b>:</b>&nbsp;${employee.internalNo}</td>
												
											</tr>
											<tr>
												<td style="width:25%;"><b>Mother Tongue</b></td>
												<td style="width:25%;"><b>:</b>&nbsp;${employee.motherTongue}</td>
												<td style="width:25%;"><b>Height</b></td>
												<td style="width:25%;"><b>:</b>${employee.height} Cms</td>
											</tr>
											
											<tr>
												<td style="width:25%;"><b>ID Marks</b></td>
												<td colspan="3"><b>:</b>&nbsp;${employee.idMarks}</td>	
											</tr>
										</table>
								</fieldset>
								
								<fieldset><legend><strong><font color='green'>Pay Details</font></strong></legend>
									<table style="width:100%;">
										<tr>
											<td style="width:25%;"><b>Basic Pay</b></td>
											<td style="width:25%;"><b>:</b>&nbsp;${employee.basicPay}</td>	
											<td style="width:25%;"><b>Grade Pay</b></td>
											<td style="width:25%;"><b>:</b>&nbsp;${employee.gradePay}</td>
										</tr>
									</table>
								</fieldset>
								
								<fieldset><legend><strong><font color='green'>Professional Details</font></strong></legend>
									<table style="width:100%;">
										<tr>
											<td style="width:25%;"><b>Appointment Type</b></td>
											<td style="width:25%;"><b>:</b>&nbsp;${employee.appName}</td>
											<%-- <td style="width:25%;"><b>Reporting to</b></td>
											<td style="width:25%;"><b>:</b>&nbsp;${employee.divisionName}</td>--%>
											
										</tr>
										<tr>
											<td style="width:25%;"><b>Employment Type</b></td>
											<td style="width:25%;"><b>:</b>&nbsp;${employee.empName}</td>
											<%-- <td style="width:25%;"><b>Department</b></td>
											<td style="width:25%;"><b>:</b>&nbsp;${employee.directorateName}</td>--%>
										</tr>
										<tr>
											<td style="width:25%;"><b>Designation</b></td>
											<td style="width:25%;"><b>:</b>&nbsp;${employee.designationName}</td>
											<td style="width:25%;"><b>Reservation Type</b></td>
											<td style="width:25%;"><b>:</b>&nbsp;${employee.reservationName}</td>
										</tr>
										
										<tr>
											<td style="width:25%;"><b>PH Type</b></td>
											<td style="width:25%;"><b>:</b>											
											<c:if test="${empty employee.handicapName}">
													NA
											</c:if>
											<c:if test="${not empty employee.handicapName}">
													${employee.handicapName}
											</c:if>
											</td>
											<td style="width:25%;"><b>Whether Service Person</b></td>
											<td style="width:25%;"><b>:</b>
											<c:if test="${not empty employee.joinName}">
													${employee.joinName}
											</c:if>
											<c:if test="${empty employee.joinName}">
													NA
											</c:if></td>
										</tr>
										<tr>
											<td style="width:25%;"><b>Duration of Service</b></td>
											<td style="width:25%;"><b>:</b>
											<c:if test="${not empty employee.workedYears}">
													${employee.workedYears}
											</c:if>
											<c:if test="${empty employee.workedYears}">
													NA
											</c:if>
											</td>
											<td style="width:25%;"><b>Family Planning Allowances</b></td>
											<td style="width:25%;"><b>:</b>
											<c:if test="${not empty employee.famPlanning}">
													${employee.famPlanning}
											</c:if>
											<c:if test="${empty employee.famPlanning }">
													NA
											</c:if>
											</td>
										</tr>
										<tr>
											<td style="width:25%;"><b>House Build Allowance</b></td>
											<td style="width:25%;"><b>:</b>
											<c:if test="${not empty employee.houseAllowence}">
													${employee.houseAllowence}
											</c:if>
											<c:if test="${empty employee.houseAllowence}">
													NA
											</c:if></td>
											<td style="width:25%;"><b>Group Insurance Allowance</b></td>
											<td style="width:25%;"><b>:</b>
											<c:if test="${not empty employee.groupAllowence}">
													${employee.groupAllowence}
											</c:if>
											<c:if test="${empty employee.groupAllowence}">
													NA
											</c:if></td>
										</tr>
										<tr>
											<td style="width:25%;"><b>Group Insurance Expire Date</b></td>
											<td style="width:25%;"><b>:</b>
											<c:if test="${not empty employee.uptoDate}">
													${employee.uptoDate}
											</c:if>
											<c:if test="${empty employee.uptoDate}">
													NA
											</c:if>
											</td>
											<td style="width:25%;"><b>Date of Join in Govt</b></td>
											<td style="width:25%;"><b>:</b>&nbsp;${employee.dojGovt}</td>
										</tr>
										
										<tr>
											<td style="width:25%;"><b>Date of Join in DRDO</b></td>
											<td style="width:25%;"><b>:</b> ${employee.dojDrdo}</td>
											<td style="width:25%;"><b>Date of Join in ASL</b></td>
											<td style="width:25%;"><b>:</b>&nbsp;${employee.dojAsl}</td>
										</tr>
										<tr>
											<td style="width:25%;"><b>Present Rank Seniority Date</b></td>
											<td style="width:25%;"><b>:</b>
											<c:if test="${ not empty employee.seniorityDate}">
													${employee.seniorityDate}
											</c:if>
											<c:if test="${empty employee.seniorityDate}">
													NA
											</c:if></td>
											<td style="width:25%;"><b>Date of Promotion </b></td>
											<td style="width:25%;"><b>:</b>
											<c:if test="${not empty employee.lastPromotion}">
													${employee.lastPromotion}
											</c:if>
											<c:if test="${empty employee.lastPromotion}">
													NA
											</c:if></td>
										</tr>
										<tr>
											<td style="width: 25%;"><b>Date of Retirement / Relieving</b></td>
											<td style="width: 25%;"><b>:</b>&nbsp;${employee.retirementDate}</td>
										</tr>
									</table>			
								</fieldset>
								
								<fieldset><legend><strong><font color='green'>Employee Other Details</font></strong></legend>
									<table style="width:100%;">
										<tr>
											<td style="width:25%;"><b>Dispensary No</b></td>
											<td style="width:25%;"><b>:</b>
										    <c:if test="${employee.dispensaryName eq 0}">
											</c:if>
											<c:if test="${employee.dispensaryName ne 0}">
											${employee.dispensaryName}
											</c:if>
											</td>
											<td style="width:25%;"><b>CGHS Card Number</b></td>
											<td style="width:25%;"><b>:</b>&nbsp;${employee.cgshNumber}</td>
										</tr>
										<tr>
											<td style="width:25%;"><b>PPA Number</b></td>
											<td style="width:25%;"><b>:</b>&nbsp;${employee.ppaNo}</td>
											<td style="width:25%;"><b>PRAN Number</b></td>
											<td style="width:25%;"><b>:</b>&nbsp;${employee.pranNo}</td>
											
										</tr>
										<tr>
											<td style="width:25%;"><b>GPF A/C No</b></td>
											<td style="width:25%;"><b>:</b>&nbsp;${employee.gpfAcNo}</td>
										</tr>
									</table>
									
										</fieldset>
								<%-- Content Page end --%>

		

<!-- End:EmployeeDetails.jsp -->