<!-- Begin : DemandRequestDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<div class="line">
	<div class="line">
		<div class="quarter bold">Inventory Number</div>
		<div class="quarter">${workflowmap.voucherRequestDetails.invNum}</div>
	</div>
	<div class="line">
		<div class="quarter bold">Voucher Number</div>
		<div class="quarter">${workflowmap.voucherRequestDetails.voucherId}</div>
	</div>
	<div class="line">
		<div class="quarter bold">Voucher Type</div>
		<div class="quarter">${workflowmap.voucherRequestDetails.vouherTypeName}</div>
	</div>
	
	<div class="line">
		<div class="quarter bold">Reference Number</div>
		<div class="quarter">${workflowmap.voucherRequestDetails.referenceNo}</div>
	</div>
	<c:choose>
	<c:when test="${workflowmap.voucherRequestDetails.voucherTypeId eq '8'}">
		<div class="headTitle">Voucher Work Details</div>
	<div class="line">
		<table width="100%" cellpadding="2" cellspacing="0" border="1" class="sub_2">
			<tr>
				<th width="33%">Work Details</th>
				<th width="33%">Work Amount</th>
				<th width="33%">Division Control Number</th>
			</tr>
			<tr>
				<td>&nbsp;${workflowmap.voucherRequestDetails.workDetails}</td>
				<td>&nbsp;${workflowmap.voucherRequestDetails.workAmount}</td>
				<td>&nbsp;${workflowmap.voucherRequestDetails.divControlNum}</td>
			</tr>
		</table>							
	</div>
	</c:when>
	<c:otherwise>
	<div class="headTitle">Voucher Material Details</div>
	<div class="line">
		<table width="100%" cellpadding="2" cellspacing="0" border="1" class="sub_2">
			<tr>
				<th width="15%">SNo</th>
				<th width="33%">Nomenclature</th>
				<th width="33%">Material Code</th>
				<th width="10%">Qty</th>
				<th width="15%">U/M</th>
				<c:forEach items="${sessionScope.JsonVoucherList}" var="voucherList" varStatus="i" begin="0">
					<c:if test="${voucherList.uomConvert ne null}">
						<th width="15%">To U/M</th>
					</c:if>
				</c:forEach>
				<th width="33%">C/NC</th>	
				<c:forEach items="${sessionScope.JsonVoucherList}" var="voucherList" varStatus="i" begin="0">
					<c:if test="${voucherList.cncConvert ne null}">
						<th width="33%">To C/NC</th>
					</c:if>
				</c:forEach>	
			</tr>
			<c:forEach items="${sessionScope.JsonVoucherList}" var="voucherList" varStatus="i" begin="0">
				<tr>
					<td>&nbsp;<c:out value="${i.count}"/></td>
					<td>&nbsp;${voucherList.materialName}</td>
					<td>&nbsp;${voucherList.materialCode}</td>
					<td>&nbsp;${voucherList.qty}</td>
					<td>&nbsp;${voucherList.uom}</td>
					<td>&nbsp;${voucherList.uomConvert}</td>
					<td>&nbsp;${voucherList.cflag}</td>	
					<td>&nbsp;${voucherList.cncConvert}</td>	
									
											
				</tr>
			</c:forEach>
		</table>							
	</div>
	</c:otherwise>
	</c:choose>
	
</div>
<script>displayResult('${message}');</script>
<!-- End : RequestHistoryDetails.jsp -->