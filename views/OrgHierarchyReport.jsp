<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:OrgHierarchReport.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link rel="stylesheet" type="text/css" href="styles/jquery.treeTable.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.treeTable.js"></script>
<title>OrgHierarchy Reports</title>
</head>

<body id="body">
	<form:form method="post" commandName="reports">
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
								<div class="headTitle">OrganizationHierarchy Reports</div>
								<div>
									<%-- Content Page starts --%>
								<div id="line" style="float:left;width: 100%;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewOrgReports('OrganizationReport','treeModel','orgGrid','')"><b>Organization Report</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="orgGrid" style="display:none;">
								<div class="quarter bold" style="color: blue;">Report Type<span class="mandatory">*</span></div>
										<div class="quarter" style="width:25%">
											<form:select path="organization" id="organization" cssClass="formSelect">
												<form:option value="select">Select</form:option>
												<form:option value="DepartmentHierarchy">Physical Department Hierarchy</form:option>
											    <form:option value="OrgStructure">Physical Organization Structure</form:option>
											    <form:option value="RolesHierarchy">Logical Roles Hierarchy</form:option>
											    <form:option value="RolesStructure">Logical Roles Structure</form:option>
											    <form:option value="EmpRolesMapping">Employee Roles Mapping</form:option>
											    <form:option value="EmployeesMapping">Employees Mapping</form:option>
											</form:select>
										</div>
										<div class="quarter">
											<div class="quarter">
											<a
												href="javascript:getOrganizationReport();"
												style="color: purple"> PDF </a>
										</div>
										
										</div>									
								</div>
										<div id="line" style="float:left;width: 100%;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel2"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewOrgReports('empMapping','treeModel2','mappingGrid')"><b>Employee Roles Mapping Report</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="mappingGrid" style="display:none;">
								<div class="quarter bold" style="color: blue;">Report Type<span class="mandatory">*</span></div>
										<div class="quarter" style="width:25%">
											<form:select path="hierarchy" id="hierarchyMaping" cssClass="formSelect" onmouseover="setSelectWidth('#hierarchy')">
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.HierarchyReports}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
										<div class="quarter">
											<div class="quarter">
											<a
												href="javascript:getEmpMapping('pdf');"
												style="color: purple"> PDF </a>
										</div>
										<div class="quarter">
											<a
												href="javascript:getEmpMapping('excel');"
												style="color: purple"> Excel </a>
										</div>
										</div>									
								</div>
										<div id="line" style="float:left;width: 100%;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel9"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewOrgReports('EmpHierarchyHome','treeModel9','hierarchyGrid')"><b>Employee Logical Hierarchy Report</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="hierarchyGrid" style="display:none;">
								<div class="quarter bold" style="color: blue;">Role Instance Name<span class="mandatory">*</span></div>
										<div class="quarter" style="width:25%">
											<form:select path="hierarchy" id="hierarchy" cssClass="formSelect" onmouseover="setSelectWidth('#hierarchy')">
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.HierarchyReports}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
										<div class="quarter">
											<div class="quarter">
											<a
												href="javascript:getHierarchyReport('hierarchy','','pdf');"
												style="color: purple"> PDF </a>
										</div>
										<div class="quarter">
											<a
												href="javascript:getHierarchyReport('','excel');"
												style="color: purple"> EXCEL</a>
										</div>
										</div>									
								</div>
									<div id="line" style="float:left;width: 100%;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel10"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewOrgReports('EmpHierarchyHome','treeModel10','phyhierarchyGrid','Physical')"><b>Employee Physical Hierarchy Report</b></span>
									</td></tr>
									</table></div>
									 <div class="line" id="phyhierarchyGrid"  style="display:none;">
								<div class="quarter bold" style="color: blue;">
Department Name<span class="mandatory">*</span></div>
										<div class="quarter" style="width:25%">
											<form:select path="hindi" id="phyhierarchy" cssClass="formSelect" onmouseover="setSelectWidth('#phyhierarchy')">
												<form:option value="select">Select</form:option>
					 <form:options items="${sessionScope.PhyHierarchyReports}" itemValue="id" itemLabel="deptName"/>
											</form:select>
										</div>
											<div class="quarter">
											<div class="quarter">
											<a
												href="javascript:getHierarchyReport('phyhierarchy','Physical','pdf');"
												style="color: purple"> PDF </a>
										</div>
										<div class="quarter">
											<a
												href="javascript:getHierarchyReport('Physical','excel');"
												style="color: purple"> EXCEL</a>
										</div>
										</div>									
								</div>  
							
									<%-- Content Page end --%>
								</div>
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
<!-- End:OrgHierarchReport.jsp -->