<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LoanImmediateRelief.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/layout.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/loan.js"></script>
<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>
<title>Immediate Loan Relief on Death</title>
</head>

<body onload="javascript:clearRelief()">
<form:form method="post" commandName="loan" name="LoanImmediate">
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
	<div class="headTitle">Application for Immediate Relief on Death</div>
	<%-- Content Page starts --%>
	<div class="line">
	<div class="line">
	<div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
	<div class="quarter"><form:input path="refSfID" id="refSfID" /></div>
	</div>
	<div class="line">
	<div class="quarter leftmar">Date of Death<span
		class="mandatory">*</span></div>
	<div class="quarter"><form:input path="dod" id="dod"
		cssClass="dateClass" readonly="true" /> 
		<script type="text/javascript">
		  new tcal({'formname':'LoanImmediate','controlname':'dod'});
			</script>
											</div>
	</div>
	<div class="line">
	<div class="quarter leftmar">Name of the Applicant<span
		class="mandatory">*</span></div>
	<div class="quarter"><form:input path="appname" id="appname" onkeypress= "return isAlphabetExp(event,'appname');"></form:input></div>
	</div>
	<div class="line">
	<div class="quarter leftmar">Relationship with the deceased
	Government Servant<span class="mandatory">*</span></div>
	<div class="quarter"><form:select path="relationShipID"
		id="relationShipID" cssClass="formSelect">
		<form:option value="0">Select</form:option>
		<form:options items="${loan.relationList}" itemValue="id"
			itemLabel="name" />
	</form:select></div>
	</div>

	<div class="line">
	<div class="quarter leftmar">Amount applied<span
		class="mandatory">*</span></div>
	<div class="quarter"><form:input path="advanceAmount"
		id="advanceAmount" onpaste="return false"
		onkeypress="return checkFloat(event,'advanceAmount');CheckDeathReliefAmountValid(event);" onkeyup="return CheckDeathReliefAmountValid(event);"></form:input></div>
	</div>
	<div class="line">
	<div class="quarter leftmar">Witness 1</div>
	<div class="quarter"><form:input path="witness1" id="witness1" onkeypress="return isAlphabetSplCharExp(event,'witness1');"></form:input></div>
	</div>
	<div class="line">
	<div class="quarter leftmar">Witness 2</div>
	<div class="quarter"><form:input path="witness2" id="witness2" onkeypress="return isAlphabetSplCharExp(event,'witness1');"></form:input></div>
	</div>
	<div class="line">
	<div class="quarter leftmar">Residential Address<span
		class="mandatory">*</span>
	</div>
	<div><form:textarea path="address" id="address" rows="5" cols="30"  onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"/>
		<input type="text" class="counter" name="counter" value="500" id="counter" disabled=""/>
		</div>
	</div>
	<div class="line">
	<div class="quarter leftmar">Approved by<span
		class="mandatory">*</span></div>
	<div class="quarter"><form:input path="approvedBy"
		id="approvedBy" onkeypress= "return isAlphabetExp(event,'approvedBy');"></form:input></div>
	</div>
	<div style="margin-left: 25%;"><a
		href="javascript:manageRelief();" class="appbutton">Submit</a> <a
		href="javascript:clearRelief();" class="appbutton">Clear</a></div>

	<div class="line height">
	<hr />
	</div>
	<div class="line" id="result">
		<jsp:include page="LoanImmediateReliefList.jsp"></jsp:include>
	</div>


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
<!-- End:LoanImmediateRelief.jsp -->