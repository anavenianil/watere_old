<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:empEolSearch.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="./styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/payBill.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<title>EOL / HPL Acceptance</title>
</head>
<body >
<form:form method="post" commandName="payBillMaster" id="PayBillMasterBean">
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
							<div class="headTitle" id="headTitle">EOL / HPL Acceptance</div>
								<%-- Content Page starts --%>
								 	<div class="line">
										<div class="quarter leftmar">Search SFID<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="sfidSearchKey" id="sfidSearchKey"  cssClass="formSelect"  />
										</div>
									</div>
									
									<div class="line">
										<div class="quarter leftmar">Start Date<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="startDate" id="startDate" cssClass="required" onkeypress="javascript:setReadOnly(event, 'startDate')"/>
										<img  src="./images/calendar.gif" id="date_start_trigger1" />	
										<script type="text/javascript">
											Calendar.setup({inputField :"startDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
										</script>
										</div>
										<div class="quarter leftmar">End Date<span class="mandatory">*</span></div>
										<div class="quarter"><form:input path="endDate" id="endDate" cssClass="required" onkeypress="javascript:setReadOnly(event, 'endDate')"/>
										<img  src="./images/calendar.gif" id="date_start_trigger2" />	
										<script type="text/javascript">
											Calendar.setup({inputField :"endDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
										</script>
										</div>
									</div>
									
									<div class="line" style="margin-left:45%;">
										<div class="line">
											<div class="expbutton"><a href="javascript:searchEMPEol();"><span>Submit</span></a></div>
										</div>
									</div>
							<div class="line" id="result"></div>
									
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
		
	</body>
</html>
<!-- End:empEolSearch.jsp -->	