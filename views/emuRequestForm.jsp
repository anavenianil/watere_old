<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin: emuRequestForm.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/pisscript.js"></script>
<title>EMU Request Form Details</title>
</head>

<body >
	<form:form method="post" commandName="quarterRequest">
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
						<div class="headTitle">EMU Quarter Request Form</div>
							
							<%-- Content Page starts --%>
							<div>&nbsp;</div>
							<div class="line">
    							<div class="leftmar quarter">Letter Number<span class="mandatory">*</span></div>
								<div class="quarter">
									<form:select path="letterNo" id="letterNo" cssStyle="width:145px" onchange="javascript:setEmuLetterDate();" onmouseover="javascript:setSelectWidth(this);">
			  							<form:option value="Select" label="Select"></form:option>
			  							<form:options items="${sessionScope.letterNumberList}" itemValue="letterNumber" itemLabel="letterNumber"></form:options>
									</form:select>
								</div>
    					        <div class="leftmar quarter">Letter Date<span class="mandatory">*</span></div>
								<div class="quarter">
									<form:input path="letterDt" id="letterDt" cssClass="dateClass" readonly="true" /> 
									<img src="./images/calendar.gif" id="letterDt1" /> 
										<script	type="text/javascript">
		  		Calendar.setup({inputField :"letterDt",ifFormat : "%d-%b-%Y",showsTime : false,button :"letterDt1",singleClick : true,step : 1});
										</script>
								</div>
    						</div>
    						<div>&nbsp;</div>
    						<div>&nbsp;</div>
							<div class="line" id="result"><jsp:include page="Result.jsp" /></div>
	
				<div id="dataTable">
				<% int i = 0; %>
				<display:table name="${sessionScope.emuSFIDList}" excludedParams="*" export="false" class="list" requestURI="" id="emu" pagesize="50" sort="list" cellpadding="2" cellspacing="1">
					<display:column style="width:3%;vertical-align:middle" title='<input type=checkbox  name="selectall" id="selectall" onclick="checkBoxCheckAll(this.name,\'row\');"/>'>
						<input type="checkbox" class="row" name="row" id="adv<%= i %>" onclick="checkBoxCheck(this.name);"/>
					</display:column>
					
					<display:column title="SFID" style="width:10%;vertical-align:middle">
						<input type="text" class="row" name="sfID" id="sfID<%= i %>" size="15" value="${emu.sfID}" readonly="readonly" />
					</display:column>
				
					<display:column title="RequestID" style="width:10%;vertical-align:middle">
						<input type="text" class="row" name="requestID" id="requestID<%= i %>" size="15" value="${emu.requestID}" readonly="readonly" />
					</display:column>
				
					<display:column title="Name" style="width:20%;vertical-align:middle">
						<input type="text" class="row" name="sfID" id="sfID<%= i %>" size="25" value="${emu.name}" readonly="readonly" />
					</display:column>
				
					<display:column title="Designation" style="width:20%;vertical-align:middle">
						<input type="text" class="row" name="requestID" id="requestID<%= i %>" size="75" value="${emu.designationID}" readonly="readonly" />
					</display:column>
				
				<% i++; %>
				</display:table>
				</div>
	
							<div>&nbsp;</div><div>&nbsp;</div>
							<div class="line">
								<div style="float:left;padding-left: 40%;">
									<div class="expbutton"><a href="javascript:saveRequestForm('${emu.requestTypeID}')"><span>Submit</span></a></div> 
									<div class="expbutton"><a href="javascript:clearFormFields();"><span>Clear</span></a></div>
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
	
			<form:hidden path="param" />
			<form:hidden path="type" />
			<form:hidden path="jsonValues" id="jsonValues"/>
</form:form>
<script type="text/javascript">
	clearFormFields();
	emuSFIDListJSON = <%= (net.sf.json.JSONArray) session.getAttribute("emuSFIDListJSON") %>;
	letterNoList = <%= (net.sf.json.JSONArray) session.getAttribute("letterNumberListJson") %>;
</script>

</body>
</html>
<!-- End: emuRequestForm.jsp -->