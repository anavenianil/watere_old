<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:AdminEntryHome.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>

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
<script type="text/javascript" src="script/ltc.js"></script>
<script type="text/javascript" src="script/date.js"></script>

<title>LTC Admin Entry</title>
</head>

<body>
<form:form method="post" id="LtcApplicationBean" commandName="ltcApprovalRequest">
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
	<div class="headTitle">LTC Admin Entry</div>
	<%-- Content Page starts --%>
	<div class="line">
	<div class="line">
	<div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
	<div class="quarter"><form:input path="refSfID" id="refSfID" onchange="getFamilyMemberList()"/></div>
		<div class="half" id="result1">	
	</div>	
	</div>
		<div class="line">
	<div class="quarter leftmar">Family Members<span class="mandatory">*</span></div>
	<div class="quarter" id="familyDetails">
	<form:select path="familyMemberId" id="familyMemberId" size="3" multiple="true" cssStyle="width:150px">
		
	</form:select></div>
		<div class="quarter leftmar" style="width: 20%">LTC Type<span class="mandatory">*</span></div>
	<div class="quarter leftmar"><form:select path="ltcTypeId" id="ltcTypeId" cssClass="formSelect">
					<form:option value="0" label="Select"></form:option>
					<form:options items="${ltcApprovalRequest.ltcTypeDetails}" itemLabel="name" itemValue="id"></form:options>
					</form:select>
	</div>
	
	</div>
	
	
	<div class="line">
	<div class="quarter leftmar">LTC Block Year<span class="mandatory">*</span></div>
	<div class="quarter"><form:select path="ltcBlockYearId"
		id="ltcBlockYearId" style="width:100px;">
		<form:option value="0">Select</form:option>
	</form:select></div>
	<!--commented by bkr 11/04/2016 only one line  -->
		<!-- <div class="quarter leftmar" style="width: 20%">EL Encashment Days</div> -->
		<!--Added by bkr 11/04/2016  -->
		<div class="quarter leftmar" style="width: 20%">No of Vacation Days</div>
	<div class="quarter leftmar"><form:input path="encashmentDays" id="encashmentDays" onkeypress="return checkInt(event);"></form:input></div>
	
	</div>
	
	
	
			<div class="line">
		<div class="quarter leftmar">Departure Date<span class='failure'>*</span></div>
		<!--commented by bkr 07/04/2016  -->
		<%-- <div class="quarter" id="departureDateDiv">
	    <form:input path="departureDate" id="departureDate" readonly="true"   onchange="javascript:checkApplingYear('${ltcApprovalRequest.typeValue}');return validdate('departureDate')"/> 
		<img  src="./images/calendar.gif"   id="date_start_trigger3" />	
		<script type="text/javascript">
			Calendar.setup({inputField :"departureDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
	    </script>
		</div> --%>
			<!--added by bkr 07/04/2016  -->
		<div class="quarter" id="departureDateDiv">
	    <form:input path="departureDate" id="departureDate" readonly="true" /> 
		<img  src="./images/calendar.gif"   id="date_start_trigger3" />	
		<script type="text/javascript">
			Calendar.setup({inputField :"departureDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
	    </script>
		</div>
		
		<!-- commented by bkr 07/04/2016  -->
		<%-- <div class="quarter leftmar" style="width: 20%">Return Date</div>
		<div class="quarter leftmar" id="returnDateDiv">
		<form:input path="returnDate" id="returnDate" readonly="true"   onchange="javascript:checkApplingYear('${ltcApprovalRequest.typeValue}');return validdate('returnDate')"/> 
		<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
	    <script type="text/javascript">
			Calendar.setup({inputField :"returnDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
		</script>
		</div> --%>
		
			<!-- added by bkr 07/04/2016  -->
		<div class="quarter leftmar" style="width: 20%">Return Date</div>
		<div class="quarter leftmar" id="returnDateDiv">
		<form:input path="returnDate" id="returnDate" readonly="true"  /> 
		<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
	    <script type="text/javascript">
			Calendar.setup({inputField :"returnDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
		</script>
		</div>
		
		</div>
	
	
	<div class="line">
	<div class="quarter leftmar">Place of Visit<span class="mandatory">*</span></div>
	<div class="quarter"><form:input path="placeOfVisit" id="placeOfVisit" onkeypress="return isAlphabetExp(event);"></form:input></div>
	<div class="quarter leftmar" style="width: 20%">Nearest Railway Station<span class='failure'>*</span></div>
	<div class="quarter leftmar"><form:input path="nearestRlyStation" id="nearestRlyStation" onkeypress="return isAlphabetExp(event);"></form:input></div>
	</div>
	<div class="line" >
     <a href="javascript:searchAdminEntryDetails();" class="appbutton" style="margin-left: 35%;">Search</a>
	 <a href="javascript:manageAdminEntry();" class="appbutton">Submit</a>
	 <a href="javascript:resetAdminEntry();" class="appbutton">Clear</a>
	 </div>
	<div class="line height">
	<hr />
	</div>

	<div class="line" id="result">
		<jsp:include page="AdminEntryList.jsp"></jsp:include>
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
</form:form>
		<script type="text/javascript">
		familyMembersJson = <%= (net.sf.json.JSONArray)session.getAttribute("familyMembersJson") %>;
		</script>	
</body>
</html>
<!-- End:AdminEntryHome.jsp -->