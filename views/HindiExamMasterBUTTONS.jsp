<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MTCategoryMaster.jsp -->
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


<title>HindiExamMaster</title>
</head>
<body>
   <form:form method="post" commandName="hindi">
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
								<div class="headTitle">Exam Configuration</div>
								<div>
									<%-- Content Page starts --%>	
									<div class="line">
							     	<div class="quarter leftmar" style="margin-left: 8px;">Exam Name<span class='failure'>*</span></div>
									<div class="quarter"><form:input path="examname" id="examname" maxlength="100"></form:input></div>		
	                              </div>
								 
							      <div class="line">
											<div class="quarter leftmar" style="margin-left: 8px;">Mother Tongue<span class='failure'>*</span></div>
												<div class="quarter bold">
													<input type=radio name="mothertongueForExam" tabindex="1" value="1" id="mothertongueForExam1" />OtherThan Hindi
													<input type=radio name="mothertongueForExam" tabindex="1" value="2" id="mothertongueForExam2"  />All																						
												</div>
								 </div>	
								 
							      <div class="line">
							     	<div class="quarter leftmar" style="margin-left: 8px;">Pass %Of Marks <span class='failure'>*</span></div>
									<div class="quarter"><form:input path="sscMarksForExam" id="sscMarksForExam" maxlength="100"></form:input></div>		
	                              </div>
	                              
							<div class="line">
										<div class="quarter leftmar">Designation<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="" id="" cssClass="formSelect" onchange="javascript:getQualification();">
												<form:option value="0">Select</form:option>																									                            																																		
											</form:select>
										</div>
										</div>
										
							    <div class="line">
											<div class="quarter leftmar" style="margin-left: 8px;width:45%">After SSC Hindi As Main Language In Any One Of Qualifications<span class='failure'>*</span></div>
												<div class="quarter bold">
													<input type=radio name="hindiAsAcodomicForExam" tabindex="1" value="1" id="hindiAsAcodomicForExam1" />YES
													<input type=radio name="hindiAsAcodomicForExam" tabindex="1" value="2" id="hindiAsAcodomicForExam2"  />NO																						
												</div>
								 </div>	
								 
							     <div class="line">
							     	<div class="quarter leftmar" style="margin-left: 8px;">No.Of Increments<span class='failure'>*</span></div>
									<div class="quarter"><form:input path="noOfIncrements" id="noOfIncrements" maxlength="100"></form:input></div>		
	                              </div>
								
								<div class="line">
											<div class="quarter leftmar" style="margin-left: 8px;">Applicable For CashAward<span class='failure'>*</span></div>
												<div class="quarter bold">
													<input type=radio name="applicable" tabindex="1" value="1" id="" />YES
													<input type=radio name="applicable" tabindex="1" value="2" id=""  />NO																						
												</div>
								 </div>
	                              
	                              <div class="line">
							     	<div class="quarter leftmar" style="margin-left: 8px;">Cash Award Applicable Amount<span class='failure'>*</span></div>
									<div class="quarter"><form:input path="cashaward" id="cashaward" maxlength="100"></form:input></div>		
	                              </div>
	                              	
								<div class="line" style="margin-left: 25%;">
												<div class="expbutton"><a onclick="javascript:saveExamDetails();"> <span>Submit</span></a></div>
												<div class="expbutton"><a onclick="javascript:clearExamDetails();"> <span>Clear</span></a></div>
								</div>	
								<div id="result" class="line"><jsp:include page="HindiExamListPage.jsp" /></div>
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
		<form:hidden path="param" id="param"/>
		<form:hidden path="pk" id="pk"/>
   
   
   </form:form>
</body>
</html>