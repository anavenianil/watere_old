<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:CourseMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
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

<title>Course Master</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	
	
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
								<div class="headTitle">Training Circulation</div>
								<%-- Content Page starts --%>
								<div>
									<div class="line">
										<div   class="quarter bold leftmar">Category<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="categoryId" id="categoryId" cssClass="formSelect" onchange="javascript:getTrainingInistitute(jsonTrainingInistituteList);getCourseSubjCategory(jsonCourseSubjCategoryList,jsonCourseSubjSubCategoryList);getDesignation(jsonDesignationList);">
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.CategoryList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
										<div   class="quarter bold leftmar">Training Type<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="trainingTypeId" id="trainingTypeId" cssClass="formSelect" onchange="javascript:getTrainingInistitute(jsonTrainingInistituteList);">
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.trainingTypeList}" itemValue="id" itemLabel="name"/>
											</form:select>											
										</div>
									</div>
									<div class="line">
									<div   class="quarter bold leftmar">Training Inistitute<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="trainingInistituteId" id="trainingInistituteId" cssClass="formSelect"  onmouseover="setSelectWidth('#trainingInistituteId')" >
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.trainingInistituteList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									
										<div class="quarter bold leftmar">Subject Category</div>
										<div class="quarter">
											<form:select path="courseSubjCategoryId" id="courseSubjCategoryId" cssClass="formSelect"  onmouseover="setSelectWidth('#courseSubjCategoryId')" onchange="javascript:getCourseSubjSubCategory(jsonCourseSubjSubCategoryList);">
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.courseSubjCategoryList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
										
									</div>
																	
									<div class="line">
										<div class="quarter bold leftmar">Subject SubCategory</div>
										<div class="quarter">
											<form:select path="courseSubjSubCategoryId" id="courseSubjSubCategoryId" cssClass="formSelect"  onmouseover="setSelectWidth('#courseSubjSubCategoryId')">
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.courseSubjSubCategoryList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
										<div class="quarter bold leftmar">Year</div>
										<div class="quarter">
											<form:input path="courseYear" id="courseYear" cssClass="dateClass" readonly="true"/>
				
			    									 <img  src="./images/calendar.gif"   id="date_start_trigger1" />	
													<script type="text/javascript">
			           																Calendar.setup({inputField :"courseYear",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
													</script> 
										</div>
										
										
									</div>
									<div class="line">
									<div  class="quarter bold leftmar">Course Name<span class="failure">*</span></div>
										
										<div class="quarter"><form:select path="courseId" id="courseId" cssClass="formSelect" >
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.courseSubjSubCategoryList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
										<div  class="quarter bold leftmar">Course Duration<span class="failure">*</span></div>
										
										<div class="quarter"><form:select path="courseId" id="courseId" cssClass="formSelect" >
												<option value="">Select</option>
												<option value="">3-Jan-2006 to 6-Jan-2006</option>
												<option value="">4-Jun-2006 to 7-Jun-2006</option>
												<option value="">8-Aug-2006 to 11-Aug-2006</option>
											</form:select>
										</div>
																
									</div>
									<div class="line">
										<div class="quarter bold leftmar">Venue Details</div>
										<div class="quarter">
										<form:select path="courseId" id="courseId" cssClass="formSelect" >
												<form:option value="">Select</form:option>
												<form:options items="${CityTypeMasterList}" itemValue="id" itemLabel="cityName"/>
												
											</form:select>
											</div>
										
										
									</div>
									<div class="line">
										<div class="quarter bold leftmar">Nomination Start Date</div>
										<div class="quarter">
											<form:input path="serviceTax" id="serviceTax" cssClass="dateClass" readonly="true"/>
				
			    									 <img  src="./images/calendar.gif"   id="date_start_trigger2" />	
													<script type="text/javascript">
			           																Calendar.setup({inputField :"serviceTax",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
													</script> 
										</div>
										<div class="quarter bold leftmar">Nomination Last Date</div>
										<div class="quarter">
											<form:input path="fee" id="fee" cssClass="dateClass" readonly="true"/>
				
			    									 <img  src="./images/calendar.gif"   id="date_start_trigger3" />	
													<script type="text/javascript">
			           																Calendar.setup({inputField :"fee",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
													</script> 
										</div>
									</div>
									<div class="line">
										<div  class="quarter bold leftmar">Department<span class="failure">*</span></div>
										
										<div class="quarter"><form:select path="courseId" id="courseId" cssClass="formSelect" >
												<option value="">HRDG</option>
												
											</form:select>
										</div>
										<div  class="quarter bold leftmar">Organizer</div>
										<div class="quarter">
											<form:input path="fee" id="fee" maxlength="50" onkeypress="javascript:increaseTextWidth('fee')"/>
										
										</div>
										
									</div>
									<div class="line">
										<div  class="quarter bold leftmar">Brochure<span class="failure">*</span></div>
										
										<div class="quarter"><form:input path="fax" type="file" id="files3"/>
										</div>
										
										
									</div>
									
									<div class="line">
										<div  class="quarter bold leftmar">Departments to Circulate the brochure</div>
										
									</div>
									<div class="line">
											<div class="leftmar">
												<div style="float: left; width : 35%">
														<form:select path="designation" id="designation" size="10" multiple="true" cssStyle="width:300px">
															<form:options items="${sessionScope.departmentTypeList}" itemValue="id" itemLabel="deptName"/>
														</form:select>
													</div>
												<div style="float: left; width : 10%; margin-top: 60px;">									
													<div style="margin-bottom: 5px;" align="center">
													     <input style="margin-bottom: 5px" id="MoveRight2" type="button" value=" Add " />
	     											     <input id="MoveLeft2" type="button" value=" Remove " />    											        
	     											</div>		      																				
		      									</div>
												<div style="float: left; width : 30%">
														<form:select path="designation" id="SelectRight2" size="10" multiple="true" cssStyle="width:300px">
														</form:select>
													</div>
											</div>
									</div>
									
									
									
									
									<div class="line">
										<div style="margin-left:25%">
											<a href="javascript:getCourseFee('${sessionScope.trainingMstType}');"><div class="appbutton">Fee Details</div></a>
											<a href="javascript:courseSubmit('${sessionScope.trainingMstType}');"><div class="appbutton">Submit</div></a>
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
		<form:hidden path="categoryName" id="category"/>
		<form:hidden path="trainingType" id="trainingType"/>
		<form:hidden path="trainingInistitute" id="trainingInistitute"/>
		<form:hidden path="courseSubjCategory" id="courseSubjCategory"/>
		<form:hidden path="courseSubjSubCategory" id="courseSubjSubCategory"/>
		
		
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
<!-- End:CourseMaster.jsp -->

