<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TestPage.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
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
<title>Hindi Incentive</title>
</head>

<body>
	<form:form method="post" commandName="payBillMaster">
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
								<div class="headTitle">Hindi Incentive Details</div>
								<div>
									<%-- Content Page starts --%>
								<div class="line">
										<div class="quarter leftmar">SFID<span class="mandatory">*</span></div>
										<div class="quarter">
										<form:input path="sfidSearchKey" id="sfidSearchKey"  />
	                			</div>
			</div>
			<div class="line">
					<div class="quarter leftmar">Start Date<span class="mandatory">*</span></div>
					
					<div class="quarter"><form:input path="startDate" id="startDate" cssClass="required" readonly="true"/>
					<img  src="./images/calendar.gif" id="date_start_trigger1" />	
					<script type="text/javascript">
						Calendar.setup({inputField :"startDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
					</script>
					</div>
					<div class="quarter leftmar">End Date<span class="mandatory">*</span></div>
					<div class="quarter"><form:input path="endDate" id="endDate" cssClass="required" readonly="true"/>
					<img  src="./images/calendar.gif" id="date_start_trigger2" />	
					<script type="text/javascript">
						Calendar.setup({inputField :"endDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger2",singleClick : true,step : 1});
					</script>
					</div>
			</div>
				<div class="line">
										<div class="quarter leftmar">Present Inst<span class="mandatory">*</span></div>
										<div class="quarter">
										<form:input path="inst" id="inst"  />
	                			</div></div>
	                			<div class="line">
										<a href="javascript:submitPayHindiDetails();" class="appbutton">Submit</a>
										<a href="javascript:clearPayHindiDetails();" class="appbutton">Clear</a>
								</div>	
								<div class="line" id="result"><jsp:include page="payHindiInceList.jsp"></jsp:include></div>				
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
		<form:hidden path="pk"/>
		</form:form>
	</body>
</html>
<!-- End:TestPage.jsp -->