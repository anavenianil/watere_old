<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ErpEmployeeLeaveEntryHome.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/prompt.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jprompt.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/leave.js"></script>

<title>Manual Leave Entry</title>
</head>
<body>
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
								<div class="headTitle">Leave Days Manual Entry For New Employees</div>
								<div class="line">

									<%-- Content Page starts --%>
									<div class="line" id="result"></div>
									<div class="message"></div>
									<div class="line">
										<div class="quarter leftmar">Employee ID&nbsp;<span class="failure">*</span></div>
										<div class="quarter">
											<div class="quarter"><form:input path="entrySfid" id="entrySfid" maxlength="10" value="" onblur="javascript:fillErpAvailableLeaves(this.value);" /></div>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Annual Leave <font color="green">Balance</font>&nbsp;<span class="failure">*</span></div>
										<div class="quarter"><form:input path="annualLeaves" id="annualLeaves" maxlength="4"  onkeypress="javascript:return checkInt(event);"/></div>
																
									</div>
									<div class="line">
										<div class="quarter leftmar">Sick Leave <font color="green">Balance</font>&nbsp;<span class="failure">*</span></div>
										<div class="quarter"><form:input path="sickLeaves" id="sickLeaves" maxlength="4"   onkeypress="javascript:return checkInt(event);"/></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Maternity Leave <font color="green">Balance</font>&nbsp;<span class="failure">*</span></div>
										<div class="quarter"><form:input path="maternityLeaves" id="maternityLeaves" maxlength="4" onkeypress="javascript:return checkInt(event);"/></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Paternity Leave <font color="green">Balance</font> &nbsp;</div>
										<div class="quarter"><form:input path="peternityLeaves" id="peternityLeaves" maxlength="4"  onkeypress="javascript:return checkInt(event);"/></div>
										<%-- <div class="half">Note : In entire service an employee can able to take max of 90 days on without medical certificate</div>--%>
									</div>
									
									<div class="line">
										<div class="quarter leftmar">Compassionate Leave <font color="green">Balance</font>&nbsp;<span class="failure">*</span></div>
										<div class="quarter"><form:input path="compassionateLeaves" id="compassionateLeaves" maxlength="4" onkeypress="javascript:return checkInt(event);"/></div>
									</div>
								
									
								
								
									<div class="line">
										<div style="padding-left: 45%">
											<div><a href="javascript:submitErpEnterAvailableLeaves();" class="appbutton" style="text-decoration: none">Submit</a></div>
										    <div><a href="javascript:resetErpEnterAvailableLeaves();" class="appbutton" style="text-decoration: none">Clear</a></div>
									    </div>
									</div>
									<%-- Content Page end --%>

								</div>
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
		<script>
			resetErpEnterAvailableLeaves();
			JsonErpAvailabeLeavesList=<%= (net.sf.json.JSONArray)session.getAttribute("JsonErpAvailabeLeavesList") %>;			
		</script>
	</body>
</html>
<!-- End:EnterAvailableLeaves.jsp -->