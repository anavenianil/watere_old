<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<!-- begin:quarterOfflineDetailsEntry.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ page import="net.sf.json.JSONSerializer,java.util.List,com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO,"%>
<%@ page import="com.callippus.web.paybill.dto.PayBillQuartersTypeMasterDTO"%>
<%@ page import="com.callippus.web.beans.quarterType.QuarterTypeBean"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/pisscript.js"></script>

<title>Quarter Offline Details Entry</title>
</head>
<body onload="javascript:clearQuarterOffline1();">
	<form:form method="post" commandName="quarterRequest" id="quarterRequest">
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
								<div class="headTitle">Quarter Offline Details Entry</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter bold">SFID<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="sfID" id="sfID1" maxlength="10" onkeypress="javascript:return isAlphaNumaricExp(event);" /></div>
									    <div class="quarter"><div class="expbutton"><a href="javascript:onchangeSfidDetails()"> <span>Search</span></a></div></div>
								    </div>
								</div>
								<div class="line" id="result"></div>
								<%-- Content Page ends --%>
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
		<form:hidden path="parentID" id="parentID"/>
	</form:form>
</body>
</html>

	<!-- end:quarterOfflineDetailsEntry.jsp -->