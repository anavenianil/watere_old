<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:RetirementReportDetails.jsp -->
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

<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script language="javascript" src="./script/date.js"></script>

<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/retirement.js"></script>



<title>Retirement Reports</title>
<script type="text/javascript">
	$jq(document).ready(function(){
	javascript:clearRetirementReportDetails();
		});
</script>
</head>

<body>
	<form:form method="post" commandName="retirement" id="retirement">
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
								<div class="headTitle">Retirement Reports</div>
								<%-- Content Page starts --%>
								<div>
									
									<div class="line">
										<div  class="quarter bold leftmar">YEAR<span class="failure">*</span></div>
										<div ss class="quarter">
											<form:select  path="yearId" id="yearId" onchange="javascript:getRetirementReportDetailsList();">
											<form:option value="Select">Select</form:option>
											<form:options items="${sessionScope.yearList}" itemLabel="name" itemValue="id"/>
											</form:select>
										</div>
										
									</div>
									
									
									
									
									<div class="line">
										<div style="margin-left:25%">
										
											<a href="javascript:getRetirementReportDetailsList();"><div class="appbutton">Submit</div></a>
										</div>
										
											<a href="javascript:clearRetirementReportDetails();"><div class="appbutton">Clear</div></a>
											
									</div>
									
									
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="RetirementReportDetailsList.jsp"></jsp:include>
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
		
			
		</script>
	</body>
</html>
<!-- End:RetirementReportDetails.jsp-->