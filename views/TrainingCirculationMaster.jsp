<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TrainingCirculationMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/ajaxUpload.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/trainingscript.js"></script>
<script type="text/javascript" language="javascript" src="./script/calendar.js"></script>
<script type="text/javascript" language="javascript" src="./script/calendar-en.js"></script>
<script type="text/javascript" language="javascript" src="./script/calendar-setup_3.js"></script>
<script type="text/javascript" language="javascript" src="./script/date.js"></script>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>


<title>Training Circulation</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	javascript:clearTrainingCirculation();
	
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
									<div class="line" id="result">
										
									</div>
									<div class="line">
									<div  class="quarter leftmar_withoutbold ">Course Name<span class="failure">*</span></div>
										
										<div class="quarter bold" id="course">
											${trainingMaster.course}
										</div>
													
									</div>
															
									<div class="line">
										<div class="quarter leftmar_withoutbold ">Course Duration Start Date<span class="failure">*</span></div>
										<div class="quarter bold" id="durationStartDate">
											${trainingMaster.durationStartDate}
										</div>
										<div class="quarter leftmar_withoutbold ">Course Duration End Date<span class="failure">*</span></div>
										<div class="quarter bold">
											${trainingMaster.durationEndDate}
										</div>
										
										
									</div>
									<div class="line">
										<div class="quarter bold leftmar">Serial Number<span class="failure">*</span></div>
										<div class="quarter">
										<form:select path="letterFormatId" id="letterFormatId" cssClass="formSelect" onchange="javascript:getCirculationIonList();" onmouseover="setSelectWidth('#letterFormatId');">
												<form:option value="">Select</form:option>
												<form:options items="${seriesMstList}" itemValue="id" itemLabel="serialNum"/>
												
											</form:select>
										</div>
									
									</div>
									<div class="line">
										<div class="quarter bold leftmar">Letter Number<span class="failure">*</span></div>
										<div class="quarter" >
											<form:select path="ionId" id="ionId" cssClass="formSelect" onchange="javascript:getCirculationStartDate(jsonIonMstList);" onmouseover="setSelectWidth('#ionId');">
												<form:option value="">Select</form:option>
												<form:options items="${ionMstList}" itemValue="id" itemLabel="letterNumber"/>
												
											</form:select>
										</div>
									</div>
									<div class="line">
									<div class="quarter bold leftmar">Circulation Start Date<span class="failure">*</span></div> 
										<div class="quarter">
											<form:input path="circulationDate" id="circulationDate" cssClass="dateClass" readonly="true"/>&nbsp;
				
			    									 <img  src="./images/calendar.gif"   id="date_start_trigger1" />	
													<script type="text/javascript">
			           																Calendar.setup({inputField :"circulationDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
													</script> 
										</div>
									</div>
									
									<div class="line">
										<div class="quarter bold leftmar">Nomination Start Date<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="nominationStartDate" id="nominationStartDate" cssClass="dateClass" readonly="true"/>&nbsp;
				
			    									 <img  src="./images/calendar.gif"   id="date_start_trigger2" />	
													<script type="text/javascript">
			           																Calendar.setup({inputField :"nominationStartDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
													</script> 
										</div>
										<div class="quarter bold leftmar">Nomination Last Date<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="nominationEndDate" id="nominationEndDate" cssClass="dateClass" readonly="true"/>&nbsp;
				
			    									 <img  src="./images/calendar.gif"   id="date_start_trigger3" />	
													<script type="text/javascript">
			           																Calendar.setup({inputField :"nominationEndDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
													</script> 
										</div>
									</div>
									<div class="line">
										
										<div  class="quarter bold leftmar">Organizer</div>
										<div class="threeFourth"><form:textarea path="organizer" id="organizer" cols="78" rows="3" onkeypress="textCounter(event,$jq('#organizer'),$jq('#organizerCounter'),2000);"
															 onkeyup="textCounter(event,$jq('#organizer'),$jq('#organizerCounter'),2000);"></form:textarea>
													<input type="text" class="counter" name="organizerCounter" value="2000" id="organizerCounter" disabled=""/>
											
										</div>
										
										
									</div>
									<div class="line">
										
										<div class="quarter bold leftmar">Venue Details<span class="failure">*</span></div>
										<div class="quarter">
										<form:select path="venueId" id="venueId" cssClass="formSelect" onmouseover="setSelectWidth('#venueId');">
												<form:option value="">Select</form:option>
												<form:options items="${CityTypeMasterList}" itemValue="id" itemLabel="name"/>
												
											</form:select>
											</div>
										
									</div>
									<div class="line" id="newBrochure">
										<div  class="quarter bold leftmar">Brochure<span class="failure">*</span></div>
										
										<div class="quarter"><form:input path="brochureFile" type="file" id="files"/>
										</div>
										
										
										
									</div>
									
									<div class="line" id="brochureDisabled">
										<div class="quarter bold leftmar">Uploaded Brochure </div>
										<div  class="quarter "><a class="expbutton" href="javascript:getTrainingDetails('${trainingMaster.brochure}');"><span>${trainingMaster.brochureName}</span></a></div>
										<div class="quarter bold leftmar">  <a class="expbutton" href="javascript:cancelBrochure();"><span class="failure">&nbsp;&nbsp;&nbsp;&nbsp;            Remove</span></a></div>
									</div>
									
									<div class="line">
										<div  class="quarter bold leftmar">Departments to Circulate the brochure<span class="failure">*</span></div>
										
									</div>
									<div class="line">
											<div class="leftmar">
												<div style="float: left; width : 35%">
														<form:select path="departments" id="SelectLeft2" size="10" multiple="true" cssStyle="width:300px">
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
														<form:select path="department" id="SelectRight2" size="10" multiple="true" cssStyle="width:300px">
														<form:options items="${sessionScope.selectedDepartmentList}" itemValue="id" itemLabel="deptName"/>
														</form:select>
													</div>
											</div>
									</div>
									
									
									
									
									<div class="line">
										<div style="margin-left:25%">
											
											<a href="javascript:trainingCirculationSubmit();"><div class="appbutton">Submit</div></a>
										</div>
										
											<a href="javascript:getCourseCirculationBasedOnCourseDuration($jq('#durationId').val());"><div class="appbutton">Clear</div></a>
											
											<div class="expbutton"><a href="javascript:goBackToTrainingCirculationDetails();"><span>Go To Circulation</span></a></div>
										
									</div>
																		
									<div class="line height"></div>
									<div class="line" id="displayTable">
										
									</div>
									<div class="line height" id="result2"></div>
									
									
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
		
			<script>
			jsonMasterDataList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingMstDataList") %>;
			jsonDepartmentList = <%=(net.sf.json.JSONArray)session.getAttribute("jsonDepartmentList") %>;
			jsonIonMstList = <%=(net.sf.json.JSONArray)session.getAttribute("jsonIonMstList") %>;
						
		</script>
				
			</div>
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type" />
		<form:hidden path="id" id="id"/>
		<form:hidden path="brochureName" id="brochureName"/>
		<form:hidden path="durationId" id="durationId"/>
		<form:hidden path="courseId" id="courseId"/>
		<form:hidden path="startDate" id="startDate"/>
		<form:hidden path="endDate" id="endDate"/>
		<form:hidden path="back" id="back"/>
		<form:hidden path="brochure" id="brochure"/>
		
		
		
		</form:form>
		<script>
			
				
		</script>
	</body>
</html>
<!-- End:TrainingCirculationMaster.jsp -->

