<!-- Begin : ERPLeaveDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/tada.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<script type="text/javascript" src="script/workflow.js"></script>
<script type="text/javascript" src="script/cghs.js"></script>
<script type="text/javascript" src="script/script.js"></script>

<div>
    <div class="line">
    	<div class="line">
			<div class="quarter bold">LEAVE TYPE</div>
			<c:if test="${workflowmap.erpAvailableLeaveSaveDTO.leaveType=='AL'}">: Annual Leave </c:if>
			<c:if test="${workflowmap.erpAvailableLeaveSaveDTO.leaveType=='SL'}">: Sick Leave </c:if>
			<c:if test="${workflowmap.erpAvailableLeaveSaveDTO.leaveType=='CL'}">: Compassionate Leave </c:if>
			<c:if test="${workflowmap.erpAvailableLeaveSaveDTO.leaveType=='ML'}">: Maternity Leave</c:if>
			<c:if test="${workflowmap.erpAvailableLeaveSaveDTO.leaveType=='PL'}">: Paternity Leave</c:if>
	<%-- 		<div class="quarter" id="nod">: ${workflowmap.erpAvailableLeaveSaveDTO.leaveType}</div> --%>
		</div>
		<div class="line">
			<div class="quarter bold">From Date</div>
			
			<div class="quarter" id="returnHoliday">		
				:<fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.erpAvailableLeaveSaveDTO.fromDate}" />
			</div>
			<div class="quarter bold">To Date</div>
			<div class="quarter" id="returnHoliday">		
				:<fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.erpAvailableLeaveSaveDTO.toDate}" />
			</div>
		</div>
		
		<div class="line">
			<div class="quarter bold">Applied Date </div>
			<div class="quarter" id="returnHoliday">		
				:<fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.erpAvailableLeaveSaveDTO.applyDate}" />
			</div>
			
			<div class="quarter bold">Contact Number</div>
			<div class="quarter" id="nod">: ${workflowmap.erpAvailableLeaveSaveDTO.contactNo}</div>
			
			
		
			
		</div>
    
		<div class="line">
			<div class="quarter bold">LEAVE REASON </div>
			<div class="quarter" id="nod">: ${workflowmap.erpAvailableLeaveSaveDTO.reason}</div>
				<c:if test="${not empty workflowmap.erpAvailableLeaveSaveDTO.reasonCancellation}" >
			
			<div class="quarter bold">Reason For Leave Cancellation</div>
			<div class="quarter" id="nod">: ${workflowmap.erpAvailableLeaveSaveDTO.reasonCancellation}</div>
			
			
			</c:if>
		</div>
		
			<div class="line">
			<div class="quarter bold">Number of Days </div>
			<div class="quarter" id="nod">: ${workflowmap.erpAvailableLeaveSaveDTO.noOfDays} Days.</div>
		</div>
		
		<div class="quarter bold">Leave Application Form</div>
			<%-- <div class="quarter bold"><a href="javascript:leaveErpApplicationForm('${workflowmap.erpAvailableLeaveSaveDTO.requestId}');">PDF</a></div> --%>
			<div class="quarter bold"><a href="hello.htm?requestID=${workflowmap.erpAvailableLeaveSaveDTO.requestId}&param=LeaveRequestForm">downloaded pdf</a></div>
			<!-- <a href="reports.htm?ltcYear=2016&status=4&param=AnnualLeave">downloaded pdf111 </a> -->
			
			<!-- <a href="reports.htm?fromDate=01-01-16&toDate=01-12-16&status=1&param=TadaDetails">downloadedpdf TADA</a> -->
			
		<!-- 	<a href="hello.htm?param=EmpDetails">downloaded pdf1</a> -->
			
			<!-- <div class="quarter bold"><a href="javascript:erpabc();">Prescription Copy</a></div> -->
					
					<c:if test="${not empty workflowmap.erpAvailableLeaveSaveDTO.prescriptiondoc}">
					<div class="fvireq">
					<b> Prescription copy</b>&nbsp;&nbsp;&nbsp; <a
							href="javascript:showDatabaseFile12('726',${workflowmap.erpAvailableLeaveSaveDTO.requestId},'${workflowmap.erpAvailableLeaveSaveDTO.prescriptiondoc}')">View</a>
					</div>
					<%-- <a href="hello.htm?requestID=${workflowmap.erpAvailableLeaveSaveDTO.requestId}">downloaded pdf</a> --%>
					</c:if>
		</div>

</div>
</html>