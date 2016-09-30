
<!-- Begin : LTCCDAAdvanceList.jsp -->

<%@  page language="java" contentType="text/html; charset=UTF-8"
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
	<display:table name="${sessionScope.cdaadvanceList}" excludedParams="*"
		export="false" class="list" requestURI="" id="advance" pagesize="1000"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'rowa\');"/>'>
			<input type="checkbox" value="${advance.id}" class="rowa" name="row" id="adv<%=i %>" onclick="checkBoxCheck(this.name);"/>
		</display:column>
		<display:column title="Req ID" style="width:3%;vertical-align:middle">&nbsp;<a href="javascript:getRequestDetails('${advance.historyID}','${advance.id}','myRequests','pending','')"><font color="blue">${advance.id}</font></a></display:column>
		<display:column title="SFID" style="width:6%;vertical-align:middle">&nbsp;${advance.sfID}</display:column>
		<display:column title="Employee" style="width:8%;vertical-align:middle">&nbsp;${advance.name}</display:column>
		<display:column title="90% of Est" style="width:5%;vertical-align:middle">&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font>${advance.amountClaimed}</display:column>
		<display:column title="Sanction No<span class='mandatory'>*</span>" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${advance.sanctionNo}" name="sanctionNo" id="sanctionNoAdv<%=i %>" onkeypress="javascript:return checkInt(event);" readonly="readonly"/></display:column>
		<display:column title="Bill No<span class='mandatory'>*</span>" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${advance.billNo}" name="billNo" id="billNoAdv<%=i %>" onkeypress="javascript:return checkInt(event);" readonly="readonly"/></display:column>
	<display:column title="Acc Officer.<span class='mandatory'>*</span>" style="width:12%;vertical-align:middle">&nbsp; 
			<select name="accOfficer" id="accOfficerAdv<%=i %>" style="width:90px" disabled="disabled" >
				<option value="select" >Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}"  >
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('accOfficerAdv<%=i %>','${advance.accountentSign}');</script>
		</display:column>
		
		
		<display:column title="CDA Amount" style="width:6%;vertical-align:middle"><font size="4.5em"><span class="WebRupee" >R</span></font><input type="text" size="7" name="cdaAmount" id="cdaAmountAdv<%=i %>" value="${advance.amountClaimed}" onkeypress="javascript:return checkIntDecimal(event);"  onblur="javascript:twoDecimalCalculationAmount1(this);"/></display:column>
	<display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<a href="javascript:getInitialReport('${advance.id}','ltcAdvance');">Report</a></display:column>
		
		<%-- <display:column title="Encash" style="width:6%;vertical-align:middle">&nbsp;<c:if test="${not empty advance.encashmentDays}"><a href="javascript:getInitialReport('${advance.id}','encash');">Report</a></c:if></display:column>--%>	
		
		<% i++; %>
	</display:table>
	
</div>
<div class="line" style="padding-top:3%;">

	<div class="line">
	<div class="quarter bold">DV.No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text"  name="dvNo" id="dvNoAdv" onkeypress="javascript:return checkInt(event);"/></div>
	                </div>
	<%-- <div class="line">
	<div class="quarter bold">DV.Date <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="dvDate" id="dvDateAdv"/>
			
			<script type="text/javascript">
			new tcal({'formname':'cdaltctour','controlname':'dvDateAdv'});
			</script>
			
			
			</div>
				</div>--%>
				
				
				<div class="line">
	<div class="quarter bold">DV.Date <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="dvDate" id="dvDateAdv" readonly="readonly"/>
			<img  src="./images/calendar.gif" id="dvDateAdvImg" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"dvDateAdv",ifFormat : "%d-%b-%Y",showsTime : false,button :"dvDateAdvImg",singleClick : true,step : 1});
				</script></div>
				</div>
				
	
	</div>
	
<!-- End : LTCCDAAdvanceList.jsp -->





