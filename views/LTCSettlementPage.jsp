<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- LTCSettlementPage.jsp -->
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
<script type="text/javascript" src="script/ltc.js"></script>
<script type="text/javascript" src="script/tada.js"></script>



<title>Annual leave settlement</title>

</head>
<body>
	<form:form method="post" commandName="ltcwater"
		name="tadaWaterApproval" id="tadaWaterApproval">


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
								<div class="headTitle">Annual Leave Settlement/Reimbursement</div>
								<div>
									<div class="line" id="result"></div>
									<div>
										<b>Name & Designation:</b>&nbsp;${ltcwater.empDetailsList.nameInServiceBook}&nbsp;&nbsp;&
										${ltcwater.empDetailsList.designationDetails.name}<br /> </br> <b>SFNo.
											& Phone No. </b>:&nbsp;${ltcwater.empDetailsList.userSfid}&nbsp;&nbsp;&
										&nbsp;${ltcwater.empDetailsList.personalNumber}</br> </br>
									</div>



									<div>
										<div>
											<b>Directorate in which working</b> &nbsp;
											:&nbsp;${ltcwater.deptDTO.deptName}
										</div>

									</div>
									<div>
										</br>
										
										<div>

											<%-- <form:textarea path="claimPurpose" id="claimPurpose" rows="2" 
												cols="100" ></form:textarea> --%>
										</div>
										</br>
									</div>
									<fieldset style="border-color: green">
										<legend>
											<strong><font color='green'><b>recess advance issue</b></font></strong>
										</legend>
										<div>

											<table cellpadding="5">
												<tr>
													<td><b>Start Holiday Date</b></td>
													<td>:<fmt:formatDate pattern="dd-MMM-yyyy" value="${ltcwater.ltcWaterRequestDTO.startHoliday}" /></td>
													<%-- <td>: ${ltcwater.ltcWaterRequestDTO.startHoliday}</td> --%>
													<td><b>Return Holiday Date</b></td>
													<td>:<fmt:formatDate pattern="dd-MMM-yyyy" value="${ltcwater.ltcWaterRequestDTO.returnHoliday}" /></td>
													<%-- <td>: ${ltcwater.ltcWaterRequestDTO.returnHoliday}</td> --%>
												</tr>
												<tr>
													<td><b>Number Of Days</b></td>
													<td>: ${ltcwater.ltcWaterRequestDTO.nod}</td>
													<td><b>Leave Type</b></td>
													<td>: ${ltcwater.ltcWaterRequestDTO.leaveType}</td>
												</tr>
												<tr>
													<td><b>Number Of Adults Tickets</b></td>
													<td><form:input path="noOfAdultsTickets" id="noOfAdultsTickets" value="${ltcwater.ltcWaterRequestDTO.noOfAdultsTickets}" readonly="true"/></td>
													<td><form:input path="amountAdults" id="amountAdults"  onkeypress="javascript:return checkInt(event);"
															onkeyup="javascript:TotalAdultsAount()"  value="${ltcwater.ltcWaterRequestDTO.amountAdults}" readonly="true"  /></br>(Amount for each Person)</td>
													<td><form:input path="adultsTotAmt" id="adultsTotAmt"  value="${ltcwater.ltcWaterRequestDTO.adultsTotAmt}" readonly="true"/></br>(Total Amount for Adults)</td>
												</tr>
												<tr>
													<td><b>Number Of Children Tickets</b></td>
														<td><form:input path="noOfChildrenTickets" id="noOfChildrenTickets" value="${ltcwater.ltcWaterRequestDTO.noOfChildrenTickets}" readonly="true"/></td>
													<td><form:input path="amountChildren" id="amountChildren"  onkeypress="javascript:return checkInt(event);"
															onkeyup="javascript:TotalChildrenAmt()"    value="${ltcwater.ltcWaterRequestDTO.amountChildren}" readonly="true" /></br>(Amount for each Person)</td>
													<td><form:input path="childrenTotAmt" id="childrenTotAmt"   value="${ltcwater.ltcWaterRequestDTO.childrenTotAmt}" readonly="true"/></br>(Total Amount for Children)</td>
												</tr>
												<%-- <tr>
													<td><b>Reference Letter Date </b></td>
														<td><form:input path="refLetterDate" id="refLetterDate"
															cssClass="dateClass" readonly="true"
															onclick="javascript:clearDateText('refLetterDate');" /> &nbsp;
														<img src="./images/calendar.gif" id="date_start_trigger5" />
														<script type="text/javascript">
															Calendar
																	.setup({
																		inputField : "refLetterDate",
																		ifFormat : "%d-%b-%Y",
																		showsTime : false,
																		button : "date_start_trigger5",
																		singleClick : true,
																		step : 1
																	});
														</script></td>
													<td><b>Reference Letter No.</b></td>
													<td><form:input path="refLetterNo" id="refLetterNo" /> </td>
												</tr>
												<tr>
													<td><b>Other Amount </b>:</td>
													<td><form:input path="otherAmt" id="otherAmt" onkeyup="javascript:TotalAmountAll()"  onkeypress="javascript:return checkInt(event);"/></td>
													<td><b>Total Amount For All: </b>:</td>
													<td><form:input path="totalTicketsAmt" id="totalTicketsAmt" readonly="true" /></td>
												</tr>
												
												
												<tr>
													<td colspan="4">Amount Issued By <span class="mandatory">*</span>

														<form:radiobutton path="ltcadvcashorcheck"
															id="ltcadvcashorcheck1" value="CHEQUE"
															onclick="javascript:ltcadvChequeFields('show')" /> <label>Cheque</label>
														<form:radiobutton path="ltcadvcashorcheck"
															onclick="javascript:ltcadvChequeFields('hide')"
															id="ltcadvcashorcheck2" value="CASH" /> <label>Cash</label>

													</td>
												</tr>
												
												<tr>
													<td colspan="4">
														<table id="trdata" style="display: none">
																<tr>
																<td>Bank Name : <span class="mandatory">*</span> 
																<select name="ltcadvBankName" path="ltcadvBankName" id="ltcadvBankName">
																		<c:forEach var="item"
																			items="${ltcwater.bankNamesList}">
																			<option value="${item.bankid}">${item.bankName}</option>
																		</c:forEach>
																</select>
																
																
																
															</td>
																<td>Branch Name : <span class="mandatory">*</span>
																	<form:input path="ltcadvBranchName" id="ltcadvBranchName" /></td>
																<td>Cheque No. : <span class="mandatory">*</span> <form:input
																		path="ltcadvChequeNo" id="ltcadvChequeNo" /></td>
															</tr>
															
															
																<tr>
																<td>dvno : <span class="mandatory">*</span> <form:input
																		path="ltcadvAdminDvno" id="ltcadvAdminDvno" />DVDate. : <span
																	class="mandatory">*</span>
																</td>

																<td><form:input path="ltcadvAdminDvDate"
																		id="ltcadvAdminDvDate" cssClass="dateClass"
																		readonly="true"
																		onclick="javascript:clearDateText('ltcadvAdminDvDate');" />
																	&nbsp; <img src="./images/calendar.gif"
																	id="date_start_trigger7" /> <script
																		type="text/javascript">
																		Calendar
																				.setup({
																					inputField : "ltcadvAdminDvDate",
																					ifFormat : "%d-%b-%Y",
																					showsTime : false,
																					button : "date_start_trigger7",
																					singleClick : true,
																					step : 1
																				});
																	</script></td>


															</tr>
														
														</table>
													
													</td>
												</tr>
 --%>
 
 
 
 
 
 
 <tr>
													<td><b>Amount Issed By :</b>
														${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.cashorcheck}</td>
														
												</tr>
												<tr>
												<td><b>Total Issued Amount: </b>:</td>
													<td><form:input path="totalTicketsAmt" id="totalTicketsAmt" value="${ltcwater.ltcWaterRequestDTO.totalTicketsAmt}" readonly="true" /></td>
													<td><b>Actual Expenditure For All :</b></td>
																<td><form:input path="ltcactualExpenditure" id="ltcactualExpenditure"
															 maxlength="30" size="8"
 															
															onkeypress="javascript:return checkInt(event);"    onkeyup="javascript:ltcsettlementOrReimAmt()"
															/></td>
												</tr>
												<tr>
													<td><b>Settlement / Reimbursement Amount</b></td>
															<td><form:input path="ltcsettleOrReimAmt" id="ltcsettleOrReimAmt"  value="${ltcwater.ltcWaterRequestDTO.totalTicketsAmt}" 
															 maxlength="30" size="8"   readonly="true"
															onkeypress="javascript:return checkInt(event);" 
															/></td>
															
												
												</tr>
												<tr>
											<td><b>Remarks : </b><span class="mandatory">*</span></td>
											<td><form:textarea path="ltcselOrReimRemarks" id="ltcselOrReimRemarks"
													rows="2" cols="30" /></td>
										</tr>
										<tr>
										<td><b>Settlement / Reimbursement Apply Date : <span class="mandatory">*</span></b></td>
											<td><form:input path="ltcsettleOrReimApplyDate" id="ltcsettleOrReimApplyDate"
													cssClass="dateClass" readonly="true" onchange="javascript:noOfdays()"
													onclick="javascript:clearDateText('ltcsettleOrReimApplyDate');" /> &nbsp;
												<img src="./images/calendar.gif" id="date_start_trigger3" />
												<script type="text/javascript">
													Calendar
															.setup({
																inputField : "ltcsettleOrReimApplyDate",
																ifFormat : "%d-%b-%Y",
																showsTime : false,
																button : "date_start_trigger3",
																singleClick : true,
																step : 1
															});
												</script></td>
										
										</tr>
												<tr>
												<td colspan="2"><b>Note :</b> <font color="blue"> &nbsp;1 ) If Settlement / Reimbursement Amount negative means Your doing  Reimbursement</br>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2 ) If Settlement / Reimbursement Amount positive  means Your doing Settlement
												</font></td>
												
												</tr>


 
 
 
 
 
											</table>





											<div class="line"></div>
											<div class="line" style="margin-left: 35%;">
												<div class="expbutton">
													<a onclick="javascript:ltcWaterTadaASettlement()"> <span>Submit</span></a>
												</div>
												<div class="expbutton">
													<a onclick="javascript:clearltcWaterTadaASettlement()"> <span>Clear</span></a>
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


		<form:hidden path="param" />
		<form:hidden path="type" />
		<form:hidden path="requestID"
			value="${ltcWaterRequestBean.LTCWaterRequestDTO.requestId}" />
		<form:hidden path="requestId"
			value="${ltcWaterRequestBean.LTCWaterRequestDTO.requestId}" />
	</form:form>

</body>
</html>
<!-- End:LTCSettlementPage.jsp -->
