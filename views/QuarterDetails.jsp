<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:QuarterDetails.jsp -->
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
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/script.js"></script>


<title>Manage Quarter Details</title>
</head>

<body onload="clearQuarter();">
	<form:form method="post" commandName="quarter">
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
								<div class="headTitle">Manage Quarter Details</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line" id="result"></div>
									<div class="line">
										<div class="quarter bold" id="labelType" style="margin-left:11px;">SFID</div>
										<div class="quarter"><form:input path="sfid" id="changeSfid" maxlength="15" onkeypress="funOnKeyPress(event,'editEmployee')"/></div>
										<div class="appbutton"><a href="javascript:changeEmployeeDetails('quarter');" style="text-decoration: none";>Submit</a></div>
									</div>
									<fieldset><legend><strong><font color='green'>Quarter Details</font></strong></legend>
										<div class="line">
											<div class="line">
												<div class="quarter">SFID</div>
												<div class="quarter">${changeSfid}</div>
												<div class="quarter">Name</div>
												<div class="quarter">${changeSfidName}</div>
											</div>
											<div class="line">
												<div class="quarter">Quarter Type<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:select path="quarterSubType"  id="quarterSubType" cssClass="formSelect" >
												 		<form:option value="Select">Select</form:option>
												 		<form:options items="${quarter.quarterSubTypeList}" itemLabel="quarterSubType" itemValue="id"></form:options>
												 	</form:select>
												</div>
												<div class="quarter">Quarter Number<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="quarterNo" id="quarterNo" maxlength="30"/>
												</div>
											</div>
											<div class="line">
												<div class="quarter">Occupied Date<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="occupiedDate" id="occupiedDate" cssClass="dateClass" readonly="true"/>
													<img  src="./images/calendar.gif" id="date_start_trigger1" />	
													<script type="text/javascript">
														Calendar.setup({inputField :"occupiedDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
													</script>
												</div>				
											</div>
											<div class="float">
												<div style="float:right" class="appbutton"><a class="quarterbutton" href="javascript:clearQuarter();">Clear</a></div>
												<div style="float:right" class="appbutton"><a class="quarterbutton" href="javascript:manageQuarter();">Submit</a></div>
											</div>		
										</div>
									</fieldset>
									<div class="height"><hr/></div>
									<div class="line" id="quartersList">
										<jsp:include page="QuarterList.jsp"></jsp:include>
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		</form:form>
	</body>
</html>
<!-- End:QuarterDetails.jsp -->