<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- EmployeeRoles.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><spring:message code="asl.roles" /></title>
</head>
<body>
<form:form commandName="roles">
	<div class="Innermaindiv1">
		<div class="header"></div>
		<div class="menuBg">
			<div class="topmenuText"></div>
		</div>
		<div class="mainDisplay">
			<div class="whitebox1">
				<div class="whitebox_t">
					<div class="whitebox_tl">
						<div class="whitebox_tr"></div>
				    </div>
				</div>
				<div class="whitebox_m1">
					<div class="lightblueBg2">
						<div><input type="radio" value="" onclick=""/></div>
					</div>
				</div>
			</div>
		</div>
		<div class="footermain">
			<div class="footerLeft"></div>
			<div class="footerBg">Copyright 1999-2009 © ASL All rights reserved.</div>
			<div class="footerright"></div>
		</div>
	</div>
	<div>
		<form:hidden path="param"/>
		<form:hidden path="type"/>
	</div>
</form:form>
</body>
</html>
<!-- end of EmployeeRoles.jsp -->