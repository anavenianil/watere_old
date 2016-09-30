<!-- Begin : InvoiceRequestDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div class="line">
	<div class="line">
		<div class="quarter bold">Inventory Number</div>
		<div class="quarter">${workflowmap.invoiceRequestDetails.invId}</div>
	</div>
	<div class="line">
		<div class="quarter bold">Purchase Type</div>
		<div class="quarter">Buildup</div>
	</div>
	<div class="line">
		<div class="quarter bold">Demand Type</div>
		<div class="quarter">Cash Purchase</div>
	</div>
	<div class="line">
		<div class="quarter bold">Group Demand Number</div>
		<div class="quarter">${workflowmap.invoiceRequestDetails.demandNo}</div>
	</div>
	<div class="line">
		<div class="quarter bold">Invoice Number</div>
		<div class="quarter">${workflowmap.invoiceRequestDetails.voucherNo}</div>
	</div>
	<div class="line">
		<div class="quarter bold">Voucher Date</div>
		<div class="quarter"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.invoiceRequestDetails.voucherDate}" /></div>
	</div>
	<div class="line">
		<input type="checkbox" name="hardCopyFlag" value="1" id="hardCopyFlag"><span class="bold">Hard Copy Received</span>
	</div>
	<div class="headTitle">Invoice Material Details</div>
	<div class="line">
		<table width="100%" cellpadding="2" cellspacing="0" border="1" class="sub_2">
			<tr>
				<th width="15%">SNo</th>
				<th width="33%">Nomenclature</th>
				<th width="10%">Qty</th>
				<th width="15%">UOM</th>
				<th width="20%">Unit Rate(<b><del>&#2352;</del></b>)</th>
				<th width="20%">Tax(<b><del>&#2352;</del></b>)</th>	
				<th width="33%">Total Cost(<b><del>&#2352;</del></b>)</th>
				<th width="33%">Memo No</th>	
				<th width="33%">Memo Date</th>				
			</tr>
			<c:forEach items="${sessionScope.JsonInvoiceList}" var="demand" varStatus="i" begin="0">
				<tr>
					<td>&nbsp;<c:out value="${i.count}"/></td>
					<td>&nbsp;${demand.description}</td>
					<td>&nbsp;${demand.qty}</td>
					<td>&nbsp;${demand.uom}</td>
					<td style="text-align:right">&nbsp;<fmt:formatNumber  pattern="##0.00" type="number" maxFractionDigits="2" value="${demand.unitRate}"/></td>					
					<td style="text-align:right">&nbsp;<fmt:formatNumber  pattern="##0.00" type="number" maxFractionDigits="2" value="${demand.taxAmount}"/></td>
					<td style="text-align:right">&nbsp;<fmt:formatNumber  pattern="##0.00" type="number" maxFractionDigits="2" value="${demand.amount}"/></td>
					<c:if test="${workflowmap.requestType eq 'INVOICE'}">
						<c:if test="${workflowmap.invoiceRequestDetails.approvedDept eq 'MMG'}">
							<td><input type="text" value="${demand.memoNo}" name="memoNo" id="taxType${i.count}"/></td>						
						</c:if>
						<c:if test="${workflowmap.invoiceRequestDetails.approvedDept ne 'MMG'}">	
							<td>&nbsp;${demand.memoNo}</td>	
						</c:if>
					</c:if>
					<c:if test="${workflowmap.requestType eq 'INVOICECANCEL'}">
						<td>&nbsp;${demand.memoNo}</td>	
					</c:if>
					<td>&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${demand.memoDate}"/></td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="1">Total :</td>
				<td colspan="6" style="text-align:right"><fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${workflowmap.invoiceRequestDetails.totalAmount}"/></td>
				<td>&nbsp;</td>
			</tr>
		</table>
		</div>
		<br/>
		<c:if test="${workflowmap.requestType eq 'INVOICE'}">
			<c:if test="${workflowmap.invoiceRequestDetails.approvedDept eq 'FINANCE'}">	
					<fieldset>
					<div class="line">
						<input type="radio" name="paymentTypeId" value="2">Settlement Amount
					</div>
					<div class="line">
					<div class="quarter"><input type="radio" name="paymentType" value="1">Credit</div>
					<div class="quarter"><input type="radio" name="paymentType" value="1">Debit</div>
					<div class="quarter">Amount</div>
					<div class="quarter">
						<input type="text" name="settleAmount"></input>
					</div>
					
					</div>
					<div class="line">
						<div class="quarter">Date Of Settlement</div>
						<div class="quarter">${workflowmap.paymentDate}</div>
						<div class="quarter">Description</div>
					<div class="quarter"><input type="text" name="description"></input></div>
					</div>
				 </fieldset>
			 </c:if>
	 </c:if>
	 <c:if test="${ not empty workflowmap.invoiceRequestDetails.fundDetails}">
		<div class="headTitle">Demand Payment Details</div>
			<div class="line" >
				<table width="100%" cellpadding="2" cellspacing="0" border="1" id="demandMaterial" class="sub_2">
					<tr>
						<th width="5%">SNo</th>
						<th width="20%">DemandNo</th>
						<th width="5%">Payment Type</th>
						<th width="5%">Fund Amount</th>
						<th width="15%">Payment Date</th>
					</tr>
					<c:forEach items="${workflowmap.invoiceRequestDetails.fundDetails}" var="payment" varStatus="i" begin="0">
						<tr>
							<td>&nbsp;<c:out value="${i.count}"/></td>
							<td>&nbsp;${workflowmap.invoiceRequestDetails.demandNo}</td>
							<td>&nbsp;
								<c:if test="${payment.paymentTypeId eq '1'}">
									Advance
								</c:if>
								<c:if test="${payment.paymentTypeId eq '2'}">
									Settlement
								</c:if>
							</td>
							<td style="text-align:right">&nbsp;<fmt:formatNumber  pattern="###.00" type="number" maxFractionDigits="2" value="${payment.fundAmount}"/></td>
							<td>&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${payment.creationDate}"/></td>
						</tr>
					</c:forEach>					
				</table>
			</div>
	 </c:if>
	 <c:if test="${workflowmap.requestType eq 'INVOICECANCEL'}">
		<div class="line">
			<div class="quarter bold">Reason</div>
			<div class="threefourth">:&nbsp;<c:out  value="${workflowmap.invoiceRequestDetails.reason}"></c:out></div>	
		</div>
	</c:if>							
</div>	
<script>displayResult('${message}');</script>
<!-- End : InvoiceRequestDetails.jsp -->