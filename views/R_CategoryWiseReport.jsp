<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:R_CategoryWiseReport.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/report.js"></script>
<title>Category Wise Report</title>
</head>

<body onload="javascript:resetReportList('category');">
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
								<div class="headTitle">Category Wise Report</div>
								<div>
									<%-- Content Page starts --%>
									  <div class="line">
										<div class="quarter bold leftmar">Category Name<span class="mandatory">*</span></div>
										<div class="quarter">
											<form:select path="category" id="category"  cssClass="formSelect" onmouseover="setSelectWidth('#category')">
												<form:option value="select">Select</form:option>
												<form:option value="${reports.value}">All</form:option>
												<form:options items="${sessionScope.categoryReportHomeList}" itemValue="id" itemLabel="name"/>
											</form:select>
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
										<div><a href="javascript:getCategoryWiseReport();"><div class="appbutton">Submit</div></a></div>	
										<div><a href="javascript:resetReportList('category');"><div class="appbutton">Clear</div></a></div>								
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
<!-- End:R_CategoryWiseReport.jsp -->