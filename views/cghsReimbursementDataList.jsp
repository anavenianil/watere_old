<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : cghsReimbursementDataList.jsp -->

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
<display:table name="${sessionScope.reimbursementList}" excludedParams="*"
	export="false" class="list" requestURI="" id="reimbursement" pagesize="10"
sort="list" >
	<display:column  style="width:3%;text-align:center" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'rowr\');"/>'>
		<input type="checkbox" class="rowr" name="rowr" value="${reimbursement.requestId}" id="reim<%=i %>" onclick="checkBoxCheck(this.name);"/>
	</display:column>
	<display:column title="Req ID" style="width:3%" >&nbsp;${reimbursement.requestId}</display:column>				 		
	<display:column title="SFID" style="width:6%" >&nbsp;${reimbursement.sfID}</display:column>
	<display:column title="Employee" style="width:12%" >&nbsp;${reimbursement.employeeName}</display:column>
	<display:column title="Amount" style="width:9%" >&nbsp;${reimbursement.admissibleAmount}</display:column>
	<display:column title="Finance Amount" style="width:9%" >&nbsp;<input type="text" size="10" name="Amount" value ="${reimbursement.admissibleAmount}"  id="AmountReim<%=i %>" onkeypress="return checkInt(event,'AmountReim');"/></display:column>
	
	<%--<display:column title="Sanction No<span class='mandatory' >*</span>" style="width:6%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${reimbursement.sanctionNo}" name="sanctionNo" id="sanctionNoReim<%=i %>" onkeypress="return checkInt(event,'sanctionNoReim');"/></display:column>
	<display:column title="Bill No<span class='mandatory' >*</span>" style="width:6%;vertical-align:middle">&nbsp;<input type="text" size="7" value="${reimbursement.billNo}" name="billNo" id="billNoReim<%=i %>" onkeypress="return checkInt(event,'billNoReim');"/></display:column>--%>
<%-- 	<display:column title="CFA Sig<span class='mandatory' >*</span>" style="width:12%">&nbsp;
			<select name="repSig" id="repSigReim<%=i %>" style="width:100px">
				<option value="select">Select</option>
				<c:forEach var="cfa" items="${sessionScope.CfaOfficerList}">
					<option value="${cfa.sfid}">${cfa.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('repSigReim<%=i %>','${reimbursement.repSig}');</script>
	</display:column>--%>
<%-- 	<display:column  title="Acc Officer.<span class='mandatory' >*</span>" style="width:12%">&nbsp;
			<select name="accOfficer" id="accOfficerReim<%=i %>" style="width:100px" >
				<option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach>
			</select>
			<script>setSelectedValue('accOfficerReim<%=i %>','${reimbursement.accOfficer}');</script>
		</display:column>
	<display:column title="DV.No" style="width:8%" >&nbsp;<input type="text" size="10" value="${reimbursement.dvNo}" name="dvNo" id="dvNoReim<%=i %>" onkeypress="return checkInt(event,'dvNoReim');"/></display:column>
	<display:column title="DV.Date"  style="width:15%">&nbsp;
			<input type="text" size="13" value="${reimbursement.dvDate}" readonly="readonly" name="dvDate" id="dvDateReim<%=i %>" />
			<script type="text/javascript">
											new tcal({'formname':'cghsmedical','controlname':'dvDateReim<%=i %>'});
											</script>
	</display:column>

	<display:column title="CDA Amount" style="width:9%" >&nbsp;<input type="text" size="10" name="cdaAmount" id="cdaAmountReim<%=i %>" onkeypress="return checkInt(event,'cdaAmountReim');"/></display:column>--%>

	<display:column title="CDA Amount" style="width:9%" >&nbsp;<font size="4.5em"><span class="WebRupee" >R</span></font><input type="text" size="10" name="cdaAmount" id="cdaAmountReim<%=i %>" onkeypress="return checkInt(event,'cdaAmountReim');"/></display:column>

	<% i++; %>
</display:table>
<div class="line" style="padding-top:3%;">

	<div class="line">
	<div class="quarter bold">Sanction No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text"  name="sanctionNo" id="sanctionNoReim" onkeypress="javascript:return checkInt(event);"/></div>
	                </div>
	<div class="line">
	<div class="quarter bold">Bill No <span class="mandatory">*</span></div>
	<div class="quarter"><input type="text" name="billNo" id="billNoReim" onkeypress="javascript:return checkInt(event);"/></div></div>
	<div class="line">
    <div class="quarter bold">Account Officer<span class="mandatory">*</span></div>
    <div class="quarter"><select name="accOfficer" id="accOfficerReim" style="width:160px"><option value="select">Select</option>
				<c:forEach var="acc" items="${sessionScope.AccountOfficerList}">
					<option value="${acc.sfid}">${acc.name}</option>
				</c:forEach> </select>
				</div>
	</div>
	
	<div class="line">
    <div class="quarter bold">CFA Sig<span class="mandatory">*</span></div>
    <div class="quarter"><select name="repSig" id="repSigReim" style="width:160px"><option value="select">Select</option>
				<c:forEach var="cfa" items="${sessionScope.CfaOfficerList}">
					<option value="${cfa.sfid}">${cfa.name}</option>
				</c:forEach> </select>
				</div>
	</div>
	</div>





  <script type="text/javascript">
   $jq( function(){
					$jq("#reimbursementTable").displayTagAjax('reimpaging');
				})
</script>
</div>

<!-- End : cghsReimbursementDataList.jsp -->