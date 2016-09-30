<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:pisMiscReports.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
<title>PisMiscellaneous Reports</title>
</head>

<body id="body">
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
								<div class="headTitle">PisMiscellaneous Reports</div>
								<div>
									<%-- Content Page starts --%>
										<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable" >
									<tr class="collapsed" id="treeModel0"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewMiscReports('nominalreport','treeModel0','nominalGrid','')"><b>Nominal Report</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="nominalGrid" style="display:none;margin-left:250px;">
								<div class="quarter bold" style="color: blue;">Report Type <span class="mandatory">*</span></div>
										<div class="quarter">
												<a
												href="javascript:nominalReport();"
												style="color: purple"> PDF </a>
										</div>	
										<div class="quarter">
												<a
												href="javascript:nominalReportXL('excel');"
												style="color: purple"> EXCEL </a>
										</div>	
																		
								</div>
									
									<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable" >
									<tr class="collapsed" id="treeModel"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewMiscReports('categoryreport','treeModel','categoryGrid','')"><b>Category Wise Reports</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="categoryGrid" style="display:none;margin-left:250px" >
								<div class="quarter bold" style="color: blue;">Category Wise Report</div>
								<div class="quarter" style="width:25%">
											<form:select path="category" id="category"  cssClass="formSelect" onmouseover="setSelectWidth('#category')">
												<form:option value="select">Select</form:option>
												<form:option value="${reports.value}">All</form:option>
												<form:options items="${sessionScope.categoryReportHomeList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
										<div class="quarter">
											<div class="quarter">
											<a
												href="javascript:getCategoryWiseReport('pdf');"
												style="color: purple"> PDF </a>
										</div>
										<div class="quarter">
											<a
												href="javascript:getCategoryWiseReport('excel');"
												style="color: purple"> EXCEL</a>
										</div>
										</div>									
								</div>
								<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel2"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewMiscReports('designationwiseReport','treeModel2','designationGrid','')"><b>Designation Wise Report</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="designationGrid" style="display:none;margin-left:250px">
								<div class="quarter bold" style="color: blue;">Designation<span class="mandatory">*</span></div>
								<div class="quarter" style="width:25%">
											<form:select path="designation" id="designation" cssClass="formSelect" onmouseover="setSelectWidth('#designation')" >
												<form:option value="select">Select</form:option>
												<form:option value="${reports.value}">All</form:option>
												<form:options items="${sessionScope.designationReportHomeList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
										<div class="quarter">
											<div class="quarter">
											<a
												href="javascript:getDesignationwieReport('pdf');"
												style="color: purple"> PDF </a>
										</div>
										<div class="quarter">
											<a
												href="javascript:getDesignationwieReport('excel');"
												style="color: purple"> EXCEL</a>
										</div>
										</div>									
								</div>
								<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel3"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewMiscReports('EmpGroupwiseReport','treeModel3','groupGrid','')"><b>Group Wise Report</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="groupGrid" style="display:none;margin-left:250px">
								<div class="quarter bold" style="color: blue;">Group Name<span class="mandatory">*</span></div>
								<div class="quarter" style="width:25%">
											<form:select path="groupName" id="groupName" cssClass="formSelect" >
												<form:option value="select">Select</form:option>
												<form:option value="${reports.value}">All</form:option>
												<form:options items="${sessionScope.groupReportHomeList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
										<div class="quarter">
											<div class="quarter">
											<a
												href="javascript:getGroupWiseReport('pdf');"
												style="color: purple"> PDF </a>
										</div>
										<div class="quarter">
											<a
												href="javascript:getGroupWiseReport('excel');"
												style="color: purple"> EXCEL</a>
										</div>
										</div>									
								</div>
								<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel4"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewMiscReports('empDOJwiseReport','treeModel4','joiningGrid','')"><b>Date of Joining Wise Report</b></span>
									</td></tr>
									</table></div>
											<div class="line" id="joiningGrid" style="display:none;margin-left:250px">
									<div class="quarter bold leftmar">From Date<span class="mandatory">*</span></div>
											<div class="quarter ">
												<form:input path="fromDate" id="fromDate" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('fromDate');"/>
												<img  src="./images/calendar.gif"   id="date_start_trigger1" />	
												<script type="text/javascript">
												Calendar.setup({inputField :"fromDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
												</script>
											</div>
											<div class="quarter">
											<a
												href="javascript:getEmpDOJWiseReport('pdf');"
												style="color: purple"> PDF </a>
										</div>
										<div class="quarter">
											<a
												href="javascript:getEmpDOJWiseReport('excel');"
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
										<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel5"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewMiscReports('PHEmpReport','treeModel5','phGrid','')"><b>PH Report</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="phGrid" style="display:none;margin-left:250px">
								<div class="quarter bold" style="color: blue;">Report Type<span class="mandatory">*</span></div>
								
										<div class="quarter">
											<div class="quarter">
											<a
												href="javascript:getPHEmpReport('pdf');"
												style="color: purple"> PDF </a>
										</div>
										<div class="quarter">
											<a
												href="javascript:getPHEmpReport('excel');"
												style="color: purple"> EXCEL</a>
										</div>
										</div>									
								</div>
									<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel6"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewMiscReports('EmpReligionwiseReport','treeModel6','religionGrid','')"><b>Religion Wise Report</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="religionGrid" style="display:none;margin-left:250px">
								<div class="quarter bold" style="color: blue;">Religion<span class="mandatory">*</span></div>
								<div class="quarter" style="width:25%">
											<form:select path="religionName" id="religionName" cssClass="formSelect" >
												<form:option value="select">Select</form:option>
												<form:option value="${reports.value}">All</form:option>
												<form:options items="${sessionScope.religionReportHomeList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
										<div class="quarter">
											<div class="quarter">
											<a
												href="javascript:getReligionWiseReport('pdf');"
												style="color: purple"> PDF </a>
										</div>
										<div class="quarter">
											<a
												href="javascript:getReligionWiseReport('excel');"
												style="color: purple"> EXCEL</a>
										</div>
										</div>									
								</div>
								<div id="line" style="float:left;width: 100%;margin-left:200px;margin-left:200px"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel7"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewMiscReports('EmpReservationwiseReport','treeModel7','reservationGrid','')"><b>Reservation  Wise Report</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="reservationGrid" style="display:none;margin-left:250px">
								<div class="quarter bold" style="color: blue;">Reservation <span class="mandatory">*</span></div>
											<div class="quarter">
											<div class="quarter">
											<a
												href="javascript:getReservationWiseReport('reservationReport','pdf','');"
												style="color: purple"> PDF </a>
										</div>
										<div class="quarter">
											<a
												href="javascript:getReservationWiseReport('reservationReport','excel','');"
												style="color: purple"> EXCEL</a>
										</div>
										</div>										
								</div>
								<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel8"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewMiscReports('EmpCommunityReport','treeModel8','communityGrid','')"><b>Community Wise Report</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="communityGrid" style="display:none;margin-left:250px">
								<div class="quarter bold" style="color: blue;">Community Type <span class="mandatory">*</span></div>
										<div class="quarter" style="width:25%">
											<form:select path="community" id="community" cssClass="formSelect" >
												<form:option value="select">Select</form:option>
												<form:option value="${reports.value}">All</form:option>
												<form:options items="${sessionScope.communityReportHomeList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
										<div class="quarter">
											<div class="quarter">
											<a
												href="javascript:getCommunityWiseReport('pdf');"
												style="color: purple"> PDF </a>
										</div>
										<div class="quarter">
											<a
												href="javascript:getCommunityWiseReport('excel');"
												style="color: purple"> EXCEL</a>
										</div>
										</div>									
								</div>
								<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel11"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewMiscReports('EmpLastModifyReport','treeModel11','joiningDrdoGrid','DOJDRDO')"><b>Date of Joining in DRDO Report</b></span>
									</td></tr>
									</table></div>
											<div class="line" id="joiningDrdoGrid" style="display:none;margin-left:250px">
									<div class="quarter bold leftmar">From Date<span class="mandatory">*</span></div>
											<div class="quarter ">
												<form:input path="fromDate" id="fromDate2" cssClass="dateClass" readonly="true" onclick="javascript:clearDateText('fromDate2');"/>
												<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
												<script type="text/javascript">
												Calendar.setup({inputField :"fromDate2",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
												</script>
											</div>
											<div class="quarter">
											<a
												href="javascript:getEmpLastModifiedReport('DOJDRDO','pdf','fromDate2','toDate2');"
												style="color: purple"> PDF </a>
										</div>
										<div class="quarter">
											<a
												href="javascript:getEmpLastModifiedReport('DOJDRDO','excel','fromDate2','toDate2');"
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
										<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel12"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewMiscReports('','treeModel12','experienceGrid','')"><b>DRDO Employees Experience Report</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="experienceGrid" style="display:none;margin-left:250px">
								<div class="quarter bold" style="color: blue;">Report Type <span class="mandatory">*</span></div>
										<div class="quarter">
											<a
												href="javascript:createEmpExpReport();"
												style="color: purple"> PDF </a>
										</div>								
								</div>
									<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel13"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewMiscReports('','treeModel13','retirementGrid','')"><b>Retirement Report</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="retirementGrid" style="display:none;margin-left:250px">
								<div class="quarter bold" style="color: blue;">Report Type <span class="mandatory">*</span></div>
										<div class="quarter">
											<a
												href="javascript:createRetirementReport();"
												style="color: purple"> PDF </a>
										</div>								
								</div>
								
									<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel14"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewMiscReports('','treeModel14','financeGrid','')"><b>Finance Status Active Report</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="financeGrid" style="display:none;margin-left:250px">
								<div class="quarter bold" style="color: blue;">Report Type <span class="mandatory">*</span></div>
										<div class="quarter">
												<a
												href="javascript:financeStatusActiveReport();"
												style="color: purple"> PDF </a>
										</div>	
																		
								</div>
								
								
								<!-- //NARESH -->
										<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel15"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewMiscReports('','treeModel15','AgeGrid','')"><b>DRDS EMPLOYEES (AGE >=40)Report</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="AgeGrid" style="display:none;margin-left:250px">
								<div class="quarter bold" style="color: blue;">Report Type <span class="mandatory">*</span></div>
										<div class="quarter">
												<a
												href="javascript:AgeReport();"
												style="color: purple"> PDF </a>
										</div>										
								</div>
							
							<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel16"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewMiscReports('','treeModel16','QualificationGrid','')"><b>Qualification DetailsReport</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="QualificationGrid" style="display:none;margin-left:250px">
								<div class="quarter bold" style="color: blue;">Report Type <span class="mandatory">*</span></div>
										<div class="quarter">
												<a href="javascript:QualificationReport();"
												style="color: purple"> PDF </a>
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
<!-- End:pisMiscReports.jsp -->