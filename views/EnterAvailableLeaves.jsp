<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:EnterAvailableLeaves.jsp -->
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
								<div class="headTitle">Leave Manual Entry For New Employees</div>
								<div class="line">

									<%-- Content Page starts --%>
									<div class="line" id="result"></div>
									<div class="message"></div>
									<div class="line">
										<div class="quarter leftmar">SFID&nbsp;<span class="failure">*</span></div>
										<div class="quarter">
											<div class="quarter"><form:input path="entrySfid" id="entrySfid" maxlength="10" value="SF0" onblur="javascript:fillAvailableLeaves(this.value);getSpellsDetails();" /></div>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Earned Leave <font color="green">Balance</font>&nbsp;<span class="failure">*</span></div>
										<div class="quarter"><form:input path="earnedLeaveId" id="earnedLeaveId" maxlength="4" onkeypress="return checkSignFloat(event, 'earnedLeaveId', '+');"/></div>
										<div class="half">
											<form:input path="ltcEncashmentDays" id="ltcEncashmentDays" maxlength="4" value="LTC Encashed Days" cssStyle="color:#A29A9A" onblur="javascript:appendText('LTC Encashed Days','ltcEncashmentDays')" onclick="javascript:clearText('LTC Encashed Days','ltcEncashmentDays');" onkeyup="javascript:changeColor('ltcEncashmentDays');" onkeypress="return  checkInt(event);"/>&nbsp;&nbsp;
										</div>									
									</div>
									<div class="line">
										<div class="quarter leftmar">Half Pay Leave <font color="green">Balance</font>&nbsp;<span class="failure">*</span></div>
										<div class="quarter"><form:input path="halfPayLeaveId" id="halfPayLeaveId" maxlength="4" onkeypress="return checkSignFloat(event,'halfPayLeaveId','-');"/></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Casual Leave <font color="green">Balance</font>&nbsp;<span class="failure">*</span></div>
										<div class="quarter"><form:input path="casualLeaveId" id="casualLeaveId" maxlength="4" onkeypress="return checkSignFloat(event,'casualLeaveId','+');"/></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">CML <font color="red">Availed</font> &nbsp;</div>
										<div class="quarter"><form:input path="cmlwomc" id="cmlwomc" maxlength="4" onkeypress="return checkSignFloat(event,'cmlwomc','+');"/></div>
										<%-- <div class="half">Note : In entire service an employee can able to take max of 90 days on without medical certificate</div>--%>
									</div>
									<div class="line">
										<div class="quarter leftmar">Leave Not Due <font color="green">Balance</font>&nbsp;</div>
										<div class="quarter"><form:input path="leaveNotDue" id="leaveNotDue" maxlength="4" onkeypress="return checkSignFloat(event,'leaveNotDue','+');"/></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">LND <font color="red">Availed</font> without MC  &nbsp;</div>
										<div class="quarter"><form:input path="leaveNotDuewomc" id="leaveNotDuewomc" maxlength="4" onkeypress="return checkSignFloat(event,'leaveNotDuewomc','+');"/></div>
										<%--<div class="half">Note : In entire service an employee can able to take max of 180 days on without medical certificate</div>--%>
									</div>
									<div class="line">
										<div class="quarter leftmar">Child Care Leave <font color="green">Balance</font></div>
										<div class="quarter"><form:input path="childCareLeave" id="childCareLeave" maxlength="4" onkeypress="return checkSignFloat(event,'childCareLeave','+');"/></div>
										<div class="half">
											<form:input path="cclYearSpells" id="cclYearSpells" maxlength="4" value="Availed Spells in year" cssStyle="color:#A29A9A" onblur="javascript:appendText('Availed Spells in year','cclYearSpells')" onclick="javascript:clearText('Availed Spells in year','cclYearSpells');" onkeyup="javascript:changeColor('cclYearSpells');"/>&nbsp;&nbsp;
											<%--<form:input path="cclServiceSpells" id="cclServiceSpells" value="Availed Spells in service" cssStyle="color:#A29A9A" onblur="javascript:appendText('Availed Spells in service','cclServiceSpells')" onclick="javascript:clearText('Availed Spells in service','cclServiceSpells');" onkeyup="javascript:changeColor('cclServiceSpells');"/> --%>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Study Leave <font color="green">Balance</font>&nbsp;</div>
										<div class="quarter"><form:input path="studyLeave" id="studyLeave" maxlength="4" onkeypress="return checkSignFloat(event,'studyLeave','+');"/></div>
										<%--<div class="half">
											<form:input path="slYearSpells" id="slYearSpells" value="Availed Spells in year" cssStyle="color:#A29A9A" onblur="javascript:appendText('Availed Spells in year','slYearSpells')" onclick="javascript:clearText('Availed Spells in year','slYearSpells');" onkeyup="javascript:changeColor('slYearSpells');"/>&nbsp;&nbsp;
											<form:input path="slServiceSpells" id="slServiceSpells" value="Availed Spells in service" cssStyle="color:#A29A9A" onblur="javascript:appendText('Availed Spells in service','slServiceSpells')" onclick="javascript:clearText('Availed Spells in service','slServiceSpells');" onkeyup="javascript:changeColor('slServiceSpells');"/>
										</div> --%>
									</div>
									<div class="line">
										<div class="quarter leftmar">EOL <font color="red">Availed</font> with MC</div>
										<div class="quarter"><form:input path="eolLeaveId" id="eolLeaveId" maxlength="4" onkeypress="return checkSignFloat(event,'eolLeaveId','+');"/></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">EOL <font color="red">Availed</font> without MC at present</div>
										<div class="quarter"><form:input path="eolWOMCLeaveId" id="eolWOMCLeaveId" maxlength="4" onkeypress="return checkSignFloat(event,'eolWOMCLeaveId','+');"/></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">EOL <font color="red">Availed</font> without MC in entire service</div>
										<div class="quarter"><form:input path="eolWOMCInSerive" id="eolWOMCInSerive" maxlength="4" onkeypress="return checkSignFloat(event,'eolWOMCInSerive','+');"/></div>
									</div>
									<div class="line">
										<div style="padding-left: 45%">
											<div><a href="javascript:submitEnterAvailableLeaves();" class="appbutton" style="text-decoration: none">Submit</a></div>
										    <div><a href="javascript:resetEnterAvailableLeaves();" class="appbutton" style="text-decoration: none">Clear</a></div>
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
			resetEnterAvailableLeaves();
			jsonAvailabeLeavesList=<%= (net.sf.json.JSONArray)session.getAttribute("JsonAvailabeLeavesList") %>;			
		</script>
	</body>
</html>
<!-- End:EnterAvailableLeaves.jsp -->