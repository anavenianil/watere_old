<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ConfigurationDetails.jsp -->

<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/tabs.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>


<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>
<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>

<title>Configuration Details</title>

<script type="text/javascript">
$jq(function() {
	var $tabs = $('#tabs').tabs();
	$jq(".ui-tabs-panel").each(function(i){
		var totalSize = $jq(".ui-tabs-panel").size() - 1;

		if (i != totalSize) {
			next = i + 2;
			$jq(this).append("<a href='#' class='next-tab mover' rel='" + next + "'>Next Page &#187;</a>");
			
		}
  
		if (i != 0) {
			prev = i;
			$jq(this).append("<a href='#' class='prev-tab mover' rel='" + prev + "'>&#171; Prev Page</a>");
			
		}
  		
	});

	$jq('.next-tab, .prev-tab').click(function() { 
		$tabs.tabs('select', $(this).attr("rel"));
		return false;
	});
      
});
</script>
<style>
.line{
	margin-bottom:15px;
}
</style>
</head>
<body>
<form:form method="post" id="configuration" name="configuration">
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
							<div class="headTitle">Configuration Details</div>
							<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
							<%-- Content Page starts --%>
							<div>
							<div id="result"></div>
								<div>
									<div id="page-wrap">		
										<div id="tabs">
											<ul>        		
								        		<li><a href="#fragment-1" onclick="javascript:clearMessage()">PIS</a></li>
								        		<li><a href="#fragment-2" onclick="javascript:clearMessage()">Admin</a></li>
								        		<li><a href="#fragment-3" onclick="javascript:clearMessage()">Leave</a></li>
								        	</ul>
								        	<div id="fragment-1" class="ui-tabs-panel">
												<div id="pis">
													<div class="line">
														<div class="quarter bold leftmar">EscalationTime</div>
														<div class="quarter"><form:input path="escalationtime" id="escalationtime" maxlength="3" onkeypress="javascript:return checkInt(event);"/> days</div>
													</div>
													<div class="line">
														<div class="quarter bold leftmar">Admin</div>
														<div class="quarter">
															<form:select path="admin" id="admin" cssClass="admin" onmouseover="setSelectWidth('#admin')">
																<form:option value="select">Select</form:option>
																<form:options items="${sessionScope.orgInstanceList}" itemLabel="name" itemValue="id"></form:options>
															</form:select>
	
														</div>
													</div>
													<div class="line">
														<div class="quarter bold leftmar">Min Age for Govt Job</div>
														<div class="quarter"><form:input path="minAgeForJob" id="minAgeForJob" maxlength="2" onkeypress="javascript:return checkInt(event);"/></div>
													</div>
													<div class="line">
														<div class="quarter bold leftmar">DashBoard Records Limit</div>
														<div class="quarter"><form:input path="dashboardRecords" id="dashboardRecords" maxlength="2" onkeypress="javascript:return checkInt(event);"/></div>
													</div>
													<div class="line">
														<div class="quarter bold leftmar">DashBoard MyRequest Records Limit</div>
														<div class="quarter"><form:input path="myRequestDashboardRecords" id="myRequestDashboardRecords" maxlength="2" onkeypress="javascript:return checkInt(event);"/></div>
													</div>
													<div class="line" style="margin-left:50%">
														<div class="quarter"><a href="javascript:saveConfiguration('pis');">
														  <div class="appbutton">Submit</div></a>
														</div>										  
													</div>
												</div>
		     								</div>
		     	
		     								 <div id="fragment-2" class="ui-tabs-panel ui-tabs-hide">
		                						<fieldset><legend><strong><font color='green'>CGHS Cfg Details</font></strong></legend>
													<div>
														<div class="line">
															<div class="quarter bold leftmar">CGHS Major Age Limit</div>
															<div class="quarter"><form:input path="cghsMajorAgeLimit" id="cghsMajorAgeLimit" maxlength="2" onkeypress="javascript:return checkInt(event);"/></div>
															<div class="quarter bold leftmar">CGHS Son Age Limit</div>
															<div class="quarter"><form:input path="cghsSonAgeLimit" id="cghsSonAgeLimit" maxlength="2" onkeypress="javascript:return checkInt(event);"/></div>
														</div>
														<div class="line">
															<div class="quarter bold leftmar">CGHS Salary Limit</div>
															<div class="quarter"><form:input path="cghsSalaryLimit" id="cghsSalaryLimit" maxlength="4" onkeypress="javascript:return checkInt(event);"/></div>
															<div class="quarter bold leftmar">CGHS Advance Appr Perc</div>
															<div class="quarter"><form:input path="cghsAdvApprPerc" id="cghsAdvApprPerc" maxlength="2" onkeypress="javascript:return checkInt(event);"/></div>
														</div>
														<div class="line" style="margin-left:50%">
															<div class="quarter">
																<a href="javascript:saveConfiguration('cghs');">
																<div class="appbutton">Submit</div></a>
															</div>										  
														</div>
													</div>
												</fieldset>
											<!--start TADA Cfg Details  -->
											<fieldset><legend><strong><font color='green'>TADA Cfg Details</font></strong></legend>                                                 <!--This time bount of tada request of past and future  -->
													<div>
														<div class="line">
															<div class="quarter bold leftmar">Tada Request TimeBoundOf Past</div>
															<div class="quarter"><form:input path="tadaTimeBoundPast" id="tadaTimeBoundPast" maxlength="2" onkeypress="javascript:return checkInt(event);"/></div>
															<div class="quarter bold leftmar">Tada Request TimeBoundOf Future</div>
															<div class="quarter"><form:input path="tadaTimeBoundFuture" id="tadaTimeBoundFuture" maxlength="2" onkeypress="javascript:return checkInt(event);"/></div>
														</div>								
														<div class="line" style="margin-left:50%">
															<div class="quarter">
																<a href="javascript:saveConfiguration('tada');">
																<div class="appbutton">Submit</div></a>
															</div>										  
														</div>
													</div>
												</fieldset>
											<!--start TADA Cfg Details  -->
												<fieldset><legend><strong><font color='green'>LTC Cfg Details</font></strong></legend>
													<div>
														<div class="line">
															<div class="quarter bold leftmar">LTC Prior Dob</div>
															<div class="quarter">
																<form:input path="ltcPriorDob" id="ltcPriorDob" cssClass="dateClass" readonly="true"/>
																<script type="text/javascript">
															new tcal({'formname':'configuration','controlname':'ltcPriorDob'});
															</script>											
															</div>							
															<div class="quarter bold leftmar">LTC Junior Work Exp</div>
															<div class="quarter"><form:input path="ltcJuniorWorkExp" id="ltcJuniorWorkExp" maxlength="2" onkeypress="javascript:return checkInt(event);"/></div>
														</div>
														<div class="line">
															<div class="quarter bold leftmar">LTC Senior Work Exp</div>
															<div class="quarter"><form:input path="ltcSeniorrWorkExp" id="ltcSeniorrWorkExp" maxlength="2" onkeypress="javascript:return checkInt(event);"/></div>
															<div class="quarter bold leftmar">LTC Son Age Limit</div>
															<div class="quarter"><form:input path="ltcSonAgeLimit" id="ltcSonAgeLimit" maxlength="2" onkeypress="javascript:return checkInt(event);"/></div>
														</div>
														<div class="line">
															<div class="quarter bold leftmar">LTC Major Age Limit</div>
															<div class="quarter"><form:input path="ltcMajorAgeLimit" id="ltcMajorAgeLimit" maxlength="2" onkeypress="javascript:return checkInt(event);"/></div>
															<div class="quarter bold leftmar">LTC Advance Percentage</div>
															<div class="quarter"><form:input path="ltcAdvancePercentage" id="ltcAdvancePercentage" maxlength="2" onkeypress="javascript:return checkInt(event);"/></div>
														</div>
														<div class="line">
															<div class="quarter bold leftmar">LTC Advance Request Prior Days</div>
															<div class="quarter"><form:input path="ltcAdvancePriorDays" id="ltcAdvancePriorDays" maxlength="2" onkeypress="javascript:return checkInt(event);"/></div>
															<div class="quarter bold leftmar">LTC DA Percentage</div>
															<div class="quarter"><form:input path="ltcDaPercengate" id="ltcDaPercengate" maxlength="2" onkeypress="javascript:return checkInt(event);"/></div>
														</div>
														<div class="line" style="margin-left:50%">
															<div class="quarter"><a href="javascript:saveConfiguration('ltc');">
															<div class="appbutton">Submit</div></a>
														</div>										  
													</div>
													</div>
												</fieldset>
		        							</div>
		    	         	
		         							<div id="fragment-3" class="ui-tabs-panel ui-tabs-hide">
		    									<div class="line">
													<div class="quarter bold leftmar">Leave Management Starting Date</div>
													<div class="quarter">
														<form:input path="leaveStartDate" id="leaveStartDate" cssClass="dateClass" readonly="true"/>
															<script type="text/javascript">
															new tcal({'formname':'configuration','controlname':'leaveStartDate'});
															</script>		
													</div>							
												</div>
												<div class="line">
													<div class="quarter bold leftmar">CML WO MC EXCEPTIONID</div>
													<div class="quarter">
														<form:select path="cmlWoMcExceptionId" id="cmlWoMcExceptionId" cssClass="admin" onmouseover="setSelectWidth('#cmlWoMcExceptionId')">
															<form:option value="">Select</form:option>
															<form:options items="${command.cmlWoMcExceptionIdList}" itemLabel="value" itemValue="key"></form:options>
														</form:select>
													</div>
												</div>
												<div class="line">
													<div class="quarter bold leftmar">LND WO MC EXCEPTIONID</div>
													<div class="quarter">
														<form:select path="lndWoMcExceptionId" id="lndWoMcExceptionId" cssClass="admin" onmouseover="setSelectWidth('#lndWoMcExceptionId')">
															<form:option value="">Select</form:option>
															<form:options items="${command.lndWoMcExceptionIdList}" itemLabel="value" itemValue="key"></form:options>
														</form:select>
													</div>
												</div>
												<div class="line" style="margin-left:50%">
													<div class="quarter"><a href="javascript:saveConfiguration('leave');">
													<div class="appbutton">Submit</div></a>
												</div>
		         							</div>         	
       									</div>
       									 
       									
									</div>
								</div>
								
								
								
								<!-- <div class="line">
									<fieldset><legend><strong><font color='green'> Leave Management Configurations </font></strong></legend>
									<div id="leave"></div>
									<div class="line">
										<div class="half bold leftmar">Max LTC leaves encashment to EL</div>
										<div class="half"><form:input path="maxLTCEncasementToEL" id="maxLTCEncasementToEL" maxlength="3" /> days</div>
									</div>
									<div class="line">
										<div class="half bold leftmar">Min ELs Available after LTC encashment</div>
										<div class="half"><form:input path="minELAvailAftLTCEncashment" id="minELAvailAftLTCEncashment" maxlength="3" /> days</div>
									</div>
									<div class="line">
										<div class="half bold leftmar">Max LTC leaves encashment to EL in service</div>
										<div class="half"><form:input path="maxLTCEncashmentToELService" id="maxLTCEncashmentToELService" maxlength="3" /> days</div>
									</div>
									
									<div class="line">
										<div class="half bold leftmar">Commuted leave will available only if EL not available</div>
										<div class="half">
											<form:select path="commLeaveAvailELAvail" id="commLeaveAvailELAvail" cssClass="formSelect" onmouseover="setSelectWidth('#commLeaveAvailELAvail')">
												<form:option value="select">Select</form:option>
												<form:option value="1">Yes</form:option>
												<form:option value="0">No</form:option>
											</form:select>
										</div>
									</div>
									<div class="line" id="commLeaveAvailELAvailDescDiv">
										<div class="half bold leftmar">Description</div>
										<div class="half">
											<form:textarea path="commLeaveAvailELAvailDesc" id="commLeaveAvailELAvailDesc" rows="4" cols="35"/>											
										</div>
									</div>
									<div class="line">
										<div class="half bold leftmar">Maternity Leave eligible for </div>
										<div class="half"><form:input path="maternityLeaveAvailFor" id="maternityLeaveAvailFor" maxlength="3" /> children</div>
									</div>
									<div class="line">
										<div class="half bold leftmar">Peternity Leave eligible for </div>
										<div class="half"><form:input path="peternityLeaveAvailFor" id="peternityLeaveAvailFor" maxlength="3" /> children</div>
									</div>
									<div class="line">
										<div class="half bold leftmar">Max days for Leaves conversion after use </div>
										<div class="half"><form:input path="maxLeaveConversion" id="maxLeaveConversion" maxlength="3" /> days</div>
									</div>
									<div class="line" style="margin-left:50%">
										<div class="half"><a href="javascript:saveConfiguration('leave');">
										  <div class="appbutton">Submit</div></a>
										</div>										  
									</div>
									</fieldset>								
								</div>-->
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
	<form:hidden path="param" />
	<form:hidden path="type" />
	<form:hidden path="configurationDetails" />
</form:form>
</body>
</html>
<!-- End:ConfigurationDetails.jsp -->