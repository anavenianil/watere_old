<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TrainingDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>


</head>

<body>
	<form:form method="post" commandName="training">
	<script>document.title="Training Details"</script>
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
								<div style="padding: 10px 0 0 0;"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
								<div class="headTitle">Manage Training Details</div>
								<div id="result"></div>
								<div class="line">
									<div class="quarter bold" id="labelType" style="margin-left:11px;">SFID</div>
									<div class="quarter"><form:input path="sfid" id="changeSfid" maxlength="15"/></div>
									<div class="appbutton"><a href="javascript:changeEmployeeDetails('training');" style="text-decoration: none";>Search</a></div>
								</div>
								<div class="line" id="createTraining">
									   <fieldset><legend><strong><font color='green'>Training Details</font></strong></legend>
	
	<div>
		<div class="line">
			<div class="quarter">SFID</div>
			<div class="quarter">${changeSfid}</div>
			<div class="quarter">Name</div>
			<div class="quarter">${changeSfidName}</div>
		</div>
		<div class="line">
			<div class="quarter">Course<span class="mandatory">*</span></div>
			<div class="quarter">
				<form:input path="course" id="course" maxlength="150" onkeypress="return checkChar(event);"/>
			</div>
			<div class="quarter">Subject</div>
			<div class="quarter"><form:input path="subject" id="subject" maxlength="150" onkeypress="return checkChar(event);"/></div>	 
			
		</div>
		<div class="line">
			<div class="quarter">Training Area</div>
			<div class="quarter"><form:select path="area" id="area" cssClass="formSelect" >
					<form:option value="0">Select</form:option>
					<form:option value="INDIA">India</form:option>
					<form:option value="ABROAD">Abroad</form:option>
				</form:select>
			</div>
			<div class="quarter">Organisation</div>
			<div class="quarter"><form:input path="institute" id="institute" maxlength="100"  onkeypress="return checkChar(event);"/></div>
		</div>
		<div class="line">
				<div class="quarter">Year of Completion</div>
				<div class="quarter"><form:select path="year"  id="year" cssClass="formSelect" >
										<form:option value="0">Select</form:option>
										<form:options items="${yearList}" itemValue="id" itemLabel="name"/>									
									</form:select></div>
				<div class="quarter">Duration <span class="mandatory">(In months)</span></div>
				<div class="quarter"><form:input path="duration" id="duration" maxlength="2" onkeypress="return checkInt(event);"/></div>
		</div>
		<div class="line">
				<div class="quarter">From Date</div>
				<div class="quarter">
					<form:input path="fromDate" id="fromDate" cssClass="dateClass" readonly="true"/>
					<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
					<script type="text/javascript">
					Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
					</script>
				</div>
				<div class="quarter">To Date</div>
				<div class="quarter">
					<form:input path="toDate" id="toDate" cssClass="dateClass" readonly="true"/>
					<img  src="./images/calendar.gif"   id="date_start_trigger2" />	
					<script type="text/javascript">
					Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
					</script>
				</div>
		</div>
		<c:if test="${message ne 'viewTraining'}">
			<div class="float">
				<div class="appbutton" style="float:right"><a href="javascript:resetTraining();" style="text-decoration: none">Clear</a></div>
				<div class="appbutton" style="float:right"><a href="javascript:manageTraining();" style="text-decoration: none">Submit</a></div>
			</div>
		</c:if>
			
			<div class="height"></div>
			
		</div>
		
	
	
	
	</fieldset>
	<div class="height"><hr/></div>
								</div>
								<div class="line" id="trainingList">
									    <jsp:include page="TrainingList.jsp" />
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
		
		<form:hidden path="id" id="id"/>
		<form:hidden path="param" id="param"/>
		<form:hidden path="type"/>		
		<c:if test="${message ne 'changeSfidFirst'}">
		<script>
			resetTraining();
		</script>
		</c:if>
		</form:form>
	</body>
</html>
<!-- End:TrainingDetails.jsp -->