<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:NomineeDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />

<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script language="javascript" src="script/prototype.js"></script>
<script language="javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>

</head>

<body>
	<form:form method="post" commandName="nominee">
	<form:hidden path="param"/>
	<form:hidden path="type"/>
	<form:hidden path="id"/>
	<script>document.title="Nominee Details"</script>
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
								<div>
								<div id="labelType">
									<c:if test="${message eq 'changeSfidFirst'}">
										<span class="success"><spring:message code="changeSfid"></spring:message></span>
									</c:if>
								</div>
								<c:if test="${message ne 'changeSfidFirst'}">
									<c:if test="${nominee.param ne 'getNomineeDetails'}">
										<div class="headTitle">Manage Nominee Details</div>
									</c:if>
									<c:if test="${nominee.param eq 'getNomineeDetails'}">
										<div class="headTitle">Nominee Details</div>
									</c:if>
									
									   
									    <div id="viewNomineeDetails">
									    	<div id="createNomineeDetails" class="line">
												<fieldset><legend><strong>Nominee Details</strong></legend>
												<div class="line">
													<div class="quarter">SFID</div>
													<div class="quarter">${nominee.sfid}</div>
													<div class="quarter">Name</div>
													<div class="quarter">${changeSfidName}</div>
												</div>
												<div class="line">
														<div class="quarter"><spring:message code="nominee.nominee" /><span class="failure">*</span></div>
														<div class="quarter">
														 
															<form:select path="nominee" id="nomineeId" onchange="javascript:addNominee();" cssClass="formSelect" onmouseover="setSelectWidth('#nomineeId')" >
																<form:option value="select">Select</form:option>
																<c:forEach var="family" items="${familyList}">
																	 <form:option value="${family.id}">${family.name}</form:option>
																</c:forEach> 
																<%--<form:option value="new">Add New Member</form:option>--%>
															</form:select>
														 
														</div>
														<%--<div class="quarter"><spring:message code="nominee.dob" /><span class="failure">*</span></div>
														<div class="quarter"> <form:input path="dob" id="nomineedob" cssClass="dateClass" readonly="true"/>
														<img  src="./images/calendar.gif"   id="date_start_trigger3" />	
																		<script type="text/javascript">
																		Calendar.setup({inputField :"nomineedob",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
																		</script>
														</div>	 	
													</div>
													<div class="line">		
														<div class="quarter"><spring:message code="nominee.relationId" /><span class="failure">*</span></div>
														<div class="quarter"> 
															<form:select path="relationId" id="nomineerelation">
																<form:option value="select">Select</form:option>
																<c:forEach var="relation" items="${RelationList}">
																	 <form:option value="${relation.id}">${relation.name}</form:option>
																</c:forEach> 
															</form:select>
														 </div>--%>
														<div class="quarter"><spring:message code="nominee.percentage" /><span class="failure">*</span></div>
														<div class="quarter"> <form:input path="percentage" maxlength="6" id="percentage" onkeypress="return checkFloat(event,'percentage');" onblur="javascript:checkDecimals('percentage');"/></div>
													</div>
													<div class="line">
														<div class="quarter"><spring:message code="nominee.dateofnominate" /></div>
															<div class="quarter"> <form:input path="nominate" id="nominate" cssClass="dateClass" readonly="true"/>
															<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
																		<script type="text/javascript">
																		Calendar.setup({inputField :"nominate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
																		</script>
														
														
														</div>
													    <div class="quarter"><spring:message code="nominee.remarks" /></div>
														<div class="quarter"> <input type="text" name="nominee.remarks" id="remarks"/></div>
													</div>
													<div class="line">
														<div class="appbutton" style="float:right"><a class="quarterbutton" href="javascript:clearNomineeDetails()">Clear</a></div>
														<div class="appbutton submitbutton"><a class="quarterbutton" href="javascript:manageNomineeDetails();">Submit</a></div>
													</div>
												</fieldset>
												
											</div>
											<div id="nomineeList" class="line">
													<jsp:include page="nomineeDetailsList.jsp" />
											</div>
									    </div> 
									    </c:if>    
									   	<c:if test="${nominee.param eq 'getNomineeDetails'}">
									   		<script>getNomineeDetails();</script>
									   	</c:if>												
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
		<form:hidden path="nomineeID"/>		
		</form:form>
	</body>
</html>
<!-- End:NomineeDetails.jsp -->