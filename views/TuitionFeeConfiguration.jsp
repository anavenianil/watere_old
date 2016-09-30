<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TutionFeeConfiguration.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/tutionFee.js"></script>
<title>Tuition Fee Configuration</title>
</head>
<body>
<form:form method="post"  commandName="tutionFee">
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
	
	<div class="headTitle">Tuition Fee Configuration</div>
	<div class="line">
	<%-- Content Page starts --%>
	<div class="line" id="result"></div>
	<fieldset><legend><strong><font
		color='green'>Tuition Fee Max Limit Per One Child</font></strong></legend>
	    <div class="line">
		<div class="half leftmar">Max Amount Per One Child<span class="mandatory">*</span></div>
		<div class="quarter"><form:input path="tutionFeeMaxLimitPerOneChild" id="tutionFeeMaxLimitPerOneChild" onkeypress="return checkInt(event);"/></div>
        </div> 
  </fieldset>
   <fieldset><legend><strong><font
		color='red'>Tuition Fee Max Child ToBe Claimed</font></strong></legend>
        <div class="line">
        <div class="half leftmar">Max No Of Children To Apply<span class="mandatory">*</span></div>
		<div class="quarter"><form:input path="tutionFeeMaxChildToBeClaimed" id="tutionFeeMaxChildToBeClaimed" onkeypress="return checkInt(event);"/></div>
		</div>
 </fieldset>
 <div class="line">&nbsp;</div>
  <div class="line">
	<div style="margin-left:70%">
		<a href="javascript:saveTuitionFeeconfiguration();" class="appbutton">Save</a>
	</div>
  </div>
	<%-- Content Page end --%>
	</div>
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
	<form:hidden path="configurationDetails" />
	
</form:form>
</body>
</html>
<!-- End:TutionFeeConfiguration.jsp -->