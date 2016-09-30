<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : NonCdaCghsReimList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<div>
	<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='deleted'}"> <span class="success"><spring:message code="delete"/></span></c:if>
	<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
</div>
<div id="nonCghsReimTable">
<%int i=0; %>
	<display:table name="${sessionScope.cdanoncghsreimList}" excludedParams="*"
		export="false" class="list" requestURI="" id="nonCghsReim" pagesize="10"
		sort="list">
		<display:column  style="width:3%;text-align:center" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'rown\');"/>'>
			<input type="checkbox" value="${nonCghsReim.requestId}" class="rown" name="row" id="nonCghs<%=i %>" onclick="checkBoxCheck(this.name);"/>
		</display:column>
		<display:column title="Req ID" style="width:3%">&nbsp;${nonCghsReim.requestId}</display:column>
		<display:column title="SFID" style="width:6%">&nbsp;${nonCghsReim.sfID}</display:column>
		<display:column title="Employee" style="width:8%">&nbsp;${nonCghsReim.employeeName}</display:column>
		<display:column title="Finance Amount" style="width:8%;vertical-align:middle">&nbsp;${nonCghsReim.issuedAmount}</display:column>
		<display:column title="Sanction No<span class='mandatory'>*</span>" style="width:6%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${nonCghsReim.sanctionNo}" name="sanctionNo" id="sanctionNoNon<%=i %>" onkeypress="return checkInt(event,'sanctionNoNon');" disabled="disabled"/></display:column>
		<display:column title="Bill No<span class='mandatory'>*</span>" style="width:6%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${nonCghsReim.billNo}" name="billNo" id="billNoNon<%=i %>" onkeypress="return checkInt(event,'billNoNon');"  disabled="disabled"/></display:column>
		
		<display:column title="CFA Sig<span class='mandatory'>*</span>" style="width:12%">&nbsp;
			<select name="repSig" id="repSigNon<%=i %>" style="width:100px" disabled="disabled">
				<option value="select">Select</option>
				<c:forEach var="cfa" items="${sessionScope.CfaOfficerList}">
					<option value="${cfa.sfid}">${cfa.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('repSigNon<%=i %>','${nonCghsReim.repSig}');</script>
		</display:column>
		<display:column title="Acc Officer.<span class='mandatory'>*</span>" style="width:12%">&nbsp;
			<select name="accOfficer" id="accOfficerNon<%=i %>" style="width:100px"  disabled="disabled">
				<option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('accOfficerNon<%=i %>','${nonCghsReim.accOfficer}');</script>
		</display:column>
		<%--<display:column title="DV.No" style="width:8%">&nbsp;<input type="text" size="10" value="${nonCghsReim.dvNo}" name="dvNo" id="dvNoNon<%=i %>" onkeypress="return checkInt(event,'dvNoNon');"/></display:column>
		<display:column title="DV.Date" style="width:15%">&nbsp;
			<input type="text" size="13" value="${nonCghsReim.dvDate}" readonly="readonly" name="dvDate" id="dvDateNon<%=i %>"/>
			<script type="text/javascript">
											new tcal({'formname':'cghsmedical','controlname':'dvDateNon<%=i %>'});
											</script>
		</display:column> --%>
		<display:column title="CDA Amount" style="width:8%">&nbsp;<input type="text" size="10" name="cdaAmount"  value="${nonCghsReim.issuedAmount}" id="cdaAmountNon<%=i %>" onkeypress="return checkInt(event,'cdaAmountNon');"/></display:column>
		<display:column title="Report" style="width:6%">&nbsp;<a href="javascript:getReport('${nonCghsReim.requestId}','nonCghsReim');">Report</a></display:column>
		<% i++; %>
	</display:table>
	
	<div class="line" style="padding-top:3%;">
<div class="line">
	<div class="quarter bold">DV.No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text"  name="dvNo" id="dvNoNon" onkeypress="javascript:return checkInt(event);"/></div>
	                </div>
	<%-- <div class="line">
	<div class="quarter bold">DV.Date <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="dvDate" id="dvDateNon"/>
			
			<script type="text/javascript">
			new tcal({'formname':'cghscdamedical','controlname':'dvDateNon'});
			</script>
			
			
			</div>
				</div>--%>
				
				<div class="line">
	<div class="quarter bold">DV.Date <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="dvDate" id="dvDateNon" readonly="readonly"/>
			<img  src="./images/calendar.gif" id="dvDateNonImg" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"dvDateNon",ifFormat : "%d-%b-%Y",showsTime : false,button :"dvDateNonImg",singleClick : true,step : 1});
				</script></div>
				</div>
	</div>
	
	<%--<script type="text/javascript">
   $jq( function(){
					$jq("#nonCghsReimTable").displayTagAjax('nonCghsPaging');
				})
</script>--%> 
	
</div>
<!-- End : NonCdaCghsReimList.jsp -->





