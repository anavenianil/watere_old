<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:IONDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />

<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>
<script type="text/javascript" src="./script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/ajaxUpload.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/letternumberformat.js"></script>
<script language="javascript" src="./script/date.js"></script>


<title>I.O.N Details</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	javascript:clearIONDetails();
	
		});
</script>
</head>

<body>
	<form:form method="post" commandName="letterNumberFormat" id="letterNumberFormat" name="letterNumberFormat">
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
								<div class="headTitle">Search & Edit I.O.N</div>
								<%-- Content Page starts --%>
								<div>
								<div class="line">
										
										<div   class="quarter bold leftmar">Department Name<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="deptNum" id="deptNum" cssClass="formSelect" onchange="getLetterNumberFormatList('deptChanged');">
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.deptList}" itemValue="id" itemLabel="deptName"/>
											</form:select>											
										</div>
										<div   class="quarter bold leftmar">Serial Number<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="letterFormatId" id="letterFormatId" cssClass="formSelect" onmouseover="setSelectWidth('#letterFormatId')" >
												<form:option value="">Select</form:option>
												
											</form:select>											
										</div>	
										
										
									</div>
												
									
															
									<div class="line">
										<div class="quarter bold leftmar">Dates </div>
										<div class="quarter">
											<form:input path="startDate" id="startDate" cssClass="dateClass" readonly="true" />
				
			    									<script type="text/javascript">
											new tcal({'formname':'letterNumberFormat','controlname':'startDate'});
												
											</script>
										</div>
										<div class="quarter bold leftmar"> between </div>
										<div class="quarter">
											<form:input path="endDate" id="endDate" cssClass="dateClass" readonly="true" />
				
			    									 <script type="text/javascript">
											new tcal({'formname':'letterNumberFormat','controlname':'endDate'});
												
											</script> 	
										</div>
										
										
									</div>
									<div class="line">
										<div class="quarter bold leftmar">or </div>
									
										
									</div>
									<div class="line">
										<div class="quarter bold leftmar">Subject </div>
										<div class="quarter">
											<form:input path="ionSubjectSearch" id="ionSubjectSearch" />
							    								
										</div>
										
										
										
									</div>
									<div class="line">
										
										<div   class="quarter bold leftmar">I.O.N <span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="selectStatus" id="selectStatus" cssClass="formSelect" >
															<form:option value="0">Draft</form:option>
															<form:option value="1">Circulated</form:option>
															<form:option value="2">Discarded</form:option>
											</form:select>										
										</div>
																				
									</div>
									<div class="line" id="result1">
										
																				
									</div>
									
									
									
									
									
									
									<div class="line">
										<div style="margin-left:25%">
											
											<a href="javascript:getIONCirculationDetails();"><div class="appbutton">Search</div></a>
										</div>
										
											<a href="javascript:clearIONDetails();"><div class="appbutton">Clear</div></a>
											
									</div>
									
									
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="IONDetailsDataList.jsp"/>
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
		<form:hidden path="back" id="back"/>
		<form:hidden path="tempLetterFormatId" id="tempLetterFormatId"/>
		<form:hidden path="ionScreenType" id="ionScreenType"/>
		
		
		
		</form:form>
		<script>
			
			
		</script>
	</body>
</html>
<!-- End:TrainingCirculationDetails.jsp-->

