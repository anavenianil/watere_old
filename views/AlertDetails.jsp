<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin: AlertDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script language="javascript" src="script/promotions.js"></script>
<script language="javascript" src="script/script.js"></script>
<title>View Alert</title>
</head>

<body>
	<form:form method="post" commandName="workflowmap" id="workflowmap">
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
								<div class="headTitle">								
									Alert Details						
								</div>
								<%-- Content Page starts --%>
								<div id="result"></div>
									<c:if test="${workflowmap.alertDetails.alertID =='1'}">			
								<div class="line">
													
									<div class="line">
										<div class="quarter leftmar">Alert Type</div>
										<div class="quarter">${workflowmap.alertDetails.alertDetails.alert}</div>
									</div>	
									<div class="line">
										<div class="quarter leftmar">Alert Date</div>
										<div class="quarter"><fmt:formatDate pattern="dd-MMM-yyyy" value="${workflowmap.alertDetails.assignedDate}"/></div>
									</div>
																
									<div class="line">
										<div class="quarter leftmar">Alert Message</div>
										<div class="quarter">${workflowmap.alertDetails.alertMessage}</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Venue</div>
										<div class="quarter">${workflowmap.alertDetails.venueDetails.center}</div>
									</div>
								<%--	<c:if test="${workflowmap.alertDetails.venueDetails.assessmentCategoryDetails.id==1}">
									
									<div class="line">
										<div class="quarter leftmar">Lab Representative</div>
										<div class="quarter">${workflowmap.alertDetails.empName}</div>
									</div>
									</c:if>--%>
									<c:if test="${workflowmap.alertDetails.status!='2' && workflowmap.alertDetails.response!=''}">
										<div class="line">
											<div class="quarter leftmar">Response</div>
											<div class="quarter">${workflowmap.alertDetails.response}</div>
										</div>
									</c:if>									
									<%--<c:if test="${workflowmap.alertDetails.status=='2'}">
										<div class="line" id="responseDiv">
											<div class="quarter leftmar">Response</div>
											<div class="quarter">
												<form:textarea path="remarks" id="remarks" cols="30" rows="5"/>
											</div>
										</div>
									</c:if>									
									<c:choose>--%>
										<%--Promotion starts --%>
										<%--<c:when test="${workflowmap.alertDetails.alertID=='1'}">
											<c:if test="${workflowmap.alertDetails.status=='2'}">
												<div id="buttons1">
													<div class="expbutton"><a href="javascript:respondAlert('${workflowmap.alertDetails.id}','${workflowmap.alertDetails.referenceID}','56','${workflowmap.alertDetails.alertID}')"><span>Accept</span></a></div>
													<div class="expbutton"><a href="javascript:respondAlert('${workflowmap.alertDetails.id}','${workflowmap.alertDetails.referenceID}','54','${workflowmap.alertDetails.alertID}')"><span>Reject</span></a></div>
												</div>											
											</c:if>
										</c:when>--%>
										<%--Promotion ends --%>
									<%--</c:choose>	--%>
									<div>
									    <div style="margin-left:25%">
										<div class="expbutton"><a href="javascript:pisHome()"><span>Back</span></a></div>
										<div class="expbuttonred"><a href="javascript:respondAlert('${workflowmap.alertDetails.id}','${workflowmap.alertDetails.referenceID}','0','${workflowmap.alertDetails.alertID}')"><span>Delete</span></a></div>
									</div>								
								</div>	
								</div>	
								</c:if>	
								<c:if test="${workflowmap.alertDetails.alertID =='2' || workflowmap.alertDetails.alertID =='3' }">	
								<div><jsp:include page="HRDGAlertDetails.jsp" /></div>
								</c:if>							
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
<!-- End: AlertDetails.jsp -->