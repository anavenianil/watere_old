<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:OrgRoleStructure.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/orgstructure.js"></script>

<title>Create Organisation Roles</title>
</head>

<body>
	<form:form method="post" commandName="orgStructure">
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
								<div class="headTitle">Create Organisation Roles</div>
								<!-- Content Starts -->
								<div class="line">	
									<div id="result"></div>									
									<div class="line">
										<div class="quarter leftmar">Hierarchy Level<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="parentLevel" id="parentLevel" cssClass="formSelect" onmouseover="setSelectWidth('#parentLevel')"  onchange="javascript:getReportsToList('roleReportsTo');">
												<form:option value="0">Select</form:option>
												<form:options items="${orgStructure.levelsList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Reports To<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="parentRole" id="parentRole" cssClass="formSelect" onmouseover="setSelectWidth('#parentRole')">
												<form:option value="0">Select</form:option>
												<!--<form:options items="${orgStructure.rolesList}" itemValue="id" itemLabel="roleName"/>-->
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Role Name<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="roleName" id="roleName" maxlength="50" onkeypress="javascript:increaseTextWidth('roleName')"/>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">DIR/PD/AD/TD/PROJ/DIV/GP/SEC<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="departmentId" id="departmentId" cssClass="formSelect" onmouseover="setSelectWidth('#departmentId')">
												<form:option value="0">Select</form:option>
												<form:options items="${orgStructure.departmentsList}" itemValue="id" itemLabel="levelDeptName"/>
											</form:select>
										</div>
									</div>	
									<div class="line">
										<div class="quarter leftmar">Is head of the department<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:radiobutton path="isHead" id="isHead1" label="Yes" value="1" onclick="javascript:checkHead();"/>&nbsp;
											<form:radiobutton path="isHead" id="isHead0" label="No" value="0" onclick="javascript:checkHead();"/>&nbsp;
										</div>
									</div>										
									<div class="line">								
										<div style="margin-left:25%">
											<div class="expbutton"><a href="javascript:submitRole();"> <span>Submit</span></a></div>
											<div class="expbutton"><a href="javascript:clearRole();"> <span>Clear</span></a></div>
										</div>						
									</div>
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="OrgRoleStructureList.jsp"></jsp:include>
									</div>							
								</div>
								<!-- Content Ends -->
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
<!-- End:OrgRoleStructure.jsp -->