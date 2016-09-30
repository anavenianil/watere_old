<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LeaveAdit.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="./script/RegExpValidate.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<title>Leave Audit</title>
</head>

<body>
	<form:form method="post" commandName="leaveAdmin" id="leaveAdit">
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
								<div class="headTitle" >Leave Audit</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="line" id="result"></div>
										<div class="line">
											<div class="quarter bold">SFID<span class="mandatory">*</span></div>
											<div class="half">
												<form:input path="searchSfid" id="searchSfid" maxlength="10" onchange="javascript:getAvailableLeaves();" onkeypress="javascript: return isAlphaNumaricExp(event);" />
											</div>
										</div>
										<div class="line">
											<div class="quarter bold">Leave Type<span class="mandatory">*</span></div>
											<div class="quarter bold">
												<form:select path="leaveType" id="leaveType" cssClass="formSelect"  onchange="javascript:getAvailableLeaves()">
													<form:option value="0">Select</form:option>
													<form:options items="${leaveAdmin.leaveTypeList}" itemValue="id" itemLabel="leaveType"/>
												</form:select>												
											</div>
											<div id="availableLeaves" class="quarter bold success"></div>
										</div>										
										<div class="line">
											<div class="quarter bold">Txn Type<span class="mandatory">*</span></div>
											<div class="quarter bold">
												<form:select path="txnType" id="txnType" cssClass="formSelect" >
													<form:option value="select">Select</form:option>
													<form:option value="40">AUDIT</form:option>
													<form:option value="42">Correction</form:option>
													<form:option value="43">LTC</form:option>
												</form:select>												
											</div>
										</div>
                                		<div class="line">
											<div class="quarter bold">Txn Date<span class="mandatory">*</span></div>
											<div class="quater">
												<form:input path="txnDate" id="txnDate" readonly="true"/>
												<img  src="./images/calendar.gif"  id="date_start_trigger" />	
												<script type="text/javascript">
													Calendar.setup({inputField :"txnDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger",singleClick : true,step : 1});
												</script>
											</div>
                                 		</div>	
                                 		<div class="line">
											<div class="quarter bold">Number of Leaves<span class="mandatory">*</span></div>
											<div class="half">
												<form:input path="noOfDays" id="noOfDays" maxlength="5" onchange="javascript:manageLeaves();" />
											</div>
										</div>
										<div class="line">
										 <div class="quarter bold" >Remarks<span class="mandatory">*</span></div>
											 <div class="quater">
											  <form:textarea path="remarks" id="remarks" cols="20" rows="5" ></form:textarea>
											 </div>
										</div>	
										
										<div class="line">
											<div style="margin-left:24%;">
												<div class="expbutton"><a href="javascript:saveLeaveAdit();"><span>Submit</span></a></div>
											</div>
											<div class="expbutton"><a href="javascript:resetLeaveAdit();"><span>Clear</span></a></div>
										</div>
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
<!-- End:LeaveAdit.jsp -->