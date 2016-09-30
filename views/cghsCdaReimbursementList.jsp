<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : cghsCdaReimbursementList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ page import="net.sf.json.JSONSerializer,java.util.List"%>

<%@page import="org.springframework.web.context.request.SessionScope"%><script type="text/javascript" src="script/RegExpValidate.js"></script>
<div>
	<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
	<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
	<c:if test="${message=='deleted'}"> <span class="success"><spring:message code="delete"/></span></c:if>
	<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
</div>
<div id="reimbursementTable">

<%int i=0; %>
<display:table name="${sessionScope.cdareimbursementList}" excludedParams="*"
	export="false" class="list" requestURI="" id="reimbursement" pagesize="10"
sort="list" >
	<display:column  style="width:3%;text-align:center" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'rowr\');"/>'>
		<input type="checkbox" class="rowr" name="row" value="${reimbursement.requestId}" id="reim<%=i %>" onclick="checkBoxCheck(this.name);"/>
	</display:column>
	<display:column title="Req ID" style="width:3%" >&nbsp;${reimbursement.requestId}</display:column>				 		
	<display:column title="SFID" style="width:6%" >&nbsp;${reimbursement.sfID}</display:column>
	<display:column title="Employee" style="width:12%" >&nbsp;${reimbursement.employeeName}</display:column>
	<display:column title="Sanction No<span class='mandatory' >*</span>" style="width:6%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${reimbursement.sanctionNo}" name="sanctionNo" id="sanctionNoReim<%=i %>" onkeypress="return checkInt(event,'sanctionNoReim');"  readonly="readonly"/></display:column>
	<display:column title="Bill No<span class='mandatory' >*</span>" style="width:6%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${reimbursement.billNo}" name="billNo" id="billNoReim<%=i %>" onkeypress="return checkInt(event,'billNoReim');" readonly="readonly" /></display:column>
	<display:column title="Finance Amount" style="width:8%;vertical-align:middle">&nbsp;${reimbursement.issuedAmount}</display:column>
	
	<display:column title="CFA Sig<span class='mandatory' >*</span>" style="width:12%">&nbsp;
			<select name="repSig" id="repSigReim<%=i %>" style="width:100px" disabled="disabled"> 
				<option value="select">Select</option>
				<c:forEach var="cfa" items="${sessionScope.CfaOfficerList}">
					<option value="${cfa.sfid}">${cfa.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('repSigReim<%=i %>','${reimbursement.repSig}');</script>
	</display:column>
	<display:column  title="Acc Officer.<span class='mandatory' >*</span>" style="width:12%">&nbsp;
			<select name="accOfficer" id="accOfficerReim<%=i %>" style="width:100px" disabled="disabled">
				<option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('accOfficerReim<%=i %>','${reimbursement.accOfficer}');</script>
		</display:column>
	<display:column title="CDA Amount<span class='mandatory'>*</span>" style="width:6%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${reimbursement.issuedAmount}" name="cdaAmount" id="cdaAmountReim<%=i %>" onkeypress="return checkInt(event,'cdaAmountReim');" /></display:column>
	<display:column title="Report" style="width:6%;vertical-align:middle">&nbsp;<a href="javascript:getReport('${reimbursement.requestId}','reimbursement');">Report</a></display:column>
	
	<%-- <display:column title="CDA Amount" style="width:9%" >&nbsp;<input type="text" size="10" name="cdaAmount" id="cdaAmountReim<%=i %>" onkeypress="return checkInt(event,'cdaAmountReim');"/></display:column>--%>
	<% i++; %>
</display:table>
<div class="line" style="padding-top:3%;">
<div class="line">
	<div class="quarter bold">DV.No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text"  name="dvNo" id="dvNoReim" onkeypress="javascript:return checkInt(event);"/></div>
	                </div>
	<%-- <div class="line">
	<div class="quarter bold">DV.Date <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="dvDate" id="dvDateReim"/>
			
			<script type="text/javascript">
			new tcal({'formname':'cghscdamedical','controlname':'dvDateReim'});
			</script>
			
			
			</div>
				</div>--%>
				<div class="line">
	<div class="quarter bold">DV.Date <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="dvDate" id="dvDateReim" readonly="readonly"/>
			<img  src="./images/calendar.gif" id="dvDateReimImg" />	
				<script type="text/javascript">
					Calendar.setup({inputField :"dvDateReim",ifFormat : "%d-%b-%Y",showsTime : false,button :"dvDateReimImg",singleClick : true,step : 1});
				</script></div>
				</div>
				
	</div>

<%-- 
  <script type="text/javascript">
   $jq( function(){
					$jq("#reimbursementTable").displayTagAjax('reimpaging');
				})
</script>--%>
</div>

<!-- End : cghsCdaReimbursementList.jsp -->





