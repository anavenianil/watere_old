<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:PromotionPayUpadte.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>

<link href="styles/Checkbox-Background.css" rel="stylesheet" type="text/css" /><!-- kosalaram -->

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/date.js"></script>
<script type="text/javascript" src="script/promotions.js"></script>
<title>Pay Fixation</title>
</head>

<body onload="javascript:resetPayFixationDOPart()">
	<form:form method="post" commandName="promotion" id="PromotionManagementBean">
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
								<div class="headTitle">Publish Promotion/Pay Fixation Do part II</div>
								<%-- Content Page starts --%>
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
	                                       	onclick="javascript:searchPromotionPayUpdate()"> <span>Search</span></a></div>
                                    	<div class="expbutton"><a
		                                        onclick="javascript:resetViewOptionalCertificate()"> <span>Clear</span></a></div>
	
	                                      <div class="expbutton"><a onclick="javascript:submitPromotionPayUpdate()"><span>Submit</span></a></div>
	                                   </div>
							<%-- <div class="line" style="margin-left: 24%;">
										<div class="expbutton"><a onclick="javascript:searchPayFixation()"> <span>Get List</span></a></div>
										<div class="expbutton"><a onclick="javascript:resetPayFixation()"> <span>Clear</span></a></div>
									</div>--%>
									<div class="line"><hr /></div>
									<div class="line" id="paylist">
									
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
		<form:hidden path="type" id="type"/>
		<form:hidden path="param" id="param"/>	
		</form:form>
				<script>
		yearID = ${promotion.yearID}
		</script> 		
				
	</body>
</html>
<!-- End:PromotionPayUpadte.jsp -->
