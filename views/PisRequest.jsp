<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:PisRequest.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/menu.js"></script>
<link href="./styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/json2_mini.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/workflow.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<title>Update Contact Number</title>
</head>

<body>
	<form:form method="post" commandName="employee">
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
								<div class="headTitle">Update Contact Number</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="middle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								<div id="result"></div>
								<div class="line">
									<div class="line">
										<div class="quarter bold leftmar">Mobile Number</div>
										<div class="quarter">${employee.personalNumber}&nbsp;</div>
										<div class="quarter"><form:input path="personalNumber" id="personalNumber" onkeypress="return checkInt(event);"></form:input></div>
									</div>
									<div class="line">
										<div class="quarter bold leftmar">Internal Number</div>
										<div class="quarter" >${employee.internalNo}&nbsp;</div>
										<div class="quarter" ><form:input path="internalNo" id="internalNo" onkeypress="return checkInt(event);"></form:input></div>
									</div>
									<div class="line">
										<div class="quarter bold leftmar">Residence Number</div>
										<div class="quarter" >${employee.residenceNo}&nbsp;</div>
										<div class="quarter" ><form:input path="residenceNo" id="residenceNo" onkeypress="return checkInt(event);"></form:input></div>
									</div>
									<c:if test="${employee.personalNumber ne null}">
										<div class="line" style="width:382px;float:right;">
										<div><a href="javascript:submitPisRequest();"><div class="appbutton">Submit</div></a></div>	
										<div><a href="javascript:resetPisRequest();"><div class="appbutton">Clear</div></a></div>								
									</div>
									</c:if>
									<c:if test="${employee.personalNumber eq null}">
									<div class="line" style="margin-left: 25%;">
										<div><a href="javascript:submitPisRequest();"><div class="appbutton">Submit</div></a></div>	
										<div><a href="javascript:resetPisRequest();"><div class="appbutton">Clear</div></a></div>								
									</div>
									</c:if>
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
		<form:hidden path="changedValues"/>
		<form:hidden path="requestID"/>
		<form:hidden path="requestId"/>
		</form:form>
		<script>resetPisRequest();</script>
	</body>
</html>
<!-- End:PisRequest.jsp -->