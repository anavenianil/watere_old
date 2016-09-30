<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : CdaTadaTdSettlementList.jsp -->

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
<div id="cdaSettPagination">
<%int i=0; %>
	<display:table name="${sessionScope.tadaFinSettlementSanctionList}" excludedParams="*"
		export="false" class="list" requestURI="" id="cdaSettlement" pagesize="20"
		sort="list" cellpadding="2" cellspacing="1">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
				<input type="checkbox" value="${cdaSettlement.requestId}" class="row" name="row" id="cdaSettlement<%=i %>" onclick="checkBoxCheck(this.name);"/>
			
			
			
		</display:column>
		<display:column title="Req ID" style="width:3%;vertical-align:middle">&nbsp;<a href="javascript:getRequestDetails('${cdaSettlement.historyID}','${cdaSettlement.referenceRequestID}','myRequests','pending','')"><font color="blue">${cdaSettlement.referenceRequestID}</font></a></display:column>
		<display:column title="SFID" style="width:6%;vertical-align:middle">&nbsp;${cdaSettlement.sfid}</display:column>
		<display:column title="Employee" style="width:8%;vertical-align:middle">&nbsp;<input type="hidden" value="${cdaSettlement.name}" name="employeeName" id="employeeNameSett<%=i %>"/>${cdaSettlement.name}</display:column>
		<display:column title="Authority to Move" style="width:12%;vertical-align:middle;">&nbsp;
		  <c:if test="${cdaSettlement.authorizedMove=='1'}">Build-Up</c:if><c:if test="${cdaSettlement.authorizedMove=='2'}">Project-${cdaSettlement.projectName}</c:if>
		</display:column>
		<display:column title="Amount" style="width:5%;vertical-align:middle">&nbsp;<input type="hidden" size="7" value="${cdaSettlement.tadaSettlementAmount}" name="tadaAdvanceAmount" id="tadaAdvanceAmount<%=i %>"/>
		<c:if test="${cdaSettlement.tadaAdvanceAmount<cdaSettlement.tdSettAmountAftRes}"><input type="hidden" size="7" value="${cdaSettlement.tdSettAmountAftRes-cdaSettlement.tadaAdvanceAmount}" name="finalSettAmount" id="finalSettAmount<%=i %>"/>${cdaSettlement.tdSettAmountAftRes-cdaSettlement.tadaAdvanceAmount}</c:if>
		<c:if test="${cdaSettlement.tadaAdvanceAmount>cdaSettlement.tdSettAmountAftRes}"><input type="hidden" size="7" value='0' name="finalSettAmount" id="finalSettAmount<%=i %>"/> 0</c:if>
		</display:column>
		<display:column title="Sanction No" style="width:8%;vertical-align:middle">&nbsp;<input type="hidden" size="7" value="${cdaSettlement.sanctionNo}" name="sanctionNo" id="sanctionNoSett<%=i %>"/>${cdaSettlement.sanctionNo}</display:column>
		
		<display:column title="Bill No " style="width:8%;vertical-align:middle">&nbsp;<input type="hidden" size="5" value="${cdaSettlement.billNo}" name="billNo" id="billNoSett<%=i %>"/>${cdaSettlement.billNo}</display:column>
		        <display:column title="Acc Officer." style="width:12%;vertical-align:middle">&nbsp;<input type="hidden" value="${cdaSettlement.accountentSign}"/><c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<c:if test="${cdaSettlement.accountentSign eq acc.sfid}">${acc.name}
					<input type="hidden" name="accOfficer" id="accOfficerSett<%=i %>" value="${acc.sfid}" />
					</c:if>
				</c:forEach>
				
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
			<script>setSelectedValue('accOfficerSett<%=i %>','${cdaSettlement.accountentSign}');</script>
		</display:column> --%>
		<%-- <%-- <display:column title="DV.No" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${settlement.dvNo}" name="dvNo" id="dvNoSett<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		<display:column title="DV.Date" style="width:14%;vertical-align:middle">&nbsp;
		<input type="text" size="12" value='${settlement.dvDate}' readonly="readonly" name="dvDate" id="dvDateSett<%=i %>"/>
			<img  src="./images/calendar.gif" id="dvDateSettImg<%=i %>" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"dvDateSett<%=i %>",ifFormat : "%d-%b-%Y",showsTime : false,button :"dvDateSettImg<%=i %>",singleClick : true,step : 1});
				</script>
		</display:column>
		<display:column title="CDA Amount" style="width:6%;vertical-align:middle"><input type="text" size="7" name="cdaAmount" id="cdaAmountSett<%=i %>" onkeypress="javascript:return checkInt(event);"/></display:column>
		 --%> --%>
		<%-- <%-- <display:column title="DV.No" style="width:8%;vertical-align:middle">&nbsp; ${cdaSettlement.dvNo} </display:column>
		<display:column title="DV.Date" style="width:14%;vertical-align:middle">&nbsp;${cdaSettlement.dvDate}</display:column>
		<display:column title="CDA Amount" style="width:6%;vertical-align:middle">&nbsp;${cdaSettlement.cdaAmount}</display:column> --%> --%>
	<display:column title="CDA Amount" style="width:6%;vertical-align:middle">
	<c:if test="${cdaSettlement.tadaAdvanceAmount<cdaSettlement.tdSettAmountAftRes}"><input type="text" size="7" name="cdaAmount" id="cdaAmountSett<%=i %>" value="${cdaSettlement.tdSettAmountAftRes-cdaSettlement.tadaAdvanceAmount}"   onkeypress="javascript:return checkInt(event);" onkeyup="javascript:finalCdaAmount('cdaSettlement','cdaAmountSett<%=i %>','finalSettAmount<%=i %>');"/></c:if>            
	<c:if test="${cdaSettlement.tadaAdvanceAmount>cdaSettlement.tdSettAmountAftRes}"><input type="text" size="7" name="cdaAmount" id="cdaAmountSett<%=i %>" value="0"  onkeypress="javascript:return checkInt(event);" onkeyup="javascript:finalCdaAmount('cdaSettlement','cdaAmountSett<%=i %>','finalSettAmount<%=i %>');"/></c:if>
	</display:column>   <!--These two condition has been added for display the amount  -->
	
	
	<%-- <display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<a href="javascript:getTdInitialReport('${cdaSettlement.referenceRequestID}','tdSettTourParticulars');">Report</a></display:column>
	 --%>
	 
	 
	 	<% i++; %>
	</display:table>
	</div>
		<div class="line" style="padding-top:3%;">

	<div class="line">
	<div class="quarter bold">DV.No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text"  name="dvNo" id="dvNoSett" onkeypress="javascript:return checkInt(event);"/></div>
	                </div>
	<div class="line">
	<div class="quarter bold">DV.Date <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="dvDate" id="dvDateSett" readonly="readonly"/>
			<img  src="./images/calendar.gif" id="dvDateSettImg" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"dvDateSett",ifFormat : "%d-%b-%Y",showsTime : false,button :"dvDateSettImg",singleClick : true,step : 1});
				</script></div>
				</div>
	<!-- <div class="line">
    <div class="quarter bold">CDA Amount<span class="mandatory">*</span></div>
    <div class="quarter"><input type="text" name="cdaAmount" id="cdaAmountSett" onkeypress="javascript:return checkInt(event);"/></div>
	</div> -->
	</div>
	<%-- <script>
	  tadaFinSettlementList = <%= (net.sf.json.JSONArray)session.getAttribute("tadaFinSettlementList") %>;
	  tadaTdFinSettlementCmplList = <%= (net.sf.json.JSONArray)session.getAttribute("tadaTdFinSettlementCmplList") %>;
	  enableSettList();
	</script>--%>
	<script>
			$jq( function(){
					$jq("#cdaSettPagination").displayTagAjax('cdaSettlementPaging');
				})
	</script> 
</div>
<!-- End :CdaTadaTdSettlementList.jsp -->





