<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:R_LastModifiedEmpReport.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<title></title>
</head>

<body onload="javascript:resetReportList('${reports.type}'); lables('${reports.type}');">
	<form:form method="post" commandName="reports">
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
								<div class="headTitle" id="headTitle" ></div>
								<div>
									<%-- Content Page starts --%>
									<div class="line">
											<div class="quarter bold leftmar">From Date<span class="mandatory">*</span></div>
											<div class="quarter ">
												<form:input path="fromDate" id="fromDate" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('fromDate');"/>
												<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
												<script type="text/javascript">
												Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
												</script>
											</div>
											<div class="quarter bold leftmar">To Date<span class="mandatory">*</span></div>
											<div class="quarter ">
												<form:input path="toDate" id="toDate" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('toDate');"/>
												<img  src="./images/calendar.gif"   id="date_start_trigger2" />	
												<script type="text/javascript">
												Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
												</script>
											</div>
									</div>
									<div class="line">
										<div class="quarter bold leftmar">Report Type<span class="mandatory">*</span></div>
										<div class="quarter">
											<input type="radio" name="reportFormat" id="reportFormaty" value="pdf" />PDF 
		                                     <input type="radio" name="reportFormat" id="reportFormatn"  value="excel" />EXCEL	
										</div>
									</div>
									<div class="line" style="margin-left: 25%;">
										<div><a href="javascript:getEmpLastModifiedReport('${reports.type}');"><div class="appbutton">Submit</div></a></div>	
										<div><a href="javascript:resetReportList('${reports.type}');"><div class="appbutton">Clear</div></a></div>								
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
<!-- End:R_LastModifiedEmpReport.jsp -->