<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin: TreeEmpDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" errorPage="Login.jsp"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html>
<head>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/json2_mini.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script language="javascript" src="script/prototype.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/pisscript.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
</head>
<body>
	<form:form method="post" commandName="employee" >
		<input type="hidden" name="param"/>
		<input type="hidden" name="type"/>
		<input type="hidden" name="id" id="id"/>
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
							<div class="lightblueBg1" id="empbiodataPrintView">
								<div id="documentheadtitle" class="headTitle" style="text-align: center;"></div>
								<div id="newbutton" class="line" >
								  	<c:if test="${employee.empSearchId eq null}">
										<a href="javascript:viewEmpTreeDetails();" class="appbutton" style="float:right;">Back</a>
									</c:if>
									<c:if test="${employee.empSearchId eq 'yes'}">
										<a href="javascript:viewSearchDetails();" class="appbutton" style="float:right;">Back</a>
									</c:if> 
									<c:if test="${employee.empSearchId eq 'details'}">
										<a href="javascript:printBiodata();" class="appbutton" style="float:right;">Print</a>
									</c:if> 
								</div>

									<%-- Content Page starts --%>
								<div id="empbiodata">
									<c:if test="${employee.type eq null}">
									<div class="line">
										<jsp:include page="EmployeeDetails.jsp" />
									</div>
									</c:if>
									<c:if test="${employee.type eq 'downTree'}">
									<div class="line">
									
									<fieldset><legend><strong><font color='green'>Professional Details</font></strong></legend>
										<table align="center" width="100%" cellpadding="0" cellspacing="0">
												<tr>
													<td style="width:25%;" class="bold">SFID : </td>
													<td style="width:25%;">${employee.sfid}</td>
													<td style="width:25%;" class="bold">Name : </td>
													<td style="width:25%;">${employee.name}</td>
												</tr>
												<tr>
													<td  class="bold">Department : </td>
													<td>${employee.divisionName}</td>
													<td  class="bold">Reporting to : </td>
													<td>${employee.directorateName}</td>
												</tr>
												
												<tr>
													<td  class="bold">Designation : </td>
													<td>${employee.designationName}</td>
													<td  class="bold">Discipline : </td>
													<td>${employee.discipline}</td>
											   </tr>
											   <tr>
													<td  class="bold">Date of Birth : </td>
													<td>${employee.dob}</td>
													<td  class="bold">Date of Join in ASL : </td>
													<td>${employee.dojAsl}</td>
											   </tr>
											   <tr>
											   		<td class="bold">Mobile Number : </td>
													<td>${employee.personalNumber}</td>
											   		<td class="bold">Internal Number : </td>
													<td>${employee.internalNo}</td>
											   </tr>									
											</table>
									</fieldset>
									</div>
									
									</c:if>
									<div id="viewMasterData" class="line"></div>
									<script>
										var values=<%= (net.sf.json.JSONObject)session.getAttribute("jsonEmpTreeDetails") %>;
										PopulateEmpTree(values,'${employee.empSearchId}');
									</script>
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
	</form:form>
</body>
</html>
<!-- End: TreeEmpDetails.jsp -->