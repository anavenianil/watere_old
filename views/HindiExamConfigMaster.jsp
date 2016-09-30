<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:HindiExamConfigMaster.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
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
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script language="javascript" src="script/date.js"></script>
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/hindi.js"></script>


<title>HindiExamConfigMaster</title>
</head>
<body>
   <form:form method="post" commandName="hindi" id="hindi">
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
								<div class="headTitle">Hindi Exam Configuration Master</div>
								<div>
									<%-- Content Page starts --%>	
									<div id="result" class="line"></div>
									<div class="line">
										<div class="quarter leftmar">Exam Name<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="examId" id="examId" cssClass="formSelect">
												<form:option value="0">Select</form:option>
												<form:options items="${hindiExamsList}" itemLabel="examName" itemValue="examId" />											                            																																		
											</form:select>
										</div>
								    </div>
								    
									
							      <div class="line">
											<div class="quarter leftmar" style="margin-left: 8px;">Mother Tongue<span class='failure'>*</span></div>
												<div class="quarter bold">
													<input type=radio name="mothertongue" tabindex="1" value="1" id="mothertongue1" />Non Hindi
													<input type=radio name="mothertongue" tabindex="1" value="2" id="mothertongue2"  />All																						
												</div>
								 </div>	
								 
							      <div class="line">
							     	<div class="quarter leftmar" style="margin-left: 8px;">Pass %Of Marks <span class='failure'>*</span></div>
									<div class="quarter"><form:input path="passMarks" id="passMarks" maxlength="100" onkeypress="return isNumberExp(event)"></form:input></div>		
	                              </div>
	                              
							<div class="line">
										<div class="quarter leftmar">Employee Category<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="empCategory" id="empCategory" cssClass="formSelect" onchange="javascript:onchangeCategory();">
												<form:option value="0">Select</form:option>
												<form:options items="${hindiEmpCategoryList}" itemValue="value" itemLabel="key"/>																							                            																																		
											</form:select>
										</div>
										</div>
								<div class="line">
										<div class="quarter leftmar">Employee Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="empType" id="empType" cssClass="formSelect" onchange="javascript:onchangeEmpType();">
												<form:option value="0">Select</form:option>
												<form:option value="1">Gazetted</form:option>
												<form:option value="2">NonGazetted</form:option>																								                            																																		
											</form:select>
										</div>
										</div>
								
								<div  class="line" id="result1">
								   <div class="line">
											<div class="leftmar">
												<div style="float: left; width: 38%;">De Selected </div>
												<div class="half" >Selected </div>
											</div>
									</div>
								    <div class="line">
											<div class="leftmar">
												<div style="float: left; width : 28%">
													<form:select path="deselectDesignation" id="SelectLeft" size="10" multiple="true" cssStyle="width:250px">
													</form:select>
												</div>
												<div style="float: left; width : 10%; margin-top: 60px;">									
														<div style="margin-bottom: 5px;" align="center">
													          <input style="margin-bottom: 5px"id="MoveRight" type="button" value=" Add " />
      											              <input id="MoveLeft" type="button" value=" Remove " />    											        
      											        </div>		      																				
		      									</div>
												<div style="float: left; width : 35%">
													<form:select path="selectDesignation" id="SelectRight" size="10" multiple="true" cssStyle="width:250px">
													</form:select>
												</div>
											</div>
										
									</div>
								</div>
								
								<div class="line">
											<div class="quarter leftmar" style="margin-left: 8px;">Applicable For Increment<span class='failure'>*</span></div>
												<div class="quarter bold">
													<input type=radio name="incrementApplicable" tabindex="1" value="1" id="incrementApplicable1" onclick="javascript:onselectIncrement('YES')" />YES
													<input type=radio name="incrementApplicable" tabindex="1" value="2" id="incrementApplicable2"  onclick="javascript:onselectIncrement('NO')" />NO																						
												</div>
								 </div>
								  <div id="increment"></div>
								
								<div class="line">
											<div class="quarter leftmar" style="margin-left: 8px;">Applicable For CashAward<span class='failure'>*</span></div>
												<div class="quarter bold">
													<input type=radio name="cashAwardApplicable" tabindex="1" value="1" id="cashAwardApplicable1" />YES
													<input type=radio name="cashAwardApplicable" tabindex="1" value="2" id="cashAwardApplicable2"  onclick="javascript:onselectCashAward('NO')" />NO																						
												</div>
								 </div>
	                              
	                              <div id="cashApplicable"></div>
	                              
	                              	
								<div class="line">
												<div class="expbutton" style="margin-left: 25%;"><a onclick="javascript:saveExamConfigDetails();"> <span>Submit</span></a></div>
												<div class="expbutton"><a onclick="javascript:clearExamConfigDetails();"> <span>Clear</span></a></div>
								</div>	
								<div id="result2" class="line"><jsp:include page="HindiExamConfigListPage.jsp" /> </div>
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
		<form:hidden path="param" id="param" />
		 <form:hidden path="pk" id="pk" /> 
		<!-- <form:hidden path="type" id="type" />   
		<form:hidden path="oldExamId" id="oldExamId" /> 
		<form:hidden path="oldEmpCategory" id="oldEmpCategory" /> 
		<form:hidden path="oldEmpType" id="oldEmpType" /> -->
   </form:form>
</body>
</html>