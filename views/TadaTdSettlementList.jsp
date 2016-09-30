<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : TadaTdAdvList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/script.js"></script>
<div>
	<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
</div>
<div>
<div id="settPagination">
<%int i=0; %>
	<display:table name="${sessionScope.tadaFinSettlementList}" excludedParams="*"
		export="false" class="list" requestURI="" id="settlement" pagesize="20"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			<input type="checkbox" value="${settlement.requestId}" class="row" name="row" id="sett<%=i %>" onclick="checkBoxCheck(this.name);"/>
		</display:column>
		<display:column title="Req ID" style="width:3%;vertical-align:middle">&nbsp;<a href="javascript:getRequestDetails('${settlement.historyID}','${settlement.referenceRequestID}','myRequests','pending','')"><font color="blue">${settlement.referenceRequestID}</font></a></display:column>
		<display:column title="SFID" style="width:6%;vertical-align:middle">&nbsp;${settlement.sfid}</display:column>
		<display:column title="Employee" style="width:8%;vertical-align:middle">&nbsp;${settlement.name}</display:column>
		<display:column title="Authority to Move" style="width:12%;vertical-align:middle;">&nbsp;
		  <c:if test="${settlement.authorizedMove=='1'}">Build-Up</c:if><c:if test="${settlement.authorizedMove=='2'}">Project-${settlement.projectName}</c:if>
		</display:column>
		<display:column title="Amount" style="width:5%;vertical-align:middle">&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font><input type="hidden" size="7" value="${settlement.tadaSettlementAmount}" name="tadaAdvanceAmount" id="tadaAdvanceAmount<%=i %>"/>
		<c:if test="${settlement.tadaAdvanceAmount<settlement.tdSettAmountAftRes}">${settlement.tdSettAmountAftRes-settlement.tadaAdvanceAmount}</c:if>
		<c:if test="${settlement.tadaAdvanceAmount>settlement.tdSettAmountAftRes}">0</c:if>
		</display:column>
		<%-- <display:column title="Sanction No<span class='mandatory'>*</span>" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${settlement.sanctionNo}" name="sanctionNo" id="sanctionNoSett<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="Bill No<span class='mandatory'>*</span>" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${settlement.billNo}" name="billNo" id="billNoSett<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="Acc Officer.<span class='mandatory'>*</span>" style="width:12%;vertical-align:middle">&nbsp;
		<select name="accOfficer" id="accOfficerSett<%=i %>" style="width:70px">
				<option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('accOfficerSett<%=i %>','${settlement.accountentSign}');</script>
		</display:column> --%>
		<%-- <display:column title="DV.No" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${settlement.dvNo}" name="dvNo" id="dvNoSett<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="DV.Date" style="width:14%;vertical-align:middle">&nbsp;
		<input type="text" size="12" value='${settlement.dvDate}' readonly="readonly" name="dvDate" id="dvDateSett<%=i %>"/>
			<img  src="./images/calendar.gif" id="dvDateSettImg<%=i %>" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"dvDateSett<%=i %>",ifFormat : "%d-%b-%Y",showsTime : false,button :"dvDateSettImg<%=i %>",singleClick : true,step : 1});
				</script>
		</display:column>
		<display:column title="CDA Amount" style="width:6%;vertical-align:middle"><input type="text" size="7" name="cdaAmount" id="cdaAmountSett<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column> --%>
		
		
		<% i++; %>
	</display:table>
	</div>
	<div class="line" style="padding-top:3%;">

	<div class="line">
	<div class="quarter bold">Sanction No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text"  name="sanctionNo" id="sanctionNoSett" onkeypress="javascript:return checkInt(event);"/></div>
	                </div>
	<div class="line">
	<div class="quarter bold">Bill No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="billNo" id="billNoSett"/></div></div>
	<div class="line">
    <div class="quarter bold">Account Officer<span class="mandatory">*</span></div>
    <div class="quarter"><select name="accOfficer" id="accOfficerSett" style="width:160px"><option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach> </select>
				</div>
	</div>
	</div>

	
	<script>
	  tadaFinSettlementList = <%= (net.sf.json.JSONArray)session.getAttribute("tadaFinSettlementList") %>;
	  tadaTdFinSettlementCmplList = <%= (net.sf.json.JSONArray)session.getAttribute("tadaTdFinSettlementCmplList") %>;
	  enableSettList();
	</script>
	<script>
			$jq( function(){
					$jq("#settPagination").displayTagAjax('settlementPaging');
				})
				 tadaFinSettlementList = <%= (net.sf.json.JSONArray)session.getAttribute("tadaFinSettlementList") %>;
	</script>
</div>
<!-- End : TadaTdAdvList.jsp -->




