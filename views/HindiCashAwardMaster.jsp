<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:HindiCashAwardMaster.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<%@page import="net.sf.json.JSONArray"%><html xmlns="http://www.w3.org/1999/xhtml">
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


<title>Hindi CashAward Master</title>
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
								<div class="headTitle">Hindi CashAward Master</div>
								<div>
									<%-- Content Page starts --%>	
									
								 <div class="line">
										<div class="quarter leftmar">Exam Name<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="examId" id="examId" cssClass="formSelect">
												<form:option value="0">Select</form:option>												
												<form:options items="${hindiExamsList}" itemValue="examId" itemLabel="examName"/>													                            																																		
											</form:select>
										</div>
								    </div>
								    
							      <div class="line">
							     	<div class="quarter leftmar" style="margin-left: 8px;">Lower%<span class='failure'>*</span></div>
									<div class="quarter"><form:input path="lowerPercentage" id="lowerPercentage" maxlength="100" onkeypress="return isNumberExp(event)"></form:input></div>		
	                              </div>
	                             
								  <div class="line">
													<div class="quarter leftmar" style="margin-left: 8px;">Upper%<span class='failure'>*</span></div>
													<div class="quarter"><form:input path="upperPercentage" id="upperPercentage" maxlength="100" onkeypress="return isNumberExp(event)"></form:input></div>
								</div>
								  
								<div class="line">
													<div class="quarter leftmar" style="margin-left: 8px;">Cash Award Amount<span class='failure'>*</span></div>
													<div class="quarter"><form:input path="cashAwardAmount" id="cashAwardAmount" maxlength="100" onkeypress="return isNumberExp(event)"></form:input></div>
								</div>  
	                              	
								<div class="line">
												<div class="expbutton" style="margin-left: 25%;"><a onclick="javascript:saveCashAwardDetails();"> <span>Submit</span></a></div>
												<div class="expbutton"><a onclick="javascript:clearCashAwardDetails();"> <span>Clear</span></a></div>
								</div>	
								<div id="result" class="line"><jsp:include page="HindiCashAwardListPage.jsp" /></div>
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