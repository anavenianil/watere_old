<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:EmpInternalRoleMapping.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<title>Internal Divisions Creation</title>

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
								<div class="headTitle">Internal Divisions Creation</div>
								<%-- Content Page starts --%>
								<div class="line">

									<div class="line">
										<div class="quarter bold leftmar">Departments</div>
										<div class="quarter">
											<form:select path="department" id="department" cssClass="formSelect" onmouseover="setSelectWidth('#department')" onchange="javascript:setOrganizationalDetails()">
												<form:option value="select">Select</form:option>	
												<form:options items="${hierarchy.orgnRolesList}" itemValue="key" itemLabel="name"/>	
											</form:select>
											
										</div>
									</div>
									<div class="line">
										<div class="quarter bold leftmar">SFID<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="mapSFID" id="mapSFID" cssClass="formSelect" onmouseover="setSelectWidth('#mapSFID')">
												<form:option value="select">Select</form:option>	
											</form:select>											
										</div>
									</div>
									<div class="line">
										<div class="quarter bold leftmar">Internal Divisions</div>
										<div class="quarter">
											<form:select path="internalDivisions" id="internalDivisions" onchange="javascript:checkSelectedDivision()" cssClass="formSelect" onmouseover="setSelectWidth('#internalDivisions')">
												<form:option value="select">Select</form:option>	
												<form:option value="New">New</form:option>
											</form:select>
										</div>
										<div class="quarter" id="newInternalDivTag" style="display:none">
											<form:input path="newInternalDiv" id="newInternalDiv"/>
										</div>
									</div>
									<div class="line">
										<div class="quarter bold leftmar">Internal Role Name<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="internalRoleName" id="internalRoleName" onchange="javascript:checkSelectedRole()" cssClass="formSelect" onmouseover="setSelectWidth('#internalRoleName')">
												<form:option value="select">Select</form:option>	
												<form:option value="New">New</form:option>	
												<form:option value="Head">Head</form:option>	
												<form:option value="Employee">Employee</form:option>	
											</form:select>
										</div>
										<div class="quarter" id="newInternalRoleTag" style="display:none">
											<form:input path="newInternalRole" id="newInternalRole"/>
										</div>
									</div>
									<%--<div class="line">
										<div class="quarter">Immediate Superior</div>
										<div class="quarter">

											<form:select path="parentSFID" id="parentSFID" cssStyle="width:100%">
												<form:option value="select">Select</form:option>	
												<form:options items="${hierarchy.subOrdinatesList}" itemValue="sfid" itemLabel="name"/>	
											</form:select>
										</div>
									</div> --%>
									<div class="line">
										<div style="margin-left:25%">
											<a href="javascript:internalRoleMappingSubmit();"><div class="appbutton">Submit</div></a>
										</div>
											<a href="javascript:clearEmpRoleMapping();"><div class="appbutton">Clear</div></a>
										
									</div>
								</div>
								<div class="line height"><hr/></div>
									<div id="result" class="line">
										<jsp:include page="InternalStructureTree.jsp" />
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
		<form:hidden path="intDivision"/>
		<form:hidden path="intRole"/>
		<form:hidden path="manageID"/>
		<script>
			subOrdinatesList = <%= (net.sf.json.JSONArray)session.getAttribute("subOrdinatesList") %>;
			internalDivisionsList = <%= (net.sf.json.JSONArray)session.getAttribute("internalDivisionsList") %>;
			internalRolesList = <%= (net.sf.json.JSONArray)session.getAttribute("internalRolesList") %>;
		</script>
		</form:form>
	</body>
</html>
<!-- End:EmpInternalRoleMapping.jsp -->