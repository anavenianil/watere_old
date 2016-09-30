<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TutionFeeLimitMaster.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/tutionFee.js"></script>
<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<title>Tuition Fee Limit Master</title>
</head>

<body onload="javascript:clearTuitionFeeLimitMaster();">
	<form:form method="post" commandName="tutionFee">
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
								<div class="headTitle">Tuition Fee Limit Master</div>
								<div>
									<%-- Content Page starts --%>
								<div class="line">
								<div class="quarter bold">Academic Type<span style="color:red">*</span></div>
								<form:select path="academicType" id="academicType" cssStyle="width:145px;">
						     	<form:option value="select">Select</form:option>
						     	<form:option value="State">State</form:option>
						     	 <form:option value="Central">Central</form:option>
							    </form:select>
								</div>
									<div class="line">
										<div class="quarter bold">Type<span class="failure">*</span></div>
										<div class="half">
												<form:radiobutton path="type" id="type1" value="Quartarly"  label="Quarterly" onclick="javascript:quartarlyDetails(); "/>	
												<form:radiobutton path="type" id="type6" value="Halfyearly"  label="Halfyearly"  onclick="javascript:quartarlyDetails();"/>
												<form:radiobutton path="type" id="type9" value="Annually"  label="Annualy" onclick="javascript:quartarlyDetails();"/>
										
										</div>
								    </div>
								    <div class="line" id="quartarlyDetailsDiv" style="display:none">
								    <div class="line" style="margin-left:25%">
								         <form:radiobutton path="typeQuartarly" id="type2" value="Q1"  label="Q1"/>
                                         <form:radiobutton path="typeQuartarly" id="type3" value="Q2"  label="Q2"/>
                                         <form:radiobutton path="typeQuartarly" id="type4" value="Q3"  label="Q3"/>
                                         <form:radiobutton path="typeQuartarly" id="type5" value="Q4"  label="Q4"/>
								    </div>
								    </div>
								    <div class="line" id="halfyearlyDetailsDiv" style="display:none">
								    <div class="line" style="margin-left:25%">
								    	<form:radiobutton path="typeHalfyearly" id="type7" value="H1"  label="H1" />
                                        <form:radiobutton path="typeHalfyearly" id="type8" value="H2"  label="H2" />
										</div>
								    </div>
								    <div class="line">
										<div class="quarter bold">From Date<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:input path="fromDate" id="fromDate" cssClass="dateClass" readonly="true"/>
											<img  src="./images/calendar.gif" id="fromDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b",showsTime :  false,button :"fromDateImg",singleClick : true,step : 1});
											</script>
									
										</div>
								
										<div class="quarter bold">To Date<span class="failure">&nbsp;*</span></div>
										<div class="quarter">
											<form:input path="toDate" id="toDate" cssClass="dateClass" readonly="true" />
											<img  src="./images/calendar.gif" id="toDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"toDate",ifFormat : "%d-%b",showsTime : false,button :"toDateImg",singleClick : true,step : 1});
											</script>
										</div>
								</div>
								   	<div class="line">
										<div class="quarter bold">Limit(%)<span class="failure">*</span></div>
										<div class="quarter"><form:input path="limit" id="limit" onkeypress="return checkInt(event);"></form:input></div>
									</div>
										
										
							    <div class="line">
							    <div style="margin-left:80%">
							    <a href="javascript:submitTuitionFeeLimitMaster();" class="appbutton">Add</a>
							    <a href="javascript:clearTuitionFeeLimitMaster();" class="appbutton">Clear</a>
							    </div>
							    </div>
							    <div class="line" id="result"><jsp:include page="tutionFeeLimitMasterList.jsp"/>
							    </div>
									<%-- Content Page end --%>
								</div>
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="pk"/>
		</form:form>
	</body>
</html>
<!-- End:TutionFeeLimitMaster.jsp -->