<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : TadaWaterAdminReimbursementList.jsp-->

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
	<display:table name="${sessionScope.tadaWaterReimbursementList}" excludedParams="*"
		export="false" class="list" requestURI="" id="settlement" pagesize="20"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column title="Req ID" style="width:3%;vertical-align:middle">&nbsp;<a href="javascript:gettadaWaterReimbursementDetails('${settlement.requestId}')"><font color="blue">${settlement.requestId}</font></a></display:column>
		<display:column title="Employee ID" style="width:6%;vertical-align:middle">&nbsp;${settlement.sfid}</display:column>
		<display:column title="Issed Advance Amount" style="width:5%;vertical-align:middle">&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font><input type="text" size="7" readonly="readonly" value="${settlement.totalAmt}" name="tadaAdvanceAmount" id="tadaAdvanceAmount<%=i %>"/></display:column> <%-- ${advance.advanceAmountAftRes} --%>     <!-- This line has been changed -->
			<display:column title="Reimbursement Amount" style="width:5%;vertical-align:middle">&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font><input type="text" size="7" readonly="readonly" value="${settlement.settleOrReimAmt}" name="tadaAdvanceAmount" id="tadaAdvanceAmount<%=i %>"/></display:column> <%-- ${advance.advanceAmountAftRes} --%>     <!-- This line has been changed -->
		<display:column title="Authority to Move" style="width:12%;vertical-align:middle;">&nbsp;
		WATER BOARD
		</display:column>
		<% i++; %>
	</display:table>
	</div>
	
	<div style="display: none;">
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
<!-- End : TadaWaterAdminReimbursementList.jsp -->




