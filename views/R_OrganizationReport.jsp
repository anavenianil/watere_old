<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:R_OrganizationReport.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<title>Organisation Report</title>
</head>

<body>
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
								<div class="headTitle">Organisation Report</div>
								<div>
									<%-- Content Page starts --%>
										<div class="line">
										<div class="quarter bold leftmar">Report Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="organization" id="organization" cssClass="formSelect">
												<form:option value="select">Select</form:option>
												<form:option value="DepartmentHierarchy">Physical Department Hierarchy</form:option>
											    <form:option value="OrgStructure">Physical Organisation Structure</form:option>
											    <form:option value="RolesHierarchy">Logical Roles Hierarchy</form:option>
											    <form:option value="RolesStructure">Logical Roles Structure</form:option>
											    <form:option value="EmpRolesMapping">Employee Roles Mapping</form:option>
											    <form:option value="EmployeesMapping">Employees Mapping</form:option>
											</form:select>
										</div>
									</div>	
								
									<div class="line">
											<div style="margin-left:24%;">
												<div class="expbutton"><a href="javascript:getOrganizationReport();"><span>Submit</span></a></div>		
											</div>
											<div class="expbutton"><a href="javascript:clearOrganizationReport();"><span>Clear</span></a></div>
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
<!-- End:R_OrganizationReport.jsp-->