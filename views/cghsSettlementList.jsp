<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : cghsSettlementList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='deleted'}"> <span class="success"><spring:message code="delete"/></span></c:if>
	<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
</div>
<div id="settlementTable">

<%int i=0; %>
<display:table name="${sessionScope.settlementList}" excludedParams="*"
		export="false" class="list" requestURI="" id="settlement" pagesize="10"
	sort="list" cellpadding="2" cellspacing="1">
	<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'rows\');"/>'>
			<input type="checkbox" value="${settlement.requestId}" class="rows" name="row" id="sett<%=i %>" onclick="checkBoxCheck(this.name);"/>
	</display:column>
	<display:column title="Req ID" style="width:3%;vertical-align:middle">&nbsp;${settlement.requestId}</display:column>				 		
	<display:column title="SFID" style="width:6%;vertical-align:middle">&nbsp;${settlement.sfID}</display:column>
	<display:column title="Employee" style="width:8%;vertical-align:middle">&nbsp;${settlement.employeeName}</display:column>
		<display:column title="Finance Amount" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="10" name="cdaAmount"  value="${settlement.amountClaimed}"id="cdaAmountSett<%=i %>" onkeypress="return checkInt(event,'cdaAmountSett');"/></display:column>--%>
	
<%--- 	
<display:column title="Sanction No<span class='mandatory'>*</span>" style="width:6%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${settlement.sanctionNo}" name="sanctionNo" id="sanctionNoSett<%=i %>" onkeypress="return checkInt(event,'sanctionNoSettsanctionNoSettsanctionNoSett');"/></display:column>
	<display:column title="Bill No<span class='mandatory'>*</span>" style="width:6%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${settlement.billNo}" name="billNo" id="billNoSett<%=i %>" onkeypress="return checkInt(event,'billNoSett');"/></display:column>

<display:column title="CFA Sig<span class='mandatory'>*</span>" style="width:12%">&nbsp;
			<select name="repSig" id="repSigSett<%=i %>" style="width:100px">
				<option value="select">Select</option>
				<c:forEach var="cfa" items="${sessionScope.CfaOfficerList}">
					<option value="${cfa.sfid}">${cfa.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('repSigSett<%=i %>','${settlement.repSig}');</script>
	</display:column>--%>
	<%-- <display:column title="Acc Officer.<span class='mandatory'>*</span>" style="width:12%;vertical-align:middle">&nbsp;
			<select name="accOfficer" id="accOfficerSett<%=i %>" style="width:100px">
				<option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('accOfficerSett<%=i %>','${settlement.accOfficer}');</script>
		</display:column>
	<display:column title="DV.No" style="width:8%;vertical-align:middle"><input type="text" size="10" value="${settlement.dvNo}" name="dvNo" id="dvNoSett<%=i %>" onkeypress="return checkInt(event,'dvNoSett');"/></display:column>
	<display:column title="DV.Date" style="width:12%;vertical-align:middle"><input type="text" size="13" value="${settlement.dvDate}" readonly="readonly" name="dvDate" id="dvDateSett<%=i %>"/>
			<script type="text/javascript">
											new tcal({'formname':'cghsmedical','controlname':'dvDateSett<%=i %>'});
											</script>
	</display:column>

	<display:column title="CDA Amount" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="10" name="cdaAmount" id="cdaAmountSett<%=i %>" onkeypress="return checkInt(event,'cdaAmountSett');"/></display:column>--%>
	<%---<display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<a href="javascript:getReport('${settlement.requestId}','settlement');">Report</a></display:column> --%>

	<display:column title="CDA Amount" style="width:8%;vertical-align:middle">&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font><input type="text" size="10" name="cdaAmount" id="cdaAmountSett<%=i %>" onkeypress="return checkInt(event,'cdaAmountSett');"/></display:column>
	<display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<a href="javascript:getReport('${settlement.requestId}','settlement');">Report</a></display:column>

	<% i++; %>
</display:table>

<div class="line" style="padding-top:3%;">

	<div class="line">
	<div class="quarter bold">Sanction No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text"  name="sanctionNo" id="sanctionNoSett" onkeypress="javascript:return checkInt(event);"/></div>
	                </div>
	<div class="line">
	<div class="quarter bold">Bill No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="billNo" id="billNoSett" onkeypress="javascript:return checkInt(event);"/></div></div>
	<div class="line">
    <div class="quarter bold">Account Officer<span class="mandatory">*</span></div>
    <div class="quarter"><select name="accOfficer" id="accOfficerSett" style="width:160px"><option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach> </select>
				</div>
	</div>
	
	<div class="line">
    <div class="quarter bold">CFA Sig<span class="mandatory">*</span></div>
    <div class="quarter"><select name="repSig" id="repSigSett" style="width:160px"><option value="select">Select</option>
				<c:forEach var="cfa" items="${sessionScope.CfaOfficerList}">
					<option value="${cfa.sfid}">${cfa.name}</option>
				</c:forEach> </select>
				</div>
	</div>
	</div>



<script type="text/javascript">
   $jq( function(){
					$jq("#settlementTable").displayTagAjax('settlementpaging');
				})
</script>
</div>
<!-- End : cghsSettlementList.jsp -->