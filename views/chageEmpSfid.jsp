<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:chageEmpSfid.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<title>Change SFID</title>
</head>
<body>
	<form:form method="post" commandName="master">
	<form:hidden path="param"/>
	<form:hidden path="type"/>
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
								<div style="padding: 10px 0 0 0;"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
									<div class="headTitle">Change  SFID</div>
									<div id="EmpChangeSFID" style="margin-left: 8px;">
								
									</div>
									<div>&nbsp;</div>
									<div class="line">
										<div class="quarter bold leftmar">SFID<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="changeSfid" id="changeSfid" maxlength="15" /></div>
										<div class="quarter"><a href="javascript:setEmpSFID();"><div class="appbutton">Submit</div></a></div>
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
		<script>
			document.forms[0].changeSfid.focus();
		</script>
		</form:form>
	</body>
</html>
<!-- End:chageEmpSfid.jsp -->