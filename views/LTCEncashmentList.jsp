<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : LTCEncashmentList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
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
	<display:table name="${sessionScope.encashmentList}" excludedParams="*"
		export="false" class="list" requestURI="" id="encashment" pagesize="1000"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:1%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'rowe\');"/>'>
			<input type="checkbox" value="${encashment.id}" class="rowe" name="row" id="encash<%=i %>" onclick="checkBoxCheck(this.name);"/>
		</display:column>
		<display:column title="Req ID" style="width:2%;vertical-align:middle">&nbsp;<a href="javascript:getRequestDetails('${encashment.historyID}','${encashment.id}','myRequests','pending','')"><font color="blue">${encashment.id}</font></a></display:column>
		<display:column title="SFID" style="width:3%;vertical-align:middle">&nbsp;${encashment.sfID}</display:column>
		<display:column title="Employee" style="width:5%;vertical-align:middle">&nbsp;${encashment.name}</display:column>
		<display:column title="Days" style="width:1%;vertical-align:middle">&nbsp;${encashment.encashmentDays}</display:column>
		<display:column title="Finance Amount" style="width:1%;vertical-align:middle">&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font><input type="text" size="10" style="background-color: pink;text-align: center;"  value="${encashment.encashmentAmount}" name="amount" id="FinanceAmountEncash<%=i %>" onkeypress="javascript:return checkInt(event);" /></display:column>
		
		<display:column title="Calculation details: (((BP+GP)+(BP+GP)*DA/100)* (No of Encashment Days /30))" style="width:11%;vertical-align:middle">&nbsp;<b><span style="color:red"><c:if test="${encashment.enchashmentamountDavalue eq  null }">0</c:if>
	<c:if test="${encashment.enchashmentamountDavalue!=null}"><font size="4.5em"><span class="WebRupee" >R</span></font>${encashment.enchashmentamountDavalue}</c:if></span></b>=(((${encashment.basicPay}+${encashment.gradePay})+(${encashment.basicPay}+${encashment.gradePay})*${encashment.daValue}/100)* (${encashment.encashmentDays} /30))
		
		</display:column>
	<%-- 	<display:column title="Sanction No<span class='mandatory'>*</span>" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${encashment.sanctionNo}" name="sanctionNo" id="sanctionNoEncash<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="Bill No<span class='mandatory'>*</span>" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${encashment.billNo}" name="billNo" id="billNoEncash<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="Acc Officer.<span class='mandatory'>*</span>" style="width:12%;vertical-align:middle">&nbsp;
			<select name="accOfficer" id="accOfficerEnc<%=i %>" style="width:90px">
				<option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('accOfficerEnc<%=i %>','${encashment.accountentSign}');</script>
		</display:column>--%>
	<%-- 	<display:column title="DV.No" style="width:7%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${encashment.dvNo}" name="dvNo" id="dvNoEncash<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="DV.Date" style="width:10%;vertical-align:middle">&nbsp;
			<input type="text" size="13" value='${encashment.dvDate}' readonly="readonly" name="dvDate" id="dvDateEncash<%=i %>"/>
			<script type="text/javascript">
			new tcal({'formname':'ltctour','controlname':'dvDateEncash<%=i %>'});
			</script>
		</display:column>  --%>
		<%-- <display:column title="CDA Amount" style="width:6%;vertical-align:middle"><input type="text" size="7" name="cdaAmount" id="cdaAmountEnc<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<a href="javascript:getInitialReport('${encashment.id}','encash');">Report</a></display:column>--%>
		<% i++; %>
	</display:table>
</div>
<div class="line" style="padding-top:3%;">

 <div> <b><span style="color:red"> Encashment Amount Calculations are Based on Present Basic Pay And Grade Pay*</span></b></div><div>

	<div class="line">
	<div class="quarter bold">Sanction No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text"  name="sanctionNo" id="sanctionNoEncash" onkeypress="javascript:return checkInt(event);"/></div>
	                </div>
	<div class="line">
	<div class="quarter bold">Bill No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="billNo" id="billNoEncash"/></div></div>
	<div class="line">
    <div class="quarter bold">Account Officer<span class="mandatory">*</span></div>
    <div class="quarter"><select name="accOfficer" id="accOfficerEnc" style="width:160px"><option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach> </select>
				</div>
	</div>
	
	 <div class="line">
    <div class="quarter bold">CFA Sig<span class="mandatory">*</span></div>
    <div class="quarter"><select name="repSig" id="repSigEncash" style="width:160px" ><option value="select">Select</option>
				<c:forEach var="cfa" items="${sessionScope.CfaOfficerList}" >
					<option value="${cfa.sfid}">${cfa.name}</option>
				</c:forEach> </select>
				</div>
	</div>
	</div>


<!-- End : LTCEncashmentList.jsp -->





