<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin : ViewAddress.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script language="javascript" src="script/prototype.js"></script>
<script language="javascript" src="script/aa.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>

<title>Address Details</title>
</head>

<body>
	<form:form method="post" commandName="address">
	<form:hidden path="param"/>
	<form:hidden path="type"/>
	<form:hidden path="id" id="id"/>
	<form:hidden path="addFlag" id="addFlag"/>
	<script>document.title="Address Details"</script>
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
							<div class="lightblueBg1" style="float: left;">
								<div style="padding: 10px 0 0 0;"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</div>
					<%-- Content Page starts --%>
					<div>
					<div class="headTitle">Manage Address Details</div>
					<div id="result"></div>
					<div class="line">
					<!-- Employee id noting but SFId -->
						<div class="quarter bold" id="labelType" style="margin-left:11px;">Employee ID <span class="mandatory">*</span></div>
						<div class="quarter"><form:input path="sfid" id="changeSfid" maxlength="15"/></div>
						<div class="appbutton"><a href="javascript:changeEmployeeDetails('address');" style="text-decoration: none";>Search</a></div>
					</div>
						<div id="createAddress" class="line">
						<fieldset >
							<legend><strong><font color='green' >   Address Details</font></strong></legend>
							<div class="line">
								<div class="line">
									<!-- <div class="quarter">SFID</div> -->
									<div class="quarter">Employee ID</div>
									<div class="quarter">${changeSfid}</div>
									<div class="quarter">Name</div>
									<div class="quarter">${changeSfidName}</div>
									
								</div>
								<div class="line">
									<div class="quarter">Address Type<span class="mandatory">*</span></div>
									<div class="quarter">
										<form:select path="addressTypeId" id="addressTypeId" cssClass="formSelect" onchange="javascript:sameAsAddress();">
											<form:option value="Select">Select</form:option>
											<form:options items="${addressTypeList}" itemValue="id" itemLabel="name"></form:options>
										 </form:select>
									</div>
								   <div class="quarter">Same As</div>
								   <div class="quarter">
										<select id="selectAdd" style="min-width:145px;width:auto;" onchange="javascript:fillAdd();">
											<option value="Select">Select</option>
										</select>
								   </div> 
							    </div>
								<div class="line">
								<div class="quarter">C/o</div>
									<div class="quarter">
										<form:input path="careOfAddress" id="careOfAddress" maxlength="200" />
									</div>
									<div class="quarter">Address Line1<span class="mandatory">*</span></div>
									<div class="quarter">
										<form:input path="address1" id="address1" maxlength="200"/>
									</div> 
									
								</div>
								<div class="line">
								<div class="quarter">Address Line2</div>
									<div class="quarter">
										<form:input path="address2" id="address2" maxlength="200"/>
									</div>
									<div class="quarter">Address Line3</div>
									<div class="quarter">
										<form:input path="address3" id="address3" maxlength="200"/>
									</div> 
									
								</div>
								<div class="line">
										<div class="quarter">City<span class="mandatory">*</span></div>
									<div class="quarter">
										<form:input path="city" id="city" maxlength="100" onkeypress="return checkChar(event);"/>
									</div>
									<div class="quarter"><!-- State -->Region<span class="mandatory">*</span></div>
									<div class="quarter">
										<form:select path="state" id="state" cssClass="formSelect" onmouseover="setSelectWidth('#state')" onchange="javascript:getDistrictList('state','district')">
											<form:option value="0">Select</form:option>
											<form:options items="${stateList}" itemValue="id" itemLabel="name"/>
										</form:select>
									</div>
									
								</div>
								<div class="line">
								<div class="quarter">District<span class="mandatory">*</span></div>
									<div class="quarter">
										<form:select path="district" id="district" cssClass="formSelect" onmouseover="setSelectWidth('#district')" >
											<form:option value="0">Select</form:option>
											<form:options items="${districtList}" itemValue="id" itemLabel="name"/>
										</form:select>
									</div>
								</div>
								
									<div class="quarter">Pincode</div>
									<div class="quarter">
										<form:input path="pincode" id="pincode" maxlength="6" onkeypress="return checkPinCode(event);" />
									</div>
									
								</div>
								<div class="line">
								<div class="quarter">Phone</div>
									<div class="quarter">
										<form:input path="phone" id="phone" maxlength="15" onkeypress="return checkInt(event);"/>
									</div>
									<div class="quarter">Mobile</div>
									<div class="quarter">
										<form:input path="mobile" id="mobile" maxlength="15" onkeypress="return checkInt(event);"/>
									</div>
									
								</div>	
								<div class="line">
								<div class="quarter">Email</div>
									<div class="quarter">
										<form:input path="email" id="email" maxlength="100"/>
									</div>
									<div class="quarter">Nearest Railway Station<span class="mandatory">*</span></div>
									<div class="quarter">
										<form:input path="nearestRyStation" id="nearestRyStation" maxlength="200" onkeypress="return checkChar(event);"/>
									</div>
									
								</div>
								<div class="quarter">Nearest Airport</div>
									<div class="quarter">
										<form:input path="nearestAirport" id="nearestAirport" maxlength="200" onkeypress="return checkChar(event);"/>
									</div>
									<div style="display: none">
								<div class="line" style="display: none" id="dispensary">
									<div class="quarter">New Dispensary No Allocation</div>
									<div class="quarter">
										<form:checkbox path="dispensaryNumber" value = "Y" id="dispensaryNumber"/> 
									</div>
								</div>
								</div>
								</fieldset>
							</div>
							
							<div class="float line">
									<div style="float:right" class="appbutton"><a class="quarterbutton" href="javascript:clearAddress();">Clear</a></div>
									<div style="float:right" class="appbutton"><a class="quarterbutton" href="javascript:manageAddress('${message}');">Submit</a></div>
								</div>
						
							
						<div class="height"><hr/></div>
						<script>
							districtJSONList = <%= (net.sf.json.JSONArray)session.getAttribute("districtJSONList") %>;
						</script>
						<div id="addressList" class="line">
							<jsp:include page="AddressList.jsp" />
						</div>
					</div>

					</div>
					<%-- Content Page end --%>
						<div>&nbsp;</div>
							</div>
							<div class="whitebox_b_non">
							<div class="whitebox_bl">
								<div class="whitebox_br"></div>
							</div>
						</div>
						</div>
						
					</div>
					
				</div>
				
			</div>
			<div><jsp:include page="Footer.jsp" /></div>			
		
		
		</form:form>
		<script>
			clearAddress();
		</script>
	</body>
</html>


<!-- End : ViewAddress.jsp -->