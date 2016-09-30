<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="/tags/spring-form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
<form:form method="post" commandName="incomeTaxMaster" id="incomeTaxMaster">
<div class="line">
<div class="quarter leftmar">Designation Name<span class="failure">*</span></div>
<div class="quarter"><form:select path="designationId"
	id="designationId" cssClass="formSelect">
	<form:option value="select">Select</form:option>
	<form:options items="${sessionScope.desigList}" itemValue="id"
		itemLabel="designationDetails.name" />
</form:select></div>
</div>
</form:form>
</body>
</html>