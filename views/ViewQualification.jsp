<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!-- Begin : ViewQualification.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
</head>
<body>
	<form:form method="post" commandName="qualification">
	<script>document.title="Qualification Details"</script>	
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
								<div id="result"></div>
								<div class="line">
									<div class="quarter bold" id="labelType" style="margin-left:11px;">SFID</div>
									<div class="quarter"><form:input path="sfid" id="changeSfid" maxlength="15"/></div>
									<div class="appbutton"><a href="javascript:changeEmployeeDetails('qualification');" style="text-decoration: none";>Search</a></div>
								</div>
								<div class="line">
									    <div class="headTitle">Manage Qualification Details</div>           
										<div id="createQualification">
											<jsp:include page="CreateQualification.jsp"></jsp:include></div>
									 	<div id="qualificationList">
									 	    <jsp:include page="QualificationList.jsp" /> </div>
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="id" id="id"/>
		</form:form>
	</body>
</html>
<!-- End : ViewQualification.jsp -->
