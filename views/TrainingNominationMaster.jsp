<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TrainingNominationMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript"
	src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/trainingscript.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script language="javascript" src="./script/date.js"></script>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css"
	rel="stylesheet" />

<title>Training Nomination Master</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	clearNomination();
		});
</script>
<style>
</style>
</head>

<body>
	<form:form method="post" commandName="trainingRequest"
		id="trainingRequest">
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
								<div class="headTitle">Nomination Form</div>

								<%-- Content Page starts --%>
								<div>
									<c:if test="${message=='failure'}">
										<span class="failure"><spring:message code="failure" /></span>
									</c:if>
									<c:if test="${message=='success'}">
										<span class="success"><spring:message code="success" /></span>
									</c:if>
									<c:if test="${message=='update'}">
										<span class="success"><spring:message code="update" /></span>
									</c:if>
									<c:if test="${message=='duplicate'}">
										<span class="failure"><spring:message code="duplicate" /></span>
									</c:if>
									<c:if test="${message=='deletefail'}">
										<span class="failure"><spring:message
												code="recordexists" /></span>
									</c:if>
								</div>
								<div>

									<div class="line" id="one">
										<div class="quarter bold leftmar">
											Course Name<span class="failure">*</span>
										</div>

										<div class="quarter">
											<form:select path="cirId" id="courseId" cssClass="formSelect"
												onchange="javascript:getTrainingCourseDetailsForNomination();"
												onmouseover="setSelectWidth('#courseId')">
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.courseList}"
													itemValue="trainingTypeId" itemLabel="name" />
											</form:select>
										</div>

									</div>
									<div style="clear: both;"></div>
									<div id="ion"></div>

									<fieldset style="margin-top:15px;" >
										<legend>
											<strong><font color='green'>Enter Nomination
													Details</font></strong>
										</legend>

										<div class="line" id="two">
											<div class="quarter bold leftmar">
												SFID<span class="failure">*</span>
											</div>

											<div class="quarter">
												<form:input path="nomineeSfid" id="nomineeSfid"
													maxlength="50" readonly="true" />
											</div>
											<div class="quarter bold leftmar">Name</div>
											<div class="quarter">
												${trainingRequest.empDetails.nameInServiceBook}</div>

										</div>
										<div class="line">
											<div class="quarter bold leftmar">
												Current Assignment<span class="failure">*</span>
											</div>

											<div class="quarter">
												<form:textarea path="currentAssignment"
													id="currentAssignment" cols="20" rows="3"
													onkeypress="textCounter(event,$jq('#currentAssignment'),$jq('#currentAssignmentCounter'),200);"
													onfocus="textCounter(event,$jq('#currentAssignment'),$jq('#nameCounter'),200);"
													onkeyup="textCounter(event,$jq('#currentAssignment'),$jq('#nameCounter'),200);"></form:textarea>
												<input type="text" class="counter"
													name="currentAssignmentCounter" value="200"
													id="currentAssignmentCounter" disabled="" />
											</div>
											<div class="quarter bold leftmar">Programmes attended
												during the past two years</div>
											<div class="quarter">
												<form:textarea path="lastAttendedCourse"
													id="lastAttendedCourse" readonly="true" cols="20" rows="3"
													onkeypress="textCounter(event,$jq('#lastAttendedCourse'),$jq('#lastAttendedCourseCounter'),1500);"
													onkeyup="textCounter(event,$jq('#lastAttendedCourse'),$jq('#lastAttendedCourseCounter'),1500);"></form:textarea>
												<input type="text" class="counter" style="width: 35px;"
													name="lastAttendedCourseCounter" value="1500"
													id="lastAttendedCourseCounter" disabled="" />
											</div>

										</div>
										<div class="line">
											<div class="quarter bold leftmar">
												Relevance of the Proposed programme to current or future
												assignments/objectives of the Lab<span class="failure">*</span>
											</div>

											<div class="quarter">
												<form:textarea path="relevance" id="relevance" cols="20"
													rows="3"
													onkeypress="textCounter(event,$jq('#relevance'),$jq('#relevanceCounter'),200);"
													onkeyup="textCounter(event,$jq('#relevance'),$jq('#relevanceCounter'),200);"></form:textarea>
												<input type="text" class="counter" name="relevanceCounter"
													value="200" id="relevanceCounter" disabled="" />
											</div>

										</div>





										<div class="line">
											<div style="margin-left: 25%">

												<a href="javascript:nominationSubmit();"> <div class="appbutton" >Submit</div> </a>
											</div>

											<a href="javascript:clearNomination();"> <div class="appbutton">Clear</div> </a>

										</div>




										
									</fieldset>


									<div class="line height"></div>
									<div class="line" id="displayTable"></div>

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
		<form:hidden path="param" id="param" />
		<form:hidden path="type" id="type" />
		<form:hidden path="nomRequestStatus" id="nomRequestStatus" />



	</form:form>
	<script>
			
			jsonTrainingInistituteList=<%=(net.sf.json.JSONArray) session
					.getAttribute("jsonTrainingInistituteList")%>;
			jsonCourseSubjCategoryList=<%=(net.sf.json.JSONArray) session
					.getAttribute("jsonCourseSubjCategoryList")%>;
			jsonCourseSubjSubCategoryList=<%=(net.sf.json.JSONArray) session
					.getAttribute("jsonCourseSubjSubCategoryList")%>;
			jsonDesignationList=<%=(net.sf.json.JSONArray) session
					.getAttribute("jsonDesignationList")%>;
			jsonMasterDataList=<%=(net.sf.json.JSONArray) session
					.getAttribute("jsonTrainingMstDataList")%>;
			
			
			
		</script>
</body>
</html>
<!-- End:TrainingNominationMaster.jsp -->