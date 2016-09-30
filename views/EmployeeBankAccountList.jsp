<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : EmployeeBankAccountList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>

	<div class="line">
		<div class="quarter leftmar">Bank A/C No<span class="mandatory">*</span></div>
		<div class="quarter"><input name="bankAccNo" id="accNo"  maxlength="11" cssClass="formSelect" onkeypress="return checkInt(event);" /></div>
	</div>
	<div class="line">
		<div class="quarter leftmar">Branch Name<span class="mandatory">*</span></div>
		<div class="quarter"><input name="bankBranch" id="branchName"  cssClass="formSelect" onkeypress="return isAlphabetExp(event);" /></div>
	</div>
	<div class="line">
		<div class="quarter leftmar">Branch Code<span class="mandatory">*</span></div>
		<div class="quarter"><input name="branchCode" id="branchCode"  cssClass="formSelect" onkeypress="return isAlphabetExp(event);" /></div>
	</div>
	<div class="line">
		<div style="margin-left:35%">
				<div class="expbutton"><a href="javascript:submitDetails();"><span>Submit</span></a></div>
			<div class="expbutton"><a href="javascript:clearDetails();"><span>Clear</span></a></div>
		</div>
	</div>

<div>&nbsp;<jsp:include page="Result.jsp"></jsp:include></div>
<div id="dataTable">
   	<display:table name="${sessionScope.bankList}" excludedParams="*" export="false" class="list" requestURI="" id="bank" pagesize="10" sort="list">
		<display:column title="SFID" style="width:25%;text-align:left;">&nbsp;${bank.sfidSearchKey}</display:column>
		<display:column title="Bank Account No" style="width:25%;text-align:left;">&nbsp;${bank.bankAccNo}</display:column>
		<display:column title="Branch Name" style="width:30%;text-align:left">&nbsp;${bank.bankBranch}</display:column>
		<display:column title="Branch Code" style="width:10%;text-align:left">&nbsp;${bank.branchCode}</display:column>
		<display:column style="width:10%;text-align:center" title="Edit">
			<img src="./images/edit.gif" title="Edit" onclick="editDetails('${bank.sfidSearchKey}','${bank.bankAccNo}','${bank.bankBranch}','${bank.branchCode}')"/>
			<!-- onclick="payEditCategoryDetails('${emp.id}','${emp.name}','${emp.payOrderNo}')" -->
		</display:column>
	</display:table>
</div>

<script>
	$jq( function(){ $jq("#dataTable").displayTagAjax('paging');})
</script>

<!-- End : EmployeeBankAccountList.jsp -->
