<!-- begin:CreateWorkflowStages.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.dto.OrgInstanceDTO,com.callippus.web.beans.dto.WorkFlowRelationDTO,com.callippus.web.beans.dto.WorkflowDependentDTO,com.callippus.web.beans.dto.WorkflowDynamicTablesDTO,com.callippus.web.beans.workflowmapping.WorkFlowMappingBean"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<fieldset><legend><strong><font color='green'>Workflow Stages</font></strong></legend>
	<div class="line">
		<table width="100%" id="dynWorkFlow" cellpadding="0" cellspacing="0" align="center">
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
			<tr id="row0">
				<td>
					<spring:bind path="workflowmap">
						<form:select path="workflowmap.from" disabled="true" cssClass="formClass" id="workflowfrom0">
							<form:option value="0">Select</form:option>
							<form:options items="${sessionScope.RelationList}" itemValue="id" itemLabel="name"></form:options> 
						</form:select>
					</spring:bind>
				</td>
				<td>
					<spring:bind path="workflowmap">
						<form:select path="workflowmap.relation" id="workflowrelation0" onchange="javascript:getAssignedList('0','workflowrelation','workflowto')" cssClass="formClass" >
							<form:option value="0">Select</form:option>
							<form:options items="${sessionScope.RelationshipList}" itemValue="id" itemLabel="name"></form:options> 
						</form:select>
					</spring:bind>
				</td>
				<td>
					<spring:bind path="workflowmap">
						<form:select path="workflowmap.to" onchange="javascript:setWorkflowFrom();" cssClass="formClass to" id="workflowto0" >
							<form:option value="0">Select</form:option>
						</form:select>
					</spring:bind>
				</td>
				<td>
					<spring:bind path="workflowmap">
						<form:select path="workflowmap.esclateRelation"  onchange="javascript:getAssignedList('0','escalaterelation','escalteTo')" cssClass="formClass"  id="escalaterelation0">
							<form:option value="0">Select</form:option>
							<form:options items="${sessionScope.RelationshipList}" itemValue="id" itemLabel="name"></form:options> 
						</form:select>
					</spring:bind>
				</td>			
				<td>
					<spring:bind path="workflowmap">
						<form:select path="workflowmap.escalteTo" cssClass="formClass" id="escalteTo0">
							<form:option value="0">Select</form:option>
						</form:select>
					</spring:bind>
				</td>
				<td id="stages0">
					<spring:bind path="workflowmap">
						<form:select path="workflowmap.workflowStage" cssClass="formClass" id="workflowStage0">
							<form:option value="0">Select</form:option>
							<form:options items="${sessionScope.workflowStageList}" itemValue="id" itemLabel="name"></form:options> 
						</form:select>
					</spring:bind>
				</td>
				<td><input type="button" id="add" class="smallbtn" value="+" onclick="javascript:insertNewRow('0','dynWorkFlow');setEditWorkflowFrom();"/> </td>
				<td><input type="button" id="delete" class="smallbtn" value="-" onclick="javascript:deleteRow('0','dynWorkFlow');"/> </td>	
			</tr>
		</table>
	</div>
	<div class="line">
		<div class="quarter leftmar"><spring:message code="workflow.toworkflow"/></div>
		<div class="quarter">
			<spring:bind path="workflowmap">
				<form:select path="workflowmap.toworkflow" id="toworkflow">
					<form:option value="0">Select</form:option>
					<form:options items="${sessionScope.WorkflowList}" itemValue="id" itemLabel="name"></form:options> 
				</form:select>
			</spring:bind>
		</div> 
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
		loadScript('dynWorkFlow');
		rolesListJSON =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<OrgInstanceDTO>)session.getAttribute("RoleInstanceList"))%>;
		relationsListJSON =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<WorkFlowRelationDTO>)session.getAttribute("RelationList"))%>;
		dependentListJSON =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<WorkflowDependentDTO>)session.getAttribute("WorkflowDependentList"))%>;
		relationshipListJSON =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<WorkflowDependentDTO>)session.getAttribute("RelationshipList"))%>;
		workflowStageListJSON =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<WorkflowDependentDTO>)session.getAttribute("workflowStageList"))%>;
		dynamicTableListJSON =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<WorkflowDynamicTablesDTO>)session.getAttribute("DynamicTablesList"))%>;
		<% if (session.getAttribute("WorkflowDetails")!=null ) { %>
			workflowStageDetailsJSON =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<WorkFlowMappingBean>)session.getAttribute("WorkflowDetails"))%>;
		<%}%>
	</script>
</fieldset>
<!-- End:CreateWorkflowStages.jsp -->