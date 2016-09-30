<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:PisReportsHome.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link rel="stylesheet" type="text/css" href="styles/jquery.treeTable.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.treeTable.js"></script>

<title>PisReportsHome</title>
</head>

<body onload="">
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
							
								<div class="headTitle" id="headTitle">PIS-Regular</div>
								<div>
									<%-- Content Page starts --%>
									
									<div id="line" style="margin-left:200px"><table class="treeTable" >
									<tr class="collapsed" id="treeModel"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewSubReports('monthlyGrid')"><b>Monthly Reports</b></span>
									</td></tr>
									</table></div>
									
									
								<div class="line" id="monthlyGrid" style="display:none;margin-left:250px">
								<div class="quarter bold" style="color: blue;">Manpower vacancy Report</div>
								<div class="quarter" style="width:25%;margin-left:-60px"><input type="radio" name="monthlyFlag" style="formSelect" id="monthlyFlagY" value="catVacant" />Category wise vacancy position <br/>
			                                       <input type="radio" name="monthlyFlag" id="monthlyFlagN"  value="desigVacant" />Designation wise vacancy position	</div>
			                                       <div class="quarter"><a href="javascript:getReservationWiseReport('monthly','pdf');" style="color: purple">PDF</a></div>
								<div class="quarter" style="margin-left:-150px;" ><a href="javascript:getReservationWiseReport('monthly','excel');" style="color: purple"> EXCEL</a></div>
								
								</div>
									<div id="line"  style="float:left;width: 100%;margin-left:200px;" ><table class="treeTable" >
									<tr class="collapsed" id="treeModel2"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewSubReports('quarterlyGrid')"><b>Quarterly Reports</b></span>
									</td></tr>
									</table></div>
									
									
									<div class="line" id="quarterlyGrid" style="display:none;margin-left:250px;">
										<div class="quarter bold leftmar">
											Date<span class="mandatory">*</span>
										</div>
										<div class="quarter ">
											<form:input path="fromDate" id="fromDate"
												cssClass="dateClass" readonly="true"
												onclick="javascript:clearDateText('fromDate');" />
											<img src="./images/calendar.gif" id="date_start_trigger1" />
											<script type="text/javascript">
												Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
												</script>
										</div>
										<div class="quarter">
											<a
												href="javascript:getReservationWiseReport('totalVacant','pdf');"
												style="color: purple"> PDF</a>
										</div>
										<div class="quarter">
											<a
												href="javascript:getReservationWiseReport('totalVacant','excel');"
												style="color: purple"> EXCEL</a>
										</div>
									</div>
									<div id="line"  style="float:left;width: 100%;margin-left:200px;"><table class="treeTable" >
									<tr class="collapsed" id="treeModel3" ><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewSubReports('halfYearlyGrid')"><b>Half Yearly Reports</b></span>
									</td></tr>
									</table></div>
									
									<div class="" id="halfYearlyGrid" style="display:none;margin-left:250px;">
									<div class="line">
									<div class="quarter bold" style="color: blue;">Minority Report </div>
									<div class="quarter">&nbsp;</div>
										<div class="quarter" style="padding-left: 8px;">
											<a
												href="javascript:getReservationWiseReport('halfYearEnding','pdf');"
												style="color: purple"> PDF</a>
										</div>
										<div class="quarter">
											<a
												href="javascript:getReservationWiseReport('halfYearEnding','excel');"
												style="color: purple">EXCEL</a>
										</div></div>
											<div class="line">
										<div class="quarter bold" style="color: blue;">PH Report </div>
										<div class="line">
									<div class="quarter bold leftmar">From Date<span class="mandatory">*</span></div>
											<div class="quarter ">
												<form:input path="fromDate" id="fromDate2" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('fromDate2');"/>
												<img  src="./images/calendar.gif"   id="date_start_trigger5" />	
												<script type="text/javascript">
												Calendar.setup({inputField :"fromDate2",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger5",singleClick : true,step : 1});
												</script>
											</div>
											<div class="quarter">
											<a
												href="javascript:getEmpLastModifiedReport('HalfYearPhReport','pdf','fromDate2','toDate');"
												style="color: purple"> PDF </a>
										</div>
										<div class="quarter">
											<a
												href="javascript:getEmpLastModifiedReport('HalfYearPhReport','excel','fromDate2','toDate');"
												style="color: purple"> EXCEL</a>
										</div>
										<div class="line">
											<div class="quarter bold leftmar">To Date<span class="mandatory">*</span></div>
											<div class="quarter ">
												
												<form:input path="toDate" id="toDate" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('toDate');"/>
												<img  src="./images/calendar.gif"   id="date_start_trigger2" />	
												<script type="text/javascript">
												Calendar.setup({inputField :"toDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
												</script>
												
											</div>
											</div>
										</div>
									</div></div>
	<div id="line"  style="float:left;width: 100%;margin-left:200px;"><table class="treeTable" >
									<tr class="collapsed" id="treeModel4"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewSubReports('anualGrid')"><b>Annual Reports</b></span>
									</td></tr>
									</table></div>
									<div id="anualGrid" style="display:none;margin-left:250px;">
								<div class="line">
								<div class="quarter bold" style="color: blue;">Annual Nominal Roll Report</div>
			                                       <div class="quarter">&nbsp;</div>
										<div class="quarter" style="padding-left: 9px;"><a href="javascript:getReservationWiseReport('empDetails','pdf');" style="color: purple"> PDF</a></div>
								<div class="quarter" ><a href="javascript:getReservationWiseReport('empDetails','excel');" style="color: purple">EXCEL</a></div>
								
								</div>
								<div class="line">
								<div class="quarter bold" style="color: blue;">Minority welfare Report</div>
			                                       <div class="quarter">&nbsp;</div>
										<div class="quarter" style="padding-left: 9px;"><a href="javascript:getReservationWiseReport('annualYearEnding','pdf');" style="color: purple"> PDF</a></div>
								<div class="quarter" ><a href="javascript:getReservationWiseReport('annualYearEnding','excel');" style="color: purple"> EXCEL</a></div>
								
								</div>
								<div class="line">
								<div class="quarter bold" style="color: blue;">Annual HRDG Nominal Report </div>
			                                        <div class="quarter">&nbsp;</div>
										<div class="quarter" style="padding-left: 9px;"><a href="javascript:getReservationWiseReport('hrdgAnnual','pdf');" style="color: purple"> PDF</a></div>
								<div class="quarter" ><a href="javascript:getReservationWiseReport('hrdgAnnual','excel');" style="color: purple"> EXCEL</a></div>
								
								</div>
								<div class="line">
								<div class="quarter bold" style="color: blue;">Annual Disabilities Report </div>
								<div class="line">
									       <div class="quarter bold leftmar">Report<span class="mandatory">*</span></div>
									       <div class="quarter" style="width:24%">
 
		                                         <input type="radio" name="pwd" id="pwd1" value="HalfYearAllPhReport" />PWDI  
		                                         <input type="radio" name="pwd" id="pwd2"  value="phVacantReport" />PWDII	
		                                         </div>
		                                        
		                                         		 
		                                    
											
									</div>
									<div class="line">
									 <div class="quarter bold leftmar">FromDate<span class="mandatory">*</span></div>
									       <div class="quarter" style="width:24%">
 
		                                        <form:input path="fromDate" id="fromDate5" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('fromDate5');"/>
												<img  src="./images/calendar.gif"   id="date_start_trigger7" />	
												<script type="text/javascript">
												Calendar.setup({inputField :"fromDate5",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger7",singleClick : true,step : 1});
												</script>
		                     
		                                         </div>
		                                          <div class="quarter"><a href="javascript:getDisabilitiesAnnualReport('pdf');" style="color: purple">PDF</a></div>
								<div class="quarter" ><a href="javascript:getDisabilitiesAnnualReport('excel');" style="color: purple"> EXCEL</a></div> 
		                                         <div class="line">
									 <div class="quarter bold leftmar">ToDate<span class="mandatory">*</span></div>
									       <div class="quarter" style="width:24%">
 
		                                       
		                                       <form:input path="toDate" id="toDate4" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('toDate4');"/>
												<img  src="./images/calendar.gif"   id="date_start_trigger8" />	
												<script type="text/javascript">
												Calendar.setup({inputField :"toDate4",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger8",singleClick : true,step : 1});
												</script>
		                                         </div>
		                                         </div>
									
		                                         </div>
		                                         
			                                       						
								</div>
										<div class="line">
										<div class="quarter bold" style="color: blue;">SC/ST/OBC Report </div>
										<div class="line">
									<div class="quarter bold leftmar">From Date<span class="mandatory">*</span></div>
											<div class="quarter ">
												<form:input path="fromDate" id="fromDate3" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('fromDate3');"/>
												<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
												<script type="text/javascript">
												Calendar.setup({inputField :"fromDate3",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
												</script>
											</div>
											<div class="quarter">
											<a
												href="javascript:getEmpLastModifiedReport('scStObc','pdf','fromDate3','toDate2');"
												style="color: purple"> PDF</a>
										</div>
										<div class="quarter">
											<a
												href="javascript:getEmpLastModifiedReport('scStObc','excel','fromDate3','toDate2');"
												style="color: purple"> EXCEL</a>
										</div>
										<div class="line">
											<div class="quarter bold leftmar">To Date<span class="mandatory">*</span></div>
											<div class="quarter ">
												
												<form:input path="toDate" id="toDate2" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('toDate2');"/>
												<img  src="./images/calendar.gif"   id="date_start_trigger3" />	
												<script type="text/javascript">
												Calendar.setup({inputField :"toDate2",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
												</script>
												
											</div>
											</div>
										</div>
									</div>
									</div>


									<div class="line mandatory">
										<b>&nbsp;&nbsp;Note:</b> Click on the arrow to expand the tree.
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
<!-- End:PisReportsHome.jsp -->