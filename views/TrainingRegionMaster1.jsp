<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TrainingRegionMaster.jsp -->
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


<title>Region Master</title>
<script type="text/javascript">
	$jq(document).ready(function(){
		clearTrainingRegion();
	
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
								<div class="headTitle">Region Master</div>
								<%-- Content Page starts --%>
								<div>
									
									<div class="line">
									   
										<div   class="quarter bold leftmar">Training Type<span class='failure'>*</span></div>
										<div class="quarter">
											${trainingMaster.trainingType}
										</div>
										
										
									</div>
									
									<div class="line">
										<div   class="quarter bold leftmar">Region Name<span class='failure'>*</span></div>
										<div class="quarter">
											<form:input path="name" id="name" maxlength="50" onkeypress="javascript:increaseTextWidth('name')"/>									
										</div>
									</div>
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">Description</div>
										<div class="quarter"><form:textarea path="description" id="description" cols="20" rows="3" onkeypress="textCounter(event,$jq('#description'),$jq('#counter'),500);"
															 onkeyup="textCounter(event,$jq('#description'),$jq('#counter'),500);"></form:textarea>
													<input type="text" class="counter" name="counter" value="500" id="counter" disabled=""/>
									</div>
									
									
									<div class="line">
										<div style="margin-left:25%">
											<a href="javascript:trainingRegionSubmit('${sessionScope.trainingMstType}');"><div class="appbutton">Submit</div></a>
										</div>
											<a href="javascript:clearTrainingRegion();"><div class="appbutton">Clear</div></a>
											<a href="javascript:trainingRegionReport();"><div class="appbutton">Report</div></a>
											<a href="javascript:goBack();"><div class="appbutton">Back</div></a>
											
									</div>
									
									
									
									
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="TrainingRegionDataList.jsp"></jsp:include>
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
		<form:hidden path="trainingTypeId" id="trainingTypeId"/>
		
		</form:form>
		<script>
						
			
		
		</script>
	</body>
</html>
<!-- End:TrainingRegionMaster.jsp-->