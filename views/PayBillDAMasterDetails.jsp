<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:PayBillDAMasterDetails.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/payBill.js"></script>

<title>Pay Bill</title>
</head>
<body onload="javascript:clearDAMasterDetails();">
	<form:form method="post" commandName="payBillMaster" id="payBillMaster">
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
								<div class="headTitle">Dearness Allowance Master Details</div>
								<div class="line">
									<%-- Content Page starts --%>
									<div class="line">
										<div class="half" style="padding-left: 70%;width: 280px;"><strong>Pay Run Month:&nbsp;&nbsp;<font color="green">${payBillMaster.payRunMonth}</font></strong></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Year<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="daDate" id="daDate" cssClass="required" onkeypress="javascript:setReadOnly(event, 'daDate')"/>
										<img  src="./images/calendar.gif" id="date_start_trigger1" />	
										<script type="text/javascript">
											Calendar.setup({inputField :"daDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
										</script>
										</div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Rate of DA<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="daValue" id="daValue" maxlength="100" onkeypress="return checkInt(event);"/></div>
									</div>
									<div class="line">
										<div style="margin-left:43%">
											<div class="expbutton"><a href="javascript:manageDAMasterDetails();"><span>Submit</span></a></div>
											<div class="expbutton"><a href="javascript:clearDAMasterDetails();"><span>Clear</span></a></div>
											<div class="expbutton"><a href="javascript:printDAMasterDetails();"><span>Report</span></a></div>
										</div>
									</div>
									<div class="line" id="DAMasterList">
										<jsp:include page="PayBillDAMasterList.jsp"/>
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
		</form:form>
	</body>
</html>
<!-- End:PayBillDAMasterDetails.jsp -->
