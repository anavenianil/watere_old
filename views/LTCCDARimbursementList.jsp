
<!-- Begin : LTCCDARimbursementList.jsp -->

<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<div>
	<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='deleted'}"> <span class="success"><spring:message code="delete"/></span></c:if>
	<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
</div>
<div id="dataTable">
<%int i=0; %>
	<display:table name="${sessionScope.cdareimbursementList}" excludedParams="*"
		export="false" class="list" requestURI="" id="reimbursement" pagesize="1000"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'rowr\');"/>'>
			<input type="checkbox" value="${reimbursement.id}" class="rowr" name="row" id="reim<%=i %>" onclick="checkBoxCheck(this.name);"/>
		</display:column>
		<display:column title="Req ID" style="width:2%;vertical-align:middle">&nbsp;<a href="javascript:getRequestDetails('${reimbursement.historyID}','${reimbursement.id}','myRequests','pending','')"><font color="blue">${reimbursement.id}</font></a></display:column>
		<display:column title="SFID" style="width:6%;vertical-align:middle">&nbsp;${reimbursement.sfID}</display:column>
		<display:column title="Employee" style="width:8%;vertical-align:middle">&nbsp;${reimbursement.name}</display:column>
		<display:column title="Amount" style="width:5%;vertical-align:middle">&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font>${reimbursement.amountClaimed}</display:column>
		<display:column title="Sanction No<span class='mandatory'>*</span>" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${reimbursement.sanctionNo}" name="sanctionNo" id="sanctionNoReim<%=i %>" onkeypress="javascript:return checkInt(event);" readonly="readonly"/></display:column>
		<display:column title="Bill No<span class='mandatory'>*</span>" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${reimbursement.billNo}" name="billNo" id="billNoReim<%=i %>" onkeypress="javascript:return checkInt(event);" readonly="readonly"/></display:column>
		<display:column title="Acc Officer.<span class='mandatory'>*</span>" style="width:11%;vertical-align:middle">&nbsp;
			<select name="accOfficer" id="accOfficerReim<%=i %>" style="width:90px"  disabled="disabled">
				<option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('accOfficerReim<%=i %>','${reimbursement.accountentSign}');</script>
		</display:column>
		<%--<display:column title="DV.No" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${reimbursement.dvNo}" name="dvNo" id="dvNoReim<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		--%>
		<display:column title="CDA Amount" style="width:6%;vertical-align:middle"><font size="4.5em"><span class="WebRupee" >R</span></font><input type="text" size="7" name="cdaAmount" id="cdaAmountReim<%=i %>" value="${reimbursement.amountClaimed}" onkeypress="javascript:return checkIntDecimal(event);"   onblur="javascript:twoDecimalCalculationAmount1(this);"/></display:column>
	<display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<a href="javascript:getInitialReport('${reimbursement.id}','ltcFinReim');">Report</a></display:column>
		<% i++; %>
	</display:table>
</div>



<div class="line" style="padding-top:3%;">

	<div class="line">
	<div class="quarter bold">DV.No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text"  name="dvNo" id="dvNoReim" onkeypress="javascript:return checkInt(event);"/></div>
	                </div>
	<%-- <div class="line">
	<div class="quarter bold">DV.Date <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="dvDate" id="dvDateReim"/>
			<script type="text/javascript">
			new tcal({'formname':'cdaltctour','controlname':'dvDateReim'});
			</script>
				</div>
				</div>--%>
				
				
					<div class="line">
	<div class="quarter bold">DV.Date <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="dvDate" id="dvDateReim" readonly="readonly"/>
			<img  src="./images/calendar.gif" id="dvDateReimImg" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"dvDateReim",ifFormat : "%d-%b-%Y",showsTime : false,button :"dvDateReimImg",singleClick : true,step : 1});
				</script></div>
				</div>
				
	
	</div>
	
<!-- End : LTCCDARimbursementList.jsp -->





