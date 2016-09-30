<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:LicenceFeeChargesMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO,"%>


<%@page import="com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO"%>
<%@page import="com.callippus.web.beans.quarterType.QuarterTypeBean"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script type="text/javascript" src="script/payBill.js"></script>
<script>
		<% if (session.getAttribute("quarterTypeList")!=null ) { %>
			quarterTypeListJSON =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<PayBillQuartersTypeMasterDTO>)session.getAttribute("quarterTypeList"))%>;
		<%}%>
		<% if (session.getAttribute("quarterSubTypeList")!=null ) { %>
			quarterSubTypeListJSON =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List<QuarterTypeBean>)session.getAttribute("quarterSubTypeList"))%>;
		<%}%>
</script>
<title>Pay Bill</title>
</head>

<body>
	<form:form method="post" commandName="quarterType">
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
								<div class="headTitle" id="title">License Fee Charges Master</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="half" style="padding-left: 70%;width: 280px;"><strong>Pay Run Month:&nbsp;&nbsp;<font color="green">${quarterType.payRunMonth}</font></strong></div>
									</div>
										<div class="line">
											<div class="quarter bold">Type Of Quarter<span class="mandatory">*</span></div>
									     <div class="quarter bold">
												<form:select path="quartersType" id="quartersType" cssClass="formSelect" onchange="javascript:getQuarterSubTypeList();">
													<form:option value="select">Select</form:option>
													<form:options items="${sessionScope.quarterTypeList}" itemValue="id" itemLabel="name" />
												</form:select>
											</div>
										</div>
										<div class="line"> 
											<div class="quarter bold">Sub Type Of Quarter<span class="mandatory">*</span></div>
									     <div class="quarter bold">
												<form:select path="quarterSubType" id="quarterSubType" cssClass="formSelect" onchange="javascript:getQuaerterSubTypeList();">
													<form:option value="select">Select</form:option>
												</form:select>
											</div>
										</div>
										<div class="line">
											<div class="quarter bold">Rent<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="licenceFee" id="licenceFee" maxlength="100" onkeypress="javascript:increaseTextWidth('licenceFee');return checkInt(event,'licenceFee');"/>
											</div>
											<div class="quarter bold">Water Bill<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="water" id="water" maxlength="100" onkeypress="javascript:increaseTextWidth('licenceFee');return checkInt(event,'licenceFee');"/>
											</div>
										</div>
										<div class="line">
											<div class="quarter bold">Electricity Bill<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="elec" id="elec" maxlength="100" onkeypress="javascript:increaseTextWidth('licenceFee');return checkInt(event,'licenceFee');"/>
											</div>
											<div class="quarter bold">Furniture Bill<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="furn" id="furn" maxlength="100" onkeypress="javascript:increaseTextWidth('licenceFee');return checkInt(event,'licenceFee');"/>
											</div></div>
											<div class="line">
									     	<div class="quarter bold">Eff From<span class="mandatory">*</span></div>
										    <div class="quarter"><form:input path="effDate" id="effDate" cssClass="required" onkeypress="javascript:setReadOnly(event, 'effDate')"/>
										    <img  src="./images/calendar.gif" id="date_start_trigger2" />	
										    <script type="text/javascript">
											Calendar.setup({inputField :"effDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
										    </script>
										</div></div>
										
										<div class="line">
											<div style="margin-left:35%;">
												<div class="expbutton"><a href="javascript:submitLicenceFeeDetails();"><span>Submit</span></a></div>
											</div>
											<div class="expbutton"><a href="javascript:resetLicenceFeeDetails();"><span>Clear</span></a></div>
										    <div class="expbutton"><a href="javascript:printLicenceFeeDetails();"><span>Report</span></a></div>
										</div>	
									</div>
									<div class="height"><hr/></div>
									<div class="line" id="result">
								    	<jsp:include page="LicenceFeeChargesMasterList.jsp"/>	
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
		<form:hidden path="param"/>	
		<form:hidden path="type"/>	
		</form:form>		
	</body>
</html>
<!-- End:LicenceFeeChargesMaster.jsp -->