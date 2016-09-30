<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:WorkFlowMapping.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/prototype.js"></script>
<script language="javascript" src="script/aa.js"></script>
<script language="javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<title> Generic WorkFlow Mapping</title>
</head>

<body>
	<form:form method="post" commandName="workflowmap" id="workflowmap">
		<div>
			<div class="Innermaindiv">
				<div class="header"></div>
				<div><jsp:include page="MenuLinks.jsp" /></div>					
				<div class="mainDisplay">
					<div class="whitebox1">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								<div class="whitebox_tr"></div>
							</div>
						</div>
						<div class="whitebox_m1">
							<div class="lightblueBg1">
								<div class="headTitle">Workflow Mapping</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								<div>
								    <div class="line">
									   <div class="quarter bold leftmar">Workflow Type<span class="mandatory">*</span></div>
								 		<div class="quarter">
											<form:select path="workflowType" id="workflowType" cssClass="formSelect" onchange="javascript:checkWorkflowType()">
							                    <form:option value="0">Select</form:option>
							                     <form:option value="1">Generic</form:option>
							                     <form:option value="2">Role Based</form:option>
							                     <form:option value="3">Designation Based</form:option>
							                     <form:option value="4">Organization Specific</form:option>
							                </form:select>	   
									    </div>    			    
									</div>
									<div class="line" id="rolesDiv" style="display:none">
									   <div class="quarter bold leftmar">Role<span class="mandatory">*</span></div>
								 		<div class="quarter">
											<form:select path="roleInstanceID" id="roleInstanceID" cssClass="formSelect" >
							                    <form:option value="0">Select</form:option>
							                    <form:options items="${sessionScope.InstanceList}" itemValue="id" itemLabel="name"/>
							                </form:select>	   
									    </div>    			    
									</div>
									<div class="line" id="desigsDiv" style="display:none">
									   <div class="quarter bold leftmar">Designation<span class="mandatory">*</span></div>
								 		<div class="quarter">
											<form:select path="designationID" id="designationID" cssClass="formSelect" >
							                    <form:option value="select">Select</form:option>
							                    <form:options items="${sessionScope.designationList}" itemValue="id" itemLabel="name"/>
							                </form:select>	   
									    </div>    			    
									</div>
									<div class="line" id="orgDiv" style="display:none">
									   <div class="quarter bold leftmar">Organization<span class="mandatory">*</span></div>
								 		<div class="quarter">
											<form:select path="organizationID" id="organizationID" cssClass="formSelect" >
							                    <form:option value="select">Select</form:option>
							                    <form:options items="${workflowmap.organizationsList}" itemValue="id" itemLabel="name"/>
							                </form:select>	   
									    </div>    			    
									</div>
								    <div class="line">
									   <div class="quarter bold leftmar">Workflow Name<span class="mandatory">*</span></div>
								 		<div class="quarter">
											<form:select path="workflowId" id="workflowId" cssClass="formSelect"  onmouseover="setSelectWidth('#workflowId')">
							                    <form:option value="0">Select</form:option>
							                    <form:options items="${sessionScope.WorkflowList}" itemValue="id" itemLabel="name"/>
							                </form:select>	   
									    </div>    			    
									</div>
									<div class="line">
									   <div class="quarter bold leftmar">Request Type<span class="mandatory">*</span></div>
								 		<div class="quarter">
											<form:select path="requestId" id="requestId" cssClass="formSelect"  onmouseover="setSelectWidth('#requestId')">
							                    <form:option value="0">Select</form:option>
							                    <form:options items="${sessionScope.RequestList}" itemValue="id" itemLabel="name"/>
							                </form:select>	   
									    </div>
									</div>
								<div class="line" style="margin-left:25%"> 
									    <div><a href="javascript:saveReqWorkFlowMapping();"><div class="appbutton">Submit</div></a></div>
										<div><a href="javascript:clearReqWorkFlowMapping();"><div class="appbutton">Clear</div></a></div>
					                </div>	
									<div class="line height"><hr/></div>
									 <div id="mappingList" class="line">
										<jsp:include page="WorkFlowMappingList.jsp" />
									</div>
					               <div>&nbsp;</div>
				               </div>
				              
							 </div>
						</div>
						<div class="whitebox_b_non">
							<div class="whitebox_bl">
								<div class="whitebox_br"></div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>
		 <form:hidden path="param"/>
		  <form:hidden path="type"/>
		 <form:hidden path="id"/>
		 <!-- <script>
		 enableworkflowValues('${param}');
		</script> -->
		</form:form>
		
	</body>
</html>
<!-- End:WorkFlowMapping.jsp -->