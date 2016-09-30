<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : ItemDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<fieldset><legend><strong>Add Item</strong></legend>
										
		<div class="line">
				<div class="quarter">Search With
					<select name="search" style="width:90px" onchange="javascript:searchValues(voucherType);" id="search">
						<option value="Select">Select</option>
						<option value="itemName">Material Name</option>
						<option value="itemCode">Material Code</option>
					</select>
				</div>
				<div class="quarter" >
					<div id="materialCode"><input type="text" name="materialCode"/></div>
				</div>
				<div class="quarter" id="materialLabel">Material Code</div>
				<div class="quarter" id="materialdesc">&nbsp;</div>
		</div>
		<div id="qtyRate">
		<div class="line">
				
				<div class="quarter">Qty</div>
				<div class="quarter">
					<input type="text" name="qty" id="qty"/>
				</div>
				<div class="quarter">Unit Rate</div>
				<div class="quarter">
					<input type="text" name="rate" id="unitRate" readonly/>
				</div>
		</div>
		</div>
		<div id="uomCnc" style="display:none">
			<div class="line">
					<div class="quarter">UOM (Present)</div>
					<div class="quarter">
						<div id="uomLable">&nbsp;</div>
					</div>
					<div class="quarter">UOM (To be convert)</div>
					<div class="quarter">
						<select id="uomConvert" name="uomConvert" style="width:145px">
							<option value="Select">Select</option>
							<c:forEach items="${uomTypesList}" var="uomtype">
								<option value="${uomtype.id}">${uomtype.name}</option>
							</c:forEach>
							
						</select>
					</div>
			</div>
			<div class="line">
					<div class="quarter">Consumable/Non-Consumable(Present)</div>
					<div class="quarter">
						<div id="cncLable">&nbsp;</div>
					</div>
					<div class="quarter">Consumable/Non-Consumable (To be convert)</div>
					<div class="quarter">
						<select id="cncConvert" name="cncConvert" style="width:145px">
							<option value="Select">Select</option>
							<option value="C">Consumable</option>
							<option value="NC">Non Consumable</option>
						</select>
					</div>
			</div>
		</div>
		<div class="line">
			<div class="appbutton" style="float:right"><a href="javascript:addItem(voucherType);" class="quarterbutton">Add Item</a></div>
			<div class="appbutton" style="float:right"><a href="javascript:clearMaterial()" class="quarterbutton">Clear</a></div>
		</div>
		<input type="hidden" name="jsonValue"/>								
		
		<script>
			var itemsjson= "";
			var voucherType='${sessionScope.mmgVoucher}';
		</script>
</fieldset>