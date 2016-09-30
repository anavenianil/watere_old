
<!-- begin:Departments.jsp -->
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

<title>Create Organization Structure</title>
</head>

<body onload="javascript:clearDepartment();">
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
								<div class="headTitle">Create Organization Structure</div>
								<!-- Content Starts -->
								<div class="line">	
									<div id="result"></div>							
									<div class="line">
										<div class="quarter leftmar">DIR/PD/AD/TD/PROJ/DIV<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="departmentName" id="departmentName" maxlength="50" onkeypress="javascript:increaseTextWidth('departmentName')"/>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Full Form<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="description" id="description" maxlength="50" onkeypress="javascript:increaseTextWidth('description');"/>
										</div>
									</div>								
									<div class="line">
										<div class="quarter leftmar">Hierarchy Level<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="parentLevel" id="parentLevel" cssClass="formSelect" onmouseover="setSelectWidth('#parentLevel')" onchange="javascript:getReportsToList('deptReportsTo');">
												<form:option value="0">Select</form:option>
												<form:options items="${orgStructure.levelsList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Reports To<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="parentDept" id="parentDept" cssClass="formSelect" onmouseover="setSelectWidth('#parentDept')">
												<form:option value="0">Select</form:option>
												<!--<form:options items="${orgStructure.departmentsList}" itemValue="id" itemLabel="deptName"/>-->
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Department<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="departmentTypeId" id="departmentTypeId" cssClass="formSelect" onmouseover="setSelectWidth('#departmentTypeName')">
												<form:option value="select">Select</form:option>
												<form:options items="${orgStructure.departmentsTypeList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Phone No<span class="mandatory">*</span></div>
										<div class="quarter">
											<!--<form:input path="phoneNumber" id="phoneNumber" maxlength="50" onkeypress="javascript:increaseTextWidth('phoneNumber');return checkInt('phoneNumber');"/>-->
											<form:input path="phoneNumber" id="phoneNumber" maxlength="15" onkeypress="javascript:return checkInt(event);"/>
										</div>
									</div>	
									<div class="line">
										<div class="quarter leftmar">Email<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="email" id="email" maxlength="50" onkeypress="javascript:increaseTextWidth('email');" onblur="javascript:return validateEmail($jq('#email').val());"/>
										</div>
									</div>	
									<div class="line">
										<div class="quarter leftmar">Fax<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="fax" id="fax" maxlength="50" onkeypress="javascript:increaseTextWidth('fax'); return isAlphaNumaricExp(event);"/>
										</div>
									</div>	
									<div class="line">								
										<div style="margin-left:25%">
											<div class="expbutton"><a href="javascript:submitDepartment();"> <span>Submit</span></a></div>
											<div class="expbutton"><a href="javascript:clearDepartment();"> <span>Clear</span></a></div>
										</div>						
									</div>
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable"><jsp:include page="DepartmentsList.jsp" /></div>
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
<!-- End:Departments.jsp -->