<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:QualificationDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
</head>
<body>
	<form:form method="post" commandName="qualification">
	<script>document.title="Qualification Details"</script>	
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
								<div style="padding: 10px 0 0 0;"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								 <c:if test="${message ne 'viewQualificationDetails'}">
								   <div class="headTitle">Manage Qualification Details</div>
								 </c:if>
								 <c:if test="${message eq 'viewQualificationDetails'}">
								   <div class="headTitle">Qualification Details</div>
								 </c:if>
									<div class="line">
								        <c:if test="${sessionScope.roleType eq 'admin'}">
										<div class="quarter bold leftmar" >SFID</div>
										<div class="quarter"><form:input path="sfid" id="sfid"/></div>
										<div class="quarter"><a href="javascript:viewQualification();"><div class="appbutton">Submit</div></a></div>
									    </c:if>
								    </div>
								  <div class="line">
								  	<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="qualification.failure"/></span></c:if>
									<c:if test="${message=='success'}"> <span class="success"><spring:message code="qualification.success"/></span></c:if>
									<c:if test="${message=='update'}"> <span class="success"><spring:message code="qualification.update"/></span></c:if>
									<c:if test="${message=='delete'}"> <span class="success"><spring:message code="qualification.delete"/></span></c:if>
									<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="qualification.duplicate"/></span></c:if>
								  </div>
								    <div class="line" id="createQualification" style="display:none"><jsp:include page="CreateQualification.jsp"></jsp:include></div>
 	                               <div class="line" id="qualificationList" style="display:none"><jsp:include page="QualificationList.jsp"> </jsp:include></div>												
								 
									
								<%-- Content Page end --%>								
								<div>&nbsp;</div>
								</div>
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
		
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="id1"/>
		<form:hidden path="cdate"/>
		<script>
		enablequalValues('${qualification.param}');
		</script>
		</form:form>
	</body>
</html>
<!-- End:QualificationDetails.jsp -->