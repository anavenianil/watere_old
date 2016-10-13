<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ErpLeaveCancelHome.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<!--three lines added by bkr 17/06/2016  -->
<%--  <%@ taglib uri="/tags/displaytag" prefix="display" %> 
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/jstl-fn" prefix="fn" %> --%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css"
	rel="stylesheet" />
<!-- <link href="styles/calendar.css" type="text/css" rel="stylesheet"/> -->

<!-- <script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script> -->
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/ajaxUpload.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<script type="text/javascript" src="script/script.js"></script>

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>

<title>Leave Application Form</title>


<!--three style block added by bkr 17/06/2016  -->
<!-- <style type="text/css">
	a:link {
	}
	a:visited {
	}
	a:hover {
		color: red;
	}
	a:active {
	}
</style> -->
</head>

<body>
	<form:form method="post" commandName="LeaveWaterRequest" id="LeaveWaterRequest">
		<div>
			<div class="Innermaindiv">
				<div class="header"></div>
				<div><jsp:include page="MenuLinks.jsp" /></div>
				<div class="mainDisplay">
					<div class="whitebox1">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								<div class="whitebox_tr"></div>
							</div>
						</div>
						<div class="whitebox_m1">
							<%-- 	${LeaveWaterRequest.setErpAppliedLeavesList } --%>

						
							<div class="lightblueBg1">
						
								<div class="headTitle" id="title">Leave Application Form</div>
								<div>
								<div class="line" id="result"></div>
							
									<div class="line">
											<div class="line">
												<div class="half" align="right">
												<b>Nature Of Leave&nbsp;&nbsp;&nbsp; :&nbsp;&nbsp;&nbsp;</b>
												</div>
												
												<div class="half">
													<c:if test="${LeaveWaterRequest.erpAvailableLeaveSaveDTO.leaveType == 'AL'}">Annual Leave</c:if>
													<c:if test="${LeaveWaterRequest.erpAvailableLeaveSaveDTO.leaveType == 'SL'}">Sick Leave</c:if>
													<c:if test="${LeaveWaterRequest.erpAvailableLeaveSaveDTO.leaveType == 'CL'}">Compassionate Leave</c:if>
													<c:if test="${LeaveWaterRequest.erpAvailableLeaveSaveDTO.leaveType == 'ML'}">Maternity Leave</c:if>
													<c:if test="${LeaveWaterRequest.erpAvailableLeaveSaveDTO.leaveType == 'PL'}">Peternity Leave</c:if>
													
													<c:if test="${LeaveWaterRequest.erpAvailableLeaveSaveDTO.leaveType == 'ComL'}">Compassionate Leave</c:if>
												</div>
											</div>
											<div class="line">
												<div class="half" align="right">
													<b>From Date&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;</b>
												</div>
												<div class="half">${LeaveWaterRequest.erpAvailableLeaveSaveDTO.fromDate}</div>
											</div>
												<div class="line">
												<div class="half" align="right">
													<b>To Date&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;</b>
												</div>
												<div class="half">${LeaveWaterRequest.erpAvailableLeaveSaveDTO.toDate}</div>
											</div>
											</div>
											<div class="line">
												<div class="half" align="right">
													<b>Number Of Days &nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp; </b>
												</div>
												<div class="half">${LeaveWaterRequest.erpAvailableLeaveSaveDTO.noOfDays}</div>
											</div>
											<div class="line">
												<div class="half" align="right">
													<b>Reason For take Leave &nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp; </b>
												</div>
												<div class="half">${LeaveWaterRequest.erpAvailableLeaveSaveDTO.reason}</div>
											</div>
											<div class="line">
												<div class="half" align="right">
													<b>Contact Number &nbsp;&nbsp;&nbsp; :&nbsp;&nbsp;&nbsp; </b>
												</div>
												<div class="half">${LeaveWaterRequest.erpAvailableLeaveSaveDTO.contactNo}</div>
											</div>
											
												<div class="line">
												<div class="half" align="right">
													<b>  Reason for Cancellation &nbsp;&nbsp;&nbsp; :&nbsp;&nbsp;&nbsp; </b>
												</div>
												<div class="half"><form:textarea path="reasonCancellation" id="reasonCancellation"
													rows="3" cols="30" /></div>
											</div>
											
											<c:if test="${LeaveWaterRequest.erpAvailableLeaveSaveDTO.leaveStatus ne '5' }">
									
										<table width="100%" cellpadding="10" align="center">
											<tr>
											<td width="25%">&nbsp;</td><td width="25%">&nbsp;</td>
											<td width="25%">

												<div class="expbutton">
													<a onclick="javascript:submitErpLeaveCancelApplication('${LeaveWaterRequest.erpAvailableLeaveSaveDTO.noOfDays}','${LeaveWaterRequest.erpAvailableLeaveSaveDTO.leaveType}','ErpLeaveCancel','${LeaveWaterRequest.erpAvailableLeaveSaveDTO.requestId}')"> <span>Submit</span></a>
												</div>
												<div class="expbutton">
													<a onclick="javascript:clearErpLeaveApplication()"> <span>Clear</span></a>
												</div>
			
											</td>
											<td width="25%">&nbsp;</td>
											
										</tr> 
										
										</table>
										</c:if>
											<c:if test="${LeaveWaterRequest.erpAvailableLeaveSaveDTO.leaveStatus eq '5' }">
										<h2 align="center"><font color="green">You Are Already Apply CancelRequest</font></h2>
											</c:if>
									</div>
									<div>&nbsp;</div>
								</div>
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
		</div>
		<form:hidden path="type" />
		<form:hidden path="param" />
		<form:hidden path="requestID" />
		<form:hidden path="requestId" />
		<form:hidden path="dateCheck" />
		<form:hidden path="cancelRequestId" />
	</form:form>

	

</body>
</html>
<!-- End:LeaveApplication.jsp -->
