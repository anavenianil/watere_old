<!-- Begin : LTCWaterDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/tada.js"></script>

<div>
    <c:if test="${workflowmap.ltcWaterRequestDTO.nod!=0}">
    
    
    <div class="line">
		<div class="quarter bold">Start Holiday Date : </div>
		<%-- <div class="quarter" id="fromDate">: ${workflowmap.tadaWaterApprovalRequestDTO.fromDate} </div> --%>
		
		<div class="quarter" id="startHoliday"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.ltcWaterRequestDTO.startHoliday}" /></div>
		
	<div >
		<div class="quarter bold">Return Holiday Date :</div>
		<div class="quarter" id="returnHoliday">		
		<fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.ltcWaterRequestDTO.returnHoliday}" />
		</div>
	</div>
	</div>
	
	<div class="line">
	<div class="quarter bold">Number Of Days  : </div>
		<div class="quarter" id="nod">: ${workflowmap.ltcWaterRequestDTO.nod}  &nbsp;Days.</div>
	
		
	</div>
	
	<div class="line">
	
		<div class="quarter bold"> 	Leave Type  :</div>
		<div class="quarter" id="leaveType">:&nbsp;${workflowmap.ltcWaterRequestDTO.leaveType}</div>
		</div>
		<div  class="line">
		
		<!--added by bkr 26/04/2016   -->
		<div class="quarter bold">Number Of Adults Tickets  : </div>
		<div class="quarter" id="noOfAdultsTickets">:&nbsp;${workflowmap.ltcWaterRequestDTO.noOfAdultsTickets}</div>
		<div class="quarter bold">Number Of Children Tickets  : </div>
		<div class="quarter" id="noOfChildrenTickets">: ${workflowmap.ltcWaterRequestDTO.noOfChildrenTickets}  &nbsp;</div>
	
	</div>
	
		</c:if>
		<div class="line">
			<div class="quarter bold">Anuual Leave Application Form</div>
			<div class="quarter bold"><a href="hello.htm?requestID=${workflowmap.ltcWaterRequestDTO.requestId}&addressTypeId2=2&addressTypeId1=3&param=APPLICATIONFORRECESS">Print</a></div>
		
		</div>
	
	

			<form:hidden path="message"/>
			<form:hidden path="historyID"/>
		    <form:hidden path="statusMsg"/>
<script>
reqStatus = '${workflowmap.tadaApprovalRequestDTO.status}';
advanceFlag = '${workflowmap.tadaApprovalRequestDTO.advanceFlag}';
ammendementId = '${workflowmap.tadaApprovalRequestDTO.ammendementId}';
</script>
	
</div>

<!-- End :LTCWaterDetails.jsp -->