<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:Nominee.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script language="javascript" src="script/prototype.js"></script>
<script language="javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<title>Manage Nominee Details</title>
</head>

<body onload="javascript:clearNominee()">
	<form:form method="post" commandName="nominee">
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
							<div style="padding: 10px 0 0 0;"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
								
								<div class="line">
									<%-- Content Page starts --%>
									<div id="result"></div>
									<div class="headTitle" class="line">Manage Nominee Details</div>
									<div class="line">
										<div class="quarter bold" id="labelType" style="margin-left:11px;">SFID</div>
										<div class="quarter"><form:input path="sfid" id="changeSfid" maxlength="15" onkeypress="funOnKeyPress(event,'editEmployee')"/></div>
										<div class="appbutton"><a href="javascript:changeEmployeeDetails('nominee');" style="text-decoration: none";>Search</a></div>
									</div>
									
										<fieldset><legend><strong><font color='green'>Nominee Details</font></strong></legend>
										<div class="line">
											<div class="quarter">SFID</div>
											<div class="quarter">${sessionScope.changeSfid}</div>
											<div class="quarter">Name</div>
											<div class="quarter">${sessionScope.changeSfidName}</div>
										</div>
										<div class="line">
											<div class="quarter">Nominee Type<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="nomineeTypeId"  id="nomineeTypeId" cssClass="formSelect" >
													<form:option value="select">Select</form:option>
													<form:options items="${nomineeTypeList}" itemValue="id" itemLabel="name"></form:options>
													
												 </form:select>										
											</div>
											<div class="quarter">Family Member<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:select path="familyID"  id="familyID" onchange="displayAddress();" cssClass="formSelect" onmouseover="setSelectWidth('#familyID')" >
													<form:option value="select">Select</form:option>
													<form:options items="${familyMembersList}" itemValue="id" itemLabel="name"></form:options>
													<form:option value="other">Other</form:option>
												</form:select>
											</div>
										</div>
										<div id="other" style="display:none">
											<div class="line">
												<div class="quarter">Nominee<span class="mandatory">*</span></div>
												<div class="quarter"><form:input path="nomineeName" id="nomineeName" maxlength="100" onkeypress="javascript:return checkChar(event);"/></div>
												<div class="quarter">Address Line1<span class="mandatory">*</span></div>
												<div class="quarter"><form:input path="address1" id="address1" maxlength="100"/></div>
											</div>
											<div class="line">
												<div class="quarter">Address Line2</div>
												<div class="quarter"><form:input path="address2" id="address2" maxlength="100"/></div>
												<div class="quarter">Address Line3</div>
												<div class="quarter"><form:input path="address3" id="address3" maxlength="100"/></div>
												
											</div>
											<div class="line">
												<div class="quarter">City<span class="mandatory">*</span></div>
												<div class="quarter"><form:input path="city" id="city" maxlength="100" onkeypress="javascript:return checkChar(event);"/></div>
												<div class="quarter"><!-- State -->Region</div>
												<div class="quarter">
													<form:select path="stateId"  id="stateId" cssClass="formSelect" onmouseover="setSelectWidth('#stateId')" onchange="javascript:getDistrictList('stateId','districtId')">
														<form:option value="select">Select</form:option>
														<form:options items="${stateList}" itemValue="id" itemLabel="name"></form:options>
													 </form:select>
												</div>
												
											</div>
											<div class="line">
												<div class="quarter">District<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:select path="districtId"  id="districtId" cssClass="formSelect" onmouseover="setSelectWidth('#districtId')">
														<form:option value="select">Select</form:option>
													 </form:select>
												</div>
												<div class="quarter">Pin code</div>
												<div class="quarter"><form:input path="pincode" id="pincode" maxlength="6" onkeypress="return checkInt(event);"/></div>
											</div>
											
										</div>
										<div class="line">
											<div class="quarter">Percentage<span class="mandatory">*</span></div>
											<div class="quarter"><form:input path="percentage" id="percentage" maxlength="6" onkeypress="javascript:return checkFloat(event,'percentage');"/></div>
											<div class="quarter">Date of Nomination</div>
											<div class="quarter">
												<form:input path="dateOfNominate" id="dateOfNominate" cssClass="dateClass" readonly="true"/>
												<img  src="./images/calendar.gif"   id="date_start_trigger4"/>	
												<script type="text/javascript">
													Calendar.setup({inputField :"dateOfNominate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
												</script>											
											</div>
										</div>
										<div class="line">
											<div class="quarter">Remarks</div>
											<div class="quarter"><form:textarea path="remarks" id="remarks" rows="3" cols="20" onkeypress="textCounter(this,document.forms[0].counter3,500);" onkeyup="textCounter(this,document.forms[0].counter3,500);"/>
											<input type="text" class="counter" name="counter" value="500" id="counter3" disabled="disabled"/>
										</div>
										<div class="line">
											 <table cellpadding="0" cellspacing="0" align="center" width="100%" id="incontengensy">
												
											</table>
										</div>
										<div class="line">
											<div style="margin-left:60%">				
												<div  class="expbutton"><a href="javascript:nomineeInconsistency();"><span>In contingency of</span></a></div>
												<a href="javascript:submitNominee('admin');"><div class="appbutton">Submit</div></a>
												<a href="javascript:clearNominee();"><div class="appbutton">Clear</div></a>
											</div>										
										</div>
										</fieldset>
										<div class="height"><hr/></div>
										<div id="nomineeResult">
											<jsp:include page="NomineeList.jsp"></jsp:include>
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
		<form:hidden path="nomineeID"/>
		<form:hidden path="inconsistencyDetails"/>
		</form:form>
		<script>
			nomineeJSONList = <%= (net.sf.json.JSONArray)session.getAttribute("nomineeListJSON") %>;
			percentageList = <%= (net.sf.json.JSON)session.getAttribute("percentageList") %>;
			familyMemberInNominee = <%= (net.sf.json.JSON)session.getAttribute("familyMemberInNominee") %>;
			districtJSONList = <%= (net.sf.json.JSONArray)session.getAttribute("districtJSONList") %>;
		</script>
	</body>
	
	
</html>
<!-- End:Nominee.jsp -->