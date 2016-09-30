<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : CDATadaTdAdvList.jsp -->

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
<div id="cdaAdvPagination">
<%int i=0; %>
	<display:table name="${sessionScope.tadaTdFinAdvSanctionList}" excludedParams="*"
		export="false" class="list" requestURI="" id="cdaAdv" pagesize="20"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			<input type="checkbox" value="${cdaAdv.requestId}" class="row" name="row" id="cdaAdv<%=i %>" onclick="checkBoxCheck(this.name);"/>
		</display:column>
		<display:column title="Req ID" style="width:3%;vertical-align:middle">&nbsp;<a href="javascript:getRequestDetails('${cdaAdv.historyID}','${cdaAdv.referenceRequestID}','myRequests','pending','')"><font color="blue">${cdaAdv.referenceRequestID}</font></a></display:column>
		<display:column title="SFID" style="width:6%;vertical-align:middle">&nbsp;${cdaAdv.sfid}</display:column>
		<display:column title="Employee" style="width:8%;vertical-align:middle">&nbsp;<input type="hidden" value="${cdaAdv.name}" name="employeeName" id="employeeNameAdv<%=i %>"/>${cdaAdv.name}</display:column>
		<display:column title="Amount" style="width:5%;vertical-align:middle">&nbsp;<input type="hidden" size="7" value="${cdaAdv.advanceAmountAftRes}" name="tadaAdvanceAmount" id="tadaAdvanceAmount<%=i %>"/>${cdaAdv.advanceAmountAftRes}</display:column>
		
		<display:column title="Journey Date & Issuing Authority" style="width:12%;vertical-align:middle;">&nbsp;
		${cdaAdv.departureDateOne}<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="hidden" name="issueAuthority" id="issueAuthority<%=i %>" value="${cdaAdv.daType}" /><c:if test="${cdaAdv.daType=='asl'}">ASL</c:if><c:if test="${cdaAdv.daType=='cda'}">CDA</c:if>
		</display:column>
		
		<display:column title="Authority to Move" style="width:12%;vertical-align:middle;">&nbsp;
		  <c:if test="${cdaAdv.authorizedMove=='1'}">Build-Up</c:if><c:if test="${cdaAdv.authorizedMove=='2'}">Project-${cdaAdv.projectName}</c:if>
		</display:column>
		
<display:column title="Sanction No" style="width:8%;vertical-align:middle">&nbsp;<input type="hidden" size="7" value="${cdaAdv.sanctionNo}" name="sanctionNo" id="sanctionNoAdv<%=i %>"/>${cdaAdv.sanctionNo}</display:column>
		<display:column title="Bill No " style="width:8%;vertical-align:middle">&nbsp;<input type="hidden" size="7" value="${cdaAdv.billNo}" name="billNo" id="billNoAdv<%=i %>"/>${cdaAdv.billNo}</display:column>
		<display:column title="Acc Officer." style="width:12%;vertical-align:middle">&nbsp;<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<c:if test="${cdaAdv.accountentSign eq acc.sfid}">${acc.name}
					<input type="hidden" name="accOfficer" id="accOfficerAdv<%=i %>" value="${acc.sfid}" />
					
					</c:if>
				</c:forEach></display:column>
		
			
	<%-- 	<display:column title="DV.No" style="width:7%;vertical-align:middle">&nbsp;<c:if test="${cdaAdv.daType=='cda'}"><input type="text" size="6" value="${cdaAdv.dvNo}" name="dvNo" id="dvNoAdv<%=i %>" onkeypress="javascript:return checkInt(event);"/></c:if></display:column>
		<display:column title="DV.Date" style="width:15%;vertical-align:middle">&nbsp;
		<c:if test="${advance.daType=='cda'}"><input type="text" size="4" value='${cdaAdv.dvDate}' readonly="readonly" name="dvDate" id="dvDateAdv<%=i %>" />
			<img  src="./images/calendar.gif" id="dvDateAdvImg<%=i %>" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"dvDateAdv<%=i %>",ifFormat : "%d-%b-%Y",showsTime : false,button :"dvDateAdvImg<%=i %>",singleClick : true,step : 1});
				</script>
		</c:if>
		</display:column> --%>
		<display:column title="CDA Amount" style="width:6%;vertical-align:middle"><input type="text" size="7" name="cdaAmount" id="cdaAmountAdv<%=i %>" value="${cdaAdv.advanceAmountAftRes}" onkeypress="javascript:return checkInt(event);" onkeyup="javascript:finalCdaAmount('cdaAdv','cdaAmountAdv<%=i %>','tadaAdvanceAmount<%=i %>');"/></display:column> 
		
		<%-- <display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<c:if test="${cdaAdv.daType=='cda'}"><a href="javascript:getTdAdvFinReport('${cdaAdv.referenceRequestID}','<%=i %>');">Report</a></c:if></display:column>
		 --%>
		<% i++; %>
	</display:table>
	</div>
	<div class="line" style="padding-top:3%;">

	<div class="line">
	<div class="quarter bold">DV.No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text"  name="dvNo" id="dvNoAdv" onkeypress="javascript:return checkInt(event);"/></div>
	                </div>
	<div class="line">
	<div class="quarter bold">DV.Date <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="dvDate" id="dvDateAdv" readonly="readonly"/>
			<img  src="./images/calendar.gif" id="dvDateAdvImg" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"dvDateAdv",ifFormat : "%d-%b-%Y",showsTime : false,button :"dvDateAdvImg",singleClick : true,step : 1});
				</script></div>
				</div>
	<!-- <div class="line">
    <div class="quarter bold">CDA Amount<span class="mandatory">*</span></div>
    <div class="quarter"><input type="text" name="cdaAmount" id="cdaAmountAdv" onkeypress="javascript:return checkInt(event);"/></div>
	</div> -->
	</div>
	
<%-- 	<script>
	  tadaFinAdvList = <%= (net.sf.json.JSONArray)session.getAttribute("tadaFinAdvList") %>;
	  tadaTdFinAdvCmplList = <%= (net.sf.json.JSONArray)session.getAttribute("tadaTdFinAdvCmplList") %>;
	  enableAdvList('advance');
	</script> --%>
	<script>
			$jq( function(){
					$jq("#cdaAdvPagination").displayTagAjax('cdaAdvancePaging');
				})
	</script>
</div>
<!-- End : CDATadaTdAdvList.jsp -->





