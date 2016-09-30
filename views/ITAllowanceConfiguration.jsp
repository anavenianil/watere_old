<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ITAllowanceConfiguration.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.incometax.dto.IncomeTaxStageDTO"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/incomeTax.js"></script>

<script>
		loadScript('incomeTaxStages');
		<% if (session.getAttribute("incomeTaxConfList")!=null ) { %>
			incomeTaxStageListJSON =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<IncomeTaxStageDTO>)session.getAttribute("incomeTaxStageList"))%>;
		<%}%>
			
		
	</script>

<title>Configuration Details</title>
</head>
<body>
<form:form method="post" id="incomeTaxMaster" commandName="incomeTaxMaster">
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
	<div class="headTitle">IT Allowance Configuration Details</div>
	<div class="line"><%-- Content Page starts --%>
	<div class="line" id="result"></div>
	<fieldset><legend><strong><font
		color='green'>Transport Allowance Cfg Details</font></strong></legend>
	    <div class="line">
		<div class="half leftmar">Transport Allowance(per Month)<span class="mandatory">*</span></div>
		<div class="quarter"><form:input path="ittransportAllowance" id="ittransportAllowance" onkeypress="javascript:return checkInt(event);"/></div>
        </div> 
  </fieldset>
   <fieldset><legend><strong><font
		color='red'>Age Limit For Sr Citizen</font></strong></legend>
        <div class="line">
        <div class="half leftmar">Age Limit for Sr.Citizen<span class="mandatory">*</span></div>
		<div class="quarter"><form:input path="ageLimitForSrCitizen" id="ageLimitForSrCitizen" onkeypress="javascript:return checkInt(event);"/></div>
		</div>
 </fieldset>
 <div class="line">&nbsp;</div>
  <div class="line">
	<div style="margin-left:70%">
		<a href="javascript:saveITAllowanceConfiguration();" class="appbutton">Save</a>
	</div>
  </div>


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
	<form:hidden path="configurationDetails" />
	
</form:form>
</body>
</html>
<!-- End:ITAllowanceConfiguration.jsp -->