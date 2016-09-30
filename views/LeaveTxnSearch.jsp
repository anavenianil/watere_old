<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LeaveTxnSearch.jsp -->
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
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<title>Leave Transaction Search</title>
</head>

<body onload="javascript: resetTxnLeave();">
	<form:form method="post" commandName="leaveAdmin">
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
								<div class="headTitle" >Leave Transaction Search</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
									<div class="line">
											<div class="quarter bold">SFID</div>
											<div class="half">
												<form:input path="searchSfid" id="searchSfid" maxlength="10" onkeypress="javascript:return isAlphaNumaricExp(event);" />
											</div>
										</div>
										<div class="line">
											<div class="quarter bold">Leave Types</div>
											<div class="quarter bold">
												<form:select path="leaveType" id="leaveType" cssClass="formSelect" >
													<form:option value="select">Select</form:option>
													<form:options items="${leaveAdmin.leaveTypeList}" itemValue="leaveType" itemLabel="leaveType"/>
												</form:select>												
											</div>
										</div>										
										
                                		<%-- <div class="line">
											<div class="quarter bold">Txn Date</div>
											<div class="quater">
												<form:input path="txnDate" id="txnDate" readonly="true"/>
												<img  src="./images/calendar.gif"  id="date_start_trigger" />	
												<script type="text/javascript">
													Calendar.setup({inputField :"txnDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger",singleClick : true,step : 1});
												</script>
											</div>
                                 		</div> --%>		
										<div class="line">
											<div class="quarter bold">From Date<span class="mandatory">*</span></div>
											<div class="quater">
												<form:input path="fromDate" id="fromDate" readonly="true"/>
												<img  src="./images/calendar.gif"  id="date_start_trigger" />	
												<script type="text/javascript">
													Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger",singleClick : true,step : 1});
												</script>
											</div>
                                 		</div>
                                 		<div class="line">
											<div class="quarter bold">To Date<span class="mandatory">*</span></div>
											<div class="quater">
												<form:input path="toDate" id="toDate" readonly="true"/>
												<img  src="./images/calendar.gif"  id="date_end_trigger" />	
												<script type="text/javascript">
													Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_end_trigger",singleClick : true,step : 1});
												</script>
											</div>
                                 		</div>
                                 		<div class="line" id="leaveTxnTypeDiv">
                                 			<div class="quarter bold">Leave Transaction Type</div>
											<div class="half">
												<input type="checkbox" id="txnType" name="pendingStatus" value="2" />&nbsp;Pending&nbsp;
												<input type="checkbox" id="txnType" name="completedStatus" value="8" />&nbsp;Completed&nbsp;
										    	<input type="checkbox" id="txnType" name="sanctionedStatus" value="29" />&nbsp;Sanctioned&nbsp;
										    	<input type="checkbox" id="txnType" name="cancelledStatus" value="9" />&nbsp;Cancelled&nbsp;
										    	<input type="checkbox" id="txnType" name="declinedStatus" value="6" />&nbsp;Declined<br/>
										    	<input type="checkbox" id="txnType" name="auditingStatus" value="40" />&nbsp;Auditing&nbsp;
										    	<input type="checkbox" id="txnType" name="correctionStatus" value="42" />&nbsp;Correction&nbsp;
											    <input type="checkbox" id="txnType" name="creditLapsStatus" value="41" />&nbsp;Credit/Laps&nbsp;
											    <input type="checkbox" id="txnType" name="previousRequestStatus" value="59" />&nbsp;Previous Request&nbsp;
											    <input type="checkbox" id="txnType" name="ltcEncashedStatus" value="43" />&nbsp;LTC Encashed
											</div>
										</div>
										<div class="line">
											<div style="margin-left:30%;">
												<div class="expbutton"><a href="javascript:searchTxnLeave();"><span>Search</span></a></div>
											</div>
											<div class="expbutton"><a href="javascript:resetTxnLeave();"><span>Clear</span></a></div>
										</div>	
									</div>
									<div class="height"><hr/></div>
									<div class="line" id="leaveTxnSearchList">
								    	<jsp:include page="LeaveTxnSearchList.jsp"/>	
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
<!-- End:LeaveTxnSearch.jsp .jsp -->