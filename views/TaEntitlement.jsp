<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:TaEntitlement.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
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
<body onload="javascript:multiSelectBox();">
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
								<div class="headTitle">Travelling Allowance/Entitlements</div>
								
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter leftmar">Grade Pay<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="gradePay" id="gradePay"  multiple="" cssStyle="width:145px;" onchange="javascript:enableEntitlement();">
												<form:option value="Select" label="Select"></form:option>
												<form:options  items="${sessionScope.gradePayList}" itemLabel="gradePay" itemValue="gradePay"></form:options>
											</form:select>
										</div>
									</div>
									<div class="line">
									    <div class="quarter leftmar">Travel Type<span class="mandatory">*</span></div>
									    <div class="quarter">
										    <form:select path="travelType" id="travelType"  multiple="" cssStyle="width:145px;" onchange="javascript:enableTravelTypeClass();">
												<form:option value="Select" label="Select"></form:option>
											</form:select>
									    </div>
									</div>
									
									<div class="line">
										<div class="leftmar">
											<div style="float: left; width: 45%;">All Values</div>
											<div class="half" >Selected Values</div>
										</div>
									</div>
									
									<div class="line">
										<div class="leftmar">
											<div style="float: left; width : 35%">
												<form:select path="nonEntitleClass" id="SelectLeft" size="10" multiple="true" cssStyle="width:300px" >
													
												</form:select>
											</div>
											<div style="float: left; width : 10%; margin-top: 60px;">									
												<div style="margin-bottom: 5px;" align="center">
												     <input style="margin-bottom: 5px" id="MoveRight" type="button" value=" Add " />
     											     <input id="MoveLeft" type="button" value=" Remove " />    											        
     											</div>		      																				
	      									</div>
											<div style="float: left; width : 30%">
												<form:select path="entitleClass" id="SelectRight" size="10" multiple="true" cssStyle="width:300px">
												</form:select>
											</div>
										</div>
									</div>
									
									<div class="line" style="margin-left: 35%;">
											<div class="expbutton"><a onclick="javascript:submitEntitlement()"> <span>Submit</span></a></div>
											<div class="expbutton"><a onclick="javascript:clearEntitlement()"> <span>Clear</span></a></div>
									</div>	
									<div class="line"><hr /></div>
									<div class="line" id="result">
									   <jsp:include page="TaEntitlementList.jsp"></jsp:include>								
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
		    travelTypeMapDetailsJSON= <%= (net.sf.json.JSONArray) session.getAttribute("travelTypeMapDetailsJSON") %>;
		    gradePayListMapJSON = <%= (net.sf.json.JSONArray) session.getAttribute("gradePayListJSON") %>;
		    travelTypeListMapJSON = <%= (net.sf.json.JSONArray) session.getAttribute("travelTypeListJSON") %>;
		    entitleClassListJSON = <%= (net.sf.json.JSONArray) session.getAttribute("entitleClassListJSON") %>;
		    taEntitleClassListJSON = <%= (net.sf.json.JSONArray) session.getAttribute("taEntitleClassListJSON") %>;
		    taEntitleJSON = <%= (net.sf.json.JSONArray) session.getAttribute("taEntitleJSON") %>;
		</script>	
			
	</body>
</html>
<!-- End:TaEntitlement.jsp -->