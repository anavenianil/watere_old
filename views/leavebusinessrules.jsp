<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin: leavebusinessrules.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
</head>

<body>
	<form:form method="post" commandName="leaveAdmin" name="leaveBusinessRules">
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
									<div class="headTitle">Leave Business Rules</div>
								
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter leftmar">Request Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="requestType" id="requestType" cssClass="formSelect">
												<form:option value="select">Select</form:option>
												<form:option value="request">Leave Request</form:option>
												<form:option value="conversion">Leave Conversion</form:option>
												<form:option value="cancellation">Leave Cancellation</form:option>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter bold">Leave Type 1</div>
										<div class="quarter">
											<form:select path="one" id="one" cssClass="formSelect" >
												<form:option value="select">Select</form:option>
											</form:select>
										</div>
										<div class="quarter bold">Leave Type 2</div>
										<div class="quarter">
											<form:select path="two" id="two" cssClass="formSelect" >
												<form:option value="select">Select</form:option>
												<form:options items="" itemValue="" itemLabel="" />
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter bold">Leave Type 3</div>
										<div class="quarter">
											<form:select path="three" id="three" cssClass="formSelect" >
												<form:option value="select">Select</form:option>
											</form:select>
										</div>
										<div class="quarter bold">Leave Type 4</div>
										<div class="quarter">
											<form:select path="four" id="four" cssClass="formSelect" >
												<form:option value="select">Select</form:option>
												<form:options items="" itemValue="" itemLabel="" />
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter bold">Leave Type 5</div>
										<div class="quarter">
											<form:select path="five" id="five" cssClass="formSelect" >
												<form:option value="select">Select</form:option>
												<form:options items="" itemValue="" itemLabel="" />
											</form:select>
										</div>
									</div>
									<div class="line">
										
									</div>
									<div class="line">
										<div class="quarter bold">Result value<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:radiobutton path="resultValue" id="resultValue1" value="Y" label="Years"/>
											<form:radiobutton path="resultValue" id="resultValue2" value="M" label="Months"/>
											<form:radiobutton path="resultValue" id="resultValue3" value="D" label="Days"/>
										</div>
										<div class="quarter">
											<form:input path="resultField" id="resultField" /><span id="duration"></span>
										</div>
									</div>
									<div class="line">
										<div class="quarter bold">Remarks<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:textarea path="remarks" id="remarks" cols="40" rows="4"/>
										</div>
									</div>
									<div class="line">
										<div style="padding-left: 45%;">
											<div><a href="javascript: submitBusinessRules();"  class="appbutton" style="text-decoration: none;">Submit</a></div>
											<div><a href="javascript: clearBusinessRules();" class="appbutton" style="text-decoration: none;">Clear</a></div>
										</div>
									</div>
									<div class="line"><hr /></div>
									<div id="result">

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
		<form:hidden path="param" id="param"/>
		<form:hidden path="type"/>
		</form:form>		
	</body>
</html>
<!-- End: leavebusinessrules.jsp -->