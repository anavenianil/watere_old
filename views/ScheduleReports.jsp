<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:ScheduleReports.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/payBill.js"></script>
<title>Pay Bill</title>
</head>

<body onload="javascript:clearScheduleReport();">
	<form:form method="post" commandName="schedulereports">
		<div>
			<div class="Innermaindiv">
				<div class="header"></div>
				<div ><jsp:include page="MenuLinks.jsp" /></div>					
				<div class="mainDisplay">
					<div class="whitebox1">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								<div class="whitebox_tr"></div>
							</div>
						</div>
						<div class="whitebox_m1">
							<div class="lightblueBg1">
								<div class="headTitle">Schedule Reports</div>
								<div>
									<%-- Content Page starts --%>
								<div class="line">
								<div class="quarter leftmar">Report Type<span  class="mandatory">*</span></div>
								<div class="quarter"><form:select path="reportType" id="reportType" cssClass="formSelect" onchange="javascript:paySlip();">
								<form:option value="select">Select</form:option>
								<form:option value="catList">Emp Category Wise List</form:option>
								<form:option value="GPF" >GPF</form:option>
								<form:option value="CGHS" >CGHS</form:option>
								<form:option value="CGEIS" >CGEIS</form:option>
								<form:option value="LFCHARGES" >LFCHARGES</form:option>
								<form:option value="PI" >PROF TAX</form:option>
								<form:option value="CONVEYANCE" >CONVEYANCE</form:option>
								<form:option value="ITAX" >INCOME TAX</form:option>
								<form:option value="REGPAYBILL" >REGULAR PAYBILL</form:option>
								<form:option value="CASHSTATEMENT" >CASH STATEMENT</form:option>
								<form:option value="EOL/HPL" >EOL/HPL</form:option>
								<form:option value="Festival">FESTIVAL</form:option>
								<form:option value="pc">PC</form:option>
								<form:option value="HBA">HBA</form:option>
								<form:option value="paySlip">Pay Slip</form:option>
								
								</form:select></div>
								</div>
								<div class="line" id="subType">
								<div class="quarter leftmar">Report Sub Type<span  class="mandatory">*</span></div>
								<div class="quarter"><form:select path="reportSubType" id="reportSubType" cssClass="formSelect">
								<form:option value="select">Select</form:option>
								
								<form:option value="Cycle">Cycle</form:option>
								<form:option value="Car">Car</form:option>
								<form:option value="Scooter">Scooter</form:option>
								</form:select></div>
								</div>
								<div class="line" id="subType2" style="display:none">
										<div class="quarter leftmar">Year And Month<span class="mandatory">*</span></div>
										<div class="quarter">
										     <form:select path="paySlipYear" id="paySlipYear" cssStyle="width:90px;" onchange="javascript:getPaySlipMonthList();">
												    <form:option value="select">Select</form:option>
												    <form:options items="${schedulereports.paySlipYearList}"/>
											 </form:select>
										</div>
										<div id="monthList" class="quarter"></div> 
								</div>
								<div class="line" id="categoryName">
										<div class="quarter leftmar">Category Name <span
											class="mandatory">*</span></div>
										<div class="quarter">
										<form:select path="categoryID" id="categoryID" cssClass="formSelect"  >
											<form:option value="select">Select</form:option>
											<form:options items="${schedulereports.categoryList}" itemValue="id" itemLabel="name" onmouseover="setSelectWidth('#categoryID')" />
										                      </form:select>
										                      </div>
								</div>
								<div class="line" id="month">
	                              <div class="quarter leftmar">Month and Year<span class="mandatory">*</span></div>
	                                    <div class="quarter"><form:input path="schedulemonth" id="schedulemonth" cssClass="required" onkeypress="javascript:setReadOnly(event,'schedulemonth')"/>
	                                    	<img src="./images/calendar.gif" id="date_start_trigger2"/>
	                                    		<script type="text/javascript">
	Calendar.setup({inputField :"schedulemonth",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
										       </script>
										</div>
								</div>
								
								<div class="line">
									<div style="margin-left: 25%;">
										<div class="expbutton"><a href="javascript:getScheduleReport();"><span>Submit</span></a></div>
										<div class="expbutton"><a href="javascript:clearScheduleReport();"><span>Clear</span></a></div>
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
		<script type="text/javascript">$jq('#subType').hide()</script>
	</body>
	
	
</html>
<!-- End:ScheduleReports.jsp -->