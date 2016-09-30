<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:DemandCancel.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/mmgscript.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
</head>
<body>
<form:form method="post" id="cancelDemand">
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
								<div class="headTitle" id="headTitle"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</img></div>
								<%-- Content Page starts --%>
									<div class="headTitle">Demand Cancellation</div>
									<div id="result"></div>
									<fieldset><legend><strong>Demand Cancellation</strong></legend>
										<div class="line">
												<div class="quarter">Demand Number</div>
												<div class="quarter">
													<form:select path="demandNo" cssStyle="width:145px" id="demandNo">
													 <form:option value="">Select</form:option>
														<c:forEach var="demand" items="${sessionScope.demandList}">
															 <form:option value="${demand.demandNo}">${demand.demandNo}</form:option>
														</c:forEach>														
													</form:select>
												</div>
												<div class="quarter">Demand Cancel Date</div>
												<div class="quarter"><form:input path="demandCancelDate" id="demandCancelDate" readonly="true"/> 
													<img  src="./images/calendar.gif"   id="demand_date_trigger3" />	
														<script type="text/javascript">
															Calendar.setup({inputField :"demandCancelDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"demand_date_trigger3",singleClick : true,step : 1});
														</script>
												</div>
										</div>
										<div class="line">
											<div class="quarter" style="margin-left:8px;">Reason</div>
											<div class="quarter"><form:textarea path="remarks" id="remarks" cols="20" rows="3" onkeypress="textCounter(event,$jq('#remarks'),$jq('#counter'),500);"
											 onkeyup="textCounter(event,$jq('#remarks'),$jq('#counter'),500);"></form:textarea>
												<input type="text" class="counter" name="counter" value="500" id="counter"/>
											</div>
										</div>
										<div class="line">
											<div class="expbutton" style="float:right"><a href="javascript:demandCancelRaise();"><span>Cancel Demand</span></a></div>
											<div class="expbutton" style="float:right"><a href="javascript:clearCancelDemand();" class="quarterbutton"><span>Clear</span></a></div>
										</div>
									</fieldset>
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
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type"/>
		</form:form>
</body>								
</html>