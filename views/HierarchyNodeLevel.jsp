<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin : HierarchyNodeLevel.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script>var type="";</script>
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
								<c:if test="${hierarchy.type eq 'Logical'}">
									<div class="headTitle">Create Organisation Roles</div>
									<title>Create Organisation Roles</title>
							    </c:if>
							    <c:if test="${hierarchy.type eq 'Physical'}">
							    	<div class="headTitle">Create Organisation Structure</div>
							    	<title>Create Organisation Structure</title>
							    </c:if>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								<div>
									<c:if test="${hierarchy.type eq 'Physical'}">
									<div class="line">
										<div class="quarter bold leftmar rightmargin">DIR/PD/AD/TD/PROJ/DIV<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="nodeNames" id="nodeNames" maxlength="50" onkeypress="javascript:increaseTextWidth('nodeNames')"/></div>
									</div>	
									</c:if>								
									<div class="line">
										<div class="quarter bold leftmar rightmargin">Hierarchy Level<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="levelName" id="levelName" onchange="javascript:getParentInstance('${hierarchy.type}')" cssClass="formSelect" onmouseover="setSelectWidth('#levelName')">
												<form:option value="select">Select</form:option>
												<c:if test="${hierarchy.type eq 'Logical'}">
													<form:options items="${roleLevelList}" itemValue="roleID" itemLabel="roleName"/>
												</c:if>
												<c:if test="${hierarchy.type eq 'Physical'}">
													<form:options items="${roleLevelList}" itemValue="id" itemLabel="hierarchyName"/>
												</c:if>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter bold leftmar rightmargin" >Reports To<span class="mandatory">*</span></div>
											<div class="quarter">
											<c:if test="${hierarchy.type eq 'Logical'}">
												<form:select path="nodeParentName" id="nodeParentName"  onchange="javascript:getLogicalName();" cssClass="formSelect" onmouseover="setSelectWidth('#nodeParentName')">
													<form:option value="select">Select</form:option>
												</form:select>
											</c:if>
											<c:if test="${hierarchy.type eq 'Physical'}">
												<form:select path="nodeParentName" id="nodeParentName" cssClass="formSelect" onmouseover="setSelectWidth('#nodeParentName')">
													<form:option value="select">Select</form:option>
												</form:select>
											</c:if>
										</div>										
									</div>
									<c:if test="${hierarchy.type eq 'Logical'}">
									<div class="line">
										<div class="quarter bold leftmar rightmargin">DIR/PD/AD/TD/PROJ/DIV<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="nodeNames" id="nodeNames" maxlength="50"></form:input></div>
									</div>
									</c:if>
									<c:if test="${hierarchy.type eq 'Logical'}">
										<div class="line">
											<div class="quarter bold leftmar rightmargin" >Departments<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="departmentId" id="departmentId" cssClass="formSelect" onmouseover="setSelectWidth('#departmentId')">
													<form:option value="select">Select</form:option>
													<form:options items="${sessionScope.departments}" itemValue="id" itemLabel="deptName"/>
												</form:select>
											</div>
										</div>	
									</c:if>
									<c:if test="${hierarchy.type eq 'Physical'}">
									<div class="line">
										<div class="quarter bold leftmar rightmargin" >Department Type<span class="mandatory">*</span></div>
											<div class="quarter">
											
												<form:select path="departmentType" id="departmentType"  cssClass="formSelect" onmouseover="setSelectWidth('#departmentType')">
													<form:option value="select">Select</form:option>
												   <form:options items="${sessionScope.departmentTypeList}" itemValue="id" itemLabel="name"/>
												</form:select>
											</div>										
									</div>
									</c:if>
									<div class="line">
										<div style="margin-left:26%">
											<a href="javascript:nodeSubmit('${hierarchy.type}');"><div class="appbutton">Submit</div></a>
											<a href="javascript:clearNode('${hierarchy.type}');"><div class="appbutton">Clear</div></a>
										</div>				
										
									</div>
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="HierarchyNodeList.jsp"></jsp:include>
									</div>
									<div id="result"></div>
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
		<script>clearNode('${hierarchy.type}');</script>
		</form:form>
	</body>
</html>
<!-- End : HierarchyNodeLevel.jsp -->