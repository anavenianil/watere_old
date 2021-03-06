<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:InventoryReport.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<title>Inventory Report</title>
</head>

<body onload="javascript:resetReportList('inventoryNo');">
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
								<div class="headTitle">Inventory Report</div>
								<div>
									<%-- Content Page starts --%>
									  <div class="line">
										<div class="quarter bold leftmar">Inventory No<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="inventoryNo" id="inventoryNo"  cssClass="formSelect" >
												<form:option value="select">Select</form:option>
												<form:option value="${reports.value}">All</form:option>
												<form:options items="${sessionScope.inventoryReportHomeList}" itemValue="id" itemLabel="inventoryNo"/>
											</form:select>
										</div>
									</div>	
									<div class="line">
										<div class="quarter bold leftmar">Report Type<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="reportFormat" id="reportFormat"  cssClass="formSelect" >
												<form:option value="select">Select</form:option>
             	      							<form:option value="pdf">PDF</form:option>
             	      							<form:option value="excel">Excel</form:option>
             	   							</form:select>
										</div>
									</div>
									<div class="line" style="margin-left: 25%;">
										<div><a href="javascript:getInventoryReport();"><div class="appbutton">Submit</div></a></div>	
										<div><a href="javascript:resetReportList('inventoryNo');"><div class="appbutton">Clear</div></a></div>								
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
<!-- End:InventoryReport.jsp -->