<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:PayScaleMaster.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="./styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script type="text/javascript" src="script/payBill.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript">
	$jq(document).ready(function(){
		payScaleLables(type);
		clearPayScale();
	});
</script>

</head>

<body onload="javascript:payScaleLables('${sessionScope.masterType}');">
<form:form method="post" commandName="payScale">
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
								<%-- Content Page starts --%>
								<div class="line">
										<div class="half" style="padding-left: 70%;width: 280px;"><strong>Pay Run Month:&nbsp;&nbsp;<font color="green">${payScale.payRunMonth}</font></strong></div>
								</div>
								<div class="line">
                                 	<div class="line">
										<div class="quarter bold" id="firstLabelType"></div>
									  <div class="quarter"><form:input path="firstTypeValue" id="firstTypeValue" maxlength="8" onkeypress="javascript:increaseTextWidth('firstTypeValue');return checkInt(event,'firstTypeValue');" onchange="return checkGradePayFrom('firstTypeValue')"/></div>
										<c:if test="${sessionScope.masterType ne  'familyPlanning'}">
											<div class="quarter bold leftmar" style="margin-left:8px;">To<span class="mandatory">*</span></div>
											<div class="quarter">
												<form:input path="to" id="to" maxlength="10" onkeypress="javascript:increaseTextWidth('to');return checkInt(event,'to');" onchange="return checkGradePayTo('to')"/>
											</div>
										</c:if>
									</div>
									<div class="line">
										<div class="quarter bold" id="secondLabelType"></div>
										<div class="quarter"><form:input path="secondTypeValue" id="secondTypeValue" maxlength="8" onkeypress="javascript:increaseTextWidth('secondTypeValue');return checkInt(event,'secondTypeValue');"/></div>
										<div class="quarter leftmar">Eff From<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="effDate" id="effDate" cssClass="required" onkeypress="javascript:setReadOnly(event, 'effDate')"/>
											<img  src="./images/calendar.gif" id="date_start_trigger2" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"effDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
											</script>
										</div>
									</div>
									<div class="line">
											<div style="margin-left:45%;">
												<div class="expbutton"><a href="javascript:managePayScale('${sessionScope.masterType}');"><span>Submit</span></a></div>
												<div class="expbutton"><a href="javascript:clearPayScale('${sessionScope.masterType}');"><span>Clear</span></a></div>
									        	<div class="expbutton"><a href="javascript:printPayScale('${sessionScope.masterType}');"><span>Report</span></a></div>
									        </div>
									</div>
									
									<div class="line height"><hr/></div>
									<div id="payScaleMasterList">
										<jsp:include page="PayScaleMasterList.jsp" />
									</div>
									<c:if test="${sessionScope.masterType eq 'travellingAllowance'}">
										<div class="line"><span class="failure">NOTE : 1. Standard Amount + DA </span></div>
                                   		<div class="line"><span class="failure">  2. For Orthopedically, Handicapped & blind double the normal rates but not less than 1000 </span></div>
                                    </c:if>
                                   
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
			var type='<c:out value='${sessionScope.masterType}'/>';
			jsonPayScaleList = <%= (net.sf.json.JSONArray)session.getAttribute("jsonPayScaleList")%>;
		</script>	
	</body>
</html>
<!-- End:PayScaleMaster.jsp -->	