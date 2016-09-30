<!-- Begin : HQRequestDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>

<div class="line">
    <div class="line">
     	<div class="quarter bold">Request Type</div>
     	<div class="quarter">Higher Qualification</div>
	</div>
	<div class="line">
	<div class="quarter bold">Course</div>
	<div class="quarter">"${workflowmap.hqRequestDTO.course}"</div>
	</div>
    <div class="line">
		<div class="quarter bold">Duration Of Course</div>
		<div class="half">
			<div class="quarter"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.hqRequestDTO.fromDate}"/></div>
			<div class="quarter">To&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.hqRequestDTO.toDate}" /></div>
		</div>
	</div>
	<div class="line">
	<div class="half bold">Higher Qualification Application Form</div>
     	<div class="quarter bold"><a href="javascript:hQApplication('${workflowmap.requestId}')">PDF</a></div> 
    </div>
    <c:if test="${workflowmap.workflowStageID=='100' && workflowmap.lastStagePendingCheck!='lpending'}">
	  	<div class="line">
				<div class="half bold">Whether The Study Course Will Be Useful For Lab Work</div>
					<div class="half">
			  			<form:radiobutton path="labWork" id="labWorkYes" label="Yes" value="Y"/>
						<form:radiobutton path="labWork" id="labWorkNo" label="No" value="N"/>											
				</div>
		</div>
		<div class="line">
				<div class="half bold">Whether Such Permission Will Interfere With Discharge Of Duties Or Not</div>
					<div class="half">
						<form:radiobutton path="dischargeOfDuties" id="dischargeOfDutiesYes" label="Yes" value="Y"/>
						<form:radiobutton path="dischargeOfDuties" id="dischargeOfDutiesNo" label="No" value="N"/>											
					</div>
		</div>
    </c:if>
    <c:if test="${workflowmap.lastStagePendingCheck!='lpending'}">
	  
    </c:if>	
</div>
<!-- End : HQRequestDetails.jsp -->