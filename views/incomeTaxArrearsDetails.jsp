<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:incomeTaxArrearsDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
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
<script type="text/javascript" src="script/incomeTax.js"></script>
<script type="text/javascript" src="script/report.js"></script>

<title>arrears</title>
</head>

<body onload="javascript:clearArrearsDetails();">
	<form:form method="post" commandName="incomeTaxMaster">
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
								<div class="headTitle">DA1 & DA2 Arrears Details</div>
								<div>
								<div>
									<%-- Content Page starts --%>
									
								<div class="line">
										<div class="quarter leftmar">From Date</div>
										<div class="quarter">
											<form:input path="fromDate" id="fromDate" cssClass="dateClass" readonly="true"/>
											<img  src="./images/calendar.gif" id="fromDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"fromDateImg",singleClick : true,step : 1});
											</script>
										</div>
										<div class="quarter bold">To Date</div>
										<div class="quarter">
											<form:input path="toDate" id="toDate" cssClass="dateClass" readonly="true"/>
											<img  src="./images/calendar.gif" id="toDateImg" />	
											<script type="text/javascript">
												Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"toDateImg",singleClick : true,step : 1});
											</script>
										</div>
								</div>
								 <div class="line">
										<div class="quarter leftmar">Old DA<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="oldDA" id="oldDA"/></div>
								        <div class="quarter leftmar">New DA<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="newDA" id="newDA"/></div>
								</div> 
								<div class="line">
								<div class="quarter">&nbsp;</div>
										<div class="quarter">
							  				<form:radiobutton path="daFlag" label="DA1" value="1" id="daFlag1"/> 
	         								<form:radiobutton path="daFlag" label="DA2" value="2" id="daFlag2"/>
	         							</div>
								</div>  
								<div class="line">
										<div style="margin-left:90%">
										<a href="javascript:submitArrearsDetails();" class="appbutton">Run</a>
										</div>
										<div id="result"><jsp:include page="ITArrearsStatusDetails.jsp"></jsp:include></div>
								</div>
									
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
<!-- End:incomeTaxArrearsDetails.jsp -->