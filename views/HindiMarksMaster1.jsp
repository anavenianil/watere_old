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


<title>HindiMarksMaster</title>
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
								<div class="headTitle">Exams Category</div>
								<div>
									<%-- Content Page starts --%>								 														
								
								<div class="line">
							     	<div class="quarter leftmar" style="margin-left: 8px;">ExamName<span class='failure'>*</span></div>
									<div class="quarter"><form:input path="" id="" maxlength="100"></form:input></div>		
	                              </div>
							
								<div class="line" style="margin-left: 25%;">
												<div class="expbutton"><a onclick="javascript:saveDemoDetails();"> <span>Save</span></a></div>
												<div class="expbutton"><a onclick="javascript:clearDemoDetails();"> <span>Clear</span></a></div>
								</div>	
								<div id="result" class="line"><jsp:include page="DemoListPage.jsp" /></div>
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