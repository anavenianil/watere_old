<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ITSectionHome.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/incomeTax.js"></script>
<script>
	jsonSectionList = <%= (net.sf.json.JSONArray)session.getAttribute("jsonSectionList")%>;
</script>
<title>IT Section</title>
</head>

<body onload="javascript:clearSectionDetails();">
	<form:form method="post" commandName="incomeTaxMaster" id="incomeTaxMaster">
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
								<div class="headTitle">IT Section Master</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								
								<div>
									<%-- Content Page starts --%>
								<div class="line">
										<div class="quarter leftmar">Section Name<span class="mandatory">*</span></div>
										<div class="quarter" ><form:input path="secName" id="secName" onkeypress="javascript:return checkSpecialChar(event);"/>
												</div>
								</div>
								<div class="line">
										<div style="margin-left:50%">
										<a href="javascript:submitITSectionDetails();" class="appbutton">Submit</a>
										<a href="javascript:clearSectionDetails();" class="appbutton">Clear</a>
										</div>
									</div>
									<div class="line" id="result"><jsp:include page="ITSectionList.jsp"></jsp:include></div>
									
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="pk"/>
		</form:form>
	</body>
</html>
<!-- End:ITSectionHome.jsp -->