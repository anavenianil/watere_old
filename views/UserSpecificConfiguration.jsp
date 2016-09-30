<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:UserSpecificConfiguration.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script language="javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<title>User Specific Configuration</title>
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
								<div class="headTitle">User Specific Configuration</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								
									<div>
										<div class="line">
											<div class="quarter bold leftmar">Organisation Roles<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="instanceId" id="instanceId" cssClass="formSelect" onmouseover="setSelectWidth('#instanceId')" onchange = "javascript:getSubordinateList();">
													<form:option value="Select">Select</form:option>
													<form:options items="${sessionScope.UserInstanceList}" itemValue="id" itemLabel="name"/>
               	      							</form:select>
               	   							</div>
										</div>
										
										<div class="line">
											<div class="quarter bold leftmar">Request Type<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="requestId" id="requestId" cssClass="formSelect"  multiple="true">													
													<form:options items="${sessionScope.RequestList}" itemValue="id" itemLabel="name"/>
												</form:select></div>
										</div>
										<div class="line">
											<div class="quarter bold leftmar">Gazetted Type</div>
											<div class="quarter">
												<form:select path="gazettedType" id="gazettedType" cssClass="formSelect" onchange="javascript:selectedDesignation()">
													<form:option value="Select">Select</form:option>
               	      								<form:option value="GAZETTED">Gazetted</form:option>
               	      								<form:option value="NON GAZETTED">Non Gazetted</form:option>
               	   								</form:select></div>
										</div>
										<div class="line">
											<div class="quarter bold leftmar">Designation</div>
											<div class="quarter" id = "designationList">
												<form:select path="designation" id="designation" cssClass="formSelect" multiple="true">
													<form:options items="${sessionScope.designationList}" itemValue="id" itemLabel="name"/>
               	   								</form:select></div>
										</div>
										
										<div class="line">
											<div class="quarter bold leftmar">Assigned Type<span class="mandatory">*</span></div>
											<div class="quarter"><form:select path="delegateType" id="delegateType" cssClass="formSelect" >
													<form:option value="Select">Select</form:option>
               	      								<form:option value="11">Automatic</form:option>
               	      								<form:option value="10">Manual</form:option>
               	      								<form:option value="32">On Leave</form:option>
               	   								</form:select></div>
										</div>
										<div class="line">
											<div class="quarter bold leftmar">Delegated Subordinate<span class="mandatory">*</span></div>
											<div class="quarter" id = "subordinateList"><form:select path="subordinateId" id="subordinateId" cssClass="formSelect" onmouseover="setSelectWidth('#subordinateId')">
													<form:option value="Select">Select</form:option>
													<form:options items="${sessionScope.subordinateList}" itemValue="sfid" itemLabel="name"/>               	      								
               	   								</form:select></div>
										</div>
										
										
										<div class="line">
											<div class="quarter" style="margin-left:16.5%">
												<div><a href="javascript:resetUserConfiguration();" class="appbutton" style="float:right;text-decoration: none">Clear</a></div>
												<div><a href="javascript:saveUserConfiguration();" class="appbutton" style="float:right;text-decoration: none">Submit</a></div>
											</div>
										</div>
										<div class="line height"><hr/></div>
										<div class="line" id="specificList">
											<jsp:include page="UserSpecificList.jsp" />											
										</div>
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
			<form:hidden path="id"/>
		
		</form:form>
		<script>
			resetUserConfiguration();
		</script>
	</body>
</html>
<!-- End:UserSpecificConfiguration.jsp -->