<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:TaEntitleExemption.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/tada.js"></script>

<title>Ta Entitle Exemption Master</title>

</head>
<body onload="javascript:clearEntitleExemption();" >
	<form:form method="post" commandName="tada" id="TadaManagementBean">
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
								<div class="headTitle">Ta Entitle Exemption Master</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
										<div class="quarter">
										    <form:select path="sfID" id="sfID" cssStyle="width:142px" onchange="javascript:showExemptEntitle();" >
												<form:option value="Select">Select</form:option>
												<form:options items="${tada.tadaBeanList}" itemValue="sfID" itemLabel="sfID" />
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Program Code<span class="mandatory">*</span></div>
										<div class="quarter">
										    <form:select path="programCode" id="programCode" cssStyle="width:142px" onchange="javascript:showProjects();">
												<form:option value="Select">Select</form:option>
												<form:options items="${tada.programList}" itemValue="programCode" itemLabel="programName" />
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Project<span class="mandatory">*</span></div>
										<div class="quarter">
										    <form:select path="projectName" id="projectName" cssStyle="width:142px" >
												<form:option value="Select">Select</form:option>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Exemption Entitlement<span class="mandatory">*</span></div>
										<div class="quarter">
										    <form:select path="entitleType" id="entitleType" cssStyle="width:142px" >
												<form:option value="Select">Select</form:option>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Remarks<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:textarea path="description" cols="20" rows="4" id="description"  onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"/>
											<input type="text" class="counter" name="counter" value="500" id="counter" disabled="disabled"/>
										</div>
									</div>
									<div class="line" style="margin-left: 35%;">
											<div class="expbutton"><a onclick="javascript:submitEntitleExemption();"> <span>Submit</span></a></div>
											<div class="expbutton"><a onclick="javascript:clearEntitleExemption();"> <span>Clear</span></a></div>
									</div>	
									<div class="line"><hr /></div>
									<div class="line" id="result">
										<jsp:include page="TaEntitleExemptionList.jsp"></jsp:include>								
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		</form:form>
		<script type="text/javascript">
		  projectsList = <%= (net.sf.json.JSONArray) session.getAttribute("projectsList") %>;
		  travelTypeMapDetailsJSON = <%= (net.sf.json.JSONArray) session.getAttribute("travelTypeMapDetailsJSON") %>;
		  gradePayList = <%= (net.sf.json.JSONArray) session.getAttribute("gradePayList") %>;
		  travelTypeList = <%= (net.sf.json.JSONArray) session.getAttribute("travelTypeList") %>;
		</script>	
			
	</body>
</html>
<!-- End:TaEntitleExemption.jsp -->