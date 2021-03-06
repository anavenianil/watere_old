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
<div id="advPagination">
<%int i=0; %>
	<display:table name="${sessionScope.tadaFinAdvList}" excludedParams="*"
		export="false" class="list" requestURI="" id="advance" pagesize="100"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			<input type="checkbox" value="${advance.requestId}" class="row" name="row" id="adv<%=i %>" onclick="checkBoxCheck(this.name);"/>
		</display:column>
		<display:column title="Req ID" style="width:3%;vertical-align:middle">&nbsp;<a href="javascript:getRequestDetails('${advance.historyID}','${advance.referenceRequestID}','myRequests','pending','')"><font color="blue">${advance.referenceRequestID}</font></a></display:column>
		<display:column title="SFID" style="width:6%;vertical-align:middle">&nbsp;${advance.sfid}</display:column>
		<display:column title="Employee" style="width:8%;vertical-align:middle">&nbsp;${advance.name}</display:column>
		<display:column title="Sanctioned Amount" style="width:5%;vertical-align:middle">&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font><input type="text" size="7" value="${advance.advanceAmountAftRes}" name="tadaAdvanceAmount" id="tadaAdvanceAmount<%=i %>"/></display:column> <%-- ${advance.advanceAmountAftRes} --%>     <!-- This line has been changed -->
		
		<display:column title="Journey Date & Issuing Authority" style="width:12%;vertical-align:middle;">&nbsp;
		${advance.departureDateOne}<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="hidden" name="issueAuthority" id="issueAuthority<%=i %>" value="${advance.daType}" /><c:if test="${advance.daType=='asl'}">ASL</c:if><c:if test="${advance.daType=='cda'}">CDA</c:if>
		</display:column>
		
		<display:column title="Authority to Move" style="width:12%;vertical-align:middle;">&nbsp;
		  <c:if test="${advance.authorizedMove=='1'}">Build-Up</c:if><c:if test="${advance.authorizedMove=='2'}">Project-${advance.projectName}</c:if>
		</display:column>
		
		<%-- <display:column title="Sanction No<span class='mandatory'>*</span>" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${advance.sanctionNo}" name="sanctionNo" id="sanctionNoAdv<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="Bill No<span class='mandatory'>*</span>" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="5" value="${advance.billNo}" name="billNo" id="billNoAdv<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="Acc Officer.<span class='mandatory'>*</span>" style="width:10%;vertical-align:middle">&nbsp;
		<select name="accOfficer" id="accOfficerAdv<%=i %>" style="width:60px">
				<option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('accOfficerAdv<%=i %>','${advance.accountentSign}');</script>
		</display:column>
		<display:column title="DV.No" style="width:7%;vertical-align:middle">&nbsp;<c:if test="${advance.daType=='cda'}"><input type="text" size="6" value="${advance.dvNo}" name="dvNo" id="dvNoAdv<%=i %>" onkeypress="javascript:return checkInt(event);"/></c:if></display:column>
		<display:column title="DV.Date" style="width:15%;vertical-align:middle">&nbsp;
		<c:if test="${advance.daType=='cda'}"><input type="text" size="4" value='${advance.dvDate}' readonly="readonly" name="dvDate" id="dvDateAdv<%=i %>" />
			<img  src="./images/calendar.gif" id="dvDateAdvImg<%=i %>" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"dvDateAdv<%=i %>",ifFormat : "%d-%b-%Y",showsTime : false,button :"dvDateAdvImg<%=i %>",singleClick : true,step : 1});
				</script>
		</c:if>
		</display:column>
		<display:column title="CDA Amount" style="width:6%;vertical-align:middle"><input type="text" size="7" name="cdaAmount" id="cdaAmountAdv<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<c:if test="${advance.daType=='cda'}"><a href="javascript:getTdAdvFinReport('${advance.referenceRequestID}','<%=i %>');">Report</a></c:if></display:column>
		 --%>
		<% i++; %>
	</display:table>
	</div>
		<div class="line" style="padding-top:3%;">

	<div class="line">
	<div class="quarter bold">Sanction No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text"  name="sanctionNo" id="sanctionNoAdv" onkeypress="javascript:return checkInt(event);"/></div>
	                </div>
	<div class="line">
	<div class="quarter bold">Bill No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="billNo" id="billNoAdv"/></div></div>
	<div class="line">
    <div class="quarter bold">Account Officer<span class="mandatory">*</span></div>
    <div class="quarter"><select name="accOfficer" id="accOfficerAdv" style="width:160px"><option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach> </select>
				</div>
	</div>
	</div>
	
	
	
	<script>
	  tadaFinAdvList = <%= (net.sf.json.JSONArray)session.getAttribute("tadaFinAdvList") %>;
	  tadaTdFinAdvCmplList = <%= (net.sf.json.JSONArray)session.getAttribute("tadaTdFinAdvCmplList") %>;
	  enableAdvList('advance');
	</script>
	<script>
			
	</script>
</div>
<!-- End : TadaTdAdvList.jsp -->





