<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:EmployeeStatus.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page
	import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.beans.family.FamilyBean"%>
	<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/script.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<title>Employee Status</title>
</head>

<body>
	<form:form method="post" commandName="employee" id="employee">
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
							<table width="100%"><tr><td>
								<div class="headTitle"><u>Employee Status</u> </div>
								<div>
								<div id="returnrequest">
			<%-- Content Page starts 
			                      <div id="result" class="line">
			                         <b style="color:red;">${sessionScope.message}</b> 
			                      </div>
			                      
			                        --%>  
			                          
			                      
									<div class="line">
										<div class="quarter leftmar">Organisation<span class="mandatory">*</span></div>
										<div class="quarter"><form:select path="organizationId" id="organizationId"  cssClass="formSelect" onchange="javascript:selectOrganizationWiseEmployees();">
												<form:option value="select">Select</form:option>
												<form:options items="${employee.orgList}" itemLabel="name" itemValue="id"/>
											</form:select>
												</div>
									</div>
									<div  class="line" id="result1">
									<div class="line">
									<div class="leftmar">
												<div style="float: left; width: 35%;">Enabled Employees</div>
												<div class="half" >Disabled Employee</div>
											</div>
									</div>
								   <div class="line">
											<div class="leftmar">
												<div style="float: left; width : 35%">
													<form:select path="fromID" id="SelectLeft" size="20" multiple="true" cssStyle="width:300px">
													</form:select>
												</div>
												<div style="float: left; width :10%; margin-top: 60px;">									
														<div style="margin-bottom: 5px;" align="center">
													          <input style="margin-bottom:5px"id="MoveRight" type="button" value=" Add " />
      											              <input id="MoveLeft" type="button" value=" Remove " />    											        
      											        </div>		      																				
		      									</div>
												<div style="float: left; width : 50%">
													<form:select path="toID" id="SelectRight" size="20" multiple="true" cssStyle="width:300px">
													</form:select>
												</div>
											</div>
										
									</div>
									
									
							   </div>
									<div class="line">
										<div style="margin-left:32%">
										<a href="javascript:changeEmpStatus();" class="appbutton">Submit</a>
										<a href="javascript:clearEmpStatus();" class="appbutton">Clear</a>
										
										</div>
									</div>
							
								
								
									<%-- Content Page end --%>
							
			
	
	                      </div>
	                </div>
	                </td></tr>
							</table>
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
		<form:hidden path="selectedLinks"/>
      </form:form>
        </body>
        </html>
		