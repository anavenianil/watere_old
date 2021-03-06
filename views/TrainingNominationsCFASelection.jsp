<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TrainingNominationsCFASelection.jsp -->
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
<script type="text/javascript" src="script/script.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script language="javascript" src="./script/date.js"></script>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/RegExpValidate.js"></script>

<title>CFA Approval</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	clearCourse();
	
	javascript:multiSelectBox();
		});
</script>
</head>

<body>
	<form:form method="post" commandName="trainingRequest" id="trainingRequest">
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
								<div class="headTitle">CFA Approval</div>
								<%-- Content Page starts --%>
								<div>
									
									
																	
									<div class="line">
										
										<div  class="quarter bold leftmar">Course Name<span class="failure">*</span></div>
										<div ss class="quarter">
											<form:select path="courseId" id="courseId" cssClass="formSelect" onchange="javascript:getCFANominationList();" onmouseover="setSelectWidth('#courseId')" >
															<form:option value="">Select</form:option>
															<form:options items="${sessionScope.trainingRequestCFACourseList}" itemValue="id" itemLabel="name"/>
													</form:select>
										
										</div>
									
										
										
									</div>
									<div class="line">
												<div   class="quarter bold leftmar">Approved/Rejected<span class="failure">*</span></div>
												<div class="quarter">
													<form:select path="selectStatus" id="selectStatus" cssClass="formSelect" >
															<form:option value="39">Approved</form:option>
															<form:option value="38">Rejected</form:option>
															
													</form:select>
												</div>
												</div> 
									
									<div class="line">
										<div style="margin-left:25%">
										
											<a href="javascript:saveCFANominations('CFASelection');"><div class="appbutton">Submit</div></a>
										</div>
										
											<a href="javascript:clearCourse();"><div class="appbutton">Clear</div></a>
											
										
											
									</div>
									
									
									<div class="line height"><hr/></div>
									<div class="line" id="CFASelection">
										<jsp:include page="TrainingNominationsCFASelectionList.jsp"></jsp:include>
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
		<form:hidden path="requestId" id="requestId"/>
		<form:hidden path="historyID" id="historyID"/>
		<form:hidden path="statusMsg" id="statusMsg"/>
		<form:hidden path="back" id="back"/>
		<form:hidden path="message" id="message"/>
		
		
		
		
		</form:form>
		
	</body>
</html>
<!-- End:TrainingNominationsCFASelection.jsp -->