<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--  begin:HQRequest.jsp -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html>
<head>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/pisscript.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<title>Application For Pursuing Higher Studies</title>
</head>
<body>
<form:form method="POST" commandName="HQRequest">
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
								<div class="headTitle">Application For Pursuing Higher Studies</div>
								<div class="line">
									<%-- Content Page starts --%>
									<div class="line" id="result"></div>
									<div class="line">
										<div class="half leftmar">Sfid</div>
										<div class="half">${HQRequest.sfID}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Name</div>
										<div class="half">${HQRequest.name}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Designation</div>
										<div class="half">${HQRequest.designation}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Date of Appointment</div>
										<div class="half"><fmt:formatDate pattern="dd-MMM-yyyy" value="${HQRequest.doj}" /></div>
									</div>
									<div class="line">
										<div class="half leftmar">Date of Birth</div>
										<div class="half"><fmt:formatDate pattern="dd-MMM-yyyy" value="${HQRequest.dob}" /></div>
									</div>
									<div class="line">
										<div class="half leftmar">Academic Qualification</div>
										<div class="half">${HQRequest.course}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Whether SC/ST</div>
										<div class="half">
											<form:radiobutton path="religionFlag" id="religionFlagYes" label="Yes" value="Y"/>
											<form:radiobutton path="religionFlag" id="religionFlagNo" label="No" value="N"/>											
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">Type of work on which presently Engaged</div>
										<div class="half">${HQRequest.departmentName}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Course of study</div>
										<div class="half">
										<form:select path="course" id="course">
										<form:option value="">Select</form:option>
										<form:options items="${sessionScope.QualificationsList}" itemLabel="name" itemValue="name"/>
										</form:select>
										</div>
									</div>
									<div class="line">
									<div class="half leftmar">Duration Of Course</div>
										<div class="quarter">From Date
										<form:input path="fromDate" id="fromDate" size="12" cssClass="required" readonly="true"/>
										<img  src="./images/calendar.gif"   id="date_start_trigger" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger",singleClick : true,step : 1});
											</script>
										</div>
										<div class="quarter">To Date
										<form:input path="toDate" id="toDate" cssClass="required" size="12"   readonly="true"/>
										<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
											</script>
											</div>
									</div>
									<div class="line">
										<div class="half leftmar"><div class="half">Whether Such Permission Will Interfere With Discharge Of Duties Or Not</div></div>
										<div class="half">
											<form:radiobutton path="dischargeOfDuties" id="dischargeOfDutiesYes" label="Yes" value="Y"/>
											<form:radiobutton path="dischargeOfDuties" id="dischargeOfDutiesNo" label="No" value="N"/>											
										</div>
									</div>
									<div class="line">
										<div class="half leftmar"><div class="half">Whether The Study Course Will Be Useful For Lab Work</div></div>
										<div class="half">
											<form:radiobutton path="labWork" id="labWorkYes" label="Yes" value="Y"/>
											<form:radiobutton path="labWork" id="labWorkNo" label="No" value="N"/>											
										</div>
									</div>
									<div class="line" style="margin-left:50%">
										<div class="expbutton"><a href="javascript:submitHQRequest('${HQRequest.desigID}')"> <span>Submit</span></a></div>
										<div class="expbutton"><a href="javascript:clearHQRequest();"> <span>Clear</span></a></div>
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
	</form:form>
	<script>
	clearHQRequest();
	</script>
</body>
</html>
<!--  End:HQRequest.jsp-->