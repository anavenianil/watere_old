<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:SanctionAndContingent.jsp -->
<%@ page import="net.sf.json.JSONSerializer,java.util.List"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>


<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/calendar-hd.js"></script>
<script language="javascript" src="./script/RegExpValidate.js"></script>	
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/loan.js"></script>
<script type="text/javascript" src="script/script.js"></script>

<title>Sanction Report & Contingent Bills</title>
</head>

<body onload="javascript:clearHQReport()">
	<form:form method="post" commandName="loan" >
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
								<div class="headTitle">Sanction Report & Contingent Bills</div>
								<%-- Content Page starts --%>
								<div class="line">
								<div class="line">
							  			<div class="quarter leftmar">HQ Report Number<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:select path="reportNumber" id="reportNumber"  cssClass="formSelect required" onmouseover="setSelectWidth('#reportNumber')" onchange="javascript:selectedHQDates();">
                                               <form:option value="0">Select</form:option>
                                               <form:options items="${loan.reportNumberList}" itemValue="hqReportNumber" itemLabel="hqReportNumber" />
                                            </form:select>
							  			</div>
							  		</div>
							  		<div class="line" id="reportDateDiv" style="display:none">
							  			<div class="quarter leftmar">Report Date<span class="mandatory">*</span></div>
							  			<div class="quarter">
							  				<form:select path="reportDate" id="reportDate"  cssClass="formSelect required" onmouseover="setSelectWidth('#reportDate')">

                                            </form:select>
							  			</div>
							  			</div>
							  			<div class="line">
										<div style="margin-left:30%;">
											<div ><a href="javascript:searchSanctionContingentDetails();" class="appbutton">Search</a></div>
											<div ><a href="javascript:clearPaybillDetails();" class="appbutton"><span>Clear</span></a></div>
										</div>
										</div>
									<div id="LoanSanctionContingentList" class="line" >
										<jsp:include page="SanctionContingentList.jsp" />
									</div>
							  			<script>
											jsonHQReportDate =<%= (net.sf.json.JSONArray)JSONSerializer.toJSON((List)session.getAttribute("loanHQListJSON")) %>;
										</script>
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
		<form:hidden path="message"/>
		<form:hidden path="historyID"/>
		<form:hidden path="requestId"/>
		<form:hidden path="statusMsg"/>
		<form:hidden path="back"/>		
		</form:form>
	</body>
</html>
<!-- End:SanctionAndContingent.jsp -->