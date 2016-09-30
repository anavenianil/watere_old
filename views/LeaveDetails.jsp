<!-- Begin : LeaveDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-fn" prefix="fn" %>
<script type="text/javascript" src="script/leave.js"></script>

<div class="line">
	<div class="line">
		<div class="quarter bold">Nature of leave</div>
		<div class="quarter">:
			<c:if test="${workflowmap.leaveRequestDetails.leaveTypeDetails.id != 11}">
				${workflowmap.leaveRequestDetails.leaveTypeDetails.leaveType}
			</c:if>
			<c:if test="${workflowmap.leaveRequestDetails.leaveTypeDetails.id == 11}">
				${workflowmap.leaveRequestDetails.leaveSubTypeDetails.leaveSubType} (SCL)
			</c:if>
		</div>
		<div class="quarter bold">Leave Applied Date</div>
		<div class="quarter">:&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.leaveRequestDetails.formattedRequestedDate}" /></div>
	</div>
	<div class="line">
		<div class="quarter bold">Leave Applied By</div>
		<div class="quarter">:&nbsp;<c:out  value="${workflowmap.leaveRequestDetails.requestedBy}"/>			
		</div>
		<div class="quarter bold">Section/Div./Dte. with Ph. No</div>
		<div class="quarter">:&nbsp;<c:out  value="${workflowmap.leaveRequestDetails.departmentDetails.deptName}, ${workflowmap.leaveRequestDetails.internalNo}"/>			
		</div>
	</div>
	<div class="line">
		<div class="quarter bold">From Date</div>
		<div class="quarter">:
			<c:if test="${workflowmap.leaveRequestDetails.otherRemarks ne null}">
				<c:set var="remarksText" value="${workflowmap.leaveRequestDetails.otherRemarks}" />
				<c:choose>
					<c:when test="${fn:contains(remarksText, 'proceed')}">${workflowmap.leaveRequestDetails.fromDate}</c:when>
					<c:otherwise><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.leaveRequestDetails.formattedFromDate}" /></c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${workflowmap.leaveRequestDetails.otherRemarks eq null}"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.leaveRequestDetails.formattedFromDate}" /></c:if>
			<%-- <fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.leaveRequestDetails.formattedFromDate}" /> --%>
			<c:if test="${workflowmap.leaveRequestDetails.fromHalfDayFlag == 'Y'}">&nbsp;(Half Day)</c:if>
		</div>
		<div class="quarter bold">Prefixed</div>
		<div class="quarter">:&nbsp;<c:out  value="${workflowmap.leaveRequestDetails.prefix}"></c:out></div>
	</div>
	<div class="line">
		<div class="quarter bold">To Date</div>
		<div class="quarter">:
			<c:if test="${workflowmap.leaveRequestDetails.otherRemarks ne null}">
				<c:set var="remarksText" value="${workflowmap.leaveRequestDetails.otherRemarks}" />
				<c:choose>
					<c:when test="${fn:contains(remarksText, 'proceed')}">${workflowmap.leaveRequestDetails.toDate}</c:when>
					<c:otherwise><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.leaveRequestDetails.formattedToDate}" /></c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${workflowmap.leaveRequestDetails.otherRemarks eq null}"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.leaveRequestDetails.formattedToDate}" /></c:if>
			<%-- <fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.leaveRequestDetails.formattedToDate}" /> --%>
			<c:if test="${workflowmap.leaveRequestDetails.toHalfDayFlag == 'Y'}">&nbsp;(Half Day)</c:if>
		</div>
		<div class="quarter bold">Suffixed</div>
		<div class="quarter">:&nbsp;<c:out  value="${workflowmap.leaveRequestDetails.suffix}"></c:out></div>
	</div>
	<div class="line">
		<div class="quarter bold">Number of leaves</div>
		<div class="quarter">:
			<fmt:parseNumber var="days" type="number" integerOnly="false" value="${workflowmap.leaveRequestDetails.noOfDays}" />
			<fmt:parseNumber var="pHolidays" value="${workflowmap.leaveRequestDetails.prevHolidays}" />
			<fmt:parseNumber var="nHolidays" value="${workflowmap.leaveRequestDetails.nextHolidays}" />
			<fmt:parseNumber var="debitLeaves" value="${workflowmap.leaveRequestDetails.debitLeaves}" />
			<c:choose>
				<c:when test="${days + pHolidays + nHolidays eq 0.5}">
					<c:out value="${days + pHolidays + nHolidays}" />
				</c:when>
				<c:when test="${days le debitLeaves}">
					<c:out value="${debitLeaves}" />
				</c:when>
				<c:otherwise>
					<c:out value="${days}" />
				</c:otherwise>
			</c:choose>
			<%-- <c:out value="${workflowmap.leaveRequestDetails.noOfDays + workflowmap.leaveRequestDetails.prevHolidays + workflowmap.leaveRequestDetails.nextHolidays}" /> --%>
			${workflowmap.leaveRequestDetails.debitCode}
		</div> <!-- ${workflowmap.leaveRequestDetails.debitLeaves+workflowmap.leaveRequestDetails.prevHolidays+workflowmap.leaveRequestDetails.nextHolidays}"> -->
		<c:if test="${workflowmap.leaveRequestDetails.leaveTypeID == 14}">
			<div class="quarter bold">Adoption Date</div>
			<div class="quarter">:&nbsp;${workflowmap.leaveRequestDetails.additionalData}</div>
		</c:if>
	</div>
	
	<c:if test="${workflowmap.requestType == 'LEAVE'}">
		<div class="line">
			<div class="quarter bold">DO Part II No</div>
			<div class="quarter">:&nbsp;
				<c:choose>
					<c:when test="${not empty workflowmap.orgroleIdLink}">
						<c:out value="${workflowmap.leaveRequestDetails.doPartDetails.doPartNo}"/>	
						<c:if test="${empty workflowmap.leaveRequestDetails.doPartDetails.doPartNo}"><font color="red">Not Published</font></c:if>	
					</c:when>
					<c:otherwise>
						<c:if test="${not empty workflowmap.leaveRequestDetails.doPartDetails.doPartNo}"><font color="red">Published</font></c:if>
						<c:if test="${empty workflowmap.leaveRequestDetails.doPartDetails.doPartNo}"><font color="red">Not Published</font></c:if>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="quarter bold">DO Part II Date</div>
			<div class="quarter">:&nbsp;
				<c:choose>
					<c:when test="${not empty workflowmap.orgroleIdLink}">
						<fmt:formatDate value="${workflowmap.doPartDate}" pattern="dd-MMM-yyyy"/>		
						<c:if test="${empty workflowmap.leaveRequestDetails.doPartDetails.doPartDate}"><font color="red">Not Published</font></c:if>
					</c:when>
					<c:otherwise>
						<c:if test="${not empty workflowmap.leaveRequestDetails.doPartDetails.doPartDate}"><font color="red">Published</font></c:if>
						<c:if test="${empty workflowmap.leaveRequestDetails.doPartDetails.doPartDate}"><font color="red">Not Published</font></c:if>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:if>
	<c:if test="${workflowmap.requestType == 'LEAVE CANCEL'}">
		<div class="line">
			<div class="quarter bold">DO Part II No</div>
			<div class="quarter">:&nbsp;
				<c:choose>
					<c:when test="${not empty workflowmap.orgroleIdLink}">
						<c:out value="${workflowmap.leaveCancelDopartno}"/>	
						<c:if test="${empty workflowmap.leaveCancelDopartno}"><font color="red">Not Published</font></c:if>
					</c:when>
					<c:otherwise>
						<c:if test="${not empty workflowmap.leaveCancelDopartno}"><font color="red">Published</font></c:if>
						<c:if test="${empty workflowmap.leaveCancelDopartno}"><font color="red">Not Published</font></c:if>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="quarter bold">DO Part II Date</div>
			<div class="quarter">:&nbsp;
				<c:choose>
					<c:when test="${not empty workflowmap.orgroleIdLink}">
						<fmt:formatDate value="${workflowmap.leaveCancelDopartDate}" pattern="dd-MMM-yyyy"/>
						<c:if test="${empty workflowmap.leaveCancelDopartDate}"><font color="red">Not Published </font></c:if>
					</c:when>
					<c:otherwise>
						<c:if test="${not empty workflowmap.leaveCancelDopartDate}"><font color="red">Published</font></c:if>
						<c:if test="${empty workflowmap.leaveCancelDopartDate}"><font color="red">Not Published</font></c:if>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:if>

	<c:if test="${workflowmap.requestType == 'LEAVE CONVERSION'}">
		<div class="line">
			<div class="quarter bold">DO Part II No</div>
			<div class="quarter">:&nbsp;
				<c:choose>
					<c:when test="${not empty workflowmap.orgroleIdLink}">
						<c:out  value="${workflowmap.leaveConversionDopartno}"/>	
						<c:if test="${empty workflowmap.leaveConversionDopartno}"><font color="red">Not Published </font></c:if>
					</c:when>
					<c:otherwise>
						<c:if test="${not empty workflowmap.leaveConversionDopartno}"><font color="red">Published</font></c:if>
						<c:if test="${empty workflowmap.leaveConversionDopartno}"><font color="red">Not Published</font></c:if>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="quarter bold">DO Part II Date</div>
			<div class="quarter">:&nbsp;
				<c:choose>
					<c:when test="${not empty workflowmap.orgroleIdLink}">
						<fmt:formatDate value="${workflowmap.leaveConversionDopartDate}" pattern="dd-MMM-yyyy"/>
						<c:if test="${empty workflowmap.leaveConversionDopartDate}"><font color="red">Not Published</font></c:if>
					</c:when>
					<c:otherwise>
						<c:if test="${not empty workflowmap.leaveConversionDopartDate}"><font color="red">Published</font></c:if>
						<c:if test="${empty workflowmap.leaveConversionDopartDate}"><font color="red">Not Published</font></c:if>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:if>
	
	<div class="line">
		<div class="quarter bold">Reason for Leave applied</div>
		<div class="threefourth">:&nbsp;<c:out  value="${workflowmap.leaveRequestDetails.reason}"></c:out></div>
	</div>
	<div class="line">
		<div class="quarter bold">Address</div>
		<div class="threefourth">:&nbsp;<c:out  value="${workflowmap.leaveRequestDetails.address}"></c:out></div>
	</div>
	<div class="line">
		<div class="quarter bold">Contact Number</div>
		<div class="quarter">:&nbsp;<c:out  value="${workflowmap.leaveRequestDetails.contactNumber}"></c:out></div>
		<c:if test="${workflowmap.referenceID != null}">
			<div class="quarter bold">Old Leave Reference ID</div>
			<div class="quarter">:&nbsp;<c:out  value="${workflowmap.referenceID}"></c:out></div>
		</c:if>
	</div>
	<c:if test="${workflowmap.leaveRequestDetails.leaveTypeDetails.id == '10'}">
		<div class="line">
			<div class="quarter bold">Degree</div>
			<div class="quarter">:&nbsp;<c:out  value="${workflowmap.leaveRequestDetails.additionalData}"></c:out></div>
		</div>
	</c:if>
	<c:if test="${workflowmap.leaveRequestDetails.leaveTypeDetails.id == '8'}">
		<div class="line">
			<div class="quarter bold">Delivery Date</div>
			<div class="quarter">:&nbsp;<c:out  value="${workflowmap.leaveRequestDetails.additionalData}"></c:out></div>
		</div>
	</c:if>
	<c:if test="${workflowmap.leaveRequestDetails.leaveTypeDetails.id == '9'}">
		<div class="line">
			<div class="quarter bold">Child Name</div>
			<div class="quarter">:&nbsp;<c:out  value="${workflowmap.leaveRequestDetails.additionalData}"></c:out></div>
		</div>
	</c:if>
	<c:if test="${workflowmap.leaveRequestDetails.remarks != null}">
		<div class="line">
			<div class="quarter bold">Remarks</div>
			<div class="threefourth failure">:&nbsp;<c:out value="${workflowmap.leaveRequestDetails.remarks}"></c:out></div>	
		</div>	
	</c:if>	
	<div class="line">
		<c:if test="${workflowmap.requestType != 'LEAVE CANCEL'}">
			<div class="quarter bold">Leave Application Form</div>
			<div class="quarter">:&nbsp;<a href="javascript:leaveApplication('${workflowmap.leaveRequestID}', '${workflowmap.leaveRequestDetails.leaveTypeDetails.code}', '${workflowmap.requestType}')">PDF</a></div>
		</c:if>
		<c:if test="${workflowmap.requestStage == 1}">
			<div class="quarter bold">Leave Card</div>
			<div class="quarter">:&nbsp;<a href="javascript:showLeaveCard('${workflowmap.requesterSfid}')">View</a></div>
		</c:if>
	</div>
	<%-- <c:if test="${workflowmap.pendingLeaves != null && not empty workflowmap.pendingLeaves}">
		<div class="line">
			<div class="quarter bold">Important Note</div>
			<div class="threefourth">:&nbsp;<font style="color: #ff00ff;">Employee is requested to send hard copy of the following  old leave applications/cancellatios/conversions duly signed to Admin</font>
				<div class="line" style="padding-left: 1%;padding-top: 3px;">
					<table border="1" width="75%">
						<tr style="text-align: center;background-color: #ccffff;">
							<td>Request Id</td>
							<td>Leave Type</td>
							<td>Requested Date</td>
							<td>Unactioned Days</td>
							<td>Print PDF Immediately</td>
						</tr>
						<c:forEach var="record" items="${workflowmap.pendingLeaves}">
							<tr>
								<td style="text-align: right;">${record.requestId}&nbsp;</td>
								<td>${record.leaveType}</td>
								<td>${record.requestedDate}</td>
								<td style="text-align: right;">${record.unactionedDays}&nbsp;</td>
								<td style="text-align: center;"><a href="javascript:leaveApplication('${record.requestId}', '${record.leaveType}', '${workflowmap.requestType}')">PDF</a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</c:if> --%>
	<div id="certiId">
		<c:if test="${workflowmap.leaveRequestDetails.medicalCertName != null}">
			<div class="line">
				<div class="quarter bold">Medical Ceritificate</div>
				<div class="quarter">:&nbsp;<a href="javascript:showDatabaseFile('${workflowmap.leaveRequestDetails.medicalCertName}')">View</a>
					&nbsp;<form:input  path="mcFile" type="file" id="mcFile" style="width:5px;"/>
				</div>
			</div>
		</c:if>
		<c:if test="${workflowmap.leaveRequestDetails.fitnessCertName != null}">
			<div class="line">
				<div class="quarter bold">Fitness Certificate</div>
				<div class="quarter">:&nbsp;<a href="javascript:showDatabaseFile('${workflowmap.leaveRequestDetails.fitnessCertName}')">View</a>
					&nbsp;<form:input  path="fitnessFile" type="file" id="fitnessFile" style="width:5px;"/>
				</div>
			</div>
		</c:if>
		<c:if test="${workflowmap.leaveRequestDetails.otherCertName != null}">
			<div class="line">
				<c:if test="${workflowmap.leaveRequestDetails.leaveTypeID == 11}">
					<div class="quarter bold">Doctor's Certificate</div>
				</c:if>
				<c:if test="${workflowmap.leaveRequestDetails.leaveTypeID != 11}">
					<div class="quarter bold">Other Certificate</div>
				</c:if>
				<div class="quarter">:&nbsp;<a href="javascript:showDatabaseFile('${workflowmap.leaveRequestDetails.otherCertName}')">View</a>
					&nbsp;<form:input  path="otherCertFile" type="file" id="otherCertFile" style="width:5px;"/>
				</div>
			</div>
		</c:if>
	</div>
	<c:if test="${workflowmap.leaveRequestDetails.medicalCertName != null || workflowmap.leaveRequestDetails.fitnessCertName != null || workflowmap.leaveRequestDetails.otherCertName != null}">
		<div class="line">
			<div class="half">
				<div class="appbutton" id="button" style="display:show;float: right;">
					<a href="javascript:updateUploadedFiles('${workflowmap.leaveRequestDetails.medicalCertName}', '${workflowmap.leaveRequestDetails.fitnessCertName}', '${workflowmap.leaveRequestDetails.otherCertName}', '${workflowmap.leaveRequestDetails.requestID}');">Upload</a>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${workflowmap.amendment != null}">
		<div class="line">
			<div style="text-align: center;color: red;"> This leave is amended and amendment id is <font color="magenta">${workflowmap.amendment}</font></div>
		</div>
	</c:if>
	<%--<c:if test="${workflowmap.leaveRequestDetails.medicalCertName != null || workflowmap.leaveRequestDetails.fitnessCertName != null || workflowmap.leaveRequestDetails.otherCertName != null}">
		<div class="line">
		<div id="result"></div>
			<fieldset><legend><strong><font class="failure">Certificates Updation</font></strong></legend>
				<div class="line">
					
							Medical Ceritificate:<form:input  path="mcFile" type="file" id="mcFile" /><br>
					
					
							Fitness Ceritificate:<form:input path="fitnessFile" type="file" id="fitnessFile" /><br>
					
					
							Other Ceritificate:<form:input path="otherCertFile" type="file" id="otherCertFile" /><br>
					
							
				</div>
			     <div class="quarter">
							 <div style="margin-left:45%">
								<a href="javascript:updateUploadedFiles('${workflowmap.leaveRequestDetails.medicalCertName}','${workflowmap.leaveRequestDetails.fitnessCertName}','${workflowmap.leaveRequestDetails.otherCertName}');"><div class="appbutton" id="button" style="display:show">Upload</div></a>
							</div>
				</div>
			</fieldset>
		</div>
	</c:if>--%>
	<c:if test="${not empty workflowmap.requestExceptionList}">
		<div class="line">
			<fieldset><legend><strong><font class="failure">Exceptions</font></strong></legend>
				<c:forEach var="empList" items="${workflowmap.requestExceptionList}">
					<div class="line"><img src="./images/arrow.jpg"></img> ${empList.exceptionDetails.description}</div>
				</c:forEach> 
			</fieldset>	
		</div>	
	</c:if>
</div>
<!-- End : LeaveDetails.jsp -->