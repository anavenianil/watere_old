<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LeaveSearchDetails.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />

<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/leave.js"></script>

<c:if test="${message=='ADMIN'}">		
	<title>Publish in DO Part II</title>
</c:if>
<c:if test="${message=='user'}">		
	<title>Leave Search</title>
</c:if>
</head>

<body>
	<form:form method="post" commandName="leaveAdmin" name="leaveSearch">
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
								<c:if test="${message=='ADMIN'}">		
									<div class="headTitle">Publish in DO Part II</div>
								</c:if>
								<c:if test="${message=='user'}">		
									<div class="headTitle">Leave Search</div>
								</c:if>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter leftmar">From Date</div>
										<div class="quarter">
											<form:input path="fromDate" id="fromDate" cssClass="dateClass" readonly="true"/>
											
											<script type="text/javascript">
												new tcal({'formname':'leaveSearch','controlname':'fromDate'});
											</script>
										</div>
										<div class="quarter bold">To Date</div>
										<div class="quarter">
											<form:input path="toDate" id="toDate" cssClass="dateClass" readonly="true"/>
											<script type="text/javascript">
												new tcal({'formname':'leaveSearch','controlname':'toDate'});
											</script>
										</div>
									</div>
									<div class="line">
										<div style="padding-left: 45%;">
											<div><a href="javascript:leaveSearch('${message}');"  class="appbutton" style="text-decoration: none;">Search</a></div>
											<div><a href="javascript:resetLeaveSearch();" class="appbutton" style="text-decoration: none;">Clear</a></div>
										</div>
									</div>
									<div class="line"><hr /></div>
									<div id="result">
										<c:if test="${message=='user'}">
											<jsp:include page="LeaveSearchList.jsp" />
										</c:if>
										<c:if test="${message=='ADMIN'}">
											<jsp:include page="LeaveAdminSearchList.jsp" />	
										</c:if>
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
		<form:hidden path="param" id="param"/>
		<form:hidden path="type"/>
		<form:hidden path="requestID"/>
		<form:hidden path="gazType" id="gazType"/>
		</form:form>		
	</body>
</html>
<!-- End:LeaveSearchDetails.jsp -->