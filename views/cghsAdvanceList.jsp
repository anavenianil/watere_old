<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : cghsAdvanceList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>

<%@ taglib uri="/tags/spring" prefix="spring" %>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<div>
	<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='deleted'}"> <span class="success"><spring:message code="delete"/></span></c:if>
	<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
</div>
<div id="advancePaging">

<%int i=0; %>
	<display:table name="${sessionScope.advanceList}" excludedParams="*"
		export="false" class="list" requestURI="" id="advance" pagesize="10"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:2%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			<input type="checkbox" value="${advance.requestId}" class="row" name="row" id="adv<%=i %>" onclick="checkBoxCheck(this.name);"/>
		</display:column>
		<display:column title="Req ID" style="width:3%;vertical-align:middle">&nbsp;${advance.requestId}</display:column>
		<display:column title="SFID" style="width:5%;vertical-align:middle">&nbsp;${advance.sfID}</display:column>
		<display:column title="Employee" style="width:7%;vertical-align:middle">&nbsp;${advance.employeeName}</display:column>
		<display:column title="80% of Est" style="width:5%;vertical-align:middle">&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font>${advance.issuedAmount}</display:column>
	<display:column title="Amount<span class='mandatory'>*</span>" style="width:6%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${advance.issuedAmount}" name="cdaAmount" id="cdaAmountAdv<%=i %>" onkeypress="return checkInt(event,'cdaAmountAdv');" /></display:column>
		
				
	<%--	<display:column title="Sanction No<span class='mandatory'>*</span>" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${advance.sanctionNo}" name="sanctionNo" id="sanctionNoAdv<%=i %>" onkeypress="return checkInt(event,'sanctionNoAdv');"/></display:column>
		<display:column title="Bill No<span class='mandatory'>*</span>" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${advance.billNo}" name="billNo" id="billNoAdv<%=i %>" onkeypress="return checkInt(event,'billNoAdv');"/></display:column> --%>
		<%-- <display:column title="CFA Sig<span class='mandatory'>*</span>" style="width:12%;vertical-align:middle">&nbsp;
			<select name="repSig" id="repSigAdv<%=i %>" style="width:90px">
				<option value="select">Select</option>
				<c:forEach var="cfa" items="${sessionScope.CfaOfficerList}">
					<option value="${cfa.sfid}">${cfa.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('repSigAdv<%=i %>','${advance.repSig}');</script>
		</display:column>--%>
	<%-- 	<display:column title="Acc Officer.<span class='mandatory'>*</span>" style="width:12%;vertical-align:middle">&nbsp;
			<select name="accOfficer" id="accOfficerAdv<%=i %>" style="width:90px">
				<option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('accOfficerAdv<%=i %>','${advance.accOfficer}');</script>
		</display:column>
		<display:column title="DV.No" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${advance.dvNo}" name="dvNo" id="dvNoAdv<%=i %>" onkeypress="return checkInt(event,'dvNoAdv');"/></display:column>
		<display:column title="DV.Date" style="width:14%;vertical-align:middle">&nbsp;
			<input type="text" size="11" value='${advance.dvDate}' readonly="readonly" name="dvDate" id="dvDateAdv<%=i %>"/>
			<script type="text/javascript">
											new tcal({'formname':'cghsmedical','controlname':'dvDateAdv<%=i %>'});
											</script>
		</display:column>

		<display:column title="CDA Amount" style="width:6%;vertical-align:middle"><input type="text" size="7" name="cdaAmount" id="cdaAmountAdv<%=i %>" onkeypress="return checkInt(event,'dvNoAdv');"/></display:column>--%>
		<%-- <display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<a href="javascript:getReport('${advance.requestId}','advance');">Report</a></display:column>--%>

		<display:column title="CDA Amount" style="width:6%;vertical-align:middle"><font size="4.5em"><span class="WebRupee" >R</span></font><input type="text" size="7" name="cdaAmount" id="cdaAmountAdv<%=i %>" onkeypress="return checkInt(event,'dvNoAdv');"/></display:column>
		<display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<a href="javascript:getReport('${advance.requestId}','advance');">Report</a></display:column>

		<% i++; %>
	</display:table>

        <script>
			$jq( function(){
					$jq("#advancePaging").displayTagAjax('advancepaging');
				})
	</script>

</div>


		<div class="line" style="padding-top:3%;">

	<div class="line">
	<div class="quarter bold">Sanction No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text"  name="sanctionNo" id="sanctionNoAdv" onkeypress="javascript:return checkInt(event);"/></div>
	                </div>
	<div class="line">
	<div class="quarter bold">Bill No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="billNo" id="billNoAdv" onkeypress="javascript:return checkInt(event);"/></div></div>
	<div class="line">
    <div class="quarter bold">Account Officer<span class="mandatory">*</span></div>
    <div class="quarter"><select name="accOfficer" id="accOfficerAdv" style="width:160px"><option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach> </select>
				</div>
	</div>
	<div class="line">
    <div class="quarter bold">CFA Sig<span class="mandatory">*</span></div>
    <div class="quarter"><select name="repSig" id="repSigAdv" style="width:160px"><option value="select">Select</option>
				<c:forEach var="cfa" items="${sessionScope.CfaOfficerList}">
					<option value="${cfa.sfid}">${cfa.name}</option>
				</c:forEach> </select>
				</div>
	</div>
	</div>

<!-- End : cghsAdvanceList.jsp -->