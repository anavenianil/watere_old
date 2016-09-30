<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:CertificateSanctionVoucher.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
	<fieldset><legend><strong>Certificate Sanction Voucher</strong></legend>

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
			<div class="quarter">Inventory Number<span class='failure'>*</span></div>
			<div class="quarter">
				<spring:bind path="mmgVoucher">
					<form:select path="mmgVoucher.inventoryNo" id="inventoryNo" cssStyle="width:145px">
						<form:option value="">Select</form:option>
						<c:forEach var="invHolders" items="${mmgVoucher.inventoryNumsList}">
							<form:option value="${invHolders.invId}">${invHolders.inventoryNo}</form:option>
						</c:forEach>
					</form:select>
				</spring:bind>
			</div>
			<div class="quarter">Division Control Number</div>
			<div class="quarter">
				<spring:bind path="mmgVoucher">
					<form:input path="mmgVoucher.divControlNum" id="divControlNum" maxlength="20"></form:input>
				</spring:bind>
			</div>
		</div>
		<div class="line">
			<div class="quarter">Work Details</div>
			<div class="quarter">
				<spring:bind path="mmgVoucher">
					<form:textarea path="mmgVoucher.workDetails" id="workDetails" cols="18" rows="3" onkeypress="textCounter(event,$jq('#workDetails'),$jq('#counter'),200);"
						 onkeyup="textCounter(event,$jq('#workDetails'),$jq('#counter'),200);"></form:textarea>
				</spring:bind>
				<input type="text" class="counter" name="counter" value="200" id="counter"/>
			</div>
			<div class="quarter">Work Amount (<b><del>&#2352;</del></b>)</div>
			<div class="quarter">
				<spring:bind path="mmgVoucher">
					<form:input path="mmgVoucher.workAmount" id="workAmount" maxlength="10" onkeypress="return checkTwoDigitFloat(event,this.id);"></form:input>
				</spring:bind>
			</div>
		</div>
		
	</fieldset>
	<script type="text/javascript">
		$jq('#expVoucherField').css("display","none");
		$jq('#itemDetails').css("display","none");
		$jq('#demandList').css("display","none");
	</script>
<!-- End:CertificateSanctionVoucher.jsp -->