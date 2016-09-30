<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:CourseMaster.jsp -->
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
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script language="javascript" src="./script/date.js"></script>

<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>


<title>Create Course</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	if($jq('#back').val()=='course')
		clearCourse(type,'${trainingMaster.trainingTypeId}','${trainingMaster.trainingRegionId}','${trainingMaster.trainingInistituteId}','${trainingMaster.courseYear}');
		else
		  clearCourses(type);
	javascript:multiSelectBox();
		});
</script>
</head>

<body>
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
								<div class="headTitle">Course Master</div>
								<%-- Content Page starts --%>
								<div>
									<div class="line" id="result">
										<jsp:include page="CourseResult.jsp" />
									</div>
									
									<div class="line">
										<div   class="quarter bold leftmar">Training Type<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="trainingTypeId" id="trainingTypeId" cssClass="formSelect" onchange="javascript:getTrainingRegionList(jsonTrainingRegionList);getTrainingInistitute(jsonTrainingInistituteList);getCoursesList();" >
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.trainingTypeList}" itemValue="id" itemLabel="name"/>
											</form:select>											
										</div>
										<div   class="quarter bold leftmar">Region<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="trainingRegionId" id="trainingRegionId" cssClass="formSelect"  onchange="javascript:getTrainingInistitute(jsonTrainingInistituteList);getCoursesList();" onmouseover="setSelectWidth('#trainingRegionId')">
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.trainingRegionList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div   class="quarter bold leftmar">Training Institute<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="trainingInistituteId" id="trainingInistituteId" cssClass="formSelect"  onmouseover="setSelectWidth('#trainingInistituteId');" onchange="javascript:getCoursesList();" >
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.trainingInistituteList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
									<div class="quarter bold leftmar">Year<span class="mandatory">*</span></div>
										<div class="quarter">
											
												<form:select path="courseYear" id="courseYear" cssStyle="width:145px;" onchange="javascript:getCoursesList();">
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.FinancialYearList}" itemLabel="financialYear" itemValue="id"></form:options>
												</form:select>
											
										</div>
									</div>
									
																	
									<div class="line">
										<div class="quarter bold leftmar">Subject Category</div>
										<div class="quarter">
													<div class="quarter"><form:textarea path="courseSubjCategory" id="courseSubjCategory" cols="20" rows="3" onkeypress="textCounter(event,$jq('#courseSubjCategory'),$jq('#subjcounter'),200);"
															 onkeyup="textCounter(event,$jq('#courseSubjCategory'),$jq('#subjsubcounter'),200);"></form:textarea>
													<input type="text" class="counter" name="subjcounter" value="200" id="subjcounter" disabled=""/>
													</div>
										</div>			
										<div class="quarter bold leftmar">Subject SubCategory</div>
										<div class="quarter">
											<div class="quarter"><form:textarea path="courseSubjSubCategory" id="courseSubjSubCategory" cols="20" rows="3" onkeypress="textCounter(event,$jq('#courseSubjSubCategory'),$jq('#subjsubcounter'),200);"
															 onkeyup="textCounter(event,$jq('#courseSubjSubCategory'),$jq('#subjsubcounter'),200);"></form:textarea>
													<input type="text" class="counter" name="subjsubcounter" value="200" id="subjsubcounter" disabled=""/>
													</div>
										</div>
								   </div>
								   <div class="line">
										
										<div  class="quarter bold leftmar">Course Name<span class="failure">*</span></div>
										
										<div class="threeFourth"><form:textarea path="name" id="name" cols="78" rows="3" onkeypress="textCounter(event,$jq('#name'),$jq('#nameCounter'),500);"
															 onkeyup="textCounter(event,$jq('#name'),$jq('#nameCounter'),500);"></form:textarea>
													<input type="text" class="counter" name="nameCounter" value="500" id="nameCounter" disabled=""/>
											
										</div>
									</div>
									
									<div class="line">
										<div  class="quarter bold leftmar">Amount of Fee if any</div>
										<div ss class="quarter">
											<form:input path="fee" id="fee" maxlength="50" onkeypress="javascript:increaseTextWidth('fee');return isNumberExp(event);"/>
										</div>
										<div  class="quarter bold leftmar">Service Tax</div>
										<div ss class="quarter">
											<form:input path="serviceTax" id="serviceTax" maxlength="50" onkeypress="javascript:increaseTextWidth('serviceTax');return isNumberExp(event);"/> %
										</div>
									</div>
									
									
									
									
									<div class="line">
										<div style="margin-left:25%">
										
											<a href="javascript:courseSubmit('${sessionScope.trainingMstType}');"><div class="appbutton">Submit</div></a>
										</div>
										
											<a href="javascript:clearCourses('${sessionScope.trainingMstType}');"><div class="appbutton">Clear</div></a>
											<a href="javascript:getReport('${sessionScope.trainingMstType}');"><div class="appbutton">Report</div></a>
											
											
									</div>
									
									
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="CourseMasterDataList.jsp"></jsp:include>
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
			jsonTrainingRegionList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingRegionList") %>;
			jsonTrainingInistituteList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingInistituteList") %>;
			jsonMasterDataList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingMstDataList") %>;
			
			
			
		</script>
	</body>
</html>
<!-- End:CourseMaster.jsp -->