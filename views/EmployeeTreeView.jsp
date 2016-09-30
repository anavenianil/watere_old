<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:EmployeeTreeView.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Employee Tree Details</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/script.js"></script>
</head>

<body>
	<form:form method="post" commandName="employee">
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="id" id="id"/>
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
								<div class="headTitle">Employee Tree Details</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								<div>
									<div class="line">
										<div class="quarter bold leftmar">Search With<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="searchingWith" id="searchingWith" cssClass="formSelect" onmouseover="setSelectWidth('#searchingWith')" onchange="javascript:selectedSearch();">
												<form:option value="select">Select</form:option>
												<form:option value="name" disabled="true">Name</form:option>
												<form:option value="sfid" disabled="true">SFID</form:option>
												<form:option value="designation" disabled="true">Designation</form:option>
												<form:option value="instance"><spring:message code="instances"></spring:message></form:option>
											</form:select>
										</div>
										<div class="quarter" id="selectedValueDiv" style="display:none">
											<form:select path="selectedValue" id="selectedValue" cssClass="formSelect" onmouseover="setSelectWidth('#selectedValue')">
												<form:option value="select">Select</form:option>
												<form:option value="1">All</form:option>
											</form:select>
										</div>
										<div class="quarter" id="enteredValueDiv" style="display:none">
											<form:input path="enteredValue" id="enteredValue" maxlength="50"/>
										</div>
									</div>
									<div class="line">
										<div style="margin-left:25%;"><a href="javascript:searchingTree('tree');"><div class="appbutton">Search</div></a></div>
										<div><a href="javascript:resetAppMap();"><div class="appbutton">Clear</div></a></div>
									</div>
									<div class="line height"><hr/></div>
									
									<div class="line">
								    	<div id="empSearchList">
								    		
										</div>
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
		</form:form>
		<script>
			designationListJSON = <%= (net.sf.json.JSONArray)session.getAttribute("designationListJSON") %>;
			instanceListJSON = <%= (net.sf.json.JSONArray)session.getAttribute("instanceListJSON") %>;
		</script>
	</body>
</html>
<!-- End:EmpTreeDetails.jsp -->