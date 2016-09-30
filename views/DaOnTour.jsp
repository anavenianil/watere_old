<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:DaOnTour.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/tada.js"></script>

<title>DA On Tour Master</title>

</head>
<body onload="javascript:clearlocalRMA();">
	<form:form method="post" commandName="tada" id="TadaManagementBean">
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
								<div class="headTitle">DA On Tour Master</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div class="line">
										<div class="quarter leftmar">DA Range From<span class="mandatory">*</span></div>
										<div class="quarter">
										 <form:input path="daRangeFrom" id="daRangeFrom"  placeholder="Enter DA From Range" onkeypress="javascript:return checkFloat(event,'daRangeFrom');" />&nbsp;%  
										</div>
										<div class="quarter leftmar">DA Range To<span class="mandatory">*</span></div>
										<div class="quarter">
										 <form:input path="daRangeTo" id="daRangeTo" placeholder="Enter DA To Range"  onkeypress="javascript:return checkFloat(event,'daRangeTo');"/>&nbsp;% 
										</div>
									</div>
									<div>
										<div class="quarter leftmar">DA On Tour<span class="mandatory">*</span></div>
										    <div class=quarter>
										        <form:input path="daOnTour" id="daOnTour" placeholder="Enter DA On Tour" onkeypress="javascript:return checkFloat(event,'daOnTour');"/>&nbsp;% 
										    </div>
									</div>
									
									
									
									
						   			<div class="line" style="margin-left: 35%;">
											<div class="expbutton"><a onclick="javascript:submitDaOnTour();"> <span>Submit</span></a></div>
											<div class="expbutton"><a onclick="javascript:clearDaOnTour();"> <span>Clear</span></a></div>
									</div>	
									<div class="line"><hr /></div>
									<div class="line" id="result">
										<jsp:include page="DaOnTourMasterList.jsp"></jsp:include>								
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
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		</form:form>	
		<script type="text/javascript">
		designationListJSON = <%= (net.sf.json.JSONArray)session.getAttribute("designationListJSON") %>;
		</script>	
			
	</body>
</html>
<!-- End:DaOnTour.jsp -->