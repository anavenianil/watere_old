<!-- begin:WorkflowStages.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<fieldset><legend><strong><font color='green'>Work Flow Stages</font></strong></legend>
	<div class="line">
		<table width="100%" id="dynWorkFlow">
			<tr>
				<th><spring:message code="workflow.from"/></th>				
				<th><spring:message code="workflow.relation"/><span class="mandatory">*</span></th>
				<th><spring:message code="workflow.to"/><span class="mandatory">*</span></th>
				<th><spring:message code="workflow.escalRel"/><span class="mandatory">*</span></th>
				<th><spring:message code="workflow.escalatedTo"/><span class="mandatory">*</span></th>	
				<th><spring:message code="workflow.stages"/><span class="mandatory">*</span></th>			
				<th><spring:message code="workflow.add"/></th>
				<th><spring:message code="workflow.delete"/></th>
			</tr>
			<tr>
				<td id="from0"><spring:bind path="workflowmap">
					<form:select path="workflowmap.from" disabled="true" cssClass="formClass" id="workflowfrom0">
						<form:option value="0">Select</form:option>
						<form:options items="${relationList}" itemValue="id" itemLabel="name"></form:options> 
					</form:select>
				</spring:bind></td>
				<td ><spring:bind path="workflowmap">
					<form:select path="workflowmap.relation" onchange="javascript:getRoleInstances(this);" cssClass="formClass" id="workrelation0" >
						<form:option value="0">Select</form:option>
						<form:options items="${relationShipList}" itemValue="id" itemLabel="name"></form:options> 
					</form:select>
				</spring:bind></td>
				<td id="roleto0" ><spring:bind path="workflowmap">
					<form:select path="workflowmap.to" onchange="javascript:ChangeValues(this);" cssClass="formClass" id="workflowto0" >
						<form:option value="0">Select</form:option>
						<form:options items="${relationList}" itemValue="id" itemLabel="name"></form:options> 
					</form:select></spring:bind></td>
				<td ><spring:bind path="workflowmap">
					<form:select path="workflowmap.esclateRelation"  onchange="javascript:getRoleInstances(this);" cssClass="formClass"  id="workescalaterelation0">
						<form:option value="0">Select</form:option>
						<form:options items="${relationShipList}" itemValue="id" itemLabel="name"></form:options> 
					</form:select>
				</spring:bind></td>			
				<td id="escalteTo0"><spring:bind path="workflowmap">
					<form:select path="workflowmap.escalteTo" cssClass="formClass" id="workescalateTo0">
						<form:option value="0">Select</form:option>
						<form:options items="${relationList}" itemValue="id" itemLabel="name"></form:options> 
					</form:select>
				</spring:bind></td>
				<td id="stages0"><spring:bind path="workflowmap">
					<form:select path="workflowmap.workflowStage" cssClass="formClass" id="workflowStage0">
						<form:option value="0">Select</form:option>
						<form:options items="${workflowStageList}" itemValue="id" itemLabel="name"></form:options> 
					</form:select>
				</spring:bind></td>
				<td><input type="button" class="smallbtn" value="+" onclick ="javascript:funCreateNewRow('dynWorkFlow');"/> </td>
				<td><input type="button" class="smallbtn" value="-" onclick ="javascript:deleteSpecificRow(this,'dynWorkFlow');"/> </td>	
			</tr>
		</table>
	</div>
	<div class="line">
		<div class="quarter"><spring:message code="workflow.toworkflow"/></div>
		<div class="quarter"><spring:bind path="workflowmap">
			<form:select path="workflowmap.toworkflow" id="toworkflow">
				<form:option value="0">Select</form:option>
				<form:options items="${WorkflowList}" itemValue="id" itemLabel="name"></form:options> 
			</form:select>
		</spring:bind></div> 
	</div>
	<div class="line">
		
		<div class="appbutton submitbutton"><a class="quarterbutton" href="javascript:clearWorkFlowStages();">Clear</a></div>
		<c:if test="${message eq 'createStages'}">
			<div id="wfsubmit" class="appbutton submitbutton"><a class="quarterbutton" href="javascript:storeWorkflow();">Submit</a></div>
		</c:if>
		<c:if test="${message ne 'createStages'}">
			<div id="wfdelete" class="appbutton submitbutton"><a class="quarterbutton" href="javascript:deleteWorkFlow();">Delete</a></div>
		</c:if>
		
		<c:if test="${message ne 'createStages'}">
			<div id="wfupdate" class="appbutton submitbutton"><a class="quarterbutton" href="javascript:updateWorkflow();">Update</a></div>
		</c:if>
		
	</div>
	<spring:bind path="workflowmap">	
		<form:hidden path="workflowmap.jsonValue"/>	
		<c:if test="${not empty workflowmap.jsonValue}">
		<script>		
			setWorkFlowStages(${workflowmap.jsonValue},'${workflowmap.workflow}');</script>
		</c:if>
	</spring:bind>
</fieldset>
<!-- End:WorkflowStages.jsp -->