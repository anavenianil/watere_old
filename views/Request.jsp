<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : PassportList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	<%@ taglib uri="/tags/spring-form" prefix="form"%>
	<html>
	<head></head>
	<body>
		<form:form>
			<div>Field Name<form:input path="fieldname"/></div>
			<div>From<form:input path="from"/></div>
			<div>To<form:input path="to"/></div>
			<div><input type="button" value="Submit"/></div>
	</form:form>
	</body>
	</html>