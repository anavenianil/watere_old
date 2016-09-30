<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:payBillStatus.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/tabs.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/payBill.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>

<title>Pay Bill</title>
</head>
<body>
	<form:form method="post" commandName="payBill" id="PayBillBean">
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
								<div class="headTitle">Pay Bill Status Details</div>
								<div class="line">
									<%-- Content Page starts --%>
									
									<div class="line" id="result">
									<div class="line"><hr></hr></div>
									<div class="line">
										<div class="line">${payBill.description}</div>
									</div>
									<div class="line">
											<div class="quarter bold">Month</div>
											<div class="quarter bold">Pay Auto Run</div>
											<div class="quarter bold">Pay Individual Update</div>
											<div class="quarter bold">Show to Employees</div>
									</div>
									<div class="line">
									<div class="quarter bold"><font color="green">${payBill.runMonth}</font><br></br><font color="purple">Employees :${payBill.count}<br></br>Generated :${payBill.generatedCount}</font></div>
											<div class="quarter">
											<c:if test="${payBill.runStatus eq 1}">
												<div ><div class="expbutton" id="autorun"><a href="javascript:startAutoRun()"><span>Start Auto Run</span></a></div></div>
											</c:if>
											<c:if test="${payBill.runStatus eq 0 and payBill.manualStatus eq 1}">
												<div ><div class="expbutton" id="autorun"><a href="javascript:startRegenerating()"><span>Regenerate</span></a></div></div>
											</c:if>
											<c:if test="${payBill.runStatus eq 0 and payBill.manualStatus eq 2}">
												Auto Run Completed
											</c:if>
											</div>
											<div class="quarter">
											<c:if test="${payBill.manualStatus eq 1}">
												<div ><div class="expbutton" id="manual"><a href="javascript:changeStatusToVisible()"><span>Change Status</span></a></div></div>
											</c:if>
											<c:if test="${payBill.manualStatus eq 2}">
												Manual Updates Completed
											</c:if>
											</div> 
											
											<div class="quarter"><c:if test="${payBill.userStatus eq 1}">
											<div><div class="expbutton"><a href="javascript:completePayBill()"><span>Show to User</span></a></div></div>
											</c:if>
											</div> 
											<div class="quarter"><c:if test="${payBill.manualStatus eq 0}">
											<div class="expbutton" id="manual"><a href="javascript:alertAutoRun()"><span>Change Status</span></a></div>
											</c:if></div>
											<div class="quarter"><c:if test="${payBill.userStatus eq 0 and payBill.manualStatus eq 1 }">
											<div class="expbutton" id="user"><a href="javascript:alertAutoRunManual()"><span>Show to User</span></a></div>
											</c:if></div>
											<div class="quarter"><c:if test="${payBill.userStatus eq 0 and payBill.runStatus eq 1 }">
											<div class="expbutton" id="user"><a href="javascript:alertAutoRun()"><span>Show to User</span></a></div>
											</c:if></div>
									</div>
									<div class="line"  id="regenerate">
									<div class="quarter bold">&nbsp;</div>
									<c:if test="${payBill.runStatus eq 0 and payBill.manualStatus eq 1}">
									Auto Run Completed
									</c:if>
									</div>
									<div class="line"><hr></hr></div>
									<c:if test="${payBill.manualStatus eq 1}">
									<div class="line" id="result1"></div>
									<div class="line">
									<div class="quarter bold">Start Remaining Employee payBill</div>
									<div class="quarter">
									
									<div class="expbutton"><a onclick="javascript:runPendingPayBill()"> <span>Run</span></a></div>
									
									</div>
									</div>
									</c:if>
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
		<form:hidden path="runMonth"/>
		</form:form>
	</body>
</html>
<!-- End:payBillStatus.jsp -->