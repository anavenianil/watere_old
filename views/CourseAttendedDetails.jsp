<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:CourseAttendedDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
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

<title>Course Attended Details</title>

</head>

<body  onload="clearLetterNumber();">
	<form:form method="post" commandName="trainingMaster" id="trainingMaster">
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
								<div class="headTitle">Course Attended Details</div>
								<%-- Content Page starts --%>
								<div>
									<div class="line" id="result">
										<jsp:include page="CourseResult.jsp" />
									</div>
									
									<div class="line">
										<div   class="quarter bold leftmar">Training Type<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="trainingTypeId" id="trainingTypeId" cssClass="formSelect" onchange="getCoursesAttendedList();" >
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.trainingTypeList}" itemValue="id" itemLabel="name"/>
											</form:select>											
										</div>
										<div class="quarter bold leftmar">Year<span class="mandatory">*</span></div>
										<div class="quarter">
											
												<form:select path="courseYear" id="courseYear" cssStyle="width:145px;" onchange="getCoursesAttendedList();">
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.FinancialYearList}" itemLabel="financialYear" itemValue="id"></form:options>
												</form:select>
											
										</div>
										
									</div>
									<div class="line">
										<div  class="quarter bold leftmar">SFID<span class="failure">*</span></div>
										<div ss class="quarter">
											<form:input path="nomineeSfid" id="nomineeSfid" maxlength="50" onchange="javascript:getEmployeeName();"/>
										</div>
										<div class="half" id="result1">
		                           				 <jsp:include page="TrainingEmployeeName.jsp"></jsp:include>
	                               	    </div>
																
										
									</div>
									
																	
									
								   <div class="line">
										
										<div  class="quarter bold leftmar">Course Name<span class="failure">*</span></div>
										
										<div class="quarter"><form:textarea path="course" id="course" cols="73" rows="3" onkeypress="textCounter(event,$jq('#course'),$jq('#nameCounter'),200);"
															 onkeyup="textCounter(event,$jq('#course'),$jq('#nameCounter'),200);"></form:textarea>
													<input type="text" class="counter" name="nameCounter" value="200" id="nameCounter" disabled=""/>
											
										</div>
									</div>
									<div class="line">
										<div class="quarter bold leftmar">Start Date<span class="mandatory">*</span></div>
										<div ss class="quarter">
											<form:input path="durationStartDate" id="durationStartDate" cssClass="dateClass" readonly="true" />&nbsp;
				
			    									 <img  src="./images/calendar.gif"   id="date_start_trigger1" />	
													<script type="text/javascript">
			           																Calendar.setup({inputField :"durationStartDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
													</script> 
										</div>
										<div  class="quarter bold leftmar">End Date<span class="mandatory">*</span></div>
										<div ss class="quarter">
											<form:input path="durationEndDate" id="durationEndDate" cssClass="dateClass" readonly="true"/>&nbsp;
				
			    									 <img  src="./images/calendar.gif"   id="date_start_trigger2" />	
													<script type="text/javascript">
			           																Calendar.setup({inputField :"durationEndDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
													</script> 
										</div>
									</div>
									<div class="line">
										<div  class="quarter bold leftmar">Fee</div>
										<div ss class="quarter">
											<form:input path="fee" id="fee" maxlength="50" onkeypress="javascript:return checkFloat1(event,'fee');"/>
										</div>
										<div  class="quarter bold leftmar">Service Tax</div>
										<div ss class="quarter">
											<form:input path="serviceTax" id="serviceTax" maxlength="50" onkeypress="javascript:return checkFloat1(event,'serviceTax');"/> %
										</div>
									</div>
									
									
									
									
									
									<div class="line">
										<div style="margin-left:25%">
										
											<a href="javascript:manageCourseAttended('${sessionScope.trainingMstType}',jsonFinancialList,jsonMasterDataList);"><div class="appbutton">Submit</div></a>
										</div>
										
											<a href="javascript:clearAttendedCourses('${sessionScope.trainingMstType}');"><div class="appbutton">Clear</div></a>
											
									</div>
									
									
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="CourseAttendedDataList.jsp"></jsp:include>  
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
				
			
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type" />
		<form:hidden path="id" id="id"/>
		<form:hidden path="trainingType" id="trainingType"/>
		<form:hidden path="trainingInistitute" id="trainingInistitute"/>
		<form:hidden path="trainingRegion" id="trainingRegion"/>
		<form:hidden path="year" id="year"/>
		<form:hidden path="courseSubjCategory" id="courseSubjCategory"/>
		<form:hidden path="courseSubjSubCategory" id="courseSubjSubCategory"/>
		<form:hidden path="course" id="course"/>
		<form:hidden path="courseId" id="courseId"/>
		<form:hidden path="back" id="back"/>
		
		
		</form:form>
		<script>
		var type='<c:out value='${sessionScope.trainingMstType}'/>';
			
		jsonMasterDataList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingMstDataList") %>;
		jsonFinancialList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonFinancialList") %>;	
			
			
		</script>
	</body>
</html>
<!-- End:CourseAttendedDetails.jsp -->