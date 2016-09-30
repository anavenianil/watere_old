<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TrainingInistituteMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/trainingscript.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<title>Training Institute</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	
		if($jq('#back').val()=='trainingInistitute')
		clearTrainingInistitutesMaster(type,'${trainingMaster.trainingTypeId}','${trainingMaster.trainingRegionId}',jsonTrainingRegionList);
		else
		  clearTrainingInistituteMaster(type);
		});
</script>
<style>
.highlight {
font-style: italic;
background-color: #0f0;
}
</style>
</head>

<body>
	<form:form method="post" commandName="trainingMaster" name="trainingMaster" id="trainingMaster">
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
								<div class="headTitle" id="headTitle">Training Institute</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								<div>
										<div class="line">
											
												<div class="quarter bold" style="margin-left:8px;">Training Type<span class='failure'>*</span></div>
												<div class="quarter">
													<form:select path="trainingTypeId" id="trainingTypeId" cssStyle="width:145px;" onchange="javascript:getTrainingRegionList(jsonTrainingRegionList);javascript:getTrainingInistituteList();">
														<form:option value="">Select</form:option>
														<form:options items="${trainingTypeList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
																							
										</div>
										<div class="line">
											
												<div class="quarter bold" style="margin-left:8px;">Region Name<span class='failure'>*</span></div>
												<div class="quarter">
													<form:select path="trainingRegionId" id="trainingRegionId" cssStyle="width:145px;" onchange="javascript:getTrainingInistituteList();">
														<form:option value="">Select</form:option>
														<form:options items="${trainingRegionList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
												
											
										</div>
										<div class="line">
										<div class="quarter bold" style="margin-left:8px;" id="labelType">Training Institute<span class='failure'>*</span></div>
												<div class="quarter">
													
													<div class="quarter"><form:textarea path="typeValue" id="typeValue" cols="20" rows="3" onkeypress="textCounter(event,$jq('#name'),$jq('#nameCounter'),200);"
															 onkeyup="textCounter(event,$jq('#name'),$jq('#nameCounter'),200);"></form:textarea>
													<input type="text" class="counter" name="nameCounter" value="200" id="nameCounter" disabled=""/>
												</div>
								  		 </div>
								  		 </div>
										<div class="line">
												<div class="quarter bold" style="margin-left:8px;">Short Name</div>
												<div class="quarter">
													<form:input path="shortName" id="shortName" maxlength="50" />
												</div>
							    		</div>	
									
								   
									
									
									<div class="line">
										<div style="margin-left:25%">
											<a href="javascript:trainingMasterSubmit('${sessionScope.trainingMstType}');"><div class="appbutton">Submit</div></a>
										</div>
											<a href="javascript:clearTrainingInistituteMaster('${sessionScope.trainingMstType}');"><div class="appbutton">Clear</div></a>
											
											
									</div>
									<div class="line height"><hr/></div>
									<div id="result" class="line">
										<jsp:include page="TrainingMasterDataList.jsp" />
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
		<form:hidden path="name" id="name"/>
		<form:hidden path="trainingInistituteId" id="trainingInistituteId"/>
		<form:hidden path="trainingInistitute" id="trainingInistitute"/>
		<form:hidden path="trainingType" id="trainingType"/>
		<form:hidden path="trainingRegion" id="trainingRegion"/>
		<form:hidden path="back" id="back"/>
		
		
		</form:form>
		<script type="text/javascript">
			var type='<c:out value='${sessionScope.trainingMstType}'/>';
						jsonTrainingRegionList=<%=(net.sf.json.JSONArray)session.getAttribute("jsonTrainingRegionList") %>;
			
		</script>
		
		
		
		

	</body>
</html>
<!-- End:TrainingInistituteMaster.jsp  -->