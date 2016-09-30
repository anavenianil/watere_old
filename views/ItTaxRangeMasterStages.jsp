<!-- begin:ItTaxRangeMasterStages.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.incometax.dto.IncomeTaxStageDTO"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<fieldset><legend><strong><font color='green'>Income Tax Slabs</font></strong></legend>

<div class="line">
		<table width="100%" id="incomeTaxStages" cellpadding="2" cellspacing="0" align="center">
            <tr id="row0">
				<td style="width:20%">
					<spring:bind path="incomeTaxMaster">
					      <form:input path="incomeTaxMaster.FYearFrom" id="FYearFrom0" value="From"  onclick="javascript:emptyTextBox(this);" onkeypress="return checkInt(event);"/>	
					</spring:bind></td>
				<td  style="width:30%">
					<spring:bind path="incomeTaxMaster">
					     <form:input path="incomeTaxMaster.FYearTo" id="FYearTo0" value="To" onclick="javascript:emptyTextBox(this);" onkeypress="return checkInt(event);"/>	
					</spring:bind></td>
				<td  style="width:20%">
					<spring:bind path="incomeTaxMaster">
					     <form:input path="incomeTaxMaster.amount" id="amount0"  value="Tax" onclick="javascript:emptyTextBox(this);" onkeypress="return checkInt(event);"/>	
					</spring:bind></td>
				<td  style="width:10%"> % </td>
				<td  style="width:10%"><input type="button" id="add" class="smallbtn" value="+" onclick="javascript:insertNewRow('0','incomeTaxStages');"/> </td>
				<td  style="width:10%"><input type="button" id="delete" class="smallbtn" value="-" onclick="javascript:deleteRow('0','incomeTaxStages');"/> </td>
          </tr>
		</table>
</div>
<div class="line">		
	<div class="appbutton submitbutton"><a class="quarterbutton" href="javascript:clearIncomeTaxStages();">Clear</a></div>
	<spring:bind path="incomeTaxMaster">
		<c:if test="${incomeTaxMaster.type ne 'old'}">
			<div id="wfsubmit" class="appbutton submitbutton"><a class="quarterbutton" href="javascript:manageIncomeTaxStages();">Submit</a></div>
		</c:if>
	</spring:bind>
	<spring:bind path="incomeTaxMaster">
		<c:if test="${incomeTaxMaster.type eq 'old'}">
			<div id="wfdelete" class="appbutton submitbutton"><a class="quarterbutton" href="javascript:deleteIncometaxStages();">Delete</a></div>
			<div id="wfupdate" class="appbutton submitbutton"><a class="quarterbutton" href="javascript:manageIncomeTaxStages();">Update</a></div>
		</c:if>
	</spring:bind>
</div>
	<script>
		loadScript('incomeTaxStages');
		<% if (session.getAttribute("incomeTaxStageList")!=null ) { %>
			incomeTaxStageListJSON = <%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<IncomeTaxStageDTO>)session.getAttribute("incomeTaxStageList"))%>;
		<%}%>
			
		
	</script>
</fieldset>

<!-- End:ItTaxRangeMasterStages.jsp -->