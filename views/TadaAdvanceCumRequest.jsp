<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:TadaAdvanceCumRequest.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css"
	rel="stylesheet" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript"
	src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/tada.js"></script>

<title>TadaAdvanceCumRequest1</title>

</head>
<body>
	<form:form method="post" commandName="tada" name="tadaWaterApproval" id="tadaWaterApproval">
		<div id="tada">
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
							<div class="lightblueBg1">
								<div class="headTitle">TA Advance Request</div>
								<div>
									<div class="line" id="result"></div>
									<div>
										<b>Name & Designation:</b>&nbsp;${tada.empDetailsList.nameInServiceBook}&nbsp;&nbsp;&
													${tada.empDetailsList.designationDetails.name}<br /> </br><b>Employee ID. & Phone No. </b>:&nbsp;${tada.empDetailsList.userSfid}&nbsp;&nbsp;&
													&nbsp;${tada.empDetailsList.personalNumber}</br> </br>
									</div>
									
									<div>
										<div><b>Directorate in which working</b> &nbsp; :&nbsp;${tada.deptDTO.deptName} </div>
										
									</div>
									<div>
									</br>
										<div>
											<b>This Claim is for</b> <span class="mandatory">*</span>
										</div>
										<div>
											<form:textarea path="claimPurpose" id="claimPurpose" rows="2"
												cols="100" />
										</div>
										</br>
									</div>
									<fieldset style="border-color: green"><legend><strong><font color='green'><b>Claim for Travelling on Duty</b></font></strong></legend>
									<div>
									<table >
										<tr>
											<td>Travelling to : <span class="mandatory">*</span></td>
											<td><form:textarea path="travellingTo" id="travellingTo"
													rows="1" cols="30" /></td>
										</tr>
										<tr>
											<td>Date from : <span class="mandatory">*</span></td>
											<td><form:input path="fromDate" id="fromDate"
													cssClass="dateClass" readonly="true" onchange="javascript:noOfdays();javascript:multiplyFunction()"
													onclick="javascript:clearDateText('fromDate');" /> &nbsp;
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
												</script></td>
											<td>Date to : <span class="mandatory">*</span></td>
											<td><form:input path="toDate" id="toDate"
													cssClass="dateClass" readonly="true"  onchange="javascript:noOfdays();javascript:multiplyFunction()"
													onclick="javascript:clearDateText('toDate');" /> &nbsp; <img
												src="./images/calendar.gif" id="date_start_trigger4" /> <script
													type="text/javascript">
													Calendar
															.setup({
																inputField : "toDate",
																ifFormat : "%d-%b-%Y",
																showsTime : false,
																button : "date_start_trigger4",
																singleClick : true,
																step : 1
															});
												</script></td>

										</tr>
										<tr>
											<td>No.of Days : <span class="mandatory">*</span></td>
											<td><form:input path="noOfDays" id="noOfDays"  readonly="true" 
													maxlength="30"
													onkeypress="javascript:return checkInt(event);"   onkeyup="javascript:multiplyFunction()"/></td>
											<td>Per Day Amount</br> ( Night out Allowance ): <span class="mandatory">*</span></td>
											<td><form:input path="perDayFoodandAccmAmt"
													id="perDayFoodandAccmAmt" maxlength="30" 
													onkeypress="javascript:return checkInt(event);"  onkeyup="javascript:multiplyFunction()" /></td>
											</br>


										</tr>
										<tr>
											<td>Total Amount For Night out Allowance :</td>
											<td><form:input path="foodandAccmAmt"  readonly="true"
													id="foodandAccmAmt" maxlength="30"
													onkeypress="javascript:return checkInt(event);"  onkeyup="javascript:multiplyFunction()"/></td>
											<td>Transit Amount : <span class="mandatory">*</span></td>
											<td><form:input path="transitAmt" id="transitAmt"
													maxlength="30"
													onkeypress="javascript:return checkInt(event);"    onkeyup="javascript:multiplyFunction()"/></td>
											</br>

										</tr>
										<tr>
											
											<td>Fare amount  : <span class="mandatory">*</span></td>
											<td><form:input path="daAmt" id="daAmt" 
													maxlength="30" 
													onkeypress="javascript:return checkInt(event);" onkeyup="javascript:multiplyFunction()"/></td>
											</br>
										</tr>
										<tr>
											<td>Taxi Amount : <span class="mandatory"></span></td>
											<td><form:input path="taxiAmt" id="taxiAmt"   
													maxlength="30"
													onkeypress="javascript:return checkInt(event);"  onkeyup="javascript:multiplyFunction()"/></td>
											<td>Total Amount For All :</td>
											<td><form:input path="totalAmt" id="totalAmt"  readonly="true" 
													maxlength="30"
													onkeypress="javascript:return checkInt(event);" onkeyup="javascript:multiplyFunction()" /></td>
											</br>
										</tr>
										
										
										<tr><td>&nbsp;</td></tr>
										
									</table>
									</div>
											<div class="line" style="margin-left: 35%;">
												<div class="expbutton">
													<a onclick="javascript:submitTadaAdvanceCumReq()"> <span>Submit</span></a>
												</div>
												<div class="expbutton">
													<a onclick="javascript:clearTadaAdvanceCumReq()"> <span>Clear</span></a>
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
					</div>
				</div>
			</div>
		</div>
		<div><jsp:include page="Footer.jsp" /></div>
		</div>
		
		<form:hidden path="param" />
		<form:hidden path="type" />
		<form:hidden path="requestID" />
		<form:hidden path="requestId" />
	</form:form>
	
	
	<script type="text/javascript">
	$jq('#fromDate').val('');
	$jq('#toDate').val('');
	
	</script>

</body>
</html>
<!-- End:TadaAdvanceCumRequest.jsp -->
