<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:SubscriptionDetailsHome.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List"%>
<%@page import="org.springframework.web.context.request.SessionScope"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css"
	rel="stylesheet" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="script/date.js"></script>
<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>
<script type="text/javascript"
	src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/datetimepicker.js"></script>
<script type="text/javascript" src="script/healthInsurance.js"></script>
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css"
	rel="stylesheet" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript"
	src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script>
	$jq(function() {
		$jq("#Pagination").displayTagAjax('paging');
	})
</script>
<title>Subscription Details</title>
</head>
<body>
	<form:form method="post" id="HealthInsuranceDto" commandName="health"
		name="health">
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
								<div class="headTitle">Subscription Details</div>
								<!--Page Content Starts  -->
								<div id="tabs">
									<div class="line" id="result"></div>
									<fieldset style="border-color: green">
										<legend>
											<strong><font color='green'><b>Subscription Details</b></font></strong>
										</legend>
										
										<div class="line">
										<div class="quarter "><b>Search Employee By Name or SFID</b></div>
										<div class="quarter"><form:input path="nameOrSfid" id="nameOrSfid" maxlength="15" onkeypress="funOnKeyPress(event,'editEmployee')"/></div>
										<div class="appbutton"><a href="javascript:getHealthEmployeeDetails();" style="text-decoration: none;">Search</a></div>
										</div>
										<div class="line"><hr></hr></div>
										
										
										<c:if test="${not empty health.nameOrSfid && empty sessionScope.healthInsuranceEmployeesList}">
										
										<div class="line">&nbsp;
										<h2 align="center" style="color: red">You Don't Have Any Data With Your Search Name or Sfid</h2>
										
										</div>
										
										</c:if>
										
										<div class="line">
												<div class="line">
													<div class="quarter"><b>SFID</b></div>
													<div class="quarter"><form:input path="sfId" maxlength="15" readonly="true"/> </div>
												</div>
												
													<div class="line">
											<div class="quarter"><b>Health Insurance Company Name</b></div>
											<div class="quarter"><form:input path="hicName" maxlength="50" /></div>
											</div>
												<div class="line">
											<div class="quarter"><b>Health Insurance Company Number</b></div>
											<div class="quarter"><form:input path="hicNo" maxlength="15" /></div>
											</div>
												
												<div class="line">
													<div class="quarter"><b>Subscription Amount</b></div>
													<div class="quarter"><form:input path="subScriptiomAmt" maxlength="15"/> </div>
												</div>
												<div class="line">
														<div class="quarter"><b>Effective Date</b></div>
												<div class="quarter">
													<form:input path="effctiveDate" id="effctiveDate"
														cssClass="dateClass" readonly="true"
														onclick="javascript:clearDateText('effctiveDate');" />
													&nbsp; <img src="./images/calendar.gif"
														id="date_start_trigger3" />
													<script type="text/javascript">
														Calendar
																.setup({
																	inputField : "effctiveDate",
																	ifFormat : "%d-%b-%Y",
																	showsTime : false,
																	button : "date_start_trigger3",
																	singleClick : true,
																	step : 1
																});
													</script>
												</div>

											</div>
											<div class="class" >&nbsp;</div>
											<div class="line">
											<div class="quarter">&nbsp;</div>
												<div class="expbutton">
													<a onclick="javascript:submitHealthSubscriptionDetails()"> <span>Submit</span></a>
												</div>
												<div class="expbutton">
													<a onclick="javascript:clearsubmitHealthSubscriptionDetails()"> <span>Clear</span></a>
												</div>
											
											
											</div>
										
											
											
										</div>
										
										<c:if test="${not empty sessionScope.healthInsuranceEmployeesList}">
										<div class="line">
										<jsp:include page="HealthInsuranceEmpHome.jsp"/>
										</div>
										</c:if>
										<div id="result" class="line">
											<div>
												<c:if test="${message=='failure'}">
													<span class="failure"><spring:message code="failure" /></span>
												</c:if>
												<c:if test="${message=='success'}">
													<span class="success"><spring:message code="success" /></span>
												</c:if>
												<c:if test="${message=='deleted'}">
													<span class="success"><spring:message code="delete" /></span>
												</c:if>
												<c:if test="${message=='duplicate'}">
													<span class="failure"><spring:message
															code="duplicate" /></span>
												</c:if>
											</div>
										</div>
									</fieldset>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div>
				<jsp:include page="Footer.jsp" />
			</div>
		</div>
		<form:hidden path="param" />
		<form:hidden path="type" />
		<form:hidden path="requestID" />
		<form:hidden path="requestId" />
	</form:form>
	<script>
			document.getElementById('sfId').value="";
			$jq('#subScriptiomAmt').val('');
			$jq('#hicNo').val('');
			$jq('#effctiveDate').val('');
			$jq('#hicName').val('');
		
	</script>
</body>
</html>
<!-- End:SubscriptionDetailsHome.jsp -->