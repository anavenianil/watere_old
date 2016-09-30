<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:Login.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" isErrorPage="true"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><spring:message code="home.title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/script.js"></script>

<!--[if lte IE 6]>
<link href="styles/ie6.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!--[if lte IE 7]>
<link href="styles/ie6.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!--[if lte IE 8]>
<link href="styles/ie6.css" rel="stylesheet" type="text/css" />
<![endif]-->

</head>

<body>
<form:form method="post" commandName="login">
	<div class="maindiv">
		<div class="header"></div>
		<div class="menuBg">
			<div class="topmenuText" style="margin-left:20px">Personnel Information System</div>
		</div>
		<div class="main_round">
		<div class="LeftRound"></div>
		<div class="RoundBg"><span class="boldText welcomeText">Welcome to Login</span></div>
		<div class="RightRound"></div>
		</div>
		<div class="loginBox">
			<div class="signBg"></div>
			<div class="invaliduser">
			<c:if test="${exception.errorCode ne 'SessionExpExcep'}">${message}
			</c:if>
			<c:if test="${exception.errorCode eq 'SessionExpExcep'}">
			<spring:message code="${exception.errorCode}"/>
			</c:if>
			</div>
			<div class="usernameText">USER ID</div>
			<div class="textBox">
				 <form:input path="username" tabindex="1" size="19"  cssClass="hometextbox" id="username"/>
			</div>
			<div class="usernameText">PASSWORD</div>
			<div class="textBox" onkeypress="funKeyPress(event)">
				 <form:password path="password" tabindex="2" size="19"  cssClass="hometextbox" id="password"/>
			</div>
			<a href="javascript:login();">
				<div class="loginbutton" tabindex="3"></div>
			</a>
			<div class="forgotText"></div>
		</div>
		<div class="footermain">
			<div class="footerLeft"></div>
			<div class="footerBg">Copyright 2016 ©  All rights reserved.<span style="float:right;">
			Call us @ 025-2802206 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Mail us at: <b>suwasarukwa@yahoo.com</b></span></div>
			<div class="footerright"></div>
		</div>
	</div>
	<form:hidden path="param"/>
</form:form>
<script>
document.forms[0].username.value="";
document.forms[0].username.focus();</script>
</body>
</html>
<!-- End:Login.jsp -->