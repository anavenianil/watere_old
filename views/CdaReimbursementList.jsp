<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : CdaReimbursementList.jsp -->

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
<div id="cdaReimPagination">
<%int i=0; %>
	<display:table name="${sessionScope.tadaFinReimSanctionList}" excludedParams="*"
		export="false" class="list" requestURI="" id="cdaReim" pagesize="20"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle"  title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
			
	
	<input type="checkbox"  value="${cdaReim.requestId}" class="row" name="row" id="cdaReim<%=i%>" onclick="checkBoxCheck(this.name);"/>
	
</display:column>
		
		<display:column title="Req ID" style="width:3%;vertical-align:middle">&nbsp;<a href="javascript:getRequestDetails('${cdaReim.historyID}','${cdaReim.referenceRequestID}','myRequests','pending','')"><font color="blue">${cdaReim.requestId}</font></a></display:column>
		<display:column title="SFID" style="width:6%;vertical-align:middle">&nbsp;${cdaReim.sfid}</display:column>
		<display:column title="Employee" style="width:8%;vertical-align:middle">&nbsp;<input type="hidden" value="${cdaReim.name}" name="employeeName" id="employeeNameReim<%=i %>"/>${cdaReim.name}</display:column>
		<display:column title="Authority to Move" style="width:12%;vertical-align:middle;">&nbsp;
		  <c:if test="${cdaReim.authorizedMove=='1'}">Build-Up</c:if><c:if test="${cdaReim.authorizedMove=='2'}">Project-${cdaReim.projectName}</c:if>
		</display:column>
		<display:column title="Amount" style="width:5%;vertical-align:middle">&nbsp;<input type="hidden" size="7" value="${cdaReim.tdSettAmountAftRes}" name="tadaAdvanceAmount" id="tadaAdvanceAmount<%=i %>"/>${cdaReim.tdSettAmountAftRes}</display:column>
		<display:column title="Sanction No" style="width:8%;vertical-align:middle">&nbsp;<input type="hidden" size="7" value="${cdaReim.sanctionNo}" name="sanctionNo" id="sanctionNoReim<%=i %>"/>${cdaReim.sanctionNo}</display:column>
		<display:column title="Bill No " style="width:8%;vertical-align:middle">&nbsp;<input type="hidden" size="5" value="${cdaReim.billNo}" name="billNo" id="billNoReim<%=i %>"/>${cdaReim.billNo}</display:column>
		<display:column title="Acc Officer." style="width:12%;vertical-align:middle">&nbsp;<input type="hidden" value="${cdaReim.accountentSign}"/><c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<c:if test="${cdaReim.accountentSign eq acc.sfid}">${acc.name}
					<input type="hidden" name="accOfficer" id="accOfficerReim<%=i %>" value="${acc.sfid}" />
					</c:if>
				</c:forEach>
				
				</display:column>
		<!-- <select name="accOfficer" id="accOfficerReim<%=i %>" style="width:80px">
				<option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('accOfficerReim<%=i %>','${cdaReim.accountentSign}');</script>
		-->
		<%-- <display:column title="DV.No" style="width:8%;vertical-align:middle">&nbsp; ${cdaReim.dvNo} </display:column>
		<display:column title="DV.Date" style="width:14%;vertical-align:middle">&nbsp;${cdaReim.dvDate}</display:column>
		<display:column title="CDA Amount" style="width:6%;vertical-align:middle">&nbsp;${cdaReim.cdaAmount}</display:column>
	 --%>
	 		<display:column title="CDA Amount" style="width:6%;vertical-align:middle"><input type="text" size="7" name="cdaAmount" id="cdaAmountReim<%=i %>" value="${cdaReim.tdSettAmountAftRes}" onkeypress="javascript:return checkInt(event);" onkeyup="javascript:finalCdaAmount('cdaReim','cdaAmountReim<%=i %>','tadaAdvanceAmount<%=i %>');"/></display:column> 
	
		<display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<a href="javascript:getTdInitialReport('${cdaReim.referenceRequestID}','tadaTdReimbursement');">Report</a></display:column>
		<% i++; %>
	</display:table>
	</div>
	<div class="line" style="padding-top:3%;">

	<div class="line">
	<div class="quarter bold">DV.No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text"  name="dvNo" id="dvNoReim" onkeypress="javascript:return checkInt(event);"/></div>
	                </div>
	<div class="line">
	<div class="quarter bold">DV.Date <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="dvDate" id="dvDateReim" readonly="readonly"/>
			<img  src="./images/calendar.gif" id="dvDateReimImg" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"dvDateReim",ifFormat : "%d-%b-%Y",showsTime : false,button :"dvDateReimImg",singleClick : true,step : 1});
				</script></div>
				</div>
	<!-- <div class="line">
    <div class="quarter bold">CDA Amount<span class="mandatory">*</span></div>
    <div class="quarter"><input type="text" name="cdaAmount" id="cdaAmountReim" onkeypress="javascript:return checkInt(event);"/></div>
	</div> -->
	</div>
	
	<%-- <script>
	  tadaFinReimList = <%= (net.sf.json.JSONArray)session.getAttribute("tadaFinReimList") %>;
	  tadaTdFinReimCmplList = <%= (net.sf.json.JSONArray)session.getAttribute("tadaTdFinReimCmplList") %>;
	  enableReimList();
	</script>--%>
	<script>
			$jq( function(){
					$jq("#cdaReimPagination").displayTagAjax('cdaReimbursementPaging');
				})
	</script> 
</div>
<!-- End : CdaReimbursementList.jsp -->
