<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TrainingCirculationDetails.jsp -->
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
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script language="javascript" src="./script/date.js"></script>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>

<title>Training Circulation</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	javascript:clearTrainingCirculationDetails();
	
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
										<div class="quarter bold leftmar">Course Duration Start Date</div>
										<div class="quarter">
											<form:input path="startDate" id="startDate" cssClass="dateClass" readonly="true" />&nbsp;
				
			    									 <img  src="./images/calendar.gif"   id="date_start_trigger4" />	
													<script type="text/javascript">
			           																Calendar.setup({inputField :"startDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
													</script> 
										</div>
										<div class="quarter bold leftmar">Course Duration End Date</div>&nbsp;
										<div class="quarter">
											<form:input path="endDate" id="endDate" cssClass="dateClass" readonly="true" />&nbsp;
				
			    									 <img  src="./images/calendar.gif"   id="date_start_trigger1" />	
													<script type="text/javascript">
			           																Calendar.setup({inputField :"endDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
													</script> 
										</div>
										
										
									</div>
									
									
									
									
									
									
									
									<div class="line">
										<div style="margin-left:25%">
											
											<a href="javascript:getTrainingCirculationDetails();"><div class="appbutton">Submit</div></a>
										</div>
										
											<a href="javascript:clearTrainingCirculationDetails();"><div class="appbutton">Clear</div></a>
											<a href="javascript:reportCirculationDetails();"><div class="appbutton">Report</div></a>
											
											
											
									</div>
									
									
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="TrainingCirculationDetailsDataList.jsp"/>
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
		
			<script>
			
		</script>
				
			</div>
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type" />
		<form:hidden path="id" id="id"/>
		<form:hidden path="durationId" id="durationId"/>
		<form:hidden path="back" id="back"/>
		
		
		
		
		</form:form>
		<script>
			
				
		</script>
	</body>
</html>
<!-- End:TrainingCirculationDetails.jsp-->

