<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ViewOptionalCertificate.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/promotions.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/date.js"></script>

<title>View Option Certificate</title>
</head>

<body>

<form:form method="post" commandName="promotion"
	id="PromotionManagementBean">
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
	<div class="headTitle">View Option Certificates & Update Designation</div>
	<%-- Content Page starts --%>
	<div class="line">
	
    <div class="line">
    <div class="line">
		<div class="quarter leftmar">Category Type<span class="mandatory">*</span></div>
			<div class="quarter">
										<%-- onchange="getCategoryPayList()" --%>
			<form:select path="assessmentCategoryID" id="assessmentCategoryID"  cssClass="formSelect" cssStyle="width:90%">
				                   <option value="0">Select</option>
										<c:forEach var="assessmentDetails" items="${promotion.assessmentDetails}" >
									
									<option value="${assessmentDetails.yearID}-${assessmentDetails.boardID}-${assessmentDetails.assessmentCategoryID}">${assessmentDetails.reservation}</option>
									
										
										 </c:forEach>
				 
			                   	</form:select>
						</div>
						</div>
								
				
        </div>
    
    
	<div class="line">
	<div  style="margin-left: 25%;" class="expbutton"><a
		onclick="javascript:searchViewOptionalCertificate()"> <span>Search</span></a></div>
	<div class="expbutton"><a
		onclick="javascript:resetViewOptionalCertificate()"> <span>Clear</span></a></div>
	
	<div class="expbutton"><a onclick="javascript:submitOptionValue()"><span>Update Designation</span></a></div>
	</div>
	<div class="line">
	<hr />
	</div>
	<div>
    <u style="color: brown">Note</u><label><font style="font-weight:normal:" color="brown">
    :1)Select The employees to Update Designation with immediate effect<br/> 	
     2) Employees whose Designations are updated will be removed from Below list And Listed in Promotion Pay Fixation DopartII screen.<br/>  
     3) Designation can't updated for employees whose<br />
          &nbsp&nbsp a)option (higher/lower grade) not saved.<br />
          &nbsp&nbsp b)Annual Increment Basic pay updation is pending.<br />
							     </font></label>	
									</div>
	<div class="line" id="result">
	<jsp:include page="OptionCertificateList1.jsp" />
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
	<form:hidden path="type" />
	<form:hidden path="param" />
</form:form>
</body>
</html>
<!-- End:ViewOptionalCertificate.jsp -->