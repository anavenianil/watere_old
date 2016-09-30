<!-- Begin : CancelTrainingNominationDetails.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/trainingscript.js"></script>


<div>
    <div class="line">
		<div class="quarter bold">Ref Request ID</div>
		<div class="quarter">:&nbsp;${workflowmap.trainingNominationDto.hrdgTxnReqDto.requestId}</div>
		
	</div>
	<div class="line">
		<div class="quarter bold">Course Name</div>
		<div class="quarter">:&nbsp;${workflowmap.trainingNominationDto.courseDto.name}</div>
		<div class="quarter bold">Date & Duration</div>
		<div class="quarter">:&nbsp;${workflowmap.trainingNominationDto.courseDto.startDate}&nbsp;&&nbsp;${workflowmap.trainingNominationDto.courseDto.days}&nbsp; Days</div>
	</div>
	<div class="line">
		<div class="quarter bold">Nomination Date</div>
		<div class="quarter" id="tdWorkPlace">:&nbsp;${workflowmap.trainingNominationDto.nominationDate}</div>
	</div>
	<div class="line">
		<div class="quarter bold">Current Assignment</div>
		<div class="quarter" id="authorizedMove">:&nbsp;${workflowmap.trainingNominationDto.currentAssignment}</div>
		<div class="quarter bold">Last Attended Program</div>
		<div class="quarter" id="tadaAdvanceAmount">:&nbsp;${workflowmap.trainingNominationDto.lastAttendedCourse}</div>
	</div>
	<div class="line">
	    <div class="quarter bold">Relevance</div>
	    <div class="quarter" id="tadaAdvanceAmount">:&nbsp;${workflowmap.trainingNominationDto.relevance}</div>
	</div>
	<div class="line">
	    <div class="quarter bold">Nomination Cancel Form:</div>
		<div class="quarter">:&nbsp;<a href="javascript:getNominationReport('${workflowmap.trainingNominationDto.id}','${workflowmap.trainingNominationDto.hrdgTxnCancelReqDto.requestId}','trainingCancelNominationPrint')">Print</a></div>
	    
	</div>
	

	
</div>
		<form:hidden path="message" id="message"/>
		<form:hidden path="requestId" id="requestId"/>
		<form:hidden path="back" id="back"/>
		<form:hidden path="statusMsg" id="statusMsg"/>
<!-- End : CancelTrainingNominationDetails.jsp -->