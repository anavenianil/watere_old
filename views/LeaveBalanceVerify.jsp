<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LeaveBalanceVerify.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />

<link href="styles/Checkbox-Background.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/leave.js"></script>

<title>Leave Balance Verification</title>
<!-- <style>
	.regular-checkbox + label {
	    background-color: #99FF00;
	    border: 1px solid #3F7AB5;
	    box-shadow: 0 1px 2px rgba(0,0,0,0.05), inset 0px -15px 10px -12px rgba(0,0,0,0.05);
	    padding: 7px;
	    border-radius: 3px;
	    display: inline-block;
	    position: relative;
	}
	.regular-checkbox:checked + label {
	    background-color: #99FF00;
	    border: 1px solid #3F7AB5;
	    box-shadow: 0 1px 2px rgba(0,0,0,0.05), inset 0px -15px 10px -12px rgba(0,0,0,0.05), inset 15px 10px -12px rgba(255,255,255,0.1);
	    color: #99a1a7;
	}
</style> -->
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
								<div class="headTitle">Leave Balance Verification</div>
								<div class="line">

									<%-- Content Page starts --%>
									<div class="message"></div>
									<div class="line">
										<div   class="quarter bold leftmar">Designation<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="designationID" id="designationID" cssClass="formSelect"  onmouseover="setSelectWidth('#designationID')" onchange="javascript:getSearchEmployees();">
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.designationList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">SFID&nbsp;<span class="failure">*</span></div>
										<div class="quarter"><form:input path="entrySfid" id="entrySfid" maxlength="10" disabled="true" onkeyup="javascript:fillAvailableLeaves(this.value);" onblur="javascript:getSpellsDetails()"/></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Earned Leave <font color="green">Balance</font>&nbsp;<span class="failure">*</span></div>
										<div class="quarter"><form:input path="earnedLeaveId" id="earnedLeaveId" maxlength="4" onkeypress="return checkSignFloat(event,'earnedLeaveId','+');"/></div>
										<div class="half">
											<form:input path="ltcEncashmentDays" id="ltcEncashmentDays" maxlength="4" value="LTC Encashed Days" cssStyle="color:#A29A9A"  onkeypress="return  checkInt(event);" onblur="javascript:appendText('LTC Encashed Days','ltcEncashmentDays')" onclick="javascript:clearText('LTC Encashed Days','ltcEncashmentDays');" onkeyup="javascript:changeColor('ltcEncashmentDays');" />&nbsp;&nbsp;&nbsp;&nbsp;
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
										<%-- <div class="quarter leftmar">CML on without medical certificate applied till date&nbsp;<span class="failure">*</span></div>--%>
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
											<form:input path="cclYearSpells" id="cclYearSpells" value="Availed Spells in year" cssStyle="color:#A29A9A" onblur="javascript:appendText('Availed Spells in year','cclYearSpells')" onclick="javascript:clearText('Availed Spells in year','cclYearSpells');" onkeyup="javascript:changeColor('cclYearSpells');"/>&nbsp;&nbsp;
											<%--<form:input path="cclServiceSpells" id="cclServiceSpells" value="Availed Spells in service" cssStyle="color:#A29A9A" onblur="javascript:appendText('Availed Spells in service','cclServiceSpells')" onclick="javascript:clearText('Availed Spells in service','cclServiceSpells');" onkeyup="javascript:changeColor('cclServiceSpells');"/>--%>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Study Leave <font color="green">Balance</font>&nbsp;</div>
										<div class="quarter"><form:input path="studyLeave" id="studyLeave" maxlength="4" onkeypress="return checkSignFloat(event,'studyLeave','+');"/></div>
										<%--<div class="half">
											<form:input path="slYearSpells" id="slYearSpells" value="Availed Spells in year" cssStyle="color:#A29A9A" onblur="javascript:appendText('Availed Spells in year','slYearSpells')" onclick="javascript:clearText('Availed Spells in year','slYearSpells');" onkeyup="javascript:changeColor('slYearSpells');"/>&nbsp;&nbsp;
											<form:input path="slServiceSpells" id="slServiceSpells" value="Availed Spells in service" cssStyle="color:#A29A9A" onblur="javascript:appendText('Availed Spells in service','slServiceSpells')" onclick="javascript:clearText('Availed Spells in service','slServiceSpells');" onkeyup="javascript:changeColor('slServiceSpells');"/>
										</div>--%>
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
										<div class="quarter leftmar">
											<form:checkbox path="verifyFlag" id="verifyFlag" value="Y" onclick="javascript:verifyCheck()" />
											<label for="verifyFlag" style="color: magenta;font-weight: bold;font-style: italic;">VERIFIED</label> 
											<form:checkbox path="verifyFlag" class="regular-checkbox" value="Y" onclick="javascript:verifyCheck()" />
											<!-- <input id="verifyFlag" class="regular-checkbox" type="checkbox" style="background-color: #99ffff" onclick="javascript:verifyCheck()"/>
											<label for="verifyFlag"></label>&nbsp;&nbsp;<span style="color: magenta;font-weight: bold;font-style: italic;">VERIFIED</span> --><!-- New checkbox -->
										</div>
									</div>
									<div  class="line" style="width:50%">
									    <div><a href="javascript:resetLeaveBalance();" class="appbutton" style="float:right;text-decoration: none">Clear</a></div>
									    <div id="button"><a href="javascript:submitEnterAvailableLeaves();" class="appbutton" style="float:right;text-decoration: none">Submit</a></div>
									</div>	
									<div  class="line"><hr /></div>
									<div id="result"><jsp:include page="LeaveBalanceVerifyList.jsp"/></div>
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
		<script>
			resetEnterAvailableLeaves();
		</script>
		</form:form>
	</body>
</html>
<!-- End:LeaveBalanceVerify.jsp -->