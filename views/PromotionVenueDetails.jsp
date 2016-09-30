<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:PromotionVenueDetails.jsp -->

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

<title>Venue Details</title>
</head>

<body>
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
								<div class="headTitle">Interview Venue Details</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter leftmar">Category Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="assessmentTypeID" id="assessmentTypeID"  cssClass="formSelect" onchange="getVenueList()">
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.assessmentTypeList}" itemValue="id"  itemLabel="name"/>
											</form:select>
										</div>
									</div>
								
									<div class="line">
										<div class="quarter leftmar">Board Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="assessmentCategoryID" id="assessmentCategoryID"  cssClass="formSelect" onchange="getVenueList()">
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.assessmentCategoryList}" itemValue="id" itemLabel="category"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Year<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="yearID" id="yearID"  cssClass="formSelect" onchange="getVenueList()">
												<form:option value="0">Select</form:option>
												<form:options items="${promotion.yearList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Center<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="center" id="center" onkeypress="javascript:return checkChar(event);"></form:input>
										</div>
									</div>	
									<div class="line">
										<div class="quarter leftmar">Name of the co-ordinator<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="coOrdinator" id="coOrdinator" onkeypress="javascript:return checkChar(event);"></form:input>
										</div>
									</div>	
									<div class="line">
										<div class="quarter leftmar">Co-ordinating Lab<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="coOrdinatorLab" id="coOrdinatorLab" onkeypress="javascript:return checkChar(event);"></form:input>
										</div>
									</div>	
									<div class="line">
										<div class="quarter leftmar">Address<span class="mandatory">*</span></div>
										<div >
											<form:textarea path="address" cols="30" rows="4" id="address"  onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"/>
											<input type="text" class="counter" name="counter" value="500" id="counter" disabled="disabled"/>
										</div>
									</div>	
									<div class="line">
										<div class="quarter leftmar">Contact Details<span class="mandatory">*</span></div>
										<div class>
											<form:textarea path="contactAddress" cols="30" rows="4" id="contactAddress"  onkeypress="textCounter(this,document.forms[0].counter1,500);" onkeyup="textCounter(this,document.forms[0].counter1,500);"/>
											<input type="text" class="counter" name="counter1" value="500" id="counter1" disabled="disabled"/>
										</div>
									</div>	
									<div class="line">
										<div class="quarter leftmar">Venue<span class="mandatory">*</span></div>
										<div class>
											<form:textarea path="venue" cols="30" rows="4" id="venue"  onkeypress="textCounter(this,document.forms[0].counter2,500);" onkeyup="textCounter(this,document.forms[0].counter2,500);"/>
											<input type="text" class="counter" name="counter2" value="500" id="counter2" disabled="disabled"/>
										</div>
									</div>		
									<div class="line" style="margin-left: 25%;">
										<div class="expbutton"><a onclick="javascript:submitVenueDetails()"> <span>Submit</span></a></div>
										<div class="expbutton"><a onclick="javascript:resetVenueDetails()"> <span>Clear</span></a></div>
									</div>
									<div class="line"><hr /></div>
									<div class="line" id="result">
										<jsp:include page="PromotionVenueDetailsList.jsp"></jsp:include>
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
	</body>
</html>
<!-- End:PromotionVenueDetails.jsp -->