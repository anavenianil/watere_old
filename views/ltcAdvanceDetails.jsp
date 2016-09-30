<!-- Begin : ltcAdvanceDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<html>
<head>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
</head>
<body>

	<div class="line">
		<div class="headTitle"></div>
		<div class="line">
			<div class="quarter bold">LTC Type</div>
			<div class="quarter">:&nbsp;${workflowmap.ltcAdvanceRequestDTO.ltcTypeId}
			</div>
			<!-- <div class="quarter bold">LTC Block year</div> -->
			<div class="quarter bold">LTC Business year</div>
			<div class="quarter">:&nbsp;${workflowmap.ltcAdvanceRequestDTO.ltcBlockYearId}</div>
		</div>
		<div class="line">
			<div class="quarter bold">Place of Visit</div>
			<div class="quarter">:&nbsp;${workflowmap.ltcAdvanceRequestDTO.placeOfVisit}</div>

			<%-- <div class="quarter bold">Desired advanced amount</div>
			<div class="quarter">
				:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font>
				<fmt:formatNumber
					value="${workflowmap.ltcAdvanceRequestDTO.amountClaimed}" />
				/-
			</div> --%>

		</div>
		<c:if
			test="${not empty workflowmap.ltcAdvanceRequestDTO.leaveRequestId}">
			<div class="line">
				<div class="quarter bold">Leave Details</div>
				<div class="quarter">:&nbsp;${workflowmap.ltcAdvanceRequestDTO.leaveRequestId}</div>


				<div class="quarter bold">Leave RequestID</div>
				<div class="quarter">:&nbsp;${workflowmap.ltcAdvanceRequestDTO.leaveID}</div>
			</div>
		</c:if>
		<div class="line">
			<c:if
				test="${not empty workflowmap.ltcAdvanceRequestDTO.encashTypeId}">
				<div class="quarter bold">Encashment Type</div>
				<div class="quarter">:&nbsp;${workflowmap.ltcAdvanceRequestDTO.encashTypeId}</div>
			</c:if>
			<c:if
				test="${not empty workflowmap.ltcAdvanceRequestDTO.encashmentDays}">
				<!-- <div class="quarter bold">Encashment Days</div> -->
				<div class="quarter bold">Vacation Days</div>
				<div class="quarter">:&nbsp;${workflowmap.ltcAdvanceRequestDTO.encashmentDays}</div>
			</c:if>
		</div>
		<div class="line">
			<div class="quarter bold">Date of Departure</div>
			<div class="quarter" id="departureDate">
				:
				<fmt:formatDate
					value="${workflowmap.ltcAdvanceRequestDTO.departureDate}"
					pattern="dd-MMM-yyyy" />
			</div>
			<div class="quarter bold">Expected Date of Return</div>
			<div class="quarter">
				:&nbsp;
				<fmt:formatDate
					value="${workflowmap.ltcAdvanceRequestDTO.returnDate}"
					pattern="dd-MMM-yyyy" />
			</div>
		</div>

		<c:if test="${not empty workflowmap.ltcAdvanceRequestDTO.nearesrAirport}">
			<div class="line">
				<c:if test="${workflowmap.checkStage eq 'last'}">
					<!-- Reached here: 4 -->
					<div class="quarter bold">Issued advance amount</div>
					<c:if
						test="${workflowmap.checkStage eq 'last' && workflowmap.message eq 'pending' && empty workflowmap.ltcAdvanceRequestDTO.issuedAmount}">
						<!-- Reached here: 5 -->
						<div class="quarter">
							:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font><input
								type="text" id="issuedAmount" name="issuedAmount"
								value="${workflowmap.ltcAdvanceRequestDTO.issuedAmount}"
								readonly="readonly" />
						</div>

					</c:if>

					<c:if
						test="${not empty workflowmap.ltcAdvanceRequestDTO.issuedAmount}">
						<!-- Reached here: 6 -->
						<c:if
							test="${workflowmap.lastStagePendingCheck eq 'pending' && workflowmap.message eq 'completed'}">
							<!-- Reached here: 7 -->

							<div class="quarter">
								:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font>
								<fmt:formatNumber
									value="${workflowmap.ltcAdvanceRequestDTO.issuedAmount}" />
								/-
							</div>
						</c:if>

						<div></div>
						<div></div>
						<div></div>
						<c:if
							test="${workflowmap.lastStagePendingCheck ne 'completed' && workflowmap.message eq 'pending'}">
							<!-- Reached here: 8 -->
							<div class="quarter">
								:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font>
								<input type="hidden" id="issuedAmount1" name="issuedAmount1"
									value="${workflowmap.ltcAdvanceRequestDTO.issuedAmount}" /> <input
									type="text" id="issuedAmount" name="issuedAmount"
									value="${workflowmap.ltcAdvanceRequestDTO.issuedAmount}"
									onkeypress="javascript:return checkIntDecimal(event);" />
							</div>
						</c:if>

						<c:if
							test="${workflowmap.lastStagePendingCheck eq  'completed' || workflowmap.message eq 'myRequests'}">
							<!-- Reached here: 9 -->
							<div class="quarter">
								:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font>
								<fmt:formatNumber
									value="${workflowmap.ltcAdvanceRequestDTO.issuedAmount}" />
								/-
							</div>
						</c:if>
					</c:if>
				</c:if>

				<c:if
					test="${not empty workflowmap.ltcAdvanceRequestDTO.nearesrAirport && workflowmap.checkStage eq 'previous'}">
					<!-- Reached here: 10 -->
					<div class="quarter bold">Issued advance amount</div>
					<c:if
						test="${workflowmap.checkStage eq 'previous' && workflowmap.message eq 'pending' && empty workflowmap.ltcAdvanceRequestDTO.issuedAmount}">
						<!-- Reached here: 11 -->
						<div class="quarter">
							<input type="text" id="issuedAmount" name="issuedAmount" value="" />
						</div>

					</c:if>
					<c:if
						test="${not empty workflowmap.ltcAdvanceRequestDTO.issuedAmount}">
						<!-- Reached here: 12 -->
						<div class="quarter">
							:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font>
							<fmt:formatNumber
								value="${workflowmap.ltcAdvanceRequestDTO.issuedAmount}" />
							/-
						</div>

					</c:if>
				</c:if>
			</div>
			<c:if
				test="${not empty workflowmap.ltcAdvanceRequestDTO.encashmentDays}">
				<!-- Reached here: 13 -->
				<div class="line">
					<div class="quarter bold">Finance Issued Encashment Amount</div>
					<div class="quarter">
						<c:if
							test="${not empty workflowmap.ltcAdvanceRequestDTO.financeEncashmentAmt}">
							<!-- Reached here: 14 -->
					:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font>${workflowmap.ltcAdvanceRequestDTO.financeEncashmentAmt}/-
				</c:if>
						<c:if
							test="${empty workflowmap.ltcAdvanceRequestDTO.financeEncashmentAmt}">
							<!-- Reached here: 15 -->
							<font color='red'>:&nbsp;Finance Encashment Amount is not
								entered</font>
						</c:if>
					</div>
					<div class="quarter bold">CDA Issued Encashment Amount</div>
					<div class="quarter">
						<c:if
							test="${not empty workflowmap.ltcAdvanceRequestDTO.cdaEncashmentAmt}">
							<!-- Reached here: 16 -->
					:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font>${workflowmap.ltcAdvanceRequestDTO.cdaEncashmentAmt}/-
				</c:if>
						<c:if
							test="${empty workflowmap.ltcAdvanceRequestDTO.cdaEncashmentAmt}">
							<!-- Reached here: 17 -->
							<font color='red'>:&nbsp;CDA Encashment Amount is not
								entered</font>
						</c:if>
					</div>
				</div>
			</c:if>
		</c:if>

		<c:if
			test="${workflowmap.checkStage eq 'last' && workflowmap.message eq 'pending' && workflowmap.lastStagePendingCheck ne 'completed'}">
			<!-- Reached here: 18 -->
			<div class="line" id="financeEachAmountDiv">
				<div class="quarter bold">Amount per each full ticket(One way)</div>
				<div class="quarter">
					:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font><input
						type="text" name="amountPerPerson" id="amountPerPerson"
						value="${workflowmap.ltcAdvanceRequestDTO.amountPerPerson}"
						onkeypress="return checkIntDecimal(event);"
						onkeyup="javascript:calculateAdvancePercentage('${workflowmap.ltcAdvanceRequestDTO.amountClaimed}','${workflowmap.ltcAdvanceRequestDTO.advanceCfgValue}','${workflowmap.ltcAdvanceRequestDTO.noOfTickets}','${workflowmap.ltcAdvanceRequestDTO.noOfInfantTickets}');">
				</div>
				
				<!--added by bkr 19/04/2016 complete div  -->
				
				
				<div>
				<div class="quarter bold">OtherAmount</div>
				<div class="quarter">
					:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font><input
						type="text" name="doPartId" id="doPartId"
						value="${workflowmap.ltcAdvanceRequestDTO.doPartId}"
						onkeypress="return checkIntDecimal(event);">
				</div>
				
				</div>
				
				
				
				
				
				<c:if
					test="${not empty workflowmap.ltcAdvanceRequestDTO.noOfInfantTickets}">
					<!-- Reached here: 19 -->
					<div class="quarter bold">Amount per each infant/child
						ticket(One way)</div>
					<div class="quarter">
						:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font><input
							type="text" name="amountPerEachInfant" id="amountPerEachInfant"
							value="${workflowmap.ltcAdvanceRequestDTO.amountPerEachInfant}"
							onkeypress="return checkIntDecimal(event);"
							onkeyup="javascript:calculateAdvancePercentage('${workflowmap.ltcAdvanceRequestDTO.amountClaimed}','${workflowmap.ltcAdvanceRequestDTO.advanceCfgValue}','${workflowmap.ltcAdvanceRequestDTO.noOfTickets}','${workflowmap.ltcAdvanceRequestDTO.noOfInfantTickets}');">
					</div>
				</c:if>
			</div>
		</c:if>

		<c:if
			test="${workflowmap.checkStage eq 'previous' && not empty workflowmap.ltcAdvanceRequestDTO.nearesrAirport && workflowmap.message eq 'pending' && workflowmap.lastStagePendingCheck ne 'completed'}">
			<!-- Reached here: 21 -->
			<div class="line" id="financeEachAmountDiv">
				<div class="quarter bold">Amount per each full ticket(One way)</div>
				<div class="quarter">
					:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font><input
						type="text" name="amountPerPerson" id="amountPerPerson"
						value="${workflowmap.ltcAdvanceRequestDTO.amountPerPerson}"
						onkeypress="return checkIntDecimal(event);"
						onkeyup="javascript:calculateAdvancePercentage('${workflowmap.ltcAdvanceRequestDTO.amountClaimed}','${workflowmap.ltcAdvanceRequestDTO.advanceCfgValue}','${workflowmap.ltcAdvanceRequestDTO.noOfTickets}','${workflowmap.ltcAdvanceRequestDTO.noOfInfantTickets}');">
				</div>
				<!--added by bkr 19/04/2016 complete div  -->
				
				
				<div>
				<div class="quarter bold">OtherAmount</div>
				<div class="quarter">
					:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font><input
						type="text" name="doPartId" id="doPartId"
						value="${workflowmap.ltcAdvanceRequestDTO.doPartId}"
						onkeypress="return checkIntDecimal(event);">
				</div>
				
				</div>
				
				
				<c:if
					test="${not empty workflowmap.ltcAdvanceRequestDTO.noOfInfantTickets}">
					<!-- Reached here: 22 -->
					<div class="quarter bold">Amount per each infant/child
						ticket(One way)</div>
					<div class="quarter">
						:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font><input
							type="text" name="amountPerEachInfant" id="amountPerEachInfant"
							value="${workflowmap.ltcAdvanceRequestDTO.amountPerEachInfant}"
							onkeypress="return checkIntDecimal(event);"
							onkeyup="javascript:calculateAdvancePercentage('${workflowmap.ltcAdvanceRequestDTO.amountClaimed}','${workflowmap.ltcAdvanceRequestDTO.advanceCfgValue}','${workflowmap.ltcAdvanceRequestDTO.noOfTickets}','${workflowmap.ltcAdvanceRequestDTO.noOfInfantTickets}');">
					</div>
				</c:if>
			</div>
		</c:if>


		<div class="line">
			<div class="quarter bold">LTC Request Form</div>
			<div class="quarter">
				:&nbsp;<a
					href="javascript:getInitialReport('${workflowmap.requestId}','advanceForm1')">Print</a>
			</div>
			<div class="quarter bold">LTC Advance Form</div>
			<div class="quarter">
				:&nbsp;
				<c:if
					test="${not empty workflowmap.ltcAdvanceRequestDTO.issuedAmount}">
					<!-- Reached here: 23 -->
					<a
						href="javascript:getInitialReport('${workflowmap.requestId}','advanceForm2')">Print</a>
				</c:if>
				<c:if test="${empty workflowmap.ltcAdvanceRequestDTO.issuedAmount}">
					<!-- Reached here: 24 -->
					<b><span style="color: red">Don't forget to print & Sign
							report after finance approval from here</span></b>
				</c:if>
			</div>
		</div>

		<div class="line">
			<c:if test="${workflowmap.requestType eq 'LTC ADVANCE AMENDMENT'}">
				<!-- Reached here: 25 -->
				<div class="quarter bold">LTC Amendment Letter</div>
				<div class="quarter">
					:&nbsp;<a
						href="javascript:getInitialDocReport('${workflowmap.requestId}','ltcAdvanceAmendment')">Report
						in Doc</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
						href="javascript:getInitialPdfReport('${workflowmap.requestId}','ltcAdvanceAmendment')">Report
						in Pdf </a>
				</div>

			</c:if>
		</div>
		<div class="line">
			<c:if
				test="${not empty workflowmap.ltcAdvanceRequestDTO.escortDetails}">
				<!-- Reached here: 26 -->
				<div class="quarter bold">Escort Details</div>
				<div class="quarter">:&nbsp;${workflowmap.ltcApprovalRequestDTO.escortDetails}</div>
			</c:if>
		</div>
		<c:if
			test="${not empty workflowmap.ltcAdvanceRequestDTO.issuedAmount}">
			<!-- Reached here: 27 -->
			<div class="line">
				<div class="quarter bold">CDA Issued Amount</div>
				<div class="quarter">
					<c:if test="${empty workflowmap.ltcAdvanceRequestDTO.cdaAmount}">
						<!-- Reached here: 28 -->
						<font color='red'>:&nbsp;CDA Amount is not entered</font>
					</c:if>
					<c:if
						test="${not empty workflowmap.ltcAdvanceRequestDTO.cdaAmount}">
						<!-- Reached here: 29 -->
			:&nbsp;<font size="4.5em"><span class="WebRupee">R</span></font>${workflowmap.ltcAdvanceRequestDTO.cdaAmount}/-
		</c:if>

				</div>
			</div>
		</c:if>

		<div class="headTitle">Details of Members for LTC</div>
		<div class="line">
			<table border="1" width="100%" align="center" cellpadding="2"
				cellspacing="0" class="sub_2">
				<tr>
					<th>Name</th>
					<th>Date of Birth</th>
					<th>Age As On ${workflowmap.requestDate}</th>
					<th>Relation Ship with Govt. Servant</th>
				</tr>
				<c:forEach
					items="${workflowmap.ltcAdvanceRequestDTO.ltcMemberDetails}"
					var="membersList">
					<tr>
						<td width="30%">${membersList.familyMemberName}</td>
						<td width="20%">${membersList.dob}</td>
						<td width="10%">${membersList.age}</td>
						<td width="20%">${membersList.relation}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="line">
			<c:if test="${workflowmap.lastStagePendingCheck ne 'completed'}">
				<div class="line" id="individualNote" style="display: none">
					<br />
					<div style="colore: red">
						<font color='green'><b>Note for Individual - Pending
								Request </b></font>
					</div>
					<div>
						<font color='purple'><ol>
								<li>Finance Division will keep the <font color='red'>LTC
										Advance Request Pending till the submission of tickets and
										related proofs(Except LTC Advance Form)</font> to calculate
									permissible advance amount.
								</li>
								<li>Once the related documents are submitted finance will
									process the LTC Advance request.</li>
								<li>Then the LTC ADVANCE FORM is available for individual
									which is complete in all respects.</li>
								<li>Print,sign and submit the LTC ADVANCE FORM along with
									documents to Finance.</li>
							</ol> </font>
					</div>
				</div>
			</c:if>
		</div>
		<c:if
			test="${workflowmap.doPartShowFlag eq 'Y' && empty workflowmap.ltcAdvanceRequestDTO.doPartNo}">
			<!-- Reached here: 31 -->
			<fieldset>
				<legend>
					<strong><font color='green' id="emergency">Admin
							DoPart Details</font></strong>
				</legend>
				<div class="line">
					<%-- <div class="quarter bold">DO Part Date<span class="mandatory">*</span></div>
			<div class="quarter">
					<form:select path="doPartDate" id="doPartDate" cssStyle="width:145px;" onchange="javascript:getDoPartNumber('${workflowmap.ltcAdvanceRequestDTO.gazType}');">
														<form:option value="Select" label="Select"></form:option>
														<form:options items="${doPartDatewiseList}"></form:options>
													</form:select>
			</div> --%>
					<!--added by bkr 16/04/2016 calendar code instaed of dopartdate  -->
					<div class="quarter">
						DO Part Date<span class="mandatory">*</span>
					</div>
					<div class="quarter">
						<form:input path="doPartDate" id="doPartDate" cssClass="dateClass"
							readonly="true" onclick="javascript:clearDateText('doPartDate');" />
						&nbsp; <img src="./images/calendar.gif" id="date_start_trigger4" />
						<script type="text/javascript">
							Calendar.setup({
								inputField : "doPartDate",
								ifFormat : "%d-%b-%Y",
								showsTime : false,
								button : "date_start_trigger4",
								singleClick : true,
								step : 1
							});
						</script>
					</div>


					<div class="quarter bold">
						DO Part No<span class="mandatory">*</span>
					</div>
					<!-- <div class="quarter" id="doPartNumber"><input type="text" name="doPartNo" id="doPartNo" readonly="readonly"></input></div> -->
					<div class="quarter" id="doPartNumber">
						<input type="text" name="doPartNo" id="doPartNo"></input>
					</div>
				</div>
			</fieldset>
		</c:if>
		<c:if test="${not empty workflowmap.ltcAdvanceRequestDTO.doPartNo}">
			<!-- Reached here: 33 -->
			<input type="hidden" name="doPartDate" id="doPartDate"
				value="${workflowmap.ltcAdvanceRequestDTO.doPartDate}"></input>
			<input type="hidden" name="doPartNo" id="doPartNo"
				value="${workflowmap.ltcAdvanceRequestDTO.doNo}">
		</c:if>
	</div>

	<div class="line">
		<fieldset>
			<div class="quarter">
				<div>
					<c:if
						test="${ not empty workflowmap.ltcAdvanceRequestDTO.setlrequestid && workflowmap.ltcAdvanceRequestDTO.setlrequestid !=0}">
						<!-- Reached here: 34 -->
						<div class="quarter leftmar">
							Settlement RequestId:
							<div class="quarter">
								<a
									href="javascript:getRequestDetails('${workflowmap.ltcAdvanceRequestDTO.setlhistoryid}','${workflowmap.ltcAdvanceRequestDTO.setlrequestid}','myRequests','completed','')">${workflowmap.ltcAdvanceRequestDTO.setlrequestid}</a>
							</div>
						</div>
					</c:if>
				</div>
			</div>

		</fieldset>
	</div>
	<input type="hidden" id="statusMsg" />
	<input type="hidden" id="back" />
	<input type="hidden" id="message" />
	<input type="hidden" id="historyID" />
	<script type="text/javascript">
		// gazType is global variable
		gazType = '${workflowmap.ltcAdvanceRequestDTO.gazType}';
		calculateAdvancePercentage(
				'${workflowmap.ltcAdvanceRequestDTO.amountClaimed}',
				'${workflowmap.ltcAdvanceRequestDTO.advanceCfgValue}',
				'${workflowmap.ltcAdvanceRequestDTO.noOfTickets}',
				'${workflowmap.ltcAdvanceRequestDTO.noOfInfantTickets}');
		leaveStatus = '${workflowmap.ltcAdvanceRequestDTO.leaveStatus}';
		leaveID = '${workflowmap.ltcAdvanceRequestDTO.leaveID}';
	</script>
</body>
</html>



<!-- End : ltcAdvanceDetails.jsp -->