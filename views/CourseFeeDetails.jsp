<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:CourseDuration.jsp -->
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


<title>Course Qualification</title>
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
								<div class="headTitle">Course Fee Details</div>
								<%-- Content Page starts --%>
								<div>
									
									<div class="line">
									  
										<div   class="quarter bold leftmar">Category</div>
										<div class="quarter">
											${categoryName}
										</div>
										
										
										<div   class="quarter bold leftmar">Training Type</div>
										<div class="quarter">
											${trainingType}										
										</div>
										
									</div>
									
									
									<div class="line">
									<div   class="quarter bold leftmar">Training Inistitute</div>
										<div class="quarter">
											${trainingInistitute}	
										</div>
									
										<div class="quarter bold leftmar">Subject Category</div>
										<div class="quarter">
											${courseSubjCategory}	
										</div>
										
									</div>
																	
									<div class="line">
										<div class="quarter bold leftmar">Subject SubCategory</div>
										<div class="quarter">
											${courseSubjSubCategory}
										</div>
										
										<div  class="quarter bold leftmar">Year</div>
										<div class="quarter">
										${courseYear}
										</div>
									</div>
									<div class="line">
																	
										<div  class="quarter bold leftmar">Course Name</div>
										
										<div class="quarter">
											${name}
										</div>
									</div>
									<div class="line">
										<div  class="quarter bold leftmar">Fee </div>
										<div class="quarter">
											<form:input path="fee" id="fee" maxlength="50" onkeypress="javascript:increaseTextWidth('fee')"/>
										
										</div>
										
										<div  class="quarter bold leftmar">Service Tax</div>
										<div class="quarter">
											<form:input path="fee" id="fee" maxlength="50" onkeypress="javascript:increaseTextWidth('fee')"/>
										
										</div>
										
									</div>
									<div class="line">
										<div  class="quarter bold leftmar">Acamidation </div>
										<div class="quarter">
											<input type="checkbox" value="0" class="row" name="headOfficeFlag" id="headOfficeFlag" />
										</div>
										
										
									</div>
									<div class="line">
										<div  class="quarter bold leftmar">Single </div>
										<div class="quarter">
											<form:input path="fee" id="fee" maxlength="50" onkeypress="javascript:increaseTextWidth('fee')"/>
										</div>
										
										<div  class="quarter bold leftmar">Double</div>
										<div class="quarter">
											<form:input path="fee" id="fee" maxlength="50" onkeypress="javascript:increaseTextWidth('fee')"/>
										
										</div>
										
									</div>
									
									
									<div class="line">
										<div style="margin-left:25%">
											<a href="javascript:courseQualificationSubmit('${sessionScope.trainingMstType}');"><div class="appbutton">Submit</div></a>
										</div>
											<a href="javascript:clearCourse();"><div class="appbutton">Clear</div></a>
											
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
						
			jsonQualificationList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonQualificationList") %>;
			jsonSelectedQualificationList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonSelectedQualificationList") %>;
			
			
			
		</script>
	</body>
</html>
<!-- End:CourseDuration.jsp-->