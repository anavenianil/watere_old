<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:HRDGAlertDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="./script/RegExpValidate.js"></script>
<script language="javascript" src="script/trainingscript.js"></script>				
<fieldset><legend><strong><font color='green'>Alert Details</font></strong></legend>
	<div class="line">
		<div class="line">
			
		</div>
		<div class="line">
				<div class="quarter leftmar">Alert Date</div>
				<c:if test="${workflowmap.alertDetails.alertID =='2'}">
			    <div class="quarter"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.alertDetails.assignedDate}"/></div>
			    </c:if>
			    <c:if test="${workflowmap.alertDetails.alertID =='3'}">
			    <div class="quarter"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.alertDetails.assignedDate}"/></div>
			    </c:if>
		</div>
		<div class="line">
				
		</div>
		<div class="line">
			   <div class="quarter leftmar">Course Name</div>
			   <div class="quarter">${workflowmap.trainingCirculationDto.courseDto.name}</div>
			   
		</div>
		<c:if test="${workflowmap.alertDetails.alertID =='2'}">
		<div class="line">
				<div class="quarter leftmar">Nomination Start Date</div>
			   <div class="quarter">${workflowmap.trainingCirculationDto.nominationStartDate}</div>
			   <div class="quarter leftmar">Nomination End Date</div>
			   <div class="quarter">${workflowmap.trainingCirculationDto.nominationEndDate}</div>
		</div>
		</c:if>
		<c:if test="${workflowmap.alertDetails.alertID =='3'}">
		
		<div class="line">
		
			    <div class="quarter leftmar">Start Date</div>
			   <div class="quarter">${workflowmap.trainingCirculationDto.courseDurationDto.startDate}</div>
			   <div class="quarter leftmar">End Date</div>
			   <div class="quarter">${workflowmap.trainingCirculationDto.courseDurationDto.endDate}</div>
			   
		</div>
		</c:if>
		<div class="line">
				
				<div class="quarter leftmar">Training Institute Details</div>
				<div class="quarter leftmar ">${workflowmap.trainingCirculationDto.trainingInistituteDto.name},${workflowmap.trainingCirculationDto.trainingVenueDto.address}</div>
		
		</div>
		<c:if test="${workflowmap.trainingCirculationDto.courseDto.fee != null}">
		<div class="line">
				<div class="quarter leftmar">Fee</div>
			   <div class="quarter">Rs. ${workflowmap.trainingCirculationDto.courseDto.fee}<c:if test="${workflowmap.trainingCirculationDto.courseDto.serviceTax != null}"> ServiceTax ${workflowmap.trainingCirculationDto.courseDto.serviceTax}%</c:if></div>
			  
		</div>
		</c:if>
		<c:if test="${workflowmap.trainingCirculationDto.brochure != null}">
		
		<div class="line">
				<div class="quarter"><a class="expbutton" href="javascript:showDatabaseFile('${workflowmap.trainingCirculationDto.brochure}');"><span>&nbsp;&nbsp;View Brochure</span></a></div>
				
				<div class="quarter"><a class="expbutton" href="javascript:reportION('${workflowmap.trainingCirculationDto.durationId}');"><span>&nbsp;&nbsp;View I.O.N</span></a></div>
		
		</div>
		</c:if>
										<div class="line">
										<div style="margin-left:25%">
										<div class="expbutton"><a href="javascript:pisHome()"><span>Back</span></a></div>
										
										</div>
										<div class="expbuttonred"><a id="report" href="javascript:respondAlert('${workflowmap.alertDetails.id}','${workflowmap.alertDetails.referenceID}','0','${workflowmap.alertDetails.alertID}')"><span>Delete</span></a></div>
										
										</div>
		
	</div>
</fieldset>

<!-- End:HRDGAlertDetails.jsp -->