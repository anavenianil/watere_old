<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:TadaAppliedUsers.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
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
<script type="text/javascript" src="script/tada.js"></script>
<script type="text/javascript" src="script/script.js"></script>

<title>Tada Applied Users</title>

</head>
<body>
	<form:form method="post" commandName="tada" id="tadaRequestBean" name="tada">
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
								<div class="headTitle">TD Transaction Search</div>
								<%-- Content Page starts --%>
								
									<div class="line" id="searchWith">
										<div class="quarter1_4"><strong>Search With:</strong></div>
										<div class="quarter1_4_tdu ">
										    
											  	
												<form:radiobutton path="searchWith" value="requestId" id="searchWith" label="RequestId" onclick="javascript:showTdSearchWithOption();"></form:radiobutton>
										        </div>
												<div class="quarter1_4_tdu ">
												<form:radiobutton path="searchWith" value="sfid" id="searchWith" label="Sfid" onclick="javascript:showTdSearchWithOption();"></form:radiobutton>
												</div>	<div class="quarter1_4_tdu ">
												<form:radiobutton path="searchWith" value="directorate" id="searchWith" label="Directorate" onclick="javascript:showTdSearchWithOption();"></form:radiobutton>
											</div>	<div class="quarter1_4_tdu " style="width: 18%;">
												<form:radiobutton path="searchWith" value="entitlementexemption" id="searchWith" label="EntitlementExemption" onclick="javascript:showTdSearchWithOption();"></form:radiobutton>
												</div>
										 <br></br>
										 </div>
										
										
										
									<div class="line" id="datesDiv">
									<div class="quarter1_4"><strong>Dates:<span class="mandatory">*</span></strong></div>
										<div class="quarter1_4_tdu " style="text-align: right; font-weight: bold;">From Date<span class="mandatory">*</span></div>
										<div class="quarter1_4_tdu">
										    <input type="text" readonly="readonly" name="departureDate" id="departureDate" size="10" />
										    <script type="text/javascript">
												new tcal({'formname':'tada','controlname':'departureDate'});
											</script>
										</div>
										<div class="quarter1_4_tdu " style="text-align: right;font-weight: bold;">To Date<span class="mandatory">*</span></div>
										<div class="quarter1_4_tdu">
										    <input type="text" readonly="readonly" name="arrivalDate" id="arrivalDate" size="10" />
										    <script type="text/javascript">
												new tcal({'formname':'tada','controlname':'arrivalDate'});
											</script>
										</div><br></br>
									</div>
								
										<div class="line">
										<div id="requestTypeDiv">
										<div class="quarter1_4 "><strong>Request Type</strong></div>
										<div class="quarter1_4_tdu leftmar">
										    <form:select path="requestType" id="requestType" cssStyle="width:142px">
												<form:option value="Select">Select</form:option>
												<form:option value="45">TD Build-Up</form:option>
												<form:option value="46">TD Project</form:option>
												<form:option value="47">TD Advance</form:option>
												<form:option value="48">TD Settlement</form:option>
												<form:option value="49">TD Reimbursement</form:option>
											</form:select>
										</div><br></br>
										</div></div>
									
									<div class="line">
										<div id="requestIdDiv" style="display: none">
										<div class="quarter1_4"><strong>Request Id<span class="mandatory">*</span></strong></div>
										<div class="quarter1_4_tdu leftmar">
										    <form:input path="requestId" id="requestId" maxlength="20" onkeypress="javascript:return checkInt(event);" />
										</div><br><br>
										</div>
										<div id="sfidDiv" style="display: none">
										<div class="quarter1_4"><strong>SFID<span class="mandatory">*</span></strong></div>
										<div class="quarter leftmar">
										    <form:input path="sfID" id="sfID" maxlength="20" />
										</div><br><br>
										</div>
										<div id="directorateDiv" style="display: none">
										<div class="quarter1_4"><strong>Directorate<span class="mandatory">*</span></strong></div>
										<div class="quarter leftmar">
										    <form:select path="directorate" id="directorate" cssStyle="width:142px">
												<form:option value="Select">Select</form:option>
												<form:options items="${tadaRequestBean.directorateList}" itemValue="id" itemLabel="deptName" />
											</form:select>
										</div><br></br>
										</div>
									</div>
									
									
									<div class="line" id="statusDiv">
										<div class="quarter1_4 "><strong>Status<span class="mandatory">*</span></strong></div>
										<div id="check">
										    <input type="checkbox" name="status" id="completedStatus" value="8" checked="checked">Completed</input>
										    <input type="checkbox" name="status" id="pendingStatus" value="2">Pending</input>
										    <input type="checkbox" name="status" id="declinedStatus" value="6">Declined</input>
										    <input type="checkbox" name="status" id="cancelledStatus" value="9">Cancelled</input>
										    <input type="checkbox" name="status" id="waitingStatus" value="103">Hold</input>
										</div><br></br>
									</div>
								    <div class="line" style="margin-left: 35%;">
											<div class="expbutton"><a onclick="javascript:searchAppliedUsers();"> <span>Search</span></a></div>
											<div class="expbutton"><a onclick="javascript:clearAppliedUsers();"> <span>Clear</span></a></div>
									</div>	
									<div class="line"><hr /></div>
									<div class="line" id="tadaAppliedUsersList" style="display: none">
										<jsp:include page="TadaAppliedUsersList.jsp"></jsp:include>								
									</div>																
								</div>					
								<%-- Content Page end --%>
								<div>&nbsp;</div>
							</div>
						
						<div class="whitebox_b_non">
							<div class="whitebox_bl">
								<div class="whitebox_br"></div>
							</div>
						</div></div>
					</div>
				</div>
			</div>
			<div><jsp:include page="Footer.jsp" /></div>			
		
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="message"/>
		<form:hidden path="historyID"/>
		<form:hidden path="statusMsg"/>
		<form:hidden path="jsonValue"/>
		<form:hidden path="back"/>
		</form:form>
		</body>	
	
</html>
<!-- End:TadaAppliedUsers.jsp -->
