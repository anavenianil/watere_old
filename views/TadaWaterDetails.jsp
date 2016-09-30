<!-- Begin : TadaWaterDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/tada.js"></script>

<div>
    <c:if test="${workflowmap.tadaWaterApprovalRequestDTO.noOfDays!=0}">
	<div class="line">
	
		<div class="quarter bold">This Claim for :</div>
		<div class="quarter" id="claimPurpose">:&nbsp;${workflowmap.tadaWaterApprovalRequestDTO.claimPurpose}</div>
		</div>
		<div  class="line">
		
		<!--added by bkr 26/04/2016   -->
		<div class="quarter bold">Travelling TO Place : </div>
		<div class="quarter" id="travellingTo">:&nbsp;${workflowmap.tadaWaterApprovalRequestDTO.travellingTo}</div>
		
	</div>
	<div class="line">
		<div class="quarter bold">From Date : </div>
		<%-- <div class="quarter" id="fromDate">: ${workflowmap.tadaWaterApprovalRequestDTO.fromDate} </div> --%>
		
		<div class="quarter" id="fromDate"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.tadaWaterApprovalRequestDTO.fromDate}" /></div>
		
	<div >
		<div class="quarter bold">To Date :</div>
		<div class="quarter" id="toDate">		
		<fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.tadaWaterApprovalRequestDTO.toDate}" />
		</div>
	</div>
	</div>
	
	<div class="line">
		<div class="quarter bold">No.of Journy Days : </div>
		<div class="quarter" id="fromDate">: ${workflowmap.tadaWaterApprovalRequestDTO.noOfDays}  &nbsp;Days.</div>
	
	</div>
	
	<div class="line">
		<div class="quarter bold">Amount for Food and Accomidation  </div>
		<div class="quarter" id="foodandAccmAmt">: TZS&nbsp; ${workflowmap.tadaWaterApprovalRequestDTO.foodandAccmAmt} /-</div>
	<div >
		<div class="quarter bold">Amount For Fare </div>
		<div class="quarter" id="daAmt">: TZS  ${workflowmap.tadaWaterApprovalRequestDTO.daAmt} /-</div>
	</div>
	</div>
	
	<div class="line">
		<div class="quarter bold">Amount for Taxi  </div>
		<div class="quarter" id="taxiAmt">: TZS&nbsp; ${workflowmap.tadaWaterApprovalRequestDTO.taxiAmt} /- </div>
	<div >
		<div class="quarter bold">Amount For Transit  </div>
		<div class="quarter" id="transitAmt">: TZS&nbsp; ${workflowmap.tadaWaterApprovalRequestDTO.transitAmt} /-</div>
	</div>
	</div>
	<div class="line">
		<div class="quarter bold">Total Amount For All  </div>
		<div class="quarter" id="totalAmt">:  TZS&nbsp; ${workflowmap.tadaWaterApprovalRequestDTO.totalAmt} /- </div>
	
	</div>
	<div class="line">
		<div >
		<div class="quarter bold"></div>
		
	</div>
	</div>

	</c:if>
	
	
	<div class="line">
		<div class="quarter bold">TD Application Form</div>
		<c:choose>
		<c:when test="${workflowmap.tadaAmendmentDetails!=null && workflowmap.tadaAmendmentDetails!='[]' && workflowmap.tadaAmendmentDetails!=''}">
		<div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.tadaAmendmentDetails.requestId}','tadaTdApproval')">Print</a></div>
		</c:when>
		<c:otherwise><%-- <div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdApproval')">Print</a></div> --%>
		
		<div  class="quarter"><a href="hello.htm?requestId=${workflowmap.requestId}&param=ExpenseesClaim">PRINT</a></div>
		
		
		</c:otherwise>
		</c:choose>
		<c:if test="${workflowmap.tadaAmendmentDetails!=null && workflowmap.tadaAmendmentDetails!='[]' && workflowmap.tadaAmendmentDetails!=''}">
		<c:if test="${workflowmap.tadaApprovalRequestDTO.stayDuration!=0}">
		<div class="quarter bold">TD Amendment Form</div>
		<div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdAmendmentApproval')">Print</a></div>
		</c:if>
		<c:if test="${workflowmap.tadaApprovalRequestDTO.stayDuration==0}">
		<div class="quarter bold">TD Cancellation Form</div>
		<div class="quarter">:&nbsp;<a href="javascript:getTdInitialReport('${workflowmap.requestId}','tadaTdCancelApproval')">Print</a></div>
		</c:if>
		</c:if>
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

<!-- End : TadaWaterDetails.jsp -->