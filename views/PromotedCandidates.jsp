<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:PromotedCandidates.jsp -->

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

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/promotions.js"></script>
<script type="text/javascript" src="script/date.js"></script>

<title>Promoted Candidates</title>
</head>

<body onload="javascript:resetPromotedCandidates()">
	<form:form method="post" commandName="promotion" id="PromotionManagementBean">
		<div>
			<div class="Innermaindiv">
				<div class="header"></div>
				<div><jsp:include page="MenuLinks.jsp" /></div>					
				<div class="mainDisplay">
					<div class="whitebox1">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								
							</div>
						</div>
						<div class="whitebox_m1">
							<div class="lightblueBg1">
								<div class="headTitle">Promoted Candidates List</div>
								<%-- Content Page starts --%>
								<div class="line">
								<div class="line">
										<div class="quarter leftmar">Year<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="yearID" id="yearID"  cssClass="formSelect" onchange="javascript:getYearWiseAssessmentList(this,'promotedCandidates');" >
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.yearList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
								<div class="line">
										<div class="quarter leftmar">Assessment Category Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="assessmentTypeID" id="assessmentTypeID" onchange="getCategoryDesignationList()" cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.assessmentTypeList}" itemValue="id"  itemLabel="name"/>
											</form:select>
										</div>
									</div>
								<!-- 
									<div class="line">
										<div class="quarter leftmar">Board Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="assessmentCategoryID" id="assessmentCategoryID"  cssClass="formSelect" onchange="javascript:enbleReservation()">
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.assessmentCategoryList}" itemValue="id" itemLabel="category"/>
											</form:select>
										</div>
									</div>
									 -->
									<div class="line">
										<div class="quarter leftmar">Designation</div>
										<div class="quarter">
											<form:select path="designationFrom" id="designationFrom" onmouseover="setSelectWidth('#designationFrom')" cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												<form:options itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
																				
									<div class="line" style="margin-left: 25%;">
										<div class="expbutton"><a onclick="javascript:searchPromotedCandidates()"> <span>Search</span></a></div>
										<div class="expbutton"><a onclick="javascript:resetPromotedCandidates()"> <span>Clear</span></a></div>
									</div>
									<div class="line"><hr /></div>
									<div class="line">
										<div class="quarter leftmar">Status<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="status" id="status"  cssClass="formSelect" onchange="javascript:disableDates()">
												<form:option value="0">Select</form:option>
												<form:option value="53">Promoted</form:option>
												<form:option value="55">Deferred</form:option>
											    <form:option value="62">Not Selected</form:option>
											    <form:option value="54">Interview Not Attended</form:option>
											</form:select>
										</div>
									</div>
									<div class="line" id="rejectedID" style="display:none">
									<div class="line">
										<div class="quarter leftmar">Promotion Date<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="promotionDate" id="promotionDate" cssClass="dateClass" readonly="true" />
											<img  src="./images/calendar.gif" id="promotionDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"promotionDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"promotionDateImg",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Variable Inc Start Date<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="effectiveDate" id="effectiveDate" cssClass="dateClass" readonly="true" />
											<img  src="./images/calendar.gif" id="effectiveDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"effectiveDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"effectiveDateImg",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Variable Incr<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="variableIncr" id="variableIncr"/>	
										</div>
									</div>
								 <div class="line">
										<div class="quarter leftmar">Ending Date<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="endingDate" id="endingDate" cssClass="dateClass" readonly="true" />
											<img  src="./images/calendar.gif" id="endingDateImg"  />	
											<script type="text/javascript">
												Calendar.setup({inputField :"endingDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"endingDateImg",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									
									</div>
									<div class="line" id="reservationDiv" style="display:none">
										<div class="quarter leftmar">Reservation<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="reservationID" id="reservationID"  cssClass="formSelect" >
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.reservationList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line" style="margin-left: 25%;">
										<div class="expbutton"><a onclick="javascript:submitPromotedCandidates()"> <span>Submit</span></a></div>
										<div class="expbutton"><a onclick="javascript:getPromotionReport('PromotedCandidates')"> <span>promoted list</span></a></div>
									</div>
									
									<div class="line"><hr /></div>
									<div class="line" id="result">
										
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
		<form:hidden path="type"/>
		<form:hidden path="param"/>	
		</form:form>
		<script>
		designationListJSON= <%= (net.sf.json.JSONArray)session.getAttribute("designationListJSON") %>;
		yearID = ${promotion.yearID}
		</script> 		
	</body>
</html>
<!-- End:PromotedCandidates.jsp -->