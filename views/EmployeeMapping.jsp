<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:EmployeeMapping.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
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
<title>Employee Role Mapping</title>

</head>

<body>
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
								<div class="headTitle">Employee Role Mapping</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line" id="result"></div>
									<div class="line">
										<div class="quarter">SFID</div>
										<div class="quarter"><form:input path="mapSFID" id="mapSFID"></form:input></div>
									</div>
									<div class="line">
										<div class="quarter">Organisation Role Names</div>
										<div class="quarter">
											<form:select path="roleInstanceName" id="roleInstanceName" onchange="javascript:getSubInstanceLevels()" cssClass="formSelect" onmouseover="setSelectWidth('#roleInstanceName')">	
												<form:option value="select">Select</form:option>	
												<form:options items="${roleInstanceList}" itemValue="id" itemLabel="name"/>									
											</form:select>
										</div>
									</div>
									
									<div class="line">
										<div class="quarter">Organisation Sub Role Names</div>
										<div class="quarter">
											<form:select path="roleInstanceSubName" id="roleInstanceSubName" cssClass="formSelect" onmouseover="setSelectWidth('#roleInstanceSubName')" >	
												<form:option value="select">Select</form:option>	
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter">Internal Divisions</div>
										<div class="quarter">
											<form:select path="internalDivisions" id="internalDivisions" onchange="javascript:checkSelectedDivision()" cssClass="formSelect" onmouseover="setSelectWidth('#internalDivisions')">
												<form:option value="select">Select</form:option>	
												<form:option value="new">New</form:option>
												<form:options items="${internalDivisionsList}" itemValue="id" itemLabel="name"/>			
											</form:select>
										</div>
										<div class="quarter" id="newInternalDivTag" style="display:none">
											<form:input path="newInternalDiv" id="newInternalDiv"/>
										</div>
									</div>
									<div class="line">
										<div class="quarter">Internal Role Name</div>
										<div class="quarter">
											<form:select path="internalRoleName" id="internalRoleName" onchange="javascript:checkSelectedRole()" cssClass="formSelect" onmouseover="setSelectWidth('#internalRoleName')">
												<form:option value="select">Select</form:option>	
												<form:option value="new">New</form:option>	
												<form:option value="head">Head</form:option>	
												<form:option value="employee">Employee</form:option>	
												<form:options items="${internalRolesList}" itemValue="id" itemLabel="name"/>			
											</form:select>
										</div>
										<div class="quarter" id="newInternalRoleTag" style="display:none">
											<form:input path="newInternalRole" id="newInternalRole"/>
										</div>
									</div>
									<div class="line">
										<div class="quarter">Immediate Superior</div>
										<div class="quarter">
											<%--<form:input path="parentSFID" id="parentSFID"/>--%>
											<form:select path="parentSFID" id="parentSFID" cssClass="formSelect" onmouseover="setSelectWidth('#parentSFID')">
												<form:option value="select">Select</form:option>	
												<form:options items="${subOrdinatesList}" itemValue="sfid" itemLabel="name"/>	
											</form:select>
										</div>
									</div>
									<div class="line">
										<div style="margin-left:25%">
											<a href="javascript:empRoleMappingSubmit();"><div class="appbutton">Submit</div></a>
											<a href="javascript:clearEmpRoleMapping();"><div class="appbutton">Clear</div></a>
										</div>
									</div>
								</div>
								<div id="result"></div>
							
								
								
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
		<form:hidden path="intDivision"/>
		<form:hidden path="intRole"/>
		<form:hidden path="instanceRoleID"/>
		</form:form>
	</body>
</html>
<!-- End:EmployeeMapping.jsp -->