<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:tuitionFeeSentToCDADetails.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css"
	rel="stylesheet" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript"
	src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script language="javascript" src="script/tutionFee.js"></script>
<script language="javascript" src="script/telephone.js"></script>
<title>Tuition Fee CDA</title>
</head>

<body>
<form:form method="post" commandName="tutionFee">
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
	<div class="headTitle"> CDA Details</div>
	<div><%-- Content Page starts --%>
	<div class="line">
	<div class="quarter bold">Bill No.<span style="color: red">*</span></div>
	<div class="half"><form:input path="billNo" id="billNo" size="80" />
	</div>
	</div>
	<div class="line">
	<div class="expbutton" style="float: right"><a
		href="javascript:tuitionFeeSendToCDA();" ><span>Submit</span></a></div>
	</div>
	<div class="line" id="result"></div>
	<%-- Content Page end --%></div>
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
	<form:hidden path="param" />
	<form:hidden path="type" />
</form:form>
</body>
</html>
<!-- End:tuitionFeeSentToCDADetails.jsp -->