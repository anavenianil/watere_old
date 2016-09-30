<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LeaveSearchDetails.jsp -->
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
<title>Health Insurance Details</title>
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
								<div class="headTitle">Health Insurance Details</div>
								<!--Page Content Starts  -->
								<div id="tabs">
									<div class="line" id="result"></div>
									<fieldset style="border-color: green">
										<legend>
											<strong><font color='green'><b>Health
														Insurance Details</b></font></strong>
										</legend>
										<div class="line">
											<div class="quarter leftmar">
												Date Of Enrollment<span class="mandatory">*</span>
											</div>
											<div class="quarter">
												<input type="text" name="fromDate" id="fromDate"
													readonly="readonly" /> <img src="./images/calendar.gif"
													id="effectiveFromDateAdvImg" />
												<script type="text/javascript">
													Calendar
															.setup({
																inputField : "fromDate",
																ifFormat : "%d-%b-%Y",
																showsTime : false,
																button : "effectiveFromDateAdvImg",
																singleClick : true,
																step : 1
															});
												</script>
											</div>
										</div> 
										<div class="expbutton" style="margin-left: 45%">
					 						<a href="javascript:saveHealthEnrollmentDetails()"><span>Submit</span></a>
										</div>
										<div class="expbutton">
											<a href="javascript:clearHealthInsuranceDetails()"><span>Clear</span></a>
										</div>
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
											<div id="HealthInsurance">
												<%
													int i = 0;
												%>
												<display:table name="${health.healthInsuranceDetails}"
													excludedParams="*" export="false" class="list"
													requestURI="" id="health" pagesize="10000" sort="list">
													<display:column style="width:5%;text-align:center"
														title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'rowh\'); checkApplying();"/>'>
														<input type="checkbox" class="rowh" name="rowh"
															value="${health.sfid}" id="hea<%=i %>"
															onclick="checkApplying()" />
													</display:column>
													<display:column title="Id" style="width:8%">${health.sfid} </display:column>
													</br>
													<display:column title="Name" style="width:15%">&nbsp;<span
															name="FullName" id="healthName<%=i%>"
															onkeypress="return checkInt(event,'healthName');"
															readonly>${health.fullName}</span>
													</display:column>
													</br>
													<display:column title="Designation" style="width:15%">&nbsp;<span
															name="Desg" id="healthdesg<%=i%>"
															onkeypress="return checkInt(event,'healthdesg');"
															readonly>${health.designation}</span>
													</display:column>
													</br>
													<display:column style="width:20%;text-align:center"
														title="Joining Date">
														<span><font color='red'>&nbsp;<span
																name="Date" id="healthjoin<%=i%>"
																onkeypress="return checkInt(event,'healthjoin');"
																readonly>${health.joiningDate}</span>
														</font></span>
													</display:column>
													</br>
													<display:column title="Status" style="width:6%">&nbsp;<span
															name="status" id="healthstatus<%=i%>"
															onkeypress="return checkInt(event,'healthstatus');"
															readonly>${health.status}</span>
													</display:column>
													<%
														i++;
													%>
												</display:table>
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
</body>
</html>