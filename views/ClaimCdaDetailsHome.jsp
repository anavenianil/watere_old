<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ClaimCdaDetailsHome.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<head>
<title><spring:message code="home.title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/script.js"></script>
</head>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%-- Content Page starts --%>
	<div class="line">
	<div class="quarter bold">Bill No.<span style="color: red">*</span></div>
	<div class="half" ><spring:bind path="tutionFee"><form:input path="tutionFee.billNo" id="billNo" size="80" /></spring:bind>
	</div>
	</div>
	<div class="line">
	<div class="expbutton" style="float: right"><a
		href="javascript:tuitionFeeSendToCDA('${tutionFee.billNo}');"><span>Search</span></a></div>
	</div>
	<div class="line" id="result"></div>
	<%-- Content Page end --%>
</body>
</html>
<!-- End:ClaimCdaDetailsHome.jsp -->