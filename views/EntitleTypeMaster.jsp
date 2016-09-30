<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:EntitleTypeMaster.jsp -->
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

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/tada.js"></script>

<title>TaEntitlement</title>

</head>
<body>
	<form:form method="post" commandName="tada" id="TadaManagementBean">
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
								<div class="headTitle">TA Entitlement Type Master</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter leftmar">Entitlement Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="travelType" id="travelType" cssClass="formSelect" onchange="javascript:getEntitleClassList();">
											  <form:option value="Select" label="Select"></form:option>
											  <form:options items="${tada.travelTypeList}" itemValue="id" itemLabel="travelType"/>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Class Of Entitlement<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:input path="entitleClass" id="entitleClass" maxlength="200" onkeypress="javascript:return isAlphabetExp(event);" onchange="javascript:return isAlphabetExp(event,'entitleClass');"/>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Description</div>
										<div class="quarter">
									         <form:textarea path="description" id="description" cols="20" rows="3" onkeypress="textCounter(event,$jq('#description'),$jq('#counter'),500);" onkeyup="textCounter(event,$jq('#description'),$jq('#counter'),500);"></form:textarea>
												<input type="text" class="counter" name="counter" value="500" id="counter"/>
									    </div>
									</div>
						   			<div class="line" style="margin-left: 35%;">
											<div class="expbutton"><a onclick="javascript:submitEntitleType()"> <span>Submit</span></a></div>
											<div class="expbutton"><a onclick="javascript:clearEntitleType()"> <span>Clear</span></a></div>
									</div>	
									<div class="line"><hr /></div>
									<div class="line" id="result">
										<jsp:include page="EntitleTypeMasterList.jsp"></jsp:include>								
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		</form:form>	
		<script type="text/javascript">
		designationListJSON = <%= (net.sf.json.JSONArray)session.getAttribute("designationListJSON") %>;
		</script>	
			
	</body>
</html>
<!-- End:EntitleTypeMaster.jsp -->