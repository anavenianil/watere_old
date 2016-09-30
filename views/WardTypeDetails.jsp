<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:WardTypeDetails.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/cghs.js"></script>
<title>Ward Type Details</title>
</head>

<body>
	<form:form method="post" commandName="cghs" id="cghs">
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
								<div class="headTitle">Ward Type Details</div>
								<div>
									<%-- Content Page starts --%>
									<div class="line">
										<div class="quarter leftmar">Ward Type Name<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="wardName" id="wardName" maxlength="100" onkeypress="javascript:increaseTextWidth('wardName');return checkChar(event);"  /></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Pay Band From<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="startBasicPay" id="startBasicPay" maxlength="100" onkeypress="javascript:increaseTextWidth('startBasicPay');return checkFloat(event,'startBasicPay');" /></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Pay Band To<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="endBasicPay" id="endBasicPay" maxlength="100" onkeypress="javascript:increaseTextWidth('endBasicPay');return checkFloat(event,'endBasicPay');"/></div>
									</div>
									<div class="line">
										<div style="margin-left:25%">
											<a href="javascript:manageWardType();"><div class="appbutton">Submit</div></a>
											<a href="javascript:clearWardType();"><div class="appbutton">Clear</div></a>
										</div>
									</div>
									<div class="line" id="wardTypeList">
									    <jsp:include page="WardTypeList.jsp" />
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="pk"/>
		</form:form>
	</body>
</html>
<!-- End:WardTypeDetails.jsp -->