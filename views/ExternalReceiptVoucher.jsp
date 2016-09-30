<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ExternalReceiptVoucher.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
	<fieldset><legend><strong>
	<c:choose>
		<c:when test="${mmgVoucher.type eq 'ExternalReceiptVoucher'}">
			External Receipt Voucher
		</c:when>
		<c:when test="${mmgVoucher.type eq 'ExternalIssueVoucher'}">
			External Issue Voucher
		</c:when>
	</c:choose>
	
	</strong></legend>

		<div class="line">
			<div class="quarter">Voucher Number</div>
			<div class="quarter">
			<spring:bind path="mmgVoucher">
				<form:label path="mmgVoucher.voucherId" id="voucherId">${mmgVoucher.voucherId}</form:label>
			</spring:bind>
			</div>
			<div class="quarter">Voucher Date</div>
			<div class="quarter">
			<spring:bind path="mmgVoucher">
				<form:label path="mmgVoucher.voucherDate" id="voucherDate">${mmgVoucher.voucherDate}</form:label>
			</spring:bind>
			</div>
		</div>
		<div class="line">
			<div class="quarter">Inventory Number</div>
			<div class="quarter">
			<spring:bind path="mmgVoucher">
				<form:select path="mmgVoucher.inventoryNo" id="inventoryNo" cssStyle="width:145px">
					<form:option value="Select">Select</form:option>
					<c:forEach var="invHolders" items="${mmgVoucher.inventoryNumsList}">
						<form:option value="${invHolders.invId}">${invHolders.inventoryNo}</form:option>
					</c:forEach>
				</form:select>
			</spring:bind>
			</div>
			<div class="quarter">Other Lab/Firm Name</div>
			<div class="quarter">
			<spring:bind path="mmgVoucher">
				<form:input path="mmgVoucher.labName" id="labName"></form:input>
			</spring:bind>
			</div>
		</div>
		<div class="line">
			<div class="quarter">Location</div>
			<div class="quarter">
			<spring:bind path="mmgVoucher">
				<form:input path="mmgVoucher.location" id="location"></form:input>
			</spring:bind>
			</div>
			<div class="quarter">Reference Number</div>
			<div class="quarter">
			<spring:bind path="mmgVoucher">
				<form:input path="mmgVoucher.referenceNo" id="referenceNo"></form:input>
			</spring:bind>
			</div>
		</div>
	</fieldset>
	<script type="text/javascript">
		$jq('#expVoucherField').css("display","none");
		$jq('#itemDetails').css("display","block");
		$jq('#demandList').css("display","block");
		$jq('#qtyRate').css("display","block");
		$jq('#uomCnc').css("display","none");
	</script>
<!-- End:ExternalReceiptVoucher.jsp -->