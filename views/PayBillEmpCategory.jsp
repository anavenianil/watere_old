<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:payBillEmpCategory.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script type="text/javascript" src="script/payBill.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<title>Pay Bill</title>
</head>
<body onload="javascript:clearPayCategoryDetails();">
	<form:form method="post" commandName="payBillMaster" id="PayBillMasterBean">
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
								<div class="headTitle">Employee Category Master</div>
								<div class="line">
									<%-- Content Page starts --%>
									<div class="line">
										<div class="half" style="padding-left: 70%;width: 280px;"><strong>Pay Run Month:&nbsp;&nbsp;<font color="green">${payBillMaster.payRunMonth}</font></strong></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Employee Category Name<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="categoryName" id="categoryName"  cssClass="formSelect" onkeypress="return categoryNameValidation(event);" /></div>
									</div>
									<div class="line">
										<div class="quarter leftmar">Order No<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="payOrderNo" id="payOrderNo"  cssClass="formSelect" onkeypress="return checkInt(event);" /></div>
									</div>
									<div class="line">
									<div class="quarter leftmar">PayBill Type</div>
									<div class="quarter">
											<form:radiobutton path="payBillType" id="payBillType1" value="Regular" label="Regular"/>
											<form:radiobutton path="payBillType" id="payBillType2" value="Supplementary" label="Supplementary"/>
									</div>
									</div>
									<div class="line">
										<div style="margin-left:35%">
											<div class="expbutton"><a href="javascript:payCategoryDetails();"><span>Submit</span></a></div>
											<div class="expbutton"><a href="javascript:clearPayCategoryDetails();"><span>Clear</span></a></div>
											<div class="expbutton"><a href="javascript:printCategoryDetails();"><span>Report</span></a></div>
										</div>
									</div>
									
									<div class="line" id="result"><jsp:include page="paybillEmpCategoryDetails.jsp" /></div>
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
				<script>
					jsonPayOrderNo = <%= (net.sf.json.JSONArray)session.getAttribute("jsonPayOrderNo")%>;
				</script>
			</div>
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		<form:hidden path="pk"/>
		</form:form>
	</body>
</html>
<!-- End:payBillEmpCategory.jsp -->
