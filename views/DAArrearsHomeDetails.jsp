<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TestPage.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
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
<script type="text/javascript" src="script/arrears.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<title>DA Arrears Details</title>
</head>

<body>
	<form:form method="post" commandName="arrears">
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
								<div class="headTitle">Dearness Allowances Arrears Details</div>
								<div>
									<%-- Content Page starts --%>
								<div class="line">
								<fieldset><legend><strong><font color='green'>DA Arrears Details</font></strong></legend>
								<div class="half">
								<div class="line">&nbsp;</div>
								<div class="line">
										<div class="half leftmar">From Date<span class="mandatory">*</span></div>
										<div class="half"><form:input path="adminAccDate" id="adminAccDate" cssClass="required" readonly="true"/>
										<img  src="./images/calendar.gif" id="date_start_trigger1" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"adminAccDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
											</script>
										</div></div>
										<div class="line">
											<div class="half leftmar">To Date<span class="mandatory">*</span></div>
											<div class="half"><form:input path="financeAccDate" id="financeAccDate" cssClass="required" readonly="true"/>
											<img  src="./images/calendar.gif" id="date_start_trigger2" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"financeAccDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
											</script>
											</div>
										</div>
									 <div class="line"><a class="appbutton" style="margin-left:75%" onclick="javascript:getDaArrearsPreviewDetails();">Submit</a></div>
							   </div>
							   <div id="previewHome" class="half">
							   </div></fieldset>
							    </div>
							    <div class="line" id="previewList"></div>
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
<!-- End:TestPage.jsp -->