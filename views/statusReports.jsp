<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:statusReports.jsp -->
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
<title>Status Reports</title>
</head>
<body>
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
								<div class="headTitle">Status Reports</div>
								<div>
									<%-- Content Page starts --%>
				<div id="line" style="float:left;width: 100%;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewStatusReports('treeModel','allReqGrid')"><b>All Request</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="allReqGrid" style="display:none;">
								<div class="quarter bold" style="color: blue;">Report Type <span class="mandatory">*</span></div>
										<div class="quarter">
												<a
												href="javascript:normalReports('all');"
												style="color: purple"> PDF </a>
										</div>	
																		
								</div>
								<div id="line" style="float:left;width: 100%;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel2"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewStatusReports('treeModel2','pendingGrid')"><b>Pending Request Count</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="pendingGrid" style="display:none;">
								<div class="quarter bold" style="color: blue;">Report Type <span class="mandatory">*</span></div>
										<div class="quarter">
												<a
												href="javascript:normalReports('penCount');"
												style="color: purple"> PDF </a>
										</div>	
																		
								</div>
								<div id="line" style="float:left;width: 100%;"><table class="treeTable"  >
									<tr class="collapsed" id="treeModel3"><td><span class="expander"  style="margin-left: -8px; padding-left: 26px" onclick="javascript:viewStatusReports('treeModel3','leaveGrid')"><b>Leave Pending Request</b></span>
									</td></tr>
									</table></div>
									<div class="line" id="leaveGrid" style="display:none;">
								<div class="quarter bold" style="color: blue;">Report Type <span class="mandatory">*</span></div>
										<div class="quarter">
												<a
												href="javascript:normalReports('leavePending');"
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

<!-- end:statusReports.jsp -->