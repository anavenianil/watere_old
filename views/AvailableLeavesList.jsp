<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:AvailableLeavesList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa" %>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<title>Leave Balance</title>
</head>

<body>
	<form:form method="post" commandName="leaveRequest">
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
									<c:forEach var="leaves" items="${leaveRequest.leaveBalanceList}"  varStatus="rowCounter">
										<div class="line">
											<div class="quarter" style="margin-left:20%">${leaves.leaveType}</div>
											<c:if test="${leaves.id==6}"><div style="width:10%;text-align:right;float:left">-${leaves.leaves}</div></c:if>
											<c:if test="${leaves.id!=6}"><div style="width:10%;text-align:right;float:left">${leaves.leaves}</div></c:if>
										</div>
									</c:forEach>										
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
<!-- End:AvailableLeavesList.jsp -->