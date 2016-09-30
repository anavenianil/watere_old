<!-- begin:CreateWorkflowStages.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<fieldset><legend><strong><font color='green'>Income Tax Stages</font></strong></legend>
<div class="line">
		<table width="100%" id="dynIncomeTax" cellpadding="0" cellspacing="0" align="center">
			
			<tr id="row0">
			<td>
					<spring:bind path="payBillMaster">
						<form:input path="payBillMaster.from"  cssClass="formClass" id="from0"/>
					</spring:bind>
			</td>
			<td>
					<spring:bind path="payBillMaster">
						<form:input path="payBillMaster.to"  cssClass="formClass" id="to0"/>
					</spring:bind>
			</td>
			<td><input type="button" id="add" class="smallbtn" value="+" onclick="javascript:insertNewIncomeRow('0','dynIncomeTax');"/> </td>
			<td><input type="button" id="delete" class="smallbtn" value="-" onclick="javascript:deleteIncomeRow('0','dynIncomeTax');"/> </td>	
			</tr>
	  </table>
</div>
<div class="line">		
		<div class="appbutton submitbutton"><a class="quarterbutton" href="javascript:clearWorkFlowStages();">Clear</a></div>
		<c:if test="${type ne 'old'}">
			<div id="wfsubmit" class="appbutton submitbutton"><a class="quarterbutton" href="javascript:manageWorkflowStages();">Submit</a></div>
		</c:if>
		<c:if test="${type eq 'old'}">
			<div id="wfdelete" class="appbutton submitbutton"><a class="quarterbutton" href="javascript:deleteWorkFlow();">Delete</a></div>
			<div id="wfupdate" class="appbutton submitbutton"><a class="quarterbutton" href="javascript:manageWorkflowStages();">Update</a></div>
		</c:if>
		
	</div>
	<script>
		loadScript('dynIncomeTax');
	</script>
</fieldset>