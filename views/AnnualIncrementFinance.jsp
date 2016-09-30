<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:AnnualIncrementHome.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/increment.js"></script>
<title>Annual Increment</title>
</head>
<body>
<form:form method="post" id="increment" commandName="increment">
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
	<div class="headTitle">Grant of Annual Increment</div>
	<%-- Content Page starts --%>	
	<div class="line">
			<div class="quarter bold">Do Part Number<span style="color:red">*</span></div>
				<div class="quarter">
			        <form:select path="doPartNumberDetails" id="doPartNumberDetails" onchange="javascript:getEmployeeIntegratedPayIncrement();">
					<form:option value="select">Select</form:option>
					<c:forEach var="basicPayIdList" items="${increment.basicPayIdList}">
					<option value="${basicPayIdList.id}#<fmt:formatDate pattern="dd-MMM-yyyy" value="${basicPayIdList.doPartDate1}"/> -- ${basicPayIdList.type}">${basicPayIdList.gazettedType}-${basicPayIdList.doPartNo} - Dated <fmt:formatDate pattern="dd-MMM-yyyy" value="${basicPayIdList.doPartDate1}"/></option>
					</c:forEach>
					</form:select>
		      </div>
    </div>
	<div class="line">
		<div class="quarter leftmar">Accepted Date<span style="color:red">*</span></div>
		<div class="quarter">
			<form:input path="financeAcceptedDate" id="financeAcceptedDate" readonly="true" onchange="javascript:checkRunmonthDate('${increment.payBillRunMonth}');"/>
				<img  src="./images/calendar.gif" id="financeAcceptedDateImg" />	
					<script type="text/javascript">
						Calendar.setup({inputField :"financeAcceptedDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"financeAcceptedDateImg",singleClick : true,step : 1});
					</script>	
		 </div>
	 </div>
	<div id="EmpDetails">
		<jsp:include page="AnnualIncrementList.jsp"></jsp:include>
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
	<form:hidden path="param" />
	<form:hidden path="type" />
</form:form>
</body>
</html>
<!-- End : AnnualIncrementHome.jsp  -->