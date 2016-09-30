<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : CghsCdaEmergencyList.jsp -->

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
<div id="cghsEmergencyTable">
<%int i=0; %>
	<display:table name="${sessionScope.cdaemergencyList}" excludedParams="*"
		export="false" class="list" requestURI="" id="emergency" pagesize="10"
		sort="list">
		<display:column  style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'rowe\');"/>'>
			<input type="checkbox" value="${emergency.requestId}" class="rowe" name="row" id="emer<%=i %>" onclick="checkBoxCheck(this.name);"/>
		</display:column>
		<display:column title="Req ID" style="width:3%;vertical-align:middle">&nbsp;${emergency.requestId}</display:column>
		<display:column title="SFID" style="width:6%">&nbsp;${emergency.sfID}</display:column>
		<display:column title="Employee" style="width:8%;vertical-align:middle">&nbsp;${emergency.employeeName}</display:column>
		<display:column title="Sanction No<span class='mandatory'>*</span>" style="width:6%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${emergency.sanctionNo}" name="sanctionNo" id="sanctionNoEmer<%=i %>" onkeypress="return checkInt(event,'sanctionNoEmer');" readonly="readonly"/></display:column>
		<display:column title="Bill No<span class='mandatory'>*</span>" style="width:6%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${emergency.billNo}" name="billNo" id="billNoEmer<%=i %>" onkeypress="return checkInt(event,'billNoEmer');" readonly="readonly"/></display:column>
			<display:column title="Finance Amount" style="width:8%;vertical-align:middle">&nbsp;${emergency.issuedAmount}</display:column>
		<display:column title="CFA Sig<span class='mandatory'>*</span>" style="width:12%" >&nbsp;
			<select name="repSig" id="repSigEmer<%=i %>" style="width:100px" disabled="disabled">
				<option value="select">Select</option>
				<c:forEach var="cfa" items="${sessionScope.CfaOfficerList}">
					<option value="${cfa.sfid}">${cfa.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('repSigEmer<%=i %>','${emergency.repSig}');</script>
		</display:column>
		<display:column title="Acc Officer.<span class='mandatory'>*</span>" style="width:12%;vertical-align:middle">&nbsp;
			<select name="accOfficer" id="accOfficerEmer<%=i %>" style="width:100px" disabled="disabled">
				<option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('accOfficerEmer<%=i %>','${emergency.accOfficer}');</script>
		</display:column>
		<%-- <display:column title="DV.No" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="10" value="${emergency.dvNo}" name="dvNo" id="dvNoEmer<%=i %>" onkeypress="return checkInt(event,'dvNoEmer');"/></display:column>
		<display:column title="DV.Date" style="width:15%;vertical-align:middle">&nbsp;
			<input type="text" size="13" value="${emergency.dvDate}" readonly="readonly" name="dvDate" id="dvDateEmer<%=i %>"/>
			<script type="text/javascript">
											new tcal({'formname':'cghsmedical','controlname':'dvDateEmer<%=i %>'});
											</script>
		</display:column>
		
		<display:column title="CDA Amount" style="width:8%;vertical-align:middle">&nbsp;<input type="text" size="10" name="cdaAmount" id="cdaAmountEmer<%=i %>" onkeypress="return checkInt(event,'cdaAmountEmer');"/></display:column>--%>
	
		<display:column title="CDA Amount<span class='mandatory'>*</span>" style="width:6%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${emergency.issuedAmount}" name="cdaAmount" id="cdaAmountEmer<%=i %>" onkeypress="return checkInt(event,'cdaAmountEmer');" /></display:column>
		
			<display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<a href="javascript:getReport('${emergency.requestId}','emergency');">Report</a></display:column>
		<% i++; %>
	</display:table>
	
	<div class="line" style="padding-top:3%;">
<div class="line">
	<div class="quarter bold">DV.No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text"  name="dvNo" id="dvNoEmer" onkeypress="javascript:return checkInt(event);"/></div>
	                </div>
	<%--<div class="line">
	<div class="quarter bold">DV.Date <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="dvDate" id="dvDateEmer"/>
			
			<script type="text/javascript">
			new tcal({'formname':'cghscdamedical','controlname':'dvDateEmer'});
			</script>
			
			
			</div>
				</div> --%>
				<div class="line">
	<div class="quarter bold">DV.Date <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="dvDate" id="dvDateEmer" readonly="readonly"/>
			<img  src="./images/calendar.gif" id="dvDateEmerImg" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"dvDateEmer",ifFormat : "%d-%b-%Y",showsTime : false,button :"dvDateEmerImg",singleClick : true,step : 1});
				</script></div>
				</div>
	</div>
	
	
	
	
	
	<%-- 
	 <script type="text/javascript">
   $jq( function(){
					$jq("#cghsEmergencyTable").displayTagAjax('cghsEmergencyPaging');
				})
</script>--%>
</div>
<!-- End : CghsCdaEmergencyList.jsp -->





