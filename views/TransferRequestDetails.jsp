<!-- Begin : TransferRequestDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div class="line">
	<div class="line">
		<div class="quarter leftmar">Employee Name</div>
		<div class="quarter">${workflowmap.transferRequestDetails.transferedSFID}-${workflowmap.transferRequestDetails.employeeDetails.nameInServiceBook }</div>
		<div class="quarter leftmar">Type of Transfer</div>
		<div class="quarter" id="transferType">
			<c:if test="${workflowmap.transferRequestDetails.transferTypeID==45}">Internal</c:if>
			<c:if test="${workflowmap.transferRequestDetails.transferTypeID==46}">External</c:if>
		</div>
	</div>
	<div class="line">
		<div class="quarter leftmar">Department From</div>
		<div class="quarter">${workflowmap.transferRequestDetails.departmentFrom}</div>
		<div class="quarter leftmar">To</div>
		<div class="quarter">${workflowmap.transferRequestDetails.departmentTo}</div>
	</div>
	<div class="line">
		<div class="quarter leftmar">Reason</div>
		<div class="quarter">${workflowmap.transferRequestDetails.reason}</div>
	</div>
	<c:if test="${workflowmap.transferRequestDetails.transferTypeID==46}">
		<div class="line">
			<div class="quarter leftmar">Attachment</div>
			<div class="quarter"><a href="javascript:viewAttachment('${workflowmap.transferRequestDetails.attachment}')">View</a></div>
		</div>
	</c:if>
	<c:if test="${workflowmap.transferRequestDetails.doPartDTO!=null}">
		<div class="line">
			<div class="quarter leftmar">ION</div>
			<div class="quarter">${workflowmap.transferRequestDetails.doPartDTO.doPartNumber}</div>
			<div class="quarter leftmar">ION Date</div>
			<div class="quarter"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.transferRequestDetails.doPartDTO.doPartDate}" /></div>
		</div>		
	</c:if>
	<c:if test="${workflowmap.transferRequestDetails.hqRefNo!=null}">
		<div class="line">
			<div class="quarter leftmar">R&Q HQrs Ref No</div>
			<div class="quarter">${workflowmap.transferRequestDetails.hqRefNo}</div>
			<div class="quarter leftmar">Received Date</div>
			<div class="quarter"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.transferRequestDetails.receivedDate}" /></div>
		</div>
	</c:if>
	
	<c:if test="${workflowmap.lastStagePendingCheck=='lpending'}">		
		<div class="line">
			<div class="quarter leftmar">ION</div>
			<div class="quarter" id="ionNoDiv">
				<form:input path="doPartNo" id="doPartNo" maxlength="50"/>
			</div>
			<div class="quarter leftmar">ION Date</div>
			<div class="quarter" id="ionDateDiv">
				<form:input path="doPartDate" id="doPartDate" cssClass="dateClass" readonly="true"/>
				<img  src="./images/calendar.gif" id="doPartDateImg" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"doPartDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"doPartDateImg",singleClick : true,step : 1});
				</script>
			</div>
		</div>		
		<c:if test="${workflowmap.transferRequestDetails.transferTypeID==46}">
			<div class="line">
				<div class="quarter leftmar">R&Q HQrs Ref No</div>
				<div class="quarter" id="hqrNoDiv">
					<form:input path="hqRefNo" id="hqRefNo" maxlength="50"/>
				</div>
				<div class="quarter leftmar">Received Date</div>
				<div class="quarter" id="hqrDateDiv">
					<form:input path="receivedDate" id="receivedDate" cssClass="dateClass" readonly="true"/>
					<img  src="./images/calendar.gif" id="receivedDateImg" />	
					<script type="text/javascript">
						Calendar.setup({inputField :"receivedDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"receivedDateImg",singleClick : true,step : 1});
					</script>					
				</div>
			</div>
		</c:if>
	</c:if>
</div>
<!-- End : TransferRequestDetails.jsp -->