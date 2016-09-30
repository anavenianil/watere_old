<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ERPLeaveApplication.jsp -->

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
	<form:form method="post" commandName="LeaveWaterRequest" id="LeaveWaterRequest" enctype="multipart/form-data">
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

							<c:if test="${LeaveWaterRequest.eorN == 'Eligible' }">
							<div class="lightblueBg1">
						
								<div class="headTitle" id="title">Leave Application Form</div>
								<div>
								<div class="line" id="result"></div>
							
									<div class="line">
										<table cellpadding="5" align="center">
										<tr>
											<td  align="right"><b>Nature Of Leave :<span class="mandatory">*</span> </b></td>
											<td colspan="3">
											<select name="ltcYear" path="leaveType" id="leaveType" onchange="javascript:emptyDateFileds();docterCertificateDeatils();thisLeaveAvailOrNot();" >
														<c:forEach var="item" items="${LeaveWaterRequest.erpAvailTypeleavesList}">
															<option value="${item.leaveCode}">${item.natureofLeave}</option>
														</c:forEach>
												</select>
											</td>
										</tr>
										
										<tr>
											<td  align="right"><b>From Date </b><b>:<span class="mandatory">*</span></b></td>
											<td colspan="3"><form:input path="fromDate" id="fromDate"
														cssClass="dateClass" readonly="true" onchange="javascript:noOfdays();dateChecking();checkAppliedDays()"
														 /> &nbsp;
													<img src="./images/calendar.gif" id="date_start_trigger3" />
													<script type="text/javascript">
														Calendar
																.setup({
																	inputField : "fromDate",
																	ifFormat : "%d-%b-%Y",
																	showsTime : false,
																	button : "date_start_trigger3",
																	singleClick : true,
																	step : 1
																});
													</script>
												</td>
										</tr>
										
										<tr>
											<td align="right"><b>To Date </b><b>:<span class="mandatory">*</span></b></td>
											<td colspan="3"><form:input path="toDate" id="toDate"
														cssClass="dateClass" readonly="true"  onchange="javascript:noOfdays();todateChecking();checkAppliedDays()"
														/> &nbsp;
													<img src="./images/calendar.gif" id="date_start_trigger4" />
													<script type="text/javascript">
														Calendar
																.setup({
																	inputField : "toDate",
																	ifFormat : "%d-%b-%Y",
																	showsTime : false,
																	button : "date_start_trigger4",
																	singleClick : true,
																	step : 1
																});
													</script>
												</td>
										</tr>
										
										<tr>
											<td align="right"><b>Number Of Days :<span class="mandatory">*</span></b></td>
											<td colspan="3">
												<form:input path="noOfDays" id="noOfDays" maxlength="30" readonly="true"/>
											</td>
										</tr>
										
										
									<tr >
									
										<td align="right" id="tddata4"><b>Prescription Copy Available <span class="mandatory">*</span></b></td>
										<td   id="tddata5">&nbsp;</td>
										<td id="tddata3">	<form:radiobutton path="prescriptionYorN"
												id="prescription1" value="YES"
												onclick="javascript:prescriptionBrowse('show')"  /> <label>Yes</label>
												<form:radiobutton path="prescriptionYorN"
												onclick="javascript:prescriptionBrowse('hide')"
												id="prescription2" value="NO" /> <label>No</label></td>
									</tr>

								<%-- 	<tr  >
									<td align="right" id="tddata"><b>Prescription Copy : <span class="mandatory"></span></b></td>
										<!-- <td align="right"><b>Prescription Copy :<span class="mandatory"></span></b></td> -->
										<td colspan="3" >
										<div id="tddata1">
										<form:input path="prescriptiondoc" type="file" id="prescriptiondoc" />
										</div>
										</td>
									</tr>
									<tr  > --%>
									<tr>
									<td align="right" id="tddata"><b>Prescription Copy : <span class="mandatory"></span></b></td>
										<!-- <td align="right"><b>Prescription Copy :<span class="mandatory"></span></b></td> -->
										<td colspan="3" >
										<div id="tddata1">
											<form:input path="prescriptionFile" type="file" id="files" />	
										</div>
										</td>
									</tr>
									
									
									
									<%-- 
									
									<tr>
									<div class="line">

											<div class="quarter leftmar">Prescription Copy</div>
											<div class="half">
												<form:input path="prescriptionFile" type="file" id="files" />	
											</div>
									</div>
									</tr> --%>
										
									
										
										
										<tr>
											<td align="right"><b>Reason </b><span class="mandatory">*</span></td>
											<td colspan="3"><form:textarea path="reason" id="reason"
													rows="3" cols="30" /></td>
										</tr>
										
										<tr>
											<td align="right"><b>Contact Number :<span class="mandatory">*</span></b></td>
											<td colspan="3">
												<form:input path="contactNo" id="contactNo" maxlength="30" onkeypress="javascript:return checkInt(event);" />
											</td>
										</tr>
									
										</table>
										<table width="100%" cellpadding="10" align="center">
											<tr>
											<td width="25%">&nbsp;</td><td width="25%">&nbsp;</td>
											<td width="25%">

												<div class="expbutton">
													<a onclick="javascript:submitErpLeaveApplication()"> <span>Submit</span></a>
												</div>
												<div class="expbutton">
													<a onclick="javascript:clearErpLeaveApplication()"> <span>Clear</span></a>
												</div>
			
											</td>
											<td width="25%">&nbsp;</td>
											
										</tr> 
										
										</table>
									</div>
									<div>&nbsp;</div>
								</div>
							</div>
							</c:if>
							<c:if test="${LeaveWaterRequest.eorN == 'NOTEligible'}">
							
							Your not eligible for leave Please Contact Your Admin
							
							
							</c:if>
							
									<div class="line"><hr /></div>
										<div class="line" id="leaves">
										
											<jsp:include page="ErpAppliedLeavesList.jsp"></jsp:include>
										</div>
										<div>&nbsp;</div>
					
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
		<form:hidden path="leaveTypeCopy" />
		<form:hidden path="prescriptiondoc" id="prescriptiondoc" />
			<form:hidden path="prescriptionDOC"  />
	</form:form>
	<script type="text/javascript">
	
	$jq('#fromDate').val('');
	$jq('#toDate').val('');
	
	$jq('#contactNo').val('');
	$jq('#noOfDays').val('');$jq('#reason').val('');
	
	//document.getElementById('prescription1').checked='checked';
		$jq('#tddata').css('display', 'none');
		$jq('#tddata1').css('display', 'none');
		$jq('#tddata4').css('display', 'none');
		$jq('#tddata3').css('display', 'none');
	</script>
	

</body>
</html>
<!-- End:LeaveApplication.jsp -->
