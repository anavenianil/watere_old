<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:TdUserDelegation.jsp -->
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

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/tada.js"></script>

<title>TD User Delegation</title>

</head>
<body>
	<form:form method="post" commandName="tada" id="tadaReq">
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
								<div class="headTitle">Delegation of TD Request</div>
								<%-- Content Page starts --%>
								<div class="line">
								<div id="result">
						           <jsp:include page="RequestResult.jsp"></jsp:include>
						        </div>
									<div class="line">
										<div class="quarter leftmar">Request Type<span class="mandatory">*</span></div>
										<div class="quarter">
										    <form:select path="requestType" id="requestType" cssStyle="width:142px" onchange="javascript:setDelegateReqId();">
												<form:option value="Select">Select</form:option>
												<form:option value="45">TADA TD BUILDUP</form:option>
												<form:option value="46">TADA TD PROJECT</form:option>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Request Id<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="requestId" id="requestId" cssStyle="width:142px" onchange="javascript:setSuperior();">
												<form:option value="Select">Select</form:option>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Absence of superiors<span class="mandatory">*</span></div>
										<div class="half" id="absence">
											
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Delegate To<span class="mandatory">*</span></div>
										<div class="quarter">
										    <form:select path="delegateTo" id="delegateTo" cssStyle="width:142px" >
												<form:option value="Select">Select</form:option>
											</form:select>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Remarks<span class="mandatory">*</span></div>
										<div>										
											<form:textarea path="reason" cols="20" rows="4" id="reason"  onkeypress="textCounter(this,document.forms[0].counter,500);" onkeyup="textCounter(this,document.forms[0].counter,500);"/>
											<input type="text" class="counter" name="counter" value="500" id="counter" disabled="disabled"/>										
										</div>
									</div>
						   			<div class="line" style="margin-left: 25%;">
						   			        <div class="expbutton"><a onclick="javascript:tdRequestDelegate();"> <span>Delegate</span></a></div>
											<div class="expbutton"><a onclick="javascript:clearTdRequestDelegate();"> <span>Clear</span></a></div>
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
		pendingReqList = <%= (net.sf.json.JSONArray)session.getAttribute("pendingReqListJSON") %>;
		keyList = <%= (net.sf.json.JSONArray)session.getAttribute("keyListJSON") %>;
		tdReqBean = <%= (net.sf.json.JSONObject)session.getAttribute("tdRequestBeanJSON") %>;
		</script>	
		</body>	
	
</html>
<!-- End:TdUserDelegation.jsp -->