<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TrainingNominationOfflineMaster.jsp -->
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
	$jq("#courseId").val('');
	$jq("#currentAssignment").val('');
	$jq('#currentAssignmentCounter').val('200');
	$jq('#lastAttendedCourseCounter').val('200');
	$jq('#relevanceCounter').val('200');
	$jq("#lastAttendedCourse").val('');
	$jq("#relevance").val('');
		});
</script>
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
								<div class="headTitle">Training Nomination Form</div>
								
								<%-- Content Page starts --%>
								<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='update'}"> <span class="success"><spring:message code="update"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='deletefail'}"> <span class="failure"><spring:message code="recordexists"/></span></c:if>
	</div>
								<div>
																		
									
									
									<div class="line">
										<div  class="quarter bold leftmar">SFID<span class="failure">*</span></div>
										
										<div class="quarter"><form:input path="sfid" id="sfid" maxlength="50" readonly="true"/>
										</div>
										<div  class="quarter bold leftmar">Name</div>
										<div class="quarter">
										${trainingRequest.empDetails.nameInServiceBook}
										</div>
										
									</div>
									<div class="line">
										<div  class="quarter bold leftmar">Nominee SFID<span class="failure">*</span></div>
										
										<div class="quarter"><form:select path="nomineeSfid" id="nomineeSfid" cssClass="formSelect" >
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.empList}" itemValue="sfid" itemLabel="sfid"/>
											</form:select>
										</div>
										<div  class="quarter bold leftmar">Name</div>
										<div class="quarter" id="nomineeName">
										
										</div>
										
									</div>
									<div class="line">
									<div  class="quarter bold leftmar">Course Name<span class="failure">*</span></div>
										
										<div class="quarter"><form:select path="cirId" id="courseId" cssClass="formSelect" >
												<form:option value="">Select</form:option>
												
											</form:select>
										</div>
													
									</div>
									<div class="line">
										<div  class="quarter bold leftmar">Current Assignment<span class="failure">*</span></div>
										
										<div class="quarter"><form:textarea path="currentAssignment" id="currentAssignment" cols="20" rows="3" onkeypress="textCounter(event,$jq('#currentAssignment'),$jq('#currentAssignmentCounter'),200);"
															 onkeyup="textCounter(event,$jq('#currentAssignment'),$jq('#nameCounter'),200);"></form:textarea>
													<input type="text" class="counter" name="currentAssignmentCounter" value="200" id="currentAssignmentCounter" disabled=""/>
										</div>
										<div  class="quarter bold leftmar">Programmes attended during the past two years<span class="failure">*</span></div>
										<div class="quarter">	<form:textarea path="lastAttendedCourse" id="lastAttendedCourse" cols="20" rows="3" onkeypress="textCounter(event,$jq('#lastAttendedCourse'),$jq('#lastAttendedCourseCounter'),200);"
															 onkeyup="textCounter(event,$jq('#lastAttendedCourse'),$jq('#lastAttendedCourseCounter'),200);"></form:textarea>
													<input type="text" class="counter" name="lastAttendedCourseCounter" value="200" id="lastAttendedCourseCounter" disabled=""/>
										</div>
										
									</div>
									<div class="line">
										<div  class="quarter bold leftmar">Relevance of the Proposed programme to current or future assignments/objectives of the Lab<span class="failure">*</span></div>
										
										<div class="quarter"><form:textarea path="relevance" id="relevance" cols="20" rows="3" onkeypress="textCounter(event,$jq('#relevance'),$jq('#relevanceCounter'),200);"
															 onkeyup="textCounter(event,$jq('#relevance'),$jq('#relevanceCounter'),200);"></form:textarea>
													<input type="text" class="counter" name="relevanceCounter" value="200" id="relevanceCounter" disabled=""/>
										</div>
										
									</div>
									
									
									
									
									
									<div class="line">
										<div style="margin-left:25%">
										
											<a href="javascript:nominationSubmit();"><div class="appbutton">Submit</div></a>
										</div>
										
											<a href="javascript:clearCourse();"><div class="appbutton">Clear</div></a>
											<a id="report" href="javascript:getReport('${sessionScope.trainingMstType}');"><div class="appbutton">Report</div></a>
											
											
											
											
									</div>
									
									
									<div class="line height"><hr/></div>
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
		<form:hidden path="id" id="id"/>
		
		
		
		</form:form>
		<script>
			
			jsonTrainingInistituteList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingInistituteList") %>;
			jsonCourseSubjCategoryList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonCourseSubjCategoryList") %>;
			jsonCourseSubjSubCategoryList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonCourseSubjSubCategoryList") %>;
			jsonDesignationList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonDesignationList") %>;
			jsonMasterDataList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingMstDataList") %>;
			
			
			
		</script>
	</body>
</html>
<!-- End:TrainingNominationOfflineMaster.jsp -->