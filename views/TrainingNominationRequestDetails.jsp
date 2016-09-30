<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : TrainingNominationRequestDetails.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<script type="text/javascript" src="script/script.js"></script>
<div>
	<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
</div>
<div id="dataTable">
<%int i=0; %>
	<display:table name="${sessionScope.advanceList}" excludedParams="*"
		export="false" class="list" requestURI="" id="advance" pagesize="1000"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			<input type="checkbox" value="${advance.id}" class="row" name="row" id="adv<%=i %>" onclick="checkBoxCheck(this.name);"/>
		</display:column>
		<display:column title="Req ID" style="width:3%;vertical-align:middle">&nbsp;<a href="javascript:getRequestDetails('${advance.historyID}','${advance.id}','myRequests','pending','')"><font color="blue">${advance.id}</font></a></display:column>
		<display:column title="SFID" style="width:6%;vertical-align:middle">&nbsp;${advance.sfID}</display:column>
		<display:column title="Employee" style="width:8%;vertical-align:middle">&nbsp;${advance.name}</display:column>
		<display:column title="90% of Est" style="width:5%;vertical-align:middle">&nbsp;${advance.amountClaimed}</display:column>
		<display:column title="Sanction No<span class='mandatory'>*</span>" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${advance.sanctionNo}" name="sanctionNo" id="sanctionNoAdv<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="Bill No<span class='mandatory'>*</span>" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${advance.billNo}" name="billNo" id="billNoAdv<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="Acc Officer.<span class='mandatory'>*</span>" style="width:12%;vertical-align:middle">&nbsp;
			<select name="accOfficer" id="accOfficerAdv<%=i %>" style="width:90px">
				<option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('accOfficerAdv<%=i %>','${advance.accountentSign}');</script>
		</display:column>
		<display:column title="DV.No" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${advance.dvNo}" name="dvNo" id="dvNoAdv<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="DV.Date" style="width:14%;vertical-align:middle">&nbsp;
			<input type="text" size="13" value='${advance.dvDate}' readonly="readonly" name="dvDate" id="dvDateAdv<%=i %>"/>
			<img  src="./images/calendar.gif" id="dvDateAdvImg<%=i %>" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"dvDateAdv<%=i %>",ifFormat : "%d-%b-%Y",showsTime : false,button :"dvDateAdvImg<%=i %>",singleClick : true,step : 1});
				</script>
		</display:column>
		<display:column title="CDA Amount" style="width:6%;vertical-align:middle"><input type="text" size="7" name="cdaAmount" id="cdaAmountAdv<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<a href="javascript:getInitialReport('${advance.id}','ltcAdvance');">Report</a></display:column>
		
		<display:column title="Encash" style="width:6%;vertical-align:middle">&nbsp;<c:if test="${not empty advance.encashmentDays}"><a href="javascript:getInitialReport('${advance.id}','encash');">Report</a></c:if></display:column>
		
		<% i++; %>
	</display:table>
	
</div>
<!-- End :TrainingNominationRequestDetails.jsp -->





