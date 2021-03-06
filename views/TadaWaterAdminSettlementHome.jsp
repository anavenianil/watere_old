<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- TadaWaterAdminSettlementHome.jsp-->
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
								<div class="headTitle">TA ADMIN SETTLEMENT</div>
								<div>
									<div class="line" id="result"></div>
									<div>
										<b>Name & Designation:</b>&nbsp;${tada.empDetailsList.nameInServiceBook}&nbsp;&nbsp;&
										${tada.empDetailsList.designationDetails.name}<br /> </br> <b>Employee Id.
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
												</tr>
												<tr>
													<td><b>Date from :</b> <fmt:formatDate
															pattern="dd-MMM-yyyy"
															value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.fromDate}" />
													</td>
													<td><b>Date to :</b>
													<fmt:formatDate
															pattern="dd-MMM-yyyy"
															value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.toDate}" />
														</td>
												</tr>
												<tr>
													<td><b>No.of Days :</b>
														${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.noOfDays}</td>
													<td><b>Per Day Amount</br> ( Night out Allowance ):
													</b>
													TZS&nbsp;	${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.perDayFoodandAccmAmt} /-
													</td>



												</tr>
												<tr>
													<td><b>Total Amount For Night out Allowance :</b>
														TZS&nbsp;${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.foodandAccmAmt} /-</td>
													<td><b>Transit Amount :</b>
														TZS&nbsp; ${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.transitAmt} /-</td>



												</tr>
												<tr>

													<td><b>Fare amount :</b>
														TZS&nbsp;${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.daAmt} /-</td>

												</tr>
												<tr>
													<td><b>Taxi Amount :</b>
														TZS&nbsp;${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.taxiAmt} /-</td>

													<td><b>Total Amount For All :</b></td>
													<td><form:input path="totalAmt" id="totalAmt"
															readonly="true" maxlength="30" size="8"
															value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.totalAmt}"
															onkeypress="javascript:return checkInt(event);"
															onkeyup="javascript:multiplyFunction()" /></td>
													</br>
												</tr>




												<tr>
													<td><b>Amount Issed By :</b>
														${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.cashorcheck}</td>
													<td><b>Actual Expenditure For All :</b></td>
													<td><form:input path="actualExpenditure"
															id="actualExpenditure"
															value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.actualExpenditure}"
															maxlength="30" size="8" readonly="true"
															onkeypress="javascript:return checkInt(event);"
															onkeyup="javascript:settlementOrReimAmt()" /></td>
												</tr>

												<tr>
													<td><b>Settlement Amount</b></td>
													<td><form:input path="settleOrReimAmt"
															id="settleOrReimAmt"
															value="${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.settleOrReimAmt}"
															maxlength="30" size="8" readonly="true"
															onkeypress="javascript:return checkInt(event);" /></td>

												</tr>
													<tr>
												<td><b>User Remarks :</b></td>
												<td><textarea readonly="readonly">${tadaWaterRequestBean.tadaWaterApprovalRequestDTO.selOrReimRemarks}</textarea>
												</td>
												</tr>
												
												
												<tr>
													<td><b>Remarks : </b></td>
													<td><form:textarea path="settlementRemarks"
															id="settlementRemarks" rows="2" cols="30" /></td>
												</tr>
												<tr>
													<td><b>Settlement Date :</b><span class="mandatory">*</span></td>
													<td><form:input path="waterSettlementDate"
															id="waterSettlementDate" cssClass="dateClass"
															readonly="true" 
															onclick="javascript:clearDateText('waterSettlementDate');" />
														&nbsp; <img src="./images/calendar.gif"
														id="date_start_trigger3" /> <script
															type="text/javascript">
															Calendar
																	.setup({
																		inputField : "waterSettlementDate",
																		ifFormat : "%d-%b-%Y",
																		showsTime : false,
																		button : "date_start_trigger3",
																		singleClick : true,
																		step : 1
																	});
														</script></td>

												</tr>



											</table>

											<div class="line" style="margin-left: 35%;">
												<div class="expbutton">
													<a onclick="javascript:tadaAdminWaterSettlement()"> <span>Submit</span></a>
												</div>
												<div class="expbutton">
													<a onclick="javascript:cleaTadaAdminWaterSettlement()">
														<span>Clear</span>
													</a>
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
<!-- End:TadaWaterAdminSettlementHome.jsp -->
