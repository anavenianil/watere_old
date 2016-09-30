<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:addressReports.jsp -->
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
<title>addressReports</title>
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
								<div class="headTitle">Address Reports</div>
								<div>
									<%-- Content Page starts --%>
									
								<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable" >
									<tr class="collapsed" id="treeModel"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewaddressReports('PRESENTADDRESSREPORT','treeModel','orgGrid','')"><b>PRESENT ADDRESS REPORT </b></span>
									</td></tr>
									</table></div>
									<div class="line" id="orgGrid" style="display:none;margin-left:250px;">
								    <div class="quarter bold" style="color: blue;">Report Type <span class="mandatory">*</span></div>
										<div class="quarter">
												<a
												href="javascript:PRESENTADDRESSREPORT();"
												style="color: purple"> PDF </a>
										</div>		
																		
								</div>
									
									<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable" >
									<tr class="collapsed" id="treeModel2"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewaddressReports('CGHSREPORT','treeModel2','mappingGrid','')"><b>CGHS ADDRESS REPORT</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="mappingGrid" style="display:none;margin-left:250px" >
								    <div class="quarter bold" style="color: blue;">Report Type</div>
										<div class="quarter">
											<div class="quarter">
											<a
												href="javascript:CGHSREPORT('pdf');"
												style="color: purple"> PDF </a>
										</div>
										
										</div>									
								</div>
								<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable"  >
									<tr class="collapsed" id="treeMode3"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewaddressReports('addressreport','treeMode3','hierarchyGrid','')"><b>PERMENANT ADDRESS REPORT </b></span>
									</td></tr>
									</table></div>
									<div class="line" id="hierarchyGrid" style="display:none;margin-left:250px">
								<div class="quarter bold" style="color: blue;">Report Type<span class="mandatory">*</span></div>
								
										<div class="quarter">
											<div class="quarter">
											<a
												href="javascript:addressreport('pdf');"
												style="color: purple"> PDF </a>
										</div>
						
										</div>									
								</div>
								
								
								<div id="line" style="float:left;width: 100%;margin-left:200px;"><table class="treeTable"  >
									<tr class="collapsed" id="treeMode4"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewaddressReports('addressreport','treeMode4','persinfoGrid','')"><b>PERSONAL INFORMATION REPORT </b></span>
									</td></tr>
									</table></div>
									
									<div class="line" id="persinfoGrid" style="display:none;margin-left:250px">
								<div class="quarter bold" style="color: blue;">Report Type<span class="mandatory">*</span></div>
								
										<div class="quarter">
											<div class="quarter">
											<a
												href="javascript:persInforeport('pdf');"
												style="color: purple"> PDF </a>
										</div>
						
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
<!-- End:OrgHierarchReport.jsp -->