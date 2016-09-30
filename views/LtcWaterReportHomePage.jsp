<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:LTCWaterRequestHome.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css"
	rel="stylesheet" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript"
	src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/ltc.js"></script>
<!-- <script type="text/javascript" src="script/tada.js"></script> -->



<title>LTC ADVANCE HOME</title>
</head>
<body>
	<form:form method="post" commandName="ltcwater" name="ltcWaterApproval"
		id="ltcWaterApproval">
		<div>
			<div class="Innermaindiv">
				<div class="header"></div>
				<div><jsp:include page="MenuLinks.jsp" /></div>
				<div class="mainDisplay">
					<div class="whitebox_t">
						<div class="whitebox_tl">
							<div class="whitebox_tr"></div>
						</div>
					</div>
					<div class="whitebox_m1">
						<div class="lightblueBg1">
							<div class="headTitle">ANNUAL LEAVE REPORTS LIST</div>
							<div class="line" id="result"></div>

							<!--content Start here  -->

							<div>
								<table align="center">
									<tr>
										<td>Select Annual Year For Report&nbsp;&nbsp;<b>:</b>
											&nbsp;&nbsp;
										</td>
										<td><select name="ltcYear" path="ltcYear" id="ltcYear">
												<c:forEach var="item" items="${ltcwater.ltcYearList}">
													<option value="${item.id}">${item.year}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
									<td>&nbsp;</td>
									</tr>
									<tr>
									<td>&nbsp;</td>
										<td align="center">
											<div class="expbutton">
												<a onclick="javascript:annualLeaveReport()"> <span>Submit</span></a>
											</div>
										</td>
									</tr>
								</table>
							</div>
							<!--content End here  -->
						</div>
					</div>
				</div>
			</div>
		</div>
		<div><jsp:include page="Footer.jsp" /></div>
		</div>
		<form:hidden path="param" />
		<form:hidden path="type" />
		<form:hidden path="requestID" />
		<form:hidden path="requestId" />

	</form:form>
</body>
</html>