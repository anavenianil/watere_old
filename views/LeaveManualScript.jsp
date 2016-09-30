<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LeaveManualScript.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/leave.js"></script>

<title>Leave Manual Script</title>
</head>
<body>
	<form:form method="post" commandName="leaveAdmin">
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
								<div class="headTitle">Leave Manual Script</div>
								
								<%-- Content Page starts --%>
								<div class="line"></div>
								<div class="line">
									<div class="line" id="result"></div>
									<div class="line">
										<div class="quarter leftmar">Run Date<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="scriptDate" id="scriptDate" cssClass="dateClass" readonly="true"/>
											<img  src="./images/calendar.gif" id="scriptDateImg" style="padding: 1px 2px 4px 4px;"/>	
											<script type="text/javascript">
												Calendar.setup({inputField :"scriptDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"scriptDateImg",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">
										<div style="margin-left:30%">
											<div class="expbutton"><a href="javascript:runLeaveScript();"><span>Submit</span></a></div>
											<div class="expbutton"><a href="javascript:resetLeaveScript();"><span>Reset</span></a></div>
										</div>
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
		<form:hidden path="type"/>
		<form:hidden path="param"/>	
		</form:form>		
	</body>
</html>
<!-- End:LeaveManualScript.jsp -->