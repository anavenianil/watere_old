<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- TadaAdvanceUpdate.jsp -->
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



<title>TaAdvanceCumRequest</title>

</head>
<body>
	<form:form method="post" commandName="tada" name="tadaWaterApproval"
		id="tadaWaterApproval">


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
								<div class="headTitle">TA Advance Sanctioned</div>
								<div>
									<div class="line" id="result"></div>
									<div>
										<b>Name & Designation:</b>&nbsp;${tada.empDetailsList.nameInServiceBook}&nbsp;&nbsp;&
										${tada.empDetailsList.designationDetails.name}<br /> </br> <b>Employee ID
											& Phone No. </b>:&nbsp;${tada.empDetailsList.userSfid}&nbsp;&nbsp;&
										&nbsp;${tada.empDetailsList.personalNumber}</br> </br>
									</div>



									<div>
										<div>
											<b>Directorate in which working</b> &nbsp;
											:&nbsp;${tada.deptDTO.deptName}
										</div>

									</div>
									<div>
										</br>
										<div>
											<b>This Claim is for : </b>${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.claimPurpose}
										</div>
										<div>

											<%-- <form:textarea path="claimPurpose" id="claimPurpose" rows="2" 
												cols="100" ></form:textarea> --%>
										</div>
										</br>
									</div>
									<fieldset style="border-color: green">
										<legend>
											<strong><font color='green'><b>Claim for
														Travelling on Duty</b></font></strong>
										</legend>
										<div>
											<table>
												<tr>
													<td><b>Travelling to :</b>
														${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.travellingTo}</td>
													<td></td>
													<%-- <td><form:textarea path="travellingTo" id="travellingTo"  
													rows="1" cols="30" /></td> --%>
												</tr>
												<tr>
													<td><b>Date from : </b> <fmt:formatDate
															pattern="dd-MMM-yyyy"
															value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.fromDate}" /></td>




													<%-- <td><form:input path="fromDate" id="fromDate" value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.fromDate}"
													cssClass="dateClass" readonly="true" onchange="javascript:noOfdays()"
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
												</script></td> --%>
													<td><b>Date to : </b> <fmt:formatDate
															pattern="dd-MMM-yyyy"
															value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.toDate}" /></td>



													<%-- <td><form:input path="toDate" id="toDate"  value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.toDate}"
													cssClass="dateClass" readonly="true"  onchange="javascript:noOfdays()"
													onclick="javascript:clearDateText('toDate');" /> &nbsp; <img
												src="./images/calendar.gif" id="date_start_trigger4" /> <script
													type="text/javascript">
													Calendar
															.setup({
																inputField : "toDate1",
																ifFormat : "%d-%b-%Y",
																showsTime : false,
																button : "date_start_trigger4",
																singleClick : true,
																step : 1
															});
												</script></td> --%>

												</tr>
												<tr>
													<td><b>No.of Days :</b></td>
													<td><form:input path="noOfDays" id="noOfDays"
															readonly="true" maxlength="30"
															value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.noOfDays}"
															onkeypress="javascript:return checkInt(event);"
															onkeyup="javascript:multiplyFunction()" /></td>
													<td><b>Per Day Amount</br> ( Night out Allowance ):
													</b></td>
													<td><form:input path="perDayFoodandAccmAmt"
															id="perDayFoodandAccmAmt" maxlength="30"
															value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.perDayFoodandAccmAmt}"
															onkeypress="javascript:return checkInt(event);"
															onkeyup="javascript:multiplyFunction()" /></td>
													</br>


												</tr>
												<tr>
													<td><b>Total Amount For Night out Allowance :</b></td>
													<td><form:input path="foodandAccmAmt" readonly="true"
															id="foodandAccmAmt" maxlength="30"
															value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.foodandAccmAmt}"
															onkeypress="javascript:return checkInt(event);"
															onkeyup="javascript:multiplyFunction()" /></td>
													<td><b>Transit Amount :</b></td>
													<td><form:input path="transitAmt" id="transitAmt"
															maxlength="30"
															value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.transitAmt}"
															onkeypress="javascript:return checkInt(event);"
															onkeyup="javascript:multiplyFunction()" /></td>
													</br>

												</tr>
												<tr>

													<td><b>Fare amount :</b></td>
													<td><form:input path="daAmt" id="daAmt" maxlength="30"
															value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.daAmt}"
															onkeypress="javascript:return checkInt(event);"
															onkeyup="javascript:multiplyFunction()" /></td>
													</br>
												</tr>
												<tr>
													<td><b>Taxi Amount :</b></td>
													<td><form:input path="taxiAmt" id="taxiAmt"
															maxlength="30"
															value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.taxiAmt}"
															onkeypress="javascript:return checkInt(event);"
															onkeyup="javascript:multiplyFunction()" /></td>
													<td><b>Total Amount For All :</b></td>
													<td><form:input path="totalAmt" id="totalAmt"
															readonly="true" maxlength="30"
															value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.totalAmt}"
															onkeypress="javascript:return checkInt(event);"
															onkeyup="javascript:multiplyFunction()" /></td>
													</br>
												</tr>


												<tr>
													<td><b>PV NO :</b> <span class="mandatory">*</span></td>
													<td><form:input path="dvno" id="dvno" /></td>

													<td><b>PV Date :</b> <span class="mandatory">*</span></td>
													<td><form:input path="dvDate" id="dvDate"
															cssClass="dateClass" readonly="true"
															onclick="javascript:clearDateText('dvDate');" /> &nbsp;
														<img src="./images/calendar.gif" id="date_start_trigger5" />
														<script type="text/javascript">
															Calendar
																	.setup({
																		inputField : "dvDate",
																		ifFormat : "%d-%b-%Y",
																		showsTime : false,
																		button : "date_start_trigger5",
																		singleClick : true,
																		step : 1
																	});
														</script></td>

												</tr>

												<tr>
													<td><b>Mode Of Payment </b><span class="mandatory">*</span>

														<form:radiobutton path="cashorcheck" id="cashorcheck1"
															value="CHEQUE" onclick="javascript:chequeFields('show')" />
														<label>Cheque</label> <form:radiobutton path="cashorcheck"
															id="cashorcheck2" value="CASH"
															onclick="javascript:chequeFields('hide')" /> <label>Cash</label></td>
												</tr>

												<tr>
													<td colspan="4">
														<table  id="trdata" style="display: none">
															<tr>
																<td>Bank Name :  <span class="mandatory">*</span>
																<%-- <form:input path="advBankName" id="advBankName" /> --%>
																<select name="advBankName" path="advBankName" id="advBankName">
																		<c:forEach var="item"
																			items="${tadaWaterRequestBean.bankNamesList}">
																			<option value="${item.bankid}">${item.bankName}</option>
																		</c:forEach>
																</select>
																
																
																</td>
																<td>Branch Name :  <span class="mandatory">*</span><form:input path="advBranchName" id="advBranchName" /></td>
																<td>Cheque No. :  <span class="mandatory">*</span><form:input path="advChequeNo" id="advChequeNo" /></td>
															</tr>
															<tr style="display: none">
																<td>dvno :  <span class="mandatory">*</span><form:input path="advDvno" id="advDvno" />DVDate. :  <span class="mandatory">*</span></td>
																
																<td> <form:input path="advDvDate" id="advDvDate"
															cssClass="dateClass" readonly="true"
															onclick="javascript:clearDateText('advDvDate');" /> &nbsp;
														<img src="./images/calendar.gif" id="date_start_trigger6" />
														<script type="text/javascript">
															Calendar
																	.setup({
																		inputField : "advDvDate",
																		ifFormat : "%d-%b-%Y",
																		showsTime : false,
																		button : "date_start_trigger6",
																		singleClick : true,
																		step : 1
																	});
														</script></td>
																
																
															</tr>
															<%-- <tr>
															<td>${sessionScope.bankNamesList}</td>
															</tr> --%>
														</table>
													</td>

												</tr>




											</table>
											<div class="line"></div>
											<div class="line" style="margin-left: 35%;">
												<div class="expbutton">
													<a onclick="javascript:updateTadaAdvance()"> <span>Submit</span></a>
												</div>
												<div class="expbutton">
													<a onclick="javascript:clearUpdateTadaAdvance()"> <span>Clear</span></a>
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
		<form:hidden path="requestID"
			value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.requestId}" />
		<form:hidden path="requestId"
			value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.requestId}" />
	</form:form>

</body>
</html>
<!-- End:TadaAdvanceUpdate.jsp -->
