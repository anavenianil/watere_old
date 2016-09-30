<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:RequestWorkFlowMapping.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
</head>

<body>
	<form:form method="post" commandName="workflowmap">
	<script>document.title="Request WorkFlow Mapping"</script>
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
								<div style="padding: 30px 0 0 0;"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								<fieldset><legend><div class="headTitle">Role Based WorkFlow Mapping</div></legend>
								
								<div class="line">
											<div class="quarter">TD/Division/Project</div>
											<div class="quarter">
												<form:select path="instanceId" id="instanceId" cssClass="formSelect" onmouseover="setSelectWidth('#instanceId')">
													<form:option value="0">Select</form:option>
               	      								<form:options items="${InstanceList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											</div>
								</div>
								<div class="line">
											<div class="quarter">Request Type</div>
											<div class="quarter">
												<form:select path="requestId" id="requestId" cssClass="formSelect" onmouseover="setSelectWidth('#requestId')" >
													<form:option value="0">Select</form:option>
               	      								<form:options items="${RequestList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											</div>
								</div>
								<div class="line">
											<div class="quarter">WorkFlow Name</div>
											<div class="quarter">
												<form:select path="workflowId" id="workflowId" cssClass="formSelect" onmouseover="setSelectWidth('#workflowId')" >
													<form:option value="0">Select</form:option>
               	      								<form:options items="${WorkflowList}" itemValue="id" itemLabel="name"/>
               	   								</form:select>
											</div>
								</div>
								<div class="line">
											<div><a class="appbutton" style="float:right;text-decoration: none" href="javascript:clearworkflow();">Clear</a></div>
											<div><a class="appbutton" style="float:right;text-decoration: none" href="javascript:assignValues();">Submit</a></div>
											
								</div>
								
								<div class="height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="RequestWorkflowList.jsp"></jsp:include>
								</div>
											
											
									
									
								</fieldset>
								
							
								
								
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
		<form:hidden path="id"/>
		</form:form>
	</body>
	
</html>
<!-- End:RequestWorkFlowMapping.jsp -->