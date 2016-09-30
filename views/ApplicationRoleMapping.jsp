<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ApplicationRoleMapping.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
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
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/script.js"></script>

<title>Application Roles Mapping</title>
</head>

<body>
	<form:form method="post" commandName="approles">
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
								<div class="headTitle">Application Roles Mapping</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<div>
									<%-- Content Page starts --%>
									<div class="line">
										<div class="quarter bold leftmar">Search With</div>
										<div class="quarter">
											<form:select path="searchingWith" id="searchingWith" cssClass="formSelect"  onchange="javascript:selectedSearch();">
												<form:option value="select">Select</form:option>
												<form:option value="name">Name</form:option>
												<form:option value="sfid">EmployeeID</form:option>
												<form:option value="designation">Designation</form:option>
												<form:option value="approle">Application Role</form:option>
											</form:select>
										</div>
										<div class="quarter" id="selectedValueDiv" style="display:none">
											<form:select path="selectedValue" id="selectedValue" cssClass="formSelect" onmouseover="setSelectWidth('#selectedValue')">
												<form:option value="select">Select</form:option>
											</form:select>
										</div>
										<div class="quarter" id="enteredValueDiv" style="display:none">
											<form:input path="enteredValue" id="enteredValue" maxlength="50" onkeypress="javascript:return checkSpecialChar(event);"/>
										</div>
									</div>
									<div class="line">
										<div class="quarter bold leftmar">EmployeeID<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="mapSfid" id="mapSfid" cssClass="formSelect" onmouseover="setSelectWidth('#mapSfid')" onchange="javascript:getApplicationRoles();">
												<form:option value="select">Select</form:option>
												<c:forEach var="empList" items="${sessionScope.applicatinEmployeesList}">
													 <form:option value="${empList.userSfid}">${empList.userSfid} - ${empList.nameInServiceBook}</form:option>
												</c:forEach> 
												
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter bold leftmar">Assign Application Role<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="assigningRole" id="assigningRole" cssStyle="width:240px;"  size="5" multiple="true">
												<form:options items="${sessionScope.applicationRoles}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div style="margin-left:25%;"><a href="javascript:searchingAppRoles();"><div class="appbutton">Search</div></a></div>
										<div><a href="javascript:assigningRole();"><div class="appbutton">Submit</div></a></div>
										<div><a href="javascript:resetAppMap();"><div class="appbutton">Clear</div></a></div>
									</div>
									<div class="line height"><hr/></div>
									<div id="result" class="line">
										<jsp:include page="ApplicationRolesList.jsp" />
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
		<form:hidden path="checkedValues"/>
		<form:hidden path="id"/>
		</form:form>
		<script>
			designationListJSON = <%= (net.sf.json.JSONArray)session.getAttribute("designationListJSON") %>;
			//instanceListJSON = <%= (net.sf.json.JSONArray)session.getAttribute("instanceListJSON") %>;
			applicationRolesListJSON = <%= (net.sf.json.JSONArray)session.getAttribute("applicationRolesListJSON") %>;
			applicationRolesMapJSON = <%= (net.sf.json.JSONArray)session.getAttribute("applicationRolesMapJSON") %>;
		</script>
	</body>
</html>
<!-- End:ApplicationRoleMapping.jsp -->