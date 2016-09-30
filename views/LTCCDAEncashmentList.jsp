
<!-- Begin : LTCCDAEncashmentList.jsp -->

<%@  page language="java" contentType="text/html; charset=UTF-8"
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
	<display:table name="${sessionScope.cdaencashmentList}" excludedParams="*"
		export="false" class="list" requestURI="" id="encashment" pagesize="1000"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'rowe\');"/>'>
			<input type="checkbox" value="${encashment.id}" class="rowe" name="rowe" id="encash<%=i %>" onclick="checkBoxCheck(this.name);"/>
		</display:column>
		<display:column title="Req ID" style="width:2%;vertical-align:middle">&nbsp;<a href="javascript:getRequestDetails('${encashment.historyID}','${encashment.id}','myRequests','pending','')"><font color="blue">${encashment.id}</font></a></display:column>
		<display:column title="SFID" style="width:5%;vertical-align:middle">&nbsp;${encashment.sfID}</display:column>
		<display:column title="Employee" style="width:5%;vertical-align:middle">&nbsp;${encashment.name}</display:column>
		<display:column title="Days" style="width:3%;vertical-align:middle">&nbsp;${encashment.encashmentDays}</display:column>
		<display:column title="Finance Amount" style="width:5%;vertical-align:middle">&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font><input type="text" size="5" value="${encashment.encashmentAmount}" name="FinanceAmountEncash" id="FinanceAmountEncash<%=i %>" onkeypress="javascript:return checkInt(event);" readonly="readonly"/></display:column>
		<display:column title="Sanction No<span class='mandatory'>*</span>" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${encashment.sanctionNo}" name="sanctionNo" id="sanctionNoEncash<%=i %>" onkeypress="javascript:return checkInt(event);" readonly="readonly"/></display:column>
		<display:column title="Bill No<span class='mandatory'>*</span>" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${encashment.billNo}" name="billNo" id="billNoEncash<%=i %>" onkeypress="javascript:return checkInt(event);" readonly="readonly"/></display:column>
	<display:column title="CDA Amount" style="width:6%;vertical-align:middle"><font size="4.5em"><span class="WebRupee" >R</span></font><input type="text" size="7" name="cdaAmount" id="cdaAmountEnc<%=i %>" value="${encashment.encashmentAmount}" onkeypress="javascript:return checkInt(event);"/></display:column>
			<display:column title="Acc Officer.<span class='mandatory'>*</span>" style="width:12%;vertical-align:middle">&nbsp;
			<select name="accOfficer" id="accOfficerEnc<%=i %>" style="width:90px" disabled="disabled">
				
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('accOfficerEnc<%=i %>','${encashment.accountentSign}');</script>
		</display:column>	
		 <display:column title="CFA Sig<span class='mandatory' >*</span>" style="width:12%">&nbsp;
			<select name="repSig" id="repSigEncash<%=i %>" style="width:100px" disabled="disabled"> 
			
				<c:forEach var="cfa" items="${sessionScope.CfaOfficerList}">
					<option value="${cfa.sfid}">${cfa.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('repSigEncash<%=i %>','${encashment.cfaSign}');</script>
	</display:column>
		
	
	<%-- 	<display:column title="DV.No" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${encashment.dvNo}" name="dvNo" id="dvNoEncash<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		 --%>
		<%-- <display:column title="CDA Amount" style="width:6%;vertical-align:middle"><input type="text" size="7" name="cdaAmount" id="cdaAmountEnc<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>--%>
		<display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<a href="javascript:getInitialReport('${encashment.id}','encash');">Report</a></display:column>
		<% i++; %>
	</display:table>
</div>

<div class="line" style="padding-top:3%;">

	<div class="line">
	<div class="quarter bold">DV.No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text"  name="dvNo" id="dvNoEncash" onkeypress="javascript:return checkInt(event);"/></div>
	                </div>
<%-- 	<div class="line">
	<div class="quarter bold">DV.Date <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="dvDate" id="dvDateEncash"/>
	
	<script type="text/javascript">
			new tcal({'formname':'cdaltctour','controlname':'dvDateEncash'});
			</script>
			</div>
				</div>--%>
				<div class="line">
	<div class="quarter bold">DV.Date <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="dvDate" id="dvDateEncash" readonly="readonly"/>
			<img  src="./images/calendar.gif" id="dvDateEncashImg" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"dvDateEncash",ifFormat : "%d-%b-%Y",showsTime : false,button :"dvDateEncashImg",singleClick : true,step : 1});
				</script></div>
				</div>
		
	
	</div>


<!-- End : LTCCDAEncashmentList.jsp -->





