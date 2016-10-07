<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ErpEmpLeaveBalaneHome.jsp-->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/prompt.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css"
	rel="stylesheet" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jprompt.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/leave.js"></script>

<title>Employee Balance</title>
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
								<div class="headTitle">Leave Balance</div>
								<div class="line">

									<%-- Content Page starts --%>
									<div class="line" id="result"></div>
									<div class="message"></div>
									<div id="result" class="line">
										<jsp:include page="ERPLeaveDeatils.jsp" />
									</div>
									<div class="line">
										Note :</br> <font color="#ff0000"> <!-- 1) First Half Sick Leaves are Consider to Paid Leaves,Next Half are consider to Half paid leaves</br> -->
											1) Maternity Leave applicable for <b>WOMEN</b> Employees</br> 2)
											Paternity Leave applicable for <b>MEN</b> Employees
										</font>
									</div>

									<div class="line">
										<div class="expbutton">
											<a onclick="javascript:ShowLeavesList('showMydiv')"> <span>Details</span></a>
										</div>
									</div>

									<div class="line" id="showMydiv" style="display: none">
										<jsp:include page="ERPLeaveTXNDetailsList.jsp" />
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
	</form:form>
	<script>
			resetErpEnterAvailableLeaves();
			JsonErpAvailabeLeavesList=<%= (net.sf.json.JSONArray)session.getAttribute("JsonErpAvailabeLeavesList") %>;			
		</script>
</body>
</html>
<!-- End:EnterAvailableLeaves.jsp -->