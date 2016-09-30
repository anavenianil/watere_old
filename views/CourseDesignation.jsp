<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:CourseDesignation.jsp-->
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


<title>Course Designation</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	clearDesignation();	
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
								<div class="headTitle">Course Designation</div>
								<%-- Content Page starts --%>
								<div>
									
									<div class="line">
										<div   class="quarter leftmar_withoutbold">Year<span class="failure">*</span></div>
										<div class="quarter bold">
											${trainingMaster.year}
										</div>
										<div   class="quarter leftmar_withoutbold">Training Type<span class="failure">*</span></div>
										<div class="quarter bold">
												${trainingMaster.trainingType}									
										</div>
									</div>
									<div class="line">
									<div   class="quarter leftmar_withoutbold">Region<span class="failure">*</span></div>
										<div class="quarter bold">
											${trainingMaster.trainingRegion}			
										</div>
									
										<div class="quarter leftmar_withoutbold">Training Institute<span class="failure">*</span></div>
										<div class="quarter bold">
											${trainingMaster.trainingInistitute}
										</div>
										
									</div>
																	
									
									<div class="line">
																	
										<div  class="quarter leftmar_withoutbold">Course Name<span class="failure">*</span></div>
									
										<div class="quarter bold ">
											${trainingMaster.course}
										</div>
									</div>
									<div class="line">
																	
										<div  class="quarter bold leftmar">Category<span class="failure">*</span></div>
										<div class="quarter">
														<form:select path="categoryId" id="categoryId" cssClass="formSelect" onchange="javascript:getDesignationsBasedOnCategory();" onmouseover="setSelectWidth('#trainingTypeId')">
														<form:option value="">Select</form:option>
														<form:options items="${sessionScope.CategoryList}" itemValue="id" itemLabel="name"/>
														</form:select>
										</div>
									</div>
									<div class="line">
									<div  class="quarter bold leftmar">Designations<span class="failure">*</span></div>
										
									</div>
									<div class="line">
											<div class="leftmar">
												<div style="float: left; width : 35%">
														<form:select path="designations" id="SelectLeft2" size="10" multiple="true" cssStyle="width:300px">
															
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
									<div class="line" id="menuLinksList">
										
									</div>
									
									<div class="line">
										<div style="margin-left:25%">
											<a href="javascript:courseDesignationSubmit('${sessionScope.trainingMstType}');"><div class="appbutton">Submit</div></a>
										</div>
											<div class="expbutton"><a href="javascript:goBackToCourse();"><span>Go To Course</span></a></div>
									</div>
									
									<div class="line height"></hr></div>
									<div class="line" id="result">
										<jsp:include page="DesignationsDataList.jsp" />
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
		<form:hidden path="courseId" id="courseId"/>
		<form:hidden path="trainingTypeId" id="trainingTypeId"/>
		<form:hidden path="trainingRegionId" id="trainingRegionId"/>
		<form:hidden path="courseYear" id="courseYear"/>
		<form:hidden path="trainingInistituteId" id="trainingInistituteId"/>
		<form:hidden path="back" id="back"/>
		<form:hidden path="count" id="count"/>
		
		
		</form:form>
		<script>
				
			var type='<c:out value="${sessionScope.trainingMstType}"/>';
		
			
		</script>
	</body>
</html>
<!-- End:CourseDesignation.jsp-->