<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!-- begin:EmpSearchDetailsList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Employee Details</title>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
</head>
<body>
	<form:form method="post" commandName="employee">
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="id" id="id"/>
		<form:hidden path="firstName"/>
		<form:hidden path="designationId"/> 
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
								<div class="headTitle">Employee Details</div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								<%-- Content Page starts --%>
									<div class="line">
										<div  class="appbutton submitbutton"><a class="quarterbutton" href="javascript:viewSearchDetails()">Back</a></div>
									</div>
								    
								    <fieldset>
									   <legend><strong><font color='green'>Roles</font></strong> </legend>
									   <table style="width:100%;">
									      <!--<tr>
									         <td style="width:25%"><b>Primary Role</b></td>
									         <td style="width:25%"><b>:</b>${employee.defaultRole}</td>
									         
									      </tr>
									      <c:if test="${employee.parentName ne null}">
									      <tr>
									         <td style="width:25%"><b>Reporting To</b></td>
									         <td style="width:25%"><b>:</b>${employee.parentName}&nbsp;&nbsp;<b>(</b>${employee.parentRole}<b>)</b></td>
									        
									      </tr>
									      </c:if>
									      <c:if test="${employee.additionalRoles ne 'null' && employee.additionalRoles ne null }"> 
									      <tr>
									         <td style="width:25%"><b>Additional Roles</b></td>
									         <td style="width:25%"><b>:</b>${employee.additionalRoles}</td>
									         
									      </tr>
									      </c:if>-->
									      <c:forEach items="${employee.empRoleslist}" var="roles">
									      
									      <tr>
									      <c:if test="${roles.defaultRole eq 'YES'}">
									      <td style="width:25%"><b>Primary Role</b></td>
									      </c:if>
									      <c:if test="${roles.defaultRole eq 'NO'}">
									     <td style="width:25%"><b>Additional Role</b></td>
									      </c:if>
									      
									         
									         <td style="width:25%"><b>:</b>${roles.additionalRoles}</td>
									         
									         <td style="width:25%"><b>Reporting To</b></td>
									         <td style="width:25%"><b>:</b>${roles.parentName}&nbsp;&nbsp;<b>(</b>${roles.parentRole}<b>)</b></td>
									      </tr>
									      <tr>
									         
									        
									      </tr>
									      
									      </c:forEach>
									   </table>
									</fieldset>
								    
								    
									<fieldset><legend><strong><font color='green'>Professional Details</font></strong></legend>
									
									<div class="line">
											<table align="center" width="100%" cellpadding="0" cellspacing="0">
												<tr>
													<td style="width:25%;" class="bold">SFID : </td>
													<td style="width:25%;">${employee.sfid}</td>
													<td style="width:25%;" class="bold">Name : </td>
													<td style="width:25%;">${employee.name}</td>
												</tr>
												<tr>
													
													<td  class="bold">Dir/PD/TD/Project : </td>
													<td>${employee.directorateName}</td>
													<td  class="bold">Division : </td>
													<td>${employee.divisionName}</td>
												</tr>
												
												<tr>
													<td  class="bold">Designation : </td>
													<td>${employee.designationName}</td>
													<td  class="bold">Discipline : </td>
													<td>${employee.discipline}</td>
											   </tr>
											    <tr>
											   		<td class="bold">Internal Number : </td>
													<td>${employee.internalNo}</td>
											   </tr>									
											</table>
										</div>											
									</fieldset>
									
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
	<!-- End:EmpSearchDetailsList.jsp -->
</html>
