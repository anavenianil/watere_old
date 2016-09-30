<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:EmpMapping.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/json2_mini.js"></script>
<title>Default Role</title>
</head>

<body onload="javascript:clearEmpMapping()">
	<form:form method="post" commandName="hierarchy">
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
								<div class="headTitle">Default Role</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</img></div>
								<%-- Content Page starts --%>
									<div class="line">
										<div class="line">
											<div class="quarter bold leftmar">Search With</div>
											<div class="quarter">
												<form:select path="empname" id="empname" cssClass="formSelect" onmouseover="setSelectWidth('#empname')"onchange="javascript:displayInstances();">	
														<form:option value="select">Select</form:option>
														<form:option value="SFID">SFID</form:option>
														<form:option value="NAME">NAME</form:option>
														<form:option value="DIVISION"><spring:message code="instances"></spring:message></form:option>
												</form:select>
											</div>
											<div class="quarter displayvalues" id="searchWithdiv">
												    <form:select path="searchWith"   id="searchWith" cssClass="formSelect" onmouseover="setSelectWidth('#searchWith')">	
														<form:option value="select">Select</form:option>
														<form:option value="contains">Contains</form:option>
														<form:option value="starts">Starts With</form:option>
													</form:select>
											</div>
											<div class="quarter displayvalues" id="searchValues">
												<form:input path="searchvalue" id="searchvalue" cssStyle="width:145px"></form:input>
											</div>
											<div class="quarter displayvalues" id="divisions">
												<form:select path="divisionName" id="division" cssClass="formSelect" onmouseover="setSelectWidth('#division')">	
														<form:option value="select">Select</form:option>
														<form:options items="${roleInstanceData}" itemValue="id" itemLabel="name"/> 
												</form:select>										
											</div>	
										</div>
										<div class="line">
											<div class="quarter bold leftmar"><spring:message code="instances"></spring:message><span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="roleInstanceName" id="roleInstanceName" cssClass="formSelect" onmouseover="setSelectWidth('#roleInstanceName')">	
													<form:option value="select">Select</form:option>	
													<form:options items="${roleInstanceData}" itemValue="id" itemLabel="name"/>									
												</form:select>
											</div>
										</div>
										<div class="line">
	
											<div style="margin-left:13.5%">
											        <div style="margin-left:13.5%"><a href="javascript:getSearchList('employee');"><div class="appbutton">Search</div></a></div>
													<div><a href="javascript:employeeMappingSubmit('map');"><div class="appbutton">Submit</div></a></div>
													<div><a href="javascript:employeeMappingSubmit('delete');"><div class="appbutton">Delete</div></a></div>
													<div><a href="javascript:clearEmpMapping();"><div class="appbutton">Clear</div></a></div>										
											</div>										
										</div>
							             <div class="line height"><hr/></div>
										<div id="empresult" class="line">
											<jsp:include page="EmployeeList.jsp"/>
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
		<form:hidden path="checkedValues"/>		
		<form:hidden path="id" id="id1"/>		
		</form:form>
	</body>
</html>
<!-- End:EmpMapping.jsp -->