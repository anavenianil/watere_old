<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:HRDGTabularReport.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/trainingscript.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script language="javascript" src="./script/date.js"></script>
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>

<title>HRDG Reports</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	
		});
</script>
</head>

<body>
	<form:form method="post" commandName="trainingRequest" id="trainingRequest">
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
								<div class="headTitle">HRDG Reports</div>
								<%-- Content Page starts --%>
								<div>
									<div class="line" id="result">
										<jsp:include page="CourseResult.jsp" />
									</div>
									
									<div class="line">
										
										<div class="quarter bold leftmar">Year<span class="mandatory">*</span></div>
										<div class="quarter">
											
												<form:select path="courseYear" id="courseYear" cssStyle="width:145px;" >
												<form:option value="">Select</form:option>
												<form:options items="${sessionScope.FinancialYearList}" itemLabel="financialYear" itemValue="id"></form:options>
						 						</form:select>
											
										</div>
										
									</div>
									<div class="line">
										<div   class="quarter bold leftmar">Report<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="yearBookType" id="yearBookType" cssClass="formSelect"  onmouseover="setSelectWidth('#yearBookType')">
												<form:option value="">Select</form:option>
												<form:option value="category">Category</form:option>
												<form:option value="designation">Designation</form:option>
												
												
											</form:select>											
										</div>
										
									</div>
									
									
									
									<div class="line">
										<div style="margin-left:25%">
										
											<a href="javascript:getHRDGReports();"><div class="appbutton">Report</div></a>
										</div>
										
											<a href="javascript:clearReportPage();"><div class="appbutton">Clear</div></a>
											
											
											
									</div>
									
									
									<div class="line height"></div>
									<div class="line" id="displayTable">
									
									</div>
									
									
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
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type" />
		<form:hidden path="id" id="id"/>
		
		
		</form:form>
		<script>
		var type='<c:out value='${sessionScope.trainingMstType}'/>';
			
		</script>
	</body>
</html>
<!-- End:HRDGTabularReport.jsp -->