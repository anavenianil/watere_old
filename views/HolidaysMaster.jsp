<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:HolidaysMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>

<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<title>Holidays Master</title>
</head>

<body>
	<form:form method="post" commandName="holidays">
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
								<div class="headTitle" id="title">Add Holidays For The Year ${holidays.year}</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="line">
											<div class="quarter bold">Years<span class="mandatory">*</span></div>
											<div class="quarter bold">
												<form:select path="year" id="year" cssClass="formSelect" onchange="javascript:getSeletedYearHolidays();">
													<form:option value="select">Select</form:option>
													<form:options items="${holidays.yearsList}" itemValue="name" itemLabel="name"/>
												</form:select>
												
											</div>
										</div>
										<div class="line">
											<div class="quarter bold">Holiday<span class="mandatory">*</span></div>
											<div class="half">
												<form:input path="holiday" id="holiday" maxlength="100"/>
											</div>
										</div>
										
                                		<div class="line">
											<div class="quarter bold">Date<span class="mandatory">*</span></div>
											<div class="quater">
												<input id="holidayDate" name="holidayDate" readonly="readonly" type="text" value="" />
												<img  src="./images/calendar.gif"   id="date_start_trigger" />	
												<script type="text/javascript">
												Calendar.setup({inputField :"holidayDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger",singleClick : true,step : 1});
												</script>
											</div>
                                 		</div>		
										<div class="line">
											<div class="quarter bold">Description</div>
											<div class="quater">
												<form:textarea path="description" id="description" cols="35" rows="3" style="resize: none;"></form:textarea>
											</div>
										</div>
										<div class="line">
											<div style="padding-left: 30%;">
												<div class="expbutton"><a href="javascript:manageHoliday();"> <span>Submit</span></a></div>
											</div>
											<div class="expbutton"><a href="javascript:resetHoliday();"><span>Clear</span></a></div>
										</div>	
									</div>
									<div class="height"><hr/></div>
									<div class="line" id="holidaysList">
								    	<jsp:include page="HolidayList.jsp"/>	
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
<!-- End:HolidaysMaster.jsp -->