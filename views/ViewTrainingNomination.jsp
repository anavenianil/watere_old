<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ViewTrainingNomination.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/trainingscript.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script language="javascript" src="./script/date.js"></script>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>

<title>Training Nomination Master</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	
		});
</script>
<style>

</style>
</head>

<body>
	<form:form method="post" commandName="trainingRequest" id="trainingRequest">
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
								<div class="headTitle">Nomination Cancellation</div>
								
								<%-- Content Page starts --%>
								<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='update'}"> <span class="success"><spring:message code="update"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='deletefail'}"> <span class="failure"><spring:message code="recordexists"/></span></c:if>
	</div>
								<div>
																		
									<div class="line" id="one">
										
									</div>
									<div id="ion">
									
									</div>
									<fieldset class="line">
<legend ><strong><font color='green'>Nomination Details</font></strong></legend>
									
									<div class="line">
												<div class="quarter bold">Course Name</div>
												<div class="quarter">:&nbsp;${trainingRequest.course}</div>
												<div class="quarter bold">Nomination Date</div>
												<div class="quarter" id="tdWorkPlace">:&nbsp;${trainingRequest.nominationDate}</div>
								    </div>
									<div class="line">
										<div class="quarter bold">Current Assignment</div>
										<div class="quarter" id="authorizedMove">:&nbsp;${trainingRequest.currentAssignment}</div>
										<div class="quarter bold">Last Attended Program</div>
										<div class="quarter" id="tadaAdvanceAmount">:&nbsp;${trainingRequest.lastAttendedCourse}</div>
									</div>
									<div class="line">
									    <div class="quarter bold">Relevance</div>
									    <div class="quarter" id="tadaAdvanceAmount">:&nbsp;${trainingRequest.relevance}</div>
									</div>
									<div class="line">
									    <div class="quarter bold">Nomination Form:</div>
										<div class="quarter">:&nbsp;<a href="javascript:getNominationReport('${trainingRequest.id}','trainingNominationPrint')">Print</a></div>
									    
									</div>
									</fieldset>
									 <div class="line">
										
										<div  class="quarter bold leftmar">Reason for Cancellation<span class="failure">*</span></div>
										
										<div class="quarter"><form:textarea path="cancelReason" id="cancelReason" cols="20" rows="3" onkeypress="textCounter(event,$jq('#cancelReason'),$jq('#nameCounter'),200);"
															 onkeyup="textCounter(event,$jq('#cancelReason'),$jq('#nameCounter'),200);"></form:textarea>
													<input type="text" class="counter" name="nameCounter" value="200" id="nameCounter" disabled=""/>
											
										</div>
									</div>
									
									
									
									<div class="line">
										<div style="margin-left:25%">
										
											<a href="javascript:cancelNominationSubmit();"><div class="appbutton">Cancel</div></a><a href="javascript:backToNominationDetails();"><div class="appbutton">Back</div></a>
										</div>
										
											
									</div>
									
									
									
									<div class="line height"></div>
									<div class="line" id="displayTable">
										
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
		<form:hidden path="type" id="type" />
		<form:hidden path="requestType" id="requestType"/>
		<form:hidden path="requestId" id="requestId"/>
		<form:hidden path="historyID" id="historyID"/>
		<form:hidden path="stageID" id="stageID"/>
		
		
		
		
		</form:form>
		<script>
		
			
		</script>
	</body>
</html>
<!-- end:ViewTrainingNomination.jsp -->