<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin: ViewQuarterDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/pisscript.js"></script>

<title>View Quarter Details</title>
</head>

<body>
	<form:form method="post" commandName="quarter">
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
								<div class="headTitle">View Quarter Details</div>
								<%-- Content Page starts --%>
								<div class="line">
									<c:if test="${Result == 'success'}">
										<fieldset>
											<div class="line">
												<div class="quarter bold">SFID</div>
												<div class="quarter">${quarter.logInSfID}</div>
												<div class="quarter bold">Quarter Type</div>
												<div class="quarter">${quarter.userQuarterDetails.mainQuarter}</div>											
											</div>
											<div class="line">
												<div class="quarter bold">Quarter Sub Type</div>
												<div class="quarter">${quarter.userQuarterDetails.quarterTypeDetails.quarterSubType}</div>
												<div class="quarter bold">Quarter Number</div>
												<div class="quarter">${quarter.userQuarterDetails.quarterNo}</div>											
											</div>
											<div class="line">
												<div class="quarter bold">Allotted Date</div>
												<div class="quarter"></div>
											
												<div class="quarter bold">Occupied Date</div>
												<div class="quarter"><fmt:formatDate pattern="dd-MMM-yyyy" value="${quarter.userQuarterDetails.occupiedDate}"/></div>
											</div>
											<div class="line">
												<div class="expbutton"><a href="javascript:quarterRequest('change')"><span>Change</span></a></div>
											</div>
										</fieldset>
									</c:if>
									<c:if test="${Result == 'Not Applied' || Result == 'vacated'}">
										<div>&nbsp;</div>
										<div>&nbsp;</div>
										<div class="line">
											<div class="expbutton" style="padding-left: 40%;"><a href="javascript:quarterRequest('new')"><span>New Quarter Application</span></a></div>
										</div>
										<div>&nbsp;</div>
									</c:if>
									
									<c:if test="${Result == 'Applied' }">
										<div>&nbsp;</div>
										<div>&nbsp;</div>
										<div class="line">
											<div class="expbutton" style="padding-left: 35%">Quarter request has already been Applied</div>
										</div>
										<div>&nbsp;</div>
									</c:if>
									
									<c:if test="${Result == 'Empty'}">
										<div>&nbsp;</div><div>&nbsp;</div>
										<div class="line">
											<div class="expbutton" style="padding-left: 35%">Quarter request has already been Applied</div>
										</div>
										<div>&nbsp;</div>
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
		</form:form>
	</body>
</html>
<!-- End: ViewQuarterDetails.jsp -->