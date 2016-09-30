<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TransferVoucher.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
	<fieldset><legend><strong>Expense Voucher</strong></legend>

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
			<div class="quarter">Form Inventory</div>
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
			<div class="quarter">To Inventory</div>
			<div class="quarter">
				<spring:bind path="mmgVoucher">
					<form:select path="mmgVoucher.toInventory" id="toInventory" cssStyle="width:145px">
						<form:option value="Select">Select</form:option>
						<c:forEach var="toinvHolders" items="${mmgVoucher.toInventoryNumsList}">
							<form:option value="${toinvHolders.invId}">${toinvHolders.inventoryNo}</form:option>
						</c:forEach>
					</form:select>
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
<!-- End:TransferVoucher.jsp -->