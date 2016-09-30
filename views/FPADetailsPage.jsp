<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:FPADetailsPage.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/Fpa.js"></script>

<title>FPA Request Details</title>
</head>

<body>
	<form:form method="post" commandName="fpaRequest">
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
								<div class="headTitle">FPA Request Details</div>
								<div class="line">
								
								<%-- Content Page starts --%>
								<div class="line">
                                 <c:if test="${Result=='failure'}">  <span class="failure">${remarks}</span></c:if>
                                </div>
									<div class="line">
										<div class="half leftmar">SFID</div>
										<div class="half">${fpaRequest.sfID}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Name</div>
										<div class="half">${fpaRequest.employeeDetails.nameInServiceBook}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Designation</div>
										<div class="half">${fpaRequest.employeeDetails.designationDetails.name}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Employment Type</div>
										<div class="half">${fpaRequest.employeeDetails.employmentDetails.name}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Date of joining in DRDO</div>
										<div class="half"><fmt:formatDate pattern="dd-MMM-yyyy" value="${fpaRequest.employeeDetails.dojDrdoInFormat}" /></div>
									</div>
									<div class="line">
										<div class="half leftmar">Date of superannuation or retirement</div>
										<div class="half">${fpaRequest.employeeDetails.retiredDate}</div>
									</div>
									<div class="line">
										<div class="half leftmar"></div>
										<div class="half"></div>
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		</form:form>
	</body>
</html>
<!-- End:FPADetailsPage.jsp -->
