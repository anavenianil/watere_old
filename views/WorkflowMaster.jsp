<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:WorkflowMaster.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/json2_mini.js"></script>

<title>Create Workflow</title>
</head>

<body>
	<form:form method="post" commandName="workflowmap">
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
								<div class="headTitle">Create Workflow</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle"/>&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								<div class="line">									
									<div id="workflow">
										<div class="line">
											<div class="quarter bold leftmar">Workflow Name<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="workflowName"  id="workflowName" cssClass="formSelect" onchange="javascript:createWorkFlowStages();" onmouseover="setSelectWidth('#workflowName')">
						 							<form:option value="select">Select</form:option>
						 							<form:option value="new">New</form:option>
							 						<form:options items="${WorkflowList}" itemValue="id" itemLabel="name"></form:options>
					   							</form:select>
			   								</div>
			   								<div class="quarter displayvalues" id="flowname">
												<form:input path="flowname" id="workflowname"/>
			   								</div>			   								
									  </div>
									  </div>
									  <div id="stages" class="line"></div>								  							
							  </div>						
								
								<%-- Content Page end --%>
								
								<div>&nbsp;</div>
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
		</form:form>
	</body>
</html>